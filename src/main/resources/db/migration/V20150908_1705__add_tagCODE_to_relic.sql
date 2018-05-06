--author lijianfei
--description 修改装箱单文物 totalCode --> relicId

UPDATE h_packing_relic pr SET pr.`totalCode`= (SELECT id FROM o_historical_relic r WHERE r.`totalCode`=pr.`totalCode`);
ALTER TABLE `h_packing_relic` CHANGE COLUMN totalCode relicId INT(11);
