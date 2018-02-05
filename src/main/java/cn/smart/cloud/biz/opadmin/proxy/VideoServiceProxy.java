package cn.smart.cloud.biz.opadmin.proxy;

import cn.smart.cloud.biz.opadmin.gson.Pager;
import cn.smart.cloud.biz.opadmin.gson.SelectedVideoOneday;
import cn.smart.cloud.biz.opadmin.gson.UserAction;
import cn.smart.cloud.biz.opadmin.gson.video.M3u8;
import cn.smart.cloud.biz.opadmin.gson.video.M3u8ContentType;
import cn.smart.cloud.biz.opadmin.gson.video.UserVideo;
import cn.smart.cloud.biz.opadmin.gson.video.VideoConfig;
import cn.smart.cloud.biz.opadmin.util.DateTimeUtil;
import cn.smart.cloud.biz.opadmin.util.HttpPostUploadUtil;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class VideoServiceProxy {

    private static final Logger logger = LoggerFactory.getLogger(VideoServiceProxy.class);
    private static final String BASE_URL_VIDEO_CONFIG = ServiceConstant.MSVC_INSTANCE_NAME_VIDEO_SERVICE + "video/config";
    private static final String BASE_URL_VIDEO = ServiceConstant.MSVC_INSTANCE_NAME_VIDEO_SERVICE + "video";
    private static final String BASE_URL_UPLOAD = ServiceConstant.MSVC_INSTANCE_NAME_VIDEO_SERVICE + "upload";
    private static final String BASE_URL_DOWNLOAD = ServiceConstant.MSVC_INSTANCE_NAME_VIDEO_SERVICE + "download";
    private static final String BASE_URL_DELETE = ServiceConstant.MSVC_INSTANCE_NAME_VIDEO_SERVICE + "delete";
    private static final String BASE_URL_USER_VIDEO = ServiceConstant.MSVC_INSTANCE_NAME_VIDEO_SERVICE + "video/uservideo";
    private static final SimpleDateFormat format = new SimpleDateFormat("M月dd日");

    @Autowired
    private SfRestTemplate rstl;

    public int getVideoCloseupConfidence() {
        return rstl.get(ServiceConstant.MSVC_INSTANCE_NAME_VIDEO_SERVICE
                        + "video/sysconf/video_closeup_confidence", Integer.class);
    }

    public String[] getDeviceExceptionAlarmOpenIds() {
        return rstl.get(ServiceConstant.MSVC_INSTANCE_NAME_VIDEO_SERVICE
                + "video/sysconf/device_exception_alarm_openids", String[].class);
    }

    public String getDeviceExceptionAlarmTemplate() {
        return rstl.get(ServiceConstant.MSVC_INSTANCE_NAME_VIDEO_SERVICE
                + "video/sysconf/device_exception_alarm_template", String.class);
    }

    public VideoConfig getVideoConfigByKinderGartenIdAndBizType(String kinderGartenId, String bizType) {
        logger.info("getVideoConfigByKinderGartenIdAndBizType, kinderGartenId:" + kinderGartenId);
        if (StringUtils.isBlank(bizType)) {
            bizType = "unknown";
        }
        return rstl.get(BASE_URL_VIDEO_CONFIG + "/getByKinderGartenIdAndBizType/" + kinderGartenId
                        + "/" + bizType, VideoConfig.class);
    }

    public UserVideo getUserVideoByUrl(String url) {
        logger.info("getUserVideoByUrl, url:" + url);
        UserVideo result = rstl.get(
                BASE_URL_VIDEO + "/getUserVideoByUrl/" + url,
                UserVideo.class);
        return result;
    }

    public UserVideo getUserVideo(String id) {
        logger.info("getUserVideo, videoId:" + id);
        UserVideo result = rstl.get(
                BASE_URL_VIDEO + "/getUserVideo/" + id,
                UserVideo.class);
        return result;
    }

    public boolean deleteUserVideo(String id) {
        logger.info("deleteUserVideo, videoId:" + id);
        Boolean result = rstl.get(
                BASE_URL_VIDEO + "/deleteUserVideo/" + id,
                Boolean.class);
        return result;
    }

    public List<UserVideo> getUserVideoList(List<String> idList) {
        logger.info("getUserVideoList, idList:" + idList);
        UserVideo[] temp = rstl.post(
                BASE_URL_VIDEO + "/getUserVideoList",
                idList, UserVideo[].class);
        return Arrays.asList(temp);
    }

    public UserVideo getNewestUserVideo(String cameraId, boolean live) {
        logger.info("getNewestUserVideo, cameraId:" + cameraId + ", live:" + live);
        UserVideo result = null;
        if (live) {
            result = rstl.get(
                    BASE_URL_VIDEO + "/getLiveByCameraId/" + cameraId,
                    UserVideo.class);
            return result;
        }
        result = rstl.get(
                BASE_URL_VIDEO + "/generateUserVideo/" + cameraId,
                UserVideo.class);
        return result;
    }

    public M3u8 getNewestByCameraIdAndType(String cameraId, M3u8ContentType type) {
        logger.info("getNewestByCameraIdAndType, cameraId:" + cameraId);
        M3u8 result = rstl.get(
                BASE_URL_VIDEO + "/getNewestByCameraIdAndType/" + cameraId + "/" + type,
                M3u8.class);
        return result;
    }

    public UserVideo getCloseupVideoByCameraIdWithTime(String cameraId, long start, long end) {
        logger.info("getCloseupVideoByCameraIdWithTime, cameraId:" + cameraId + ", start:" + start + ", end:" + end);
        UserVideo result = rstl.get(
                BASE_URL_VIDEO + "/getUserVideoBetweenCreateDate/" + cameraId
                        + "/" + start + "/" + end + "/" + M3u8ContentType.SELECTED.name(), UserVideo.class);
        return result;
    }


    public M3u8 generateM3u8WithTime(String cameraId, long start, long end) {
        logger.info("generateM3u8WithTime, cameraId:" + cameraId);
        M3u8 result = rstl.get(
                BASE_URL_VIDEO + "/generateM3u8WithTime/" + cameraId
                        + "/" + start + "/" + end, M3u8.class);
        return result;
    }

    public boolean notifyClientUserAction(String cameraId, UserAction action) {
        return true;
    }

    public Pager<M3u8> getM3u8PageByDuration(String cameraId, int page, int size, int duration) {
        logger.info("getM3u8PageByDuration, cameraId:" + cameraId);
        @SuppressWarnings("unchecked")
        Pager<M3u8> result = rstl.get(
                BASE_URL_VIDEO + "/getM3u8PageByDuration/" + cameraId
                        + "/" + page + "/" + size + "/" + duration, Pager.class);
        return result;
    }

    public Pager<UserVideo> getWonderfulVideoPage(String cameraId, int page, int size) {
        logger.info("getWonderfulVideoPage, cameraId:" + cameraId);
        @SuppressWarnings("unchecked")
        Pager<UserVideo> result = rstl.get(
                BASE_URL_VIDEO + "/getUserVideoPageByType/" + cameraId
                        + "/" + page + "/" + size + "/" + M3u8ContentType.SELECTED.name(), Pager.class);
        return result;
    }

    public List<UserVideo> getWonderfulVideoBetweenCreateDate(String cameraId, long start, long end) {
        logger.info("getWonderfulVideoBetweenCreateDate, cameraId:" + cameraId);
        UserVideo[] result = rstl.get(
                BASE_URL_VIDEO + "/getUserVideoBetweenCreateDate/" + cameraId
                        + "/" + start + "/" + end + "/" + M3u8ContentType.SELECTED.name(), UserVideo[].class);
        return Arrays.asList(result);
    }

    public List<UserVideo> getWonderfulVideoBetweenCreateDate(List<String> cameraIds, long start, long end) {
        UserVideo[] result = rstl.post(
                BASE_URL_VIDEO + "/getUserVideoByCamerasAndBetweenCreateDate/"
                        + "/" + start + "/" + end + "/" + M3u8ContentType.SELECTED.name(),
                cameraIds, UserVideo[].class);
        return Arrays.asList(result);
    }

	/*public String getCoverByVideoId(String m3u8Id) {
		logger.info("getCoverByVideoId, m3u8Id:"+m3u8Id);
		String result = rstl.get(
				BASE_URL_VIDEO + "/getCoverByVideo/"+m3u8Id, String.class);
		return result;
	}*/

    @SuppressWarnings("deprecation")
    public SelectedVideoOneday getWonderfulVideoOneDay(List<String> cameraIds, Long time) {
        logger.info("getWonderfulVideoOneDay, time:" + time);
        SelectedVideoOneday videoOneday = new SelectedVideoOneday();
        videoOneday.setDate(String.valueOf(time));
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(time);
        videoOneday.setDateString(format.format(calendar.getTime()).toString());
        calendar.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH), 0, 0, 0);
        long start = calendar.getTimeInMillis();
        long end = start + (1000 * 60 * 60 * 24);
        List<UserVideo> userVideos = getWonderfulVideoBetweenCreateDate(cameraIds, start, end);
        if (userVideos.isEmpty())
            return videoOneday;
        for (UserVideo uv : userVideos) {
            if (new Date().getDate() == calendar.getTime().getDate()) {
                videoOneday.setToday(true);
            }
            videoOneday.getAll().add(uv);
        }
        return videoOneday;
    }

    @SuppressWarnings("deprecation")
    public List<SelectedVideoOneday> getWonderfulVideoLastDays(List<String> cameraIds, int days) {
        List<SelectedVideoOneday> videoOnedays = new ArrayList<>();
        Calendar calendar = Calendar.getInstance();
        calendar.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH), 0, 0, 0);
        long oneDayInMillis = 1000 * 60 * 60 * 24;
        long end = calendar.getTimeInMillis() + oneDayInMillis;
        long start = end - (oneDayInMillis * days);
        List<UserVideo> userVideos = getWonderfulVideoBetweenCreateDate(cameraIds, start, end);
        if (userVideos.isEmpty())
            return videoOnedays;
        int dayInMonth = -1;
        boolean aNewDay = false;
        SelectedVideoOneday selectedVideoOneday = null;
        for (UserVideo uv : userVideos) {
            int tempDate = DateTimeUtil.formatServerTime(uv.getCreateDate()).getDate();
            if (tempDate != dayInMonth) {
                dayInMonth = tempDate;
                aNewDay = true;
                selectedVideoOneday = new SelectedVideoOneday();
                selectedVideoOneday.setDate(String.valueOf(DateTimeUtil.formatServerTime(uv.getCreateDate()).getTime()));
                selectedVideoOneday.setDateString(format.format(uv.getCreateDate()).toString());
                if (dayInMonth == calendar.getTime().getDate()) {
                    selectedVideoOneday.setToday(true);
                }
            } else {
                aNewDay = false;
            }
            selectedVideoOneday.getAll().add(uv);
            if (aNewDay)
                videoOnedays.add(selectedVideoOneday);
        }
        return videoOnedays;
    }

    public UserVideo createUserVideo(String url, String coverUrl, String cameraId) {
        logger.info("createUserVideo, url:" + url + ", c:" + coverUrl + ", cameraId:" + cameraId);
        UserVideo result = rstl.get(
                BASE_URL_VIDEO + "/createUserVideo/" + url + "/" + cameraId + "?coverUrl=" + coverUrl,
                UserVideo.class);
        return result;
    }

    public UserVideo saveUserVideo(UserVideo uv) {
        logger.info("saveUserVideo, uv:" + uv);
        UserVideo result = rstl.post(
                BASE_URL_USER_VIDEO + "/update", uv,
                UserVideo.class);
        return result;
    }

    public String uploadFaceImage(File file, String path) {
        logger.info("uploadFaceImage, file:" + file);
        String result = HttpPostUploadUtil.formUploadFile("http://dev.video.smart-f.cn:8005/upload/uploadFaceImage",
                file.getName(), file.getAbsolutePath(), path);
		/*String result = rstl.post(
				BASE_URL_UPLOAD + "/uploadFaceImage", file,
				String.class);*/
        return result;
    }

    public String uploadVideoCardQRcode(File file, String path) {
        logger.info("uploadVideoCardQRcode, file:" + file);
        String result = HttpPostUploadUtil.formUploadFile("http://dev.video.smart-f.cn:8005/upload/uploadVideoCardQRcode",
                file.getName(), file.getAbsolutePath(), path);
		/*String result = rstl.post(
				BASE_URL_UPLOAD + "/uploadVideoCardQRcode", file,
				String.class);*/
        return result;
    }

    public List<String> getImageList(String path, int count) {
        logger.info("getImageList, path:" + path + ", count:" + count);
        String[] result = rstl.get(
                BASE_URL_DOWNLOAD + "/getFaceImageList/" + count + "?path=" + path,
                String[].class);
        return Arrays.asList(result);
    }

    public boolean deleteFaceImages(String path) {
        logger.info("deleteFaceImages, path:" + path);
        Boolean result = rstl.get(
                BASE_URL_DELETE + "/deleteFaceImages?path=" + path,
                Boolean.class);
        return result;
    }

    public boolean deleteFaceImagesExcept(String path, String url) {
        logger.info("deleteFaceImagesExcept, path:" + path + ", e:" + url);
        Boolean result = rstl.post(
                BASE_URL_DELETE + "/deleteFaceImagesExcept?path=" + path,
                url,
                Boolean.class);
        return result;
    }

}