/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package negocio;

import datos.Conexion;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 *
 * @author USER
 */
public class proveedor extends Conexion{
    
    public ResultSet listar() throws Exception {
        String sql = "SELECT proveedor.ruc_proveedor, proveedor.razon_social, proveedor.direccion, proveedor.telefono, proveedor.representante_legal FROM   public.proveedor order by 2";
        PreparedStatement sentencia = abrirConexion().prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);

        ResultSet resultado = ejecutarSQLSelectSP(sentencia);
        return resultado;
    }
    
    public ResultSet leerDatosRUCoRazonSocial(String cod_pro,int num) throws Exception {
        String sql="";
        if (num==1) {
             sql= "SELECT proveedor.ruc_proveedor, proveedor.razon_social, proveedor.direccion, proveedor.telefono, proveedor.representante_legal FROM   public.proveedor where ruc_proveedor=? order by 2";
        }else if (num==2) {
            sql = "SELECT proveedor.ruc_proveedor, proveedor.razon_social, proveedor.direccion, proveedor.telefono, proveedor.representante_legal FROM   public.proveedor where razon_social=? order by 2";
        }
        PreparedStatement sentencia = abrirConexion().prepareStatement(sql);
        sentencia.setString(1, cod_pro);
        return ejecutarSQLSelectSP(sentencia);
    }
    
    public ResultSet leerDatosRUC(String cod_pro) throws Exception {
        String sql="SELECT proveedor.ruc_proveedor, proveedor.razon_social, proveedor.direccion, proveedor.telefono, proveedor.representante_legal FROM   public.proveedor where ruc_proveedor=? order by 2";
        PreparedStatement sentencia = abrirConexion().prepareStatement(sql);
        sentencia.setString(1, cod_pro);
        return ejecutarSQLSelectSP(sentencia);
    }
    
    public ResultSet leerDatosRazonSocial(String cod_pro) throws Exception {
        String sql = "SELECT proveedor.ruc_proveedor, proveedor.razon_social, proveedor.direccion, proveedor.telefono, proveedor.representante_legal FROM   public.proveedor where razon_social=? order by 2";
        PreparedStatement sentencia = abrirConexion().prepareStatement(sql);
        sentencia.setString(1, cod_pro);
        return ejecutarSQLSelectSP(sentencia);
    }
    
}
