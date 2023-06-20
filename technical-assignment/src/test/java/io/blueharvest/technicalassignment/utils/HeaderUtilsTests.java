package io.blueharvest.technicalassignment.utils;


import io.blueharvest.technicalassignment.AbstractUnitTests;
import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockHttpServletRequest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

class HeaderUtilsTests extends AbstractUnitTests {

    @Test
    void newInstanceFailed() {
        assertThrows(IllegalAccessException.class,
                () -> HeaderUtils.class.getDeclaredConstructor().newInstance());
    }

    @Test
    void extractTokenIsValid() {
        MockHttpServletRequest request = new MockHttpServletRequest();
        request.addHeader("Authorization", "Bearer 123");
        assertThat(HeaderUtils.extractToken(request)).isEqualTo("123");
    }

    @Test
    void extractTokenIsNotValid() {
        MockHttpServletRequest request = new MockHttpServletRequest();
        request.addHeader("Authorization", "Bearerr 123");
        assertThat(HeaderUtils.extractToken(request)).isEqualTo("");
    }
}
