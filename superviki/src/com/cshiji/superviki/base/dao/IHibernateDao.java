package com.cshiji.superviki.base.dao;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import org.hibernate.Cache;
import org.hibernate.Session;

import com.cshiji.superviki.base.mybatis.Pager;

/**
 * hibernateDao实现
 * @author 
 *
 */
public interface IHibernateDao extends IBaseDao {

	/**
	 * 获取session
	 * @return
	 */
	public abstract Session getSession();

	public abstract void save(Object obj);

	public abstract void saveOrUpdate(Object obj);

	/**
	 * 批量保存
	 * 
	 * @param collection 集合
	 * @param clearTimePer 清空批次
	 * 
	 * @return 受影响行数
	 */
	public abstract int saveAll(Collection<?> collection, int clearTimePer);

	public abstract void update(Object obj);

	/**
	 * 删除一个已经持久化的实例对象
	 * @param obj
	 */
	public abstract void delete(Object obj);

	/**
	 * 根据ID删除
	 * @param id
	 * @param clazz
	 */
	public abstract void deleteById(Serializable id, Class<?> clazz);

	/**
	 * 批量删除数据
	 * @param objectsToRemove
	 * @throws DataAccessException
	 */
	public abstract void batchRemove(List<?> objsList, int batchSize);

	/**
	 * 根据ids批量删除记录
	 * @param clazz 要删除的对象class
	 * @param idName 对象属性的主键名称
	 * @param ids 多个id
	 * @return 返回受影响的记录条数
	 */
	public abstract int batchRemoveByIds(Class<?> clazz, String idName, Object[] ids);

	public abstract Object get(Serializable id, Class<?> clazz);

	public abstract Object load(Serializable id, Class<?> clazz);

	/**
	 * 合并，返回一个更新过的持久化实例 
	 */
	public abstract Object merge(Object obj);

	/**
	 * hql的复杂查询，返回多条记录
	 * @param hql
	 * @param param
	 * @return 返回多条记录
	 */
	public abstract <T> List<T> findListByHql(String hql, Map<String, Object> paramMap);

	/**
	 * 查询一条记录
	 * @param hql
	 * @param paramMap
	 * @return
	 */
	public abstract Object findObject(String hql, Map<String, Object> paramMap);

	/**
	 * 查询count
	 * @param hql 只有一个返回结果的countHql语句
	 * @param paramMap
	 * @return
	 */
	public abstract int queryCountByHql(String countHql, Map<String, Object> paramMap);

	/**
	 * 分页查询，
	 * @param hql 查询语句
	 * @param paraMap 参数
	 * @param pageNo 当前页
	 * @param pageSize pageSize
	 * @return
	 */
	public abstract Pager<?> pageByHql(String hql, Map<String, Object> paraMap, int pageNo, int pageSize);

	/**
	 * 通过hql执行update或者delete
	 * @param hql 更新或者删除hql语句
	 * @param paramMap 参数
	 * @return 返回受影响条数
	 */
	public abstract int updateOrDeleteByHql(String hql, Map<String, Object> paramMap);

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
	public abstract List<List<Map<String, Object>>> callProceMulti( String procName, Object[] params);

	/**
	 * 调用存储过程，返回0个或1个结果集。
	 * 结果集中的每条记录用map封装，
	 * 列名作为key，值作为value；
	 * @param procName 存储过程名称
	 * @param params 参数
	 * @param columnNum 返回的列数
	 * @return 返回1个结果集中的记录，没有结果集则返回null
	 */
	public abstract List<Map<String, Object>> callProceSingle(String procName, Object[] params);

	/**
	 * 调用无返回结果的存储过程
	 * @param procName 存储过程名称
	 * @param params 参数
	 */
	public abstract void callProceVoid(String procName, Object[] params);

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
	public abstract Map<String, Object> callProceOutParam(String procName, Object[] param);

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
	public abstract Map<String, Object> callProce(String procName, Object[] param);

	/**
	 * 获得hibernate的sessionFactory级别的二级缓存；
	 * @return
	 */
	public abstract Cache getSecondCache();

	/**
	 * 清理二级缓存，释放其占用的内存资源;
	 * 将某个类的指定ID的持久化对象从二级缓存中清除，
	 * 如果id为空则将指定类的所有持久化对象从二级缓存中清除，
	 * @param clazz 指定类
	 * @param id 指定的id
	 * 
	 */
	public abstract void clearSecondEntityCache(Class<?> clazz, Serializable id);

	/**
	 * 将指定类的所有持久化对象的指定集合从二级缓存中清除，
	 * 释放其占用的内存资源. 
	 * 
	 * @param role eg:   Customer.orders
	 * 
	 */
	public abstract void clearSecondCollectionCache(String role);
	
}
