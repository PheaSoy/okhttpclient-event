package com.example.demo;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class MainTest2 {

    static String test(String... args) {

        String message = "Transferred successfully with #amount# currency and #date#";
        List<String> msgs = new ArrayList<>();


        boolean isMatchedWithSpecial;
        int indexStart = 0;
        int indexEnd = 0;

        for (int i = 0; i < message.length(); i++) {
            if (message.charAt(i) == '#') {
                if (indexStart == 0) {
                    indexStart = i;
                    continue;
                }
                if (indexEnd == 0 && indexStart > 0) {
                    indexEnd = i;
                }

                if (indexStart > 0 && indexEnd > 0) {
                    String msg = message.substring(indexStart, indexEnd + 1);
                    msgs.add(msg);
                    indexEnd = indexStart = 0;
                }
            }

            //IntStream.range(0, msgs.size()).mapToObj(j -> j + "").forEach(System.out::println);

            for (int j = 0; j < msgs.size(); j++) {
                message = message.replace(msgs.get(j), args[j]);
            }

        }
        System.out.println("Message =?" + message);
        return message;

    }

    public static void main(String[] args) {
        test("100", "2029-10-90");
    }
}
