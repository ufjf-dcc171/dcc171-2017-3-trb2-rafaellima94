package ufjf.dcc171;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.HeadlessException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.TimeZone;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import org.json.JSONObject;

public class ControlePedidos extends JFrame {
    private List<Historico> dtHistorico = new ArrayList<>();
    private List<Mesas> dtMesas;
    private JList<Mesas> lstMesas = new JList<>(new DefaultListModel<>());
    private List<Itens> dtItens;
    private JList<Itens> lstItens = new JList<>(new DefaultListModel<>());
    
    private JButton btnHistorico = new JButton("Ver Histórico");
    
    private JLabel lblHoraAbertura = new JLabel();
    
    private JLabel lblItem = new JLabel("Nome do item");
    private JTextField txtItem = new JTextField(10);
    private JLabel lblQuantidade = new JLabel("Quantidade");
    private JTextField txtQuantidade = new JTextField(10);
    private JLabel lblValor = new JLabel("Valor");
    private JTextField txtValor = new JTextField(10);
    private JLabel lblItens = new JLabel("Itens");
    private JScrollPane scrollItens = new JScrollPane();
    
    private JButton btnApagarItem = new JButton("Apagar item");
    private JButton btnSalvarItem = new JButton("Salvar item");
    private JButton btnNovoItem = new JButton("Novo item");
    private JButton btnFecharPedido = new JButton("Fechar pedido");
    
    private JButton btnAbrirPedido = new JButton("Novo pedido");
            
