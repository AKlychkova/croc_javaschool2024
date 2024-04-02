package ru.croc.javaschool2024.Klychkova.task10;

import java.util.Collection;
import java.util.List;
import java.util.Set;

public class BlackListFilterImpl implements BlackListFilter {
    private boolean checkComment(String comment, Collection<String> blackList) {
        var words = List.of(comment.toLowerCase().split("[\\s-.?!)(,:;]"));
        for (String blackWord : blackList) {
            if (words.contains(blackWord.toLowerCase())) {
                return false;
            }
        }
        return true;
    }

    @Override
    public void filterComments(List<String> comments, Set<String> blackList) {
        comments.removeIf(comment -> !checkComment(comment, blackList));
    }
}
