package com.example.user.repositories;

import com.example.user.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface UserRepository  extends JpaRepository<User, Integer> {
 //   List<User> findByHebergementId(Long hebergementId);

    @Query("SELECT p.userId FROM User p")
    List<Integer> findAllUserIds();

    List<User> findByHebergementId(Long hebergementId);
}
