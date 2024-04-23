package ru.croc.javaschool2024.logic;

import java.util.ArrayList;
import java.util.List;

/**
 * Класс, хранящий данные обо всех избирательных участках.
 */
public class PollingStationsData {
    private final List<PollingStation> stations;
    private final String[] candidatesNames;

    /**
     * @param candidatesNames Список имен кандидатов.
     */
    public PollingStationsData(String[] candidatesNames) {
        this.candidatesNames = candidatesNames;
        stations = new ArrayList<>();
    }

    /**
     * @param candidatesNames Список имен кандидатов.
     * @param stations Список избирательных участков.
     * @throws IllegalArgumentException Если избирательные участки имеют разное количество кандидатов.
     */
    public PollingStationsData(String[] candidatesNames, List<PollingStation> stations) {
        this(candidatesNames);
        if (stations != null) {
            for (PollingStation station : stations) {
                addPollingStation(station);
            }
        }
    }

    /**
     * Добавить данные об избирательном участке.
     * @param station Избирательный участок.
     * @throws IllegalArgumentException Если участок имеет отличное от других количество кандидатов.
     */
    public void addPollingStation(PollingStation station) {
        if (station.candidatesNum() == candidatesNames.length) {
            stations.add(station);
        } else {
            throw new IllegalArgumentException(
                    String.format(
                            "Избирательный участок содержит данные по неправильному количеству кандидатов (%d вместо %d)",
                            station.candidatesNum(),
                            candidatesNames.length
                    )
            );
        }
    }

    /**
     * @return Список имен кандидатов.
     */
    public String[] getCandidatesNames() {
        return candidatesNames.clone();
    }

    /**
     * @return Количество избирательных участков.
     */
    public int getStationsNumber() {
        return stations.size();
    }

    /**
     * @param candidateNumber Индекс кандидата.
     * @return Результаты кандидата в процентах от количества действительных бюллетений по каждому из участков.
     */
    public double[] getCandidatePercentsData(int candidateNumber) {
        return stations.stream()
                .mapToDouble(station -> (double) station.votes()[candidateNumber] / station.validBallotsNum() * 100)
                .toArray();
    }

    /**
     * @return Явка в процентах по каждому из избирательных участков.
     */
    public double[] getTurnoutData() {
        return stations.stream()
                .mapToDouble(station -> (double) station.validBallotsNum() / station.totalVotersNum() * 100)
                .toArray();
    }

    /**
     * @param candidateNumber Индекс кандидата.
     * @return Результаты кандидата в процентах от общего числа избирателей по каждому из избирательных участков.
     */
    public double[] getCandidatePercentOfTotalData(int candidateNumber) {
        return stations.stream()
                .mapToDouble(station -> (double) station.votes()[candidateNumber] / station.totalVotersNum() * 100)
                .toArray();
    }
}
