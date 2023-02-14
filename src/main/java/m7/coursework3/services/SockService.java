package m7.coursework3.services;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import m7.coursework3.model.Socks;
import m7.coursework3.model.SocksQuantityDTO;
import m7.coursework3.model.TransactionType;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Path;
import java.util.List;


public interface SockService {
    List<Socks> all();

    Socks add(@Valid SocksQuantityDTO socksQuantityDTO);

    SocksQuantityDTO releaseSocks(@Valid SocksQuantityDTO socksQuantityDTO,
                                  TransactionType transactionType);

    SocksQuantityDTO writeOff(@Valid SocksQuantityDTO socksQuantityDTO,
                              TransactionType transactionType);

    int getQuantityByCottonMin(@NotBlank String color,
                               @NotNull Integer size,
                               @NotNull @Min(0) @Max(100) Integer cottonMin);

    int getQuantityByCottonMax(@NotBlank String color,
                               @NotNull Integer size,
                               @NotNull @Min(0) @Max(100) Integer cottonMin);

    Path saveWarehouseBackup();

    Path saveTransactionsBackup();

    void uploadWarehouseBackup(MultipartFile file);

    void uploadTransactionsBackup(MultipartFile file);

    void uploadTransactions(MultipartFile file);

    void addSocksTransaction(TransactionType transactionType, Socks socks, Integer quantity);
}
