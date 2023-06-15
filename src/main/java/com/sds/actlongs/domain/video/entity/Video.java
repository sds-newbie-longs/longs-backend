package com.sds.actlongs.domain.video.entity;

import java.sql.Time;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

import lombok.Getter;
import lombok.NoArgsConstructor;

import com.sds.actlongs.domain.BaseEntity;
import com.sds.actlongs.domain.board.entity.Board;
import com.sds.actlongs.vo.ImageExtension;
import com.sds.actlongs.vo.VideoExtension;

@Entity(name = "videos")
@Getter
@NoArgsConstructor
public class Video extends BaseEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@OneToOne
	@JoinColumn(name = "board_id")
	private Board board;

	@Column(nullable = false, unique = true, length = 36)
	private String thumbnailImageUuid;

	@Enumerated(EnumType.STRING)
	@Column(nullable = false)
	private ImageExtension thumbnailImageType;

	@Column(nullable = false, unique = true, length = 36)
	private String videoUuid;

	@Enumerated(EnumType.STRING)
	@Column(nullable = false)
	private VideoExtension videoType;

	@Column(nullable = false)
	private Time playingTime;

	public Video(Board board, String thumbnailImageUuid, ImageExtension thumbnailImageType,
		String videoUuid, VideoExtension videoType, Time playingTime) {
		this.board = board;
		this.thumbnailImageUuid = thumbnailImageUuid;
		this.thumbnailImageType = thumbnailImageType;
		this.videoUuid = videoUuid;
		this.videoType = videoType;
		this.playingTime = playingTime;
	}

	public static Video createNewVideo(
		Board board,
		String thumbnailImageUuid,
		ImageExtension thumbnailImageType,
		String videoUuid,
		VideoExtension videoType,
		Time playingTime) {
		return new Video(board, thumbnailImageUuid, thumbnailImageType, videoUuid, videoType, playingTime);
	}

}
