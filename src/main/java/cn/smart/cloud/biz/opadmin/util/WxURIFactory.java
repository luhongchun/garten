package cn.smart.cloud.biz.opadmin.util;

import org.apache.http.client.utils.URIBuilder;

import java.net.URI;
import java.net.URISyntaxException;

public class WxURIFactory {

    private final static String WX_BASE_URL = "api.weixin.qq.com/cgi-bin/";

    public static URI getWxAccessTokenURI(String appId,
                                          String secret) throws URISyntaxException {
        URIBuilder uriBuilder = new URIBuilder().setScheme("https")
                .setHost(WX_BASE_URL).setPath("token")
                .setParameter("grant_type", "client_credential")
                .setParameter("appid", appId).setParameter("secret", secret);

        return uriBuilder.build();
    }

    public static URI getWxJsTicketURI(String access_token) throws URISyntaxException {
        URIBuilder uriBuilder = new URIBuilder().setScheme("https")
                .setHost(WX_BASE_URL).setPath("ticket/getticket")
                .setParameter("access_token", access_token)
                .setParameter("type", "jsapi");

        return uriBuilder.build();
    }

/*	public static void main(String[] args) {
        try {
			logger.info(getWxJsTicketURI("cYdxFI2MrVyBIi3PGj-XNKEXu6jYr9oSnh-7UTnvoqedZBC6__WALTCYQYDAYuNxgXHu9qbPfKqNmIKdwvlHtvKOuv4bjyWHcM3TcrP95uU").toString());
		} catch (URISyntaxException e) {
			e.printStackTrace();
		}
	}*/

    public static URI getWxSendCustomMessageURI(
            String accesssToken) throws URISyntaxException {
        URIBuilder uriBuilder = new URIBuilder().setScheme("https")
                .setHost(WX_BASE_URL).setPath("message/custom/send")
                .setParameter("access_token", accesssToken);

        return uriBuilder.build();
    }

    public static URI getWxCreateCustomMenuURI(
            String accesssToken) {
        URIBuilder uriBuilder = new URIBuilder().setScheme("https")
                .setHost(WX_BASE_URL)
                .setPath("menu/create")
                .setParameter("access_token", accesssToken);

        try {
            return uriBuilder.build();
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }

        return null;
    }

    public static URI getWxGetCustomMenuURI(
            String accesssToken) {
        URIBuilder uriBuilder = new URIBuilder().setScheme("https")
                .setHost(WX_BASE_URL)
                .setPath("menu/get")
                .setParameter("access_token", accesssToken);

        try {
            return uriBuilder.build();
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }

        return null;
    }

    public static URI getWxDeleteCustomMenuURI(
            String accesssToken) {
        URIBuilder uriBuilder = new URIBuilder().setScheme("https")
                .setHost(WX_BASE_URL)
                .setPath("menu/delete")
                .setParameter("access_token", accesssToken);

        try {
            return uriBuilder.build();
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }

        return null;
    }

    public static URI getWxCreateQRCodeURI(String accesssToken) throws URISyntaxException {
        URIBuilder uriBuilder = new URIBuilder().setScheme("https")
                .setHost(WX_BASE_URL).setPath("qrcode/create")
                .setParameter("access_token", accesssToken);

        return uriBuilder.build();
    }

    public static URI getWxUserListURI(
            String accesssToken) throws URISyntaxException {
        URIBuilder uriBuilder = new URIBuilder().setScheme("https")
                .setHost(WX_BASE_URL).setPath("user/get")
                .setParameter("access_token", accesssToken);

        return uriBuilder.build();
    }

    public static URI getWxUserListURI(
            String accesssToken, String nextOpenId) throws URISyntaxException {
        URIBuilder uriBuilder = new URIBuilder().setScheme("https")
                .setHost(WX_BASE_URL).setPath("user/get")
                .setParameter("access_token", accesssToken)
                .setParameter("next_openid", nextOpenId);

        return uriBuilder.build();
    }

    public static URI getWxSendTemplateMessageURI(
            String accesssToken) throws URISyntaxException {
        URIBuilder uriBuilder = new URIBuilder().setScheme("https")
                .setHost(WX_BASE_URL).setPath("message/template/send")
                .setParameter("access_token", accesssToken);
        return uriBuilder.build();
    }

