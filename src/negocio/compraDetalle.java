/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package negocio;

import datos.Conexion;
import java.sql.ResultSet;
import java.util.ArrayList;

/**
 *
 * @author USER
 */
public class compraDetalle extends Conexion {
    
  private int codigo_articulo;
  private int item;
  private int cantidad;
  private double precio;
  private double descuento;

  
    public int getCodigo_articulo() {
        return codigo_articulo;
    }

    public void setCodigo_articulo(int codigo_articulo) {
        this.codigo_articulo = codigo_articulo;
    }

    public int getItem() {
        return item;
    }

    public void setItem(int item) {
        this.item = item;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    public double getDescuento() {
        return descuento;
    }

    public void setDescuento(double descuento) {
        this.descuento = descuento;
    }
  
    public ResultSet configurarTablaDetalleCompra() throws Exception {
        String sql = "select * from( select 0 as codigo, ''::character varying(100) as articulo, 0 as cantidad,0 as precio,0 as descuento , 0 as importe) as tb_temporal where tb_temporal.codigo <> 0";
        ResultSet resultado = this.ejecutarSQLSelect(sql);
        return resultado;
    }

    public double calcularImporte(int cantidad, double precio, double descuento){
        double importeBruto = 0;
        double importeNeto = 0;
        double importeDescuento = 0;

        importeBruto = cantidad * precio;
        importeDescuento = (importeBruto * descuento) / 100;
        importeNeto = importeBruto - importeDescuento;

        return importeNeto;
    }

}
