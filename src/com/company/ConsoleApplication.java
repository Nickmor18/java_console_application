package com.company;

import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class ConsoleApplication {
    private static ArrayList<Point2D> pointListFirstGroup = new ArrayList<>();
    private static ArrayList<Point2D> pointListSecondGroup = new ArrayList<>();
    private static ArrayList<Point2D> pointListThirdGroup = new ArrayList<>();
    private static ArrayList<Point2D> pointListNullGroup = new ArrayList<>();
    private static Map<Integer, ArrayList<Point2D>> mapGroupCoordinates = new HashMap<>();

    public static void main(String[] args) {
        double param;
        boolean isGoodParam = true;
        String userCommand = "";
        String checkCommand = "";

        ArrayList<Double> paramsList = new ArrayList<>();
        Scanner scanner = new Scanner(System.in);

        while (true){
            System.out.println("Введите команду: ");
            userCommand = scanner.nextLine();
            String command = userCommand.trim();
            String[] words = command.split(" ");

            if (!words[0].isEmpty()) {
                checkCommand = words[0];
                for (String word : words){
                    if (word.isEmpty() || word.equals(checkCommand)) continue;
                    try {
                        param = Double.parseDouble(word);
                        paramsList.add(param);
                    } catch (Exception e) {
                        isGoodParam = false;
                        continue;
                    }
                }
            }

            if (!isGoodParam) {
                System.out.println("Ошибка. Кажется Вы ввели неверный параметр. Пожалуйста, попробуйте еще раз");
                paramsList.clear();
                isGoodParam = true;
                continue;
            }

            switch (checkCommand) {
                case "add":
                    System.out.println("Определена команда - add");
                    add(paramsList);
                    break;
                case "print":
                    System.out.println("Определена команда - print");
                    print(paramsList);
                    break;
                case "remove":
                    System.out.println("Определена команда - remove");
                    remove(paramsList);
                    break;
                case "clear":
                    System.out.println("Определена команда - clear");
                    break;
                case "help":
                    System.out.println("Выводим список команд");
                    break;
                default:
                    System.out.println("Ошибка. Команда не определена. Пожалуйста, попробуйте ввести команду раз");
                    break;

            }

            paramsList.clear();
        }
    }

    /**
     *
     * Реализация команды "add"
     *
     * @param paramList
     */
    private static void add(ArrayList<Double> paramList){
        double xCoord = 0;
        double yCoord = 0;


        int countCoordinates = paramList.size();
        if (countCoordinates <= 0) {
            System.out.println("Ошибка. Кажется Вы не указали координаты. Пожалуйста, попробуйте ввести команду раз");
            return;
        } else {
            if (countCoordinates % 2 != 0){
                System.out.println("Ошибка. Кажется Вы не указали координату \"Y\" для точки. Пожалуйста, попробуйте ввести команду еще раз");
                return;
            }

            for (int i = 0; i < paramList.size(); i++ ){
                if (i % 2 != 1) {
                    xCoord = paramList.get(i);
                    continue;
                }
                yCoord = paramList.get(i);

                //реализуем, в какую группу закинем координаты (математика..)
                boolean isGoodCoords = false;
                if (yCoord >= xCoord) {
                    pointListFirstGroup.add(new Point2D.Double(xCoord,yCoord));
                    isGoodCoords = true;
                }

                if (yCoord >= (xCoord*xCoord)){
                    pointListSecondGroup.add(new Point2D.Double(xCoord,yCoord));
                    isGoodCoords = true;
                }

                if (yCoord >= (xCoord*xCoord*xCoord)){
                    pointListThirdGroup.add(new Point2D.Double(xCoord,yCoord));
                    isGoodCoords = true;
                }
                if (!isGoodCoords) {
                    pointListNullGroup.add(new Point2D.Double(xCoord,yCoord));
                }
            }

            mapGroupCoordinates.put(1,  pointListFirstGroup);
            mapGroupCoordinates.put(2,  pointListSecondGroup);
            mapGroupCoordinates.put(3,  pointListThirdGroup);
        }
    }

    /**
     *
     * Реализация команды "print"
     *
     * @param paramList
     */
    private static void print(ArrayList<Double> paramList) {
        ArrayList<Point2D> groupNullList = new ArrayList<>();
        if (paramList.size() == 0) {
            if (mapGroupCoordinates.entrySet().size() > 0) {
                for (Map.Entry<Integer, ArrayList<Point2D>> entry: mapGroupCoordinates.entrySet()) {
                    Integer key = entry.getKey();
                    ArrayList<Point2D> groupList = entry.getValue();
                    if (groupList.size() == 0) {
                        System.out.println("Группа " + key + " - пуста");
                    } else {
                        System.out.print("Группа " + key + ": ");
                        for (int i = 0; i < groupList.size(); i++) {
                            System.out.print("(" + groupList.get(i).getX() + "," + groupList.get(i).getY() + ")  ");
                        }
                        System.out.println("");
                    }
                }
            } else {
                System.out.println("Группа 1 - пуста");
                System.out.println("Группа 2 - пуста");
                System.out.println("Группа 3 - пуста");
            }

            System.out.println("Кол-во точек не вошедших в группы: " + pointListNullGroup.size());
        } else {
            for (int i = 0; i < paramList.size(); i++ ){
                double doubleValueParam = paramList.get(i);
                int currentGroup = (int)doubleValueParam;
                ArrayList<Point2D>  pointsList = mapGroupCoordinates.get(currentGroup);
                if (pointsList != null) {
                    pointsList.forEach(point -> System.out.print("(" + point.getX() + ", " + point.getY() + ") "));
                } else {
                    System.out.print("");
                }
            }
            System.out.println("");
        }
    }

    /**
     *
     * Реализация команды "remove"
     *
     * @param paramList
     */
    private static void remove(ArrayList<Double> paramList){
        int countCoordinates = paramList.size();
        if (countCoordinates <= 0) {
            System.out.println("Ошибка. Кажется Вы не указали какие группы нужно очистить. Пожалуйста, попробуйте ввести команду раз");
            return;
        } else {
            for (int i = 0; i < paramList.size(); i++ ){
                double doubleValueParam = paramList.get(i);
                int currentGroup = (int)doubleValueParam;
                ArrayList<Point2D>  pointsList = mapGroupCoordinates.get(currentGroup);
                if (pointsList != null) {
                    pointsList.clear();
                }
            }
        }
    }
}
