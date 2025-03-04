package fr.univtln.eberge.solarsystem.utils;

import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

public class TimeManager {
    private float speedFactor = 1f;

    public float getSpeedFactor() {
        return speedFactor;
    }

    public void setSpeedFactor(float speedFactor) {
        this.speedFactor = speedFactor;
    }

    public String getFormattedTime(double time) {

        Instant instant = Instant.ofEpochSecond((long)time);
        ZonedDateTime zonedDateTime = instant.atZone(ZoneId.systemDefault());
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
        String affichage = zonedDateTime.format(dateTimeFormatter);
        return affichage;
    }

}
