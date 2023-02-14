package m7.coursework3.services.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.CollectionType;
import com.fasterxml.jackson.databind.type.MapType;
import m7.coursework3.services.BackupService;
import m7.coursework3.services.FileService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Path;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class BackupServiceImpl implements BackupService {
    private final FileService fIleService;
    @Value("${path.to.backup.folder}")
    private String backupFolder;

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
    public <K, V> Optional<Map<K, V>> loadBackup(Class<K> kClass,
                                                 Class<V> vClass,
                                                 String fileName) {
        Path path = Path.of(backupFolder, fileName);
        if (!path.toFile().exists()) {
            return Optional.empty();
        }
        ObjectMapper objectMapper = new ObjectMapper();
        MapType mapType = objectMapper.getTypeFactory().constructMapType(Map.class, kClass, vClass);
        try {
            return Optional.ofNullable(
                    objectMapper.readValue(fIleService.read(path).orElse(""), mapType)
            );
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }

    @Override
    public <T> Optional<List<T>> loadBackup(Class<T> kClass, String fileName) {
        Path path = Path.of(backupFolder, fileName);
        if (!path.toFile().exists()) {
            return Optional.empty();
        }
        ObjectMapper objectMapper = new ObjectMapper();
        CollectionType collectionType = objectMapper.getTypeFactory().constructCollectionType(LinkedList.class, kClass);
        try {
            return Optional.ofNullable(
                    objectMapper.readValue(fIleService.read(path).orElse(""), collectionType)
            );
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }

    @Override
    public <K, V> Optional<Map<K, V>> uploadBackupFile(Class<K> kClass,
                                                       Class<V> vClass,
                                                       MultipartFile file,
                                                       String fileName) {
        Path path = Path.of(backupFolder, fileName);
        return fIleService.upload(file, path) ? loadBackup(kClass, vClass, fileName) : Optional.empty();
    }

    @Override
    public <T> Optional<List<T>> uploadBackupFile(Class<T> kClass,
                                                  MultipartFile file,
                                                  String fileName) {
        Path path = Path.of(backupFolder, fileName);
        return fIleService.upload(file, path) ? loadBackup(kClass, fileName) : Optional.empty();
    }
}
