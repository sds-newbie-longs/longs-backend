package com.sds.actlongs.domain.channelmember.repository;

import static org.assertj.core.api.AssertionsForClassTypes.*;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;

import com.sds.actlongs.config.JpaAuditingConfig;
import com.sds.actlongs.domain.channel.entity.Channel;
import com.sds.actlongs.domain.channel.repository.ChannelRepository;
import com.sds.actlongs.domain.channelmember.entity.ChannelMember;
import com.sds.actlongs.domain.member.entity.Member;
import com.sds.actlongs.domain.member.repository.MemberRepository;

@Import(JpaAuditingConfig.class)
@DataJpaTest
class ChannelMemberRepositoryTest {

	@Autowired
	private ChannelMemberRepository channelMemberRepository;

	@Autowired
	private MemberRepository memberRepository;

	@Autowired
	private ChannelRepository channelRepository;

	@Test
	void test() {
		Member harry = Member.createNewMember("Harry");
		memberRepository.save(harry);
		Channel knoxSre = Channel.createNewChannel("Knox SRE", harry);
		channelRepository.save(knoxSre);

		channelMemberRepository.save(ChannelMember.registerMemberToChannel(harry, knoxSre));

		List<ChannelMember> byMember = channelMemberRepository.findAllFetchMemberAndChannelByMemberId(harry.getId());
		assertThat(byMember.get(0).getMember().getUsername()).isEqualTo("Harry");
		assertThat(byMember.get(0).getChannel().getName()).isEqualTo("Knox SRE");
	}

}
