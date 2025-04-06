package com.example.user.services;

import com.example.user.entities.User;
import org.springframework.stereotype.Repository;

import java.util.List;


public interface IUserService {
    List<Integer> getAllUserIds();
    List<User> getAllUsers();
    public String assignUserToHebergement(int userId, Long hebergementId);
    User addUser(User user);
}
