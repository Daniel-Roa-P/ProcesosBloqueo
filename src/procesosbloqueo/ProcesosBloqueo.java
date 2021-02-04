
package procesosbloqueo;

import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.WindowConstants;
import javax.swing.JComboBox;
import javax.swing.JTextField;

public class ProcesosBloqueo extends JFrame implements Runnable ,ActionListener {

    JScrollPane scrollPane = new JScrollPane();
    JScrollPane scrollPane1 = new JScrollPane();
    
    JScrollPane scrollPane2 = new JScrollPane();
    JScrollPane scrollPane3 = new JScrollPane();
    
    JLabel semaforo = new JLabel();
    
    JLabel label1 = new JLabel("Nombre del proceso: ");
    JLabel label2 = new JLabel("Prioridad del proceso:");
    JLabel label3 = new JLabel("Proceso en ejecucion: Ninguno");
    JLabel label4 = new JLabel("Tiempo: ");
    
    JButton botonIngresar = new JButton("Ingresar proceso");
    JButton botonIniciar = new JButton("Iniciar ejecucion");
    JButton botonBloquear = new JButton("Bloquear proceso");
    
    JComboBox prioridad = new JComboBox();
    
    JTextField tfNombre = new JTextField("a");
    
    JTextField[][] tabla = new JTextField[40][8];
    JLabel[][] diagrama = new JLabel[41][101];
    
    ListaCircular cola = new ListaCircular();
    
    Nodo nodoEjecutado;
    
    int filas = 0, rafagaTemporal;
    int tiempoGlobal = 0;
    int coorX = 0, coorY = 1;
    
    Thread procesos;
    
    public static void main(String[] args) throws InterruptedException {

        ProcesosBloqueo pb = new ProcesosBloqueo(); 
        pb.setBounds(0, 0, 1200, 730);
        pb.setTitle("Procesos con bloqueo");
        pb.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        pb.setVisible(true);
        
    }

    ProcesosBloqueo(){
        
        Container c = getContentPane();
        c.setLayout(null);
        this.getContentPane().setBackground(Color.GRAY);
        
        c.add(label1);
        c.add(label2);
        c.add(label3);
        c.add(label4);
        c.add(semaforo);
        
        c.add(scrollPane1);
        c.add(scrollPane3);
        
        c.add(botonIngresar);
        c.add(botonIniciar);
        c.add(botonBloquear);
        
        c.add(prioridad);
        
        c.add(tfNombre);
        
        prioridad.addItem("1");
        prioridad.addItem("2");
        prioridad.addItem("3");
        prioridad.addItem("4");
        prioridad.addItem("5");
        
        label1.setBounds(800, 40, 300, 20);
        label2.setBounds(800, 70, 300, 20);
        label3.setBounds(800, 250, 300, 20);
        label4.setBounds(1020, 250, 300, 20);
        
        scrollPane.setBounds(50, 40, 2500, 2500);
        scrollPane.setPreferredSize(new Dimension(2500, 2500));  
        scrollPane.setBackground(Color.lightGray);
        
        scrollPane1.setBounds(50, 40, 700, 230);
        scrollPane1.setPreferredSize(new Dimension(1150, 400)); 
        scrollPane1.setBackground(Color.lightGray);
        
        scrollPane2.setBounds(50, 300, 2500, 2500);
        scrollPane2.setPreferredSize(new Dimension(2500, 2500));  
        scrollPane2.setBackground(Color.lightGray);
        
        scrollPane3.setBounds(50, 300, 1100, 350);
        scrollPane3.setPreferredSize(new Dimension(1150, 400)); 
        scrollPane3.setBackground(Color.lightGray);
        
        prioridad.setBounds(930, 70, 70, 20);
        tfNombre.setBounds(930, 40, 70, 20);
        
        botonIngresar.addActionListener(this);
        botonIngresar.setBounds(800, 100, 200, 40);
        botonIngresar.setBackground(Color.CYAN);
        
        botonIniciar.addActionListener(this);
        botonIniciar.setBounds(800, 150, 200, 40);
        botonIniciar.setBackground(Color.GREEN);
        
        botonBloquear.addActionListener(this);
        botonBloquear.setBounds(800, 200, 200, 40);
        botonBloquear.setBackground(Color.RED);
        
        dibujarSemaforo("Verde.jpg");
        
    }
    
