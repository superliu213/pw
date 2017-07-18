-----------------------------------------------------
-- Export file for user ACC_CHENGDU_V1             --
-- Created by Administrator on 2017/7/11, 14:02:33 --
-----------------------------------------------------

spool ȫ���ű�.log

drop table SYS_FUNCTION;
drop table SYS_GROUP;
drop table SYS_GROUP_ROLE;
drop table SYS_LOG;
drop table SYS_ROLE;
drop table SYS_ROLE_FUNCTION;
drop table SYS_USER;
drop table SYS_USER_GROUP;
drop table SYS_USER_ROLE;

drop sequence S_SYS_FUNCTION;
drop sequence S_SYS_GROUP;
drop sequence S_SYS_GROUP_ROLE;
drop sequence S_SYS_LOG;
drop sequence S_SYS_ROLE;
drop sequence S_SYS_ROLE_FUNCTION;
drop sequence S_SYS_USER;
drop sequence S_SYS_USER_GROUP;
drop sequence S_SYS_USER_ROLE;


prompt
prompt Creating table SYS_FUNCTION
prompt ===========================
prompt
create table SYS_FUNCTION
(
  id                 NUMBER(19) not null,
  function_id        VARCHAR2(32) not null,
  function_name      VARCHAR2(32) not null,
  function_type      CHAR(1) not null,
  function_parent_id VARCHAR2(32),
  function_url       VARCHAR2(64),
  order_no           NUMBER(10) not null,
  function_logo      VARCHAR2(64),
  button_position    VARCHAR2(64),
  remark             VARCHAR2(200)
)
tablespace AFCLCTABLE
  pctfree 10
  initrans 1
  maxtrans 255
  storage
  (
    initial 64K
    next 1M
    minextents 1
    maxextents unlimited
  )
nologging;
comment on table SYS_FUNCTION
  is 'Ȩ����Ϣ��';
comment on column SYS_FUNCTION.id
  is '���';
comment on column SYS_FUNCTION.function_id
  is '�˵�����';
comment on column SYS_FUNCTION.function_name
  is '�˵�����';
comment on column SYS_FUNCTION.function_type
  is '�˵�����';
comment on column SYS_FUNCTION.function_parent_id
  is '���˵�����';
comment on column SYS_FUNCTION.function_url
  is '�˵�URL';
comment on column SYS_FUNCTION.order_no
  is '�������';
comment on column SYS_FUNCTION.function_logo
  is 'ͼ��';
comment on column SYS_FUNCTION.button_position
  is '��ťλ��';
comment on column SYS_FUNCTION.remark
  is '��ע';
alter table SYS_FUNCTION
  add constraint PK_SYS_FUNCTION primary key (ID)
  using index
  tablespace AFCLCTABLE
  pctfree 10
  initrans 2
  maxtrans 255
  storage
  (
    initial 64K
    next 1M
    minextents 1
    maxextents unlimited
  );

prompt
prompt Creating table SYS_GROUP
prompt ========================
prompt
create table SYS_GROUP
(
  id              NUMBER(19) not null,
  group_id        VARCHAR2(32) not null,
  group_name      VARCHAR2(32) not null,
  group_lever     CHAR(1) not null,
  group_parent_id VARCHAR2(32),
  order_no        NUMBER(10) not null,
  remark          VARCHAR2(200)
)
tablespace AFCLCTABLE
  pctfree 10
  initrans 1
  maxtrans 255
  storage
  (
    initial 64K
    next 1M
    minextents 1
    maxextents unlimited
  )
nologging;
comment on table SYS_GROUP
  is '������Ϣ��';
comment on column SYS_GROUP.id
  is '���';
comment on column SYS_GROUP.group_id
  is '��������';
comment on column SYS_GROUP.group_name
  is '��������';
comment on column SYS_GROUP.group_lever
  is '�������';
comment on column SYS_GROUP.group_parent_id
  is '����������';
comment on column SYS_GROUP.order_no
  is '�������';
comment on column SYS_GROUP.remark
  is '��ע';
alter table SYS_GROUP
  add constraint PK_SYS_GROUP primary key (ID)
  using index
  tablespace AFCLCTABLE
  pctfree 10
  initrans 2
  maxtrans 255
  storage
  (
    initial 64K
    next 1M
    minextents 1
    maxextents unlimited
  );

prompt
prompt Creating table SYS_GROUP_ROLE
prompt =============================
prompt
create table SYS_GROUP_ROLE
(
  id       NUMBER(19) not null,
  group_id VARCHAR2(32) not null,
  role_id  VARCHAR2(32) not null
)
tablespace AFCLCTABLE
  pctfree 10
  initrans 1
  maxtrans 255
  storage
  (
    initial 64K
    next 1M
    minextents 1
    maxextents unlimited
  )
nologging;
comment on table SYS_GROUP_ROLE
  is '�û�������Ӧ��';
comment on column SYS_GROUP_ROLE.id
  is '���';
comment on column SYS_GROUP_ROLE.group_id
  is '��������';
comment on column SYS_GROUP_ROLE.role_id
  is '��ɫ����';
alter table SYS_GROUP_ROLE
  add constraint PK_SYS_GROUP_ROLE primary key (ID)
  using index
  tablespace AFCLCTABLE
  pctfree 10
  initrans 2
  maxtrans 255
  storage
  (
    initial 64K
    next 1M
    minextents 1
    maxextents unlimited
  );

prompt
prompt Creating table SYS_LOG
prompt ======================
prompt
create table SYS_LOG
(
  id          NUMBER(19) not null,
  occur_time  TIMESTAMP(6) not null,
  operator_id VARCHAR2(32) not null,
  log_type    CHAR(1) not null,
  log_level   CHAR(1) not null,
  log_desc    VARCHAR2(200),
  ip_address  VARCHAR2(32)
)
tablespace AFCLCTABLE
  pctfree 10
  initrans 1
  maxtrans 255
  storage
  (
    initial 64K
    next 1M
    minextents 1
    maxextents unlimited
  )
nologging;
comment on table SYS_LOG
  is 'ϵͳ��־��';
