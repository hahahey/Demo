package main.log;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author locks
 * @date 2022-03-09 17:15
 */
public class Logtest {
  private static final Logger log = LoggerFactory.getLogger(Logtest.class);




  public static void main(String[] args)   {

    log.info("----------------------");
    log.error("----------------------");
    log.warn("----------------------");

    int i = 1;
    LogA.print();
    LogB.print();


  }
}
