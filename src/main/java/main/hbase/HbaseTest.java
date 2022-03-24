package main.hbase;

import org.apache.commons.lang3.StringUtils;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author locks
 * @date 2021-10-12 14:17
 */
public class HbaseTest {
  private static final String url = "jdbc:mysql://10.252.68.192:8001/slm";
  private static final String name = "com.mysql.jdbc.Driver";
  private static final String userName = "slm";
  private static final String passWd = "1QAZ2wsx";

  private static Connection connection = null;

  static {
    try {
      Class.forName(name);
      connection = DriverManager.getConnection(url, userName, passWd);
    } catch (Exception throwables) {
      throwables.printStackTrace();
    }
  }

  public static Connection getConnec() {
    return connection;
  }

  public static void main(String[] args) throws SQLException {


    Map<String,List<String>> hashMap = new HashMap<>();


    PreparedStatement preparedStatement = connection.prepareStatement("select code from slm_dict_tmp order by code asc ");

    ResultSet resultSet = preparedStatement.executeQuery();
    while (resultSet.next()) {
      String code = resultSet.getString("code");


      int result = (code.hashCode()  & Integer.MAX_VALUE ) % 12;
      String prefix = StringUtils.leftPad(result + "", 3, "0");

//      String prefix = String.valueOf((code.hashCode() ^ Integer.MAX_VALUE) % 12);
      List<String> list = new ArrayList<>();
      if(hashMap.containsKey(prefix)){
        hashMap.get(prefix).add(code);
      }else {
        list.add(code);
        hashMap.put(prefix,list);
      }
    }
    resultSet.close();
    preparedStatement.close();
    connection.close();

    for (Map.Entry<String, List<String>> stringListEntry : hashMap.entrySet()) {
      System.out.println(stringListEntry.getKey() + "   " + stringListEntry.getValue());
    }
  }
}
