package com.pluswe.redis;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

public class FirstTest {
	JedisPool pool;
	Jedis jedis;

	public FirstTest() {
		// Jedis jedis = new Jedis("localhost",6379);
		// 连接池
		JedisPoolConfig config = new JedisPoolConfig();
		//连接耗尽时是否阻塞, false报异常,ture阻塞直到超时, 默认true
		config.setBlockWhenExhausted(true);
		//设置的逐出策略类名, 默认DefaultEvictionPolicy(当连接超过最大空闲时间,或连接数超过最大空闲连接数)
		config.setEvictionPolicyClassName("org.apache.commons.pool2.impl.DefaultEvictionPolicy");
		//是否启用pool的jmx管理功能, 默认true
		config.setJmxEnabled(true);
		//MBean ObjectName = new ObjectName("org.apache.commons.pool2:type=GenericObjectPool,name=" + "pool" + i); 默 认为"pool", JMX不熟,具体不知道是干啥的...默认就好.
		config.setJmxNamePrefix("pool");
		//是否启用后进先出, 默认true
		config.setLifo(true);
		//最大连接数, 默认8个
		config.setMaxTotal(8);
        //控制一个pool最多有多少个状态为idle(空闲的)的jedis实例。
        config.setMaxIdle(5);
        //建立连接最大的等待时间，如果超过等待时间，则直接抛出JedisConnectionException；
        config.setMaxWaitMillis(1000 * 100);
        //逐出连接的最小空闲时间 默认1800000毫秒(30分钟)
        config.setMinEvictableIdleTimeMillis(1800000);
        //最小空闲连接数, 默认0
        config.setMinIdle(0);
        //每次逐出检查时 逐出的最大数目 如果为负数就是 : 1/abs(n), 默认3
        config.setNumTestsPerEvictionRun(3);
        //对象空闲多久后逐出, 当空闲时间>该值 且 空闲连接>最大空闲数 时直接逐出,不再根据MinEvictableIdleTimeMillis判断  (默认逐出策略)   
        config.setSoftMinEvictableIdleTimeMillis(1800000);
        //在获取连接的时候检查有效性, 默认false.在borrow一个jedis实例时，是否提前进行validate操作；如果为true，则得到的jedis实例均是可用的；为true时无法获得连接？
        config.setTestOnBorrow(false);
        //在空闲时检查有效性, 默认false
        config.setTestWhileIdle(false);
        //逐出扫描的时间间隔(毫秒) 如果为负数,则不运行逐出线程, 默认-1
        config.setTimeBetweenEvictionRunsMillis(-1);
        pool = new JedisPool(config, "localhost", 6379);
		jedis = pool.getResource();
		jedis.auth("password");
	}

	public void testBasicString() {
		// -----添加数据----------
		jedis.set("name", "minxr");// 向key-->name中放入了value-->minxr
		System.out.println(jedis.get("name"));// 执行结果：minxr

		// -----修改数据-----------
		// 1、在原来基础上修改
		jedis.append("name", "jarorwar"); // 追加
		System.out.println(jedis.get("name"));// 执行结果:minxrjarorwar

		// 2、直接覆盖原来的数据
		jedis.set("name", "闵晓荣");
		System.out.println(jedis.get("name"));// 执行结果：闵晓荣

		// 删除key对应的记录
		jedis.del("name");
		System.out.println(jedis.get("name") == null);// 执行结果：null

		// mset相当于 jedis.set("name","minxr"); jedis.set("jarorwar","闵晓荣");
		jedis.mset("name", "minxr", "jarorwar", "闵晓荣");
		System.out.println(jedis.mget("name", "jarorwar"));
	}

