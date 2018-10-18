/*_.,MNBV 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package negocio;

import datos.Conexion;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import javax.swing.JComboBox;
import static negocio.provincia.listaProvincia;

/**
 *
 * @author USER
 */
public class distrito extends Conexion{
    
    private String codigo_departamento;
    private String codigo_provincia;
    private String codigo_distrito;
    private String nombre;
    
    public static ArrayList<distrito> listaDistrito= new ArrayList<distrito>();

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

    public String getCodigo_distrito() {
        return codigo_distrito;
    }

    public void setCodigo_distrito(String codigo_distrito) {
        this.codigo_distrito = codigo_distrito;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    
    private void cargarLista(String codigoDepartamento,String codigoProvincia) throws Exception {
        String sql = "select * from distrito where codigo_departamento=? and codigo_provincia=? order by 4";
        PreparedStatement sentencia = this.abrirConexion().prepareStatement(sql);
        sentencia.setString(1, codigoDepartamento);
        sentencia.setString(2, codigoProvincia);
        
        ResultSet resultado = this.ejecutarSQLSelectSP(sentencia);
        
        listaDistrito.clear();
        
        while (resultado.next()) {
            distrito obj = new distrito();
            obj.setCodigo_departamento(resultado.getString("codigo_departamento"));
            obj.setCodigo_provincia(resultado.getString("codigo_provincia"));
            obj.setCodigo_distrito(resultado.getString("codigo_distrito"));
            obj.setNombre(resultado.getString("nombre"));
            
            listaDistrito.add(obj);
        }        
    }
    
    public void cargarCombo(JComboBox objCombo,String codigoDepartamento,String codigoProvincia) throws Exception{
        cargarLista(codigoDepartamento,codigoProvincia);
        objCombo.removeAllItems();
        
        for (distrito Distri : listaDistrito) {
            objCombo.addItem(Distri.getNombre());
        }
    }
    
    public  void cargarTabla(String codigoDepartamento,String codigoProvincia) throws Exception{
        String sql = "select * from distrito where codigo_departamento = ? and codigo_provincia=? order by 4";
        PreparedStatement sentencia =  this.abrirConexion().prepareStatement(sql);
        sentencia.setString(1, codigoDepartamento);
        sentencia.setString(2, codigoProvincia);
        ResultSet resultado = this.ejecutarSQLSelectSP(sentencia);
        listaDistrito.clear();
        while(resultado.next()){
            distrito obj = new distrito();
            obj.setCodigo_departamento(resultado.getString("codigo_departamento") );
            obj.setCodigo_provincia(resultado.getString("codigo_provincia") );
            obj.setCodigo_distrito(resultado.getString("codigo_distrito") );
            obj.setNombre(resultado.getString("nombre"));
            listaDistrito.add(obj);
        }
    }
}
