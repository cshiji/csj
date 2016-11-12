package com.cshiji.superviki.base.mybatis.pagedialect;

/**
 * SQLSERVER2005的数据库方言类<BR>
 * 主要用于在iBatis分页机制中获取当前数据库自身的分页语句<BR>
 * 传入的sql语句中，要保证有order by参数，最好是id或者其它绝对不会重复的字段<BR>
 */
public class SQLServerDialect implements IDialect {

	/**
	 * 自动建立的字段: INDEX
	 */
	private static final int INDEX = 15;

	/**
	 * 自动建立的字段: LENGTH_OFFSET
	 */
	private static final int LENGTH_OFFSET = 23;

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
		if (offset == 0) { // no offset , use top , 这时不能支持limit参数绑定
			return new StringBuffer(sql.length() + 8).append(sql)
					.insert(getSqlAfterSelectInsertPoint(sql), " top " + limit).toString();
		} else {
			int orderByIndex = sql.toLowerCase().lastIndexOf("order by");

			if (orderByIndex <= 0) {
				throw new UnsupportedOperationException(
						"must specify 'order by' statement to support limit operation with offset i"
								+ "n sql server 2005");
			}

			String sqlOrderBy = sql.substring(orderByIndex + 8);
			String sqlRemoveOrderBy = sql.substring(0, orderByIndex);
			int insertPoint = getSqlAfterSelectInsertPoint(sql);
			return new StringBuffer(sql.length() + 100)
					.append("with tempPagination as(")
					.append(sqlRemoveOrderBy)
					.insert(insertPoint + LENGTH_OFFSET,
							" ROW_NUMBER() OVER(ORDER BY " + sqlOrderBy + ") as RowNumber,")
					.append(") select * from tempPagination where RowNumber between "
							+ (offset + 1) + " and " + (offset + limit)).toString();
		}

	}

	/**
	 * 
	 * @param querySelect
	 * @param offset
	 * @param offsetPlaceholder
	 * @param limit
	 * @param limitPlaceholder
	 * @return
	 */
	public String getPagedString(String querySelect, int offset, String offsetPlaceholder,
			int limit, String limitPlaceholder) {
		if (offset > 0) {
			throw new UnsupportedOperationException("sql server has no offset");
		}

		return new StringBuffer(querySelect.length() + 8).append(querySelect)
				.insert(getSqlAfterSelectInsertPoint(querySelect), " top " + limit).toString();
	}

	/**
	 * 获取sql中select子句位置
	 */
	protected static int getSqlAfterSelectInsertPoint(String sql) {
		int selectIndex = sql.toLowerCase().indexOf("select");
		final int selectDistinctIndex = sql.toLowerCase().indexOf("select distinct");
		return selectIndex + (selectDistinctIndex == selectIndex ? INDEX : 6);
	}

	/**
	 * 是否支持分页
	 */
	public boolean supportsPaged() {
		return true;
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
