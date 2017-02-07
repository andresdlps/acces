package co.fuziontek.web;

import co.fuziontek.model.ConsolidadoAsh;
import co.fuziontek.model.ConsolidadoCai;
import co.fuziontek.model.ConsolidadoCau;
import co.fuziontek.model.Departamento;
import co.fuziontek.model.Enfermedad;
import co.fuziontek.model.Estacion;
import co.fuziontek.model.MorbilidadDesnutricion;
import co.fuziontek.model.Mortalidad;
import co.fuziontek.model.MortalidadConsolidadoCai;
import co.fuziontek.model.MortalidadConsolidadoCau;
import co.fuziontek.model.MortalidadDesnutricion;
import co.fuziontek.model.Poblacion;
import co.fuziontek.model.Region;
import co.fuziontek.model.Svca;
import co.fuziontek.model.dao.ConsolidadoAshDAO;
import co.fuziontek.model.dao.ConsolidadoCaiDAO;
import co.fuziontek.model.dao.ConsolidadoCauDAO;
import co.fuziontek.model.dao.DepartamentoDAO;
import co.fuziontek.model.dao.EnfermedadDAO;
import co.fuziontek.model.dao.EstacionDAO;
import co.fuziontek.model.dao.GrupoEtareoDAO;
import co.fuziontek.model.dao.MorbilidadDesnutricionDAO;
import co.fuziontek.model.dao.MortalidadConsolidadoCaiDAO;
import co.fuziontek.model.dao.MortalidadConsolidadoCauDAO;
import co.fuziontek.model.dao.MortalidadDAO;
import co.fuziontek.model.dao.MortalidadDesnutricionDAO;
import co.fuziontek.model.dao.PoblacionDAO;
import co.fuziontek.model.dao.RegionDAO;
import co.fuziontek.model.dao.SvcaDAO;
import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.SQLException;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.math3.distribution.NormalDistribution;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

/**
 *
 * @author Andres
 */
@RestController
public class ConsolidadoController {

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

    @Autowired
    private EstacionDAO estacionDao;

    @Autowired
    private ConsolidadoAshDAO consolidadoAshDAO;

    @Autowired
    private ConsolidadoCaiDAO consolidadoCaiDAO;

    @Autowired
    private MortalidadConsolidadoCaiDAO mortconsolidadoCaiDAO;

    @Autowired
    private ConsolidadoCauDAO consolidadoCauDAO;

    @Autowired
    private MortalidadConsolidadoCauDAO mortconsolidadoCauDAO;
    
    @Autowired
    private MortalidadDesnutricionDAO mortdesnutricionDAO;
    
    @Autowired
    private MorbilidadDesnutricionDAO morbdesnutricionDAO;
    
    @Autowired
    private BeanInsumos beanInsumos;

    NumberFormat format = NumberFormat.getInstance(Locale.FRANCE);

