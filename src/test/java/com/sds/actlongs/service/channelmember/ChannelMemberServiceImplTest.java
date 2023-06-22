package com.sds.actlongs.service.channelmember;

import static org.assertj.core.api.AssertionsForClassTypes.*;
import static org.mockito.BDDMockito.*;

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
import com.sds.actlongs.domain.channelmember.repository.ChannelMemberRepository;

@ExtendWith(MockitoExtension.class)
class ChannelMemberServiceImplTest {

	@InjectMocks
	private ChannelMemberServiceImpl subject;
	@Mock
	private ChannelRepository mockChannelRepository;

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

}
