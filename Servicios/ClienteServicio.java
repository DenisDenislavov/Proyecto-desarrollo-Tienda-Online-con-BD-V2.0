package ProyectoTienda.Servicios;


import ProyectoTienda.DTOs.Cliente;
import ProyectoTienda.Repositorios.ClienteRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
public class ClienteServicio {
    private final ClienteRepository clienteRepository;

    public ClienteServicio(ClienteRepository clienteRepository) {
        this.clienteRepository = clienteRepository;
    }

    public List<Cliente> mostrarTodosClientes() {
        return clienteRepository.findAll();
    }

    public Optional<Cliente> mostrarClientePorId(Integer id) {
        return clienteRepository.findById(id);
    }

    public Cliente guardarCliente(Cliente cliente) {
        return clienteRepository.save(cliente);
    }

    public void eliminarClientePorId(Integer id) {
        clienteRepository.deleteById(id);
    }


}
