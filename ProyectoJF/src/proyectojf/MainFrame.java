/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Singleton.java to edit this template
 */
package proyectojf;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainFrame extends JFrame {
    private JButton agregarFincasButton;

    public MainFrame() {
        this.setTitle("Sistema de Gestión de Fincas");
        this.setSize(400, 300);
        this.setLayout(new BorderLayout());

        agregarFincasButton = new JButton("Agregar Fincas");
        agregarFincasButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int cantidadFincas = pedirCantidadFincas();
                if (cantidadFincas > 0) {
                    Finca[] fincas = new Finca[cantidadFincas];
                    for (int i = 0; i < cantidadFincas; i++) {
                       fincas[i] = crearFinca(i + 1);
                    }
                    mostrarMapa(fincas);
                }
            }
        });

        this.add(agregarFincasButton, BorderLayout.CENTER);
    }

    private int pedirCantidadFincas() {
        int cantidadFincas = -1;
        boolean cantidadValida = false;

        while (!cantidadValida) {
            try {
                cantidadFincas = Integer.parseInt(JOptionPane.showInputDialog("Ingrese la cantidad de fincas:"));
                if (cantidadFincas >= 0) {
                    cantidadValida = true;
                } else {
                    JOptionPane.showMessageDialog(null, "La cantidad de fincas debe ser mayor o igual a cero.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(null, "Error al ingresar la cantidad de fincas. Por favor, ingrese un valor numérico válido.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }

        return cantidadFincas;
    }

    private Finca crearFinca(int numeroFinca) {
        String nombreFinca = JOptionPane.showInputDialog(null, "Ingrese el nombre de la finca " + numeroFinca + ":");
        String ubicacion = JOptionPane.showInputDialog(null, "Ingrese la ubicación de la finca " + nombreFinca + ":");
        String nombreEncargado = JOptionPane.showInputDialog(null, "Ingrese el nombre del encargado de la finca " + nombreFinca + ":");
        int cedulaEncargado = Integer.parseInt(JOptionPane.showInputDialog(null, "Ingrese la cédula del encargado de la finca " + nombreFinca + ":"));
        int telefonoEncargado = Integer.parseInt(JOptionPane.showInputDialog(null, "Ingrese el teléfono del encargado de la finca " + nombreFinca + ":"));
        int cantidadPotreros = Integer.parseInt(JOptionPane.showInputDialog(null, "Ingrese la cantidad de potreros de la finca " + nombreFinca + ":"));
        int tamañoFinca = Integer.parseInt(JOptionPane.showInputDialog(null, "Ingrese el tamaño de la finca " + nombreFinca + ":"));
        int cantidadAnimales = Integer.parseInt(JOptionPane.showInputDialog(null, "Ingrese la cantidad de animales de la finca " + nombreFinca + ":"));

        Potrero[] potreros = new Potrero[cantidadPotreros];
        for (int i = 0; i < cantidadPotreros; i++) {
            potreros[i] = crearPotrero("Potrero " + (i + 1));
        }

        return new Finca(nombreFinca, ubicacion, nombreEncargado, cedulaEncargado, telefonoEncargado,
                cantidadPotreros, tamañoFinca, cantidadAnimales, potreros);
}

    private Potrero crearPotrero(String nombrePotrero) {
        String estado = JOptionPane.showInputDialog(null, "Ingrese el estado del potrero " + nombrePotrero + ":");
        String fechaEntrada = JOptionPane.showInputDialog(null, "Ingrese la fecha de entrada del potrero " + nombrePotrero + ":");
        String fechaSalida = JOptionPane.showInputDialog(null, "Ingrese la fecha de salida del potrero " + nombrePotrero + ":");
        int cantidadAnimales = Integer.parseInt(JOptionPane.showInputDialog(null, "Ingrese la cantidad de animales del potrero " + nombrePotrero + ":"));
        boolean poseeAgua = JOptionPane.showConfirmDialog(null, "El potrero " + nombrePotrero + " posee agua?", "Posee Agua", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION;
        String tipoTerreno = JOptionPane.showInputDialog(null, "Ingrese el tipo de terreno del potrero " + nombrePotrero + ":");

        Ganado[] ganado = new Ganado[cantidadAnimales];
        for (int i = 0; i < cantidadAnimales; i++) {
            ganado[i] = crearGanado("Ganado " + (i + 1));
        }

        return new Potrero(nombrePotrero, estado, fechaEntrada, fechaSalida, cantidadAnimales, poseeAgua, tipoTerreno, ganado);
    }

    private Ganado crearGanado(String nombreGanado) {
        String codigo = JOptionPane.showInputDialog(null, "Ingrese el código del ganado " + nombreGanado + ":");
        String nombre = JOptionPane.showInputDialog(null, "Ingrese el nombre del ganado " + nombreGanado + ":");
        String fechaNacimiento = JOptionPane.showInputDialog(null, "Ingrese la fecha de nacimiento del ganado " + nombreGanado + ":");
        double ultimoPeso = Double.parseDouble(JOptionPane.showInputDialog(null, "Ingrese el último peso registrado del ganado " + nombreGanado + ":"));
        String sexo = JOptionPane.showInputDialog(null, "Ingrese el sexo del ganado " + nombreGanado + ":");
        String raza = JOptionPane.showInputDialog(null, "Ingrese la raza del ganado " + nombreGanado + ":");

        return new Ganado(codigo, nombre, fechaNacimiento, ultimoPeso, sexo, raza);
    }

private void mostrarMapa(Finca[] fincas) {
    StringBuilder mapaHTML = new StringBuilder("<html><body>");
    mapaHTML.append("<h1>Sistema de Gestión de Fincas</h1><hr>");
    mapaHTML.append("<div style='display: flex; flex-wrap: wrap; justify-content: space-between;'>"); // Contenedor flex para alinear cuadros horizontalmente

    for (Finca finca : fincas) {
        mapaHTML.append("<div style='border: 2px solid black; padding: 10px; margin: 10px; flex: 1; min-width: 30%;'>"); // Cuadro de finca
        mapaHTML.append("<h2>").append("Nombre de la Finca: ").append(finca.getNombre()).append("</h2>");
        mapaHTML.append("<p>").append("<b>Ubicación:</b> ").append(finca.getUbicacion()).append("<br>");
        mapaHTML.append(obtenerInfoFinca(finca));
        mapaHTML.append("</p></div>");
    }

    mapaHTML.append("</div></body></html>");

    JOptionPane.showMessageDialog(null, mapaHTML.toString(), "Sistema de Gestión de Fincas", JOptionPane.PLAIN_MESSAGE);
}

private String obtenerInfoFinca(Finca finca) {
    StringBuilder infoHTML = new StringBuilder();
    infoHTML.append("<b><u>Detalles:</u></b><br>");
    infoHTML.append("Nombre del Encargado: ").append(finca.getNombreEncargado()).append("<br>");
    infoHTML.append("Cédula del Encargado: ").append(finca.getCedulaEncargado()).append("<br>");
    infoHTML.append("Teléfono del Encargado: ").append(finca.getTelefonoEncargado()).append("<br>");
    infoHTML.append("Cantidad de Potreros: ").append(finca.getCantidadPotreros()).append("<br>");
    infoHTML.append("Tamaño de la Finca: ").append(finca.getTamañoFinca()).append("<br>");
    infoHTML.append("Cantidad de Animales: ").append(finca.getCantidadAnimales()).append("<br>");
    
    Potrero[] potreros = finca.getPotreros();
    for (Potrero potrero : potreros) {
        infoHTML.append("<hr>");
        infoHTML.append("<b>Potrero: ").append(potrero.getNombre()).append("</b><br>");
        infoHTML.append("Estado: ").append(potrero.getEstado()).append("<br>");
        infoHTML.append("Fecha de Entrada: ").append(potrero.getFechaEntrada()).append("<br>");
        infoHTML.append("Fecha de Salida: ").append(potrero.getFechaSalida()).append("<br>");
        infoHTML.append("Cantidad de Animales: ").append(potrero.getCantidadAnimales()).append("<br>");
        infoHTML.append("Posee Agua: ").append(potrero.isPoseeAgua() ? "Sí" : "No").append("<br>");
        infoHTML.append("Tipo de Terreno: ").append(potrero.getTipoTerreno()).append("<br>");
        
        Ganado[] ganado = potrero.getGanado();
        if (ganado != null) {
            for (Ganado animal : ganado) {
                infoHTML.append("<b>Ganado: ").append(animal.getNombre()).append("</b><br>");
                infoHTML.append("Código: ").append(animal.getCodigo()).append("<br>");
                infoHTML.append("Fecha de Nacimiento: ").append(animal.getFechaNacimiento()).append("<br>");
                infoHTML.append("Último Peso: ").append(animal.getUltimoPeso()).append("<br>");
                infoHTML.append("Sexo: ").append(animal.getSexo()).append("<br>");
                infoHTML.append("Raza: ").append(animal.getRaza()).append("<br>");
            }
        }
    }
    
    return infoHTML.toString();
}

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            MainFrame mainFrame = new MainFrame();
            mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            mainFrame.setVisible(true);
        });
    }
}