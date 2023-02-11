package m7.coursework3.services.impl;

import m7.coursework3.services.FileService;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Optional;

import static java.nio.file.StandardOpenOption.CREATE_NEW;

@Service
public class FileServiceImpl implements FileService {

    @Override
    public Path save(String data, Path path) {
        try {
            Files.writeString(path, data);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return path;
    }

    @Override
    public Optional<String> read(Path path) {
        try {
            return Optional.ofNullable(Files.readString(path));
        } catch (IOException e) {
            e.printStackTrace();
            return Optional.empty();
        }
    }

    @Override
    public void clean(Path path) {
        try {
            Files.deleteIfExists(path);
            Files.createFile(path);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean download(MultipartFile file, Path path) {
        try {
            Files.deleteIfExists(path);
        } catch (IOException e) {
            e.printStackTrace();
        }
        try (
                InputStream is = file.getInputStream();
                OutputStream os = Files.newOutputStream(path, CREATE_NEW);
                BufferedInputStream bis = new BufferedInputStream(is, 1024);
                BufferedOutputStream bos = new BufferedOutputStream(os, 1024)
        ) {
            bis.transferTo(bos);
            return true;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }
}
