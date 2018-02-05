package cn.smart.cloud.biz.opadmin.controller.garten.video;

import cn.smart.cloud.biz.opadmin.constant.Constant;
import cn.smart.cloud.biz.opadmin.controller.MessageRender;
import cn.smart.cloud.biz.opadmin.entity.fr.*;
import cn.smart.cloud.biz.opadmin.entity.garten.GartenClass;
import cn.smart.cloud.biz.opadmin.gson.fr.GroupOfFacepp;
import cn.smart.cloud.biz.opadmin.gson.fr.youtu.YtIdentifyResult;
import cn.smart.cloud.biz.opadmin.gson.fr.youtu.YtIdentityCandidate;
import cn.smart.cloud.biz.opadmin.gson.fr.youtu.YtPersonCreateResult;
import cn.smart.cloud.biz.opadmin.proxy.FaceServiceProxy;
import cn.smart.cloud.biz.opadmin.proxy.GartenServiceProxy;
import cn.smart.cloud.biz.opadmin.proxy.VideoServiceProxy;
import cn.smart.cloud.biz.opadmin.util.CharUtil;
import cn.smart.cloud.biz.opadmin.util.FileUtil;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.util.*;

@Controller
@RequestMapping("/biz/video")
public class VideoClassifyController {

    private static final Logger logger = LoggerFactory.getLogger(VideoClassifyController.class);
    private static final int SNAPSHOT_START = 0;
    private static final int SNAPSHOT_INTERVAL = 10;
    private static final int SNAPSHOT_CNT = 1;
    private static final String PREFIX_SNAP = "snap";
    private static final String PREFIX_CUT = "cut";
    private static final String PREFIX_ID = "identified";
    private static final String FFMPEG_PATH = "ffmpeg";
    //private static final double MIN_FACE_SIZE   = 0.01;

    @Autowired
    private GartenServiceProxy gartenServiceProxy;
    @Autowired
    private FaceServiceProxy faceServiceProxy;
    @Autowired
    private VideoServiceProxy videoServiceProxy;

    @Autowired
    ThreadPoolTaskExecutor tp;

    @Value("${server.port}")
    String svcPort;
    @Value("${service.biz.wan.url.base}")
    String serverUrl;
    @Value("${text.biz.garten.name}")
    String gartenName;
    @Value("${text.biz.garten.video.news.live.title}")
    String titleNewsLive;
    @Value("${text.biz.garten.video.news.live.des}")
    String descNewsLive;
    @Value("${text.biz.garten.video.news.live.cover}")
    String coverNewsLive;
    @Value("${text.biz.garten.video.closed}")
    String videoClosed;
    @Value("${text.biz.garten.video.none}")
    String videoNone;
    @Value("${text.biz.device.camera.closed}")
    String cameraClosed;

    @RequestMapping(value = "index")
    public String index(HttpServletRequest request,
                        HttpServletResponse response) {
        return "operate/video_index";
    }

    @RequestMapping(value = "snapshot")
    public String snapshot(HttpServletRequest request,
                           HttpServletResponse response) {
        return "operate/video_snapshot";
    }

    @RequestMapping(value = "classify")
    public String classify(HttpServletRequest request,
                           HttpServletResponse response) {
        return "operate/video_classify";
    }

    @RequestMapping(value = "frGroup")
    public String frGroup(HttpServletRequest request,
                          HttpServletResponse response) {
        return "operate/video_fr_group";
    }

