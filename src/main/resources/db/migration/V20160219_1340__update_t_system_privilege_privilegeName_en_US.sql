-- author chenyaofei
-- describe  t_system_privilege update privilegeName_en_US column

UPDATE t_system_privilege SET privilegeName_en_US='Monitoring per-warning' WHERE privilegeId='blueplanet:alarm:index';
UPDATE t_system_privilege SET privilegeName_en_US='Measure manegement' WHERE privilegeId='blueplanet:alarm:measure';
UPDATE t_system_privilege SET privilegeName_en_US='Alarm log' WHERE privilegeId='blueplanet:alarm:record';
UPDATE t_system_privilege SET privilegeName_en_US='Regulation' WHERE privilegeId='blueplanet:controller';
UPDATE t_system_privilege SET privilegeName_en_US='Regulate home page' WHERE privilegeId='blueplanet:controller:index';
UPDATE t_system_privilege SET privilegeName_en_US='Information publishing' WHERE privilegeId='blueplanet:datacenter';
UPDATE t_system_privilege SET privilegeName_en_US='New layout' WHERE privilegeId='blueplanet:datacenter:addlayout';
UPDATE t_system_privilege SET privilegeName_en_US='Information publishing' WHERE privilegeId='blueplanet:datacenter:charts';
UPDATE t_system_privilege SET privilegeName_en_US='Delete' WHERE privilegeId='blueplanet:datacenter:delete';
UPDATE t_system_privilege SET privilegeName_en_US='Edit' WHERE privilegeId='blueplanet:datacenter:edit';

UPDATE t_system_privilege SET privilegeName_en_US='Preview' WHERE privilegeId='blueplanet:datacenter:preview';
UPDATE t_system_privilege SET privilegeName_en_US='Home' WHERE privilegeId='blueplanet:home';
UPDATE t_system_privilege SET privilegeName_en_US='Location piont' WHERE privilegeId='blueplanet:location';
UPDATE t_system_privilege SET privilegeName_en_US='Big data analysis and per-warning' WHERE privilegeId='blueplanet:location:stock';
UPDATE t_system_privilege SET privilegeName_en_US='System maintenance' WHERE privilegeId='blueplanet:manage';
UPDATE t_system_privilege SET privilegeName_en_US='Equipment management' WHERE privilegeId='blueplanet:manage:device';
UPDATE t_system_privilege SET privilegeName_en_US='Delete' WHERE privilegeId='blueplanet:manage:device:delete';
UPDATE t_system_privilege SET privilegeName_en_US='Edit' WHERE privilegeId='blueplanet:manage:device:edit';
UPDATE t_system_privilege SET privilegeName_en_US='Query' WHERE privilegeId='blueplanet:manage:device:query';
UPDATE t_system_privilege SET privilegeName_en_US='Lacation point management' WHERE privilegeId='blueplanet:manage:location';

UPDATE t_system_privilege SET privilegeName_en_US='Delete' WHERE privilegeId='blueplanet:manage:location:delete';
UPDATE t_system_privilege SET privilegeName_en_US='Edit' WHERE privilegeId='blueplanet:manage:location:edit';
UPDATE t_system_privilege SET privilegeName_en_US='Query' WHERE privilegeId='blueplanet:manage:location:query';
UPDATE t_system_privilege SET privilegeName_en_US='Monitor' WHERE privilegeId='blueplanet:monitor';
UPDATE t_system_privilege SET privilegeName_en_US='Control panel' WHERE privilegeId='blueplanet:monitor:controlPanel';
UPDATE t_system_privilege SET privilegeName_en_US='Edit' WHERE privilegeId='blueplanet:monitor:controlPanel:edit';
UPDATE t_system_privilege SET privilegeName_en_US='Query' WHERE privilegeId='blueplanet:monitor:controlPanel:query';
UPDATE t_system_privilege SET privilegeName_en_US='Real-time data' WHERE privilegeId='blueplanet:monitor:realtimeData';
UPDATE t_system_privilege SET privilegeName_en_US='3D model management' WHERE privilegeId='blueplanet:monitor:threedimensional';
UPDATE t_system_privilege SET privilegeName_en_US='Delete' WHERE privilegeId='blueplanet:monitor:threedimensional:delete';

UPDATE t_system_privilege SET privilegeName_en_US='Edit' WHERE privilegeId='blueplanet:monitor:threedimensional:edit';
UPDATE t_system_privilege SET privilegeName_en_US='Analysis' WHERE privilegeId='blueplanet:monitor:threedimensional:look';
UPDATE t_system_privilege SET privilegeName_en_US='Browse' WHERE privilegeId='blueplanet:monitor:threedimensional:preview';
UPDATE t_system_privilege SET privilegeName_en_US='Network topology' WHERE privilegeId='blueplanet:monitor:topo';
UPDATE t_system_privilege SET privilegeName_en_US='Off-line data' WHERE privilegeId='blueplanet:offline';
UPDATE t_system_privilege SET privilegeName_en_US='Off-line data' WHERE privilegeId='blueplanet:offline:batch';
UPDATE t_system_privilege SET privilegeName_en_US='Batch add' WHERE privilegeId='blueplanet:offline:batch:add';
UPDATE t_system_privilege SET privilegeName_en_US='Batch list' WHERE privilegeId='blueplanet:offline:batch:list';
UPDATE t_system_privilege SET privilegeName_en_US='Batch update' WHERE privilegeId='blueplanet:offline:batch:update';
UPDATE t_system_privilege SET privilegeName_en_US='Data edit' WHERE privilegeId='blueplanet:offline:data:edit';

UPDATE t_system_privilege SET privilegeName_en_US='Plan' WHERE privilegeId='blueplanet:planImage';
UPDATE t_system_privilege SET privilegeName_en_US='Edit' WHERE privilegeId='blueplanet:planImage:edit';
UPDATE t_system_privilege SET privilegeName_en_US='Site' WHERE privilegeId='blueplanet:site';
UPDATE t_system_privilege SET privilegeName_en_US='Overview' WHERE privilegeId='blueplanet:site:index';
UPDATE t_system_privilege SET privilegeName_en_US='Statistical analysis' WHERE privilegeId='blueplanet:statistics:report';
UPDATE t_system_privilege SET privilegeName_en_US='Region' WHERE privilegeId='blueplanet:zone';
UPDATE t_system_privilege SET privilegeName_en_US='Location piont' WHERE privilegeId='blueplanet:zone:location';
UPDATE t_system_privilege SET privilegeName_en_US='Scalar field' WHERE privilegeId='blueplanet:zone:scalarFiled';
UPDATE t_system_privilege SET privilegeName_en_US='Alam threshold' WHERE privilegeId='blueplanet:zone:thresholdAlarm';
UPDATE t_system_privilege SET privilegeName_en_US='Wind field' WHERE privilegeId='blueplanet:zone:windFiled';
