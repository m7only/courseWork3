package m7.coursework3.model;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class SocksTransactions {
    @NotNull TransactionType transactionType;
    @NotNull String transactionDateTime;
    @NotNull Socks socks;
    @NotNull Integer quantity;
}