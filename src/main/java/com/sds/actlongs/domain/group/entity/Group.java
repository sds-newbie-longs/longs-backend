package com.sds.actlongs.domain.group.entity;

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
import com.sds.actlongs.domain.member.entity.Member;
import com.sds.actlongs.vo.ImageExtension;

@Entity(name = "groups")
@Getter
@NoArgsConstructor
public class Group extends BaseEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne
	@JoinColumn(name = "owner_id")
	private Member owner;

	@Column(nullable = false, unique = true, length = 20)
	private String name;

	@Column(unique = true, length = 36)
	private String groupImageUuid;

	@Enumerated(EnumType.STRING)
	private ImageExtension groupImageType;

	public Group(String name, Member owner, String groupImageUuid, ImageExtension groupImageType) {
		this.name = name;
		this.owner = owner;
		this.groupImageUuid = groupImageUuid;
		this.groupImageType = groupImageType;
	}

	public static Group createNewGroup(String name, Member owner) {
		return new Group(name, owner, null, null);
	}

}
