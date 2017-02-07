package co.fuziontek.web;

import co.fuziontek.model.Departamento;
import co.fuziontek.model.Enfermedad;
import co.fuziontek.model.Estacion;
import co.fuziontek.model.GrupoEtareo;
import co.fuziontek.model.Mortalidad;
import co.fuziontek.model.Poblacion;
import co.fuziontek.model.Region;
import co.fuziontek.model.Svca;
import co.fuziontek.model.dao.DepartamentoDAO;
import co.fuziontek.model.dao.EnfermedadDAO;
import co.fuziontek.model.dao.EstacionDAO;
import co.fuziontek.model.dao.GrupoEtareoDAO;
import co.fuziontek.model.dao.MortalidadDAO;
import co.fuziontek.model.dao.PoblacionDAO;
import co.fuziontek.model.dao.RegionDAO;
import co.fuziontek.model.dao.SvcaDAO;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

/**
 *
 * @author Andres
 */
@RestController
public class MainController {
    
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
        
    @PersistenceContext
    EntityManager em;
    
    @Autowired
    private DepartamentoDAO departamentoDao;
    
    @Autowired
    private EnfermedadDAO enfermedadDao;
    
    @Autowired
    private MortalidadDAO mortalidadDao;
    
    @Autowired
    private GrupoEtareoDAO grupoEtareoDao;
    
    @Autowired
    private PoblacionDAO poblacionDao;
    
    @Autowired
    private RegionDAO regionDao;
    
    @Autowired
    private SvcaDAO svcaDao;
    
    @Autowired
    private EstacionDAO estacionDao;
    
    Map<Long, Departamento> mapaDep = new HashMap<>();
    Map<Long, GrupoEtareo> mapaGrupoEtareo = new HashMap<>();
    Map<Long, Enfermedad> mapaEnfermedades = new HashMap<>();
    
    @RequestMapping("/acces/uploadmortalidad")
    @Transactional
    public String uploadMortalidad(@RequestParam("file") MultipartFile uploadfile) throws IOException{
        HSSFWorkbook wb = new HSSFWorkbook(uploadfile.getInputStream());
        try{
            crearMortalidad(wb);
        }catch(Exception ex){
            ex.printStackTrace();
            logger.error(ex.toString());
            return "{\"error\": \"Error cargando el archivo: Por favor verifique la estructura del archivo\"}";
        }
        return "{}";
    }
    
    @RequestMapping("/acces/uploadpoblacion")
    @Transactional
    public String uploadPoblacion(@RequestParam("file") MultipartFile uploadfile, @RequestParam("year")String year) throws IOException{
        logger.info("YEAR = " + year);
        HSSFWorkbook wb = new HSSFWorkbook(uploadfile.getInputStream());
        try{
            crearPoblacion(wb, year);
        }catch(Exception ex){
            ex.printStackTrace();
            logger.error(ex.toString());
            return "{\"error\": \"Error cargando el archivo: Por favor verifique la estructura del archivo\"}";
        }
        return "{}";
    }
    
    @RequestMapping("/acces/getdata")
    public String getData() throws FileNotFoundException, IOException{
        BufferedReader br = new BufferedReader(new FileReader(new File("data.csv")));
        StringBuilder sb = new StringBuilder();
	String line;
        while ((line = br.readLine()) != null) {
                sb.append(line);
                sb.append(":");
        } 
        br.close();
        return sb.toString();
    }
    
    @RequestMapping("/acces/verificarMortalidad")
    public Boolean verificarMortalidad() {
        return (mortalidadDao.count()>0 && poblacionDao.count()>0);
    }
    
    @RequestMapping("/acces/verificarIdentificacion")
    public Boolean verificarIdentificacion() {
        return regionDao.count()>0;
    }
    
    @RequestMapping("/acces/verificarIdentificacionCau")
    public Boolean verificarIdentificacionCau() {
        return svcaDao.count()>0;
    }
    
    
    @RequestMapping("/acces/uploadidentificacion")
    public String uploadIdentificacion(@RequestParam("file") MultipartFile uploadfile) throws FileNotFoundException, IOException{
        HSSFWorkbook wb = new HSSFWorkbook(uploadfile.getInputStream());
        try{
            cargarRegiones(wb);
        }catch(Exception ex){
            ex.printStackTrace();
            logger.error(ex.toString());
            return "{\"error\": \"Error cargando el archivo: Por favor verifique la estructura del archivo\"}";
        }
        return "{}";
    }
    
