package main.strategy;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.Map;
import java.util.Set;

/**
 * @author locks
 * @date 2022-02-21 14:04
 */
public class Main {
  public static void main(String[] args) {

      String name = "sx_real";
      String confFilePath = "";
      Context context = new Context(name, confFilePath);
      Map<String, String> propMap = context.initProperty();
      context.dealWithData(propMap);



      Gson gson = new Gson();
      String str =  "{\"key1\":\"value1\",\"key2\":\"\",\"key3\":null,\"key4\":100,\"key5\":0.001,\"key6\":true}";
      Map<String, Object> map = gson.fromJson(str, new TypeToken<Map<String,Object>>() {}.getType());

    for (Map.Entry<String, Object> stringObjectEntry : map.entrySet()) {
      //
    }
  }
}
