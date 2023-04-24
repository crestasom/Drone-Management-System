package com.crestasom.dms.model.response;

import com.crestasom.dms.model.ResponseBean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

@AllArgsConstructor
@NoArgsConstructor
@Data
@ToString
@EqualsAndHashCode(callSuper = false)
public class AuthResponse extends ResponseBean {
	private int id;
	private String jwt;
	private String username;

}
