package main.log;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author locks
 * @date 2022-03-09 17:36
 */
public class LogA {
    private static final Logger log = LoggerFactory.getLogger(LogA.class);
    public static void print(){
        log.info("LogA");
        System.out.println("LogA");
    }
}
