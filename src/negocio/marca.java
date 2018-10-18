package negocio;

import datos.Conexion;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import javax.swing.JComboBox;

public class marca extends Conexion {

    private int codigo_marca;
    private String descripcion;

    public static ArrayList<marca> listaMarcas = new ArrayList<marca>();

    public int getCodigo_marca() {
        return codigo_marca;
    }

    public void setCodigo_marca(int codigo_marca) {
        this.codigo_marca = codigo_marca;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    private void cargarLista() throws Exception {
        String sql = "select codigo_marca,descripcion from marca order by 2";
        ResultSet resultado = this.ejecutarSQLSelect(sql);

        listaMarcas.clear();

        while (resultado.next()) {
            marca obj = new marca();
            obj.setCodigo_marca(resultado.getInt("codigo_marca"));
            obj.setDescripcion(resultado.getString("descripcion"));
            listaMarcas.add(obj);
        }
    }

    public void cargarCombo(JComboBox objCombo) throws Exception {
        cargarLista();
        objCombo.removeAllItems();
        /*for (int i = 0; i < listaMarcas.size(); i++) {
         objCombo.addItem(listaMarcas.get(i).descripcion);
         }*/
        for (marca listaMarca : listaMarcas) {
            objCombo.addItem(listaMarca.descripcion);
        }
    }

    public ResultSet listar() throws Exception {
        String sql = "select * from marca order by descripcion";
        PreparedStatement sentencia = abrirConexion().prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);

        ResultSet resultado = ejecutarSQLSelectSP(sentencia);
        return resultado;
    }
    
    public boolean agregar() throws Exception {
        String sql = "select * from f_generar_correlativo('marca') as numero";
        ResultSet resultado = ejecutarSQLSelect(sql);

        if (resultado.next()) {
            int nuevoCodigo = resultado.getInt("numero");
            setCodigo_marca(nuevoCodigo);

            Connection transaccion = abrirConexion();
            transaccion.setAutoCommit(false);

            sql = "INSERT INTO marca(codigo_marca, descripcion) VALUES (?, ?);";
            PreparedStatement sentencia1 = transaccion.prepareStatement(sql);
            sentencia1.setInt(1, this.getCodigo_marca());
            sentencia1.setString(2, this.getDescripcion());
            this.ejecutarSQL(sentencia1, transaccion);

            sql = "UPDATE correlativo SET numero=numero+1 WHERE tabla=?";
            PreparedStatement sentencia2 = transaccion.prepareStatement(sql);
            sentencia2.setString(1, "marca");
            this.ejecutarSQL(sentencia2, transaccion);

            transaccion.commit();
            transaccion.close();
        } else {
            throw new Exception("No existe un correlativo registrado para la tabla marca");
        }
        return true;
    }

    public ResultSet leerDatos(int cod_cat) throws Exception {
        String sql = "select * from marca where codigo_marca=?";
        PreparedStatement sentencia = abrirConexion().prepareStatement(sql);
        sentencia.setInt(1, cod_cat);
        return ejecutarSQLSelectSP(sentencia);
    }

    public boolean editar() throws Exception {
        Connection transaccion = abrirConexion();
        transaccion.setAutoCommit(false);

        String sql = "update marca set descripcion=? where codigo_marca=?";

        PreparedStatement sentencia = transaccion.prepareStatement(sql);
        sentencia.setString(1, getDescripcion());
        sentencia.setInt(2, getCodigo_marca());

        this.ejecutarSQL(sentencia, transaccion);
        transaccion.commit();
        transaccion.close();

        return true;
    }

    public boolean eliminar() throws Exception {
        Connection transaccion = abrirConexion();
        transaccion.setAutoCommit(false);

        String sql = "delete from marca where codigo_marca=?";

        PreparedStatement sentencia = transaccion.prepareStatement(sql);
        sentencia.setInt(1, getCodigo_marca());

        this.ejecutarSQL(sentencia, transaccion);
        transaccion.commit();
        transaccion.close();

        return true;
    }

    
    public String[] obtenerCamposBusqueda() {
        String campos[] = {"codigo_marca","descripcion"};
        return campos;
    }
}
