package org.example.threadlocal;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.Date;

/**
 * @program interview
 * @author Ben
 * @date 2023-07-06 14:18
 */
public class ThreadLocalDateUtils {

  // 1
  public static final SimpleDateFormat simpleDateFormat =
      new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

  // 2
  public static final ThreadLocal<SimpleDateFormat> sdf =
      ThreadLocal.withInitial(() -> new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"));

  public static final DateTimeFormatter DATE_TIME_FORMATTER =
      DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

  public static void main(String[] args) {
    // errorUse();
    correctUser();
  }

  private static void correctUser() {
    for (int i = 0; i < 5; i++) {
      new Thread(
              () -> {
                System.out.println(correctParse("2011-11-11 12:12:13"));
              },
              String.valueOf(i))
          .start();
    }
  }

  private static Date correctParse(String s) {
    try {
      return sdf.get().parse(s);
    } catch (ParseException e) {
      throw new RuntimeException(e);
    } finally {
      sdf.remove();
    }
  }

  private static void errorUse() {
    for (int i = 0; i < 5; i++) {
      new Thread(
              () -> {
                System.out.println(errorParse("2011-11-11 12:12:13"));
              },
              String.valueOf(i))
          .start();
    }
  }

  private static Date errorParse(String date) {
    try {
      return simpleDateFormat.parse(date);
    } catch (ParseException e) {
      throw new RuntimeException(e);
    }
  }
}
