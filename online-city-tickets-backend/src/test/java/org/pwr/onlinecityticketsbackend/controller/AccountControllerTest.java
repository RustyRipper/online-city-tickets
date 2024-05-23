package org.pwr.onlinecityticketsbackend.controller;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.pwr.onlinecityticketsbackend.dto.AccountDto;
import org.pwr.onlinecityticketsbackend.mapper.AccountMapper;
import org.pwr.onlinecityticketsbackend.model.Account;
import org.pwr.onlinecityticketsbackend.model.Role;
import org.pwr.onlinecityticketsbackend.service.AccountService;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

@ExtendWith(MockitoExtension.class)
public class AccountControllerTest {
    @Mock private AccountMapper accountMapper;
    @Mock private AccountService accountService;

    @InjectMocks private AccountController accountController;

    private MockMvc mockMvc;

    @Test
    public void getAccountTest() throws Exception {
        Account account = new Account();
        account.setEmail("test@test.com");
        account.setFullName("Test Test");
        account.setRole(Role.PASSENGER);

        // Mock the security context
        Authentication authentication = mock(Authentication.class);
        SecurityContext securityContext = mock(SecurityContext.class);
        SecurityContextHolder.setContext(securityContext);

        when(securityContext.getAuthentication()).thenReturn(authentication);
        when(authentication.getPrincipal()).thenReturn(account);

        when(accountService.getAccountByEmail(anyString())).thenReturn(account);
        AccountDto accountDto = new AccountDto();
        accountDto.setEmail("test@test.com");
        when(accountMapper.toDto(account)).thenReturn(accountDto);

        mockMvc = MockMvcBuilders.standaloneSetup(accountController).build();

        mockMvc.perform(get("/api/v1/account").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.email").value("test@test.com"));
    }

    @Test
    public void getAccountTest_InvalidAccount() throws Exception {
        Account account = new Account();
        account.setEmail("admin@test.com");
        account.setRole(Role.ADMIN);

        // Mock the security context
        Authentication authentication = mock(Authentication.class);
        SecurityContext securityContext = mock(SecurityContext.class);
        SecurityContextHolder.setContext(securityContext);

        when(securityContext.getAuthentication()).thenReturn(authentication);
        when(authentication.getPrincipal()).thenReturn(account);

        mockMvc = MockMvcBuilders.standaloneSetup(accountController).build();

        mockMvc.perform(get("/api/v1/account").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }
}
