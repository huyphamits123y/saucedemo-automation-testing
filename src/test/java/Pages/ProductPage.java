package Pages;

import Base.BaseTest;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

public class ProductPage {
    // biến static truyền vào hàm static được
    // biến static truyền vào hàm thường được
    // biến thường truyền vào hàm static không được

    public static String nameProduct;
    public static String descriptionProduct;
    public static String priceProduct;

    public static String nameProductAtHome;
    public static String descriptionProductAtHome;
    public static String priceProductAtHome;

    By total = By.xpath("//span[@class='shopping_cart_badge']");
    By name = By.xpath("//div[@class='inventory_details_name large_size']");
    By description = By.xpath("//div[@class='inventory_details_desc large_size']");
    By price = By.xpath("//div[@class='inventory_details_price']");
    By buttonBackToProducts = By.xpath("//button[@id='back-to-products']");
    By buttonRemove = By.xpath("//button[@id='remove']");
    public ProductPage(){

    }
//    public void openProductDetail(String name){
//
//        BaseTest.openProductDetail(name);
//
//
//    }
//    public boolean verifyProductDetails(){
//       return BaseTest.verifyProductDetails(name, description, price);
//
//    }
    public boolean verifyBackToProducts(){
        BaseTest.clickJS(BaseTest.driver.findElement(buttonBackToProducts));
        System.out.println("verify back" + BaseTest.driver.getCurrentUrl());
        BaseTest.waitForUrl("https://www.saucedemo.com/inventory.html");
        return BaseTest.driver.getCurrentUrl().contains("https://www.saucedemo.com/inventory.html");
    }
    public void openProductDetailsAtCart(String name){
        // anh chị em
        // //div[normalize-space()='Sauce Labs Backpack']/following-sibling::div[@class='inventory_item_desc']
        // //div[normalize-space()='Sauce Labs Backpack']/following-sibling::div

        // cha nó là thẻ a
//        "div[normalize-space()='Sauce Labs Backpack']/parent::a


        //div[normalize-space()='Sauce Labs Backpack']/parent::a/following-sibling::div[@class='inventory_item_desc']
        //div[normalize-space()='Sauce Labs Backpack']/parent::a/parent::div/following-sibling::div[@class="pricebar"]/div[@class="inventory_item_price"]
        //div[normalize-space()='Sauce Labs Backpack']/parent::a/following-sibling::div[@class='item_pricebar']/div[@class='inventory_item_price']
        nameProduct = name;
        System.out.println(BaseTest.driver.getCurrentUrl());
        priceProduct = BaseTest.getElement(By.xpath(
                "//div[normalize-space()='" + name + "']/parent::a/following-sibling::div[@class='item_pricebar']/div[@class='inventory_item_price']"
        )).getText();
        descriptionProduct = BaseTest.getElement(By.xpath(
                "//div[normalize-space()='" + name + "']/parent::a/following-sibling::div[@class='inventory_item_desc']"
        )).getText();
        System.out.println("des" + descriptionProduct);
        System.out.println("price" + priceProduct);
        BaseTest.click(By.xpath("//div[normalize-space()='" + name + "']"));


    }
    public boolean verifyProductDetailsAtCart(){
        System.out.println(BaseTest.driver.getCurrentUrl());
        System.out.println("name product" + BaseTest.getElementPresence(name).getText());
        System.out.println("des product" + BaseTest.getElementPresence(description).getText());
        System.out.println("price product" + BaseTest.getElementPresence(price).getText());
        int a = 0;
        try{
            WebElement img = BaseTest.getElementPresence(By.xpath("//img[@alt='" + nameProduct + "']"));
        } catch (Exception e) {
            a++;
        }
        System.out.println(a);
        return BaseTest.getElement(name).getText().contains(nameProduct) && BaseTest.getElement(description).getText().contains(descriptionProduct) && BaseTest.getElement(price).getText().contains(priceProduct) && a == 0;
    }
    public void openProductDetailsAtHome(String name){
        // anh chị em
        // //div[normalize-space()='Sauce Labs Backpack']/following-sibling::div[@class='inventory_item_desc']
        // //div[normalize-space()='Sauce Labs Backpack']/following-sibling::div

        // cha nó là thẻ a
//        "div[normalize-space()='Sauce Labs Backpack']/parent::a


        //div[normalize-space()='Sauce Labs Backpack']/parent::a/following-sibling::div[@class='inventory_item_desc']
        //div[normalize-space()='Sauce Labs Backpack']/parent::a/parent::div/following-sibling::div[@class="pricebar"]/div[@class="inventory_item_price"]
        //div[normalize-space()='Sauce Labs Backpack']/parent::a/following-sibling::div[@class='item_pricebar']/div[@class='inventory_item_price']
        nameProductAtHome = name;
        System.out.println(BaseTest.driver.getCurrentUrl());
        priceProductAtHome = BaseTest.getElement(By.xpath(
                "//div[normalize-space()='" + name + "']/parent::a/parent::div//following-sibling::div[@class='pricebar']/div[@class='inventory_item_price']"
        )).getText();
        descriptionProductAtHome = BaseTest.getElement(By.xpath(
                "//div[normalize-space()='" + name + "']/parent::a/following-sibling::div[@class='inventory_item_desc']"
        )).getText();
        System.out.println("des at home" + descriptionProductAtHome);
        System.out.println("price at home" + priceProductAtHome);
        BaseTest.click(By.xpath("//div[normalize-space()='" + name + "']"));


    }
    public boolean verifyProductDetailsAtHome(){
        System.out.println(BaseTest.driver.getCurrentUrl());
        System.out.println("name product" + BaseTest.getElementPresence(name).getText());
        System.out.println("des product" + BaseTest.getElementPresence(description).getText());
        System.out.println("price product" + BaseTest.getElementPresence(price).getText());
        int a = 0;
        try{
            WebElement img = BaseTest.getElementPresence(By.xpath("//img[@alt='" + nameProductAtHome + "']"));
        } catch (Exception e) {
            a++;
        }
        System.out.println(a);
        return BaseTest.getElement(name).getText().contains(nameProductAtHome) && BaseTest.getElement(description).getText().contains(descriptionProductAtHome) && BaseTest.getElement(price).getText().contains(priceProductAtHome) && a == 0;
    }



    public void removeProduct(String name) {
        if (HomePage.nameproductAdd.contains(name)) {
            HomePage.nameproductAdd.remove(name);
        }
        System.out.println("name remove");
        if (HomePage.nameproductAdd.size() != 0){
            HomePage.nameproductAdd.forEach(i -> System.out.println(" name product con lai " + i));
        }
        BaseTest.getElement(buttonRemove).click();
    }
    public boolean isRemoveProductSuccess(String name) {
        int totalInitial;
        int totalChanged;
        totalInitial = Integer.valueOf(BaseTest.getElement(total).getText());
        System.out.println("BEFORE ---- " + totalInitial);
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
}
