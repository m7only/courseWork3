package m7.coursework3.model;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SocksTransactions {
    private @NotNull TransactionType transactionType;
    private @NotNull String transactionDateTime;
    private @NotNull Socks socks;
    private @NotNull Integer quantity;
}