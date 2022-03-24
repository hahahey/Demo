package main.date;

import com.google.gson.*;
import org.apache.commons.lang3.StringUtils;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class DateDemo {
    public static void main(String[] args) throws Exception {


//        String date = "2021-08-25T15:12:56+08:00";
//
//        DateTimeFormatter formatterYMDTH = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ssXXX");
//        DateTimeFormatter formatterYMD = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
//
//        System.out.println(formatterYMD.format(formatterYMDTH.parse(date)));
//        SimpleDateFormat formatYMDTH = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssXXX");
//
//        SimpleDateFormat formatterYMD =  new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//
//
//        System.out.println(formatterYMD.format(formatYMDTH.parse(date)));


        String plateNumber = "鲁AB3331";
        String dateStr = "20210831143733";
        int result = (plateNumber.hashCode() & Integer.MAX_VALUE) % 12;
        String prefix = StringUtils.leftPad(result + "", 3, "0");

        System.out.println(prefix);


        System.out.println(StringUtils.substring("yyyy-MM-dd HH:mm:ss",0,10));




    }
}
