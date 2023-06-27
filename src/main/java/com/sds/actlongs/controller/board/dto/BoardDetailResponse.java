package com.sds.actlongs.controller.board.dto;

import static com.sds.actlongs.model.ResultCode.*;
import static com.sds.actlongs.util.Constants.*;

import java.time.LocalTime;
import java.util.Optional;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;

import com.sds.actlongs.domain.board.entity.Board;
import com.sds.actlongs.domain.video.entity.Video;
import com.sds.actlongs.model.ResultCode;
import com.sds.actlongs.model.ResultResponse;

@Getter
public class BoardDetailResponse extends ResultResponse {

	@ApiModelProperty(value = "제목", example = "재진스 플레이리스트")
	private final String title;
	@ApiModelProperty(value = "설명", example = "재진스의 뉴진스 플레이리스트 입니다.")
	private final String description;
	@ApiModelProperty(value = "작성자", example = "harry")
	private final String username;
	@ApiModelProperty(value = "썸네일 url", example = "https://act-longs.s3.ap-northeast-2.amazonaws.com/thumbnails/c0c5afcaaad24d91bfb777440ef3bc12.png")
	private final String thumbnailUrl;
	@ApiModelProperty(value = "동영상 파일 ID", example = "jazzeans")
	private final String videoUuid;
	@ApiModelProperty(value = "재생시간", example = "2017-02-16 09:21:50.634")
	private final LocalTime playingTime;
	@ApiModelProperty(value = "상태값", example = "COMPLETED")
	private final Board.Status boardStatus;

	private BoardDetailResponse(ResultCode resultCode, Video video) {
		super(resultCode);
		this.title = video.getBoard().getTitle();
		this.description = video.getBoard().getDescription();
		this.username = video.getBoard().getMember().getUsername();
		this.thumbnailUrl = S3_BUCKET_HOST_THUMBNAIL_URL + CATEGORY_PREFIX + video.getThumbnailImageUuid() + DOT
			+ video.getThumbnailImageType().name().toLowerCase();
		this.videoUuid = video.getVideoUuid();
		this.playingTime = video.getPlayingTime();
		this.boardStatus = video.getBoard().getStatus();
	}

	private BoardDetailResponse() {
		super(GET_BOARDDETAIL_FAIL);
		this.title = null;
		this.description = null;
		this.username = null;
		this.thumbnailUrl = null;
		this.videoUuid = null;
		this.playingTime = null;
		this.boardStatus = null;
	}

	public static BoardDetailResponse of(Optional<Video> result) {
		return result.map(video -> new BoardDetailResponse(GET_BOARDDETAIL_SUCCESS, video))
			.orElseGet(BoardDetailResponse::new);
	}

}