    @RequestMapping("/acces/setParamAsh")
    public ModelAndView parametersAsh(@RequestParam Map<String, String> allRequestParams) throws ParseException {
//        System.err.println("Set PARAMETERS INVOCADO!!!!  " + allRequestParams.size());
        consolidadoAshDAO.deleteAll();
        mortdesnutricionDAO.deleteAll();
        
        double promediodiarreacm=0,promediodiarreasro = 0;
        for (Region region : regionDao.findAll()) {
            long poblacionTotal = 0;
            long poblacionTotalHombres = 0;
            long poblacionTotalMujeres = 0;
            long poblacionTotalMenor5 = 0;
            long poblacionTotal5a64 = 0;
            long poblacionTotalMayor65 = 0;

            long mortalidadMenores5 = 0;

            System.out.println("Region " + region.getNombre() + " ID " + region.getId());
            for (Departamento dep : departamentoDao.findByRegion(region)) {
                System.out.println("          Dep " + dep.getNombre() + " ID " + dep.getId());
                for (Poblacion pob : poblacionDao.findByDepartamento(dep)) {
//                        System.out.println("                    pob " + pob.getGrupo_etareo().getNombre()+ " ID " + pob.getTotal());
                    if (pob.getGrupo_etareo().getId() == 0) {
                        poblacionTotal += pob.getTotal();
                        poblacionTotalHombres += pob.getHombres();
                        poblacionTotalMujeres += pob.getMujeres();
                    } else if (pob.getGrupo_etareo().getId() == 1) {
                        poblacionTotalMenor5 += pob.getTotal();
                    } else if (pob.getGrupo_etareo().getId() <= 13) {
                        if (pob.getGrupo_etareo().getId() >= 2) {
                            poblacionTotal5a64 += pob.getTotal();
                        }
                    } else if (pob.getGrupo_etareo().getId() >= 14) {
                        poblacionTotalMayor65 += pob.getTotal();
                    }
                }

                for (Mortalidad mort : mortalidadDao.findByDepartamento(dep)) {
                    if (mort.getEnfermedad().getId() == 101) {
                        mortalidadMenores5 += (mort.getHombres1menor() + mort.getHombres1a4())
                                + (mort.getMujeres1menor() + mort.getMujeres1a4())
                                + (mort.getIndeterminado1menor() + mort.getIndeterminado1a4());
                    }
                }
            }

            System.out.println("Region " + region.getNombre() + " -> " + poblacionTotal + "  " + poblacionTotalHombres + "  " + poblacionTotalMujeres + "  " + poblacionTotalMenor5 + "  " + poblacionTotal5a64 + "  " + poblacionTotalMayor65 + "  " + mortalidadMenores5);
            //Distribucion de porcentajes AP Y SB
            String acukey = allRequestParams.get("Acu" + region.getId());
            String alckey = allRequestParams.get("Alc" + region.getId());
            double distI = 0;
            double distII = 0;
            double distIV = 0;
            double distVa = 0;
            double distVb = 0;
            double distVI = 0;

            if (acukey != null && alckey != null) {
//                System.out.println("AcuKey =" + acukey);
//                System.out.println("AlcKey =" + alckey);
                double acukeynum = format.parse(acukey).doubleValue();
                double alckeynum = format.parse(alckey).doubleValue();
                distII = (alckeynum >= 98.0f && acukeynum >= 98.0f) ? (acukeynum > alckeynum) ? alckeynum : acukeynum : 0;
                distIV = (alckeynum >= 98.0f && acukeynum >= 98.0f) ? (acukeynum > alckeynum) ? alckeynum - distII : acukeynum - distII : (acukeynum > alckeynum) ? alckeynum : acukeynum;
                distVa = (acukeynum > alckeynum) ? 0 : alckeynum - acukeynum;
                distVb = (acukeynum > alckeynum) ? acukeynum - alckeynum : 0;
                distVI = (acukeynum > alckeynum) ? 100.0f - acukeynum : 100.0f - alckeynum;
            }

            distII = Math.round(distII * 100.0) / 100.0;
            distIV = Math.round(distIV * 100.0) / 100.0;
            distVa = Math.round(distVa * 100.0) / 100.0;
            distVb = Math.round(distVb * 100.0) / 100.0;
            distVI = Math.round(distVI * 100.0) / 100.0;

//            System.out.println("Region " + region.getNombre() + " -> \t" + distII + "\t" + distIV + "\t" + distVa + "\t" + distVb + "\t" + distVI);
            //Producto escenario RR
            double prodII = (format.parse(allRequestParams.get("RII")).doubleValue() * distII) / 100.0f;
            double prodIV = (format.parse(allRequestParams.get("RIV")).doubleValue() * distIV) / 100.0f;
            double prodVa = (format.parse(allRequestParams.get("RVa")).doubleValue() * distVa) / 100.0f;
            double prodVb = (format.parse(allRequestParams.get("RVb")).doubleValue() * distVb) / 100.0f;
            double prodVI = (format.parse(allRequestParams.get("RVI")).doubleValue() * distVI) / 100.0f;

            double sum = prodII + prodIV + prodVa + prodVb + prodVI;
            double fa = (sum - 1.0f) / sum;

//            sum = Math.round (sum * 100.0) / 100.0;
//            fa = Math.round (fa * 100.0) / 100.0;  
            System.out.println("Region " + region.getNombre() + " -> \t" + sum + "\t" + fa);

            ConsolidadoAsh ash = new ConsolidadoAsh();

            ash.setMortNum(fa * mortalidadMenores5);
            ash.setMortAVAD(ash.getMortNum() * format.parse(allRequestParams.get("AVADmort")).doubleValue());
            ash.setMortCostos((ash.getMortNum() * format.parse(allRequestParams.get("VEV")).doubleValue()) / 1000000000.0);

//            System.out.printf("Region %s -> \t %f \t %f \t %f \n", region.getNombre(), ash.getMortNum(), ash.getMortAVAD(), ash.getMortCostos());
            ash.setMorbMenor5Inc((format.parse(allRequestParams.get("Dia" + region.getId())).doubleValue() * 52.0) / (format.parse(allRequestParams.get("perRec")).doubleValue() * 100.0));
            ash.setMorbMenor5(ash.getMorbMenor5Inc() * poblacionTotalMenor5);
            ash.setMorbMenor5Ash(ash.getMorbMenor5() * fa);
            ash.setMorbMenor5AVADAsh(ash.getMorbMenor5Ash() * format.parse(allRequestParams.get("AVADmorbMenor5")).doubleValue());
            double diarreaCM = format.parse(allRequestParams.get("DiaCm" + region.getId())).doubleValue() / 100.0;
            double diarreaSuero = format.parse(allRequestParams.get("DiaSue" + region.getId())).doubleValue() / 100.0;
            ash.setMorbMenor5CostosAsh((((diarreaCM * format.parse(allRequestParams.get("ValCons")).doubleValue())
                    + (diarreaCM * format.parse(allRequestParams.get("ValMeds")).doubleValue())
                    + (diarreaSuero * format.parse(allRequestParams.get("ValSue")).doubleValue())
                    + (format.parse(allRequestParams.get("CostMenorHosp")).doubleValue() / 100.0) * ((format.parse(allRequestParams.get("Chosp")).doubleValue()) * (format.parse(allRequestParams.get("DurHosp")).doubleValue()) + ((format.parse(allRequestParams.get("DurHosp")).doubleValue()) * (format.parse(allRequestParams.get("DurVisitas")).doubleValue()) * (format.parse(allRequestParams.get("CostAdult")).doubleValue())))
                    + (format.parse(allRequestParams.get("TiempoDed")).doubleValue()) * (format.parse(allRequestParams.get("EnfDur")).doubleValue()) * (format.parse(allRequestParams.get("CostAdult")).doubleValue()))
                    * ash.getMorbMenor5Ash()) / 1000000000.0);

//            System.out.printf("Region %s -> \t %.2f \t %.2f \t %.2f \t %.2f \t %.2f \n", region.getNombre(), ash.getMorbMenor5Inc(), ash.getMorbMenor5(), ash.getMorbMenor5Ash(), ash.getMorbMenor5AVADAsh(), ash.getMorbMenor5CostosAsh());
            ash.setMorbMayor5Inc(((format.parse(allRequestParams.get("Dia" + region.getId())).doubleValue() * 52.0) / (format.parse(allRequestParams.get("perRec")).doubleValue() * 100.0)) * format.parse(allRequestParams.get("Rips")).doubleValue() * format.parse(allRequestParams.get("Rmed")).doubleValue());
            ash.setMorbMayor5(ash.getMorbMayor5Inc() * (poblacionTotal5a64 + poblacionTotalMayor65));
            ash.setMorbMayor5Ash(ash.getMorbMayor5() * fa);
            ash.setMorbMayor5AVADAsh(ash.getMorbMayor5Ash() * format.parse(allRequestParams.get("AVADmorbMayor5")).doubleValue());
            ash.setMorbMayor5CostosAsh(((((format.parse(allRequestParams.get("DiaCmMayor")).doubleValue() / 100.0) * format.parse(allRequestParams.get("ValCons")).doubleValue())
                    + ((format.parse(allRequestParams.get("DiaCmMayor")).doubleValue() / 100.0) * format.parse(allRequestParams.get("ValMeds")).doubleValue())
                    + ((format.parse(allRequestParams.get("CostMayorHosp")).doubleValue() / 100.0) * ((format.parse(allRequestParams.get("Chosp")).doubleValue()) * (format.parse(allRequestParams.get("DurHosp")).doubleValue()) + ((format.parse(allRequestParams.get("DurHosp")).doubleValue()) * (format.parse(allRequestParams.get("DurVisitas")).doubleValue()) * (format.parse(allRequestParams.get("CostAdult")).doubleValue()))))
                    + (format.parse(allRequestParams.get("TiempoPerd")).doubleValue()) * (format.parse(allRequestParams.get("EnfDur")).doubleValue()) * (format.parse(allRequestParams.get("CostAdult")).doubleValue()))
                    * ash.getMorbMayor5Ash()) / 1000000000.0);

//            System.err.println((poblacionTotal5a64 + poblacionTotalMayor65));
//            System.err.println(ash.getMorbMayor5Inc());
//            System.err.println(ash.getMorbMayor5Inc()*(poblacionTotal5a64 + poblacionTotalMayor65));
//            System.out.printf("Region %s -> \t %.2f \t %.2f \t %.2f \t %.2f \t %.2f \n", region.getNombre(), ash.getMorbMayor5Inc(), ash.getMorbMayor5(), ash.getMorbMayor5Ash(), ash.getMorbMayor5AVADAsh(), ash.getMorbMayor5CostosAsh());
            consolidadoAshDAO.save(ash);
            
            promediodiarreacm += format.parse(allRequestParams.get("DiaCm" + region.getId())).doubleValue() / 100.0;
            promediodiarreasro += format.parse(allRequestParams.get("DiaSue" + region.getId())).doubleValue() / 100.0;
        }
        
        promediodiarreacm /= regionDao.count();
        promediodiarreasro /= regionDao.count();
        
        
        double BajoPesoHombres =  format.parse(allRequestParams.get("BajoPesoHombres")).doubleValue()/100;
        double BajoPesoMujeres =  format.parse(allRequestParams.get("BajoPesoMujeres")).doubleValue()/100;
        
        double norminvhombres = norminv(BajoPesoHombres, 0, 1);
        double norminvmujeres = norminv(BajoPesoMujeres, 0, 1);
        
        double mediahombres = -(2+norminvhombres);
        double mediamujeres = -(2+norminvmujeres);
        
        double uwexposure4hombres = CNDF(-3-mediahombres);
        double uwexposure4mujeres = CNDF(-3-mediamujeres);
        
        double uwexposure3hombres = CNDF(-2-mediahombres) - CNDF(-3-mediahombres);
        double uwexposure3mujeres = CNDF(-2-mediamujeres) - CNDF(-3-mediamujeres);
        
        double uwexposure2hombres = CNDF(-1-mediahombres) - CNDF(-2-mediahombres);
        double uwexposure2mujeres = CNDF(-1-mediamujeres) - CNDF(-2-mediamujeres);
        
        double uwexposure1hombres = 1 - (uwexposure4hombres + uwexposure3hombres + uwexposure2hombres);
        double uwexposure1mujeres = 1 - (uwexposure4mujeres + uwexposure3mujeres + uwexposure2mujeres);

        long totalMortalidadDiarreaH = 0;
        long totalMortalidadDiarreaM = 0;
        long totalMortalidadIRAH = 0;
        long totalMortalidadIRAM = 0;
        long totalMortalidadOtrasH = 0;
        long totalMortalidadOtrasM = 0;
        long totalMortalidadMalnutricionH = 0;
        long totalMortalidadMalnutricionM = 0;

        for (Mortalidad mort : mortalidadDao.findByDepartamento(departamentoDao.findOne(0l))) {
            if (mort.getEnfermedad().getId() == 101) {
                totalMortalidadDiarreaH += (mort.getHombres1menor() + mort.getHombres1a4());
                totalMortalidadDiarreaM += (mort.getMujeres1menor() + mort.getMujeres1a4());
            }
            if (mort.getEnfermedad().getId() != 101 && mort.getEnfermedad().getId() != 108 && mort.getEnfermedad().getId() != 109 && !(mort.getEnfermedad().getId() <= 407 && mort.getEnfermedad().getId() >= 401) && mort.getEnfermedad().getId() != 602) {
                totalMortalidadOtrasH += (mort.getHombres1menor() + mort.getHombres1a4());
                totalMortalidadOtrasM += (mort.getMujeres1menor() + mort.getMujeres1a4());
            }
            if (mort.getEnfermedad().getId() == 109) {
                totalMortalidadIRAH += (mort.getHombres1menor() + mort.getHombres1a4());
                totalMortalidadIRAM += (mort.getMujeres1menor() + mort.getMujeres1a4());
            }
            if (mort.getEnfermedad().getId() == 602) {
                totalMortalidadMalnutricionH += (mort.getHombres1menor() + mort.getHombres1a4());
                totalMortalidadMalnutricionM += (mort.getMujeres1menor() + mort.getMujeres1a4());
            }
        }
        
        System.err.println("EXPOSURE 1 H " + uwexposure1hombres);
        System.err.println("EXPOSURE 1 M " + uwexposure1mujeres);
        
        double MTNivel1 =  format.parse(allRequestParams.get("MTNivel1")).doubleValue()/100;
        double MTNivel2 =  format.parse(allRequestParams.get("MTNivel2")).doubleValue()/100;
        double MTNivel3 =  format.parse(allRequestParams.get("MTNivel3")).doubleValue()/100;
        double MTNivel4 =  format.parse(allRequestParams.get("MTNivel4")).doubleValue()/100;
        
//        System.err.println("MT " + MTNivel1 + " " + MTNivel2 + " " + MTNivel3 + " " + MTNivel4);
        
        double BajoPesoRecienNacidos = format.parse(allRequestParams.get("BajoPesoRecienNacidos")).doubleValue();
        double IMCMujeres15a29 = format.parse(allRequestParams.get("IMCMujeres15a29")).doubleValue();
        double IMCMujeres30a44 = format.parse(allRequestParams.get("IMCMujeres30a44")).doubleValue();
        double Mujeres15a29 = format.parse(allRequestParams.get("Mujeres15a29")).doubleValue();
        double Mujeres30a44 = 1-Mujeres15a29;
        double IUGR15a29 = format.parse(allRequestParams.get("IUGR15a29")).doubleValue();
        double IUGR30a44= format.parse(allRequestParams.get("IUGR30a44")).doubleValue();
        double neonatalIUGR15a29 = format.parse(allRequestParams.get("neonatalIUGR15a29")).doubleValue();
        double neonatalIUGR30a44 = format.parse(allRequestParams.get("neonatalIUGR30a44")).doubleValue();
        
        
        double FAIUGR15a29 = (IMCMujeres15a29/100*(IUGR15a29-1))/(1+IMCMujeres15a29/100*(IUGR15a29-1));
        double FAIUGR30a44 = (IMCMujeres30a44/100*(IUGR30a44-1))/(1+IMCMujeres30a44/100*(IUGR30a44-1));
        
        double prev15a29 = (-3.2452+0.8528*BajoPesoRecienNacidos)/100.0;
        double prev30a44 = (-3.2452+0.8528*BajoPesoRecienNacidos)/100.0;
        
        double FAmuerteneonatal15a29 = (prev15a29*(neonatalIUGR15a29-1))/(1+prev15a29*(neonatalIUGR15a29-1));
        double FAmuerteneonatal30a44 = (prev30a44*(neonatalIUGR30a44-1))/(1+prev30a44*(neonatalIUGR30a44-1));
        
        double FA = (Mujeres15a29*(FAIUGR15a29*FAmuerteneonatal15a29))+(Mujeres30a44*(FAIUGR30a44*FAmuerteneonatal30a44));
        
        String enfdesnutricion[] = {"Diarrea", "Malaria", "Sarampion", "IRA", "Otras", "Desnutrición proteínico", "Muertes neonatales"};
        
        for (String enf : enfdesnutricion) {
            double RR1 = 0, RR2 = 0, RR3 = 0, RR4 = 0;
            double muerteshombres = 0, muertesmujeres = 0; 
            if(enf.equals("Diarrea")){
                RR1 =  format.parse(allRequestParams.get("RRDiarrea1")).doubleValue();
                RR2 =  format.parse(allRequestParams.get("RRDiarrea2")).doubleValue();
                RR3 =  format.parse(allRequestParams.get("RRDiarrea3")).doubleValue();
                RR4 =  format.parse(allRequestParams.get("RRDiarrea4")).doubleValue();
                muerteshombres = totalMortalidadDiarreaH;
                muertesmujeres = totalMortalidadDiarreaM;
            }else if(enf.equals("Malaria")){
                RR1 =  format.parse(allRequestParams.get("RRMalaria1")).doubleValue();
                RR2 =  format.parse(allRequestParams.get("RRMalaria2")).doubleValue();
                RR3 =  format.parse(allRequestParams.get("RRMalaria3")).doubleValue();
                RR4 =  format.parse(allRequestParams.get("RRMalaria4")).doubleValue();
                muerteshombres = format.parse(allRequestParams.get("MuertesMalariaHombres")).doubleValue();
                muertesmujeres = format.parse(allRequestParams.get("MuertesMalariaMujeres")).doubleValue();
            }else if(enf.equals("Sarampion")){
                RR1 =  format.parse(allRequestParams.get("RRSarampion1")).doubleValue();
                RR2 =  format.parse(allRequestParams.get("RRSarampion2")).doubleValue();
                RR3 =  format.parse(allRequestParams.get("RRSarampion3")).doubleValue();
                RR4 =  format.parse(allRequestParams.get("RRSarampion4")).doubleValue();
                muerteshombres = format.parse(allRequestParams.get("MuertesSarampionHombres")).doubleValue();
                muertesmujeres = format.parse(allRequestParams.get("MuertesSarampionMujeres")).doubleValue();
            }else if(enf.equals("IRA")){
                RR1 =  format.parse(allRequestParams.get("RRIRA1")).doubleValue();
                RR2 =  format.parse(allRequestParams.get("RRIRA2")).doubleValue();
                RR3 =  format.parse(allRequestParams.get("RRIRA3")).doubleValue();
                RR4 =  format.parse(allRequestParams.get("RRIRA4")).doubleValue();
                muerteshombres = totalMortalidadIRAH;
                muertesmujeres = totalMortalidadIRAM;
            }else if(enf.equals("Otras")){
                RR1 =  format.parse(allRequestParams.get("RROtras1")).doubleValue();
                RR2 =  format.parse(allRequestParams.get("RROtras2")).doubleValue();
                RR3 =  format.parse(allRequestParams.get("RROtras3")).doubleValue();
                RR4 =  format.parse(allRequestParams.get("RROtras4")).doubleValue();
                muerteshombres = totalMortalidadOtrasH;
                muertesmujeres = totalMortalidadOtrasM;
            }
            
            double FAhombres=0, FAmujeres=0;
            
            if(enf.equals("Desnutrición proteínico")){
                muerteshombres = totalMortalidadMalnutricionH;
                muertesmujeres = totalMortalidadMalnutricionM;
                FAhombres = 1;
                FAmujeres = 1;
            }else if(enf.equals("Muertes neonatales")){
                muerteshombres = format.parse(allRequestParams.get("MuertesNeonatalesHombres")).doubleValue();
                muertesmujeres = format.parse(allRequestParams.get("MuertesNeonatalesMujeres")).doubleValue();
//                FAhombres = 0.019;
//                FAmujeres = 0.019;
                FAhombres = FA;
                FAmujeres = FA;
            }else{
                
//                System.out.println(enf + " -> " + RR1 + " " + RR2 + " " + RR3 + " " + RR4);
                
                
                double sumaprodH = (RR1*uwexposure1hombres) + (RR2*uwexposure2hombres) + (RR3*uwexposure3hombres) +(RR4*uwexposure4hombres);
                double sumaprodM = (RR1*uwexposure1mujeres) + (RR2*uwexposure2mujeres) + (RR3*uwexposure3mujeres) +(RR4*uwexposure4mujeres);
                double sumaprodMT = (RR1*MTNivel1) + (RR2*MTNivel2) + (RR3*MTNivel3) + (RR4*MTNivel4);
                
//                System.out.println("SUMA PROD H " + sumaprodH);
//                System.out.println("SUMA PROD M " + sumaprodM);
//                System.out.println("SUMA PROD MIN T " + sumaprodMT);

                FAhombres = (sumaprodH - sumaprodMT)/sumaprodH;
                FAmujeres = (sumaprodM - sumaprodMT)/sumaprodM;
            }
            
            System.err.println(enf + " ->  FA " + FAhombres + " " + FAmujeres);
            System.err.println("MUERTES HOMBRES  = " + muerteshombres + " MUJERES = " + muertesmujeres);
            
            MortalidadDesnutricion desnutricion = new MortalidadDesnutricion(enf, (FAhombres*muerteshombres), (FAmujeres*muertesmujeres));
            desnutricion.setMortalidadMenoresCostos((desnutricion.getMuertesMenoresTotal()*format.parse(allRequestParams.get("VEV")).doubleValue())/1000000000.0f);
            mortdesnutricionDAO.save(desnutricion);
        }
        
        if(hasConsolidadoCai()){
            
            morbdesnutricionDAO.deleteAll();
            
            double sumaCAIIRA=0, sumaASHDiarrea=0;
            for (ConsolidadoCai cai : consolidadoCaiDAO.findAll()) {
                sumaCAIIRA += cai.getMorbMenor5();
            }
            
            for (ConsolidadoAsh ash : consolidadoAshDAO.findAll()) {
                sumaASHDiarrea += ash.getMorbMenor5();
            }
            
//            System.err.println("SUMAS -----> " + sumaASHDiarrea + " " + sumaCAIIRA);
            
            enfdesnutricion = new String[]{"Diarrea", "IRA"};
            
            for (String enf : enfdesnutricion) {
                double FAhombres=0, FAmujeres=0;
                double RR1 = 0, RR2 = 0;
                double totalhombres = 0, totalmujeres = 0, AVAD=0;
                if(enf.equals("Diarrea")){
                    RR1 = format.parse(allRequestParams.get("RRMorbDiarrea1")).doubleValue();
                    RR2 = format.parse(allRequestParams.get("RRMorbDiarrea2")).doubleValue();
                    totalhombres = sumaASHDiarrea/2.0;
                    totalmujeres = sumaASHDiarrea/2.0;
                    AVAD = format.parse(allRequestParams.get("AVADmorbMenor5")).doubleValue();
                }else if(enf.equals("IRA")){
                    RR1 = format.parse(allRequestParams.get("RRMorbIRA1")).doubleValue();
                    RR2 = format.parse(allRequestParams.get("RRMorbIRA2")).doubleValue();
                    totalhombres = sumaCAIIRA/2.0;
                    totalmujeres = sumaCAIIRA/2.0;
                    AVAD = beanInsumos.getAVADmorbMenor5IRA();
                }
                
                
                double sumaprodH = (RR1*(uwexposure1hombres + uwexposure2hombres)) + (RR2*(uwexposure3hombres + uwexposure4hombres));
                double sumaprodM = (RR1*(uwexposure1mujeres + uwexposure2mujeres)) + (RR2*(uwexposure3mujeres + uwexposure4mujeres));
                double sumaprodMT = (RR1*(MTNivel1+MTNivel2)) + (RR2*(MTNivel3+MTNivel4));
                
//                System.err.println("MT -> " + (MTNivel1+MTNivel2) + " " + (MTNivel3+MTNivel4));
                
                FAhombres = (sumaprodH - sumaprodMT)/sumaprodH;
                FAmujeres = (sumaprodM - sumaprodMT)/sumaprodM;
                
//                System.err.println("FA -> " + FAhombres + " " + FAmujeres);
                
                MorbilidadDesnutricion morbdesnutricion = new MorbilidadDesnutricion(enf, totalhombres*FAhombres, totalmujeres*FAmujeres);
                morbdesnutricion.setMorbilidadMenores5AVAD(morbdesnutricion.getMorbilidadMenores5Total()*AVAD);
                
                if(enf.equals("Diarrea")){
                    morbdesnutricion.setMorbilidadMenores5Costos(((promediodiarreacm*beanInsumos.getPrecioConsultaCAI())+(promediodiarreacm*beanInsumos.getPrecioMedicinasCAI())
                            +(promediodiarreasro*format.parse(allRequestParams.get("ValSue")).doubleValue())
                            +((format.parse(allRequestParams.get("CostMenorHosp")).doubleValue() / 100.0)*(format.parse(allRequestParams.get("DurHosp")).doubleValue()*promediodiarreasro+format.parse(allRequestParams.get("DurVisitas")).doubleValue()*format.parse(allRequestParams.get("EnfDur")).doubleValue()*format.parse(allRequestParams.get("CostAdult")).doubleValue()))
                            +(format.parse(allRequestParams.get("TiempoDed")).doubleValue()*format.parse(allRequestParams.get("EnfDur")).doubleValue()*format.parse(allRequestParams.get("CostAdult")).doubleValue()))*morbdesnutricion.getMorbilidadMenores5Total()/1000000000.0);
                }else if(enf.equals("IRA")){
                    morbdesnutricion.setMorbilidadMenores5Costos(((beanInsumos.getPromedioPorcentajeIRACM()*format.parse(allRequestParams.get("ValCons")).doubleValue())+(beanInsumos.getPorcentajeIRAmed()*format.parse(allRequestParams.get("ValMeds")).doubleValue()))*morbdesnutricion.getMorbilidadMenores5Total()/1000000000.0);
                }
                
                morbdesnutricionDAO.save(morbdesnutricion);
                
            }
        }
        

        return new ModelAndView("redirect:calcular");
    }

