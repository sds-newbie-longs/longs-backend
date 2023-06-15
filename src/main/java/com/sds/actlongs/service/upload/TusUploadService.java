package com.sds.actlongs.service.upload;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import me.desair.tus.server.TusFileUploadService;
import me.desair.tus.server.exception.TusException;
import me.desair.tus.server.upload.UploadInfo;

import com.sds.actlongs.service.upload.dto.UploadResponseDto;
import com.sds.actlongs.utils.manage.file.FileManage;
import com.sds.actlongs.utils.thumbnail.ThumbnailExtractor;
import com.sds.actlongs.utils.uuid.UuidGenerate;

@Profile({"local", "dev"})
@Service
@RequiredArgsConstructor
public class TusUploadService implements UploadService {

	private final TusFileUploadService tusFileUploadService;
	private final UuidGenerate uuidGenerate;
	private final FileManage fileManage;
	private final ThumbnailExtractor thumbnailExtractor;

	@Override
	public UploadResponseDto upload(HttpServletRequest request, HttpServletResponse response) {
		try {
			tusFileUploadService.process(request, response);
			UploadInfo uploadInfo = tusFileUploadService.getUploadInfo(request.getRequestURI());

			if (uploadInfo != null && !uploadInfo.isUploadInProgress()) {
				String vodUuid = uuidGenerate.getVodUuid();

				fileManage.createTempVideoFileInLocal(tusFileUploadService.getUploadedBytes(request.getRequestURI()),
					vodUuid);
				thumbnailExtractor.extract(vodUuid);
				return new UploadResponseDto(vodUuid);
			}
		} catch (IOException | TusException e) {
			//TODO
		}
		return new UploadResponseDto();
	}

}