comment on column SYS_LOG.id
  is '���';
comment on column SYS_LOG.occur_time
  is '����ʱ��';
comment on column SYS_LOG.operator_id
  is '����Ա';
comment on column SYS_LOG.log_type
  is '��־����';
comment on column SYS_LOG.log_level
  is '��־�ȼ�';
comment on column SYS_LOG.log_desc
  is '��־����';
comment on column SYS_LOG.ip_address
  is '����ԱIP';
alter table SYS_LOG
  add constraint PK_SYS_LOG primary key (ID)
  using index
  tablespace AFCLCTABLE
  pctfree 10
  initrans 2
  maxtrans 255
  storage
  (
    initial 64K
    next 1M
    minextents 1
    maxextents unlimited
  );

prompt
prompt Creating table SYS_ROLE
prompt =======================
prompt
create table SYS_ROLE
(
  id        NUMBER(19) not null,
  role_id   VARCHAR2(32) not null,
  role_desc VARCHAR2(32) not null,
  remark    VARCHAR2(200)
)
tablespace AFCLCTABLE
  pctfree 10
  initrans 1
  maxtrans 255
  storage
  (
    initial 64K
    next 1M
    minextents 1
    maxextents unlimited
  )
nologging;
comment on table SYS_ROLE
  is '��ɫ��Ϣ��';
comment on column SYS_ROLE.id
  is '���';
comment on column SYS_ROLE.role_id
  is '��ɫ����';
comment on column SYS_ROLE.role_desc
  is '��ɫ����';
comment on column SYS_ROLE.remark
  is '��ע';
alter table SYS_ROLE
  add constraint PK_SYS_ROLE primary key (ID)
  using index
  tablespace AFCLCTABLE
  pctfree 10
  initrans 2
  maxtrans 255
  storage
  (
    initial 64K
    next 1M
    minextents 1
    maxextents unlimited
  );

prompt
prompt Creating table SYS_ROLE_FUNCTION
prompt ================================
prompt
create table SYS_ROLE_FUNCTION
(
  id          NUMBER(19) not null,
  role_id     VARCHAR2(32) not null,
  function_id VARCHAR2(32) not null
)
tablespace AFCLCTABLE
  pctfree 10
  initrans 1
  maxtrans 255
  storage
  (
    initial 64K
    next 1M
    minextents 1
    maxextents unlimited
  )
nologging;
comment on table SYS_ROLE_FUNCTION
  is '��ɫȨ�޶�Ӧ��';
comment on column SYS_ROLE_FUNCTION.id
  is '���';
comment on column SYS_ROLE_FUNCTION.role_id
  is '��ɫ����';
comment on column SYS_ROLE_FUNCTION.function_id
  is '�˵�����';
alter table SYS_ROLE_FUNCTION
  add constraint PK_SYS_ROLE_FUNCTION primary key (ID)
  using index
  tablespace AFCLCTABLE
  pctfree 10
  initrans 2
  maxtrans 255
  storage
  (
    initial 64K
    next 1M
    minextents 1
    maxextents unlimited
  );

prompt
prompt Creating table SYS_USER
prompt =======================
prompt
create table SYS_USER
(
  id                   NUMBER(19) not null,
  user_id              VARCHAR2(32) not null,
  user_pass_word       VARCHAR2(32) not null,
  user_name            VARCHAR2(32) not null,
  user_telephone       VARCHAR2(32),
  user_email           VARCHAR2(32),
  user_birthday        DATE,
  user_id_card         VARCHAR2(64),
  if_valid             CHAR(1) not null,
  user_validity_period DATE,
  pw_validity_period   DATE,
  remark               VARCHAR2(200)
)
tablespace AFCLCTABLE
  pctfree 10
  initrans 1
  maxtrans 255
  storage
  (
    initial 64K
    next 1M
    minextents 1
    maxextents unlimited
  )
nologging;
comment on table SYS_USER
  is '�û���Ϣ��';
comment on column SYS_USER.id
  is '���';
comment on column SYS_USER.user_id
  is '�û�����';
comment on column SYS_USER.user_pass_word
  is '�û�����';
comment on column SYS_USER.user_name
  is '�û�����';
comment on column SYS_USER.user_telephone
  is '�绰����';
comment on column SYS_USER.user_email
  is '�û�����';
comment on column SYS_USER.user_birthday
  is '��������';
comment on column SYS_USER.user_id_card
  is '���֤��';
comment on column SYS_USER.if_valid
  is '�Ƿ���Ч:0-��Ч 1-��Ч';
comment on column SYS_USER.user_validity_period
  is '�û���Ч��';
comment on column SYS_USER.pw_validity_period
  is '������Ч��';
comment on column SYS_USER.remark
  is '��ע';
alter table SYS_USER
  add constraint PK_SYS_USER primary key (ID)
  using index
  tablespace AFCLCTABLE
  pctfree 10
  initrans 2
  maxtrans 255
  storage
  (
    initial 64K
    next 1M
    minextents 1
    maxextents unlimited
  );

prompt
prompt Creating table SYS_USER_GROUP
prompt =============================
prompt
create table SYS_USER_GROUP
(
  id       NUMBER(19) not null,
  user_id  VARCHAR2(32) not null,
  group_id VARCHAR2(32) not null
)
tablespace AFCLCTABLE
  pctfree 10
  initrans 1
  maxtrans 255
  storage
  (
    initial 64K
    next 1M
    minextents 1
    maxextents unlimited
  )
nologging;
comment on table SYS_USER_GROUP
  is '�û�������Ӧ��';
comment on column SYS_USER_GROUP.id
  is '���';
comment on column SYS_USER_GROUP.user_id
  is '�û�����';
comment on column SYS_USER_GROUP.group_id
  is '��������';
alter table SYS_USER_GROUP
  add constraint PK_SYS_USER_GROUP primary key (ID)
  using index
  tablespace AFCLCTABLE
  pctfree 10
  initrans 2
  maxtrans 255
  storage
  (
    initial 64K
    next 1M
    minextents 1
    maxextents unlimited
  );

