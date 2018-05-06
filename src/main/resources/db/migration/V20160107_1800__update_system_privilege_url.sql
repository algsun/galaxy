--author  chenyaofei
--description 给环境监测主页权限赋值

UPDATE t_system_privilege SET url='../blackhole/subsystems.action' WHERE privilegeId='blueplanet:home';