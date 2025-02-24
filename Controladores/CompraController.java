package ProyectoTienda.Controladores;

import ProyectoTienda.DTOs.Historial;
import ProyectoTienda.Servicios.CompraServicio;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;


@RestController
@RequestMapping("/historial")
@CacheConfig(cacheNames = {"compras"})
public class CompraController {

    private final CompraServicio compraServicio;

    public CompraController(CompraServicio historialService) {
        this.compraServicio = historialService;
    }

    @GetMapping
    public List<Historial> mostrarCompras() {
        return compraServicio.mostrarTodasCompras();
    }

    @GetMapping("/{id}")
    @Cacheable
    public Optional<Historial> mostrarCompraPorId(@PathVariable Integer id) {
        return compraServicio.obtenerHistorialPorId(id);
    }

    @PostMapping("/comprar")
    public ResponseEntity<Historial> realizarCompra(@RequestBody Historial historial) {
        try {
            Historial nuevaCompra = compraServicio.realizarCompra(historial);
            return ResponseEntity.ok(nuevaCompra);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(null);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Historial> actualizarCompra(@PathVariable Integer id, @RequestBody Historial historial) {
        return compraServicio.obtenerHistorialPorId(id)
                .map(compra -> {
                    compra.setCliente(historial.getCliente());
                    compra.setProducto(historial.getProducto());
                    compra.setFechaCompra(historial.getFechaCompra());
                    compra.setCantidad(historial.getCantidad());
                    compra.setTipo(historial.getTipo());
                    compra.setDescripcion(historial.getDescripcion());
                    return ResponseEntity.ok(compraServicio.actualizarCompra(compra));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> eliminarCompra(@PathVariable Integer id) {
        Optional<Historial> historial = compraServicio.obtenerHistorialPorId(id);

        if (historial.isPresent()) {
            compraServicio.eliminarCompra(id);
            return ResponseEntity.ok("La compra con ID " + id + " ha sido eliminada exitosamente.");
        } else {
            return ResponseEntity.status(404).body("La compra con ID " + id + " no existe.");
        }
    }

}
