package ufjf.dcc171;

import java.util.List;
import javax.swing.JFrame;

public class TrabalhoDCC171 {
    public static void main(String[] args) {
        List<Mesas> dados = new FileManager().setMesasData();
        ControlePedidos controle = new ControlePedidos(dados);
        controle.setSize(900, 600);
        controle.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        controle.setLocationRelativeTo(null);
        controle.setVisible(true);
    }
}