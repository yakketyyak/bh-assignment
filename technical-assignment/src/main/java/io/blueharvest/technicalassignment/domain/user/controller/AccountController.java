package io.blueharvest.technicalassignment.domain.user.controller;

import io.blueharvest.technicalassignment.domain.user.dto.AccountDto;
import io.blueharvest.technicalassignment.domain.user.forms.AccountCreationForm;
import io.blueharvest.technicalassignment.domain.user.service.AccountService;
import io.blueharvest.technicalassignment.utils.ControllerUtils;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;

@RestController
@CrossOrigin("*")
@RequiredArgsConstructor
@RequestMapping("/accounts")
public class AccountController {
    private final AccountService accountService;

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<AccountDto> create(@RequestBody @Valid AccountCreationForm request) {
        var response = this.accountService.create(request);
        URI uri = ControllerUtils.buildMvcPathComponent(response.getIdentifier(), AccountController.class);
        return ResponseEntity.created(uri).body(response);
    }
}
