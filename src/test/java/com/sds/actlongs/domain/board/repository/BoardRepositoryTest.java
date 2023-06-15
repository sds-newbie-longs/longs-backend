package com.sds.actlongs.domain.board.repository;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;

import com.sds.actlongs.config.JpaAuditingConfig;
import com.sds.actlongs.domain.board.entity.Board;
import com.sds.actlongs.domain.channel.entity.Channel;
import com.sds.actlongs.domain.member.entity.Member;

@Import(JpaAuditingConfig.class)
@DataJpaTest
class BoardRepositoryTest {

	@Autowired
	private BoardRepository subject;

	@Nested
	class Save {

		@Test
		@DisplayName("DB에 새로운 게시글을 추가하면, DB에서 ID를 자동적으로 부여한다.")
		void ifCreateBoardThenIdIsAutomaticallyGiven() {
			// given
			Member member = Member.createNewMember("Harry");
			Channel channel = Channel.createNewChannel("Knox SRE", member);
			Board board = Board.createNewBoard(member, channel, "Title 1");

			// when
			Board result = subject.save(board);

			// then
			Assertions.assertThat(result.getId()).isNotNull();
			Assertions.assertThat(result.getMember().getUsername()).isEqualTo("Harry");
			Assertions.assertThat(result.getChannel().getName()).isEqualTo("Knox SRE");
			Assertions.assertThat(result.getTitle()).isEqualTo("Title 1");
		}

	}

}
