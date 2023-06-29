package com.sds.actlongs.util.manage.upload;

import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import com.sds.actlongs.model.IncodingStatus;
import com.sds.actlongs.service.convert.ConvertService;
import com.sds.actlongs.util.manage.file.FileManage;
import com.sds.actlongs.util.sender.FileSender;

@Slf4j
@Component
@RequiredArgsConstructor
public class UploadManageSync implements UploadManage {

	private static final Long VIDEO_SIZE_LIMITED = 300L;

	private final ConvertService convertService;
	private final FileManage fileManage;
	private final FileSender fileSender;

	@Override
	public void uploadProcess(String vodUuid) {
		IncodingStatus status = (fileManage.getVideoSizeMB(vodUuid) < VIDEO_SIZE_LIMITED
			? IncodingStatus.NEED_NOT_CODEC : IncodingStatus.NEED_CODEC_H264);

		if (status == IncodingStatus.NEED_NOT_CODEC) {
			convertService.convertToHlsWithoutCodec(vodUuid);
		} else {
			convertService.convertToHlsWithCodec(vodUuid, status);
		}
		fileSender.sendHlsFiles(vodUuid);

		fileManage.deleteTempVideo(vodUuid);
		fileManage.deleteTempHls(vodUuid);
	}

	@Override
	public void uploadProcess480P(String vodUuid) {
		fileSender.sendThumbnailFile(vodUuid);

		IncodingStatus status = (fileManage.getVideoSizeMB(vodUuid) < VIDEO_SIZE_LIMITED
			? IncodingStatus.NEED_NOT_CODEC : IncodingStatus.NEED_CODEC_H264);

		if (status == IncodingStatus.NEED_NOT_CODEC) {
			convertService.firstStepIncodingWithoutCodec(vodUuid);
		} else {
			convertService.firstStepIncodingWithCodec(vodUuid, status);
		}
		fileSender.sendHlsFiles(vodUuid);

		fileManage.deleteTempImage(vodUuid);
		fileManage.deleteTempHls(vodUuid);
	}

}
