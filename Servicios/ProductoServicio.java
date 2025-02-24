package ProyectoTienda.Servicios;

import ProyectoTienda.DTOs.Producto;
import ProyectoTienda.Repositorios.ProductoRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductoServicio {

    private final ProductoRepository productoRepository;

    public ProductoServicio(ProductoRepository productoRepository) {
        this.productoRepository = productoRepository;
    }

    public List<Producto> mostrarTodosProductos() {
        return productoRepository.findAll();
    }

    public Optional<Producto> mostrarProductoPorId(Integer id) {
        return productoRepository.findById(id);
    }

    /*public Optional<Producto> mostrarProductoPorNombre(String nombre) {
        return productoRepository.findByNombre(nombre);
    }*/

    public Producto guardarProducto(Producto producto) {
        if (productoRepository.existsByNombre(producto.getNombre())) {
            throw new IllegalArgumentException("El nombre del producto ya existe, introduce otro nombre");
        }
        return productoRepository.save(producto);
    }

    public void eliminarProductoPorId(Integer id) {
        productoRepository.deleteById(id);
    }
}
