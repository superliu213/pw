package com.springapp.common.taskserver;

import java.util.Calendar;


public interface GlobalTaskConstant {

    /**
     * 触发类型
     */
    public enum TriggerType {
        CYCLE(0) {
            @Override
            public String toString() {
                return "循环执行";
            }
        },
        ONCE(1) {
            @Override
            public String toString() {
                return "单次执行";
            }
        },
        IMMEDIATELY(2) {
            @Override
            public String toString() {
                return "立即执行";
            }
        };

        private short type;

        private TriggerType(int type) {
            this.type = (short) type;
        }

        public short getType() {
            return this.type;
        }

        public static String[] getNames() {
            TriggerType[] valueArray = values();
            int length = valueArray.length;
            String[] names = new String[length];
            for (int i = 0; i < length; i++) {
                names[i] = valueArray[i].toString();
            }
            return names;
        }

        public static TriggerType getTriggerType(int type) {
            for (TriggerType triggerType : values()) {
                if (triggerType.getType() == type) {
                    return triggerType;
                }
            }
            return null;
        }
    }

    public final static String UNIT_TYPE_SECOND = "second";

    public final static String UNIT_TYPE_MINUTE = "minute";

    public final static String UNIT_TYPE_HOUR = "hour";

    public final static String UNIT_TYPE_DAY = "day";

    public final static String UNIT_TYPE_WEEK = "week";

    public final static String UNIT_TYPE_MONTH = "month";

    public final static String UNIT_TYPE_YEAR = "year";

    /**
     * 触发器状态
     */
    public enum TriggerStatus {
        UNAVALIABLE(0) {
            @Override
            public String toString() {
                return "触发器未启用";
            }
        },
        AVALIABLE(1) {
            @Override
            public String toString() {
                return "触发器已启用";
            }
        };
        private short status;

        private TriggerStatus(int status) {
            this.status = (short) status;
        }

        public short getStatus() {
            return this.status;
        }

        public static String[] getNames() {
            TriggerStatus[] valueArray = values();
            int length = valueArray.length;
            String[] names = new String[length];
            for (int i = 0; i < length; i++) {
                names[i] = valueArray[i].toString();
            }
            return names;
        }

        public static String getStatus(int status) {
            for (TriggerStatus tStatus : values()) {
                if (tStatus.getStatus() == (short) status) {
                    return tStatus.toString();
                }
            }
            return null;
        }
    }

    /**
     * 任务运行方式
     */
    public enum RunWay {
        RANDOM(0) {
            @Override
            public String toString() {
                return "随机运行";
            }
        },
        APPOIND(1) {
            @Override
            public String toString() {
                return "指定服务器运行";
            }
        };
        private short runWay;

        private RunWay(int runWay) {
            this.runWay = (short) runWay;
        }

        public short getRunWay() {
            return this.runWay;
        }

        public static String[] getNames() {
        	RunWay[] valueArray = values();
            int length = valueArray.length;
            String[] names = new String[length];
            for (int i = 0; i < length; i++) {
                names[i] = valueArray[i].toString();
            }
            return names;
        }

        public static String getRunWay(int runWay) {
            for (RunWay tRunWay : values()) {
                if (tRunWay.getRunWay() == (short) runWay) {
                    return tRunWay.toString();
                }
            }
            return null;
        }
    }
    
    public enum TriggerExec {
        FAILURE(0) {
            @Override
            public String toString() {
                return "未进行调度";
            }
        },
        SUCCESS(1) {
            @Override
            public String toString() {
                return "执行成功";
            }
        },
        EXECUTE_FAILED(2) {
            @Override
            public String toString() {
                return "触发成功，但执行失败";
            }
        },
        EXECUTING(3) {
            @Override
            public String toString() {
                return "执行中";
            }
        },
        PAUSE(4) {
            @Override
            public String toString() {
                return "暂停中";
            }
        },
        DUPLICATED(5) {
            @Override
            public String toString() {
                return "重复执行";
            }
        },
        INTERRUPT(6) {
            @Override
            public String toString() {
                return "中断";
            }
        };

        private int status;

        private TriggerExec(int status) {
            this.status = status;
        }

        public short getStatus() {
            return (short) status;
        }

        public static TriggerExec getTriggerExec(int status) {
            for (TriggerExec exec : values()) {
                if (exec.getStatus() == (short) status) {
                    return exec;
                }
            }
            return null;
        }
    }

    /**
     * 任务执行结果
     */
    // 触发失败，显示“未进行调度”
    public final static short TRIGGER_FAILURE = 0;

    // 触发成功，但执行成功，显示“执行成功”
    public final static short TRIGGER_EXECUTE_SUCCESS = 1;

    // 触发成功，但执行失败，显示“执行失败”
    public final static short TRIGGER_SUCCESS_EXECUTE_FAILED = 2;

    // 正在运行触发器，显示“执行中”
    public final static short TRIGGER_EXECUTING = 3;

    // 正在运行触发器，显示“暂停中”
    public final static short TRIGGER_PAUSE = 4;

    // 重复执行
    public final static short TRIGGER_EXECUTING_DUPLICATED = 5;

    // 触发成功，但执行成功，显示“中断”
    public final static short TRIGGER_SUCCESS_EXECUTE_INTERRUPT = 6;

    /**
     * 默认的超时时间
     */
    public final static long DEFALUT_TASK_TIMEOUT = 5 * 1000;

    public enum CalendarType {
        SECOND(Calendar.SECOND), MINUTE(Calendar.MINUTE), HOUR(
                Calendar.HOUR_OF_DAY), DAY(Calendar.DAY_OF_MONTH), MONTH(
                Calendar.MONTH), WEEK(Calendar.WEEK_OF_YEAR), YEAR(
                Calendar.YEAR);
        private int value;

        private CalendarType(int value) {
            this.value = value;
        }

        public int getType() {
            return value;
        }

        public static int getType(String name) {
            for (CalendarType calendarType : values()) {
                if (calendarType.toString().equals(name)) {
                    return calendarType.getType();
                }
            }
            return 0;
        }
    }
}
