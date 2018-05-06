-- author xu.yuexi
-- description 修改图片浏览，摄像机权限

DELETE FROM t_role_privilege WHERE  privilegeId LIKE "proxima:opticsDV%" ;
DELETE FROM t_role_privilege WHERE  privilegeId LIKE "proxima:dV%" ;
DELETE FROM t_system_privilege WHERE  privilegeId LIKE "proxima:opticsDV%" AND parentPrivilegeId IS NOT NULL;
DELETE FROM t_system_privilege WHERE  privilegeId LIKE "proxima:dV%" AND parentPrivilegeId IS NOT NULL;
DELETE FROM t_system_privilege WHERE  privilegeId LIKE "proxima:opticsDV%";
DELETE FROM t_system_privilege WHERE  privilegeId LIKE "proxima:dV%";

INSERT INTO `t_system_privilege`
            (`subsystemId`,
             `sequence`,
             `privilegeId`,
             `parentPrivilegeId`,
             `privilegeName_CN`,
             `url`,
             `state`,
             `isNavigation`)
VALUES ('3',
        '2',
        'proxima:dVImage',
        NULL,
        '图片浏览',
        'viewOpticsImageDefault.action',
        '50',
        '1'),
       ('3',
        '1',
        'proxima:dVImage:optics',
       'proxima:dVImage',
        '光学图片浏览',
        'viewOpticsImageDefault.action',
        '50',
        '1'),
       ('3',
        '2',
        'proxima:dVImage:infrared',
         'proxima:dVImage',
        '红外图片浏览',
        'viewInfraredImageDefault.action',
        '50',
        '1'),
         ('3',
        '3',
        'proxima:dVImage:trendChart',
         'proxima:dVImage',
        '温度变化趋势图',
        'infraredTrendChartDefault.action',
        '50',
        '1'),
       ('3',
        '4',
        'proxima:dVPlace',
        NULL,
        '摄像机管理',
        'queryDVPlaceDefault.action',
        '50',
        '1'),
       ('3',
        '1',
        'proxima:dVPlace:addOptics',
        'proxima:dVPlace',
        '添加光学摄像机',
        'toAddOpticsDVPlace.action',
        '50',
        '1'),
       ('3',
        '2',
        'proxima:dVPlace:addInfrared',
        'proxima:dVPlace',
        '添加红外摄像机',
        'toAddInfraredDVPlace.action',
        '50',
        '1'),
       ('3',
        '0',
        'proxima:dVPlace:enableOptics',
        'proxima:dVPlace',
        '启用/停用',
        NULL,
        '50',
        '0'),
       ('3',
        '0',
        'proxima:dVPlace:enableInfrared',
        'proxima:dVPlace',
        '启用/停用',
        NULL,
        '50',
        '0'),
       ('3',
        '0',
        'proxima:dVPlace:list',
        'proxima:dVPlace',
        '摄像机列表',
        'queryDVPlaceDefault.action',
        '50',
        '1'),
       ('3',
        '0',
        'proxima:dVPlace:updateOptics',
        'proxima:dVPlace',
        '修改',
        NULL,
        '50',
        '0'),
       ('3',
        '0',
        'proxima:dVPlace:updateInfrared',
        'proxima:dVPlace',
        '修改',
        NULL,
        '50',
        '0');


INSERT INTO `t_role_privilege` (`roleId`, `privilegeId`) (
SELECT
  id AS roldId,
  'proxima:dVPlace' AS privilegeId
FROM
  `t_roles`
WHERE isManager = 1);

INSERT INTO `t_role_privilege` ( `roleId`, `privilegeId`) (SELECT
  id AS roldId,
  'proxima:dVImage:optics' AS privilegeId
FROM
  `t_roles`
WHERE isManager = 1);

INSERT INTO `t_role_privilege` ( `roleId`, `privilegeId`) (SELECT
  id AS roldId,
  'proxima:dVImage:infrared' AS privilegeId
FROM
  `t_roles`
WHERE isManager = 1);

INSERT INTO `t_role_privilege` (`roleId`, `privilegeId`) (SELECT
  id AS roldId,
  'proxima:dVPlace:addOptics' AS privilegeId
FROM
  `t_roles`
WHERE isManager = 1);

INSERT INTO `t_role_privilege` (`roleId`, `privilegeId`) (SELECT
  id AS roldId,
  'proxima:dVPlace:addInfrared' AS privilegeId
FROM
  `t_roles`
WHERE isManager = 1);

INSERT INTO `t_role_privilege` (`roleId`, `privilegeId`) (SELECT
  id AS roldId,
  'proxima:dVPlace:enableOptics' AS privilegeId
FROM
  `t_roles`
WHERE isManager = 1);

INSERT INTO `t_role_privilege` (`roleId`, `privilegeId`) (SELECT
  id AS roldId,
  'proxima:dVPlace:enableInfrared' AS privilegeId
FROM
  `t_roles`
WHERE isManager = 1);


INSERT INTO `t_role_privilege` (`roleId`, `privilegeId`) (
SELECT
  id AS roldId,
  'proxima:dVPlace:list' AS privilegeId
FROM
  `t_roles`
WHERE isManager = 1);

INSERT INTO `t_role_privilege` (`roleId`, `privilegeId`)(
SELECT
  id AS roldId,
  'proxima:dVPlace:updateOptics' AS privilegeId
FROM
  `t_roles`
WHERE isManager = 1);

INSERT INTO `t_role_privilege` (`roleId`, `privilegeId`)(
SELECT
  id AS roldId,
  'proxima:dVPlace:updateInfrared' AS privilegeId
FROM
  `t_roles`
WHERE isManager = 1);

INSERT INTO `t_role_privilege` (`roleId`, `privilegeId`)(
SELECT
  id AS roldId,
  'proxima:dVImage' AS privilegeId
FROM
  `t_roles`
);

INSERT INTO `t_role_privilege` (`roleId`, `privilegeId`)(
SELECT
  id AS roldId,
  'proxima:dVImage:optics' AS privilegeId
FROM
  `t_roles`
);

INSERT INTO `t_role_privilege` (`roleId`, `privilegeId`)(
SELECT
  id AS roldId,
  'proxima:dVImage:infrared' AS privilegeId
FROM
  `t_roles`
);
