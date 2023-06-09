package com.sds.actlongs.utils.duration;

import java.io.File;
import java.io.IOException;

import org.jcodec.api.FrameGrab;
import org.jcodec.common.io.NIOUtils;

public class DurationExtractorImpl implements DurationExtractor{
	@Override
	public Double extract(File source) throws IOException {
		try {
			FrameGrab frameGrab = FrameGrab
				.createFrameGrab(NIOUtils.readableChannel(source));

			return frameGrab.getVideoTrack().getMeta().getTotalDuration();
		}catch (Exception e){
			// exception
		}
		return (double)0;
	}

}
