package co.edu.uniquindio.poo.app.model;

import java.time.LocalDate;
import java.util.ArrayList;

public record Factura(String codigo, LocalDate fecha, double total,
                      EstadoFactura estadoFactura, MetodoPago metodoPago, Cliente cliente,
                      ArrayList<DetalleFactura> listaDetallesFactura, Tienda ownedByTienda) {
    public boolean tieneIphone() {
        for (DetalleFactura detalle : this.listaDetallesFactura) {
            if (detalle.esIphone()) {
                return true;
            }
        }
        return false;
    }
    public boolean esNombreClienteYTieneIphone(String nombreCliente) {
        return this.cliente.tieneNombre(nombreCliente) && tieneIphone();
    }
}

