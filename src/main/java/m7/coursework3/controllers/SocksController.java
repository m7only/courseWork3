package m7.coursework3.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import m7.coursework3.model.Socks;
import m7.coursework3.model.SocksQuantityDTO;
import m7.coursework3.model.TransactionType;
import m7.coursework3.services.SockService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/socks")
@Tag(name = "API носочного склада", description = "CRUD носков. Показываем. Покупаем, продаем, списываем.")
@ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Результат запроса получен"),
        @ApiResponse(responseCode = "400", description = "Невалидные входные данные"),
        @ApiResponse(responseCode = "404", description = "Результат запроса равен NULL"),
        @ApiResponse(responseCode = "500", description = "Внутренняя ошибка")
})
public class SocksController {
    private final SockService sockService;

    public SocksController(SockService sockService) {
        this.sockService = sockService;
    }

    @Operation(summary = "Возвращает список всех носков")
    @GetMapping
    public ResponseEntity<List<Socks>> all() {
        return ResponseEntity.ok(sockService.all());
    }

    @Operation(summary = "Добавляет носки")
    @PostMapping
    public ResponseEntity<Socks> addSocks(@RequestBody SocksQuantityDTO socksQuantityDTO) {
        return ResponseEntity.ok(sockService.add(socksQuantityDTO));
    }

    @Operation(summary = "Продает носки")
    @PutMapping
    public ResponseEntity<SocksQuantityDTO> releaseSocks(@RequestBody SocksQuantityDTO socksQuantityDTO) {
        return ResponseEntity.ok(sockService.releaseSocks(socksQuantityDTO, TransactionType.RELEASE));
    }

    @Operation(summary = "Считает количество носков, содержание хлопка которых больше указанного")
    @GetMapping(value = "bymin/", params = {"color", "size", "cottonmin"})
    public ResponseEntity<Integer> getQuantityByCottonMin(@RequestParam(defaultValue = "") String color,
                                                          @RequestParam(defaultValue = "") Integer size,
                                                          @RequestParam(defaultValue = "-1", name = "cottonmin") Integer cottonMin) {
        return ResponseEntity.ok(sockService.getQuantityByCottonMin(color, size, cottonMin));
    }

    @Operation(summary = "Считает количество носков, содержание хлопка которых меньше указанного")
    @GetMapping(value = "bymax/", params = {"color", "size", "cottonmax"})
    public ResponseEntity<Integer> getQuantityByCottonMax(@RequestParam(defaultValue = "") String color,
                                                          @RequestParam(defaultValue = "") Integer size,
                                                          @RequestParam(defaultValue = "-1", name = "cottonmax") Integer cottonMax) {

        return ResponseEntity.ok(sockService.getQuantityByCottonMax(color, size, cottonMax));
    }

    @Operation(summary = "Списание указанного количества носков")
    @DeleteMapping
    public ResponseEntity<SocksQuantityDTO> writeOff(@RequestBody SocksQuantityDTO socksQuantityDTO) {
        return ResponseEntity.ok(sockService.writeOff(socksQuantityDTO, TransactionType.WRITE_OF));
    }
}
