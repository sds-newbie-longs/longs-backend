package com.sds.actlongs.utils.uuid;

import java.util.UUID;

public class UUIDGenerateImpl implements UUIDGenerate{
	@Override
	public String getVodUUID() {
		return UUID.randomUUID()
			.toString()
			.replaceAll("-", "");
	}

}
