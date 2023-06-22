package com.sds.actlongs.domain.channelmember.repository;

import static org.assertj.core.api.AssertionsForClassTypes.*;

import java.util.List;

import org.junit.jupiter.api.DisplayName;
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
	void findByChannelName() {
		Member harry = Member.createNewMember("Harry");
		memberRepository.save(harry);
		Channel knoxSre = Channel.createNewChannel("Knox SRE", harry);
		channelRepository.save(knoxSre);

		channelMemberRepository.save(ChannelMember.registerMemberToChannel(harry, knoxSre));

		List<ChannelMember> byMember = channelMemberRepository.findAllFetchMemberAndChannelByMemberId(harry.getId());
		assertThat(byMember.get(0).getMember().getUsername()).isEqualTo("Harry");
		assertThat(byMember.get(0).getChannel().getChannelName()).isEqualTo("Knox SRE");
	}

	@Test
	@DisplayName("그룹원이 존재할 때 그룹원 목록을 조회하면, 그룹원 목록 조회에 성공한다.")
	void ifGetMemberListWhenChannelMembersExistThenSucceed() {
		Member harry = Member.createNewMember("Harry");
		memberRepository.save(harry);
		Member ari = Member.createNewMember("Ari");
		memberRepository.save(ari);
		Channel knoxSre = Channel.createNewChannel("Knox SRE", harry);
		channelRepository.save(knoxSre);

		channelMemberRepository.save(ChannelMember.registerMemberToChannel(harry, knoxSre));
		channelMemberRepository.save(ChannelMember.registerMemberToChannel(ari, knoxSre));

		List<ChannelMember> byChannel = channelMemberRepository.findAllFetchMemberByChannelId(knoxSre.getId());
		assertThat(byChannel.get(0).getMember().getUsername()).isEqualTo("Harry");
		assertThat(byChannel.get(1).getMember().getUsername()).isEqualTo("Ari");
		for (ChannelMember cm : byChannel) {
			assertThat(cm.getChannel().getChannelName()).isEqualTo("Knox SRE");
		}
	}

	@Test
	@DisplayName("삭제된 그룹일 때, 그룹 목록 조회시 조회되지 않는다.")
	void ifGetMemberListWhenChannelIsDeletedThenSucceed() {
		Member harry = Member.createNewMember("Harry");
		memberRepository.save(harry);
		Channel knoxSre = Channel.createNewChannel("Knox SRE", harry);
		knoxSre.delete();
		channelRepository.save(knoxSre);

		channelMemberRepository.save(ChannelMember.registerMemberToChannel(harry, knoxSre));

		List<ChannelMember> byChannel = channelMemberRepository.findAllFetchMemberAndChannelByMemberId(harry.getId());
		assertThat(byChannel.size()).isEqualTo(0);
	}

}
