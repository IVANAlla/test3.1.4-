package web6.controller;

import web6.Exception.ExceptionInfo;
import web6.Exception.UserUsernameExistException;
import web6.model.User;
import web6.service.RoleService;
import web6.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
import java.security.Principal;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
public class UsersController {

    private final UserService userService;

    @Autowired
    public UsersController(RoleService roleService, UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/users")
    public ResponseEntity<List<User>> getUsers() {
        return new ResponseEntity<>(userService.getAllUsers(),HttpStatus.OK);
    }

    @PostMapping("/users")
    public ResponseEntity<ExceptionInfo> createUser(@Valid @RequestBody User user, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            String error = getErrorsFromBindingResult(bindingResult);
            return new ResponseEntity<>(new ExceptionInfo(error), HttpStatus.BAD_REQUEST);
        }
        try {
            userService.saveUser(user);
            return new ResponseEntity<>(HttpStatus.OK);
        }catch (UserUsernameExistException u) {
            throw new UserUsernameExistException("User with username exist");
        }
    }

    @DeleteMapping("/users/{id}")
    public ResponseEntity<ExceptionInfo> pageDelete(@PathVariable("id") long id) {
        userService.deleteUser(id);
        return new ResponseEntity<>(new ExceptionInfo("User deleted"), HttpStatus.OK);
    }

    @GetMapping("users/{id}")
    public ResponseEntity<User> getUser (@PathVariable("id") long id) {
        User user = userService.getById(id);
        return new ResponseEntity<>(user,HttpStatus.OK);
    }

    @GetMapping("/user")
    public ResponseEntity<User> getUserByUsername (Principal principal) {
        User user = userService.findByUsername(principal.getName());
        return new ResponseEntity<>(user,HttpStatus.OK);
    }

    @PutMapping("/users/{id}")
    public ResponseEntity<ExceptionInfo> pageEdit(@PathVariable("id") long id,
                                                  @Valid @RequestBody User user,
                                                  BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            String error = getErrorsFromBindingResult(bindingResult);
            return new ResponseEntity<>(new ExceptionInfo(error), HttpStatus.BAD_REQUEST);
        }
        try {
            String oldPassword = userService.getById(id).getPassword();
            if (oldPassword.equals(user.getPassword())) {
                System.out.println("TRUE");
                user.setPassword(oldPassword);
                userService.updateUser(user);
            } else {
                System.out.println("FALSE");
                userService.saveUser(user);
            }
            return new ResponseEntity<>(HttpStatus.OK);
        }catch (UserUsernameExistException u) {
            throw new UserUsernameExistException("User with username exist");
        }
    }

    private String getErrorsFromBindingResult(BindingResult bindingResult) {
        return bindingResult.getFieldErrors()
                .stream()
                .map(DefaultMessageSourceResolvable::getDefaultMessage)
                .collect(Collectors.joining("; "));
    }
}