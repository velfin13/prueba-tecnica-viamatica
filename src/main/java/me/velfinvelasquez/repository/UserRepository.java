package me.velfinvelasquez.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import me.velfinvelasquez.models.UserModel;

public interface UserRepository extends JpaRepository<UserModel, Long> {
	@Query(value = "SELECT * FROM users WHERE email = :email", nativeQuery = true)
	UserModel findByEmail(String email);
}
