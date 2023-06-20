package com.sds.actlongs.service.channel;

import static org.mockito.BDDMockito.*;

import java.util.ArrayList;
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
import com.sds.actlongs.domain.channel.repository.ChannelRepository;
import com.sds.actlongs.domain.channelmember.entity.ChannelMember;
import com.sds.actlongs.domain.channelmember.repository.ChannelMemberRepository;
import com.sds.actlongs.domain.member.entity.Member;
import com.sds.actlongs.domain.member.repository.MemberRepository;

@ExtendWith(MockitoExtension.class)
class ChannelServiceImplTest {

	@InjectMocks
	private ChannelServiceImpl subject;

	@Mock
	private ChannelMemberRepository channelMemberRepository;

	@Mock
	private ChannelRepository channelRepository;

	@Mock
	private MemberRepository memberRepository;

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

	@Nested
	class CreateChannelList {

		@Test
		@DisplayName("Harry가 기존과 겹치지 않는 그룹명을 입력하면, 그룹 생성에 성공한다.")
		public void ifCreateChannelWithNonDuplicateNameTheSucceed() {
			//given
			Member harry = Member.createNewMember("Harry");
			String channelName = "Knox SRE";
			given(channelRepository.findByChannelName(channelName)).willReturn(Optional.empty());
			given(memberRepository.findById(harry.getId())).willReturn(Optional.of(harry));

			Channel channel = Channel.createNewChannel(channelName, harry);
			given(channelRepository.save(any())).willReturn(channel);
			ChannelMember channelMember = ChannelMember.registerMemberToChannel(harry, channel);
			given(channelMemberRepository.save(any())).willReturn(channelMember);

			//when
			boolean result = subject.createChannel(channelName, harry.getId());

			//then
			Assertions.assertThat(result).isTrue();
		}

		@Test
		@DisplayName("Ari가 생성한 그룹과 동일한 이름으로 Harry가 그룹명을 입력하면, 그룹 생성에 실패한다.")
		public void ifCreateChannelWithDuplicatedNameTheFail() {
			//given
			Member ari = Member.createNewMember("Ari");
			String channelName = "Knox SRE";
			given(channelRepository.findByChannelName(channelName)).willReturn(Optional.empty());
			Channel channelAri = Channel.createNewChannel(channelName, ari);

			Member harry = Member.createNewMember("Harry");
			given(channelRepository.findByChannelName(channelName)).willReturn(Optional.of(channelAri));

			//when
			boolean result = subject.createChannel(channelName, harry.getId());

			//then
			Assertions.assertThat(result).isFalse();
		}

	}

}
