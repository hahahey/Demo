package main.mysql;

import java.sql.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * @author locks
 * @date 2022-03-16 13:50
 */
public class MysqlDmeo {
    private static final String URL = "jdbc:mysql://10.252.68.192:8001/slm?useServerPrepStmts=false&rewriteBatchedStatements=true";
    private static final String NAME = "com.mysql.jdbc.Driver";
    private static final String USER_NAME = "slm";
    private static final String PASS_WD = "1QAZ2wsx";
    private static Connection connection = null;



    public static void getConByManual(){
        try {
            Class.forName(NAME);
            connection = DriverManager.getConnection(URL, USER_NAME, PASS_WD);
        } catch (Exception throwables) {
            throwables.printStackTrace();
        }
    }

    public static Connection getConnec() {
        try {
            if(connection == null || connection.isClosed()){
                getConByManual();
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();

        }
        return connection;
    }

  public static void main(String[] args) throws SQLException {
      Connection connec = getConnec();

      PreparedStatement preparedStatement = connec.prepareStatement("insert into slm_test_lzk(arriveTime,leaveTimestamp) values (?,?)");

      LocalDateTime localDateTime = LocalDateTime.parse("2038-01-19 04:17:08", DateTimeFormatter.ofPattern("yyy-MM-dd HH:mm:ss"));

      preparedStatement.setObject(1, localDateTime);
      preparedStatement.setTimestamp(2, Timestamp.valueOf(localDateTime));


      preparedStatement.execute();
      preparedStatement.close();
      connec.close();
  }
}
