package ProyectoTienda.Controladores;

import ProyectoTienda.DTOs.Producto;
import ProyectoTienda.Servicios.ProductoServicio;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/productos")
@CacheConfig(cacheNames = {"productos"})
public class ProductoController {

    private final ProductoServicio productoService;

    public ProductoController(ProductoServicio productoService) {
        this.productoService = productoService;
    }

    @GetMapping
    public List<Producto> mostrarProductos() {
        return productoService.mostrarTodosProductos();
    }

    @GetMapping("/{id}")
    @Cacheable
    public Optional<Producto> mostrarProductoPorId(@PathVariable Integer id) {
        return productoService.mostrarProductoPorId(id);
    }

    /*@GetMapping("/{nombre}")
    @Cacheable
    public Optional<Producto> mostrarProductoPorNombre(@PathVariable String nombre) {
        return productoService.mostrarProductoPorNombre(nombre);
    }*/

    @PostMapping
    public Producto crearProducto(@RequestBody Producto producto) {
        return productoService.guardarProducto(producto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Producto> actualizarProducto(@PathVariable Integer id, @RequestBody Producto producto) {
        return productoService.mostrarProductoPorId(id)
                .map(productoActualizar -> {
                    productoActualizar.setNombre(producto.getNombre());
                    productoActualizar.setDescripcion(producto.getDescripcion());
                    productoActualizar.setPrecio(producto.getPrecio());
                    productoActualizar.setStock(producto.getStock());
                    return ResponseEntity.ok(productoService.guardarProducto(productoActualizar));
                }).orElse(ResponseEntity.notFound().build());

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> eliminarProducto(@PathVariable Integer id) {
        Optional<Producto> producto = productoService.mostrarProductoPorId(id);

        if (producto.isPresent()) {
            productoService.eliminarProductoPorId(id);
            return ResponseEntity.ok("El producto con ID " + id + " ha sido eliminado exitosamente.");
        } else {
            return ResponseEntity.status(404).body("El producto con ID " + id + " no existe.");
        }
    }
}
