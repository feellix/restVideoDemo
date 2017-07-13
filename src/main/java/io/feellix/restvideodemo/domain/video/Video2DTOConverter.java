package io.feellix.restvideodemo.domain.video;

import org.dozer.Mapper;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class Video2DTOConverter implements Converter<VideoEntity, VideoDTO> {

    private final Mapper mapper;

    public Video2DTOConverter(Mapper mapper) {
        this.mapper = mapper;
    }

    @Override
    public VideoDTO convert(VideoEntity videoEntity) {
        return mapper.map(videoEntity, VideoDTO.class);
    }
}
