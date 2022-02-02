package SwagLabs;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Stream;

import static org.openqa.selenium.support.locators.RelativeLocator.*;
import static org.openqa.selenium.support.locators.RelativeLocator.with;

public class SwagLabTest {

    static WebDriver driver;

    public static void main(String[] args) throws InterruptedException {

        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.get("https://www.saucedemo.com/");
        driver.findElement(By.id("user-name")).sendKeys("standard_user");
        driver.findElement(By.id("password")).sendKeys("secret_sauce");
        driver.findElement(By.id("login-button")).click();

        List<WebElement> pricesList = driver.findElements(By.xpath("//div[@class='inventory_item_price']"));
        double maxPrice = getMaxValueItem(pricesList);
        By locator = By.xpath("//div[text()='"+ maxPrice+ "']");
        Thread.sleep(3000);
        driver.findElement(with(By.xpath("//button[text()='Add to cart']")).toRightOf(locator)).click();

    }

    public static String removeFirstCharacter(String str){
        StringBuilder sb = new StringBuilder(str);
        sb.deleteCharAt(0);
        return sb.toString();
    }

    public static double getMaxValueItem(List<WebElement> elements){
        List<Double> values = new ArrayList<Double>();
        for(WebElement e : elements){
            String text = e.getText();
            text = removeFirstCharacter(text);
            values.add(Double.parseDouble(text));
        }
        Double var = values.stream().max(Double::compare).get();
        return var;
    }
}