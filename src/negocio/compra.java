package negocio;

import datos.Conexion;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

public class compra extends Conexion {

    private int numero_compra;
    private String codigo_tipo_comprobante;
    private String ruc_proveedor;
    private int numero_serie;
    private int numero_documento;
    private java.sql.Date fecha_compra;
    private double porcentaje_igv;
    private double sub_total;
    private double igv;
    private double total;
    private int codigo_usuario;

    private ArrayList<compraDetalle> articuloDetalleCompra = new ArrayList<compraDetalle>();

    public ArrayList<compraDetalle> getArticuloDetalleCompra() {
        return articuloDetalleCompra;
    }

    public void setArticuloDetalleCompra(ArrayList<compraDetalle> articuloDetalleCompra) {
        this.articuloDetalleCompra = articuloDetalleCompra;
    }

    public int getNumero_compra() {
        return numero_compra;
    }

    public void setNumero_compra(int numero_compra) {
        this.numero_compra = numero_compra;
    }

    public String getCodigo_tipo_comprobante() {
        return codigo_tipo_comprobante;
    }

    public void setCodigo_tipo_comprobante(String codigo_tipo_comprobante) {
        this.codigo_tipo_comprobante = codigo_tipo_comprobante;
    }

    public String getRuc_proveedor() {
        return ruc_proveedor;
    }

    public void setRuc_proveedor(String ruc_proveedor) {
        this.ruc_proveedor = ruc_proveedor;
    }

    public int getNumero_serie() {
        return numero_serie;
    }

    public void setNumero_serie(int numero_serie) {
        this.numero_serie = numero_serie;
    }

    public int getNumero_documento() {
        return numero_documento;
    }

    public void setNumero_documento(int numero_documento) {
        this.numero_documento = numero_documento;
    }

    public Date getFecha_compra() {
        return fecha_compra;
    }

    public void setFecha_compra(Date fecha_compra) {
        this.fecha_compra = fecha_compra;
    }

    public double getPorcentaje_igv() {
        return porcentaje_igv;
    }

    public void setPorcentaje_igv(double porcentaje_igv) {
        this.porcentaje_igv = porcentaje_igv;
    }

    public double getSub_total() {
        return sub_total;
    }

    public void setSub_total(double sub_total) {
        this.sub_total = sub_total;
    }

    public double getIgv() {
        return igv;
    }

    public void setIgv(double igv) {
        this.igv = igv;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public int getCodigo_usuario() {
        return codigo_usuario;
    }

    public void setCodigo_usuario(int codigo_usuario) {
        this.codigo_usuario = codigo_usuario;
    }

    public boolean grabarCompra() throws Exception {
        String sql = "select * from f_generar_correlativo('compra') as numero";
        ResultSet resultado = this.ejecutarSQLSelect(sql);

        if (resultado.next()) {
            int nuevoCodigo = resultado.getInt("numero");
            setNumero_compra(nuevoCodigo);

            Connection transaccion = abrirConexion();
            transaccion.setAutoCommit(false);

            sql = "INSERT INTO compra(numero_compra, codigo_tipo_comprobante, ruc_proveedor, numero_serie, numero_documento, fecha_compra, porcentaje_igv, sub_total, igv, total,codigo_usuario) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?);";
            PreparedStatement sentenciaCompra = transaccion.prepareStatement(sql);
            sentenciaCompra.setInt(1, this.getNumero_compra());
            sentenciaCompra.setString(2, this.getCodigo_tipo_comprobante());
            sentenciaCompra.setString(3, this.getRuc_proveedor());
            sentenciaCompra.setInt(4, this.getNumero_serie());
            sentenciaCompra.setInt(5, this.getNumero_documento());
            sentenciaCompra.setDate(6, this.getFecha_compra());
            sentenciaCompra.setDouble(7, this.getPorcentaje_igv());
            sentenciaCompra.setDouble(8, this.getSub_total());
            sentenciaCompra.setDouble(9, this.getIgv());
            sentenciaCompra.setDouble(10, this.getTotal());
            sentenciaCompra.setInt(11, this.getCodigo_usuario());
            this.ejecutarSQL(sentenciaCompra, transaccion);

            for (int i = 0; i < articuloDetalleCompra.size(); i++) {
                compraDetalle fila = articuloDetalleCompra.get(i);
                sql = "INSERT INTO compra_detalle(numero_compra, codigo_articulo, item, cantidad, precio, descuento) VALUES (?, ?, ?, ?, ?, ?);";
                PreparedStatement sentenciaCompraDetalle = transaccion.prepareStatement(sql);
                sentenciaCompraDetalle.setInt(1, this.getNumero_compra());
                sentenciaCompraDetalle.setInt(2, fila.getCodigo_articulo());
                sentenciaCompraDetalle.setInt(3, fila.getItem());
                sentenciaCompraDetalle.setInt(4, fila.getCantidad());
                sentenciaCompraDetalle.setDouble(5, fila.getPrecio());
                sentenciaCompraDetalle.setDouble(6, fila.getDescuento());
                this.ejecutarSQL(sentenciaCompraDetalle, transaccion);

                sql = "update articulo set stock = stock + ? where codigo_articulo=?";
                PreparedStatement sentenciaActualizarArticulo = transaccion.prepareStatement(sql);
                sentenciaActualizarArticulo.setInt(1, fila.getCantidad());
                sentenciaActualizarArticulo.setInt(2, fila.getCodigo_articulo());
                this.ejecutarSQL(sentenciaActualizarArticulo, transaccion);
            }

            sql = "update correlativo set numero = numero+1 where tabla='compra'";
            PreparedStatement actualizarCorrelativo = transaccion.prepareStatement(sql);
            this.ejecutarSQL(actualizarCorrelativo, transaccion);

            transaccion.commit();
            transaccion.close();
        } else {
            throw new Exception("No existe un correlativo para la tabla compra");
        }
        return true;
    }

    public ResultSet listarPorFecha(java.sql.Date fecha1, java.sql.Date fecha2, int tip) throws Exception {
        String sql = "select * from f_listado_compra(?,?,?)";
        PreparedStatement sentencia = abrirConexion().prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);

        sentencia.setDate(1, fecha1);
        sentencia.setDate(2, fecha2);
        sentencia.setInt(3, tip);

        ResultSet resultado = ejecutarSQLSelectSP(sentencia);
        return resultado;
    }

