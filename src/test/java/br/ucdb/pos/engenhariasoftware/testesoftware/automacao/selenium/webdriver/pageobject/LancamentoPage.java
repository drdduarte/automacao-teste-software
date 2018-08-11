package br.ucdb.pos.engenhariasoftware.testesoftware.automacao.selenium.webdriver.pageobject;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
/**
 * @author danielduarte
 * */
public class LancamentoPage {

    private WebDriver driver;

    public LancamentoPage(final WebDriver driver){
        this.driver = driver;
    }

    public void cria(final String descricaoLancamento, final BigDecimal valorLancamento, LocalDateTime dataHora, TipoLancamento tipo, Categoria categoria){

        WebElement descricao = driver.findElement(By.id("descricao"));
        descricao.click();
        descricao.sendKeys(descricaoLancamento);

        DateTimeFormatter formatoDataLancamento = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        WebElement dataLancamento = driver.findElement(By.name("dataLancamento"));
        dataLancamento.sendKeys(dataHora.format(formatoDataLancamento));

        if(tipo == TipoLancamento.SAIDA) {
            driver.findElement(By.id("tipoLancamento2")).click(); // informa lançamento: SAÍDA
        }else{
            driver.findElement(By.id("tipoLancamento1")).click(); // informa lançamento: ENTRADA
        }

        WebElement valor = driver.findElement(By.id("valor"));
        //driver.findElement(By.id("tipoLancamento2")).click();
        valor.sendKeys(String.valueOf(valorLancamento));

        driver.findElement(By.id("categoria")).sendKeys(categoria.getDescricao());
        //new Select(driver.findElement(By.id("categoria"))).selectByVisibleText(categoria.getDescricao());

        driver.findElement(By.id("btnSalvar")).click();
    }

    public void editar(final String dataEdicao) {

        WebElement descricao = driver.findElement(By.id("descricao"));
        descricao.click();
        String descricaoAtual = " [EDITADO em " + dataEdicao + "]";
        descricao.sendKeys(String.valueOf(descricaoAtual));

        driver.findElement(By.id("btnSalvar")).click();
    }
}


