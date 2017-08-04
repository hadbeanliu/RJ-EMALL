package com.rongji.dfish.cache.meta;

import com.rongji.dfish.cache.RedisOper;
import redis.clients.jedis.Jedis;

public class StringMeta extends BaseMeta {
	public StringMeta() {
	}

	public StringMeta(String id) {
		super(id);
	}

	/**
	 * 设置一个key的value值
	 * @param str
	 * @return
	 */
	public String set(final String str) {
		return (doJedis(new RedisOper<String>() {
			public String act(Jedis jedis) {
				return jedis.set(getId(), str);
			}
		}));
	}
	
	/**
	 * 获取key的值
	 * @return
	 */
	public String get() {
		return (doJedis(new RedisOper<String>() {
			public String act(Jedis jedis) {
				return jedis.get(getId());
			}
		}));
	}

	/**
	 * Sets or clears the bit at offset in the string value stored at key
	 * @param offset
	 * @param value
	 * @return
	 */
	public Boolean setbit(final long offset, final boolean value) {
		return (doJedis(new RedisOper<Boolean>() {
			public Boolean act(Jedis jedis) {
				return jedis.setbit(getId(), offset, value);
			}
		}));
	}

	public byte[] getbyte() {
		return ((byte[]) doJedis(new RedisOper<byte[]>() {
			public byte[] act(Jedis jedis) {
				return jedis.get(getId().getBytes());
			}
		}));
	}
	
	/**
	 * 返回位的值存储在关键的字符串值的偏移量。
	 * @param offset
	 * @return
	 */
	public Boolean getbit(final long offset) {
		return (doJedis(new RedisOper<Boolean>() {
			public Boolean act(Jedis jedis) {
				return jedis.getbit(getId(), offset);
			}
		}));
	}
	///////////////////////////////////////////////////////////////////////////////
	/**
	 * 设置key-value并设置过期时间（单位：秒）
	 * @param seconds 
	 * @param value 
	 * @return
	 */
	public String setex(final int seconds,final String value){
		return (doJedis(new RedisOper<String>(){
			public String act(Jedis jedis){
				return jedis.setex(getId(), seconds, value);
			}
		}));
	}
}