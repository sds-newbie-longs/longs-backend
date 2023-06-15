package com.sds.actlongs.controller.member.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoginRequest {

	@NotBlank
	@Size(max = 20)
	@ApiModelProperty(value = "아이디(최대 20자, 공백 불가)", example = "harry", required = true)
	private String username;

}
