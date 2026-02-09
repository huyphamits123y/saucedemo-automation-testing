package Pages;

import Base.BaseTest;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.net.HttpURLConnection;
import java.net.URL;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class HomePage {
    By cart = By.xpath("//a[@class='shopping_cart_link']");
    By select = By.xpath("//select[@class='product_sort_container']");
    By total = By.xpath("//span[@class='shopping_cart_badge']");
    By listProductNames = By.xpath("//div[@class='inventory_list']/div[@class='inventory_item']//div[@class='inventory_item_name ']");
    By listProductPrices = By.xpath("//div[@class='inventory_list']/div[@class='inventory_item']//div[@class='pricebar']/div[@class='inventory_item_price']");
    By listProductImages = By.xpath("//div[@class='inventory_list']/div[@class='inventory_item']//img[@class='inventory_item_img']");
    By menuButton = By.xpath("//button[@id='react-burger-menu-btn']");
    By menuPanel = By.className("bm-menu");
    By allItems = By.xpath("//a[@id='inventory_sidebar_link']");
    By about = By.xpath("//a[@id='about_sidebar_link']");
    By logout = By.xpath("//a[@id='logout_sidebar_link']");
    By menuOpen = By.cssSelector(".bm-menu-wrap.bm-menu-wrap-open");
    By twitterButton = By.xpath("//a[normalize-space()='Twitter']");
    By facebookButton = By.xpath("//a[normalize-space()='Facebook']");
    By linkedinButton = By.xpath("//a[normalize-space()='LinkedIn']");
    By name = By.xpath("//div[@class='inventory_details_name large_size']");
    By description = By.xpath("//div[@class='inventory_details_desc large_size']");
    By price = By.xpath("//div[@class='inventory_details_price']");
    public static List<String> nameproductAdd = new ArrayList<>();

    public HomePage() {

    }

    public void switchToCart(){
        BaseTest.clickJS(BaseTest.getElement(cart));
    }

    public By buttonAddProduct(String name) {
        String lower = name.toLowerCase();
        String[] list = lower.split(" ");
        String nameProduct = "add-to-cart";
        for (String a : list) {
            nameProduct += "-" + a;

        }
        return By.xpath(" //button[@id='" + nameProduct + "'] ");
    }

    public void addProduct(String name) {
        nameproductAdd.add(name);
        System.out.println("name add");
        nameproductAdd.forEach(i -> System.out.println(i));
        BaseTest.getElement(buttonAddProduct(name)).click();
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
        if (nameproductAdd.contains(name)) {
            nameproductAdd.remove(name);
        }
        System.out.println("name remove");
       if (nameproductAdd.size() != 0){
           nameproductAdd.forEach(i -> System.out.println(" name product con lai " + i));
       }
        BaseTest.getElement(buttonRemoveProduct(name)).click();
    }

    public boolean isSelectedSearch(String name) {
        Select option = new Select(BaseTest.getElement(select));
        option.selectByVisibleText(name);
        return BaseTest.getOption(name).isSelected();
    }

    public boolean isAddProductSuccess(String name) {
        int totalInitial;
        int totalChanged;
        try {
            totalInitial = Integer.valueOf(BaseTest.getElement(cart).getText());
            System.out.println("totalinita > 0" + totalInitial);
        } catch (Exception e) {
            totalInitial = 0;
        }
        addProduct(name);
        totalChanged = Integer.valueOf(BaseTest.getElement(cart).getText());
        System.out.println("totalinita" + totalInitial);
        System.out.println("totalinita" + totalChanged);

        return totalChanged - totalInitial == 1;
    }

    //    public boolean isRemoveProductSuccess(String name){
//        int totalInitial;
//        int totalChanged;
//        totalInitial = Integer.valueOf(BaseTest.getElement(cart).getText());
//        System.out.println("BEFORE" + totalInitial);
//        removeProduct(name);
//        String a = BaseTest.getElement(cart).getText().trim();
//        if (a.isEmpty()){
//            totalChanged = 0;
//        }else{
//            totalChanged = Integer.valueOf(BaseTest.getElement(cart).getText());
//        }
//        System.out.println("AFTER" + totalChanged);
//        return  totalInitial - totalChanged == 1;
//    }
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

    public List<String> getProductNames() {
        return BaseTest.getElements(listProductNames).stream().map(e -> e.getText().trim()).toList();
    }

    // TESTCASE
    // CLick lọc
    // Lấy dữ liệu sao khi lọc
    // Gọi hàm sắp xếp xử lí thuật toán lọc (lấy dữ liệu sau khi lọc để sắp xếp)
    // so sánh dữ liệu sắp xếp với dữ liệu sau khi lọc có giống nhau không
    public boolean isSortedByNameAToZ() {
        // Dữ liệu sau khi lọc thứ tụ khác ban đầu
        List<String> uiList = getProductNames();
        System.out.println("isSortedByNameAToZ");
        System.out.println("arrange before");
        uiList.forEach(i -> {
            System.out.println(i);
        });
        // Tạo bản sao của uiList
        List<String> sortedList = new ArrayList<>(uiList);
        Collections.sort(sortedList, String.CASE_INSENSITIVE_ORDER);
        System.out.println("arrange after");
        sortedList.forEach(i -> {
            System.out.println(i);
        });
        // Dùng equal để so sánh hai mảng có cùng phần tử và cùng thứ tự (kiểu dữ liệu nào cũng được)
        // List.equals() chỉ true khi cùng phần tử VÀ cùng thứ tự
        return uiList.equals(sortedList);
    }

    public boolean isSortedByNameZToA() {
        System.out.println("isSortedByNameZToA");
        List<String> uiList = getProductNames();
        System.out.println("arrange before");
        uiList.forEach(i -> {
            System.out.println(i);
        });
        // Tạo bản sao của uiList
        List<String> sortedList = new ArrayList<>(uiList);

        Collections.sort(sortedList, String.CASE_INSENSITIVE_ORDER.reversed());
        System.out.println("arrange after");
        sortedList.forEach(i -> {
            System.out.println(i);
        });
        // Dùng equal để so sánh hai mảng có cùng phần tử và cùng thứ tự (kiểu dữ liệu nào cũng được)
        // List.equals() chỉ true khi cùng phần tử VÀ cùng thứ tự
        return uiList.equals(sortedList);
    }

    public List<Double> getProductPrice() {
        return BaseTest.getElements(listProductPrices).stream().map(e -> Double.valueOf(e.getText().replace("$", ""))).toList();
    }

    public boolean isSortedPriceHighToLow() {
        // Dữ liệu sau khi lọc thứ tụ khác ban đầu
        List<Double> uiList = getProductPrice();
        System.out.println("isSortedPriceHighToLow");
        System.out.println("arrange before");
        uiList.forEach(i -> {
            System.out.println("double" + i);
        });
        // Tạo bản sao của uiList
        List<Double> sortedList = new ArrayList<>(uiList);
        sortedList.sort(Comparator.reverseOrder());
        System.out.println("arrange after");
        sortedList.forEach(i -> {
            System.out.println("double" + i);
        });
        // List.equals() chỉ true khi cùng phần tử VÀ cùng thứ tự
        // Dùng equal để so sánh hai mảng có cùng phần tử và cùng thứ tự (kiểu dữ liệu nào cũng được)
        return uiList.equals(sortedList);
    }

    public boolean isSortedPriceLowToHigh() {
        // Dữ liệu sau khi lọc thứ tụ khác ban đầu
        List<Double> uiList = getProductPrice();
        System.out.println("isSortedPriceLowToHigh");
        System.out.println("arrange before");
        uiList.forEach(i -> {
            System.out.println(i);
        });
        // Tạo bản sao của uiList
        List<Double> sortedList = new ArrayList<>(uiList);
        // Sort từ thấp đến cao
        sortedList.sort(Comparator.naturalOrder());
        System.out.println("arrange after");
        sortedList.forEach(i -> {
            System.out.println(i);
        });
        // List.equals() chỉ true khi cùng phần tử VÀ cùng thứ tự
        // Dùng equal để so sánh hai mảng có cùng phần tử và cùng thứ tự (kiểu dữ liệu nào cũng được)
        return uiList.equals(sortedList);
    }

    // “Broken image không thể kiểm tra bằng isDisplayed.
    //Tôi lấy src và verify HTTP status code để đảm bảo image load từ backend.”
    public boolean verifyBrokenImages() {
        List<WebElement> images = BaseTest.getElements(listProductImages);
        int brokenCount = 0;

        for (WebElement img : images) {
            String imgSrc = img.getAttribute("src");

            if (imgSrc == null || imgSrc.isEmpty()) {
                System.out.println("❌ Image src is empty");
                brokenCount++;
                continue;
            }

            try {
                URL url = new URL(imgSrc);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("HEAD");
                connection.setConnectTimeout(3000);
                connection.setReadTimeout(3000);
                connection.connect();

                int statusCode = connection.getResponseCode();

                if (statusCode != 200) {
                    System.out.println("❌ Broken Image: " + imgSrc + " | Status: " + statusCode);
                    brokenCount++;
                } else {
                    System.out.println("✅ Valid Image: " + imgSrc);
                }

            } catch (Exception e) {
                System.out.println("❌ Exception for image: " + imgSrc);
                brokenCount++;
            }
        }
        return brokenCount == 0;
    }
//    public void openTwitter() {
//        WebElement twitter = BaseTest.getElementPresence(twitterButton);
//
//        ((JavascriptExecutor) BaseTest.driver)
//                .executeScript("arguments[0].scrollIntoView(true);", twitter);
//
//        ((JavascriptExecutor) BaseTest.driver)
//                .executeScript("arguments[0].click();", twitter);
//    }

    public void openTwitter(){
        BaseTest.openLinkPresenceJS(twitterButton);
    }
    public void openFacebook(){
        BaseTest.openLinkPresenceJS(facebookButton);
    }
    public void openLinkedin(){
        BaseTest.openLinkPresenceJS(linkedinButton);
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


//public boolean verifyLinkAbout() {
//    openMenu();
//    // presenceOfElementLocated
//    // Chỉ cần element trong DOM
//    // không cần visible
//    // không cần click able
//    // Vì sao dùng presence thay vì visibility hay clickable
//    // visibility fail do animation trượt
//    // clickable fail do overly
//    // với menu kiểu react + animation, element:
//    // luôn tồn tại trong DOM
//    // chị bị translate/ opacity -> presenceOf là lựa chọn đúng
//    WebElement aboutLink = BaseTest.wait.until(
//            ExpectedConditions.presenceOfElementLocated(
//                    By.id("about_sidebar_link")
//            )
//    );
//
//    // Dùng jsClick thay cho click()
//    // gọi click trực tiếp trên DOM
//    // không quan tâm: overlay, animation, z-index
//    // click giống như user click thật ở browser
//    ((JavascriptExecutor) BaseTest.driver)
//            .executeScript("arguments[0].click();", aboutLink);
//
//    // chờ đơị đến khi url chuyển sang saucelabs.com
//    BaseTest.wait.until(
//            ExpectedConditions.urlContains("saucelabs.com")
//    );
//
//    return BaseTest.driver.getCurrentUrl().contains("saucelabs.com");
//}

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
