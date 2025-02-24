package ProyectoTienda.Controladores;

import ProyectoTienda.Servicios.CompraServicio;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/devoluciones")
public class DevolucionController {
    private final CompraServicio historialService;

    public DevolucionController(CompraServicio historialService) {
        this.historialService = historialService;
    }

    @PostMapping("/{historialId}")
    public ResponseEntity<String> realizarDevolucion(@PathVariable Integer historialId) {
        try {
            historialService.realizarDevolucion(historialId);
            return ResponseEntity.ok("Devolución realizada con éxito");
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

}
