/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package GUI;

import clases.Alquiler;
import clases.Producto;
import clases.Cliente;
import clases.Sistema;
import clases.Usuario;
import java.awt.HeadlessException;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author DIEGO
 */
public class FrmAlquiler extends javax.swing.JFrame {

    /**
     * Creates new form FrmAlquiler
     */
    private Sistema sistema;
    private Usuario usuarioLogueado;
    private DefaultTableModel modeloDisponibles;
    private DefaultTableModel modeloCarrito;
    private List<Producto> carrito = new ArrayList<>();

    public FrmAlquiler(Usuario usuario, Sistema sistema) {
        initComponents();
        this.sistema = sistema;
        this.usuarioLogueado = usuario;
        this.setDefaultCloseOperation(javax.swing.JFrame.DISPOSE_ON_CLOSE);
        this.setTitle("Gestión de Alquileres - " + usuario.getNombre());

        configurarTablas();
        cargarProductosDisponibles();

        cmbTipoDoc.setModel(new javax.swing.DefaultComboBoxModel<>(new String[]{"DNI", "RUC"}));

        clases.Estilos.estiloVentana(this);

        clases.Estilos.estiloEtiqueta(jLabel1, false);
        clases.Estilos.estiloEtiqueta(jLabel2, false);
        clases.Estilos.estiloEtiqueta(jLabel4, false);
        clases.Estilos.estiloEtiqueta(jLabel5, false);
        clases.Estilos.estiloEtiqueta(jLabel6, false);

        clases.Estilos.estiloCampo(txtNombreCliente);
        clases.Estilos.estiloCampo(txtDniCliente);
        clases.Estilos.estiloCampo(txtDireccion);
        clases.Estilos.estiloCampo(txtDias);

        clases.Estilos.estiloTabla(tblCarrito, jScrollPane1);

        clases.Estilos.estiloTabla(tblProductosDisponibles, jScrollPane2);
        jScrollPane1.getViewport().setBackground(clases.Estilos.COLOR_WHITE);
        jScrollPane2.getViewport().setBackground(clases.Estilos.COLOR_WHITE);

        clases.Estilos.estiloBoton(btnRegistrarAlquiler, true);
        clases.Estilos.estiloBoton(btnAgregar, false);
        clases.Estilos.estiloBoton(btnQuitar, false);

        clases.Estilos.estiloBotonDestructivo(btnSalir);

        cmbTipoDoc.setFont(clases.Estilos.FONT_NORMAL);
        cmbTipoDoc.setBackground(clases.Estilos.COLOR_WHITE);
        
         this.setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
        this.addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent e) {
                int opcion = javax.swing.JOptionPane.showConfirmDialog(
                        null,
                        "¿Deseas cerrar esta ventana sin guardar?", 
                        "Cerrar Ventana",
                        javax.swing.JOptionPane.YES_NO_OPTION,
                        javax.swing.JOptionPane.QUESTION_MESSAGE
                );

                if (opcion == javax.swing.JOptionPane.YES_OPTION) {
                    dispose();
                }
            }
        });

    }

    public FrmAlquiler() {
        initComponents();
    }

    private void configurarTablas() {

        modeloDisponibles = new DefaultTableModel(new String[]{"ID", "Nombre", "Precio", "Stock"}, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        tblProductosDisponibles.setModel(modeloDisponibles);

        modeloCarrito = new DefaultTableModel(new String[]{"ID", "Nombre", "Precio", "Cant.", "Subtotal"}, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        tblCarrito.setModel(modeloCarrito);
    }

    private void cargarProductosDisponibles() {
        modeloDisponibles.setColumnIdentifiers(new String[]{"ID", "Nombre", "Precio", "Stock"});
        modeloDisponibles.setRowCount(0);

        for (Producto p : sistema.getInventario().getListaProductos()) {
            modeloDisponibles.addRow(new Object[]{
                p.getIdProducto(),
                p.getNomProducto(),
                p.getPrecio(),
                p.getStock()
            });
        }
    }

    private void actualizarCarrito() {
        modeloCarrito.setColumnIdentifiers(new String[]{"ID", "Nombre", "Precio"});
        modeloCarrito.setRowCount(0);
        for (Producto p : carrito) {
            modeloCarrito.addRow(new Object[]{
                p.getIdProducto(),
                p.getNomProducto(),
                p.getPrecio()
            });
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        tblCarrito = new javax.swing.JTable();
        jScrollPane2 = new javax.swing.JScrollPane();
        tblProductosDisponibles = new javax.swing.JTable();
        jLabel1 = new javax.swing.JLabel();
        btnRegistrarAlquiler = new javax.swing.JButton();
        btnAgregar = new javax.swing.JButton();
        btnQuitar = new javax.swing.JButton();
        btnSalir = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        txtDias = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        txtNombreCliente = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        txtDireccion = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        cmbTipoDoc = new javax.swing.JComboBox<>();
        txtDniCliente = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Panel de Alquiler");
        setResizable(false);

        jScrollPane1.setViewportBorder(javax.swing.BorderFactory.createTitledBorder("CARRITO"));

        tblCarrito.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane1.setViewportView(tblCarrito);

        jScrollPane2.setViewportBorder(javax.swing.BorderFactory.createTitledBorder("PRODUCTOS DISPONIBLES"));

        tblProductosDisponibles.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane2.setViewportView(tblProductosDisponibles);

        jLabel1.setText("Nombre :");

        btnRegistrarAlquiler.setIcon(new javax.swing.ImageIcon(getClass().getResource("/media/anotar.png"))); // NOI18N
        btnRegistrarAlquiler.setText("REGISTAR ALQUILER");
        btnRegistrarAlquiler.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRegistrarAlquilerActionPerformed(evt);
            }
        });

        btnAgregar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/media/agregar.png"))); // NOI18N
        btnAgregar.setText("AGREGAR");
        btnAgregar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAgregarActionPerformed(evt);
            }
        });

        btnQuitar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/media/cerrar.png"))); // NOI18N
        btnQuitar.setText("QUITAR");
        btnQuitar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnQuitarActionPerformed(evt);
            }
        });

        btnSalir.setIcon(new javax.swing.ImageIcon(getClass().getResource("/media/cerrar.png"))); // NOI18N
        btnSalir.setText("SALIR");
        btnSalir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSalirActionPerformed(evt);
            }
        });

        jLabel2.setText("Dias:");

        jLabel3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/media/logohd.jpg"))); // NOI18N

        jLabel4.setText("Dirección :");

        jLabel5.setText("Tipo de Documento :");

        cmbTipoDoc.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        jLabel6.setText("N° Documento :");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap(234, Short.MAX_VALUE)
                        .addComponent(jLabel3)
                        .addGap(188, 188, 188))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(19, 19, 19)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 772, Short.MAX_VALUE)
                            .addComponent(jScrollPane2)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addComponent(jLabel6)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(txtDniCliente, javax.swing.GroupLayout.PREFERRED_SIZE, 210, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jLabel2)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtDias, javax.swing.GroupLayout.PREFERRED_SIZE, 210, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(48, 48, 48))
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                        .addComponent(btnRegistrarAlquiler, javax.swing.GroupLayout.PREFERRED_SIZE, 230, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGroup(layout.createSequentialGroup()
                                            .addComponent(jLabel1)
                                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                            .addComponent(txtNombreCliente, javax.swing.GroupLayout.PREFERRED_SIZE, 210, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                    .addGroup(layout.createSequentialGroup()
                                        .addGap(169, 169, 169)
                                        .addComponent(btnQuitar))
                                    .addGroup(layout.createSequentialGroup()
                                        .addGap(12, 12, 12)
                                        .addComponent(btnAgregar)))
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addGap(18, 18, 18)
                                        .addComponent(jLabel4)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(txtDireccion, javax.swing.GroupLayout.PREFERRED_SIZE, 210, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(jLabel5)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(cmbTipoDoc, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(layout.createSequentialGroup()
                                        .addGap(242, 242, 242)
                                        .addComponent(btnSalir, javax.swing.GroupLayout.PREFERRED_SIZE, 210, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap(58, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 203, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 176, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel1)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(txtNombreCliente, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel4)
                        .addComponent(txtDireccion, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel5)
                        .addComponent(cmbTipoDoc, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(17, 17, 17)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel2)
                            .addComponent(txtDias, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtDniCliente, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel6))))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(btnRegistrarAlquiler)
                        .addGap(17, 17, 17)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(btnAgregar)
                            .addComponent(btnQuitar)))
                    .addComponent(btnSalir, javax.swing.GroupLayout.PREFERRED_SIZE, 59, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(12, 12, 12)
                .addComponent(jLabel3)
                .addContainerGap(102, Short.MAX_VALUE))
        );

        setSize(new java.awt.Dimension(911, 804));
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void btnAgregarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAgregarActionPerformed
        int fila = tblProductosDisponibles.getSelectedRow();
        if (fila == -1) {
            JOptionPane.showMessageDialog(this,
                    "Seleccione un producto de la tabla.",
                    "Selección Requerida",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }

        int idProducto = (int) modeloDisponibles.getValueAt(fila, 0);
        Producto p = sistema.getInventario().buscarProducto(idProducto);
        int stockActual = (int) modeloDisponibles.getValueAt(fila, 3);

        if (p != null) {

            String input = JOptionPane.showInputDialog(this,
                    "Ingrese la Cantidad:",
                    "Agregar al Carrito",
                    JOptionPane.QUESTION_MESSAGE);

            if (input == null || input.isEmpty()) {
                return;
            }

            try {
                int cantidad = Integer.parseInt(input);

                if (cantidad <= 0) {
                    JOptionPane.showMessageDialog(this,
                            "La cantidad debe ser mayor a 0.",
                            "Cantidad Inválida",
                            JOptionPane.ERROR_MESSAGE);
                    return;
                }

                if (cantidad > stockActual) {
                    JOptionPane.showMessageDialog(this,
                            "No hay suficiente stock.\nStock actual: " + stockActual,
                            "Stock Insuficiente",
                            JOptionPane.WARNING_MESSAGE);
                    return;
                }

                for (int i = 0; i < cantidad; i++) {
                    carrito.add(p);
                }

                modeloDisponibles.setValueAt(stockActual - cantidad, fila, 3);

                agregarFilaCarritoVisual(p, cantidad);

            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(this,
                        "Ingrese un número válido.",
                        "Error de Formato",
                        JOptionPane.ERROR_MESSAGE);
            }
        }
    }//GEN-LAST:event_btnAgregarActionPerformed

    private void btnQuitarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnQuitarActionPerformed
        int fila = tblCarrito.getSelectedRow();
        if (fila == -1) {
            JOptionPane.showMessageDialog(this,
                    "Seleccione un ítem del carrito para quitar.",
                    "Selección Requerida",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }

        int idProducto = (int) modeloCarrito.getValueAt(fila, 0);
        int cantidadEnCarrito = (int) modeloCarrito.getValueAt(fila, 3);

        for (int i = 0; i < modeloDisponibles.getRowCount(); i++) {
            if ((int) modeloDisponibles.getValueAt(i, 0) == idProducto) {
                int stockActual = (int) modeloDisponibles.getValueAt(i, 3);
                modeloDisponibles.setValueAt(stockActual + cantidadEnCarrito, i, 3);
                break;
            }
        }

        carrito.removeIf(p -> p.getIdProducto() == idProducto);

        modeloCarrito.removeRow(fila);
    }//GEN-LAST:event_btnQuitarActionPerformed

    private void btnRegistrarAlquilerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRegistrarAlquilerActionPerformed
        if (carrito.isEmpty()) {
            JOptionPane.showMessageDialog(this,
                    "El carrito está vacío. Agregue productos antes de registrar.",
                    "Carrito Vacío",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }

        try {

            int dias = Integer.parseInt(txtDias.getText());
            if (dias <= 0) {
                throw new NumberFormatException();
            }

            String tipoDoc = cmbTipoDoc.getSelectedItem().toString();
            String nroDoc = txtDniCliente.getText().trim();
            String nombre = txtNombreCliente.getText().trim();
            String direccion = txtDireccion.getText().trim();

            if (nroDoc.isEmpty() || nombre.isEmpty()) {
                JOptionPane.showMessageDialog(this,
                        "Debe ingresar el Número de Documento y el Nombre.",
                        "Datos Incompletos",
                        JOptionPane.WARNING_MESSAGE);
                return;
            }

            if (!nroDoc.matches("\\d+")) {
                JOptionPane.showMessageDialog(this,
                        "El documento debe contener solo números.",
                        "Error de Formato",
                        JOptionPane.WARNING_MESSAGE);
                return;
            }

            if (tipoDoc.equals("DNI") && nroDoc.length() != 8) {
                JOptionPane.showMessageDialog(this,
                        "El DNI debe tener exactamente 8 dígitos.",
                        "Error DNI",
                        JOptionPane.WARNING_MESSAGE);
                return;
            }
            if (tipoDoc.equals("RUC") && nroDoc.length() != 11) {
                JOptionPane.showMessageDialog(this,
                        "El RUC debe tener exactamente 11 dígitos.",
                        "Error RUC",
                        JOptionPane.WARNING_MESSAGE);
                return;
            }

            Cliente cliente = sistema.buscarClientePorDNI(nroDoc);
            if (cliente == null) {
                cliente = new Cliente(nroDoc, nombre, "", direccion);
                sistema.agregarCliente(cliente);
            } else {
                cliente.setNombre(nombre);
                cliente.setDireccion(direccion);
            }

            double precioTotalBase = 0;
            for (Producto p : carrito) {
                precioTotalBase += p.getPrecio();
            }

            for (Producto p : carrito) {
                p.reducirStock(1);
            }

            List<Producto> productosFinales = new ArrayList<>(carrito);
            int nuevoId = sistema.generarNuevoIdAlquiler();

            Alquiler nuevoAlquiler = new Alquiler(nuevoId, cliente, productosFinales, precioTotalBase, dias);
            sistema.agregarAlquiler(nuevoAlquiler);
            sistema.guardarDatos();

            JOptionPane.showMessageDialog(this,
                    "Alquiler registrado correctamente.\nTotal: S/ " + nuevoAlquiler.calcularTotal(),
                    "Registro Exitoso",
                    JOptionPane.INFORMATION_MESSAGE);

            FrmRecibo recibo = new FrmRecibo(nuevoAlquiler);
            recibo.setVisible(true);

            carrito.clear();
            modeloCarrito.setRowCount(0);
            cargarProductosDisponibles();
            txtDias.setText("");
            txtDniCliente.setText("");
            txtNombreCliente.setText("");
            txtDireccion.setText("");

        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this,
                    "Ingrese una cantidad de días válida.",
                    "Dato Inválido",
                    JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_btnRegistrarAlquilerActionPerformed

    private void btnSalirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSalirActionPerformed
        int opcion = javax.swing.JOptionPane.showConfirmDialog(
            this,
            "¿Seguro que deseas salir?",
            "Confirmar salida",
            javax.swing.JOptionPane.YES_NO_OPTION,
            javax.swing.JOptionPane.QUESTION_MESSAGE
        );

        if (opcion == javax.swing.JOptionPane.YES_OPTION) {
            this.dispose();
        }
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
            java.util.logging.Logger.getLogger(FrmAlquiler.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(FrmAlquiler.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(FrmAlquiler.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(FrmAlquiler.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new FrmAlquiler().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAgregar;
    private javax.swing.JButton btnQuitar;
    private javax.swing.JButton btnRegistrarAlquiler;
    private javax.swing.JButton btnSalir;
    private javax.swing.JComboBox<String> cmbTipoDoc;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTable tblCarrito;
    private javax.swing.JTable tblProductosDisponibles;
    private javax.swing.JTextField txtDias;
    private javax.swing.JTextField txtDireccion;
    private javax.swing.JTextField txtDniCliente;
    private javax.swing.JTextField txtNombreCliente;
    // End of variables declaration//GEN-END:variables

    private void agregarFilaCarritoVisual(Producto p, int cantidad) {
        for (int i = 0; i < modeloCarrito.getRowCount(); i++) {
            int idEnTabla = (int) modeloCarrito.getValueAt(i, 0);
            if (idEnTabla == p.getIdProducto()) {
                int cantAnterior = (int) modeloCarrito.getValueAt(i, 3);
                int nuevaCant = cantAnterior + cantidad;
                modeloCarrito.setValueAt(nuevaCant, i, 3);
                modeloCarrito.setValueAt(nuevaCant * p.getPrecio(), i, 4);
                return;
            }
        }
        modeloCarrito.addRow(new Object[]{
            p.getIdProducto(),
            p.getNomProducto(),
            p.getPrecio(),
            cantidad,
            cantidad * p.getPrecio()
        });
    }
}
