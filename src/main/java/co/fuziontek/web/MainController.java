package co.fuziontek.web;

import co.fuziontek.model.Departamento;
import co.fuziontek.model.Enfermedad;
import co.fuziontek.model.GrupoEtareo;
import co.fuziontek.model.Mortalidad;
import co.fuziontek.model.Poblacion;
import co.fuziontek.model.Region;
import co.fuziontek.model.Svca;
import co.fuziontek.model.dao.DepartamentoDAO;
import co.fuziontek.model.dao.EnfermedadDAO;
import co.fuziontek.model.dao.GrupoEtareoDAO;
import co.fuziontek.model.dao.MortalidadDAO;
import co.fuziontek.model.dao.PoblacionDAO;
import co.fuziontek.model.dao.RegionDAO;
import co.fuziontek.model.dao.SvcaDAO;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

/**
 *
 * @author Andres
 */
@RestController
public class MainController {
    
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
        
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
    
    
    @RequestMapping("/acces/uploadmortalidad")
    public String uploadMortalidad(@RequestParam("file") MultipartFile uploadfile) throws IOException{
        HSSFWorkbook wb = new HSSFWorkbook(uploadfile.getInputStream());
        crearDepartamentos(wb);
        crearEnfermedades(wb);
        crearMortalidad(wb);
        return "{}";
    }
    
    @RequestMapping("/acces/uploadpoblacion")
    public String uploadPoblacion(@RequestParam("file") MultipartFile uploadfile, @RequestParam("year")String year) throws IOException{
        logger.info("YEAR = " + year);
        HSSFWorkbook wb = new HSSFWorkbook(uploadfile.getInputStream());
        crearPoblacion(wb);
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
        cargarRegiones(wb);
        return "{}";
    }
    
    @RequestMapping("/acces/uploadidentificacioncau")
    public String uploadIdentificacionCau(@RequestParam("file") MultipartFile uploadfile) throws FileNotFoundException, IOException{
        HSSFWorkbook wb = new HSSFWorkbook(uploadfile.getInputStream());
        try{
            cargarSvca(wb);
        }catch(Exception e){
            return "{ error : '"+ e.getMessage() +" }";
        }
        return "{}";
    }
    
    public void crearDepartamentos(HSSFWorkbook wb){
        HSSFSheet sheet = wb.getSheetAt(0);
        for (int i=12; i<=44; i++) {
            Row row = sheet.getRow(i);
            Departamento dep = new Departamento(Long.parseLong(row.getCell(0).getStringCellValue()), row.getCell(1).getStringCellValue());
            departamentoDao.save(dep);
        }
    }

    private void crearEnfermedades(HSSFWorkbook wb) {
        HSSFSheet sheet = wb.getSheetAt(1);
        for (int i=12; i<=82; i++) {
            Row row = sheet.getRow(i);
            String value = row.getCell(0).getStringCellValue();
            Enfermedad enfermedad = new Enfermedad(Long.parseLong(value.substring(0, value.indexOf(" "))), value.substring(value.indexOf(" ")+1));
            enfermedadDao.save(enfermedad);
        }
    }

    private void crearMortalidad(HSSFWorkbook wb) {
        mortalidadDao.deleteAll();
        for (int j = 2; j < wb.getNumberOfSheets()-2; j++) {            
            HSSFSheet sheet = wb.getSheetAt(j);
            Departamento d = departamentoDao.findOne(Long.parseLong(sheet.getSheetName()));
            logger.info(d.getNombre());
            System.out.println(d.getNombre());
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
                    d = departamentoDao.findOne(id);
                    if(d==null)d = new Departamento(id, nombre);
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
                mortalidadDao.save(mortalidad);
            }
        }
    }   

    private void crearPoblacion(HSSFWorkbook wb) {
        HSSFSheet sheet = wb.getSheetAt(0);
        int column=0;
        int year=2015;
        for (Cell cell : sheet.getRow(4)) {
            if(cell.getCellType()== Cell.CELL_TYPE_NUMERIC && (int)cell.getNumericCellValue()==year){
                column = cell.getColumnIndex();
                break;
            }
        }
//        System.err.println(column);
//        System.err.println(sheet.getLastRowNum());
        poblacionDao.deleteAll();
        for (int i = 6; i < sheet.getLastRowNum();) {
            Row row = sheet.getRow(i);
            long idDept = Long.parseLong(row.getCell(0).getStringCellValue());
//            System.out.println(" ID : " + idDept);
            Departamento departamento = departamentoDao.findOne(idDept);
            if(departamento == null){
                departamento = new Departamento(idDept, row.getCell(1).getStringCellValue());
            }
            
            i++;
            for (int j = 0; j < 18; j++,i++) {
                row = sheet.getRow(i);
                GrupoEtareo grupo = grupoEtareoDao.findOne((long)j);
                if(grupo==null){
                    grupo = new GrupoEtareo((long)j, row.getCell(1).getStringCellValue());
                }
                Poblacion poblacion = new Poblacion((int)row.getCell(column).getNumericCellValue(), (int)row.getCell(column+1).getNumericCellValue(), (int)row.getCell(column+2).getNumericCellValue(), grupo, departamento);
                poblacionDao.save(poblacion);
            }
            System.out.println(departamento.getNombre());
            logger.info(departamento.getNombre());
        }
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
    
}
