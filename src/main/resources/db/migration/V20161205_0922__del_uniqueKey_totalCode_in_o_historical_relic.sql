-- author:bai.weixing
-- description: 历史文物表删除总登记号唯一约束

DROP PROCEDURE IF EXISTS del_idx;
DELIMITER $
CREATE PROCEDURE del_idx(IN p_tablename VARCHAR(200), IN p_idxname VARCHAR(20))
  BEGIN
    DECLARE str VARCHAR(250);
    SET @str=CONCAT(' drop index ',p_idxname,' on ',p_tablename);

    SELECT COUNT(*) INTO @cnt FROM information_schema.statistics WHERE  table_schema=DATABASE() AND table_name=p_tablename AND index_name=p_idxname ;
    IF @cnt >0 THEN
      PREPARE stmt FROM @str;
      EXECUTE stmt ;
    END IF;

  END
$
DELIMITER ;
CALL del_idx('o_historical_relic','totalCode');
DROP PROCEDURE IF EXISTS del_idx;

