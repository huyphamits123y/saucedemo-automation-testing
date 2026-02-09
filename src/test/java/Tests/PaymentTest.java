package Tests;

import Base.BaseTest;
import Pages.CartPage;
import Pages.HomePage;
import Pages.LoginPage;
import Pages.PaymentPage;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.io.FileHandler;
import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.annotations.*;

import java.io.File;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class PaymentTest {
    HomePage homePage;
    LoginPage loginPage;
    PaymentPage paymentPage;
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
//        homePage.isAddProductSuccess("Sauce Labs Backpack");
//        System.out.println("1");
//        homePage.isAddProductSuccess("Sauce Labs Bike Light");
        Thread.sleep(2000);
        homePage.switchToCart();
        System.out.println("switch to cart");

    }
//    @BeforeClass(dependsOnMethods = "SwitchToCartBeforeTests")
//    public void SwitchToPaymentBeforeTests() throws InterruptedException {
//
//        paymentPage = new PaymentPage();
//        paymentPage.openPayment();
//    }

    // onlyforGroups dùng khi có testng
//    @BeforeMethod(onlyForGroups = "reload")
//    public void reloadPage() {
//        System.out.println("Reload page");
//        BaseTest.driver.navigate()
//                .to("https://www.saucedemo.com/checkout-step-one.html");
//    }

//
@BeforeMethod
public void reloadPage(Method method) {

    List<String> reloadTests = List.of(
            "isEnteredWithEmptyFirstName",
            "isEnteredWithEmptyLastName",
            "isEnteredWithEmptyPostTalCode",
            "isEnteredSuccessfully"
    );

    if (reloadTests.contains(method.getName())) {
        BaseTest.driver.navigate()
                .to("https://www.saucedemo.com/checkout-step-one.html");
    }
}







    @Test(priority = 1)
    public void verifyOpenPayment(){
        paymentPage = new PaymentPage();
        paymentPage.openPayment();
        System.out.println("currnet" + BaseTest.driver.getCurrentUrl());
        Assert.assertTrue(BaseTest.driver.getCurrentUrl().contains("https://www.saucedemo.com/checkout-step-one.html"));
    }
    @Test(priority = 2)
    public void isEnteredWithEmptyFirstName() throws InterruptedException {
        Assert.assertTrue(paymentPage.isEnteredWithEmptyFirstName("", "a", "a"));
    }
    @Test(priority = 3)
    public void isEnteredWithEmptyLastName() throws InterruptedException {
        System.out.println("peg" + BaseTest.driver.getCurrentUrl());
        Assert.assertTrue(paymentPage.isEnteredWithEmptyLastName("a", "", "a"));
    }
    @Test(priority = 4)
    public void isEnteredWithEmptyPostTalCode() throws InterruptedException {
        Assert.assertTrue(paymentPage.isEnteredWithEmptyPostalCode("a", "a", ""));
    }
    @Test(priority = 5)
    public void isEnteredSuccessfully() throws InterruptedException {
        Assert.assertTrue(paymentPage.isEnteredSuccessfully("aa", "aa", "aa"));
    }
    @Test(priority = 6)
    public void verifyItemListAtPayment(){
        Assert.assertTrue(paymentPage.verifyListItemsAtPayment());
    }

    @Test(priority = 7)
    public void verifyTotalPrice(){
        Assert.assertTrue(paymentPage.verifyTotalPrice());
    }
    @Test(priority = 8)
    public void verifyCheckoutSuccessfully(){
        Assert.assertTrue(paymentPage.verifyCheckoutSuccessfully());
    }
    @Test(priority = 9)
    public void verifyBackToHome(){
        Assert.assertTrue(paymentPage.verifyBackToHome());
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
//                FileHandler.copy(source, new File(String.format("target/screenshot-"+ result.getName() + dateFormat.format(new Date()) +"-%s-%s.png", "context-menu", System.currentTimeMillis())));
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
