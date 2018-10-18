package negocio;

import datos.Conexion;
import java.sql.ResultSet;
import java.util.ArrayList;
import javax.swing.JComboBox;
import static negocio.departamento.listaDpto;

public class tipoComprobante extends Conexion{
    
    private String codigo_tipo_comprobante;
    private String descripcion;

    
    public static ArrayList<tipoComprobante> listaTipoComp = new ArrayList<tipoComprobante>();
    
    public String getCodigo_tipo_comprobante() {
        return codigo_tipo_comprobante;
    }

    public void setCodigo_tipo_comprobante(String codigo_tipo_comprobante) {
        this.codigo_tipo_comprobante = codigo_tipo_comprobante;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
    
    private void cargarLista() throws Exception {
        String sql = "select * from tipo_comprobante order by 2";
        ResultSet resultado = this.ejecutarSQLSelect(sql);

        listaTipoComp.clear();

        while (resultado.next()) {
            tipoComprobante obj = new tipoComprobante();
            obj.setCodigo_tipo_comprobante(resultado.getString("codigo_tipo_comprobante"));
            obj.setDescripcion(resultado.getString("descripcion"));
            listaTipoComp.add(obj);
        }
    }
    
    public void cargarCombo(JComboBox objCombo) throws Exception {
        cargarLista();
        objCombo.removeAllItems();
        
        for (tipoComprobante listCom : listaTipoComp) {
            objCombo.addItem(listCom.getDescripcion());
        }
    }
    
}
