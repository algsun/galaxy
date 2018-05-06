--author wang.geng
--站点/GIS地图概览，站点配置功能的权限
INSERT INTO `t_system_privilege`(subsystemId,sequence,privilegeId,parentPrivilegeId,privilegeName_CN,url,state,isNavigation) VALUES
(9,0,'biela:mapOverview',NULL,'站点地图概览',NULL,122,0),(9,1,'biela:mapOverview:config','biela:mapOverview','站点配置',NULL,122,0);