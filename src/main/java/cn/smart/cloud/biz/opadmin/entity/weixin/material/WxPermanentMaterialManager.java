package cn.smart.cloud.biz.opadmin.entity.weixin.material;

import cn.smart.cloud.biz.opadmin.entity.weixin.WxCommonResult;
import cn.smart.cloud.biz.opadmin.util.GsonUtil;
import cn.smart.cloud.biz.opadmin.util.WxURIFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.client.RestTemplate;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;

/**
 * Author: jjj
 * Date: 16-1-11
 * Time: 上午12:01
 */
public class WxPermanentMaterialManager {

    @Autowired
    private static RestTemplate restTemplate = new RestTemplate();

//	public boolean uploadPermanetMaterial(String accountId, File file) {
//		WxOfficialAccount account = getOfficialAccount(accountId);
//		WxOfficialAccountToken token = getOfficialAccountToken(account);
//		WxMaterialResult result = WxPermanentMaterialManager.uploadMaterialMedia(token.getAccessToken(), file);
//		if (result == null || StringUtils.isEmpty(result.getMedia_id())) {
//			token = updateOfficialAccountToken(account);
//		}
//		result = WxPermanentMaterialManager.uploadMaterialMedia(token.getAccessToken(), file);
//		return result != null && !StringUtils.isEmpty(result.getMedia_id());
//	}

    /// <summary>
    /// 新增其他类型永久素材(图片（image）、语音（voice）和缩略图（thumb）)
    /// </summary>
    /// <param name="accessToken">调用接口凭证</param>
    /// <param name="type">媒体文件类型，分别有图片（image）、语音（voice）、视频（video）和缩略图（thumb）</param>
    /// <param name="file">form-data中媒体文件标识，有filename、filelength、content-type等信息</param>
    /// <returns></returns>
    public static WxMaterialResult uploadMaterialMedia(String token, File file) {

        String url = String.format("http://api.weixin.qq.com/cgi-bin/material/add_material?access_token={0}&type={1}", token, "media");

        if (file.exists()) {
            System.out.println("UploadMaterialMedia, file is exist.");
        }
        WxMaterialResult result = GsonUtil.fromJson(postFile(url, file), WxMaterialResult.class);

        return result;
    }

    public static WxMaterialListResult getMaterialList(String token, WxMaterialRequest request) {

        URI uri = WxURIFactory.getWxGetMaterialListURI(token);
        WxMaterialListResult result = restTemplate.postForObject(uri, request, WxMaterialListResult.class);

        return result;
    }