    public boolean anular(int numeroCompra) throws Exception {
        String sql = "select estado from compra where numero_compra=?";
        PreparedStatement sentencia = abrirConexion().prepareStatement(sql);
        sentencia.setInt(1, numeroCompra);
        ResultSet resultado = this.ejecutarSQLSelectSP(sentencia);

        if (resultado.next()) {
            if (resultado.getString("estado").equalsIgnoreCase("A")) {
                throw new Exception("La compra que intenta anular ya ha sido anulado");
            } else {

                Connection transaccion = this.abrirConexion();
                transaccion.setAutoCommit(false);

                sql = "update compra set estado = 'A' where numero_compra=?";
                PreparedStatement sentenciaAnular = transaccion.prepareStatement(sql);
                sentenciaAnular.setInt(1, numeroCompra);
                ejecutarSQL(sentenciaAnular, transaccion);

                sql = "select codigo_articulo,cantidad from compra_detalle where numero_compra=?";
                PreparedStatement sentenciaArticuloCompra = this.abrirConexion().prepareStatement(sql);
                sentenciaArticuloCompra.setInt(1, numeroCompra);
                ResultSet resultadoArticuloCompra = ejecutarSQLSelectSP(sentenciaArticuloCompra);

                while (resultadoArticuloCompra.next()) {
                    sql = "update articulo set stock = stock - ? where codigo_articulo=?";
                    PreparedStatement actualizarStock = transaccion.prepareStatement(sql);
                    actualizarStock.setInt(1, resultadoArticuloCompra.getInt("cantidad"));
                    actualizarStock.setInt(2, resultadoArticuloCompra.getInt("codigo_articulo"));
                    this.ejecutarSQL(actualizarStock, transaccion);
                }
                
                transaccion.commit();
                transaccion.close();

            }
        } else {
            throw new Exception("No se ha encontrado la compra que quiere anular");
        }
        return true;
    }
    
    public ResultSet listarCompraDetalle(int numeroCompra) throws Exception {
        String sql = "SELECT   compra_detalle.numero_compra,   compra_detalle.item,  articulo.nombre,   compra_detalle.cantidad,   compra_detalle.precio,   compra_detalle.descuento,   proveedor.ruc_proveedor,   proveedor.razon_social FROM   public.compra,   public.compra_detalle,  public.articulo,   public.proveedor WHERE   compra.numero_compra = compra_detalle.numero_compra AND  compra_detalle.codigo_articulo = articulo.codigo_articulo AND  proveedor.ruc_proveedor = compra.ruc_proveedor and compra_detalle.numero_compra=? ORDER BY  articulo.nombre ASC,   compra_detalle.item ASC,   compra.numero_compra DESC;";
        PreparedStatement sentencia = abrirConexion().prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);

        sentencia.setInt(1,numeroCompra);

        ResultSet resultado = ejecutarSQLSelectSP(sentencia);
        return resultado;
    }

}
