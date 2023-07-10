package me.velfinvelasquez.controllers;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
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
	
	@GetMapping("/{id}")
	public ResponseEntity<?> getById(@PathVariable Long id) {
		return ResponseEntity.ok(userService.getById(id));
	}

	@PostMapping("/register")
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
						.body(new ErrorMessage("Usuario bloqueado, comuniquese con Administrador"));
			}
			if (user.getSession_active()) {
				return ResponseEntity.status(HttpStatus.BAD_REQUEST)
						.body(new ErrorMessage("Ya iniciaste sesion en otro ordenador"));
			}
			user.setSession_active(true);
			userRepository.save(user);
			return ResponseEntity.ok(user);
			
	
		} else {
			// Usuario no encontrado o la contraseña no coincide
			user.setIntentos_login(user.getIntentos_login()+1);
			userRepository.save(user);
			if (user.getIntentos_login()>=3) {
				user.setStatus(false);
				userRepository.save(user);
				return ResponseEntity.status(HttpStatus.BAD_REQUEST)
						.body(new ErrorMessage("Usuario bloqueado por seguridad, contacte con administrador!"));	
			}
			return ResponseEntity.status(HttpStatus.BAD_REQUEST)
					.body(new ErrorMessage("Usuario o contraseña invalida: Intento "+user.getIntentos_login()+"/3"));	
		}
		
	}

	@PostMapping("/logout")
	public ResponseEntity<?> logout(@RequestBody Map<String, String> data){
		String email = data.get("email");
		userService.logout(email);
		return ResponseEntity.ok(new ErrorMessage("Cerraste Sesion con exito!"));
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<?> update(@PathVariable Long id, @RequestBody UserModel user){
		UserModel userUpdated = userService.update(id, user);
		return ResponseEntity.ok(userUpdated);
	}

	
}
