package br.ucdb.pos.engenhariasoftware.testesoftware.automacao.selenium.webdriver;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

public class PrimeiroExemplo {

    public static void main(String[] args) throws InterruptedException {

        // seta o gecko driver no classpath do prorama
        boolean windows = System.getProperty("os.name").toUpperCase().contains("WIN");
        System.setProperty("webdriver.gecko.driver",
                System.getProperty("user.dir") + "/src/test/resources/drivers/" +
                        "/geckodriver" + (windows ? ".exe" : ""));

        // abre o firefox
        WebDriver driver = new FirefoxDriver();

        // acessa o site do Selenium
        driver.get("http://www.seleniumhq.org");

        //obtem e clica no link da página de download
        driver.findElement(By.id("menu_download")).click();

        Thread.sleep(1000);

        // fecha todas as janelas do navegador e finaliza a sessão do WebDriver
        driver.quit();
    }
}


