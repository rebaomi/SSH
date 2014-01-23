import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class SqlConnection {
	public String serverIP = "172.20.3.16";
	public String port = "1433";
	public String serverDataBase = "TRIAL_DWH";
	public String driverName = "com.microsoft.sqlserver.jdbc.SQLServerDriver";
	public String userName = "sa";
	public String passWord = "dbadmin";
	private String connectionString = "jdbc:sqlserver://" + serverIP 
									+ ":" + port
									+ "; DatabaseName=" + serverDataBase + ";";
	
	public SqlConnection() {}
	
	public SqlConnection(String ip,
			 			 String port,
						 String dataBase,
						 String userName,
						 String passWord) {
		this.serverIP = ip;
		this.serverDataBase = dataBase;
		this.userName = userName;
		this.passWord = passWord;
		this.connectionString = "jdbc:sqlserver://" + serverIP 
							  + ":" + port
						      + "; DatabaseName=" + serverDataBase + ";";
		System.out.println(this.connectionString);
	}
	
	/**
	 * 获取数据库连接 *sql08以前
	 * @return 一个数据库连接
	 */
	public Connection getSql2008Connection() {
		Connection connection = null;		
		try {
			Class.forName(driverName);
			connection = DriverManager.getConnection(connectionString,
											userName, passWord);
		} 
		catch (ClassNotFoundException e) {
			System.out.println("jdbc库不存在, 请检查sqljdbc.jar是否存在后重试.");
		}
		catch (SQLException e) {
			System.out.println("创建连接失败, 请尝试以下方法:");
			System.out.println("\t1.检查sqljdbc.jar是否满足数据库版本要求.");
			System.out.println("\t2.检查连接所相关的字符串是否正确.");

			e.printStackTrace();
		}
		return connection;
	}
	
	
	public String ExecAccessLogSql(Connection connection, 
								   String ipAddress, 
								   String reportName) {
		String message = "";
		
		if (connection == null) {
			return "数据库连接失败, 未执行SQL.";
		}
		
		System.out.println("创建连接成功");
		String sql = "exec TRIAL_DWH.dbo.proc_sale_log @IP_ADDRESS='"
				   + ipAddress + "',@REPORT_NM='"
				   + reportName + "',@TimeStamp_CD=0";

		try {
			PreparedStatement ps = connection.prepareStatement(sql);
			message = String.valueOf(ps.executeUpdate());			
		} catch (SQLException e) {
			message = "执行失败, 请检查sql语句.";
			e.printStackTrace();
		}
		finally {
			try {
				connection.close();
			} catch (SQLException e) {
				e.printStackTrace();
				message = "执行成功, 但数据库连接关闭失败.";
			}
			System.out.println("数据库连接成功关闭.");
		}
		return message;
	}
	
	public static void main(String[] args) throws SQLException {
		//SqlConnection sqlHelp = new SqlConnection("172.20.2.154", 
		//										  "1433",
		//										  "TRIAL_DWH",
		//										  "sa",
		//										  "dbadmin");
		SqlConnection sqlHelp = new SqlConnection("172.20.3.16", 
				  "1433",
				  "TRIAL_DWH",
				  "sa",
				  "dbadmin");
		Connection connection = sqlHelp.getSql2008Connection();
		
		System.out.println(sqlHelp.ExecAccessLogSql(connection, "10060817", "ttt"));
		
		connection.close();
	}
}
