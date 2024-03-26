package ru.croc.javaschool2024.Klychkova.task4;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static java.lang.Math.min;

public class Task4 {
    public static void main(String[] args) {
        String source = """
                /*
                 * My first ever program in Java!
                 */
                class Hello { // class body starts here
                 
                  /* main method */
                  public static void main(String[] args/* we put command line arguments here*/) {
                    // this line prints my first greeting to the screen
                    System.out.println("Hi!"); // :)
                  }
                } // the end
                // to be continued...
                """; // test data
        String noComments = removeJavaComments(source);
        System.out.println(noComments);
    }

    private static int minPositive(int... values) {
        int res = Integer.MAX_VALUE;
        for (int value : values) {
            if (value >= 0) {
                res = min(res, value);
            }
        }
        if (res == Integer.MAX_VALUE) {
            res = -1;
        }
        return res;
    }


    public static String removeJavaComments(String input) {
        StringBuilder result = new StringBuilder();

        int current_position = 0;
        int quotes = input.indexOf('\"', current_position);
        int s_cmt = input.indexOf("//", current_position);
        int m_cmt = input.indexOf("/*", current_position);

        while (s_cmt > 0 || m_cmt > 0) {
            int first = minPositive(quotes, s_cmt, m_cmt);
            if (first == quotes) {
                int close_quotes = input.indexOf('\"',quotes+1);
                result.append(input, current_position, close_quotes+1);
                current_position = close_quotes + 1;
            } else if (first == s_cmt) {
                result.append(input, current_position, s_cmt);
                current_position = input.indexOf('\n',s_cmt);
            } else {
                result.append(input, current_position, m_cmt);
                current_position = input.indexOf("*/",m_cmt) + 2;
            }
            quotes = input.indexOf('\"', current_position);
            s_cmt = input.indexOf("//", current_position);
            m_cmt = input.indexOf("/*", current_position);
        }
        result.append(input,current_position,input.length());
        return result.toString();
    }
}
