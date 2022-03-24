package main.implement;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author locks
 * @date 2022-02-22 16:26
 */
public class A implements C<String>{

  @Override
  public String prinMessage(String str) {
     return str + "___________";
  }

}
