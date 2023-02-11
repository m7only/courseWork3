package m7.coursework3.model;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Socks {
    private @NotNull Color color;
    private @NotNull Size size;
    private @NotNull @Min(0) @Max(100) int cottonPart;
}