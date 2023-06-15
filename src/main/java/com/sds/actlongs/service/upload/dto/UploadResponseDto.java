package com.sds.actlongs.service.upload.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class UploadResponseDto {
	private String videoUUid;

	public UploadResponseDto(String videoUUid) {
		this.videoUUid = videoUUid;
	}
}