    @RequestMapping(value = "frGroup/create")//为某集体创建分组
    public void geneFaceReference(HttpServletRequest request,
                                  HttpServletResponse response) {
        String classId = request.getParameter("classId");//"285b71f3-50b7-11e7-99f6-a4dcbef4";
        String path = request.getParameter("materialPath");
        logger.info("geneFaceReference, classId:" + classId + ", path:" + path);
        //String home = "/operate/video_fr_group";
        if (StringUtils.isBlank(classId) || StringUtils.isBlank(path)) {
            logger.error("geneFaceReference, param is invalid.");
            MessageRender.renderJson(response, "路径错误！");
            return;
        }
        File dir = new File(path);
        if (!dir.exists() || !dir.isDirectory()) {
            logger.error("geneFaceReference, path invalid.");
            MessageRender.renderJson(response, "路径错误！");
            return;
        }

        boolean hasPerson = false;
        File[] pList = dir.listFiles();
        if (pList != null && pList.length > 0) {
            for (File p : pList) {
                if (isPic(p.getName()) && CharUtil.isChinese(p.getName())) {
                    hasPerson = true;
                    break;
                }
            }
        }
        if (!hasPerson) {
            logger.error("geneFaceReference, path has no person.");
            MessageRender.renderJson(response, "无个体素材！");
            return;
        }

        GartenClass gartenClass = gartenServiceProxy.getClass(classId);
        if (gartenClass == null) {
            logger.error("geneFaceReference, gartenClass invalid.");
            MessageRender.renderJson(response, "班级不存在！");
            return;
        }
        Group2GartenClass group2Class = faceServiceProxy.getGroup2GartenClass(classId);
        if (group2Class != null) {
            Group group = faceServiceProxy.getGroup(group2Class.getGroupId());
            if (group != null) {
                logger.error("geneFaceReference, group already exist.");
                MessageRender.renderJson(response, "分组已存在！");
                return;
            } else {
                logger.error("geneFaceReference, group2Class invalid.");
                faceServiceProxy.deleteGroup2GartenClass(group2Class.getId());
            }
        }
        String groupName = gartenClass.getName();
        boolean groupCreated = false;
        Group group = null;
        for (File p : dir.listFiles()) {
            String name = p.getName();
            if (isPic(name)) {
                String personName = name.substring(0, name.lastIndexOf("."));
                if (!CharUtil.isChinese(personName)) {
                    logger.error("geneFaceReference, personName not chinese:" + personName);
                    continue;
                }
                Person person = null;
                String personId = faceServiceProxy.genPersonId();
                String imgPath = p.getAbsolutePath();
                logger.info("geneFaceReference p:" + imgPath);

                // TODO: 2017/11/25 Need to confirm groupName or groupId
                YtPersonCreateResult result = faceServiceProxy
                        .personCreateByPath(groupName, personId, personName, imgPath, "");

                String face_id = "";
                // add face when create person
                if (result == null || result.getCode() != Constant.FR_RESULT_SUC) {
                    logger.info("geneFaceReference, personCreateByPath failed.");
                } else {
                    logger.info("geneFaceReference, create person succeed.");
                    if (!groupCreated) {
                        logger.info("geneFaceReference, group created.");
                        groupCreated = true;
                        group = new Group();
                        group.setGroupId(groupName);
                        group.setGroupName(groupName);
                        group.setTag("");
                        faceServiceProxy.saveGroup(group);
                        Group2GartenClass group2GartenClass = new Group2GartenClass();
                        group2GartenClass.setClassId(classId);
                        group2GartenClass.setGroupId(group.getId());
                        faceServiceProxy.saveGroup2GartenClass(group2GartenClass);
                    }

                    person = new Person();
                    person.setPersonId(personId);
                    person.setPersonName(personName);
                    person.setTag("");
                    faceServiceProxy.savePerson(person);

                    Person2Group p2g = new Person2Group();
                    p2g.setGroupId(group.getId());
                    p2g.setPersonId(person.getId());
                    faceServiceProxy.savePerson2Group(p2g);

                    face_id = result.getFace_id();
                }

                if (StringUtils.isBlank(face_id)) {
                    logger.info("geneFaceReference, add face to person, no face id.");
                    continue;
                }
                logger.info("geneFaceReference, add face to person.");
                Face face = new Face();
                face.setFaceId(face_id);
                face.setImgId("");
                face.setTag("");
                face.setUrl(imgPath);
                faceServiceProxy.saveFace(face);

                Face2Person face2Person = new Face2Person();
                face2Person.setFaceId(face.getId());
                face2Person.setPersonId(person.getId());
                faceServiceProxy.saveFace2Person(face2Person);
            }
        }
        GroupOfFacepp groupOfFacepp = faceServiceProxy.getFaceGroupInfo(groupName);
        logger.info("geneFaceReference, groupGetInfo:" + groupOfFacepp);
        if (groupOfFacepp.getPerson().isEmpty()) {
            MessageRender.renderJson(response, "没有建立个体数据！");
        } else {
            MessageRender.renderJson(response, "已完成！");
        }
    }

