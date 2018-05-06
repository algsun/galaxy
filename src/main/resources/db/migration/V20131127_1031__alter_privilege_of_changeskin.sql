-- author xu.yuexi
-- description 修改更换皮肤url

UPDATE t_system_privilege
SET
url='theme.action'
WHERE
privilegeId='blackhole:theme'


