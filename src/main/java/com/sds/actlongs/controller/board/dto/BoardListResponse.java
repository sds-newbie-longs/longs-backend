package com.sds.actlongs.controller.board.dto;

import java.util.ArrayList;
import java.util.List;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;

import com.sds.actlongs.domain.video.entity.Video;
import com.sds.actlongs.model.ResultCode;
import com.sds.actlongs.model.ResultResponse;
import com.sds.actlongs.vo.ImageExtension;

@Getter
public class BoardListResponse extends ResultResponse {

	@ApiModelProperty(value = "게시글 검색목록")
	private final List<BoardResponse> boardList;

	public BoardListResponse(ResultCode resultCode, List<Video> videoList) {
		super(resultCode);
		List<BoardResponse> boardList = new ArrayList<>();
		for (Video video : videoList) {
			boardList.add(BoardResponse.of(video));
		}
		this.boardList = boardList;
	}

	public static BoardListResponse of(List<Video> videoList) {
		return new BoardListResponse(ResultCode.SEARCH_BOARDLIST_SUCCESS, videoList);
	}

	@Getter
	public static class BoardResponse {

		@ApiModelProperty(value = "게시글 ID", example = "1")
		private final Long boardId;
		@ApiModelProperty(value = "작성자", example = "harry")
		private final String username;
		@ApiModelProperty(value = "제목", example = "재진스 플레이리스트")
		private final String title;
		@ApiModelProperty(value = "설명", example = "재진스의 뉴진스 플레이리스트 입니다.")
		private final String description;
		@ApiModelProperty(value = "동영상 썸네일 이미지 파일 ID", example = "harry_thumbnail")
		private final String thumbnailImageUuid;
		@ApiModelProperty(value = "동영상 썸네일 이미지 파일 유형", example = "PNG")
		private final ImageExtension thumbnailImageType;

		private BoardResponse(Video video) {
			this.boardId = video.getBoard().getId();
			this.username = video.getBoard().getMember().getUsername();
			this.title = video.getBoard().getTitle();
			this.description = video.getBoard().getDescription();
			this.thumbnailImageUuid = video.getThumbnailImageUuid();
			this.thumbnailImageType = video.getThumbnailImageType();
		}

		public static BoardResponse of(Video video) {
			return new BoardResponse(video);
		}

	}

}
