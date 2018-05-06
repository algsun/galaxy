--author xuyuexi
--description 添加权限，显示一年监测指标峰值

INSERT INTO `t_system_privilege`
(`subsystemId`,
`sequence`,
`privilegeId`,
`parentPrivilegeId`,
`privilegeName_CN`,
`url`,
`state`,
`isNavigation`)
VALUE
(6,
4,
'phoenix:blueplanet:peakValue',
'phoenix:blueplanet',
'区域监测指标峰值',
'blueplanet/peakValue',
122,
1);

