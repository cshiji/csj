/**
 * FileName:     PagePlugin.java
 * @Description: 分页实现类
 * All rights Reserved, Designed By hylandtec
 * Copyright:    Copyright(C) 2011-2014
 * Company       hylandtec
 * @author:      YangFan
 * @version      V1.0 
 * Createdate:   2014-2-18 下午6:19:37
 *
 * Modification  History:
 * Date         Author        Version        Discription
 * -----------------------------------------------------------------------------------
 * 2014-2-18      YangFan          1.0             1.0
 * Why & What is modified: <修改原因描述>
 */
package com.cshiji.superviki.base.mybatis;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.ibatis.executor.ErrorContext;
import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.executor.ExecutorException;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.ParameterMapping;
import org.apache.ibatis.mapping.ParameterMode;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Plugin;
import org.apache.ibatis.plugin.Signature;
import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.reflection.property.PropertyTokenizer;
import org.apache.ibatis.scripting.xmltags.ForEachSqlNode;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.ResultHandler;
import org.apache.ibatis.session.RowBounds;
import org.apache.ibatis.type.TypeHandler;
import org.apache.ibatis.type.TypeHandlerRegistry;

import com.cshiji.superviki.base.mybatis.pagedialect.IDialect;
import com.cshiji.superviki.base.util.ReflectHelper;


/**
 * @ClassName: PagePlugin
 * @Description: 分页实现类
 */
@Intercepts({ @Signature(type = Executor.class, method = "query", args = { MappedStatement.class, Object.class, 
	RowBounds.class, ResultHandler.class }) })
public class PageInterceptor implements Interceptor {
	private Log logger = LogFactory.getLog(PageInterceptor.class);
	
	private String dialectClass;
	
	@Override
	public void setProperties(Properties properties) {
		this.dialectClass = properties.getProperty("dialectClass");
	}
	
	@Override
	public Object intercept(Invocation invocation) throws Throwable {
		Executor executor = (Executor) invocation.getTarget();
		MappedStatement mappedStatement = (MappedStatement) invocation.getArgs()[0];
		Object paramObject = invocation.getArgs()[1];
		RowBounds rowBounds = (RowBounds) invocation.getArgs()[2];
		ResultHandler resultHandler = (ResultHandler) invocation.getArgs()[3];
		BoundSql boundSql = mappedStatement.getBoundSql(paramObject);
		if (paramObject instanceof Map) {
			//从参数中获取pager对象
			Map map = (Map) paramObject;
			Iterator pages = map.entrySet().iterator();
			Pager page = null;
			for (Iterator iterator = pages; iterator.hasNext();) {
				Map.Entry<String, Object> mapPage = (Map.Entry<String, Object>) iterator.next();
				if (mapPage.getValue() instanceof Pager) {
					page = (Pager) mapPage.getValue();
					break;
				}
			}
			//page对象不为空，进行分页查询
			if (page != null) {
				int pageNo = page.getPageNo();
				int pageSize = page.getPageSize();
				IDialect dialect = this.getDialect(executor.getTransaction().getConnection());
				
				String countSql = dialect.getCountString(boundSql.getSql());
				String pageSql = dialect.getPagedString(boundSql.getSql(), (int) page.getStart(), page.getPageSize());
				
				// 需要缓冲和不需要缓存的处理
				// 获取记录总数
				Connection connection = executor.getTransaction().getConnection();  
                PreparedStatement countStmt = connection.prepareStatement(countSql);  
                logger.debug("search sql:"+boundSql.getSql());
                logger.debug("search count sql:"+countSql);
                BoundSql countBS = this.copyFromBoundSql(mappedStatement, boundSql, countSql);
                
                this.setParameters(countStmt,mappedStatement,countBS,paramObject);  
                ResultSet rs = countStmt.executeQuery();  
                int count = 0;  
                if (rs.next()) {  
                    count = rs.getInt(1);  
                }  
                rs.close();  
                countStmt.close(); 
				int totalCount = count;
				if (totalCount < 1) {
					page.setPageNo(1);
					page.setTotal(0);
				} else {
					// 计算总页数
					int totalPageCount = 0;
					if (totalCount % pageSize == 0) {
						totalPageCount = totalCount / pageSize;
					} else {
						totalPageCount = totalCount / pageSize + 1;
					}
					if (pageNo > totalPageCount) {
						page.setPageNo(totalPageCount);
					}
				}
				page.setStart(page.getPageNo(), page.getPageSize());
				page.setTotal(totalCount);
				this.logger.debug(pageSql);
				ReflectHelper.setValueByFieldName(boundSql, "sql", pageSql);//将分页sql语句反射回BoundSql.  
				List<Object> list = executor.query(mappedStatement, paramObject, rowBounds, resultHandler, null, boundSql);
				page.setRows(list);
				return list;
			} else {
				return executor.query(mappedStatement, paramObject, rowBounds, resultHandler, null, boundSql);
			}
		}
		
		return executor.query(mappedStatement, paramObject, rowBounds, resultHandler, null, boundSql);
	}

	@Override
	public Object plugin(Object arg0) {
		return Plugin.wrap(arg0, this);
	}
	
