package com.sds.actlongs.util.sender;

import java.io.File;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static com.sds.actlongs.util.Constants.*;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

import com.sds.actlongs.infra.S3Uploader;
import com.sds.actlongs.util.manage.file.FileManage;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class FileSenderForS3 implements FileSender{

	private final S3Uploader s3Uploader;
	private final FileManage fileManage;

	@Override
	public void sendFiles(String rooPath) {
		File root = new File(rooPath);
		File[] files = root.listFiles();

		if(files == null){
			return;
		}

		this.process(rooPath,files);

		for(File file : files){
			if(file.isDirectory()){
				File child = new File(file.getPath());
				File[] childFiles = child.listFiles();
				if(childFiles != null) process(file.getPath() , childFiles);
			}
		}
	}

	private void process(String rooPath ,File[] files){
		for(File file : files){
			if(file.isFile()){
				s3Uploader.uploadFile(fileManage.transFileToMultipartFile(file),rooPath);
			}
		}
	}


}
