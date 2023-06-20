package io.blueharvest.technicalassignment.domain.user.controlelr;

import io.blueharvest.technicalassignment.AbstractControllerTests;
import io.blueharvest.technicalassignment.common.exception.DuplicateResourceException;
import io.blueharvest.technicalassignment.common.exception.NotFoundException;
import io.blueharvest.technicalassignment.domain.user.controller.AccountController;
import io.blueharvest.technicalassignment.domain.user.dto.AccountDto;
import io.blueharvest.technicalassignment.domain.user.forms.AccountCreationForm;
import io.blueharvest.technicalassignment.domain.user.service.AccountService;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;

import java.time.LocalDateTime;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = AccountController.class)
class AccountControllerTests extends AbstractControllerTests {

    @MockBean
    private AccountService accountService;

    @Test
    @WithMockUser(username = "test")
    void createAccountSuccess() throws Exception {
        String customerID = "2c0db8e0-caa7-4025-9cea-5e6f3f2c9d0c";
        AccountCreationForm creationForm = AccountCreationForm.builder()
                .customerID(customerID)
                .build();
        AccountDto dto = AccountDto.builder()
                .createdAt(LocalDateTime.now().toString())
                .identifier(UUID.fromString(customerID)).build();

        when(this.accountService.create(any(AccountCreationForm.class))).thenReturn(dto);

        this.mvc.perform(post("/accounts").with(csrf())
                        .content(this.mapper.writeValueAsString(creationForm))
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isCreated());
    }

    @Test
    @WithMockUser(username = "test")
    void createAccountNotFoundException() throws Exception {
        String customerID = "2c0db8e0-caa7-4025-9cea-5e6f3f2c9d0c";
        AccountCreationForm creationForm = AccountCreationForm.builder()
                .customerID(customerID)
                .build();

        when(this.accountService.create(any(AccountCreationForm.class))).thenThrow(NotFoundException.class);

        this.mvc.perform(post("/accounts").with(csrf())
                        .content(this.mapper.writeValueAsString(creationForm))
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @WithMockUser(username = "test")
    void createAccountDuplicateResourceException() throws Exception {
        String customerID = "2c0db8e0-caa7-4025-9cea-5e6f3f2c9d0c";
        AccountCreationForm creationForm = AccountCreationForm.builder()
                .customerID(customerID)
                .build();

        when(this.accountService.create(any(AccountCreationForm.class))).thenThrow(DuplicateResourceException.class);

        this.mvc.perform(post("/accounts").with(csrf())
                        .content(this.mapper.writeValueAsString(creationForm))
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andDo(print())
                .andExpect(status().isConflict());
    }

    @Test
    void createAccountIsUnauthorized() throws Exception {
        String customerID = "2c0db8e0-caa7-4025-9cea-5e6f3f2c9d0c";
        AccountCreationForm creationForm = AccountCreationForm.builder()
                .customerID(customerID)
                .build();

        this.mvc.perform(post("/accounts").with(csrf())
                        .content(this.mapper.writeValueAsString(creationForm))
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isUnauthorized());

        verify(this.accountService, never()).create(any(AccountCreationForm.class));
    }


    @Test
    @WithMockUser(username = "test")
    void createAccountBadRequest() throws Exception {
        this.mvc.perform(post("/accounts").with(csrf())
                        .content(this.mapper.writeValueAsString(AccountCreationForm.builder().build()))
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isBadRequest());

        verify(this.accountService, never()).create(any(AccountCreationForm.class));
    }
}
