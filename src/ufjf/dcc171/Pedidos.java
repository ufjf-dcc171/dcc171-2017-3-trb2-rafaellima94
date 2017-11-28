package ufjf.dcc171;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Pedidos {
    private String horaAbertura;
    private String horaFechamento;
    private List<Itens> itens;
    
    public Pedidos(String horaAbertura) {
        this.horaAbertura = horaAbertura;
    }
    
    public Pedidos() {
    }

    public String getHoraAbertura() {
        return horaAbertura;
    }

    public void setHoraAbertura(String horaAbertura) {
        this.horaAbertura = horaAbertura;
    }

    public String getHoraFechamento() {
        return horaFechamento;
    }

    public void setHoraFechamento(String horaFechamento) {
        this.horaFechamento = horaFechamento;
    }

    public List<Itens> getItens() {
        return itens;
    }

    public void setItens(List<Itens> itens) {
        this.itens = itens;
    }

    @Override
    public String toString() {
        return "Hora de aberura do pedido: " + horaAbertura;
    }
}
