package m7.coursework3.services.impl;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import m7.coursework3.exceptions.SocksNotExistException;
import m7.coursework3.model.*;
import m7.coursework3.services.BackupService;
import m7.coursework3.services.SockService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Path;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

@Service
@Validated
public class SockServiceImpl implements SockService {
    private final BackupService backupService;
    @Value("${warehouse.backup.file.name}")
    private String warehouseFileName;
    @Value("${transactions.backup.file.name}")
    private String transactionsFileName;
    private Map<Socks, Integer> socksWarehouse = new HashMap<>();
    private List<SocksTransactions> socksTransactions = new LinkedList<>();

    public SockServiceImpl(BackupService backupService) {
        this.backupService = backupService;
    }

    @Override
    public List<Socks> all() {
        return socksWarehouse.keySet().stream().toList();
    }

    @Override
    public Socks add(@Valid SocksQuantityDTO socksQuantityDTO) {
        socksWarehouse.put(
                socksQuantityDTO.getSocks(),
                socksWarehouse.getOrDefault(socksQuantityDTO.getSocks(), 0) + socksQuantityDTO.getQuantity()
        );
        addSocksTransaction(
                TransactionType.ACCEPT,
                socksQuantityDTO.getSocks(),
                socksQuantityDTO.getQuantity()
        );
        return socksQuantityDTO.getSocks();
    }

    @Override
    public SocksQuantityDTO releaseSocks(@Valid SocksQuantityDTO socksQuantityDTO,
                                         TransactionType transactionType) {
        if (!socksWarehouse.containsKey(socksQuantityDTO.getSocks())) {
            throw new SocksNotExistException("No socks at warehouse.");
        }
        socksWarehouse.put(
                socksQuantityDTO.getSocks(),
                socksWarehouse.get(socksQuantityDTO.getSocks()) - socksQuantityDTO.getQuantity()
        );
        addSocksTransaction(transactionType, socksQuantityDTO.getSocks(), socksQuantityDTO.getQuantity());
        return socksQuantityDTO;
    }

    @Override
    public SocksQuantityDTO writeOff(@Valid SocksQuantityDTO socksQuantityDTO,
                                     TransactionType transactionType) {
        return releaseSocks(socksQuantityDTO, transactionType);
    }

    @Override
    public int getQuantityByCottonMin(@NotBlank String color,
                                      @NotNull Integer size,
                                      @NotNull @Min(0) @Max(100) Integer cottonMin) {
        return socksWarehouse.entrySet().stream()
                .filter(entry ->
                        entry.getKey().getCottonPart() >= cottonMin
                                && entry.getKey().getSize() == Size.getSizeByNum(size)
                                && entry.getKey().getColor() == Color.valueOf(color.toUpperCase()))
                .map(Map.Entry::getValue)
                .mapToInt(Integer::intValue)
                .sum();
    }

    @Override
    public int getQuantityByCottonMax(@NotBlank String color,
                                      @NotNull Integer size,
                                      @NotNull @Min(0) @Max(100) Integer cottonMax) {
        return socksWarehouse.entrySet().stream()
                .filter(entry ->
                        entry.getKey().getCottonPart() <= cottonMax
                                && entry.getKey().getSize() == Size.getSizeByNum(size)
                                && entry.getKey().getColor() == Color.valueOf(color.toUpperCase()))
                .map(Map.Entry::getValue)
                .mapToInt(Integer::intValue)
                .sum();
    }

    @Override
    public Path saveWarehouseBackup() {
        return backupService.saveBackup(socksWarehouse, warehouseFileName);
    }

    @Override
    public Path saveTransactionsBackup() {
        return backupService.saveBackup(socksTransactions, transactionsFileName);
    }

    @Override
    public void uploadWarehouseBackup(MultipartFile file) {
        socksWarehouse = backupService.downloadBackup(socksWarehouse, file, warehouseFileName).orElse(socksWarehouse);
    }

    @Override
    public void uploadTransactionsBackup(MultipartFile file) {
        socksTransactions = backupService.downloadBackup(socksTransactions, file, transactionsFileName).orElse(socksTransactions);
    }

    private void addSocksTransaction(TransactionType transactionType, Socks socks, Integer quantity) {
        socksTransactions.add(new SocksTransactions(
                transactionType,
                LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd-MM-yyyy hh:mm:ss")),
                socks,
                quantity
        ));
    }
}
