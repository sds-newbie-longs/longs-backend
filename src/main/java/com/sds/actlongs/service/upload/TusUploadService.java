package com.sds.actlongs.service.upload;

import java.io.IOException;
import java.sql.Time;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.desair.tus.server.TusFileUploadService;
import me.desair.tus.server.exception.TusException;
import me.desair.tus.server.upload.UploadInfo;

import com.sds.actlongs.domain.board.repository.BoardRepository;
import com.sds.actlongs.domain.video.entity.Video;
import com.sds.actlongs.domain.video.repository.VideoRepository;
import com.sds.actlongs.model.Authentication;
import com.sds.actlongs.service.upload.dto.UploadResponseDto;
import com.sds.actlongs.util.duration.DurationExtractor;
import com.sds.actlongs.util.manage.file.FileManage;
import com.sds.actlongs.util.thumbnail.ThumbnailExtractor;
import com.sds.actlongs.util.uuid.UuidGenerate;

@Slf4j
@Profile({"local", "dev"})
@Service
@RequiredArgsConstructor
public class TusUploadService implements UploadService {

	private final TusFileUploadService tusFileUploadService;
	private final UuidGenerate uuidGenerate;
	private final FileManage fileManage;
	private final ThumbnailExtractor thumbnailExtractor;
	private final DurationExtractor durationExtractor;
	private final BoardRepository boardRepository;
	private final VideoRepository videoRepository;

	@Override
	public UploadResponseDto upload(Authentication authentication, Long groupId, HttpServletRequest request,
		HttpServletResponse response) {
		log.info("[UPLOAD] 들어왔다~");
		try {
			tusFileUploadService.process(request, response);
			final UploadInfo uploadInfo = tusFileUploadService.getUploadInfo(request.getRequestURI());

			if (uploadInfo != null && !uploadInfo.isUploadInProgress()) {
				final String vodUuid = uuidGenerate.getVodUuid();

				fileManage.createTempVideoFileInLocal(tusFileUploadService.getUploadedBytes(request.getRequestURI()),
					vodUuid);
				thumbnailExtractor.extract(vodUuid);
				// TODO - 재생시간 추출
				// final Time videoDuration = durationExtractor.extractReturnTime(vodUuid);
				// TODO - board, video save
				// TODO - board DDL 수정(title is nullable)
				// boardRepository.save(Board.create());
				// videoRepository.save(Video.createNewVideo());
				// TODO - 응답 data는 board_id로 변경
				log.info("[UPLOAD] 응답한다~");
				return new UploadResponseDto(vodUuid);
			}
		} catch (IOException | TusException e) {
			//TODO
		}
		log.info("[UPLOAD] 진행중~");
		return new UploadResponseDto();
	}

}
