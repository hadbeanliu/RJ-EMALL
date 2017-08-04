package com.rongji.dfish.cache.meta;

import redis.clients.jedis.Jedis;

import com.rongji.dfish.cache.RedisOper;

public class DoubleMeta extends BaseMeta {
	
	public DoubleMeta(){}
	
	public DoubleMeta(String id){
		super(id);
	}
	
	/**
	 * 将key对应的数字加ix。如果key不存在，操作之前，key就会被置为0.00
	 * <p>
	 * 本过程为原子操作
	 * 
	 * @ix 加的值
	 * @return
	 */
	public Double plus(final Double ix) {
		return doJedis(new RedisOper<Double>() {
			public Double act(Jedis jedis) {
				if (ix == 1) {
					return Double.valueOf(jedis.incr(getId()));
				} else {
					return jedis.incrByFloat(getId(), ix);
				}
			}
		});
	}
	
	/**
	 * 将key对应的数字减ix。如果key不存在，操作之前，key就会被置为0.00
	 * <p>
	 * 本过程为原子操作
	 * 
	 * @ix 减的值
	 * @return
	 */
	public Double minus(final Double ix) {
		return doJedis(new RedisOper<Double>() {
			public Double act(Jedis jedis) {
				if (ix == 1) {
					return Double.valueOf(jedis.decr(getId()));
				} else {
					return jedis.incrByFloat(getId(), -ix);
				}
			}
		});
	}
		
	public Double get() {
		return doJedis(new RedisOper<Double>() {
			public Double act(Jedis jedis) {
				Double v = 0.00;
				try {
					v = Double.valueOf(jedis.get(getId()));
				} catch (Exception e) {}
				return v;
			}
		});
	}
}