--author: gaohui
--description: 修正 X/Y/Z方向裂隙 与 位移量 四个监测指标的公式参数

UPDATE `m_defaultcoefficient` SET defaultValue = '0'                     WHERE sensorPhysicalId = 76 AND paramName = 'a';
UPDATE `m_defaultcoefficient` SET defaultValue = '0.000001515197755317'  WHERE sensorPhysicalId = 76 AND paramName = 'b';
UPDATE `m_defaultcoefficient` SET defaultValue = '0'                     WHERE sensorPhysicalId = 76 AND paramName = 'c';

UPDATE `m_defaultcoefficient` SET defaultValue = '0'                     WHERE sensorPhysicalId = 77 AND paramName = 'a';
UPDATE `m_defaultcoefficient` SET defaultValue = '0.000001515197755317'  WHERE sensorPhysicalId = 77 AND paramName = 'b';
UPDATE `m_defaultcoefficient` SET defaultValue = '0'                     WHERE sensorPhysicalId = 77 AND paramName = 'c';

UPDATE `m_defaultcoefficient` SET defaultValue = '0'                     WHERE sensorPhysicalId = 78 AND paramName = 'a';
UPDATE `m_defaultcoefficient` SET defaultValue = '0.000001515197755317'  WHERE sensorPhysicalId = 78 AND paramName = 'b';
UPDATE `m_defaultcoefficient` SET defaultValue = '0'                     WHERE sensorPhysicalId = 78 AND paramName = 'c';

UPDATE `m_defaultcoefficient` SET defaultValue = '0'                     WHERE sensorPhysicalId = 79 AND paramName = 'a';
UPDATE `m_defaultcoefficient` SET defaultValue = '0.0003111004'          WHERE sensorPhysicalId = 79 AND paramName = 'b';
UPDATE `m_defaultcoefficient` SET defaultValue = '0.6857831575'          WHERE sensorPhysicalId = 79 AND paramName = 'c';
