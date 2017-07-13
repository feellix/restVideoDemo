package io.feellix.restvideodemo.domain.video;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.web.multipart.MultipartFile;

import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;


public class VideoControllerTest {

    private static final Long TEST_VIDEO_ID = 1L;
    private VideoController objectUnderTest;
    @Mock
    private VideoService videoServiceMock;
    @Mock
    private MultipartFile mockMultipartFile;
    @Mock
    private VideoDTO videoDTOMock;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        objectUnderTest = new VideoController(videoServiceMock);
    }

    @Test
    public void shouldUploadVideo() {
        given(videoServiceMock.createFrom(mockMultipartFile)).willReturn(videoDTOMock);

        VideoDTO result = objectUnderTest.uploadVideo(mockMultipartFile);

        assertThat(result).isEqualTo(videoDTOMock);
    }

    @Test
    public void getVideo() {
        given(videoServiceMock.getOne(TEST_VIDEO_ID)).willReturn(videoDTOMock);

        VideoDTO result = objectUnderTest.getVideo(TEST_VIDEO_ID);

        assertThat(result).isEqualTo(videoDTOMock);
    }

    @Test
    public void getAllVideos() {
        given(videoServiceMock.getAllVideos()).willReturn(Collections.singletonList(videoDTOMock));

        List<VideoDTO> result = objectUnderTest.getAllVideos();

        assertThat(result).containsExactly(videoDTOMock);
    }

}