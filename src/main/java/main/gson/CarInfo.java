package main.gson;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 * @author locks
 * @date 2022-03-07 17:35
 */
public class CarInfo {

  private String carNo;
  private String heartTime;

  public CarInfo(String carNo, String heartTime) {
    this.carNo = carNo;
    this.heartTime = heartTime;
  }

  @Override
  public String toString() {
    return "CarInfo{" + "carNo='" + carNo + '\'' + ", heartTime='" + heartTime + '\'' + '}';
  }

  public static void main(String[] args) {

    //    List<CarInfo> list = new ArrayList<>();
    //     for (int i = 0; i < 100000; i++) {
    //      int i1 = ThreadLocalRandom.current().nextInt(10, 99);
    //
    //      int i2 = ThreadLocalRandom.current().nextInt(10, 23);
    //      int i3 = ThreadLocalRandom.current().nextInt(10, 59);
    //      int i4 = ThreadLocalRandom.current().nextInt(10, 59);
    //
    //      CarInfo carInfo = new CarInfo("陕A000" + i1, "2021-03-07 " + i2 + ":" + i3 + ":" + i4);
    //
    //      list.add(carInfo);
    //    }
    //
    //    long start = System.currentTimeMillis();
    //    list.sort(
    //        new Comparator<CarInfo>() {
    //          @Override
    //          public int compare(CarInfo o1, CarInfo o2) {
    //            return (o1.carNo + o1.heartTime).compareTo(o2.carNo + o2.heartTime);
    //          }
    //        });
    //    long end = System.currentTimeMillis();
    //
    //
    //    list.stream().forEach(System.out::println);
    //    System.out.println(end - start);

    List<Integer> list = new ArrayList<>();
    List<Integer> lis1 = new ArrayList<>();

    list.add(4);
    list.add(4);
    list.add(4);
    list.add(4);
    list.add(1);
    list.add(1);
    list.add(1);
    list.add(2);
    list.add(5);
    list.add(5);
    list.add(3);
    list.add(3);

    System.out.println(list.size());

    for (int i = 0; i < list.size(); i++) {

      if (i == list.size() - 1) {
        lis1.add(list.get(i));
      } else {
        if (list.get(i) != list.get(i + 1)) {
          lis1.add(list.get(i));
        }
      }
    }

    list.sort(Comparator.comparing(o -> o.intValue()));

    list.forEach(System.out::println);
  }
}
