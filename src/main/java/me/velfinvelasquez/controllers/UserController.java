package me.velfinvelasquez.controllers;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import me.velfinvelasquez.models.UserModel;
import me.velfinvelasquez.repository.UserRepository;
import me.velfinvelasquez.services.UserService;
import me.velfinvelasquez.utils.ErrorMessage;
import me.velfinvelasquez.utils.ValidatorData;

@RestController
@RequestMapping("api/user")
public class UserController {
	private ValidatorData validatorData = new ValidatorData();
	@Autowired
	private UserRepository userRepository;

	@Autowired
	private UserService userService;

	@GetMapping
	public ResponseEntity<?> getAll() {
		return ResponseEntity.ok(userService.getAll());
	}

	@PostMapping
	public ResponseEntity<?> saveUser(@Valid @RequestBody UserModel user, BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST)
					.body(new ErrorMessage(bindingResult.getFieldError().getDefaultMessage()));
		}
		boolean isValid = validatorData.isValidIdentification(user.getIdentificacion());
		if (!isValid) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST)
					.body(new ErrorMessage("El campo identificacion posee un numero repetido 4 veces"));
		}

		return new ResponseEntity<>(userService.save(user), HttpStatus.CREATED);
	}

	@PostMapping("/login")
	public ResponseEntity<?> login(@RequestBody Map<String, String> loginData) {
		String email = loginData.get("email");
		String password = loginData.get("password");
		UserModel user = userRepository.findByEmail(email);
		if (user != null && user.getPassword().equals(password)) {
			if (!user.getStatus()) {
				return ResponseEntity.status(HttpStatus.BAD_REQUEST)
						.body(new ErrorMessage("Usuario bloqueado, comuniquese con Admin"));
			}
			if (user.getSession_active()) {
				user.setIntentos_sesion(user.getIntentos_sesion()+1);
				userRepository.save(user);
				if (user.getIntentos_sesion() ==3) {
					user.setStatus(false);
					userRepository.save(user);	
				}
				return ResponseEntity.status(HttpStatus.BAD_REQUEST)
						.body(new ErrorMessage("Sesion iniciada en otro ordenador: intento #"+user.getIntentos_sesion()+".  Al intento # 3 su usuario sera bloqueado."));
			}
			user.setSession_active(true);
			userRepository.save(user);
			return ResponseEntity.ok(user);
			
	
		} else {
			// Usuario no encontrado o la contraseña no coincide
			return ResponseEntity.status(HttpStatus.BAD_REQUEST)
					.body(new ErrorMessage("Usuario o contraseña invalida"));	
		}
		
	}

	
}
