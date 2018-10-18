/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package presentacion;

import java.awt.event.KeyEvent;
import java.sql.ResultSet;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import negocio.articulo;
import negocio.categoria;
import negocio.linea;
import negocio.marca;
import util.Funciones;

/**
 *
 * @author laboratorio_computo
 */
public class frmArticuloAgregarEditar extends javax.swing.JDialog {

    public String operacion;
    public int valorRetorno = 0;

    public frmArticuloAgregarEditar(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        setLocationRelativeTo(null);

        cargarComboCategoria();
//        cargarComboLinea();
        cargarComboMarca();
        chkDescuento.setSelected(true);

        //estado();
        txtNombre.requestFocus();
    }

    public void leerDatosArticulo(int codCat) {
        try {
            ResultSet resultado = new articulo().leerDatosCodigo(codCat);
            if (resultado.next()) {
                lblCodigo.setText(String.valueOf(resultado.getInt("codigo_articulo")));
                txtNombre.setText(String.valueOf(resultado.getString("nombre")));
                txtPrecio.setText(String.valueOf(resultado.getDouble("precio")));
                cboCategoria.setSelectedItem(String.valueOf(resultado.getString("categoria")));
                cboMarca.setSelectedItem(String.valueOf(resultado.getString("marca")));
                txtStock.setText(String.valueOf(resultado.getInt("stock")));

                String estado = resultado.getString("estado");
                if (estado.equalsIgnoreCase("A")) {
                    rbActivo.setSelected(true);
                } else if (estado.equalsIgnoreCase("I")) {
                    rbInactivo.setSelected(true);
                }

                if (resultado.getDouble("descuento") != 0) {
                    chkDescuento.setSelected(true);
                    txtDescuento.setText(String.valueOf(resultado.getDouble("descuento")));
                } else {
                    chkDescuento.setSelected(false);
                    txtDescuento.setEnabled(false);
                }

            }
        } catch (Exception e) {
            Funciones.mensajeError(e.getMessage(), Funciones.NOMBRE_SOFTWARE);
        }
    }

    private void cargarComboCategoria() {
        try {
            new categoria().cargarCombo(cboCategoria);
        } catch (Exception e) {
            Funciones.mensajeError(e.getMessage(), Funciones.NOMBRE_SOFTWARE);
        }
    }

