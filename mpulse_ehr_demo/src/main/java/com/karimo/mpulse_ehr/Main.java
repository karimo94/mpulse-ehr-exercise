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
import com.karimo.mpulse_ehr.models.HealthProvider;

public class Main {
    static final String ENDPOINT = "https://www.healthit.gov/data/open-api?source=Meaningful-Use-Acceleration-Scorecard.csv";
    public static void main(String[] args) {
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder().GET().uri(URI.create(ENDPOINT)).build();
        
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
        Gson gson = new Gson();
        ArrayList<HealthProvider> healthProviders = null;
        try {
            healthProviders = gson.fromJson(response.body(), new TypeToken<ArrayList<HealthProvider>>(){}.getType());
        }
        catch(Exception convertException) {
            convertException.printStackTrace();
        }
        //some cleaning up
        Predicate<HealthProvider> isNullValues = n -> n.period == null || n.region == null;
        healthProviders.removeIf(isNullValues);
        Predicate<HealthProvider> isNot2014 = n -> !n.period.equals("2014");
        healthProviders.removeIf(isNot2014);
        Predicate<HealthProvider> isNationalRegion = n -> n.region.equals("National");
        healthProviders.removeIf(isNationalRegion);

        for(int i = healthProviders.size() - 1; i >= 0; i--) {
            System.out.println("State: " + healthProviders.get(i).region + 
                " -- Percentage: " + (100 * Double.parseDouble(healthProviders.get(i).pct_hospitals_mu)));
        }
    }
}