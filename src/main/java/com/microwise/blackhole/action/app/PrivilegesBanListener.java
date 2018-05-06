package com.microwise.blackhole.action.app;


import com.google.common.io.Resources;
import com.microwise.blackhole.bean.LogicGroup;
import com.microwise.blackhole.bean.Privilege;
import com.microwise.blackhole.sys.Blackhole;
import com.microwise.common.sys.annotation.Beans;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.yaml.snakeyaml.Yaml;

import java.io.IOException;
import java.io.InputStream;
import java.util.*;

/**
 * 权限过滤被禁用权限
 *
 * @author xu.baoji
 * @date 2014.02.25
 */
@Beans.Bean
@Blackhole
public class PrivilegesBanListener implements BeforeInitPrivilegesListener {
    public static final Logger log = LoggerFactory.getLogger(PrivilegesBanListener.class);

    private static final String FILTE_YMAL_URL = "privilegesBan.yml";

    private static final String FILTE_KEY = "privilegesBan";


    @Override
    public void beforeInit(Collection<Privilege> privileges, LogicGroup logicGroup) {
        List<String> filtePrivileges = getBanPrivileges();
        Iterator<Privilege> iterator = privileges.iterator();
        while (iterator.hasNext()) {
            Privilege privilege = iterator.next();
            if (filtePrivileges.contains(privilege.getPrivilegeId())) {
                log.info("过滤权限[{}]", privilege.getPrivilegeId());
                iterator.remove();
            }
        }
    }

    /**
     * 获取被禁用的权限
     *
     * @return
     */
    public static List<String> getBanPrivileges() {
        InputStream input = null;
        try {
            input = Resources.getResource(FILTE_YMAL_URL).openStream();
            Map<String, Object> config = (Map<String, Object>) new Yaml().load(input);
            List<String> privileges = (List<String>) config.get(FILTE_KEY);
            if (privileges != null) {
                return privileges;
            }
        } catch (IOException e) {
            log.error("load config", e);
        } finally {
            try {
                if (input != null) {
                    input.close();
                }
            } catch (IOException e) {
                log.error("", e);
            }
        }

        return Collections.emptyList();
    }

}
