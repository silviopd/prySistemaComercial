/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package presentacion;

import java.awt.Color;
import java.awt.PopupMenu;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.KeyEvent;
import java.sql.Date;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.BorderFactory;
import javax.swing.DefaultCellEditor;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellEditor;
import negocio.articulo;
import negocio.compra;
import negocio.compraDetalle;
import negocio.configuracion;
import negocio.proveedor;
import negocio.tipoComprobante;
import util.Funciones;

/**
 *
 * @author USER
 */
public class frmCompra extends javax.swing.JDialog {

//    private ResultSet resultado;
    public int valorRetorno = 0;

    public frmCompra(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        setLocationRelativeTo(null);

        cargarComboTipoComprobante();
        configurarCabeceraDetalleCompra();
        obtenerPorcentajeIGV();

        tblCompra.setCellSelectionEnabled(true);
        tblCompra.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        tblCompra.setRowHeight(25);

        txtNumeroSerie.requestFocus();
        obtenerFecha();
    }

    private void obtenerFecha() {
        java.util.Date obj = new java.util.Date();
        txtFecha.setDate(obj);
    }

    private boolean validarDatos() {

        if (cboTipoComprobante.getSelectedItem().toString().isEmpty()) {
            Funciones.mensajeAdvertencia("Debe elegir un tipo de comprobando", "Verificar");
            cboTipoComprobante.requestFocus();
            return false;
        } else if (txtNumeroSerie.getText().isEmpty()) {
            Funciones.mensajeAdvertencia("Debe ingresar la serie", "Verifique");
            txtNumeroSerie.requestFocus();
            return false;
        } else if (txtNumeroDocumento.getText().isEmpty()) {
            Funciones.mensajeAdvertencia("Debe ingresar numero de documento", "Verifique");
            txtNumeroDocumento.requestFocus();
            return false;
        } else if (txtNumeroDocumento.getText().length() < 8) {
            Funciones.mensajeAdvertencia("Debe ingresar numero de documento de 8 caracteres", "Verifique");
            txtNumeroDocumento.requestFocus();
            return false;
        } else if (txtFecha.getDate() == null) {
            Funciones.mensajeAdvertencia("Debe ingresar una fecha", "Verifique");
            txtFecha.requestFocus();
            return false;
        } else if (txtIGV.getText().isEmpty()) {
            Funciones.mensajeAdvertencia("Debe ingresar igv", "Verifique");
            txtIGV.requestFocus();
            return false;
        } else if (txtRuc.getText().isEmpty()) {
            Funciones.mensajeAdvertencia("Debe ingresar ruc del proveedor", "Verifique");
            txtNumeroDocumento.requestFocus();
            return false;
        } else if (tblCompra.getRowCount() <= 0) {
            Funciones.mensajeAdvertencia("Debe agregar articulos para registrar una compra", "Verifique");
            return false;
        }

        int cantidad = 0;
        String articulo = "";
        double descuento = 0;
        for (int i = 0; i < tblCompra.getRowCount(); i++) {
            cantidad = Integer.parseInt(this.tblCompra.getValueAt(i, 2).toString());
            articulo = this.tblCompra.getValueAt(i, 1).toString();
            descuento = Double.parseDouble(this.tblCompra.getValueAt(i, 4).toString());
            if (cantidad == 0) {
                Funciones.mensajeAdvertencia("Debe agregar una cantidad al articulo " + articulo, "Verifique");
                tblCompra.changeSelection(i, 2, false, false);
                tblCompra.requestFocus();
                return false;
            } else if (descuento < 0 || descuento > 100) {
                Funciones.mensajeAdvertencia("Debe ingresar un descuento entre 0 y 100 en el articulo " + articulo, "Verifique");
                tblCompra.changeSelection(i, 4, false, false);
                tblCompra.requestFocus();
                return false;
            }
        }

        return true;
    }

