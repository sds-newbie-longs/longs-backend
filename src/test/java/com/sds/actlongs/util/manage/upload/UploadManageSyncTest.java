package com.sds.actlongs.util.manage.upload;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.sds.actlongs.util.uuid.UuidGenerate;

@SpringBootTest
class UploadManageSyncTest {

	@Autowired
	private UuidGenerate uuidGenerate;

	@Autowired
	private UploadManage uploadManage;

	private String testVodUuid;

	@BeforeEach
	void init(){
		// generat uuid and copy samplevideo
		testVodUuid = uuidGenerate.getVodUuid();
		File sampleVideo = new File("./video/default/sample_1920x1080.mp4");
		File copyVideo = new File("./video/upload/temp/original/" + testVodUuid + ".mp4");
		try {
			FileUtils.copyFile(sampleVideo, copyVideo);
		} catch (IOException exception) {

		}
	}

	@Test
	void uploadProcessTest(){
		uploadManage.uploadProcess(testVodUuid);
	}
}
