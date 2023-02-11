package m7.coursework3.model;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class SocksTransactions {
    private @NotNull TransactionType transactionType;
    private @NotNull String transactionDateTime;
    private @NotNull Socks socks;
    private @NotNull Integer quantity;
}