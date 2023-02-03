package m7.coursework3.model;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public record SocksQuantity(@NotNull Socks socks, @NotNull @Min(0) Integer quantity) {
}
