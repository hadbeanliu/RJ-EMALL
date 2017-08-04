package com.rongji.dfish.cache.meta;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.Pipeline;
import redis.clients.jedis.SortingParams;
import redis.clients.jedis.BinaryClient.LIST_POSITION;  

import com.rongji.dfish.cache.RedisOper;

public class ListMeta extends BaseMeta {
	
	public ListMeta(){}
	
	public ListMeta(String id){
		super(id);
	}
	
	public void add(final String... values) {
//		getJedis().rpush(getId(), values);
		doJedis(new RedisOper<Long>() {
			public Long act(Jedis jedis) {
				return jedis.rpush(getId(), values);
			}
		});
	}
	
	public Long size() {
//		return getJedis().llen(getId());
		return doJedis(new RedisOper<Long>() {
			public Long act(Jedis jedis) {
				return jedis.llen(getId());
			}
		});
	}
	
	/**
	 * 移除其中指定的值
	 * @param value
	 * @return 返回被移除的个数
	 */
	public Long remove(final String value) {
//		return getJedis().lrem(getId(), 0, value);
		return doJedis(new RedisOper<Long>() {
			public Long act(Jedis jedis) {
				return jedis.lrem(getId(), 0, value);
			}
		});
	}
	
	/**
	 * 获取列表中的数据支持反序（输入负数可）
	 * <p>
	 * 下标(index)参数 start 和 end 都以 0 为底，也就是说，以 0 表示列表的第一个元素，以 1 表示列表的第二个元素，以此类推。
	 * 你也可以使用负数下标，以 -1 表示列表的最后一个元素， -2 表示列表的倒数第二个元素，以此类推。
	 * 
	 * @param start
	 * @param end
	 * @return
	 */
	public List<String> gets(final long start, final long end) {
//		return getJedis().lrange(getId(), start, end);
		return doJedis(new RedisOper<List<String>>() {
			public List<String> act(Jedis jedis) {
				return jedis.lrange(getId(), start, end);
			}
		});
	}
	
/////////////////////////////////////////////////////////////////////////////////////	
	/**
	 * 获取其中指定的值
	 * @param index
	 * @return 返回指定值
	 */
	public String get(final long index) {
		return doJedis(new RedisOper<String>() {
			public String act(Jedis jedis) {
				return jedis.lindex(getId(),index);
			}
		});
	}

	/**
	 * 修剪到指定范围内的清单
	 * @param start
	 * @param end
	 * @return
	 */
	public String ltrim(final long start,final long end){		
		return doJedis(new RedisOper<String>() {
			public String act(Jedis jedis) {
				return jedis.ltrim(getId(), start, end);
			}
		});
	}
	
	/**
	 * 从队列的左边入队一个或多个元素
	 * @param values
	 */
	public void lAdd(final String... values) {
		doJedis(new RedisOper<Long>() {
			public Long act(Jedis jedis) {
				return jedis.lpush(getId(), values);
			}
		});
	}
	
	/**
	 * 在value的相对位置插入记录
	 * @param LIST_POSITION 前面插入或后面插入
	 * @param String pivot 相对位置的内容
	 * @param String value 插入的内容
	 * @return 记录总数
	 */
	public Long lInsert(final LIST_POSITION where, final String pivot,final String value) {
		return doJedis(new RedisOper<Long>() {
			public Long act(Jedis jedis) {
				return jedis.linsert(getId(), where, pivot, value);
			}
		});
	}
	
	/**
	 * 单个添加成员并排序返回结果个数
	 * @param srcList 成员结果集
	 * @param toKey 
	 * @return
	 */
	public Long appendMember(final String member,final String toKey){
	    doJedis(new RedisOper<Long>() {
			public Long act(Jedis jedis) {
				jedis.rpush(toKey, member);
				SortingParams sortingParameters=new SortingParams();			
				sortingParameters.desc();				
				return jedis.sort(getId(), sortingParameters,toKey);
			}
		});
	    return null;
	}
	
