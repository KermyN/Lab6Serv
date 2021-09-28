package com.Kermy.Lab6.commands;

import com.Kermy.Lab6.collection.*;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.time.ZonedDateTime;
import java.util.Scanner;

/**
 * класс для ввода полей элемента.
 */
public class FieldsScanner {
    private static Scanner sc;
    private static FieldsScanner fs;
    /**
     * Instantiates a new Input helper.
     *
     * @param scanner the scanner
     */
    BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
    private FieldsScanner(Scanner scanner){
        sc = scanner;
    }

    public void configureScanner(Scanner scanner){
        sc = scanner;
    }
    public static FieldsScanner getInstance(){
        if(fs==null) {
            fs = new FieldsScanner(new Scanner(System.in));
        }
        return fs;
    }


    private String scanLine(String cheVvodit){
        System.out.println("введите " + cheVvodit);
        return sc.nextLine().trim();
    }

    private String scanLine(){
        return sc.nextLine().trim();
    }


    private String scanNotEmptyLine(String cheVvodit){
        String res = scanLine(cheVvodit);
        while(res.trim().isEmpty()) {
            System.out.println("Строка не должна быть пустой или состоять только из пробелов.");
            System.out.println("Введите " + cheVvodit + " заново.");
            res = sc.nextLine();
        }
        return res.trim();
    }

    /**
     * метод для ввода аргументов-строк. Все Стринговые аргументы в лабе не могут быть пустыми.
     * Имена то есть.
     *
     * @param cheVvodit че вводить?
     * @return введенное пользователем, скорее всего, имя
     */
    public String scanStringArg(String cheVvodit){
        String str = scanLine(cheVvodit);
        while(str==null || str.equals("")){
            System.out.println("Не может быть пустой. Введите " + cheVvodit + " еще раз.");
            str = sc.nextLine();
        }
        return str;
    }

    /**
     * метод для сканирования любого Enum. проверяет, является ли введенная
     * пользователем строка элементом enum'а, который передается во втором аргументе.
     *
     * @param canBeNull может ли быть Enum пустым?
     * @param enumType  тип перечисления
     * @return enum
     */
    public Enum<?> scanEnum(boolean canBeNull, Class<? extends Enum> enumType){
        /*
        не могу вывести доступные значение Enum'а прям тут, потому что...
        Обратите внимание, что ни метод valueOf(), ни метод values() не определен в классе java.lang.Enum.
        Вместо этого они автоматически добавляются компилятором на этапе компиляции enum-класса.
         */
        while(true) {
            String str = scanLine();
            try {
                if (str.equals("") && canBeNull) return null;
                else if (str.equals("")){
                    throw new NullPointerException();
                }
                return Enum.valueOf(enumType, str);
            } catch (IllegalArgumentException | NullPointerException e) {
                System.out.println("Пожалуйста, введите одно из значений enum'а.");
            }
        }
    }

    /**
     * метод для сканирования аргумента который должен быть integer
     *
     * @param cheVvodit    че вводить?
     * @param positiveOnly должно ли число быть только положительным?
     * @return число int
     */
    public int scanInteger(String cheVvodit, boolean positiveOnly){
        while(true) {
            String input = scanNotEmptyLine(cheVvodit);
            int res;
            try{
                res = Integer.parseInt(input);
                if(positiveOnly && (res<=0)){
                    System.out.println("необходимо ввести число большее нуля");
                }else   {
                    return res;
                }
            }catch (Exception e){
                System.out.println("введите целое число");
            }
        }
    }

    /**
     * метод для сканирования аргумента который должен быть float
     *
     * @param cheVvodit    че вводить?
     * @param positiveOnly должно ли число быть только положительным?
     * @return число float
     */
    public float scanFloat(String cheVvodit, boolean positiveOnly){
        while(true) {
            String input = scanNotEmptyLine(cheVvodit);
            float res;
            try{
                res = Float.parseFloat(input);
                if(positiveOnly && (res<=0)){
                    System.out.println("необходимо ввести число большее нуля");
                }else{
                    return res;
                }
            }catch (Exception e){
                System.out.println("введите число");
            }
        }
    }







    /**
     * сканирует всего дракона. проверяет правильность ввода полей. учитывает, какие поля
     * могут быть null, а какие поля-числа больше нуля
     *
     * @return дракон dragon
     */
    public Dragon scanDragon(){
        String name = scanStringArg("имя дракона");
        System.out.println("Координаты.");
        int x = scanInteger("Х", false);
        int y = scanInteger("Y", false);
        Coordinates coordinates = new Coordinates(x, y);
        int age = scanInteger("возраст дракона", true);
        System.out.println("Введите тип дракона. Доступные типы: ");
        for(DragonType t : DragonType.values()){
            System.out.print(t + " ");
        }
        System.out.println();
        DragonType type = (DragonType) scanEnum( true, DragonType.class);
        System.out.println("Введите характер дракона. Доступные типы: ");
        for(DragonCharacter t : DragonCharacter.values()){
            System.out.print(t + " ");
        }
        System.out.println();
        DragonCharacter character = (DragonCharacter) scanEnum(false, DragonCharacter.class);
        float treasure = scanFloat("Количество сокоровищь (это число)", true);

        DragonCave cave = new DragonCave(treasure);
        Color color = (Color) scanEnum(true,Color.class);

        return new Dragon(12,name, coordinates, ZonedDateTime.now(), age,color,type, character,cave);
    }

}