    public void calcularTotales() {
        double porcentajeIGV = 0;
        if (!this.txtIGV.getText().isEmpty()) {
            porcentajeIGV = Double.parseDouble(txtIGV.getText());
        }

        double subTotal = 0;
        double igv = 0;
        double neto = 0;

        for (int i = 0; i < tblCompra.getRowCount(); i++) {
            double importe = Double.parseDouble(tblCompra.getValueAt(i, 5).toString().replace(",", ""));
            neto = neto + importe;
        }

        subTotal = neto / (1 + (porcentajeIGV / 100));

        igv = neto - subTotal;

        lblSubTotal.setText(Funciones.formatearNumero(subTotal));
        lblIGV.setText(Funciones.formatearNumero(igv));
        lblNeto.setText(Funciones.formatearNumero(neto));
    }

    private void obtenerPorcentajeIGV() {
        try {
            String valor = new configuracion().obtenerValorConfiguracion(1);
            txtIGV.setText(valor);
        } catch (Exception e) {
            Funciones.mensajeError(e.getMessage(), Funciones.NOMBRE_SOFTWARE);
        }
    }

    private void configurarCabeceraDetalleCompra() {
        try {
            ResultSet resultado = new compraDetalle().configurarTablaDetalleCompra();
            int anchoCol[] = {80, 400, 80, 80, 100, 110};
            String alineCol[] = {"C", "I", "D", "D", "D", "D"};
            Funciones.llenarTabla(tblCompra, resultado, anchoCol, alineCol);
        } catch (Exception e) {
            Funciones.mensajeInformacion(e.getMessage(), "ERROR...");
        }
    }

    private void cargarComboTipoComprobante() {
        try {
            new tipoComprobante().cargarCombo(cboTipoComprobante);
        } catch (Exception e) {
            Funciones.mensajeError(e.getMessage(), Funciones.NOMBRE_SOFTWARE);
        }
    }

    /*Metodo=2*/
    public void leerDatosRUCoRazonSocial(String codRuc, int num) {
        try {
            ResultSet resultado = new proveedor().leerDatosRUCoRazonSocial(codRuc, num);
            if (resultado.next()) {
                txtRuc.setText(String.valueOf(resultado.getString("ruc_proveedor")));
                txtRazonSocial.setText(String.valueOf(resultado.getString("razon_social")));
                txtDireccion.setText(String.valueOf(resultado.getString("direccion")));
                txtTelefono.setText(String.valueOf(resultado.getString("telefono")));
            }
        } catch (Exception e) {
            Funciones.mensajeError(e.getMessage(), Funciones.NOMBRE_SOFTWARE);
        }
    }

    /*Metodo=1*/
    public void leerDatosRUC(String codRuc) {
        try {
            ResultSet resultado = new proveedor().leerDatosRUC(codRuc);
            if (resultado.next()) {
                txtRuc.setText(String.valueOf(resultado.getString("ruc_proveedor")));
                txtRazonSocial.setText(String.valueOf(resultado.getString("razon_social")));
                txtDireccion.setText(String.valueOf(resultado.getString("direccion")));
                txtTelefono.setText(String.valueOf(resultado.getString("telefono")));
            }
        } catch (Exception e) {
            Funciones.mensajeError(e.getMessage(), Funciones.NOMBRE_SOFTWARE);
        }
    }

