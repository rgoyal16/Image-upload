

import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;

public class ReadData {
    XSSFWorkbook wb;
    XSSFSheet sh1;
    public ReadData(String excelpath)
    {
        try {
            File file = new File(excelpath);
            FileInputStream F1 = new FileInputStream(file);
// workbook
            wb = new XSSFWorkbook(F1);


        } catch (Exception e){
            System.out.println(e.getMessage());
        }
    }

    public String getData(int sheetNumber,int row,int column){
        sh1= wb.getSheetAt(0);
        String data = sh1.getRow(row).getCell(column).getStringCellValue();
        return data;

    }



}