package tech;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import java.util.concurrent.TimeUnit;

public class Topic_09_Depend_Test {

    @Test()
    public void Product_01_Create() {
        Assert.assertTrue(false);
    }

    //Nếu thằng TC01 fail thì 3 thằng sau k chạy, sẽ skip
    @Test(dependsOnMethods = "Product_01_Create")
    public void Product_02_View() {

    }

    @Test(dependsOnMethods = {"Product_01_Create", "Product_02_View"})
    public void Product_03_Edit() {

    }

    @Test(dependsOnMethods = "Product_01_Create")
    public void Product_04_Delete() {

    }

}
