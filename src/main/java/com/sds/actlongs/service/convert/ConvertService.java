package com.sds.actlongs.service.convert;

import com.sds.actlongs.model.IncodingStatus;

public interface ConvertService {

	void convertToHlsWithCodec(String fileName, IncodingStatus status);

	void convertToHlsWithoutCodec(String fileName);

}