    private void cargarComboMarca() {
        try {
            new marca().cargarCombo(cboMarca);
        } catch (Exception e) {
            Funciones.mensajeError(e.getMessage(), Funciones.NOMBRE_SOFTWARE);
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        buttonGroup1 = new javax.swing.ButtonGroup();
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        lblCodigo = new javax.swing.JTextField();
        txtNombre = new javax.swing.JTextField();
        btnGuardar = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jLabel5 = new javax.swing.JLabel();
        txtStock = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        txtPrecio = new javax.swing.JTextField();
        cboMarca = new javax.swing.JComboBox();
        cboCategoria = new javax.swing.JComboBox();
        jLabel9 = new javax.swing.JLabel();
        rbActivo = new javax.swing.JRadioButton();
        rbInactivo = new javax.swing.JRadioButton();
        chkDescuento = new javax.swing.JCheckBox();
        txtDescuento = new javax.swing.JTextField();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setBackground(new java.awt.Color(255, 255, 255));

        jPanel1.setBackground(new java.awt.Color(153, 255, 255));

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/categorias2.png"))); // NOI18N
        jLabel1.setText("Mantenimiento de Artículos");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(77, 77, 77)
                .addComponent(jLabel1)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel1)
                .addContainerGap())
        );

        jLabel2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/1454137835_ID_SnipGeneric_Icon_file_document.png"))); // NOI18N
        jLabel2.setText("Código");

        jLabel3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/1454138595_Red_tag.png"))); // NOI18N
        jLabel3.setText("Nombre");

        lblCodigo.setEditable(false);

        btnGuardar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/guardar.png"))); // NOI18N
        btnGuardar.setText("Guardar");
        btnGuardar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGuardarActionPerformed(evt);
            }
        });

        jButton2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/salir2.png"))); // NOI18N
        jButton2.setText("Salir");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jLabel5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/1454138108_price_tag_usd.png"))); // NOI18N
        jLabel5.setText("Precio");

        txtStock.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtStockKeyTyped(evt);
            }
        });

        jLabel6.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/1454138218_lego-bricks.png"))); // NOI18N
        jLabel6.setText("Categoria");

        jLabel7.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/1454138831_Tyres_1.png"))); // NOI18N
        jLabel7.setText("Marca");

        jLabel8.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/1454138475_ChirstmaxShop_basketadd.png"))); // NOI18N
        jLabel8.setText("Stock");

        txtPrecio.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtPrecioKeyTyped(evt);
            }
        });

        cboMarca.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        cboCategoria.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        jLabel9.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/1454138284_arrow_state_blue_right.png"))); // NOI18N
        jLabel9.setText("Estado");

        buttonGroup1.add(rbActivo);
        rbActivo.setSelected(true);
        rbActivo.setText("Activo");

        buttonGroup1.add(rbInactivo);
        rbInactivo.setText("Inactivo");

        chkDescuento.setText("Tiene Descuento");
        chkDescuento.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/1454138549_sale.png"))); // NOI18N
        chkDescuento.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                chkDescuentoActionPerformed(evt);
            }
        });

        txtDescuento.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtDescuentoKeyPressed(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtDescuentoKeyTyped(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel6)
                    .addComponent(jLabel7)
                    .addComponent(jLabel9)
                    .addComponent(jLabel8)
                    .addComponent(chkDescuento)
                    .addComponent(jLabel5)
                    .addComponent(jLabel3)
                    .addComponent(jLabel2))
                .addGap(47, 47, 47)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(rbActivo)
                                .addGap(56, 56, 56)
                                .addComponent(rbInactivo))
                            .addComponent(txtDescuento, javax.swing.GroupLayout.PREFERRED_SIZE, 125, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtPrecio, javax.swing.GroupLayout.PREFERRED_SIZE, 125, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lblCodigo, javax.swing.GroupLayout.PREFERRED_SIZE, 124, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(cboCategoria, javax.swing.GroupLayout.PREFERRED_SIZE, 223, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtStock, javax.swing.GroupLayout.PREFERRED_SIZE, 125, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtNombre, javax.swing.GroupLayout.PREFERRED_SIZE, 342, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(cboMarca, javax.swing.GroupLayout.PREFERRED_SIZE, 223, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(0, 37, Short.MAX_VALUE))))
            .addGroup(layout.createSequentialGroup()
                .addGap(89, 89, 89)
                .addComponent(btnGuardar)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 91, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(127, 127, 127))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(28, 28, 28)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(lblCodigo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(txtNombre, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(txtPrecio, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(cboCategoria, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7)
                    .addComponent(cboMarca, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtStock, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel8))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel9)
                    .addComponent(rbActivo)
                    .addComponent(rbInactivo))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(chkDescuento)
                    .addComponent(txtDescuento, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(33, 33, 33)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnGuardar)
                    .addComponent(jButton2))
                .addContainerGap(26, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnGuardarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGuardarActionPerformed
        //Funciones.mensajeInformacion(operacion, "");

        String estado = "";
        if (txtNombre.getText().isEmpty()) {
            Funciones.mensajeAdvertencia("Falta llenar descripcion", "Verifique");
            txtNombre.requestFocus();
            return;
        }

        if (txtPrecio.getText().isEmpty()) {
            Funciones.mensajeAdvertencia("Falta llenar precio", "Verifique");
            txtPrecio.requestFocus();
            return;
        }

        if (txtStock.getText().isEmpty()) {
            Funciones.mensajeAdvertencia("Falta llenar Stock", "Verifique");
            txtStock.requestFocus();
            return;
        }

        if (chkDescuento.isSelected() && txtDescuento.getText().isEmpty()) {
            Funciones.mensajeAdvertencia("Falta llenar Descuento", "Verifique");
            txtDescuento.requestFocus();
            return;
        }

        try {
            articulo obj = new articulo();
            obj.setNombre(txtNombre.getText().toUpperCase());
            obj.setPrecio(Double.parseDouble(txtPrecio.getText()));
            //obj.setCodigo_linea(linea.listaLineas.get(cboLinea.getSelectedIndex()).getCodigo_linea());
            obj.setCodigo_categoria(categoria.listaCategorias.get(cboCategoria.getSelectedIndex()).getCodigo_categoria());
            obj.setCodigo_marca(marca.listaMarcas.get(cboMarca.getSelectedIndex()).getCodigo_marca());
            obj.setStock(Integer.parseInt(txtStock.getText()));

            if (rbActivo.isSelected()) {
                estado = "A";
            } else if (rbInactivo.isSelected()) {
                estado = "I";
            }

            obj.setEstado(estado);

            if (chkDescuento.isSelected()) {
                if (Double.parseDouble(txtDescuento.getText()) < 0 || Double.parseDouble(txtDescuento.getText()) > 100) {
                    Funciones.mensajeAdvertencia("Ingresar descuento entre 0 y 100", "Verifique");
                    txtDescuento.requestFocus();
                    return;
                } else {
                    obj.setDescuento(Double.parseDouble(txtDescuento.getText()));
                }
            }

            int respuesta = Funciones.mensajeConfirmacion("¿Estás seguro de grabar los datos?", Funciones.NOMBRE_SOFTWARE);
            if (respuesta != 0) {
                return;
            }

            boolean resultado;

            if (operacion.equalsIgnoreCase("Agregar")) {
                resultado = obj.agregar();

                if (resultado) {
                    Funciones.mensajeInformacion("El registro se ha realizado con éxito...", Funciones.NOMBRE_SOFTWARE);
                    valorRetorno = 1;
                    dispose();
                }
            } else if (operacion.equalsIgnoreCase("Editar")) {

                obj.setCodigo_articulo(Integer.parseInt(lblCodigo.getText()));

                resultado = obj.editar();

                if (resultado) {
                    Funciones.mensajeInformacion("Actualización éxitosa...", Funciones.NOMBRE_SOFTWARE);
                    valorRetorno = 1;
                    dispose();
                }
            }

        } catch (Exception e) {
            Funciones.mensajeError(e.getMessage(), Funciones.NOMBRE_SOFTWARE);
        }

    }//GEN-LAST:event_btnGuardarActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        dispose();
    }//GEN-LAST:event_jButton2ActionPerformed

    private void txtPrecioKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtPrecioKeyTyped
        Funciones.soloNumerosDecimal(evt, txtPrecio, 20);
    }//GEN-LAST:event_txtPrecioKeyTyped

    private void txtStockKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtStockKeyTyped
        Funciones.soloNumeros(evt, txtStock, 20);
    }//GEN-LAST:event_txtStockKeyTyped

    private void txtDescuentoKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtDescuentoKeyTyped
        Funciones.soloNumerosDecimal(evt, txtDescuento, 3);

    }//GEN-LAST:event_txtDescuentoKeyTyped

    private void chkDescuentoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_chkDescuentoActionPerformed
        if (chkDescuento.isSelected()) {
            txtDescuento.setEnabled(true);
        } else {
            txtDescuento.setEnabled(false);
            txtDescuento.setText("");
        }
    }//GEN-LAST:event_chkDescuentoActionPerformed

    private void txtDescuentoKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtDescuentoKeyPressed
        if (evt.getKeyCode()==KeyEvent.VK_ENTER) {
            this.btnGuardar.doClick();
        }
    }//GEN-LAST:event_txtDescuentoKeyPressed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnGuardar;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JComboBox cboCategoria;
    private javax.swing.JComboBox cboMarca;
    private javax.swing.JCheckBox chkDescuento;
    private javax.swing.JButton jButton2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JTextField lblCodigo;
    private javax.swing.JRadioButton rbActivo;
    private javax.swing.JRadioButton rbInactivo;
    private javax.swing.JTextField txtDescuento;
    private javax.swing.JTextField txtNombre;
    private javax.swing.JTextField txtPrecio;
    private javax.swing.JTextField txtStock;
    // End of variables declaration//GEN-END:variables
}
