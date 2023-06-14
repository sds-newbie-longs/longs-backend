package com.sds.actlongs.service.member;

import static org.mockito.BDDMockito.*;

import java.util.Optional;

import javax.servlet.http.HttpSession;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.sds.actlongs.domain.member.entity.Member;
import com.sds.actlongs.domain.member.repository.MemberRepository;

@ExtendWith(MockitoExtension.class)
class MemberServiceImplTest {

	@Mock
	private MemberRepository memberRepository;

	@InjectMocks
	private MemberServiceImpl subject;

	@Nested
	class Login {

		@Test
		@DisplayName("존재하는 사용자 아이디로 로그인하면, 로그인에 성공한다.")
		void ifLoginWithExistingUsernameThenSucceed() {
			//given
			String username = "Harry";
			Member member = Member.createNewMember(username);
			given(memberRepository.findByUsername(username)).willReturn(Optional.of(member));
			HttpSession mockHttpSession = mock(HttpSession.class);

			//when
			boolean result = subject.login(username, mockHttpSession);

			//then
			Assertions.assertThat(result).isEqualTo(true);
		}

		@Test
		@DisplayName("존재하지 않는 사용자 아이디로 로그인하면, 로그인에 실패한다.")
		void ifLoginWithNotExistingUsernameThenFail() {
			//given
			String username = "Harry";
			given(memberRepository.findByUsername(username)).willReturn(Optional.empty());
			HttpSession mockHttpSession = mock(HttpSession.class);

			//when
			boolean result = subject.login(username, mockHttpSession);

			//then
			Assertions.assertThat(result).isEqualTo(false);
		}

	}

}
