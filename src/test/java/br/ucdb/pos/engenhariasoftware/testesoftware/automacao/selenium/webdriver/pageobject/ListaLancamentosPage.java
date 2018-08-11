package br.ucdb.pos.engenhariasoftware.testesoftware.automacao.selenium.webdriver.pageobject;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class ListaLancamentosPage {

    private WebDriver driver;

    public ListaLancamentosPage(final WebDriver driver){
        this.driver = driver;
    }

    public void acessa(){
        driver.get("http://localhost:8080/lancamentos");
    }

    public void novoLancamento(){
        driver.findElement(By.id("novoLancamento")).click();
    }

    public void editaLancamento(){
        driver.findElement(By.xpath("//table[@id='tabelaLancamentos']/tbody/tr[1]//a[contains(@id,'editar')]")).click();
    }

    public Integer totalRegistros(){
        String totalLista = driver.findElement(By.xpath("//span[@id='totalRegistros']")).getText();
        Integer total = Integer.valueOf(totalLista);
        return total;
    }

    public boolean existeLancamento(final String descricaoLancamento, final BigDecimal valorLancamento,
                                    LocalDateTime dataHora, TipoLancamento tipo, Categoria categoria){

        DateTimeFormatter formatoDataLancamento = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        String lancamentos = driver.getPageSource();
        DecimalFormat df = new DecimalFormat("#,###,##0.00");
        return (lancamentos.contains(descricaoLancamento) &&
                lancamentos.contains(df.format(valorLancamento)) &&
                lancamentos.contains(dataHora.format(formatoDataLancamento)) &&
                lancamentos.contains(tipo.getDescricao()) &&
                lancamentos.contains(categoria.getDescricao()));
    }

    public Boolean validaLancamento(final String valor){
        WebElement busca = driver.findElement(By.xpath("//input[@id='itemBusca']"));
        busca.click();
        busca.sendKeys(valor);
        driver.findElement(By.xpath("//button[@id='bth-search']")).click();
        Integer total = totalRegistros();
        if(total > 0)
            return true;
        else
            return false;
    }

    public void removeLancamento(){
        WebElement remover = driver.findElement(By.xpath("//table[@id='tabelaLancamentos']/tbody/tr[1]//a[contains(@class,'delete')]"));
        remover.click();
    }

    public void acessaRelatorio(){
        WebElement relatorio = driver.findElement(By.xpath("//a[contains(@title,'Gráfico')]"));
        relatorio.click();
    }

    public void recarregarListagem(){
        WebElement recarregar = driver.findElement(By.xpath("//a[contains(@id,'recarregar')]"));
        recarregar.click();
    }

    public void salvarLancamento(){
        WebElement salvar = driver.findElement(By.xpath("//button[contains(@id,'btnSalvar')]"));
        salvar.click();
    }

    public void cancelaLancamento(){
        WebElement cancelar = driver.findElement(By.xpath("//a[contains(@id,'cancelar')]"));
        cancelar.click();
    }
}

