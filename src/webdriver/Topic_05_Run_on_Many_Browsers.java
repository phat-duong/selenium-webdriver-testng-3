package webdriver;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.annotations.Test;

public class Topic_05_Run_on_Many_Browsers {
    WebDriver driver;
    String projectPath = System.getProperty("user.dir");
    String osName = System.getProperty("os.name");


    @Test
    public void TC_01_Run_On_Firefox() {
        if(osName.startsWith("Windows")) {
            //Windows
            System.setProperty("webdriver.gecko.driver", projectPath + "\\browserDrivers\\geckodriver.exe");
        }else{
            //MAC
            System.setProperty("webdriver.gecko.driver", projectPath + "/browserDrivers/geckodriver");
        }

        driver = new FirefoxDriver();
        driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
        driver.manage().window().maximize();
        driver.get("https://www.facebook.com/");
        sleepInSecond(3);
        driver.quit();
    }

    @Test
    public void TC_02_Run_On_Chrome() {
        if(osName.startsWith("Windows")) {
            //Windows
            System.setProperty("webdriver.chrome.driver", projectPath + "\\browserDrivers\\chromedriver.exe");
        }else{
            //MAC
            System.setProperty("webdriver.chrome.driver", projectPath + "/browserDrivers/chromedriver");
        }

        driver = new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
        driver.manage().window().maximize();
        driver.get("https://www.facebook.com/");
        sleepInSecond(3);
        driver.quit();
    }

    @Test
    public void TC_03_Run_On_Edge() {

    }

    public void sleepInSecond(long second) {
        try {
            Thread.sleep(second * 1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