    @RequestMapping(value = "genSnapshot")
    public String genSnapshot(HttpServletRequest request,
                              HttpServletResponse response) {
        Map<String, Object> resultMap = new HashMap<String, Object>();
        resultMap.put("code", -1);

        String path = request.getParameter("materialPath");
        String sString = request.getParameter("startSecond");
        String iString = request.getParameter("interval");
        String cString = request.getParameter("cnt");
        logger.info("genSnapshot, path:" + path);

        if (StringUtils.isBlank(path)) {
            logger.error("start, path is null.");
            resultMap.put("msg", "路径错误！");
            MessageRender.renderJson(response, resultMap);
            return "operate/video_snapshot";
        }
        File dir = new File(path);
        if (!dir.exists() || !dir.isDirectory()) {
            logger.error("start, path invalid.");
            resultMap.put("msg", "路径错误！");
            MessageRender.renderJson(response, resultMap);
            return "operate/video_snapshot";
        }
        boolean hasVideo = false;
        String[] vList = dir.list();
        if (vList != null && vList.length > 0) {
            for (String v : vList) {
                if (v.endsWith(Constant.MEDIA_FORMAT_TS) || v.endsWith(Constant.MEDIA_FORMAT_MP4)) {
                    hasVideo = true;
                    break;
                }
            }
        }
        if (!hasVideo) {
            logger.error("genSnapshot, path has no video.");
            resultMap.put("msg", "无视频素材！");
            MessageRender.renderJson(response, resultMap);
            return "operate/video_snapshot";
        }
        int start = 0, interval = 0, cnt = 0;
        try {
            start = Integer.parseInt(sString);
            interval = Integer.parseInt(iString);
            cnt = Integer.parseInt(cString);
        } catch (NumberFormatException e) {
        } finally {
            if (start <= 0)
                start = SNAPSHOT_START;
            if (interval <= 0)
                interval = SNAPSHOT_INTERVAL;
            if (cnt <= 0)
                cnt = SNAPSHOT_CNT;
        }
        final int s = start;
        final int i = interval;
        final int c = cnt;
        int vCnt = 0;
        for (File v : dir.listFiles()) {
            String vName = v.getName();
            if (vName.endsWith(Constant.MEDIA_FORMAT_TS) || vName.endsWith(Constant.MEDIA_FORMAT_MP4)) {
                File snapDir = new File(dir, vName.substring(0, vName.lastIndexOf(".")));
                if (isSnapped(snapDir, cnt)) {
                    continue;
                }
                vCnt++;
                snapDir.mkdir();
                tp.execute(new Runnable() {
                    @Override
                    public void run() {
                        List<File> snapshotList = getSnapshot(snapDir.getAbsolutePath(), v.getAbsolutePath(),
                                s, i, c);
                        if (snapshotList != null && !snapshotList.isEmpty()) {
                            logger.info("genSnapshot, snapshotList cnt:" + snapshotList.size());
                        } else {
                            logger.error("genSnapshot, 抽帧失败！v:" + vName);
                        }
                    }
                });
            }
        }

        logger.info("genSnapshot, finished");
        resultMap.put("code", 0);
        resultMap.put("msg", "已开始抽帧. 视频个数：" + vCnt);
        MessageRender.renderJson(response, resultMap);
        return "operate/video_snapshot";
    }

    private int sucCnt = 0;

