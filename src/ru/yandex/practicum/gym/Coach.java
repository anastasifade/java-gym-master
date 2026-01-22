package ru.yandex.practicum.gym;

import java.util.Objects;

public class Coach implements Comparable<Coach> {

    //фамилия
    private String surname;
    //имя
    private String name;
    //отчество
    private String middleName;

    // количество тренировок
    private int trainingSessionsCount;

    public Coach(String surname, String name, String middleName) {
        this.surname = surname;
        this.name = name;
        this.middleName = middleName;
        this.trainingSessionsCount = 0;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Coach coach = (Coach) o;
        return Objects.equals(surname, coach.surname) && Objects.equals(name, coach.name) &&
                Objects.equals(middleName, coach.middleName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(surname, name, middleName);
    }

    @Override
    public int compareTo(Coach o) {
        return o.trainingSessionsCount - this.trainingSessionsCount;
    }

    // увеличение счетчика тренировок при добавлении новой тренировки с тренером в расписание
    public void assignToTrainingSession() {
        this.trainingSessionsCount++;
    }

    // снижение счета тренировок - при удалении тренировки из расписания и/или замене тренера в тренировке на другого
    public void removeFromTrainingSession() {
        this.trainingSessionsCount--;
    }

    public String getSurname() {
        return surname;
    }

    public String getName() {
        return name;
    }

    public String getMiddleName() {
        return middleName;
    }

    public int getTrainingSessionsCount() {
        return trainingSessionsCount;
    }
}
