package io.feellix.restvideodemo.domain.video;

import com.jayway.restassured.http.ContentType;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.context.embedded.LocalServerPort;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import static com.jayway.restassured.RestAssured.given;


@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("it")
public class VideoControllerIT {

	private static final Long TEST_VIDEO_ID = 1L;
	@LocalServerPort
	int port;

	@Before
	public void setUp() throws Exception {
	}

	@Test
	public void shouldUploadVideoFile() throws InterruptedException {
		given().port(port).basePath("/").authentication().preemptive().basic("user", "!user!")
				.contentType(MediaType.MULTIPART_FORM_DATA_VALUE)
				.multiPart("file", "test.mp4", this.getClass().getResourceAsStream("test.mp4"))
				.post("/rest/video")
				.then().statusCode(201).and().log().all();
	}


	@Test
	public void shouldGetAllVideos() {
		given().port(port).basePath("/").authentication().preemptive().basic("user", "!user!")
				.contentType(ContentType.JSON)
				.get("/rest/video")
				.then().statusCode(200).and().log().all();
	}

	@Test
	public void shouldGetOneVideo() {
		given().port(port).basePath("/").authentication().preemptive().basic("user", "!user!")
				.pathParam("id", TEST_VIDEO_ID)
				.contentType(ContentType.JSON)
				.get("/rest/video/{id}")
				.then().statusCode(200).and().log().all();
	}

	@Test
	public void shouldRejectAnonymousRequest() {
		given().port(port).basePath("/")
				.contentType(ContentType.JSON)
				.get("/rest/video")
				.then().statusCode(401).and().log().all();
	}

	@Test
	public void shouldRejectUploadingTxtFile() {
		given().port(port).basePath("/").authentication().preemptive().basic("user", "!user!")
				.contentType(MediaType.MULTIPART_FORM_DATA_VALUE)
				.multiPart("file", "test.txt", this.getClass().getResourceAsStream("test.txt"))
				.post("/rest/video")
				.then().statusCode(400).and().log().all();
	}
}
