package com.sds.actlongs.domain.video.repository;

import static com.sds.actlongs.vo.ImageExtension.*;
import static com.sds.actlongs.vo.VideoExtension.*;
import static org.assertj.core.api.AssertionsForClassTypes.*;

import java.sql.Time;
import java.util.List;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;

import com.sds.actlongs.config.JpaAuditingConfig;
import com.sds.actlongs.domain.board.entity.Board;
import com.sds.actlongs.domain.board.repository.BoardRepository;
import com.sds.actlongs.domain.channel.entity.Channel;
import com.sds.actlongs.domain.channel.repository.ChannelRepository;
import com.sds.actlongs.domain.channelmember.entity.ChannelMember;
import com.sds.actlongs.domain.channelmember.repository.ChannelMemberRepository;
import com.sds.actlongs.domain.member.entity.Member;
import com.sds.actlongs.domain.member.repository.MemberRepository;
import com.sds.actlongs.domain.video.entity.Video;
import com.sds.actlongs.vo.ImageExtension;

@Import(JpaAuditingConfig.class)
@DataJpaTest
class VideoRepositoryTest {

	@Autowired
	private VideoRepository subject;

	@Autowired
	private MemberRepository memberRepository;

	@Autowired
	private VideoRepository videoRepository;

	@Autowired
	private BoardRepository boardRepository;

	@Autowired
	private ChannelMemberRepository channelMemberRepository;

	@Autowired
	private ChannelRepository channelRepository;

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

	@Nested
	class FindAllByChannelIdAndKeywordContainingOrderByCreatedAtDesc {

		@Test
		void test() {
			// given
			final Member member = Member.createNewMember("mandoo");
			final Channel channel = Channel.createNewChannel("knox", member);
			final ChannelMember channelMember = ChannelMember.registerMemberToChannel(member, channel);
			memberRepository.save(member);
			channelRepository.save(channel);
			channelMemberRepository.save(channelMember);

			final Board board = Board.createNewBoard(member, channel, "newjeans");
			final String uuid = "62dd98f0bd8e11ed93ab325096b39f43";
			final Video video = Video.createNewVideo(board, uuid, PNG, uuid, MP4, new Time(1000));
			boardRepository.save(board);
			videoRepository.save(video);

			final Board board2 = Board.createNewBoard(member, channel, "newjeans1");
			final String uuid2 = "62dd98f0bd8e11ed93ab325096b39f41";
			final Video video2 = Video.createNewVideo(board2, uuid2, PNG, uuid2, MP4, new Time(1000));
			boardRepository.save(board2);
			videoRepository.save(video2);

			final Board board3 = Board.createNewBoard(member, channel, "ive");
			final String uuid3 = "62dd98f0bd8e11ed93ab325096b39f42";
			final Video video3 = Video.createNewVideo(board3, uuid3, PNG, uuid3, MP4, new Time(1000));
			videoRepository.save(video3);
			boardRepository.save(board3);

			// when
			final List<Video> videos = subject.findAllByChannelIdAndKeywordContainingOrderByCreatedAtDesc(
				channel.getId(), "jeans");

			// then
			assertThat(videos.size()).isEqualTo(2);
			assertThat(videos.get(0).getBoard().getTitle()).isEqualTo("newjeans1");
			assertThat(videos.get(1).getBoard().getTitle()).isEqualTo("newjeans");
		}

	}

}
