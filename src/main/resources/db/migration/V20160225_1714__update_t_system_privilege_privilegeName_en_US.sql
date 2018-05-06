-- author liuzhu
-- describe  t_system_privilege update privilegeName_en_US column

UPDATE t_system_privilege SET privilegeName_en_US='Crack monitoring' WHERE privilegeId='proxima:crack';
UPDATE t_system_privilege SET privilegeName_en_US='Position change chart' WHERE privilegeId='proxima:crack:positionChangeChart';
UPDATE t_system_privilege SET privilegeName_en_US='Trendgram' WHERE privilegeId='proxima:crack:trendChart';
UPDATE t_system_privilege SET privilegeName_en_US='Picture browse' WHERE privilegeId='proxima:dVImage';
UPDATE t_system_privilege SET privilegeName_en_US='Infrared image browse' WHERE privilegeId='proxima:dVImage:infrared';
UPDATE t_system_privilege SET privilegeName_en_US='Optical image browse' WHERE privilegeId='proxima:dVImage:optics';
UPDATE t_system_privilege SET privilegeName_en_US='Temperture change trendgram' WHERE privilegeId='proxima:dVImage:trendChart';
UPDATE t_system_privilege SET privilegeName_en_US='Camera' WHERE privilegeId='proxima:dVPlace';
UPDATE t_system_privilege SET privilegeName_en_US='Add infrared camera' WHERE privilegeId='proxima:dVPlace:addInfrared';
UPDATE t_system_privilege SET privilegeName_en_US='Add optical camera' WHERE privilegeId='proxima:dVPlace:addOptics';

UPDATE t_system_privilege SET privilegeName_en_US='Start\stop' WHERE privilegeId='proxima:dVPlace:enableInfrared';
UPDATE t_system_privilege SET privilegeName_en_US='Start\stop' WHERE privilegeId='proxima:dVPlace:enableOptics';
UPDATE t_system_privilege SET privilegeName_en_US='Camera list' WHERE privilegeId='proxima:dVPlace:list';
UPDATE t_system_privilege SET privilegeName_en_US='Modify' WHERE privilegeId='proxima:dVPlace:updateInfrared';
UPDATE t_system_privilege SET privilegeName_en_US='Modify' WHERE privilegeId='proxima:dVPlace:updateOptics';
UPDATE t_system_privilege SET privilegeName_en_US='FTP' WHERE privilegeId='proxima:ftp';
UPDATE t_system_privilege SET privilegeName_en_US='Add' WHERE privilegeId='proxima:ftp:add';
UPDATE t_system_privilege SET privilegeName_en_US='Delete' WHERE privilegeId='proxima:ftp:delete';
UPDATE t_system_privilege SET privilegeName_en_US='List' WHERE privilegeId='proxima:ftp:list';
UPDATE t_system_privilege SET privilegeName_en_US='Modify' WHERE privilegeId='proxima:ftp:update';
UPDATE t_system_privilege SET privilegeName_en_US='Home' WHERE privilegeId='proxima:home';
