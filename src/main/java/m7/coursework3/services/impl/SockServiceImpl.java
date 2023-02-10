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

import java.nio.file.Path;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

@Service
@Validated
public class SockServiceImpl implements SockService {

    private final BackupService backupService;
    @Value("${warehouse.backup.file.name}")
    String warehouseFileName;
    @Value("${transactions.backup.file.name}")
    String transactionsFileName;
    private final Map<Socks, Integer> socksWarehouse = new HashMap<>() {{
        put(new Socks(Color.BLACK, Size.L, 90), 100);
        put(new Socks(Color.BLACK, Size.L, 95), 99);
        put(new Socks(Color.WHITE, Size.L, 90), 100);
    }};
    private final List<SocksTransactions> socksTransactions = new LinkedList<>();

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
                socksQuantityDTO.socks(),
                socksWarehouse.getOrDefault(socksQuantityDTO.socks(), 0) + socksQuantityDTO.quantity()
        );
        addSocksTransaction(
                TransactionType.ACCEPT,
                socksQuantityDTO.socks(),
                socksQuantityDTO.quantity()
        );
        return socksQuantityDTO.socks();
    }

    @Override
    public SocksQuantityDTO releaseSocks(@Valid SocksQuantityDTO socksQuantityDTO,
                                         TransactionType transactionType) {
        if (!socksWarehouse.containsKey(socksQuantityDTO.socks())) {
            throw new SocksNotExistException("No socks at warehouse.");
        }
        //SOCKS_WAREHOUSE.put(socks, SOCKS_WAREHOUSE.get(socks) > quantity ? SOCKS_WAREHOUSE.get(socks) - quantity : 0);
        socksWarehouse.put(
                socksQuantityDTO.socks(),
                socksWarehouse.get(socksQuantityDTO.socks()) - socksQuantityDTO.quantity()
        );
        addSocksTransaction(transactionType, socksQuantityDTO.socks(), socksQuantityDTO.quantity());
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
                        entry.getKey().cottonPart() >= cottonMin
                                && entry.getKey().size() == Size.getSizeByNum(size)
                                && entry.getKey().color() == Color.valueOf(color.toUpperCase()))
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
                        entry.getKey().cottonPart() <= cottonMax
                                && entry.getKey().size() == Size.getSizeByNum(size)
                                && entry.getKey().color() == Color.valueOf(color.toUpperCase()))
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

    private void addSocksTransaction(TransactionType transactionType, Socks socks, Integer quantity) {
        socksTransactions.add(new SocksTransactions(transactionType, LocalDateTime.now(), socks, quantity));
    }
}
