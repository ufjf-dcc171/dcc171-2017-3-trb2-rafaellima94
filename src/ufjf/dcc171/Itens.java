package ufjf.dcc171;

public class Itens {
    private String nome;
    private Integer quantidade;
    private double valor;
    
    public Itens(String nome, Integer quantidade, double valor) {
        this.nome = nome;
        this.quantidade = quantidade;
        this.valor = valor;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Integer getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(Integer quantidade) {
        this.quantidade = quantidade;
    }

    public double getValor() {
        return valor;
    }

    public void setValor(double valor) {
        this.valor = valor;
    }

    @Override
    public String toString() {
        return "Item: " + nome + " --> R$" + valor + " X " + quantidade;
    }
}
