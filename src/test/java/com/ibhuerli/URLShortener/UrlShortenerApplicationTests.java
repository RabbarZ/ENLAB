package com.ibhuerli.URLShortener;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ibhuerli.URLShortener.models.ShortUrl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.testcontainers.containers.MongoDBContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;

import java.util.ArrayList;
import java.util.List;
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

    @BeforeEach
    void setUp() throws Exception {
        // Clear the database before each test
        List<String> ids = new ArrayList<>();
        ids.add("973ea25d-d596-4107-96fb-bda225800882");
        ids.add("0835e673-d1c0-46c9-87b5-d3f4fe0973aa");
        ids.add("db90b3f0-8ee3-4e31-bbd6-377f6962db22");
        ids.add("53012196-bc75-4bf8-a19d-ac6678ba24df");
        ids.add("64b9c38b-92a2-490e-87a5-c210e1b432cf");
        ids.add("741d058b-d7b7-484c-a168-16f524068c53");

        for (String item : ids) {
            mockMvc.perform(delete("/shorturls/" + item))
                    .andExpect(status().isOk());
        }
    }

    @Test
    @WithMockUser(username = "user", password = "password", roles = "USER")
    void testDeleteShortUrl() throws Exception {
        ShortUrl shortUrl = new ShortUrl();
        shortUrl.setId(UUID.fromString("973ea25d-d596-4107-96fb-bda225800882"));
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

    @Test
    @WithMockUser(username = "user", password = "password", roles = "USER")
    void testCreateOrUpdateShortUrl() throws Exception {
        ShortUrl shortUrl = new ShortUrl();
        shortUrl.setId(UUID.fromString("0835e673-d1c0-46c9-87b5-d3f4fe0973aa"));
        shortUrl.setShortUrl("shortUrl1");
        shortUrl.setUrl("https://example.com");

        mockMvc.perform(put("/shorturls")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(shortUrl)))
                .andExpect(status().isOk())
                .andExpect(content().string("added short url"));

        mockMvc.perform(get("/shorturls"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0]").value("shortUrl1"));
    }

    @Test
    @WithMockUser(username = "user", password = "password", roles = "USER")
    void testGetShortUrlById() throws Exception {
        ShortUrl shortUrl = new ShortUrl();
        UUID id = UUID.fromString("db90b3f0-8ee3-4e31-bbd6-377f6962db22");
        shortUrl.setId(id);
        shortUrl.setShortUrl("shortUrl2");
        shortUrl.setUrl("https://example2.com");

        mockMvc.perform(put("/shorturls")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(shortUrl)))
                .andExpect(status().isOk())
                .andExpect(content().string("added short url"));

        mockMvc.perform(get("/shorturls/" + id))
                .andExpect(status().isOk())
                .andExpect(content().string("shortUrl2"));
    }

    @Test
    @WithMockUser(username = "user", password = "password", roles = "USER")
    void testUpdateShortUrl() throws Exception {
        ShortUrl shortUrl = new ShortUrl();
        UUID id = UUID.fromString("53012196-bc75-4bf8-a19d-ac6678ba24df");
        shortUrl.setId(id);
        shortUrl.setShortUrl("shortUrl3");
        shortUrl.setUrl("https://example3.com");

        mockMvc.perform(put("/shorturls")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(shortUrl)))
                .andExpect(status().isOk())
                .andExpect(content().string("added short url"));

        shortUrl.setShortUrl("updatedShortUrl3");
        shortUrl.setUrl("https://updatedexample3.com");

        mockMvc.perform(put("/shorturls/" + id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(shortUrl)))
                .andExpect(status().isOk())
                .andExpect(content().string("updated short url"));

        mockMvc.perform(get("/shorturls/" + id))
                .andExpect(status().isOk())
                .andExpect(content().string("updatedShortUrl3"));
    }

    @Test
    @WithMockUser(username = "user", password = "password", roles = "USER")
    void testDeleteShortUrl2() throws Exception {
        ShortUrl shortUrl = new ShortUrl();
        UUID id = UUID.fromString("64b9c38b-92a2-490e-87a5-c210e1b432cf");
        shortUrl.setId(id);
        shortUrl.setShortUrl("shortUrl4");
        shortUrl.setUrl("https://example4.com");

        mockMvc.perform(put("/shorturls")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(shortUrl)))
                .andExpect(status().isOk())
                .andExpect(content().string("added short url"));

        mockMvc.perform(delete("/shorturls/" + id))
                .andExpect(status().isOk())
                .andExpect(content().string("deleted short url"));

        mockMvc.perform(get("/shorturls"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$").isEmpty());
    }


    @Test
    @WithMockUser(username = "user", password = "password", roles = "USER")
    void testRedirectToUrl() throws Exception {
        ShortUrl shortUrl = new ShortUrl();
        shortUrl.setId(UUID.fromString("741d058b-d7b7-484c-a168-16f524068c53"));
        shortUrl.setShortUrl("shortUrl5");
        shortUrl.setUrl("https://example5.com");

        mockMvc.perform(MockMvcRequestBuilders.put("/shorturls")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(shortUrl)))
                .andExpect(status().isOk())
                .andExpect(content().string("added short url"));

        mockMvc.perform(MockMvcRequestBuilders.get("/a/shortUrl5"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("https://example5.com"));
    }

    @Test
    @WithMockUser(username = "user", password = "password", roles = "USER")
    void testInvalidShortUrlId() throws Exception {
        // Test attempting to retrieve statistics with an invalid short URL ID
        String invalidId = "invalidId";
        mockMvc.perform(MockMvcRequestBuilders.get("/shorturls/" + invalidId + "/statistics"))
                .andExpect(status().isBadRequest());
    }
}
