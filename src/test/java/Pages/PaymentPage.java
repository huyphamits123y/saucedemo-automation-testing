package Pages;

import Base.BaseTest;
import io.opentelemetry.api.baggage.BaggageEntry;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.util.ArrayList;
import java.util.List;

import static Base.BaseTest.driver;
import static Base.BaseTest.wait;

public class PaymentPage {
    By buttonPayment = By.xpath("//button[@id='checkout']");
    By inputFirstName = By.xpath("//input[@id='first-name']");
    By inputLastName = By.xpath("//input[@id='last-name']");
    By inputPostalCode = By.xpath("//input[@id='postal-code']");
    // KHÔNG NÊN LẤY XPATH NHƯ VẬY VÌ NÓ CHỈ CÓ 1 XPATH VÀ CHỈ THAY ĐỔI TEXT DO ĐÓ NÊN LẤY 1 XPATH LÀ ĐỦ
//    By errorFistName = By.xpath("//h3[normalize-space()='Error: First Name is required']");
//    By errorLastName = By.xpath("//h3[normalize-space()='Error: Last Name is required']");
//    By errorPostalCode = By.xpath("//h3[normalize-space()='Error: Postal Code is required']");
//      By errorMessage = By.xpath("//div[@class='error-message-container error']/h3[@data-test='error']");
    By errorMessage = By.xpath("//h3[@data-test='error']");


    By buttonContinue = By.xpath("//input[@id='continue']");
    By nameListItem = By.xpath("//div[@class='inventory_item_name']");
    By priceListItem = By.xpath("//div[@class='inventory_item_price']");
    By totalPriceTax = By.xpath("//div[@class='summary_total_label']");
    By buttonFinish = By.xpath("//button[@id='finish']");
    By buttonBackHome = By.xpath("//button[@id='back-to-products']");
    public PaymentPage(){

    }
    public void openPayment(){

        BaseTest.clickJS(BaseTest.getElement(buttonPayment));

    }

//    public void submitValidInformation(String firstname, String lastname, String postalcode){
//        System.out.println("cc ma,e" + firstname);
//        System.out.println("cc ma,e" + lastname);
//        System.out.println("cc ma,e" + postalcode);
//        BaseTest.click(inputFirstName);
//        BaseTest.fill(inputFirstName, firstname);
//        System.out.println("firsdt name " + lastname);
//        System.out.println("first" + BaseTest.getElementPresence(inputFirstName).getAttribute("value"));
//        BaseTest.click(inputLastName);
//        BaseTest.fill(inputLastName, lastname);
//
//
//
//        BaseTest.click(inputPostalCode);
//        BaseTest.fill(inputPostalCode, postalcode);
//        JavascriptExecutor js = (JavascriptExecutor) BaseTest.driver;
//
//        String value = (String) js.executeScript(
//                "return document.querySelector('[data-test=\"postalCode\"]').value"
//        );
//
//        System.out.println("postal = [" + value + "]");
//
//
//        System.out.println("postal" + BaseTest.getElementPresence(inputFirstName).getAttribute("value"));
//        BaseTest.click(buttonContinue);
//    }

//    public void submitValidInformation(String firstname, String lastname, String postalcode) {
//
//        System.out.println("param firstname = " + firstname);
//        System.out.println("param lastname  = " + lastname);
//        System.out.println("param postal    = " + postalcode);
//
//       fillReactInput(inputFirstName, firstname);
//        System.out.println("UI first name = [" + getInputValue(inputFirstName) + "]");
//
//        fillReactInput(inputLastName, lastname);
//        System.out.println("UI last name  = [" + getInputValue(inputLastName) + "]");
//
//    fillReactInput(inputPostalCode, postalcode);
//        System.out.println("UI postal     = [" + getInputValue(inputPostalCode) + "]");
//
//
//    }

    public void submitValidInformation(String firstname, String lastname, String postalcode) {

        // First Name
        BaseTest.click(inputFirstName);
        BaseTest.clear(inputFirstName);
        BaseTest.fill(inputFirstName, firstname);

        String firstNameUI = getInputValue(inputFirstName);
        System.out.println("UI first name = [" + firstNameUI + "]");

        // Last Name
        BaseTest.click(inputLastName);
        BaseTest.clear(inputLastName);
        BaseTest.fill(inputLastName, lastname);

        String lastNameUI = getInputValue(inputLastName);
        System.out.println("UI last name  = [" + lastNameUI + "]");

        // Postal Code
        BaseTest.click(inputPostalCode);
        BaseTest.clear(inputPostalCode);
        BaseTest.fill(inputPostalCode, postalcode);

        String postalUI = getInputValue(inputPostalCode);
        System.out.println("UI postal     = [" + postalUI + "]");
    }

//    public static void fillReactInput(By by, String value) {
//        WebElement el = wait.until(ExpectedConditions.visibilityOfElementLocated(by));
//
//        el.click();
//
//        // Clear theo cách người dùng
//        el.sendKeys(Keys.chord(Keys.CONTROL, "a"));
//        el.sendKeys(Keys.DELETE);
//
//        // Nhập dữ liệu
//        el.sendKeys(value);
//    }



