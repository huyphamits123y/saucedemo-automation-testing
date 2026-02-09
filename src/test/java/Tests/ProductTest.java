package Tests;

import Base.BaseTest;
import Pages.CartPage;
import Pages.HomePage;
import Pages.LoginPage;
import Pages.ProductPage;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.io.FileHandler;
import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ProductTest {
    HomePage homePage;
    LoginPage loginPage;
    ProductPage productPage;
    @BeforeClass
    public void setUp(){
        BaseTest.setDriver("chrome");
    }
    @BeforeClass(dependsOnMethods = "setUp")
    public void loginBeforeTests() throws InterruptedException {
        loginPage = new LoginPage();
        loginPage.open();
        Assert.assertTrue(loginPage.isLoginSuccess("standard_user", "secret_sauce"));

    }
    @BeforeClass(dependsOnMethods = "loginBeforeTests")
    public void SwitchToCartBeforeTests() throws InterruptedException {
        homePage = new HomePage();
        homePage.isAddProductSuccess("Sauce Labs Fleece Jacket");

        homePage.switchToCart();
        Thread.sleep(2000);
    }
    @Test(priority = 1)
    public void verifyProductDetailsAtCart() throws InterruptedException {
        productPage = new ProductPage();
        Thread.sleep(2000);
        productPage.openProductDetailsAtCart("Sauce Labs Fleece Jacket");
        Assert.assertTrue(productPage.verifyProductDetailsAtCart());
        Thread.sleep(2000);
    }
    @Test(priority = 2)
    public void removeProductSuccessfullyAtCart(){

        Assert.assertTrue(productPage.isRemoveProductSuccess("Sauce Labs Fleece Jacket"));
//        Assert.assertTrue(homePage.isRemoveProductSuccess("Sauce Labs Bike Light"));

    }
    @Test(priority = 3)
    public void verifyBackToProduct() throws InterruptedException {
        Assert.assertTrue(productPage.verifyBackToProducts());
        Thread.sleep(2000);
    }
//    @Test(priority = 3)
//    public void verifyProductDetailsAtHome(){
//        productPage.openProductDetailsAtHome("Sauce Labs Backpack");
//        Assert.assertTrue(productPage.verifyProductDetailsAtHome());
//    }
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
    public void teardown(){
        BaseTest.quit();
    }
}
