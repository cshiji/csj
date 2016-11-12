package com.cshiji.superviki.base.dao.impl;

import java.io.Serializable;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ParameterMetaData;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import javax.annotation.Resource;
import org.apache.commons.collections.CollectionUtils;
import org.hibernate.Cache;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.jdbc.Work;
import org.springframework.stereotype.Repository;

import com.cshiji.superviki.base.dao.IHibernateDao;
import com.cshiji.superviki.base.mybatis.Pager;

@Repository
public class HibernateDao implements IHibernateDao {

	@Resource(name="sessionFactory")
	private SessionFactory sessionFactory;
	
	/**
	 * 获取session
	 * @return
	 */
	@Override
	public Session getSession(){
		return this.sessionFactory.getCurrentSession();
	}
	
	/**
	 * 清理session缓存
	 */
	private void flushCatch(){
		getSession().flush();
	}
	
	@Override
	public void save(Object obj){
		getSession().save(obj);
		flushCatch();
	}
	
	@Override
	public void saveOrUpdate(Object obj){
		getSession().saveOrUpdate(obj);
		flushCatch();
	}
	
	/**
	 * 批量保存
	 * 
	 * @param collection 集合
	 * @param clearTimePer 清空批次
	 * 
	 * @return 受影响行数
	 */
    @Override
	public int saveAll(Collection<?> collection, int clearTimePer) {
        int index = 0;
        Session session = getSession();
        for (Iterator<?> iterator = collection.iterator(); iterator.hasNext();) {
            session.save(iterator.next());
            index++;
            if (index % clearTimePer == 0) {
                session.flush();
                session.clear();
            }
        }
        session.flush();
        session.clear();
        return index;
    }
	
    @Override
	public void update(Object obj){
		getSession().update(obj);
		flushCatch();
	}
    
    /**
	 * 删除一个已经持久化的实例对象
	 * @param obj
	 */
	@Override
	public void delete(Object obj) {
		getSession().delete(obj);
		flushCatch();
	}
    
	/**
	 * 根据ID删除
	 * @param id
	 * @param clazz
	 */
	@Override
	public void  deleteById(Serializable id, Class<?> clazz) {
		getSession().delete(getSession().get(clazz, id));
		flushCatch();
	}
	
	/**
	 * 批量删除数据
	 * @param objectsToRemove
	 * @throws DataAccessException
	 */
	@Override
	public void batchRemove(List<?> objsList,int batchSize) {
		if (CollectionUtils.isEmpty(objsList)) return;
		int max = objsList.size();
		for (int i = 0; i < max; ++i) {
			getSession().refresh(objsList.get(i));
			getSession().delete(objsList.get(i));
			if (((i != 0) && (i % batchSize == 0)) || (i == max - 1))
				getSession().flush();
		}
	}
    
	/**
	 * 根据ids批量删除记录
	 * @param clazz 要删除的对象class
	 * @param idName 对象属性的主键名称
	 * @param ids 多个id
	 * @return 返回受影响的记录条数
	 */
    @Override
	public int batchRemoveByIds(Class<?> clazz,String idName,Object[] ids){
    	StringBuffer sb = new StringBuffer("DELETE FROM ");
    	sb.append(clazz.getName()).append(" WHERE ");
    	sb.append(idName);
    	sb.append("in (:ids) ");
    	int count = getSession().createQuery(sb.toString())
    			.setParameterList("ids", ids).executeUpdate();
    	flushCatch();
    	return count;
    }
	
	@Override
	public Object get(Serializable id,Class<?> clazz) {
		return getSession().get(clazz, id);
	}
	
    @Override
	public Object load(Serializable id,Class<?> clazz) {
    	return getSession().load(clazz, id);
    }
    
	/**
	 * 合并，返回一个更新过的持久化实例 
	 */
    @Override
	public Object merge(Object obj){
    	return getSession().merge(obj);
    }
    
