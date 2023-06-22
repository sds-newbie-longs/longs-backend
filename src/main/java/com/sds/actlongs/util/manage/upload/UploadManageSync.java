package com.sds.actlongs.util.manage.upload;

import org.springframework.stereotype.Component;

import com.sds.actlongs.service.convert.ConvertService;
import com.sds.actlongs.util.manage.file.FileManage;
import com.sds.actlongs.util.sender.FileSender;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class UploadManageSync implements UploadManage {

	private final ConvertService convertService;
	private final FileManage fileManage;
	private final FileSender fileSender;

	@Override
	public void uploadProcess(String vodUuid) {
		convertService.convertToHls(vodUuid);

		fileSender.sendHlsFiles(vodUuid);
		fileSender.sendThumbnailFile(vodUuid);

		fileManage.deleteTempVideo(vodUuid);
		fileManage.deleteTempImage(vodUuid);
		fileManage.deleteTempHls(vodUuid);
	}

}
