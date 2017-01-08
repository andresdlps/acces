package co.fuziontek.web;

import co.fuziontek.model.ConsolidadoAsh;
import co.fuziontek.model.ConsolidadoCai;
import co.fuziontek.model.ConsolidadoCau;
import co.fuziontek.model.Departamento;
import co.fuziontek.model.Enfermedad;
import co.fuziontek.model.Mortalidad;
import co.fuziontek.model.MortalidadConsolidadoCai;
import co.fuziontek.model.MortalidadConsolidadoCau;
import co.fuziontek.model.Poblacion;
import co.fuziontek.model.Region;
import co.fuziontek.model.Svca;
import co.fuziontek.model.dao.ConsolidadoAshDAO;
import co.fuziontek.model.dao.ConsolidadoCaiDAO;
import co.fuziontek.model.dao.ConsolidadoCauDAO;
import co.fuziontek.model.dao.DepartamentoDAO;
import co.fuziontek.model.dao.EnfermedadDAO;
import co.fuziontek.model.dao.GrupoEtareoDAO;
import co.fuziontek.model.dao.MortalidadConsolidadoCaiDAO;
import co.fuziontek.model.dao.MortalidadConsolidadoCauDAO;
import co.fuziontek.model.dao.MortalidadDAO;
import co.fuziontek.model.dao.PoblacionDAO;
import co.fuziontek.model.dao.RegionDAO;
import co.fuziontek.model.dao.SvcaDAO;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
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
    private ConsolidadoAshDAO consolidadoAshDAO;

    @Autowired
    private ConsolidadoCaiDAO consolidadoCaiDAO;

    @Autowired
    private MortalidadConsolidadoCaiDAO mortconsolidadoCaiDAO;
    
    @Autowired
    private ConsolidadoCauDAO consolidadoCauDAO;

    @Autowired
    private MortalidadConsolidadoCauDAO mortconsolidadoCauDAO;
    
    NumberFormat format = NumberFormat.getInstance(Locale.FRANCE);

    @RequestMapping("/acces/setParamAsh")
    public ModelAndView parametersAsh(@RequestParam Map<String, String> allRequestParams) throws ParseException {
//        System.err.println("Set PARAMETERS INVOCADO!!!!  " + allRequestParams.size());
        consolidadoAshDAO.deleteAll();
        for (Region region : regionDao.findAll()) {
            long poblacionTotal = 0;
            long poblacionTotalHombres = 0;
            long poblacionTotalMujeres = 0;
            long poblacionTotalMenor5 = 0;
            long poblacionTotal5a64 = 0;
            long poblacionTotal15a64 = 0;
            long poblacionTotalMayor65 = 0;

            long mortalidadMenores5 = 0;
//            System.out.println("Region " + region.getNombre() + " ID " + region.getId());
            for (Departamento dep : departamentoDao.findByRegion(region)) {
//                System.out.println("          Dep " + dep.getNombre() + " ID " + dep.getId());
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
                        if (pob.getGrupo_etareo().getId() >= 4) {
                            poblacionTotal15a64 += pob.getTotal();
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
            
//            System.out.println("Region " + region.getNombre() + " -> " + poblacionTotal + "  " + poblacionTotalHombres + "  " + poblacionTotalMujeres + "  " + poblacionTotalMenor5 + "  " + poblacionTotal5a64 + "  " + poblacionTotal15a64 + "  " + poblacionTotalMayor65 + "  " + mortalidadMenores5);

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
                System.out.println("AcuKey =" + acukey);
                System.out.println("AlcKey =" + alckey);
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
//            System.out.println("Region " + region.getNombre() + " -> \t" + sum + "\t" + fa);

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

            System.err.println((poblacionTotal5a64 + poblacionTotalMayor65));
            System.err.println(ash.getMorbMayor5Inc());
            System.err.println(ash.getMorbMayor5Inc()*(poblacionTotal5a64 + poblacionTotalMayor65));
            System.out.printf("Region %s -> \t %.2f \t %.2f \t %.2f \t %.2f \t %.2f \n", region.getNombre(), ash.getMorbMayor5Inc(), ash.getMorbMayor5(), ash.getMorbMayor5Ash(), ash.getMorbMayor5AVADAsh(), ash.getMorbMayor5CostosAsh());

            consolidadoAshDAO.save(ash);
        }

        return new ModelAndView("redirect:calcular");
    }

    @RequestMapping("/acces/setParamCai")
    public ModelAndView parametersCai(@RequestParam Map<String, String> allRequestParams) throws ParseException {
        mortconsolidadoCaiDAO.deleteAll();
        consolidadoCaiDAO.deleteAll();
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

            System.err.println(Cs);

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
                    mortcai.setMorteMujeresMayores44((double) mortalidadMenores5_109 * FARRIRA5menos);
                    mortcai.setMorteMujeresMenores5((double) mortalidadMenores5_109 * FARRIRA5menos);
                    mortcai.setMortAvad(mortcai.getMorteMujeresMayores44() * format.parse(allRequestParams.get("AVADIra1")).doubleValue());
                } else if (enfermedadids[i] == 206) {
                    mortcai.setMorteMujeresMayores44((mortalidadMujeres45a64_206 + mortalidadMujeresMayor65_206) * FARRCancer);
                    mortcai.setMorteMujeresMenores5((mortalidadMujeres45a64_206) * FARRCancer);
                    mortcai.setMortAvad(mortcai.getMorteMujeresMayores44() * format.parse(allRequestParams.get("AVADMortEpoc")).doubleValue());
                } else if (enfermedadids[i] == 302) {
                    mortcai.setMorteMujeresMayores44((mortalidadMujeres45a64_302 + mortalidadMujeresMayor65_302) * FARRHipertensivas);
                    mortcai.setMorteMujeresMenores5((mortalidadMujeres45a64_302) * FARRHipertensivas);
                    mortcai.setMortAvad(mortcai.getMorteMujeresMayores44() * format.parse(allRequestParams.get("AVADMortEpoc")).doubleValue());
                } else if (enfermedadids[i] == 303) {
                    mortcai.setMorteMujeresMayores44((mortalidadMujeres45a64_303 + mortalidadMujeresMayor65_303) * FARRIsquemicas);
                    mortcai.setMorteMujeresMenores5((mortalidadMujeres45a64_303) * FARRIsquemicas);
                    mortcai.setMortAvad(mortcai.getMorteMujeresMayores44() * format.parse(allRequestParams.get("AVADMortEpoc")).doubleValue());
                } else if (enfermedadids[i] == 307) {
                    mortcai.setMorteMujeresMayores44((mortalidadMujeres45a64_307 + mortalidadMujeresMayor65_307) * FARRCerebroVascular);
                    mortcai.setMorteMujeresMenores5((mortalidadMujeres45a64_307) * FARRCerebroVascular);
                    mortcai.setMortAvad(mortcai.getMorteMujeresMayores44() * format.parse(allRequestParams.get("AVADMortEpoc")).doubleValue());
                } else if (enfermedadids[i] == 605) {
                    mortcai.setMorteMujeresMayores44((mortalidadMujeres45a64_605 + mortalidadMujeresMayor65_605) * FARRepoc);
                    mortcai.setMorteMujeresMenores5((mortalidadMujeres45a64_605) * FARRepoc);
                    mortcai.setMortAvad(mortcai.getMorteMujeresMayores44() * format.parse(allRequestParams.get("AVADMortEpoc")).doubleValue());
                }
                mortcai.setMortCostos((mortcai.getMorteMujeresMayores44() + mortcai.getMorteMujeresMenores5()) * format.parse(allRequestParams.get("VEV")).doubleValue() / 1000000000.0);
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
            cai.setMorbMayor15Inc((format.parse(allRequestParams.get("PrevIra" + region.getId())).doubleValue() * 52.0)* format.parse(allRequestParams.get("Rips")).doubleValue() * format.parse(allRequestParams.get("Rmed")).doubleValue() / (format.parse(allRequestParams.get("perRec")).doubleValue() * 100.0) );
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
            cai.setMorbMayor15CostosCaiEPOC((presentValue(costosSalud/100.0 ,durEpoc, 
                    (((pacientesEpoc/100.0)*format.parse(allRequestParams.get("DurPromHosp")).doubleValue()
                            *costoHospitalizacion)+(format.parse(allRequestParams.get("DiasPerdidos")).doubleValue()
                            *format.parse(allRequestParams.get("CTiempoPerd")).doubleValue())+(format.parse(allRequestParams.get("PromVisitas")).doubleValue()
                            *format.parse(allRequestParams.get("CPrimerNivel")).doubleValue())+((format.parse(allRequestParams.get("PocentajeEPOCUrg")).doubleValue()/100.0)
                            *format.parse(allRequestParams.get("CUrg")).doubleValue())))
                            *cai.getMorbMayor15CaiEPOC())/1000000000.0f);
            
            System.out.printf("Region %s ->\t %.2f \t %.2f \t %.2f \t %.2f \n", region.getNombre(), cai.getMorbMayor15EPOC(), cai.getMorbMayor15CaiEPOC(), cai.getMorbMayor15AVADCaiEPOC(), cai.getMorbMayor15CostosCaiEPOC());

            consolidadoCaiDAO.save(cai);
        }

        return new ModelAndView("redirect:calcular");
    }
    
    @RequestMapping("/acces/setParamCau")
    public ModelAndView parametersCau(@RequestParam Map<String, String> allRequestParams) throws ParseException {
        mortconsolidadoCauDAO.deleteAll();
        consolidadoCauDAO.deleteAll();
        logger.info("parametersCAU");
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
                        promedioPm10 += dep.getPromedioPM10()*pob.getTotal();
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
                
                for (Mortalidad mort : mortalidadDao.findByDepartamento(dep)) {
                    long id = mort.getEnfermedad().getId();
                    if (id == 206) {
                        poblacionTotal45a64_206 += mort.getHombres45a64() + mort.getMujeres45a64() + mort.getIndeterminado45a64();
                        poblacionTotalMayor65_206 += mort.getHombres65mayor()+ mort.getMujeres65mayor() + mort.getIndeterminado65mayor();
                    }
                    if ((id >= 301 && id <= 309) || (id >= 605 && id <= 608)) {
                        poblacionTotal45a64_800 += mort.getHombres45a64() + mort.getMujeres45a64() + mort.getIndeterminado45a64();
                        poblacionTotalMayor65_800 += mort.getHombres65mayor()+ mort.getMujeres65mayor() + mort.getIndeterminado65mayor();
                    }
                }
            }
            promedioPm10 /= poblacionTotal;
