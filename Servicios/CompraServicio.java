package ProyectoTienda.Servicios;


import ProyectoTienda.DTOs.Cliente;
import ProyectoTienda.DTOs.Historial;
import ProyectoTienda.DTOs.Producto;
import ProyectoTienda.Excepciones.RecursoNoEncontradoException;
import ProyectoTienda.Excepciones.ValidacionException;
import ProyectoTienda.Repositorios.ClienteRepository;
import ProyectoTienda.Repositorios.CompraRepository;
import ProyectoTienda.Repositorios.ProductoRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class CompraServicio {

    private final CompraRepository compraRepository;
    private final ProductoRepository productoRepository;
    private final ClienteRepository clienteRepository;

    public CompraServicio(CompraRepository compraRepository, ProductoRepository productoRepository, ClienteRepository clienteRepository) {
        this.compraRepository = compraRepository;
        this.productoRepository = productoRepository;
        this.clienteRepository = clienteRepository;
    }

    public List<Historial> mostrarTodasCompras() {
        return compraRepository.findAll();
    }

    public Optional<Historial> obtenerHistorialPorId(Integer historialId) {
        return compraRepository.findById(historialId);
    }

    public Historial realizarCompra(Historial historial) {

        Cliente cliente = clienteRepository.findById(historial.getCliente().getId())
                .orElseThrow(() -> new RecursoNoEncontradoException("Cliente no existe"));

        Producto producto = productoRepository.findById(historial.getProducto().getId())
                .orElseThrow(() -> new RecursoNoEncontradoException("Producto no existe"));

        if (producto.getStock() < historial.getCantidad()) {
            throw new ValidacionException("Stock insuficiente");
        }

        producto.setStock(producto.getStock() - historial.getCantidad());
        productoRepository.save(producto);

        // Asignar cliente y producto al historial para asegurar integridad
        historial.setCliente(cliente);
        historial.setProducto(producto);

        return compraRepository.save(historial);
    }

    public void realizarDevolucion(Integer historialId) {
        Historial historial = compraRepository.findById(historialId)
                .orElseThrow(() -> new IllegalArgumentException("Historial no encontrado"));

        // Para verificar si ya fue devuelto
        if ("Devolución".equalsIgnoreCase(historial.getTipo())) {
            throw new RuntimeException("Este producto ya ha sido devuelto");
        }

        if (LocalDate.now().isAfter(historial.getFechaCompra().plusDays(30))) {
            throw new ValidacionException("No se puede devolver después de 30 días");
        }

        // Si se devuelve el producto actualizar el tipo a "Devolución"
        historial.setTipo("Devolución");

        Producto producto = historial.getProducto();
        producto.setStock(producto.getStock() + historial.getCantidad());
        productoRepository.save(producto);
    }

    public Historial actualizarCompra(Historial historial) {
        return compraRepository.save(historial);
    }

    public void eliminarCompra(Integer id) {
        compraRepository.deleteById(id);
    }


}
