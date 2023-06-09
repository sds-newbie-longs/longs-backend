package com.sds.actlongs.model;

import static com.sds.actlongs.model.ErrorCode.*;
import static com.sds.actlongs.util.Constants.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import javax.validation.ConstraintViolation;

import org.springframework.validation.BindingResult;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@ApiModel(description = "응답 결과 공통 모델(예외)")
@Getter
@NoArgsConstructor
public class ErrorResponse {

	@ApiModelProperty(value = "HTTP 상태 코드", example = "400")
	private int status;
	@ApiModelProperty(value = "Business 상태 코드", example = "E-G002")
	private String code;
	@ApiModelProperty(value = "에러 메세지", example = "입력 값이 유효하지 않습니다.")
	private String message;
	@ApiModelProperty(value = "에러 목록")
	private List<FieldError> errors;

	private ErrorResponse(final ErrorCode code, final List<FieldError> errors) {
		this.message = code.getMessage();
		this.status = code.getStatus();
		this.errors = errors;
		this.code = code.getCode();
	}

	private ErrorResponse(final ErrorCode code) {
		this.message = code.getMessage();
		this.status = code.getStatus();
		this.code = code.getCode();
		this.errors = new ArrayList<>();
	}

	private ErrorResponse(final int status, final String code, final String message, final List<FieldError> errors) {
		this.status = status;
		this.code = code;
		this.message = message;
		this.errors = errors;
	}

	public static ErrorResponse of(final int status, final String code, final String message,
		final List<FieldError> errors) {
		return new ErrorResponse(status, code, message, errors);
	}

	public static ErrorResponse of(final ErrorCode code, final BindingResult bindingResult) {
		return new ErrorResponse(code, FieldError.of(bindingResult));
	}

	public static ErrorResponse of(final ErrorCode code, final Set<ConstraintViolation<?>> constraintViolations) {
		return new ErrorResponse(code, FieldError.of(constraintViolations));
	}

	public static ErrorResponse of(final ErrorCode code, final String missingParameterName) {
		return new ErrorResponse(code,
			FieldError.of(missingParameterName, EMPTY, REQUEST_PARAMETER_MISSING.getMessage()));
	}

	public static ErrorResponse of(final ErrorCode code) {
		return new ErrorResponse(code);
	}

	public static ErrorResponse of(final ErrorCode code, final List<FieldError> errors) {
		return new ErrorResponse(code, errors);
	}

	public static ErrorResponse of(MethodArgumentTypeMismatchException exception) {
		final String value = exception.getValue() == null ? EMPTY : exception.getValue().toString();
		final List<FieldError> errors = FieldError.of(exception.getName(), value, exception.getErrorCode());
		return new ErrorResponse(INPUT_TYPE_INVALID, errors);
	}

	@Getter
	@NoArgsConstructor(access = AccessLevel.PROTECTED)
	public static class FieldError {

		@ApiModelProperty(value = "에러 필드", example = "name")
		private String field;
		@ApiModelProperty(value = "에러 값", example = "이름을길게만들면유효한이름이아니라서사용할수없어요")
		private String value;
		@ApiModelProperty(value = "에러 이유", example = "\"^[A-Za-z\\d]{5,20}$\"와 일치해야 합니다")
		private String reason;

		public FieldError(final String field, final String value, final String reason) {
			this.field = field;
			this.value = value;
			this.reason = reason;
		}

		public static List<FieldError> of(final String field, final String value, final String reason) {
			final List<FieldError> fieldErrors = new ArrayList<>();
			fieldErrors.add(new FieldError(field, value, reason));
			return fieldErrors;
		}

		public static List<FieldError> of(final String field, final String value, final ErrorCode errorCode) {
			final List<FieldError> fieldErrors = new ArrayList<>();
			fieldErrors.add(new FieldError(field, value, errorCode.getMessage()));
			return fieldErrors;
		}

		private static List<FieldError> of(final BindingResult bindingResult) {
			final List<org.springframework.validation.FieldError> fieldErrors = bindingResult.getFieldErrors();
			return fieldErrors.stream()
				.map(error -> new FieldError(
					error.getField(), error.getRejectedValue() == null ? EMPTY : error.getRejectedValue().toString(),
					error.getDefaultMessage()))
				.collect(Collectors.toList());
		}

		private static List<FieldError> of(final Set<ConstraintViolation<?>> constraintViolations) {
			final List<ConstraintViolation<?>> lists = new ArrayList<>(constraintViolations);
			return lists.stream()
				.map(error -> {
					final String invalidValue =
						error.getInvalidValue() == null ? EMPTY : error.getInvalidValue().toString();
					final int index = error.getPropertyPath().toString().indexOf(DOT);
					final String propertyPath = error.getPropertyPath().toString().substring(index + 1);
					return new FieldError(propertyPath, invalidValue, error.getMessage());
				})
				.collect(Collectors.toList());
		}

	}

}
