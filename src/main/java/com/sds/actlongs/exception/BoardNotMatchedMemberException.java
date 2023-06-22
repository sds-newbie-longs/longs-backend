package com.sds.actlongs.exception;

import static com.sds.actlongs.model.ErrorCode.*;

public class BoardNotMatchedMemberException extends BusinessException {

	public BoardNotMatchedMemberException() {
		super(BOARD_NOT_MATCHED_MEMBER_FAILURE);
	}

}
