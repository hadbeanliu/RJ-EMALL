package com.rongji.dfish.cache.meta;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.rongji.dfish.cache.RedisOper;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.Pipeline;


public class HashMeta extends BaseMeta {
	
	private Map<String, String> map = new HashMap<String, String>();
	
	public HashMeta(){}
	
	public HashMeta(String id){
		super(id);
	}
	
	/**
	 * 增加 key 指定的哈希集中指定字段的数值。如果 key 不存在，会创建一个新的哈希集并与 key 关联。
	 * 如果字段不存在，则字段的值在该操作执行前被设置为 0
	 * 
	 * @param 修改的字段
	 * @ix 加的值
	 * @return 返回添加后的值
	 */
	public Long hincrBy(final String field, final long ix) {
//		return getJedis().hincrBy(getId(), field, ix);
		return doJedis(new RedisOper<Long>() {
			public Long act(Jedis jedis) {
				return jedis.hincrBy(getId(), field, ix);
			}
		});
	}
	
	public Map<String, String> getAll() {
//		return getJedis().hgetAll(getId());
		return doJedis(new RedisOper<Map<String, String>>() {
			public Map<String, String> act(Jedis jedis) {
				return jedis.hgetAll(getId());
			}
		});
	}
	
	public String get(final String field) {
//		return getJedis().hget(getId(), field);
		return doJedis(new RedisOper<String>() {
			public String act(Jedis jedis) {
				return jedis.hget(getId(), field);
			}
		});
	}
	
	public List<String> gets(final String... fields) {
//		return getJedis().hmget(getId(), fields);
		return doJedis(new RedisOper<List<String>>() {
			public List<String> act(Jedis jedis) {
				return jedis.hmget(getId(), fields);
			}
		});
	}
	
	public void set(final String field, final String value) {
//		getJedis().hset(getId(), field, value);
		doJedis(new RedisOper<Long>() {
			public Long act(Jedis jedis) {
				return jedis.hset(getId(), field, value);
			}
		});
	}
	
	public boolean exists(final String field) {
//		return getJedis().hexists(getId(), field);
		return doJedis(new RedisOper<Boolean>() {
			public Boolean act(Jedis jedis) {
				return jedis.hexists(getId(), field);
			}
		});
	}
	
	public Long getLong(String field) {
		Long lg = null;
		String v = get(field);
		if (v != null && !v.trim().isEmpty()) {
			try {
				lg = Long.valueOf(v);
			} catch (Exception e) {}
		}
		return lg == null ? 0L : lg;
	}
	
	/**
	 * 移除其中指定的值
	 * @param fields
	 * @return 返回被移除的个数
	 */
	public Long remove(final String... fields) {
//		return getJedis().hdel(getId(), fields);
		return doJedis(new RedisOper<Long>() {
			public Long act(Jedis jedis) {
				return jedis.hdel(getId(), fields);
			}
		});
	}
	
	/**
	 * 添加一个单元，本操作需要手动调用{@link #save()}
	 * 
	 * @param key
	 * @param value
	 * @return
	 */
	public HashMeta addEntry(String key, String value) {
		if (key == null || key.trim().isEmpty()) {
			throw new NullPointerException("key is null or empty");
		}
		map.put(key, value);
		return this;
	}
	
	
	
	public void save() {
//	    Pipeline pl = getJedis().pipelined();
//	    pl.multi();
//	    String id = getId();
//		for (Map.Entry<String, String> en : map.entrySet()) {
//			pl.hset(id, en.getKey(), en.getValue());
//		}
//	    pl.exec();
//	    List<Object> results = pl.syncAndReturnAll();
//	    results.size();
	    doJedis(new RedisOper<Integer>() {
			public Integer act(Jedis jedis) {
				Pipeline pl = jedis.pipelined();
				pl.multi();
				String id = getId();
				for (Map.Entry<String, String> en : map.entrySet()) {
					pl.hset(id, en.getKey(), en.getValue());
				}
				pl.exec();
				List<Object> results = pl.syncAndReturnAll();
				return results.size();
			}
		});
	}
	
//	private Map<String, String> toMap(List<Entry<String, String>> result) {
//		if (result != null && result.size() > 0) {
//			HashMap<String, String> r = new HashMap<String, String>(result.size());
//			for (Entry<String, String> en : result) {
//				r.put(en.getKey(), en.getValue());
//			}
//			return r;
//		}
//		return Collections.emptyMap();
//	}
	
	
	////////////////////////////////////////////////////////////////////////////////////////
	/**
	 * 获取所有key
	 * @return 返回所有key值
	 */
	public Set<String> hkeys() {
		return doJedis(new RedisOper<Set<String>>() {
			public Set<String> act(Jedis jedis) {
				return jedis.hkeys(getId());
			}
		});
	}
	
	/**
	 * 返回 key 指定的哈希集中所有字段的值。
	 * @return 哈希集中的值的列表，当 key 指定的哈希集不存在时返回空列表。
	 */
	public List<String> hvals() {
		return doJedis(new RedisOper<List<String>>() {
			public List<String> act(Jedis jedis) {
				return jedis.hvals(getId());
			}
		});
	}
	
	/**
	 * 返回 key 指定的哈希集包含的字段的数量。
	 * @return 哈希集中字段的数量，当 key 指定的哈希集不存在时返回 0 
	 */
	public Long hlen() {
		return doJedis(new RedisOper<Long>() {
			public Long act(Jedis jedis) {
				return jedis.hlen(getId());
			}
		});
	}
}
