package com.crestasom.dms.model.request;

import com.crestasom.dms.dto.BaseDTO;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@ToString
@NoArgsConstructor
public class AuthRequest implements BaseDTO {
	@NotNull(message = "Username cannot be blank")
	private String username;
	@NotNull(message = "Password cannot be blank")
	private String password;
	private String token;
}
