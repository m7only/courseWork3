package m7.coursework3.services.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import m7.coursework3.services.BackupService;
import m7.coursework3.services.FileService;
import org.springframework.stereotype.Service;

import java.nio.file.Path;
import java.util.Optional;

@Service
public class BackupServiceImpl implements BackupService {
    FileService fIleService;

    public BackupServiceImpl(FileService fIleService) {
        this.fIleService = fIleService;
    }

    @Override
    public <T> Path saveBackup(T objToSave, String fileName) {
        try {
            return fIleService.save(
                    new ObjectMapper().writeValueAsString(objToSave),
                    fileName
            );
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public <T> Optional<T> loadBackup(T type, String fileName) {
        try {
            return Optional.ofNullable(
                    new ObjectMapper().readValue(
                            fIleService.read(fileName).orElse(""),
                            new TypeReference<>() {
                            }
                    )
            );
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }
}
