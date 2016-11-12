package com.cshiji.superviki.base.mybatis.pagedialect;


/**
 * ORACLE数据库方言
 */
public class OracleDialect implements IDialect {

	/**
	 * 自动建立的字段: LENGTH_DECREASE
	 */
	private static final int LENGTH_DECREASE = 11;

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
		
		String oraclePagedsqlFormatter = "SELECT * FROM (SELECT A.*, ROWNUM RN FROM (@_z_#) A "
				+ ") WHERE RN BETWEEN @_y_# AND @_x_# ";
		
		//String oraclePagedsqlFormatter = "SELECT * FROM (SELECT A.*, ROWNUM RN FROM (@_z_#) A "
		//		+ "WHERE ROWNUM <= @_x_#) WHERE RN >= @_y_#";
		
//		String oraclePagedsqlFormatter = "SELECT * FROM (SELECT A.*, ROWNUM RN FROM (@_z_#) A ) "
//				+  "WHERE RN <= @_x_# AND RN >= @_y_#" ;
		
		offset = offset + 1;
		int endset = offset + limit - 1;
		String rs = oraclePagedsqlFormatter.replaceFirst("@_y_#", offset + "")
				.replaceFirst("@_x_#", endset + "").replaceFirst("@_z_#", sql);

		return rs;
	}

	/**
	 * 是否支持分页
	 */
	public boolean supportsPaged() {
		return true;
	}

	/**
	 * 获取分页SQL串
	 */
	public String getPagedString(String sql, int offset, String offsetPlaceholder, int limit,
			String limitPlaceholder) {
		sql = sql.trim();
		boolean isForUpdate = false;
		if (sql.toLowerCase().endsWith(" for update")) {
			sql = sql.substring(0, sql.length() - LENGTH_DECREASE);
			isForUpdate = true;
		}

		StringBuffer pagingSelect = new StringBuffer(sql.length() + 100);
		if (offset > 0) {
			pagingSelect.append("select * from ( select row_.*, rownum rownum_ from ( ");
		} else {
			pagingSelect.append("select * from ( ");
		}
		pagingSelect.append(sql);
		if (offset > 0) {

			String endString = offsetPlaceholder + "+" + limitPlaceholder;
			pagingSelect.append(" ) row_ ) where rownum_ <= " + endString + " and rownum_ > "
					+ offsetPlaceholder);
		} else {
			pagingSelect.append(" ) where rownum <= " + limitPlaceholder);
		}

		if (isForUpdate) {
			pagingSelect.append(" for update");
		}

		return pagingSelect.toString();
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
