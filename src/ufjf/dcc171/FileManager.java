package ufjf.dcc171;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.json.JSONArray;
import org.json.JSONObject;

public class FileManager {    
    public FileManager() {
        
    }
    
    private JSONObject readDataFile() {
        BufferedReader br = null;
        String jsondata = "";
        try {
            br = new BufferedReader(new FileReader("C:\\Users\\Rafael\\Documents\\NetBeansProjects\\TrabalhoDCC171\\src\\ufjf\\dcc171\\JSONdata.txt"));
            StringBuilder sb = new StringBuilder();
            String line = br.readLine();

            while (line != null) {
                sb.append(line);
                sb.append(System.lineSeparator());
                line = br.readLine();
            }
            jsondata = sb.toString();
        } catch (FileNotFoundException ex) {
            Logger.getLogger(TrabalhoDCC171.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(TrabalhoDCC171.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                br.close();
            } catch (IOException ex) {
                Logger.getLogger(TrabalhoDCC171.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        		
	JSONObject jsonobj = new JSONObject(jsondata);
        return jsonobj;
    }

    public List<Mesas> setMesasData() {
        List<Mesas> mesas = new ArrayList<Mesas>();
        
        for(int i = 1; i < 9; i++) {
            mesas.add(new Mesas(i));
        }
        
        JSONArray jsonfiledata = readDataFile().getJSONArray("mesas");
        
        for(int i = 0; i < jsonfiledata.length(); i++) {
            if(jsonfiledata.getJSONObject(i).getJSONArray("pedido").length() > 0) {
                JSONArray jsonpedido = jsonfiledata.getJSONObject(i).getJSONArray("pedido");
                
                List<Itens> itens = new ArrayList<Itens>();
                JSONObject jsonitem = jsonpedido.getJSONObject(jsonpedido.length() - 1);
                String data = jsonitem.getString("data");
                JSONArray jsonitens = jsonitem.getJSONArray("item");

                for(int k = 0; k < jsonitens.length(); k++) {
                    String nome = jsonitens.getJSONObject(k).getString("nome");
                    Integer qtd = jsonitens.getJSONObject(k).getInt("qtd");
                    Double valor = jsonitens.getJSONObject(k).getDouble("valor");
                    itens.add(new Itens(nome, qtd, valor));
                }

                Pedidos pedido = new Pedidos(data);
                pedido.setItens(itens);
                mesas.get(i).setPedido(pedido);
            }
        }
        
        return mesas;
    }
    
    public void saveDataFile(Integer numMesa, JSONObject jsonstring) {
        JSONObject jsonfiledata = readDataFile();
        JSONArray jsonfilestring = jsonfiledata.getJSONArray("mesas").getJSONObject(numMesa - 1).getJSONArray("pedido").put(jsonstring);
        
        PrintWriter out = null;
        try {
            out = new PrintWriter("C:\\Users\\Rafael\\Documents\\NetBeansProjects\\TrabalhoDCC171\\src\\ufjf\\dcc171\\JSONdata.txt");
            out.println(jsonfiledata);
        } catch (FileNotFoundException ex) {
            Logger.getLogger(FileManager.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            out.close();
        }
    }
}
