package com.s.t.m.sys.service;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.s.t.m.sys.entity.LogEntity;

/**
 * 日志队列的消费者
 * @author Bai
 *
 */
@Component
public class LogConsumer implements Runnable{

    private static Logger logger = LoggerFactory.getLogger(LogConsumer.class);
    
    public static final int DEFAULT_BATCH_SIZE = 64;
    @Autowired
    private LogQueue logQueue;
    @Autowired
    private LogService logService;
    
    private int batchSize = DEFAULT_BATCH_SIZE;
    
    private boolean active = true;
    
    private Thread thread;

    @PostConstruct
    public void init() {
        thread = new Thread(this);
        thread.start();
    }

    @PreDestroy
    public void close() {
        active = false;
    }

    @Override
    public void run() {
        while (active) {
            execute();
        }
    }

    public void execute() {
        List<LogEntity> logs = new ArrayList<>();
        try {
            int size = 0;
            while (size < batchSize) {
            	LogEntity systemLog = logQueue.poll();
                if (systemLog == null) {
                    break;
                }
                logs.add(systemLog);
                size++;
            }
        } catch (Exception ex) {
        	ex.printStackTrace();
            logger.info(ex.getMessage(), ex);
        }
        if (!logs.isEmpty()) {
        	logService.addLogList(logs);//新增
        }
    }

   

    public void setBatchSize(int batchSize) {
        this.batchSize = batchSize;
    }



}