    private static String postFile(String url, File file) {
        String result = null;
        try {

            //这块是用来处理如果上传的类型是video的类型的
            //JSONObject j=new JSONObject();
            //j.put("title", title);
            //j.put("introduction", introduction);

            WxMediaInfo requestMedia = new WxMediaInfo();
            requestMedia.setTitle("title1");
            requestMedia.setIntroduction("audio1");

            long fileLength = file.length();
            String fileName = file.getName();
            String suffix = fileName.substring(fileName.lastIndexOf("."), fileName.length());
            /** TODO 需要在这里根据文件后缀suffix将type的值设置成对应的mime类型的值 */
            String type = "audio/" + suffix;
            System.out.println("url:" + url);
            URL url1 = new URL(url);
            HttpURLConnection con = (HttpURLConnection) url1.openConnection();
            con.setRequestMethod("POST"); // 以Post方式提交表单，默认get方式
            con.setDoInput(true);
            con.setDoOutput(true);
            con.setUseCaches(false); // post方式不能使用缓存
            // 设置请求头信息
            con.setRequestProperty("Connection", "Keep-Alive");
            con.setRequestProperty("Charset", "UTF-8");

            // 设置边界,这里的boundary是http协议里面的分割符，不懂的可惜百度(http 协议 boundary)，这里boundary 可以是任意的值(111,2222)都行
            String BOUNDARY = "----------" + System.currentTimeMillis();
            con.setRequestProperty("Content-Type",
                    "multipart/form-data; boundary=" + BOUNDARY);
            // 请求正文信息
            // 第一部分：

            StringBuilder sb = new StringBuilder();


            //这块是post提交type的值也就是文件对应的mime类型值
            sb.append("--"); // 必须多两道线 这里说明下，这两个横杠是http协议要求的，用来分隔提交的参数用的，不懂的可以看看http 协议头
            sb.append(BOUNDARY);
            sb.append("\r\n");
            sb.append("Content-Disposition: form-data;name=\"type\" \r\n\r\n"); //这里是参数名，参数名和值之间要用两次
            sb.append(type + "\r\n"); //参数的值

            //这块是上传video是必须的参数，你们可以在这里根据文件类型做if/else 判断
            sb.append("--"); // 必须多两道线
            sb.append(BOUNDARY);
            sb.append("\r\n");
            sb.append("Content-Disposition: form-data;name=\"description\" \r\n\r\n");
            sb.append(GsonUtil.toJson(requestMedia) + "\r\n");

            /**
             * 这里重点说明下，上面两个参数完全可以卸载url地址后面 就想我们平时url地址传参一样，
             * http://api.weixin.qq.com/cgi-bin/material/add_material?access_token=##ACCESS_TOKEN##&type=""&description={} 这样，如果写成这样，上面的
             * 那两个参数的代码就不用写了，不过media参数能否这样提交我没有试，感兴趣的可以试试
             */

            sb.append("--"); // 必须多两道线
            sb.append(BOUNDARY);
            sb.append("\r\n");
            //这里是media参数相关的信息，这里是否能分开下我没有试，感兴趣的可以试试
            sb.append("Content-Disposition: form-data;name=\"media\";filename=\""
                    + fileName + "\";filelength=\"" + fileLength + "\" \r\n");
            sb.append("Content-Type:application/octet-stream\r\n\r\n");
            System.out.println(sb.toString());
            byte[] head = sb.toString().getBytes("utf-8");
            // 获得输出流
            OutputStream out = new DataOutputStream(con.getOutputStream());
            // 输出表头
            out.write(head);
            // 文件正文部分
            // 把文件已流文件的方式 推入到url中
            DataInputStream in = new DataInputStream(new FileInputStream(file));
            int bytes = 0;
            byte[] bufferOut = new byte[1024];
            while ((bytes = in.read(bufferOut)) != -1) {
                out.write(bufferOut, 0, bytes);
            }
            in.close();
            // 结尾部分，这里结尾表示整体的参数的结尾，结尾要用"--"作为结束，这些都是http协议的规定
            byte[] foot = ("\r\n--" + BOUNDARY + "--\r\n").getBytes("utf-8");// 定义最后数据分隔线
            out.write(foot);
            out.flush();
            out.close();
            StringBuffer buffer = new StringBuffer();
            BufferedReader reader = null;
            // 定义BufferedReader输入流来读取URL的响应
            reader = new BufferedReader(new InputStreamReader(
                    con.getInputStream()));
            String line = null;
            while ((line = reader.readLine()) != null) {
                System.out.println("UploadMaterialMedia, read response.");
                buffer.append(line);
            }
            if (result == null) {
                result = buffer.toString();
            }
            System.out.println("UploadMaterialMedia, result:" + result);
            WxCommonResult ret = GsonUtil.fromJson(result, WxCommonResult.class);
            System.out.println(ret);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {

        }
        return result;
    }

    //以下命令成功上传了2个文件到语音素材中
    //[root@VM_85_148_centos ~]# curl "https://api.weixin.qq.com/cgi-bin/material/add_material?access_token=sukNqeefz6x6wptW1X7suLrGdBhZ4g3f3iWiQnMFhDhYp_DOrNXLl87vro2jo8hG5okSV_1yledHCA8nDUb6d_4tvPqNz39BZgTZvnFb8pcMNNiAAAUVV" -F media=@test.mp3 -F  description='{"title":V1, "introduction":INTRO1}'
    //{"media_id":"a6AeEoS5Jp0CDgviFJYdBO8k4HjhihGBFrPm9NziYU4"}
    //[root@VM_85_148_centos ~]# curl "https://api.weixin.qq.com/cgi-bin/material/add_material?access_token=sukNqeefz6x6wptW1X7suLrGdBhZ4g3f3iWiQnMFhDhYFb8pcMNNiAAAUVV" -F media=@arrivedVoice.wav -F  description='{"title":a1, "introduction":intro1}'
    //{"media_id":"a6AeEoS5Jp0CDgviFJYdBFq0Lu-eCvciNffO1DdnrHw"}
    //baidu_version: a6AeEoS5Jp0CDgviFJYdBP2qp1tn4qrJTbZu8YnSfb4

}