    public void dibujarSemaforo(String color){
        
        JLabel img = new JLabel();
        
        ImageIcon imgIcon = new ImageIcon(getClass().getResource(color));

        Image imgEscalada = imgIcon.getImage().getScaledInstance(130, 200, Image.SCALE_SMOOTH);
        Icon iconoEscalado = new ImageIcon(imgEscalada);
        semaforo.setBounds(1020 , 40, 130, 200);
        semaforo.setIcon(iconoEscalado);
     
    }
    
    public void dibujarTabla(String nombre, int prio, int rafaga, int tiempo){
        
        scrollPane.removeAll();
        
        JLabel texto1 = new JLabel("Proceso");
        JLabel texto2 = new JLabel("T. llegada");
        JLabel texto3 = new JLabel("Rafaga");
        JLabel texto4 = new JLabel("Prioridad");
        JLabel texto5 = new JLabel("T. comienzo");
        JLabel texto6 = new JLabel("T. final");
        JLabel texto7 = new JLabel("T. retorno");
        JLabel texto8 = new JLabel("T. espera");
        
        texto1.setBounds(20, 20, 150, 20);
        texto2.setBounds(100, 20, 150, 20);
        texto3.setBounds(180, 20, 150, 20);
        texto4.setBounds(260, 20, 150, 20);
        texto5.setBounds(340, 20, 150, 20);
        texto6.setBounds(420, 20, 150, 20);
        texto7.setBounds(500, 20, 150, 20);
        texto8.setBounds(580, 20, 150, 20);
        
        scrollPane.add(texto1);
        scrollPane.add(texto2);
        scrollPane.add(texto3);
        scrollPane.add(texto4);
        scrollPane.add(texto5);
        scrollPane.add(texto6);
        scrollPane.add(texto7);
        scrollPane.add(texto8);
        
        for(int i = 0; i<filas; i++){
            
            for(int j = 0; j<8; j++){
            
                if(tabla[i][j] != null){
                    
                    scrollPane.add(tabla[i][j]);
                    
                } else {
                
                    tabla[i][j] = new JTextField("-");
                    tabla[i][j].setBounds(20 + (j*80), 40 + (i*25), 70, 20);
                    
                    scrollPane.add(tabla[i][j]);
                    
                }

            }
        
        }
        
        tabla[filas-1][0].setText(nombre);
        tabla[filas-1][1].setText(Integer.toString(tiempo));
        tabla[filas-1][2].setText(Integer.toString(rafaga));
        tabla[filas-1][3].setText(String.valueOf(prio));

        scrollPane.repaint();
        scrollPane1.setViewportView(scrollPane);
            
    }
    
    public void llenarRestante(){
        
        tabla[nodoEjecutado.getIndice()-1][4].setText(Integer.toString(nodoEjecutado.getComienzo()));
        tabla[nodoEjecutado.getIndice()-1][5].setText(Integer.toString(nodoEjecutado.getFinalizacion()));
        tabla[nodoEjecutado.getIndice()-1][6].setText(Integer.toString(nodoEjecutado.getFinalizacion() - nodoEjecutado.getLlegada()));
        tabla[nodoEjecutado.getIndice()-1][7].setText(Integer.toString(nodoEjecutado.getComienzo() - nodoEjecutado.getLlegada()));
 
    }
    
    public void dibujarDiagrama(String nombre, int coorX, int coorY){
        
        scrollPane2.removeAll();
        
        for(int i = 0; i<101; i++){
            
            diagrama[0][i] = new JLabel(Integer.toString(i));
            diagrama[0][i].setBounds(20 + (i*20), 20, 20, 20);

            scrollPane2.add(diagrama[0][i]);
            
        }
        
        diagrama[coorY][0] = new JLabel("  " + nombre);
        diagrama[coorY][0].setBounds(0, 20 + (coorY*20), 20, 20);
        
        scrollPane2.add(diagrama[coorY][0]);
        
        JLabel img = new JLabel();
        
        ImageIcon imgIcon = new ImageIcon(getClass().getResource("barra.jpg"));

        Image imgEscalada = imgIcon.getImage().getScaledInstance(20, 20, Image.SCALE_SMOOTH);
        Icon iconoEscalado = new ImageIcon(imgEscalada);
        
        for(int i = 1; i < coorY+1; i++){
            
            for(int j = 0; j < coorX+1; j++){
                
                if(diagrama[i][j] != null){
                
                    scrollPane2.add(diagrama[i][j]);
                    
                }
                
            }
            
        }
        
        diagrama[coorY][coorX+1] = new JLabel();
        diagrama[coorY][coorX+1].setBounds(20 + (coorX*20), 20 + (coorY*20), 20, 20);
        diagrama[coorY][coorX+1].setIcon(iconoEscalado);
        
        scrollPane2.add(diagrama[coorY][coorX+1]);
        
        scrollPane2.repaint();
        scrollPane3.setViewportView(scrollPane2);
            
    }        
    