    @RequestMapping("/acces/uploadidentificacioncau")
    public String uploadIdentificacionCau(@RequestParam("file") MultipartFile uploadfile) throws FileNotFoundException, IOException{
        HSSFWorkbook wb = new HSSFWorkbook(uploadfile.getInputStream());
        try{
            cargarSvca(wb);
            cargarEstaciones(wb);
        }catch(Exception e){
            e.printStackTrace();
            logger.error(e.toString());
            return "{\"error\": \"Error cargando el archivo: Por favor verifique la estructura del archivo\"}";
        }
        return "{}";
    }
    
    @RequestMapping(value = "/acces/downloadDocTec", method = RequestMethod.POST)
    public void getDocTec(HttpServletRequest request, HttpServletResponse response) throws FileNotFoundException, IOException {
        InputStream is = new FileInputStream(new File("C:\\FT\\ACCes\\manual\\ManualTecnico_v1.0.docx"));
        response.setContentType("application/vnd.openxmlformats-officedocument.wordprocessingml.document");
        response.setHeader("Content-disposition", "attachment; filename=DocTecnico.docx");
        FileCopyUtils.copy(is, response.getOutputStream());
    }
    
    @RequestMapping(value = "/acces/downloadManual", method = RequestMethod.POST)
    public void getManual(HttpServletRequest request, HttpServletResponse response) throws FileNotFoundException, IOException {
        InputStream is = new FileInputStream(new File("C:\\FT\\ACCes\\manual\\ManualUsuario_v1.0.docx"));
        response.setContentType("application/vnd.openxmlformats-officedocument.wordprocessingml.document");
        response.setHeader("Content-disposition", "attachment; filename=ManualAcces.docx");
        FileCopyUtils.copy(is, response.getOutputStream());
    }
    
    public void crearDepartamentos(HSSFWorkbook wb){
        HSSFSheet sheet = wb.getSheetAt(0);
        for (int i=11; i<=44; i++) {
            Row row = sheet.getRow(i);
//            getDepartamento(Long.parseLong(row.getCell(0).getStringCellValue()), row.getCell(1).getStringCellValue());
            Departamento dep = new Departamento(Long.parseLong(row.getCell(0).getStringCellValue()), row.getCell(1).getStringCellValue());
            departamentoDao.save(dep);
        }
    }

    private void crearEnfermedades(HSSFWorkbook wb) {
        HSSFSheet sheet = wb.getSheetAt(1);
        for (int i=12; i<sheet.getLastRowNum(); i++) {
            Row row = sheet.getRow(i);
//            System.err.println("ROW -> "  + i + " " + row.getLastCellNum());
            if(row == null || row.getCell(0) == null)break;
            String value = row.getCell(0).getStringCellValue();
            if(value == null || value.equals(""))break;
            Enfermedad enfermedad = new Enfermedad(Long.parseLong(value.substring(0, value.indexOf(" "))), value.substring(value.indexOf(" ")+1));
            enfermedadDao.save(enfermedad);
        }
    }

