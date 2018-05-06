UPDATE t_system_privilege SET privilegeName_en_US='Location point management' WHERE privilegeId='blueplanet:manage:location';

UPDATE t_system_privilege SET privilegeName_en_US='Log' WHERE privilegeId='blackhole:log';
UPDATE t_system_privilege SET privilegeName_en_US='Home' WHERE privilegeId='blackhole:home';
UPDATE t_system_privilege SET privilegeName_en_US='News' WHERE privilegeId='blackhole:post';
UPDATE t_system_privilege SET privilegeName_en_US='Role' WHERE privilegeId='blackhole:role';
UPDATE t_system_privilege SET privilegeName_en_US='User' WHERE privilegeId='blackhole:user';
UPDATE t_system_privilege SET privilegeName_en_US='Area management' WHERE privilegeId='blackhole:zone';
UPDATE t_system_privilege SET privilegeName_en_US='Theme' WHERE privilegeId='blackhole:theme';
UPDATE t_system_privilege SET privilegeName_en_US='Personal information' WHERE privilegeId='blackhole:profile';
UPDATE t_system_privilege SET privilegeName_en_US='Add news' WHERE privilegeId='blackhole:post:add';
UPDATE t_system_privilege SET privilegeName_en_US='Add role' WHERE privilegeId='blackhole:role:add';

UPDATE t_system_privilege SET privilegeName_en_US='Add user' WHERE privilegeId='blackhole:user:add';
UPDATE t_system_privilege SET privilegeName_en_US='Query log' WHERE privilegeId='blackhole:log:query';
UPDATE t_system_privilege SET privilegeName_en_US='Edit news' WHERE privilegeId='blackhole:post:edit';
UPDATE t_system_privilege SET privilegeName_en_US='Role detail' WHERE privilegeId='blackhole:role:info';
UPDATE t_system_privilege SET privilegeName_en_US='Business system operation' WHERE privilegeId='blackhole:subsystem';
UPDATE t_system_privilege SET privilegeName_en_US='Assign user role' WHERE privilegeId='blackhole:user:role';
UPDATE t_system_privilege SET privilegeName_en_US='Site' WHERE privilegeId='blackhole:logicGroup';
UPDATE t_system_privilege SET privilegeName_en_US='Query news' WHERE privilegeId='blackhole:post:query';
UPDATE t_system_privilege SET privilegeName_en_US='Query role' WHERE privilegeId='blackhole:role:query';
UPDATE t_system_privilege SET privilegeName_en_US='Query user' WHERE privilegeId='blackhole:user:query';

UPDATE t_system_privilege SET privilegeName_en_US='Delete role' WHERE privilegeId='blackhole:role:delete';
UPDATE t_system_privilege SET privilegeName_en_US='Edit role' WHERE privilegeId='blackhole:role:update';
UPDATE t_system_privilege SET privilegeName_en_US='Delete user' WHERE privilegeId='blackhole:user:delete';
UPDATE t_system_privilege SET privilegeName_en_US='Disable user' WHERE privilegeId='blackhole:user:disable';
UPDATE t_system_privilege SET privilegeName_en_US='Personal information' WHERE privilegeId='blackhole:subsystem:uma';
UPDATE t_system_privilege SET privilegeName_en_US='My work' WHERE privilegeId='blackhole:profile:mytask';
UPDATE t_system_privilege SET privilegeName_en_US='Personal information' WHERE privilegeId='blackhole:profile:update';
UPDATE t_system_privilege SET privilegeName_en_US='Site detail' WHERE privilegeId='blackhole:logicGroup:info';
UPDATE t_system_privilege SET privilegeName_en_US='Regional center' WHERE privilegeId='blackhole:subsystem:biela';
UPDATE t_system_privilege SET privilegeName_en_US='Asset mamagment' WHERE privilegeId='blackhole:subsystem:orion';

UPDATE t_system_privilege SET privilegeName_en_US='Sssign user site groups' WHERE privilegeId='blackhole:user:logicGroup';
UPDATE t_system_privilege SET privilegeName_en_US='Query site' WHERE privilegeId='blackhole:logicGroup:query';
UPDATE t_system_privilege SET privilegeName_en_US='Modify password' WHERE privilegeId='blackhole:profile:password';
UPDATE t_system_privilege SET privilegeName_en_US='Outreach management' WHERE privilegeId='blackhole:subsystem:halley';
UPDATE t_system_privilege SET privilegeName_en_US='Achievement exhibition' WHERE privilegeId='blackhole:subsystem:saturn';
UPDATE t_system_privilege SET privilegeName_en_US='Site' WHERE privilegeId='blackhole:currentLogicGroup';
UPDATE t_system_privilege SET privilegeName_en_US='Add site' WHERE privilegeId='blackhole:logicGroup:create';
UPDATE t_system_privilege SET privilegeName_en_US='Delete site' WHERE privilegeId='blackhole:logicGroup:delete';
UPDATE t_system_privilege SET privilegeName_en_US='Data analysis' WHERE privilegeId='blackhole:subsystem:phoenix';
UPDATE t_system_privilege SET privilegeName_en_US='notology monitoring' WHERE privilegeId='blackhole:subsystem:proxima';

UPDATE t_system_privilege SET privilegeName_en_US='Publish task' WHERE privilegeId='blackhole:profile:publishtask';
UPDATE t_system_privilege SET privilegeName_en_US='System management' WHERE privilegeId='blackhole:subsystem:blackhole';
UPDATE t_system_privilege SET privilegeName_en_US='Archives management system' WHERE privilegeId='blackhole:subsystem:cybertron';
UPDATE t_system_privilege SET privilegeName_en_US='Environmental monitoring' WHERE privilegeId='blackhole:subsystem:blueplanet';
UPDATE t_system_privilege SET privilegeName_en_US='Edit department' WHERE privilegeId='blackhole:user:updateDepartment';
UPDATE t_system_privilege SET privilegeName_en_US='Site information ' WHERE privilegeId='blackhole:currentLogicGroup:info';
UPDATE t_system_privilege SET privilegeName_en_US='Modify site information' WHERE privilegeId='blackhole:currentLogicGroup:update';
UPDATE t_system_privilege SET privilegeName_en_US='Initialization administrator' WHERE privilegeId='blackhole:logicGroup:initializeAdmin';
UPDATE t_system_privilege SET privilegeName_en_US='Structure adjustment' WHERE privilegeId='blackhole:currentLogicGroup:changeStructure';
UPDATE t_system_privilege SET privilegeName_en_US='Limiting operation' WHERE privilegeId='blackhole:currentLogicGroup:privilegeOperate';
UPDATE t_system_privilege SET privilegeName_en_US='Business system' WHERE privilegeId='blackhole:currentLogicGroup:logicGroupSubsystem';

UPDATE t_system_privilege SET state=122 WHERE privilegeId='blackhole:home';
