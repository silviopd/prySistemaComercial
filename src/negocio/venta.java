package negocio;

import datos.Conexion;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

public class venta extends Conexion {

    private int numero_venta;
    private String codigo_tipo_comprobante;
    private int codigo_cliente;
    private int numero_serie;
    private int numero_documento;
    private java.sql.Date fecha_compra;
    private double porcentaje_igv;
    private double sub_total;
    private double igv;
    private double total;
    private int codigo_usuario;

    private ArrayList<ventaDetalle> articuloDetalleVenta = new ArrayList<ventaDetalle>();

    public int getNumero_venta() {
        return numero_venta;
    }

    public void setNumero_venta(int numero_venta) {
        this.numero_venta = numero_venta;
    }

    public String getCodigo_tipo_comprobante() {
        return codigo_tipo_comprobante;
    }

    public void setCodigo_tipo_comprobante(String codigo_tipo_comprobante) {
        this.codigo_tipo_comprobante = codigo_tipo_comprobante;
    }

    public int getCodigo_cliente() {
        return codigo_cliente;
    }

    public void setCodigo_cliente(int codigo_cliente) {
        this.codigo_cliente = codigo_cliente;
    }

    public ArrayList<ventaDetalle> getArticuloDetalleVenta() {
        return articuloDetalleVenta;
    }

    public void setArticuloDetalleVenta(ArrayList<ventaDetalle> articuloDetalleVenta) {
        this.articuloDetalleVenta = articuloDetalleVenta;
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

    public ArrayList<ventaDetalle> getArticuloDetalleCompra() {
        return articuloDetalleVenta;
    }

    public void setArticuloDetalleCompra(ArrayList<ventaDetalle> articuloDetalleCompra) {
        this.articuloDetalleVenta = articuloDetalleCompra;
    }

    public boolean grabarVenta() throws Exception {
        String sql = "select * from f_generar_correlativo('venta') as numero";
        ResultSet resultado = this.ejecutarSQLSelect(sql);

        if (resultado.next()) {
            int nuevoCodigo = resultado.getInt("numero");
            setNumero_venta(nuevoCodigo);

            Connection transaccion = abrirConexion();
            transaccion.setAutoCommit(false);
                        
            sql = "INSERT INTO venta("
                    + "            numero_venta, codigo_tipo_comprobante, numero_serie, numero_documento,"
                    + "            codigo_cliente, fecha_venta, porcentaje_igv, sub_total, igv,"
                    + "            total,codigo_usuario)"
                    + "    VALUES (?, ?, ?, ?,"
                    + "            ?, ?, ?, ?, ?,"
                    + "            ?, ?);";
            PreparedStatement sentenciaCompra = transaccion.prepareStatement(sql);
            sentenciaCompra.setInt(1, this.getNumero_venta());
            sentenciaCompra.setString(2, this.getCodigo_tipo_comprobante());
            sentenciaCompra.setInt(3, this.getNumero_serie());
            sentenciaCompra.setInt(4, this.getNumero_documento());
            sentenciaCompra.setInt(5, this.getCodigo_cliente());
            sentenciaCompra.setDate(6, this.getFecha_compra());
            sentenciaCompra.setDouble(7, this.getPorcentaje_igv());
            sentenciaCompra.setDouble(8, this.getSub_total());
            sentenciaCompra.setDouble(9, this.getIgv());
            sentenciaCompra.setDouble(10, this.getTotal());
            sentenciaCompra.setInt(11, this.getCodigo_usuario());
            this.ejecutarSQL(sentenciaCompra, transaccion);

            for (int i = 0; i < articuloDetalleVenta.size(); i++) {
                ventaDetalle fila = articuloDetalleVenta.get(i);
                sql = "INSERT INTO venta_detalle("
                        + "            numero_venta, item, codigo_articulo, cantidad, precio, descuento1,"
                        + "            descuento2)"
                        + "    VALUES (?, ?, ?, ?, ?, ?,"
                        + "            ?);";
                PreparedStatement sentenciaCompraDetalle = transaccion.prepareStatement(sql);
                sentenciaCompraDetalle.setInt(1, this.getNumero_venta());
                sentenciaCompraDetalle.setInt(3, fila.getCodigo_articulo());
                
                
                String sql2= "select stock from articulo where codigo_articulo=?";
                PreparedStatement validar = abrirConexion().prepareStatement(sql2);
                validar.setInt(1, fila.getCodigo_articulo());
                ResultSet validacion = this.ejecutarSQLSelectSP(validar);
                
                if (validacion.next()) {
                    if (validacion.getInt("stock")<fila.getCantidad()) {
                    transaccion.close();
                    throw new Exception("Tiene menos stock en el codigo de producto "+fila.getCodigo_articulo()+" cantidad"+fila.getCantidad());
                    }
                }               
                                
                sentenciaCompraDetalle.setInt(2, fila.getItem());
                sentenciaCompraDetalle.setInt(4, fila.getCantidad());
                sentenciaCompraDetalle.setDouble(5, fila.getPrecio());
                sentenciaCompraDetalle.setDouble(6, fila.getDescuento1());
                sentenciaCompraDetalle.setDouble(7, fila.getDescuento2());
                this.ejecutarSQL(sentenciaCompraDetalle, transaccion);

                sql = "update articulo set stock = stock - ? where codigo_articulo=?";
                PreparedStatement sentenciaActualizarArticulo = transaccion.prepareStatement(sql);
                sentenciaActualizarArticulo.setInt(1, fila.getCantidad());
                sentenciaActualizarArticulo.setInt(2, fila.getCodigo_articulo());
                this.ejecutarSQL(sentenciaActualizarArticulo, transaccion);
            }

            sql = "update correlativo set numero = numero+1 where tabla='venta'";
            PreparedStatement actualizarCorrelativo = transaccion.prepareStatement(sql);
            this.ejecutarSQL(actualizarCorrelativo, transaccion);

            transaccion.commit();
            transaccion.close();
        } else {
            throw new Exception("No existe un correlativo para la tabla venta");
        }
        return true;
    }

    public ResultSet listarPorFecha(java.sql.Date fecha1, java.sql.Date fecha2, int tip) throws Exception {
        String sql = "select * from f_listado_venta(?,?,?)";
        PreparedStatement sentencia = abrirConexion().prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);

        sentencia.setDate(1, fecha1);
        sentencia.setDate(2, fecha2);
        sentencia.setInt(3, tip);

        ResultSet resultado = ejecutarSQLSelectSP(sentencia);
        return resultado;
    }

