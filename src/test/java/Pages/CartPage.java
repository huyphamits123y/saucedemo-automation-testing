package Pages;

import Base.BaseTest;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.util.ArrayList;
import java.util.List;

public class CartPage {
    By listItems = By.xpath("//div[@class='cart_list']/div[@class='cart_item']");
    By continueShopping = By.xpath("//button[@id='continue-shopping']");
    By total = By.xpath("//span[@class='shopping_cart_badge']");
    By menuButton = By.xpath("//button[@id='react-burger-menu-btn']");
    By allItems = By.xpath("//a[@id='inventory_sidebar_link']");
    By about = By.xpath("//a[@id='about_sidebar_link']");
    By logout = By.xpath("//a[@id='logout_sidebar_link']");

    By nameListItem = By.xpath("//div[@class='inventory_item_name']");
    public CartPage(){

    }
    public boolean verifyCartPage(){
        System.out.println("Link cart" + BaseTest.driver.getCurrentUrl());
        return BaseTest.driver.getCurrentUrl().contains("https://www.saucedemo.com/cart.html");
    }
    public int totalListItems(){
        if (BaseTest.getElements(listItems).isEmpty()){
            return 0;
        }
        return BaseTest.getElements(listItems).size();
    }
    public boolean verifyListItemsAtCart(){
        System.out.println("name product");
        HomePage.nameproductAdd.forEach(i -> System.out.println(i));
        System.out.println("cart product");
        List<String> name = BaseTest.getElements(nameListItem).stream().map(i -> i.getText()).toList();
        name.forEach(n -> System.out.println(n));
        // Dùng equals là so sánh 2 mảng có cùng số lượng, thứ tự, giá trị bằng nhau
      return  HomePage.nameproductAdd.equals(BaseTest.getElements(nameListItem).stream().map(i -> i.getText()).toList());
    }

    public void BackToHomePage(){
        BaseTest.clickJS(BaseTest.getElement(continueShopping));

    }
    public boolean CheckBackToHomePage(){
        return BaseTest.driver.getCurrentUrl().contains("https://www.saucedemo.com/inventory.html");
    }
    public By buttonRemoveProduct(String name) {
        String lower = name.toLowerCase();
        String[] list = lower.split(" ");
        String nameProduct = "remove";
        for (String a : list) {
            nameProduct += "-" + a;

        }
        System.out.println(nameProduct);

        return By.xpath(" //button[@id='" + nameProduct + "'] ");
    }

    public void removeProduct(String name) {
        if (HomePage.nameproductAdd.contains(name)) {
            HomePage.nameproductAdd.remove(name);
        }
        System.out.println("name remove");
        if (HomePage.nameproductAdd.size() != 0){
            HomePage.nameproductAdd.forEach(i -> System.out.println(" name product con lai " + i));
        }
        BaseTest.getElement(buttonRemoveProduct(name)).click();
    }
    public boolean isRemoveProductSuccess(String name) {
        int totalInitial;
        int totalChanged;
        totalInitial = Integer.valueOf(BaseTest.getElement(total).getText());
        System.out.println("BEFORE" + totalInitial);
        removeProduct(name);
        try {
//            totalChanged = Integer.valueOf(BaseTest.getElement(total).getText());
            totalChanged = Integer.valueOf(BaseTest.getElementPresence(total).getText());
        } catch (Exception e) {
            totalChanged = 0;
        }
        System.out.println("AFTER" + totalChanged);
        return totalInitial - totalChanged == 1;
    }
    public void detailsProduct(String name){
        BaseTest.click(By.xpath("//div[normalize-space()='" + name + "']"));

    }


    public boolean verifyLinkTwitter() {
        return BaseTest.driver.getCurrentUrl().contains("https://x.com/saucelabs");
    }

    public boolean verifyLinkFacebook() {
        System.out.println(BaseTest.driver.getCurrentUrl());
        return BaseTest.driver.getCurrentUrl().contains("https://www.facebook.com/saucelabs");
    }
    public boolean verifyLinkLinkedin() {
        System.out.println(BaseTest.driver.getCurrentUrl());
        return BaseTest.driver.getCurrentUrl().contains("https://www.linkedin.com/company/sauce-labs/");
    }
    public boolean verifyLinkAbout() {
        BaseTest.click(menuButton);
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
        WebElement aboutLink = BaseTest.getElementPresence(about);


        // Dùng jsClick thay cho click()
        // gọi click trực tiếp trên DOM
        // không quan tâm: overlay, animation, z-index
        // click giống như user click thật ở browser
        BaseTest.clickJS(aboutLink);

        // chờ đơị đến khi url chuyển sang saucelabs.com
        BaseTest.waitForUrl("saucelabs.com");

        return BaseTest.driver.getCurrentUrl().contains("saucelabs.com");
    }

    public boolean verifyLinkAllItems(){

        BaseTest.click(menuButton);
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
        WebElement itemLink = BaseTest.getElementPresence(allItems);


        // Dùng jsClick thay cho click()
        // gọi click trực tiếp trên DOM
        // không quan tâm: overlay, animation, z-index
        // click giống như user click thật ở browser
        BaseTest.clickJS(itemLink);

        // chờ đơị đến khi url chuyển sang https://www.saucedemo.com/inventory.html
        BaseTest.waitForUrl("https://www.saucedemo.com/inventory.html");

        return BaseTest.driver.getCurrentUrl().contains("https://www.saucedemo.com/inventory.html");

    }

    public boolean verifyLinkLogout(){
        BaseTest.click(menuButton);
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
        WebElement logoutLink = BaseTest.getElementPresence(logout);


        // Dùng jsClick thay cho click()
        // gọi click trực tiếp trên DOM
        // không quan tâm: overlay, animation, z-index
        // click giống như user click thật ở browser
        BaseTest.clickJS(logoutLink);

        // chờ đơị đến khi url chuyển sang https://www.saucedemo.com/
        BaseTest.waitForUrl("https://www.saucedemo.com/");

        return BaseTest.driver.getCurrentUrl().contains("https://www.saucedemo.com/");



    }
}
