package com.sds.actlongs.model;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public abstract class ResultResponse {

	private int status;
	private String code;
	private String message;

	public ResultResponse(ResultCode resultCode) {
		this.status = resultCode.getStatus();
		this.code = resultCode.getCode();
		this.message = resultCode.getMessage();
	}

}
