package com.microwise.blackhole.service.impl;

import com.google.common.base.Function;
import com.google.common.collect.Maps;
import com.microwise.blackhole.bean.Subsystem;
import com.microwise.blackhole.dao.SubsystemDao;
import com.microwise.blackhole.service.GlobalizationService;
import com.microwise.blackhole.sys.Blackhole;
import com.microwise.blackhole.sys.Subsystems;
import com.microwise.common.sys.annotation.Beans;
import org.apache.struts2.ServletActionContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import javax.servlet.http.HttpSession;
import java.util.Map;

/**
 * @author chenyaofei
 * @date 16-2-29
 */

@Beans.Service
@Transactional
@Blackhole
public class GlobalizationServiceImpl implements GlobalizationService {

    @Autowired
    SubsystemDao subsystemDao;

    @Override
    public void changeDisplayByLanguage(String language) {
      initSubsystems(language);
    }

    private void initSubsystems(String language) {
        Subsystems.setSubsystems(subsystemDao.findAllSubsystemsByLanguage(language));
        HttpSession session= ServletActionContext.getRequest().getSession();
        Map<String, Subsystem> subsystemMap = Maps.uniqueIndex(Subsystems.all(), new Function<Subsystem, String>() {
            public String apply(Subsystem subsystem) {
                return subsystem.getSubsystemCode();
            }
        });
        session.setAttribute("app.subsystems", subsystemMap);
    }
}
