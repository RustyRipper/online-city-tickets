package org.pwr.onlinecityticketsbackend.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.pwr.onlinecityticketsbackend.auth.*;
import org.pwr.onlinecityticketsbackend.service.AccountService;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

@ExtendWith(MockitoExtension.class)
public class AuthenticationControllerTest {

    @Mock private AccountService accountService;

    @Mock private AuthenticationService authenticationService;

    @InjectMocks private AuthenticationController authenticationController;

    private MockMvc mockMvc;

    @Test
    public void testRegisterPassenger() throws Exception {
        RegisterRequestPassenger request = new RegisterRequestPassenger();
        request.setEmail("test@test.com");
        request.setFullName("Test User");
        request.setPhoneNumber("1234567890");
        request.setPassword("password");

        AuthenticationResponse response = new AuthenticationResponse();
        response.setJwt("token");

        when(authenticationService.registerPassenger(any(RegisterRequestPassenger.class)))
                .thenReturn(response);

        mockMvc = MockMvcBuilders.standaloneSetup(authenticationController).build();

        mockMvc.perform(
                        post("/api/v1/auth/register/passenger")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(
                                        "{\"email\":\"test@test.com\",\"fullName\":\"Test User\",\"phoneNumber\":\"1234567890\",\"password\":\"password\"}"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.jwt").value("token"));
    }

    @Test
    public void testRegisterInspector() throws Exception {
        RegisterRequest request = new RegisterRequest();
        request.setEmail("test@test.com");
        request.setFullName("Test User");
        request.setPassword("password");

        AuthenticationResponse response = new AuthenticationResponse();
        response.setJwt("token");

        when(authenticationService.registerInspector(any(RegisterRequest.class)))
                .thenReturn(response);

        mockMvc = MockMvcBuilders.standaloneSetup(authenticationController).build();

        mockMvc.perform(
                        post("/api/v1/auth/register/inspector")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(
                                        "{\"email\":\"test@test.com\",\"fullName\":\"Test User\",\"password\":\"password\"}"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.jwt").value("token"));
    }

    @Test
    public void testAuthenticate() throws Exception {
        AuthenticationRequest request = new AuthenticationRequest();
        request.setEmail("test@test.com");
        request.setPassword("password");

        AuthenticationResponse response = new AuthenticationResponse();
        response.setJwt("token");

        when(authenticationService.authenticate(any(AuthenticationRequest.class)))
                .thenReturn(response);

        mockMvc = MockMvcBuilders.standaloneSetup(authenticationController).build();

        mockMvc.perform(
                        post("/api/v1/auth/login")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content("{\"email\":\"test@test.com\",\"password\":\"password\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.jwt").value("token"));
    }
}
