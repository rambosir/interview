package org.example.base.juc;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentSkipListMap;

/**
 * @author Ben
 * @program interview
 * @date 2023-06-16 12:52
 **/
public class ConcurrentMap {

    public static void main(String[] args) {

        ConcurrentHashMap<String, String> hashMap = new ConcurrentHashMap<>();
        hashMap.put("12312asdas", "a");
        hashMap.put("3321321123qwe", "b");
        hashMap.put("21213123qweq", "c");
        System.out.println(hashMap);
        
        ConcurrentSkipListMap<String, String> skipListMap = new ConcurrentSkipListMap<>();
        skipListMap.put("555eqwe", "a");
        skipListMap.put("111rwqe", "b");
        skipListMap.put("222qwe", "c");
        System.out.println(skipListMap);
        
        

    }
}
