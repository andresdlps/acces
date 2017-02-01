package co.fuziontek.web;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRichTextString;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.web.servlet.view.document.AbstractXlsView;

/**
 *
 * @author Andres
 */
public class MyExcelView extends AbstractXlsView
{
       
    @Override
    protected void buildExcelDocument(Map<String, Object> map, Workbook workbook, HttpServletRequest hsr, HttpServletResponse hsr1) throws Exception {
        //VARIABLES REQUIRED IN MODEL
        String sheetName = (String)map.get("sheetname");
        List<String> headers = (List<String>)map.get("headers");
        List<List<String>> results = (List<List<String>>)map.get("results");
        List<String> numericColumns = new ArrayList<String>();
        if (map.containsKey("numericcolumns"))
            numericColumns = (List<String>)map.get("numericcolumns");
        //BUILD DOC
        HSSFSheet sheet = (HSSFSheet) workbook.createSheet(sheetName);
        int currentRow = 0;
        int currentColumn = 0;
        //CREATE STYLE FOR HEADER
        HSSFCellStyle headerStyle = (HSSFCellStyle) workbook.createCellStyle();
        HSSFFont headerFont = (HSSFFont) workbook.createFont();
        headerFont.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
        headerStyle.setFont(headerFont); 
        //POPULATE HEADER COLUMNS
        HSSFRow headerRow = sheet.createRow(currentRow);
        for(String header:headers){
            HSSFRichTextString text = new HSSFRichTextString(header);
            HSSFCell cell = headerRow.createCell(currentColumn); 
            cell.setCellStyle(headerStyle);
            cell.setCellValue(text);            
            currentColumn++;
        }
        //POPULATE VALUE ROWS/COLUMNS
        currentRow++;//exclude header
        for(List<String> result: results){
            currentColumn = 0;
            HSSFRow row = sheet.createRow(currentRow);
            for(String value : result){//used to count number of columns
                HSSFCell cell = row.createCell(currentColumn);
                if (numericColumns.contains(headers.get(currentColumn))){
                    cell.setCellType(HSSFCell.CELL_TYPE_NUMERIC);
                    cell.setCellValue(Double.parseDouble(value));
                } else {
                    HSSFRichTextString text = new HSSFRichTextString(value.replace('.', ','));                
                    cell.setCellValue(text);                    
                }
                currentColumn++;
            }
            currentRow++;
        }
    }
}