prompt
prompt Creating table SYS_USER_ROLE
prompt ============================
prompt
create table SYS_USER_ROLE
(
  id      NUMBER(19) not null,
  user_id VARCHAR2(32) not null,
  role_id VARCHAR2(32) not null
)
tablespace AFCLCTABLE
  pctfree 10
  initrans 1
  maxtrans 255
  storage
  (
    initial 64K
    next 1M
    minextents 1
    maxextents unlimited
  )
nologging;
comment on table SYS_USER_ROLE
  is '�û���ɫ��Ӧ��';
comment on column SYS_USER_ROLE.id
  is '���';
comment on column SYS_USER_ROLE.user_id
  is '�û�����';
comment on column SYS_USER_ROLE.role_id
  is '��ɫ����';
alter table SYS_USER_ROLE
  add constraint PK_SYS_USER_ROLE primary key (ID)
  using index
  tablespace AFCLCTABLE
  pctfree 10
  initrans 2
  maxtrans 255
  storage
  (
    initial 64K
    next 1M
    minextents 1
    maxextents unlimited
  );

prompt
prompt Creating sequence S_SYS_FUNCTION
prompt ================================
prompt
create sequence S_SYS_FUNCTION
minvalue 1
maxvalue 999999999999999999999999999
start with 1
increment by 1
cache 20;

prompt
prompt Creating sequence S_SYS_GROUP
prompt =============================
prompt
create sequence S_SYS_GROUP
minvalue 1
maxvalue 999999999999999999999999999
start with 1
increment by 1
cache 20;

prompt
prompt Creating sequence S_SYS_GROUP_ROLE
prompt ==================================
prompt
create sequence S_SYS_GROUP_ROLE
minvalue 1
maxvalue 999999999999999999999999999
start with 1
increment by 1
cache 20;

prompt
prompt Creating sequence S_SYS_LOG
prompt ===========================
prompt
create sequence S_SYS_LOG
minvalue 1
maxvalue 999999999999999999999999999
start with 1
increment by 1
cache 20;

prompt
prompt Creating sequence S_SYS_ROLE
prompt ============================
prompt
create sequence S_SYS_ROLE
minvalue 1
maxvalue 999999999999999999999999999
start with 1
increment by 1
cache 20;

prompt
prompt Creating sequence S_SYS_ROLE_FUNCTION
prompt =====================================
prompt
create sequence S_SYS_ROLE_FUNCTION
minvalue 1
maxvalue 999999999999999999999999999
start with 1
increment by 1
cache 20;

prompt
prompt Creating sequence S_SYS_USER
prompt ============================
prompt
create sequence S_SYS_USER
minvalue 1
maxvalue 999999999999999999999999999
start with 1
increment by 1
cache 20;

prompt
prompt Creating sequence S_SYS_USER_GROUP
prompt ==================================
prompt
create sequence S_SYS_USER_GROUP
minvalue 1
maxvalue 999999999999999999999999999
start with 1
increment by 1
cache 20;

prompt
prompt Creating sequence S_SYS_USER_ROLE
prompt =================================
prompt
create sequence S_SYS_USER_ROLE
minvalue 1
maxvalue 999999999999999999999999999
start with 1
increment by 1
cache 20;

