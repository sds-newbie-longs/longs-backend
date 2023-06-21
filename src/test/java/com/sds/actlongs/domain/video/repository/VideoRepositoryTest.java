package com.sds.actlongs.domain.video.repository;

import static com.sds.actlongs.vo.VideoExtension.*;

import java.sql.Time;

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
import com.sds.actlongs.domain.video.entity.Video;
import com.sds.actlongs.vo.ImageExtension;

@Import(JpaAuditingConfig.class)
@DataJpaTest
class VideoRepositoryTest {

	@Autowired
	private VideoRepository subject;

	@Nested
	class Save {

		@Test
		@DisplayName("DB에 새로운 비디오를 추가하면, DB에서 ID를 자동적으로 부여한다.")
		void ifCreateVideoThenIdIsAutomaticallyGiven() {
			// given
			Member member = Member.createNewMember("Harry");
			Channel channel = Channel.createNewChannel("Knox SRE", member);
			Board board = Board.createNewBoard(member, channel, "Title 1");
			Video video = Video.createNewVideo(board,
				"62dd98f0bd8e11ed93ab325096b39f47",
				ImageExtension.JPG,
				"d9daccee39dd4c4d855d9376bc981c11",
				MP4,
				new Time(0, 20, 15));

			// when
			Video result = subject.save(video);

			// then
			Assertions.assertThat(result.getId()).isNotNull();
			Assertions.assertThat(result.getThumbnailImageUuid()).isEqualTo("62dd98f0bd8e11ed93ab325096b39f47");
			Assertions.assertThat(result.getVideoUuid()).isEqualTo("d9daccee39dd4c4d855d9376bc981c11");
			Assertions.assertThat(result.getPlayingTime().toString()).isEqualTo("00:20:15");
		}

	}

}