    public int calcularRafaga(){
        
        return 1 + ((int) (Math.random()*10));
        
    }
    
    public void ordenarPrioridades(){
        
        int movimientos = 0;
        int contador = 0;
        
        Nodo temp = cola.getCabeza().getSiguiente();
        
        int menorPrio = cola.getCabeza().getPrioridad();
        
        while(!(temp.equals(cola.getCabeza()))){
    
            contador++;
            
            if(temp.getPrioridad() < menorPrio){
            
                menorPrio = temp.getPrioridad();
                movimientos = contador;
                
            }
            
            temp = temp.getSiguiente();
            
        }
        
        for(int i = 0; i < movimientos; i++){
            
            cola.intercambiar(cola.getCabeza());
            
        }
        
    }
    
    public void ingresar(String nombre, int prio, int rafaga, int tiempo, int filas){
        
        cola.insertar(nombre, prio, rafaga, tiempo, filas);
        
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        
        if(e.getSource() == botonIngresar){
            
            filas++;
            
            String nombre = tfNombre.getText();
            String p = String.valueOf(prioridad.getSelectedItem());
            int prio = Integer.parseInt(p);
            rafagaTemporal = calcularRafaga();
            
            ingresar(nombre, prio, rafagaTemporal, tiempoGlobal, filas);
            dibujarTabla(nombre, prio, rafagaTemporal, tiempoGlobal);
            
            
        } else if(e.getSource() == botonIniciar){
        
            procesos = new Thread( this );
            procesos.start();  
            
        } else if(e.getSource() == botonBloquear){
        
            filas++;
            procesos.suspend();
            ingresar(nodoEjecutado.getLlave() + "*", 5, nodoEjecutado.getRafaga(), tiempoGlobal, filas);
            dibujarTabla(nodoEjecutado.getLlave() + "*", 5, nodoEjecutado.getRafaga(), tiempoGlobal);
            nodoEjecutado.setFinalizacion(tiempoGlobal);
            llenarRestante();
            cola.eliminar(cola.getCabeza());
            ordenarPrioridades();
            nodoEjecutado = cola.getCabeza();
            coorY++;
            nodoEjecutado.setComienzo(tiempoGlobal);
            procesos.resume();
            
        }
        
    }
    
    @Override
    public void run() {
        
        try{

            while(cola.getTamaño() != 0){
                
                dibujarSemaforo("Rojo.jpg");
                
                ordenarPrioridades();
                
                nodoEjecutado = cola.getCabeza();
                nodoEjecutado.setComienzo(tiempoGlobal);
                
                while(nodoEjecutado.getRafaga() > 0){
                    
                    nodoEjecutado.setRafaga(nodoEjecutado.getRafaga()-1);
                    
                    label3.setText("Proceso en ejecucion: " + nodoEjecutado.getLlave());
                    label4.setText("Tiempo: " + String.valueOf(tiempoGlobal) + " Segundos.");
                    
                    dibujarDiagrama(nodoEjecutado.getLlave(), coorX, coorY);
                    
                    tiempoGlobal++;
                    coorX++;
                    
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException ex) {
                        Logger.getLogger(ProcesosBloqueo.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    
                }
                
                nodoEjecutado.setFinalizacion(tiempoGlobal);
                llenarRestante();
                cola.eliminar(cola.getCabeza());
                
                coorY++;
                
            }

            dibujarSemaforo("Verde.jpg");
            label3.setText("Proceso en ejecucion: Ninguno");
            
        } catch(Exception e){
        
            System.out.print("No se que poner aca :D");
            
        } 
    
    } 
    
}
