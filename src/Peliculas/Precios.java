
package Peliculas;


public class Precios  extends Peliculas{
     private int cantidad;
     private double precioventa;
     private double preciorenta;
     private int iva;
     private int descuento;
     private double total;
     private double subtotal;
     private double cambio;
     private double pago;
    public Precios(){
       
    }
    public int getCantidad() {
        return cantidad;
    }

   
    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }
    
    public double getPrecioventa() {
        return precioventa;
    }

    public void setPrecioventa(double precioventa) {
        this.precioventa = precioventa;
    }

    public double getPreciorenta() {
        return preciorenta;
    }
    public void setPreciorenta(double preciorenta) {
        this.preciorenta = preciorenta;
    }

    public int getIva() {
        return iva;
    }

    
    public void setIva(int iva) {
        this.iva = iva;
    }

   
    public int getDescuento() {
        return descuento;
    }

    
    public void setDescuento(int descuento) {
        this.descuento = descuento;
    }

    
    public double getTotal() {
        return total;
    }

   
    public void setTotal(double total) {
        this.total = total;
    }

   
    public double getSubtotal() {
        return subtotal;
    }

    public void setSubtotal(double subtotal) {
        this.subtotal = subtotal;
    }

    
    public double getCambio() {
        return cambio;
    }

    
    public void setCambio(double cambio) {
        this.cambio = cambio;
    }

    
    public double getPago() {
        return pago;
    }

       public void setPago(double pago) {
        this.pago = pago;
    }
       
       

}
