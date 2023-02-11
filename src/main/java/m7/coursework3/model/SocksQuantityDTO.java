package m7.coursework3.model;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Data;

@Data
public class SocksQuantityDTO {
    private @Valid @NotNull Socks socks;
    private @NotNull @PositiveOrZero Integer quantity;
}