    /**
     * 具名参数绑定，并返回绑定参数后的query<br>
     * 不支持定位参数绑定，注意：只支持具名参数绑定的hql语句
     * @param query
     * @param param 参数
     * @return 返回填充后的query
     */
    private Query fillQuery(Query query,Map<String,Object> paramMap){
    	if(paramMap == null || paramMap.size()==0){
    		return query;
    	}
    	for(Map.Entry<String, Object> param : paramMap.entrySet()){
    		String name = param.getKey();
    		Object obj = param.getValue();
			if(obj instanceof Collection<?>){
				query.setParameterList(name, (Collection<?>) obj);
			} else if(obj instanceof Object[]){  
                query.setParameterList(name, (Object[])obj);  
			}else{  
                query.setParameter(name, obj);  
            }  
    	}
    	return query;
    }
    
    /**
	 * hql的复杂查询，返回多条记录
	 * @param hql
	 * @param param
	 * @return 返回多条记录
	 */
    @Override
	@SuppressWarnings("unchecked")
	public <T> List<T> findListByHql(String hql,Map<String,Object> paramMap){
    	Query query = fillQuery(getSession().createQuery(hql),paramMap);
    	//query.setCacheable(true);//设置缓存,启用二级查询缓存
    	return query.list();
    }
	
    /**
	 * 查询一条记录
	 * @param hql
	 * @param paramMap
	 * @return
	 */
    @Override
	public Object findObject(String hql, Map<String,Object> paramMap){
        List<Object> list = findListByHql(hql, paramMap);
        if (list != null && list.size() == 1) {
            return list.get(0);
        } else {
            return null;
        }
    }
    
    /**
	 * 查询count
	 * @param hql 只有一个返回结果的countHql语句
	 * @param paramMap
	 * @return
	 */
    @Override
	public int queryCountByHql(String countHql,Map<String,Object> paramMap){
    	Query query = fillQuery(getSession().createQuery(countHql), paramMap);
    	Object rs = query.uniqueResult();
    	int count = 0;
    	if(rs!=null){
    		count = Integer.parseInt(rs.toString());
    	}
    	return count;
    }
    
    /**
	 * 分页查询，
	 * @param hql 查询语句
	 * @param paraMap 参数
	 * @param pageNo 当前页
	 * @param pageSize pageSize
	 * @return
	 */
    @Override
	public Pager<?> pageByHql(String hql,Map<String,Object> paraMap,int pageNo,int pageSize){
    	Pager<Object> page = new Pager<Object>(pageNo, pageSize);
    	StringBuffer totalCountHql = new StringBuffer("SELECT COUNT(1) ");
    	totalCountHql.append(hql.substring(hql.toUpperCase().indexOf("FROM")));
    	//总记录数
    	int total = queryCountByHql(totalCountHql.toString(), paraMap);
    	page.setTotal(total);
    	Query query = fillQuery(getSession().createQuery(hql),paraMap);
    	query.setFirstResult((page.getPageNo() - 1) * page.getPageSize());
    	query.setMaxResults(page.getPageSize());
    	//query.setCacheable(true);//设置缓存,启用二级查询缓存
    	List list = query.list();
    	page.setRows(list);
		return page;
    }

    /**
	 * 通过hql执行update或者delete
	 * @param hql 更新或者删除hql语句
	 * @param paramMap 参数
	 * @return 返回受影响条数
	 */
    @Override
	public int updateOrDeleteByHql(String hql,Map<String,Object> paramMap){
    	int count = 0;
    	Query query = fillQuery(getSession().createQuery(hql), paramMap);
    	//query.setCacheable(true);//设置缓存,启用二级查询缓存
    	count = query.executeUpdate();
    	flushCatch();
    	return count;
    }
    
