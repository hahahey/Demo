package main;

import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import java.io.StringReader;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class UdpDemo {
  public static void main(String[] args) throws Exception {

    String s =
        "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n<response><status>1</status><info>OK</info><infocode>10000</infocode><results type=\"list\"><result><origin_id>1</origin_id><dest_id>1</dest_id><distance>305287</distance><duration>12660</duration></result><result><origin_id>2</origin_id><dest_id>1</dest_id><distance>600627</distance><duration>24360</duration></result><result><origin_id>3</origin_id><dest_id>1</dest_id><distance>605241</distance><duration>24660</duration></result><result><origin_id>4</origin_id><dest_id>1</dest_id><distance>143517</distance><duration>7200</duration></result><result><origin_id>5</origin_id><dest_id>1</dest_id><distance>282150</distance><duration>11880</duration></result><result><origin_id>6</origin_id><dest_id>1</dest_id><distance>481853</distance><duration>21240</duration></result><result><origin_id>7</origin_id><dest_id>1</dest_id><distance>285657</distance><duration>11820</duration></result><result><origin_id>8</origin_id><dest_id>1</dest_id><distance>286585</distance><duration>11760</duration></result><result><origin_id>9</origin_id><dest_id>1</dest_id><distance>98315</distance><duration>6420</duration></result><result><origin_id>10</origin_id><dest_id>1</dest_id><distance>536701</distance><duration>21060</duration></result><result><origin_id>11</origin_id><dest_id>1</dest_id><distance>596388</distance><duration>23880</duration></result><result><origin_id>12</origin_id><dest_id>1</dest_id><distance>603067</distance><duration>24240</duration></result><result><origin_id>13</origin_id><dest_id>1</dest_id><distance>330986</distance><duration>14700</duration></result><result><origin_id>14</origin_id><dest_id>1</dest_id><distance>287180</distance><duration>12480</duration></result><result><origin_id>15</origin_id><dest_id>1</dest_id><distance>163796</distance><duration>7080</duration></result><result><origin_id>16</origin_id><dest_id>1</dest_id><distance>200838</distance><duration>9720</duration></result><result><origin_id>17</origin_id><dest_id>1</dest_id><distance>205567</distance><duration>11220</duration></result><result><origin_id>18</origin_id><dest_id>1</dest_id><distance>306272</distance><duration>14520</duration></result><result><origin_id>19</origin_id><dest_id>1</dest_id><distance>465521</distance><duration>20100</duration></result><result><origin_id>20</origin_id><dest_id>1</dest_id><distance>223040</distance><duration>9960</duration></result><result><origin_id>21</origin_id><dest_id>1</dest_id><distance>333128</distance><duration>13320</duration></result><result><origin_id>22</origin_id><dest_id>1</dest_id><distance>311119</distance><duration>14820</duration></result><result><origin_id>23</origin_id><dest_id>1</dest_id><distance>449584</distance><duration>17580</duration></result><result><origin_id>24</origin_id><dest_id>1</dest_id><distance>99952</distance><duration>6840</duration></result><result><origin_id>25</origin_id><dest_id>1</dest_id><distance>22591</distance><duration>2040</duration></result><result><origin_id>26</origin_id><dest_id>1</dest_id><distance>243883</distance><duration>10560</duration></result><result><origin_id>27</origin_id><dest_id>1</dest_id><distance>159018</distance><duration>6840</duration></result><result><origin_id>28</origin_id><dest_id>1</dest_id><distance>633846</distance><duration>26520</duration></result><result><origin_id>29</origin_id><dest_id>1</dest_id><distance>240819</distance><duration>10080</duration></result><result><origin_id>30</origin_id><dest_id>1</dest_id><distance>428854</distance><duration>18120</duration></result></results></response>\n";

    List<String> list = new ArrayList<>(128);

    System.out.println(s);
    StringReader stringReader = new StringReader(s);
    SAXReader saxReader = new SAXReader();
    org.dom4j.Document read = saxReader.read(stringReader);
    Element rootElement = read.getRootElement();

    List<Element> elements = rootElement.elements();
    for (Element element : elements) {
      System.out.println(element.getName());
    }



    Element results = rootElement.element("results");
    System.out.println(results);

    Iterator<Element> elementIterator = results.elementIterator();

    while (elementIterator.hasNext()) {
      Element next = elementIterator.next();
      String distance = (String) next.element("distance").getData();
      list.add(distance);
    }

    System.out.println(list);
  }
}
