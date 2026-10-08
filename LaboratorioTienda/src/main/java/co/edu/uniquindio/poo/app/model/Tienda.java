package co.edu.uniquindio.poo.app.model;

import java.util.*;

import java.time.LocalDate;

public class Tienda {

    private final String nombre;
    private final String nit;
    private String telefono;

    private final ArrayList<Cliente> listaClientes = new ArrayList<>();
    private final List<Factura> listaFacturas = new LinkedList<>();
    private Map<String, Producto> listaProductos = new HashMap<>();


    public Tienda(String nombre, String nit, String telefono) {
        this.nombre = nombre;
        this.nit = nit;
        this.telefono = telefono;
    }

    // setters y getters

    public String getNombre() {
        return nombre;
    }

    public String getNit() {
        return nit;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String registrarCliente(Cliente cliente) {
        return Optional.ofNullable(buscarCliente(cliente.getDocumentoIdentidad()))
                .map(clienteExistente -> "No se puede registrar, ya existe un cliente con esa informacion registrado anteriormente.")
                .orElseGet(() -> {
                    listaClientes.add(cliente);
                    return "El cliente fue registrado exitosamente";
                });
    }

    // Cambiar if(clienteEncontrado == null){ por un Optional
    // hacer el metodo buscar cliente usando un optional
    public Optional<Cliente> buscarCliente(String documentoIdentidad) {
        return listaClientes.stream()
                .filter(cliente -> cliente.getDocumentoIdentidad().equals(documentoIdentidad))
                .findFirst();
    }

    public Factura buscarFactura(String codigo) {

        for (Factura factura : listaFacturas) {
            if (factura.codigo().equals(codigo)) {
                return factura;
            }
        }
        return null;
    }

    public Optional<Factura> obtenerFactura(String codigo) {
        return listaFacturas.stream().filter(f -> f.codigo().equals(codigo)).findFirst();
    }

    //calcular el valor total de la factura
    public double calcularTotalFactura(Factura factura) {
        double total = 0;

        for (DetalleFactura detalle : factura.listaDetallesFactura()) {
            total += detalle.getSubTotal() * detalle.getCantidadComprada();
        }

        return total;
    }

    //1. Obtener los productos con una cantidad disponible mayor igual a 10
    public List<Producto> obtenerProductoMayorA10() {
        List<Producto> productosMayor10 = new ArrayList<>();

        for (Producto producto : listaProductos.values()) {
            if (producto.getCantidadDisponible() >= 10) {
                productosMayor10.add(producto);
            }
        }

        return productosMayor10;
    }

    //2. Obtener los codigos de los productos con una cantidad disponible mayor igual a 10 y menor a 50
    public List<String> obtenerCodigosProductos() {
        List<String> codigos = new ArrayList<>();

        for (Producto producto : listaProductos.values()) {
            if (producto.getCantidadDisponible() >= 10 && producto.getCantidadDisponible() < 50) {
                codigos.add(producto.getCodigo());
            }
        }

        return codigos;
    }
    //3. obtener la lista de clientes que hayan comprado el 07/10/2026

    public List<Cliente> obtenerListaCliente() {
        List<Cliente> listaClientes=new ArrayList<>();
        LocalDate fechaConsultada=LocalDate.of(2026,10,7);

        for (Factura facturaAux:listaFacturas){
            if (facturaAux.fecha().isEqual(fechaConsultada){
                listaClientes.add(facturaAux.cliente());
            }
        }
        return listaClientes;
    }
}
