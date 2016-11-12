package com.cshiji.superviki.base.mybatis.pagedialect;

/**
 * Sybase的数据库方言类
 */
public class SybaseDialect implements IDialect {
	/**
	 * 获取分页SQL串
	 */
	public String getPagedString(String sql, boolean hasOffset) {
		return null;
	}

	/**
	 * 获取分页SQL串
	 */
	public String getPagedString(String sql, int offset, int limit) {

		return null;
	}

	/**
	 * 
	 * @param sql
	 * @param offset
	 * @param offsetPlaceholder
	 * @param limit
	 * @param limitPlaceholder
	 * @return
	 */
	public String getPagedString(String sql, int offset, String offsetPlaceholder, int limit,
			String limitPlaceholder) {
		throw new UnsupportedOperationException("paged queries not supported");
	}

	/**
	 * 是否支持分页
	 */
	public boolean supportsPaged() {
		return false;
	}

	/**
	 * 将sql转换为总记录数SQL
	 * 
	 * @param sql
	 *            SQL语句
	 * @return 总记录数的sql
	 */
	public String getCountString(String sql) {
		return "select count(1) from (" + sql + ") tmp_count";
	}

}