    /*Metodo=1*/
    public void leerDatosRazonSocial(String codRazonSocial) {
        try {
            ResultSet resultado = new proveedor().leerDatosRazonSocial(codRazonSocial);
            if (resultado.next()) {
                txtRuc.setText(String.valueOf(resultado.getString("ruc_proveedor")));
                txtRazonSocial.setText(String.valueOf(resultado.getString("razon_social")));
                txtDireccion.setText(String.valueOf(resultado.getString("direccion")));
                txtTelefono.setText(String.valueOf(resultado.getString("telefono")));
            }
        } catch (Exception e) {
            Funciones.mensajeError(e.getMessage(), Funciones.NOMBRE_SOFTWARE);
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jButton4 = new javax.swing.JButton();
        jPanel1 = new javax.swing.JPanel();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        txtRuc = new javax.swing.JTextField();
        jButton1 = new javax.swing.JButton();
        txtRazonSocial = new javax.swing.JTextField();
        txtDireccion = new javax.swing.JTextField();
        jLabel11 = new javax.swing.JLabel();
        txtTelefono = new javax.swing.JTextField();
        btnQuitar = new javax.swing.JButton();
        jButton5 = new javax.swing.JButton();
        btnAgregar = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        /*parte 1*/
        final JTextField field = new JTextField("0");
        final DefaultCellEditor edit = new DefaultCellEditor(field);
        field.setBorder(BorderFactory.createMatteBorder(3,3,3,3,Color.red));
        field.setForeground(Color.blue);
        /*parte 1*/
        tblCompra = new javax.swing.JTable(){
            /*parte 2*/
            public boolean isCellEditable(int fila, int columna){
                if (columna == 2 || columna == 3 || columna == 4){
                    return true;
                }
                return false;
            }

            public TableCellEditor getCellEditor(int row, int col) {
                if (col == 2){
                    field.setDocument(new util.ValidaNumeros());
                }else{
                    field.setDocument(new util.ValidaNumeros(util.ValidaNumeros.ACEPTA_DECIMAL));
                }
                edit.setClickCountToStart(2);
                field.addFocusListener(new FocusAdapter() {
                    public void focusLost(FocusEvent e) {
                        field.select(0,0);
                    }
                });
                return edit;
            }
            /*parte 2*/

        };
        jPanel3 = new javax.swing.JPanel();
        jLabel12 = new javax.swing.JLabel();
        lblSubTotal = new javax.swing.JTextField();
        lblIGV = new javax.swing.JTextField();
        jLabel13 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        lblNeto = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        jTextField1 = new javax.swing.JTextField();
        cboTipoComprobante = new javax.swing.JComboBox();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        txtNumeroSerie = new javax.swing.JTextField();
        txtNumeroDocumento = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        txtFecha = new com.toedter.calendar.JDateChooser();
        txtIGV = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                formKeyPressed(evt);
            }
        });

        jButton4.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jButton4.setText("Salir");
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder("Datos del proveedor"));

        jLabel8.setText("RUC:");

        jLabel9.setText("Razón Social:");

        jLabel10.setText("Dirección:");

        txtRuc.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtRucKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtRucKeyReleased(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtRucKeyTyped(evt);
            }
        });

        jButton1.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jButton1.setText("Buscar");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        txtRazonSocial.setEditable(false);

        txtDireccion.setEditable(false);

        jLabel11.setText("Telefono: ");

        txtTelefono.setEditable(false);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(46, 46, 46)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel10)
                    .addComponent(jLabel9)
                    .addComponent(jLabel8))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtRazonSocial)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(txtRuc, javax.swing.GroupLayout.PREFERRED_SIZE, 159, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(jButton1))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(txtDireccion, javax.swing.GroupLayout.PREFERRED_SIZE, 393, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 66, Short.MAX_VALUE)
                                .addComponent(jLabel11)))
                        .addGap(18, 18, 18)
                        .addComponent(txtTelefono, javax.swing.GroupLayout.PREFERRED_SIZE, 197, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel8)
                    .addComponent(txtRuc, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton1))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel9)
                    .addComponent(txtRazonSocial, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel10)
                    .addComponent(txtDireccion, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel11)
                    .addComponent(txtTelefono, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        btnQuitar.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        btnQuitar.setText("Quitar artículo");
        btnQuitar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnQuitarActionPerformed(evt);
            }
        });

        jButton5.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jButton5.setText("Grabar la compra");
        jButton5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton5ActionPerformed(evt);
            }
        });

        btnAgregar.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        btnAgregar.setText("Agregar artículo");
        btnAgregar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAgregarActionPerformed(evt);
            }
        });

        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder("Artículos registrados de la compra"));

        tblCompra.setModel(new javax.swing.table.DefaultTableModel(
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
        tblCompra.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                tblCompraPropertyChange(evt);
            }
        });
        tblCompra.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                tblCompraKeyPressed(evt);
            }
        });
        jScrollPane1.setViewportView(tblCompra);
        /*parte 3*/
        tblCompra.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                field.setText("");
                field.requestFocus();
            }
        });

        field.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                if (evt.getKeyCode()==10){
                    if (field.getText().isEmpty()){
                        evt.consume();
                    }
                }
            }
        });
        /*parte 3*/

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1)
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 231, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel3.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));

        jLabel12.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel12.setText("Sub. Total:");

        lblSubTotal.setEditable(false);
        lblSubTotal.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        lblSubTotal.setForeground(new java.awt.Color(255, 51, 51));
        lblSubTotal.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        lblSubTotal.setText("0.00");
        lblSubTotal.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));

        lblIGV.setEditable(false);
        lblIGV.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        lblIGV.setForeground(new java.awt.Color(255, 51, 51));
        lblIGV.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        lblIGV.setText("0.00");

        jLabel13.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel13.setText("IGV:");

        jLabel14.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel14.setText("Neto:");

        lblNeto.setEditable(false);
        lblNeto.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        lblNeto.setForeground(new java.awt.Color(255, 51, 51));
        lblNeto.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        lblNeto.setText("0.00");

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel12)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblSubTotal, javax.swing.GroupLayout.DEFAULT_SIZE, 134, Short.MAX_VALUE)
                .addGap(18, 18, 18)
                .addComponent(jLabel13)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblIGV, javax.swing.GroupLayout.PREFERRED_SIZE, 123, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel14)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(lblNeto, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel12)
                    .addComponent(lblSubTotal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel13)
                    .addComponent(lblIGV, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel14)
                    .addComponent(lblNeto, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jLabel1.setText("N° Compra");

        jTextField1.setEditable(false);

        cboTipoComprobante.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        jLabel2.setText("Tipo Comprobante");

        jLabel3.setText("N° Serie");

        txtNumeroSerie.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtNumeroSerieActionPerformed(evt);
            }
        });
        txtNumeroSerie.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtNumeroSerieKeyPressed(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtNumeroSerieKeyTyped(evt);
            }
        });

        txtNumeroDocumento.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtNumeroDocumentoKeyPressed(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtNumeroDocumentoKeyTyped(evt);
            }
        });

        jLabel4.setText("N° Documento");

        jLabel6.setText("Fecha Compra");

        txtIGV.setEditable(false);
        txtIGV.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtIGVKeyPressed(evt);
            }
        });

        jLabel7.setText("IGV (%)");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(btnAgregar)
                        .addGap(18, 18, 18)
                        .addComponent(btnQuitar))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                        .addGap(17, 17, 17)
                        .addComponent(jButton5)
                        .addGap(71, 71, 71)
                        .addComponent(jButton4)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jPanel1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, 93, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel1))
                                .addGap(18, 18, 18)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(cboTipoComprobante, javax.swing.GroupLayout.PREFERRED_SIZE, 123, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel2))
                                .addGap(18, 18, 18)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel3)
                                    .addComponent(txtNumeroSerie, javax.swing.GroupLayout.PREFERRED_SIZE, 77, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(18, 18, 18)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(txtNumeroDocumento, javax.swing.GroupLayout.PREFERRED_SIZE, 93, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel4))
                                .addGap(18, 18, 18)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel6)
                                    .addComponent(txtFecha, javax.swing.GroupLayout.PREFERRED_SIZE, 129, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(18, 18, 18)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel7)
                                    .addComponent(txtIGV, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE))))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel1)
                            .addComponent(jLabel2)
                            .addComponent(jLabel3))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(cboTipoComprobante, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtNumeroSerie, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel4)
                            .addComponent(jLabel6)
                            .addComponent(jLabel7))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(txtNumeroDocumento, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(txtIGV, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(txtFecha, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addGap(18, 18, 18)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnQuitar)
                    .addComponent(btnAgregar))
                .addGap(18, 18, 18)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(29, 29, 29)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jButton4)
                            .addComponent(jButton5))))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void txtNumeroSerieActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtNumeroSerieActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtNumeroSerieActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        frmProveedorBuscar obj = new frmProveedorBuscar(null, true);
        obj.setTitle("Buscar Proveedor");
        obj.setVisible(true);
        /*Metodo=1*/
        /*
         if (obj.valorRetorno2 == 1) {
         try {
         leerDatosRUC(obj.datosBuscar);
         } catch (Exception e) {
         Funciones.mensajeError(e.getMessage(), "Verifique");
         }
         } else if (obj.valorRetorno2 == 2) {
         try {
         leerDatosRazonSocial(obj.datosBuscar);
         } catch (Exception e) {
         Funciones.mensajeError(e.getMessage(), "Verifique");
         }
         }*/

        /*Metodo=2*/
        if (obj.valorRetorno2 > 0) {
            try {
                leerDatosRUCoRazonSocial(obj.datosBuscar, obj.valorRetorno2);
            } catch (Exception e) {
                Funciones.mensajeError(e.getMessage(), "Verifique");
            }
        }
    }//GEN-LAST:event_jButton1ActionPerformed

    private void btnAgregarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAgregarActionPerformed
        /*
         frmArticuloBuscar obj = new frmArticuloBuscar(null, true);
         obj.setTitle("Buscar Artículo");
         obj.setVisible(true);

         int anchoColumnas[] = {90, 300, 80, 220, 220, 130, 85, 80, 90};
         String alinearColumnas[] = {"C", "I", "I", "I", "I", "I", "I", "I", "I"};
         articulo objAr = new articulo();

         if (obj.valorRetorno1 == 1) {
         try {
         resultado = objAr.listarPorCodigo(Integer.parseInt(obj.datosBuscar));
         Funciones.llenarTabla(tblCompra, resultado, anchoColumnas, alinearColumnas);
         } catch (Exception e) {
         Funciones.mensajeError(e.getMessage(), "Verifique");
         }
         } else if (obj.valorRetorno1 == 2) {
         try {
         resultado = objAr.listarPorNombre((obj.datosBuscar));
         Funciones.llenarTabla(tblCompra, resultado, anchoColumnas, alinearColumnas);
         } catch (Exception e) {
         Funciones.mensajeError(e.getMessage(), "Verifique");
         }
         }*/

        frmArticuloBuscar obj = new frmArticuloBuscar(null, true);
        obj.setVisible(true);

        if (obj.valorRetorno1 > 0) {
            int filasel = obj.tblListado.getSelectedRow();
            int codigoArticulo = Integer.parseInt(obj.tblListado.getValueAt(filasel, 0).toString());

            String nombreArticulo = obj.tblListado.getValueAt(filasel, 1).toString();

            int cantidad = 0;
            int precio = 0;
            int descuento = 0;
            int importe = 0;

            DefaultTableModel modelo = (DefaultTableModel) this.tblCompra.getModel();

            Object filaDatos[] = new Object[6];
            filaDatos[0] = codigoArticulo;
            filaDatos[1] = nombreArticulo;
            filaDatos[2] = cantidad;
            filaDatos[3] = precio;
            filaDatos[4] = descuento;
            filaDatos[5] = importe;

            modelo.addRow(filaDatos);

            tblCompra.setModel(modelo);
        }
    }//GEN-LAST:event_btnAgregarActionPerformed

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        dispose();
    }//GEN-LAST:event_jButton4ActionPerformed

    private void txtRucKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtRucKeyPressed

        /*metodo 1*/
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            try {
                leerDatosRUC(txtRuc.getText());
            } catch (Exception e) {
                Funciones.mensajeError(e.getMessage(), "Verifique");
            }
        }

        if (evt.getKeyCode() == KeyEvent.VK_BACK_SPACE) {
            txtRazonSocial.setText("");
            txtDireccion.setText("");
            txtTelefono.setText("");
        }
    }//GEN-LAST:event_txtRucKeyPressed

    private void txtRucKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtRucKeyTyped
        Funciones.soloNumeros(evt, txtRuc, 11);
    }//GEN-LAST:event_txtRucKeyTyped

    private void txtRucKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtRucKeyReleased
        /*metodo 2*/
        if (txtRuc.getText().length() == 11) {
            try {
                leerDatosRUC(txtRuc.getText());
            } catch (Exception e) {
                Funciones.mensajeError(e.getMessage(), "Verifique");
            }
        }
    }//GEN-LAST:event_txtRucKeyReleased

    private void tblCompraPropertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_tblCompraPropertyChange
        if (evt.getPropertyName().equalsIgnoreCase("tableCellEditor")) {
            //System.out.println("Editando datos sobre la tabla");
            int columnaEditar = this.tblCompra.getEditingColumn();
            int filaEditar = this.tblCompra.getEditingRow();

            if (columnaEditar == 2 || columnaEditar == 3 || columnaEditar == 4) {
                int cantidad = Integer.parseInt(this.tblCompra.getValueAt(filaEditar, 2).toString());
                double precio = Double.parseDouble(this.tblCompra.getValueAt(filaEditar, 3).toString());
                double descuento = Double.parseDouble(this.tblCompra.getValueAt(filaEditar, 4).toString());

                double importe = new compraDetalle().calcularImporte(cantidad, precio, descuento);

                this.tblCompra.setValueAt(Funciones.formatearNumero(importe), filaEditar, 5);
                calcularTotales();
            }
        }
    }//GEN-LAST:event_tblCompraPropertyChange

    private void btnQuitarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnQuitarActionPerformed
        DefaultTableModel tablaDetalle
                = (DefaultTableModel) this.tblCompra.getModel();

        int fila = this.tblCompra.getSelectedRow();

        if (fila < 0) {
            Funciones.mensajeError("Debe seleccionar una fila", "Verifique");
            return;
        }

        String articulo = tblCompra.getValueAt(fila, 1).toString();
        int respuesta = Funciones.mensajeConfirmacion(
                "Esta seguro de quitar el artículo: " + articulo,
                "Confirme"
        );

        if (respuesta != 0) {
            return;
        }

        tablaDetalle.removeRow(fila);
        this.tblCompra.setModel(tablaDetalle);

        calcularTotales();
    }//GEN-LAST:event_btnQuitarActionPerformed

    private void tblCompraKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tblCompraKeyPressed
        switch (evt.getKeyCode()) {
            case KeyEvent.VK_DELETE:
                btnQuitar.doClick();
                break;

            case KeyEvent.VK_INSERT:
                btnAgregar.doClick();
                break;
        }
    }//GEN-LAST:event_tblCompraKeyPressed

    private void formKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_formKeyPressed
    }//GEN-LAST:event_formKeyPressed

    private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton5ActionPerformed
        if (validarDatos() == false) {
            return;
        }

        int respuesta = Funciones.mensajeConfirmacion("Desea grabar la compra", "comfirme");
        if (respuesta == 1) {
            return;
        }
        grabarCompra();

    }//GEN-LAST:event_jButton5ActionPerformed

    private void txtNumeroDocumentoKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtNumeroDocumentoKeyTyped
        Funciones.soloNumeros(evt, txtNumeroDocumento, 8);
    }//GEN-LAST:event_txtNumeroDocumentoKeyTyped

    private void txtNumeroDocumentoKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtNumeroDocumentoKeyPressed

    }//GEN-LAST:event_txtNumeroDocumentoKeyPressed

    private void txtNumeroSerieKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtNumeroSerieKeyPressed
        
    }//GEN-LAST:event_txtNumeroSerieKeyPressed

    private void txtIGVKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtIGVKeyPressed
       Funciones.soloNumerosDecimal(evt, txtIGV, 5);
    }//GEN-LAST:event_txtIGVKeyPressed

    private void txtNumeroSerieKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtNumeroSerieKeyTyped
        Funciones.soloNumeros(evt, txtNumeroSerie, 5);
    }//GEN-LAST:event_txtNumeroSerieKeyTyped

    private void grabarCompra() {

        try {
            String codigo_tipo_comprobante = tipoComprobante.listaTipoComp.get(cboTipoComprobante.getSelectedIndex()).getCodigo_tipo_comprobante();
            String ruc_proveedor = txtRuc.getText();
            int numero_serie = Integer.parseInt(txtNumeroSerie.getText());
            int numero_documento = Integer.parseInt(txtNumeroDocumento.getText());
            java.sql.Date fecha_compra = new java.sql.Date(this.txtFecha.getDate().getTime());
            double porcentaje_igv = Double.parseDouble(txtIGV.getText());
            double sub_total = Double.parseDouble(lblSubTotal.getText().replace(",", ""));
            double igv = Double.parseDouble(lblIGV.getText().replace(",", ""));
            double total = Double.parseDouble(lblNeto.getText().replace(",", ""));
            int codigo_usuario = 1; // esta por cambiar

            compra objCompra = new compra();
            objCompra.setCodigo_tipo_comprobante(codigo_tipo_comprobante);
            objCompra.setRuc_proveedor(ruc_proveedor);
            objCompra.setNumero_serie(numero_serie);
            objCompra.setNumero_documento(numero_documento);
            objCompra.setFecha_compra(fecha_compra);
            objCompra.setPorcentaje_igv(porcentaje_igv);
            objCompra.setSub_total(sub_total);
            objCompra.setIgv(igv);
            objCompra.setTotal(total);
            objCompra.setCodigo_usuario(codigo_usuario);

            ArrayList<compraDetalle> articuloDetalleCompra = new ArrayList<compraDetalle>();
            for (int i = 0; i < tblCompra.getRowCount(); i++) {
                int codigo_articulo = Integer.parseInt(tblCompra.getValueAt(i, 0).toString());
                int cantidad = Integer.parseInt(tblCompra.getValueAt(i, 2).toString());;
                double precio = Double.parseDouble(tblCompra.getValueAt(i, 3).toString());;
                double descuento = Double.parseDouble(tblCompra.getValueAt(i, 4).toString());;
                //double importe = Double.parseDouble(tblCompra.getValueAt(i, 5).toString().replace(",", ""));

                compraDetalle objFila = new compraDetalle();
                objFila.setCodigo_articulo(codigo_articulo);
                objFila.setCantidad(cantidad);
                objFila.setPrecio(precio);
                objFila.setDescuento(descuento);
                objFila.setItem(i + 1);

                articuloDetalleCompra.add(objFila);
            }
            objCompra.setArticuloDetalleCompra(articuloDetalleCompra);

            if (objCompra.grabarCompra()) {
                Funciones.mensajeInformacion("Grabado correctamente", "Exito");
                this.valorRetorno = 1;
                this.dispose();
            }

        } catch (Exception e) {
            Funciones.mensajeError(e.getMessage(), "Error");
        }
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAgregar;
    private javax.swing.JButton btnQuitar;
    private javax.swing.JComboBox cboTipoComprobante;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton5;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JTextField lblIGV;
    private javax.swing.JTextField lblNeto;
    private javax.swing.JTextField lblSubTotal;
    private javax.swing.JTable tblCompra;
    private javax.swing.JTextField txtDireccion;
    private com.toedter.calendar.JDateChooser txtFecha;
    private javax.swing.JTextField txtIGV;
    private javax.swing.JTextField txtNumeroDocumento;
    private javax.swing.JTextField txtNumeroSerie;
    private javax.swing.JTextField txtRazonSocial;
    private javax.swing.JTextField txtRuc;
    private javax.swing.JTextField txtTelefono;
    // End of variables declaration//GEN-END:variables
}