    @RequestMapping("/acces/setParamCai")
    public ModelAndView parametersCai(@RequestParam Map<String, String> allRequestParams) throws ParseException {
        mortconsolidadoCaiDAO.deleteAll();
        consolidadoCaiDAO.deleteAll();
        
        double sumaporcentajecm = 0;
        
        for (Region region : regionDao.findAll()) {
            long poblacionTotal = 0;
            long poblacionTotalHombres = 0;
            long poblacionTotalMujeres = 0;
            long poblacionTotalMenor5 = 0;
            long poblacionTotalHombresMenor5 = 0;
            long poblacionTotalMujeresMenor5 = 0;

            long poblacionTotalMujeres45a64 = 0;
            long poblacionTotalMujeresMayor65 = 0;

            long mortalidadMenores5_109 = 0;

            long mortalidadMujeres45a64_206 = 0;
            long mortalidadMujeres45a64_302 = 0;
            long mortalidadMujeres45a64_303 = 0;
            long mortalidadMujeres45a64_307 = 0;
            long mortalidadMujeres45a64_605 = 0;

            long mortalidadMujeresMayor65_206 = 0;
            long mortalidadMujeresMayor65_302 = 0;
            long mortalidadMujeresMayor65_303 = 0;
            long mortalidadMujeresMayor65_307 = 0;
            long mortalidadMujeresMayor65_605 = 0;

            for (Departamento dep : departamentoDao.findByRegion(region)) {
                int poblaciontotal = 0;
                for (Poblacion pob : poblacionDao.findByDepartamento(dep)) {
                    if (pob.getGrupo_etareo().getId() == 0) {
                        poblacionTotal += pob.getTotal();
                        poblacionTotalHombres += pob.getHombres();
                        poblacionTotalMujeres += pob.getMujeres();
                    } else if (pob.getGrupo_etareo().getId() == 1) {
                        poblacionTotalMenor5 += pob.getTotal();
                        poblacionTotalHombresMenor5 += pob.getHombres();
                        poblacionTotalMujeresMenor5 += pob.getMujeres();
                    } else if (pob.getGrupo_etareo().getId() <= 13) {
                        if (pob.getGrupo_etareo().getId() >= 10) {
                            poblacionTotalMujeres45a64 += pob.getMujeres();
                        }
                    } else if (pob.getGrupo_etareo().getId() >= 14) {
                        poblacionTotalMujeresMayor65 += pob.getMujeres();
                    }
                }
                for (Mortalidad mort : mortalidadDao.findByDepartamento(dep)) {
                    if (mort.getEnfermedad().getId() == 109) {
                        mortalidadMenores5_109 += (mort.getHombres1menor() + mort.getHombres1a4())
                                + (mort.getMujeres1menor() + mort.getMujeres1a4())
                                + (mort.getIndeterminado1menor() + mort.getIndeterminado1a4());
                    }
                    if (mort.getEnfermedad().getId() == 303) {
                        mortalidadMujeres45a64_303 += mort.getMujeres45a64();
                        mortalidadMujeresMayor65_303 += mort.getMujeres65mayor();
                    }
                    if (mort.getEnfermedad().getId() == 307) {
                        mortalidadMujeres45a64_307 += mort.getMujeres45a64();
                        mortalidadMujeresMayor65_307 += mort.getMujeres65mayor();
                    }
                    if (mort.getEnfermedad().getId() == 206) {
                        mortalidadMujeres45a64_206 += mort.getMujeres45a64();
                        mortalidadMujeresMayor65_206 += mort.getMujeres65mayor();
                    }
                    if (mort.getEnfermedad().getId() == 302) {
                        mortalidadMujeres45a64_302 += mort.getMujeres45a64();
                        mortalidadMujeresMayor65_302 += mort.getMujeres65mayor();
                    }
                    if (mort.getEnfermedad().getId() == 605) {
                        mortalidadMujeres45a64_605 += mort.getMujeres45a64();
                        mortalidadMujeresMayor65_605 += mort.getMujeres65mayor();
                    }
                }
            }

            //Calculo de fracciones atribuibles
            double Cs = format.parse(allRequestParams.get("Cs" + region.getId())).doubleValue() / 100.0;
            double RRepoc = format.parse(allRequestParams.get("RRepoc")).doubleValue();
            double RRCerebroVascular = format.parse(allRequestParams.get("RRCerebrovascular")).doubleValue();
            double RRIsquemicas = format.parse(allRequestParams.get("RRIsquem")).doubleValue();
            double RRHipertensivas = format.parse(allRequestParams.get("RRHiper")).doubleValue();
            double RRCancer = format.parse(allRequestParams.get("RRcancer")).doubleValue();
            double RRIRA5menos = format.parse(allRequestParams.get("RRiraMayores")).doubleValue();
            double RRIRA15mas = format.parse(allRequestParams.get("RRiraMenores")).doubleValue();

//            System.err.println(Cs);
            double FARRepoc = ((Cs * RRepoc) + ((1 - Cs)) - 1) / ((Cs * RRepoc) + ((1 - Cs)));
            double FARRCerebroVascular = ((Cs * RRCerebroVascular) + ((1 - Cs)) - 1) / ((Cs * RRCerebroVascular) + ((1 - Cs)));
            double FARRIsquemicas = ((Cs * RRIsquemicas) + ((1 - Cs)) - 1) / ((Cs * RRIsquemicas) + ((1 - Cs)));
            double FARRHipertensivas = ((Cs * RRHipertensivas) + ((1 - Cs)) - 1) / ((Cs * RRHipertensivas) + ((1 - Cs)));
            double FARRCancer = ((Cs * RRCancer) + ((1 - Cs)) - 1) / ((Cs * RRCancer) + ((1 - Cs)));
            double FARRIRA5menos = ((Cs * RRIRA5menos) + ((1 - Cs)) - 1) / ((Cs * RRIRA5menos) + ((1 - Cs)));
            double FARRIRA15mas = ((Cs * RRIRA15mas) + ((1 - Cs)) - 1) / ((Cs * RRIRA15mas) + ((1 - Cs)));

//            System.out.printf("Region %s -> \t %.2f \t %.2f \t %.2f \t %.2f \t %.2f \t %.2f \t %.2f \n", region.getNombre(),
//                    FARRepoc, FARRCerebroVascular, FARRIsquemicas, FARRHipertensivas, FARRCancer, FARRIRA5menos, FARRIRA15mas);
            long enfermedadids[] = {109, 206, 302, 303, 307, 605};
            ConsolidadoCai cai = new ConsolidadoCai();
            cai = consolidadoCaiDAO.save(cai);
            List<MortalidadConsolidadoCai> list = new ArrayList<>();
            for (int i = 0; i < enfermedadids.length; i++) {
                MortalidadConsolidadoCai mortcai = new MortalidadConsolidadoCai();
                Enfermedad e = enfermedadDao.findOne(enfermedadids[i]);
                mortcai.setEnfermedad(e);
                if (enfermedadids[i] == 109) {
                    mortcai.setMorteMujeresMayores44(0.0);
                    mortcai.setMorteMujeresMenores5((double) mortalidadMenores5_109 * FARRIRA5menos);
                    mortcai.setMortAvad(mortcai.getMorteMujeresMenores5() * format.parse(allRequestParams.get("AVADIra1")).doubleValue());
                    mortcai.setMortCostos((mortcai.getMorteMujeresMenores5()) * format.parse(allRequestParams.get("VEV")).doubleValue() / 1000000000.0);
                } else if (enfermedadids[i] == 206) {
                    mortcai.setMorteMujeresMayores44((mortalidadMujeres45a64_206 + mortalidadMujeresMayor65_206) * FARRCancer);
                    mortcai.setMorteMujeresMenores5(0.0);
                    mortcai.setMortAvad(mortcai.getMorteMujeresMayores44() * format.parse(allRequestParams.get("AVADMortEpoc")).doubleValue());
                    mortcai.setMortCostos((mortcai.getMorteMujeresMayores44()) * format.parse(allRequestParams.get("VEV")).doubleValue() / 1000000000.0);
                } else if (enfermedadids[i] == 302) {
                    mortcai.setMorteMujeresMayores44((mortalidadMujeres45a64_302 + mortalidadMujeresMayor65_302) * FARRHipertensivas);
                    mortcai.setMorteMujeresMenores5(0.0);
                    mortcai.setMortAvad(mortcai.getMorteMujeresMayores44() * format.parse(allRequestParams.get("AVADMortEpoc")).doubleValue());
                    mortcai.setMortCostos((mortcai.getMorteMujeresMayores44()) * format.parse(allRequestParams.get("VEV")).doubleValue() / 1000000000.0);
                } else if (enfermedadids[i] == 303) {
                    mortcai.setMorteMujeresMayores44((mortalidadMujeres45a64_303 + mortalidadMujeresMayor65_303) * FARRIsquemicas);
                    mortcai.setMorteMujeresMenores5(0.0);
                    mortcai.setMortAvad(mortcai.getMorteMujeresMayores44() * format.parse(allRequestParams.get("AVADMortEpoc")).doubleValue());
                    mortcai.setMortCostos((mortcai.getMorteMujeresMayores44()) * format.parse(allRequestParams.get("VEV")).doubleValue() / 1000000000.0);
                } else if (enfermedadids[i] == 307) {
                    mortcai.setMorteMujeresMayores44((mortalidadMujeres45a64_307 + mortalidadMujeresMayor65_307) * FARRCerebroVascular);
                    mortcai.setMorteMujeresMenores5(0.0);
                    mortcai.setMortAvad(mortcai.getMorteMujeresMayores44() * format.parse(allRequestParams.get("AVADMortEpoc")).doubleValue());
                    mortcai.setMortCostos((mortcai.getMorteMujeresMayores44()) * format.parse(allRequestParams.get("VEV")).doubleValue() / 1000000000.0);
                } else if (enfermedadids[i] == 605) {
                    mortcai.setMorteMujeresMayores44((mortalidadMujeres45a64_605 + mortalidadMujeresMayor65_605) * FARRepoc);
                    mortcai.setMorteMujeresMenores5(0.0);
                    mortcai.setMortAvad(mortcai.getMorteMujeresMayores44() * format.parse(allRequestParams.get("AVADMortEpoc")).doubleValue());
                    mortcai.setMortCostos((mortcai.getMorteMujeresMayores44()) * format.parse(allRequestParams.get("VEV")).doubleValue() / 1000000000.0);
                }
                mortcai.setCai(cai);
                mortconsolidadoCaiDAO.save(mortcai);
            }

            cai.setMorbMenor5Inc((format.parse(allRequestParams.get("PrevIra" + region.getId())).doubleValue() * 52.0) / (format.parse(allRequestParams.get("perRec")).doubleValue() * 100.0));
            cai.setMorbMenor5(cai.getMorbMenor5Inc() * poblacionTotalMenor5);
            cai.setMorbMenor5Cai(cai.getMorbMenor5() * FARRIRA5menos);
            cai.setMorbMenor5AVADCai((cai.getMorbMenor5Cai() * format.parse(allRequestParams.get("AVADIra2")).doubleValue()) / 100000.0);
            cai.setMorbMenor5CostosCai((((format.parse(allRequestParams.get("Ira" + region.getId())).doubleValue() / 100.0) * format.parse(allRequestParams.get("CProm")).doubleValue())
                    + (format.parse(allRequestParams.get("CMedicinas")).doubleValue() * format.parse(allRequestParams.get("PorcentajeMed")).doubleValue() / 100.0)) * cai.getMorbMenor5Cai() / 1000000000.0);

            System.err.println(poblacionTotalMujeres45a64 + " " + poblacionTotalMujeresMayor65);
            cai.setMorbMayor15Inc((format.parse(allRequestParams.get("PrevIra" + region.getId())).doubleValue() * 52.0) * format.parse(allRequestParams.get("Rips")).doubleValue() * format.parse(allRequestParams.get("Rmed")).doubleValue() / (format.parse(allRequestParams.get("perRec")).doubleValue() * 100.0));
            cai.setMorbMayor15(cai.getMorbMayor15Inc() * (poblacionTotalMujeres45a64 + poblacionTotalMujeresMayor65));
            cai.setMorbMayor15Cai(cai.getMorbMayor15() * FARRIRA15mas);
            cai.setMorbMayor15AVADCai((cai.getMorbMayor15Cai() * format.parse(allRequestParams.get("AVADIraMayores30")).doubleValue()) / 100000.0);
            cai.setMorbMayor15CostosCai((((format.parse(allRequestParams.get("IRAPorcentaje")).doubleValue() / 100.0) * format.parse(allRequestParams.get("CProm")).doubleValue())
                    + (format.parse(allRequestParams.get("CMedicinas")).doubleValue() * format.parse(allRequestParams.get("PorcentajeMed")).doubleValue() / 100.0)) * cai.getMorbMayor15Cai() / 1000000000.0);

//            System.out.printf("Region %s -> \t %.2f \t %.2f \t %.2f \t %.2f \t %.2f \n", region.getNombre(), cai.getMorbMayor15Inc(), cai.getMorbMayor15(), cai.getMorbMayor15Cai(), cai.getMorbMayor15AVADCai(), cai.getMorbMayor15CostosCai());
            cai.setMorbMayor15EPOC((poblacionTotalMujeres45a64 + poblacionTotalMujeresMayor65) * format.parse(allRequestParams.get("IEpoc")).doubleValue() / 100000.0);
            cai.setMorbMayor15CaiEPOC(cai.getMorbMayor15EPOC() * FARRepoc);
            cai.setMorbMayor15AVADCaiEPOC(cai.getMorbMayor15CaiEPOC() * format.parse(allRequestParams.get("AVADMorbEpoc")).doubleValue());
            double costosSalud = format.parse(allRequestParams.get("CostosSalud")).doubleValue();
            double durEpoc = Integer.parseInt(allRequestParams.get("DurEpoc"));
            double pacientesEpoc = format.parse(allRequestParams.get("PacientesEpoc")).doubleValue();
            double costoHospitalizacion = format.parse(allRequestParams.get("Chosp")).doubleValue();
            cai.setMorbMayor15CostosCaiEPOC((presentValue(costosSalud / 100.0, durEpoc,
                    (((pacientesEpoc / 100.0) * format.parse(allRequestParams.get("DurPromHosp")).doubleValue()
                    * costoHospitalizacion) + (format.parse(allRequestParams.get("DiasPerdidos")).doubleValue()
                    * format.parse(allRequestParams.get("CTiempoPerd")).doubleValue()) + (format.parse(allRequestParams.get("PromVisitas")).doubleValue()
                    * format.parse(allRequestParams.get("CPrimerNivel")).doubleValue()) + ((format.parse(allRequestParams.get("PocentajeEPOCUrg")).doubleValue() / 100.0)
                    * format.parse(allRequestParams.get("CUrg")).doubleValue())))
                    * cai.getMorbMayor15CaiEPOC()) / 1000000000.0f);

            System.out.printf("Region %s ->\t %.2f \t %.2f \t %.2f \t %.2f \n", region.getNombre(), cai.getMorbMayor15EPOC(), cai.getMorbMayor15CaiEPOC(), cai.getMorbMayor15AVADCaiEPOC(), cai.getMorbMayor15CostosCaiEPOC());

            consolidadoCaiDAO.save(cai);
            
            sumaporcentajecm += format.parse(allRequestParams.get("Ira" + region.getId())).doubleValue()/100.0;
        }
        
        beanInsumos.setPorcentajeIRAmed(format.parse(allRequestParams.get("PorcentajeMed")).doubleValue() / 100.0);
        beanInsumos.setAVADmorbMenor5IRA(format.parse(allRequestParams.get("AVADIra2")).doubleValue());
        beanInsumos.setPrecioConsultaPrimerNivel(format.parse(allRequestParams.get("CPrimerNivel")).doubleValue());
        beanInsumos.setPrecioConsultaCAI(format.parse(allRequestParams.get("CProm")).doubleValue());
        beanInsumos.setPrecioMedicinasCAI(format.parse(allRequestParams.get("CMedicinas")).doubleValue());
        beanInsumos.setPrecioTiempoPerdido(format.parse(allRequestParams.get("CTiempoPerd")).doubleValue());
        beanInsumos.setPrecioUrgencias(format.parse(allRequestParams.get("CUrg")).doubleValue());
        beanInsumos.setPromedioPorcentajeIRACM(sumaporcentajecm/regionDao.count());

        return new ModelAndView("redirect:calcular");
    }