    @RequestMapping(value = "reco")
    public void reco(HttpServletRequest request,
                     HttpServletResponse response) {
        Map<String, Object> resultMap = new HashMap<String, Object>();
        resultMap.put("code", -1);
        String classId = request.getParameter("classId");//"285b71f3-50b7-11e7-99f6-a4dcbef4";
        String path = request.getParameter("materialPath");
        logger.info("reco, classId:" + classId + ", path:" + path);

        if (StringUtils.isBlank(path)) {
            logger.error("reco, path is null.");
            resultMap.put("msg", "路径错误！");
            MessageRender.renderJson(response, resultMap);
            return;
        }
        File dir = new File(path);
        if (!dir.exists() || !dir.isDirectory()) {
            logger.error("reco, path invalid.");
            resultMap.put("msg", "路径错误！");
            MessageRender.renderJson(response, resultMap);
            return;
        }
        boolean hasVideo = false;
        String[] vList = dir.list();
        if (vList != null && vList.length > 0) {
            for (String v : vList) {
                if (v.endsWith(Constant.MEDIA_FORMAT_TS) || v.endsWith(Constant.MEDIA_FORMAT_MP4)) {
                    hasVideo = true;
                    break;
                }
            }
        }
        if (!hasVideo) {
            logger.error("reco, path has no video.");
            resultMap.put("msg", "无视频素材！");
            MessageRender.renderJson(response, resultMap);
            return;
        }

        String groupId = faceServiceProxy.getGroup2GartenClass(classId).getGroupId();
        String frGroupId = faceServiceProxy.getGroup(groupId).getGroupId();
        int confidence = videoServiceProxy.getVideoCloseupConfidence();
        logger.info("reco, confidence:" + confidence);
        int vCnt = 0, pCnt = 0;
        sucCnt = 0;
        for (File v : dir.listFiles()) {
            String vName = v.getName();
            if (vName.endsWith(Constant.MEDIA_FORMAT_TS) || vName.endsWith(Constant.MEDIA_FORMAT_MP4)) {
                vCnt++;
                File snapDir = new File(dir, vName.substring(0, vName.lastIndexOf(".")));
                File[] snapList = snapDir.listFiles();
                if (snapList == null)
                    continue;
                List<File> picList = Arrays.asList(snapList);
                if (picList != null && !picList.isEmpty()) {
                    logger.info("reco, picList cnt:" + picList.size());
                    for (File f : picList) {
                        String name = f.getName();
                        logger.info("reco, picList p:" + name);
                        if (!isCutPic(name))
                            continue;
                        pCnt++;
                        //抽多张，如10秒一张 取第一张，抠图识别之 有人则结束 无人则取第二张抠图识别之 如此循环
                        //TODO recognize, save for background or no
                        tp.execute(new Runnable() {
                            @Override
                            public void run() {
                                YtIdentifyResult result = faceServiceProxy
                                        .recognitionIdentifyByPath(frGroupId, f.getAbsolutePath());
                                if (result != null && result.getCode() == Constant.FR_RESULT_SUC) {
                                    List<YtIdentityCandidate> candidates = result.getCandidates();
                                    logger.info("reco, candidates size:" + candidates.size());
                                    if (candidates != null && !candidates.isEmpty()) {
                                        YtIdentityCandidate candidate = candidates.get(0);
                                        logger.info("reco, candidate 0:" + candidate + ", confidence:" + confidence);
                                        if (candidate.getConfidence() >= confidence) {
                                            sucCnt++;
                                            Person person = faceServiceProxy.getPersonByPersonId(candidate.getPerson_id());
                                            String personName = person.getPersonName();
                                            logger.info("reco, v:" + v + ", person:" + personName);
                                            File personDir = new File(dir, personName);
                                            if (!personDir.exists() || !personDir.isDirectory())
                                                personDir.mkdir();
                                            FileUtil.nioTransferCopy(new File(dir, vName), new File(personDir, vName));
                                        }
                                    }
                                }
                                f.renameTo(new File(f.getParentFile(), name.replace(PREFIX_CUT, PREFIX_ID)));
                            }
                        });

                    }
                } else {
                    logger.error("reco, video has no snapshot:" + v.getAbsolutePath());
                }
            }
        }

        logger.info("reco, finished");
        //MessageRender.renderJson(response, "归类完成！处理视频个数："+vCnt+", 处理图片个数："+pCnt+", 识别成功个数："+sucCnt);
        resultMap.put("code", 0);
        resultMap.put("msg", "已开始归类. 视频个数：" + vCnt + ", 图片个数：" + pCnt);
        MessageRender.renderJson(response, resultMap);
        return;
    }

    private List<File> getSnapshot(String storagePath, String videoPath,
                                   int start, int interval, int cnt) {
        if (frameExtract(storagePath, videoPath, start, interval, cnt)) {
            File dir = new File(storagePath);
            File[] sArray = dir.listFiles();
            if (sArray != null) {
                return Arrays.asList(dir.listFiles());
            }
        }
        return null;
    }

