
package negocio;

import datos.Conexion;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import javax.swing.JComboBox;
public class provincia extends Conexion{

    private String codigo_departamento;
    private String codigo_provincia;
    private String nombre;
    
    public static ArrayList<provincia> listaProvincia= new ArrayList<provincia>();

    public String getCodigo_departamento() {
        return codigo_departamento;
    }

    public void setCodigo_departamento(String codigo_departamento) {
        this.codigo_departamento = codigo_departamento;
    }

    public String getCodigo_provincia() {
        return codigo_provincia;
    }

    public void setCodigo_provincia(String codigo_provincia) {
        this.codigo_provincia = codigo_provincia;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    
    private void cargarLista(String codigoDepartamento) throws Exception {
        String sql = "select * from provincia where codigo_departamento=? order by 3";
        PreparedStatement sentencia = this.abrirConexion().prepareStatement(sql);
        sentencia.setString(1, codigoDepartamento);
        
        ResultSet resultado = this.ejecutarSQLSelectSP(sentencia);
        
        listaProvincia.clear();
        
        while (resultado.next()) {
            provincia obj = new provincia();
            obj.setCodigo_departamento(resultado.getString("codigo_departamento"));
            obj.setCodigo_provincia(resultado.getString("codigo_provincia"));
            obj.setNombre(resultado.getString("nombre"));
            
            listaProvincia.add(obj);
        }        
    }
    
    public void cargarCombo(JComboBox objCombo,String codigoDepartamento) throws Exception{
        cargarLista(codigoDepartamento);
        objCombo.removeAllItems();
        
        for (provincia Prov : listaProvincia) {
            objCombo.addItem(Prov.getNombre());
        }
    }
    
    public  void cargarTabla(String codigoDepartamento) throws Exception{
        String sql = "select * from provincia where codigo_departamento = ? order by 3";
        PreparedStatement sentencia =  this.abrirConexion().prepareStatement(sql);
        sentencia.setString(1, codigoDepartamento);
        ResultSet resultado = this.ejecutarSQLSelectSP(sentencia);
        listaProvincia.clear();
        while(resultado.next()){
            provincia obj = new provincia();
            obj.setCodigo_departamento(resultado.getString("codigo_departamento") );
            obj.setCodigo_provincia(resultado.getString("codigo_provincia") );
            obj.setNombre(resultado.getString("nombre"));
            listaProvincia.add(obj);
        }
    }
}
