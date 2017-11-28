package ufjf.dcc171;

import java.util.ArrayList;
import java.util.List;
import javax.swing.ListModel;
import javax.swing.event.ListDataListener;

public class MesasListModel implements ListModel<Mesas>{
    private final List<Mesas> mesas;
    private final List<ListDataListener> dataListeners;

    public MesasListModel(List<Mesas> mesas) {
        this.mesas = mesas;
        this.dataListeners =  new ArrayList<>();
    }

    @Override
    public int getSize() {
        return mesas.size();
    }

    @Override
    public Mesas getElementAt(int index) {
        return mesas.get(index);
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
