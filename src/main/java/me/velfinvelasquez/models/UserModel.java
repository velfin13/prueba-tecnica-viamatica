package me.velfinvelasquez.models;

import java.time.LocalDate;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Entity
@Data
@Table(name = "users")
public class UserModel {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@Column(name = "name", nullable = false)
	@NotBlank(message = "La name es obligatoria")
	@NotNull(message = "El campo name es obligatorio")
	private String name;

	@Column(name = "username", nullable = false, unique = true)
	@NotBlank(message = "La username es obligatoria")
	@Pattern(regexp = "^[^!@#$%^&*()_+=\\-\\[\\]{};':\"\\\\|,.<>/?]*$", message = "El nombre no debe contener signos")
	@NotNull(message = "El campo username es obligatorio")
	private String username;

	@NotBlank(message = "La identificación es obligatoria")
	@Pattern(regexp = "\\d{10}", message = "La identificación debe tener 10 dígitos")
//    @ValidIdentificacion 
	private String identificacion;

	@Column(name = "lastname", nullable = false)
	@NotBlank(message = "La name es obligatoria")
	@NotNull(message = "El campo lastname es obligatorio")
	private String lastname;

	@Column(name = "email", nullable = false, unique = true)
	private String email;

	@Column(name = "password", nullable = false)
	@NotNull(message = "El campo password es obligatorio")
	@NotBlank(message = "La contraseña es obligatoria")
	@Size(min = 8, message = "La contraseña debe tener al menos 8 caracteres")
	@Pattern(regexp = "^(?=.*[A-Z])(?=.*\\d)(?=.*[!@#$%^&*()_+=\\-\\[\\]{};':\"\\\\|,.<>/?]).*$", message = "La contraseña no cumple con los requisitos")
	private String password;

	@Column(name = "birthdate")
	@Past(message = "La birthdate debe ser anterior a la fecha actual")
	@NotNull(message = "birthdate es obligatoria")
	private LocalDate birthdate;
	
	@Column(name = "status")
	private Boolean status = true;
	
	@Column(name = "session_active")
	private Boolean session_active = false;
	
	@Column(name = "intentos_sesion")
	private int intentos_login = 0;
	
	@OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id")
    private List<RolModel> roles;
}