    @RequestMapping("/acces/setParamCau")
    public ModelAndView parametersCau(@RequestParam Map<String, String> allRequestParams) throws ParseException {
        mortconsolidadoCauDAO.deleteAll();
        consolidadoCauDAO.deleteAll();
        logger.info("parametersCAU");

        enfermedadDao.save(new Enfermedad(800l, "Cardiopulmonares"));

        for (Svca svca : svcaDao.findAll()) {
            double promedioPm10 = 0;
            long poblacionTotal = 0;
            long poblacionTotalMenor5 = 0;
            long poblacionTotal45a64 = 0;
            long poblacionTotalMayor65 = 0;
            long poblacionTotal45a64_206 = 0;
            long poblacionTotalMayor65_206 = 0;
            long poblacionTotal45a64_800 = 0;
            long poblacionTotalMayor65_800 = 0;
            long totalmuertes = 0;
            long totalmuertesMenor5 = 0;
            int count = 0;
            long poblacionDep = 0;
            System.out.println("SVCA " + svca.getNombre() + " ID " + svca.getId());
            for (Departamento dep : departamentoDao.findBySvca(svca)) {
                count++;
                System.out.println("          Dep " + dep.getNombre() + " ID " + dep.getId());
                for (Poblacion pob : poblacionDao.findByDepartamento(dep)) {
//                        System.out.println("                    pob " + pob.getGrupo_etareo().getNombre()+ " ID " + pob.getTotal());
                    if (pob.getGrupo_etareo().getId() == 0) {
                        poblacionTotal += pob.getTotal();
                        promedioPm10 += dep.getPromedioPM10() * pob.getTotal();
                    } else if (pob.getGrupo_etareo().getId() == 1) {
                        poblacionTotalMenor5 += pob.getTotal();
                    } else if (pob.getGrupo_etareo().getId() <= 13) {
                        if (pob.getGrupo_etareo().getId() >= 10) {
                            poblacionTotal45a64 += pob.getTotal();
                        }
                    } else if (pob.getGrupo_etareo().getId() >= 14) {
                        poblacionTotalMayor65 += pob.getTotal();
                    }
                }

                if (dep.getId() == 11001) {
                    dep = departamentoDao.findOne(11l);
                }
                for (Mortalidad mort : mortalidadDao.findByDepartamento(dep)) {
                    long id = mort.getEnfermedad().getId();
                    if (id == 206) {
                        poblacionTotal45a64_206 += mort.getHombres45a64() + mort.getMujeres45a64() + mort.getIndeterminado45a64();
                        poblacionTotalMayor65_206 += mort.getHombres65mayor() + mort.getMujeres65mayor() + mort.getIndeterminado65mayor();
                    }
                    if ((id >= 301 && id <= 309) || (id >= 605 && id <= 608)) {
                        poblacionTotal45a64_800 += mort.getHombres45a64() + mort.getMujeres45a64() + mort.getIndeterminado45a64();
                        poblacionTotalMayor65_800 += mort.getHombres65mayor() + mort.getMujeres65mayor() + mort.getIndeterminado65mayor();
                    }
                    totalmuertes += mort.getTotal();
                    totalmuertesMenor5 += mort.getHombres1a4() + mort.getMujeres1a4() + mort.getIndeterminado1a4()
                            + mort.getHombres1menor() + mort.getMujeres1menor() + mort.getIndeterminado1menor();
                }
                System.err.println("Muertes hasta aqui --> " + totalmuertes);
            }
            promedioPm10 /= poblacionTotal;
//            promedioPm10 = ((double)Math.round(promedioPm10*100)/100.0f);
            

            System.err.printf("Svca %s -------->\t\t %d %d \n", svca.getNombre(), totalmuertes, totalmuertesMenor5);
            System.err.printf("Svca %s ->\t\t %d \t %d \t %d \t %d \n", svca.getNombre(), poblacionTotal45a64_206, poblacionTotalMayor65_206, poblacionTotal45a64_800, poblacionTotalMayor65_800);
            System.err.printf("Svca %s ->\t\t %f \t\t %d \t\t %d \t\t %d \t\t %d \n", svca.getNombre(), promedioPm10, poblacionTotal, poblacionTotalMenor5, poblacionTotal45a64, poblacionTotalMayor65);

            double pm25 = format.parse(allRequestParams.get("PM25Basal")).doubleValue();
            double pm10 = format.parse(allRequestParams.get("PM10Basal")).doubleValue();
            double drcancer = format.parse(allRequestParams.get("DRcancer")).doubleValue();
            double drcardiopulmonar = format.parse(allRequestParams.get("DRcardiopulmonar")).doubleValue();
            double drTodas = format.parse(allRequestParams.get("DRTodas")).doubleValue();
            double drTodasMenores = format.parse(allRequestParams.get("DRTodasMenores5")).doubleValue();
            double relacion = format.parse(allRequestParams.get("RelacionPM")).doubleValue();

//            System.out.println(allRequestParams.get("PM25Basal") + " " +  allRequestParams.get("DRcancer") + " " + allRequestParams.get("DRcardiopulmonar"));
            double RRMort_206 = Math.pow((((promedioPm10 * relacion) + 1.0f) / (pm25 + 1.0f)), drcancer);
            double RRMort_800 = Math.pow((((promedioPm10 * relacion) + 1.0f) / (pm25 + 1.0f)), drcardiopulmonar);
            double FAMort_206 = (RRMort_206 - 1.0f) / RRMort_206;
            double FAMort_800 = (RRMort_800 - 1.0f) / RRMort_800;

            double RRTodasEnfermedades = Math.exp(drTodas * (promedioPm10 - pm10));
            double FATodasEnfermedades = (RRTodasEnfermedades - 1.0f) / RRTodasEnfermedades;
            double RRTodasEnfermedadesMenores = Math.exp(drTodasMenores * (promedioPm10 - pm10));
            double FATodasEnfermedadesMenores = (RRTodasEnfermedadesMenores - 1.0f) / RRTodasEnfermedadesMenores;

//            System.err.println(" ------ " + RRTodasEnfermedades + " " + FATodasEnfermedades + " " + RRTodasEnfermedadesMenores + " " + FATodasEnfermedadesMenores);

            long enfermedadids[] = {206, 800};
            ConsolidadoCau cau = new ConsolidadoCau();
            cau = consolidadoCauDAO.save(cau);
            double suma = 0;
            for (int i = 0; i < enfermedadids.length; i++) {
                MortalidadConsolidadoCau mortcau = new MortalidadConsolidadoCau();
                Enfermedad e = enfermedadDao.findOne(enfermedadids[i]);
                mortcau.setEnfermedad(e);
                if (enfermedadids[i] == 206) {
                    mortcau.setMortMayores45(FAMort_206 * (poblacionTotal45a64_206 + poblacionTotalMayor65_206));
                } else if (enfermedadids[i] == 800) {
                    mortcau.setMortMayores45(FAMort_800 * (poblacionTotal45a64_800 + poblacionTotalMayor65_800));
                }
                suma += mortcau.getMortMayores45();
                mortcau.setMortAvadMayor45((mortcau.getMortMayores45() * format.parse(allRequestParams.get("AVAD")).doubleValue()) / 10000.0);

//                System.err.printf("Svca %s ->\t\t %d \t %.2f \t %d \t %d \t %d \n", svca.getNombre(), enfermedadids[i], promedioPm10, poblacionTotal, poblacionTotalMenor5, poblacionTotal45a64);
                mortcau.setCau(cau);
                mortconsolidadoCauDAO.save(mortcau);
            }

            cau.setMortTodas(FATodasEnfermedades * totalmuertes);
            cau.setMortTodasAVAD(cau.getMortTodas() * format.parse(allRequestParams.get("AVAD")).doubleValue() / 10000.0f);
            cau.setMortTodasMenores(FATodasEnfermedadesMenores * totalmuertesMenor5);
            cau.setMortTodasMenoresAVAD(cau.getMortTodasMenores() * format.parse(allRequestParams.get("AVAD")).doubleValue() / 10000.0f);

            cau.setCostosMortalidad(format.parse(allRequestParams.get("VEV")).doubleValue() * ((suma + cau.getMortTodas() + cau.getMortTodasMenores()) / 1000000000.0));
//            System.err.println("COSTOS MORTALIDAD " + cau.getCostosMortalidad());

            double DRbc = format.parse(allRequestParams.get("DRBronquitis")).doubleValue() / 100f;
            double incBC = format.parse(allRequestParams.get("IncBC")).doubleValue();
            double avadBC = format.parse(allRequestParams.get("AVADBC")).doubleValue();
            double delta = format.parse(allRequestParams.get("IncrementoAnualSalud")).doubleValue() / 100f;
            double durBC = format.parse(allRequestParams.get("DuracionBC")).doubleValue();
            double hospBC = format.parse(allRequestParams.get("HospBC")).doubleValue() / 100f;
            double durHosp = format.parse(allRequestParams.get("DuracionHosp")).doubleValue();
            double Chosp = format.parse(allRequestParams.get("Chosp")).doubleValue();
            double diasPerdidos = format.parse(allRequestParams.get("DiasPerdidosBC")).doubleValue();
            double costoTiempoPerdido = format.parse(allRequestParams.get("CTiempoPerd")).doubleValue();
            double visitasBC = format.parse(allRequestParams.get("VisitasBC")).doubleValue();
            double costoConsulta = format.parse(allRequestParams.get("CConsulta")).doubleValue();
            double porcentajeUrgBC = format.parse(allRequestParams.get("UrgenciasBC")).doubleValue() / 100f;
            double costoUrgencias = format.parse(allRequestParams.get("CUrg")).doubleValue();
            double DRMorbDAR = format.parse(allRequestParams.get("DRDAR")).doubleValue();
            double AVADDAR = format.parse(allRequestParams.get("AVADDAR")).doubleValue();
            double durDAR = format.parse(allRequestParams.get("DiasActRestringida")).doubleValue();
            double DRAdmisiones = format.parse(allRequestParams.get("DRAdmisiones")).doubleValue();
            double AVADAdmisiones = format.parse(allRequestParams.get("AVADAdmisiones")).doubleValue();
            double ERDiasPerdidos = format.parse(allRequestParams.get("ERDiasPerdidos")).doubleValue();
            double ERHosp = format.parse(allRequestParams.get("ERHosp")).doubleValue();
            double DRUrgencias = format.parse(allRequestParams.get("DRUrgencias")).doubleValue();
            double AVADUrgencias = format.parse(allRequestParams.get("AVADUrgencias")).doubleValue();
            double DiasPerdUrgencias = format.parse(allRequestParams.get("DiasPerdUrgencias")).doubleValue();
            double DRMorbEVRI = format.parse(allRequestParams.get("DREVRI")).doubleValue();
            double AVADEVRI = format.parse(allRequestParams.get("AVADEVRI")).doubleValue();
            double VisitasEVRI = format.parse(allRequestParams.get("VisitasEVRI")).doubleValue();
            double CuidadoEVRI = format.parse(allRequestParams.get("CuidadoEVRI")).doubleValue();
            double DRSintomasResp = format.parse(allRequestParams.get("DRSintomasResp")).doubleValue();
            double AVADSintomasResp = format.parse(allRequestParams.get("AVADSintomasResp")).doubleValue();
            double CTapabocas = format.parse(allRequestParams.get("CTapabocas")).doubleValue();

            //Fraccion atribuible Bronquitis cronica
            cau.setRRbc(Math.exp((promedioPm10 - pm10) * (DRbc)));
            cau.setFAbc((cau.getRRbc() - 1.0f) / cau.getRRbc());

//            System.err.println(DRbc + " ***** " + pm10);
//            System.err.println("BRONQUITIS CRONICA ->  " + cau.getRRbc() + " " + cau.getFAbc());

            cau.setMorbBC(((cau.getFAbc() * incBC) / 100000.0f) * (poblacionTotal45a64 + poblacionTotalMayor65));
            cau.setMorbBCAVAD(cau.getMorbBC() * avadBC / 10000.0f);
            cau.setMorbBCCostos(presentValue(delta, durBC, ((hospBC * durHosp * Chosp) + (diasPerdidos * costoTiempoPerdido) + (visitasBC * costoConsulta) + (porcentajeUrgBC * costoUrgencias))) * cau.getMorbBC() / 1000000000.0f);

            cau.setDAR((DRMorbDAR / 100000f) * (poblacionTotal45a64 + poblacionTotalMayor65) * promedioPm10);
            cau.setDARAVAD(cau.getDAR() * AVADDAR / 10000f);
            cau.setDARCostos(durDAR * costoTiempoPerdido * cau.getDAR() / 1000000000.0f);

            cau.setMorbEVRI((DRMorbEVRI / 100000.0f) * poblacionTotalMenor5 * promedioPm10);
            cau.setMorbEVRIAVAD(cau.getMorbEVRI() * AVADEVRI / 10000.0f);
            cau.setMorbEVRICostos(((VisitasEVRI * costoConsulta) + (CuidadoEVRI * costoTiempoPerdido)) * cau.getMorbEVRI() / 1000000000.0);

            cau.setAdm((DRAdmisiones / 100000.0f) * poblacionTotal * promedioPm10);
            cau.setAdmAVAD(cau.getAdm() * AVADAdmisiones / 10000.0f);
            cau.setAdmCostos(((ERDiasPerdidos * costoTiempoPerdido) + (ERHosp * Chosp)) * cau.getAdm() / 1000000000f);

            cau.setVisitas((DRUrgencias / 100000f) * poblacionTotal * promedioPm10);
            cau.setVisitasAVAD(cau.getVisitas() * avadBC / 10000f);
            cau.setVisitasCostos(((DiasPerdUrgencias * costoTiempoPerdido) + costoUrgencias) * cau.getVisitas() / 1000000000f);

            cau.setSR((DRSintomasResp / 100000.0f) * promedioPm10 * (poblacionTotal45a64 + poblacionTotalMayor65));
            cau.setSRAVAD(cau.getSR() * AVADSintomasResp / 10000.0f);
            cau.setSRCostos((cau.getSR() * CTapabocas) / 1000000000.0f);

            consolidadoCauDAO.save(cau);
        }
        return new ModelAndView("redirect:calcular");
    }

