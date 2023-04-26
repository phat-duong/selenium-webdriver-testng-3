package webdriver;

import java.util.Random;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class Topic_18_JavascriptExecutor {
    WebDriver driver;
    JavascriptExecutor jsExecutor;
    String projectPath = System.getProperty("user.dir");
    String osName = System.getProperty("os.name");

    @BeforeClass
    public void beforeClass() {
        System.setProperty("webdriver.chrome.driver", projectPath + "\\browserDrivers\\chromedriver.exe");
        driver = new ChromeDriver();

        //Khởi tạo (ép kiểu tường minh)
        jsExecutor = (JavascriptExecutor) driver;
        driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
        driver.manage().window().maximize();

    }

    @Test
    public void TC_01_() {
        //jsExecutor không có apply wait hoặc chờ element visible/displayed
        // MỞ URL ra
        openUrl("http://live.techpanda.org/");
        sleepInSecond(3);

        // get domain của trang
        System.out.println(getPageDomain());

    }

    @Test
    public void TC_02_() {
        navigateToUrlByJS("http://live.techpanda.org/");
        sleepInSecond(3);

        String pageGuruDomain = (String) executeForBrowser("return document.domain");
        Assert.assertEquals(pageGuruDomain, "live.techpanda.org");

        String pageGuruUrl = (String) executeForBrowser("return document.URL");
        Assert.assertEquals(pageGuruUrl, "http://live.techpanda.org/");

        hightlightElement("//a[text()='Mobile']");
        clickToElementByJS("//a[text()='Mobile']");
        sleepInSecond(3);

        hightlightElement("//a[text()='Samsung Galaxy']/parent::h2[@class='product-name']/following-sibling::div[@class='actions']/ul/li/a[text()='Add to Compare']");
        clickToElementByJS("//a[text()='Samsung Galaxy']/parent::h2[@class='product-name']/following-sibling::div[@class='actions']/ul/li/a[text()='Add to Compare']");
        sleepInSecond(3);

        Assert.assertTrue(areExpectedTextInInnerText("The product Samsung Galaxy has been added to comparison list."));

        hightlightElement("//a[text()='Customer Service']");
        clickToElementByJS("//a[text()='Customer Service']");
        sleepInSecond(3);

        String customerServiceTitle = (String) executeForBrowser("return document.title");
        Assert.assertEquals(customerServiceTitle, "Customer Service");

        hightlightElement("//input[@id='newsletter']");
        scrollToElementOnTop("//input[@id='newsletter']");
        sendkeyToElementByJS("//input[@id='newsletter']", "abc" + getRandomNumber() + "@gmail.com");

        hightlightElement("//button[@title='Subscribe']");
        clickToElementByJS("//button[@title='Subscribe']");
        sleepInSecond(3);

        String subscribeMessage = getInnerText();
        Assert.assertTrue(subscribeMessage.contains("Thank you for your subscription."));

        navigateToUrlByJS("http://demo.guru99.com/v4/");
        sleepInSecond(3);
        Assert.assertEquals(executeForBrowser("return document.domain;"), "demo.guru99.com");

    }

    @Test
    public void TC_03_() {
        driver.get("https://login.ubuntu.com/");
        sleepInSecond(10);

        WebElement emailTextbox = driver.findElement(By.xpath("//input[@id='id_email' and @class='textType']"));
        WebElement btnLogin = driver.findElement(By.xpath("//button[@data-qa-id='login_button']"));
        btnLogin.click();

        String message = getElementValidationMessage("//input[@id='id_email' and @class='textType']");
        Assert.assertEquals(message, "Please fill out this field.");
    }

    public void openUrl(String url) {
        jsExecutor.executeScript("window.location='" + url + "'");

    }

    public String getPageDomain() {
        return (String) jsExecutor.executeScript("return document.domain");
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

    public Object executeForBrowser(String javaScript) {
        return jsExecutor.executeScript(javaScript);
    }

    public String getInnerText() {
        return (String) jsExecutor.executeScript("return document.documentElement.innerText;");
    }

    public boolean areExpectedTextInInnerText(String textExpected) {
        String textActual = (String) jsExecutor.executeScript("return document.documentElement.innerText.match('" + textExpected + "')[0];");
        return textActual.equals(textExpected);
    }

    public void scrollToBottomPage() {
        jsExecutor.executeScript("window.scrollBy(0,document.body.scrollHeight)");
    }

    public void navigateToUrlByJS(String url) {
        jsExecutor.executeScript("window.location = '" + url + "'");
    }

    public void hightlightElement(String locator) {
        WebElement element = getElement(locator);
        String originalStyle = element.getAttribute("style");
        jsExecutor.executeScript("arguments[0].setAttribute('style', arguments[1])", element, "border: 2px solid red; border-style: dashed;");
        sleepInSecond(1);
        jsExecutor.executeScript("arguments[0].setAttribute('style', arguments[1])", element, originalStyle);
    }

    public void clickToElementByJS(String locator) {
        jsExecutor.executeScript("arguments[0].click();", getElement(locator));
    }

    public void scrollToElementOnTop(String locator) {
        jsExecutor.executeScript("arguments[0].scrollIntoView(true);", getElement(locator));
    }

    public void scrollToElementOnDown(String locator) {
        jsExecutor.executeScript("arguments[0].scrollIntoView(false);", getElement(locator));
    }

    public void sendkeyToElementByJS(String locator, String value) {
        jsExecutor.executeScript("arguments[0].setAttribute('value', '" + value + "')", getElement(locator));
    }

    public void removeAttributeInDOM(String locator, String attributeRemove) {
        jsExecutor.executeScript("arguments[0].removeAttribute('" + attributeRemove + "');", getElement(locator));
    }

    public String getElementValidationMessage(String locator) {
        return (String) jsExecutor.executeScript("return arguments[0].validationMessage;", getElement(locator));
    }

    public boolean isImageLoaded(String locator) {
        boolean status = (boolean) jsExecutor.executeScript("return arguments[0].complete && typeof arguments[0].naturalWidth != 'undefined' && arguments[0].naturalWidth > 0", getElement(locator));
        if (status) {
            return true;
        }
        return false;
    }

    public WebElement getElement(String locator) {
        return driver.findElement(By.xpath(locator));
    }
}