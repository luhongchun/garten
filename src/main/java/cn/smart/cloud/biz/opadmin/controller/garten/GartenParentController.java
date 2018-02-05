package cn.smart.cloud.biz.opadmin.controller.garten;

import cn.smart.cloud.biz.opadmin.constant.Constant;
import cn.smart.cloud.biz.opadmin.entity.BaseRole;
import cn.smart.cloud.biz.opadmin.entity.BaseUser;
import cn.smart.cloud.biz.opadmin.entity.SexType;
import cn.smart.cloud.biz.opadmin.entity.account.Account;
import cn.smart.cloud.biz.opadmin.entity.garten.user.*;
import cn.smart.cloud.biz.opadmin.entity.weixin.WxOpenUser;
import cn.smart.cloud.biz.opadmin.entity.weixin.WxSubscription;
import cn.smart.cloud.biz.opadmin.entity.weixin.WxSubscriptionType;
import cn.smart.cloud.biz.opadmin.proxy.*;
import cn.smart.cloud.biz.opadmin.util.UuidUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
@RequestMapping("/garten/manage/parent")
public class GartenParentController {

    private static final Logger logger = LoggerFactory.getLogger(GartenParentController.class);
    private static final String REDIRECT_LOCAL = "redirect:/garten/manage/parent/";
    private static final String REDIRECT_CHILD = "redirect:/garten/manage/child/edit/";

    @Value("${text.tip.success}")
    String tipSuccess;
    @Value("${text.tip.failed}")
    String tipFailed;

    @Autowired
    private GartenServiceProxy gartenServiceProxy;
    @Autowired
    private BaseUserServiceProxy baseUserServiceProxy;
    @Autowired
    private BaseCoreServiceProxy baseServiceProxy;
    @Autowired
    private WxOpenUserServiceProxy wxOpenUserServiceProxy;
    @Autowired
    private WxServiceProxy wxServiceProxy;

    @RequestMapping(value = "add", method = RequestMethod.POST)
    public String add(HttpServletRequest request) {
        String childId = request.getParameter("childId");
        String mId = request.getParameter("mId");
        logger.info("add, childId:" + childId + ", mId:" + mId);
        request.setAttribute("childId", childId);
        request.setAttribute("mId", mId);
        request.setAttribute("sex", SexType.MALE);
        return "manage/garten/parent/add";
    }

    @RequestMapping(value = "add_submit")
    public String addSubmit(HttpServletRequest request) {
        String childId = request.getParameter("childId");
        String mId = request.getParameter("mId");
        String name = request.getParameter("name");
        String sexString = request.getParameter("sex");
        String phoneNum = request.getParameter("phoneNum");
        logger.info("addSubmit, n:" + name + ", c:" + childId + ", s:" + sexString + ", mId:" + mId);
        if (StringUtils.isEmpty(name)) {
            return REDIRECT_CHILD + childId + "/" + mId;
        }
        SexType sex = SexType.MALE;
        if (SexType.FEMALE.name().equals(sexString)) {
            sex = SexType.FEMALE;
        }

        try {
            createParent(childId, name, phoneNum, sex, null);
            request.setAttribute("tip", tipSuccess);
        } catch (Exception e) {
            logger.error("addSubmit exception:" + e);
            request.setAttribute("tip", tipSuccess);
        }
        return REDIRECT_CHILD + childId + "/" + mId;
    }

    @RequestMapping(value = "edit", method = RequestMethod.POST)
    public String edit(HttpServletRequest request) {
        String parentId = request.getParameter("parentId");
        String childId = request.getParameter("childId");
        String mId = request.getParameter("mId");
        logger.info("edit parentId:" + parentId + ", childId:" + childId + ", mId:" + mId);
        GartenUser parent = gartenServiceProxy.getUser(parentId);
        request.setAttribute("parent", parent);
        BaseUser p = baseUserServiceProxy.get(parent.getBaseUserId());
        if (p != null) {
            request.setAttribute("sex", p.getSex());
            request.setAttribute("phoneNum", p.getPhoneNum());
        }
        request.setAttribute("childId", childId);
        request.setAttribute("mId", mId);
        return "manage/garten/parent/edit";
    }

    @RequestMapping(value = "edit_submit", method = RequestMethod.POST)
    public String editSubmit(HttpServletRequest request) {
        String id = request.getParameter("parentId");
        String childId = request.getParameter("childId");
        String mId = request.getParameter("mId");
        String name = request.getParameter("name");
        String phoneNum = request.getParameter("phoneNum");
        String sex = request.getParameter("sex");
        logger.info("editSubmit, n:" + name + ", phoneNum:" + phoneNum + ", childId:" + childId + ", s:" + sex + ", mId:" + mId);
        if (StringUtils.isEmpty(name)) {
            return REDIRECT_CHILD + childId + "/" + mId;
        }
        try {
            GartenUser parent = gartenServiceProxy.getUser(id);
            parent.setAliasName(name);
            gartenServiceProxy.saveUser(parent);
            BaseUser buParent = baseUserServiceProxy.get(parent.getBaseUserId());
            buParent.setSex(SexType.valueOf(sex));
            buParent.setName(name);
            buParent.setPhoneNum(phoneNum);
            baseUserServiceProxy.update(buParent);

            List<BaseRole> roles = gartenServiceProxy.getAllGartenRoleByGartenUserId(parent.getId());
            BaseRole brParent = baseServiceProxy.getBaseRoleById(roles.get(0).getId());
            brParent.setName(name);
            baseServiceProxy.updateBaseRole(brParent);
            Account account = gartenServiceProxy.getByBaseUserId(parent.getBaseUserId());
            account.setAliasName(name);
            gartenServiceProxy.updateAccount(account);
            request.setAttribute("tip", tipSuccess);
        } catch (Exception e) {
            logger.info("editSubmit exception:" + e);
            request.setAttribute("tip", tipFailed);
        }
        return REDIRECT_CHILD + childId + "/" + mId;
    }

