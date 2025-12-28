package utilities;

import java.io.IOException;

import org.testng.annotations.DataProvider;

/**
 * DataProviders - supplies test data from Excel using ExcelUtility.
 * DataProvider name: "LoginData"
 *
 * Expects an Excel file at ".\\testData\\Opencart_LoginData.xlsx"
 * and a sheet named "Sheet1". Rows are read from 1..lastRow (row 0 is assumed header).
 */
public class DataProviders {

    @DataProvider(name = "LoginData")
    public String[][] getData() throws IOException {
        String path = ".\\testData\\OpenCartLoginData.xlsx"; // taking xl file from testData
        ExcelUtility xlutil = new ExcelUtility(path);        // creating an object for ExcelUtility

        int totalrows = xlutil.getRowCount("Sheet1");
        int totalcols = xlutil.getCellCount("Sheet1", 1);

        // created for two dimensional array which can store the data
        String logindata[][] = new String[totalrows][totalcols];

        // read the data from excel and store it in two dimensional array
        // Note: loop i from 1 to totalrows inclusive so we skip header row (row 0)
        for (int i = 1; i <= totalrows; i++) {
            for (int j = 0; j < totalcols; j++) {
                logindata[i - 1][j] = xlutil.getCellData("Sheet1", i, j);
            }
        }
        return logindata; // returning two dimensional array
    }

}
