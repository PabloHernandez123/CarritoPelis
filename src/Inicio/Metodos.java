
package Inicio;

import Clientess.Clientes;
import java.io.Serializable;
import java.util.ArrayList;

public class Metodos {
    
    private ArrayList<Object> a = new ArrayList<Object>();
    
    public Metodos(){}
    
    public Metodos(ArrayList<Object> a){
        this.a = a;
    }
    
    public void agregarCliente(Clientes c){
        this.a.add(c);
    }

    public void modificarCliente(int i, Clientes c){
        this.a.set(i, c);
    }
    
    public void eliminarCliente(int i){
        this.a.remove(i);
    }
    
    public Clientes obtenerCliente(int i){
        return (Clientes)a.get(i);
    }
    
    public int cantidadID(){
        return this.a.size();
    }
    
    public int buscarID(int codigo){
        for(int i = 0; i < cantidadID(); i++){
            if(codigo == obtenerCliente(i).getId())return i;
        }
        return -1;
    }
    
}
