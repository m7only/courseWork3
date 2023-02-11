package m7.coursework3.services.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import m7.coursework3.services.BackupService;
import m7.coursework3.services.FileService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Path;
import java.util.Optional;

@Service
public class BackupServiceImpl implements BackupService {
    @Value("${path.to.backup.folder}")
    String backupFolder;
    FileService fIleService;

    public BackupServiceImpl(FileService fIleService) {
        this.fIleService = fIleService;
    }

    @Override
    public <T> Path saveBackup(T objToSave, String fileName) {
        Path path = Path.of(backupFolder, fileName);
        try {
            return fIleService.save(
                    new ObjectMapper().writeValueAsString(objToSave),
                    path
            );
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public <T> Optional<T> loadBackup(T type, String fileName) {
        Path path = Path.of(backupFolder, fileName);
        try {
            return Optional.ofNullable(
                    new ObjectMapper().readValue(
                            fIleService.read(path).orElse(""),
                            new TypeReference<>() {
                            }
                    )
            );
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }

    @Override
    public <T> Optional<T> downloadBackup(T type, MultipartFile file, String fileName) {
        Path path = Path.of(backupFolder, fileName);
        return fIleService.download(file, path) ? loadBackup(type, fileName) : Optional.empty();
    }
}
