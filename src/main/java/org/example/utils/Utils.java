package org.example.utils;

public class Utils {
    public static String generateTicketId(String name, int age, String berthPreference) {
        int randomNum = (int) (Math.random() * 10000);
        return name + age + berthPreference + randomNum;
    }
}
