package m7.coursework3.services.impl;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import m7.coursework3.exceptions.SocksNotExistException;
import m7.coursework3.model.Color;
import m7.coursework3.model.Size;
import m7.coursework3.model.Socks;
import m7.coursework3.services.SockService;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.util.HashMap;
import java.util.Map;

@Service
@Validated
public class SockServiceImpl implements SockService {

    private final Map<Socks, Integer> SOCKS_WAREHOUSE = new HashMap<>() {{
        put(new Socks(Color.BLACK, Size.L, 90), 100);
        put(new Socks(Color.BLACK, Size.L, 95), 99);
        put(new Socks(Color.WHITE, Size.L, 90), 100);
    }};

    @Override
    public Map<Socks, Integer> all() {
        return SOCKS_WAREHOUSE;
    }

    @Override
    public Socks add(@Valid Socks socks, @NotNull @Min(1) Integer quantity) {
        if (!SOCKS_WAREHOUSE.containsKey(socks)) {
            SOCKS_WAREHOUSE.put(socks, 0);
        }
        SOCKS_WAREHOUSE.put(socks, SOCKS_WAREHOUSE.get(socks) + quantity);
        return socks;
    }

    @Override
    public Socks release(@Valid Socks socks, @NotNull @Min(1) Integer quantity) {
        if (!SOCKS_WAREHOUSE.containsKey(socks)) {
            throw new SocksNotExistException("No socks at warehouse.");
        }
        SOCKS_WAREHOUSE.put(socks, SOCKS_WAREHOUSE.get(socks) > quantity ? SOCKS_WAREHOUSE.get(socks) - quantity : 0);
        return socks;
    }

    @Override
    public int getQuantityByCottonMin(@NotNull String color, @NotNull Integer size, @NotNull @Min(0) @Max(100) Integer cottonMin) {
        return SOCKS_WAREHOUSE.entrySet().stream()
                .filter(entry ->
                        entry.getKey().cottonPart() >= cottonMin
                                && entry.getKey().size() == Size.getSizeByNum(size)
                                && entry.getKey().color() == Color.valueOf(color.toUpperCase()))
                .map(Map.Entry::getValue)
                .mapToInt(Integer::intValue)
                .sum();
    }

    @Override
    public int getQuantityByCottonMax(@NotNull String color, @NotNull Integer size, @NotNull @Min(0) @Max(100) Integer cottonMax) {
        return SOCKS_WAREHOUSE.entrySet().stream()
                .filter(entry ->
                        entry.getKey().cottonPart() <= cottonMax
                                && entry.getKey().size() == Size.getSizeByNum(size)
                                && entry.getKey().color() == Color.valueOf(color.toUpperCase()))
                .map(Map.Entry::getValue)
                .mapToInt(Integer::intValue)
                .sum();
    }

    @Override
    public Socks delete(Socks socks, int quantity) throws SocksNotExistException {
        return release(socks, quantity);
    }

}
