package com.supinfo.suplink.util;

public class CheckNumber {
	public static boolean checkIfNumber(String in) {
        
        try {

            Integer.parseInt(in);
        
        } catch (NumberFormatException ex) {
            return false;
        }
        
        return true;
    }
}