    public static double presentValue(double annualInterestRate, double numberOfYears, double futureValue) {
        return futureValue * (((1 - Math.pow(1 + annualInterestRate, -numberOfYears)) / annualInterestRate));
    }

    public static double norminv(double p, double mu, double sigma) {
        if (p < 0 || p > 1) {
            throw new RuntimeException("The Probabilidad debe ser mayor a 0 y menor que 1");
        }
        if (sigma < 0) {
            throw new RuntimeException("La Desviación Estandar debe ser positiva");
        }
        if (p == 0) {
            return Double.NEGATIVE_INFINITY;
        }
        if (p == 1) {
            return Double.POSITIVE_INFINITY;
        }
        if (sigma == 0) {
            return mu;
        }
        double q, r, val;

        q = p - 0.5;

        /* 0.075 <= p <= 0.925 */
        if (Math.abs(q) <= .425) {
            r = .180625 - q * q;
            val
                    = q * (((((((r * 2509.0809287301226727
                    + 33430.575583588128105) * r + 67265.770927008700853) * r
                    + 45921.953931549871457) * r + 13731.693765509461125) * r
                    + 1971.5909503065514427) * r + 133.14166789178437745) * r
                    + 3.387132872796366608)
                    / (((((((r * 5226.495278852854561
                    + 28729.085735721942674) * r + 39307.89580009271061) * r
                    + 21213.794301586595867) * r + 5394.1960214247511077) * r
                    + 687.1870074920579083) * r + 42.313330701600911252) * r + 1);
        } else {
            if (q > 0) {
                r = 1 - p;
            } else {
                r = p;
            }

            r = Math.sqrt(-Math.log(r));

            if (r <= 5) {

                r += -1.6;
                val = (((((((r * 7.7454501427834140764e-4
                        + .0227238449892691845833) * r + .24178072517745061177)
                        * r + 1.27045825245236838258) * r
                        + 3.64784832476320460504) * r + 5.7694972214606914055)
                        * r + 4.6303378461565452959) * r
                        + 1.42343711074968357734)
                        / (((((((r
                        * 1.05075007164441684324e-9 + 5.475938084995344946e-4)
                        * r + .0151986665636164571966) * r
                        + .14810397642748007459) * r + .68976733498510000455)
                        * r + 1.6763848301838038494) * r
                        + 2.05319162663775882187) * r + 1);
            } else { /* very close to  0 or 1 */

                r += -5;
                val = (((((((r * 2.01033439929228813265e-7
                        + 2.71155556874348757815e-5) * r
                        + .0012426609473880784386) * r + .026532189526576123093)
                        * r + .29656057182850489123) * r
                        + 1.7848265399172913358) * r + 5.4637849111641143699)
                        * r + 6.6579046435011037772)
                        / (((((((r
                        * 2.04426310338993978564e-15 + 1.4215117583164458887e-7)
                        * r + 1.8463183175100546818e-5) * r
                        + 7.868691311456132591e-4) * r + .0148753612908506148525)
                        * r + .13692988092273580531) * r
                        + .59983220655588793769) * r + 1);
            }

            if (q < 0.0) {
                val = -val;
            }
        }
        return mu + sigma * val;
    }
    
    public static double CNDF(double x) {
        NormalDistribution d = new NormalDistribution(0.0, 1.0);
        return d.cumulativeProbability(x);
//        int neg = (x < 0d) ? 1 : 0;
//        if (neg == 1) {
//            x *= -1d;
//        }
//
//        double k = (1d / (1d + 0.2316419 * x));
//        double y = ((((1.330274429 * k - 1.821255978) * k + 1.781477937)
//                * k - 0.356563782) * k + 0.319381530) * k;
//        y = 1.0 - 0.398942280401 * Math.exp(-0.5 * x * x) * y;
//
//        return (1d - neg) * y + neg * (1d - y);
    }
    
    

    @RequestMapping("/acces/getConsolidadoAsh")
    public List<ConsolidadoAsh> getConsolidadoAsh() {
        return (List<ConsolidadoAsh>) consolidadoAshDAO.findAll();
    }

    @RequestMapping("/acces/getConsolidadoCai")
    public List<ConsolidadoCai> getConsolidadoCai() {
        return (List<ConsolidadoCai>) consolidadoCaiDAO.findAll();
    }

    @RequestMapping("/acces/getConsolidadoCau")
    public List<ConsolidadoCau> getConsolidadoCau() {
        return (List<ConsolidadoCau>) consolidadoCauDAO.findAll();
    }

    @RequestMapping("/acces/hasConsolidadoAsh")
    public Boolean hasConsolidadoAsh() {
        return consolidadoAshDAO.count() > 0;
    }

    @RequestMapping("/acces/getMortConsolidadoCai")
    public List<MortalidadConsolidadoCai> getConsolidadoMortCai() {
        return (List<MortalidadConsolidadoCai>) mortconsolidadoCaiDAO.findAll();
    }
    
    @RequestMapping("/acces/getMortDesnutricion")
    public List<MortalidadDesnutricion> getMortDesnutricion() {
        return (List<MortalidadDesnutricion>) mortdesnutricionDAO.findAll();
    }
    
    @RequestMapping("/acces/getMorbDesnutricion")
    public List<MorbilidadDesnutricion> getMorbDesnutricion() {
        return (List<MorbilidadDesnutricion>) morbdesnutricionDAO.findAll();
    }
    

    @RequestMapping("/acces/getSVs")
    public List<Svca> getSVs() {
        return (List<Svca>) svcaDao.findAll();
    }

    @RequestMapping("/acces/hasConsolidadoCai")
    public Boolean hasConsolidadoCai() {
        return consolidadoCaiDAO.count() > 0;
    }

    @RequestMapping("/acces/hasConsolidadoCau")
    public Boolean hasConsolidadoCau() {
        return consolidadoCauDAO.count() > 0;
    }

