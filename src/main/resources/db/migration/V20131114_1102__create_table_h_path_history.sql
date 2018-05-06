-- author li.jianfei
-- description 添加车辆运行线路(历史数据)表

drop table if exists h_route_history;

/*==============================================================*/
/* Table: h_route_history                                       */
/*==============================================================*/
create table h_route_history
(
   id                   int not null auto_increment comment '自增ID',
   carId                int not null comment '外展车辆ID',
   longitude            double not null comment '经度',
   latitude             double not null comment '纬度',
   time                 datetime not null comment '上传时间',
   primary key (id)
);

alter table h_route_history comment '记录车辆运行线路(历史数据)';

alter table h_route_history add constraint FK_Reference_89 foreign key (carId)
      references h_car (id) on delete restrict on update restrict;


