package com.sds.actlongs.service.channel;

import static org.mockito.BDDMockito.*;

import java.util.List;
import java.util.Optional;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.sds.actlongs.domain.channel.entity.Channel;
import com.sds.actlongs.domain.channelmember.entity.ChannelMember;
import com.sds.actlongs.domain.channelmember.repository.ChannelMemberRepository;
import com.sds.actlongs.domain.member.entity.Member;
import com.sds.actlongs.domain.member.repository.MemberRepository;

@ExtendWith(MockitoExtension.class)
class ChannelServiceImplTest {

	@InjectMocks
	private ChannelServiceImpl subject;
	@Mock
	private MemberRepository memberRepository;
	@Mock
	private ChannelMemberRepository channelMemberRepository;

	@Nested
	class GetChannelList {

		@Test
		@DisplayName("어떤 그룹에도 소속되지 않은 Harry가 접속하면, 빈 그룹목록을 조회한다.")
		public void whenUserWhoDoesNotBelongToAnyGroupEntersThenGetEmptyList() {
			//given
			Member member = Member.createNewMember("Harry");
			given(memberRepository.findById(member.getId())).willReturn(Optional.of(member));
			given(channelMemberRepository.findByMember(member)).willReturn(List.of());

			//when
			List<ChannelMember> resultList = subject.getChannelList(member.getId());

			//then
			Assertions.assertThat(resultList.size()).isEqualTo(0);
		}

		@Test
		@DisplayName("3개의 그룹에 소속된 Harry가 접속하면, 3개의 그룹에 대한 그룹ID, 소유자ID, 그룹명을 조회한다.")
		public void whenUserBelongingToThreeGroupsEntersThenGetListOfThreeElements() {
			//given
			Member member1 = Member.createNewMember("Harry");
			Channel channel1 = Channel.createNewChannel("Knox SRE", member1);
			Channel channel2 = Channel.createNewChannel("Knox Common", member1);
			Channel channel3 = Channel.createNewChannel("Knox Portal", member1);
			List<ChannelMember> channelsHarry = List.of(
				ChannelMember.registerMemberToChannel(member1, channel1),
				ChannelMember.registerMemberToChannel(member1, channel2),
				ChannelMember.registerMemberToChannel(member1, channel3)
			);

			given(memberRepository.findById(member1.getId())).willReturn(Optional.of(member1));
			given(channelMemberRepository.findByMember(member1)).willReturn(channelsHarry);

			//when
			List<ChannelMember> resultList = subject.getChannelList(member1.getId());

			//then
			Assertions.assertThat(resultList.size()).isEqualTo(3);
			for (ChannelMember channelMember : resultList) {
				Assertions.assertThat(channelMember.getMember().getUsername()).isEqualTo("Harry");
			}
		}

	}

}
