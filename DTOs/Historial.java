package ProyectoTienda.DTOs;

/*
PROBLEMA AL REALIZAR COMPRA (4600 lineas body 200 OK Postman)
Ciclo de Serialización Infinito
Las relaciones bidireccionales entre tus entidades están creando un bucle al serializar los datos:

Cliente tiene un Set<Historial> y Historial tiene una referencia de vuelta a Cliente.
Producto tiene un Set<Historial> y Historial también tiene una referencia a Producto.
Esto genera un ciclo infinito cuando Jackson intenta serializar el objeto para la respuesta (motivo por las 4600 lineas postman)
*/

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.time.LocalDate;

@Entity
public class Historial {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonBackReference(value = "cliente-historial")
    @JoinColumn(name = "cliente_id", nullable = false)
    private Cliente cliente;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonBackReference(value = "producto-historial")
    @JoinColumn(name = "producto_id", nullable = false)
    private Producto producto;

    @NotNull
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Column(name = "fecha_compra", nullable = false)
    private LocalDate fechaCompra;

    @ColumnDefault("1")
    @Column(name = "cantidad")
    private Integer cantidad;

    @Size(max = 100)
    @NotNull
    @Column(name = "tipo", nullable = false, length = 100)
    private String tipo;

    @Size(max = 200)
    @Column(name = "descripcion", length = 200)
    private String descripcion;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public Producto getProducto() {
        return producto;
    }

    public void setProducto(Producto producto) {
        this.producto = producto;
    }

    public LocalDate getFechaCompra() {
        return fechaCompra;
    }

    public void setFechaCompra(LocalDate fechaCompra) {
        this.fechaCompra = fechaCompra;
    }

    public Integer getCantidad() {
        return cantidad;
    }

    public void setCantidad(Integer cantidad) {
        this.cantidad = cantidad;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

}