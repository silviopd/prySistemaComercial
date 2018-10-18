/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package presentacion;

import java.awt.Dimension;
import java.sql.ResultSet;
import java.util.ArrayList;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import negocio.cliente;
import negocio.departamento;
import negocio.distrito;
import negocio.provincia;
import util.Funciones;

/**
 *
 * @author USER
 */
public class frmClienteListadoJtree extends javax.swing.JInternalFrame {

    private ResultSet resultado;

    public frmClienteListadoJtree() {
        initComponents();

        cboBusqueda.setPreferredSize(new Dimension(140, 30));
        txtValorBusqueda.setPreferredSize(new Dimension(200, 30));
        this.tbOpciones.add(cboBusqueda, 1);
        this.tbOpciones.add(txtValorBusqueda, 2);
        txtValorBusqueda.requestFocus();

        llenarComboBusqueda();
        cargarDatosConsulta();
        filtrar();

        cargarArbol();

        cboBusqueda.setSelectedIndex(1);

    }

    private void llenarComboBusqueda() {
        String campos[] = new cliente().obtenerCamposBusqueda();
        cboBusqueda.removeAllItems();
        for (int i = 0; i < campos.length; i++) {
            String campo = campos[i];
            cboBusqueda.addItem(campo);
        }
    }

    private void cargarDatosConsulta() {
        try {
            resultado = new cliente().listar();
        } catch (Exception e) {
            Funciones.mensajeError(e.getMessage(), Funciones.NOMBRE_SOFTWARE);
        }
    }

    private void filtrar() {
        try {
            int anchoColumnas[] = {140, 300, 90, 220, 180, 180, 180, 300, 300, 400};
            String alinearColumnas[] = {"C", "I", "I", "I", "I", "I", "I", "I", "I", "I",};
            Funciones.llenarTablaBusqueda(tblListado, resultado, anchoColumnas, alinearColumnas, cboBusqueda.getSelectedItem().toString(), txtValorBusqueda.getText());
        } catch (Exception e) {
            Funciones.mensajeError(e.getMessage(), Funciones.NOMBRE_SOFTWARE);
        }
    }

