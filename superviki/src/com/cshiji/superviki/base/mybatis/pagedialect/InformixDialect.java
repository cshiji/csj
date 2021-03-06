package com.cshiji.superviki.base.mybatis.pagedialect;


/**
 * Informix数据库方言
 */
public class InformixDialect implements IDialect {
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
		if (offset > 0) {
			throw new UnsupportedOperationException("informix has no offset");
		}
		return new StringBuffer(sql.length() + 8).append(sql)
				.insert(sql.toLowerCase().indexOf("select") + 6, " first " + limit).toString();
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