	 /** 
     * 对SQL参数(?)设值,参考org.apache.ibatis.executor.parameter.DefaultParameterHandler 
     * @param ps 
     * @param mappedStatement 
     * @param boundSql 
     * @param parameterObject 
     * @throws SQLException 
     */  
    private void setParameters(PreparedStatement ps,MappedStatement mappedStatement,BoundSql boundSql,Object parameterObject) throws SQLException {  
        ErrorContext.instance().activity("setting parameters").object(mappedStatement.getParameterMap().getId());  
        List<ParameterMapping> parameterMappings = boundSql.getParameterMappings();  
        if (parameterMappings != null) {  
            Configuration configuration = mappedStatement.getConfiguration();  
            TypeHandlerRegistry typeHandlerRegistry = configuration.getTypeHandlerRegistry();  
            MetaObject metaObject = parameterObject == null ? null: configuration.newMetaObject(parameterObject);  
            for (int i = 0; i < parameterMappings.size(); i++) {  
                ParameterMapping parameterMapping = parameterMappings.get(i);  
                if (parameterMapping.getMode() != ParameterMode.OUT) {  
                    Object value;  
                    String propertyName = parameterMapping.getProperty();  
                    PropertyTokenizer prop = new PropertyTokenizer(propertyName);  
                    if (parameterObject == null) {  
                        value = null;  
                    } else if (typeHandlerRegistry.hasTypeHandler(parameterObject.getClass())) {  
                        value = parameterObject;  
                    } else if (boundSql.hasAdditionalParameter(propertyName)) {  
                        value = boundSql.getAdditionalParameter(propertyName);  
                    } else if (propertyName.startsWith(ForEachSqlNode.ITEM_PREFIX)&& boundSql.hasAdditionalParameter(prop.getName())) {  
                        value = boundSql.getAdditionalParameter(prop.getName());  
                        if (value != null) {  
                            value = configuration.newMetaObject(value).getValue(propertyName.substring(prop.getName().length()));  
                        }  
                    } else {  
                        value = metaObject == null ? null : metaObject.getValue(propertyName);  
                    }  
                    TypeHandler typeHandler = parameterMapping.getTypeHandler();  
                    if (typeHandler == null) {  
                        throw new ExecutorException("There was no TypeHandler found for parameter "+ propertyName + " of statement "+ mappedStatement.getId());  
                    }  
                    typeHandler.setParameter(ps, i + 1, value, parameterMapping.getJdbcType());  
                }  
            }  
        }  
    }  
      
    /**
	 * 初始化方言
	 * 
	 * @param invocation
	 */
	private IDialect getDialect(Connection conn) throws SQLException, InstantiationException, IllegalAccessException, ClassNotFoundException {
		String dialectType = "MySQLDialect";
		String packagePath = "com.tk.base.mybatis.pagedialect.";
		// 无需关闭
		// 获取数据产品名称
		String databaseProductName = conn.getMetaData().getDatabaseProductName();
		
		if ("MySQL".equalsIgnoreCase(databaseProductName)) {// 1、MySQL：数据库名称为MySQL
			dialectType = "MySQLDialect";
			
		} else if ("Oracle".equalsIgnoreCase(databaseProductName)) { // 2、Oracle：数据库名称为Oracle
			dialectType = "OracleDialect";
			
		} else if ("DB2".equalsIgnoreCase(databaseProductName)) {// 3、DB2：数据库名称为db2
			dialectType = "DB2Dialect";
			
		} else if ("Informix".equalsIgnoreCase(databaseProductName)) {// 4、Informix：数据库名称为Informix
			dialectType = "InformixDialect";
			
		} else if ("SQLServer".equalsIgnoreCase(databaseProductName)) {// 5、SQLServer：数据库名称为SQLServer
			dialectType = "SQLServerDialect";
			
		} else if ("SQLServer2000".equalsIgnoreCase(databaseProductName)) {// 6、SQLServer2000：数据库名称为SQLServer2000
			dialectType = "SQLServer2000Dialect";
			
		} else if ("Sybase".equalsIgnoreCase(databaseProductName)) {// 7、Sybase：数据库名称为Sybase
			dialectType = "SybaseDialect";
		}
		if(dialectClass == null || "".equals(dialectClass)){
			logger.debug("dialectClass为空，采用自动判断方言");
			dialectClass = packagePath + dialectType;
			logger.debug("自动判断方言为:"+dialectClass);
		}
		
		IDialect dialect = (IDialect) Class.forName(dialectClass).newInstance();
		return dialect;
	}
	
	  /** 
	   * 复制BoundSql对象 
	   */  
	  private BoundSql copyFromBoundSql(MappedStatement ms, BoundSql boundSql, String sql) {  
		BoundSql newBoundSql = new BoundSql(ms.getConfiguration(),sql, boundSql.getParameterMappings(), boundSql.getParameterObject());  
		for (ParameterMapping mapping : boundSql.getParameterMappings()) {  
		    String prop = mapping.getProperty();  
		    if (boundSql.hasAdditionalParameter(prop)) {  
		        newBoundSql.setAdditionalParameter(prop, boundSql.getAdditionalParameter(prop));  
		    }  
		}  
		return newBoundSql;  
	  }  

}
