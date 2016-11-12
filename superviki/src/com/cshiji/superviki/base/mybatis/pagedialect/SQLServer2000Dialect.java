package com.cshiji.superviki.base.mybatis.pagedialect;


/**
 * SQLSERVER的数据库方言类
 */
public class SQLServer2000Dialect implements IDialect {

	/**
	 * 自动建立的字段: INDEX
	 */
	private static final int INDEX = 15;

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
			throw new UnsupportedOperationException("sql server has no offset");
		}
		return new StringBuffer(sql.length() + 8).append(sql)
				.insert(getAfterSelectInsertPoint(sql), " top " + limit).toString();
	}

	/**
	 * 获取sql中select子句位置
	 */
	static int getAfterSelectInsertPoint(String sql) {
		int selectIndex = sql.toLowerCase().indexOf("select");
		final int selectDistinctIndex = sql.toLowerCase().indexOf("select distinct");
		return selectIndex + (selectDistinctIndex == selectIndex ? INDEX : 6);
	}

	/**
	 * 获取分页SQL串
	 */
	public String getPagedString(String querySqlString, int offset, String offsetPlaceholder,
			int limit, String limitPlaceholder) {
		StringBuffer pagingBuilder = new StringBuffer();
		String orderby = getOrderByPart(querySqlString);
		String distinctStr = "";

		String loweredString = querySqlString.toLowerCase();
		String sqlPartString = querySqlString;
		if (loweredString.trim().startsWith("select")) {
			int index = 6;
			if (loweredString.startsWith("select distinct")) {
				distinctStr = "DISTINCT ";
				index = INDEX;
			}
			sqlPartString = sqlPartString.substring(index);
		}
		pagingBuilder.append(sqlPartString);

		// if no ORDER BY is specified use fake ORDER BY field to avoid errors
		if (orderby == null || orderby.length() == 0) {
			orderby = "ORDER BY CURRENT_TIMESTAMP";
		}

		StringBuffer result = new StringBuffer();
		result.append("WITH query AS (SELECT ").append(distinctStr).append("TOP 100 PERCENT ")
				.append(" ROW_NUMBER() OVER (").append(orderby).append(") as __row_number__, ")
				.append(pagingBuilder)
				.append(") SELECT * FROM query WHERE __row_number__ BETWEEN ").append(offset)
				.append(" AND ").append(offset + limit).append(" ORDER BY __row_number__");

		return result.toString();
	}

	/**
	 * 
	 * @param sql
	 * @return
	 */
	static String getOrderByPart(String sql) {
		String loweredString = sql.toLowerCase();
		int orderByIndex = loweredString.indexOf("order by");
		if (orderByIndex != -1) {
			return sql.substring(orderByIndex);
		} else {
			return "";
		}
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