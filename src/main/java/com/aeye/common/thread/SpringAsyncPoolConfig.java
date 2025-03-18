//package com.aeye.common.thread;
//
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.context.annotation.PropertySource;
//import org.springframework.core.task.AsyncTaskExecutor;
//import org.springframework.scheduling.annotation.EnableAsync;
//import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
//import com.aeye.common.utils.SpringContextUtils;
//
//import java.util.HashMap;
//import java.util.Map;
//import java.util.concurrent.ThreadPoolExecutor;
//
///**
// * Created by klaus on 2019/8/26.
// */
//@Configuration
//@EnableAsync
//@PropertySource("classpath:config/threadPool.properties")
//public class SpringAsyncPoolConfig {
//
//    public static Integer corePoolSize;
//    @Value("${maxPoolSize}")
//    private String maxPoolSize;
//    @Value("${queueCapacity}")
//    private String queueCapacity;
//    @Value("${keepAliveSeconds}")
//    private String keepAliveSeconds;
//
//    @Value("${corePoolSize}")
//    public void setCorePoolSize(String corePoolSize) {
//        SpringAsyncPoolConfig.corePoolSize = Integer.parseInt(corePoolSize);
//    }
//
//    @Bean(name = "XPBootExecutor")
//    public AsyncTaskExecutor xPBootExecutor() {
//        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
//        // 设置核心线程数
//        executor.setCorePoolSize(corePoolSize);
//        // 设置最大线程数
//        executor.setMaxPoolSize(Integer.parseInt(maxPoolSize));
//        // 设置队列容量
//        executor.setQueueCapacity(Integer.parseInt(queueCapacity));
//        // 设置线程活跃时间（秒）
//        executor.setKeepAliveSeconds(Integer.parseInt(keepAliveSeconds));
//        // 设置默认线程名称
//        executor.setThreadNamePrefix("xpBoot");
//        // 设置拒绝策略
//        executor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
//        // 等待所有任务结束后再关闭线程池
//        executor.setWaitForTasksToCompleteOnShutdown(true);
//        return executor;
//    }
//
//    public static int getActiveCount(String beanName){
//        Map<String, Object> data = showThreadPoolInfo(beanName);
//        if(data != null){
//            return (int)data.get("activeCount");
//        }
//        return 0;
//    }
//
//    public static Map<String, Object> showThreadPoolInfo(String beanName){
//        ThreadPoolTaskExecutor threadPoolExecutor = (ThreadPoolTaskExecutor) SpringContextUtils.getBean(beanName);
//        if(null==threadPoolExecutor){
//            return null;
//        }
//        Map<String, Object> data = new HashMap<>();
//        data.put("activeCount",threadPoolExecutor.getThreadPoolExecutor().getActiveCount());//活跃线总数
//        data.put("taskCount",threadPoolExecutor.getThreadPoolExecutor().getTaskCount());//任务总数
//        data.put("queue",threadPoolExecutor.getThreadPoolExecutor().getQueue());//等待队列大小
//        data.put("completedTaskCount",threadPoolExecutor.getThreadPoolExecutor().getCompletedTaskCount());//完成任务总数
//        data.put("poolSize",threadPoolExecutor.getThreadPoolExecutor().getPoolSize());//等待队列大小
//        data.put("corePoolSize",threadPoolExecutor.getThreadPoolExecutor().getCorePoolSize());//核心线程数
//        return data;
//    }
//}
