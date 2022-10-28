package hu.webuni.studentregister202210.web;

import hu.webuni.studentregister202210.service.ImageService;
import lombok.AllArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.NativeWebRequest;

import java.util.Optional;

@AllArgsConstructor
@RestController
public class ImageController implements ImagesControllerApi{

    private final NativeWebRequest nativeWebRequest;
    private final ImageService imageService;


    @Override
    public Optional<NativeWebRequest> getRequest() {
        return Optional.of(nativeWebRequest);
    }

    @Override
    public ResponseEntity<Resource> getStudentImage(Long id) {
        return ResponseEntity.ok(imageService.find(id));
    }
}
