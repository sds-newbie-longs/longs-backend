package com.sds.actlongs.domain.channel.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import lombok.Getter;
import lombok.NoArgsConstructor;

import com.sds.actlongs.domain.BaseEntity;
import com.sds.actlongs.domain.member.entity.Member;
import com.sds.actlongs.vo.ImageExtension;

@Entity(name = "channels")
@Getter
@NoArgsConstructor
public class Channel extends BaseEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "owner_id")
	private Member owner;

	@Column(nullable = false, unique = true, length = 20)
	private String name;

	@Column(unique = true, length = 36)
	private String imageUuid;

	@Enumerated(EnumType.STRING)
	private ImageExtension imageType;

	public Channel(String name, Member owner, String imageUuid, ImageExtension imageType) {
		this.name = name;
		this.owner = owner;
		this.imageUuid = imageUuid;
		this.imageType = imageType;
	}

	public static Channel createNewChannel(String name, Member owner) {
		return new Channel(name, owner, null, null);
	}

}
