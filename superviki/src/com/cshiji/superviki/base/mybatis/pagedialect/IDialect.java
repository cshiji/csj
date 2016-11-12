package com.cshiji.superviki.base.mybatis.pagedialect;


/**
 * 数据库方言接口，用于区别不同的数据库的分页机制
 */
public interface IDialect {

	/**
	 * 数据库本身是否支持分页机制<br>
	 * 1:MySQL支持limit方式，,Oracle和SQL Server2005支持rownum方式，属于真正的数据库分页<br>
	 * 2：informix支持first,Sybase 12.5.3和SQL Server等，只支持top，不是真正的数据库分页<br>
	 * 
	 * @return 返回布尔结果
	 */
	public boolean supportsPaged();

	/**
	 * 提供参数化的分页语句 结果类似：select a,b,c from table limit ?,?;
	 * 
	 * @param sql
	 *            原生sql查询语句
	 * @param hasOffset
	 *            是否需要分页
	 * @return 配置好分页后的sql语句
	 */
	public String getPagedString(String sql, boolean hasOffset);

	/**
	 * 提供最终的分页语句 结果类似：select a,b,c from table limit 1,10;
	 * 
	 * @param sql
	 *            原生sql查询语句
	 * @param offset
	 *            起始位置，offset在当前框架中是从intelliweb传过来的，从0开始
	 * @param limit
	 *            需要取出的记录数
	 * @return 返回结果
	 */
	public String getPagedString(String sql, int offset, int limit);

	/**
	 * 将sql转换为总记录数SQL
	 * 
	 * @param sql
	 *            SQL语句
	 * @return 总记录数的sql
	 */
	public String getCountString(String sql);

}
