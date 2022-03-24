import com.google.gson.JsonObject;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Optional;
import java.util.TimeZone;

/**
 * @author locks
 * @date 2021-12-14 10:50
 */
public class Test1 {

  private static final Logger logger = LoggerFactory.getLogger(Test1.class);

  public static void main(String[] args) throws ParseException {

    try {
      String date = "Wed, 26 Jan 2022 09:44:16 GMT";

      Date date1 = new Date();
      DateFormat df = new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss 'GMT'", Locale.US);
      df.setTimeZone(TimeZone.getTimeZone("GMT"));
      DateFormat df1 = new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss 'GMT'", Locale.CHINA);

      System.out.println(df.format(date));
      System.out.println(df1.format(date));
    } catch (Exception e) {
      logger.error(" 错误 ", e.getMessage());
      System.out.println("-------------");
      logger.error("  错误  ", e);
    }


    JsonObject jsonObject = new JsonObject();
    jsonObject.addProperty("aaa","11111");
    jsonObject.add("bbb",null);



    String str = "aaaa";
    Optional<String> str1 = Optional.ofNullable(str);
    System.out.println(str1.get());
    System.out.println(str1.isPresent());
     str1.ifPresent(item -> item.concat("bbbb"));
    System.out.println(str1.get());
  }
}