    public static URI getWxCreateIndividualMenuURI(
            String accesssToken) {
        URIBuilder uriBuilder = new URIBuilder().setScheme("https")
                .setHost(WX_BASE_URL)
                .setPath("menu/addconditional")
                .setParameter("access_token", accesssToken);

        try {
            return uriBuilder.build();
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }

        return null;
    }

    public static URI getWxDeleteIndividualMenuURI(
            String accesssToken) {
        URIBuilder uriBuilder = new URIBuilder().setScheme("https")
                .setHost(WX_BASE_URL)
                .setPath("menu/delconditional")
                .setParameter("access_token", accesssToken);

        try {
            return uriBuilder.build();
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }

        return null;
    }

    public static URI getWxTryMatchIndividualMenuURI(
            String accesssToken) {
        URIBuilder uriBuilder = new URIBuilder().setScheme("https")
                .setHost(WX_BASE_URL)
                .setPath("menu/trymatch")
                .setParameter("access_token", accesssToken);

        try {
            return uriBuilder.build();
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }

        return null;
    }

    /**
     * begin ---------------------   wx user group   ------------ begin
     */
    // 创建分组
    public static URI getWxCreateGroupURI(
            String accesssToken) {
        URIBuilder uriBuilder = new URIBuilder().setScheme("https")
                .setHost(WX_BASE_URL)
                .setPath("groups/create")
                .setParameter("access_token", accesssToken);

        try {
            return uriBuilder.build();
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }

        return null;
    }

    // 查询所有分组
    public static URI getWxGetGroupURI(
            String accesssToken) {
        URIBuilder uriBuilder = new URIBuilder().setScheme("https")
                .setHost(WX_BASE_URL)
                .setPath("groups/get")
                .setParameter("access_token", accesssToken);

        try {
            return uriBuilder.build();
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }

        return null;
    }

    // 查询用户所在分组
    public static URI getWxGetGroupIdURI(
            String accesssToken) {
        URIBuilder uriBuilder = new URIBuilder().setScheme("https")
                .setHost(WX_BASE_URL)
                .setPath("groups/getid")
                .setParameter("access_token", accesssToken);

        try {
            return uriBuilder.build();
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }

        return null;
    }

    // 修改分组名
    public static URI getWxUpdateGroupURI(
            String accesssToken) {
        URIBuilder uriBuilder = new URIBuilder().setScheme("https")
                .setHost(WX_BASE_URL)
                .setPath("groups/update")
                .setParameter("access_token", accesssToken);

        try {
            return uriBuilder.build();
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }

        return null;
    }

    // 移动用户分组
    public static URI getWxUpdateGroupMemberURI(
            String accesssToken) {
        URIBuilder uriBuilder = new URIBuilder().setScheme("https")
                .setHost(WX_BASE_URL)
                .setPath("groups/members/update")
                .setParameter("access_token", accesssToken);

        try {
            return uriBuilder.build();
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }

        return null;
    }

    // 批量移动用户分组
    public static URI getWxUpdateGroupMembersURI(
            String accesssToken) {
        URIBuilder uriBuilder = new URIBuilder().setScheme("https")
                .setHost(WX_BASE_URL)
                .setPath("groups/members/batchupdate")
                .setParameter("access_token", accesssToken);

        try {
            return uriBuilder.build();
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }

        return null;
    }

    // 删除分组
    public static URI getWxDeleteGroupURI(
            String accesssToken) {
        URIBuilder uriBuilder = new URIBuilder().setScheme("https")
                .setHost(WX_BASE_URL)
                .setPath("groups/delete")
                .setParameter("access_token", accesssToken);

        try {
            return uriBuilder.build();
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }

        return null;
    }
    /** end ---------------------   wx user group   ------------ end */


    /**
     * begin ---------------------   wx material   ------------ begin
     */
    // 获取素材列表
    public static URI getWxGetMaterialListURI(
            String accesssToken) {
        URIBuilder uriBuilder = new URIBuilder().setScheme("https")
                .setHost(WX_BASE_URL)
                .setPath("material/batchget_material")
                .setParameter("access_token", accesssToken);

        try {
            return uriBuilder.build();
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }

        return null;
    }
    /** end -----------------------   wx material   ------------ end */
}