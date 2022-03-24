package main.gson;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.sun.javafx.scene.KeyboardShortcutsHandler;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class GsonDemo {
    public static void main(String[] args) {

        JsonParser parser = new JsonParser();
        String str =  "{\"key\": null,\"key1\":\"value1\"}";


        JsonElement parse = parser.parse(str);
        JsonObject asJsonObject = parse.getAsJsonObject();


        Gson gson = new Gson();
        Map map = gson.fromJson(asJsonObject, Map.class);
        System.out.println(map.get("key") == null);


        Optional<JsonElement> key3 = Optional.ofNullable(asJsonObject.get("key3"));
        System.out.println(key3);

        //String key3 = asJsonObject.get("key3").getAsString();
        System.out.println(asJsonObject.has("key"));
        System.out.println(asJsonObject.has("key3"));



//        Truck truck = new Truck();
//        truck.setTruckId("123");
//        truck.setTruckMc("456");
//        truck.setCysMc(null);
//
//        Gson gson = new Gson();
//        String gsonStr = gson.toJson(truck);
//
//
//        System.out.println(gsonStr);
//
//
//        Truck truck1 = gson.fromJson(gson.toJson(truck),Truck.class);
//        System.out.println(truck1.getTruckId());
//        System.out.println(truck1.getCysMc());
//        System.out.println(truck1.getTruckMc());
//
//
//        List<String> list = new ArrayList<>();
//        list.add("1");
//        list.add("2");
//        list.add("3");
//        list.add("3");
//
//        list.stream().forEach(System.out::println);


    }
}
