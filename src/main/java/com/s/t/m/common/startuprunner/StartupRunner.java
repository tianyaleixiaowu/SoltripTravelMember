package com.s.t.m.common.startuprunner;


import java.util.Map;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import com.s.t.m.common.token.UserTokenUtils;

/**
 * 初始化资源
 * 
 * @author Bai
 *
 */
@Component
@Order(value = 1) // 越小优先级越高
public class StartupRunner implements ApplicationRunner {
	private static final Logger logger = LoggerFactory.getLogger(StartupRunner.class);
	/**
	 * 项目唯一秘钥对(每次启动更新)
	 */
	private static final String PASSWORD = UUID.randomUUID().toString().replaceAll("-", "");
	
	public static String pubKey;
	public static String priKey;
	
	@Override
	public void run(ApplicationArguments var1) throws Exception {
		// 生成公钥私钥，用于服务启动后token的使用
		Map<String, String> keyPair = UserTokenUtils.keyPair(PASSWORD);
		pubKey = keyPair.get("publicKey");// 公钥
		priKey = keyPair.get("privateKey");// 私钥
		logger.info("服务器启动成功！<<<<使用ApplicationRunner接口生成秘钥对>>>>");
	}

	public static void main(String[] args) {
		System.err.println(UUID.randomUUID().toString().replaceAll("-", ""));
		Map<String, String> keyPair = UserTokenUtils.keyPair(PASSWORD);
		pubKey = keyPair.get("publicKey");// 公钥
		priKey = keyPair.get("privateKey");// 私钥
		String tokenDecrypt = UserTokenUtils.tokenDecrypt("KNIvg+Ng67WEk5bW7RU5AopMhud7psPQvoLbnFPu6NbyGLTf4qlQlwgSq6Se1ZnSceksZPqvbN087Ns3f9AipzOPTF1CIYM4t5fje+EHcXg5SLJLyERAxjkxQ69RP/TZl2dOMS4Xg0Ut7oCaVcu+W4HVM/FqkK8teNk5kciGUq0=", StartupRunner.priKey);
	}
}
