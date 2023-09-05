package org.example.base.boxing;

/**
 * @author Ben
 * @program interview
 * @date 2023-06-20 17:21
 **/
public class BoxingTest {

    public static void main(String[] args) {


        System.out.println("new Integer(\"2\") == 2:" + (new Integer("2") == 2));

        System.out.println("new Integer(\"2\") == new Integer(\"2\"):" + (new Integer("2") == new Integer("2")));

        System.out.println("Integer.valueOf(\"2\") == Integer.valueOf(\"2\"):" + (Integer.valueOf("2") == Integer.valueOf("2")));

        System.out.println("Integer.valueOf(\"2\").intValue() == 2:" + (Integer.valueOf("2").intValue() == 2));

        System.out.println("new Integer(\"2\").equals(new Integer(\"2\")):" + (new Integer("2").equals(new Integer("2"))));
    }

}
