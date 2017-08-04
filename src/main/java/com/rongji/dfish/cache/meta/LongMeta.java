package com.rongji.dfish.cache.meta;

import redis.clients.jedis.Jedis;

import com.rongji.dfish.cache.RedisOper;

public class LongMeta extends BaseMeta {
	
	public LongMeta(){}
	
	public LongMeta(String id){
		super(id);
	}
	
	/**
	 * 将key对应的数字加ix。如果key不存在，操作之前，key就会被置为0
	 * <p>
	 * 本过程为原子操作
	 * 
	 * @ix 加的值
	 * @return
	 */
	public Long plus(final long ix) {
//		if (ix == 1) {
//			return getJedis().incr(getId());
//		} else {
//			return getJedis().incrBy(getId(), ix);
//		}
		return doJedis(new RedisOper<Long>() {
			public Long act(Jedis jedis) {
				if (ix == 1) {
					return jedis.incr(getId());
				} else {
					return jedis.incrBy(getId(), ix);
				}
			}
		});
	}
	
	/**
	 * 将key对应的数字减ix。如果key不存在，操作之前，key就会被置为0
	 * <p>
	 * 本过程为原子操作
	 * 
	 * @ix 减的值
	 * @return
	 */
	public Long minus(final long ix) {
//		if (ix == 1) {
//			return getJedis().decr(getId());
//		} else {
//			return getJedis().incrBy(getId(), ix);
//		}
		return doJedis(new RedisOper<Long>() {
			public Long act(Jedis jedis) {
				if (ix == 1) {
					return jedis.decr(getId());
				} else {
					return jedis.incrBy(getId(), -ix);
				}
			}
		});
	}
	
	public Long get() {
//		Long v = 0L;
//		try {
//			v = Long.valueOf(getJedis().get(getId()));
//		} catch (Exception e) {}
//		return v;
		return doJedis(new RedisOper<Long>() {
			public Long act(Jedis jedis) {
				Long v = 0L;
				try {
					v = Long.valueOf(jedis.get(getId()));
				} catch (Exception e) {}
				return v;
			}
		});
	}
/////////////////////////////////////////////
	/**
	 * 设置初始值
	 * @param v
	 * @return
	 */
	public String set(final String v){
		
		return doJedis(new RedisOper<String>() {
			public String act(Jedis jedis) {	
				return jedis.set(getId(), v);
			}
		});
	}
}

