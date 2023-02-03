package m7.coursework3.services;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import m7.coursework3.exceptions.SocksNotExistException;
import m7.coursework3.model.Socks;

import java.util.Map;


public interface SockService {
    Socks add(@Valid Socks socks, @NotNull @Min(1) Integer quantity); // post, приход

    Socks release(@Valid Socks socks, @NotNull @Min(1) Integer quantity);

    int getQuantityByCottonMin(@NotNull String color, @NotNull Integer size, @NotNull @Min(0) @Max(100) Integer cottonMin); // get, количество по > min

    int getQuantityByCottonMax(@NotNull String color, @NotNull Integer size, @NotNull @Min(0) @Max(100) Integer cottonMin); // get, количество < max

    Socks delete(Socks socks, int quantity) throws SocksNotExistException; // delete, списание

    Map<Socks, Integer> all();
}