INSERT INTO SYS_FUNCTION (ID, FUNCTION_ID, FUNCTION_NAME, FUNCTION_TYPE, FUNCTION_PARENT_ID, FUNCTION_URL, ORDER_NO, FUNCTION_LOGO, BUTTON_POSITION, REMARK) VALUES (151, '1', 'Ȩ�޹���', '1', '-1', '#', 1, 'fa-diamond', null, '1');
INSERT INTO SYS_FUNCTION (ID, FUNCTION_ID, FUNCTION_NAME, FUNCTION_TYPE, FUNCTION_PARENT_ID, FUNCTION_URL, ORDER_NO, FUNCTION_LOGO, BUTTON_POSITION, REMARK) VALUES (152, '2', '�������', '1', '-1', '#', 2, 'fa-bar-chart', null, '1');
INSERT INTO SYS_FUNCTION (ID, FUNCTION_ID, FUNCTION_NAME, FUNCTION_TYPE, FUNCTION_PARENT_ID, FUNCTION_URL, ORDER_NO, FUNCTION_LOGO, BUTTON_POSITION, REMARK) VALUES (153, '3', 'ϵͳ����', '1', '-1', '#', 3, 'fa-money', null, '1');
INSERT INTO SYS_FUNCTION (ID, FUNCTION_ID, FUNCTION_NAME, FUNCTION_TYPE, FUNCTION_PARENT_ID, FUNCTION_URL, ORDER_NO, FUNCTION_LOGO, BUTTON_POSITION, REMARK) VALUES (154, '4', '��־����', '1', '-1', '#', 4, 'fa-map-o', null, '1');
INSERT INTO SYS_FUNCTION (ID, FUNCTION_ID, FUNCTION_NAME, FUNCTION_TYPE, FUNCTION_PARENT_ID, FUNCTION_URL, ORDER_NO, FUNCTION_LOGO, BUTTON_POSITION, REMARK) VALUES (155, '5', '�û�����', '2', '1', './modules/authority/user/user.html', 101, null, null, '1');
INSERT INTO SYS_FUNCTION (ID, FUNCTION_ID, FUNCTION_NAME, FUNCTION_TYPE, FUNCTION_PARENT_ID, FUNCTION_URL, ORDER_NO, FUNCTION_LOGO, BUTTON_POSITION, REMARK) VALUES (156, '6', '��ɫ����', '2', '1', './modules/authority/role/role.html', 102, null, null, '1');
INSERT INTO SYS_FUNCTION (ID, FUNCTION_ID, FUNCTION_NAME, FUNCTION_TYPE, FUNCTION_PARENT_ID, FUNCTION_URL, ORDER_NO, FUNCTION_LOGO, BUTTON_POSITION, REMARK) VALUES (157, '7', '��������', '2', '1', './modules/authority/group/group.html', 103, null, null, '1');
INSERT INTO SYS_FUNCTION (ID, FUNCTION_ID, FUNCTION_NAME, FUNCTION_TYPE, FUNCTION_PARENT_ID, FUNCTION_URL, ORDER_NO, FUNCTION_LOGO, BUTTON_POSITION, REMARK) VALUES (158, '8', 'Ȩ�޹���', '2', '1', './modules/authority/function/function.html', 104, null, null, '1');
INSERT INTO SYS_FUNCTION (ID, FUNCTION_ID, FUNCTION_NAME, FUNCTION_TYPE, FUNCTION_PARENT_ID, FUNCTION_URL, ORDER_NO, FUNCTION_LOGO, BUTTON_POSITION, REMARK) VALUES (159, '9', 'demo', '2', '2', './modules/report/demo/report.html', 201, null, null, '1');
INSERT INTO SYS_FUNCTION (ID, FUNCTION_ID, FUNCTION_NAME, FUNCTION_TYPE, FUNCTION_PARENT_ID, FUNCTION_URL, ORDER_NO, FUNCTION_LOGO, BUTTON_POSITION, REMARK) VALUES (160, '10', '���ܼ��', '2', '3', '/monitoring', 301, null, null, '1');
INSERT INTO SYS_FUNCTION (ID, FUNCTION_ID, FUNCTION_NAME, FUNCTION_TYPE, FUNCTION_PARENT_ID, FUNCTION_URL, ORDER_NO, FUNCTION_LOGO, BUTTON_POSITION, REMARK) VALUES (161, '11', '���ݿ���', '2', '3', '/proxooladmin', 302, null, null, '1');
INSERT INTO SYS_FUNCTION (ID, FUNCTION_ID, FUNCTION_NAME, FUNCTION_TYPE, FUNCTION_PARENT_ID, FUNCTION_URL, ORDER_NO, FUNCTION_LOGO, BUTTON_POSITION, REMARK) VALUES (162, '12', '��־��ѯ', '2', '4', './modules/log/log/log.html', 401, null, null, '1');
INSERT INTO SYS_FUNCTION (ID, FUNCTION_ID, FUNCTION_NAME, FUNCTION_TYPE, FUNCTION_PARENT_ID, FUNCTION_URL, ORDER_NO, FUNCTION_LOGO, BUTTON_POSITION, REMARK) VALUES (163, '13', '���', '3', '5', null, 501, null, 'userForm.add_table', '1');
INSERT INTO SYS_FUNCTION (ID, FUNCTION_ID, FUNCTION_NAME, FUNCTION_TYPE, FUNCTION_PARENT_ID, FUNCTION_URL, ORDER_NO, FUNCTION_LOGO, BUTTON_POSITION, REMARK) VALUES (164, '14', '�༭', '3', '5', null, 502, null, 'userForm.update_table', '1');
INSERT INTO SYS_FUNCTION (ID, FUNCTION_ID, FUNCTION_NAME, FUNCTION_TYPE, FUNCTION_PARENT_ID, FUNCTION_URL, ORDER_NO, FUNCTION_LOGO, BUTTON_POSITION, REMARK) VALUES (165, '15', 'ɾ��', '3', '5', null, 503, null, 'userForm.remove_table', '1');
INSERT INTO SYS_FUNCTION (ID, FUNCTION_ID, FUNCTION_NAME, FUNCTION_TYPE, FUNCTION_PARENT_ID, FUNCTION_URL, ORDER_NO, FUNCTION_LOGO, BUTTON_POSITION, REMARK) VALUES (166, '16', '��������', '3', '5', null, 504, null, 'userForm.password_reset', '1');
INSERT INTO SYS_FUNCTION (ID, FUNCTION_ID, FUNCTION_NAME, FUNCTION_TYPE, FUNCTION_PARENT_ID, FUNCTION_URL, ORDER_NO, FUNCTION_LOGO, BUTTON_POSITION, REMARK) VALUES (167, '17', '����', '3', '5', null, 505, null, 'userForm.import_table', '1');
INSERT INTO SYS_FUNCTION (ID, FUNCTION_ID, FUNCTION_NAME, FUNCTION_TYPE, FUNCTION_PARENT_ID, FUNCTION_URL, ORDER_NO, FUNCTION_LOGO, BUTTON_POSITION, REMARK) VALUES (168, '18', '����', '3', '5', null, 506, null, 'userForm.export_table', '1');
INSERT INTO SYS_FUNCTION (ID, FUNCTION_ID, FUNCTION_NAME, FUNCTION_TYPE, FUNCTION_PARENT_ID, FUNCTION_URL, ORDER_NO, FUNCTION_LOGO, BUTTON_POSITION, REMARK) VALUES (169, '19', '���û���', '3', '5', null, 507, null, 'userForm.configure_group', '1');
INSERT INTO SYS_FUNCTION (ID, FUNCTION_ID, FUNCTION_NAME, FUNCTION_TYPE, FUNCTION_PARENT_ID, FUNCTION_URL, ORDER_NO, FUNCTION_LOGO, BUTTON_POSITION, REMARK) VALUES (170, '20', '���ý�ɫ', '3', '5', null, 508, null, 'userForm.configure_role', '1');
INSERT INTO SYS_FUNCTION (ID, FUNCTION_ID, FUNCTION_NAME, FUNCTION_TYPE, FUNCTION_PARENT_ID, FUNCTION_URL, ORDER_NO, FUNCTION_LOGO, BUTTON_POSITION, REMARK) VALUES (171, '21', '��ѯ', '3', '5', null, 509, null, 'userForm.query_table', '1');
INSERT INTO SYS_FUNCTION (ID, FUNCTION_ID, FUNCTION_NAME, FUNCTION_TYPE, FUNCTION_PARENT_ID, FUNCTION_URL, ORDER_NO, FUNCTION_LOGO, BUTTON_POSITION, REMARK) VALUES (172, '22', '����', '3', '5', null, 510, null, 'userForm.reset_form', '1');
INSERT INTO SYS_FUNCTION (ID, FUNCTION_ID, FUNCTION_NAME, FUNCTION_TYPE, FUNCTION_PARENT_ID, FUNCTION_URL, ORDER_NO, FUNCTION_LOGO, BUTTON_POSITION, REMARK) VALUES (173, '23', '���ݳ�ʼ��', '3', '5', null, 511, null, 'userForm.reset_data', '1');
INSERT INTO SYS_FUNCTION (ID, FUNCTION_ID, FUNCTION_NAME, FUNCTION_TYPE, FUNCTION_PARENT_ID, FUNCTION_URL, ORDER_NO, FUNCTION_LOGO, BUTTON_POSITION, REMARK) VALUES (174, '24', '���', '3', '6', null, 601, null, 'roleForm.add_table', '1');
INSERT INTO SYS_FUNCTION (ID, FUNCTION_ID, FUNCTION_NAME, FUNCTION_TYPE, FUNCTION_PARENT_ID, FUNCTION_URL, ORDER_NO, FUNCTION_LOGO, BUTTON_POSITION, REMARK) VALUES (175, '25', '�༭', '3', '6', null, 602, null, 'roleForm.update_table', '1');
INSERT INTO SYS_FUNCTION (ID, FUNCTION_ID, FUNCTION_NAME, FUNCTION_TYPE, FUNCTION_PARENT_ID, FUNCTION_URL, ORDER_NO, FUNCTION_LOGO, BUTTON_POSITION, REMARK) VALUES (176, '26', 'ɾ��', '3', '6', null, 603, null, 'roleForm.remove_table', '1');
INSERT INTO SYS_FUNCTION (ID, FUNCTION_ID, FUNCTION_NAME, FUNCTION_TYPE, FUNCTION_PARENT_ID, FUNCTION_URL, ORDER_NO, FUNCTION_LOGO, BUTTON_POSITION, REMARK) VALUES (177, '27', '����', '3', '6', null, 604, null, 'roleForm.import_table', '1');
INSERT INTO SYS_FUNCTION (ID, FUNCTION_ID, FUNCTION_NAME, FUNCTION_TYPE, FUNCTION_PARENT_ID, FUNCTION_URL, ORDER_NO, FUNCTION_LOGO, BUTTON_POSITION, REMARK) VALUES (178, '28', '����', '3', '6', null, 604, null, 'roleForm.export_table', '1');
INSERT INTO SYS_FUNCTION (ID, FUNCTION_ID, FUNCTION_NAME, FUNCTION_TYPE, FUNCTION_PARENT_ID, FUNCTION_URL, ORDER_NO, FUNCTION_LOGO, BUTTON_POSITION, REMARK) VALUES (179, '29', '����Ȩ��', '3', '6', null, 605, null, 'roleForm.configure_function', '1');
INSERT INTO SYS_FUNCTION (ID, FUNCTION_ID, FUNCTION_NAME, FUNCTION_TYPE, FUNCTION_PARENT_ID, FUNCTION_URL, ORDER_NO, FUNCTION_LOGO, BUTTON_POSITION, REMARK) VALUES (180, '30', '��ѯ', '3', '6', null, 606, null, 'roleForm.query_table', '1');
INSERT INTO SYS_FUNCTION (ID, FUNCTION_ID, FUNCTION_NAME, FUNCTION_TYPE, FUNCTION_PARENT_ID, FUNCTION_URL, ORDER_NO, FUNCTION_LOGO, BUTTON_POSITION, REMARK) VALUES (181, '31', '����', '3', '6', null, 607, null, 'roleForm.reset_form', '1');
INSERT INTO SYS_FUNCTION (ID, FUNCTION_ID, FUNCTION_NAME, FUNCTION_TYPE, FUNCTION_PARENT_ID, FUNCTION_URL, ORDER_NO, FUNCTION_LOGO, BUTTON_POSITION, REMARK) VALUES (182, '32', '���ݳ�ʼ��', '3', '6', null, 608, null, 'roleForm.reset_data', '1');
INSERT INTO SYS_FUNCTION (ID, FUNCTION_ID, FUNCTION_NAME, FUNCTION_TYPE, FUNCTION_PARENT_ID, FUNCTION_URL, ORDER_NO, FUNCTION_LOGO, BUTTON_POSITION, REMARK) VALUES (183, '33', '���', '3', '7', null, 701, null, 'groupForm.add_table', '1');
INSERT INTO SYS_FUNCTION (ID, FUNCTION_ID, FUNCTION_NAME, FUNCTION_TYPE, FUNCTION_PARENT_ID, FUNCTION_URL, ORDER_NO, FUNCTION_LOGO, BUTTON_POSITION, REMARK) VALUES (184, '34', '�༭', '3', '7', null, 702, null, 'groupForm.update_table', '1');
INSERT INTO SYS_FUNCTION (ID, FUNCTION_ID, FUNCTION_NAME, FUNCTION_TYPE, FUNCTION_PARENT_ID, FUNCTION_URL, ORDER_NO, FUNCTION_LOGO, BUTTON_POSITION, REMARK) VALUES (185, '35', 'ɾ��', '3', '7', null, 703, null, 'groupForm.remove_table', '1');
INSERT INTO SYS_FUNCTION (ID, FUNCTION_ID, FUNCTION_NAME, FUNCTION_TYPE, FUNCTION_PARENT_ID, FUNCTION_URL, ORDER_NO, FUNCTION_LOGO, BUTTON_POSITION, REMARK) VALUES (186, '36', '���ý�ɫ', '3', '7', null, 704, null, 'groupForm.configure_function', '1');
INSERT INTO SYS_FUNCTION (ID, FUNCTION_ID, FUNCTION_NAME, FUNCTION_TYPE, FUNCTION_PARENT_ID, FUNCTION_URL, ORDER_NO, FUNCTION_LOGO, BUTTON_POSITION, REMARK) VALUES (187, '37', '��ѯ', '3', '7', null, 705, null, 'groupForm.query_table', '1');
INSERT INTO SYS_FUNCTION (ID, FUNCTION_ID, FUNCTION_NAME, FUNCTION_TYPE, FUNCTION_PARENT_ID, FUNCTION_URL, ORDER_NO, FUNCTION_LOGO, BUTTON_POSITION, REMARK) VALUES (188, '38', '����', '3', '7', null, 706, null, 'groupForm.reset_form', '1');
INSERT INTO SYS_FUNCTION (ID, FUNCTION_ID, FUNCTION_NAME, FUNCTION_TYPE, FUNCTION_PARENT_ID, FUNCTION_URL, ORDER_NO, FUNCTION_LOGO, BUTTON_POSITION, REMARK) VALUES (189, '39', '���ݳ�ʼ��', '3', '7', null, 707, null, 'groupForm.reset_data', '1');
INSERT INTO SYS_FUNCTION (ID, FUNCTION_ID, FUNCTION_NAME, FUNCTION_TYPE, FUNCTION_PARENT_ID, FUNCTION_URL, ORDER_NO, FUNCTION_LOGO, BUTTON_POSITION, REMARK) VALUES (190, '40', '���', '3', '8', null, 801, null, 'functionForm.add_table', '1');
INSERT INTO SYS_FUNCTION (ID, FUNCTION_ID, FUNCTION_NAME, FUNCTION_TYPE, FUNCTION_PARENT_ID, FUNCTION_URL, ORDER_NO, FUNCTION_LOGO, BUTTON_POSITION, REMARK) VALUES (191, '41', '�༭', '3', '8', null, 802, null, 'functionForm.update_table', '1');
INSERT INTO SYS_FUNCTION (ID, FUNCTION_ID, FUNCTION_NAME, FUNCTION_TYPE, FUNCTION_PARENT_ID, FUNCTION_URL, ORDER_NO, FUNCTION_LOGO, BUTTON_POSITION, REMARK) VALUES (192, '42', 'ɾ��', '3', '8', null, 803, null, 'functionForm.remove_table', '1');
INSERT INTO SYS_FUNCTION (ID, FUNCTION_ID, FUNCTION_NAME, FUNCTION_TYPE, FUNCTION_PARENT_ID, FUNCTION_URL, ORDER_NO, FUNCTION_LOGO, BUTTON_POSITION, REMARK) VALUES (193, '43', '��ѯ', '3', '8', null, 804, null, 'functionForm.query_table', '1');
INSERT INTO SYS_FUNCTION (ID, FUNCTION_ID, FUNCTION_NAME, FUNCTION_TYPE, FUNCTION_PARENT_ID, FUNCTION_URL, ORDER_NO, FUNCTION_LOGO, BUTTON_POSITION, REMARK) VALUES (194, '44', '����', '3', '8', null, 805, null, 'functionForm.reset_form', '1');
INSERT INTO SYS_FUNCTION (ID, FUNCTION_ID, FUNCTION_NAME, FUNCTION_TYPE, FUNCTION_PARENT_ID, FUNCTION_URL, ORDER_NO, FUNCTION_LOGO, BUTTON_POSITION, REMARK) VALUES (195, '45', '���ݳ�ʼ��', '3', '8', null, 806, null, 'functionForm.reset_data', '1');
INSERT INTO SYS_FUNCTION (ID, FUNCTION_ID, FUNCTION_NAME, FUNCTION_TYPE, FUNCTION_PARENT_ID, FUNCTION_URL, ORDER_NO, FUNCTION_LOGO, BUTTON_POSITION, REMARK) VALUES (196, '46', '��ѯ', '3', '9', null, 901, null, 'reportForm.query_table', '1');
INSERT INTO SYS_FUNCTION (ID, FUNCTION_ID, FUNCTION_NAME, FUNCTION_TYPE, FUNCTION_PARENT_ID, FUNCTION_URL, ORDER_NO, FUNCTION_LOGO, BUTTON_POSITION, REMARK) VALUES (197, '47', '����', '3', '9', null, 902, null, 'reportForm.reset_form', '1');
INSERT INTO SYS_FUNCTION (ID, FUNCTION_ID, FUNCTION_NAME, FUNCTION_TYPE, FUNCTION_PARENT_ID, FUNCTION_URL, ORDER_NO, FUNCTION_LOGO, BUTTON_POSITION, REMARK) VALUES (198, '48', '��ѯ', '3', '12', null, 1201, null, 'logForm.query_table', '1');
INSERT INTO SYS_FUNCTION (ID, FUNCTION_ID, FUNCTION_NAME, FUNCTION_TYPE, FUNCTION_PARENT_ID, FUNCTION_URL, ORDER_NO, FUNCTION_LOGO, BUTTON_POSITION, REMARK) VALUES (199, '49', '����', '3', '12', null, 1203, null, 'logForm.reset_form', '1');
INSERT INTO SYS_FUNCTION (ID, FUNCTION_ID, FUNCTION_NAME, FUNCTION_TYPE, FUNCTION_PARENT_ID, FUNCTION_URL, ORDER_NO, FUNCTION_LOGO, BUTTON_POSITION, REMARK) VALUES (200, '50', '���ݳ�ʼ��', '3', '12', null, 1203, null, 'logForm.reset_data', '1');


