package com.sds.actlongs.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

@ApiModel(description = "응답 결과 공통 모델(정상)")
@Data
@NoArgsConstructor
public abstract class ResultResponse {

	@ApiModelProperty(position = 1, value = "HTTP 상태 코드", example = "200")
	private int status;
	@ApiModelProperty(position = 2, value = "Business 상태 코드", example = "M001")
	private String code;
	@ApiModelProperty(position = 3, value = "결과 메세지", example = "로그인에 성공하였습니다.")
	private String message;

	public ResultResponse(ResultCode resultCode) {
		this.status = resultCode.getStatus();
		this.code = resultCode.getCode();
		this.message = resultCode.getMessage();
	}

}
