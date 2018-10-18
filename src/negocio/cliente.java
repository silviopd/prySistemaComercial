package negocio;

import datos.Conexion;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 *
 * @author USER
 */
public class cliente extends Conexion {

    private int codigo_cliente;
    private String apellido_paterno;
    private String apellido_materno;
    private String nombres;
    private String nro_documento_identidad;
    private String direccion;
    private String telefono_fijo;
    private String telefono_movil1;
    private String telefono_movil2;
    private String email;
    private String direccion_web;
    private String codigo_departamento;
    private String codigo_provincia;
    private String codigo_distrito;

    public int getCodigo_cliente() {
        return codigo_cliente;
    }

    public void setCodigo_cliente(int codigo_cliente) {
        this.codigo_cliente = codigo_cliente;
    }

    public String getApellido_paterno() {
        return apellido_paterno;
    }

    public void setApellido_paterno(String apellido_paterno) {
        this.apellido_paterno = apellido_paterno;
    }

    public String getApellido_materno() {
        return apellido_materno;
    }

    public void setApellido_materno(String apellido_materno) {
        this.apellido_materno = apellido_materno;
    }

    public String getNombres() {
        return nombres;
    }

    public void setNombres(String nombres) {
        this.nombres = nombres;
    }

    public String getNro_documento_identidad() {
        return nro_documento_identidad;
    }