	/**
	 * 批量添加成员并排序返回结果个数
	 * @param srcList 成员结果集
	 * @param toKey 
	 * @return
	 */
	public Long appendMembers(final List<String> srcList,final String toKey){
	    doJedis(new RedisOper<Long>() {
			public Long act(Jedis jedis) {
				Pipeline pl = jedis.pipelined();
				pl.multi();
				//批量添加命令
				for(String str:srcList){
					pl.rpush(toKey, str);
				}
				pl.exec();
				pl.sync();
				SortingParams sortingParameters=new SortingParams();			
				sortingParameters.desc();				
				return jedis.sort(getId(), sortingParameters,toKey);
			}
		});
	    return null;
	}

	/**
	 * 批量移除成员并排序返回结果
	 * @param srcList 成员结果集
	 * @param toKey
	 * @return
	 */
	public List<Object> removeMembers(final List<String> srcList,final String toKey){
		
		return doJedis(new RedisOper<List<Object>>() {
			public List<Object> act(Jedis jedis) {
				Pipeline pl = jedis.pipelined();
				pl.multi();
				//批量移除命令
				for(String str:srcList){
					pl.lrem(toKey,0, str);
				}
				pl.exec();		
				List<Object> results = pl.syncAndReturnAll();		
				return results;
			}
		});
	}
	
	/**
	 * 批量移除成员中的一个指定结果
	 * @param afterKeyList 拼接后缀key结合
	 * @param beforeKey 统一拼接前缀Key
	 * @param member 指定删除元素
	 * @return
	 */
	public List<Object> removeMembers(final List<String> afterKeyList,final String beforeKey,final String member){
		
		return doJedis(new RedisOper<List<Object>>() {
			public List<Object> act(Jedis jedis) {
				Pipeline pl = jedis.pipelined();
				pl.multi();
				//批量移除命令
				for(String str:afterKeyList){					
					pl.lrem(beforeKey+str,0, member);
				}
				pl.exec();	
				List<Object> results = pl.syncAndReturnAll();	
				return results;
			}
		});
	}
	
	/**
	 * 批量添加成员中的一个指定结果
	 * @param afterKeyList 拼接后缀key结合
	 * @param beforeKey 统一拼接前缀Key
	 * @param member 指定添加元素
	 * @return
	 */
	public List<Object> addMembers(final List<String> afterKeyList,final String beforeKey,final String member){
		
		return doJedis(new RedisOper<List<Object>>() {
			public List<Object> act(Jedis jedis) {
				Pipeline pl = jedis.pipelined();
				pl.multi();
				//批量添加命令
				for(String str:afterKeyList){					
					pl.lpush(beforeKey+str, member);
				}
				pl.exec();						
				List<Object> results = pl.syncAndReturnAll();	
				return results;
			}
		});
	}
	
	/**
	 * 批量删除信息
	 * @param srcKeys 要删除的key集
	 * @return
	 */
	public List<Object> delDataByKeys(final List<String> srcKeys) {
		return doJedis(new RedisOper<List<Object>>() {
			public List<Object> act(Jedis jedis) {
				Pipeline pl = jedis.pipelined();
				pl.multi();
				// 批量删除命令
				for (String str : srcKeys) {
					pl.del(str);
				}
				pl.exec();
				List<Object> results = pl.syncAndReturnAll();	
				return results;
			}
		});
	}
	
