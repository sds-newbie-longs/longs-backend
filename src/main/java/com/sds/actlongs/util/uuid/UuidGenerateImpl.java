package com.sds.actlongs.util.uuid;

import static com.sds.actlongs.util.Constants.*;

import java.util.UUID;

import org.springframework.stereotype.Component;

@Component
public class UuidGenerateImpl implements UuidGenerate {

	@Override
	public String getVodUuid() {
		return UUID.randomUUID().toString().replaceAll(HYPHEN, EMPTY);
	}

}
