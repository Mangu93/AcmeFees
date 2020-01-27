package com.amp.acmefees.web.rest;

import com.amp.acmefees.AcmeFeesApp;
import com.amp.acmefees.web.rest.errors.ExceptionTranslator;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.google.gson.Gson;
import org.apache.commons.io.IOUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.validation.Validator;

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

import static com.amp.acmefees.web.rest.TestUtil.createFormattingConversionService;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(classes = AcmeFeesApp.class)
public class ReaderResourceTest {
    @Autowired
    private Validator validator;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;
    @Autowired
    private ExceptionTranslator exceptionTranslator;
    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    private MockMvc mockMvc;
    @Autowired
    private MongoTemplate mongoTemplate;

    private ReaderResource readerResource;
    private ObjectMapper mapper;
    @Autowired
    private JavaTimeModule javaTimeModule;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        this.readerResource = Mockito.spy(new ReaderResource(mongoTemplate));
        this.mockMvc = MockMvcBuilders.standaloneSetup(readerResource).setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter)
            .setValidator(validator).build();

        mapper = new ObjectMapper();
        mapper.findAndRegisterModules();
        mapper.enable(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY);

        mapper.registerModule(javaTimeModule);
        mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
    }

    @Test
    public void testNoBody() throws Exception {
        this.mockMvc.perform(post("/api/fees").contentType(TestUtil.APPLICATION_JSON_UTF8).content("")).andExpect(status().is4xxClientError());
    }

    @Test
    public void testCorrect() throws Exception {
        String goodProductString = readFile("goodJson.json");

        this.mockMvc.perform(post("/api/fees").contentType(TestUtil.APPLICATION_JSON_UTF8).content(new Gson().toJson(goodProductString)))
            .andExpect(status().is2xxSuccessful());
    }

    private String readFile(String name) throws IOException {
        return IOUtils.toString(new FileInputStream("src/test/resources/" + name), StandardCharsets.UTF_8);
    }
}
