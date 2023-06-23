package com.sds.actlongs.controller.channel;

import static com.sds.actlongs.util.SessionConstants.*;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.SessionAttribute;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;

import com.sds.actlongs.controller.channel.dto.ChannelCreateRequest;
import com.sds.actlongs.controller.channel.dto.ChannelCreateResponse;
import com.sds.actlongs.controller.channel.dto.ChannelDeleteResponse;
import com.sds.actlongs.controller.channel.dto.ChannelListResponse;
import com.sds.actlongs.domain.channel.entity.Channel;
import com.sds.actlongs.model.Authentication;
import com.sds.actlongs.service.channel.ChannelService;

@Api(tags = "그룹 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/groups")
public class ChannelController {

	private final ChannelService channelService;

	@ApiOperation(value = "그룹목록 조회 API", notes = "CL001: 그룹목록 조회에 성공하였습니다.")
	@GetMapping
	public ResponseEntity<ChannelListResponse> getChannelList(
		@SessionAttribute(AUTHENTICATION) Authentication authentication,
		final HttpServletRequest servletRequest) {
		final List<Channel> channels = channelService.getChannelList(authentication.getMemberId(),
			servletRequest.getSession());
		return ResponseEntity.ok(ChannelListResponse.from(channels));
	}

	@ApiOperation(value = "그룹생성 API", notes = ""
		+ "CC001: 그룹 생성에 성공하였습니다.\n"
		+ "CC002: 그룹 생성에 실패하였습니다.")
	@PostMapping
	public ResponseEntity<ChannelCreateResponse> createChannel(
		@Valid @RequestBody final ChannelCreateRequest request,
		@SessionAttribute(AUTHENTICATION) Authentication authentication,
		final HttpServletRequest servletRequest) {
		final boolean result = channelService.createChannel(request.getName(), authentication.getMemberId(),
			servletRequest.getSession());
		return ResponseEntity.ok(ChannelCreateResponse.from(result));
	}

	@ApiOperation(value = "그룹 삭제 API", notes = "CD001: 그룹 삭제에 성공하였습니다.")
	@DeleteMapping("/{groupId}")
	public ResponseEntity<ChannelDeleteResponse> deleteChannel(
		@PathVariable("groupId") final Long channelId,
		@SessionAttribute(AUTHENTICATION) Authentication authentication,
		final HttpServletRequest servletRequest) {
		channelService.deleteChannel(channelId, authentication.getMemberId(), servletRequest.getSession());
		return ResponseEntity.ok(new ChannelDeleteResponse());
	}

}
