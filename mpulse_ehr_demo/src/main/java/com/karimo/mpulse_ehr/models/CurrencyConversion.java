package com.karimo.mpulse_ehr.models;

public class CurrencyConversion {
    public boolean success;
    public String[] validationMessage;
    class Result {
        public String from;
        public String to;
        public String amountToConvert;
        public String convertedAmount;
    }
    public Result result;
    @Override
    public String toString() {
        return "From : " + result.from + " -- To: " + result.to + 
            " -- Amount: " + result.amountToConvert + " -- Conversion: " + result.convertedAmount;
    }
}