    public ControlePedidos(List<Mesas> dtMesas) throws HeadlessException{
        super("Controle de Pedidos");
        
        this.dtMesas = dtMesas;
        lstMesas.setModel(new MesasListModel(dtMesas));
        JPanel painel = new JPanel(new GridLayout(1,2));
        painel.add(new JScrollPane(lstMesas));
        
        JPanel controleHistorico = new JPanel(new GridLayout(1,1));
        controleHistorico.add(btnHistorico);
        add(controleHistorico, BorderLayout.NORTH);
        
        JPanel dadosPedidos = new JPanel(new GridLayout(7,1));
        dadosPedidos.add(lblHoraAbertura);
        JPanel painelItens = new JPanel(new GridLayout(2,1));
        painelItens.add(lblItem);
        painelItens.add(txtItem);
        dadosPedidos.add(painelItens);
        JPanel painelQuantidade = new JPanel(new GridLayout(2,1));
        painelQuantidade.add(lblQuantidade);
        painelQuantidade.add(txtQuantidade);
        dadosPedidos.add(painelQuantidade);
        JPanel painelValor = new JPanel(new GridLayout(2,1));
        painelValor.add(lblValor);
        painelValor.add(txtValor);
        dadosPedidos.add(painelValor);
        dadosPedidos.add(lblItens);
        dadosPedidos.add(scrollItens);
        JPanel controleItens = new JPanel(new GridLayout(1,3));
        controleItens.add(btnNovoItem);
        controleItens.add(btnSalvarItem);
        btnSalvarItem.setEnabled(false);
        controleItens.add(btnApagarItem);
        btnApagarItem.setEnabled(false);
        controleItens.add(btnFecharPedido);
        dadosPedidos.add(controleItens);
        painel.add(dadosPedidos);
        add(painel, BorderLayout.CENTER);
        
        JPanel controlePedidos = new JPanel(new GridLayout(1,1));
        controlePedidos.add(btnAbrirPedido);
        add(controlePedidos, BorderLayout.SOUTH);
        
        dadosPedidos.setVisible(false);
        controlePedidos.setVisible(false);
        
        lstMesas.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                Mesas selMesa = lstMesas.getSelectedValue();
                btnSalvarItem.setEnabled(false);
                btnApagarItem.setEnabled(false);
                lblHoraAbertura.setText("");
                
                if(selMesa.getPedido() == null || selMesa.getPedido().getHoraAbertura() == null) {
                    dadosPedidos.setVisible(false);
                    controlePedidos.setVisible(true);
                } else if(selMesa.getPedido().getItens() == null) {
                    dadosPedidos.setVisible(true);
                    controlePedidos.setVisible(false);
                    lblHoraAbertura.setText(selMesa.getPedido().getHoraAbertura());
                    
                    dtItens = new ArrayList<>();
                    lstItens.setModel(new ItensListModel(dtItens));
                    scrollItens.setViewportView(lstItens);
                } else {
                    dadosPedidos.setVisible(true);
                    controlePedidos.setVisible(false);
                    lblHoraAbertura.setText(selMesa.getPedido().getHoraAbertura());

                    dtItens = selMesa.getPedido().getItens();
                    lstItens.setModel(new ItensListModel(dtItens));
                    scrollItens.setViewportView(lstItens);
                }
            }
        });
        
        lstItens.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                btnSalvarItem.setEnabled(true);
                btnApagarItem.setEnabled(true);
            }
        });
        
        btnAbrirPedido.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Mesas selMesa = lstMesas.getSelectedValue();
                Pedidos pedido = new Pedidos(getCurrentTime());
                selMesa.setPedido(pedido);
                dadosPedidos.setVisible(true);
                controlePedidos.setVisible(false);
                dtItens = new ArrayList<>();
                lstItens.setModel(new ItensListModel(dtItens));
                scrollItens.setViewportView(lstItens);
                lblHoraAbertura.setText(selMesa.getPedido().getHoraAbertura());
            }
        });
        
        btnNovoItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(!txtItem.getText().isEmpty() && !txtQuantidade.getText().isEmpty() && !txtValor.getText().isEmpty()) {
                    Integer qtd = Integer.parseInt(txtQuantidade.getText());
                    double val = Double.parseDouble(txtValor.getText());
                    
                    Mesas selMesa = lstMesas.getSelectedValue();
                    Itens item = new Itens(txtItem.getText(), qtd, val);
                    dtItens.add(item);
                    selMesa.getPedido().setItens(dtItens);
                    txtItem.setText("");
                    txtQuantidade.setText("");
                    txtValor.setText("");
                    txtItem.requestFocus();
                    lstItens.clearSelection();
                    lstItens.updateUI();
                }
            }
        });
        
        btnSalvarItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(!lstItens.isSelectionEmpty() && (!txtItem.getText().isEmpty() || !txtQuantidade.getText().isEmpty() || !txtValor.getText().isEmpty())){
                    lstItens.getSelectedValue().setNome(txtItem.getText());
                    lstItens.getSelectedValue().setQuantidade(Integer.parseInt(txtQuantidade.getText()));
                    lstItens.getSelectedValue().setValor(Double.parseDouble(txtValor.getText()));
                    lstItens.updateUI();
                }
            }
        });
        
        btnApagarItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(!lstItens.isSelectionEmpty()){
                    dtItens.remove(lstItens.getSelectedValue());
                    lstItens.clearSelection();
                    lstItens.updateUI();
                }
            }
        });
        
        btnFecharPedido.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Mesas selMesa = lstMesas.getSelectedValue();
                selMesa.getPedido().setHoraFechamento(getCurrentTime());
                double valTotal = 0;
                
                StringBuilder s = new StringBuilder("<html><body><h1>Pedido fechado com sucesso!</h1>");
                StringBuilder sb = new StringBuilder("Mesa: ");
                sb.append(selMesa.getNum());
                sb.append(System.getProperty("line.separator"));
                sb.append("Horário de abertura: ");
                sb.append(selMesa.getPedido().getHoraAbertura());
                sb.append(System.getProperty("line.separator"));
                sb.append("Horário de Fechamento: ");
                sb.append(selMesa.getPedido().getHoraFechamento());
                sb.append(System.getProperty("line.separator"));
                for(Itens item : selMesa.getPedido().getItens()) {
                    valTotal += item.getValor()*item.getQuantidade();
                    s.append("<p>");
                    s.append(item);
                    s.append("</p>");
                    sb.append(item);
                    sb.append(" | ");
                }
                s.append("</br><p>Valor total: R$ ");
                s.append(valTotal);
                s.append("</p></body></html>");
                
                JOptionPane.showMessageDialog(null, s, "Fechamento", JOptionPane.INFORMATION_MESSAGE);
                
                sb.append(" Valor Total = R$ ");
                sb.append(valTotal);
                
                Historico historico = new Historico(sb.toString());
                dtHistorico.add(historico);
                
                String jsonstring = "{\"item\":[";
                for(Itens item : selMesa.getPedido().getItens()) {
                    String nome = item.getNome();
                    Integer qtd = item.getQuantidade();
                    Double valor = item.getValor();
                    jsonstring += "{\"nome\":\"" + nome + "\", \"qtd\":" + qtd + ", \"valor\":" + valor + "},";
                }
                
                jsonstring += "],\"data\":\"" + getCurrentTime() + "\"},";
                
                new FileManager().saveDataFile(selMesa.getNum(), new JSONObject(jsonstring));
                
                selMesa.setPedido(new Pedidos());
                dadosPedidos.setVisible(false);
                controlePedidos.setVisible(true);
            }
        });
        
        btnHistorico.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                StringBuilder s = new StringBuilder("Histórico");
                s.append(System.getProperty("line.separator"));
                
                for(Historico historico : dtHistorico) {System.out.println(historico.getInformacoes());
                    s.append(historico.getInformacoes());
                s.append(System.getProperty("line.separator"));
                }
                
                JOptionPane.showMessageDialog(null, s, "Fechamento", JOptionPane.INFORMATION_MESSAGE);
            }
        });
    }
    
    private String getCurrentTime() {
        Calendar cal = new GregorianCalendar(TimeZone.getTimeZone("Brazil/East"));
        return cal.get(Calendar.DAY_OF_MONTH)+"/"+cal.get(Calendar.MONTH)+"/"+cal.get(Calendar.YEAR)+" "+cal.get(Calendar.HOUR_OF_DAY)+":"+cal.get(Calendar.MINUTE);
    }
}
