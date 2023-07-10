package me.velfinvelasquez.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import me.velfinvelasquez.models.UserModel;
import me.velfinvelasquez.repository.UserRepository;
import me.velfinvelasquez.utils.ValidatorData;

@Service
public class UserServiceImpl implements UserService{
	private ValidatorData validatorData = new ValidatorData();
	
	@Autowired
	private UserRepository userRepository ;
	
	@Override
	public List<UserModel>getAll(){
		return userRepository.findAll();
	}
	
	@Override
	public UserModel save(UserModel user) {
		String email = validatorData.generateEmail(user.getName(), user.getLastname(),user.getIdentificacion());
        user.setEmail(email);
		return userRepository.save(user);
	}
	
	@Override
	public UserModel login(String email, String password) {
	    UserModel user = userRepository.findByEmail(email);

	    if (user != null && user.getPassword().equals(password)) {
	        // Usuario encontrado y la contraseña coincide
	        return user;
	    } else {
	        // Usuario no encontrado o la contraseña no coincide
	        return null;
	    }
	}
}
