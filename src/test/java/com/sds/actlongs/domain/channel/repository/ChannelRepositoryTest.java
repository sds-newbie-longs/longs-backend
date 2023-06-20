package com.sds.actlongs.domain.channel.repository;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;

import com.sds.actlongs.config.JpaAuditingConfig;
import com.sds.actlongs.domain.channel.entity.Channel;
import com.sds.actlongs.domain.member.entity.Member;

@Import(JpaAuditingConfig.class)
@DataJpaTest
class ChannelRepositoryTest {

	@Autowired
	private ChannelRepository subject;

	@Nested
	class Save {

		@Test
		@DisplayName("DB에 새로운 채널을 추가하면, DB에서 ID를 자동적으로 부여한다.")
		void ifCreateChannelThenIdIsAutomaticallyGiven() {
			// given
			Member member = Member.createNewMember("Harry");
			Channel channel = Channel.createNewChannel("Knox SRE", member);

			// when
			Channel result = subject.save(channel);

			// then
			Assertions.assertThat(result.getId()).isNotNull();
			Assertions.assertThat(result.getOwner().getUsername()).isEqualTo("Harry");
			Assertions.assertThat(result.getChannelName()).isEqualTo("Knox SRE");
		}

	}

}