    @RequestMapping("/acces/hasConsolidadoGeneral")
    public Boolean hasConsolidadoGeneral() {
        return (consolidadoAshDAO.count() > 0 && consolidadoCaiDAO.count() > 0 && consolidadoCauDAO.count() > 0);
    }

    @RequestMapping("/acces/getMortConsolidadoCau")
    public List<MortalidadConsolidadoCau> getConsolidadoMortCau() {
        return (List<MortalidadConsolidadoCau>) mortconsolidadoCauDAO.findAll();
    }

    @RequestMapping(value = "/acces/downloadAsh1")
    public ModelAndView getAsh1(HttpServletRequest request, HttpServletResponse response) throws SQLException {
        Map<String, Object> model = new HashMap<String, Object>();
        //Sheet Name
        model.put("sheetname", "MortalidadAsh");
        //Headers List
        List<String> headers = new ArrayList<String>();
        headers.add("Region");
        headers.add("Mort");
        headers.add("AVAD");
        headers.add("Costos (Miles de millones de pesos)");
        model.put("headers", headers);
        //Results Table (List<Object[]>)
        List<List<String>> results = new ArrayList<List<String>>();
        long regionid = 1;
        for (ConsolidadoAsh consolidado : consolidadoAshDAO.findAll()) {
            Region r = regionDao.findOne(regionid);
            List<String> l = new ArrayList<String>();
            l.add(r.getNombre());
            l.add(String.format("%.2f", consolidado.getMortNum()));
            l.add(String.format("%.2f", consolidado.getMortAVAD()));
            l.add(String.format("%.2f", consolidado.getMortCostos()));
            results.add(l);
            regionid++;
        }
        model.put("results", results);
        response.setContentType("application/ms-excel");
        response.setHeader("Content-disposition", "attachment; filename=MortalidadAsh.xls");
        return new ModelAndView(new MyExcelView(), model);
    }

    @RequestMapping(value = "/acces/downloadAsh2")
    public ModelAndView getAsh2(HttpServletRequest request, HttpServletResponse response) throws SQLException {
        Map<String, Object> model = new HashMap<String, Object>();
        //Sheet Name
        model.put("sheetname", "MorbilidadAsh1");
        //Headers List
        List<String> headers = new ArrayList<String>();
        headers.add("Region");
        headers.add("Incidencia");
        headers.add("Morbilidad menores a 5 años");
        headers.add("Morbilidad menores a causa de ASH");
        headers.add("AVAD morbilidad de menores a causa de ASH");
        headers.add("Costos morbilidad de menores a causa de ASH (Miles de millones de pesos)");
        model.put("headers", headers);
        //Results Table (List<Object[]>)
        List<List<String>> results = new ArrayList<List<String>>();
        long regionid = 1;
        for (ConsolidadoAsh consolidado : consolidadoAshDAO.findAll()) {
            Region r = regionDao.findOne(regionid);
            List<String> l = new ArrayList<String>();
            l.add(r.getNombre());
            l.add(String.format("%.2f", consolidado.getMorbMenor5Inc()));
            l.add(String.format("%.2f", consolidado.getMorbMenor5()));
            l.add(String.format("%.2f", consolidado.getMorbMenor5Ash()));
            l.add(String.format("%.2f", consolidado.getMorbMenor5AVADAsh()));
            l.add(String.format("%.2f", consolidado.getMorbMenor5CostosAsh()));
            results.add(l);
            regionid++;
        }
        model.put("results", results);
        response.setContentType("application/ms-excel");
        response.setHeader("Content-disposition", "attachment; filename=MorbilidadAsh1.xls");
        return new ModelAndView(new MyExcelView(), model);
    }

    @RequestMapping(value = "/acces/downloadAsh3")
    public ModelAndView getAsh3(HttpServletRequest request, HttpServletResponse response) throws SQLException {
        Map<String, Object> model = new HashMap<String, Object>();
        //Sheet Name
        model.put("sheetname", "MorbilidadAsh2");
        //Headers List
        List<String> headers = new ArrayList<String>();
        headers.add("Region");
        headers.add("Incidencia");
        headers.add("Morbilidad mayores a 5 años");
        headers.add("Morbilidad mayores a causa de ASH");
        headers.add("AVAD morbilidad de mayores a causa de ASH");
        headers.add("Costos morbilidad de mayores a causa de ASH (Miles de millones de pesos)");
        model.put("headers", headers);
        //Results Table (List<Object[]>)
        List<List<String>> results = new ArrayList<List<String>>();
        long regionid = 1;
        for (ConsolidadoAsh consolidado : consolidadoAshDAO.findAll()) {
            Region r = regionDao.findOne(regionid);
            List<String> l = new ArrayList<String>();
            l.add(r.getNombre());
            l.add(String.format("%.2f", consolidado.getMorbMayor5Inc()));
            l.add(String.format("%.2f", consolidado.getMorbMayor5()));
            l.add(String.format("%.2f", consolidado.getMorbMayor5Ash()));
            l.add(String.format("%.2f", consolidado.getMorbMayor5AVADAsh()));
            l.add(String.format("%.2f", consolidado.getMorbMayor5CostosAsh()));
            results.add(l);
            regionid++;
        }
        model.put("results", results);
        response.setContentType("application/ms-excel");
        response.setHeader("Content-disposition", "attachment; filename=MorbilidadAsh2.xls");
        return new ModelAndView(new MyExcelView(), model);
    }
    
    @RequestMapping(value = "/acces/downloadAsh4")
    public ModelAndView getAsh4(HttpServletRequest request, HttpServletResponse response) throws SQLException {
        Map<String, Object> model = new HashMap<String, Object>();
        //Sheet Name
        model.put("sheetname", "MortalidadDesnutricion");
        //Headers List
        List<String> headers = new ArrayList<String>();
        headers.add("Causas");
        headers.add("Mortalidad por desnutrición en hombres");
        headers.add("Mortalidad por desnutrición en mujeres");
        headers.add("Costos mortalidad por desnutrición para hombres y mujeres (Miles de millones de pesos)");
        model.put("headers", headers);
        //Results Table (List<Object[]>)
        List<List<String>> results = new ArrayList<List<String>>();
        for (MortalidadDesnutricion consolidado : mortdesnutricionDAO.findAll()) {
            List<String> l = new ArrayList<String>();
            l.add(consolidado.getEnfermedad());
            l.add(String.format("%.6f", consolidado.getMuertesMenoresHombres()));
            l.add(String.format("%.6f", consolidado.getMuertesMenoresMujeres()));
            l.add(String.format("%.6f", consolidado.getMortalidadMenoresCostos()));
            results.add(l);
        }
        model.put("results", results);
        response.setContentType("application/ms-excel");
        response.setHeader("Content-disposition", "attachment; filename=MortalidadDesnutricion.xls");
        return new ModelAndView(new MyExcelView(), model);
    }
    
    @RequestMapping(value = "/acces/downloadAsh5")
    public ModelAndView getAsh5(HttpServletRequest request, HttpServletResponse response) throws SQLException {
        Map<String, Object> model = new HashMap<String, Object>();
        //Sheet Name
        model.put("sheetname", "MorbilidadDesnutricion");
        //Headers List
        List<String> headers = new ArrayList<String>();
        headers.add("Causas");
        headers.add("Morbilidad en hombres y mujeres menores a 5 años por desnutrición");
        headers.add("AVAD Morbilidad en hombres y mujeres menores a 5 años por desnutrición");
        headers.add("Costos Morbilidad en hombres y mujeres menores a 5 años por desnutrición (Miles de millones de pesos)");
        model.put("headers", headers);
        //Results Table (List<Object[]>)
        List<List<String>> results = new ArrayList<List<String>>();
        for (MorbilidadDesnutricion consolidado : morbdesnutricionDAO.findAll()) {
            List<String> l = new ArrayList<String>();
            l.add(consolidado.getEnfermedad());
            l.add(String.format("%.2f", consolidado.getMorbilidadMenores5Total()));
            l.add(String.format("%.2f", consolidado.getMorbilidadMenores5AVAD()));
            l.add(String.format("%.2f", consolidado.getMorbilidadMenores5Costos()));
            results.add(l);
        }
        model.put("results", results);
        response.setContentType("application/ms-excel");
        response.setHeader("Content-disposition", "attachment; filename=MorbilidadDesnutricion.xls");
        return new ModelAndView(new MyExcelView(), model);
    }

    @RequestMapping(value = "/acces/downloadCai1")
    public ModelAndView getCai1(HttpServletRequest request, HttpServletResponse response) throws SQLException {
        Map<String, Object> model = new HashMap<String, Object>();
        //Sheet Name
        model.put("sheetname", "MortalidadCai");
        //Headers List
        List<String> headers = new ArrayList<String>();
        headers.add("Region");
        headers.add("Enfermedad");
        headers.add("Mortalidad por CAI en Menores de 5 años");
        headers.add("Mortalidad por CAI en Mujeres mayores a 45 años");
        headers.add("AVAD para Mortalidad por CAI");
        headers.add("Costos en mortalidad por CAI (Miles de millones de pesos)");
        model.put("headers", headers);
        //Results Table (List<Object[]>)
        List<List<String>> results = new ArrayList<List<String>>();
        long regionid = 0;
        Region r = null;
        for (MortalidadConsolidadoCai consolidado : mortconsolidadoCaiDAO.findAll()) {
            if ((regionid) % 6 == 0) {
                r = regionDao.findOne(((regionid) / 6) + 1);
                System.err.println((((regionid) / 6) + 1));
                System.err.println(r.getNombre());
            }
            List<String> l = new ArrayList<String>();
            l.add(r.getNombre());
            l.add(String.format("%s", consolidado.getEnfermedad().getNombre()));
            l.add(String.format("%.2f", consolidado.getMorteMujeresMenores5()));
            l.add(String.format("%.2f", consolidado.getMorteMujeresMayores44()));
            l.add(String.format("%.2f", consolidado.getMortAvad()));
            l.add(String.format("%.2f", consolidado.getMortCostos()));
            results.add(l);
            regionid++;
        }
        model.put("results", results);
        response.setContentType("application/ms-excel");
        response.setHeader("Content-disposition", "attachment; filename=MortalidadCai.xls");
        return new ModelAndView(new MyExcelView(), model);
    }
    
    

    @RequestMapping(value = "/acces/downloadCai2")
    public ModelAndView getCai2(HttpServletRequest request, HttpServletResponse response) throws SQLException {
        Map<String, Object> model = new HashMap<String, Object>();
        //Sheet Name
        model.put("sheetname", "MorbilidadCai1");
        //Headers List
        List<String> headers = new ArrayList<String>();
        headers.add("Region");
        headers.add("Incidencia");
        headers.add("Morbilidad menores de 5 años");
        headers.add("Morbilidad menores de 5 años a causa de CAI");
        headers.add("AVAD para morbilidad de menores a causa de CAI");
        headers.add("Costos en morbilidad de menores a causa de CAI (Miles de millones de pesos)");
        model.put("headers", headers);
        //Results Table (List<Object[]>)
        List<List<String>> results = new ArrayList<List<String>>();
        long regionid = 1;
        for (ConsolidadoCai consolidado : consolidadoCaiDAO.findAll()) {
            Region r = regionDao.findOne(regionid);
            List<String> l = new ArrayList<String>();
            l.add(r.getNombre());
            l.add(String.format("%.2f", consolidado.getMorbMenor5Inc()));
            l.add(String.format("%.2f", consolidado.getMorbMenor5()));
            l.add(String.format("%.2f", consolidado.getMorbMenor5Cai()));
            l.add(String.format("%.2f", consolidado.getMorbMenor5AVADCai()));
            l.add(String.format("%.2f", consolidado.getMorbMenor5CostosCai()));
            results.add(l);
            regionid++;
        }
        model.put("results", results);
        response.setContentType("application/ms-excel");
        response.setHeader("Content-disposition", "attachment; filename=MorbilidadCai1.xls");
        return new ModelAndView(new MyExcelView(), model);
    }

    @RequestMapping(value = "/acces/downloadCai3")
    public ModelAndView getCai3(HttpServletRequest request, HttpServletResponse response) throws SQLException {
        Map<String, Object> model = new HashMap<String, Object>();
        //Sheet Name
        model.put("sheetname", "MorbilidadCai2");
        //Headers List
        List<String> headers = new ArrayList<String>();
        headers.add("Region");
        headers.add("Incidencia");
        headers.add("Morbilidad mujeres mayores de 45 años");
        headers.add("Morbilidad mujeres mayores de 45 años a causa de CAI");
        headers.add("AVAD para morbilidad de mujeres a causa de CAI");
        headers.add("Costos en morbilidad de mujeres a causa de CAI (Miles de millones de pesos)");
        model.put("headers", headers);
        //Results Table (List<Object[]>)
        List<List<String>> results = new ArrayList<List<String>>();
        long regionid = 1;
        for (ConsolidadoCai consolidado : consolidadoCaiDAO.findAll()) {
            Region r = regionDao.findOne(regionid);
            List<String> l = new ArrayList<String>();
            l.add(r.getNombre());
            l.add(String.format("%.2f", consolidado.getMorbMayor15Inc()));
            l.add(String.format("%.2f", consolidado.getMorbMayor15()));
            l.add(String.format("%.2f", consolidado.getMorbMayor15Cai()));
            l.add(String.format("%.2f", consolidado.getMorbMayor15AVADCai()));
            l.add(String.format("%.2f", consolidado.getMorbMayor15CostosCai()));
            results.add(l);
            regionid++;
        }
        model.put("results", results);
        response.setContentType("application/ms-excel");
        response.setHeader("Content-disposition", "attachment; filename=MorbilidadCai2.xls");
        return new ModelAndView(new MyExcelView(), model);
    }

