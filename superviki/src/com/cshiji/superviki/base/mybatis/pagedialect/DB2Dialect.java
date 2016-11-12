package com.cshiji.superviki.base.mybatis.pagedialect;

/**
 * 数据库方言DB2
 */
public class DB2Dialect implements IDialect {

	/**
	 * 自动建立的字段: BUFFER_LENGTH
	 */
	private static final int BUFFER_LENGTH = 50;

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
		return getPagedString(sql, offset, "", limit, "");
	}

	/**
	 * 获取分页SQL串
	 */
	public String getPagedString(String sql, int offset, String offsetPlaceholder, int limit,
			String limitPlaceholder) {
		int startOfSelect = sql.toLowerCase().indexOf("select");

		StringBuffer pagingSelect = new StringBuffer(sql.length() + 100)
				.append(sql.substring(0, startOfSelect)) // add the comment
				.append("select * from ( select ") // nest the main query in an
													// outer select
				.append(getRowNumber(sql)); // add the rownnumber bit into the
											// outer query select list

		if (hasDistinct(sql)) {
			pagingSelect.append(" row_.* from ( ") // add another (inner) nested
													// select
					.append(sql.substring(startOfSelect)) // add the main query
					.append(" ) as row_"); // close off the inner nested select
		} else {
			pagingSelect.append(sql.substring(startOfSelect + 6)); // add the
																	// main
																	// query
		}

		pagingSelect.append(" ) as temp_ where rownumber_ ");

		// add the restriction to the outer select
		if (offset > 0) {
			// int end = offset + limit;
			String endString = offsetPlaceholder + "+" + limitPlaceholder;
			pagingSelect.append("between " + offsetPlaceholder + "+1 and " + endString);
		} else {
			pagingSelect.append("<= " + limitPlaceholder);
		}

		return pagingSelect.toString();
	}

	/**
	 * 是否支持分页
	 */
	public boolean supportsPaged() {
		return false;
	}

	/**
	 * Render the <tt>rownumber() over ( .... ) as rownumber_,</tt> bit, that
	 * goes in the select list
	 */
	private String getRowNumber(String sql) {
		StringBuffer rownumber = new StringBuffer(BUFFER_LENGTH).append("rownumber() over(");

		int orderByIndex = sql.toLowerCase().indexOf("order by");

		if (orderByIndex > 0 && !hasDistinct(sql)) {
			rownumber.append(sql.substring(orderByIndex));
		}

		rownumber.append(") as rownumber_,");

		return rownumber.toString();
	}

	/**
	 * 是否distinct
	 */
	private static boolean hasDistinct(String sql) {
		return sql.toLowerCase().indexOf("select distinct") >= 0;
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
