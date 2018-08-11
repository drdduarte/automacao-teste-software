package br.ucdb.pos.engenhariasoftware.testesoftware.automacao.selenium.webdriver;

import br.ucdb.pos.engenhariasoftware.testesoftware.automacao.selenium.webdriver.pageobject.Categoria;
import br.ucdb.pos.engenhariasoftware.testesoftware.automacao.selenium.webdriver.pageobject.LancamentoPage;
import br.ucdb.pos.engenhariasoftware.testesoftware.automacao.selenium.webdriver.pageobject.ListaLancamentosPage;
import br.ucdb.pos.engenhariasoftware.testesoftware.automacao.selenium.webdriver.pageobject.TipoLancamento;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Random;

import static org.testng.Assert.assertTrue;

/**
 * @author danielduarte
 * */
public class LancamentoTest {

    private WebDriver driver;
    private ListaLancamentosPage listaLancamentosPage;
    private LancamentoPage lancamentoPage;

    @BeforeClass
    private void inicialliza() {
        boolean windows = System.getProperty("os.name").toUpperCase().contains("WIN");
        System.setProperty("webdriver.gecko.driver",
                System.getProperty("user.dir") + "/src/test/resources/drivers/" +
                        "/geckodriver" + (windows ? ".exe" : ""));
        driver = new FirefoxDriver();
        listaLancamentosPage = new ListaLancamentosPage(driver);
        lancamentoPage = new LancamentoPage(driver);
    }

    public String cadastraLancamento(){
        listaLancamentosPage.acessa();
        Integer totalRegistros = listaLancamentosPage.totalRegistros();
        listaLancamentosPage.novoLancamento();

        LocalDateTime dataHoraBase = LocalDateTime.now();
        // Data randomica de 1 a 28
        LocalDateTime dataHora = LocalDateTime.of(
                dataHoraBase.getYear(),
                dataHoraBase.getMonth().getValue(),
                this.getDiaMes(),
                0,
                0,
                0);

        DateTimeFormatter formatoLancamento = DateTimeFormatter.ofPattern("dd/MM/YYYY");
        final BigDecimal valor = getValorLancamento();
        final String descricaoLancamento;
        if(totalRegistros%2==0){
            descricaoLancamento = "Lançando Entrada " + dataHora.format(formatoLancamento);
            lancamentoPage.cria(descricaoLancamento, valor, dataHora, TipoLancamento.ENTRADA, Categoria.SALARIO);
        }
        else{
            descricaoLancamento = "Lançando Saída " + dataHora.format(formatoLancamento);
            lancamentoPage.cria(descricaoLancamento, valor, dataHora, TipoLancamento.SAIDA, Categoria.CARRO);
        }
        return descricaoLancamento;
    }

    /**
     * FLUXO 01
     * Cria Lancamento
     * */
    @Test
    public void executaFluxo01(){
        String descricaoLancamento = cadastraLancamento();
        assertTrue(listaLancamentosPage.validaLancamento(descricaoLancamento),"Registro não cadastado/atualizado!");
    }

    /**
     * FLUXO 02
     * Edita Lancamento
     * */
    @Test
    public void executaFluxo02(){
        listaLancamentosPage.acessa();
        listaLancamentosPage.editaLancamento();

        LocalDateTime dataHoraBase = LocalDateTime.now();
        // Data randomica de 1 a 28
        LocalDateTime dataHora = LocalDateTime.of(
                dataHoraBase.getYear(),
                dataHoraBase.getMonth().getValue(),
                this.getDiaMes(),
                0,
                0,
                0);

        DateTimeFormatter formatoLancamento = DateTimeFormatter.ofPattern("dd/MM/YYYY");

        lancamentoPage.editar(dataHora.format(formatoLancamento));
        assertTrue(listaLancamentosPage.validaLancamento(" [EDITADO em " + dataHora.format(formatoLancamento) + "]"),"Registro não cadastado/atualizado!");
    }

    /**
     * FLUXO 03
     * Remove Lancamento
     * */
    @Test
    public void executaFluxo03(){
        String valorInserido = cadastraLancamento();
        Boolean validaInsercao = listaLancamentosPage.validaLancamento(valorInserido);
        assertTrue(validaInsercao,"Registro não cadastado/atualizado!");
        if(validaInsercao){
            listaLancamentosPage.removeLancamento();
            listaLancamentosPage.validaLancamento(valorInserido);
            Boolean validaRemocao = false;
            Integer totalRegistros = listaLancamentosPage.totalRegistros();
            if(totalRegistros==0){
                validaRemocao = true;
            }
            assertTrue(validaRemocao,"Registro não removido");
            // Professor, aqui esta ocorrendo uma falha, devido ao possível BUG no código do projeto test-software
            // O mesmo quando removemos um registro, depois realizamos uma busca (para validar a remocao) e não retorna nenhum registro,
            // Não esta atualizando o Total de Registros na Tela, fica exibindo o total anterior.
            // E isso comprometeu a forma como estou fazendo meu teste.

        }
    }

    /**
     * FLUXO 04
     * Acessa Relatorio
     * */
    @Test
    public void executaFluxo04(){
        String valorInserido = cadastraLancamento();
        Boolean validaInsercao = listaLancamentosPage.validaLancamento(valorInserido);
        assertTrue(validaInsercao,"Registro não cadastado/atualizado!");
        listaLancamentosPage.acessaRelatorio();
    }

    /**
     * FLUXO 05
     * Acessa Novo Lancamento, Cancela e Recarrega
     * */
    @Test
    public void executaFluxo05(){
        listaLancamentosPage.acessa();
        listaLancamentosPage.novoLancamento();
        listaLancamentosPage.salvarLancamento();
        listaLancamentosPage.cancelaLancamento();
        listaLancamentosPage.recarregarListagem();
    }

    private int getDiaMes(){
        Random rand = new Random();
        int n = rand.nextInt(27)+1;
        return n;
    }

    @AfterClass
    private void finaliza(){
        driver.quit();
    }

    private BigDecimal getValorLancamento() {

        boolean  aplicaVariante = (System.currentTimeMillis() % 3) == 0;
        int fator = 10;
        long mim = 30;
        long max = 900;
        if(aplicaVariante){
            mim /= fator;
            max /= fator;
        }
        return new BigDecimal(( 1 + (Math.random() * (max - mim)))).setScale(2, RoundingMode.HALF_DOWN);
    }
    
}