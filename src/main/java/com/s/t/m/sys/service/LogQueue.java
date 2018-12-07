package com.s.t.m.sys.service;

import org.springframework.stereotype.Component;

import com.s.t.m.sys.entity.LogEntity;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

/**
 * 日志存放队列
 * 
 * @author Bai
 *
 */
@Component
public class LogQueue {

	private BlockingQueue<LogEntity> blockingQueue = new LinkedBlockingQueue<>();

	public void add(LogEntity log) {
		blockingQueue.add(log);
	}

	public LogEntity poll() throws InterruptedException {
		return blockingQueue.poll(1, TimeUnit.SECONDS);
	}

}
