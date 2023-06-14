package com.sds.actlongs.controller.member.dto;

import javax.validation.constraints.NotBlank;

import lombok.Getter;

@Getter
public class LoginRequest {

	@NotBlank
	private String username;

}
