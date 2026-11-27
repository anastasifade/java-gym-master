package ru.yandex.practicum.gym;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.*;

public class TimetableTest {

    private Timetable timetable;

    @BeforeEach
    void createTimetable(){
        timetable = new Timetable();
        Group groupChild = new Group("Акробатика для детей", Age.CHILD, 60);
        Group groupAdult = new Group("Акробатика для взрослых", Age.ADULT, 90);
        Coach firstCoach = new Coach("Васильев", "Николай", "Сергеевич");
        Coach secondCoach = new Coach("Иванов", "Иван", "Борисович");

        TrainingSession mondayTrainingSession = new TrainingSession(groupChild, firstCoach, DayOfWeek.MONDAY,
                new TimeOfDay(13, 0));
        TrainingSession thursdayChildTrainingSession = new TrainingSession(groupChild, secondCoach, DayOfWeek.THURSDAY,
                new TimeOfDay(13, 0));
        TrainingSession thursdayAdultTrainingSession = new TrainingSession(groupAdult, secondCoach, DayOfWeek.THURSDAY,
                new TimeOfDay(20, 0));
        TrainingSession saturdayTrainingSession = new TrainingSession(groupChild, secondCoach, DayOfWeek.SATURDAY,
                new TimeOfDay(10, 0));

        timetable.addNewTrainingSession(mondayTrainingSession);
        timetable.addNewTrainingSession(thursdayChildTrainingSession);
        timetable.addNewTrainingSession(thursdayAdultTrainingSession);
        timetable.addNewTrainingSession(saturdayTrainingSession);
    }

    //Проверить, что за понедельник вернулось одно занятие
    @Test
    void shouldReturnOneTrainingSessionOnMonday() {
        int trainingSessions = countTrainingSessionsInDay(timetable.getTrainingSessionsForDay(DayOfWeek.MONDAY));
        int expectedTrainingSessions = 1;

        Assertions.assertEquals(expectedTrainingSessions, trainingSessions);
    }

    //Проверить, что за вторник не вернулось занятий
    @Test
    void shouldReturnNoTrainingSessionsOnTuesday() {
        int trainingSessions = countTrainingSessionsInDay(timetable.getTrainingSessionsForDay(DayOfWeek.TUESDAY));
        int expectedTrainingSessions = 0;

        Assertions.assertEquals(expectedTrainingSessions, trainingSessions);
    }

    // Проверить, что за четверг вернулось 2 занятия
    @Test
    void shouldReturnTwoTrainingSessionsOnThursday() {
        int trainingSessions = countTrainingSessionsInDay(timetable.getTrainingSessionsForDay(DayOfWeek.THURSDAY));
        int expectedTrainingSessions = 2;

        Assertions.assertEquals(expectedTrainingSessions, trainingSessions);
    }

    // Проверить, что за четверг вернулись занятия в правильном порядке: сначала в 13:00, потом в 20:00
    @Test
    void shouldSortTrainingSessionsCorrectlyByHour() {
        List<TimeOfDay> trainingSessionHours = timetable.getTrainingSessionsHoursForDay(DayOfWeek.THURSDAY);

        TimeOfDay firstSessionTime = (trainingSessionHours == null ? null : trainingSessionHours.getFirst());
        TimeOfDay firstSessionTimeExpected = new TimeOfDay(13, 0);

        TimeOfDay secondSessionTime = (trainingSessionHours == null ? null : trainingSessionHours.getLast());
        TimeOfDay secondSessionTimeExpected = new TimeOfDay(20, 0);

        Assertions.assertEquals(firstSessionTimeExpected, firstSessionTime);
        Assertions.assertEquals(secondSessionTimeExpected, secondSessionTime);
    }

    // Проверить, что за четверг вернулись занятия в правильном порядке: сначала в 13:00, потом в 20:00
    @Test
    void shouldSortTrainingSessionsCorrectlyByMinute() {

        TrainingSession firstSundayTrainingSession = new TrainingSession(new Group("a", Age.CHILD, 60),
                new Coach("a", "a", "a"),
                DayOfWeek.SUNDAY, new TimeOfDay(10, 1));
        TrainingSession secondSundayTrainingSession = new TrainingSession(new Group("a", Age.CHILD, 60),
                new Coach("a", "a", "a"),
                DayOfWeek.SUNDAY, new TimeOfDay(10, 2));

        timetable.addNewTrainingSession(firstSundayTrainingSession);
        timetable.addNewTrainingSession(secondSundayTrainingSession);

        List<TimeOfDay> trainingSessionHours = timetable.getTrainingSessionsHoursForDay(DayOfWeek.SUNDAY);

        TimeOfDay firstSessionTime = ((trainingSessionHours == null || trainingSessionHours.isEmpty()) ?
                null : trainingSessionHours.getFirst());
        TimeOfDay firstSessionTimeExpected = new TimeOfDay(10, 1);

        TimeOfDay secondSessionTime = ((trainingSessionHours == null || trainingSessionHours.isEmpty()) ?
                null : trainingSessionHours.getLast());
        TimeOfDay secondSessionTimeExpected = new TimeOfDay(10, 2);

        Assertions.assertEquals(firstSessionTimeExpected, firstSessionTime);
        Assertions.assertEquals(secondSessionTimeExpected, secondSessionTime);
    }

