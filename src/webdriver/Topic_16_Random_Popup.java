package webdriver;

import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class Topic_16_Random_Popup {
    WebDriver driver;
    JavascriptExecutor jsExecutor;
    String projectPath = System.getProperty("user.dir");
    String osName = System.getProperty("os.name");

    @BeforeClass
    public void beforeClass() {
        System.setProperty("webdriver.chrome.driver", projectPath + "\\browserDrivers\\chromedriver.exe");
        driver = new ChromeDriver();
        jsExecutor = (JavascriptExecutor) driver;
        driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
        driver.manage().window().maximize();

    }

    @Test
    public void TC_01_Random_Popup_In_DOM() {
        //Step 1
        driver.get("https://vnk.edu.vn/");

        sleepInSecond(30);
        WebElement supportHomePopup = driver.findElement(By.cssSelector("img.tve_image"));

        //Step 2
        //Nếu popup hiển thị thì close đi hoặc tương tác với popup đó, close nó đi -> qua step  tiếp theo
        //Nếu popup không hiển thị thì qua step tiếp theo luôn
        if(supportHomePopup.isDisplayed()) {
            System.out.println("Case 1: Nếu popup hiển thị thì close đi");
            //Close đi
            By btnClose = By.cssSelector("div.thrv_icon");
            jsExecutor.executeScript("arguments[0].click();", driver.findElement(btnClose));
            sleepInSecond(3);
            //Verify popup không còn hiển thị
            Assert.assertFalse(supportHomePopup.isDisplayed());
        }else {
            System.out.println("Case 2: Nếu popup không hiển thị thì qua step tiếp theo luôn");
        }


//        driver.findElement(By.xpath("//a[@class='mb_sp mb_btn2 support-layer']")).click();
//        sleepInSecond(3);
//
//        WebElement homePopup = driver.findElement(By.xpath("//img[@id='support-img']"));
//        Assert.assertTrue(homePopup.isDisplayed());
//        jsExecutor.executeScript("arguments[0].click();", driver.findElement(By.xpath("//area[@id='down-url']")));

        //Step 3: Click vào trang liên hệ
        jsExecutor.executeScript("arguments[0].click();", driver.findElement(By.xpath("//a[@title='Liên hệ']")));
//        driver.findElement(By.xpath("//a[@title='Liên hệ']")).click();

        //Step 4: Verify qua trang liên hệ thành công
        Assert.assertEquals(driver.findElement(By.xpath("//div[@class='title-content']/h1")).getText(), "Liên Hệ");
    }

    @Test
    public void TC_02_Random_Popup_Not_In_DOM() {
        driver.get("https://dehieu.vn/");
        sleepInSecond(5);
//        WebElement couponPopup1 = driver.findElement(By.cssSelector("div.popup-content"));
        List<WebElement> couponPopup = driver.findElements(By.cssSelector("div.popup-content"));

        //Nếu popup hiển thị thì close đi hoặc tương tác với popup đó
        if(couponPopup.size()>0) {
            System.out.println("Case 1: Nếu popup hiển thị thì close đi");
            //Close đi
            driver.findElement(By.cssSelector("button#close-popup")).click();
            sleepInSecond(3);

            //Verify popup không còn hiển thị
//            Assert.assertFalse(couponPopup1.isDisplayed()); //không dùng cách này vì popup k có trong DOM thì k findElement được
            // phải tìm lại lần nữa mới ra giá trị 0, nếu k tìm lại thì vẫn lưu giá trị 1 của lần tìm đầu tiên
            couponPopup = driver.findElements(By.cssSelector("div.popup-content"));
            Assert.assertEquals(couponPopup.size(),0);
        }else {
            System.out.println("Case 2: Nếu popup không hiển thị thì qua step tiếp theo luôn");
        }

        driver.findElement(By.xpath("//a[text()='Tất cả khóa học']")).click();
        sleepInSecond(3);

    }

    @Test
    public void TC_03_() {

    }

    @AfterClass
    public void afterClass() {
        driver.quit();
    }

    public void sleepInSecond(long second) {
        try {
            Thread.sleep(second * 1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public int getRandomNumber() {
        Random rand = new Random();
        // random 1 số có 5 chữ số từ 0 -> 99999
        return rand.nextInt(99999);
    }
}