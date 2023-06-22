package com.sds.actlongs.service.channelmember;

import static org.assertj.core.api.AssertionsForClassTypes.*;
import static org.mockito.BDDMockito.*;

import java.util.List;
import java.util.Optional;

import javax.persistence.EntityNotFoundException;

import org.assertj.core.api.ThrowableAssert;
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

@ExtendWith(MockitoExtension.class)
class ChannelMemberServiceImplTest {

	@InjectMocks
	private ChannelMemberServiceImpl subject;
	@Mock
	private ChannelRepository mockChannelRepository;
	@Mock
	private ChannelMemberRepository channelMemberRepository;

	@Test
	void getMemberList() {
		// given
		final Long channelId = 1L;
		final Channel mockChannel = mock(Channel.class);
		given(mockChannelRepository.findById(channelId)).willReturn(Optional.of(mockChannel));
		given(mockChannel.getStatus()).willReturn(Channel.Status.DELETED);

		// when
		ThrowableAssert.ThrowingCallable callable = () -> subject.getMemberList(channelId);

		// then
		assertThatThrownBy(callable)
			.isInstanceOf(EntityNotFoundException.class);
	}

	@Test
	void searchMembersNotInChannel() {
		//given
		Member ari = Member.createNewMember("Ari");
		Member harry = Member.createNewMember("Harry");
		Member nuree = Member.createNewMember("Null");
		Member jin = Member.createNewMember("Jin");
		Member sean = Member.createNewMember("Sean");

		Channel sre = Channel.createNewChannel("Knox SRE", harry);
		Channel common = Channel.createNewChannel("Knox Common", nuree);
		Channel portal = Channel.createNewChannel("Knox Portal", jin);
		List<Channel> channels = List.of(sre, common, portal);

		List<ChannelMember> channelMembers = List.of(
			new ChannelMember(harry, sre),
			new ChannelMember(ari, sre),
			new ChannelMember(nuree, common),
			new ChannelMember(sean, common),
			new ChannelMember(jin, portal),
			new ChannelMember(harry, portal)
		);
		given(channelMemberRepository.findAll()).willReturn(channelMembers);

		Long channelId = sre.getId();
		Long loginId = harry.getId();
		String keyword = "di";
		List<Member> searchedMembers = List.of(nuree, jin, sean);

		//when
		List<Member> result = subject.searchMembersNotInChannel(channelId, loginId, keyword);

		//then
		assertThat(result.size()).isEqualTo(searchedMembers.size());
		for (int i = 0; i < result.size(); i++) {
			Member m = result.get(i);
			assertThat(m.getUsername()).isEqualTo(searchedMembers.get(i).getUsername());
		}

	}

}
