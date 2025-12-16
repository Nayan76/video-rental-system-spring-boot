package com.Video.Rent;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
class VideoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    @WithMockUser(roles = "ADMIN")
    void createVideo_asAdmin_shouldReturn201() throws Exception {

        String videoJson = """
                {
                  "title": "JUnit Movie",
                  "director": "JUnit Director",
                  "genre": "Test",
                  "available": true
                }
                """;

        mockMvc.perform(post("/api/videos")
                .contentType(MediaType.APPLICATION_JSON)
                .content(videoJson))
                .andExpect(status().isCreated());
    }

    @Test
    @WithMockUser(roles = "CUSTOMER")
    void createVideo_asCustomer_shouldReturn403() throws Exception {

        String videoJson = """
                {
                  "title": "Forbidden Movie",
                  "director": "No Access",
                  "genre": "Test",
                  "available": true
                }
                """;

        mockMvc.perform(post("/api/videos")
                .contentType(MediaType.APPLICATION_JSON)
                .content(videoJson))
                .andExpect(status().isForbidden());
    }
}
