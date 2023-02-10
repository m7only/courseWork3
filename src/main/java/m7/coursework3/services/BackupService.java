package m7.coursework3.services;

import java.nio.file.Path;
import java.util.Optional;

public interface BackupService {
    <T> Path saveBackup(T mapToSave, String fileName);

    <T> Optional<T> loadBackup(T map, String fileName);
}
