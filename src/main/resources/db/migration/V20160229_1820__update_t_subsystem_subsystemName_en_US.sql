-- author chen.yaoFei ;
-- description update t_subsystem table subsystemName_en_US column;

UPDATE t_subsystem SET subsystemName_en_US='System management' WHERE subsystemId=1;
UPDATE t_subsystem SET subsystemName_en_US='Environmental monitoring' WHERE subsystemId=2;
UPDATE t_subsystem SET subsystemName_en_US='Ontology monitoring' WHERE subsystemId=3;
UPDATE t_subsystem SET subsystemName_en_US='Asset management' WHERE subsystemId=4;
UPDATE t_subsystem SET subsystemName_en_US='Personal management' WHERE subsystemId=5;

UPDATE t_subsystem SET subsystemName_en_US='Data analysis' WHERE subsystemId=6;
UPDATE t_subsystem SET subsystemName_en_US='Outreach management' WHERE subsystemId=8;
UPDATE t_subsystem SET subsystemName_en_US='Regional center' WHERE subsystemId=9;
UPDATE t_subsystem SET subsystemName_en_US='Archives management' WHERE subsystemId=10;
UPDATE t_subsystem SET subsystemName_en_US='Achievement exhibition' WHERE subsystemId=11;