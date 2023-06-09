package com.sds.actlongs.utils.thumbnail;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import org.apache.commons.io.FileUtils;
import org.jcodec.api.FrameGrab;
import org.jcodec.common.io.NIOUtils;
import org.jcodec.common.model.Picture;
import org.jcodec.scale.AWTUtil;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class ThumbnailExtractorImpl implements ThumbnailExtractor{

	private static final String EXTENSION = "png";
	private static final Integer FRAME = 0;
	private static final String DEFAULT_IMAGE_PATH = "src/temp";

	public String extract(File source) throws IOException {
		File thumbnail = new File(source.getParent(),
			source.getName().split("\\.")[0] + "." + EXTENSION);
		String thumbnailName;

		try {
			FrameGrab frameGrab = FrameGrab.createFrameGrab(NIOUtils.readableChannel(source));

			frameGrab.seekToSecondPrecise(FRAME);
			Picture picture = frameGrab.getNativeFrame();

			BufferedImage bufferedImage = AWTUtil.toBufferedImage(picture);
			ImageIO.write(bufferedImage, EXTENSION, thumbnail);

			thumbnailName = thumbnail.getName();
		}catch (Exception e){
			thumbnailName = generateDefaultThumbnail(source);
		}
		return thumbnailName;
	}

	@Override
	public String generateDefaultThumbnail(File source) throws IOException {
		File thumbnail = new File(source.getParent(),
			source.getName().split("\\.")[0] + "." + EXTENSION);

		File defaultImage = new File(DEFAULT_IMAGE_PATH);

		try{
			FileUtils.copyFile(defaultImage, thumbnail);
		}catch (Exception exception){
			log.info("Thunmbnail Extract Faild => {}" , source.getPath());
		}
		return thumbnail.getName();
	}
}
