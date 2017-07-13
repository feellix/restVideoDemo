package io.feellix.restvideodemo.domain.video;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.core.convert.ConversionService;
import org.springframework.web.multipart.MultipartFile;

import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;


public class VideoServiceTest {

    private static final Long TEST_VIDEO_ID = 1L;
    private VideoService objectUnderTest;
    @Mock
    private MultipartFile mockMultipartFile;
    @Mock
    private VideoDTO videoDTOMock;
    @Mock
    private VideoFactory videoFactoryMock;
    @Mock
    private VideoRepository videoRepositoryMock;
    @Mock
    private ConversionService conversionServiceMock;
    @Mock
    private VideoEntity videoEntityMock;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        objectUnderTest = new VideoService(videoFactoryMock, videoRepositoryMock, conversionServiceMock);
    }

    @Test
    public void shouldUploadVideo() {
        given(videoFactoryMock.createFrom(mockMultipartFile)).willReturn(videoEntityMock);
        given(videoRepositoryMock.save(videoEntityMock)).willReturn(videoEntityMock);
        given(conversionServiceMock.convert(videoEntityMock, VideoDTO.class)).willReturn(videoDTOMock);

        VideoDTO result = objectUnderTest.createFrom(mockMultipartFile);

        assertThat(result).isEqualTo(videoDTOMock);
    }

    @Test
    public void getVideo() {
        given(videoRepositoryMock.getOne(TEST_VIDEO_ID)).willReturn(videoEntityMock);
        given(conversionServiceMock.convert(videoEntityMock, VideoDTO.class)).willReturn(videoDTOMock);

        VideoDTO result = objectUnderTest.getOne(TEST_VIDEO_ID);

        assertThat(result).isEqualTo(videoDTOMock);
    }

    @Test
    public void getAllVideos() {
        given(videoRepositoryMock.findAll()).willReturn(Collections.singletonList(videoEntityMock));
        given(conversionServiceMock.convert(videoEntityMock, VideoDTO.class)).willReturn(videoDTOMock);

        List<VideoDTO> result = objectUnderTest.getAllVideos();

        assertThat(result).containsExactly(videoDTOMock);
    }

}