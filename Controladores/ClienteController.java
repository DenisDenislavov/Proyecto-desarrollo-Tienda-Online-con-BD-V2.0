package ProyectoTienda.Controladores;

import ProyectoTienda.DTOs.Cliente;
import ProyectoTienda.Servicios.ClienteServicio;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/clientes")
@CacheConfig(cacheNames = {"clientes"})
public class ClienteController {
    private final ClienteServicio clienteService;

    public ClienteController(ClienteServicio clienteService) {
        this.clienteService = clienteService;
    }

    @GetMapping
    public List<Cliente> mostrarClientes() {
        return clienteService.mostrarTodosClientes();
    }

    @GetMapping("/{id}")
    @Cacheable
    public Optional<Cliente> mostrarClientePorId(@PathVariable Integer id) {
        return clienteService.mostrarClientePorId(id);
    }

    @PostMapping
    public Cliente crearCliente(@RequestBody Cliente cliente) {
        return clienteService.guardarCliente(cliente);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Cliente> actualizarCliente(@PathVariable Integer id, @RequestBody Cliente cliente) {
        Optional<Cliente> clienteExistente = clienteService.mostrarClientePorId(id);

        if (clienteExistente.isPresent()) {
            Cliente c = clienteExistente.get();

            c.setNombre(cliente.getNombre());
            c.setApellido(cliente.getApellido());
            c.setNickname(cliente.getNickname());
            c.setPassword(cliente.getPassword());
            c.setTelefono(cliente.getTelefono());
            c.setDomicilio(cliente.getDomicilio());

            Cliente clienteActualizado = clienteService.actualizarCliente(c);

            return ResponseEntity.ok(clienteActualizado);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> eliminarCliente(@PathVariable Integer id) {
        Optional<Cliente> cliente = clienteService.mostrarClientePorId(id);

        if (cliente.isPresent()) {
            clienteService.eliminarClientePorId(id);
            return ResponseEntity.ok("El cliente con ID " + id + " ha sido eliminado exitosamente.");
        } else {
            return ResponseEntity.status(404).body("El producto con ID " + id + " no existe.");
        }
    }

}