    private void cargarArbol() {
        try {
            new departamento().cargarTabla();
            DefaultMutableTreeNode raiz = new DefaultMutableTreeNode("Departamentos");
            ArrayList<departamento> departamentos = departamento.listaDpto;

            for (departamento depart : departamentos) {
                DefaultMutableTreeNode nodeDepartamento = new DefaultMutableTreeNode();
                nodeDepartamento.setUserObject(depart.getNombre());
                
                provincia objProvincia = new provincia();
                objProvincia.cargarTabla(depart.getCodigo_departamento());
                ArrayList<provincia> provincias = provincia.listaProvincia;

                for (provincia prov : provincias) {
                    DefaultMutableTreeNode nodeProvincias = new DefaultMutableTreeNode();
                    nodeProvincias.setUserObject(prov.getNombre());
                    nodeDepartamento.add(nodeProvincias);

                    distrito objDistrito = new distrito();
                    objDistrito.cargarTabla(depart.getCodigo_departamento(), prov.getCodigo_provincia());
                    ArrayList<distrito> distritos = distrito.listaDistrito;

                    for (distrito dist : distritos) {
                        DefaultMutableTreeNode nodeDistritos = new DefaultMutableTreeNode();
                        nodeDistritos.setUserObject(dist.getNombre());
                        nodeProvincias.add(nodeDistritos);
                    }
                }

                raiz.add(nodeDepartamento);
            }

            DefaultTreeModel model = new DefaultTreeModel(raiz);
            treeArticulos.setModel(model);

        } catch (Exception e) {
            Funciones.mensajeError(e.getMessage(), "Verifique");
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        treeArticulos = new javax.swing.JTree();
        jScrollPane2 = new javax.swing.JScrollPane();
        tblListado = new javax.swing.JTable(){
            public boolean isCellEditable(int fila,int columna){
                return false;
            }
        };
        tbOpciones = new javax.swing.JToolBar();
        jLabel1 = new javax.swing.JLabel();
        jSeparator1 = new javax.swing.JToolBar.Separator();
        cboBusqueda = new javax.swing.JComboBox();
        jLabel2 = new javax.swing.JLabel();
        txtValorBusqueda = new javax.swing.JTextField();
        btnAgregar = new javax.swing.JButton();
        btnEditar = new javax.swing.JButton();
        btnEliminar = new javax.swing.JButton();
        btnRefrescar = new javax.swing.JButton();
        btnSalir = new javax.swing.JButton();

        treeArticulos.addTreeSelectionListener(new javax.swing.event.TreeSelectionListener() {
            public void valueChanged(javax.swing.event.TreeSelectionEvent evt) {
                treeArticulosValueChanged(evt);
            }
        });
        jScrollPane1.setViewportView(treeArticulos);

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
        jScrollPane2.setViewportView(tblListado);

        tbOpciones.setFloatable(false);
        tbOpciones.setRollover(true);

        jLabel1.setText("Buscar");
        tbOpciones.add(jLabel1);
        tbOpciones.add(jSeparator1);

        cboBusqueda.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        tbOpciones.add(cboBusqueda);

        jLabel2.setText("     Valor:  ");
        tbOpciones.add(jLabel2);

        txtValorBusqueda.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtValorBusquedaActionPerformed(evt);
            }
        });
        txtValorBusqueda.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtValorBusquedaKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtValorBusquedaKeyReleased(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtValorBusquedaKeyTyped(evt);
            }
        });
        tbOpciones.add(txtValorBusqueda);

        btnAgregar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/add2.png"))); // NOI18N
        btnAgregar.setText("Agregar");
        btnAgregar.setFocusable(false);
        btnAgregar.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        btnAgregar.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnAgregar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAgregarActionPerformed(evt);
            }
        });
        tbOpciones.add(btnAgregar);

        btnEditar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/edit.png"))); // NOI18N
        btnEditar.setText("Editar");
        btnEditar.setFocusable(false);
        btnEditar.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        btnEditar.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnEditar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEditarActionPerformed(evt);
            }
        });
        tbOpciones.add(btnEditar);

        btnEliminar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/delete.png"))); // NOI18N
        btnEliminar.setText("Eliminar");
        btnEliminar.setFocusable(false);
        btnEliminar.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        btnEliminar.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnEliminar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEliminarActionPerformed(evt);
            }
        });
        tbOpciones.add(btnEliminar);

        btnRefrescar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/actualizar.png"))); // NOI18N
        btnRefrescar.setText("Refrescar");
        btnRefrescar.setFocusable(false);
        btnRefrescar.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        btnRefrescar.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnRefrescar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRefrescarActionPerformed(evt);
            }
        });
        tbOpciones.add(btnRefrescar);

        btnSalir.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/salir2.png"))); // NOI18N
        btnSalir.setText("Salir");
        btnSalir.setFocusable(false);
        btnSalir.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        btnSalir.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnSalir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSalirActionPerformed(evt);
            }
        });
        tbOpciones.add(btnSalir);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 269, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 935, Short.MAX_VALUE)
                    .addComponent(tbOpciones, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 450, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(tbOpciones, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)))
                .addGap(0, 13, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void treeArticulosValueChanged(javax.swing.event.TreeSelectionEvent evt) {//GEN-FIRST:event_treeArticulosValueChanged
        String codDep,codPro,codDis;
        
        DefaultMutableTreeNode node = (DefaultMutableTreeNode) treeArticulos.getLastSelectedPathComponent();
        if (node == null) {
            return;
        }
         try {
            cliente obj = new cliente();
            
            if (node.getLevel() == 1) {
                codDep = obj.codigoDepartamento(node.getUserObject().toString());
                this.resultado = obj.listarPorDepartamento(codDep);
            } 
            else if (node.getLevel() == 2) {
                codDep=obj.codigoDepartamento(node.getSharedAncestor(node.getPreviousNode()).toString());
                codPro = obj.codigoProvincia(codDep, node.getUserObject().toString());
                this.resultado = obj.listarPorProvincia(codDep, codPro);
            }else if (node.getLevel() == 3) {
                codDep = obj.codigoDepartamento(node.getSharedAncestor(node.getPreviousNode()).getParent().toString());
                codPro = obj.codigoProvincia(codDep,node.getSharedAncestor(node.getPreviousNode()).toString());
                codDis = obj.codigoDistrito(codDep, codPro, node.getUserObject().toString());
                this.resultado = obj.listarPorDistrito(codDep, codPro, codDis);
            } 
            else {
                cargarDatosConsulta();
            }
            filtrar();
        } catch (Exception e) {
            Funciones.mensajeError(e.getMessage(), "Error");
        }
    }//GEN-LAST:event_treeArticulosValueChanged

    private void tblListadoMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblListadoMouseClicked
        if (evt.getClickCount() == 2) {
            btnEditar.doClick();
        }
    }//GEN-LAST:event_tblListadoMouseClicked

    private void txtValorBusquedaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtValorBusquedaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtValorBusquedaActionPerformed

    private void txtValorBusquedaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtValorBusquedaKeyPressed

    }//GEN-LAST:event_txtValorBusquedaKeyPressed

    private void txtValorBusquedaKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtValorBusquedaKeyReleased

    }//GEN-LAST:event_txtValorBusquedaKeyReleased

    private void txtValorBusquedaKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtValorBusquedaKeyTyped
        filtrar();
    }//GEN-LAST:event_txtValorBusquedaKeyTyped

    private void btnAgregarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAgregarActionPerformed
        frmClienteAgregarEditar obj = new frmClienteAgregarEditar(null, true);
        obj.setTitle("Agregar Nuevo Cliente");
        obj.operacion = "Agregar";
        obj.setVisible(true);

        //Funciones.mensajeInformacion("mensaje de prueba", "");
        if (obj.valorRetorno == 1) {
            cargarDatosConsulta();
            filtrar();
        }
    }//GEN-LAST:event_btnAgregarActionPerformed

    private void btnEditarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEditarActionPerformed
        frmClienteAgregarEditar obj = new frmClienteAgregarEditar(null, true);
        obj.setTitle("Editar Cliente");
        obj.operacion = "Editar";

        int fila = tblListado.getSelectedRow();
        if (fila < 0) {
            Funciones.mensajeError("No ha seleccionado un registro", Funciones.NOMBRE_SOFTWARE);
            return;
        }

        int codigoCliente = Integer.parseInt(tblListado.getValueAt(fila, 0).toString());

        obj.leerDatosCliente(codigoCliente);

        obj.setVisible(true);

        if (obj.valorRetorno == 1) {
            cargarDatosConsulta();
            filtrar();
            txtValorBusqueda.requestFocus();
            treeArticulos.removeSelectionPath(treeArticulos.getSelectionPath());
        }
    }//GEN-LAST:event_btnEditarActionPerformed

    private void btnEliminarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEliminarActionPerformed

        int fila = tblListado.getSelectedRow();
        if (fila < 0) {
            Funciones.mensajeError("No ha seleccionado un registro", Funciones.NOMBRE_SOFTWARE);
            return;
        }

        int codigoCliente = Integer.parseInt(tblListado.getValueAt(fila, 0).toString());

        int r = Funciones.mensajeConfirmacion("¿Estás seguro de eliminar el registro seleccionado?", "Confirme...");

        if (r != 0) {
            return;
        }

        try {
            boolean resultado;
            cliente obj = new cliente();
            obj.setCodigo_cliente(codigoCliente);
            resultado = obj.eliminar();
            if (resultado) {
                cargarDatosConsulta();
                filtrar();
            }
        } catch (Exception e) {
            Funciones.mensajeError(e.getMessage(), Funciones.NOMBRE_SOFTWARE);
        }
    }//GEN-LAST:event_btnEliminarActionPerformed

    private void btnRefrescarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRefrescarActionPerformed
        cargarDatosConsulta();
        filtrar();
    }//GEN-LAST:event_btnRefrescarActionPerformed

    private void btnSalirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSalirActionPerformed
        dispose();
    }//GEN-LAST:event_btnSalirActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAgregar;
    private javax.swing.JButton btnEditar;
    private javax.swing.JButton btnEliminar;
    private javax.swing.JButton btnRefrescar;
    private javax.swing.JButton btnSalir;
    private javax.swing.JComboBox cboBusqueda;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JToolBar.Separator jSeparator1;
    private javax.swing.JToolBar tbOpciones;
    private javax.swing.JTable tblListado;
    private javax.swing.JTree treeArticulos;
    private javax.swing.JTextField txtValorBusqueda;
    // End of variables declaration//GEN-END:variables
}
