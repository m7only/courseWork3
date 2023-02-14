package m7.coursework3.services;

import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Path;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface BackupService {
    <T> Path saveBackup(T mapToSave, String fileName);

    <K, V> Optional<Map<K, V>> loadBackup(Class<K> kClass, Class<V> vClass, String fileName);

    <T> Optional<List<T>> loadBackup(Class<T> kClass, String fileName);

    <K, V> Optional<Map<K, V>> uploadBackupFile(Class<K> kClass, Class<V> vClass, MultipartFile file, String fileName);

    <T> Optional<List<T>> uploadBackupFile(Class<T> kClass,
                                           MultipartFile file,
                                           String fileName);
}
