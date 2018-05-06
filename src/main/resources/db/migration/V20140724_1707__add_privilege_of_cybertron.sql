-- author xu.yuexi
-- description 添加档案管理权限
INSERT INTO `t_system_privilege`
            (subsystemId,
             sequence,
             privilegeId,
             parentPrivilegeId,
             privilegeName_CN,
             url,
             state,
             isNavigation)
VALUES (10,
        1,
        'cybertron:manage',
        NULL,
        '档案管理',
        NULL,
        122,
        1),
       (10,
        2,
        'cybertron:manage:add',
        'cybertron:manage',
        '添加档案',
        NULL,
        122,
        1),
       (10,
        2,
        'cybertron:manage:edit',
        'cybertron:manage',
        '编辑档案',
        NULL,
        122,
        1),
       (10,
        2,
        'cybertron:manage:delete',
        'cybertron:manage',
        '删除档案',
        NULL,
        122,
        1);

--站点管理员提添加权限
INSERT INTO `t_role_privilege` (roleId, privilegeId)
(SELECT
  id AS roleId,
  'cybertron:manage' AS privilegeId
FROM
  `t_roles`
WHERE isManager = 1) ;

INSERT INTO `t_role_privilege` (roleId, privilegeId)
(SELECT
  id AS roleId,
  'cybertron:manage:add' AS privilegeId
FROM
  `t_roles`
WHERE isManager = 1) ;

INSERT INTO `t_role_privilege` (roleId, privilegeId)
(SELECT
  id AS roleId,
  'cybertron:manage:edit' AS privilegeId
FROM
  `t_roles`
WHERE isManager = 1) ;

INSERT INTO `t_role_privilege` (roleId, privilegeId)
(SELECT
  id AS roleId,
  'cybertron:manage:delete' AS privilegeId
FROM
  `t_roles`
WHERE isManager = 1) ;