INSERT INTO SYS_GROUP (ID, GROUP_ID, GROUP_NAME, GROUP_LEVER, GROUP_PARENT_ID, ORDER_NO, REMARK) VALUES (85, '3', '��վ2', '2', '1', 2, null);
INSERT INTO SYS_GROUP (ID, GROUP_ID, GROUP_NAME, GROUP_LEVER, GROUP_PARENT_ID, ORDER_NO, REMARK) VALUES (83, '1', '1����', '1', null, 1, null);
INSERT INTO SYS_GROUP (ID, GROUP_ID, GROUP_NAME, GROUP_LEVER, GROUP_PARENT_ID, ORDER_NO, REMARK) VALUES (84, '2', '��վ1', '2', '1', 1, null);

INSERT INTO SYS_GROUP_ROLE (ID, GROUP_ID, ROLE_ID) VALUES (3, '2', '2');
INSERT INTO SYS_GROUP_ROLE (ID, GROUP_ID, ROLE_ID) VALUES (9, '1', '1');

INSERT INTO SYS_ROLE (ID, ROLE_ID, ROLE_DESC, REMARK) VALUES (4, '0', '��������Ա', '1');
INSERT INTO SYS_ROLE (ID, ROLE_ID, ROLE_DESC, REMARK) VALUES (5, '1', 'һ����', '2');

