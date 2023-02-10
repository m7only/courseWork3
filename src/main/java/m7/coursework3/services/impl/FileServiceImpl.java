package m7.coursework3.services.impl;

import m7.coursework3.services.FileService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Optional;

@Service
public class FileServiceImpl implements FileService {
    @Value("${path.to.backup.folder}")
    String backupFolder;

    @Override
    public Path save(String data, String fileName) {
        Path path = Path.of(backupFolder, fileName);
        try {
            Files.writeString(path, data);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return path;
    }

    @Override
    public Optional<String> read(String fileName) {
        Path path = Path.of(backupFolder, fileName);
        try {
            return Optional.ofNullable(Files.readString(path));
        } catch (IOException e) {
            e.printStackTrace();
            return Optional.empty();
        }
    }

    @Override
    public void clean(String fileName) {
        Path path = Path.of(backupFolder, fileName);
        try {
            Files.deleteIfExists(path);
            Files.createFile(path);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
