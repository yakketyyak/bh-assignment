package io.blueharvest.technicalassignment.domain.user.controller;

import io.blueharvest.technicalassignment.domain.user.dto.UserDto;
import io.blueharvest.technicalassignment.domain.user.dto.UserLiteDto;
import io.blueharvest.technicalassignment.domain.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springdoc.api.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@CrossOrigin("*")
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserController {
    private final UserService userService;

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<UserDto> findById(@PathVariable String id) {
        var response = this.userService.findById(UUID.fromString(id));
        return ResponseEntity.ok(response);
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Page<UserLiteDto>> getAll(@PageableDefault @ParameterObject Pageable page) {
        return ResponseEntity.ok(this.userService.findAll(page));
    }
}
