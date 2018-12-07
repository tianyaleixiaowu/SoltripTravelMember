package code.example.test;

import com.s.t.m.common.redis.RedisUtils;
import org.junit.Test;

import javax.annotation.Resource;

public class RedisTest extends Tester{
	
	@Resource
	private RedisUtils redisService;

	@Test
	public void ma(){
		redisService.set("name_2", "ccccc");
		redisService.expire("name_2", 60*60*2);
	}
}
