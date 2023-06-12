package com.sds.actlongs.domain.member.repository;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;

import com.sds.actlongs.config.JpaAuditingConfig;
import com.sds.actlongs.domain.member.entity.Member;

@Import(JpaAuditingConfig.class)
@DataJpaTest
class MemberRepositoryTest {

	@Autowired
	private MemberRepository subject;

	@Nested
	class Save {

		@Test
		@DisplayName("DB에 사용자를 추가하면, DB에서 ID를 자동적으로 부여한다.")
		void ifSaveMemberThenIdIsAutomaticallyGiven() {
			// given
			Member member = Member.createNewMember("mandoo");

			// when
			Member result = subject.save(member);

			// then
			Assertions.assertThat(result.getId()).isNotNull();
		}

	}

}
