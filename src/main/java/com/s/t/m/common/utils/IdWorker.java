package com.s.t.m.common.utils;

import java.math.BigDecimal;
import java.net.Inet4Address;
import java.util.logging.Level;
import java.util.logging.Logger;

import sun.net.util.IPAddressUtil;

/**
 * 生成18位id
 * @author Bai
 *
 */
@SuppressWarnings("unused")
public class IdWorker {
	private static final Logger log = Logger.getLogger("IdWorker");
	private final long workerId;
	private static final long TWEPOCH = 1483200000000L;
	private long sequence;
	private static final long workerIdBits = 18L;
	private static final long maxWorkerId = 262143L;
	private static final long sequenceBits = 4L;
	private static final long workerIdShift = 4L;
	private static final long timestampLeftShift = 22L;
	private static final long sequenceMask = 15L;
	private long lastTimestamp;
	private static IdWorker self = new IdWorker();



	public static IdWorker getInstance() {
		return self;
	}
	/**
	 * 初始化
	 */
	private IdWorker()
    {
        sequence = 0L;
        lastTimestamp = -1L;
        long workerId = -1L;
        try
        {
            workerId = generateWorkerId();
        }
        catch(Exception e)
        {	
			log.log(Level.SEVERE, (new StringBuilder()).append("Occur an error when initializing the IdWorker because ")
					.append(e.getMessage()).toString(), e);
        }
        if(workerId > 262143L || workerId < 0L)
        {
            throw new IllegalArgumentException(String.format("worker Id can't be greater than %d or less than 0", new Object[] {
                Long.valueOf(262143L)
            }));
        } else
        {
            this.workerId = workerId;
            return;
        }
    }
	/**
	 * 下个id 默认18位
	 * 
	 * @param obj 类型，支持Long，和BigDecimal，参数为class类型
	 * @return BigDecimal 或者 Long类型
	 */
	public synchronized Object nextId(Object obj) throws Exception{
		long timestamp = now();
		if (timestamp < lastTimestamp)
			try {
				throw new Exception(
						String.format(
								"Clock moved backwards. Refusing to generate id for %d milliseconds",
								new Object[] { Long.valueOf(lastTimestamp
										- timestamp) }));
			} catch (Exception e) {
				log.log(Level.SEVERE, "下一个ID获取异常", e.getMessage());
			}
		if (lastTimestamp == timestamp) {
			sequence = sequence + 1L & 15L;
			if (sequence == 0L)
				timestamp = tilNextMillis(lastTimestamp);
		} else {
			sequence = 0L;
		}
		lastTimestamp = timestamp;
		long nextId = (timestamp - 1483200000000L & 2199023255551L) << 22//长度
				| workerId << 4 | sequence;
		if(obj.toString().equals("class java.lang.Long")){
			return new Long(nextId);
		}else if(obj.toString().equals("class java.lang.Long")){
			return new BigDecimal(nextId);
		}else{
			throw new Exception("ID生成策略失败,传入不支持类型:"+obj.getClass()); 
		}
	}

	private long tilNextMillis(long lastTimestamp) {
		long timestamp;
		for (timestamp = now(); timestamp <= lastTimestamp; timestamp = now())
			;
		return timestamp;
	}
	
	private long now() {
		return System.currentTimeMillis();
	}

	private long generateWorkerId() throws Exception {
		String hostAddress = Inet4Address.getLocalHost().getHostAddress();
		byte ipBytes[] = textToNumericFormatV4(hostAddress);
		return (long) (((ipBytes[1] & 3) << 16) + ((ipBytes[2] & 255) << 8) + (ipBytes[3] & 255));
	}
	/**
     * 将ipv4字符串转为长度为4的byte数组
     * @param ip ipv4字符串
     * @return byte数组
     */
    @SuppressWarnings("restriction")
	public static byte[] textToNumericFormatV4(String ip)
    {
        if(ip == null || "".equals(ip))
        	//参数IP不能为空
            throw new IllegalArgumentException("the argument ip can't be empty.");
        if(!IPAddressUtil.isIPv4LiteralAddress(ip))
        	//IPv4地址格式错误
            throw new IllegalArgumentException("wrong format of ipv4 address.");
        else
        	log.log(Level.INFO,"验证id生成：ipv4",ip);
            return IPAddressUtil.textToNumericFormatV4(ip);
    }
    
}
