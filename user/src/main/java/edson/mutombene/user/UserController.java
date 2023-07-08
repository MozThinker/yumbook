package edson.mutombene.user;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@Slf4j
@RestController
@RequestMapping(value = "api/v1/users",
        produces = "application/json")
public record UserController(UserService userService) {

    @PostMapping()
    public ResponseEntity<?> registerUser(@RequestBody UserRegistrationRequest userRegistrationRequest) {
        log.info("new user registration {}", userRegistrationRequest);
        Optional<User> optionalUser = userService.getUserByEmail(userRegistrationRequest.email());

        if(optionalUser.isPresent())
            return new ResponseEntity<>("User with the given email already exists", HttpStatus.CONFLICT);
        return new ResponseEntity<>(userService.registerUser(userRegistrationRequest), HttpStatus.CREATED);
    }

    @GetMapping
    public List<User> getUsers() {
        return userService.getUsers();
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getUserById(@PathVariable ("id") Integer userId) {
        Optional<User> user = userService.getUserById(userId);

        if(user.isPresent())
            return new ResponseEntity<>(user, HttpStatus.OK);
        return new ResponseEntity<>("User doesn't exist", HttpStatus.NOT_FOUND);
    }

    @GetMapping("/{email}")
    public ResponseEntity<?> getUserById(@PathVariable ("email") String email) {
        Optional<User> user = userService.getUserByEmail(email);

        if(user.isPresent())
            return new ResponseEntity<>(user, HttpStatus.OK);
        return new ResponseEntity<>("User doesn't exist", HttpStatus.NOT_FOUND);
    }

    @PutMapping("{id}")
    public ResponseEntity<?> updateUser(@PathVariable("id") Integer userId, @RequestBody User updaterUser) {
        boolean userUpdated = userService.updateUser(userId, updaterUser);

        if (userUpdated) {
            return new ResponseEntity<>("User updated successfully", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("User not found", HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("{id}")
    public ResponseEntity<?> deleteUser(@PathVariable("id") Integer userId) {
        boolean userDeleted = userService.deleteUser(userId);

        if (userDeleted) {
            return new ResponseEntity<>("User deleted successfully", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("User not found", HttpStatus.NOT_FOUND);
        }
    }
}
