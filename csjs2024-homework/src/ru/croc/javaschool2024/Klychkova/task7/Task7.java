package ru.croc.javaschool2024.Klychkova.task7;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Task7 {
    public static void main(String[] args) {

    }

    public static String normalizePath(String source){
        Path path = Paths.get(source);
        String result = path.normalize().toString();
        if (! Objects.equals(File.separator, "/")) {
            result = separatorsToUnix(result);
        }
        return result;
    }

    // "Ручное" решение
    public static String normalizePath2(String source){
        String result = source.replaceAll("(/\\.(?=/))|(^\\./)|(/.$)", "");
        Pattern pattern = Pattern.compile("[\\wА-Яа-я]+?/\\.\\./");
        Matcher matcher = pattern.matcher(result);
        while (matcher.find()) {
            result = matcher.replaceAll("");
            matcher = pattern.matcher(result);
        }
        return result;
    }

    private static String separatorsToUnix(String path) {
        return path.replaceAll("\\\\","/");
    }
}
