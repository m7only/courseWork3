package m7.coursework3.model;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public record Socks(@NotNull Color color,
                    @NotNull Size size,
                    @NotNull @Min(0) @Max(100) int cottonPart) {

}
