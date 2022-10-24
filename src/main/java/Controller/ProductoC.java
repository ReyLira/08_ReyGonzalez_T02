package Controller;

import dao.ProductoImpl;
import java.io.IOException;
import java.io.Serializable;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.Logger;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.servlet.ServletContext;
import model.Producto;
import services.ReportePorductos;

@Named(value = "productoC")
@SessionScoped
public class ProductoC implements Serializable {

    private Producto producto;
    private ProductoImpl dao;
    private List<Producto> lstProducto;
    private int estado = 1;

    public ProductoC() {
        producto = new Producto();
        dao = new ProductoImpl();
    }

    public void registrar() throws Exception {
        try {
            if (!dao.existe(producto, lstProducto)) {
                dao.guardar(producto);
                listar();
                limpiar();
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Registrado", "Registrado con éxito"));
            } else {
                FacesContext.getCurrentInstance().addMessage(null,
                        new FacesMessage(FacesMessage.SEVERITY_INFO, "AVISO", "¡El producto ya se registro!"));
            }
        } catch (Exception e) {
            LogManager lgmngr = LogManager.getLogManager();
            Logger log = lgmngr.getLogger(Logger.GLOBAL_LOGGER_NAME);
            log.log(Level.INFO, "Error en ClienteC/registrar: {0}", e.getMessage());
        }
    }

    public void modificar() throws Exception {
        try {
            dao.modificar(producto);
            listar();
            limpiar();
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Modificado", "Registrado con éxito"));
        } catch (Exception e) {
            LogManager lgmngr = LogManager.getLogManager();
            Logger log = lgmngr.getLogger(Logger.GLOBAL_LOGGER_NAME);
            log.log(Level.INFO, "Error en modificar ProductoC: {0}", e.getMessage());
        }
    }

        //REPORTE VISTA PREVIA
    public void verSubReportePDFEST() throws SQLException, ClassNotFoundException, InstantiationException, IllegalAccessException, IOException {
        int idven = dao.UltimoProducto();
        System.out.println("venta " + idven);
        ReportePorductos reporte = new ReportePorductos();
        FacesContext facescontext = FacesContext.getCurrentInstance();
        ServletContext servletcontext = (ServletContext) facescontext.getExternalContext().getContext();
        String root = servletcontext.getRealPath("reporte/Prueba7.jasper");
        String numeroinformesocial = String.valueOf(idven);
        reporte.ReportePdfVentas(root, numeroinformesocial);
        FacesContext.getCurrentInstance().responseComplete();
    }
    
    //REPORTE VISTA PREVIA
    public void verReportePDFEST() throws SQLException, ClassNotFoundException, InstantiationException, IllegalAccessException, IOException {
        int idven = dao.UltimoProducto();
        System.out.println("venta " + idven);
        ReportePorductos reporte = new ReportePorductos();
        FacesContext facescontext = FacesContext.getCurrentInstance();
        ServletContext servletcontext = (ServletContext) facescontext.getExternalContext().getContext();
        String root = servletcontext.getRealPath("reporte/Prueba2.jasper");
        String numeroinformesocial = String.valueOf(idven);
        reporte.ReportePdfVentas(root, numeroinformesocial);
        FacesContext.getCurrentInstance().responseComplete();
    }
    
//        public void verReportePDFEST1() throws SQLException, ClassNotFoundException, InstantiationException, IllegalAccessException, IOException {
//        int idven = dao.UltimoProducto();
//        System.out.println("venta " + idven);
//        ReportePorductos reporte = new ReportePorductos();
//        FacesContext facescontext = FacesContext.getCurrentInstance();
//        ServletContext servletcontext = (ServletContext) facescontext.getExternalContext().getContext();
//        String root = servletcontext.getRealPath("reporte/Prueba3.jasper");
//        String numeroinformesocial = String.valueOf(idven);
//        reporte.ReportePdfVentas(root, numeroinformesocial);
//        FacesContext.getCurrentInstance().responseComplete();
//    }
//        
//        public void verReportePDFEST2() throws SQLException, ClassNotFoundException, InstantiationException, IllegalAccessException, IOException {
//        int idven = dao.UltimoProducto();
//        System.out.println("venta " + idven);
//        ReportePorductos reporte = new ReportePorductos();
//        FacesContext facescontext = FacesContext.getCurrentInstance();
//        ServletContext servletcontext = (ServletContext) facescontext.getExternalContext().getContext();
//        String root = servletcontext.getRealPath("reporte/Prueba4.jasper");
//        String numeroinformesocial = String.valueOf(idven);
//        reporte.ReportePdfVentas(root, numeroinformesocial);
//        FacesContext.getCurrentInstance().responseComplete();
//    }
//        
//        public void verReportePDFEST3() throws SQLException, ClassNotFoundException, InstantiationException, IllegalAccessException, IOException {
//        int idven = dao.UltimoProducto();
//        System.out.println("venta " + idven);
//        ReportePorductos reporte = new ReportePorductos();
//        FacesContext facescontext = FacesContext.getCurrentInstance();
//        ServletContext servletcontext = (ServletContext) facescontext.getExternalContext().getContext();
//        String root = servletcontext.getRealPath("reporte/Prueba5.jasper");
//        String numeroinformesocial = String.valueOf(idven);
//        reporte.ReportePdfVentas(root, numeroinformesocial);
//        FacesContext.getCurrentInstance().responseComplete();
//    }

