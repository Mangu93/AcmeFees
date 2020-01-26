package com.amp.acmefees.utils;

import com.amp.acmefees.domain.Fee;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import org.springframework.data.mongodb.core.MongoTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class FeeUtils {

    public static Set<Map.Entry<String, JsonElement>> insertEntries(JsonElement jsonElement, MongoTemplate mongoTemplate) {
        Set<Map.Entry<String, JsonElement>> entries = jsonElement.getAsJsonObject().entrySet();
        for (Map.Entry<String, JsonElement> entry : entries) {
            String key = entry.getKey();
            JsonArray jsonArray = entry.getValue().getAsJsonArray();
            jsonArray.forEach(jsonElement1 -> {
                List<String> fees = new ArrayList<>();
                String limLow = ((JsonArray) jsonElement1).get(0).getAsString();
                String limTop = ((JsonArray) jsonElement1).get(1).getAsString();
                String fee = ((JsonArray) jsonElement1).get(2).getAsString();
                fees.add(limLow);
                fees.add(limTop);
                fees.add(fee);
                mongoTemplate.save(new Fee(key, fees));
            });
        }
        return entries;
    }
}
