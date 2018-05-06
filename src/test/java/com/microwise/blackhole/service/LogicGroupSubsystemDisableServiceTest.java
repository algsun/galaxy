package com.microwise.blackhole.service;

import com.microwise.blackhole.bean.Subsystem;
import com.microwise.common.sys.test.CleanDBTest;
import org.junit.*;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * 站点开关子系统测试
 *
 * @author liuzhu
 * @date 2014/3/26
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class LogicGroupSubsystemDisableServiceTest extends CleanDBTest {

    @Autowired
    private LogicGroupSubsystemDisableService logicGroupSubsystemDisableService;

    @Before
    public void before() throws Exception {
        xmlInsert2("common/dbxml/blackhole/LogicGroupSubsystemDisableServiceTest.xml");
    }

    @Test
    public void findSubsystem() {
        List<Subsystem> subsystems = logicGroupSubsystemDisableService.findSubsystem();
        Assert.assertEquals(10, subsystems.size());
    }

    @Ignore
    public void findSubsystemOpen(){
       List<Subsystem> subsystems = logicGroupSubsystemDisableService.findSubsystemOpen(1);
       Assert.assertEquals(7,subsystems.size());
    }

    @Ignore
    public void findSubsystemByLogicGroupId(){
        List<Subsystem> subsystems = logicGroupSubsystemDisableService.findSubsystemByLogicGroupId(1);
        Assert.assertEquals(8,subsystems.size());
    }

    @Test
    public void openState(){
        logicGroupSubsystemDisableService.able(1, 2);
    }



    @Test
    public void changeState() {
        logicGroupSubsystemDisableService.changeState(3, 0);
        List<Subsystem> subsystems = logicGroupSubsystemDisableService.findSubsystem();
        Subsystem subsystem = new Subsystem();
        for (Subsystem s : subsystems) {
            if (s.getSubsystemId()==3){
                subsystem = s;
            }
        }
        Assert.assertEquals(false,subsystem.isEnable());
    }

    @Test
    public void closeState(){
        logicGroupSubsystemDisableService.disable("32", 1, 3);
    }


}
