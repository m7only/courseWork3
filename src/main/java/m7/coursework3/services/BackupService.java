package m7.coursework3.services;

import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Path;
import java.util.Optional;

public interface BackupService {
    <T> Path saveBackup(T mapToSave, String fileName);

    <T> Optional<T> loadBackup(T map, String fileName);

    <T> Optional<T> downloadBackup(T type, MultipartFile file, String fileName);
}
