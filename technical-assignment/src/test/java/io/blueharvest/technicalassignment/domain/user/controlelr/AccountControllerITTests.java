package io.blueharvest.technicalassignment.domain.user.controlelr;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.blueharvest.technicalassignment.AbstractTests;
import io.blueharvest.technicalassignment.domain.auth.dto.Token;
import io.blueharvest.technicalassignment.domain.user.dto.AccountDto;
import io.blueharvest.technicalassignment.domain.user.entity.TransactionEntity;
import io.blueharvest.technicalassignment.domain.user.repository.TransactionRepository;
import io.blueharvest.technicalassignment.domain.user.repository.UserRepository;
import io.blueharvest.technicalassignment.utils.JwtUtils;
import org.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class AccountControllerITTests extends AbstractTests {

    @Autowired
    private ObjectMapper mapper;
    @Autowired
    private TestRestTemplate restTemplate;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private TransactionRepository transactionRepository;
    @Autowired
    private JwtUtils jwtUtils;

    private HttpHeaders headers;

    @BeforeEach
    void beforeEach() {
        headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setAccept(List.of(MediaType.APPLICATION_JSON));
    }

    @Test
    void createAccountWithoutTransactionSuccess() throws Exception {
        String customerID = this.userRepository.findAll().get(0).getIdentifier().toString();
        JSONObject creationForm = new JSONObject();
        creationForm.put("customerID", customerID);

        Token token = jwtUtils.generateToken("admin");
        headers.add(HttpHeaders.AUTHORIZATION, "Bearer " + token.getAccessToken());

        HttpEntity<String> request =
                new HttpEntity<>(creationForm.toString(), headers);


        ResponseEntity<String> response = restTemplate
                .postForEntity("/accounts", request, String.class);

        AccountDto account = mapper.readValue(response.getBody(), AccountDto.class);
        assertThat(account.getCreatedBy()).isEqualTo("admin");
        assertThat(account.getIdentifier()).isEqualTo(UUID.fromString(customerID));

        List<TransactionEntity> transactions = this.transactionRepository.findAllByAccountId(UUID.fromString(customerID));
        assertThat(transactions).isEmpty();
    }

    @Test
    void createAccountWithTransactionSuccess() throws Exception {
        String customerID = this.userRepository.findAll().get(2).getIdentifier().toString();
        JSONObject creationForm = new JSONObject();
        creationForm.put("customerID", customerID);
        creationForm.put("initialCredit", 100);

        Token token = jwtUtils.generateToken("admin");
        headers.add(HttpHeaders.AUTHORIZATION, "Bearer " + token.getAccessToken());

        HttpEntity<String> request =
                new HttpEntity<>(creationForm.toString(), headers);


        ResponseEntity<String> response = restTemplate
                .postForEntity("/accounts", request, String.class);

        AccountDto account = mapper.readValue(response.getBody(), AccountDto.class);
        assertThat(account.getCreatedBy()).isEqualTo("admin");
        assertThat(account.getIdentifier()).isEqualTo(UUID.fromString(customerID));

        List<TransactionEntity> transactions = this.transactionRepository.findAllByAccountId(UUID.fromString(customerID));
        assertThat(transactions).isNotEmpty();

        TransactionEntity transaction = transactions.get(0);
        assertThat(transaction.getAccountId()).isEqualTo(UUID.fromString(customerID));
    }

    @Test
    void createAccountNotFoundException() throws Exception {
        String customerID = "2c0db8e0-caa7-4025-9cea-5e6f3f2c9d0c";
        JSONObject creationForm = new JSONObject();
        creationForm.put("customerID", customerID);

        Token token = jwtUtils.generateToken("admin");
        headers.add(HttpHeaders.AUTHORIZATION, "Bearer " + token.getAccessToken());

        HttpEntity<String> request =
                new HttpEntity<>(creationForm.toString(), headers);


        ResponseEntity<String> response = restTemplate
                .postForEntity("/accounts", request, String.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatusCode.valueOf(HttpStatus.NOT_FOUND.value()));
    }

    @Test
    void createAccountDuplicateResourceException() throws Exception {
        String customerID = this.userRepository.findAll().get(1).getIdentifier().toString();
        JSONObject creationForm = new JSONObject();
        creationForm.put("customerID", customerID);

        Token token = jwtUtils.generateToken("admin");
        headers.add(HttpHeaders.AUTHORIZATION, "Bearer " + token.getAccessToken());

        HttpEntity<String> request =
                new HttpEntity<>(creationForm.toString(), headers);


        restTemplate
                .postForEntity("/accounts", request, String.class);

        ResponseEntity<String> response = restTemplate
                .postForEntity("/accounts", request, String.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatusCode.valueOf(HttpStatus.CONFLICT.value()));
    }

    @Test
    void createAccountIsUnauthorized() throws Exception {
        String customerID = "2c0db8e0-caa7-4025-9cea-5e6f3f2c9d0c";
        JSONObject creationForm = new JSONObject();
        creationForm.put("customerID", customerID);

        HttpEntity<String> request =
                new HttpEntity<>(creationForm.toString(), headers);

        ResponseEntity<String> response = restTemplate
                .postForEntity("/accounts", request, String.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatusCode.valueOf(HttpStatus.UNAUTHORIZED.value()));
    }


    @Test
    void createAccountBadRequest() throws Exception {
        JSONObject creationForm = new JSONObject();
        creationForm.put("customerID", null);

        Token token = jwtUtils.generateToken("admin");
        headers.add(HttpHeaders.AUTHORIZATION, "Bearer " + token.getAccessToken());
        HttpEntity<String> request =
                new HttpEntity<>(creationForm.toString(), headers);

        ResponseEntity<String> response = restTemplate
                .postForEntity("/accounts", request, String.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatusCode.valueOf(HttpStatus.BAD_REQUEST.value()));
    }
}
