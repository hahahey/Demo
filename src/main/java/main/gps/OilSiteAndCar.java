package main.gps;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.google.gson.JsonSyntaxException;
import com.google.gson.internal.Primitives;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class OilSiteAndCar {
    public static void main(String[] args) {


        Map<String,String> map = new HashMap<>();
        map.put("key","value");
        map.put("key1","value1");

        String json = "{\"key\":\"value\"}";

        Gson gson = new Gson();
        Type type = new TypeToken<Map<String, String>>() {}.getType();

        Map<String,String>  hashMap = gson.fromJson(json, type);
        System.out.println(hashMap.size());


        User user = new User();
        String u = user.getU(10.001, Double.class);
        System.out.println(u);

        List<JsonElement> list = new ArrayList<>();
        System.out.println(list.getClass().getName());


    }
}


class User {
//    public <T> T fromJson(String json, Class<T> classOfT) throws JsonSyntaxException {
//        Object object = fromJson(json, (Type) classOfT);
//        return Primitives.wrap(classOfT).cast(object);
//
//    }

    public <T> String getU(Object dou,Class<T> t){

        System.out.println(t.getName());
        T cast = (T) t.cast(dou);
        System.out.println(cast);
        return "hello";
    }

}
