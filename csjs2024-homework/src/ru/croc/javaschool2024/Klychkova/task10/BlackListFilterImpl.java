package ru.croc.javaschool2024.Klychkova.task10;

import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class BlackListFilterImpl implements BlackListFilter {
    private boolean checkComment(String comment, Collection<String> blackList) {
        Set<String> blackSet = blackList.stream()
                .map(String::toLowerCase)
                .collect(Collectors.toSet());
        var words = List.of(comment.toLowerCase().split("[\\s-.?!)(,:;]"));
        for (String word : words) {
            if (blackSet.contains(word)) {
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
