package Tests;

import Base.BaseTest;
import Pages.LoginPage;
import Pages.HomePage;
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

public class HomeTest {
    HomePage homePage;
    LoginPage loginPage;

    // valid, invalid kiểm tra có hợp lệ hay không (vd: kiểm tra đầu vào)
    // verify kiểm tra xác nhận kết quả
    // check kiểm tra có hiển thị, có click được, đã click chưa
    @BeforeClass
    public void setUp() {
        BaseTest.setDriver("chrome");
    }
    // dependsOnMethods = "setUp" là Class này sẽ được chạy sau khi Class setUp chạy thành công
    @BeforeClass(dependsOnMethods = "setUp")
    public void loginBeforeTests() throws InterruptedException {
        loginPage = new LoginPage();
        loginPage.open();
        Assert.assertTrue(loginPage.isLoginSuccess("standard_user", "secret_sauce"));
        homePage = new HomePage();
    }
//    @Test(priority = 1)
//    public void verifyProductDetail(){
//
//        homePage.openProductDetail("Sauce Labs Backpack");
//        Assert.assertTrue(homePage.verifyProductDetails());
//    }
//    @Test(priority = 2)
//    public void verifyBackToProducts(){
//        Assert.assertTrue(homePage.verfifyBackToProducts());
//    }


    @Test(priority = 1)
    public void addProductSuccessfully(){

        Assert.assertTrue(homePage.isAddProductSuccess("Sauce Labs Backpack"));
//        Assert.assertTrue(homePage.isAddProductSuccess("Sauce Labs Bike Light"));
//        Assert.assertTrue(homePage.isAddProductSuccess("Sauce Labs Bolt T-Shirt"));
    }
    @Test(priority = 2)
    public void removeProductSuccessfully(){

        Assert.assertTrue(homePage.isRemoveProductSuccess("Sauce Labs Backpack"));
//        Assert.assertTrue(homePage.isRemoveProductSuccess("Sauce Labs Bike Light"));

    }
    @Test(priority = 3)
    public void checkSelectedSearchSuccessfully(){
        Assert.assertTrue(homePage.isSelectedSearch("Price (high to low)"));
        Assert.assertTrue(homePage.isSelectedSearch("Name (A to Z)"));
    }

    /** TESTCASE
     * Click chọn lọc
     * Lấy dữ liệu sau khi lọc
     * xử lí thuật toán sắp xếp trong java (lấy dữ liệu sau khi lọc để xử lí)
     * so sánh kết quả dữ liệu sau khi lọc với dữ liệu sau khi sắp xếp có giống nhau không
     */
    @Test(priority = 4)
    public void checkSearchSuccessfully(){
        Assert.assertTrue(homePage.isSelectedSearch("Name (A to Z)"));
        Assert.assertTrue(homePage.isSortedByNameAToZ());
        Assert.assertTrue(homePage.isSelectedSearch("Name (Z to A)"));
        Assert.assertTrue(homePage.isSortedByNameZToA());
        Assert.assertTrue(homePage.isSelectedSearch("Price (high to low)"));
        Assert.assertTrue(homePage.isSortedPriceHighToLow());
        Assert.assertTrue(homePage.isSelectedSearch("Price (low to high)"));
        Assert.assertTrue(homePage.isSortedPriceLowToHigh());

    }
    @Test(priority = 5)
    public void verifyBrokenImages(){
       Assert.assertTrue(homePage.verifyBrokenImages());
    }
    @Test(priority = 6)
    // Thuật toán
    // lấy ID của window/tab hiện tại
    // mở tab mới
    // BaseTest.switchToNewTab(parent);
    // chờ đợi đến khi tổng số tab là 2
    // duyệt for qua từng tab nếu tab nào không phải là tab parent thì chuyển sang tab đó
    // break thoát for
    // Assert.assertTrue(homePage.verifyLinkTwitter());
    // kiểm tra url tab hiện tại có bằng tab mong đợi không
    // BaseTest.closeCurrentTabAndBack(parent);
    // đóng tab và di chuyển về tab cha
    public void verifyLinkTwitter() {

        // Trả về ID của window/tab hiện tại
        String parent = BaseTest.driver.getWindowHandle();
        System.out.println("link" + parent);

        // Mở tab mới
        homePage.openTwitter();
        System.out.println("open tab");
        // chờ đợi đến khi tổng số tab là 2
        // duyệt for qua từng tab nếu tab nào không phải là tab parent thì chuyển sang tab đó
        // break thoát for
        BaseTest.switchToNewTab(parent);

        // kiểm tra url tab hiện tại có bằng tab mong đợi không
        Assert.assertTrue(homePage.verifyLinkTwitter());

        // đóng tab và di chuyển về tab cha
       BaseTest.closeCurrentTabAndBack(parent);
    }
    @Test(priority = 7)
    public void verifyLinkFaceBook() {

        // Trả về ID của window/tab hiện tại
        String parent = BaseTest.driver.getWindowHandle();
        System.out.println("link" + parent);
        homePage.openFacebook();
        System.out.println("open tab");

        BaseTest.switchToNewTab(parent);
        System.out.println("switch parent");

        Assert.assertTrue(homePage.verifyLinkFacebook());


        BaseTest.closeCurrentTabAndBack(parent);
    }
    @Test(priority = 8)
    public void verifyLinkLinkedin() {

        // Trả về ID của window/tab hiện tại
        String parent = BaseTest.driver.getWindowHandle();

        homePage.openLinkedin();
        System.out.println("open tab");

        BaseTest.switchToNewTab(parent);
        System.out.println("switch parent");

        Assert.assertTrue(homePage.verifyLinkLinkedin());
        BaseTest.closeCurrentTabAndBack(parent);
    }

        @Test(priority = 9)
    public void verifyLinkAbout(){
        Assert.assertTrue(homePage.verifyLinkAbout());
        BaseTest.back();
    }
    @Test(priority = 10)
    public void verifyLinkAllItems(){
        // vì khi vô trang linkAllItems thì nó là trang homePage luôn nên không quay lại được
        Assert.assertTrue(homePage.verifyLinkAllItems());
        BaseTest.refresh();
    }
    @Test(priority = 11)
    public void verifyLinkLogout(){
        Assert.assertTrue(homePage.verifyLinkLogout());
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
