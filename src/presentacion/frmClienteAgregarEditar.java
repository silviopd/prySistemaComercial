package presentacion;

import java.awt.event.KeyEvent;
import java.sql.ResultSet;
import negocio.cliente;
import negocio.departamento;
import negocio.distrito;
import negocio.provincia;
import util.Funciones;

public class frmClienteAgregarEditar extends javax.swing.JDialog {

    public String operacion;
    public int valorRetorno = 0;
    
    public frmClienteAgregarEditar(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        this.setLocationRelativeTo(null);
        cargarComboDpto();
        txtApellidoPaterno.requestFocus();
    }
    
    private void cargarComboDpto() {
        try {
            new departamento().cargarCombo(cboDepartamento);
        } catch (Exception e) {
            Funciones.mensajeError(e.getMessage(), Funciones.NOMBRE_SOFTWARE);
        }
    }
    
    private void cargarComboProvincia(String codigoDepartamento) {
        try {
            new provincia().cargarCombo(cboProvincia,codigoDepartamento);
        } catch (Exception e) {
            Funciones.mensajeError(e.getMessage(), Funciones.NOMBRE_SOFTWARE);
        }
    }
    
    private void cargarComboDistrito(String codigoDepartamento,String codigoProvincia) {
        try {
            new distrito().cargarCombo(cboDistrito,codigoDepartamento,codigoProvincia);
        } catch (Exception e) {
            Funciones.mensajeError(e.getMessage(), Funciones.NOMBRE_SOFTWARE);
        }
    }
    
    public void leerDatosCliente(int codCat){
        try {
            ResultSet resultado = new cliente().leerDatos(codCat);
            if (resultado.next()) {
                lblCodigo.setText(String.valueOf(resultado.getInt("codigo")));
                txtApellidoPaterno.setText(String.valueOf(resultado.getString("apellido_paterno")));
                txtApellidoMaterno.setText(String.valueOf(resultado.getString("apellido_materno")));
                txtNombres.setText(String.valueOf(resultado.getString("nombres")));
                txtDni.setText(String.valueOf(resultado.getString("dni")));
                txtDireccion.setText(String.valueOf(resultado.getString("direccion")));
                txtTelefonoFijo.setText(String.valueOf(resultado.getString("telefono_fijo")));
                txtTelefonoMovil1.setText(String.valueOf(resultado.getString("telefono_movil1")));
                txtTelefonoMovil2.setText(String.valueOf(resultado.getString("telefono_movil2")));
                txtEmail.setText(String.valueOf(resultado.getString("email")));
                txtDireccionWeb.setText(String.valueOf(resultado.getString("direccion_web")));
                
                cboDepartamento.setSelectedItem(String.valueOf(resultado.getString("departamento")));
                cboProvincia.setSelectedItem(String.valueOf(resultado.getString("provincia")));
                cboDistrito.setSelectedItem(String.valueOf(resultado.getString("distrito")));
            }
        } catch (Exception e) {
            Funciones.mensajeError(e.getMessage(), Funciones.NOMBRE_SOFTWARE);
        }
    }
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        cboDepartamento = new javax.swing.JComboBox();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        cboProvincia = new javax.swing.JComboBox();
        jLabel4 = new javax.swing.JLabel();
        cboDistrito = new javax.swing.JComboBox();
        jLabel5 = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        txtApellidoPaterno = new javax.swing.JTextField();
        txtApellidoMaterno = new javax.swing.JTextField();
        txtNombres = new javax.swing.JTextField();
        txtDni = new javax.swing.JTextField();
        txtDireccion = new javax.swing.JTextField();
        txtTelefonoMovil1 = new javax.swing.JTextField();
        txtTelefonoMovil2 = new javax.swing.JTextField();
        txtEmail = new javax.swing.JTextField();
        txtTelefonoFijo = new javax.swing.JTextField();
        jLabel15 = new javax.swing.JLabel();
        txtDireccionWeb = new javax.swing.JTextField();
        lblCodigo = new javax.swing.JTextField();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setBackground(new java.awt.Color(255, 255, 255));

