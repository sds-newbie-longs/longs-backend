package com.sds.actlongs.service.channel;

import static org.mockito.BDDMockito.*;

import java.util.ArrayList;
import java.util.List;

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

@ExtendWith(MockitoExtension.class)
class ChannelServiceImplTest {

	@InjectMocks
	private ChannelServiceImpl subject;

	@Mock
	private ChannelMemberRepository channelMemberRepository;

	@Nested
	class GetChannelList {

		@Test
		@DisplayName("어떤 그룹에도 속하지 않은 Harry가 접속하면, 빈 그룹목록을 조회한다.")
		public void whenUserWhoDoesNotBelongToAnyGroupEntersThenGetEmptyList() {
			//given
			Member member = Member.createNewMember("Harry");
			given(channelMemberRepository.findAllFetchMemberAndChannelByMemberId(member.getId()))
				.willReturn(List.of());

			//when
			List<ChannelMember> resultList = subject.getChannelList(member.getId());

			//then
			Assertions.assertThat(resultList.size()).isEqualTo(0);
		}

		@Test
		@DisplayName("3개의 그룹에 속한 Harry가 접속하면, 3개의 그룹에 대한 그룹ID, 소유자ID, 그룹명을 조회한다.")
		public void whenUserBelongingToThreeGroupsEntersThenGetListOfThreeElements() {
			//given
			Member harry = Member.createNewMember("Harry");
			Member ari = Member.createNewMember("Ari");
			Member sean = Member.createNewMember("Sean");
			Channel channel1 = Channel.createNewChannel("Knox SRE", harry);
			Channel channel2 = Channel.createNewChannel("Knox Common", harry);
			Channel channel3 = Channel.createNewChannel("Knox Portal", ari);

			List<ChannelMember> channelsHarry = new ArrayList<>();
			channelsHarry.add(ChannelMember.registerMemberToChannel(harry, channel1));
			channelsHarry.add(ChannelMember.registerMemberToChannel(harry, channel2));
			channelsHarry.add(ChannelMember.registerMemberToChannel(harry, channel3));
			given(channelMemberRepository.findAllFetchMemberAndChannelByMemberId(harry.getId()))
				.willReturn(channelsHarry);

			List<ChannelMember> list = new ArrayList<>();
			list.addAll(channelsHarry);
			list.add(ChannelMember.registerMemberToChannel(ari, channel1));
			list.add(ChannelMember.registerMemberToChannel(sean, channel2));

			//when
			List<ChannelMember> resultList = subject.getChannelList(harry.getId());

			//then
			Assertions.assertThat(resultList.size()).isEqualTo(3);
			for (ChannelMember channelMember : resultList) {
				Assertions.assertThat(channelMember.getMember().getUsername()).isEqualTo("Harry");
			}
		}

	}

}
