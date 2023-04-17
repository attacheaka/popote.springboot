package attache.devs.popote.utils;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.apache.tika.Tika;
import java.io.IOException;

@Service
public class FileValidator {

    public boolean isImage(MultipartFile file) throws IOException {
        Tika tika = new Tika();
        String mimeType = tika.detect(file.getBytes());
        return mimeType.startsWith("image/");
    }

    public boolean isFileSizeValid(MultipartFile file) {
        final long MAX_FILE_SIZE = 1024 * 1024;
        return file.getSize() <= MAX_FILE_SIZE;
    }

}
