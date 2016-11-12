package com.cshiji.superviki.base.mybatis.pagedialect;


/**
 * MySQL数据库方言
 */
public class MySQLDialect implements IDialect {
	protected static final String SQL_END_DELIMITER = ";";

	/**
	 * 自动建立的字段: increaseLength
	 */
	private static final int INCREASELENGTH = 20;

	/**
	 * 获取分页SQL串
	 */
	public String getPagedString(String sql, boolean hasOffset) {
		return new StringBuffer(sql.length() + INCREASELENGTH).append(trim(sql))
				.append(hasOffset ? " limit ?,?" : " limit ?").append(SQL_END_DELIMITER).toString();
	}

	/**
	 * 获取分页SQL串
	 */
	public String getPagedString(String sql, int offset, int limit) {
		sql = trim(sql);
		StringBuffer sb = new StringBuffer(sql.length() + INCREASELENGTH);
		sb.append(sql);
		if (offset > 0) {
			sb.append(" limit ").append(offset).append(',').append(limit).append(SQL_END_DELIMITER);
		} else {
			sb.append(" limit ").append(limit).append(SQL_END_DELIMITER);
		}
		return sb.toString();
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
	public String getLimitString(String sql, int offset, String offsetPlaceholder, int limit,
			String limitPlaceholder) {
		if (offset > 0) {
			return sql + " limit " + offsetPlaceholder + "," + limitPlaceholder;
		} else {
			return sql + " limit " + limitPlaceholder;
		}
	}

	/**
	 * 是否支持分页
	 */
	public boolean supportsPaged() {
		return true;
	}

	/**
	 * 是否支持分页
	 */
	private String trim(String sql) {
		sql = sql.trim();
		if (sql.endsWith(SQL_END_DELIMITER)) {
			sql = sql.substring(0, sql.length() - 1 - SQL_END_DELIMITER.length());
		}
		return sql;
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
