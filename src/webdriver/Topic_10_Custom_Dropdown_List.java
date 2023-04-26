package webdriver;

import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class Topic_10_Custom_Dropdown_List {
    WebDriver driver;
    String projectPath = System.getProperty("user.dir");
    String osName = System.getProperty("os.name");
    Select select;
    WebDriverWait explicitWait;
    JavascriptExecutor jsExecutor;

    @BeforeClass
    public void beforeClass() {
        System.setProperty("webdriver.gecko.driver", projectPath + "\\browserDrivers\\geckodriver.exe");
        driver = new FirefoxDriver();
        jsExecutor = (JavascriptExecutor) driver;
        explicitWait = new WebDriverWait(driver, 30);

        driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
        driver.manage().window().maximize();

    }

    @Test
    public void TC_01_JQuery() {
        driver.get("http://jqueryui.com/resources/demos/selectmenu/default.html");

        selectItemInCustomDropdown("//span[@id='number-button']", "//ul[@id='number-menu']/li/div", "7");
        Assert.assertEquals(driver.findElement(By.xpath("//span[@id='number-button']/span[@class='ui-selectmenu-text']")).getText(), "7");

        selectItemInCustomDropdown("//span[@id='number-button']", "//ul[@id='number-menu']/li/div", "10");
        Assert.assertEquals(driver.findElement(By.xpath("//span[@id='number-button']/span[@class='ui-selectmenu-text']")).getText(), "10");
    }

    @Test
    public void TC_02_ReactJs() {
        driver.get("https://react.semantic-ui.com/maximize/dropdown-example-selection/");
        selectItemInCustomDropdown("//i[@class='dropdown icon']", "//div[@class='item']/span[@class='text']", "Elliot Fu");
        Assert.assertEquals(driver.findElement(By.xpath("//div[@class='divider text']")).getText(), "Elliot Fu");

        selectItemInCustomDropdown("//i[@class='dropdown icon']", "//div[@class='item']/span[@class='text']", "Stevie Feliciano");
        Assert.assertEquals(driver.findElement(By.xpath("//div[@class='divider text']")).getText(), "Stevie Feliciano");
    }

    @Test
    public void TC_03_VueJS() {
        driver.get("https://mikerodham.github.io/vue-dropdowns/");
        selectItemInCustomDropdown("//li[@class='dropdown-toggle']", "//ul[@class='dropdown-menu']/li/a", "First Option");
        Assert.assertEquals(driver.findElement(By.xpath("//li[@class='dropdown-toggle']")).getText(), "First Option");
    }

    @Test
    public void TC_04_Angular() {
        driver.get("https://ej2.syncfusion.com/angular/demos/?_ga=2.262049992.437420821.1575083417-524628264.1575083417#/material/drop-down-list/data-binding");
        selectItemInCustomDropdown("//span[@aria-owns='games_options']", "//ul[@id='games_options']/li", "Badminton");
        // dùng javascript để lấy text ẩn
//		jsExecutor.executeScript("return document.querySelector('#games input').value");
        Assert.assertEquals(jsExecutor.executeScript("return document.querySelector('#games input').value"), "Badminton");
    }

    @Test
    public void TC_05_Angular_2() {
        driver.get("https://tiemchungcovid19.gov.vn/portal/register-person");
        selectItemInCustomDropdown("//ng-select[@bindvalue='provinceCode']//span[@class='ng-arrow-wrapper']", "//span[contains(@class,'ng-option-label')]", "Thành phố Hồ Chí Minh");
        Assert.assertEquals(jsExecutor.executeScript("return document.querySelector(\"ng-select[bindvalue='provinceCode']\").innerText.replace(\"×\",\"\").trim()"), "Thành phố Hồ Chí Minh");

        selectItemInCustomDropdown("//ng-select[@bindvalue='districtCode']//span[@class='ng-arrow-wrapper']", "//span[contains(@class,'ng-option-label')]", "Quận Tân Bình");
        Assert.assertEquals(jsExecutor.executeScript("return document.querySelector(\"ng-select[bindvalue='districtCode']\").innerText.replace(\"×\",\"\").trim()"), "Quận Tân Bình");

        selectItemInCustomDropdown("//ng-select[@bindvalue='wardCode']//span[@class='ng-arrow-wrapper']", "//span[contains(@class,'ng-option-label')]", "Phường 07");
        Assert.assertEquals(jsExecutor.executeScript("return document.querySelector(\"ng-select[bindvalue='wardCode']\").innerText.replace(\"×\",\"\").trim()"), "Phường 07");
    }

    @Test
    public void TC_06_Editable() {
        driver.get("https://react.semantic-ui.com/maximize/dropdown-example-search-selection/");
//		selectItemInCustomDropdown("//input[@class='search']", "//div[contains(@class,'item')]/span", "Belgium");
        selectItemInEditableDropdown("//input[@class='search']", "//div[contains(@class,'item')]/span", "Belgium");
        Assert.assertEquals(driver.findElement(By.xpath("//div[@class='divider text']")).getText(), "Belgium");
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

    public void selectItemInCustomDropdown(String parentXpath, String childXpath, String expectedItemText) {
        //Step 1: Click vào 1 element cho nó xổ hết ra các item
        driver.findElement(By.xpath(parentXpath)).click();
        sleepInSecond(2);

        //Step 2: Chờ cho các item load hết ra thành công
        //Lưu ý 1: Locator chứa hết tất cả các item
        //Lưu ý 2: Locator phải đến node cuối cùng chứa text
        explicitWait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.xpath(childXpath)));

        //Step 3: Tìm item cần chọn
        //Lấy hết tất cả các element (item) ra sau đó duyệt qua từng item
        List<WebElement> childItem = driver.findElements(By.xpath(childXpath));

        //Duyệt qua từng item getText của item ra
        for (WebElement temp : childItem) {
            //Nếu text = text mình mong muốn (item cần click vào)
            if(temp.getText().trim().equals(expectedItemText)) {
                // B1: Nếu item cần chọn nằm trong vùng nhìn thấy thì không cần scroll tới element tìm tiếp
                if(temp.isDisplayed()) {
                    temp.click();
                    sleepInSecond(1);
                }else {
                    // B2: Nếu item cần chọn nằm ở dưới thì scroll tới element
                    //Scroll to element
                    jsExecutor.executeScript("arguments[0].scrollIntoView(true);", temp);
                    sleepInSecond(1);
                    // Step 4: Click vào item đó
                    //Click by JavascriptExecutor
                    jsExecutor.executeScript("arguments[0].click()", temp);
                    sleepInSecond(1);
                }
                // Thoát khỏi vòng lặp không có kiểm tra element tiếp theo nữa
                break;
            }
        }
    }

    public void selectItemInEditableDropdown(String parentXpath, String childXpath, String expectedItemText) {
        driver.findElement(By.xpath(parentXpath)).sendKeys(expectedItemText);
        sleepInSecond(2);

        explicitWait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.xpath(childXpath)));

        List<WebElement> childItem = driver.findElements(By.xpath(childXpath));
        for (WebElement temp : childItem) {
            if(temp.getText().trim().equals(expectedItemText)) {
                temp.click();
                sleepInSecond(2);
                break;
            }
        }
    }
}
