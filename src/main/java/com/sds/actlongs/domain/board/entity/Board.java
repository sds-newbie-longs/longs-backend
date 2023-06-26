package com.sds.actlongs.domain.board.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import lombok.Getter;
import lombok.NoArgsConstructor;

import com.sds.actlongs.domain.BaseEntity;
import com.sds.actlongs.domain.channel.entity.Channel;
import com.sds.actlongs.domain.member.entity.Member;

@Entity(name = "boards")
@Getter
@NoArgsConstructor
public class Board extends BaseEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne
	@JoinColumn(name = "member_id")
	private Member member;

	@ManyToOne
	@JoinColumn(name = "channel_id")
	private Channel channel;

	@Column(nullable = false, length = 50)
	private String title;

	@Column(length = 1000)
	private String description;

	@Column(nullable = false)
	@Enumerated(EnumType.STRING)
	private Status status = Status.CREATED;

	public Board(Member member, Channel channel, String title, String description) {
		this.member = member;
		this.channel = channel;
		this.title = title;
		this.description = description;
	}

	public Board(Long id, String title, String description) {
		this.id = id;
		this.member = null;
		this.channel = null;
		this.title = title;
		this.description = description;
	}

	public static Board createNewBoard(Member member, Channel channel, String title) {
		return new Board(member, channel, title, null);
	}

	public static Board createNewBoardWithDescription(Member member, Channel channel, String title,
		String description) {
		return new Board(member, channel, title, description);
	}

	public Board updateBoard(String title, String description) {
		this.title = title;
		this.description = description;
		return this;
	}

	public void delete() {
		if (this.status.equals(Status.COMPLETED)) {
			this.status = Status.DELETED;
		}
	}

	public void completed() {
		if (this.status.equals(Status.CREATED)) {
			this.status = Status.COMPLETED;
		}
	}

	public enum Status {
		CREATED, COMPLETED, DELETED
	}

}
