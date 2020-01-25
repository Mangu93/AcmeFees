package com.amp.acmefees.web.rest;

import com.amp.acmefees.repository.FeeRepository;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.Set;

import static com.amp.acmefees.utils.FeeUtils.insertEntries;

@RestController
@RequestMapping("/api")
public class ReaderResource {
    private final Logger log = LoggerFactory.getLogger(ReaderResource.class);

//    private final FeeRepository feeRepository;

    public ReaderResource() {
//        this.feeRepository = feeRepository;
    }
    @PostMapping("/fees")
    public ResponseEntity<String> receiveFees(@RequestBody String payload) {
        log.info("Received {}", payload);
        JsonElement jsonElement = JsonParser.parseString(payload);
        Set<Map.Entry<String, JsonElement>> entries = insertEntries(jsonElement, null);
        log.info("Inserted {} entities", entries.size());
        return new ResponseEntity<>("{\"success\":1}", HttpStatus.OK);
    }

    @GetMapping("/fees")
    public ResponseEntity<Object> readAllFees() {
        throw new UnsupportedOperationException();
    }
}
