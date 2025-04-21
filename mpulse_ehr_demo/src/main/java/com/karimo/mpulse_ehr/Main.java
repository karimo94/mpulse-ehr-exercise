package com.karimo.mpulse_ehr;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandlers;
import java.util.ArrayList;
import java.util.function.Predicate;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.karimo.mpulse_ehr.models.CurrencyConversion;
import com.karimo.mpulse_ehr.models.HealthProvider;

public class Main {
    static final String ENDPOINT = "https://currency-converter18.p.rapidapi.com/api/v1/convert?";
    public static void main(String[] args) {
        String endpointArgs = "from=EUR&to=USD&amount=10";
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
        .GET()
        .header("x-rapidapi-key", "e14d9e09a5msh348b2bda8798db2p15d68cjsn1f2e7d7c5740")
        .header("x-rapidapi-host", "currency-converter18.p.rapidapi.com")
        .uri(URI.create(ENDPOINT + endpointArgs))
        .build();
        
        HttpResponse<String> response = null;
        try{
            response = client.send(request, BodyHandlers.ofString());
        }
        catch(IOException ex) {
            ex.printStackTrace();
        }
        catch(InterruptedException i_ex) {
            i_ex.printStackTrace();
        }
        if(response.statusCode() == 200) {
            Gson gson = new Gson();
            CurrencyConversion currencyRecord = null;
            try {
                currencyRecord = gson.fromJson(response.body(), CurrencyConversion.class);
            }
            catch(Exception convertException) {
                convertException.printStackTrace();
            }
            System.out.println(currencyRecord.toString());
        }
        else {
            System.out.println("Tried calling the API, returned: " + response.statusCode());
        }
    }
}