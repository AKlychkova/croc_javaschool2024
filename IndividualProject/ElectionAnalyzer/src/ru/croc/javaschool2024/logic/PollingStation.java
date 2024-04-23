package ru.croc.javaschool2024.logic;

/**
 * Класс избирательного участка.
 * @param totalVotersNum Общее количество избирателей.
 * @param validBallotsNum Количество действительных бюллетений.
 * @param votes Голоса отданные за каждого из кандидатов.
 */
public record PollingStation(int totalVotersNum, int validBallotsNum, int[] votes) {

    /**
     * @return Голоса отданные за каждого из кандидатов.
     */
    public int[] votes() {
        return votes.clone();
    }

    /**
     * @return Количество кандидатов.
     */
    public int candidatesNum() {
        return votes.length;
    }
}
