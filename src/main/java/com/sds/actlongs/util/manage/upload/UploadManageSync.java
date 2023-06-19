package com.sds.actlongs.util.manage.upload;

import static com.sds.actlongs.util.Constants.*;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

import com.sds.actlongs.service.convert.ConvertService;
import com.sds.actlongs.util.manage.file.FileManage;
import com.sds.actlongs.util.sender.FileSender;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
@PropertySource("classpath:upload.properties")
public class UploadManageSync implements UploadManage{

	private final ConvertService convertService;
	private final FileManage fileManage;
	private final FileSender fileSender;

	@Value("{temp.hls.path}")
	private String hlsPath;

	@Override
	public void uploadProcess(String vodUuid) {
		convertService.convertToHls(vodUuid);

		fileSender.sendFiles(hlsPath + CATEGORY_PREFIX);

		fileManage.deleteTempVideo(vodUuid);
		fileManage.deleteTempImage(vodUuid);
		fileManage.deleteTempHls(vodUuid);
	}
}
