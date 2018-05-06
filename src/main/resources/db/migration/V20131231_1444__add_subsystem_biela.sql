--author wang.geng
--GIS站点地图概览子系统增加(biela)
DELETE FROM `t_subsystem` WHERE subsystemId = 9;
INSERT INTO `t_subsystem`(subsystemId,subsystemName,subsystemCode,remark,enable) VALUES
(9,'GIS地图','biela','比拉彗星',1);