import java.io.Serializable;

//clasi
enum TipoGasto {
    UNICO("Único"),
    COMPRA_RECURRENTE("Compra Mensual"), //compras
    PAGO_RECURRENTE("Pago Fijo"); //Servis

    private final String descripcion;

    TipoGasto(String descripcion) {
        this.descripcion = descripcion;
    }

    @Override
    public String toString() {
        return descripcion;
    }
}


public class Gasto implements Serializable {
    private String nombre;
    private double precioUnitario;
    private int cantidad;
    private double montoTotal;
    private TipoGasto tipo;


    public Gasto(String nombre, int cantidad, double precioUnitario, TipoGasto tipo) {
        this.nombre = nombre;
        this.cantidad = cantidad;
        this.precioUnitario = precioUnitario;
        this.tipo = tipo;
        this.montoTotal = cantidad * precioUnitario;
    }

    public String getNombre() { return nombre; }
    public double getMontoTotal() { return montoTotal; }
    public TipoGasto getTipo() { return tipo; }
    public int getCantidad() { return cantidad; }
    public double getPrecioUnitario() { return precioUnitario; }


    public boolean esParaComprar() {
        return tipo == TipoGasto.UNICO || tipo == TipoGasto.COMPRA_RECURRENTE;
    }

    @Override
    public String toString() {
        if (cantidad > 1) {
            return String.format("%s (%d x S/ %.2f) - Total: S/ %.2f [%s]", nombre, cantidad, precioUnitario, montoTotal, tipo);
        }
        return String.format("%s - Total: S/ %.2f [%s]", nombre, montoTotal, tipo);
    }

    public String paraListaDeCompra() {
        return String.format("• %s (%d uds) - S/ %.2f", nombre, cantidad, montoTotal);
    }
}
