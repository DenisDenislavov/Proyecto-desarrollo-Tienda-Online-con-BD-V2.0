package ProyectoTienda.Repositorios;

import ProyectoTienda.DTOs.Historial;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CompraRepository extends JpaRepository<Historial, Integer> {
    Optional<Historial> findById(Integer id);
}