    /**
     * 关闭结果集和statement
     * @param cs
     * @param rs
     */
    private void close(CallableStatement cs,ResultSet rs){
        try {
            if(cs!=null){
                cs.close();
            }
            if(rs!=null){
                rs.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    /**
     * 创建jdbc调用存储过程语句
     * @param procName 存储过程名字
     * @param obj 参数
     * @return 返回jdbc调用存储过程的语句
     */
    private String createProcStatement(String procName,Object[] params){
    	StringBuffer sb = new StringBuffer("{ CALL ").append(procName);
    	if (params != null && params.length > 0) {
    		sb.append("(?");
            for (int i = 1; i < params.length; i++) {
                sb.append(",?");
            }
            sb.append(")");
        }
        sb.append(" }");
        return sb.toString();
    }
    
    /**
	 * 调用存储过程，返回0个或多个结果集。
	 * 多个结果集用list封装，
	 * 每个结果集中的每条记录用map封装，
	 * 列名作为key，值作为value；
	 * @param procName 存储过程名称
	 * @param params 参数
	 * @param columnNum 返回的列数
	 * @return 返回多个结果集，没有结果集则返回null
	 */
    @Override
	public List<List<Map<String, Object>>> callProceMulti(final String procName,final Object[] params){
    	final List<List<Map<String, Object>>> result = new ArrayList<List<Map<String, Object>>>();
        try {
            Session session = getSession();
            session.doWork(new Work(){
                public void execute(Connection conn) throws SQLException {
                    CallableStatement cs=null;
                    ResultSet rs=null;
                    cs = conn.prepareCall(createProcStatement(procName,params));
                    if(null!=params && params.length>0){
                    	for(int i=1;i<=params.length;i++){
                            cs.setObject(i, params[i-1]);
                        }
                    }
                    boolean hadResults = cs.execute();
                    ResultSetMetaData metaData = null;
                    while(hadResults){//遍历结果集
                        List<Map<String, Object>> rsList = new ArrayList<Map<String, Object>>();//用于装该结果集的内容
                        rs = cs.getResultSet();//获取当前结果集
                        metaData=rs.getMetaData();
                        int colCount=metaData.getColumnCount();//获取当前结果集的列数
                        while(rs.next()){
                            Map<String, Object> map = new HashMap<String, Object>();
                            for(int i=1;i<=colCount;i++){
                                String colName=metaData.getColumnName(i);
                                map.put(colName, rs.getObject(colName));
                            }
                            rsList.add(map);
                        }
                        result.add(rsList);
                        close(null, rs);//遍历完一个结果集，将其关闭
                        hadResults=cs.getMoreResults();//移到下一个结果集
                    }
                    close(cs, rs);
                }
            });
            return result.size() == 0? null:result;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
	 * 调用存储过程，返回0个或1个结果集。
	 * 结果集中的每条记录用map封装，
	 * 列名作为key，值作为value；
	 * @param procName 存储过程名称
	 * @param params 参数
	 * @param columnNum 返回的列数
	 * @return 返回1个结果集中的记录，没有结果集则返回null
	 */
    @Override
	public List<Map<String, Object>> callProceSingle(final String procName,final Object[] params){
    	List<List<Map<String, Object>>> resultSets = callProceMulti(procName, params);
    	return (resultSets!=null && resultSets.size()>0 /*&& resultSets.size()==1*/) ? resultSets.get(0):null ;
    }

    /**
	 * 调用无返回结果的存储过程
	 * @param procName 存储过程名称
	 * @param params 参数
	 */
    @Override
	public void callProceVoid(final String procName,final Object[] params){
    	Session session = getSession();
    	session.doWork(new Work() {
			@Override
			public void execute(Connection connection) throws SQLException {
				CallableStatement cs = connection.prepareCall(createProcStatement(procName, params));
				if(null!=params && params.length>0){
                	for(int i=1;i<=params.length;i++){
                        cs.setObject(i, params[i-1]);
                    }
                }
				cs.execute();
				close(cs, null);
			}
		});
    }
    
    /**
	 * 调用有out,inout输出参数的存储过程，
	 * @param procName 存储过程名
	 * @param param 参数；需要输出的参数传入规则：
	 * 							out型参数：参数名；
	 * 							inout型参数：参数名_参数；
	 * 							注意：参数名称不可相同，且参数顺序和存储过程参数顺序必须一致；
	 * 							eg: new Object[]{"参数1","参数名1","参数2","参数名2_参数3" }
	 * @return 返回输出参数结果，以参数名作为key，结果作为value的Map；
	 */
    @Override
	public Map<String,Object> callProceOutParam(final String procName,final Object[] param){
    	final Map<String,Object> result = new HashMap<String,Object>();
    	try {
    		Session session = getSession();
        	session.doWork(new Work(){
    			@Override
    			public void execute(Connection connection) throws SQLException {
    				CallableStatement cs = connection.prepareCall(createProcStatement(procName, param));
    				ParameterMetaData paraMeta = cs.getParameterMetaData();
    				//组装参数
    				Map<String,Integer> outParaInfo = new HashMap<String,Integer>();//输出参数名称和位置信息
    				if(param!=null&&param.length>0){
    					for(int i=1;i<=param.length;i++){
    						int paramMode = paraMeta.getParameterMode(i);
    						switch (paramMode) {
    						case ParameterMetaData.parameterModeOut : {
    									int paramType = paraMeta.getParameterType(i);
    									cs.registerOutParameter(i, paramType);
    									outParaInfo.put(param[i-1].toString(), i);
    									break;
    								}
    						case ParameterMetaData.parameterModeInOut : {
    									int paramType = paraMeta.getParameterType(i);
    									cs.setObject(i, param[i-1].toString().substring(param[i].toString().indexOf("_")+1),paramType);
    									cs.registerOutParameter(i, paramType);
    									outParaInfo.put(param[i-1].toString().split("_")[0], i);
    									break;
    								}
    						default:
    							cs.setObject(i, param[i-1]);
    							break;
    						}
                        }
    				}
    				cs.execute();
    				//组装返回结果
    				if(outParaInfo!=null && outParaInfo.size()>0){
    					for(Map.Entry<String, Integer> op : outParaInfo.entrySet()){
    						String paraName = op.getKey();
    						int paraPostion = op.getValue().intValue();
    						result.put(paraName, cs.getObject(paraPostion));
    					}
    				}
    				close(cs, null);
    			}
    		});
        	return result.size() == 0? null:result;
		} catch (Exception e) {
			e.printStackTrace();
		}
    	return null;
    }
    
    /**
	 * 存储过程调用。适用于有返回结果（结果集，输出型参数返回）和无返回结果的各种情况下的存储过程；
	 * 
	 * @param procName 存储过程名称；
	 * 
	 * @param param 存储过程参数，参数规则：
	 * 							1，参数顺序必须和存储过程参数顺序一致；
	 * 							2，out型参数传入规则：参数名；
	 * 							3，inout型参数传入规则：参数名_参数；
	 * 							例子：new Object[]{"参数1","参数名1","参数2","参数名2_参数3" }
	 * 
	 * @return 返回结果集（结果集可能有多个），out,inout参数返回值，无任何返回结果则返回null；返回结果使用map封装；
	 * 			1，结果集的key为：resultSets；value是一个List<List<Map<String,Object>>>，封装的是多个结果集，
	 * 				 每个结果集里的每条记录是一个Map<String,Object>，key为列名的小写，注意：列名为小写，value为值；
	 * 			2，out,inout参数型返回值的key为：outValues；value是一个Map<String,Object>,该map的key为参数名，value为其返回的值。
	 * 
	 */
    @Override
	public Map<String,Object> callProce(final String procName,final Object[] param){
    	final Map<String,Object> result = new HashMap<String,Object>();
        try {
            Session session = getSession();
            session.doWork(new Work(){
                public void execute(Connection conn) throws SQLException {
                	ResultSet rs=null;
                	CallableStatement cs=null;
                    cs = conn.prepareCall(createProcStatement(procName,param));
                    
    				//组装参数 START
                    ParameterMetaData paraMeta = cs.getParameterMetaData();
    				Map<String,Integer> outParaInfo = new HashMap<String,Integer>();    //输出参数的名称和位置信息
    				if(param!=null&&param.length>0){
    					for(int i=1;i<=param.length;i++){
    						int paramMode = paraMeta.getParameterMode(i);
    						switch (paramMode) {
    						case ParameterMetaData.parameterModeOut : {
    									int paramType = paraMeta.getParameterType(i);
    									cs.registerOutParameter(i, paramType);
    									outParaInfo.put(param[i-1].toString(), i);
    									break;
    								}
    						case ParameterMetaData.parameterModeInOut : {
    									int paramType = paraMeta.getParameterType(i);
    									//将参数名和参数值分开，并将参数值设置进去
    									cs.setObject(i, param[i-1].toString().substring(param[i].toString().indexOf("_")+1),paramType);
    									cs.registerOutParameter(i, paramType);
    									//拿出参数名作为map的可以
    									outParaInfo.put(param[i-1].toString().split("_")[0], i);
    									break;
    								}
    						default:
    							cs.setObject(i, param[i-1]);
    							break;
    						}
                        }
    				}
                    //组装参数 END
    				
                    boolean hadResults = cs.execute();
                    
                    //out,inout参数型的返回结果
                    Map<String,Object> outParamResult = null;
    				if(outParaInfo!=null && outParaInfo.size()>0){
    					outParamResult = new HashMap<String,Object>();
    					for(Map.Entry<String, Integer> op : outParaInfo.entrySet()){
    						String paraName = op.getKey();
    						int paraPostion = op.getValue().intValue();
    						outParamResult.put(paraName, cs.getObject(paraPostion));
    					}
    				}
    				
    				//将out,inout参数的返回结果组装进返回结果；
    				if(outParamResult!=null){
    					result.put("outValues", outParamResult);
    				}
                    
    				//组装结果集  开始
    				List<List<Map<String, Object>>> resultSetsResult = new ArrayList<List<Map<String, Object>>>(); //结果集型的返回结果
                    ResultSetMetaData metaData = null;
                    while(hadResults){//遍历结果集
                        List<Map<String, Object>> rsList = new ArrayList<Map<String, Object>>();//用于组装该结果集的内容
                        rs = cs.getResultSet();//获取当前结果集
                        metaData=rs.getMetaData();
                        int colCount=metaData.getColumnCount();//获取当前结果集的列数
                        while(rs.next()){
                            Map<String, Object> map = new HashMap<String, Object>();
                            for(int i=1;i<=colCount;i++){
                                String colName=metaData.getColumnName(i);
                                map.put(colName.toLowerCase(), rs.getObject(colName));
                            }
                            rsList.add(map);
                        }
                        resultSetsResult.add(rsList);
                        close(null, rs);//遍历完一个结果集，将其关闭
                        hadResults=cs.getMoreResults();//移到下一个结果集
                    }
                    //组装结果集  结束
                    
                    //将结果集的返回结果组装进返回结果；
                    if(resultSetsResult!=null &&resultSetsResult.size() > 0){
                    	result.put("resultSets", resultSetsResult);
                    }
                    
                    close(cs, rs);//释放资源
                }
            });
            
            return result.size() == 0 ? null:result;//返回最终结果
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    
    /**
	 * 获得hibernate的sessionFactory级别的二级缓存；
	 * @return
	 */
    @Override
	public Cache getSecondCache(){
    	Cache secondCache = this.sessionFactory.getCache();
    	return secondCache;
    }
    
    /**
	 * 清理二级缓存，释放其占用的内存资源;
	 * 将某个类的指定ID的持久化对象从二级缓存中清除，
	 * 如果id为空则将指定类的所有持久化对象从二级缓存中清除，
	 * @param clazz 指定类
	 * @param id 指定的id
	 * 
	 */
    @Override
	public void clearSecondEntityCache(Class<?> clazz,Serializable id) {
    	Cache secondCache =  this.sessionFactory.getCache();
    	if(id == null){
    		secondCache.evictEntityRegion(clazz);
    	}else{
    		secondCache.evictEntity(clazz, id);
    	}
    }

    /**
	 * 将指定类的所有持久化对象的指定集合从二级缓存中清除，
	 * 释放其占用的内存资源. 
	 * 
	 * @param role eg:   Customer.orders
	 * 
	 */
    @Override
	public void clearSecondCollectionCache(String role) {
    	Cache secondCache =  this.sessionFactory.getCache();
    	if(role == null || role.trim().equals("") ){
    		secondCache.evictCollectionRegions();
    	}else{
    		secondCache.evictCollectionRegion(role);
    	}
    }

}
