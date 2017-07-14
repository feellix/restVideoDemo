package io.feellix.restvideodemo.domain.video;

import com.xuggle.xuggler.*;
import io.feellix.restvideodemo.common.error.DefaultException;
import io.feellix.restvideodemo.file.FileStorageRepository;
import org.apache.commons.lang3.StringUtils;
import org.apache.tika.Tika;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

@Component
public class VideoFactory {

    private static final Logger logger = LoggerFactory.getLogger(VideoFactory.class);

    private static final String VIDEO_CONTENT_TYPE_PREFIX = "video/";
    private static final String VIDEO_CONTENT_TYPE_IPHONE = "application/x-mpegURL";
    private static final String SUFFIX = "FILE";
    private static final String PREFIX = "REST_VIDEO";

    private final FileStorageRepository fileStorageRepository;
    private final Tika detector;

    @Value("${java.io.tmpdir}")
    private String tmpDir;

    public VideoFactory(FileStorageRepository fileStorageRepository, Tika detector) {
        this.fileStorageRepository = fileStorageRepository;
        this.detector = detector;
    }

    public VideoEntity createFrom(MultipartFile file) {
        validateContentType(file);
        String location = UUID.randomUUID().toString() + "_" + file.getOriginalFilename();
        try {
            fileStorageRepository.storeFile(location, file.getInputStream());
        } catch (IOException e) {
            throw new DefaultException("Could not read from uploaded file.", e);
        }
        VideoEntity videoEntity = new VideoEntity(file.getOriginalFilename())
                .withLocation(location)
                .withSize(file.getSize());
        try {
            return extract(videoEntity, file);
        } catch (IOException e) {
            throw new DefaultException("Could not read from uploaded file.", e);
        }
    }

    private VideoEntity extract(VideoEntity videoEntity, MultipartFile file) throws IOException {
        Path tempFile = Files.createTempFile(Paths.get(tmpDir), PREFIX, SUFFIX);
        file.transferTo(tempFile.toFile());
        IContainer container = IContainer.make();

        // Open up the container
        if (container.open(tempFile.toString(), IContainer.Type.READ, null) < 0) {
            throw new IllegalArgumentException("could not read file: ");
        }
        int numStreams = container.getNumStreams();
        logger.info("file: {} stream{}; ", numStreams, numStreams == 1 ? "" : "s");
        logger.info("duration (ms): {}; ", container.getDuration() == Global.NO_PTS ? "unknown" : "" + container.getDuration() / 1000);
        logger.info("start time (ms): {}; ", container.getStartTime() == Global.NO_PTS ? "unknown" : "" + container.getStartTime() / 1000);
        logger.info("file size (bytes): {}; ", container.getFileSize());
        logger.info("bit rate: {}; ", container.getBitRate());
        logger.info("\n");
        VideoEntity result = extractStreamsInformation(videoEntity, container).withDuration(container.getDuration() == Global.NO_PTS ? null : container.getDuration());
        container.close();
        return result;
    }


    private VideoEntity extractStreamsInformation(VideoEntity videoEntity, IContainer container) {
        for (int i = 0; i < container.getNumStreams(); i++) {
            // Find the stream object
            IStream stream = container.getStream(i);
            // Get the pre-configured decoder that can decode this stream;
            IStreamCoder coder = stream.getStreamCoder();

            // and now print out the meta data.
            logger.info("stream {}: ", i);
            logger.info("type: {}; ", coder.getCodecType());
            logger.info("codec: {}; ", coder.getCodecID());
            logger.info("duration: {}; ", stream.getDuration() == Global.NO_PTS ? "unknown" : "" + stream.getDuration());
            logger.info("start time: {}; ", container.getStartTime() == Global.NO_PTS ? "unknown" : "" + stream.getStartTime());
            logger.info("language: {}; ", stream.getLanguage() == null ? "unknown" : stream.getLanguage());
            logger.info("timebase: {}/{}; ", stream.getTimeBase().getNumerator(), stream.getTimeBase().getDenominator());
            logger.info("coder tb: {}/{}; ", coder.getTimeBase().getNumerator(), coder.getTimeBase().getDenominator());

            if (coder.getCodecType() == ICodec.Type.CODEC_TYPE_AUDIO) {
                logger.info("sample rate: {}; ", coder.getSampleRate());
                logger.info("channels: {}; ", coder.getChannels());
                logger.info("format: {}", coder.getSampleFormat());
                videoEntity.withAudio(new CodecInformation(coder.getCodec().getName(), (long) coder.getBitRate()));
            } else if (coder.getCodecType() == ICodec.Type.CODEC_TYPE_VIDEO) {
                logger.info("width: {}; ", coder.getWidth());
                logger.info("height: {}; ", coder.getHeight());
                logger.info("format: {}; ", coder.getPixelType());
                logger.info("frame-rate: {} ", coder.getFrameRate().getDouble());
               videoEntity.withVideo(new CodecInformation(coder.getCodec().getName(), (long) coder.getBitRate()));
            }
            logger.info("\n");
        }
        return videoEntity;
    }

    private void validateContentType(MultipartFile file) {
        try (InputStream io = file.getInputStream()) {
            String contentType = detector.detect(io);
            if (StringUtils.isBlank(contentType)
                    || !contentType.startsWith(VIDEO_CONTENT_TYPE_PREFIX) && !contentType.equals(VIDEO_CONTENT_TYPE_IPHONE)) {
                throw new IllegalArgumentException("Provided file is not video type.");
            }
        } catch (IOException e) {
            throw new DefaultException("Could not read from uploaded file.", e);
        }
    }

    //VisibleForTesting
    void setTmpDir(String tmpDir) {
        this.tmpDir = tmpDir;
    }
}
