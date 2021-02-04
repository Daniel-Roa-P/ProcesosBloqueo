
package procesosbloqueo;

import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.WindowConstants;
import javax.swing.JComboBox;
import javax.swing.JTextField;

public class ProcesosBloqueo extends JFrame implements ActionListener {

    JScrollPane scrollPane = new JScrollPane();
    JScrollPane scrollPane1 = new JScrollPane();
    
    JScrollPane scrollPane2 = new JScrollPane();
    JScrollPane scrollPane3 = new JScrollPane();
    
    JLabel semaforo = new JLabel();
    
    JLabel label1 = new JLabel("Nombre del proceso: ");
    JLabel label2 = new JLabel("Prioridad del proceso:");
    JLabel label3 = new JLabel("Proceso en ejecucion: Ninguno");
    
    JButton botonIngresar = new JButton("Ingresar proceso");
    JButton botonIniciar = new JButton("Iniciar ejecucion");
    JButton botonBloquear = new JButton("Bloquear proceso");
    
    JComboBox prioridad = new JComboBox();
    
    public JTextField tfNombre = new JTextField("A");
    
    public static void main(String[] args) {

        ProcesosBloqueo pb = new ProcesosBloqueo(); 
        pb.setBounds(0, 0, 1060, 730);
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
        label3.setBounds(800, 550, 300, 200);
        
        scrollPane.setBounds(50, 40, 2500, 2500);
        scrollPane.setPreferredSize(new Dimension(2500, 2500));  
        scrollPane.setBackground(Color.lightGray);
        
        scrollPane1.setBounds(50, 40, 700, 200);
        scrollPane1.setPreferredSize(new Dimension(1150, 400)); 
        scrollPane1.setBackground(Color.lightGray);
        
        scrollPane2.setBounds(50, 300, 2500, 2500);
        scrollPane2.setPreferredSize(new Dimension(2500, 2500));  
        scrollPane2.setBackground(Color.lightGray);
        
        scrollPane3.setBounds(50, 300, 700, 350);
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

        Image imgEscalada = imgIcon.getImage().getScaledInstance(200, 330, Image.SCALE_SMOOTH);
        Icon iconoEscalado = new ImageIcon(imgEscalada);
        semaforo.setBounds(800 , 300, 200, 330);
        semaforo.setIcon(iconoEscalado);
     
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        
        if(e.getSource() == botonIngresar){
        
            System.out.println(prioridad.getSelectedItem());
            System.out.println(tfNombre.getText());
            
        } else if(e.getSource() == botonIniciar){
        
        } else if(e.getSource() == botonBloquear){
        
        }
        
    }
    
}