	/**
	 * 根据用户ID和列表文章ID集批量获取信息
	 * @param srcList  文章ID列表源数据
	 * @param userId   用户ID
	 * @param needBehavior   是否需要行为计数(喜欢状态、推荐状态、热度大小、评论大小)
	 * @return
	 */
	public List<Map<String,Object>> getMembersByArticleIdsAndUserId(final List<String> srcList,final String userId,final boolean needBehavior){
		
		return doJedis(new RedisOper<List<Map<String,Object>>>() {
			@SuppressWarnings({ "unchecked", "rawtypes" })
			public List<Map<String,Object>> act(Jedis jedis) {
				
				Pipeline commonPl = jedis.pipelined();//批量封装获取常用数据命令
				List<Map<String,Object>> membersList = new ArrayList<Map<String,Object>>();	//最终返回结果集
				int size=srcList.size();//获取块数
				for (int i = 0; i < size; i++) {
					String objId=srcList.get(i).toString();//文章ID
					//是否需要行为计数数据
					if(needBehavior&& userId !=null){
						String likeKey="articleLike.userId:"+userId+"objId:"+objId;//喜欢标识
						String recommendKey="recommend.userId:"+userId+"objId:"+objId;//推荐标识
						commonPl.get(likeKey);//喜欢状态
						commonPl.get(recommendKey);//推荐状态
					}
					String commentKey="cmtList.objId:"+objId;//评论标识					
					commonPl.llen(commentKey);//评论大小
					String labelKey="articleLabel.objId:"+objId;//标签标识							
					commonPl.hgetAll(labelKey);//标签信息
					String articleKey="article.objId:"+objId;//文章标识
					commonPl.hgetAll(articleKey);//文章信息
					String seeCountKey="seeCount.objId:"+objId;//查看标识
					commonPl.incr(seeCountKey);//浏览计数
				}
				List<Object> commonMemberList = commonPl.syncAndReturnAll();//常用数据集
				Pipeline supplementPl = jedis.pipelined();//批量封装增补数据命令				
				int flag=0;//命令计数
				int divisor=6;//不同类型数
				int indexofdivisor=0;//配合是否需要行为计数改索引位置
				for(int i = 0;i < size; i++){
					Map<String,Object> memberMap=new HashMap<String,Object>(1);//单块数据集合
					Map<String,Object> behaviorMap=new HashMap<String,Object>(1);
					if(needBehavior && userId!=null){
						//行为计数
						behaviorMap.put("articleLikeStaus",commonMemberList.get(i*divisor));//喜欢状态
						behaviorMap.put("recommendStatus", commonMemberList.get(i*divisor+1));//推荐状态
					}else{
						divisor=4;//浏览计数、文章信息、标签信息、评论大小
						indexofdivisor=-2;//喜欢状态、推荐状态
					}		
					behaviorMap.put("cmtSize", commonMemberList.get(i*divisor+2+indexofdivisor));//评论大小
					memberMap.put("behavior", behaviorMap);	
				
					//标签信息
					Map<String,String> tempMap=new HashMap<String,String>();
					List<Object> labelList = new ArrayList<Object>(1);
					tempMap=(Map)commonMemberList.get(i*divisor+3+indexofdivisor);
					if(tempMap.size()>0){
						for(String str:tempMap.keySet()){
							Map<String,String> labelMap=new HashMap<String,String>();
							labelMap.put("labelName",str);
							labelMap.put("siteId",tempMap.get(str));
							String queryTagKey="queryTag.siteId:"+labelMap.get("siteId");//查询标签标识
							supplementPl.hmget(queryTagKey,labelMap.get("labelName"));//查询信息	
							flag=flag+1;
							labelList.add(labelMap);
						}
						memberMap.put("label", labelList);			
					}
					
					//文章信息
					Map<String,String> articleMap=new HashMap<String,String>();
					articleMap=(Map)commonMemberList.get(i*divisor+4+indexofdivisor);
					//文章是否存在
					if(!articleMap.isEmpty()){
						memberMap.put("article", articleMap);
						membersList.add(memberMap);	
						//浏览次数
						memberMap.put("seeCount", commonMemberList.get(i*divisor+5+indexofdivisor));
						
						String againReprintUserId=userId;
						String authorId=articleMap.get("userId");	
						String usersKey="users.userId:"+authorId;//用户标识
						supplementPl.hgetAll(usersKey);
						flag=flag+1;
						if(articleMap.get("reprintUserId")!=null){						
							supplementPl.hgetAll("users.userId:" + articleMap.get("reprintUserId"));
							flag=flag+1;
							supplementPl.sismember("isAttention.userId:"+againReprintUserId, articleMap.get("reprintUserId"));
							flag=flag+1;
							if(articleMap.get("againReprintUserId")!=null){
								supplementPl.hgetAll("users.userId:" + articleMap.get("againReprintUserId"));
								flag=flag+1;
								supplementPl.sismember("isAttention.userId:"+againReprintUserId, articleMap.get("againReprintUserId"));
								flag=flag+1;
							}
						}
						String objId=null;
						//是否转载附件获取源文章ID
						if(articleMap.get("reprintObjId")!=null){
							 objId =articleMap.get("reprintObjId").toString();
						}else{
							objId = articleMap.get("objId").toString();// 文章ID
						}
						
						//活动|话题文章信息
						flag=flag+1;
						if(articleMap.get("activitiesId")!=null){
							supplementPl.hgetAll("activitiesMap.activitiesId:" + articleMap.get("activitiesId").toString());
						}
						flag=flag+1;
						if(articleMap.get("topicId")!=null){
							supplementPl.hgetAll("topicMap.topicId:" + articleMap.get("topicId").toString());
						}
						
						String heatKey="heatSize.objId:"+objId;//热度标识
						supplementPl.get(heatKey);//热度大小
						// 附件ID是否为空
						if (articleMap.get("fileId") != null) {
							String fileId[] = articleMap.get("fileId").toString().split(",");
							
							for (int j = 0; j < fileId.length; j++) {							
								String annexKey = "cmsArticleFile.objId:" + objId + "fileId:" + fileId[j];// 附件标识
								supplementPl.hgetAll(annexKey);// 附件信息
								flag = flag + 1;
							}
						}					
					}else{		
						supplementPl.get("");//热度大小
						memberMap.put("article", articleMap);
						memberMap.put("objId", srcList.get(i));	
						memberMap.put("Msg", "该文章已不存在!");	
						membersList.add(memberMap);
					}
				}
				List<Object> supplementMemberList = supplementPl.syncAndReturnAll();//返回增补数据集
				
				int index=0;//返回的结果集中取数据类型不确定，通过记录未取索引位置
				for(int i = 0;i < size; i++){
				Map<String,Object> memberMap=new HashMap<String,Object>(1);//单块数据集合				
				memberMap=membersList.get(i);			
				  if(!memberMap.isEmpty()&&memberMap.get("article")!=null){
					 //是否有标签信息，添加是否系统数据				 
				    if(memberMap.get("label")!=null){
				    	List<Object> labelList = new ArrayList<Object>();
				    	List<Object> tempLabelList = new ArrayList<Object>();
				    	tempLabelList=(List)memberMap.get("label");	
				    	int tempLabelSize=tempLabelList.size();
					    for(int j=0;j<tempLabelSize;j++){
					    	Map<String,String> labelMap=new HashMap<String,String>();	
					    	labelMap=(Map)tempLabelList.get(j);
							String isSystem=supplementMemberList.get(index).toString();//返回值为数组包含"[]"号
						    labelMap.put("isSystem",isSystem.substring(1,isSystem.length()-1));
						    labelList.add(labelMap);
						    index=index+1;
					    }
					    memberMap.put("label", labelList);
				    }
				  }
					Map<String,String> articleMap=new HashMap<String,String>();						
					articleMap=(Map)memberMap.get("article");
					if(!articleMap.isEmpty()){
						//用户信息
					    Map<String,String> userMap=(Map)supplementMemberList.get(index);
					    articleMap.put("nick", userMap.get("nick"));
					    articleMap.put("userName", userMap.get("userName"));
					    articleMap.put("headPic", userMap.get("headPic"));
					    
					    index=index+1;
					    //判断是否转载文章
					    if(articleMap.get("reprintUserId")!=null){
						    Map<String,String> reprintUserMap=(Map)supplementMemberList.get(index);
						    articleMap.put("reprintUserId", reprintUserMap.get("id"));
						    articleMap.put("reprintNick", reprintUserMap.get("nick"));
						    articleMap.put("reprintUserName", reprintUserMap.get("userName"));
						    articleMap.put("reprintHeadPic", reprintUserMap.get("headPic"));
						    index=index+1;
						    articleMap.put("relationReprintUserId", supplementMemberList.get(index).toString());
						    index=index+1;
						    //判断是否再次转载文章
						    if(articleMap.get("againReprintUserId")!=null){
							    Map<String,String> againReprintUserMap=(Map)supplementMemberList.get(index);
							    articleMap.put("againReprintUsersId", againReprintUserMap.get("id"));
							    articleMap.put("againReprintNick", againReprintUserMap.get("nick"));
							    articleMap.put("againReprintUserName", againReprintUserMap.get("userName"));
							    articleMap.put("againReprintHeadPic", againReprintUserMap.get("headPic"));
							    index =index+1;
							    articleMap.put("relationAgainReprintUserId", supplementMemberList.get(index).toString());
							    index=index+1;
						    }
					    }
					    //获取活动标题
					    if(articleMap.get("activitiesId")!=null){
					    	Map<String,String> activitiesMap=(Map)supplementMemberList.get(index);
					    	articleMap.put("activitiesTitle", activitiesMap.get("arTitle"));
					    	index=index+1;
					    }
					    //获取话题标题
					    if(articleMap.get("topicId")!=null){
					    	Map<String,String> topicMap=(Map)supplementMemberList.get(index);
					    	articleMap.put("topicTitle", topicMap.get("ht"));
					    	index=index+1;
					    }
					    memberMap.put("article", articleMap);
					}
				    Map<String,Object> behaviorMap=new HashMap<String,Object>(1);
				    behaviorMap=(Map)memberMap.get("behavior");				   
					behaviorMap.put("heatSize", supplementMemberList.get(index));
					index=index+1;
				
					if (articleMap.get("fileId") != null) {
						//附件信息
						List<Object> annexList = new ArrayList<Object>();
						int fileIdSize=articleMap.get("fileId").toString().split(",").length;
					    for(int j=0;j<fileIdSize;j++){
					    	annexList.add(supplementMemberList.get(index));
					    	index=index+1;
					    }
					   memberMap.put("annex", annexList);
					}						
					membersList.set(i,memberMap);	
				}			
				return membersList;
			}
		});
    }
    
