
package Inicio;

import Peliculas.Peliculas;
import java.util.ArrayList;

public class MetodoPelicula {
    
    private ArrayList<Object> a = new ArrayList<Object>();
    
    public MetodoPelicula(){}
    
    public MetodoPelicula(ArrayList<Object> a){
        this.a = a;
    }
    
    public void agregarPelicula(Peliculas p){
        this.a.add(p);
    }

    public void modificarPelicula(int i, Peliculas p){
        this.a.set(i, p);
    }
    
    public void eliminarPelicula(int i){
        this.a.remove(i);
    }
    
    public Peliculas obtenerPelicula(int i){
        return (Peliculas)a.get(i);
    }
    
    public int cantidadIDPel(){
        return this.a.size();
    }
    
    public int buscarIDPel(int codigo){
        for(int i = 0; i < cantidadIDPel(); i++){
            if(codigo == obtenerPelicula(i).getIdpeli())return i;
        }
        return -1;
    }
    
}
