package main.webService;

import cn.hutool.http.HttpConnection;
import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import main.gson.Truck;
import org.dom4j.Attribute;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.jdom.*;
import org.jdom.input.SAXBuilder;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.EmptyStackException;
import java.util.Iterator;
import java.util.List;

public class PostDemo {
    public static void main(String[] args) {

        String urlStr = "http://10.252.78.30:8011/Service.asmx/truck";
        String body = "str=ShanDong202108^<>,*!";
        HttpRequest post = HttpRequest
                .post(urlStr)
                .body(body);

        HttpResponse response = post.execute();
        String body1 = response.body();

        StringReader stringReader = new StringReader(body1);
        List<Truck> truckList = new ArrayList();
        try {

            SAXReader saxReader = new SAXReader();
            org.dom4j.Document read = saxReader.read(stringReader);
            Element rootElement = read.getRootElement();
            Element diffgram = rootElement.element("diffgram").element("NewDataSet");
            Iterator<Element> elementIterator = diffgram.elementIterator();

            while (elementIterator.hasNext()) {
                Truck truck = new Truck();
                Element next = elementIterator.next();
                truck.setTruckMc((String) next.element("truck_mc").getData());
                truck.setTruckId((String) next.element("truck_id").getData());
                truck.setCysMc((String) next.element("cys_mc").getData());
                truckList.add(truck);
            }


            truckList.stream().forEach(System.out::println);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

class truck {
    private String truck_mc;
    private String truck_id;
    private String cys_mc;

    public String getTruck_mc() {
        return truck_mc;
    }

    public void setTruck_mc(String truck_mc) {
        this.truck_mc = truck_mc;
    }

    public String getTruck_id() {
        return truck_id;
    }

    public void setTruck_id(String truck_id) {
        this.truck_id = truck_id;
    }

    public String getCys_mc() {
        return cys_mc;
    }

    public void setCys_mc(String cys_mc) {
        this.cys_mc = cys_mc;
    }

    @Override
    public String toString() {
        return "truck{" +
                "truck_mc='" + truck_mc + '\'' +
                ", truck_id='" + truck_id + '\'' +
                ", cys_mc='" + cys_mc + '\'' +
                '}';
    }
}
