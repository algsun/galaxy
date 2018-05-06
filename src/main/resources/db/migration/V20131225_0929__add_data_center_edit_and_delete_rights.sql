--author wang.geng
--数据中心，图表组件化，编辑与删除功能的权限
INSERT INTO `t_system_privilege`(subsystemId,sequence,privilegeId,parentPrivilegeId,privilegeName_CN,url,state,isNavigation) VALUES
(2,0,'blueplanet:datacenter:edit','blueplanet:datacenter:charts','编辑',NULL,122,0),
(2,0,'blueplanet:datacenter:delete','blueplanet:datacenter:charts','删除',NULL,122,0),
(2,0,'blueplanet:datacenter:preview','blueplanet:datacenter:charts','预览',NULL,122,0),
(2,0,'blueplanet:datacenter:addlayout','blueplanet:datacenter:charts','新增布局',NULL,122,0);