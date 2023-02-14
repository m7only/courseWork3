package m7.coursework3.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import m7.coursework3.services.SockService;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

@RestController
@RequestMapping("/api/socks/backup")
@Tag(name = "API backup носочного склада", description = "Сгрузить данные склада, транзакции. Нагрузить данные склада, транзакции.")
@ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Результат запроса получен"),
        @ApiResponse(responseCode = "400", description = "Невалидные входные данные"),
        @ApiResponse(responseCode = "404", description = "Результат запроса равен NULL"),
        @ApiResponse(responseCode = "500", description = "Внутренняя ошибка")
})
public class BackupController {

    private final SockService sockService;

    public BackupController(SockService sockService) {
        this.sockService = sockService;
    }

    @Operation(summary = "Выгрузка текущих данных по складу")
    @GetMapping("/warehouse")
    public ResponseEntity<InputStreamResource> getWarehouseBackup() throws IOException {
        Path path = sockService.saveWarehouseBackup();
        return path != null
                ? ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .contentLength(Files.size(path))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + path.getFileName().toString())
                .body(new InputStreamResource(Files.newInputStream(path)))
                : ResponseEntity.notFound().build();
    }

    @Operation(summary = "Загрузка данных по складу")
    @PostMapping(value = "/warehouse", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Void> uploadWarehouseBackup(@RequestParam MultipartFile file) {
        sockService.uploadWarehouseBackup(file);
        return ResponseEntity.ok().build();

    }

    @Operation(summary = "Выгрузка текущих данных по транзакциям")
    @GetMapping("/transactions")
    public ResponseEntity<InputStreamResource> getTransactionsBackup() throws IOException {
        Path path = sockService.saveTransactionsBackup();
        return path != null
                ? ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .contentLength(Files.size(path))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + path.getFileName().toString())
                .body(new InputStreamResource(Files.newInputStream(path)))
                : ResponseEntity.notFound().build();
    }

    @Operation(summary = "Загрузка и замена данных по транзакциям")
    @PostMapping(value = "/transactions", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Void> uploadTransactionsBackup(@RequestParam MultipartFile file) {
        sockService.uploadTransactionsBackup(file);
        return ResponseEntity.ok().build();

    }

    @Operation(summary = "Загрузка и добавление данных по транзакциям")
    @PutMapping(value = "/transactions", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Void> uploadTransactions(@RequestParam MultipartFile file) {
        sockService.uploadTransactions(file);
        return ResponseEntity.ok().build();

    }
}