	/**
	 * 批量获取key指定的哈希集
	 * @param prefix 拼接前缀
	 * @param srcList 列表源后缀数据
	 * @return
	 */
	public List<Object> getHashDatasByListKeys(final String prefix,final List<String> srcList){
	
		return doJedis(new RedisOper<List<Object>>() {
			public List<Object> act(Jedis jedis) {
				Pipeline pl = jedis.pipelined();
				// 批量获取hash命令
				for (String str : srcList) {
					pl.hgetAll(prefix+str);
				}				
				return pl.syncAndReturnAll();
			}
		});
    }
	
	/**
	 * 获取动态信息(批量获取两次hash数据 第二次依据第一次取得值作为key)
	 * @param srcList 列表源key数据
	 * @return 
	 */
	public List<Object> getHashDatasByListKeys(final List<String> srcList){
		return doJedis(new RedisOper<List<Object>>() {
			@SuppressWarnings("unchecked")
			public List<Object> act(Jedis jedis) {
				if(srcList.size()>0){
					List<Object> data=new ArrayList<Object>();//返回结果				
					Pipeline pl = jedis.pipelined();
					// 批量获取hash命令
					for (String str : srcList) {
						pl.hgetAll(str);
					}					
					Pipeline commentPl = jedis.pipelined();
					data=pl.syncAndReturnAll();	
					int dataSize=data.size();
					for (int i=0;i<dataSize;i++) {
						Map<String,String> map=(Map<String,String>) data.get(i);	
						if(!map.isEmpty()){
							if(map.get("comment")!=null){
								commentPl.hgetAll(map.get("comment"));
							}else{
								commentPl.hgetAll("");
							}
						}else{
								commentPl.hgetAll("");
						}
					}
					
					List<Object> commentList=commentPl.syncAndReturnAll();
					int commentSize=commentList.size();
					for(int i=0;i<commentSize;i++){			
						Map<String,String> commentMap=(Map<String,String>) commentList.get(i);
						Map<String,Object> dataMap=(Map<String,Object>)data.get(i);
						if(!commentMap.isEmpty()){
							dataMap.put("comment", commentMap);
							data.set(i,dataMap);
						}else{
							data.set(i, "");
						}						
					}
					return data;
				}
			  return null;
			}			
		});
	}
}
