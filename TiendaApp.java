package ProyectoTienda;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@EnableCaching
@SpringBootApplication
public class TiendaApp {
    public static void main(String[] args) {
        SpringApplication.run(TiendaApp.class, args);
    }

}
/* CLIENTE JSON
* {
    "nombre": "Denis",
    "apellido": "Denislavov",
    "nickname": "denis_04",
    "password": "Password1234",
    "telefono": "635942007",
    "domicilio": "Avenida de Teresa de Calcuta"
}
*
* PRODUCTO JSON
*  {
    "nombre": "PC Gaming",
    "descripcion": "PC Gaming RTX 4090 i9-9900k 32gb ram",
    "precio": "9999.99",
    "stock": "10"
}
*
* COMPRA JSON
* {
    "cliente": { "id": 2 },
    "producto": { "id": 1 },
    "fechaCompra": "2025-02-22",
    "cantidad": 1,
    "tipo": "Compra",
    "descripcion": "Compra de producto"
}
* */