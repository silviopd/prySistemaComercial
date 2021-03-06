/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package presentacion;

import java.awt.event.KeyEvent;
import java.sql.ResultSet;
import negocio.articulo;
import negocio.proveedor;
import util.Funciones;

/**
 *
 * @author USER
 */
public class frmProveedorBuscar extends javax.swing.JDialog {

    private ResultSet resultado;
    public String datosBuscar = "";
    public int valorRetorno2=0;
    
    public frmProveedorBuscar(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();        
        setLocationRelativeTo(null);
        
        cargarDatosConsulta();
        filtrar();
        
        txtValorBusqueda.requestFocus();
    }
private void cargarDatosConsulta() {
        try {
            resultado = new proveedor().listar();
        } catch (Exception e) {
            Funciones.mensajeError(e.getMessage(), Funciones.NOMBRE_SOFTWARE);
        }
    }

    private void filtrar() {
        try {
            int anchoColumnas[] = {90, 300, 80, 220, 220};
            String alinearColumnas[] = {"C", "I", "I", "I", "I"};
            if (rbRuc.isSelected()) {
                Funciones.llenarTablaBusqueda(tblListado, resultado, anchoColumnas, alinearColumnas, "ruc_proveedor", txtValorBusqueda.getText());
            } else {
                Funciones.llenarTablaBusqueda(tblListado, resultado, anchoColumnas, alinearColumnas, "razon_social", txtValorBusqueda.getText());

            }
        } catch (Exception e) {
            Funciones.mensajeError(e.getMessage(), Funciones.NOMBRE_SOFTWARE);
        }
    }
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        buttonGroup1 = new javax.swing.ButtonGroup();
        jLabel1 = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        rbRuc = new javax.swing.JRadioButton();
        rbRazon = new javax.swing.JRadioButton();
        jLabel2 = new javax.swing.JLabel();
        txtValorBusqueda = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblListado = new javax.swing.JTable(){
            public boolean isCellEditable(int fila,int columna){
                return false;
            }
        };
        btnAceptar = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jLabel1.setFont(new java.awt.Font("Tahoma", 3, 24)); // NOI18N
        jLabel1.setText("Buscar Proveedor");

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Buscar por", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 1, 14), new java.awt.Color(51, 51, 255))); // NOI18N

        buttonGroup1.add(rbRuc);
        rbRuc.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        rbRuc.setSelected(true);
        rbRuc.setText("RUC");
        rbRuc.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rbRucActionPerformed(evt);
            }
        });

        buttonGroup1.add(rbRazon);
        rbRazon.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        rbRazon.setText("Razón Social");
        rbRazon.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rbRazonActionPerformed(evt);
            }
        });

        jLabel2.setText("Ingrese el valor que desea buscar");

        txtValorBusqueda.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtValorBusquedaKeyReleased(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtValorBusqueda)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(rbRuc)
                                .addGap(18, 18, 18)
                                .addComponent(rbRazon))
                            .addComponent(jLabel2))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(rbRuc)
                    .addComponent(rbRazon))
                .addGap(18, 18, 18)
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(txtValorBusqueda, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        tblListado.setModel(new javax.swing.table.DefaultTableModel(
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
        tblListado.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblListadoMouseClicked(evt);
            }
        });
        tblListado.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                tblListadoKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                tblListadoKeyReleased(evt);
            }
        });
        jScrollPane1.setViewportView(tblListado);

        btnAceptar.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        btnAceptar.setText("Aceptar");
        btnAceptar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAceptarActionPerformed(evt);
            }
        });

        jButton2.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jButton2.setText("Salir");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel1)
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 630, Short.MAX_VALUE)))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(187, 187, 187)
                        .addComponent(btnAceptar)
                        .addGap(109, 109, 109)
                        .addComponent(jButton2)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addGap(18, 18, 18)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 307, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(32, 32, 32)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnAceptar)
                    .addComponent(jButton2))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        dispose();
    }//GEN-LAST:event_jButton2ActionPerformed

    private void txtValorBusquedaKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtValorBusquedaKeyReleased
        filtrar();
    }//GEN-LAST:event_txtValorBusquedaKeyReleased

    private void rbRucActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rbRucActionPerformed
        txtValorBusqueda.setText("");
        filtrar();
        txtValorBusqueda.requestFocus();
    }//GEN-LAST:event_rbRucActionPerformed

    private void rbRazonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rbRazonActionPerformed
        txtValorBusqueda.setText("");
        filtrar();
        txtValorBusqueda.requestFocus();
    }//GEN-LAST:event_rbRazonActionPerformed
           
    private void btnAceptarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAceptarActionPerformed
        int fila = tblListado.getSelectedRow();
        
        if (fila < 0) {
            Funciones.mensajeError("No ha seleccionado un registro", Funciones.NOMBRE_SOFTWARE);
            return;
        }
        
        if (rbRuc.isSelected()) {
             datosBuscar= tblListado.getValueAt(fila, 0).toString();
             valorRetorno2=1;
             dispose();
        }else{
            datosBuscar = tblListado.getValueAt(fila, 1).toString();
            valorRetorno2=2;
            dispose();
        }
    }//GEN-LAST:event_btnAceptarActionPerformed

    private void tblListadoMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblListadoMouseClicked
        if (evt.getClickCount()==2) {
            btnAceptar.doClick();
        }
    }//GEN-LAST:event_tblListadoMouseClicked

    private void tblListadoKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tblListadoKeyReleased
        
    }//GEN-LAST:event_tblListadoKeyReleased

    private void tblListadoKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tblListadoKeyPressed
       if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            btnAceptar.doClick();
        }
    }//GEN-LAST:event_tblListadoKeyPressed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAceptar;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JButton jButton2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JRadioButton rbRazon;
    private javax.swing.JRadioButton rbRuc;
    private javax.swing.JTable tblListado;
    private javax.swing.JTextField txtValorBusqueda;
    // End of variables declaration//GEN-END:variables
}
