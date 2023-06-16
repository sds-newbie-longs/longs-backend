package com.sds.actlongs.controller.member.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MemberSearchRequest {

	@NotBlank
	@Size(max = 20)
	@ApiModelProperty(value = "회원검색 키워드(최대 20자, 공백 불가)", example = "di", required = true)
	private String keyword;

	@NotNull
	@ApiModelProperty(value = "그룹 ID", example = "1", required = true)
	private Long channelId;

}
