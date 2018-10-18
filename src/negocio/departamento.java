/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package negocio;

import datos.Conexion;
import java.sql.ResultSet;
import java.util.ArrayList;
import javax.swing.JComboBox;

/**
 *
 * @author USER
 */
public class departamento extends Conexion{
    
    private String codigo_departamento;
    private String nombre;
    
    
    public static ArrayList<departamento> listaDpto = new ArrayList<departamento>();

    public String getCodigo_departamento() {
        return codigo_departamento;
    }

    public void setCodigo_departamento(String codigo_departamento) {
        this.codigo_departamento = codigo_departamento;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    
    private void cargarLista() throws Exception {
        String sql = "select codigo_departamento,nombre from departamento order by 2";
        ResultSet resultado = this.ejecutarSQLSelect(sql);

        listaDpto.clear();

        while (resultado.next()) {
            departamento obj = new departamento();
            obj.setCodigo_departamento(resultado.getString("codigo_departamento"));
            obj.setNombre(resultado.getString("nombre"));
            listaDpto.add(obj);
        }
    }
    
    public void cargarCombo(JComboBox objCombo) throws Exception {
        cargarLista();
        objCombo.removeAllItems();
        
        /*
        for (int i = 0; i < listaDpto.size(); i++) {
            objCombo.addItem(listaDpto.get(i)..getNombre());
        }
        */
        
        for (departamento Dpto : listaDpto) {
            objCombo.addItem(Dpto.getNombre());
        }
    }
    
    public  void cargarTabla() throws Exception{
            String sql = "select * from departamento order by 2";
            ResultSet resultado = this.ejecutarSQLSelect(sql);
            listaDpto.clear();
            while(resultado.next()){
                departamento obj = new departamento();
                obj.setCodigo_departamento(resultado.getString("codigo_departamento") );
                obj.setNombre(resultado.getString("nombre"));
                listaDpto.add(obj);
            }
        }  
   
}
