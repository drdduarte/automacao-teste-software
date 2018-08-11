package br.ucdb.pos.engenhariasoftware.testesoftware.automacao.selenium.webdriver.pageobject;

public enum Categoria {
    ALIMENTACAO("Alimentação"),
    SALARIO("Salário"),
    LAZER("Lazer"),
    TELEFONE_INTERNET("Telefone & Internet"),
    CARRO("Carro"),
    EMPRESTIMO("Empréstimo"),
    INVESTIMENTOS("Investimentos"),
    OUTROS("Outros");

    private Categoria(final String descricao){
        this.descricao = descricao;
    }

    private String descricao;

    public String getDescricao() {
        return descricao;
    }
}
