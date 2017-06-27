package com.yisingle.webapp.job;

import org.quartz.*;

import java.util.Date;
import java.util.Map;

public class QuartzManager {
    private Scheduler scheduler;

    public QuartzManager(Scheduler scheduler) {
        this.scheduler = scheduler;
    }

    /**
     * 添加任务
     *
     * @param jobName  任务名
     * @param jobGroup 任务组
     * @param jobClass 任务实现类
     * @param jobData  任务数据
     * @return
     */
    public Boolean addJob(String jobName, String jobGroup, Class<? extends Job> jobClass, Map<String, Object> jobData) {
        try {
            // 任务名，任务组，任务执行类
            JobDetail jobDetail = JobBuilder.newJob(jobClass).withIdentity(jobName, jobGroup).build();
            if (jobData != null && jobData.size() > 0)
                jobDetail.getJobDataMap().putAll(jobData);

            // 如果任务已经存在，则抛出异常
            scheduler.addJob(jobDetail, false, true);
            return true;
        } catch (SchedulerException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 添加任务
     *
     * @param jobName     任务名
     * @param jobGroup    任务组
     * @param jobClass    任务实现类
     * @param jobData     任务数据
     * @param triggerCron 触发器Cron表示式
     * @return
     */
    public Boolean addJob(String jobName, String jobGroup, Class<? extends Job> jobClass, Map<String, Object> jobData, String triggerCron) {
        String triggerName = "TRIGGER-" + jobName;
        String triggerGroup = "TRIGGER-GROUP-" + jobGroup;
        return addJob(jobName, jobGroup, jobClass, jobData, triggerName, triggerGroup, triggerCron);
    }

    /**
     * 添加任务
     *
     * @param jobName      任务名
     * @param jobGroup     任务组
     * @param jobClass     任务实现类
     * @param jobData      任务数据
     * @param triggerName  触发器名
     * @param triggerGroup 触发器组
     * @param triggerCron  触发器Cron表示式
     * @return
     */
    public Boolean addJob(String jobName, String jobGroup, Class<? extends Job> jobClass, Map<String, Object> jobData, String triggerName, String triggerGroup, String triggerCron) {
        try {
            // 任务名，任务组，任务执行类
            JobDetail jobDetail = JobBuilder.newJob(jobClass).withIdentity(jobName, jobGroup).build();
            if (jobData != null && jobData.size() > 0)
                jobDetail.getJobDataMap().putAll(jobData);

            // 触发器
            TriggerBuilder<Trigger> triggerBuilder = TriggerBuilder.newTrigger();
            // 触发器名,触发器组
            triggerBuilder.withIdentity(triggerName, triggerGroup);
            // 触发器时间设定
            triggerBuilder.withSchedule(CronScheduleBuilder.cronSchedule(triggerCron));
            // 创建Trigger对象
            CronTrigger trigger = (CronTrigger) triggerBuilder.build();

            // 调度容器设置JobDetail和Trigger
            scheduler.scheduleJob(jobDetail, trigger);
            return true;
        } catch (SchedulerException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 添加任务
     *
     * @param jobName         任务名
     * @param jobGroup        任务组
     * @param jobClass        任务实现类
     * @param jobData         任务数据
     * @param triggerInterval 触发器间隔（毫秒）
     * @param triggerRepeat   触发器次数
     * @return
     */
    public Boolean addJob(String jobName, String jobGroup, Class<? extends Job> jobClass, Map<String, Object> jobData, Long triggerInterval, Integer triggerRepeat) {
        String triggerName = "TRIGGER-" + jobName;
        String triggerGroup = "TRIGGER-GROUP-" + jobGroup;
        return addJob(jobName, jobGroup, jobClass, jobData, triggerName, triggerGroup, triggerInterval, triggerRepeat);
    }

    /**
     * 添加任务
     *
     * @param jobName         任务名
     * @param jobGroup        任务组
     * @param jobClass        任务实现类
     * @param jobData         任务数据
     * @param triggerName     触发器名
     * @param triggerGroup    触发器组
     * @param triggerInterval 触发器间隔（毫秒）
     * @param triggerRepeat   触发器重复执行次数
     * @return
     */
    public Boolean addJob(String jobName, String jobGroup, Class<? extends Job> jobClass, Map<String, Object> jobData, String triggerName, String triggerGroup, Long triggerInterval, Integer triggerRepeat) {
        try {
            // 任务名，任务组，任务执行类
            JobDetail jobDetail = JobBuilder.newJob(jobClass).withIdentity(jobName, jobGroup).build();
            if (jobData != null && jobData.size() > 0)
                jobDetail.getJobDataMap().putAll(jobData);

            // 触发器
            TriggerBuilder<Trigger> triggerBuilder = TriggerBuilder.newTrigger();
            // 触发器名,触发器组
            triggerBuilder.withIdentity(triggerName, triggerGroup);
            // 触发器时间设定
            triggerBuilder.withSchedule(SimpleScheduleBuilder.simpleSchedule().withIntervalInMilliseconds(triggerInterval).withRepeatCount(triggerRepeat));

            Date date = new Date(new Date().getTime() + triggerInterval);
            triggerBuilder.startAt(date);

            // 创建Trigger对象
            SimpleTrigger trigger = (SimpleTrigger) triggerBuilder.build();


            // 调度容器设置JobDetail和Trigger
            // 如果任务已经存在，则抛出异常
            scheduler.scheduleJob(jobDetail, trigger);
            return true;
        } catch (SchedulerException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 添加触发器
     *
     * @param jobName     任务名
     * @param jobGroup    任务组
     * @param triggerCron 触发器Cron表示式
     * @return
     */
    public Boolean addTrigger(String jobName, String jobGroup, String triggerCron) {
        String triggerName = "TRIGGER-" + jobName;
        String triggerGroup = "TRIGGER-GROUP-" + jobGroup;
        return addTrigger(jobName, jobGroup, triggerName, triggerGroup, triggerCron);
    }

    /**
     * 添加触发器
     *
     * @param jobName      任务名
     * @param jobGroup     任务组
     * @param triggerName  触发器名
     * @param triggerGroup 触发器组
     * @param triggerCron  触发器Cron表示式
     * @return
     */
    public Boolean addTrigger(String jobName, String jobGroup, String triggerName, String triggerGroup, String triggerCron) {
        try {
            JobKey jobKey = JobKey.jobKey(jobName, jobGroup);
            JobDetail jobDetail = scheduler.getJobDetail(jobKey);
            if (jobDetail == null)
                return false;

            // 触发器
            TriggerBuilder<Trigger> triggerBuilder = TriggerBuilder.newTrigger();
            // 触发器名,触发器组
            triggerBuilder.withIdentity(triggerName, triggerGroup);
            // 触发器时间设定
            triggerBuilder.withSchedule(CronScheduleBuilder.cronSchedule(triggerCron));
            // 创建Trigger对象
            CronTrigger trigger = (CronTrigger) triggerBuilder.build();

            // 调度容器设置Trigger
            scheduler.scheduleJob(jobDetail, trigger);
            return true;
        } catch (SchedulerException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 添加触发器
     *
     * @param jobName         任务名
     * @param jobGroup        任务组
     * @param triggerInterval 触发器间隔（毫秒）
     * @param triggerRepeat   触发器重复执行次数
     * @return
     */
    public Boolean addTrigger(String jobName, String jobGroup, Long triggerInterval, Integer triggerRepeat) {
        String triggerName = "TRIGGER-" + jobName;
        String triggerGroup = "TRIGGER-GROUP-" + jobGroup;
        return addTrigger(jobName, jobGroup, triggerName, triggerGroup, triggerInterval, triggerRepeat);
    }

    /**
     * 添加触发器
     *
     * @param jobName         任务名
     * @param jobGroup        任务组
     * @param triggerName     触发器名
     * @param triggerGroup    触发器组
     * @param triggerInterval 触发器间隔（毫秒）
     * @param triggerRepeat   触发器重复执行次数
     * @return
     */
    public Boolean addTrigger(String jobName, String jobGroup, String triggerName, String triggerGroup, Long triggerInterval, Integer triggerRepeat) {
        try {
            JobKey jobKey = JobKey.jobKey(jobName, jobGroup);
            JobDetail jobDetail = scheduler.getJobDetail(jobKey);
            if (jobDetail == null)
                return false;

            // 触发器
            TriggerBuilder<Trigger> triggerBuilder = TriggerBuilder.newTrigger();
            // 触发器名,触发器组
            triggerBuilder.withIdentity(triggerName, triggerGroup);
            // 触发器时间设定
            triggerBuilder.withSchedule(SimpleScheduleBuilder.simpleSchedule().withIntervalInMilliseconds(triggerInterval).withRepeatCount(triggerRepeat));
            // 创建Trigger对象
            SimpleTrigger trigger = (SimpleTrigger) triggerBuilder.build();

            // 调度容器设置Trigger
            scheduler.scheduleJob(jobDetail, trigger);
            return true;
        } catch (SchedulerException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 修改任务
     *
     * @param jobName  任务名
     * @param jobGroup 任务组
     * @param jobData  任务数据
     * @return
     */
    public Boolean modifyJob(String jobName, String jobGroup, Map<String, Object> jobData) {
        try {
            JobKey jobKey = JobKey.jobKey(jobName, jobGroup);
            JobDetail jobDetail = scheduler.getJobDetail(jobKey);
            if (jobDetail == null)
                return false;

            jobDetail.getJobDataMap().clear();
            if (jobData != null && jobData.size() > 0)
                jobDetail.getJobDataMap().putAll(jobData);

            // 替换原来的任务
            scheduler.addJob(jobDetail, true, true);
            return true;
        } catch (SchedulerException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 修改触发器
     *
     * @param jobName     任务名
     * @param jobGroup    任务组
     * @param triggerCron 触发器Cron表示式
     * @return
     */
    public Boolean modifyTrigger(String jobName, String jobGroup, String triggerCron) {
        String triggerName = "TRIGGER-" + jobName;
        String triggerGroup = "TRIGGER-GROUP-" + jobGroup;
        return modifyTrigger(jobName, jobGroup, triggerName, triggerGroup, triggerCron);
    }

    /**
     * 修改触发器
     *
     * @param jobName      任务名
     * @param jobGroup     任务组
     * @param triggerName  触发器名
     * @param triggerGroup 触发器组
     * @param triggerCron  触发器Cron表示式
     * @return
     */
    public Boolean modifyTrigger(String jobName, String jobGroup, String triggerName, String triggerGroup, String triggerCron) {
        try {
            TriggerKey triggerKey = TriggerKey.triggerKey(triggerName, triggerGroup);
            CronTrigger trigger = (CronTrigger) scheduler.getTrigger(triggerKey);
            if (trigger == null)
                return false;

            if (trigger.getCronExpression().equalsIgnoreCase(triggerCron))
                return true;

            // 触发器
            TriggerBuilder<Trigger> triggerBuilder = TriggerBuilder.newTrigger();
            // 触发器名,触发器组
            triggerBuilder.withIdentity(triggerName, triggerGroup);
            // 触发器时间设定
            triggerBuilder.withSchedule(CronScheduleBuilder.cronSchedule(triggerCron));
            // 创建Trigger对象
            trigger = (CronTrigger) triggerBuilder.build();
            // 更新触发器到调度容器中
            scheduler.rescheduleJob(triggerKey, trigger);
            return true;
        } catch (SchedulerException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 修改触发器
     *
     * @param jobName         任务名
     * @param jobGroup        任务组
     * @param triggerInterval 触发器间隔（毫秒）
     * @param triggerRepeat   触发器重复执行次数
     * @return
     */
    public Boolean modifyTrigger(String jobName, String jobGroup, Long triggerInterval, Integer triggerRepeat) {
        String triggerName = "TRIGGER-" + jobName;
        String triggerGroup = "TRIGGER-GROUP-" + jobGroup;
        return modifyTrigger(jobName, jobGroup, triggerName, triggerGroup, triggerInterval, triggerRepeat);
    }

    /**
     * 修改触发器
     *
     * @param jobName         任务名
     * @param jobGroup        任务组
     * @param triggerName     触发器名
     * @param triggerGroup    触发器组
     * @param triggerInterval 触发器间隔（毫秒）
     * @param triggerRepeat   触发器重复执行次数
     * @return
     */
    public Boolean modifyTrigger(String jobName, String jobGroup, String triggerName, String triggerGroup, Long triggerInterval, Integer triggerRepeat) {
        try {
            TriggerKey triggerKey = TriggerKey.triggerKey(triggerName, triggerGroup);
            SimpleTrigger trigger = (SimpleTrigger) scheduler.getTrigger(triggerKey);
            if (trigger == null)
                return false;

            if (trigger.getRepeatInterval() == triggerInterval && trigger.getRepeatCount() == triggerRepeat)
                return true;

            // 触发器
            TriggerBuilder<Trigger> triggerBuilder = TriggerBuilder.newTrigger();
            // 触发器名,触发器组
            triggerBuilder.withIdentity(triggerName, triggerGroup);
            // 触发器时间设定
            triggerBuilder.withSchedule(SimpleScheduleBuilder.simpleSchedule().withIntervalInMilliseconds(triggerInterval).withRepeatCount(triggerRepeat));
            // 创建Trigger对象
            trigger = (SimpleTrigger) triggerBuilder.build();
            // 更新触发器到调度容器中
            scheduler.rescheduleJob(triggerKey, trigger);
            return true;
        } catch (SchedulerException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 是否存在任务
     *
     * @param jobName  任务名
     * @param jobGroup 任务组
     * @return
     */
    public Boolean hasJob(String jobName, String jobGroup) {
        try {
            JobKey jobKey = JobKey.jobKey(jobName, jobGroup);
            return scheduler.checkExists(jobKey);
        } catch (SchedulerException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 是否存在触发器
     *
     * @param jobName  任务名
     * @param jobGroup 任务组
     * @return
     */
    public Boolean hasTrigger(String jobName, String jobGroup) {
        try {
            String triggerName = "TRIGGER-" + jobName;
            String triggerGroup = "TRIGGER-GROUP-" + jobGroup;
            TriggerKey triggerKey = TriggerKey.triggerKey(triggerName, triggerGroup);
            return scheduler.checkExists(triggerKey);
        } catch (SchedulerException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 是否存在触发器
     *
     * @param jobName      任务名
     * @param jobGroup     任务组
     * @param triggerName  触发器名
     * @param triggerGroup 触发器组
     * @return
     */
    public Boolean hasTrigger(String jobName, String jobGroup, String triggerName, String triggerGroup) {
        try {
            TriggerKey triggerKey = TriggerKey.triggerKey(triggerName, triggerGroup);
            return scheduler.checkExists(triggerKey);
        } catch (SchedulerException e) {
            e.printStackTrace();
            return false;
        }
    }


    /**
     * 移除任务
     *
     * @param jobName  任务名
     * @param jobGroup 任务组
     * @return
     */
    public Boolean removeJob(String jobName, String jobGroup) {
        String triggerName = "TRIGGER-" + jobName;
        String triggerGroup = "TRIGGER-GROUP-" + jobGroup;
        return removeJob(jobName, jobGroup, triggerName, triggerGroup);
    }

    /**
     * 移除任务
     *
     * @param jobName      任务名
     * @param jobGroup     任务组
     * @param triggerName  触发器名
     * @param triggerGroup 触发器组
     * @return
     */
    public Boolean removeJob(String jobName, String jobGroup, String triggerName, String triggerGroup) {
        try {
            TriggerKey triggerKey = TriggerKey.triggerKey(triggerName, triggerGroup);

            scheduler.pauseTrigger(triggerKey);// 停止触发器
            scheduler.unscheduleJob(triggerKey);// 移除触发器
            scheduler.deleteJob(JobKey.jobKey(jobName, jobGroup));// 删除任务
            return true;
        } catch (SchedulerException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 触发任务
     *
     * @param jobName  任务名
     * @param jobGroup 任务组
     * @return
     */
    public Boolean triggerJob(String jobName, String jobGroup) {
        try {
            JobKey jobKey = JobKey.jobKey(jobName, jobGroup);
            scheduler.triggerJob(jobKey);
            return true;
        } catch (SchedulerException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 启动任务
     *
     * @param jobName  任务名
     * @param jobGroup 任务组
     * @return
     */
    public Boolean startJob(String jobName, String jobGroup) {
        String triggerName = "TRIGGER-" + jobName;
        String triggerGroup = "TRIGGER-GROUP-" + jobGroup;
        return startJob(jobName, jobGroup, triggerName, triggerGroup);
    }

    /**
     * 启动任务
     *
     * @param jobName      任务名
     * @param jobGroup     任务组
     * @param triggerName  触发器名
     * @param triggerGroup 触发器组
     * @return
     */
    public Boolean startJob(String jobName, String jobGroup, String triggerName, String triggerGroup) {
        try {
            JobKey jobKey = JobKey.jobKey(jobName, jobGroup);
            scheduler.resumeJob(jobKey);
            return true;
        } catch (SchedulerException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 关闭任务
     *
     * @param jobName  任务名
     * @param jobGroup 任务组
     * @return
     */
    public Boolean stopJob(String jobName, String jobGroup) {
        String triggerName = "TRIGGER-" + jobName;
        String triggerGroup = "TRIGGER-GROUP-" + jobGroup;
        return stopJob(jobName, jobGroup, triggerName, triggerGroup);
    }

    /**
     * 关闭任务
     *
     * @param jobName      任务名
     * @param jobGroup     任务组
     * @param triggerName  触发器名
     * @param triggerGroup 触发器组
     * @return
     */
    public Boolean stopJob(String jobName, String jobGroup, String triggerName, String triggerGroup) {
        try {
            JobKey jobKey = JobKey.jobKey(jobName, jobGroup);
            scheduler.pauseJob(jobKey);
            return true;
        } catch (SchedulerException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 启动触发器
     *
     * @param jobName  任务名
     * @param jobGroup 任务组
     * @return
     */
    public Boolean startTrigger(String jobName, String jobGroup) {
        String triggerName = "TRIGGER-" + jobName;
        String triggerGroup = "TRIGGER-GROUP-" + jobGroup;
        return startTrigger(jobName, jobGroup, triggerName, triggerGroup);
    }

    /**
     * 启动触发器
     *
     * @param jobName      任务名
     * @param jobGroup     任务组
     * @param triggerName  触发器名
     * @param triggerGroup 触发器组
     * @return
     */
    public Boolean startTrigger(String jobName, String jobGroup, String triggerName, String triggerGroup) {
        try {
            TriggerKey triggerKey = TriggerKey.triggerKey(triggerName, triggerGroup);
            scheduler.resumeTrigger(triggerKey);
            return true;
        } catch (SchedulerException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 关闭触发器
     *
     * @param jobName  任务名
     * @param jobGroup 任务组
     * @return
     */
    public Boolean stopTrigger(String jobName, String jobGroup) {
        String triggerName = "TRIGGER-" + jobName;
        String triggerGroup = "TRIGGER-GROUP-" + jobGroup;
        return stopTrigger(jobName, jobGroup, triggerName, triggerGroup);
    }

    /**
     * 关闭触发器
     *
     * @param jobName      任务名
     * @param jobGroup     任务组
     * @param triggerName  触发器名
     * @param triggerGroup 触发器组
     * @return
     */
    public Boolean stopTrigger(String jobName, String jobGroup, String triggerName, String triggerGroup) {
        try {
            TriggerKey triggerKey = TriggerKey.triggerKey(triggerName, triggerGroup);
            scheduler.pauseTrigger(triggerKey);
            return true;
        } catch (SchedulerException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 启动调度容器
     *
     * @return
     */
    public Boolean startScheduler() {
        try {
            if (!scheduler.isStarted())
                scheduler.start();
            return true;
        } catch (SchedulerException e) {
            e.printStackTrace();
            return false;
        }
    }


    /**
     * 启动调度容器
     *
     * @return
     */
    public Boolean startScheduler(int time) {
        try {
            if (!scheduler.isStarted())
                scheduler.startDelayed(time);
            return true;
        } catch (SchedulerException e) {
            e.printStackTrace();
            return false;
        }
    }


    /**
     * 关闭调度容器
     *
     * @return
     */
    public Boolean stopScheduler() {
        try {
            if (!scheduler.isShutdown())
                scheduler.shutdown();
            return true;
        } catch (SchedulerException e) {
            e.printStackTrace();
            return false;
        }
    }
}