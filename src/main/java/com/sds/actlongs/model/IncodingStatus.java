package com.sds.actlongs.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum IncodingStatus {
	NEED_CODEC_H264("", "Codec H264"),
	NEED_CODEC_VP9("libvpx-vp9", "Codec Vp9"),
	NEED_NOT_CODEC("", "Empty");

	private final String value;
	private final String description;
}
