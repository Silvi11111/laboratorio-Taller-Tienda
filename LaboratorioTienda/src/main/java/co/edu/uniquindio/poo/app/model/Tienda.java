package co.edu.uniquindio.poo.app.model;

import java.util.*;

public class Tienda {

    private final String nombre;
    private final String nit;
    private String telefono;

    private final ArrayList<Cliente> listaClientes = new ArrayList<>();
    private final List<Factura> listaFacturas = new LinkedList<>();
    private Map<String,Producto> listaProductos = new HashMap<>();


    public Tienda(String nombre, String nit,String telefono){
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
    public Factura buscarFactura(String codigo){

        for (Factura factura : listaFacturas){
            if(factura.codigo().equals(codigo)){
                return factura;
            }
        }
        return null;
    }

    public Optional<Factura> obtenerFactura(String codigo) {
        return listaFacturas.stream().filter(f -> f.codigo().equals(codigo)).findFirst();
    }

    //1. Obtener los productos con una cantidad disponible mayor igual a 10
    public List<String> obtenerProductoDisponible() {
        List<String> productos = new ArrayList<>();

        for (Producto producto : listaProductos.values()) {
            if (producto.getCantidadDisponible() >= 10) {
                productos.add(producto.getNombre());
            }
        }

        return productos;
    }
}
