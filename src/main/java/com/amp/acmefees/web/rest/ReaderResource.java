package com.amp.acmefees.web.rest;

import com.amp.acmefees.domain.Fee;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import static com.amp.acmefees.utils.FeeUtils.insertEntries;

@RestController
@RequestMapping("/api")
public class ReaderResource {
    private final Logger log = LoggerFactory.getLogger(ReaderResource.class);

    private final MongoTemplate mongoTemplate;

    public ReaderResource(MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }

    @PostMapping("/fees")
    public ResponseEntity<String> receiveFees(@RequestBody String payload) {
        log.info("Received {}", payload);
        JsonElement jsonElement = JsonParser.parseString(payload);
        Set<Map.Entry<String, JsonElement>> entries = insertEntries(jsonElement, mongoTemplate);
        log.info("Inserted {} entities", entries.size());
        return new ResponseEntity<>("{\"success\":1}", HttpStatus.OK);
    }

    @GetMapping("/fees")
    public ResponseEntity<Object> readAllFees() {
        Query query = new Query();
        List<Fee> feeList = mongoTemplate.find(query, Fee.class);
        List<Fee.CompositeKey> keyList = feeList.stream().map(Fee::getId).collect(Collectors.toList());
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        return new ResponseEntity<>(gson.toJson(keyList), HttpStatus.OK);
    }

    @GetMapping("/fees/{category}")
    public ResponseEntity<Object> readAllFeesByCategory(@PathVariable String category) {
        Query query = new Query();
        query.addCriteria(Criteria.where("category").is(category));
        List<Fee> feeList = mongoTemplate.find(query, Fee.class);
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        return new ResponseEntity<>(gson.toJson(feeList), HttpStatus.OK);
    }
}
