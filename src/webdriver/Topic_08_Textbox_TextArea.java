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

public class Topic_08_Textbox_TextArea {
    WebDriver driver;
    JavascriptExecutor jsExecutor;
    String projectPath = System.getProperty("user.dir");
    String osName = System.getProperty("os.name");
    String loginPageUrl, userID, userPassword, customerID;
    String customerName, gender, dateOfBirth, addressInput, addressOutput, city, state, pinNumber, phoneNumber, email,
            password;
    String editAddressInput, editAddressOutput, editCity, editState, editPinNumber, EditPhoneNumber, editEmail;

    @BeforeClass
    public void beforeClass() {
        System.setProperty("webdriver.chrome.driver", projectPath + "\\browserDrivers\\chromedriver.exe");
        driver = new ChromeDriver();
        // ép kiểu tường minh
        jsExecutor = (JavascriptExecutor) driver;

        driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
        driver.manage().window().maximize();
        driver.get("http://demo.guru99.com/v4");

        customerName = "abc";
        gender = "male";
        dateOfBirth = "1950-01-31";
        addressInput = "123 USA\n Hoa Ky";
        addressOutput = "123 USA Hoa Ky";
        city = "New York";
        state = "Cali";
        pinNumber = "123456";
        phoneNumber = "0123456789";
        email = "abc" + getRandomNumber() + "@gmail.net";
        password = "123456";

        editAddressInput = "123 USA\\n My";
        editAddressOutput = "123 USA My";
        editCity = "New York abc";
        editState = "California";
        editPinNumber = "234567";
        EditPhoneNumber = "1234567890";
        editEmail = "def" + getRandomNumber() + "@gmail.net";
    }

    @Test
    public void TC_01_Register() {
        loginPageUrl = driver.getCurrentUrl();
        driver.findElement(By.xpath("//a[text()='here']")).click();
        driver.findElement(By.name("emailid")).sendKeys(email);
        driver.findElement(By.name("btnLogin")).click();
        userID = driver.findElement(By.xpath("//td[text()='User ID :']/following-sibling::td")).getText();
        userPassword = driver.findElement(By.xpath("//td[text()='Password :']/following-sibling::td")).getText();

    }

    @Test
    public void TC_02_Login() {
        driver.get(loginPageUrl);
        driver.findElement(By.name("uid")).sendKeys(userID);
        driver.findElement(By.name("password")).sendKeys(userPassword);
        driver.findElement(By.name("btnLogin")).click();
        Assert.assertEquals(driver.findElement(By.cssSelector("marquee.heading3")).getText(),
                "Welcome To Manager's Page of Guru99 Bank");
        Assert.assertEquals(driver.findElement(By.cssSelector("tr.heading3>td")).getText(), "Manger Id : " + userID);
    }

