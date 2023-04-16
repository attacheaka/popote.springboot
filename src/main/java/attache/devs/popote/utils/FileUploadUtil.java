package attache.devs.popote.utils;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

public class FileUploadUtil {

    @Value("${file.upload-dir}")
    private static String uploadDir;

    public static void saveFile(String fileName, MultipartFile image) throws IOException {

        try (InputStream inputStream = image.getInputStream() ) {

            Files.copy(inputStream, Paths.get(uploadDir + File.separator + image.getOriginalFilename()), StandardCopyOption.REPLACE_EXISTING);

        } catch (IOException ioe) {
            throw new IOException("error upload image: "+ fileName, ioe);
        }
    }

}
