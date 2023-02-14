package m7.coursework3.model;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Socks {
    private @NotNull Color color;
    private @NotNull Size size;
    private @NotNull @Min(0) @Max(100) int cottonPart;

    //для десериализации по типовому toString
    public Socks(String serialized) {
        Pattern pattern = Pattern.compile("(?i)color=(\\w*), size=(\\w*), cottonPart=(\\w*)");
        Matcher matcher = pattern.matcher(serialized);
        if (matcher.find()) {
            this.color = Color.valueOf(matcher.group(1));
            this.size = Size.valueOf(matcher.group(2));
            this.cottonPart = Integer.parseInt(matcher.group(3));
        } else {
            throw new IllegalArgumentException();
        }
    }
}