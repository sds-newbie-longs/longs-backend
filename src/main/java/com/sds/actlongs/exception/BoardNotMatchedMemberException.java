package com.sds.actlongs.exception;

import com.sds.actlongs.model.ErrorCode;

public class BoardNotMatchedMemberException extends BusinessException {

	public BoardNotMatchedMemberException(ErrorCode errorCode) {
		super(errorCode);
	}

}
