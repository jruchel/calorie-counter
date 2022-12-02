package com.jruchel.caloriecounter.service.nutritionix;

import com.jruchel.caloriecounter.configuration.NutritionixConfig;
import com.jruchel.caloriecounter.model.external.NutritionixNutrientsRequest;
import com.jruchel.caloriecounter.model.external.NutritionixNutrientsResponse;
import java.net.URI;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

@Service
@RequiredArgsConstructor
public class NutritionixClient {

    private final RestTemplate restTemplate;
    private final NutritionixConfig nutritionixConfig;

    public Optional<NutritionixNutrientsResponse> getNutrients(String foodItem) {
        RequestEntity<NutritionixNutrientsRequest> requestEntity =
                RequestEntity.post(getNutrientsURI())
                        .headers(getHeaders())
                        .body(new NutritionixNutrientsRequest(foodItem));
        ResponseEntity<NutritionixNutrientsResponse> response =
                restTemplate.exchange(requestEntity, NutritionixNutrientsResponse.class);
        return Optional.ofNullable(response.getBody());
    }

    private HttpHeaders getHeaders() {
        MultiValueMap<String, String> multiValueMap = new LinkedMultiValueMap<>();
        multiValueMap.add("x-app-id", nutritionixConfig.getAppId());
        multiValueMap.add("x-app-key", nutritionixConfig.getAppKey());
        multiValueMap.add("Content-Type", "application/json");

        return new HttpHeaders(multiValueMap);
    }

    private URI getNutrientsURI() {
        String nutrientsEndpoint = "/v2/natural/nutrients";
        return URI.create(nutritionixConfig.getAppAddress() + nutrientsEndpoint);
    }
}
