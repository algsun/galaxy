--author wang.geng
--修改子系统'GIS地图'为'区域中心'
--修改'图库'为'信息发布'

UPDATE t_system_privilege p SET p.privilegeName_CN ='信息发布' WHERE subsystemId = 2 AND privilegeId = 'blueplanet:datacenter';
UPDATE t_system_privilege p SET p.privilegeName_CN ='信息发布' WHERE parentPrivilegeId = 'blueplanet:datacenter' AND privilegeId = 'blueplanet:datacenter:charts';
UPDATE t_subsystem s SET s.subsystemName = '区域中心' WHERE subsystemId = 9 AND subsystemCode = 'biela';