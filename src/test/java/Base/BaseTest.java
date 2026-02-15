package Base;

import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import java.time.Duration;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BaseTest {
    public static WebDriver driver;
    public static WebDriverWait wait;

//    public static void setDriver(String browser){
////        ChromeOptions chromeOptions = new ChromeOptions();
////        chromeOptions.addArguments("--headless=new");
//
//
//
//        switch (browser){
//            case "chrome":
//                driver = new ChromeDriver();
//                break;
//            case "firefox":
//                driver = new FirefoxDriver();
//                break;
//            default:
//                driver = new ChromeDriver();
//                break;
//
//        }
//        wait = new WebDriverWait(driver, Duration.ofSeconds(5));
//        driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(30)); // có chức năng chờ trang load trong 30s nếu vượt quá thì fail
//        driver.manage().window().maximize();;
//
//    }


public static void setDriver(String browser){

    ChromeOptions options = new ChromeOptions();

    Map<String, Object> prefs = new HashMap<>();

    // Tắt password manager
    prefs.put("credentials_enable_service", false);
    prefs.put("profile.password_manager_enabled", false);

    // Tắt leak detection
    prefs.put("profile.password_manager_leak_detection", false);

    // Tắt Safe Browsing
    prefs.put("safebrowsing.enabled", false);

    options.setExperimentalOption("prefs", prefs);

    options.addArguments("--headless=new");
    // Ẩn automation info bar
    options.setExperimentalOption("excludeSwitches",
            Arrays.asList("enable-automation"));


    options.addArguments("--disable-notifications");
    options.addArguments("--incognito");  // QUAN TRỌNG
    options.addArguments("--disable-infobars");
    options.addArguments("--disable-save-password-bubble");

    switch (browser){
        case "chrome":
            driver = new ChromeDriver(options);
            break;
        case "firefox":
            driver = new FirefoxDriver();
            break;
        default:
            driver = new ChromeDriver(options);
            break;
    }

    wait = new WebDriverWait(driver, Duration.ofSeconds(5));
    driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(30));
    driver.manage().window().maximize();
}



