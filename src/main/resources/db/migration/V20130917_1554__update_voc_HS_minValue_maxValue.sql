-- author xie.deng
-- description 修改voc高灵敏度的最大值和最小值
UPDATE m_sensorinfo SET `minValue`=0 ,`maxValue`=50000 WHERE sensorPhysicalid = 83