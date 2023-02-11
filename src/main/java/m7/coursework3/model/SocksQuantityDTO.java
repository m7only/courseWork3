package m7.coursework3.model;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Data;

//public record SocksQuantityDTO(@Valid @NotNull Socks socks,
//                               @NotNull @PositiveOrZero Integer quantity) {
//}
@Data
public class SocksQuantityDTO {
    @Valid @NotNull Socks socks;
    @NotNull @PositiveOrZero Integer quantity;
}