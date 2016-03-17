package com.models;

import java.sql.*;
import java.util.Enumeration;
public class DBConnection {
	private static Connection connection = null;

	private static Statement stat=null;
	public static  Connection getActiveConnection() {
		try {

			Class.forName("com.mysql.jdbc.Driver");
			//connection = DriverManager
			//		.getConnection("jdbc:mysql://127.8.100.2:3306/se2firstapp?"
			//				+ "user=adminYKFs38v&password=QG9RmdNVFgmc&characterEncoding=utf8");
			/*connection = DriverManager
					.getConnection("jdbc:mysql://localhost:3306/om?useSSL=false"
							+ " user=root&password=eman&characterEncoding=utf8");*/
			connection = DriverManager
					.getConnection("jdbc:mysql://localhost:3306/se2firstapp?useSSL=false"
							,"root","root");
			 stat=connection.createStatement();
			return connection;
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	public static void ExecuteUpdate(String Query)
	{
		try {
			stat.executeUpdate(Query);
		} catch (SQLException e) {
			
			e.printStackTrace();
		}
	}
	public static ResultSet ExecuteQuery(String Query)
	{
		ResultSet res=null;
		try {
			res=stat.executeQuery(Query);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return res;
	}
	public static void Close_Connection()
	{
		try {
			connection.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}