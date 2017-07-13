package io.feellix.restvideodemo.domain.video;

import io.feellix.restvideodemo.common.ApplicationService;
import org.springframework.core.convert.ConversionService;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

import static java.util.stream.Collectors.toList;

@ApplicationService
public class VideoService {

    private final VideoFactory videoFactory;
    private final VideoRepository videoRepository;
    private final ConversionService conversionService;

    public VideoService(VideoFactory videoFactory, VideoRepository videoRepository, ConversionService conversionService) {
        this.videoFactory = videoFactory;
        this.videoRepository = videoRepository;
        this.conversionService = conversionService;
    }

    public List<VideoDTO> getAllVideos(){
        return videoRepository.findAll()
                .stream()
                .map(videoEntity -> conversionService.convert(videoEntity, VideoDTO.class))
                .collect(toList());
    }

    public VideoDTO getOne(Long id) {
        return conversionService.convert(videoRepository.getOne(id), VideoDTO.class);
    }

    public VideoDTO createFrom(MultipartFile file) {
        VideoEntity videoEntity = videoFactory.createFrom(file);
        videoRepository.save(videoEntity);
        return conversionService.convert(videoEntity, VideoDTO.class);
    }
}
