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
import negocio.cliente;
import negocio.configuracion;
import negocio.proveedor;
import negocio.tipoComprobante;
import negocio.venta;
import negocio.ventaDetalle;
import util.Funciones;

/**
 *
 * @author USER
 */
public class frmVenta extends javax.swing.JDialog {

//    private ResultSet resultado;
    public int valorRetorno = 0;

    public frmVenta(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        setLocationRelativeTo(null);

        cargarComboTipoComprobante();
        configurarCabeceraDetalleCompra();
        obtenerPorcentajeIGV();

        tblVenta.setCellSelectionEnabled(true);
        tblVenta.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        tblVenta.setRowHeight(25);

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
        } else if (txtDni.getText().isEmpty()) {
            Funciones.mensajeAdvertencia("Debe ingresar ruc del proveedor", "Verifique");
            txtNumeroDocumento.requestFocus();
            return false;
        } else if (tblVenta.getRowCount() <= 0) {
            Funciones.mensajeAdvertencia("Debe agregar articulos para registrar una compra", "Verifique");
            return false;
        }

        int cantidad = 0;
        String articulo = "";
        double descuento1 = 0;
        double descuento2 = 0;
        
        for (int i = 0; i < tblVenta.getRowCount(); i++) {
            cantidad = Integer.parseInt(this.tblVenta.getValueAt(i, 2).toString());
            articulo = this.tblVenta.getValueAt(i, 1).toString();
            descuento1 = Double.parseDouble(this.tblVenta.getValueAt(i, 4).toString());
            descuento2 = Double.parseDouble(this.tblVenta.getValueAt(i, 5).toString());
            if (cantidad == 0) {
                Funciones.mensajeAdvertencia("Debe agregar una cantidad al articulo " + articulo, "Verifique");
                tblVenta.changeSelection(i, 2, false, false);
                tblVenta.requestFocus();
                return false;
            } else if (descuento1 < 0 || descuento1 > 100) {
                Funciones.mensajeAdvertencia("Debe ingresar un descuento entre 0 y 100 en el articulo " + articulo, "Verifique");
                tblVenta.changeSelection(i, 4, false, false);
                tblVenta.requestFocus();
                return false;
            }else if (descuento2 < 0 || descuento2 > 100) {
                Funciones.mensajeAdvertencia("Debe ingresar un descuento entre 0 y 100 en el articulo " + articulo, "Verifique");
                tblVenta.changeSelection(i, 5, false, false);
                tblVenta.requestFocus();
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

        for (int i = 0; i < tblVenta.getRowCount(); i++) {
            double importe = Double.parseDouble(tblVenta.getValueAt(i, 6).toString().replace(",", ""));
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
            ResultSet resultado = new ventaDetalle().configurarTablaDetalleCompra();
            int anchoCol[] = {80, 400, 80, 80, 100, 100, 110};
            String alineCol[] = {"C", "I", "D", "D", "D", "D", "D"};
            Funciones.llenarTabla(tblVenta, resultado, anchoCol, alineCol);
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

    /*Metodo=1*/
    public void leerDatosDni(String codDni) {
        try {
            ResultSet resultado = new cliente().leerDatosDniApellido(codDni);
            
                txtDni.setText(String.valueOf(resultado.getString("nro_documento_identidad")));
                txtApellidoNombres.setText(String.valueOf(resultado.getString("apellido_paterno"))+" "+String.valueOf(resultado.getString("apellido_materno"))+", "+String.valueOf(resultado.getString("nombres")));
                txtDireccion.setText(String.valueOf(resultado.getString("direccion")));
                txtTelefono.setText(String.valueOf("fijo: "+resultado.getString("telefono_fijo"))+" / movil1: "+String.valueOf(resultado.getString("telefono_movil1"))+" / movil2: "+String.valueOf(resultado.getString("telefono_movil2")));
            
        } catch (Exception e) {
            Funciones.mensajeError(e.getMessage(), Funciones.NOMBRE_SOFTWARE);
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jButton4 = new javax.swing.JButton();
        jPanel1 = new javax.swing.JPanel();
        sfd = new javax.swing.JLabel();
        sdf = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        txtDni = new javax.swing.JTextField();
        jButton1 = new javax.swing.JButton();
        txtApellidoNombres = new javax.swing.JTextField();
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
        tblVenta = new javax.swing.JTable(){
            /*parte 2*/
            public boolean isCellEditable(int fila, int columna){
                if (columna == 2 || columna == 3 || columna == 4|| columna == 5){
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

        sfd.setText("Dni");

        sdf.setText("Apellidos y Nombres");

        jLabel10.setText("Dirección:");

        txtDni.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtDniKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtDniKeyReleased(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtDniKeyTyped(evt);
            }
        });

        jButton1.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jButton1.setText("Buscar");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        txtApellidoNombres.setEditable(false);

        txtDireccion.setEditable(false);

        jLabel11.setText("Telefono: ");

        txtTelefono.setEditable(false);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(46, 46, 46)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel10)
                            .addComponent(sdf)
                            .addComponent(sfd))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtApellidoNombres)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addComponent(txtDni, javax.swing.GroupLayout.PREFERRED_SIZE, 159, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(18, 18, 18)
                                        .addComponent(jButton1))
                                    .addComponent(txtDireccion, javax.swing.GroupLayout.PREFERRED_SIZE, 393, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(0, 0, Short.MAX_VALUE)))
                        .addContainerGap())
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(49, 49, 49)
                        .addComponent(jLabel11)
                        .addGap(18, 18, 18)
                        .addComponent(txtTelefono, javax.swing.GroupLayout.PREFERRED_SIZE, 514, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(217, Short.MAX_VALUE))))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(sfd)
                    .addComponent(txtDni, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton1))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(sdf)
                    .addComponent(txtApellidoNombres, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel10)
                    .addComponent(txtDireccion, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
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
        jButton5.setText("Grabar la venta");
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

        tblVenta.setModel(new javax.swing.table.DefaultTableModel(
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
        tblVenta.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                tblVentaPropertyChange(evt);
            }
        });
        tblVenta.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                tblVentaKeyPressed(evt);
            }
        });
        jScrollPane1.setViewportView(tblVenta);
        /*parte 3*/
        tblVenta.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
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
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnQuitar)
                    .addComponent(btnAgregar))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(11, 11, 11)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jButton4)
                            .addComponent(jButton5))))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void txtNumeroSerieActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtNumeroSerieActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtNumeroSerieActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        frmClienteBuscar obj = new frmClienteBuscar(null, true);
        obj.setTitle("Buscar Cliente");
        obj.setVisible(true);
        /*Metodo=1*/
        /*
         if (obj.valorRetorno2 == 1) {
         try {
         leerDatosDni(obj.datosBuscar);
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
        if (obj.valorRetorno1 ==1) {
            
            int filaSelecciona = obj.tblListado.getSelectedRow();
            String dni = obj.tblListado.getValueAt(filaSelecciona, 2).toString();
            String nombres = obj.tblListado.getValueAt(filaSelecciona, 1).toString();
            String direccion = obj.tblListado.getValueAt(filaSelecciona, 3).toString();
            String telefonofijo = obj.tblListado.getValueAt(filaSelecciona, 4).toString();
            String telefonomovil1 = obj.tblListado.getValueAt(filaSelecciona, 4).toString();
            String telefonomovil2 = obj.tblListado.getValueAt(filaSelecciona, 4).toString();
            
            txtDni.setText(dni);
            txtApellidoNombres.setText(nombres);
            txtDireccion.setText(direccion);
            txtTelefono.setText("fijo: "+telefonofijo+" / movil1: "+telefonomovil1+" / movil2: "+telefonomovil2);
        }
    }//GEN-LAST:event_jButton1ActionPerformed

    private void btnAgregarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAgregarActionPerformed
        
        frmArticuloBuscar obj = new frmArticuloBuscar(null, true);
        obj.setVisible(true);

        if (obj.valorRetorno1 > 0) {
            int filasel = obj.tblListado.getSelectedRow();
            int codigoArticulo = Integer.parseInt(obj.tblListado.getValueAt(filasel, 0).toString());

            String nombreArticulo = obj.tblListado.getValueAt(filasel, 1).toString();

            int cantidad = 0;
            int precio = 0;
            int descuento1 = 0;
            int descuento2 = 0;
            int importe = 0;

            DefaultTableModel modelo = (DefaultTableModel) this.tblVenta.getModel();

            Object filaDatos[] = new Object[7];
            filaDatos[0] = codigoArticulo;
            filaDatos[1] = nombreArticulo;
            filaDatos[2] = cantidad;
            filaDatos[3] = precio;
            filaDatos[4] = descuento1;
            filaDatos[5] = descuento2;
            filaDatos[6] = importe;

            modelo.addRow(filaDatos);

            tblVenta.setModel(modelo);
        }
    }//GEN-LAST:event_btnAgregarActionPerformed

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        dispose();
    }//GEN-LAST:event_jButton4ActionPerformed

    private void txtDniKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtDniKeyPressed

        /*metodo 1*/
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            try {
                leerDatosDni(txtDni.getText());
            } catch (Exception e) {
                Funciones.mensajeError(e.getMessage(), "Verifique");
            }
        }

        if (evt.getKeyCode() == KeyEvent.VK_BACK_SPACE) {
            txtApellidoNombres.setText("");
            txtDireccion.setText("");
            txtTelefono.setText("");
        }
    }//GEN-LAST:event_txtDniKeyPressed

    private void txtDniKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtDniKeyTyped
        Funciones.soloNumeros(evt, txtDni, 8);
    }//GEN-LAST:event_txtDniKeyTyped

    private void txtDniKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtDniKeyReleased
        /*metodo 2*/
        if (txtDni.getText().length() == 8) {
            try {
                leerDatosDni(txtDni.getText());
            } catch (Exception e) {
                Funciones.mensajeError(e.getMessage(), "Verifique");
            }
        }
    }//GEN-LAST:event_txtDniKeyReleased

    private void tblVentaPropertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_tblVentaPropertyChange

        if (evt.getPropertyName().equalsIgnoreCase("tableCellEditor")) {
            //System.out.println("Editando datos sobre la tabla");
            int columnaEditar = this.tblVenta.getEditingColumn();
            int filaEditar = this.tblVenta.getEditingRow();

            if (columnaEditar == 2 || columnaEditar == 3 || columnaEditar == 4|| columnaEditar == 5) {
                int cantidad = Integer.parseInt(this.tblVenta.getValueAt(filaEditar, 2).toString());
                double precio = Double.parseDouble(this.tblVenta.getValueAt(filaEditar, 3).toString());
                double descuento1 = Double.parseDouble(this.tblVenta.getValueAt(filaEditar, 4).toString());
                double descuento2 = Double.parseDouble(this.tblVenta.getValueAt(filaEditar, 5).toString());

                double importe = new ventaDetalle().calcularImporte(cantidad, precio, descuento1,descuento2);

                this.tblVenta.setValueAt(Funciones.formatearNumero(importe), filaEditar, 6);
                calcularTotales();
            }
        }
    }//GEN-LAST:event_tblVentaPropertyChange

    private void btnQuitarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnQuitarActionPerformed
        DefaultTableModel tablaDetalle
                = (DefaultTableModel) this.tblVenta.getModel();

        int fila = this.tblVenta.getSelectedRow();

        if (fila < 0) {
            Funciones.mensajeError("Debe seleccionar una fila", "Verifique");
            return;
        }

        String articulo = tblVenta.getValueAt(fila, 1).toString();
        int respuesta = Funciones.mensajeConfirmacion(
                "Esta seguro de quitar el artículo: " + articulo,
                "Confirme"
        );

        if (respuesta != 0) {
            return;
        }

        tablaDetalle.removeRow(fila);
        this.tblVenta.setModel(tablaDetalle);

        calcularTotales();
    }//GEN-LAST:event_btnQuitarActionPerformed

    private void tblVentaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tblVentaKeyPressed
        switch (evt.getKeyCode()) {
            case KeyEvent.VK_DELETE:
                btnQuitar.doClick();
                break;

            case KeyEvent.VK_INSERT:
                btnAgregar.doClick();
                break;
        }
    }//GEN-LAST:event_tblVentaKeyPressed

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
        grabarVenta();

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

    private void grabarVenta() {

        try {
            String codigo_tipo_comprobante = tipoComprobante.listaTipoComp.get(cboTipoComprobante.getSelectedIndex()).getCodigo_tipo_comprobante();
            String dni = txtDni.getText();   
            cliente cli = new cliente();
            ResultSet resultado = cli.leerDatosDniApellido(dni);
            int codigo_cliente = resultado.getInt("codigo_cliente");
            int numero_serie = Integer.parseInt(txtNumeroSerie.getText());
            int numero_documento = Integer.parseInt(txtNumeroDocumento.getText());
            java.sql.Date fecha_compra = new java.sql.Date(this.txtFecha.getDate().getTime());
            double porcentaje_igv = Double.parseDouble(txtIGV.getText());
            double sub_total = Double.parseDouble(lblSubTotal.getText().replace(",", ""));
            double igv = Double.parseDouble(lblIGV.getText().replace(",", ""));
            double total = Double.parseDouble(lblNeto.getText().replace(",", ""));
            int codigo_usuario = 1; // esta por cambiar

            venta objCompra = new venta();
            objCompra.setCodigo_cliente(codigo_cliente);
            objCompra.setCodigo_tipo_comprobante(codigo_tipo_comprobante);
            objCompra.setNumero_serie(numero_serie);
            objCompra.setNumero_documento(numero_documento);
            objCompra.setFecha_compra(fecha_compra);
            objCompra.setPorcentaje_igv(porcentaje_igv);
            objCompra.setSub_total(sub_total);
            objCompra.setIgv(igv);
            objCompra.setTotal(total);
            objCompra.setCodigo_usuario(codigo_usuario);

            ArrayList<ventaDetalle> articuloDetalleCompra = new ArrayList<ventaDetalle>();
            for (int i = 0; i < tblVenta.getRowCount(); i++) {
                int codigo_articulo = Integer.parseInt(tblVenta.getValueAt(i, 0).toString());
                int cantidad = Integer.parseInt(tblVenta.getValueAt(i, 2).toString());;
                double precio = Double.parseDouble(tblVenta.getValueAt(i, 3).toString());;
                double descuento1 = Double.parseDouble(tblVenta.getValueAt(i, 4).toString());;
                double descuento2 = Double.parseDouble(tblVenta.getValueAt(i, 5).toString());;
//                double importe = Double.parseDouble(tblVenta.getValueAt(i, 6).toString().replace(",", ""));

                ventaDetalle objFila = new ventaDetalle();
                objFila.setCodigo_articulo(codigo_articulo);
                objFila.setCantidad(cantidad);
                objFila.setPrecio(precio);
                objFila.setDescuento1(descuento1);
                objFila.setDescuento2(descuento2);
                objFila.setItem(i + 1);

                articuloDetalleCompra.add(objFila);
            }
            objCompra.setArticuloDetalleCompra(articuloDetalleCompra);

            if (objCompra.grabarVenta()) {
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
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JTextField lblIGV;
    private javax.swing.JTextField lblNeto;
    private javax.swing.JTextField lblSubTotal;
    private javax.swing.JLabel sdf;
    private javax.swing.JLabel sfd;
    private javax.swing.JTable tblVenta;
    private javax.swing.JTextField txtApellidoNombres;
    private javax.swing.JTextField txtDireccion;
    private javax.swing.JTextField txtDni;
    private com.toedter.calendar.JDateChooser txtFecha;
    private javax.swing.JTextField txtIGV;
    private javax.swing.JTextField txtNumeroDocumento;
    private javax.swing.JTextField txtNumeroSerie;
    private javax.swing.JTextField txtTelefono;
    // End of variables declaration//GEN-END:variables
}
