package hu.webuni.studentregister202210.repository;


import hu.webuni.studentregister202210.model.Image;
import org.springframework.core.io.FileSystemResource;
import org.springframework.stereotype.Repository;

import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Date;

@Repository
public class ImageFileSystemRepository {

    String RESOURCES_DIR = ImageFileSystemRepository.class.getResource("/")
            .getPath();

    public String save(InputStream content, String imageName, Image currentImage) throws Exception {

        Path newFile = null;

        if (null != currentImage && null != currentImage.getLocation()){
            newFile = Paths.get(currentImage.getLocation());
            Files.delete(newFile);
        }

        newFile = Paths.get(RESOURCES_DIR + new Date().getTime() + "-" + imageName);

        Files.createDirectories(newFile.getParent());
        Files.copy(content, newFile);
        //Files.write(newFile, content);

        return newFile.toAbsolutePath()
                .toString();
    }

    public FileSystemResource findInFileSystem(String location) {
        try {
            return new FileSystemResource(Paths.get(location));
        } catch (Exception e) {
            // Handle access or file not found problems.
            throw new RuntimeException();
        }
    }
}