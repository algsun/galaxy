-- author xie.deng
-- description 删除没用的存储过程、函数、视图
DROP PROCEDURE IF EXISTS  `sp_createViewByDate`;
DROP PROCEDURE IF EXISTS  `sp_datePeakAvgMath`;
DROP PROCEDURE IF EXISTS  `sp_dateRbMath`;
DROP PROCEDURE IF EXISTS  `sp_dateWindRoseMath`;
DROP PROCEDURE IF EXISTS  `sp_hourLuxMath`;
DROP PROCEDURE IF EXISTS  `sp_hourRbMath`;
DROP PROCEDURE IF EXISTS  `sp_reMathFlag`;
DROP VIEW IF EXISTS  `v_datewinddirection`;
DROP VIEW IF EXISTS  `v_datewindrosebase`;
DROP VIEW IF EXISTS  `v_datewindrosereal`;
DROP VIEW IF EXISTS  `v_datewindspeed`;
DROP VIEW IF EXISTS  `v_lux_day`;
DROP VIEW IF EXISTS  `v_lux_month`;
DROP VIEW IF EXISTS  `v_lux_season`;
DROP VIEW IF EXISTS  `v_lux_year`;
DROP VIEW IF EXISTS  `v_rb_month`;
DROP VIEW IF EXISTS  `v_rose_y`;
DROP VIEW IF EXISTS  `v_rose_y_m`;
DROP VIEW IF EXISTS  `v_rose_y_s`;
DROP FUNCTION IF EXISTS  `fun_directionRate`;
DROP FUNCTION IF EXISTS  `fun_getAvgGroupid`;
DROP FUNCTION IF EXISTS  `fun_getLuxIntegral`;