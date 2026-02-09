package Pages;

import Base.BaseTest;
import org.openqa.selenium.By;

public class LoginPage {
    By username = By.xpath("//input[@id='user-name']");
    By password = By.xpath("//input[@id='password']");
    By loginButton = By.xpath("//input[@id='login-button']");

    // KHÔNG NÊN LẤY XPATH NHƯ VẬY VÌ NÓ CHỈ CÓ 1 XPATH VÀ CHỈ THAY ĐỔI TEXT DO ĐÓ NÊN LẤY 1 XPATH LÀ ĐỦ
//    By errorEmptyUsername = By.xpath("//h3[normalize-space()='Epic sadface: Username is required']");
//    By errorEmptyErrorPassword = By.xpath("//h3[normalize-space()='Epic sadface: Password is required']");

    By errorMessage = By.xpath("//h3[@data-test='error']");

    By errorInvalid = By.xpath("//h3[contains(text(),'Epic sadface: Username and password do not match a')]");
    public LoginPage(){

    }
    public void login(String user, String pass){
        BaseTest.fill(username, user);
        BaseTest.fill(password, pass);
        BaseTest.click(loginButton);
    }
    public void open(){
        BaseTest.visit("https://www.saucedemo.com/");
    }
    public Boolean isLoginSuccess(String user, String pass) {
        BaseTest.clear(username);
        BaseTest.clear(password);
        login(user, pass);
        String currentUrl = BaseTest.driver.getCurrentUrl();
        return currentUrl.contains("inventory.html");
    }
    public Boolean isLoginFailedWithEmptyUsername(String user, String pass) {
        BaseTest.clear(username);
        BaseTest.clear(password);
        login(user, pass);
        return BaseTest.getText(errorMessage).contains("Username is required") ;

    }
    public Boolean isLoginFailedWithEmptyPassword(String user, String pass) {
        BaseTest.clear(username);
        BaseTest.clear(password);
        login(user, pass);
        return  BaseTest.getText(errorMessage).contains("Password is required");

    }

    public Boolean isLoginFailedInvalid(String user, String pass) {
        BaseTest.clear(username);
        BaseTest.clear(password);
        login(user, pass);
        return BaseTest.getText(errorInvalid).contains("Username and password do not match any user in this service");
    }


}
