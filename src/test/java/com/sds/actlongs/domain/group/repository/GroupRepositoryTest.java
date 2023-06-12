package com.sds.actlongs.domain.group.repository;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;

import com.sds.actlongs.config.JpaAuditingConfig;
import com.sds.actlongs.domain.group.entity.Group;
import com.sds.actlongs.domain.member.entity.Member;

@Import(JpaAuditingConfig.class)
@DataJpaTest
class GroupRepositoryTest {

	@Autowired
	private GroupRepository subject;

	@Nested
	class Save {

		@Test
		@DisplayName("새로운 그룹을 생성하면, DB에서 ID를 자동적으로 부여한다.")
		void ifCreateGroupThenIdIsAutomaticallyGiven() {
			// given
			Member member = Member.createNewMember("Harry");
			Group group = Group.createNewGroup("Knox SRE", member);

			// when
			Group result = subject.save(group);

			// then
			Assertions.assertThat(result.getId()).isNotNull();
			Assertions.assertThat(result.getOwner().getUsername()).isEqualTo("Harry");
		}

	}

}
