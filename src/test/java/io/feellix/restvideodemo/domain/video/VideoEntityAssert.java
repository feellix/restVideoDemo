package io.feellix.restvideodemo.domain.video;

import org.assertj.core.api.AbstractAssert;

public class VideoEntityAssert extends AbstractAssert<VideoEntityAssert, VideoEntity> {

    VideoEntityAssert(VideoEntity actual) {
        super(actual, VideoEntity.class);
    }

    public static VideoEntityAssert assertThat(VideoEntity actual){
        VideoEntityAssert videoEntityAssert = new VideoEntityAssert(actual);
        videoEntityAssert.isNotNull();
        return videoEntityAssert;
    }


}
