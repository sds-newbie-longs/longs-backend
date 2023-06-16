package com.sds.actlongs.controller.channel;

import java.util.Optional;

import javax.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;

import com.sds.actlongs.controller.channel.dto.ChannelCreateRequest;
import com.sds.actlongs.controller.channel.dto.ChannelCreateResponse;

@Api(tags = "그룹 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/groups")
public class ChannelController {

	@ApiOperation(value = "그룹생성 API", notes = ""
		+ "CC001: 그룹 생성에 성공하였습니다.\n"
		+ "CC002: 그룹 생성에 실패하였습니다.")
	@PostMapping
	public ResponseEntity<ChannelCreateResponse> createChannel(@Valid @RequestBody final ChannelCreateRequest request) {
		// final boolean result = channelService.uniqueName(request.getUsername()); //채널생성 여부 (중복확인)
		return ResponseEntity.ok(ChannelCreateResponse.of(true));
	}

}
