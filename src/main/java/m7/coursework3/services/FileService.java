package m7.coursework3.services;

import java.nio.file.Path;
import java.util.Optional;

public interface FileService {

    Path save(String data, String fileName);

    Optional<String> read(String fileName);

    void clean(String fileName);
}