INSERT INTO SYS_ROLE_FUNCTION (ID, ROLE_ID, FUNCTION_ID) VALUES (2154, '1', '16');
INSERT INTO SYS_ROLE_FUNCTION (ID, ROLE_ID, FUNCTION_ID) VALUES (2155, '1', '17');
INSERT INTO SYS_ROLE_FUNCTION (ID, ROLE_ID, FUNCTION_ID) VALUES (2156, '1', '18');
INSERT INTO SYS_ROLE_FUNCTION (ID, ROLE_ID, FUNCTION_ID) VALUES (2157, '1', '22');
INSERT INTO SYS_ROLE_FUNCTION (ID, ROLE_ID, FUNCTION_ID) VALUES (2158, '1', '23');
INSERT INTO SYS_ROLE_FUNCTION (ID, ROLE_ID, FUNCTION_ID) VALUES (2159, '1', '24');
INSERT INTO SYS_ROLE_FUNCTION (ID, ROLE_ID, FUNCTION_ID) VALUES (2160, '1', '26');
INSERT INTO SYS_ROLE_FUNCTION (ID, ROLE_ID, FUNCTION_ID) VALUES (2161, '1', '36');
INSERT INTO SYS_ROLE_FUNCTION (ID, ROLE_ID, FUNCTION_ID) VALUES (2162, '1', '27');
INSERT INTO SYS_ROLE_FUNCTION (ID, ROLE_ID, FUNCTION_ID) VALUES (2163, '1', '28');
INSERT INTO SYS_ROLE_FUNCTION (ID, ROLE_ID, FUNCTION_ID) VALUES (2164, '1', '29');
INSERT INTO SYS_ROLE_FUNCTION (ID, ROLE_ID, FUNCTION_ID) VALUES (2165, '1', '30');
INSERT INTO SYS_ROLE_FUNCTION (ID, ROLE_ID, FUNCTION_ID) VALUES (2166, '1', '31');
INSERT INTO SYS_ROLE_FUNCTION (ID, ROLE_ID, FUNCTION_ID) VALUES (2167, '1', '32');
INSERT INTO SYS_ROLE_FUNCTION (ID, ROLE_ID, FUNCTION_ID) VALUES (2168, '1', '33');
INSERT INTO SYS_ROLE_FUNCTION (ID, ROLE_ID, FUNCTION_ID) VALUES (2169, '1', '37');
INSERT INTO SYS_ROLE_FUNCTION (ID, ROLE_ID, FUNCTION_ID) VALUES (2170, '1', '38');
INSERT INTO SYS_ROLE_FUNCTION (ID, ROLE_ID, FUNCTION_ID) VALUES (2171, '1', '39');
INSERT INTO SYS_ROLE_FUNCTION (ID, ROLE_ID, FUNCTION_ID) VALUES (2172, '1', '40');
INSERT INTO SYS_ROLE_FUNCTION (ID, ROLE_ID, FUNCTION_ID) VALUES (2173, '1', '42');
INSERT INTO SYS_ROLE_FUNCTION (ID, ROLE_ID, FUNCTION_ID) VALUES (2174, '1', '43');
INSERT INTO SYS_ROLE_FUNCTION (ID, ROLE_ID, FUNCTION_ID) VALUES (2175, '1', '44');
INSERT INTO SYS_ROLE_FUNCTION (ID, ROLE_ID, FUNCTION_ID) VALUES (2176, '1', '45');
INSERT INTO SYS_ROLE_FUNCTION (ID, ROLE_ID, FUNCTION_ID) VALUES (2177, '1', '46');
INSERT INTO SYS_ROLE_FUNCTION (ID, ROLE_ID, FUNCTION_ID) VALUES (2178, '1', '48');
INSERT INTO SYS_ROLE_FUNCTION (ID, ROLE_ID, FUNCTION_ID) VALUES (2179, '1', '49');
INSERT INTO SYS_ROLE_FUNCTION (ID, ROLE_ID, FUNCTION_ID) VALUES (2180, '1', '51');
INSERT INTO SYS_ROLE_FUNCTION (ID, ROLE_ID, FUNCTION_ID) VALUES (2181, '1', '52');
INSERT INTO SYS_ROLE_FUNCTION (ID, ROLE_ID, FUNCTION_ID) VALUES (2182, '1', '53');
INSERT INTO SYS_ROLE_FUNCTION (ID, ROLE_ID, FUNCTION_ID) VALUES (2183, '1', '54');
INSERT INTO SYS_ROLE_FUNCTION (ID, ROLE_ID, FUNCTION_ID) VALUES (2184, '1', '55');
INSERT INTO SYS_ROLE_FUNCTION (ID, ROLE_ID, FUNCTION_ID) VALUES (2185, '1', '56');
INSERT INTO SYS_ROLE_FUNCTION (ID, ROLE_ID, FUNCTION_ID) VALUES (2186, '1', '58');
INSERT INTO SYS_ROLE_FUNCTION (ID, ROLE_ID, FUNCTION_ID) VALUES (2187, '1', '59');
INSERT INTO SYS_ROLE_FUNCTION (ID, ROLE_ID, FUNCTION_ID) VALUES (2188, '1', '61');
INSERT INTO SYS_ROLE_FUNCTION (ID, ROLE_ID, FUNCTION_ID) VALUES (2189, '1', '62');
INSERT INTO SYS_ROLE_FUNCTION (ID, ROLE_ID, FUNCTION_ID) VALUES (2190, '1', '64');
INSERT INTO SYS_ROLE_FUNCTION (ID, ROLE_ID, FUNCTION_ID) VALUES (2191, '1', '65');
INSERT INTO SYS_ROLE_FUNCTION (ID, ROLE_ID, FUNCTION_ID) VALUES (2192, '1', '67');
INSERT INTO SYS_ROLE_FUNCTION (ID, ROLE_ID, FUNCTION_ID) VALUES (2193, '1', '1');
INSERT INTO SYS_ROLE_FUNCTION (ID, ROLE_ID, FUNCTION_ID) VALUES (2194, '1', '19');
INSERT INTO SYS_ROLE_FUNCTION (ID, ROLE_ID, FUNCTION_ID) VALUES (2195, '1', '20');
INSERT INTO SYS_ROLE_FUNCTION (ID, ROLE_ID, FUNCTION_ID) VALUES (2196, '1', '21');
INSERT INTO SYS_ROLE_FUNCTION (ID, ROLE_ID, FUNCTION_ID) VALUES (2197, '1', '25');
INSERT INTO SYS_ROLE_FUNCTION (ID, ROLE_ID, FUNCTION_ID) VALUES (2198, '1', '34');
INSERT INTO SYS_ROLE_FUNCTION (ID, ROLE_ID, FUNCTION_ID) VALUES (2199, '1', '41');
INSERT INTO SYS_ROLE_FUNCTION (ID, ROLE_ID, FUNCTION_ID) VALUES (2200, '1', '47');
INSERT INTO SYS_ROLE_FUNCTION (ID, ROLE_ID, FUNCTION_ID) VALUES (2201, '1', '7');
INSERT INTO SYS_ROLE_FUNCTION (ID, ROLE_ID, FUNCTION_ID) VALUES (2202, '1', '8');
INSERT INTO SYS_ROLE_FUNCTION (ID, ROLE_ID, FUNCTION_ID) VALUES (2203, '1', '9');
INSERT INTO SYS_ROLE_FUNCTION (ID, ROLE_ID, FUNCTION_ID) VALUES (2204, '1', '10');
INSERT INTO SYS_ROLE_FUNCTION (ID, ROLE_ID, FUNCTION_ID) VALUES (2205, '1', '2');
INSERT INTO SYS_ROLE_FUNCTION (ID, ROLE_ID, FUNCTION_ID) VALUES (2206, '1', '50');
INSERT INTO SYS_ROLE_FUNCTION (ID, ROLE_ID, FUNCTION_ID) VALUES (2207, '1', '11');
INSERT INTO SYS_ROLE_FUNCTION (ID, ROLE_ID, FUNCTION_ID) VALUES (2208, '1', '3');
INSERT INTO SYS_ROLE_FUNCTION (ID, ROLE_ID, FUNCTION_ID) VALUES (2209, '1', '57');
INSERT INTO SYS_ROLE_FUNCTION (ID, ROLE_ID, FUNCTION_ID) VALUES (2210, '1', '12');
INSERT INTO SYS_ROLE_FUNCTION (ID, ROLE_ID, FUNCTION_ID) VALUES (2211, '1', '4');
INSERT INTO SYS_ROLE_FUNCTION (ID, ROLE_ID, FUNCTION_ID) VALUES (2212, '1', '60');
INSERT INTO SYS_ROLE_FUNCTION (ID, ROLE_ID, FUNCTION_ID) VALUES (2213, '1', '13');
INSERT INTO SYS_ROLE_FUNCTION (ID, ROLE_ID, FUNCTION_ID) VALUES (2214, '1', '5');
INSERT INTO SYS_ROLE_FUNCTION (ID, ROLE_ID, FUNCTION_ID) VALUES (2215, '1', '63');
INSERT INTO SYS_ROLE_FUNCTION (ID, ROLE_ID, FUNCTION_ID) VALUES (2216, '1', '14');
INSERT INTO SYS_ROLE_FUNCTION (ID, ROLE_ID, FUNCTION_ID) VALUES (2217, '1', '6');
INSERT INTO SYS_ROLE_FUNCTION (ID, ROLE_ID, FUNCTION_ID) VALUES (2218, '1', '66');
INSERT INTO SYS_ROLE_FUNCTION (ID, ROLE_ID, FUNCTION_ID) VALUES (2219, '1', '15');

