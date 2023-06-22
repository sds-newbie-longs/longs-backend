package com.sds.actlongs.util.manage.upload;

import org.springframework.stereotype.Component;

import com.sds.actlongs.service.convert.ConvertService;
import com.sds.actlongs.util.manage.file.FileManage;
import com.sds.actlongs.util.sender.FileSender;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@RequiredArgsConstructor
public class UploadManageSync implements UploadManage {

	private final ConvertService convertService;
	private final FileManage fileManage;
	private final FileSender fileSender;

	@Override
	public void uploadProcess(String vodUuid) {
		System.out.println("================================= uploadProcess  Start =================================");
		convertService.convertToHls(vodUuid);

		fileSender.sendHlsFiles(vodUuid);
		fileSender.sendThumbnailFile(vodUuid);

	}

}
