-- author li.jianfei
-- description 添加 t_sys_log logTime 索引

ALTER TABLE t_sys_logs
  ADD  INDEX IDX_T_SYS_LOG_LogTime (logTime);
ALTER TABLE t_sys_logs
  ADD  INDEX idx_t_sys_log_userEmail (userEmail);


ALTER TABLE p_pictures
  ADD INDEX idx_p_picture_picCreateTime(picCreateTime);