    @RequestMapping(value = "/acces/downloadCai4")
    public ModelAndView getCai4(HttpServletRequest request, HttpServletResponse response) throws SQLException {
        Map<String, Object> model = new HashMap<String, Object>();
        //Sheet Name
        model.put("sheetname", "MorbilidadCai3");
        //Headers List
        List<String> headers = new ArrayList<String>();
        headers.add("Region");
        headers.add("Morbilidad mujeres mayores de 45 años por EPOC");
        headers.add("Morbilidad mujeres mayores de 45 años por EPOC a causa de CAI");
        headers.add("AVAD para morbilidad de mujeres por EPOC a causa de CAI");
        headers.add("Costos en morbilidad de mujeres por EPOC a causa de CAI (Miles de millones de pesos)");
        model.put("headers", headers);
        //Results Table (List<Object[]>)
        List<List<String>> results = new ArrayList<List<String>>();
        long regionid = 1;
        for (ConsolidadoCai consolidado : consolidadoCaiDAO.findAll()) {
            Region r = regionDao.findOne(regionid);
            List<String> l = new ArrayList<String>();
            l.add(r.getNombre());
            l.add(String.format("%.2f", consolidado.getMorbMayor15EPOC()));
            l.add(String.format("%.2f", consolidado.getMorbMayor15CaiEPOC()));
            l.add(String.format("%.2f", consolidado.getMorbMayor15AVADCaiEPOC()));
            l.add(String.format("%.2f", consolidado.getMorbMayor15CostosCaiEPOC()));
            results.add(l);
            regionid++;
        }
        model.put("results", results);
        response.setContentType("application/ms-excel");
        response.setHeader("Content-disposition", "attachment; filename=MorbilidadCai3.xls");
        return new ModelAndView(new MyExcelView(), model);
    }

    @RequestMapping(value = "/acces/downloadCau1")
    public ModelAndView getCau1(HttpServletRequest request, HttpServletResponse response) throws SQLException {
        Map<String, Object> model = new HashMap<String, Object>();
        //Sheet Name
        model.put("sheetname", "MortalidadCau");
        //Headers List
        List<String> headers = new ArrayList<String>();
        headers.add("Sistema de Vigilancia Calidad de Aire");
        headers.add("Enfermedad");
        headers.add("Mortalidad por CAU en Mayores de 45 años");
        headers.add("AVAD para Mortalidad por CAU en Mayores de 45 años");
        headers.add("Mortalidad por CAU en todas las causas todas las edades");
        headers.add("AVAD para Mortalidad por CAU en todas las causas todas las edades");
        headers.add("Mortalidad por CAU en todas las causas menores de 5 años");
        headers.add("AVAD para Mortalidad por CAU en todas las causas menores de 5 años");
        headers.add("Costos en mortalidad por CAU (Miles de millones de pesos)");
        model.put("headers", headers);
        //Results Table (List<Object[]>)
        List<List<String>> results = new ArrayList<List<String>>();
        long svcaid = 0;
        Svca r = null;
        for (MortalidadConsolidadoCau consolidado : mortconsolidadoCauDAO.findAll()) {
            List<String> l = new ArrayList<String>();
            if ((svcaid) % 2 == 0) {
                r = svcaDao.findOne(((svcaid) / 2) + 1);
                System.err.println((((svcaid) / 2) + 1));
                System.err.println(r.getNombre());
                l.add(r.getNombre());
                l.add(String.format("%s", consolidado.getEnfermedad().getNombre()));
                l.add(String.format("%.2f", consolidado.getMortMayores45()));
                l.add(String.format("%.2f", consolidado.getMortAvadMayor45()));
                l.add(String.format("%.2f", consolidado.getCau().getMortTodas()));
                l.add(String.format("%.2f", consolidado.getCau().getMortTodasAVAD()));
                l.add(String.format("%.2f", consolidado.getCau().getMortTodasMenores()));
                l.add(String.format("%.2f", consolidado.getCau().getMortTodasMenoresAVAD()));
                l.add(String.format("%.2f", consolidado.getCau().getCostosMortalidad()));
            }else{
                l.add(r.getNombre());
                l.add(String.format("%s", consolidado.getEnfermedad().getNombre()));
                l.add(String.format("%.2f", consolidado.getMortMayores45()));
                l.add(String.format("%.2f", consolidado.getMortAvadMayor45()));
                l.add("");
                l.add("");
                l.add("");
                l.add("");
                l.add("");
            }
            
            results.add(l);
            svcaid++;
        }
        model.put("results", results);
        response.setContentType("application/ms-excel");
        response.setHeader("Content-disposition", "attachment; filename=MortalidadCau.xls");
        return new ModelAndView(new MyExcelView(), model);
    }

    @RequestMapping(value = "/acces/downloadCau2")
    public ModelAndView getCau2(HttpServletRequest request, HttpServletResponse response) throws SQLException {
        Map<String, Object> model = new HashMap<String, Object>();
        //Sheet Name
        model.put("sheetname", "MorbilidadCau1");
        //Headers List
        List<String> headers = new ArrayList<String>();
        headers.add("Sistema de Vigilancia Calidad de Aire");
        headers.add("Morbilidad personas mayores de 45 años por Bronquitis cronica");
        headers.add("AVAD para morbilidad personas mayores de 45 años por Bronquitis cronica");
        headers.add("Costos en morbilidad personas mayores de 45 años por Bronquitis cronica (Miles de millones de pesos)");
        model.put("headers", headers);
        //Results Table (List<Object[]>)
        List<List<String>> results = new ArrayList<List<String>>();
        long svcaid = 1;
        for (ConsolidadoCau consolidado : consolidadoCauDAO.findAll()) {
            Svca r = svcaDao.findOne(svcaid);
            List<String> l = new ArrayList<String>();
            l.add(r.getNombre());
            l.add(String.format("%.2f", consolidado.getMorbBC()));
            l.add(String.format("%.2f", consolidado.getMorbBCAVAD()));
            l.add(String.format("%.2f", consolidado.getMorbBCCostos()));
            results.add(l);
            svcaid++;
        }
        model.put("results", results);
        response.setContentType("application/ms-excel");
        response.setHeader("Content-disposition", "attachment; filename=MorbilidadCau1.xls");
        return new ModelAndView(new MyExcelView(), model);
    }

    @RequestMapping(value = "/acces/downloadCau3")
    public ModelAndView getCau3(HttpServletRequest request, HttpServletResponse response) throws SQLException {
        Map<String, Object> model = new HashMap<String, Object>();
        //Sheet Name
        model.put("sheetname", "MorbilidadCau2");
        //Headers List
        List<String> headers = new ArrayList<String>();
        headers.add("Sistema de Vigilancia Calidad de Aire");
        headers.add("Morbilidad personas mayores de 45 años por sintomas respiratorios");
        headers.add("AVAD para morbilidad personas mayores de 45 años por sintomas respiratorios");
        headers.add("Costos en morbilidad personas mayores de 45 años por sintomas respiratorios (Miles de millones de pesos)");
        model.put("headers", headers);
        //Results Table (List<Object[]>)
        List<List<String>> results = new ArrayList<List<String>>();
        long svcaid = 1;
        for (ConsolidadoCau consolidado : consolidadoCauDAO.findAll()) {
            Svca r = svcaDao.findOne(svcaid);
            List<String> l = new ArrayList<String>();
            l.add(r.getNombre());
            l.add(String.format("%.2f", consolidado.getSR()));
            l.add(String.format("%.2f", consolidado.getSRAVAD()));
            l.add(String.format("%.2f", consolidado.getSRCostos()));
            results.add(l);
            svcaid++;
        }
        model.put("results", results);
        response.setContentType("application/ms-excel");
        response.setHeader("Content-disposition", "attachment; filename=MorbilidadCau2.xls");
        return new ModelAndView(new MyExcelView(), model);
    }

    @RequestMapping(value = "/acces/downloadCau4")
    public ModelAndView getCau4(HttpServletRequest request, HttpServletResponse response) throws SQLException {
        Map<String, Object> model = new HashMap<String, Object>();
        //Sheet Name
        model.put("sheetname", "MorbilidadCau3");
        //Headers List
        List<String> headers = new ArrayList<String>();
        headers.add("Sistema de Vigilancia Calidad de Aire");
        headers.add("Morbilidad personas mayores de 45 años por enfermedad en las vias respiratorias inferiores");
        headers.add("AVAD para morbilidad personas mayores de 45 años por enfermedad en las vias respiratorias inferiores");
        headers.add("Costos en morbilidad personas mayores de 45 años por enfermedad en las vias respiratorias inferiores (Miles de millones de pesos)");
        model.put("headers", headers);
        //Results Table (List<Object[]>)
        List<List<String>> results = new ArrayList<List<String>>();
        long svcaid = 1;
        for (ConsolidadoCau consolidado : consolidadoCauDAO.findAll()) {
            Svca r = svcaDao.findOne(svcaid);
            List<String> l = new ArrayList<String>();
            l.add(r.getNombre());
            l.add(String.format("%.2f", consolidado.getMorbEVRI()));
            l.add(String.format("%.2f", consolidado.getMorbEVRIAVAD()));
            l.add(String.format("%.2f", consolidado.getMorbEVRICostos()));
            results.add(l);
            svcaid++;
        }
        model.put("results", results);
        response.setContentType("application/ms-excel");
        response.setHeader("Content-disposition", "attachment; filename=MorbilidadCau3.xls");
        return new ModelAndView(new MyExcelView(), model);
    }
    
    @RequestMapping(value = "/acces/downloadCau5")
    public ModelAndView getCau5(HttpServletRequest request, HttpServletResponse response) throws SQLException {
        Map<String, Object> model = new HashMap<String, Object>();
        //Sheet Name
        model.put("sheetname", "MorbilidadCau4");
        //Headers List
        List<String> headers = new ArrayList<String>();
        headers.add("Sistema de Vigilancia Calidad de Aire");
        headers.add("Morbilidad por días de actividad restringida en adultos, hombres y mujeres, mayores a 44 años");
        headers.add("AVAD para morbilidad por días de actividad restringida en adultos, hombres y mujeres, mayores a 44 años");
        headers.add("Costos en morbilidad por días de actividad restringida en adultos, hombres y mujeres, mayores a 44 años (Miles de millones de pesos)");
        model.put("headers", headers);
        //Results Table (List<Object[]>)
        List<List<String>> results = new ArrayList<List<String>>();
        long svcaid = 1;
        for (ConsolidadoCau consolidado : consolidadoCauDAO.findAll()) {
            Svca r = svcaDao.findOne(svcaid);
            List<String> l = new ArrayList<String>();
            l.add(r.getNombre());
            l.add(String.format("%.2f", consolidado.getDAR()));
            l.add(String.format("%.2f", consolidado.getDARAVAD()));
            l.add(String.format("%.2f", consolidado.getDARCostos()));
            results.add(l);
            svcaid++;
        }
        model.put("results", results);
        response.setContentType("application/ms-excel");
        response.setHeader("Content-disposition", "attachment; filename=MorbilidadCau4.xls");
        return new ModelAndView(new MyExcelView(), model);
    }
    
    @RequestMapping(value = "/acces/downloadCau6")
    public ModelAndView getCau6(HttpServletRequest request, HttpServletResponse response) throws SQLException {
        Map<String, Object> model = new HashMap<String, Object>();
        //Sheet Name
        model.put("sheetname", "MorbilidadCau5");
        //Headers List
        List<String> headers = new ArrayList<String>();
        headers.add("Sistema de Vigilancia Calidad de Aire");
        headers.add("Morbilidad admisiones hospitalarias por causas respiratorias - hombres y mujeres de todas las edades");
        headers.add("AVAD para morbilidad admisiones hospitalarias por causas respiratorias - hombres y mujeres de todas las edades");
        headers.add("Costos en morbilidad admisiones hospitalarias por causas respiratorias - hombres y mujeres de todas las edades (Miles de millones de pesos)");
        model.put("headers", headers);
        //Results Table (List<Object[]>)
        List<List<String>> results = new ArrayList<List<String>>();
        long svcaid = 1;
        for (ConsolidadoCau consolidado : consolidadoCauDAO.findAll()) {
            Svca r = svcaDao.findOne(svcaid);
            List<String> l = new ArrayList<String>();
            l.add(r.getNombre());
            l.add(String.format("%.2f", consolidado.getAdm()));
            l.add(String.format("%.2f", consolidado.getAdmAVAD()));
            l.add(String.format("%.2f", consolidado.getAdmCostos()));
            results.add(l);
            svcaid++;
        }
        model.put("results", results);
        response.setContentType("application/ms-excel");
        response.setHeader("Content-disposition", "attachment; filename=MorbilidadCau5.xls");
        return new ModelAndView(new MyExcelView(), model);
    }
    
