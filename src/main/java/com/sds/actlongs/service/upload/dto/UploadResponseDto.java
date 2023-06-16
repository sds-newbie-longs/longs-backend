package com.sds.actlongs.service.upload.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class UploadResponseDto {

	@ApiModelProperty(value = "동영상 UUID", example = "3cc20d2df4bf4ef1b2fa6ee84913716f", required = true)
	private String videoUUid;

	public UploadResponseDto(String videoUUid) {
		this.videoUUid = videoUUid;
	}

}
