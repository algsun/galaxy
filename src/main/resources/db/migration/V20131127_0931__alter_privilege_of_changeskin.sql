-- author xu.yuexi
-- description 修改更换皮肤url

UPDATE t_system_privilege
SET
url='/blackhole/pages/profile/update-view.ftl'
WHERE
privilegeId='blackhole:theme'