    @RequestMapping(value = "/acces/downloadCau7")
    public ModelAndView getCau7(HttpServletRequest request, HttpServletResponse response) throws SQLException {
        Map<String, Object> model = new HashMap<String, Object>();
        //Sheet Name
        model.put("sheetname", "MorbilidadCau6");
        //Headers List
        List<String> headers = new ArrayList<String>();
        headers.add("Sistema de Vigilancia Calidad de Aire");
        headers.add("Morbilidad Visitas a urgencias - hombres y mujeres de todas las edades");
        headers.add("AVAD para morbilidad Visitas a urgencias - hombres y mujeres de todas las edades");
        headers.add("Costos en morbilidad Visitas a urgencias - hombres y mujeres de todas las edades (Miles de millones de pesos)");
        model.put("headers", headers);
        //Results Table (List<Object[]>)
        List<List<String>> results = new ArrayList<List<String>>();
        long svcaid = 1;
        for (ConsolidadoCau consolidado : consolidadoCauDAO.findAll()) {
            Svca r = svcaDao.findOne(svcaid);
            List<String> l = new ArrayList<String>();
            l.add(r.getNombre());
            l.add(String.format("%.2f", consolidado.getVisitas()));
            l.add(String.format("%.2f", consolidado.getVisitasAVAD()));
            l.add(String.format("%.2f", consolidado.getVisitasCostos()));
            results.add(l);
            svcaid++;
        }
        model.put("results", results);
        response.setContentType("application/ms-excel");
        response.setHeader("Content-disposition", "attachment; filename=MorbilidadCau6.xls");
        return new ModelAndView(new MyExcelView(), model);
    }
    
    @RequestMapping(value = "/acces/downloadTotalASH")
    public ModelAndView getTotalASH(HttpServletRequest request, HttpServletResponse response) throws SQLException {
        Map<String, Object> model = new HashMap<String, Object>();
        //Sheet Name
        model.put("sheetname", "ASH");
        //Headers List
        List<String> headers = new ArrayList<String>();
        headers.add("Costos - Mortalidad Menores por enfermedades intestinales");
        headers.add("Costos - Morbilidad Diarrea en menores de 5 años");
        headers.add("Costos - Morbilidad Diarrea en mayores de 5 años");
        headers.add("Costos - Mortalidad desnutrición");
        headers.add("Costos - Mortalidad Menores por enfermedades intestinales");
        headers.add("Costos - TOTAL ASH");
        
        model.put("headers", headers);
        //Results Table (List<Object[]>)
        List<List<String>> results = new ArrayList<List<String>>();
        double sumaEI = 0, morbMen = 0, morbMay = 0, mortDesnutricion = 0, morbDesnutricion = 0, total = 0;
        for (ConsolidadoAsh consolidado : consolidadoAshDAO.findAll()) {
            sumaEI += consolidado.getMortCostos();
            morbMen += consolidado.getMorbMenor5CostosAsh();
            morbMay += consolidado.getMorbMayor5CostosAsh();
        }
        for (MortalidadDesnutricion m : mortdesnutricionDAO.findAll()) {
            mortDesnutricion += m.getMortalidadMenoresCostos();
        }
        
        for (MorbilidadDesnutricion m : morbdesnutricionDAO.findAll()) {
            mortDesnutricion += m.getMorbilidadMenores5Costos();
        }
        
        List<String> l = new ArrayList<String>();
        l.add(String.format("%.2f", sumaEI));
        l.add(String.format("%.2f", morbMen));
        l.add(String.format("%.2f", morbMay));
        l.add(String.format("%.2f", mortDesnutricion));
        l.add(String.format("%.2f", morbDesnutricion));
        l.add(String.format("%.2f", sumaEI + morbMen + morbMay + mortDesnutricion + morbDesnutricion));
        results.add(l);
        
        model.put("results", results);
        response.setContentType("application/ms-excel");
        response.setHeader("Content-disposition", "attachment; filename=consolidadoASH.xls");
        return new ModelAndView(new MyExcelView(), model);
    }
    
    @RequestMapping(value = "/acces/downloadTotalCAI")
    public ModelAndView getTotalCAI(HttpServletRequest request, HttpServletResponse response) throws SQLException {
        Map<String, Object> model = new HashMap<String, Object>();
        //Sheet Name
        model.put("sheetname", "CAI");
        //Headers List
        List<String> headers = new ArrayList<String>();
        headers.add("Costos - Mortalidad Mujeres mayores a 44 años");
        headers.add("Costos - Morbilidad menores de 5 años por IRA");
        headers.add("Costos - Morbilidad mujeres mayores de 45 años por IRA");
        headers.add("Costos - Morbilidad mujeres mayores de 45 años por EPOC");
        headers.add("Costos - TOTAL CAI");
        
        model.put("headers", headers);
        //Results Table (List<Object[]>)
        List<List<String>> results = new ArrayList<List<String>>();
        double sumas[] = new double[4];
        for (MortalidadConsolidadoCai m : mortconsolidadoCaiDAO.findAll()) {
            sumas[0] += m.getMortCostos();
        }
        for (ConsolidadoCai consolidado : consolidadoCaiDAO.findAll()) {
            sumas[1] += consolidado.getMorbMenor5CostosCai();
            sumas[2] += consolidado.getMorbMayor15CostosCai();
            sumas[3] += consolidado.getMorbMayor15CostosCaiEPOC();
        }
        List<String> l = new ArrayList<String>();
        double total = 0 ;
        for (double s : sumas) {
            l.add(String.format("%.2f", s));
            total += s;
        }
        l.add(String.format("%.2f", total));
        results.add(l);
        
        model.put("results", results);
        response.setContentType("application/ms-excel");
        response.setHeader("Content-disposition", "attachment; filename=consolidadoCAI.xls");
        return new ModelAndView(new MyExcelView(), model);
    }
    
    @RequestMapping(value = "/acces/downloadTotalCAU")
    public ModelAndView getTotalCAU(HttpServletRequest request, HttpServletResponse response) throws SQLException {
        Map<String, Object> model = new HashMap<String, Object>();
        //Sheet Name
        model.put("sheetname", "CAU");
        //Headers List
        List<String> headers = new ArrayList<String>();
        headers.add("Costos - Muerte por exposición (corto y largo plazo)");
        headers.add("Costos - Morbilidad Bronquitis Crónica");
        headers.add("Costos - Morbilidad Sintomas Respiratorios");
        headers.add("Costos - Morbilidad Dias de actividad restringida");
        headers.add("Costos - Morbilidad Vias respiratorias inferiores");
        headers.add("Costos - Morbilidad Admisiones hosp. por causas respiratorias");
        headers.add("Costos - Morbilidad visitas a urgencias");
        headers.add("Costos - TOTAL CAU");
        
        model.put("headers", headers);
        //Results Table (List<Object[]>)
        List<List<String>> results = new ArrayList<List<String>>();
        double sumas[] = new double[7];
        for (ConsolidadoCau consolidado : consolidadoCauDAO.findAll()) {
            sumas[0] += consolidado.getCostosMortalidad();
            sumas[1] += consolidado.getMorbBCCostos();
            sumas[2] += consolidado.getSRCostos();
            sumas[3] += consolidado.getDARCostos();
            sumas[4] += consolidado.getMorbEVRICostos();
            sumas[5] += consolidado.getAdmCostos();
            sumas[6] += consolidado.getVisitasCostos();
        }
        List<String> l = new ArrayList<String>();
        double total = 0 ;
        for (double s : sumas) {
            l.add(String.format("%.2f", s));
            total += s;
        }
        l.add(String.format("%.2f", total));
        results.add(l);
        
        model.put("results", results);
        response.setContentType("application/ms-excel");
        response.setHeader("Content-disposition", "attachment; filename=consolidadoCAU.xls");
        return new ModelAndView(new MyExcelView(), model);
    }
    

    @RequestMapping("/acces/uploadpm10")
    public String uploadPM10(@RequestParam("file") MultipartFile uploadfile) throws Exception {
        StringBuilder message = new StringBuilder();
        BufferedReader br = new BufferedReader(new InputStreamReader(uploadfile.getInputStream()));
        
        for (Estacion e : estacionDao.findAll()) {
            e.setSumaPm10(0.0);
            e.setCount(0l);
            estacionDao.save(e);
        }
//        
//        for (Departamento dep : departamentoDao.findAll()) {
//            dep.setPromedioPM10(0);
//            departamentoDao.save(dep);
//        }
        
        Estacion estacion = null;
        String separator = ",";
        long count = 0;
        long linescount = 0;
        double sum = 0;
        while (br.ready()) {
            String s = br.readLine();
            linescount++;
            if (s.split(separator).length < 2)continue;
            if (s.contains("Fecha")) {
                if (count > 0) {
                    estacion.setSumaPm10(estacion.getSumaPm10() + sum);
                    estacion.setCount(estacion.getCount() + count);
//                    System.err.println("ESTACION -> " + estacion.getNombre() + " -  " + estacion.getSumaPm10() + " " + estacion.getCount());
                    estacionDao.save(estacion);
                    count = 0;
                    sum = 0;
                }
                if (s.split(separator)[1].split("_").length < 2)continue;
                logger.info(s.split(separator)[1].split("_")[1]);
                estacion = estacionDao.findByNombre(s.split(separator)[1].split("_")[1]);
                if (estacion == null && !s.split(separator)[1].split("_")[1].isEmpty()) {
                    System.err.println("NO SE ENCUENTRA LA ESTACION -> " + s.split(separator)[1].split("_")[1]);
                    message.append(" La estacion " + s.split(separator)[1].split("_")[1] + " fue ignorada debido a que no se encuentra en la base de datos <br/>");
                }
                count = 0;
                sum = 0;
            } else {
//                logger.info(s.split(";")[1].split("_")[1]);
                if (s.split(separator)[1].contains("NA") || estacion==null) {
                    continue;
                }
                sum += Double.parseDouble(s.split(separator)[1]);
                count++;
            }
        }
        
        System.err.println("CONTEO DE LINEAS = " + linescount);
        if (count > 0) {
            estacion.setSumaPm10(estacion.getSumaPm10() + sum);
            estacion.setCount(estacion.getCount() + count);
//            System.err.println("ESTACION -> " + estacion.getNombre() + " -  " + estacion.getSumaPm10() + " " + estacion.getCount());
            estacionDao.save(estacion);
            count = 0;
            sum = 0;
        }

        for (Departamento dep : departamentoDao.findAll()) {
            count = 0;
            sum = 0;
            double promedio = 0;
            List<Estacion> list = estacionDao.findByDepartamento(dep);
            for (Estacion est : list) {
                sum += est.getSumaPm10();
                count += est.getCount();
                System.err.println("ESTACION -> " + est.getNombre() + " -  " + est.getSumaPm10() + " " + est.getCount());
            }
            if (list.size() > 0) {
                if(count !=0){
                    dep.setPromedioPM10(sum / count);
                }else{
                    dep.setPromedioPM10(0.0);
                }
                System.err.println("DEPARTAMENTO -> " +dep.getNombre() + "  PM10  = " + dep.getPromedioPM10());
                departamentoDao.save(dep);
            }
        }
        return "{\"message\": \""+ message.toString() +"\"}";
    }

    @RequestMapping("/acces/uploadpm10xls")
    public String uploadPM10xls(@RequestParam("file") MultipartFile uploadfile) {
        HSSFWorkbook wb = null;
        try {
            wb = new HSSFWorkbook(uploadfile.getInputStream());
        } catch (IOException ex) {
            logger.debug(ex.getMessage());
        }
//        HSSFWorkbook wb = new HSSFWorkbook(uploadfile.getInputStream());
        Sheet sheet = wb.getSheetAt(0);
        for (int i = 3; i < sheet.getLastRowNum(); i++) {
            Row row = sheet.getRow(i);
            Departamento dep = departamentoDao.findOne((long) row.getCell(0).getNumericCellValue());
            dep.setPromedioPM10((double) row.getCell(1).getNumericCellValue());
            departamentoDao.save(dep);
        }
        return "{}";
    }

    @RequestMapping("/acces/purge")
    public String purge() {
        estacionDao.deleteAll();
        consolidadoAshDAO.deleteAll();
        mortconsolidadoCaiDAO.deleteAll();
        consolidadoCaiDAO.deleteAll();
        mortconsolidadoCauDAO.deleteAll();
        consolidadoCauDAO.deleteAll();
        poblacionDao.deleteAll();
        mortalidadDao.deleteAll();
        departamentoDao.deleteAll();
        enfermedadDao.deleteAll();
        svcaDao.deleteAll();
        regionDao.deleteAll();
        mortdesnutricionDAO.deleteAll();
        morbdesnutricionDAO.deleteAll();
        return "Base de datos eliminadas exitosamente";
    }

    @RequestMapping("/acces/consolidate")
    public String consolidate() throws IOException {
        List cmdAndArgs = Arrays.asList("cmd", "/c", "fusionar.bat");
        ProcessBuilder pb = new ProcessBuilder(cmdAndArgs);
        pb.directory(new File("C:/FT/ACCes/insumosCSV"));
        pb.redirectOutput(new File("C:/FT/ACCes/insumosCSV/out.txt"));
        pb.start();
        return "Archivo consolidado exitosamente";
    }

}
