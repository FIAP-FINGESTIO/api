package br.com.fingestio.api.utils;

public class Global {
    public static String convertBooleanStringToYesNo(Boolean boolStr) {
        if (boolStr == null) {
            return null;
        }
        return boolStr ? "Y" : "N";
    } 
}
