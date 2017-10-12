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
	
	private static String driver ="com.mysql.jdbc.Driver";
	private static String username = "root";
	private static String password = "root!@#$";
	private static String db = "test";
	private static String url = "jdbc:mysql://10.134.105.155:3306/"+db;

	public static String getCamelName(String name) {
		StringBuilder result = new StringBuilder();
		String names[] = name.split("_");
		for (String item : names) {
			if (item.length() > 0)
				result.append(item.substring(0, 1).toUpperCase() + item.substring(1));
		}
		return result.toString();
	}
	
	public static List<ColumnVO> getColumns(String tableName) throws SQLException{
		return getColumns(driver, url, username, password, tableName,db);
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
							+ table.toLowerCase() + "' and table_schema='" + db.toLowerCase() + "'");
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
				} else if ("decimal".equals(dataType)||"float".equals(dataType)) {
					vo.setType("Float");
				}else if ("Double".equals(dataType)) {
					vo.setType("Double");
				} else {
					vo.setType("String");
				}
				vo.setDbField(rs.getString("column_name"));
				vo.setComment(rs.getString("column_comment"));
				vo.setName(getCamelName(rs.getString("column_name")));
				vo.setField(vo.getName().substring(0, 1).toLowerCase() + vo.getName().substring(1));
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

	public static void main(String[] args) throws SQLException {
		System.out.println(getCamelName("hello_nihao_zhongguo_ren"));
		List<ColumnVO> cols = getColumns("game_kaifu");
		System.out.println("-----");
		for(ColumnVO col:cols) {
			System.out.println(col.getName());
		}
		
	}

}
