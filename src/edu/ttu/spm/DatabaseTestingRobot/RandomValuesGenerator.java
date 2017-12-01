package edu.ttu.spm.DatabaseTestingRobot;

import java.util.Random;

public class RandomValuesGenerator {

    public String getRandomDate(){
        Random rand = new Random();
        int randomYear = rand.nextInt(20) + 2000;
        int randomMonth = rand.nextInt(2) + 10;
        int randomHour = rand.nextInt(14) + 10;
        int randomMin = rand.nextInt(50) + 10;
        int randomSec = rand.nextInt(50) + 10;

        return randomYear + "-" + randomMonth + "-2T" + randomHour + ":" + randomMin + ":" + randomSec + ".511Z";
    }

    public String getRandomProvider(){
        String SALTCHARS = "QWERTYUIOPASDFGHJKLZXCVBNM";
        StringBuilder salt = new StringBuilder();
        Random rnd = new Random();
        int index = (int) (rnd.nextFloat() * SALTCHARS.length());
        salt.append(SALTCHARS.charAt(index));
        return salt.toString() + "company";
    }

    public String getRandomPickup(){
        String SALTCHARS = "QWERTYUIOPASDFGHJKLZXCVBNM";
        StringBuilder salt = new StringBuilder();
        Random rnd = new Random();
        int index = (int) (rnd.nextFloat() * SALTCHARS.length());
        salt.append(SALTCHARS.charAt(index));
        return salt.toString() + "place";
    }

    public String getRandomDestination(){
        String SALTCHARS = "QWERTYUIOPASDFGHJKLZXCVBNM";
        StringBuilder salt = new StringBuilder();
        Random rnd = new Random();
        int index = (int) (rnd.nextFloat() * SALTCHARS.length());
        salt.append(SALTCHARS.charAt(index));
        return salt.toString() + "place";
    }

    public String getRandomFee(){
        Random rand = new Random();
        int randomFee = rand.nextInt(20) + 10;
        return "$" + randomFee;
    }

    public String getRandomUserName(int number){
//        String SALTCHARS = "QWERTYUIOPASDFGHJKLZXCVBNM";
//        StringBuilder salt = new StringBuilder();
//        Random rnd = new Random();
//        int index = (int) (rnd.nextFloat() * SALTCHARS.length());
//        salt.append(SALTCHARS.charAt(index));
        return "student" + number + Thread.currentThread().getId();
    }
}