	public void testMap() {
		Map<String, String> user = new HashMap<String, String>();
		user.put("name", "minxr");
		user.put("pwd", "password");
		jedis.hmset("user", user);
		// 取出user中的name，执行结果:[minxr]-->注意结果是一个泛型的List
		// 第一个参数是存入redis中map对象的key，后面跟的是放入map中的对象的key，后面的key可以跟多个，是可变参数
		List<String> rsmap = jedis.hmget("user", "name", "pwd");
		System.out.println(rsmap);

		// 删除map中的某个键值
		jedis.hdel("user","pwd");
		System.out.println(jedis.hmget("user", "pwd")); // 因为删除了，所以返回的是null
		System.out.println(jedis.hlen("user")); // 返回key为user的键中存放的值的个数1
		System.out.println(jedis.exists("user"));// 是否存在key为user的记录 返回true
		System.out.println(jedis.hkeys("user"));// 返回map对象中的所有key [pwd, name]
		System.out.println(jedis.hvals("user"));// 返回map对象中的所有value [minxr,password]
		
		Iterator<String> iter = jedis.hkeys("user").iterator();
		while (iter.hasNext()) {
			String key = iter.next();
			System.out.println(key + ":" + jedis.hmget("user", key));
		}

	}

	public void testList() {
		// 开始前，先移除所有的内容
		jedis.del("java framework");
		System.out.println(jedis.lrange("java framework", 0, -1));
		// 先向key java framework中存放三条数据
		jedis.lpush("java framework", "spring");
		jedis.lpush("java framework", "struts");
		jedis.lpush("java framework", "hibernate");
		// 再取出所有数据jedis.lrange是按范围取出，
		// 第一个是key，第二个是起始位置，第三个是结束位置，-1表示取得所有
		System.out.println(jedis.lrange("java framework", 0, -1));
		System.out.println(jedis.llen("java framework")); // jedis.llen获取长度
	}

	public void testSet() {
		// 添加
		jedis.sadd("sname", "minxr");
		jedis.sadd("sname", "jarorwar");
		jedis.sadd("sname", "noname");
		// 移除noname
		jedis.srem("sname", "noname");
		System.out.println(jedis.smembers("sname"));// 获取所有加入的value
		System.out.println(jedis.sismember("sname", "minxr"));// 判断 minxr是否是sname集合的元素
		System.out.println(jedis.srandmember("sname")); // 随机返回一个元素
		System.out.println(jedis.scard("sname"));// 返回集合的元素个数
	}

	public void test() throws InterruptedException {
		// keys中传入的可以用通配符
		System.out.println(jedis.keys("*")); // 返回当前库中所有的key [java framework, sname, foo, name, jarorwar, user]
		System.out.println(jedis.keys("*name"));// 返回的sname [sname, name]
		System.out.println(jedis.del("sanmdde"));// 删除key为sanmdde的对象 删除成功返回1 。删除失败（或者不存在）返回 0
		System.out.println(jedis.ttl("sname"));// 返回给定key的有效时间，如果是-1则表示永远有效
		
		jedis.setex("timekey", 10, "min");// 通过此方法，可以指定key的存活（有效时间） 时间为秒
		Thread.sleep(5000);// 睡眠5秒后，剩余时间将为<=5
		System.out.println(jedis.ttl("timekey")); // 输出结果为5
		jedis.setex("timekey", 1, "min"); // 设为1后，下面再看剩余时间就是1了
		System.out.println(jedis.ttl("timekey")); // 输出结果为1
		System.out.println("exists:"+jedis.exists("timekey"));// 检查key是否存在
		System.out.println("rename:"+jedis.rename("timekey", "time"));// 重命名为time，如果timekey不存在会抛出异常ERR no such key
		System.out.println(jedis.get("timekey"));// 因为重命名，返回为null
		System.out.println(jedis.get("time")); // 因为将timekey 重命名为time 所以可以取得值min

		// jedis 排序
		// 注意，此处的rpush和lpush是List的操作。是一个双向链表（但从表现来看的）
		jedis.del("a");// 先清除数据，再加入数据进行测试
		jedis.rpush("a", "1");
		jedis.lpush("a", "6");
		jedis.lpush("a", "3");
		jedis.lpush("a", "9");
		System.out.println(jedis.lrange("a", 0, -1));// [9, 3, 6, 1]
		System.out.println(jedis.sort("a")); // [1, 3, 6, 9] //输入排序后结果
		System.out.println(jedis.lrange("a", 0, -1));// [9, 3, 6, 1]
		
	}

	public static void main(String[] args) throws InterruptedException {
		// TODO Auto-generated method stub
		FirstTest f = new FirstTest();
		f.test();
	}
}
