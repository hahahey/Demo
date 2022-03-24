package main.implement;

import java.lang.reflect.Field;
import java.util.HashMap;

/**
 * @author locks
 * @date 2022-03-10 13:40
 */
public class ImpB extends ParentA {



  public static void main(String[] args) {
    //      String s = "ImpB";
    //      String s1 = getString(s);
    //    System.out.println(s1);

    C a = new A();
    System.out.println(a instanceof C);
    System.out.println(a instanceof A);
    System.out.println(a instanceof D);
  }
}
