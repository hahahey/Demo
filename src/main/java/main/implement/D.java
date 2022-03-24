package main.implement;

/**
 * @author locks
 * @date 2022-02-22 16:26
 */
public class D implements C<String>{

  @Override
  public String prinMessage(String str) {
     return str + "___________";
  }

}
