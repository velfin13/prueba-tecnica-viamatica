package me.velfinvelasquez.controllers;


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
import me.velfinvelasquez.services.UserService;
import me.velfinvelasquez.utils.ErrorMessage;
import me.velfinvelasquez.utils.ValidatorData;

@RestController
@RequestMapping("api/user")
public class UserController {
	private ValidatorData validatorData=new ValidatorData();
	
	@Autowired
	private UserService userService;
	

	@GetMapping
	public ResponseEntity<?> getAll() {
		return ResponseEntity.ok(userService.getAll());
	}
	
    @PostMapping
    public ResponseEntity<?> saveUser(@Valid @RequestBody UserModel user, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorMessage(bindingResult.getFieldError().getDefaultMessage()));
        }
        boolean isValid = validatorData.isValidIdentification(user.getIdentificacion());
        if(!isValid) {
        	return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorMessage("El campo identificacion posee un numero repetido 4 veces"));
        }

        return new ResponseEntity<>(
				userService.save(user), 
				HttpStatus.CREATED);
    }

}

