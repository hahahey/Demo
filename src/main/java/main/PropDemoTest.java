package main;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class PropDemoTest {
  public static void main(String[] args) throws Exception {

    String key = "$USER_DateInterface,Pcitc,True,ShanDong202108^<>,*!";

    DatagramSocket socket = new DatagramSocket(10000);
    byte[] buf = key.getBytes();

    DatagramPacket packet =
        new DatagramPacket(buf, buf.length, InetAddress.getByName("60.216.4.11"), 1020);

    socket.send(packet);

    socket.close();

    System.out.println("key 发送成功");

    // 定义一个接收端，并且指定了接收的端口号
    DatagramSocket socket1 = new DatagramSocket(10000);

    while (true) {
      byte[] buf1 = new byte[1024];
      // 解析数据包
      DatagramPacket packet1 = new DatagramPacket(buf1, buf1.length);

      socket1.receive(packet1);

      String ip = packet1.getAddress().getHostAddress();

      buf1 = packet1.getData();

      String data = new String(buf1, 0, packet1.getLength(), "GBK");

      System.out.println(data);
    }
  }
}
