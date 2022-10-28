package hu.webuni.studentregister202210.service;

import hu.webuni.studentregister202210.model.Image;
import hu.webuni.studentregister202210.model.Student;
import hu.webuni.studentregister202210.repository.ImageDBRepository;
import hu.webuni.studentregister202210.repository.ImageFileSystemRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import javax.transaction.Transactional;
import java.io.InputStream;

@Slf4j
@Service
@RequiredArgsConstructor
public class ImageService {

    private final ImageFileSystemRepository fileSystemRepository;

    private final ImageDBRepository imageDbRepository;

    private final StudentService studentService;


    public FileSystemResource find(Long studentId) {

        Student student = studentService.getStudent(studentId);
        if (null == student){
            throw new RuntimeException("Student does not exists");
        }
        if (null == student.getImage())
        {
            throw new RuntimeException("Student does not have image uploaded");
        }
        Long imageId = student.getImage().getId();

        Image image = imageDbRepository.findById(imageId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        return fileSystemRepository.findInFileSystem(image.getLocation());
    }

    @Transactional
    public Long save(InputStream bytes, String imageName, Long id) throws Exception {

        Student student = studentService.getStudent(id);
        if (null == student){
            throw new RuntimeException("Student does not exists");
        }

        log.info(student.toString());
        //Store the location into local file system
        String location = fileSystemRepository.save(bytes, imageName,student.getImage());
        log.info(location);

        if (null == location){
            throw new RuntimeException("Problem during location saving");

        }
        Image image= Image.builder().name(imageName).location(location).build();
        Image savedImage = imageDbRepository.save(image);
        student.setImage(savedImage);


        return savedImage.getId();
    }
}
