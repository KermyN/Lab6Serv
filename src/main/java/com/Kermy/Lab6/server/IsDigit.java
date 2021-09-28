package com.Kermy.Lab6.server;

public class IsDigit {

    public static boolean isDouble(String s) throws NumberFormatException {
        try {
            Double.parseDouble(s);
            return true;
        } catch (NumberFormatException e) {return false;}
    }

    public static boolean isFloat(String s) throws NumberFormatException {
        try {
            Float.parseFloat(s);
            return true;
        } catch (NumberFormatException e) {return false;}
    }

    public static boolean isInteger(String s) throws NumberFormatException {
        try {
            Integer.parseInt(s);
            return true;
        } catch (NumberFormatException e) { return false;}
    }
}
