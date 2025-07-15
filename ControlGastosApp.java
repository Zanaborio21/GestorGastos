import java.awt.*;
import java.io.*;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.List;
import java.util.stream.Collectors;
import javax.swing.*;

public class ControlGastosApp {

    private EstadoApp estado;
    private final String archivoDatos = "gastos.dat";
    private JFrame frame;
    private JLabel sueldoInicialLabel, gastosTotalesLabel, saldoRestanteLabel;
    private JTextField sueldoInput, gastoNombreInput, gastoCantidadInput, gastoPrecioUnitarioInput;
    private JRadioButton radioUnico, radioCompraRecurrente;
    private DefaultListModel<Gasto> listaGastosModel;
    private JList<Gasto> listaGastosUI;
    private DefaultListModel<String> listaCompraModel;
    private JRadioButton radioPagoRecurrente;
    private JLabel fechaLabel;

    public ControlGastosApp() {
        cargarEstado();
        crearYMostrarGUI();
        actualizarUI();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(ControlGastosApp::new);
    }

    private void cargarEstado() {
        File f = new File(archivoDatos);
        if (f.exists()) {
            try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(f))) {
                estado = (EstadoApp) ois.readObject();

                int mesActual = Calendar.getInstance().get(Calendar.MONTH);
                if (estado.getMesRegistro() != mesActual) {
                    JOptionPane.showMessageDialog(null,
                            "¡Bienvenido a un nuevo mes!\nSe han conservado tus compras y pagos fijos del mes anterior.",
                            "Nuevo Mes", JOptionPane.INFORMATION_MESSAGE);

                    List<Gasto> gastosRecurrentes = estado.getGastos().stream()
                            .filter(gasto -> gasto.getTipo() != TipoGasto.UNICO)
                            .collect(Collectors.toList());

                    estado = new EstadoApp();
                    estado.setGastos(gastosRecurrentes);
                }

            } catch (IOException | ClassNotFoundException e) {
                estado = new EstadoApp();
            }
        } else {
            estado = new EstadoApp();
        }
    }

    private void guardarEstado() {
        estado.setMesRegistro(Calendar.getInstance().get(Calendar.MONTH));
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(archivoDatos))) {
            oos.writeObject(estado);
        } catch (IOException e) {
            JOptionPane.showMessageDialog(frame, "No se pudieron guardar los datos.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void crearYMostrarGUI() {
        frame = new JFrame("Gestión de Gastos Mensuales");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(950, 720);
        frame.setLayout(new BorderLayout(10, 10));

        JPanel panelSuperior = new JPanel(new GridLayout(1, 2, 10, 0));

        JPanel panelSueldo = new JPanel(new FlowLayout(FlowLayout.LEFT));
        panelSueldo.setBorder(BorderFactory.createTitledBorder("Configuración Mensual"));
        panelSueldo.add(new JLabel("Sueldo del Mes:"));
        sueldoInput = new JTextField(10);
        panelSueldo.add(sueldoInput);
        JButton guardarSueldoBtn = new JButton("Guardar");
        guardarSueldoBtn.addActionListener(e -> guardarSueldo());
        panelSueldo.add(guardarSueldoBtn);

        JPanel panelResumen = new JPanel(new GridLayout(4, 1));
        panelResumen.setBorder(BorderFactory.createTitledBorder("Resumen Financiero"));
        sueldoInicialLabel = new JLabel("Sueldo Inicial: S/ 0.00");
        gastosTotalesLabel = new JLabel("Gastos Totales: S/ 0.00");
        saldoRestanteLabel = new JLabel("Saldo Restante: S/ 0.00");
        saldoRestanteLabel.setFont(new Font("Arial", Font.BOLD, 14));
        fechaLabel = new JLabel("Fecha de creación: --");
        panelResumen.add(sueldoInicialLabel);
        panelResumen.add(gastosTotalesLabel);
        panelResumen.add(saldoRestanteLabel);
        panelResumen.add(fechaLabel);

        panelSuperior.add(panelSueldo);
        panelSuperior.add(panelResumen);

        JSplitPane panelCentral = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
        panelCentral.setResizeWeight(0.6);

        JPanel panelListaGastos = new JPanel(new BorderLayout());
        panelListaGastos.setBorder(BorderFactory.createTitledBorder("Lista de Gastos del Mes"));
        listaGastosModel = new DefaultListModel<>();
        listaGastosUI = new JList<>(listaGastosModel);
        panelListaGastos.add(new JScrollPane(listaGastosUI), BorderLayout.CENTER);

        JPanel panelListaCompra = new JPanel(new BorderLayout());
        panelListaCompra.setBorder(BorderFactory.createTitledBorder("🛒 Lista de Compras"));
        listaCompraModel = new DefaultListModel<>();
        JList<String> listaCompraUI = new JList<>(listaCompraModel);
        panelListaCompra.add(new JScrollPane(listaCompraUI), BorderLayout.CENTER);

        JPanel panelBotonesCompra = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JButton generarListaBtn = new JButton("Generar");
        generarListaBtn.addActionListener(e -> generarListaCompra());
        JButton limpiarListaBtn = new JButton("Limpiar");
        limpiarListaBtn.addActionListener(e -> listaCompraModel.clear());
        panelBotonesCompra.add(generarListaBtn);
        panelBotonesCompra.add(limpiarListaBtn);
        panelListaCompra.add(panelBotonesCompra, BorderLayout.SOUTH);

        panelCentral.setLeftComponent(panelListaGastos);
        panelCentral.setRightComponent(panelListaCompra);

        JPanel panelAnadirGasto = new JPanel();
        panelAnadirGasto.setLayout(new BoxLayout(panelAnadirGasto, BoxLayout.Y_AXIS));
        panelAnadirGasto.setBorder(BorderFactory.createTitledBorder("Añadir Gasto"));

        gastoNombreInput = new JTextField();
        gastoCantidadInput = new JTextField("1");
        gastoPrecioUnitarioInput = new JTextField();

        radioUnico = new JRadioButton("Gasto Único", true);
        radioCompraRecurrente = new JRadioButton("Compra Mensual");
        radioPagoRecurrente = new JRadioButton("Pago Fijo");

        ButtonGroup tipoGastoGroup = new ButtonGroup();
        tipoGastoGroup.add(radioUnico);
        tipoGastoGroup.add(radioCompraRecurrente);
        tipoGastoGroup.add(radioPagoRecurrente);

        JButton anadirGastoBtn = new JButton("Añadir Gasto");
        anadirGastoBtn.addActionListener(e -> anadirGasto());

        panelAnadirGasto.add(new JLabel("Nombre:"));
        panelAnadirGasto.add(gastoNombreInput);
        panelAnadirGasto.add(new JLabel("Cantidad:"));
        panelAnadirGasto.add(gastoCantidadInput);
        panelAnadirGasto.add(new JLabel("Precio Unitario:"));
        panelAnadirGasto.add(gastoPrecioUnitarioInput);
        panelAnadirGasto.add(Box.createVerticalStrut(10));
        panelAnadirGasto.add(new JLabel("Tipo de Gasto:"));
        panelAnadirGasto.add(radioUnico);
        panelAnadirGasto.add(radioCompraRecurrente);
        panelAnadirGasto.add(radioPagoRecurrente);
        panelAnadirGasto.add(Box.createVerticalStrut(10));
        panelAnadirGasto.add(anadirGastoBtn);

        JPanel panelAcciones = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton eliminarGastoBtn = new JButton("Eliminar Gasto Seleccionado");
        eliminarGastoBtn.addActionListener(e -> eliminarGasto());
        panelAcciones.add(eliminarGastoBtn);

        frame.add(panelSuperior, BorderLayout.NORTH);
        frame.add(panelCentral, BorderLayout.CENTER);
        frame.add(panelAnadirGasto, BorderLayout.EAST);
        frame.add(panelAcciones, BorderLayout.SOUTH);

        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    private void guardarSueldo() {
        try {
            double sueldo = Double.parseDouble(sueldoInput.getText());
            if (sueldo < 0) throw new NumberFormatException();
            estado.setSueldo(sueldo);
            sueldoInput.setText("");
            actualizarUI();
            guardarEstado();
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(frame, "Por favor, ingresa un sueldo válido.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void anadirGasto() {
        try {
            String nombre = gastoNombreInput.getText().trim();
            int cantidad = Integer.parseInt(gastoCantidadInput.getText());
            double precioUnitario = Double.parseDouble(gastoPrecioUnitarioInput.getText());

            if (nombre.isEmpty() || cantidad <= 0 || precioUnitario <= 0) {
                throw new IllegalArgumentException("Todos los campos deben ser positivos y el nombre no puede estar vacío.");
            }

            TipoGasto tipo;
            if (radioCompraRecurrente.isSelected()) {
                tipo = TipoGasto.COMPRA_RECURRENTE;
            } else if (radioPagoRecurrente.isSelected()) {
                tipo = TipoGasto.PAGO_RECURRENTE;
            } else {
                tipo = TipoGasto.UNICO;
            }

            estado.agregarGasto(new Gasto(nombre, cantidad, precioUnitario, tipo));

            gastoNombreInput.setText("");
            gastoCantidadInput.setText("1");
            gastoPrecioUnitarioInput.setText("");
            radioUnico.setSelected(true);

            actualizarUI();
            guardarEstado();
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(frame, "La cantidad y el precio deben ser números válidos.", "Error de Formato", JOptionPane.ERROR_MESSAGE);
        } catch (IllegalArgumentException ex) {
            JOptionPane.showMessageDialog(frame, ex.getMessage(), "Error de Entrada", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void eliminarGasto() {
        Gasto seleccionado = listaGastosUI.getSelectedValue();
        if (seleccionado != null) {
            estado.getGastos().remove(seleccionado);
            actualizarUI();
            guardarEstado();
        } else {
            JOptionPane.showMessageDialog(frame, "Por favor, selecciona un gasto de la lista para eliminar.", "Advertencia", JOptionPane.WARNING_MESSAGE);
        }
    }

    private void generarListaCompra() {
        listaCompraModel.clear();
        List<Gasto> gastosParaComprar = estado.getGastos().stream()
                .filter(Gasto::esParaComprar)
                .collect(Collectors.toList());

        if (gastosParaComprar.isEmpty()) {
            listaCompraModel.addElement("No hay gastos para la lista de compras.");
            mostrarTextoCopiable("No hay gastos para la lista de compras.");
        } else {
            StringBuilder sb = new StringBuilder();
            for (Gasto g : gastosParaComprar) {
                String item = g.paraListaDeCompra();
                listaCompraModel.addElement(item);
                sb.append(item).append("\n");
            }
            mostrarTextoCopiable(sb.toString());
        }
    }

    private void mostrarTextoCopiable(String texto) {
        JTextArea textArea = new JTextArea(texto);
        textArea.setEditable(false);
        textArea.setLineWrap(true);
        textArea.setWrapStyleWord(true);
        JScrollPane scrollPane = new JScrollPane(textArea);
        scrollPane.setPreferredSize(new Dimension(400, 300));
        JOptionPane.showMessageDialog(frame, scrollPane, "Lista de Compras para Copiar", JOptionPane.INFORMATION_MESSAGE);
    }

    private void actualizarUI() {
        double gastosTotales = estado.getGastos().stream().mapToDouble(Gasto::getMontoTotal).sum();
        double saldoRestante = estado.getSueldo() - gastosTotales;
        double porcentajeGasto = gastosTotales / estado.getSueldo();

        sueldoInicialLabel.setText(String.format("Sueldo Inicial: S/ %.2f", estado.getSueldo()));
        gastosTotalesLabel.setText(String.format("Gastos Totales: S/ %.2f", gastosTotales));
        saldoRestanteLabel.setText(String.format("Saldo Restante: S/ %.2f", saldoRestante));

        if (porcentajeGasto >= 1.0) {
            saldoRestanteLabel.setForeground(Color.RED);
        } else if (porcentajeGasto >= 0.7) {
            saldoRestanteLabel.setForeground(new Color(255, 140, 0));
        } else {
            saldoRestanteLabel.setForeground(new Color(0, 128, 0));
        }

        
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        fechaLabel.setText("Fecha de creación: " + estado.getFechaCreacion().format(formatter));

        listaGastosModel.clear();
        for (Gasto g : estado.getGastos()) {
            listaGastosModel.addElement(g);
        }
    }
}
