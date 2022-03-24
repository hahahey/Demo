package main.implement;

import java.util.concurrent.ThreadLocalRandom;

/**
 * @author locks
 * @date 2022-02-22 16:27
 */
public class B {

  public static void main(String[] args) {
    A a = new A();
    String a1 = a.prinMessage("A");
     System.out.println(a1);


    try {
      Class<?> aClass = Class.forName("main.implement." + "A");
      C<String> aaa = (C<String>) aClass.newInstance();
      System.out.println(aaa.prinMessage("aaaa"));

    } catch (Exception e) {
      e.printStackTrace();
    }

  }



}