    //Проверить, что за понедельник в 13:00 вернулось одно занятие
    @Test
    void shouldReturnOneTrainingSessionsOnMondayAtOnePm() {
        Map<TimeOfDay, List<TrainingSession>> sessionsOnMonday = timetable.getTrainingSessionsForDay(DayOfWeek.MONDAY);
        List<TrainingSession> sessionsAtOnePm = sessionsOnMonday.get(new TimeOfDay(13, 0));

        int sessionsCountAtOnePm = (sessionsAtOnePm == null ? 0 : sessionsAtOnePm.size());
        int expectedSessionsCountAtOnePm = 1;

        Assertions.assertEquals(expectedSessionsCountAtOnePm, sessionsCountAtOnePm);
    }

    //Проверить, что за понедельник в 14:00 не вернулось занятий
    @Test
    void shouldReturnNoTrainingSessionsOnMondayAtTwoPm() {
        Map<TimeOfDay, List<TrainingSession>> sessionsOnMonday = timetable.getTrainingSessionsForDay(DayOfWeek.MONDAY);
        List<TrainingSession> sessionsAtTwoPm = sessionsOnMonday.get(new TimeOfDay(14, 0));

        int sessionCountAtTwoPm = (sessionsAtTwoPm == null ? 0 : sessionsAtTwoPm.size());
        int expectedSessionCountAtTwoPm = 0;

        Assertions.assertEquals(expectedSessionCountAtTwoPm, sessionCountAtTwoPm);
    }

    @Test
    void shouldReturnCorrectSessionByDayAndTime() {
        Group groupChild = new Group("Акробатика для детей", Age.CHILD, 60);
        Coach coach = new Coach("Васильев", "Николай", "Сергеевич");
        TrainingSession expectedTrainingSession = new TrainingSession(groupChild, coach, DayOfWeek.SUNDAY,
                new TimeOfDay(13, 0));

        timetable.addNewTrainingSession(expectedTrainingSession);

        List<TrainingSession> sundayTrainingSessions = timetable.getTrainingSessionsForDayAndTime(DayOfWeek.SUNDAY,
                new TimeOfDay(13, 0));

        TrainingSession testTrainingSession = ((sundayTrainingSessions == null || sundayTrainingSessions.isEmpty()) ?
                null : sundayTrainingSessions.getFirst());

        Assertions.assertEquals(expectedTrainingSession, testTrainingSession);
    }

    @Test
    void shouldNotReturnTrainingSessionsForDayIfWrongDay() {
        Map<TimeOfDay, List<TrainingSession>> expectedNull = null;
        Map<TimeOfDay, List<TrainingSession>> test = timetable.getTrainingSessionsForDay(DayOfWeek.SUNDAY);

        Assertions.assertEquals(expectedNull, test);
    }

    @Test
    void shouldNotReturnTrainingSessionsForDayAndTimeIfWrongTime() {
        List<TrainingSession> expectedNull = null;
        List<TrainingSession> test = timetable.getTrainingSessionsForDayAndTime(DayOfWeek.MONDAY,
                new TimeOfDay(13, 1));

        Assertions.assertEquals(expectedNull, test);
    }

    @Test
    void shouldReturnCoachWithMostTrainingSessionsFirst() {
        Coach expectedCoachWithMaxTrainingSessions = new Coach("Иванов", "Иван", "Борисович");

        List<Coach> coachesSorted = timetable.getCoachesByCount();
        Coach firstCoachInSortedList = (coachesSorted == null || coachesSorted.isEmpty()) ? null : coachesSorted.get(0);

        Assertions.assertEquals(expectedCoachWithMaxTrainingSessions, firstCoachInSortedList);
    }

    @Test
    void shouldReturnCoachWithLeastTrainingSessionsLast() {
        Coach expectedCoachWithLeastTrainingSessions = new Coach("Васильев", "Николай",
                "Сергеевич");

        List<Coach> coachesSorted = timetable.getCoachesByCount();
        Coach lastCoachInSortedList = (coachesSorted == null || coachesSorted.isEmpty()) ?
                null : coachesSorted.getLast();

        Assertions.assertEquals(expectedCoachWithLeastTrainingSessions, lastCoachInSortedList);
    }

    @Test
    void shouldNotAddCoachWithNoTrainingSessionsToRegistry() {
        Coach coachWithoutTrainingSessions = new Coach("Петров", "Петр", "Петрович");
        List<Coach> coaches = timetable.getCoachesByCount();

        Assertions.assertFalse(coaches.contains(coachWithoutTrainingSessions));
    }

    private static int countTrainingSessionsInDay(Map<TimeOfDay, List<TrainingSession>> timetableForDay) {

        if (timetableForDay == null) return 0;

        int trainingSessions = 0;

        for (List<TrainingSession> trainingSessionsForTime : timetableForDay.values()) {
            for (TrainingSession session : trainingSessionsForTime) {
                trainingSessions++;
            }
        }

        return trainingSessions;
    }

}
