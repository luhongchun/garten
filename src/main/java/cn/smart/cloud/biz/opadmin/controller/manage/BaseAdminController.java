package cn.smart.cloud.biz.opadmin.controller.manage;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import cn.smart.cloud.biz.opadmin.entity.GartenManager;
import cn.smart.cloud.biz.opadmin.entity.KeyValueEntity;
import cn.smart.cloud.biz.opadmin.entity.garten.Garten;
import cn.smart.cloud.biz.opadmin.entity.garten.GartenClass;
import cn.smart.cloud.biz.opadmin.entity.garten.user.GartenUser;
import cn.smart.cloud.biz.opadmin.entity.user.SysUser;
import cn.smart.cloud.biz.opadmin.proxy.BaseCoreServiceProxy;
import cn.smart.cloud.biz.opadmin.proxy.BaseUserServiceProxy;
import cn.smart.cloud.biz.opadmin.proxy.GartenServiceProxy;

public class BaseAdminController {

    private static final Logger logger = LoggerFactory.getLogger(AdminGartenManageAdminController.class);

    @Autowired
    GartenServiceProxy gartenServiceProxy;
    @Autowired
    BaseCoreServiceProxy baserCoreServiceProxy;
    @Autowired
    BaseUserServiceProxy baseUserServiceProxy;

    List<Garten> getGatensByManagerId(HttpSession session) {
    	GartenManager user = (GartenManager) session.getAttribute("user");
        if (user == null) {
            logger.warn("Not find session user !!!");
            return new ArrayList<>();
        }
     //   String managerId = gartenServiceProxy.getGartenManagerId(user.getUserName());
        String managerId = gartenServiceProxy.getGartenManagerId(user.getLoginId());
        logger.info("listGarten, managerId: " + managerId);
        return gartenServiceProxy.getGartensByManagerId(managerId);
    }


    /**
     * Assemble html select tag values in search box
     *
     * @return List\<List\>
     */
    List<List> assembleSelectList(List<Garten> gartenList, String selectedGartenId,
                                  List<GartenClass> gartenClassList, String selectedGartenClassId,
                                  List<GartenUser> gartenUserList, String selectedGartenUserId) {
        // Add garten list
        List<List> selectList = new ArrayList<>();
        if (gartenList == null || gartenList.size() == 0) {
            return selectList;
        }
        List<KeyValueEntity<String, String>> opList0 = new ArrayList<>();
        for (Garten garten : gartenList) {
            assembleOptionList(opList0, selectedGartenId, garten.getId(), garten.getName());
        }
        selectList.add(opList0);

        // Add garten class list
        if (gartenClassList == null || gartenClassList.size() == 0) {
            return selectList;
        }
        List<KeyValueEntity<String, String>> opList1 = new ArrayList<>();
        for (GartenClass gartenClass: gartenClassList) {
            assembleOptionList(opList1, selectedGartenClassId, gartenClass.getId(), gartenClass.getName());
        }
        selectList.add(opList1);

        // Add garten user list
        if (gartenUserList == null || gartenUserList.size() == 0) {
            return selectList;
        }
        List<KeyValueEntity<String, String>> opList2 = new ArrayList<>();
        for (GartenUser gartenUser: gartenUserList) {
            assembleOptionList(opList2, selectedGartenUserId, gartenUser.getId(), gartenUser.getAliasName());
        }
        selectList.add(opList2);

        return selectList;

    }

    private void assembleOptionList(List<KeyValueEntity<String, String>> optionList, String selectedId,
                            String id, String value) {
        KeyValueEntity<String, String> entity = new KeyValueEntity<>();
        entity.setKey(id);
        entity.setValue(value);
        entity.setSelected(id.equalsIgnoreCase(selectedId));
        optionList.add(entity);
    }

    @SuppressWarnings("rawtypes")
    protected List<List> assembleSelectList(List<Garten> gartenList, String selectedGartenId,
            List<GartenClass> gartenClassList, String selectedGartenClassId) {
        return assembleSelectList(gartenList, selectedGartenId, gartenClassList, selectedGartenClassId, null, "");
    }
}
