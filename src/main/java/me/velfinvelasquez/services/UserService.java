package me.velfinvelasquez.services;

import java.util.List;

import me.velfinvelasquez.models.UserModel;

public interface UserService {

	List<UserModel> getAll();

	UserModel save(UserModel inmueble);

}
