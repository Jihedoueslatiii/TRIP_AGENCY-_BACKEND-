package com.example.user.services;

import org.springframework.stereotype.Service;
import com.example.user.entities.User;
import com.example.user.repositories.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
@Service
@AllArgsConstructor
public class UserService implements IUserService {

UserRepository userRepository;

@Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public String assignUserToHebergement(int userId, Long hebergementId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User non trouvé avec id: " + userId));

        // Assigner simplement l'ID de l'hébergement
        user.setHebergementId(hebergementId);

        userRepository.save(user);
        return "Utilisateur " + userId + " affecté à l'hébergement " + hebergementId;
    }

    @Override
    public List<Integer> getAllUserIds() {
        return userRepository.findAllUserIds();
    }

    public User getUserById(int userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("Utilisateur non trouvé avec l'ID: " + userId));
    }
    public List<User> getUsersByHebergement(Long hebergementId) {
        return userRepository.findByHebergementId(hebergementId);
    }
    public User addUser(User user) {
        return userRepository.save(user);
    }
}
