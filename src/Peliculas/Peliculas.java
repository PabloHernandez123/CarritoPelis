
package Peliculas;


public class Peliculas {
    private int idpeli;
    private String titulo;
    private String director;
    private String actor;
    private String clasificacion;
    
    public Peliculas(){ 
    }
    
    public Peliculas(int idpeli, String titulo,String director, String actor, String clasificacion){
        this.idpeli=idpeli;
        this.titulo=titulo;
        this.director=director;
        this.actor=actor;
        this.clasificacion=clasificacion;
    }

  
    public int getIdpeli() {
        return idpeli;
    }

    public void setIdpeli(int idpeli) {
        this.idpeli = idpeli;
    }

    
    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    
    public String getDirector() {
        return director;
    }
    
    public void setDirector(String director) {
        this.director = director;
    }

    public String getActor() {
        return actor;
    }
    public void setActor(String actor) {
        this.actor = actor;
    }
    public String getClasificacion() {
        return clasificacion;
    }
    public void setClasificacion(String clasificacion) {
        this.clasificacion = clasificacion;
    }
    
}
