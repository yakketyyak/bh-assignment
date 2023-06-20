package io.blueharvest.technicalassignment.utils;


import io.blueharvest.technicalassignment.AbstractUnitTests;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;

class ControllerUtilsTests extends AbstractUnitTests {

    @Test
    void newInstanceFailed() {
        assertThrows(IllegalAccessException.class,
                () -> ControllerUtils.class.getDeclaredConstructor().newInstance());
    }
}
