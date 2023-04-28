package listener;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;


public class ReportListener implements ITestListener {

    @Override
    public void onTestStart(ITestResult iTestResult) {

    }

    @Override
    public void onTestSuccess(ITestResult iTestResult) {

    }

    @Override
    public void onTestFailure(ITestResult result) {
        Object testClass = result.getInstance();
        WebDriver webDriver = ((Topic_10_Register) testClass).getDriver();

        captureScreenshot(webDriver, result.getName());
    }

        public String captureScreenshot (WebDriver driver, String screenshotName) {
            try {
                Calendar calendar = Calendar.getInstance();
                SimpleDateFormat formater = new SimpleDateFormat("dd_MM_yyyy_hh_mm_ss");
                File source = ((TakesScreenshot) driver).getScreenshotAs (OutputType.FILE);
                String projectPath = System.getProperty("user.dir");
                String screenPath = projectPath + "\\" + screenshotName + "_" + formater.format(calendar.getTime());
                FileUtils.copyFile(source, new File(screenPath));
                return screenPath;
            } catch (IOException e) {
                System.out.println("Exception while taking screenshot: "+ e.getMessage());
                return e.getMessage();
            }
    }

    @Override
    public void onTestSkipped(ITestResult iTestResult) {

    }

    @Override
    public void onTestFailedButWithinSuccessPercentage(ITestResult iTestResult) {

    }

    @Override
    public void onStart(ITestContext iTestContext) {

    }

    @Override
    public void onFinish(ITestContext iTestContext) {

    }
}
