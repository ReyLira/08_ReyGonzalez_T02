package dao;

import static dao.Conexion.conectar;
import java.io.File;
import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.Logger;
import javax.faces.context.FacesContext;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import model.Producto;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;

public class ProductoImpl extends Conexion implements ICRUD<Producto> {

    @Override
    public void guardar(Producto producto) throws Exception {
        String sql = "insert into PRODUCTO"
                + "(NOMPRO,PREPRO,CANPRO,DESPRO,TIPPRO,ESTPRO)"
                + "values(?,?,?,?,?,?)";
        try {
            PreparedStatement ps = this.conectar().prepareStatement(sql);
            ps.setString(1, producto.getNombre());
            ps.setDouble(2, producto.getPrecio());
            ps.setInt(3, producto.getCantidad());
            ps.setString(4, producto.getDescripcion());
            ps.setString(5, producto.getTipo());
            ps.setString(6, "A");
            ps.executeUpdate();
            ps.clearParameters();
            ps.close();
        } catch (Exception e) {
            LogManager lgmngr = LogManager.getLogManager();
            Logger log = lgmngr.getLogger(Logger.GLOBAL_LOGGER_NAME);
            log.log(Level.INFO, "Error en ProductoImpl/guardar: {0}", e.getMessage());
        }

    }

    @Override
    public void modificar(Producto producto) throws Exception {
        String sql = "update Producto set NOMPRO=?,PREPRO=?,CANPRO=?,DESPRO=?,TIPPRO=? where CODPRO=?";
        try {
            PreparedStatement ps = this.conectar().prepareStatement(sql);
            ps.setString(1, producto.getNombre());
            ps.setDouble(2, producto.getPrecio());
            ps.setInt(3, producto.getCantidad());
            ps.setString(4, producto.getDescripcion());
            ps.setString(5, producto.getTipo());
            ps.setInt(6, producto.getId());
            ps.executeUpdate();
            ps.close();
        } catch (Exception e) {
            LogManager lgmngr = LogManager.getLogManager();
            Logger log = lgmngr.getLogger(Logger.GLOBAL_LOGGER_NAME);
            log.log(Level.INFO, "Error en ProductoImpl/modificar: {0}", e.getMessage());
        }
    }

    @Override
    public void eliminar(Producto producto) throws Exception {
        try {
            String sql = "UPDATE PRODUCTO set ESTPRO = 'I' where CODPRO=?";
            PreparedStatement ps = this.conectar().prepareStatement(sql);
            ps.setInt(1, producto.getId());
            ps.executeUpdate();
        } catch (Exception e) {
            LogManager lgmngr = LogManager.getLogManager();
            Logger log = lgmngr.getLogger(Logger.GLOBAL_LOGGER_NAME);
            log.log(Level.INFO, "Error en ProductoImpl/eliminar: {0}", e.getMessage());
        }
    }

    public void cambiarEstado(Producto producto) throws Exception {
        try {
            String sql = "update PRODUCTO set ESTPRO='A' where CODPRO LIKE ?";
            PreparedStatement ps = this.conectar().prepareStatement(sql);
            ps.setInt(1, producto.getId());
            ps.executeUpdate();
            ps.close();
        } catch (Exception e) {
            System.out.println("Error en ProductoImpl/cambiarEstado: " + e.getMessage());
        }
    }

    public List<Producto> listar(int estado) throws Exception {
        List<Producto> lista = new ArrayList<>();

        ResultSet rs;
        String sql = "";
        switch (estado) {
            case 1:
                sql = "SELECT * FROM PRODUCTO WHERE ESTPRO ='A' ORDER BY CODPRO ASC";
                break;
            case 2:
                sql = "SELECT * FROM PRODUCTO WHERE ESTPRO ='I' ORDER BY CODPRO ASC";
                break;
            case 3:
                sql = "select * from PRODUCTO ORDER BY CODPRO ASC";
                break;
        }
        try {
            this.conectar();
            lista = new ArrayList();
            PreparedStatement ps = this.conectar().prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                Producto pro = new Producto();
                pro.setId(rs.getInt("CODPRO"));
                pro.setNombre(rs.getString("NOMPRO"));
                pro.setPrecio(rs.getDouble("PREPRO"));
                pro.setCantidad(rs.getInt("CANPRO"));
                pro.setDescripcion(rs.getString("DESPRO"));
                pro.setTipo(rs.getString("TIPPRO"));
                lista.add(pro);
            }
            rs.close();
            ps.close();
        } catch (Exception e) {
            LogManager lgmngr = LogManager.getLogManager();
            Logger log = lgmngr.getLogger(Logger.GLOBAL_LOGGER_NAME);
            log.log(Level.INFO, "Error en listar Producto{0}", e.getMessage());
        }
        return lista;
    }

    public boolean existe(Producto modelo, List<Producto> listaModelo) {
        for (Producto pro : listaModelo) {
// 	65789012  ---->	65789011, 65789032, 65789012  
            if (modelo.getNombre().equals(pro.getNombre())) {
                return true;
            }
        }
        return false;
    }

    public int UltimoProducto() {
        int nroVentas = 0;
        String sql = "SELECT MAX(CODPRO) FROM PRODUCTO";
        try {
            Statement st = this.conectar().createStatement();
            ResultSet rs = st.executeQuery(sql);

            while (rs.next()) {
                nroVentas = rs.getInt(1);
            }
        } catch (Exception e) {
            System.out.println("Error en ventas Maximas" + e.getMessage());
        }
        return nroVentas;
    }

    public void REPORTE_PDF_PRODUCTO(Map parameters) throws JRException, IOException, Exception {
        conectar();
//        System.out.println(parameters.get("NOMMAR"));
        File jasper = new File(FacesContext.getCurrentInstance().getExternalContext().getRealPath("reporte/Pizzas.jasper"));
        JasperPrint jasperPrint = JasperFillManager.fillReport(jasper.getPath(), parameters, this.conectar());
        HttpServletResponse response = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse();
        response.addHeader("Content-disposition", "attachment; filename=Prodcutos.pdf");
        try ( ServletOutputStream stream = response.getOutputStream()) {
            JasperExportManager.exportReportToPdfStream(jasperPrint, stream);
            stream.flush();
        }
        FacesContext.getCurrentInstance().responseComplete();
    }

    @Override
    public List<Producto> listarTodos() throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