INSERT INTO SYS_USER (ID, USER_ID, USER_PASS_WORD, USER_NAME, USER_TELEPHONE, USER_EMAIL, USER_BIRTHDAY, USER_ID_CARD, IF_VALID, USER_VALIDITY_PERIOD, PW_VALIDITY_PERIOD, REMARK) VALUES (36, 'admin', 'admin', '��������Ա', '123456789', 'liuliu@163.com', TIMESTAMP '2017-06-29 00:00:00', '111111', '1', TIMESTAMP '2017-06-29 00:00:00', TIMESTAMP '2017-06-29 00:00:00', '1');
INSERT INTO SYS_USER (ID, USER_ID, USER_PASS_WORD, USER_NAME, USER_TELEPHONE, USER_EMAIL, USER_BIRTHDAY, USER_ID_CARD, IF_VALID, USER_VALIDITY_PERIOD, PW_VALIDITY_PERIOD, REMARK) VALUES (37, '1', '1', '2', '123456789', 'liuliu@163.com', TIMESTAMP '2017-06-29 00:00:00', '111111', '1', null, TIMESTAMP '2017-06-29 00:00:00', '1');

INSERT INTO SYS_USER_GROUP (ID, USER_ID, GROUP_ID) VALUES (180, '1', '1');
INSERT INTO SYS_USER_GROUP (ID, USER_ID, GROUP_ID) VALUES (181, '1', '2');
INSERT INTO SYS_USER_GROUP (ID, USER_ID, GROUP_ID) VALUES (175, 'admin', '3');
INSERT INTO SYS_USER_GROUP (ID, USER_ID, GROUP_ID) VALUES (176, 'admin', '1');
INSERT INTO SYS_USER_GROUP (ID, USER_ID, GROUP_ID) VALUES (177, 'admin', '2');

INSERT INTO SYS_USER_ROLE (ID, USER_ID, ROLE_ID) VALUES (63, '1', '1');
INSERT INTO SYS_USER_ROLE (ID, USER_ID, ROLE_ID) VALUES (68, 'admin', '1');
INSERT INTO SYS_USER_ROLE (ID, USER_ID, ROLE_ID) VALUES (64, '1', '2');

commit;

spool off
