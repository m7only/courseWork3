package m7.coursework3.controllers;

import m7.coursework3.model.SocksQuantity;
import m7.coursework3.services.SockService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping({"/api/socks", "/api/socks/"})
public class SocksController {
    private final SockService sockService;

    public SocksController(SockService sockService) {
        this.sockService = sockService;
    }

    @GetMapping
    public ResponseEntity<?> all() {
        return ResponseEntity.ok(sockService.all());
    }

    @PostMapping
    public ResponseEntity<?> addSocks(@RequestBody SocksQuantity socksQuantity) {
        return ResponseEntity.ok(sockService.add(socksQuantity.socks(), socksQuantity.quantity()));
    }

    @PutMapping
    public ResponseEntity<?> releaseSocks(@RequestBody SocksQuantity socksQuantity) {
        return ResponseEntity.ok(sockService.release(socksQuantity.socks(), socksQuantity.quantity()));
    }

    @RequestMapping(method = RequestMethod.GET, params = {"color", "size", "cottonmin"})
    public ResponseEntity<?> getQuantityByCottonMin(@RequestParam(defaultValue = "") String color,
                                                    @RequestParam(defaultValue = "") Integer size,
                                                    @RequestParam(defaultValue = "-1", name = "cottonmin") Integer cottonMin) {
        return ResponseEntity.ok(sockService.getQuantityByCottonMin(color, size, cottonMin));
    }

    @RequestMapping(method = RequestMethod.GET, params = {"color", "size", "cottonmax"})
    public ResponseEntity<?> getQuantityByCottonMax(@RequestParam(defaultValue = "") String color,
                                                    @RequestParam(defaultValue = "") Integer size,
                                                    @RequestParam(defaultValue = "-1", name = "cottonmax") Integer cottonMax) {
        return ResponseEntity.ok(sockService.getQuantityByCottonMax(color, size, cottonMax));
    }

    @DeleteMapping
    public ResponseEntity<?> writeof(@RequestBody SocksQuantity socksQuantity) {
        return ResponseEntity.ok(sockService.delete(socksQuantity.socks(), socksQuantity.quantity()));
    }

    @RequestMapping({"import", "import/"})
    public ResponseEntity<?> importCurrentState() {
        return ResponseEntity.ok("import");
    }

    @RequestMapping({"export", "export/"})
    public ResponseEntity<?> exportCurrentState() {
        return ResponseEntity.ok("export");
    }

    @RequestMapping({"history", "history/"})
    public ResponseEntity<?> history() {
        return ResponseEntity.ok("history");
    }
}
