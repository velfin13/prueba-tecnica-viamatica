package me.velfinvelasquez.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import me.velfinvelasquez.exeptions.ResourceNotFoundException;
import me.velfinvelasquez.models.UserModel;
import me.velfinvelasquez.repository.UserRepository;
import me.velfinvelasquez.utils.ErrorMessage;
import me.velfinvelasquez.utils.ValidatorData;

@Service
public class UserServiceImpl implements UserService {
	private ValidatorData validatorData = new ValidatorData();

	@Autowired
	private UserRepository userRepository;

	@Override
	public List<UserModel> getAll() {
		return userRepository.findAll();
	}
	
	@Override
	public UserModel getById(Long id) {
		UserModel userDb = userRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Empleado no existe!"));
		return userDb;
	}

	@Override
	public UserModel save(UserModel user) {
		String email = validatorData.generateEmail(user.getName(), user.getLastname(), user.getIdentificacion());
		user.setEmail(email);
		return userRepository.save(user);
	}
	
	@Override
	public UserModel logout (String email) {
		UserModel user = userRepository.findByEmail(email);
		user.setSession_active(false);
		return userRepository.save(user);
	}
	
	@Override
	public UserModel update(Long id,UserModel user) {
		UserModel userDb = userRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Empleado no existe!"));

		userDb.setName(user.getName());
		userDb.setLastname(user.getLastname());
		userDb.setBirthdate(user.getBirthdate());

		return userRepository.save(userDb);
	}

}