    private void crearMortalidad(HSSFWorkbook wb) {
        crearDepartamentos(wb);
        crearEnfermedades(wb);
        mortalidadDao.deleteAll();
        HSSFSheet sheet = wb.getSheetAt(1);
        Departamento d = departamentoDao.findOne(0l);
        for (int i=12; i <= sheet.getLastRowNum(); i++) {
            Row row = sheet.getRow(i);
            if(row == null || row.getCell(0) == null || row.getCell(0).getStringCellValue() == null || row.getCell(0).getStringCellValue().equals(""))break;
            if(row.getCell(0).getStringCellValue().equals("TOTAL NACIONAL")){
                continue;
            }
            String value = row.getCell(0).getStringCellValue();
            System.err.println("VALUE - " + value + " ");
            Enfermedad e = enfermedadDao.findOne(Long.parseLong(value.substring(0, value.indexOf(" "))));
            Mortalidad mortalidad = new Mortalidad(d, e);
            mortalidad.setTotal((int)row.getCell(1).getNumericCellValue());
            mortalidad.setTotalHombres((int)row.getCell(2).getNumericCellValue());
            mortalidad.setTotalMujeres((int)row.getCell(3).getNumericCellValue());
            mortalidad.setTotalIndeterminado((int)row.getCell(4).getNumericCellValue());
            mortalidad.setHombres1menor((int)row.getCell(5).getNumericCellValue());
            mortalidad.setMujeres1menor((int)row.getCell(6).getNumericCellValue());
            mortalidad.setIndeterminado1menor((int)row.getCell(7).getNumericCellValue());
            mortalidad.setHombres1a4((int)row.getCell(8).getNumericCellValue());
            mortalidad.setMujeres1a4((int)row.getCell(9).getNumericCellValue());
            mortalidad.setIndeterminado1a4((int)row.getCell(10).getNumericCellValue());
            mortalidad.setHombres5a14((int)row.getCell(11).getNumericCellValue());
            mortalidad.setMujeres5a14((int)row.getCell(12).getNumericCellValue());
            mortalidad.setIndeterminado5a14((int)row.getCell(13).getNumericCellValue());
            mortalidad.setHombres15a44((int)row.getCell(14).getNumericCellValue());
            mortalidad.setMujeres15a44((int)row.getCell(15).getNumericCellValue());
            mortalidad.setIndeterminado15a44((int)row.getCell(16).getNumericCellValue());
            mortalidad.setHombres45a64((int)row.getCell(17).getNumericCellValue());
            mortalidad.setMujeres45a64((int)row.getCell(18).getNumericCellValue());
            mortalidad.setIndeterminado45a64((int)row.getCell(19).getNumericCellValue());
            mortalidad.setHombres65mayor((int)row.getCell(20).getNumericCellValue());
            mortalidad.setMujeres65mayor((int)row.getCell(21).getNumericCellValue());
            mortalidad.setIndeterminado65mayor((int)row.getCell(22).getNumericCellValue());
            em.merge(mortalidad);
        }
        em.flush();
        em.clear();
        
        for (int j = 2; j < wb.getNumberOfSheets()-2; j++) {
            sheet = wb.getSheetAt(j);
            d = departamentoDao.findOne(Long.parseLong(sheet.getSheetName()));
            logger.info(d.getNombre());
            for (int i=13; i <= sheet.getLastRowNum(); i++) {
                Row row = sheet.getRow(i);
                if(row == null || row.getCell(1).getStringCellValue() == null || row.getCell(1).getStringCellValue().equals(""))break;
                if(row.getCell(1).getStringCellValue().equals("TOTAL")){
                    while(row.getCell(0).getCellType() != Cell.CELL_TYPE_STRING){
                        i++;
                        row = sheet.getRow(i);
                    }
                    String llaveDep = row.getCell(0).getStringCellValue();
                    logger.info("\t"+llaveDep);
                    long id = Long.parseLong(llaveDep.substring(0, llaveDep.indexOf(' ')));
                    String nombre = llaveDep.substring(llaveDep.indexOf(' '));
                    d = getDepartamento(id, nombre);
                    continue;
                }
                String value = row.getCell(1).getStringCellValue();
//                System.err.println("VALUE - " + value + " ");
                Enfermedad e = enfermedadDao.findOne(Long.parseLong(value.substring(0, value.indexOf(" "))));
                Mortalidad mortalidad = new Mortalidad(d, e);
                mortalidad.setTotal((int)row.getCell(2).getNumericCellValue());
                mortalidad.setTotalHombres((int)row.getCell(3).getNumericCellValue());
                mortalidad.setTotalMujeres((int)row.getCell(4).getNumericCellValue());
                mortalidad.setTotalIndeterminado((int)row.getCell(5).getNumericCellValue());
                mortalidad.setHombres1menor((int)row.getCell(6).getNumericCellValue());
                mortalidad.setMujeres1menor((int)row.getCell(7).getNumericCellValue());
                mortalidad.setIndeterminado1menor((int)row.getCell(8).getNumericCellValue());
                mortalidad.setHombres1a4((int)row.getCell(9).getNumericCellValue());
                mortalidad.setMujeres1a4((int)row.getCell(10).getNumericCellValue());
                mortalidad.setIndeterminado1a4((int)row.getCell(11).getNumericCellValue());
                mortalidad.setHombres5a14((int)row.getCell(12).getNumericCellValue());
                mortalidad.setMujeres5a14((int)row.getCell(13).getNumericCellValue());
                mortalidad.setIndeterminado5a14((int)row.getCell(14).getNumericCellValue());
                mortalidad.setHombres15a44((int)row.getCell(15).getNumericCellValue());
                mortalidad.setMujeres15a44((int)row.getCell(16).getNumericCellValue());
                mortalidad.setIndeterminado15a44((int)row.getCell(17).getNumericCellValue());
                mortalidad.setHombres45a64((int)row.getCell(18).getNumericCellValue());
                mortalidad.setMujeres45a64((int)row.getCell(19).getNumericCellValue());
                mortalidad.setIndeterminado45a64((int)row.getCell(20).getNumericCellValue());
                mortalidad.setHombres65mayor((int)row.getCell(21).getNumericCellValue());
                mortalidad.setMujeres65mayor((int)row.getCell(22).getNumericCellValue());
                mortalidad.setIndeterminado65mayor((int)row.getCell(23).getNumericCellValue());
//                mortalidadDao.save(mortalidad);
                em.merge(mortalidad);
            }
            em.flush();
            em.clear();
        }
        deleteMaps();
    }   

