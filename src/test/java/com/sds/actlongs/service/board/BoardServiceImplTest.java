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

import com.sds.actlongs.domain.board.entity.Board;
import com.sds.actlongs.domain.board.repository.BoardRepository;
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

	@Mock
	private BoardRepository boardRepository;

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
		given(videoRepository.findByBoardId(video.getId())).willReturn(Optional.of(video));

		//when
		Optional<Video> videoOptional = subject.getBoardDetail(video.getId());

		//then
		Assertions.assertThat(videoOptional.get().getId()).isEqualTo(video.getId());
	}

	@Test
	@DisplayName("존재하지 않는 게시글 id로 상세정보를 요청하면 게시글 상세정보 조회에 실패한다.")
	void ifGetBoardDetailWithNotExistingBoardIdThenFail() {
		//given
		given(videoRepository.findByBoardId((long)1)).willReturn(Optional.empty());

		//when
		Optional<Video> videoOptional = subject.getBoardDetail((long)1);

		//then
		Assertions.assertThat(videoOptional).isEmpty();
	}

	@Test
	@DisplayName("존재하는 게시글 id로 게시글 수정을 요청하면 게시글이 수정된다.")
	void ifUpdateBoardWithExistingBoardIdThenSuceess() {
		//given
		Member member = new Member("harry", null, null);
		Board board = new Board(
			member,
			new Channel("Knox SRE", new Member("din", null, null), null, null),
			"재진스",
			"재진스의 뉴진스 플레이리스트 입니다.");
		Board updateBoard = new Board(board.getId(), "제목(수정)", "설명(수정)");
		given(boardRepository.findById(board.getId())).willReturn(Optional.of(board));

		//when
		Optional<Board> boardOptional = subject.updateBoard(updateBoard, member.getId());

		//then
		Assertions.assertThat(boardOptional.get().getTitle()).isEqualTo(updateBoard.getTitle());
		Assertions.assertThat(boardOptional.get().getDescription()).isEqualTo(updateBoard.getDescription());
	}

	@Test
	@DisplayName("존재하지 않는 게시글 id로 게시글 수정을 요청하면 게시글 수정에 실패한다.")
	void ifUpdateBoardWithNotExistingBoardIdThenFail() {
		//given
		given(boardRepository.findById((long)1)).willReturn(Optional.empty());

		//when
		Optional<Board> boardOptional = subject.updateBoard(new Board((long)1, "제목(수정)", "설명(수정)"), (long)1);

		//then
		Assertions.assertThat(boardOptional).isEmpty();
	}

	@Test
	@DisplayName("존재하는 게시글 id로 게시글 삭제를 요청하면 게시글 삭제에 성공한다.")
	void ifDeleteBoardWithExistingBoardIdThenSuceess() {
		//given
		Member member = new Member("harry", null, null);
		Board board = new Board(
			member,
			new Channel("Knox SRE", new Member("din", null, null), null, null),
			"재진스",
			"재진스의 뉴진스 플레이리스트 입니다.");
		given(boardRepository.findById(board.getId())).willReturn(Optional.of(board));

		//when
		boolean result = subject.deleteBoard(board.getId(), member.getId());

		//then
		Assertions.assertThat(result).isEqualTo(true);
	}

	@Test
	@DisplayName("존재하지 않는 게시글 id로 게시글삭제를 요청하면 게시글 삭제에 실패한다.")
	void ifDeleteBoardWithNotExistingBoardIdThenFail() {
		//given
		given(boardRepository.findById((long)1)).willReturn(Optional.empty());

		//when
		boolean result = subject.deleteBoard((long)1, (long)1);

		//then
		Assertions.assertThat(result).isEqualTo(false);
	}

}