    private boolean frameExtract(String storagePath, String videoPath,
                                 int start, int interval, int cnt) {
        boolean suc = false;
        if (StringUtils.isEmpty(storagePath) || StringUtils.isEmpty(videoPath)) {
            return suc;
        }
        logger.info("frameExtract, videoUrl:" + videoPath + ", storeAddr:" + storagePath);
        String format = PREFIX_SNAP + "%03d";
        List<String> command = getSnapshotCmd(videoPath, storagePath + "/", format, Constant.MEDIA_FORMAT_JPG,
                start, interval, cnt);
        //-q:v 1
        try {
            ProcessBuilder builder = new ProcessBuilder();
            builder.command(command);
            builder.redirectErrorStream(true);
            Process p = builder.start();
            String line = null;
            BufferedReader buf = new BufferedReader(new InputStreamReader(p.getInputStream()));
            while ((line = buf.readLine()) != null) {
                logger.info(line);
                if (line.contains(Constant.FFMPEG_FINISH) || line.contains(Constant.FFMPEG_FINISH_PRE)) {
                    suc = true;
                }
            }
            p.waitFor();
        } catch (Exception e) {
            logger.error("run cmd(" + command + ") failed:" + e.getMessage());
        }
        logger.info("frameExtract, suc:" + suc);
        /*if (cnt > 1) {
            String firstPic = String.format(format+Constant.MEDIA_FORMAT_JPG, 1);
        	logger.info("frameExtract, delete:"+firstPic+", e:"+new File(storagePath, firstPic).exists()+", p:"+new File(storagePath, firstPic).getAbsolutePath());
        	new File(storagePath, firstPic).delete();
        }*/
        return suc;
    }

    private static List<String> getSnapshotCmd(String srcUrl, String storePath,
                                               String name, String format, int start, int interval, int cnt) {
        logger.info("getSnapshotCmd, src:" + srcUrl + ", store:" + storePath
                + ", name:" + name + ", format:" + format + ", interval:" + interval);
        List<String> snapshot = new ArrayList<String>();
        snapshot.add(srcUrl);
        snapshot.add("-f");
        snapshot.add("image2");
        snapshot.add("-ss"); // 添加参数＂-ss＂，该参数指定截取的起始时间
        snapshot.add(String.valueOf(start)); // 添加起始时间为第17秒

        //snapshot.add("-t"); // 添加参数＂-t＂，该参数指定持续时间
        //snapshot.add("0.001"); // 添加持续时间为1毫秒
        //snapshot.add("-s"); // 添加参数＂-s＂，设置截取的图片分辨率
        //snapshot.add("640*360");
        //snapshot.add("-r");//设置帧率，0.1则10秒一张; 此方式可同时指定起始和间隔，但如果程序是arm版的速度会很慢，耗时=最后截取帧所在时间-视频开始时间
        //此方式缺点在于，抽帧后的图片，某些相邻图片处在相同时间
        //if output file count more than one, the first and second picture will be same, should delete one.
        //snapshot.add(""+(double)1/interval);

        snapshot.add("-vf");//设置每隔几秒一张; 此方式很快，但第一张图时间不随起始时间而增加，只是小于起始时间的会丢弃
        snapshot.add("fps=1/" + interval);
        snapshot.add("-frames");
        snapshot.add(String.valueOf(cnt));
        snapshot.add(storePath + name + format);
        logger.info("getSnapshotCmd:" + snapshot);
        return wrapCmd(snapshot);
    }

    private static List<String> wrapCmd(List<String> params) {
        List<String> command = new ArrayList<String>();
        command.add(FFMPEG_PATH);
        command.add("-i");
        command.addAll(params);
        return command;
    }

    private boolean isSnapped(File picName, int cnt) {
        if (picName.isDirectory()) {
            File[] picList = picName.listFiles();
            if (picList != null && picList.length >= cnt)
                return true;
        }
        return false;
    }

    private boolean isPic(String picName) {
        if (StringUtils.isBlank(picName))
            return false;
        if (picName.endsWith(Constant.MEDIA_FORMAT_BMP) || picName.endsWith(Constant.MEDIA_FORMAT_PNG)
                || picName.endsWith(Constant.MEDIA_FORMAT_JPG) || picName.endsWith(Constant.MEDIA_FORMAT_JPEG))
            return true;
        return false;
    }

    private boolean isCutPic(String picName) {
        if (StringUtils.isBlank(picName))
            return false;
        if (isPic(picName) && picName.startsWith(PREFIX_CUT))
            return true;
        return false;
    }

}