package com.sds.actlongs.service.board;

import static org.mockito.BDDMockito.*;

import java.sql.Time;
import java.time.LocalTime;
import java.util.Optional;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.sds.actlongs.controller.board.dto.ResultBoardDetail;
import com.sds.actlongs.domain.board.entity.Board;
import com.sds.actlongs.domain.channel.entity.Channel;
import com.sds.actlongs.domain.member.entity.Member;
import com.sds.actlongs.domain.video.entity.Video;
import com.sds.actlongs.domain.video.repository.VideoRepository;
import com.sds.actlongs.vo.ImageExtension;
import com.sds.actlongs.vo.VideoExtension;

@ExtendWith(MockitoExtension.class)
class BoardServiceImplTest {

	@Mock
	private VideoRepository videoRepository;

	@InjectMocks
	private BoardServiceImpl subject;

	@Test
	@DisplayName("존재하는 게시글 id로 상세정보를 요청하면 게시글 상세정보를 조회한다.")
	void ifGetBoardDetailWithExistingBoardIdThenSuceess() {
		//given
		Video video = new Video(
			new Board(
				new Member("harry", null, null),
				new Channel("Knox SRE", new Member("din", null, null), null, null),
				"재진스",
				"재진스의 뉴진스 플레이리스트 입니다."),
			"static/스크린샷(11)_1686513849288",
			ImageExtension.PNG,
			"data/test_1686534272185",
			VideoExtension.MP4,
			Time.valueOf(LocalTime.now()));
		given(videoRepository.findByBoard_Id(video.getId())).willReturn(Optional.of(video));

		//when
		ResultBoardDetail boardDetail = subject.getBoardDetail(video.getId());

		//then
		Assertions.assertThat(boardDetail.isResult()).isEqualTo(true);
		Assertions.assertThat(boardDetail.getVideo()).isEqualTo(video);
	}

	@Test
	@DisplayName("존재하지 않는 게시글 id로 상세정보를 요청하면 게시글 상세정보 조회에 실패한다.")
	void ifGetBoardDetailWithNotExistingBoardIdThenFail() {
		//given
		given(videoRepository.findByBoard_Id((long)1)).willReturn(Optional.empty());

		//when
		ResultBoardDetail boardDetail = subject.getBoardDetail((long)1);

		//then
		Assertions.assertThat(boardDetail.isResult()).isEqualTo(false);
	}

}
