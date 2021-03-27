package com.company;

import java.io.*;

public class Main {

    public static void main(String[] args) throws IOException {
        int lines = 0;
        double sumTemperature = 0;
        double maxTemperature = Double.MIN_VALUE;
        String dayMaxTemperature = null;
        double y;
        double sumRelativeHumidity = 0;
        double minRelativeHumidity = Double.MAX_VALUE;
        String dayMinRelativeHumidity = null;
        double z;
        double sumWindSpeed = 0;
        double maxWindSpeed = Double.MIN_VALUE;
        String dayMaxWindSpeed = null;
        double x;
        double windDirection;
        String stringWindDirection = null;
        int north = 0;
        int west = 0;
        int east = 0;
        int south = 0;

        try {
            FileReader fr = new FileReader("C:\\Users\\Admin\\Desktop\\weather.txt");
            BufferedReader reader = new BufferedReader(fr);
            String line = reader.readLine();
            int redundantLine = 1;
            while (line != null) {
                if(redundantLine > 10) {
                    String[] split = line.split(",");
                    y = Double.parseDouble(split[1]);
                    if(y > maxTemperature){
                        dayMaxTemperature = split[0];
                        maxTemperature = y;
                    }
                    z = Double.parseDouble(split[2]);
                    if(z < minRelativeHumidity){
                        dayMinRelativeHumidity= split[0];
                        minRelativeHumidity = z;
                    }
                    x = Double.parseDouble(split[3]);
                    if (x > maxWindSpeed){
                        dayMaxWindSpeed = split[0];
                        maxWindSpeed = x;
                    }
                    windDirection = Double.parseDouble(split[4]);
                    sumTemperature += y;
                    sumRelativeHumidity += z;
                    sumWindSpeed += x;
                    lines++;
                    if (((windDirection > 0) && (windDirection < 50)) || ((windDirection > 320) && (windDirection < 359))){
                        north++;
                    }
                    if ((windDirection > 50) && (windDirection < 140)){
                        east++;
                    }
                    if ((windDirection > 140) && (windDirection < 230)){
                        south++;
                    }
                    if ((windDirection > 230) && (windDirection < 320)){
                        west++;
                    }
                }
                line = reader.readLine();
                redundantLine++;
            }
            fr.close();
            reader.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        double averageTemperature = sumTemperature / lines;
        double averageRelativeHumidity = sumRelativeHumidity / lines;
        double averageWindSpeed = sumWindSpeed / lines;
        int max1 = Math.max(north,east);
        int max2 = Math.max(west,south);
        int maxMax = Math.max(max1,max2);
        if (maxMax == north){
            stringWindDirection = "North";
        }
        if (maxMax == west){
            stringWindDirection = "West";
        }
        if (maxMax == south){
            stringWindDirection ="South";
        }
        if (maxMax == east){
            stringWindDirection = "East";
        }


        try (FileWriter writer = new FileWriter("C:\\Users\\Admin\\Desktop\\newWeather.txt",false)) {
            BufferedWriter bufWriter = new BufferedWriter(writer);
            bufWriter.write("Средняя температура воздуха: " + averageTemperature + "\n" +
                    "Средняя влажность: " + averageRelativeHumidity + "\n" +
                    "Средняя скорость ветра: " + averageWindSpeed + "\n" +
                    "Самое частое направление ветра: " + stringWindDirection + "\n" +
                    "<" + correctData(dayMaxTemperature) + ">, была самая высокая температура: " + maxTemperature + "\n" +
                    "<" + correctData(dayMinRelativeHumidity) + ">, была самая низкая влажность: " + minRelativeHumidity + "\n" +
                    "<" + correctData(dayMaxWindSpeed) + ">, был самый сильный ветер: " + maxWindSpeed + "\n");
            bufWriter.close();
        } catch (IOException ex) {
            System.out.println("error");
        }
    }
    static public String correctData(String s){
        String correctData = "День: ";
        char[] chars = s.toCharArray();
        for (int i = 0; i < chars.length; i++) {
            switch (i){
                case 4:
                    correctData = correctData + chars[4];
                    break;
                case 5:
                    correctData = correctData + chars[5] + "-";
                    break;
                case 6:
                    correctData = correctData + chars[6];
                    break;
                case 7:
                    correctData = correctData + chars[7] + ", время: " ;
                    break;
                case 9:
                    correctData = correctData + chars[9];
                    break;
                case 10:
                    correctData = correctData + chars[10] + ":";
                    break;
                case 11:
                    correctData = correctData + chars[11];
                    break;
                case 12:
                    correctData = correctData + chars[12];
                    break;
            }
        }
        return correctData;
    }
}