        jPanel1.setBackground(new java.awt.Color(153, 255, 255));

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/1454137630_malecostume.png"))); // NOI18N
        jLabel1.setText("Mantenimiento de Clientes");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel1)
                .addGap(308, 308, 308))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addComponent(jLabel1)
                .addContainerGap(32, Short.MAX_VALUE))
        );

        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Ubicación Geográfico", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 11), new java.awt.Color(51, 51, 255))); // NOI18N

        cboDepartamento.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        cboDepartamento.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cboDepartamentoActionPerformed(evt);
            }
        });

        jLabel2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/1454126797_map-marker.png"))); // NOI18N
        jLabel2.setText("Departamento");

        jLabel3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/1454126797_map-marker.png"))); // NOI18N
        jLabel3.setText("Provincia");

        cboProvincia.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        cboProvincia.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cboProvinciaActionPerformed(evt);
            }
        });

        jLabel4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/1454126797_map-marker.png"))); // NOI18N
        jLabel4.setText("Distrito");

        cboDistrito.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(37, 37, 37)
                        .addComponent(jLabel2))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel3, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel4, javax.swing.GroupLayout.Alignment.TRAILING))))
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(cboDepartamento, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(cboProvincia, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(cboDistrito, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(cboDepartamento, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(cboProvincia, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(11, 11, 11)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(cboDistrito, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jLabel5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/1454127066_internt_web_technology-13.png"))); // NOI18N
        jLabel5.setText("Codigo");

        jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Datos Personales", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 11), new java.awt.Color(51, 51, 255))); // NOI18N

        jLabel6.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/cliente.png"))); // NOI18N
        jLabel6.setText("Apellidos Paterno");

        jLabel7.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/cliente.png"))); // NOI18N
        jLabel7.setText("Apellido Materno");

        jLabel8.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/cliente.png"))); // NOI18N
        jLabel8.setText("Nombres");

        jLabel9.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/1454126791_user-id.png"))); // NOI18N
        jLabel9.setText("Dni");

        jLabel10.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/1454126794_map.png"))); // NOI18N
        jLabel10.setText("Direccion");

        jLabel11.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/1454126548_business-telephone-office-glyph.png"))); // NOI18N
        jLabel11.setText("Telefono Fijo");

        jLabel12.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/1454126552_phone.png"))); // NOI18N
        jLabel12.setText("Telefono Movil 1");

        jLabel13.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/1454126552_phone.png"))); // NOI18N
        jLabel13.setText("Telefono Movil 2");

        jLabel14.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/1454126471_mail.png"))); // NOI18N
        jLabel14.setText("Email");

        txtApellidoPaterno.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtApellidoPaternoFocusLost(evt);
            }
        });
        txtApellidoPaterno.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtApellidoPaternoKeyTyped(evt);
            }
        });

        txtApellidoMaterno.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtApellidoMaternoFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtApellidoMaternoFocusLost(evt);
            }
        });
        txtApellidoMaterno.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtApellidoMaternoKeyTyped(evt);
            }
        });

        txtNombres.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtNombresFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtNombresFocusLost(evt);
            }
        });
        txtNombres.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtNombresKeyTyped(evt);
            }
        });

        txtDni.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtDniFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtDniFocusLost(evt);
            }
        });
        txtDni.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtDniKeyTyped(evt);
            }
        });

        txtDireccion.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtDireccionFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtDireccionFocusLost(evt);
            }
        });

        txtTelefonoMovil1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtTelefonoMovil1KeyTyped(evt);
            }
        });

        txtTelefonoMovil2.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtTelefonoMovil2KeyTyped(evt);
            }
        });

        txtEmail.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtEmailKeyPressed(evt);
            }
        });

        txtTelefonoFijo.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtTelefonoFijoFocusGained(evt);
            }
        });
        txtTelefonoFijo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtTelefonoFijoActionPerformed(evt);
            }
        });
        txtTelefonoFijo.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtTelefonoFijoKeyTyped(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(21, 21, 21)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel14)
                    .addComponent(jLabel13)
                    .addComponent(jLabel12)
                    .addComponent(jLabel11)
                    .addComponent(jLabel10)
                    .addComponent(jLabel9)
                    .addComponent(jLabel8)
                    .addComponent(jLabel7)
                    .addComponent(jLabel6))
                .addGap(18, 18, 18)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtApellidoPaterno, javax.swing.GroupLayout.DEFAULT_SIZE, 383, Short.MAX_VALUE)
                    .addComponent(txtApellidoMaterno)
                    .addComponent(txtNombres)
                    .addComponent(txtDireccion)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(txtDni)
                            .addComponent(txtTelefonoFijo, javax.swing.GroupLayout.DEFAULT_SIZE, 189, Short.MAX_VALUE)
                            .addComponent(txtTelefonoMovil1)
                            .addComponent(txtTelefonoMovil2)
                            .addComponent(txtEmail))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(txtApellidoPaterno, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7)
                    .addComponent(txtApellidoMaterno, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel8)
                    .addComponent(txtNombres, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel9)
                    .addComponent(txtDni, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel10)
                    .addComponent(txtDireccion, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel11)
                    .addComponent(txtTelefonoFijo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel12)
                    .addComponent(txtTelefonoMovil1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel13)
                    .addComponent(txtTelefonoMovil2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel14)
                    .addComponent(txtEmail, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
        );

        jLabel15.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/1454127188_language.png"))); // NOI18N
        jLabel15.setText("Dirección Web");

        lblCodigo.setEditable(false);

        jButton1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/guardar.png"))); // NOI18N
        jButton1.setText("Guardar");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jButton2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/salir2.png"))); // NOI18N
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
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(90, 90, 90)
                        .addComponent(jLabel5)
                        .addGap(18, 18, 18)
                        .addComponent(lblCodigo, javax.swing.GroupLayout.PREFERRED_SIZE, 381, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(26, 26, 26)
                        .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addContainerGap())
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 67, Short.MAX_VALUE)
                        .addComponent(jLabel15)
                        .addGap(18, 18, 18)
                        .addComponent(txtDireccionWeb, javax.swing.GroupLayout.PREFERRED_SIZE, 263, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(27, 27, 27))))
            .addGroup(layout.createSequentialGroup()
                .addGap(393, 393, 393)
                .addComponent(jButton1)
                .addGap(141, 141, 141)
                .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 91, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(lblCodigo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtDireccionWeb, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel15))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 48, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton2)
                    .addComponent(jButton1))
                .addGap(25, 25, 25))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void cboDepartamentoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cboDepartamentoActionPerformed
        if (cboDepartamento.getSelectedIndex()>=0) {
            String codigoDepartamento = departamento.listaDpto.get(cboDepartamento.getSelectedIndex()).getCodigo_departamento();
            cargarComboProvincia(codigoDepartamento);
        }
    }//GEN-LAST:event_cboDepartamentoActionPerformed

    private void cboProvinciaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cboProvinciaActionPerformed
       if (cboProvincia.getSelectedIndex()>=0 && cboDepartamento.getSelectedIndex()>=0 ) {
            String codigoDepartamento = departamento.listaDpto.get(cboDepartamento.getSelectedIndex()).getCodigo_departamento();
            String codigoProvincia = provincia.listaProvincia.get(cboProvincia.getSelectedIndex()).getCodigo_provincia();
            cargarComboDistrito(codigoDepartamento, codigoProvincia);
        }
    }//GEN-LAST:event_cboProvinciaActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        dispose();
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        
        try {
            cliente obj = new cliente();
            obj.setApellido_paterno(txtApellidoPaterno.getText().toUpperCase());
            obj.setApellido_materno(txtApellidoMaterno.getText().toUpperCase());
            obj.setNombres(txtNombres.getText().toUpperCase());
            obj.setNro_documento_identidad(txtDni.getText());
            obj.setDireccion(txtDireccion.getText().toUpperCase());
            obj.setTelefono_fijo(txtTelefonoFijo.getText());
            obj.setTelefono_movil1(txtTelefonoMovil1.getText());
            obj.setTelefono_movil2(txtTelefonoMovil2.getText());
            obj.setEmail(txtEmail.getText());
            obj.setDireccion_web(txtDireccionWeb.getText().toUpperCase());
            
            obj.setCodigo_departamento(departamento.listaDpto.get(cboDepartamento.getSelectedIndex()).getCodigo_departamento());           
            obj.setCodigo_provincia(provincia.listaProvincia.get(cboProvincia.getSelectedIndex()).getCodigo_provincia());           
            obj.setCodigo_distrito(distrito.listaDistrito.get(cboDistrito.getSelectedIndex()).getCodigo_distrito());           
            

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
            } 
            else if (operacion.equalsIgnoreCase("Editar")) {

                obj.setCodigo_cliente(Integer.parseInt(lblCodigo.getText()));

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
        
    }//GEN-LAST:event_jButton1ActionPerformed

    private void txtDniKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtDniKeyTyped
        Funciones.soloNumeros(evt, txtDni, 8);
    }//GEN-LAST:event_txtDniKeyTyped

    private void txtApellidoPaternoKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtApellidoPaternoKeyTyped
        Funciones.soloLetras(evt, txtApellidoPaterno, 100);
    }//GEN-LAST:event_txtApellidoPaternoKeyTyped

    private void txtApellidoMaternoKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtApellidoMaternoKeyTyped
        Funciones.soloLetras(evt, txtApellidoMaterno, 100);
    }//GEN-LAST:event_txtApellidoMaternoKeyTyped

    private void txtNombresKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtNombresKeyTyped
        Funciones.soloLetras(evt, txtNombres, 100);
    }//GEN-LAST:event_txtNombresKeyTyped

    private void txtTelefonoFijoKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtTelefonoFijoKeyTyped
        Funciones.soloNumerosConCaracter(evt, txtTelefonoFijo, 10);
    }//GEN-LAST:event_txtTelefonoFijoKeyTyped

    private void txtTelefonoFijoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtTelefonoFijoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtTelefonoFijoActionPerformed

    private void txtTelefonoMovil1KeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtTelefonoMovil1KeyTyped
        Funciones.soloNumerosConCaracter(evt, txtTelefonoMovil1, 10);
    }//GEN-LAST:event_txtTelefonoMovil1KeyTyped

    private void txtTelefonoMovil2KeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtTelefonoMovil2KeyTyped
        Funciones.soloNumerosConCaracter(evt, txtTelefonoMovil2, 10);
    }//GEN-LAST:event_txtTelefonoMovil2KeyTyped

    private void txtApellidoPaternoFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtApellidoPaternoFocusLost

    }//GEN-LAST:event_txtApellidoPaternoFocusLost

    private void txtApellidoMaternoFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtApellidoMaternoFocusLost
        
    }//GEN-LAST:event_txtApellidoMaternoFocusLost

    private void txtNombresFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtNombresFocusLost
        
    }//GEN-LAST:event_txtNombresFocusLost

    private void txtDniFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtDniFocusLost
       
    }//GEN-LAST:event_txtDniFocusLost

    private void txtDireccionFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtDireccionFocusLost
        
    }//GEN-LAST:event_txtDireccionFocusLost

    private void txtEmailKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtEmailKeyPressed
        if (evt.getKeyCode()==KeyEvent.VK_TAB) {
            this.txtDireccionWeb.requestFocus();
        }
    }//GEN-LAST:event_txtEmailKeyPressed

    private void txtApellidoMaternoFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtApellidoMaternoFocusGained
        if (txtApellidoPaterno.getText().isEmpty()) {            
            txtApellidoPaterno.requestFocus();
            Funciones.mensajeAdvertencia("Apellido Paterno Vacío...", "Verifique");
        }
    }//GEN-LAST:event_txtApellidoMaternoFocusGained

    private void txtNombresFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtNombresFocusGained
        if (txtApellidoMaterno.getText().isEmpty()) {            
            txtApellidoMaterno.requestFocus();
            Funciones.mensajeAdvertencia("Apellido Materno Vacío...", "Verifique");
        }
    }//GEN-LAST:event_txtNombresFocusGained

    private void txtDniFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtDniFocusGained
        if (txtNombres.getText().isEmpty()) {
            txtNombres.requestFocus();
            Funciones.mensajeAdvertencia("Nombres Vacío...", "Verifique");
        }
    }//GEN-LAST:event_txtDniFocusGained

    private void txtDireccionFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtDireccionFocusGained
         if (txtDni.getText().isEmpty()) {
            txtDni.requestFocus();
            Funciones.mensajeAdvertencia("Dni Vacío...", "Verifique");
        }
    }//GEN-LAST:event_txtDireccionFocusGained

    private void txtTelefonoFijoFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtTelefonoFijoFocusGained
        if (txtDireccion.getText().isEmpty()) {
            txtDireccion.requestFocus();
            Funciones.mensajeAdvertencia("Dirección Vacío...", "Verifique");
        }
    }//GEN-LAST:event_txtTelefonoFijoFocusGained

  

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JComboBox cboDepartamento;
    private javax.swing.JComboBox cboDistrito;
    private javax.swing.JComboBox cboProvincia;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JTextField lblCodigo;
    private javax.swing.JTextField txtApellidoMaterno;
    private javax.swing.JTextField txtApellidoPaterno;
    private javax.swing.JTextField txtDireccion;
    private javax.swing.JTextField txtDireccionWeb;
    private javax.swing.JTextField txtDni;
    private javax.swing.JTextField txtEmail;
    private javax.swing.JTextField txtNombres;
    private javax.swing.JTextField txtTelefonoFijo;
    private javax.swing.JTextField txtTelefonoMovil1;
    private javax.swing.JTextField txtTelefonoMovil2;
    // End of variables declaration//GEN-END:variables
}
