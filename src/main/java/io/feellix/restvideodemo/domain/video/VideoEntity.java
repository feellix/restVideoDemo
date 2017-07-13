package io.feellix.restvideodemo.domain.video;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "tb_video")
@Getter
@NoArgsConstructor
public class VideoEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column(name = "file_size")
    private Long size;
    @Column(name = "file_name")
    private String name;
    @Column
    private Long duration;
    @Column(name = "file_location")
    private String location;
    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "codec", column = @Column(name = "audio_codec")),
            @AttributeOverride(name = "bitRate", column = @Column(name = "audio_bit_rate"))
    })
    private CodecInformation audioCodec;
    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "codec", column = @Column(name = "video_codec")),
            @AttributeOverride(name = "bitRate", column = @Column(name = "video_bit_rate"))
    })
    private CodecInformation videoCodec;

    public VideoEntity(String name) {
        this.name = name;
    }

    public VideoEntity withSize(long size) {
        this.size = size;
        return this;
    }

    public VideoEntity withLocation(String location) {
        this.location = location;
        return this;
    }

    public VideoEntity withAudio(CodecInformation audioInfo) {
        this.audioCodec = audioInfo;
        return this;
    }

    public VideoEntity withVideo(CodecInformation videoInfo) {
        this.videoCodec = videoInfo;
        return this;
    }

    public VideoEntity withDuration(Long duration) {
        this.duration = duration;
        return this;
    }
}
