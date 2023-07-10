package me.velfinvelasquez.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import me.velfinvelasquez.models.UserModel;

public interface UserRepository extends JpaRepository<UserModel, Long>{

}
