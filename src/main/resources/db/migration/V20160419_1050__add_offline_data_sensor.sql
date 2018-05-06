--author li.jianfei
--description 添加离线数据监测指标

DELETE FROM m_sensorinfo WHERE sensorPhysicalid BETWEEN 98 AND 109;
INSERT INTO `m_sensorinfo` (`sensorPhysicalid`, `en_name`, `cn_name`, `sensorPrecision`, `units`, `positions`, `isActive`, `showType`)
select 98,'UV','紫外',2,'μW/lm',0,1,0 from dual union
select 99,'Noise','噪声',0,'dB',0,1,0 from dual union
select 100,'HCOOH（O3）','甲酸（臭氧）',2,'μg/m³',0,1,0 from dual union
select 101,'CH3COOH（O3）','乙酸（臭氧）',2,'μg/m³',0,1,0 from dual union
select 102,'O3','臭氧',2,'μg/m³',0,1,0 from dual union
select 103,'HCOOH','甲酸',2,'μg/m³',0,1,0 from dual union
select 104,'CH3COOH','乙酸',2,'μg/m³',0,1,0 from dual union
select 105,'NH3','氨气',2,'μg/m³',0,1,0 from dual union
select 106,'SO2','二氧化硫',2,'μg/m³',0,1,0 from dual union
select 107,'NO2','二氧化碳',2,'μg/m³',0,1,0 from dual union
select 108,'HCHO','甲醛',2,'μg/m³',0,1,0 from dual union
select 109,'F+','氟离子',2,'μg/m³',0,1,0 from dual;


DELETE FROM m_formula_sensor WHERE sensor_id BETWEEN 98 AND 109;
INSERT INTO m_formula_sensor(sensor_id, formula_id, sign_type)
SELECT 98, 1, 1 FROM dual UNION
SELECT 99, 1, 1 FROM dual UNION
SELECT 100, 1, 1 FROM dual UNION
SELECT 101, 1, 1 FROM dual UNION
SELECT 102, 1, 1 FROM dual UNION
SELECT 103, 1, 1 FROM dual UNION
SELECT 104, 1, 1 FROM dual UNION
SELECT 105, 1, 1 FROM dual UNION
SELECT 106, 1, 1 FROM dual UNION
SELECT 107, 1, 1 FROM dual UNION
SELECT 108, 1, 1 FROM dual UNION
SELECT 109, 1, 1 FROM dual;

DELETE FROM m_formula_param WHERE sensor_id BETWEEN 98 AND 109;
INSERT INTO m_formula_param(sensor_id, name, value)
SELECT 98, 'a', 1 FROM dual UNION
SELECT 99, 'a', 1 FROM dual UNION
SELECT 100, 'a', 1 FROM dual UNION
SELECT 101, 'a', 1 FROM dual UNION
SELECT 102, 'a', 1 FROM dual UNION
SELECT 103, 'a', 1 FROM dual UNION
SELECT 104, 'a', 1 FROM dual UNION
SELECT 105, 'a', 1 FROM dual UNION
SELECT 106, 'a', 1 FROM dual UNION
SELECT 107, 'a', 1 FROM dual UNION
SELECT 108, 'a', 1 FROM dual UNION
SELECT 109, 'a', 1 FROM dual UNION
SELECT 98, 'b', 0 FROM dual UNION
SELECT 99, 'b', 0 FROM dual UNION
SELECT 100, 'b', 0 FROM dual UNION
SELECT 101, 'b', 0 FROM dual UNION
SELECT 102, 'b', 0 FROM dual UNION
SELECT 103, 'b', 0 FROM dual UNION
SELECT 104, 'b', 0 FROM dual UNION
SELECT 105, 'b', 0 FROM dual UNION
SELECT 106, 'b', 0 FROM dual UNION
SELECT 107, 'b', 0 FROM dual UNION
SELECT 108, 'b', 0 FROM dual UNION
SELECT 109, 'b', 0 FROM dual;





