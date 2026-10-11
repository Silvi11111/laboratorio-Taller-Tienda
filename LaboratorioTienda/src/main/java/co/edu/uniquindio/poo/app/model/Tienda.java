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
        List<Cliente> listaClientes = new ArrayList<>();
        LocalDate fechaConsultada = LocalDate.of(2026, 10, 7);

        for (Factura facturaAux : listaFacturas) {
            if (facturaAux.fecha().isEqual(fechaConsultada)) {
                listaClientes.add(facturaAux.cliente());
            }
        }
        return listaClientes;
    }

    //4. obtener las facturas que tengan un cliente donde su nombre empieze por R
    public List<Factura> buscarClienteConletraR() {
        List<Factura> listaFactura = new ArrayList<>();
        for (Factura facturaAux : listaFacturas) {
            String nombre = facturaAux.cliente().getNombreCompleto();
            if (nombre.charAt(0) == 'R') {
                listaFactura.add(facturaAux);
            }
        }

        return listaFactura;
    }

    //punto 5:Obtener las facturas donde se haya comprado un celular de marca Iphone 16 pro max
    public List<Factura> obtenerFacturaCelular() {
        List<Factura> resultado = new ArrayList<>();

        for (Factura factura : this.listaFacturas) {
            if (factura.tieneIphone()) {
                resultado.add(factura);
            }
        }
        return resultado;
    }

    //punto 6:Obtener las facturas que tenga un cliente donde su nombre sea juan y haya comprado un celular de marca Iphone 16 pro max
    public List<Factura> obtenerFacturaCelularNombre() {
        List<Factura> resultado = new ArrayList<>();

        for (Factura factura : this.listaFacturas) {
            if (factura.esNombreClienteYTieneIphone(factura.cliente().getNombreCompleto())) {
                resultado.add(factura);
            }
        }
        return resultado;
    }

    // Punto 7: Implementar un método que reciba una categoría  y retorne todos los productos registrados que pertenezcan a ella.
    public List<Producto> obtenerProductosCategoria() {
        List<Producto> filtrados = new ArrayList<>();

        for (Producto producto : listaProductos.values()) {
            if (producto.getCategoria() == Categoria.ACCESORIOS || producto.getCategoria() == Categoria.CELULARES || producto.getCategoria() == Categoria.COMPONENTES || producto.getCategoria() == Categoria.COMPUTADORES || producto.getCategoria() == Categoria.VIDEO_JUEGOS) {
                filtrados.add(producto);
            }
        }

        return filtrados;
    }

    //punto 8 : Implementar un método que reciba un precio mínimo y un precio máximo, y retorne los productos cuyo precio se encuentre dentro de ese rango, incluyendo ambos límites.
public List<Producto> rangoPrecioListaProductos(float rangoMinimo, float rangoMaximo) {
    List<Producto> productosFiltrados = new ArrayList<>();

    for (Producto producto : listaProductos.values()) {
        if (producto.getValor() >= rangoMinimo && producto.getValor() <= rangoMaximo) {
            productosFiltrados.add(producto);
        }
    }

    return productosFiltrados;
}
    //punto 9: Implementar un método que retorne todos los productos registrados en la tienda, ordenados de menor a mayor según su precio.
    public List<Producto> ordenarProductosMenorMayor() {
        List<Producto> productosOrdenados = new ArrayList<>();

        for (Producto producto : listaProductos.values()) {
            for (int i = 0; i < productosOrdenados.size(); i++) {
                for (int j = i + 1; j < productosOrdenados.size(); j++) {

                    if (productosOrdenados.get(i).getValor() > productosOrdenados.get(j).getValor()) {
                        Producto temp = productosOrdenados.get(i);
                        productosOrdenados.set(i, productosOrdenados.get(j));
                        productosOrdenados.set(j, temp);
                    }
                }
            }

            return productosOrdenados;
        }
    }
    //punto 10: Implementar un método que identifique el producto con el precio más alto de la tienda.
    // Si no existen productos registrados, el método debe retornar un Optional vacío.
    public Optional<Producto> obtenerProductoPrecioMasAlto() {
        if (listaProductos == null || listaProductos.isEmpty()) {
            return Optional.empty();
        }
        Producto productoMasAlto = listaProductos.get(0);

        for (Producto producto : listaProductos.values()) {
            if (producto.getValor() > productoMasAlto.getValor()) {
                productoMasAlto = producto;
            }
        }
        return Optional.of(productoMasAlto);
    }
}

    //Punto 11: Implementar un método que reciba el nombre de una ciudad y retorne
    // todos los clientes que residan en ella.
    // La búsqueda debe realizarse sin diferenciar entre mayúsculas y minúsculas.
}


