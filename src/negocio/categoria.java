/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package negocio;

import datos.Conexion;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import javax.swing.JComboBox;

/**
 *
 * @author laboratorio_computo
 */
public class categoria extends Conexion {

    private int codigo_categoria;
    private String descripcion;
    private int codigo_linea;

    public static ArrayList<categoria> listaCategorias = new ArrayList<categoria>();

    public int getCodigo_categoria() {
        return codigo_categoria;
    }

    public void setCodigo_categoria(int codigo_categoria) {
        this.codigo_categoria = codigo_categoria;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public int getCodigo_linea() {
        return codigo_linea;
    }

    public void setCodigo_linea(int codigo_linea) {
        this.codigo_linea = codigo_linea;
    }

    public ResultSet listar() throws Exception {
        String sql = "select c.codigo_categoria,c.descripcion as categoria,l.descripcion as linea from categoria c inner join linea l on c.codigo_linea=l.codigo_linea order by 2";
        PreparedStatement sentencia = abrirConexion().prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);

        ResultSet resultado = ejecutarSQLSelectSP(sentencia);
        return resultado;
    }

    public String[] obtenerCamposBusqueda() {
        String campos[] = {"codigo_categoria", "categoria", "linea"};
        return campos;
    }

    public boolean agregar() throws Exception {
        String sql = "select * from f_generar_correlativo('categoria') as numero";
        ResultSet resultado = ejecutarSQLSelect(sql);

        if (resultado.next()) {
            int nuevoCodigo = resultado.getInt("numero");
            setCodigo_categoria(nuevoCodigo);

            Connection transaccion = abrirConexion();
            transaccion.setAutoCommit(false);

            sql = "INSERT INTO categoria(codigo_categoria, descripcion, codigo_linea)  VALUES (?, ?, ?)";
            PreparedStatement sentencia1 = transaccion.prepareStatement(sql);
            sentencia1.setInt(1, this.getCodigo_categoria());
            sentencia1.setString(2, this.getDescripcion());
            sentencia1.setInt(3, this.getCodigo_linea());
            this.ejecutarSQL(sentencia1, transaccion);

            sql = "UPDATE correlativo SET numero=numero+1 WHERE tabla=?";
            PreparedStatement sentencia2 = transaccion.prepareStatement(sql);
            sentencia2.setString(1, "categoria");
            this.ejecutarSQL(sentencia2, transaccion);

            transaccion.commit();
            transaccion.close();
        } else {
            throw new Exception("No existe un correlativo registrado para la tabla categoria");
        }
        return true;
    }

    public ResultSet leerDatos(int cod_cat) throws Exception {
        String sql = "SELECT categoria.codigo_categoria, categoria.descripcion as categoria,  linea.descripcion as linea FROM  public.categoria, public.linea WHERE  categoria.codigo_linea = linea.codigo_linea and categoria.codigo_categoria= ?";
        PreparedStatement sentencia = abrirConexion().prepareStatement(sql);
        sentencia.setInt(1, cod_cat);
        return ejecutarSQLSelectSP(sentencia);
    }

    public boolean editar() throws Exception {
        Connection transaccion = abrirConexion();
        transaccion.setAutoCommit(false);

        String sql = "update categoria set descripcion=?,codigo_linea=? where codigo_categoria=?";

        PreparedStatement sentencia = transaccion.prepareStatement(sql);
        sentencia.setString(1, getDescripcion());
        sentencia.setInt(2, getCodigo_linea());
        sentencia.setInt(3, getCodigo_categoria());

        this.ejecutarSQL(sentencia, transaccion);
        transaccion.commit();
        transaccion.close();

        return true;
    }

    public boolean eliminar() throws Exception {
        Connection transaccion = abrirConexion();
        transaccion.setAutoCommit(false);

        String sql = "delete from categoria where codigo_categoria=?";

        PreparedStatement sentencia = transaccion.prepareStatement(sql);
        sentencia.setInt(1, getCodigo_categoria());

        this.ejecutarSQL(sentencia, transaccion);
        transaccion.commit();
        transaccion.close();

        return true;
    }

    public void cargarCombo(JComboBox objCombo) throws Exception {
        cargarLista();
        objCombo.removeAllItems();
        for (int i = 0; i < listaCategorias.size(); i++) {
            objCombo.addItem(listaCategorias.get(i).descripcion);
        }
    }

    private void cargarLista() throws Exception {
        String sql = "select codigo_categoria,descripcion,codigo_linea from categoria order by 2";
        ResultSet resultado = this.ejecutarSQLSelect(sql);

        listaCategorias.clear();

        while (resultado.next()) {
            categoria obj = new categoria();
            obj.setCodigo_categoria(resultado.getInt("codigo_categoria"));
            obj.setDescripcion(resultado.getString("descripcion"));
            obj.setCodigo_linea(resultado.getInt("codigo_linea"));
            listaCategorias.add(obj);
        }
    }

}