    @RequestMapping(value = "del_submit")
    public String delSubmit(HttpServletRequest request) {
        String parentId = request.getParameter("parentId");
        String childId = request.getParameter("childId");
        String mId = request.getParameter("mId");
        logger.info("delSubmit parentId:" + parentId + ", childId:" + childId + ", mId:" + mId);
        logger.info("delSubmit ret:" + deleteParent(parentId));
        return REDIRECT_CHILD + childId + "/" + mId;
    }

    public boolean createParent(String childId, String parentName,
                                String phoneNum, SexType sex, String openId) {
        try {
            GartenUser child = gartenServiceProxy.getUser(childId);

            BaseUser buParentOld = baseUserServiceProxy.getByPhoneNum(phoneNum);
            if (buParentOld != null) {
                logger.warn("Parent have exists ! BaseUser id: " + buParentOld.getId());
            }

            BaseUser buParent = new BaseUser();
            buParent.setGlobalId(UuidUtil.randomWithMd5Encrypted());
            buParent.setName(parentName);
            buParent.setPhoneNum(phoneNum);
            buParent.setSex(sex);
            buParent = baseUserServiceProxy.create(buParent);
            if (!StringUtils.isEmpty(openId)) {
                WxOpenUser openUser = wxOpenUserServiceProxy.getWxOpenUserByOpenId(openId);
                openUser.setBaseUserId(buParent.getId());
                wxOpenUserServiceProxy.update(openUser);
            }

            BaseRole brParent = new BaseRole();
            brParent.setName(parentName);
            brParent.setRoleType(Constant.RoleType.PARENT);
            brParent = baseServiceProxy.createBaseRole(brParent);

            GartenUser parent = new GartenUser();
            parent.setAliasName(parentName);
            parent.setBaseUserId(buParent.getId());
            parent.setGartenId(child.getGartenId());
            logger.info("Save parent id: " + parent.getId());
            parent = gartenServiceProxy.createUser(parent);
            logger.info("Saved parent id: " + (parent == null ? "null" : parent.getId()));
            gartenServiceProxy.bindUserAndRole(parent, brParent);

            GartenUser2GartenClass u2c = new GartenUser2GartenClass();
            u2c.setGartenUserId(parent.getId());
            u2c.setGartenClassId(gartenServiceProxy.getByGartenUserId(childId).getId());
            logger.info("Save GartenUser2GartenClass id: " + parent.getId());
            u2c = gartenServiceProxy.saveU2C(u2c);
            logger.info("Saved GartenUser2GartenClass id: " + (u2c == null ? "null" : u2c.getGartenUserId()));

            GartenUserRelation relation = new GartenUserRelation();
            relation.setFromGartenUserId(parent.getId());
            relation.setToGartenUserId(childId);
            relation.setType(GartenUserRelationType.PARENT);
            logger.info("Save GartenUserRelation id: " + parent.getId());
            relation = gartenServiceProxy.saveRelation(relation);
            logger.info("Saved GartenUserRelation id: " + (relation == null ? "null" : relation.getToGartenUserId()));
            initAccount(parent);
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    private boolean deleteParent(String parentId) {
        if (StringUtils.isEmpty(parentId)) {
            return false;
        }
        try {
            logger.info("deleteParent, parentId:" + parentId);
            GartenUser parent = gartenServiceProxy.getUser(parentId);
            String baseUserId = parent.getBaseUserId();

            gartenServiceProxy.deleteParent(parentId);
            if (gartenServiceProxy.getUser(parentId) != null) {
                logger.error("deleteParent, failed !");
                return false;
            }
            baseUserServiceProxy.delete(baseUserId);
            logger.info("deleteParent, after delete user.");
            WxSubscription subscription = wxServiceProxy.getSubscriptionByBizType(WxSubscriptionType.BIZ_ALL).get(0);
            WxOpenUser openUser = wxOpenUserServiceProxy
                    .getWxOpenUserByBaseUserIdAndSubscriptionId(baseUserId, subscription.getId());
            if (openUser != null) {
                openUser.setBaseUserId("");
                wxOpenUserServiceProxy.update(openUser);
                logger.info("deleteParent, update open user.");
            }

            destroyAccount(parent);
            logger.info("deleteParent, finish.");
        } catch (Exception e) {
            logger.error("deleteParent, e:" + e);
            e.printStackTrace();
            return false;
        }
        return true;
    }

    private Account initAccount(GartenUser parent) {
        return gartenServiceProxy.initAccount(parent);
    }

    private void destroyAccount(GartenUser parent) {
        gartenServiceProxy.destroyAccount(parent);
    }
}