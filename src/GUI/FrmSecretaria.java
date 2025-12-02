/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package GUI;

import java.text.SimpleDateFormat;
import clases.Alquiler;
import clases.Sistema;
import clases.Usuario;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author DIEGO
 */
public class FrmSecretaria extends javax.swing.JFrame {

    /**
     * Creates new form FrmSecretaria
     */
    private Usuario usuarioLogueado;
    private Sistema sistema;
    private DefaultTableModel modeloTabla;

    public FrmSecretaria(Usuario usuario, Sistema sistema) {
        initComponents();
        this.usuarioLogueado = usuario;
        this.sistema = sistema;

        lblBienvenida.setText("¡Hola, " + usuarioLogueado.getNombre() + "! (" + usuarioLogueado.getRol().getNombreRol() + ")");

        String[] columnas = {"ID", "DNI Cliente", "Nombre Cliente", "Total"};
        modeloTabla = new DefaultTableModel(columnas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        jTable1.setModel(modeloTabla);

        cargarHistorialAlquileres();

        this.addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowActivated(java.awt.event.WindowEvent e) {
                cargarHistorialAlquileres();
            }
        });

        for (java.awt.event.ActionListener al : btnSalir.getActionListeners()) {
            btnSalir.removeActionListener(al);
        }
        jTable1.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent evt) {
                if (evt.getClickCount() == 2) {
                    int fila = jTable1.getSelectedRow();
                    if (fila != -1) {
                        try {
                            // Obtenemos el ID de la columna 0
                            int idAlquiler = Integer.parseInt(jTable1.getValueAt(fila, 0).toString());

                            Alquiler alquiler = sistema.buscarAlquilerPorID(idAlquiler);

                            if (alquiler != null) {
                                new FrmRecibo(alquiler).setVisible(true);
                            }
                        } catch (Exception e) {
                            System.err.println("Error al abrir recibo: " + e.getMessage());
                        }
                    }
                }
            }
        });

        btnSalir.addActionListener(e -> {
            int opcion = javax.swing.JOptionPane.showConfirmDialog(
                    this,
                    "¿Seguro que deseas cerrar sesión?",
                    "Cerrar Sesión",
                    javax.swing.JOptionPane.YES_NO_OPTION,
                    javax.swing.JOptionPane.QUESTION_MESSAGE
            );

            if (opcion == javax.swing.JOptionPane.YES_OPTION) {
                sistema.guardarDatos();
                dispose();
                FrmLogin login = new FrmLogin(this.sistema);
                login.setVisible(true);
            }
        });

        clases.Estilos.estiloVentana(this);
        clases.Estilos.estiloEtiqueta(lblBienvenida, true);

        clases.Estilos.estiloTabla(jTable1, jScrollPane1);

        jScrollPane1.getViewport().setBackground(clases.Estilos.COLOR_WHITE);
        jScrollPane1.setBorder(new javax.swing.border.LineBorder(clases.Estilos.COLOR_SECONDARY, 1));

        clases.Estilos.estiloBoton(btnAlquiler, false);
        clases.Estilos.estiloBoton(btnRegistrarEntrada, false);
        clases.Estilos.estiloBoton(btnRegistrarSalida, false);
        clases.Estilos.estiloBoton(btnDevolucion, false);
        clases.Estilos.estiloBoton(btnResumenDia, false);
        clases.Estilos.estiloBoton(btnReporteGlobal, false);

        clases.Estilos.estiloBotonDestructivo(btnSalir);

        this.setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);

        this.addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent e) {

                int opcion = javax.swing.JOptionPane.showConfirmDialog(
                        null,
                        "¿Seguro que deseas salir del sistema?\nSe guardarán los cambios automáticamente.",
                        "Confirmar Salida",
                        javax.swing.JOptionPane.YES_NO_OPTION,
                        javax.swing.JOptionPane.QUESTION_MESSAGE
                );

                if (opcion == javax.swing.JOptionPane.YES_OPTION) {
                    try {
                        if (sistema != null) {
                            sistema.guardarDatos();
                            System.out.println("Datos guardados correctamente al cerrar.");
                        }
                    } catch (Exception ex) {
                        System.err.println("Error al guardar datos al salir: " + ex.getMessage());
                    }

                    System.exit(0);
                }
            }
        });

    }

    public FrmSecretaria() {
        initComponents();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        lblBienvenida = new javax.swing.JLabel();
        lblPNG = new javax.swing.JLabel();
        btnSalir = new javax.swing.JButton();
        btnRegistrarSalida = new javax.swing.JButton();
        btnRegistrarEntrada = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        btnDevolucion = new javax.swing.JButton();
        btnAlquiler = new javax.swing.JButton();
        btnReporteGlobal = new javax.swing.JButton();
        btnResumenDia = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Panel de Control Secretaria");
        setIconImage(getIconImage());
        setResizable(false);

        lblBienvenida.setText("¡Hola, NOMBRE! (SECRETARIA)");

        lblPNG.setIcon(new javax.swing.ImageIcon(getClass().getResource("/media/secretaria.png"))); // NOI18N

        btnSalir.setIcon(new javax.swing.ImageIcon(getClass().getResource("/media/cerrarsesion.png"))); // NOI18N
        btnSalir.setText("CERRAR SESION");
        btnSalir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSalirActionPerformed(evt);
            }
        });

        btnRegistrarSalida.setIcon(new javax.swing.ImageIcon(getClass().getResource("/media/anotar.png"))); // NOI18N
        btnRegistrarSalida.setText("REGISTRAR SALIDA");
        btnRegistrarSalida.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRegistrarSalidaActionPerformed(evt);
            }
        });

        btnRegistrarEntrada.setIcon(new javax.swing.ImageIcon(getClass().getResource("/media/anotar.png"))); // NOI18N
        btnRegistrarEntrada.setText("REGISTRAR ENTRADA");
        btnRegistrarEntrada.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRegistrarEntradaActionPerformed(evt);
            }
        });

        jScrollPane1.setToolTipText("c");
        jScrollPane1.setViewportBorder(javax.swing.BorderFactory.createTitledBorder("Comprobantes"));

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane1.setViewportView(jTable1);

        btnDevolucion.setIcon(new javax.swing.ImageIcon(getClass().getResource("/media/anotar.png"))); // NOI18N
        btnDevolucion.setText("REGISTRAR DEVOLUCIÓN");
        btnDevolucion.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDevolucionActionPerformed(evt);
            }
        });

        btnAlquiler.setIcon(new javax.swing.ImageIcon(getClass().getResource("/media/anotar.png"))); // NOI18N
        btnAlquiler.setText("REGISTRAR ALQUILER");
        btnAlquiler.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAlquilerActionPerformed(evt);
            }
        });

        btnReporteGlobal.setIcon(new javax.swing.ImageIcon(getClass().getResource("/media/clavemostrar.png"))); // NOI18N
        btnReporteGlobal.setText("REPORTE GLOBAL");
        btnReporteGlobal.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnReporteGlobalActionPerformed(evt);
            }
        });

        btnResumenDia.setIcon(new javax.swing.ImageIcon(getClass().getResource("/media/clavemostrar.png"))); // NOI18N
        btnResumenDia.setText("RESUMEN POR DIA");
        btnResumenDia.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnResumenDiaActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap(204, Short.MAX_VALUE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblBienvenida)
                            .addComponent(lblPNG))
                        .addGap(270, 270, 270))
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jScrollPane1)
                        .addGap(18, 18, 18)))
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(btnReporteGlobal, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnResumenDia, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnAlquiler, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnRegistrarEntrada, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnRegistrarSalida, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnDevolucion, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnSalir, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(52, 52, 52))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(14, 14, 14)
                .addComponent(lblBienvenida)
                .addGap(18, 18, 18)
                .addComponent(lblPNG)
                .addGap(51, 51, 51)
                .addComponent(jScrollPane1)
                .addContainerGap())
            .addGroup(layout.createSequentialGroup()
                .addGap(119, 119, 119)
                .addComponent(btnAlquiler, javax.swing.GroupLayout.PREFERRED_SIZE, 95, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(btnRegistrarEntrada, javax.swing.GroupLayout.PREFERRED_SIZE, 95, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnRegistrarSalida, javax.swing.GroupLayout.PREFERRED_SIZE, 95, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnDevolucion, javax.swing.GroupLayout.PREFERRED_SIZE, 95, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnResumenDia, javax.swing.GroupLayout.PREFERRED_SIZE, 95, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnReporteGlobal, javax.swing.GroupLayout.PREFERRED_SIZE, 95, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnSalir, javax.swing.GroupLayout.PREFERRED_SIZE, 95, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(56, Short.MAX_VALUE))
        );

        setSize(new java.awt.Dimension(899, 926));
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void btnRegistrarEntradaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRegistrarEntradaActionPerformed
        String dni = javax.swing.JOptionPane.showInputDialog(this, "Ingrese el DNI del personal:", "Registro de Entrada", javax.swing.JOptionPane.QUESTION_MESSAGE);

        if (dni != null && !dni.isEmpty()) {
            clases.Usuario usuario = sistema.buscarUsuarioPorDNI(dni);

            if (usuario != null) {
                sistema.registrarAsistencia(usuario, "ENTRADA (Registrado por Secretaria)");
                javax.swing.JOptionPane.showMessageDialog(this, "Entrada registrada para: " + usuario.getNombre());
            } else {
                javax.swing.JOptionPane.showMessageDialog(this, "Usuario no encontrado.", "Error", javax.swing.JOptionPane.ERROR_MESSAGE);
            }
        }
    }//GEN-LAST:event_btnRegistrarEntradaActionPerformed

    private void btnRegistrarSalidaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRegistrarSalidaActionPerformed
        String dni = javax.swing.JOptionPane.showInputDialog(this, "Ingrese el DNI del personal para registrar SALIDA:");

        if (dni != null && !dni.isEmpty()) {
            clases.Usuario usuario = sistema.buscarUsuarioPorDNI(dni);

            if (usuario != null) {
                sistema.registrarAsistencia(usuario, "SALIDA (Registrado por Secretaria)");
                javax.swing.JOptionPane.showMessageDialog(this, "Salida registrada para: " + usuario.getNombre());
            } else {
                javax.swing.JOptionPane.showMessageDialog(this, "Usuario no encontrado.", "Error", javax.swing.JOptionPane.ERROR_MESSAGE);
            }
        }
    }//GEN-LAST:event_btnRegistrarSalidaActionPerformed

    private void btnDevolucionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDevolucionActionPerformed

        FrmDevolucion frmDev = new FrmDevolucion(usuarioLogueado, sistema);
        frmDev.setVisible(true);

        frmDev.addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosed(java.awt.event.WindowEvent e) {

            }
        });
    }//GEN-LAST:event_btnDevolucionActionPerformed

    private void btnAlquilerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAlquilerActionPerformed
        FrmAlquiler frmAlq = new FrmAlquiler(usuarioLogueado, sistema);

        frmAlq.setVisible(true);
    }//GEN-LAST:event_btnAlquilerActionPerformed

    private void btnReporteGlobalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnReporteGlobalActionPerformed
        try {

            File carpeta = new File("Reportes");
            if (!carpeta.exists()) {
                carpeta.mkdir();
            }

            String nombreArchivo = "Reporte_Global_" + System.currentTimeMillis() + ".pdf";
            java.io.File archivo = new java.io.File(carpeta, nombreArchivo);

            sistema.generarPDFGlobal(archivo, usuarioLogueado.getNombre() + " " + usuarioLogueado.getApellido());

            if (java.awt.Desktop.isDesktopSupported()) {
                java.awt.Desktop.getDesktop().open(archivo);
            }
        } catch (Exception e) {
            javax.swing.JOptionPane.showMessageDialog(this, "Error: " + e.getMessage());
        }

    }//GEN-LAST:event_btnReporteGlobalActionPerformed

    private void btnResumenDiaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnResumenDiaActionPerformed
        try {

            File carpeta = new File("Reportes");
            if (!carpeta.exists()) {
                carpeta.mkdir();
            }

            String nombreArchivo = "Cierre_Caja_" + new SimpleDateFormat("yyyyMMdd").format(new java.util.Date()) + ".pdf";

            String rutaCompleta = new File(carpeta, nombreArchivo).getAbsolutePath();

            sistema.generarPDFCierreCaja(usuarioLogueado, rutaCompleta);

            File archivoGenerado = new File(rutaCompleta);
            if (java.awt.Desktop.isDesktopSupported() && archivoGenerado.exists()) {
                java.awt.Desktop.getDesktop().open(archivoGenerado);
            } else {
                javax.swing.JOptionPane.showMessageDialog(this, "Reporte guardado en Reportes.");
            }
        } catch (Exception e) {
            javax.swing.JOptionPane.showMessageDialog(this, "Error al generar reporte: " + e.getMessage());
        }

    }//GEN-LAST:event_btnResumenDiaActionPerformed

    private void btnSalirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSalirActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnSalirActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;

                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(FrmSecretaria.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);

        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(FrmSecretaria.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);

        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(FrmSecretaria.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);

        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(FrmSecretaria.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new FrmSecretaria().setVisible(true);
            }
        });
    }

    @Override
    public Image getIconImage() {
        Image retValue = Toolkit.getDefaultToolkit().
                getImage(ClassLoader.getSystemResource("media/logofinal.png"));

        return retValue;
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAlquiler;
    private javax.swing.JButton btnDevolucion;
    private javax.swing.JButton btnRegistrarEntrada;
    private javax.swing.JButton btnRegistrarSalida;
    private javax.swing.JButton btnReporteGlobal;
    private javax.swing.JButton btnResumenDia;
    private javax.swing.JButton btnSalir;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTable1;
    private javax.swing.JLabel lblBienvenida;
    private javax.swing.JLabel lblPNG;
    // End of variables declaration//GEN-END:variables

    private void cargarHistorialAlquileres() {
        modeloTabla.setRowCount(0);

        for (Alquiler alq : sistema.getAlquileres()) {
            Object[] fila = {
                alq.getIdAlquiler(),
                alq.getCliente().getDni(),
                alq.getCliente().getNombre() + " " + alq.getCliente().getApellido(),
                String.format("S/ %.2f", alq.calcularTotal())
            };
            modeloTabla.addRow(fila);
        }
    }

}
