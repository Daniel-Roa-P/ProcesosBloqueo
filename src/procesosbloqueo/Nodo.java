
package procesosbloqueo;

public class Nodo {
    
    private String llave;
    private int prioridad;
    private int rafaga;
    private int llegada;
    private int comienzo;
    private int retorno;
    private int finalizacion;
    private Nodo siguiente;
    
    Nodo(String llave, int prioridad, int rafaga, int llegada){
    
        this.llave = llave;
        this.prioridad = prioridad;
        this.rafaga = rafaga;
        this.llegada = llegada;
        this.siguiente = null;
        
    }

    public Nodo getSiguiente() {
        return siguiente;
    }

    public void setSiguiente(Nodo siguiente) {
        this.siguiente = siguiente;
    }

    public String getLlave() {
        return llave;
    }

    public void setLlave(String llave) {
        this.llave = llave;
    }

    public int getTareas() {
        return prioridad;
    }

    public void setTareas(int tareas) {
        this.prioridad = tareas;
    }

    public int getPrioridad() {
        return prioridad;
    }

    public void setPrioridad(int prioridad) {
        this.prioridad = prioridad;
    }

    public int getRafaga() {
        return rafaga;
    }

    public void setRafaga(int rafaga) {
        this.rafaga = rafaga;
    }
    
    
    
}
