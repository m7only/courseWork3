package m7.coursework3.model;

import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

public record SocksTransactions(@NotNull TransactionType transactionType,
                                @NotNull LocalDateTime transactionDateTime,
                                @NotNull Socks socks,
                                @NotNull Integer quantity) {
}
