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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

    SockService sockService;

    public BackupController(SockService sockService) {
        this.sockService = sockService;
    }

    @Operation(summary = "Выгрузка текущих данных по складу")
    @GetMapping("/warehouse")
    public ResponseEntity<InputStreamResource> getWarehouseBackup() throws IOException {
        Path path = sockService.saveWarehouseBackup();
        return path == null
                ? ResponseEntity.notFound().build()
                : ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .contentLength(Files.size(path))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + path.getFileName().toString())
                .body(new InputStreamResource(Files.newInputStream(path)));
    }

    @Operation(summary = "Выгрузка текущих данных по транзакциям")
    @GetMapping("/transactions")
    public ResponseEntity<InputStreamResource> getTransactionsBackup() throws IOException {
        Path path = sockService.saveTransactionsBackup();
        return path == null
                ? ResponseEntity.notFound().build()
                : ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .contentLength(Files.size(path))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + path.getFileName().toString())
                .body(new InputStreamResource(Files.newInputStream(path)));
    }
}