//            System.err.printf("Svca %s ->\t\t %d \t %d \t %d \t %d \n", svca.getNombre(), poblacionTotal45a64_206, poblacionTotalMayor65_206, poblacionTotal45a64_800, poblacionTotalMayor65_800);
            System.err.printf("Svca %s ->\t\t %.2f \t\t %d \t\t %d \t\t %d \n", svca.getNombre(), promedioPm10, poblacionTotal, poblacionTotalMenor5, poblacionTotal45a64);
            
            double pm25 = format.parse(allRequestParams.get("PM25Basal")).doubleValue();
            double drcancer = format.parse(allRequestParams.get("DRcancer")).doubleValue();
            double drcardiopulmonar = format.parse(allRequestParams.get("DRcardiopulmonar")).doubleValue();
            double relacion = format.parse(allRequestParams.get("RelacionPM")).doubleValue();
            
//            System.out.println(allRequestParams.get("PM25Basal") + " " +  allRequestParams.get("DRcancer") + " " + allRequestParams.get("DRcardiopulmonar"));
            
            double RRMort_206 = Math.pow((((promedioPm10*relacion)+1.0f)/(pm25+1.0f)),drcancer);
            double RRMort_800 = Math.pow((((promedioPm10*relacion)+1.0f)/(pm25+1.0f)),drcardiopulmonar);
            double FAMort_206 = (RRMort_206-1.0f)/RRMort_206;
            double FAMort_800 = (RRMort_800-1.0f)/RRMort_800;
            
            System.err.println(" ------ " + RRMort_206 + " " + FAMort_206 + " " + RRMort_800 + " " + FAMort_800);
            
            long enfermedadids[] = {206, 800};
            ConsolidadoCau cau = new ConsolidadoCau();
            cau = consolidadoCauDAO.save(cau);
            double suma = 0;
            for (int i = 0; i < enfermedadids.length; i++) {
                MortalidadConsolidadoCau mortcau = new MortalidadConsolidadoCau();
                Enfermedad e = enfermedadDao.findOne(enfermedadids[i]);
                mortcau.setEnfermedad(e);
                if(enfermedadids[i] == 206){
                    mortcau.setMorteHMMenores5Y45a64(FAMort_206* poblacionTotal45a64_206);
                    mortcau.setMorteMayor65(FAMort_206*poblacionTotalMayor65_206);
                }else if(enfermedadids[i] == 800){
                    mortcau.setMorteHMMenores5Y45a64(FAMort_800* poblacionTotal45a64_800);
                    mortcau.setMorteMayor65(FAMort_800*poblacionTotalMayor65_800);
                }
                suma  += mortcau.getMorteHMMenores5Y45a64() + mortcau.getMorteMayor65();
                mortcau.setMortAvad45a64((mortcau.getMorteHMMenores5Y45a64()*format.parse(allRequestParams.get("AVAD")).doubleValue())/10000.0);
                mortcau.setMortAvadMayor65((mortcau.getMorteMayor65()*format.parse(allRequestParams.get("AVAD")).doubleValue())/10000.0);
                
//                System.err.printf("Svca %s ->\t\t %d \t %.2f \t %d \t %d \t %d \n", svca.getNombre(), enfermedadids[i], promedioPm10, poblacionTotal, poblacionTotalMenor5, poblacionTotal45a64);
                
                mortcau.setCau(cau);
                mortconsolidadoCauDAO.save(mortcau);
            }
            cau.setCostosMortalidad(format.parse(allRequestParams.get("VEV")).doubleValue()*((suma)/1000000000.0));
            System.err.println("COSTOS MORTALIDAD " + cau.getCostosMortalidad());
            
            
            double pm10 = format.parse(allRequestParams.get("PM10Basal")).doubleValue();
            double DRbc = format.parse(allRequestParams.get("DRBronquitis")).doubleValue()/100f;
            double incBC = format.parse(allRequestParams.get("IncBC")).doubleValue();
            double avadBC = format.parse(allRequestParams.get("AVADBC")).doubleValue();
            double delta = format.parse(allRequestParams.get("IncrementoAnualSalud")).doubleValue()/100f;
            double durBC = format.parse(allRequestParams.get("DuracionBC")).doubleValue();
            double hospBC = format.parse(allRequestParams.get("HospBC")).doubleValue()/100f;
            double durHosp = format.parse(allRequestParams.get("DuracionHosp")).doubleValue();
            double Chosp = format.parse(allRequestParams.get("Chosp")).doubleValue();
            double diasPerdidos = format.parse(allRequestParams.get("DiasPerdidosBC")).doubleValue();
            double costoTiempoPerdido = format.parse(allRequestParams.get("CTiempoPerd")).doubleValue();
            double visitasBC = format.parse(allRequestParams.get("VisitasBC")).doubleValue();
            double costoConsulta = format.parse(allRequestParams.get("CConsulta")).doubleValue();
            double porcentajeUrgBC = format.parse(allRequestParams.get("UrgenciasBC")).doubleValue();
            double costoUrgencias = format.parse(allRequestParams.get("CUrg")).doubleValue();
            double DRMorbDAR =  format.parse(allRequestParams.get("DRDAR")).doubleValue();
            double AVADDAR = format.parse(allRequestParams.get("AVADDAR")).doubleValue();
            double durDAR = format.parse(allRequestParams.get("DiasActRestringida")).doubleValue();
            double DRAdmisiones = format.parse(allRequestParams.get("DRAdmisiones")).doubleValue();
            double AVADAdmisiones = format.parse(allRequestParams.get("AVADAdmisiones")).doubleValue();
            double ERDiasPerdidos = format.parse(allRequestParams.get("ERDiasPerdidos")).doubleValue();
            double ERHosp = format.parse(allRequestParams.get("ERHosp")).doubleValue();
            double DRUrgencias = format.parse(allRequestParams.get("DRUrgencias")).doubleValue();
            double AVADUrgencias = format.parse(allRequestParams.get("AVADUrgencias")).doubleValue();
            double DiasPerdUrgencias = format.parse(allRequestParams.get("DiasPerdUrgencias")).doubleValue();
            
            //Fraccion atribuible Bronquitis cronica
            cau.setRRbc(Math.exp((promedioPm10 - pm10)*DRbc));
            cau.setFAbc((cau.getRRbc()-1.0f)/cau.getRRbc());
            
            cau.setMorbBC((cau.getFAbc()*incBC)/(100000.0f*(poblacionTotal45a64+poblacionTotalMayor65)));
            cau.setMorbBCAVAD(cau.getMorbBC()*avadBC/10000.0f);
            cau.setCostosMortalidad(presentValue(delta,durBC,((hospBC*durHosp*Chosp)+(diasPerdidos*costoTiempoPerdido)+(visitasBC*costoConsulta)+(porcentajeUrgBC*costoUrgencias)))*cau.getMorbBC()/1000000000.0f);
            
            cau.setDAR((DRMorbDAR/100000f)*(poblacionTotal45a64+poblacionTotalMayor65)*promedioPm10);
            cau.setDARAVAD(cau.getDAR()*AVADDAR/10000f);
            cau.setDARCostos(durDAR*costoTiempoPerdido*cau.getDAR()/1000000000.0f);
            
            cau.setAdm((DRAdmisiones/100000.0f)*poblacionTotal*promedioPm10);
            cau.setAdmAVAD(cau.getAdm()*AVADAdmisiones/10000.0f);
            cau.setAdmCostos(((ERDiasPerdidos*costoTiempoPerdido)+(ERHosp*Chosp))*cau.getAdm()/1000000000f);
            
            cau.setVisitas((DRUrgencias/100000f)*poblacionTotal*promedioPm10);
            cau.setVisitasAVAD(cau.getVisitas()*avadBC/10000f);
            cau.setVisitasCostos(((DiasPerdUrgencias*costoTiempoPerdido)+costoUrgencias)*cau.getVisitas()/1000000000f);
            
            consolidadoCauDAO.save(cau);
        }
        return new ModelAndView("redirect:calcular");
    }

    public static double presentValue(double annualInterestRate, double numberOfYears, double futureValue) {
        return futureValue * (((1 - Math.pow(1 + annualInterestRate, -numberOfYears)) / annualInterestRate));
    }

    @RequestMapping("/acces/getConsolidadoAsh")
    public List<ConsolidadoAsh> getConsolidadoAsh() {
        return (List<ConsolidadoAsh>) consolidadoAshDAO.findAll();
    }

    @RequestMapping("/acces/hasConsolidadoAsh")
    public Boolean hasConsolidadoAsh() {
        return consolidadoAshDAO.count() > 0;
    }

    @RequestMapping("/acces/getMortConsolidadoCai")
    public List<MortalidadConsolidadoCai> getConsolidadoCai() {
        return (List<MortalidadConsolidadoCai>) mortconsolidadoCaiDAO.findAll();
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
    public List<MortalidadConsolidadoCau> getConsolidadoCau() {
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
        headers.add("Costos");
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
        headers.add("Costos morbilidad de menores a causa de ASH");
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
        headers.add("Costos morbilidad de mayores a causa de ASH");
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
    
    @RequestMapping("/acces/uploadpm10")
    public String uploadPM10(@RequestParam("file") MultipartFile uploadfile) {
        HSSFWorkbook wb = null;
        try {
            wb = new HSSFWorkbook(uploadfile.getInputStream());
        } catch (IOException ex) {
            logger.debug(ex.getMessage());
        }
//        HSSFWorkbook wb = new HSSFWorkbook(uploadfile.getInputStream());
        Sheet sheet = wb.getSheetAt(0);
        for (int i = 3; i < sheet.getLastRowNum(); i++) {
            Row row  = sheet.getRow(i);
            Departamento dep = departamentoDao.findOne((long)row.getCell(0).getNumericCellValue());
            dep.setPromedioPM10((double)row.getCell(1).getNumericCellValue());
            departamentoDao.save(dep);
        }
        return "{}";
    }
    
    @RequestMapping("/acces/purge")
    public ModelAndView purge() {
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
        return new ModelAndView("redirect:ayuda");
    }
}
