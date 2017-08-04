package com.rongji.dfish.cache.meta;

import java.util.List;
import java.util.Set;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.SortingParams;

import com.rongji.dfish.cache.RedisOper;


/**
 *
 * @since 2014-11-06
 * @author zhn
 *
 */
public class SortedSetMeta extends BaseMeta {
	
	public SortedSetMeta(){}
	
	public SortedSetMeta(String id){
		super(id);
	}
	/**
	 * 添加到有序set的一个或多个成员，或更新的分数，如果它已经存在
	 * @param score
	 * @param member
	 * @return
	 */
	public Long zadd(final double score,final String member) {
		return doJedis(new RedisOper<Long>() {
			public Long act(Jedis jedis) {
				return jedis.zadd(getId(), score, member);
			}
		});
	}
	/**
	 * 获取成员在排序设置相关的比分
	 * @param member
	 * @return
	 */
	public Double zscore(final String member) {
		return doJedis(new RedisOper<Double>() {
			public Double act(Jedis jedis) {
				return jedis.zscore(getId(), member);
			}
		});
	}
	
	/**
	 * 返回key的有序集元素个数。
	 * @return
	 */
	public Long zcard() {
		return doJedis(new RedisOper<Long>() {
			public Long act(Jedis jedis) {
				return jedis.zcard(getId());
			}
		});
	}
	
	/**
	 * 返回有序集key中，指定区间内的成员	
	 * @param star
	 * @param end
	 * @return
	 */
	public Set<String> zrevrange(final long star,final long end) {
		return doJedis(new RedisOper<Set<String>>() {
			public Set<String> act(Jedis jedis) {
				return jedis.zrevrange(getId(), star,end);
			}
		});
	}
	
	/**
	 * 返回的成员在排序设置的范围，由得分	
	 * @param min
	 * @param max	 * 
	 * @return
	 */
	public Set<String> zrangeByScore(final String min,final String max) {
		return doJedis(new RedisOper<Set<String>>() {
			public Set<String> act(Jedis jedis) {
				return jedis.zrangeByScore(getId(), min, max);
			}
		});
	}
	
	/**	
	 * 增加某个元素的分数
	 * @param score
	 * @param member
	 * @return
	 */
	public Double zincrby(final double score,final String member) {
		return doJedis(new RedisOper<Double>() {
			public Double act(Jedis jedis) {
				return jedis.zincrby(getId(), score, member);
			}
		});
	}
	
	/**
	 * 返回有序集List，降序排序
	 * @param pattern 外部key规则
	 * @return
	 */
	public List<String> sort(final String pattern) {	
		return doJedis(new RedisOper<List<String>>() {
			public List<String> act(Jedis jedis) {
				SortingParams sortingParameters=new SortingParams();			
				sortingParameters.desc();
				sortingParameters.by(pattern);
				return jedis.sort(getId(), sortingParameters);
			}
		});
	}
	
	/**
	 * 返回有序集List
	 * @param pattern 外部key规则
	 * @param liftingType 0:降序 1:升序
	 * @param start 开始索引
	 * @param count 获取数量
	 * @return
	 */
	public List<String> sort(final String pattern,final int liftingType,final int start,final int count) {	
		return doJedis(new RedisOper<List<String>>() {
			public List<String> act(Jedis jedis) {
				SortingParams sortingParameters=new SortingParams();
				if(liftingType==0){
					sortingParameters.desc();
				}else{
					sortingParameters.asc();
				}
				sortingParameters.alpha();
				sortingParameters.by(pattern);
				sortingParameters.limit(start, count);
				return jedis.sort(getId(), sortingParameters);
			}
		});
	}
	
	/**
	 * 返回有序集List,降序排序
	 * @return
	 */
	public List<String> sort() {	
		return doJedis(new RedisOper<List<String>>() {
			public List<String> act(Jedis jedis) {
				SortingParams sortingParameters=new SortingParams();			
				sortingParameters.desc();			
				return jedis.sort(getId(), sortingParameters);
			}
		});
	}
	
	/**
	 * 对有序集List进行降序排序，并存储到指定的key
	 * @param dstkey 存储的指定的新key，存在则覆盖
	 * @param pattern 外部key规则
	 * @return
	 */
	public Long sortAndStore(final String pattern,final String dstkey) {	
		return doJedis(new RedisOper<Long>() {
			public Long act(Jedis jedis) {
				SortingParams sortingParameters=new SortingParams();			
				sortingParameters.desc();
				sortingParameters.by(pattern);
				return jedis.sort(getId(), sortingParameters,dstkey);
			}
		});
	}
	
}
