package com.sds.actlongs.controller.channel;

import java.util.List;

import javax.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;

import com.sds.actlongs.controller.channel.dto.ChannelCreateRequest;
import com.sds.actlongs.controller.channel.dto.ChannelCreateResponse;
import com.sds.actlongs.controller.channel.dto.ChannelListResponse;

@Api(tags = "그룹 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/groups")
public class ChannelController {

	@ApiOperation(value = "그룹목록 조회 API", notes = ""
		+ "CL001: 그룹목록 조회에 성공하였습니다.")
	@GetMapping
	public ResponseEntity<ChannelListResponse> findChannelList() {
		// List<Channel> channelList = channelService.findChannelList();

		List<ChannelListResponse.JoinedChannel> channelList = List.of(
			// ChannelListResponse.JoinedChannel.of(1L, 1L, "Know SRE"),
			// ChannelListResponse.JoinedChannel.of(2L, 1L, "Know Common"),
			// ChannelListResponse.JoinedChannel.of(3L, 1L, "Know Portal")
		);

		ChannelListResponse listResponse = new ChannelListResponse(channelList);
		return ResponseEntity.ok(listResponse);
	}

	@ApiOperation(value = "그룹생성 API", notes = ""
		+ "CC001: 그룹 생성에 성공하였습니다.\n"
		+ "CC002: 그룹 생성에 실패하였습니다.")
	@PostMapping
	public ResponseEntity<ChannelCreateResponse> createChannel(@Valid @RequestBody final ChannelCreateRequest request) {
		// final boolean result = channelService.createChannel(request.getUsername()); //채널생성 여부 (중복확인)
		return ResponseEntity.ok(ChannelCreateResponse.from(true));
	}

}
