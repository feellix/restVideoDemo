package io.feellix.restvideodemo.domain.video;

import lombok.Data;

@Data
public class VideoDTO {

    private Long id;
    private Long size;
    private Long duration;
    private String name;
    private String location;
    private CodecInformation audioCodec;
    private CodecInformation videoCodec;

}
