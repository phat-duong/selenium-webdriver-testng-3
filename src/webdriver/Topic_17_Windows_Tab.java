package webdriver;

import java.util.Random;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class Topic_17_Windows_Tab {
    WebDriver driver;
    String projectPath = System.getProperty("user.dir");
    String osName = System.getProperty("os.name");

    @BeforeClass
    public void beforeClass() {
        System.setProperty("webdriver.chrome.driver", projectPath + "\\browserDrivers\\chromedriver.exe");
        driver = new ChromeDriver();
        System.out.println("Driver ID = " + driver.toString());
        driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
        driver.manage().window().maximize();

    }

    @Test
    public void TC_01_Only_Two_Windows_Or_Tab() {
        //Page A: Parent page
        driver.get("https://automationfc.github.io/basic-form/index.html");

        //Lấy ra ID của 1 tab/window mà driver đang đứng (active)
        String parentTabID = driver.getWindowHandle();
        System.out.println("Parent Tab ID = " + parentTabID);

        driver.findElement(By.xpath("//a[text()='GOOGLE']")).click();
        sleepInSecond(2);

        //Switch qua google tab thành công
        switchToTabByID(parentTabID);

        Assert.assertEquals(driver.getCurrentUrl(), "https://www.google.com.vn/");
        String googleTabID = driver.getWindowHandle();

        //Switch về parent tab
        switchToTabByID(googleTabID);
        Assert.assertEquals(driver.getCurrentUrl(), "https://automationfc.github.io/basic-form/index.html");
    }

    //Chỉ đúng cho duy nhất 2 tabs/windows
    public void switchToTabByID(String expectedID) {
        //Lấy ra ID của tất cả tab/window đang có
        Set<String> allTabIDs =  driver.getWindowHandles();
        //Dùng vòng lặp để duyệt qua từng ID một
        for (String id : allTabIDs) { //id là biến tạm để duyệt (so sánh)
            //ID nào mà khác với expected thì switch qua
            if(!id.equals(expectedID)) {
                driver.switchTo().window(id);
                break;
            }
        }
    }

    //Đúng cho tất cả trường hợp: 2 hoặc nhiều hơn 2 tabs/windows đều đúng
    public void switchToTabByTitle(String expectedTitle) {
        //Lấy ra ID của tất cả tab/window đang có
        Set<String> allTabIDs =  driver.getWindowHandles();
        //Dùng vòng lặp để duyệt qua từng ID một
        for (String id : allTabIDs) { //id là biến tạm để duyệt (so sánh)
            //Switch vào từng tab trước rồi kiểm tra sau
            driver.switchTo().window(id);
            //Lấy ra title của tab vừa switch vào
            String actualTitle = driver.getTitle();
            //nếu title bằng với title mong muốn
            if(actualTitle.equals(expectedTitle)) {
                //thì thoát khỏi vòng lặp
                break;
            }
        }
    }

    @Test
    public void TC_02_Greater_Than_One_Windows_Or_Tab() {
        //Page A: Parent page
        driver.get("https://automationfc.github.io/basic-form/index.html");

        driver.findElement(By.xpath("//a[text()='GOOGLE']")).click();
        sleepInSecond(2);

        //Switch qua google tab
        switchToTabByTitle("Google");
        sleepInSecond(2);
        Assert.assertEquals(driver.getCurrentUrl(), "https://www.google.com.vn/");

        //Switch về parent tab
        switchToTabByTitle("Selenium WebDriver");
        sleepInSecond(2);
        Assert.assertEquals(driver.getCurrentUrl(), "https://automationfc.github.io/basic-form/index.html");

        driver.findElement(By.xpath("//a[text()='LAZADA']")).click();
        sleepInSecond(2);

        //Switch qua lazada tab
        switchToTabByTitle("Shopping online - Buy online on Lazada.vn");
        sleepInSecond(2);
        Assert.assertEquals(driver.getCurrentUrl(), "https://www.lazada.vn/");
    }

    @Test
    public void TC_03_Greater_Than_One_Windows_Or_Tab() {
        driver.get("http://live.techpanda.org/");

        driver.findElement(By.xpath("//a[text()='Mobile']")).click();

        driver.findElement(By.xpath("//a[text()='Samsung Galaxy']/parent::h2[@class='product-name']/following-sibling::div[@class='actions']/ul/li/a[text()='Add to Compare']")).click();
        Assert.assertEquals(driver.findElement(By.xpath("//li[@class='success-msg']//span")).getText(), "The product Samsung Galaxy has been added to comparison list.");

        driver.findElement(By.xpath("//a[text()='Sony Xperia']/parent::h2[@class='product-name']/following-sibling::div[@class='actions']/ul/li/a[text()='Add to Compare']")).click();
        Assert.assertEquals(driver.findElement(By.xpath("//li[@class='success-msg']//span")).getText(), "The product Sony Xperia has been added to comparison list.");

        driver.findElement(By.xpath("//button[@title='Compare']")).click();

        switchToTabByTitle("Products Comparison List - Magento Commerce");
        Assert.assertEquals(driver.findElement(By.tagName("h1")).getText(), "COMPARE PRODUCTS");

        driver.findElement(By.xpath("//button[@title='Close Window']")).click();
        sleepInSecond(2);

        switchToTabByTitle("Mobile");
        sleepInSecond(2);
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