    private void crearPoblacion(HSSFWorkbook wb, String y) {
        HSSFSheet sheet = wb.getSheetAt(0);
        int column=0;
        System.out.println(y);
        int year=Integer.parseInt(y);
        for (Cell cell : sheet.getRow(4)) {
            if(cell.getCellType()== Cell.CELL_TYPE_NUMERIC && (int)cell.getNumericCellValue()==year){
                column = cell.getColumnIndex();
                break;
            }
        }
        poblacionDao.deleteAll();
        List<Poblacion> poblacionList = new ArrayList<>();
        for (int i = 6; i < sheet.getLastRowNum();) {
            Row row = sheet.getRow(i);
            long idDept=0;
            try{
                idDept = Long.parseLong(row.getCell(0).getStringCellValue());
            }catch(NumberFormatException ex){
                break;
            }
            Departamento departamento = getDepartamento(idDept, row.getCell(1).getStringCellValue());
            i++;
            logger.info(departamento.getNombre());
            for (int j = 0; j < 18; j++,i++) {
                row = sheet.getRow(i);
                GrupoEtareo grupo = getGrupoEtareo(j, row.getCell(1).getStringCellValue());
                Poblacion poblacion = new Poblacion((long)(i-6),(int)row.getCell(column).getNumericCellValue(), (int)row.getCell(column+1).getNumericCellValue(), (int)row.getCell(column+2).getNumericCellValue(), grupo, departamento);
                poblacionList.add(poblacion);
            }
            
        }
        logger.info(String.valueOf(poblacionList.size()));
        bulkPoblacion(poblacionList);
        deleteMaps();
    }

    private void cargarRegiones(HSSFWorkbook wb){
        Sheet sheet = wb.getSheetAt(1);
        for (Row row : sheet) {
            Region region = new Region((long)row.getCell(1).getNumericCellValue(), row.getCell(0).getStringCellValue());
            regionDao.save(region);
        }
        System.out.println("regiones guardadas");
        logger.info("regiones guardadas");
        sheet = wb.getSheetAt(0);
        for (int i = 1; i <= sheet.getLastRowNum(); i++) {
            Departamento dep = departamentoDao.findOne((long)sheet.getRow(i).getCell(0).getNumericCellValue());
            Region reg = regionDao.findOne((long)sheet.getRow(i).getCell(2).getNumericCellValue());
            dep.setRegion(reg);
            departamentoDao.save(dep);
        }
    }

