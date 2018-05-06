-- author 王耕
-- 展示类别，展示内容权限添加
INSERT INTO `t_system_privilege`(subsystemId,sequence,privilegeId,parentPrivilegeId,privilegeName_CN,url,state,isNavigation)
VALUES(11,5,'saturn:manager:mediaShows','saturn:manager','展示类别','manager/mediaShows',122,1);
INSERT INTO `t_system_privilege`(subsystemId,sequence,privilegeId,parentPrivilegeId,privilegeName_CN,url,state,isNavigation)
VALUES(11,6,'saturn:manager:mediaDetails','saturn:manager','展示内容','manager/mediaDetails',122,1);

--展示内容的增删改权限添加
INSERT INTO `t_system_privilege`(subsystemId,sequence,privilegeId,parentPrivilegeId,privilegeName_CN,url,state,isNavigation)
VALUES(11,1,'saturn:manager:mediaDetails:add','saturn:manager:mediaDetails','新增','manager/toSaveMediaDetail',50,0);
INSERT INTO `t_system_privilege`(subsystemId,sequence,privilegeId,parentPrivilegeId,privilegeName_CN,url,state,isNavigation)
VALUES(11,2,'saturn:manager:mediaDetails:update','saturn:manager:mediaDetails','修改','manager/toEditMediaDetail',50,0);
INSERT INTO `t_system_privilege`(subsystemId,sequence,privilegeId,parentPrivilegeId,privilegeName_CN,url,state,isNavigation)
VALUES(11,3,'saturn:manager:mediaDetails:delete','saturn:manager:mediaDetails','删除','manager/deleteMediaDetail',50,0);

--展示类别的增删改权限添加
INSERT INTO `t_system_privilege`(subsystemId,sequence,privilegeId,parentPrivilegeId,privilegeName_CN,url,state,isNavigation)
VALUES(11,1,'saturn:manager:mediaShows:add','saturn:manager:mediaShows','新增','manager/saveMediaShow',50,0);
INSERT INTO `t_system_privilege`(subsystemId,sequence,privilegeId,parentPrivilegeId,privilegeName_CN,url,state,isNavigation)
VALUES(11,2,'saturn:manager:mediaShows:update','saturn:manager:mediaShows','修改','manager/editMediaShow',50,0);
INSERT INTO `t_system_privilege`(subsystemId,sequence,privilegeId,parentPrivilegeId,privilegeName_CN,url,state,isNavigation)
VALUES(11,3,'saturn:manager:mediaShows:delete','saturn:manager:mediaShows','删除','manager/deleteMediaShow',50,0);