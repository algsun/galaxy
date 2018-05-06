/*==============================================================*/
/* author li.jianfei						                                */
/* description 添加 Halley 系统表结构                           */
/*==============================================================*/


drop table if exists h_car;

drop table if exists h_config;

drop table if exists h_device;

drop table if exists h_exhibition;

drop table if exists h_exhibition_state;

drop table if exists h_packing_list;

drop table if exists h_packing_relic;

drop table if exists h_path;

drop table if exists h_user;

/*==============================================================*/
/* Table: h_car                                                 */
/*==============================================================*/
create table h_car
(
   id                   int not null auto_increment,
   exhibitionId         int,
   plateNumber          varchar(10) comment '车牌号',
   director             varchar(20) comment '责任人',
   directorPhone        varchar(11) comment '责任人联系方式',
   primary key (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='外展车辆表';

/*==============================================================*/
/* Table: h_config                                              */
/*==============================================================*/
create table h_config
(
   id                   int not null auto_increment,
   exhibitionId         int,
   alarmRange           int comment '报警范围',
   primary key (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='外展配置表';

/*==============================================================*/
/* Table: h_device                                              */
/*==============================================================*/
create table h_device
(
   id                   int not null auto_increment,
   carId                int,
   deviceType           int comment '1:摄像机设备。2：传感设备',
   deviceId             varchar(22),
   primary key (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='外展设备表';

/*==============================================================*/
/* Table: h_exhibition                                          */
/*==============================================================*/
create table h_exhibition
(
   id                   int not null auto_increment,
   startTime            datetime comment '外展记录创建时间',
   endTime              datetime comment '结束外展时间',
   outEventId           varchar(20) comment '出库事件记录ID',
   primary key (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='外展记录表';

/*==============================================================*/
/* Table: h_exhibition_state                                    */
/*==============================================================*/
create table h_exhibition_state
(
   id                   int not null auto_increment,
   exhibitionId         int comment '外展ID',
   state                int comment '1.开始外展 2.开始运输 3.结束运输 4.结束外展',
   changeTime           datetime,
   primary key (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='外展状态表';

/*==============================================================*/
/* Table: h_packing_list                                        */
/*==============================================================*/
create table h_packing_list
(
   id                   int not null auto_increment,
   sequence             int,
   carId                int,
   primary key (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='装箱记录表';

/*==============================================================*/
/* Table: h_packing_relic                                       */
/*==============================================================*/
create table h_packing_relic
(
   id                   int not null auto_increment,
   packingId            int,
   totalCode            varchar(20),
   primary key (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='装箱文物表';

/*==============================================================*/
/* Table: h_path                                                */
/*==============================================================*/
create table h_path
(
   id                   int not null auto_increment,
   exhibitionId         int,
   longitude            double,
   latitude             double,
   dataType             int comment '1：目的地 2：拖拽点',
   primary key (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='外展预设路线表';

alter table h_path comment '线路预设点坐标';

/*==============================================================*/
/* Table: h_user                                                */
/*==============================================================*/
create table h_user
(
   id                   int not null auto_increment,
   exhibitionId         int,
   userId               int,
   primary key (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='外展用户表';

alter table h_car add constraint FK_Reference_94 foreign key (exhibitionId)
      references h_exhibition (id) on delete restrict on update restrict;

alter table h_config add constraint FK_Reference_85 foreign key (exhibitionId)
      references h_exhibition (id) on delete restrict on update restrict;

alter table h_device add constraint FK_Reference_92 foreign key (carId)
      references h_car (id) on delete restrict on update restrict;

alter table h_exhibition add constraint FK_Reference_90 foreign key (outEventId)
      references o_out_event (id) on delete restrict on update restrict;

alter table h_exhibition_state add constraint FK_Reference_87 foreign key (exhibitionId)
      references h_exhibition (id) on delete restrict on update restrict;

alter table h_packing_list add constraint FK_Reference_93 foreign key (carId)
      references h_car (id) on delete restrict on update restrict;

alter table h_packing_relic add constraint FK_Reference_84 foreign key (packingId)
      references h_packing_list (id) on delete restrict on update restrict;

alter table h_path add constraint FK_Reference_88 foreign key (exhibitionId)
      references h_exhibition (id) on delete restrict on update restrict;

alter table h_user add constraint FK_Reference_86 foreign key (exhibitionId)
      references h_exhibition (id) on delete restrict on update restrict;


