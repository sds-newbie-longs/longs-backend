package com.sds.actlongs.service.channelmember;

import java.util.List;

import javax.servlet.http.HttpSession;

import com.sds.actlongs.controller.channelmember.dto.MemberListDto;
import com.sds.actlongs.controller.channelmember.dto.MemberSearchDto;
import com.sds.actlongs.domain.member.entity.Member;

public interface ChannelMemberService {

	List<MemberListDto> getMemberList(final Long channelId);

	List<MemberSearchDto> searchMembersNotInChannel(final Long channelId, final Long memberId, final String keyword);

	boolean inviteMember(final Long channelId, final Long memberId);

	boolean leaveChannel(final Long channelId, final Long memberId, final HttpSession session);

}