    @Test
    public void TC_03_New_Customer() {
        driver.findElement(By.xpath("//a[text()='New Customer']")).click();
        driver.findElement(By.name("name")).sendKeys(customerName);

        WebElement dobTextbox = driver.findElement(By.name("dob"));
        jsExecutor.executeScript("arguments[0].removeAttribute('type')", dobTextbox);
        dobTextbox.sendKeys(dateOfBirth);

        driver.findElement(By.name("addr")).sendKeys(addressInput);
        driver.findElement(By.name("city")).sendKeys(city);
        driver.findElement(By.name("state")).sendKeys(state);
        driver.findElement(By.name("pinno")).sendKeys(pinNumber);
        driver.findElement(By.name("telephoneno")).sendKeys(phoneNumber);
        driver.findElement(By.name("emailid")).sendKeys(email);
        driver.findElement(By.name("password")).sendKeys(password);
        driver.findElement(By.name("sub")).click();

        Assert.assertEquals(driver.findElement(By.cssSelector("p.heading3")).getText(),
                "Customer Registered Successfully!!!");
        Assert.assertEquals(
                driver.findElement(By.xpath("//td[text()='Customer Name']/following-sibling::td")).getText(),
                customerName);
        Assert.assertEquals(driver.findElement(By.xpath("//td[text()='Gender']/following-sibling::td")).getText(),
                gender);
        Assert.assertEquals(driver.findElement(By.xpath("//td[text()='Birthdate']/following-sibling::td")).getText(),
                dateOfBirth);
        Assert.assertEquals(driver.findElement(By.xpath("//td[text()='Address']/following-sibling::td")).getText(),
                addressOutput);
        Assert.assertEquals(driver.findElement(By.xpath("//td[text()='City']/following-sibling::td")).getText(), city);
        Assert.assertEquals(driver.findElement(By.xpath("//td[text()='State']/following-sibling::td")).getText(),
                state);
        Assert.assertEquals(driver.findElement(By.xpath("//td[text()='Pin']/following-sibling::td")).getText(),
                pinNumber);
        Assert.assertEquals(driver.findElement(By.xpath("//td[text()='Mobile No.']/following-sibling::td")).getText(),
                phoneNumber);
        Assert.assertEquals(driver.findElement(By.xpath("//td[text()='Email']/following-sibling::td")).getText(),
                email);

    }

    @Test
    public void TC_04_Edit_Customer() {
        customerID = driver.findElement(By.xpath("//td[text()='Customer ID']/following-sibling::td")).getText();
        driver.findElement(By.xpath("//a[text()='Edit Customer']")).click();
        driver.findElement(By.name("cusid")).sendKeys(customerID);
        driver.findElement(By.name("AccSubmit")).click();

        Assert.assertEquals(driver.findElement(By.name("name")).getAttribute("value"), customerName);
        Assert.assertEquals(driver.findElement(By.name("gender")).getAttribute("value"), gender);
        Assert.assertEquals(driver.findElement(By.name("dob")).getAttribute("value"), dateOfBirth);

        // Edit
        driver.findElement(By.name("addr")).sendKeys(editAddressInput);
        driver.findElement(By.name("city")).sendKeys(editCity);
        driver.findElement(By.name("state")).sendKeys(editState);
        driver.findElement(By.name("pinno")).sendKeys(editPinNumber);
        driver.findElement(By.name("telephoneno")).sendKeys(EditPhoneNumber);
        driver.findElement(By.name("emailid")).sendKeys(editEmail);
        driver.findElement(By.name("sub")).click();
        // Verify
        Assert.assertEquals(driver.findElement(By.cssSelector("p.heading3")).getText(),
                "Customer details updated Successfully!!!");
        Assert.assertEquals(
                driver.findElement(By.xpath("//td[text()='Customer Name']/following-sibling::td")).getText(),
                customerName);
        Assert.assertEquals(driver.findElement(By.xpath("//td[text()='Gender']/following-sibling::td")).getText(),
                gender);
        Assert.assertEquals(driver.findElement(By.xpath("//td[text()='Birthdate']/following-sibling::td")).getText(),
                dateOfBirth);
        Assert.assertEquals(driver.findElement(By.xpath("//td[text()='Address']/following-sibling::td")).getText(),
                editAddressOutput);
        Assert.assertEquals(driver.findElement(By.xpath("//td[text()='City']/following-sibling::td")).getText(), editCity);
        Assert.assertEquals(driver.findElement(By.xpath("//td[text()='State']/following-sibling::td")).getText(),
                editState);
        Assert.assertEquals(driver.findElement(By.xpath("//td[text()='Pin']/following-sibling::td")).getText(),
                editPinNumber);
        Assert.assertEquals(driver.findElement(By.xpath("//td[text()='Mobile No.']/following-sibling::td")).getText(),
                EditPhoneNumber);
        Assert.assertEquals(driver.findElement(By.xpath("//td[text()='Email']/following-sibling::td")).getText(),
                editEmail);
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
