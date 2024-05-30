package com.ibhuerli.URLShortener;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.ibhuerli.URLShortener.models.ShortUrl;
import com.ibhuerli.URLShortener.repositories.ShortUrlRepository;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.testcontainers.containers.MongoDBContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;

import java.util.UUID;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@Testcontainers
@AutoConfigureMockMvc
public class UrlShortenerApplicationTests {
    @Container
    public static MongoDBContainer mongoDBContainer = new MongoDBContainer(DockerImageName.parse("mongo:5"));

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @DynamicPropertySource
    static void mongoDbProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.data.mongodb.uri", mongoDBContainer::getReplicaSetUrl);
    }

    @Test
    @WithMockUser(username = "user", password = "password", roles = "USER")
    void testDeleteShortUrl() throws Exception {
        ShortUrl shortUrl = new ShortUrl();
        shortUrl.setId(UUID.randomUUID());
        shortUrl.setShortUrl("stengelberg");
        shortUrl.setUrl("https://www.youtube.com");

        mockMvc.perform(put("/shorturls").content(objectMapper.writeValueAsString(shortUrl)));

        mockMvc.perform(delete("/shorturls/" + shortUrl.getId()))
                .andExpect(status().isOk())
                .andExpect(content().string("deleted short url"));

        mockMvc.perform(get("/shorturls"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$").isEmpty());
    }

//    @BeforeAll
//    void setUp() {
//    }
//
//    @Test
//    void testGetShortUrls() throws Exception {
//        mockMvc.perform(get("/shorturls"))
//                .andExpect(status().isOk())
//                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
//                .andExpect(jsonPath("$[0]").value("short123"));
//    }
//
//    @Test
//    @WithMockUser(username = "user", password = "password", roles = "USER")
//    void testRedirectToUrl() throws Exception {
//        ShortUrl shortUrl = new ShortUrl();
//        shortUrl.setId(UUID.randomUUID());
//        shortUrl.setShortUrl("engelberg");
//        shortUrl.setUrl("https://www.youtube.com");
//
//        //ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
//        //String json = ow.writeValueAsString(shortUrl);
//
//        mockMvc.perform(put("/shorturls").content(objectMapper.writeValueAsString(shortUrl)));
//
//        mockMvc.perform(get("/a/engelberg"))
//                //.andExpect(status().is3xxRedirection())
//                .andExpect(redirectedUrl("https://www.youtube.com"));
//    }
//
//    @Test
//    void testGetShortUrlById() throws Exception {
//        ShortUrl shortUrl = shortUrlRepository.findAll().get(0);
//
//        mockMvc.perform(get("/shorturls/" + shortUrl.getId()))
//                .andExpect(status().isOk())
//                .andExpect(content().string("short123"));
//    }
//
//    @Test
//    void testCreateOrUpdateShortUrl() throws Exception {
//        UUID newId = UUID.randomUUID();
//        String newUrlJson = String.format(
//                "{\"id\": \"%s\", \"shortUrl\": \"short456\", \"url\": \"http://another-example.com\"}", newId);
//
//        mockMvc.perform(put("/shorturls")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(newUrlJson))
//                .andExpect(status().isOk())
//                .andExpect(content().string("added short url"));
//
//        mockMvc.perform(get("/shorturls"))
//                .andExpect(status().isOk())
//                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
//                .andExpect(jsonPath("$[1]").value("short456"));
//    }
}
