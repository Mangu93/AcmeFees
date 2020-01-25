package com.amp.acmefees.utils;

import com.amp.acmefees.repository.FeeRepository;
import com.google.gson.JsonElement;

import java.util.Map;
import java.util.Set;

public class FeeUtils {
    public static Set<Map.Entry<String, JsonElement>> insertEntries(JsonElement jsonElement, FeeRepository feeRepository) {
        Set<Map.Entry<String, JsonElement>> entries = jsonElement.getAsJsonObject().entrySet();

        return entries;

    }
}
