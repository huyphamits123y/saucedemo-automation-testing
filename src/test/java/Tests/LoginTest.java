package Tests;

import Base.BaseTest;
import Base.TestListener;
import Pages.LoginPage;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.io.FileHandler;
import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.annotations.*;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

@Listeners(TestListener.class)
public class LoginTest {
    LoginPage loginPage;
    @BeforeClass
    public void  setUp() {
        BaseTest.setDriver("chrome");
        loginPage = new LoginPage();

    }
    @BeforeMethod
    public void reloadPage(){
        loginPage.open();
    }
    @Test(priority = 1)
    public void LoginFailedWithEmptyUsernameTestcase()  {
        BaseTest.waitForPageLoaded();
        Assert.assertTrue(loginPage.isLoginFailedWithEmptyUsername("", "secret_sauce"));
    }
    @Test(priority = 2)
    public void LoginFailedWithEmptyPasswordTestcase() {

        BaseTest.waitForPageLoaded();

        Assert.assertTrue(loginPage.isLoginFailedWithEmptyPassword("standard_user", ""));
    }
    @Test(priority = 3)
    public void LoginFailedWithInvalidCredentialsTestcase() {
        BaseTest.waitForPageLoaded();
        Assert.assertTrue(loginPage.isLoginFailedInvalid("invalid_user", "invalid_pass"));
    }

    @Test(priority = 4)
    public void LoginSuccessTestcase()  {
        BaseTest.waitForPageLoaded();
        Assert.assertTrue(loginPage.isLoginSuccess("standard_user", "secret_sauce"));
    }
    @AfterMethod
    public void takeScreenshot(ITestResult result) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy HH-mm-ss");
        // Khởi tạo đối tượng result thuộc ITestResult để lấy trạng thái và tên của từng Step
        // Ở đây sẽ so sánh điều kiện nếu testcase(@Test) passed hoặc failed
        // passed = SUCCESS và failed = FAILURE
        BaseTest.waitForPageLoaded();
        if (ITestResult.FAILURE == result.getStatus()) {
            try {
                // Tạo tham chiếu của TakesScreenshot
                TakesScreenshot ts = (TakesScreenshot) BaseTest.driver;
                // Gọi hàm capture screenshot - getScreenshotAs
                File source = ts.getScreenshotAs(OutputType.FILE);
                //Kiểm tra folder tồn tại. Nêu không thì tạo mới folder
                File theDir = new File("./Screenshots/");
                if (!theDir.exists()) {
                    theDir.mkdirs();
                }
                // result.getName() lấy tên của test case xong gán cho tên File chụp màn hình
                FileHandler.copy(
                        source,
                        new File(String.format(
                                "./Screenshots/%s-%s-%s.png",
                                result.getName(),
                                dateFormat.format(new Date()),
                                System.currentTimeMillis()
                        ))
                );
                System.out.println("Đã chụp màn hình: " + result.getName());
            } catch (Exception e) {
                System.out.println("Exception while taking screenshot " + e.getMessage());
            }
        }
    }
    @AfterClass
    public void tearDown() {
        BaseTest.quit();

    }
}
