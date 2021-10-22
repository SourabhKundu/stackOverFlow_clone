package com.mountblue.StackOverFlow.repository;

import com.mountblue.StackOverFlow.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
  @Query("SELECT u FROM User  u WHERE u.email = :email")
  public User getUserByEmail(@Param("email")String email);

  @Query("SELECT u FROM User u WHERE u.email = ?1")
  User findByEmail(String username);

  @Query("SELECT q FROM User q WHERE q.name LIKE %?1% ")
  List<User> findAll(String keyword);
}
