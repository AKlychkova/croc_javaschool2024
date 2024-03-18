package ru.croc.javaschool2024.Klychkova.task4;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Task4 {
    public static void main(String[] args) {
        String source = "..."; // test data
        String noComments = removeJavaComments(source);
        System.out.println(noComments);
    }


    public static String removeJavaComments(String input) {
        Pattern pattern = Pattern.compile("(/\\*[\\s\\S]*?\\*/)|(//.*)");
        Matcher matcher = pattern.matcher(input);
        return matcher.replaceAll("");
    }
}
