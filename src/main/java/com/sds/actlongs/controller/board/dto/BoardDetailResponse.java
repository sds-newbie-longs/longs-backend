package com.sds.actlongs.controller.board.dto;

import java.sql.Time;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;

import com.sds.actlongs.domain.video.entity.Video;
import com.sds.actlongs.model.ResultCode;
import com.sds.actlongs.model.ResultResponse;
import com.sds.actlongs.vo.ImageExtension;
import com.sds.actlongs.vo.VideoExtension;

@Getter
public class BoardDetailResponse extends ResultResponse {

	@ApiModelProperty(value = "제목", example = "재진스 플레이리스트")
	private final String title;
	@ApiModelProperty(value = "설명", example = "재진스의 뉴진스 플레이리스트 입니다.")
	private final String description;
	@ApiModelProperty(value = "동영상 썸네일 이미지 파일 ID", example = "harry_thumbnail")
	private final String thumbnailImageUuid;
	@ApiModelProperty(value = "동영상 썸네일 이미지 파일 유형", example = "PNG")
	private final ImageExtension thumbnailImageType;
	@ApiModelProperty(value = "동영상 파일 ID", example = "jazzeans")
	private final String videoUuid;
	@ApiModelProperty(value = "동영상 썸네일 이미지 파일 유형", example = "MP4")
	private final VideoExtension videoType;
	@ApiModelProperty(value = "재생시간", example = "2017-02-16 09:21:50.634")
	private final Time playingTime;

	private BoardDetailResponse(ResultCode resultCode, Video video) {
		super(resultCode);
		this.title = video.getBoard().getTitle();
		this.description = video.getBoard().getDescription();
		this.thumbnailImageUuid = video.getThumbnailImageUuid();
		this.thumbnailImageType = video.getThumbnailImageType();
		this.videoUuid = video.getVideoUuid();
		this.videoType = video.getVideoType();
		this.playingTime = video.getPlayingTime();
	}

	public static BoardDetailResponse of(Video video) {
		return new BoardDetailResponse(ResultCode.GET_BOARDDETAIL_SUCCESS, video);
	}

}
