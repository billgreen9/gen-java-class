package com.sogou.code;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.sogou.code.vo.ColumnVO;

public class GetTableInfo {

	public static String getCamelName(String name) {
		StringBuilder result = new StringBuilder();
		String names[] = name.split("_");
		for (String item : names) {
			if (item.length() > 0)
				result.append(item.substring(0, 1).toUpperCase() + item.substring(1));
		}
		return result.toString();
	}

	public static List<ColumnVO> getColumns(String driver, String url, String username, String password, String table,
			String db) throws SQLException {
		try {
			// 加载驱动类
			Class.forName(driver);
		} catch (ClassNotFoundException e1) {
			e1.printStackTrace();
		}
		Connection con = null;
		ResultSet rs = null;
		PreparedStatement ps = null;

		List<ColumnVO> columns = new ArrayList<ColumnVO>();
		try {
			// 将各种流放入try()中，将由try来执行close()操作
			con = DriverManager.getConnection(url, username, password);
			ps = con.prepareStatement(
					"select table_name,column_name,column_comment,data_type,column_type,column_key,column_default from INFORMATION_SCHEMA.Columns where table_name='"
							+ table + "' and table_schema='" + db + "'");
			rs = ps.executeQuery();

			while (rs.next()) {
				ColumnVO vo = new ColumnVO();
				String dataType = rs.getString("data_type");
				if ("varchar".equals(dataType)) {
					vo.setType("String");
				} else if ("int".equals(dataType) || "smallint".equals(dataType)) {
					vo.setType("Integer");
				} else if ("bigint".equals(dataType)) {
					vo.setType("Long");
				} else if ("timestamp".equals(dataType) || "datetime".equals(dataType)) {
					vo.setType("Date");
				} else {
					vo.setType("String");
				}
				vo.setComment(rs.getString("column_comment"));
				vo.setName(getCamelName(rs.getString("column_name")));
				vo.setTableName(getCamelName(rs.getString("table_name")));
				columns.add(vo);
			}
		} finally {
			if (rs != null) {
				rs.close();
			}
			if (ps != null) {
				ps.close();
			}
			if (con != null) {
				con.close();
			}
		}

		return columns;

	}

	public static void main(String[] args) {
		System.out.println(getCamelName("hello_nihao_zhongguo_ren"));
	}

}