//    public static WebDriver setChromeDriver(String url) {
//        ChromeOptions options = new ChromeOptions();
//
//        options.addArguments("--disable-notifications");
//        options.addArguments("--disable-infobars");
//        options.addArguments("--disable-save-password-bubble");
//        options.addArguments("--disable-features=PasswordManagerOnboarding");
//
//        Map<String, Object> prefs = new HashMap<>();
//        prefs.put("credentials_enable_service", false);
//        prefs.put("profile.password_manager_enabled", false);
//
//        options.setExperimentalOption("prefs", prefs);
//
//        driver = new ChromeDriver(options);
//        return  driver;
//    }

    public static WebDriver setChromeDriver() {

        ChromeOptions options = new ChromeOptions();

        options.addArguments("--disable-notifications");
        options.addArguments("--disable-infobars");
        options.addArguments("--disable-save-password-bubble");
        options.addArguments("--disable-features=PasswordManagerOnboarding");
        options.addArguments("--no-default-browser-check");
        options.addArguments("--disable-extensions");

        // 🔥 QUAN TRỌNG NHẤT
        options.addArguments("--user-data-dir=" + System.getProperty("java.io.tmpdir") + "/chrome-profile-" + System.currentTimeMillis());

        Map<String, Object> prefs = new HashMap<>();
        prefs.put("credentials_enable_service", false);
        prefs.put("profile.password_manager_enabled", false);

        options.setExperimentalOption("prefs", prefs);

        return new ChromeDriver(options);
    }

    public static void visit(String location) {
        driver.get(location);
    }

    public static void clear(By by) {
       driver.findElement(by).clear();
    }

    public static void fill(By by, String text) {

//        driver.findElement(by).sendKeys(text);
        getElement(by).sendKeys(text
        );
    }



    public static void click(By by) {
        wait.until(ExpectedConditions.elementToBeClickable(by)).click();
    }
    public static void clickverify(By by){
        wait.until(ExpectedConditions.visibilityOfElementLocated(by)).click();
    }


    public static String getText(By by) {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(by)).getText();
    }
    public static WebElement getElement(By by){
        return wait.until(ExpectedConditions.visibilityOfElementLocated(by));
    }
    public static List<WebElement> getElements(By by) {
        return driver.findElements(by);
    }
    public static void back(){
        driver.navigate().back();
    }
    public static void refresh(){
        driver.navigate().refresh();
    }

    public static WebElement getOption(String name){
        By by = By.xpath("//select/option[.='" + name + "']");
        return wait.until(ExpectedConditions.visibilityOfElementLocated(by));
    }
    public static void quit() {
        driver.quit();
    }

    public static void closeCurrentTabAndBack(String parentWindow) {
        System.out.println("tab hien tai" + driver.getCurrentUrl());
        driver.close();                       // đóng tab hiện tại
        driver.switchTo().window(parentWindow); // quay về tab cha
        System.out.println("tab thay doi" + driver.getCurrentUrl());
    }
    public static void waitLoad() throws InterruptedException {
        Thread.sleep(2000);
    }


    public static WebElement getElementPresence(By by){
        // presenceOfElementLocated
        // Chỉ cần element trong DOM
        // không cần visible
        // không cần click able
        // Vì sao dùng presence thay vì visibility hay clickable
        // visibility fail do animation trượt
        // clickable fail do overly
        // với menu kiểu react + animation, element:
        // luôn tồn tại trong DOM
        // chị bị translate/ opacity -> presenceOf là lựa chọn đúng
       return  wait.until(ExpectedConditions.presenceOfElementLocated(by));
    }
    public static void clickJS(WebElement element){
        // Dùng jsClick thay cho click()
        // gọi click trực tiếp trên DOM
        // không quan tâm: overlay, animation, z-index
        // click giống như user click thật ở browser

        ((JavascriptExecutor) driver)
                .executeScript("arguments[0].scrollIntoView(true);", element);

        ((JavascriptExecutor) driver)
                .executeScript("arguments[0].click();", element);
    }
    // chờ đơị đến khi url chuyển sang url mới
    public static void waitForUrl(String url){
        wait.until(
                ExpectedConditions.urlContains(url)
        );
    }
    public static void openLinkPresenceJS(By by){
        WebElement button = getElementPresence(by);

        ((JavascriptExecutor) driver)
                .executeScript("arguments[0].scrollIntoView(true);", button);

        ((JavascriptExecutor) driver)
                .executeScript("arguments[0].click();", button);
    }

public static void switchToNewTab(String parentWindow) {
    wait.until(ExpectedConditions.numberOfWindowsToBe(2));
    for (String window : driver.getWindowHandles()) {
        if (!window.equals(parentWindow)) {
            driver.switchTo().window(window);
            break; // thoát for
        }
    }
}

    public static void backToWindowParent(String parent){
        driver.switchTo().window(parent);
    }
    public static  void waitForPageLoaded() {
        ExpectedCondition<Boolean> jsLoad = new ExpectedCondition<Boolean>() {
            public Boolean apply(WebDriver driver) {
                return ((JavascriptExecutor) driver).executeScript("return document.readyState").toString()
                        .equals("complete");
            }
        };
        ExpectedCondition<Boolean> jQueryLoad = new ExpectedCondition<Boolean>() {
            public Boolean apply(WebDriver driver) {
                try{
                    return ((Long) ((JavascriptExecutor) driver).executeScript("return jQuery.active") == 0);
                }catch (Exception e){
                    return true;
                }


            }
        };
        try {
            Thread.sleep(1000);
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
            wait.until(jsLoad);
            wait.until(jQueryLoad);
        } catch (Throwable error) {

            Assert.fail("Timeout waiting for Page Load Request to complete.");
        }
    }
}
