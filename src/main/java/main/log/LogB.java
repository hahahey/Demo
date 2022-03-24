package main.log;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author locks
 * @date 2022-03-09 17:36
 */
public class LogB {
    private static final Logger log = LoggerFactory.getLogger(LogB.class);
    public static void print(){
        log.info("LogB");
        System.out.println("LogB");
    }
}
