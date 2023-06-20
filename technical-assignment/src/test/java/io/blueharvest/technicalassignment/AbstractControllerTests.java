package io.blueharvest.technicalassignment;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.blueharvest.technicalassignment.configuration.JwtFilter;
import io.blueharvest.technicalassignment.domain.user.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;


@AutoConfigureMockMvc
@ExtendWith(SpringExtension.class)
public abstract class AbstractControllerTests extends AbstractTests {

    protected MockMvc mvc;
    @Autowired
    protected ObjectMapper mapper;
    @Autowired
    private WebApplicationContext context;

    @MockBean
    private JwtFilter jwtFilter;
    @MockBean
    private UserService userService;

    @BeforeEach
    public void setup() {
        this.mvc = MockMvcBuilders
                .webAppContextSetup(this.context)
                .apply(springSecurity())
                .build();
    }

}
