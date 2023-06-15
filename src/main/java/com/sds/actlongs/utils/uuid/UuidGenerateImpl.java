package com.sds.actlongs.utils.uuid;

import java.util.UUID;

import org.springframework.stereotype.Component;

@Component
public class UuidGenerateImpl implements UuidGenerate {

	@Override
	public String getVodUuid() {
		return UUID.randomUUID().toString().replaceAll("-", "");
	}

}