    public void eliminar() throws Exception {
        try {
            dao.eliminar(producto);
            listar();
            limpiar();
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Eliminado", "Eliminado con éxito"));
        } catch (Exception e) {
            LogManager lgmngr = LogManager.getLogManager();
            Logger log = lgmngr.getLogger(Logger.GLOBAL_LOGGER_NAME);
            log.log(Level.INFO, "Error en eliminar ProductoC: {0}", e.getMessage());
        }
    }

    public String caseMayuscula(String camelcase) {
        char ch[] = camelcase.toCharArray();
        for (int i = 0; i < camelcase.length(); i++) {
            if (i == 0 && ch[i] != ' ' || ch[i] != ' ' && ch[i - 1] == ' ') {  // Si se encuentra el primer carácter de una palabra
                if (ch[i] >= 'a' && ch[i] <= 'z') {      // Si está en minúsculas
                    ch[i] = (char) (ch[i] - 'a' + 'A');  // Convertir en mayúsculas
                }
            } // Si aparte del primer carácter cualquiera está en mayúsculas
            else if (ch[i] >= 'a' && ch[i] <= 'z') {     // Convertir en minúsculas
                ch[i] = (char) (ch[i] - 'a' + 'A');
            }
        }
        String st = new String(ch);
        camelcase = st;
        return camelcase;
    }

    public void cambiarestado() throws Exception {
        try {
            dao.cambiarEstado(producto);
            listar();
            limpiar();
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Restaurado", "Restaurado con éxito"));
        } catch (Exception e) {
            LogManager lgmngr = LogManager.getLogManager();
            Logger log = lgmngr.getLogger(Logger.GLOBAL_LOGGER_NAME);
            log.log(Level.INFO, "Error en ProductoC/Restaurar: {0}", e.getMessage());
        }
    }

    public void listar() throws Exception {
        try {
            lstProducto = dao.listar(estado);
        } catch (Exception e) {
            LogManager lgmngr = LogManager.getLogManager();
            Logger log = lgmngr.getLogger(Logger.GLOBAL_LOGGER_NAME);
            log.log(Level.INFO, "Error en listar ProductoC: {0}", e.getMessage());
        }
    }

    public void limpiar() throws Exception {
        try {
            producto = new Producto();
        } catch (Exception e) {
            LogManager lgmngr = LogManager.getLogManager();
            Logger log = lgmngr.getLogger(Logger.GLOBAL_LOGGER_NAME);
            log.log(Level.INFO, "Error en  ProductoC/limpiar: {0}", e.getMessage());
        }
    }

    public void REPORTE_PDF_ADQUISICION(String CodigoUsuario) throws Exception {

        ProductoImpl ProductoImpl = new ProductoImpl();
        try {
            Map<String, Object> parameters = new HashMap();
            parameters.put(null, CodigoUsuario); //Insertamos un parametro
            ProductoImpl.REPORTE_PDF_PRODUCTO(parameters); //Pido exportar Reporte con los parametros
        } catch (Exception e) {
            throw e;
        }
    }

    public Producto getProducto() {
        return producto;
    }

    public void setProducto(Producto producto) {
        this.producto = producto;
    }

    public ProductoImpl getDao() {
        return dao;
    }

    public void setDao(ProductoImpl dao) {
        this.dao = dao;
    }

    public List<Producto> getLstProducto() {
        return lstProducto;
    }

    public void setLstProducto(List<Producto> lstProducto) {
        this.lstProducto = lstProducto;
    }

    public int getEstado() {
        return estado;
    }

    public void setEstado(int estado) {
        this.estado = estado;
    }

}