    private void cargarSvca(HSSFWorkbook wb) throws Exception {
        for (Departamento dep : departamentoDao.findAll()) {
            if(dep.getSvca()!=null){
                dep.setSvca(null);
                dep.setPromedioPM10(0);
                departamentoDao.save(dep);
            }
        }
        
        Sheet sheet = wb.getSheetAt(0);
        for (int i = 1; i <= sheet.getLastRowNum(); i++) {
            Departamento dep = departamentoDao.findOne((long)sheet.getRow(i).getCell(3).getNumericCellValue());
            if(dep==null){
                throw new Exception("El departamento numero " + (long)sheet.getRow(i).getCell(3).getNumericCellValue() + " no se encuentra en la base de datos");
            }
            Svca svca = svcaDao.findOne((long)sheet.getRow(i).getCell(4).getNumericCellValue());
            if(svca == null){
                svca = new Svca((long)sheet.getRow(i).getCell(4).getNumericCellValue(), sheet.getRow(i).getCell(5).getStringCellValue());
            }
            dep.setSvca(svca);
//            System.err.println("guardando " + svca.getNombre() + " " + dep.getId());
            departamentoDao.save(dep);
        }
        logger.info("Sistemas de vigilancia identificados");
//        System.out.println("Sistemas de vigilancia identificados");
    }
    
    public void bulkPoblacion(List<Poblacion> poblacionList){
        int batchSize = 1000;
        for (int i = 0; i < poblacionList.size(); i++) {
            Poblacion pob = poblacionList.get(i);
//            logger.info(pob.getId().toString());
//            logger.info(pob.getId() + " " +  pob.getDepartamento().getNombre());
            em.merge(pob);
            if(i % batchSize == 0) {
                em.flush();
                em.clear();
            }
        }
        em.flush();
        em.clear();
    }
    
    public Departamento getDepartamento(long idDept, String nombre){
        if(mapaDep.containsKey(idDept)){
            return mapaDep.get(idDept);
        }else{
            Departamento departamento = departamentoDao.findOne(idDept);
            if(departamento == null){
                departamento = new Departamento(idDept, nombre);
                departamentoDao.save(departamento);
            }
            mapaDep.put(idDept, departamento);
            return departamento;
        }
    }
    
    public GrupoEtareo getGrupoEtareo(long idGrupo, String nombre){
        if(mapaGrupoEtareo.containsKey(idGrupo)){
            return mapaGrupoEtareo.get(idGrupo);
        }else{
            GrupoEtareo grupo = grupoEtareoDao.findOne(idGrupo);
            if(grupo == null){
                grupo = new GrupoEtareo(idGrupo, nombre);
                grupoEtareoDao.save(grupo);
            }
            mapaGrupoEtareo.put(idGrupo, grupo);
            return grupo;
        }
    }
    
    public Enfermedad getEnfermedad(long idEnfermedad, String nombre){
        if(mapaEnfermedades.containsKey(idEnfermedad)){
            return mapaEnfermedades.get(idEnfermedad);
        }else{
            Enfermedad enfermedad = enfermedadDao.findOne(idEnfermedad);
            if(enfermedad == null){
                enfermedad = new Enfermedad(idEnfermedad, nombre);
                enfermedadDao.save(enfermedad);
            }
            mapaEnfermedades.put(idEnfermedad, enfermedad);
            return enfermedad;
        }
    }
    
    public void deleteMaps(){
        this.mapaDep.clear();
        this.mapaEnfermedades.clear();
        this.mapaGrupoEtareo.clear();
    }

    private void cargarEstaciones(HSSFWorkbook wb) throws Exception {
        estacionDao.deleteAll();
        Sheet sheet = wb.getSheetAt(1);
        for (int i = 1; i <= sheet.getLastRowNum(); i++) {
            Departamento dep = departamentoDao.findOne((long)sheet.getRow(i).getCell(2).getNumericCellValue());
            if(dep==null){
                throw new Exception("El departamento numero " + (long)sheet.getRow(i).getCell(3).getNumericCellValue() + " no se encuentra en la base de datos");
            }
            Estacion estacion = new Estacion(sheet.getRow(i).getCell(0).getStringCellValue(), dep);
            estacionDao.save(estacion);
        }
    }
    
}
