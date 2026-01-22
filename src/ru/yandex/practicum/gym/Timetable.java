package ru.yandex.practicum.gym;

import java.util.*;

public class Timetable {

    private final Map<DayOfWeek, Map<TimeOfDay, List<TrainingSession>>> timetable = new HashMap<>();
    private final CoachRegistry coaches = new CoachRegistry();

    public void addNewTrainingSession(TrainingSession trainingSession) {
        if (trainingSession == null) {
            System.out.println("Ошибка. Тренировки, которую Вы пытаетесь внести в расписание, не существует.");
            return;
        }

        DayOfWeek day = trainingSession.getDayOfWeek();
        TimeOfDay time = trainingSession.getTimeOfDay();

        if (day == null || time == null) {
            System.out.println("Ошибка. Не указан день и/или время тренировки. " +
                    "Невозможно внести тренировку в расписание.");
            return;
        }

        if (!timetable.containsKey(day)) {
            timetable.put(day, new TreeMap<>());
        }

        Map<TimeOfDay, List<TrainingSession>> timetableForDay = timetable.get(day);

        if (!timetableForDay.containsKey(trainingSession.getTimeOfDay())) {
            timetableForDay.put(time, new ArrayList<>());
        }

        List<TrainingSession> trainingSessionsForTime = timetableForDay.get(time);
        trainingSessionsForTime.add(trainingSession);

        assignCoach(trainingSession.getCoach());
    }

    public Map<TimeOfDay, List<TrainingSession>> getTrainingSessionsForDay(DayOfWeek dayOfWeek) {
        if (dayOfWeek == null) return null;
        if (!timetable.containsKey(dayOfWeek)) return null;

        return timetable.get(dayOfWeek);
    }

    public List<TrainingSession> getTrainingSessionsForDayAndTime(DayOfWeek dayOfWeek, TimeOfDay timeOfDay) {
        Map<TimeOfDay, List<TrainingSession>> timetableForDay = getTrainingSessionsForDay(dayOfWeek);

        if (timeOfDay == null) return null;
        if (!timetableForDay.containsKey(timeOfDay)) return null;

        return timetableForDay.get(timeOfDay);
    }

    public List<TimeOfDay> getTrainingSessionsHoursForDay(DayOfWeek day) {
        if (!timetable.containsKey(day)) return null;

        TreeMap<TimeOfDay, List<TrainingSession>> trainingSessionsForDay =
                (TreeMap<TimeOfDay, List<TrainingSession>>) timetable.get(day);
        List<TimeOfDay> trainingSessionHoursForDay = new ArrayList<>();

        for (TimeOfDay time : trainingSessionsForDay.navigableKeySet()) {
            trainingSessionHoursForDay.add(time);
        }

        return trainingSessionHoursForDay;
    }

    public List<Coach> getCoachesByCount() {
        return coaches.getCoachesSortedByCount();
    }

    // метод для учета списка тренеров и количества проводимых ими тренировок
    private void assignCoach(Coach coach) {
        coaches.addCoach(coach);
        coach.assignToTrainingSession();
    }
}
