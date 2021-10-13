package soft.secure_disk.authService.controller;

import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import soft.secure_disk.authService.dto.RegisterRequest;
import soft.secure_disk.authService.service.AuthService;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(AuthController.class)
class AuthControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AuthService authService;

    @Test
    void registerGet() throws Exception {
        RequestBuilder request = MockMvcRequestBuilders
                .get("/auth/register");

        mockMvc.perform(request)
                .andExpect(status().isOk())
                .andExpect(view().name("/users/form"))
                .andExpect(model().attributeExists("registerRequest"));
    }


    @Test
    void activationEmail() throws Exception {
        String activationLink = UUID.randomUUID().toString();
        String url = "/auth/token/" + activationLink;
        RequestBuilder request = MockMvcRequestBuilders
                .get(url);


        authService.verifyActivationLink(activationLink);
        verify(authService, times(1)).verifyActivationLink(activationLink);

        mockMvc.perform(request)
                .andExpect(status().isOk())
                .andExpect(content().string("User registered successfully"));

    }

    @Test
    void login() throws Exception {
        RequestBuilder request = MockMvcRequestBuilders
                .get("/auth/login");

        mockMvc.perform(request)
                .andExpect(status().isOk())
                .andExpect(view().name("/auth/login"));
    }
}