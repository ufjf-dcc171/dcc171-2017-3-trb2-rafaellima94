package ufjf.dcc171;

import java.util.List;

public class Mesas {
    private Integer num;
    private Pedidos pedido;
    
    public Mesas(Integer num) {
        this.num = num;
    }

    public Integer getNum() {
        return num;
    }

    public void setNum(Integer num) {
        this.num = num;
    }

    public Pedidos getPedido() {
        return pedido;
    }

    public void setPedido(Pedidos pedido) {
        this.pedido = pedido;
    }
    
    @Override
    public String toString() {
        return "Mesa: " + num;
    }
}
