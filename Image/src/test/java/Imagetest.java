import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.io.FileHandler;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.NoSuchElementException;
import java.util.concurrent.TimeoutException;

import static java.lang.Thread.sleep;


public class Imagetest {
    WebDriver driver;


        @Test(priority = 1)

    public void setup() throws TimeoutException {
        ReadData excel = new ReadData("/home/rupali/IdeaProjects/Image/Data/Untitled spreadsheet.xlsx");






        try{
            System.setProperty("webdriver.chrome.driver", "/home/rupali/IdeaProjects/Image/drivers/chromedriver");
            driver = new ChromeDriver();
        }
        catch (WebDriverException e)
        {
            System.out.println(e.getMessage());
        }

    // Explicit wait
        WebDriverWait wait=new WebDriverWait(driver, 20);
        // maximize browser window
        driver.manage().window().maximize();
        driver.get("https://the-internet.herokuapp.com/upload");


        WebElement add = driver.findElement(By.id("file-upload"));
        //add.click();
       // add.sendKeys("/home/rupali/IdeaProjects/Image/image/1.jpeg");
        add.sendKeys(excel.getData(0,0,0));
        WebElement logo = driver.findElement(By.id("drag-drop-upload"));

        WebElement upload = driver.findElement(By.id("file-submit"));
        upload.click();
        WebElement errorMessage = driver.findElement(By.cssSelector("h3"));
        String expectedErrorMessage = "File Uploaded!";
        String actualErrorMessage = errorMessage.getText();

        Assert.assertTrue(actualErrorMessage.contains(expectedErrorMessage),
                "Actual error message does not contain expected. \nActual: "
                        + actualErrorMessage + "\nExpected: "
                        + expectedErrorMessage);
        driver.quit();
    }


    // for firefox browser
    @Test(priority = 2)
    public void firefox() {
        ReadData excel = new ReadData("//home/rupali/IdeaProjects/Image/Data/Untitled spreadsheet.xlsx");

        System.setProperty("webdriver.gecko.driver", "/home/rupali/IdeaProjects/Image/drivers/geckodriver");
        driver = new FirefoxDriver();
        // maximize browser window
        driver.manage().window().maximize();
        driver.get("https://the-internet.herokuapp.com/upload");


        WebElement add = driver.findElement(By.id("file-upload"));
        //add.click();
        //add.sendKeys("/home/rupali/IdeaProjects/Image/image/1.jpeg");
        add.sendKeys(excel.getData(0,0,0));

        WebElement logo = driver.findElement(By.id("drag-drop-upload"));


        try{
            WebElement upload = driver.findElement(By.id("file-submit"));
            upload.click();
        }
        catch (ElementNotVisibleException e){
            System.out.println(e.getMessage());

        }

        try{
            WebElement errorMessage = driver.findElement(By.cssSelector("h3"));
            String expectedErrorMessage = "File Uploaded!";
            String actualErrorMessage = errorMessage.getText();

            Assert.assertTrue(actualErrorMessage.contains(expectedErrorMessage),
                    "Actual error message does not contain expected. \nActual: "
                            + actualErrorMessage + "\nExpected: "
                            + expectedErrorMessage);
        }
        catch (StaleElementReferenceException e)
        {
            System.out.println(e.getMessage());
        }
        driver.quit();
    }

    // for headless browser
    @Test(priority = 3)
    public void headless() {
        ReadData excel = new ReadData("/home/rupali/IdeaProjects/Image/Data/Untitled spreadsheet.xlsx");
        System.setProperty("webdriver.chrome.driver", "/home/rupali/IdeaProjects/Image/drivers/chromedriver");

        ChromeOptions options = new ChromeOptions();
        options.addArguments("--headless");
        driver = new ChromeDriver(options);
        // maximize browser window
        driver.manage().window().maximize();
        driver.get("https://the-internet.herokuapp.com/upload");

try{
    WebElement add = driver.findElement(By.id("file-upload"));
    add.sendKeys(excel.getData(0,0,0));
}
catch (NoSuchElementException e)
{
    System.out.println(e.getMessage());
}



        //add.click();

        WebElement logo = driver.findElement(By.id("drag-drop-upload"));


        WebElement upload = driver.findElement(By.id("file-submit"));
        upload.click();
        WebElement errorMessage = driver.findElement(By.cssSelector("h3"));
        String expectedErrorMessage = "File Uploaded!";
        String actualErrorMessage = errorMessage.getText();

        Assert.assertTrue(actualErrorMessage.contains(expectedErrorMessage),
                "Actual error message does not contain expected. \nActual: "
                        + actualErrorMessage + "\nExpected: "
                        + expectedErrorMessage);
        driver.quit();
    }

    @AfterMethod
    public void takeScreenShotOnFailure(ITestResult result) throws IOException {
        if(ITestResult.FAILURE==result.getStatus()){
            try{
                TakesScreenshot ts=(TakesScreenshot)driver;
                File source=ts.getScreenshotAs(OutputType.FILE);
                try{
                    FileHandler.copy(source, new File("screenshot"+result.getName()+".jpeg"));
                    System.out.println("Screenshot taken");
                }
                catch (Exception e)
                {
                    System.out.println("Exception while taking screenshot "+e.getMessage());
                }
            }
            catch (Exception e)
            {
                System.out.println("Exception while taking screenshot "+e.getMessage());
            }


        }
        }

}