    public static String getInputValue(By by) {
        return BaseTest.getElement(by).getAttribute("value");
    }


// System.out.println("UI first name = [" + getInputValue(inputFirstName) + "]");
//        System.out.println("UI last name  = [" + getInputValue(inputLastName) + "]");
//        System.out.println("UI postal     = [" + getInputValue(inputPostalCode) + "]");

//    public static String getInputValue(By by) {
//        JavascriptExecutor js = (JavascriptExecutor) driver;
//        return (String) js.executeScript(
//                "return arguments[0].value;",
//                driver.findElement(by)
//        );
//    }
//    public static void fillReactInput(By by, String value) {
//        JavascriptExecutor js = (JavascriptExecutor) driver;
//        WebElement element = driver.findElement(by);
//
//        js.executeScript(
//                "arguments[0].focus();" +
//                        "arguments[0].value = arguments[1];" +
//                        "arguments[0].dispatchEvent(new Event('input', { bubbles: true }));" +
//                        "arguments[0].dispatchEvent(new Event('change', { bubbles: true }));",
//                element,
//                value
//        );
//    }

    public static void fillReactInput(By by, String value) {
        WebElement el = wait.until(ExpectedConditions.elementToBeClickable(by));

        el.click();

        el.sendKeys(Keys.chord(Keys.CONTROL, "a"));
        el.sendKeys(Keys.DELETE);

        el.sendKeys(value);

        // ÉP React commit state
        el.sendKeys(Keys.TAB);

        // Verify ngay trên element đang dùng
        System.out.println("DEBUG UI value = [" + el.getAttribute("value") + "]");
    }


//    public void submitValidInformation(String firstname, String lastname, String postalcode) {
//
//        fillReactInput(inputFirstName, firstname);
//        System.out.println("UI first name = [" + getInputValue(inputFirstName) + "]");
//
//        fillReactInput(inputLastName, lastname);
//        System.out.println("UI last name  = [" + getInputValue(inputLastName) + "]");
//
//        fillReactInput(inputPostalCode, postalcode);
//        System.out.println("UI postal     = [" + getInputValue(inputPostalCode) + "]");
//    }

//    public void submitValidInformation(String firstname, String lastname, String postalcode) {
//
//        fillReactInput(inputFirstName, firstname);
//        fillReactInput(inputLastName, lastname);
//        fillReactInput(inputPostalCode, postalcode);
//    }










    //    public boolean isEnteredWithEmptyFirstName(String firstname, String lastname, String postalcode){
//       submitValidInformation(firstname, lastname, postalcode);
//        return BaseTest.getElementPresence(errorMessage).getText().contains("First Name is required");
//    }
public boolean isEnteredWithEmptyFirstName(String firstname, String lastname, String postalcode)  {

    submitValidInformation(firstname, lastname, postalcode);

    BaseTest.clickJS(BaseTest.getElement(buttonContinue));
    System.out.println("bin +++ ---" + BaseTest.getElementPresence(errorMessage).getText());
    return  BaseTest.getElementPresence(errorMessage).getText().contains("Error: First Name is required");
}




    public boolean isEnteredWithEmptyLastName(String firstname, String lastname, String postalcode) {
        submitValidInformation(firstname, lastname, postalcode);

        BaseTest.clickJS(BaseTest.getElement(buttonContinue));
        System.out.println("bin +++ " + BaseTest.getElementPresence(errorMessage).getText());
        return BaseTest.getElementPresence(errorMessage).getText().contains("Last Name is required");
    }
    public boolean isEnteredWithEmptyPostalCode(String firstname, String lastname, String postalcode)  {
        submitValidInformation(firstname, lastname, postalcode);
        BaseTest.clickJS(BaseTest.getElement(buttonContinue));
        return BaseTest.getElementPresence(errorMessage).getText().contains("Error: Postal Code is required");
    }
    public boolean isEnteredSuccessfully(String firstname, String lastname, String postalcode) {
        submitValidInformation(firstname, lastname, postalcode);

        BaseTest.click(buttonContinue);
        BaseTest.waitForUrl("https://www.saucedemo.com/checkout-step-two.html");
        System.out.println(driver.getCurrentUrl());
        return driver.getCurrentUrl().contains("https://www.saucedemo.com/checkout-step-two.html");
    }
    public boolean verifyListItemsAtPayment(){
        System.out.println("Checkout product");
        HomePage.nameproductAdd.forEach(i -> System.out.println(i));
        System.out.println("cart product");
        List<String> name = BaseTest.getElements(nameListItem).stream().map(i -> i.getText()).toList();
        name.forEach(n -> System.out.println(n));
        // Dùng equals là so sánh 2 mảng có cùng số lượng, thứ tự, giá trị bằng nhau
        return  HomePage.nameproductAdd.equals(BaseTest.getElements(nameListItem).stream().map(i -> i.getText()).toList());
    }
    public boolean verifyTotalPrice(){
        System.out.println(totalPrice());
        double total =  Double.valueOf(BaseTest.getElement(totalPriceTax)
                .getText()
                .split("\\$")[1]
                .trim());
        System.out.println(total);

        return total - 3.20 == totalPrice() || total == 0.0;
    }
    public double totalPrice() {
        return BaseTest.getElements(priceListItem)
                .stream()
                .mapToDouble(i -> Double.parseDouble(i.getText().replace("$", "")))
                .sum();
    }
    public boolean verifyCheckoutSuccessfully(){
        BaseTest.clickJS(BaseTest.getElement(buttonFinish));
        BaseTest.waitForUrl("https://www.saucedemo.com/checkout-complete.html");
        return driver.getCurrentUrl().contains("https://www.saucedemo.com/checkout-complete.html");
    }
    public boolean verifyBackToHome(){
        BaseTest.click(buttonBackHome);
        BaseTest.waitForUrl("https://www.saucedemo.com/inventory.html");

        return driver.getCurrentUrl().contains("https://www.saucedemo.com/inventory.html");
    }





}
