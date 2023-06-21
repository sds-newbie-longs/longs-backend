package com.sds.actlongs.domain.member.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.Getter;
import lombok.NoArgsConstructor;

import com.sds.actlongs.domain.BaseEntity;
import com.sds.actlongs.vo.ImageExtension;

@Entity(name = "members")
@Getter
@NoArgsConstructor
public class Member extends BaseEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(nullable = false, unique = true, length = 20)
	private String username;

	@Column(unique = true, length = 36)
	private String profileImageUuid;

	@Enumerated(EnumType.STRING)
	private ImageExtension profileImageType;

	public Member(String username, String profileImageUuid, ImageExtension profileImageType) {
		this.username = username;
		this.profileImageUuid = profileImageUuid;
		this.profileImageType = profileImageType;
	}

	public static Member createNewMember(String username) {
		return new Member(username, null, null);
	}

}
