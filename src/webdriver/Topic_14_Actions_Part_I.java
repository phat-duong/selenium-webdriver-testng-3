package webdriver;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.event.InputEvent;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.Charset;
import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.Point;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.Color;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class Topic_14_Actions_Part_I {
    WebDriver driver;
    Actions action;
    Alert alert;
    JavascriptExecutor jsExecutor;
    WebDriverWait explicitWait;
    String projectPath = System.getProperty("user.dir");
    String osName = System.getProperty("os.name");
    String dragDropHelperPath = projectPath + "\\DragDropHTML5\\drag_and_drop_helper.js";

    @BeforeClass
    public void beforeClass() {
        System.setProperty("webdriver.chrome.driver", projectPath + "\\browserDrivers\\chromedriver.exe");
        driver = new ChromeDriver();
        action = new Actions(driver);

        jsExecutor = (JavascriptExecutor) driver;
        driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
        driver.manage().window().maximize();

    }

    @Test
    public void TC_01_Hover() {
        driver.get("https://automationfc.github.io/jquery-tooltip/");
        //Hover chuột vào textbox
        action.moveToElement(driver.findElement(By.xpath("//input[@id='age']"))).perform();
        sleepInSecond(3);
        Assert.assertTrue(driver.findElement(By.xpath("//div[@class='ui-tooltip-content']")).isDisplayed());
        Assert.assertEquals(driver.findElement(By.xpath("//div[@class='ui-tooltip-content']")).getText(),"We ask for your age only for statistical purposes.");
    }

    @Test
    public void TC_02_Hover_1() {
        driver.get("https://www.fahasa.com/");
        action.moveToElement(driver.findElement(By.xpath("//div[contains(@class,'background-menu-homepage')]//span[text()='Sách Trong Nước']"))).perform();
        sleepInSecond(3);

        driver.findElement(By.xpath("//div[contains(@class,'background-menu-homepage')]//a[text()='Kỹ Năng Sống']")).click();
        Assert.assertTrue(driver.findElement(By.xpath("//strong[text()='Kỹ năng sống']")).isDisplayed());

    }

    @Test
    public void TC_03_Click_And_Hold_Block() {
        driver.get("https://automationfc.github.io/jquery-selectable/");
        List<WebElement> rectangleNumbers = driver.findElements(By.cssSelector("ol#selectable>li"));
        action.clickAndHold(rectangleNumbers.get(0)).moveToElement(rectangleNumbers.get(7)).release().perform();
        sleepInSecond(5);
        List<WebElement> rectangleNumbersSelected = driver.findElements(By.cssSelector("ol#selectable>li[class$='ui-selected']"));
        Assert.assertEquals(rectangleNumbersSelected.size(), 8);
    }

    @Test
    public void TC_04_Click_And_Hold_Random() {
        driver.get("https://automationfc.github.io/jquery-selectable/");
        List<WebElement> rectangleNumbers = driver.findElements(By.cssSelector("ol#selectable>li"));

        //Nhấn giữ phím Ctrl xuống
        action.keyDown(Keys.CONTROL).perform();

        action.click(rectangleNumbers.get(2))
                .click(rectangleNumbers.get(5))
                .click(rectangleNumbers.get(7))
                .click(rectangleNumbers.get(11))
                .perform();

        //Nhả phím Ctrl ra
        action.keyUp(Keys.CONTROL).perform();

        List<WebElement> rectangleNumbersSelected = driver.findElements(By.cssSelector("ol#selectable>li[class$='ui-selected']"));
        Assert.assertEquals(rectangleNumbersSelected.size(), 4);
    }

    @Test
    public void TC_05_Double_Click() {
        driver.get("https://automationfc.github.io/basic-form/index.html");
        jsExecutor.executeScript("arguments[0].scrollIntoView(true);", driver.findElement(By.xpath("//button[text()='Double click me']")));
        sleepInSecond(3);
        action.doubleClick(driver.findElement(By.xpath("//button[text()='Double click me']"))).perform();
        Assert.assertEquals(driver.findElement(By.xpath("//p[@id='demo']")).getText(), "Hello Automation Guys!");

    }

    @Test
    public void TC_06_Right_Click() {
        driver.get("http://swisnl.github.io/jQuery-contextMenu/demo.html");

        action.contextClick(driver.findElement(By.xpath("//span[text()='right click me']"))).perform();
        action.moveToElement(driver.findElement(By.cssSelector("li.context-menu-icon-paste"))).perform();

        Assert.assertTrue(driver.findElement(By.cssSelector("li.context-menu-icon-paste.context-menu-hover.context-menu-visible")).isDisplayed());

        action.click(driver.findElement(By.cssSelector("li.context-menu-icon-paste"))).perform();

        alert = driver.switchTo().alert();
//        alert = explicitWait.until(ExpectedConditions.alertIsPresent());
        Assert.assertEquals(alert.getText(), "clicked: paste");
        alert.accept();
        Assert.assertFalse(driver.findElement(By.cssSelector("li.context-menu-icon-paste")).isDisplayed());

    }

    @Test
    public void TC_07_Drag_Drop_HTML4() {
        driver.get("https://automationfc.github.io/kendo-drag-drop/");
        WebElement smallCircle = driver.findElement(By.id("draggable"));
        WebElement bigCircle = driver.findElement(By.id("droptarget"));

        action.dragAndDrop(smallCircle, bigCircle).perform();
        Assert.assertEquals(driver.findElement(By.id("droptarget")).getText(), "You did great!");

        String rgbaColor = bigCircle.getCssValue("background-color");
        String hexColor = Color.fromString(rgbaColor).asHex().toLowerCase();
        Assert.assertEquals(hexColor, "#03a9f4");

    }

    @Test
    public void TC_08_Drag_Drop_HTML5_Css() throws IOException {
        driver.get("https://automationfc.github.io/drag-drop-html5/");
        String columnACss = "#column-a";
        String columnBCss = "#column-b";

        String dragDropHelperContent = getContentFile(dragDropHelperPath);
        dragDropHelperContent = dragDropHelperContent + "$(\"" + columnACss + "\").simulateDragDrop({ dropTarget: \"" + columnBCss + "\"});";

        //drag and drop A to B
        jsExecutor.executeScript(dragDropHelperContent);
        Assert.assertEquals(driver.findElement(By.cssSelector("#column-a>header")).getText(), "B");
        Assert.assertEquals(driver.findElement(By.cssSelector("#column-b>header")).getText(), "A");

        //drag and drop B to A
        jsExecutor.executeScript(dragDropHelperContent);
        Assert.assertEquals(driver.findElement(By.cssSelector("#column-a>header")).getText(), "A");
        Assert.assertEquals(driver.findElement(By.cssSelector("#column-b>header")).getText(), "B");
    }

    @Test
    public void TC_09_Drag_Drop_HTML5_Xpath() throws AWTException{
        driver.get("https://automationfc.github.io/drag-drop-html5/");
        String columnAXpath = "//div[@id='column-a']";
        String columnBXpath = "//div[@id='column-b']";

        //drag and drop A to B
        dragAndDropHTML5ByXpath(columnAXpath, columnBXpath);
        Assert.assertEquals(driver.findElement(By.cssSelector("#column-a>header")).getText(), "B");
        Assert.assertEquals(driver.findElement(By.cssSelector("#column-b>header")).getText(), "A");

        //drag and drop B to A
        dragAndDropHTML5ByXpath(columnBXpath, columnAXpath);
        Assert.assertEquals(driver.findElement(By.cssSelector("#column-a>header")).getText(), "B");
        Assert.assertEquals(driver.findElement(By.cssSelector("#column-b>header")).getText(), "A");
    }


    public void dragAndDropHTML5ByXpath(String sourceLocator, String targetLocator) throws AWTException {

        WebElement source = driver.findElement(By.xpath(sourceLocator));
        WebElement target = driver.findElement(By.xpath(targetLocator));

        // Setup robot
        Robot robot = new Robot();
        robot.setAutoDelay(500);

        // Get size of elements
        Dimension sourceSize = source.getSize();
        Dimension targetSize = target.getSize();

        // Get center distance
        int xCentreSource = sourceSize.width / 2;
        int yCentreSource = sourceSize.height / 2;
        int xCentreTarget = targetSize.width / 2;
        int yCentreTarget = targetSize.height / 2;

        Point sourceLocation = source.getLocation();
        Point targetLocation = target.getLocation();
        System.out.println(sourceLocation.toString());
        System.out.println(targetLocation.toString());

        // Make Mouse coordinate center of element
        sourceLocation.x += 20 + xCentreSource;
        sourceLocation.y += 110 + yCentreSource;
        targetLocation.x += 20 + xCentreTarget;
        targetLocation.y += 110 + yCentreTarget;

        System.out.println(sourceLocation.toString());
        System.out.println(targetLocation.toString());

        // Move mouse to drag from location
        robot.mouseMove(sourceLocation.x, sourceLocation.y);

        // Click and drag
        robot.mousePress(InputEvent.BUTTON1_MASK);
        robot.mousePress(InputEvent.BUTTON1_MASK);
        robot.mouseMove(((sourceLocation.x - targetLocation.x) / 2) + targetLocation.x, ((sourceLocation.y - targetLocation.y) / 2) + targetLocation.y);

        // Move to final position
        robot.mouseMove(targetLocation.x, targetLocation.y);

        // Drop
        robot.mouseRelease(InputEvent.BUTTON1_MASK);
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

    public String getContentFile(String filePath) throws IOException {
        Charset cs = Charset.forName("UTF-8");
        FileInputStream stream = new FileInputStream(filePath);
        try {
            Reader reader = new BufferedReader(new InputStreamReader(stream, cs));
            StringBuilder builder = new StringBuilder();
            char[] buffer = new char[8192];
            int read;
            while ((read = reader.read(buffer, 0, buffer.length)) > 0) {
                builder.append(buffer, 0, read);
            }
            return builder.toString();
        } finally {
            stream.close();
        }
    }
}