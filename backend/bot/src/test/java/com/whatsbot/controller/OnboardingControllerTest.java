 package com.whatsbot.controller;

 import com.fasterxml.jackson.databind.ObjectMapper;
 import com.whatsbot.tenant.dto.OnboardStartRequest;
 import org.junit.jupiter.api.Test;
 import org.springframework.beans.factory.annotation.Autowired;
 import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
 import org.springframework.boot.test.context.SpringBootTest;
 import org.springframework.http.MediaType;
 import org.springframework.test.web.servlet.MockMvc;

 import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
 import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
 import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

 @SpringBootTest
 @AutoConfigureMockMvc
 class OnboardingControllerTest {

     @Autowired
     private MockMvc mockMvc;

     @Autowired
     private ObjectMapper objectMapper;

     @Test
     void startReturnsTenantId() throws Exception {
         OnboardStartRequest request = new OnboardStartRequest();
         request.setBusinessName("Biz");
         request.setPhoneNumber("123");
//         request.setAccessToken("token");
//         request.setAppSecret("secret");
//
         mockMvc.perform(post("/onboard/start")
                 .contentType(MediaType.APPLICATION_JSON)
                 .content(objectMapper.writeValueAsString(request)))
             .andExpect(status().isOk())
             .andExpect(jsonPath("$.tenantId").exists());
     }
 }
