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
		if (fileManage.getVideoSizeMB(vodUuid) < VIDEO_SIZE_LIMITED) {
			convertService.convertToHlsWithoutCodec(vodUuid);
		} else {
			convertService.convertToHlsWithCodec(vodUuid, IncodingStatus.NEED_CODEC_H264);
		}

		fileSender.sendHlsFiles(vodUuid);
		fileSender.sendThumbnailFile(vodUuid);

		fileManage.deleteTempVideo(vodUuid);
		fileManage.deleteTempImage(vodUuid);
		fileManage.deleteTempHls(vodUuid);
	}

}
