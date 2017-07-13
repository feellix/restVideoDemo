package io.feellix.restvideodemo.domain.video;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/rest/video")
public class VideoController {

    private final VideoService videoService;

    public VideoController(VideoService videoService) {
        this.videoService = videoService;
    }

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public VideoDTO uploadVideo(@RequestParam("file") MultipartFile file) {
        return videoService.createFrom(file);
    }

    @GetMapping(path = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public VideoDTO getVideo(@PathVariable Long id) {
        return videoService.getOne(id);
    }

    @GetMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public List<VideoDTO> getAllVideos() {
        return videoService.getAllVideos();
    }

}
