--author 谢登
--description 修改唯一约束

ALTER TABLE `m_control_module_sensor_action` DROP INDEX route ;
ALTER TABLE `m_control_module_sensor_action` ADD UNIQUE KEY `controlModuleId_route` (`route`,`controlModuleId`);
