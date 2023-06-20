package io.blueharvest.technicalassignment.utils;

import lombok.experimental.UtilityClass;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;

import java.net.URI;
import java.util.UUID;

@UtilityClass
public final class ControllerUtils {

    public static URI buildMvcPathComponent(final UUID pathValue, final Class<?> clazz) {
        return MvcUriComponentsBuilder
                .fromController(clazz)
                .path("/{id}")
                .buildAndExpand(pathValue)
                .toUri();
    }
}
