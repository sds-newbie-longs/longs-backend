package com.sds.actlongs.exception;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;

import com.sds.actlongs.model.ErrorCode;
import com.sds.actlongs.model.ErrorResponse.FieldError;

@Getter
public abstract class BusinessException extends RuntimeException {

	private final ErrorCode errorCode;
	private final List<FieldError> errors;

	public BusinessException(ErrorCode errorCode) {
		super(errorCode.getMessage());
		this.errorCode = errorCode;
		this.errors = new ArrayList<>();
	}

	public BusinessException(ErrorCode errorCode, List<FieldError> errors) {
		super(errorCode.getMessage());
		this.errors = errors;
		this.errorCode = errorCode;
	}

}
