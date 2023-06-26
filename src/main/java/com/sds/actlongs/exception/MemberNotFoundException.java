package com.sds.actlongs.exception;

import static com.sds.actlongs.model.ErrorCode.*;

public class MemberNotFoundException extends BusinessException {

	public MemberNotFoundException() {
		super(MEMBER_NOT_FOUND);
	}

}
