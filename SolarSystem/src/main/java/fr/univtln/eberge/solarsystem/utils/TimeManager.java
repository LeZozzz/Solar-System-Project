package fr.univtln.eberge.solarsystem.utils;

public class TimeManager {
    private float speedFactor = 0.01f;
    private float elapsedSimulatedTime = 0; // Temps en secondes
    private static final float EARTH_ROTATION_PERIOD = 360.0f; // Rotation propre = 1 jour
    private static final float EARTH_REVOLUTION_PERIOD = 360.0f; // Révolution = 1 an

    private float accumulatedRotation = 0; // Suivi de la rotation journalière
    private float accumulatedRevolution = 0; // Suivi de la révolution annuelle

    public void updateTime(float rotationAngle, float revolutionAngle) {
        // Ajoute la rotation propre
        accumulatedRotation += rotationAngle;
        if (accumulatedRotation >= EARTH_ROTATION_PERIOD) {
            elapsedSimulatedTime += 86400 * speedFactor; // Ajoute 1 jour
            accumulatedRotation -= EARTH_ROTATION_PERIOD;
        }

        // Ajoute la révolution
        accumulatedRevolution += revolutionAngle;
        if (accumulatedRevolution >= EARTH_REVOLUTION_PERIOD) {
            elapsedSimulatedTime += 86400 * 365.25f * speedFactor; // Ajoute 1 an
            accumulatedRevolution -= EARTH_REVOLUTION_PERIOD;
        }
    }

    public float getSpeedFactor() {
        return speedFactor;
    }

    public void setSpeedFactor(float speedFactor) {
        this.speedFactor = speedFactor;
    }

    public String getFormattedTime() {
        int totalSeconds = (int) elapsedSimulatedTime;
        int years = totalSeconds / (86400 * 365);
        int days = (totalSeconds % (86400 * 365)) / 86400;
        int hours = (totalSeconds % 86400) / 3600;
        int minutes = (totalSeconds % 3600) / 60;
        int seconds = totalSeconds % 60;
        return String.format("%d ans | %d jours | %02dh:%02dm:%02ds", years, days, hours, minutes, seconds);
    }
//    private float speedFactor = 1.0f;
//    private float elapsedSimulatedTime = 0;
//    private LocalDateTime startDateTime = LocalDateTime.of(2021, 1, 1, 0, 0, 0);
//
//    public void updateTime(float tpf, Planet earth) {
//        float earthRevolution = earth.getRevolutionPeriod(); // 1 an en période de révolution
//        float simulatedDaysPassed = (tpf * speedFactor * 365.25f) / earthRevolution;
//        elapsedSimulatedTime += simulatedDaysPassed * 86400; // Convertir en secondes
//    }
//
//    public float getSpeedFactor() {
//        return speedFactor;
//    }
//
//    public int getElapsedYears() {
//        return (int) (elapsedSimulatedTime / (365.25f * 86400));
//    }
//
//    public void setSpeedFactor(float speedFactor) {
//        this.speedFactor = speedFactor;
//    }
//
//    public String getFormattedTime() {
//        LocalDateTime currentDateTime = startDateTime.plusSeconds((long) elapsedSimulatedTime);
//        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
//        return currentDateTime.format(formatter);
//
////        int totalSeconds = (int) (elapsedSimulatedTime % (365.25f * 86400));
////        int days = totalSeconds / 86400;
////        int hours = (totalSeconds % 86400) / 3600;
////        int minutes = (totalSeconds % 3600) / 60;
////        int seconds = totalSeconds % 60;
////        return String.format("%d jours %d h %d m %d s", days, hours, minutes, seconds);
//    }
}
