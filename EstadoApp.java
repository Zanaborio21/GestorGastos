import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;


public class EstadoApp implements Serializable {
    private double sueldo;
    private List<Gasto> gastos;
    private int mesRegistro;
    private LocalDate fechaCreacion;

    public EstadoApp() {
        this.sueldo = 0.0;
        this.gastos = new ArrayList<>();
        this.fechaCreacion = LocalDate.now();
    }

    // Getters y setters
    public double getSueldo() { return sueldo; }
    public void setSueldo(double sueldo) { this.sueldo = sueldo; }

    public List<Gasto> getGastos() { return gastos; }
    public void setGastos(List<Gasto> gastos) { this.gastos = gastos; }

    public int getMesRegistro() { return mesRegistro; }
    public void setMesRegistro(int mesRegistro) { this.mesRegistro = mesRegistro; }

    public void agregarGasto(Gasto gasto) { this.gastos.add(gasto); }

    public LocalDate getFechaCreacion() { return fechaCreacion; }
    public void setFechaCreacion(LocalDate fechaCreacion) { this.fechaCreacion = fechaCreacion; }
}
