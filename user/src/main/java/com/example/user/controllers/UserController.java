package com.example.user.controllers;

import com.example.user.entities.User;
import com.example.user.repositories.UserRepository;
import com.example.user.services.UserService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@EnableDiscoveryClient
@RestController
@RequestMapping("/users")
@AllArgsConstructor

//@CrossOrigin(origins = "*")
public class UserController {

    private final UserRepository userRepository;
    private final UserService userService;


    @Operation(summary = "Lister tous les users")
    @GetMapping("/getAllUsers")
    public ResponseEntity<List<User>> getAllProjets() {  //
        List<User> users = userService.getAllUsers();
        return ResponseEntity.ok(users);
    }

    @Operation(summary = "Ajouter un utilisateur")
    @PostMapping("/add")
    public ResponseEntity<User> addUser(@RequestBody User user) {
        User createdUser = userService.addUser(user);
        return ResponseEntity.ok(createdUser);
    }
/*
    @PutMapping("/{userId}/assign/{hebergementId}")
    public void assignToHebergement(@PathVariable int userId,
                                    @PathVariable Long hebergementId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("Utilisateur non trouvé"));

        user.setHebergementId(hebergementId);
        userRepository.save(user);
    }
*/

@Operation(summary = "Assignation de l'utilisateur à un hébergement")
     @PostMapping("/{userId}/assign/{hebergementId}")
    public String assignUserToHebergement(@PathVariable int userId, @PathVariable Long hebergementId) {
        return userService.assignUserToHebergement(userId, hebergementId);
    }
    @GetMapping("/{userId}")
    public User getUserById(@PathVariable int userId) {
        return userService.getUserById(userId);
    }

    @GetMapping("/byHebergement/{hebergementId}")
    public List<User> getUsersByHebergement(@PathVariable Long hebergementId) {
        return userService.getUsersByHebergement(hebergementId);
    }
    @PutMapping("/{userId}/unassign")
    public void unassignFromHebergement(@PathVariable int userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("Utilisateur non trouvé"));

        user.setHebergementId(null);
        userRepository.save(user);
    }

  /*  @GetMapping("/byHebergement/{hebergementId}")
    public List<User> getUsersByHebergement(@PathVariable Long hebergementId) {
        return userRepository.findByHebergementId(hebergementId);
    }*/
}