    public boolean anular(int numeroCompra) throws Exception {
        String sql = "select estado from venta where numero_venta=?";
        PreparedStatement sentencia = abrirConexion().prepareStatement(sql);
        sentencia.setInt(1, numeroCompra);
        ResultSet resultado = this.ejecutarSQLSelectSP(sentencia);

        if (resultado.next()) {
            if (resultado.getString("estado").equalsIgnoreCase("A")) {
                throw new Exception("La venta que intenta anular ya ha sido anulado");
            } else {

                Connection transaccion = this.abrirConexion();
                transaccion.setAutoCommit(false);

                sql = "update venta set estado = 'A' where numero_venta=?";
                PreparedStatement sentenciaAnular = transaccion.prepareStatement(sql);
                sentenciaAnular.setInt(1, numeroCompra);
                ejecutarSQL(sentenciaAnular, transaccion);

                sql = "select codigo_articulo,cantidad from venta_detalle where numero_venta=?";
                PreparedStatement sentenciaArticuloCompra = this.abrirConexion().prepareStatement(sql);
                sentenciaArticuloCompra.setInt(1, numeroCompra);
                ResultSet resultadoArticuloCompra = ejecutarSQLSelectSP(sentenciaArticuloCompra);

                while (resultadoArticuloCompra.next()) {
                    sql = "update articulo set stock = stock + ? where codigo_articulo=?";
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

    public ResultSet listarVentaDetalle(int numeroCompra) throws Exception {
        String sql ="SELECT  venta_detalle.numero_venta,   venta_detalle.item,   articulo.nombre,   venta_detalle.cantidad,   venta_detalle.precio,   venta_detalle.descuento1,   venta_detalle.descuento2,   (cliente.apellido_paterno ||' '||   cliente.apellido_materno ||', '||  cliente.nombres)::character varying as nombres FROM   public.venta,   public.venta_detalle,   public.articulo,   public.cliente WHERE   venta.numero_venta = venta_detalle.numero_venta AND  venta_detalle.codigo_articulo = articulo.codigo_articulo AND  cliente.codigo_cliente = venta.codigo_cliente and venta_detalle.numero_venta=? ORDER BY  venta_detalle.numero_venta ASC,   venta_detalle.item ASC,  articulo.nombre ASC;"; 
        PreparedStatement sentencia = abrirConexion().prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);

        sentencia.setInt(1, numeroCompra);

        ResultSet resultado = ejecutarSQLSelectSP(sentencia);
        return resultado;
    }

}
