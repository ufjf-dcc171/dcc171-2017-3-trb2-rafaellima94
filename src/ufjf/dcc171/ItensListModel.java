package ufjf.dcc171;

import java.util.ArrayList;
import java.util.List;
import javax.swing.ListModel;
import javax.swing.event.ListDataListener;

public class ItensListModel implements ListModel<Itens>{
    private final List<Itens> itens;
    private final List<ListDataListener> dataListeners;

    public ItensListModel(List<Itens> itens) {
        this.itens = itens;
        this.dataListeners =  new ArrayList<>();
    }

    @Override
    public int getSize() {
        return itens.size();
    }

    @Override
    public Itens getElementAt(int index) {
        return itens.get(index);
    }

    @Override
    public void addListDataListener(ListDataListener l) {
        this.dataListeners.add(l);
    }

    @Override
    public void removeListDataListener(ListDataListener l) {
        this.dataListeners.remove(l);
    }
}