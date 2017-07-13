package io.feellix.restvideodemo.domain.video;

import io.feellix.restvideodemo.file.FileStorageRepository;
import org.apache.tika.Tika;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.mockito.BDDMockito.given;
import static org.mockito.Matchers.any;

public class VideoFactoryTest {

    private VideoFactory objectUnderTest;
    @Mock
    private FileStorageRepository fileStorageRepositoryMock;
    private MultipartFile mockTxtMultipartFile;
    private MultipartFile mockMp4MultipartFile;
    @Mock
    private Tika detectorMock;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        objectUnderTest = new VideoFactory(fileStorageRepositoryMock, detectorMock);
        objectUnderTest.setTmpDir(".");
        mockTxtMultipartFile = new MockMultipartFile("file", "test.txt", MediaType.TEXT_PLAIN_VALUE, this.getClass().getResourceAsStream("test.txt"));
        mockMp4MultipartFile = new MockMultipartFile("file", "test.mp4", MediaType.APPLICATION_OCTET_STREAM_VALUE, this.getClass().getResourceAsStream("test.mp4"));
    }

    @Test
    @Ignore
    public void shouldCreateVideoEntity() throws IOException {
        given(detectorMock.detect(any(ByteArrayInputStream.class))).willReturn("video/mp4");
        // testing xuggle will crash jvm :)
        VideoEntity result = objectUnderTest.createFrom(mockMp4MultipartFile);

        VideoEntityAssert.assertThat(result);
    }

    @Test
    public void shouldFailToCreateVideoEntityFromTxtFile() throws IOException {
        given(detectorMock.detect(any(ByteArrayInputStream.class))).willReturn(MediaType.TEXT_PLAIN_VALUE);

        Throwable thrown = catchThrowable(() -> objectUnderTest.createFrom(mockTxtMultipartFile));

        assertThat(thrown).isExactlyInstanceOf(IllegalArgumentException.class).hasNoCause();
    }

    @Test
    public void shouldFailToCreateVideoEntityFromNotKnownMultipart() throws IOException {
        given(detectorMock.detect(any(ByteArrayInputStream.class))).willReturn(null);

        Throwable thrown = catchThrowable(() -> objectUnderTest.createFrom(mockTxtMultipartFile));

        assertThat(thrown).isExactlyInstanceOf(IllegalArgumentException.class).hasNoCause();
    }
}