    public void setNro_documento_identidad(String nro_documento_identidad) {
        this.nro_documento_identidad = nro_documento_identidad;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getTelefono_fijo() {
        return telefono_fijo;
    }

    public void setTelefono_fijo(String telefono_fijo) {
        this.telefono_fijo = telefono_fijo;
    }

    public String getTelefono_movil1() {
        return telefono_movil1;
    }

    public void setTelefono_movil1(String telefono_movil1) {
        this.telefono_movil1 = telefono_movil1;
    }

    public String getTelefono_movil2() {
        return telefono_movil2;
    }

    public void setTelefono_movil2(String telefono_movil2) {
        this.telefono_movil2 = telefono_movil2;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getDireccion_web() {
        return direccion_web;
    }

    public void setDireccion_web(String direccion_web) {
        this.direccion_web = direccion_web;
    }

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

    public ResultSet listar() throws Exception {
        String sql = "SELECT   cliente.codigo_cliente as codigo,   cliente.apellido_paterno ||' '|| cliente.apellido_materno ||', '|| cliente.nombres as Nombres,   cliente.nro_documento_identidad as dni,  cliente.direccion,   cliente.telefono_fijo,   cliente.telefono_movil1,   cliente.telefono_movil2,   cliente.email,   cliente.direccion_web, departamento.nombre ||' / '||provincia.nombre||' / '|| distrito.nombre as Ubicacion FROM   public.departamento,   public.provincia,   public.distrito,   public.cliente WHERE   provincia.codigo_departamento = departamento.codigo_departamento AND   provincia.codigo_provincia = distrito.codigo_provincia AND  provincia.codigo_departamento = distrito.codigo_departamento AND   distrito.codigo_departamento = cliente.codigo_departamento AND  distrito.codigo_provincia = cliente.codigo_provincia AND  distrito.codigo_distrito = cliente.codigo_distrito ORDER BY  2";
        PreparedStatement sentencia = abrirConexion().prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);

        ResultSet resultado = ejecutarSQLSelectSP(sentencia);
        return resultado;
    }

    public String[] obtenerCamposBusqueda() {
        String campos[] = {"codigo", "Nombres", "dni", "direccion", "telefono_fijo", "telefono_movil1", "telefono_movil2", "email", "direccion_web", "ubicacion"};
        return campos;
    }

    public boolean agregar() throws Exception {
        String sql = "select * from f_generar_correlativo('cliente') as numero";
        ResultSet resultado = ejecutarSQLSelect(sql);

        if (resultado.next()) {
            int nuevoCodigo = resultado.getInt("numero");
            setCodigo_cliente(nuevoCodigo);

            Connection transaccion = abrirConexion();
            transaccion.setAutoCommit(false);

            sql = "INSERT INTO cliente(codigo_cliente, apellido_paterno, apellido_materno, nombres, nro_documento_identidad, direccion, telefono_fijo, telefono_movil1, telefono_movil2, email, direccion_web, codigo_departamento, codigo_provincia, codigo_distrito) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?);";
            PreparedStatement sentencia1 = transaccion.prepareStatement(sql);
            sentencia1.setInt(1, this.getCodigo_cliente());
            sentencia1.setString(2, this.getApellido_paterno());
            sentencia1.setString(3, this.getApellido_materno());
            sentencia1.setString(4, this.getNombres());
            sentencia1.setString(5, this.getNro_documento_identidad());
            sentencia1.setString(6, this.getDireccion());
            sentencia1.setString(7, this.getTelefono_fijo());
            sentencia1.setString(8, this.getTelefono_movil1());
            sentencia1.setString(9, this.getTelefono_movil2());
            sentencia1.setString(10, this.getEmail());
            sentencia1.setString(11, this.getDireccion_web());
            sentencia1.setString(12, this.getCodigo_departamento());
            sentencia1.setString(13, this.getCodigo_provincia());
            sentencia1.setString(14, this.getCodigo_distrito());
            this.ejecutarSQL(sentencia1, transaccion);

            sql = "UPDATE correlativo SET numero=numero+1 WHERE tabla=?";
            PreparedStatement sentencia2 = transaccion.prepareStatement(sql);
            sentencia2.setString(1, "cliente");
            this.ejecutarSQL(sentencia2, transaccion);

            transaccion.commit();
            transaccion.close();
        } else {
            throw new Exception("No existe un correlativo registrado para la tabla cliente");
        }
        return true;
    }

    public boolean editar() throws Exception {
        Connection transaccion = abrirConexion();
        transaccion.setAutoCommit(false);

        String sql = "UPDATE cliente SET  apellido_paterno=?, apellido_materno=?, nombres=?, nro_documento_identidad=?, direccion=?, telefono_fijo=?, telefono_movil1=?, telefono_movil2=?, email=?, direccion_web=?, codigo_departamento=?, codigo_provincia=?, codigo_distrito=? WHERE codigo_cliente=?;";

        PreparedStatement sentencia = transaccion.prepareStatement(sql);

        sentencia.setString(1, this.getApellido_paterno());
        sentencia.setString(2, this.getApellido_materno());
        sentencia.setString(3, this.getNombres());
        sentencia.setString(4, this.getNro_documento_identidad());
        sentencia.setString(5, this.getDireccion());
        sentencia.setString(6, this.getTelefono_fijo());
        sentencia.setString(7, this.getTelefono_movil1());
        sentencia.setString(8, this.getTelefono_movil2());
        sentencia.setString(9, this.getEmail());
        sentencia.setString(10, this.getDireccion_web());
        sentencia.setString(11, this.getCodigo_departamento());
        sentencia.setString(12, this.getCodigo_provincia());
        sentencia.setString(13, this.getCodigo_distrito());
        sentencia.setInt(14, this.getCodigo_cliente());

        this.ejecutarSQL(sentencia, transaccion);
        transaccion.commit();
        transaccion.close();

        return true;
    }

    public boolean eliminar() throws Exception {
        Connection transaccion = abrirConexion();
        transaccion.setAutoCommit(false);

        String sql = "delete from cliente where codigo_cliente=?";

        PreparedStatement sentencia = transaccion.prepareStatement(sql);
        sentencia.setInt(1, getCodigo_cliente());

        this.ejecutarSQL(sentencia, transaccion);
        transaccion.commit();
        transaccion.close();

        return true;
    }

    public ResultSet leerDatos(int cod_cli) throws Exception {
        String sql = "SELECT   cliente.codigo_cliente as codigo,   cliente.apellido_paterno,   cliente.apellido_materno,   cliente.nombres,   cliente.nro_documento_identidad as dni,  cliente.direccion,   cliente.telefono_fijo,   cliente.telefono_movil1,   cliente.telefono_movil2,   cliente.email,   cliente.direccion_web, departamento.nombre as departamento ,provincia.nombre as provincia,  distrito.nombre as distrito FROM   public.departamento,   public.provincia,   public.distrito,   public.cliente WHERE   provincia.codigo_departamento = departamento.codigo_departamento AND   provincia.codigo_provincia = distrito.codigo_provincia AND  provincia.codigo_departamento = distrito.codigo_departamento AND   distrito.codigo_departamento = cliente.codigo_departamento AND  distrito.codigo_provincia = cliente.codigo_provincia AND  distrito.codigo_distrito = cliente.codigo_distrito AND cliente.codigo_cliente=?";
        PreparedStatement sentencia = abrirConexion().prepareStatement(sql);
        sentencia.setInt(1, cod_cli);
        return ejecutarSQLSelectSP(sentencia);
    }

    public ResultSet listarPorDepartamento(String codigoDepartamento) throws Exception {
        String sql = "SELECT cliente.codigo_cliente as codigo,   cliente.apellido_paterno ||' '|| cliente.apellido_materno ||', '|| cliente.nombres as Nombres,   cliente.nro_documento_identidad as dni,  cliente.direccion,   cliente.telefono_fijo,   cliente.telefono_movil1,   cliente.telefono_movil2,   cliente.email,   cliente.direccion_web, departamento.nombre ||' / '||provincia.nombre||' / '|| distrito.nombre as Ubicacion FROM   public.departamento,   public.provincia,   public.distrito,   public.cliente WHERE   provincia.codigo_departamento = departamento.codigo_departamento AND   provincia.codigo_provincia = distrito.codigo_provincia AND  provincia.codigo_departamento = distrito.codigo_departamento AND   distrito.codigo_departamento = cliente.codigo_departamento AND  distrito.codigo_provincia = cliente.codigo_provincia AND  distrito.codigo_distrito = cliente.codigo_distrito and departamento.codigo_departamento=? ORDER BY  2";
        PreparedStatement sentencia = this.abrirConexion().prepareStatement(sql, ResultSet.FETCH_FORWARD, ResultSet.CONCUR_READ_ONLY);
        sentencia.setString(1, codigoDepartamento);
        ResultSet resultado = this.ejecutarSQLSelectSP(sentencia);
        return resultado;
    }

    public ResultSet listarPorProvincia(String codigoDepartamento, String codigoProvincia) throws Exception {
        String sql = "SELECT cliente.codigo_cliente as codigo,   cliente.apellido_paterno ||' '|| cliente.apellido_materno ||', '|| cliente.nombres as Nombres,   cliente.nro_documento_identidad as dni,  cliente.direccion,   cliente.telefono_fijo,   cliente.telefono_movil1,   cliente.telefono_movil2,   cliente.email,   cliente.direccion_web, departamento.nombre ||' / '||provincia.nombre||' / '|| distrito.nombre as Ubicacion FROM   public.departamento,   public.provincia,   public.distrito,   public.cliente WHERE   provincia.codigo_departamento = departamento.codigo_departamento AND   provincia.codigo_provincia = distrito.codigo_provincia AND  provincia.codigo_departamento = distrito.codigo_departamento AND   distrito.codigo_departamento = cliente.codigo_departamento AND  distrito.codigo_provincia = cliente.codigo_provincia AND  distrito.codigo_distrito = cliente.codigo_distrito and departamento.codigo_departamento=? and provincia.codigo_provincia=? ORDER BY  2";
        PreparedStatement sentencia = this.abrirConexion().prepareStatement(sql, ResultSet.FETCH_FORWARD, ResultSet.CONCUR_READ_ONLY);
        sentencia.setString(1, codigoDepartamento);
        sentencia.setString(2, codigoProvincia);
        ResultSet resultado = this.ejecutarSQLSelectSP(sentencia);
        return resultado;
    }

    public ResultSet listarPorDistrito(String codigoDepartamento, String codigoProvincia, String codigoDistrito) throws Exception {
        String sql = "SELECT cliente.codigo_cliente as codigo,   cliente.apellido_paterno ||' '|| cliente.apellido_materno ||', '|| cliente.nombres as Nombres,   cliente.nro_documento_identidad as dni,  cliente.direccion,   cliente.telefono_fijo,   cliente.telefono_movil1,   cliente.telefono_movil2,   cliente.email,   cliente.direccion_web, departamento.nombre ||' / '||provincia.nombre||' / '|| distrito.nombre as Ubicacion FROM   public.departamento,   public.provincia,   public.distrito,   public.cliente WHERE   provincia.codigo_departamento = departamento.codigo_departamento AND   provincia.codigo_provincia = distrito.codigo_provincia AND  provincia.codigo_departamento = distrito.codigo_departamento AND   distrito.codigo_departamento = cliente.codigo_departamento AND  distrito.codigo_provincia = cliente.codigo_provincia AND  distrito.codigo_distrito = cliente.codigo_distrito and departamento.codigo_departamento=? and provincia.codigo_provincia=? and distrito.codigo_distrito=? ORDER BY  2";
        PreparedStatement sentencia = this.abrirConexion().prepareStatement(sql, ResultSet.FETCH_FORWARD, ResultSet.CONCUR_READ_ONLY);
        sentencia.setString(1, codigoDepartamento);
        sentencia.setString(2, codigoProvincia);
        sentencia.setString(3, codigoDistrito);
        ResultSet resultado = this.ejecutarSQLSelectSP(sentencia);
        return resultado;
    }

    public String codigoDepartamento(String nombreDepartamento) throws Exception {
        String sql = "Select codigo_departamento from departamento where nombre=?";
        PreparedStatement sentencia = this.abrirConexion().prepareStatement(sql);
        sentencia.setString(1, nombreDepartamento);
        ResultSet resultado = this.ejecutarSQLSelectSP(sentencia);
        if (resultado.next()) {
            return resultado.getString("codigo_departamento");
        } else {
            throw new Exception("No existe departamento");
        }
    }

    public String codigoProvincia(String codigoDepartamento, String nombreProvincia) throws Exception {
        String sql = "select codigo_provincia from provincia p inner join departamento d on p.codigo_departamento=d.codigo_departamento where p.codigo_departamento=? and p.nombre=?";
        PreparedStatement sentencia = this.abrirConexion().prepareStatement(sql);
        sentencia.setString(1, codigoDepartamento);
        sentencia.setString(2, nombreProvincia);
        ResultSet resultado = this.ejecutarSQLSelectSP(sentencia);
        if (resultado.next()) {
            return resultado.getString("codigo_provincia");
        } else {
            throw new Exception("No existe provincia");
        }
    }

    public String codigoDistrito(String codigoDepartamento, String codigoProvincia, String nombreDistrito) throws Exception {
        String sql = "select codigo_distrito from provincia p inner join departamento d on p.codigo_departamento=d.codigo_departamento inner join distrito di on di.codigo_departamento=p.codigo_departamento and di.codigo_provincia=p.codigo_provincia where di.codigo_departamento=? and di.codigo_provincia=? and di.nombre=?";
        PreparedStatement sentencia = this.abrirConexion().prepareStatement(sql);
        sentencia.setString(1, codigoDepartamento);
        sentencia.setString(2, codigoProvincia);
        sentencia.setString(3, nombreDistrito);
        ResultSet resultado = this.ejecutarSQLSelectSP(sentencia);
        if (resultado.next()) {
            return resultado.getString("codigo_distrito");
        } else {
            throw new Exception("No existe distrito");
        }
    }

    public ResultSet leerDatosDniApellido(String cod_clie, int num) throws Exception {
        String sql = "";
        if (num == 1) {
            sql = "select * from cliente where nro_documento_identidad=?";
        } else if (num == 2) {
            sql = "select * from cliente where apelldio_paterno=?";
        }
        PreparedStatement sentencia = abrirConexion().prepareStatement(sql);
        sentencia.setString(1, cod_clie);
        return ejecutarSQLSelectSP(sentencia);
    }

    public ResultSet leerDatosDniApellido(String cod_clie) throws Exception {
        String sql = "";
        sql = "select * from cliente where nro_documento_identidad=?";

        PreparedStatement sentencia = abrirConexion().prepareStatement(sql);
        sentencia.setString(1, cod_clie);
        ResultSet resultado = ejecutarSQLSelectSP(sentencia);
        if (resultado.next()) {
            return resultado;
        }else {
            throw new Exception("No existe dni cliente");
        }
    }
}
