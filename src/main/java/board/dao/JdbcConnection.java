package board.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import org.springframework.stereotype.Repository;

@Repository
public class JdbcConnection {
	
	Connection conn;
	public Connection getConnection() {
		
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			conn = DriverManager.getConnection(
					"jdbc:oracle:thin:@localhost:1521:xe","testuser", "1111");

			if (conn == null) {
				System.out.println("db���ӽ���");}
				System.out.println("db���Ӽ���");
		} catch (Exception e) {
		}
		return conn;
	}
	
	public void ConnectionClose() {
		try {
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
