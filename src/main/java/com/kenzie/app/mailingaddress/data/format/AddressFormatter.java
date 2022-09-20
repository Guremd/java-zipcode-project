package com.kenzie.app.mailingaddress.data.format;

//utility - class that has static helper methods
//example: Math class: Math.min() or Math.random()

import java.util.HashMap;

public class AddressFormatter {
    //properties
    //lookup table - HashMap
    private static HashMap<String,String> abbreviationTable;

    //initialize -- hardcode
    public static void initializeAddressMap() {
        abbreviationTable = new HashMap<>();
        abbreviationTable.put("ST","STREET");
        abbreviationTable.put("ST.","STREET");
        abbreviationTable.put("St","STREET");
        abbreviationTable.put("St.","STREET");
        abbreviationTable.put("AVE","AVENUE");
        abbreviationTable.put("AVE.","AVENUE");
        abbreviationTable.put("Ave","AVENUE");
        abbreviationTable.put("Ave.","AVENUE");
        abbreviationTable.put("RD","ROAD");
        abbreviationTable.put("RD.","ROAD");
        abbreviationTable.put("Rd","ROAD");
        abbreviationTable.put("Rd.","ROAD");
    }

    //replace address String - use map
    public static String replaceAbbreviation(String input){
        String resultStr = input;

        //write replace logic
        //for each entry in hashmap, search key, replace with value
        for (String currentKey: abbreviationTable.keySet()) {
            resultStr = resultStr.replace(currentKey,abbreviationTable.get(currentKey));
        }
        return resultStr;
    }

    public static String formatAddress(String str){
        return str.toUpperCase().trim();
    }

    public static String formatStreetAddress(String str){
        return formatAddress(replaceAbbreviation(str));
    }


    public static void main(String[] args) {
        AddressFormatter.initializeAddressMap();
        System.out.println(AddressFormatter.formatAddress("123 Main St."));
        System.out.println(AddressFormatter.formatStreetAddress("123 asdf st."));
    }
}
