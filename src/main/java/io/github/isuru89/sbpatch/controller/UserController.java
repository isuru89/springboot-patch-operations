package io.github.isuru89.sbpatch.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.fge.jsonpatch.JsonPatch;
import com.github.fge.jsonpatch.JsonPatchException;
import com.github.fge.jsonpatch.JsonPatchOperation;
import io.github.isuru89.sbpatch.dto.UserDTO;
import io.github.isuru89.sbpatch.service.UserService;
import io.github.isuru89.sbpatch.utils.CommonUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(
        produces = MediaType.APPLICATION_JSON_VALUE
)
public class UserController extends AbstractController {

    private final UserService userService;


    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping(path = "/users", consumes = MediaType.APPLICATION_JSON_VALUE)
    public UserDTO addUser(@RequestBody UserDTO user) {
        return this.userService.addUser(user);
    }

    @GetMapping(path = "/users/{userId}")
    public UserDTO getUser(@PathVariable("userId") Long userId) {
        return this.userService.getUserById(userId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    @PutMapping(path = "/users/{userId}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public UserDTO patchUser(@PathVariable("userId") Long userId, @RequestBody UserDTO user) {
        return this.userService.editUser(user);
    }

    @PatchMapping(path = "/users/{userId}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public UserDTO patchUser(@PathVariable("userId") Long userId, @RequestBody List<JsonPatchOperation> patchOperations) {
        // unfortunately, JsonPatchOperation does not expose 'path' for external access.
        List<String> pathList = CommonUtils.extractPathsFromPatchOperations(patchOperations, objectMapper);

        // you could load these unmodifiable field set for each resource.
        // Currently, this is hard coded.
        List<String> notAllowedFields = Arrays.asList("/id", "/createdAt", "/updatedAt");
        if (CommonUtils.isAnyMatch(pathList, notAllowedFields)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Some of provided field are not allowed to patch!");
        }

        UserDTO userDTO = this.userService.getUserById(userId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        return this.userService.editUser(patchEntity(userDTO, UserDTO.class, patchOperations));
    }
}
