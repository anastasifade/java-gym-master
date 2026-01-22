package ru.yandex.practicum.gym;

// класс для хранения отсортированного списка тренеров

import java.util.*;

public class CoachRegistry {

    private final Set<Coach> coaches;

    public CoachRegistry() {
        coaches = new HashSet<>();
    }

    public void addCoach(Coach coach) {
        if (coach == null) return;
        coaches.add(coach);
    }

    public List<Coach> getCoachesSortedByCount() {
        List<Coach> coachesSorted = new ArrayList<>(coaches);
        Collections.sort(coachesSorted);

        return coachesSorted;
    }


}
