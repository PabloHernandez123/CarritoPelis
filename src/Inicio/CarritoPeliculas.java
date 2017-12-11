
package Inicio;

import Clientess.ImagenTabla;
import Clientess.LimpiarTexto;
import Peliculas.Peliculas;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.StringTokenizer;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

public class CarritoPeliculas extends javax.swing.JFrame {
     LimpiarTexto lt = new LimpiarTexto();
    
    private String ruta_txt = "Peliculas.txt";
    Peliculas peli;
    MetodoPelicula metpel;
    int clic_tabla;
    public CarritoPeliculas() {
        initComponents();
        metpel= new MetodoPelicula();
        
        try{
            cargapeliculas();
            listarPeliculas();
        }catch(Exception ex){
            mensaje("No existe el archivo Cliente");
        }
    }
    
    public void cargapeliculas(){
        File ruta = new File(ruta_txt);
        try{
            
            FileReader fi = new FileReader(ruta);
            BufferedReader bu = new BufferedReader(fi);
            
            
            String linea = null;
            while((linea = bu.readLine())!=null){
                StringTokenizer st = new StringTokenizer(linea, ",");
                peli = new Peliculas();
                peli.setIdpeli(Integer.parseInt(st.nextToken()));
                peli.setTitulo(st.nextToken());
                peli.setDirector((st.nextToken()));
                peli.setActor(st.nextToken());
                peli.setClasificacion(st.nextToken());
                metpel.agregarPelicula(peli);
            }
            bu.close();
        }catch(Exception ex){
            mensaje("Error al cargar el archivo pelicula: "+ex.getMessage());
            System.out.println(ex.getMessage());
        }
    }
    
     public void grabarpeliculas(){
        FileWriter fw;
        PrintWriter pw;
        try{
            fw = new FileWriter(ruta_txt);
            pw = new PrintWriter(fw);
            for(int i = 0; i < metpel.cantidadIDPel(); i++){
                peli= metpel.obtenerPelicula(i);
                pw.println(String.valueOf(","+peli.getIdpeli()+","+peli.getTitulo()+","+peli.getDirector()+","+peli.getActor()+","+peli.getClasificacion()));
            }
             pw.close();
        }catch(Exception ex){
            mensaje("Error al grabar archivo: "+ex.getMessage());
            System.out.println(ex.getMessage());
        }
    }
     
   public void ingresarPelicula(File ruta){
        try{
            if(leeridpel() == -666)mensaje("Ingresar id de la pelicula ");
            else if(leertitulo() == null)mensaje("Ingresar Titulo de la Pelicula");
             else if(leerdirector() == null)mensaje("Ingresar Director");
            else if(leeractor() == null)mensaje("Ingresar Actor");
            else if(leerclasificacion() == null)mensaje("Ingresar Clsificación");
            else{
                peli= new Peliculas(leeridpel(),leertitulo(),leerdirector(),leeractor(),leerclasificacion());
                if(metpel.buscarIDPel(peli.getIdpeli())!= -1)mensaje("Este id de la pelicula ya existe");
                else metpel.agregarPelicula(peli);
                
                grabarpeliculas();
                listarPeliculas();
                lt.limpiar_texto(panel); 
            }
        }catch(Exception ex){
            mensaje(ex.getMessage());
        }
    }
    
     public void modificarPelicula(File ruta){
        try{
         if(leeridpel() == -666)mensaje("Ingresar id de la pelicula ");
            else if(leertitulo() == null)mensaje("Ingresar Titulo de la Pelicula");
             else if(leerdirector() == null)mensaje("Ingresar Director");
            else if(leeractor() == null)mensaje("Ingresar Actor");
            else if(leerclasificacion() == null)mensaje("Ingresar Clsificación");
            else{
                int ids = metpel.buscarIDPel(leeridpel());
                peli = new Peliculas(leeridpel(),leertitulo(),leerdirector(),leeractor(),leerclasificacion());
                
                if(ids == -1)metpel.agregarPelicula(peli);
                else metpel.modificarPelicula(ids,peli);
                
                grabarpeliculas();
                listarPeliculas();
                lt.limpiar_texto(panel);
            }
        }catch(Exception ex){
            mensaje(ex.getMessage());
        }
     }

public void eliminarPeliculas(){
        try{
            if(leeridpel() == -666) mensaje("Ingresar id");
            
            else{
                int ids = metpel.buscarIDPel(leeridpel());
                if(ids == -1) mensaje("el id del la pelicula existe");
                
                else{
                    int s = JOptionPane.showConfirmDialog(null, "Esta seguro de eliminar este producto","Si/No",0);
                    if(s == 0){
                       metpel.eliminarPelicula(ids);
                        
                        grabarpeliculas();
                        listarPeliculas();
                        lt.limpiar_texto(panel);
                    }
                }
}
 }catch(Exception ex){
            mensaje(ex.getMessage());
        }
}

public void listarPeliculas(){
      
    DefaultTableModel dt = new DefaultTableModel(){
            @Override
            public boolean isCellEditable(int row, int column){
                return false;
            }
        };
        
        dt.addColumn("IDPelicula");
        dt.addColumn("Titulo");
        dt.addColumn("Director");
        dt.addColumn("Actor");
        dt.addColumn("Clasificación");
        
        tabla.setDefaultRenderer(Object.class, new ImagenTabla());
        
        Object fila[] = new Object[dt.getColumnCount()];
        for(int i = 0; i < metpel.cantidadIDPel(); i++){
            peli = metpel.obtenerPelicula(i);
            fila[0] = peli.getIdpeli();
            fila[1] = peli.getTitulo();
            fila[2] = peli.getDirector();
            fila[3] = peli.getActor();
            fila[4] = peli.getClasificacion();
            dt.addRow(fila);
        }
        tabla.setModel(dt);
        tabla.setRowHeight(60);
    }
    public int leeridpel(){
        try{
            int id= Integer.parseInt(txtidpel.getText().trim());
            return id;
        }catch(Exception ex){
            return -666;
        }
    }
    
    public String leertitulo(){
        try{
            String titulo = txtitulo.getText().trim().replace(" ", " ");
            return titulo;
        }catch(Exception ex){
            return null;
        }
    }
    public String leerdirector(){
        try{
            String director = txtdirector.getText().trim().replace(" ", " ");
            return director;
        }catch(Exception ex){
            return null;
        }
    }
    public String leeractor(){
        try{
            String actor = txtactor.getText().trim().replace(" ", " ");
            return actor;
        }catch(Exception ex){
            return null;
        }
    }
    public String leerclasificacion(){
        try{
            String clasificacion = txtclasificacion.getText().trim().replace(" ", " ");
            return clasificacion;
        }catch(Exception ex){
            return null;
        }
    }
    
    
    public void mensaje(String texto){
        JOptionPane.showMessageDialog(null, texto);
    }
     
    

    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        panel = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        txtidpel = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        txtitulo = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        txtdirector = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        txtclasificacion = new javax.swing.JTextField();
        txtcantidad = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        txtrenta = new javax.swing.JTextField();
        checkrenta = new javax.swing.JCheckBox();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        checkventa = new javax.swing.JCheckBox();
        btn_eliminarpel = new javax.swing.JButton();
        btn_modifica = new javax.swing.JButton();
        btn_calculo = new javax.swing.JButton();
        txtsubtotal = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        txtiva = new javax.swing.JTextField();
        jLabel12 = new javax.swing.JLabel();
        txtdescuento = new javax.swing.JTextField();
        jLabel13 = new javax.swing.JLabel();
        txtotal = new javax.swing.JTextField();
        txtpago = new javax.swing.JTextField();
        jLabel14 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        txtcambio = new javax.swing.JTextField();
        btn_guarda = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        tabla = new javax.swing.JTable();
        Menu = new javax.swing.JButton();
        btnlimpiar = new javax.swing.JButton();
        txtRuta = new javax.swing.JTextField();
        panel1 = new javax.swing.JPanel();
        jLabel16 = new javax.swing.JLabel();
        jLabel17 = new javax.swing.JLabel();
        txtidpel1 = new javax.swing.JTextField();
        jLabel18 = new javax.swing.JLabel();
        txtitulo1 = new javax.swing.JTextField();
        jLabel19 = new javax.swing.JLabel();
        txtdirector1 = new javax.swing.JTextField();
        jLabel20 = new javax.swing.JLabel();
        txtactor1 = new javax.swing.JTextField();
        txtventa1 = new javax.swing.JTextField();
        jLabel21 = new javax.swing.JLabel();
        jLabel22 = new javax.swing.JLabel();
        txtrenta1 = new javax.swing.JTextField();
        checkrenta1 = new javax.swing.JCheckBox();
        jLabel23 = new javax.swing.JLabel();
        jLabel24 = new javax.swing.JLabel();
        checkventa1 = new javax.swing.JCheckBox();
        btn_eliminarpel1 = new javax.swing.JButton();
        btn_modifica1 = new javax.swing.JButton();
        btn_calculo1 = new javax.swing.JButton();
        txtsubtotal1 = new javax.swing.JTextField();
        jLabel25 = new javax.swing.JLabel();
        jLabel26 = new javax.swing.JLabel();
        txtiva1 = new javax.swing.JTextField();
        jLabel27 = new javax.swing.JLabel();
        txtdescuento1 = new javax.swing.JTextField();
        jLabel28 = new javax.swing.JLabel();
        txtotal1 = new javax.swing.JTextField();
        txtpago1 = new javax.swing.JTextField();
        jLabel29 = new javax.swing.JLabel();
        jLabel30 = new javax.swing.JLabel();
        txtcambio1 = new javax.swing.JTextField();
        btn_guarda1 = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        tabla1 = new javax.swing.JTable();
        Menu1 = new javax.swing.JButton();
        btnlimpiar1 = new javax.swing.JButton();
        txtRuta1 = new javax.swing.JTextField();
        txtactor = new javax.swing.JTextField();
        txtventa2 = new javax.swing.JTextField();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setBackground(java.awt.SystemColor.activeCaption);

        panel.setBackground(java.awt.SystemColor.activeCaption);

        jLabel1.setFont(new java.awt.Font("Comic Sans MS", 1, 14)); // NOI18N
        jLabel1.setText("Ingreso al carrito de peliculas");

        jLabel2.setFont(new java.awt.Font("Comic Sans MS", 1, 14)); // NOI18N
        jLabel2.setText("ID Pelicula");

        txtidpel.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        txtidpel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtidpelActionPerformed(evt);
            }
        });
        txtidpel.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtidpelKeyTyped(evt);
            }
        });

        jLabel3.setFont(new java.awt.Font("Comic Sans MS", 1, 14)); // NOI18N
        jLabel3.setText("Titulo:");

        txtitulo.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        txtitulo.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtituloKeyTyped(evt);
            }
        });

        jLabel4.setFont(new java.awt.Font("Comic Sans MS", 1, 14)); // NOI18N
        jLabel4.setText("Director");

        txtdirector.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        txtdirector.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtdirectorKeyTyped(evt);
            }
        });

        jLabel8.setFont(new java.awt.Font("Comic Sans MS", 1, 14)); // NOI18N
        jLabel8.setText("Actor principal");

        txtclasificacion.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N

        txtcantidad.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N

        jLabel7.setFont(new java.awt.Font("Comic Sans MS", 1, 14)); // NOI18N
        jLabel7.setText("Precio Venta");

        jLabel9.setFont(new java.awt.Font("Comic Sans MS", 1, 14)); // NOI18N
        jLabel9.setText("Precio Renta");

        txtrenta.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        txtrenta.setText("$");

        checkrenta.setText("Renta");
        checkrenta.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                checkrentaActionPerformed(evt);
            }
        });

        jLabel10.setFont(new java.awt.Font("Comic Sans MS", 1, 14)); // NOI18N
        jLabel10.setText("Clasificación");

        jLabel11.setFont(new java.awt.Font("Comic Sans MS", 1, 14)); // NOI18N
        jLabel11.setText("Cantidad");

        checkventa.setText("Ventas");
        checkventa.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                checkventaActionPerformed(evt);
            }
        });

        btn_eliminarpel.setText("Eliminar");
        btn_eliminarpel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_eliminarpelActionPerformed(evt);
            }
        });

        btn_modifica.setText("Modificar");
        btn_modifica.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_modificaActionPerformed(evt);
            }
        });

        btn_calculo.setText("Calcular Total");
        btn_calculo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_calculoActionPerformed(evt);
            }
        });

        txtsubtotal.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        txtsubtotal.setText("$");
        txtsubtotal.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtsubtotalKeyTyped(evt);
            }
        });

        jLabel5.setFont(new java.awt.Font("Comic Sans MS", 1, 14)); // NOI18N
        jLabel5.setText("SubTotal:");

        jLabel6.setFont(new java.awt.Font("Comic Sans MS", 1, 14)); // NOI18N
        jLabel6.setText("Iva:");

        txtiva.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        txtiva.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtivaKeyTyped(evt);
            }
        });

        jLabel12.setFont(new java.awt.Font("Comic Sans MS", 1, 14)); // NOI18N
        jLabel12.setText("Descuento:");

        txtdescuento.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N

        jLabel13.setFont(new java.awt.Font("Comic Sans MS", 1, 14)); // NOI18N
        jLabel13.setText("Total:");

        txtotal.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        txtotal.setText("$");
        txtotal.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtotalActionPerformed(evt);
            }
        });

        txtpago.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        txtpago.setText("$");

        jLabel14.setFont(new java.awt.Font("Comic Sans MS", 1, 14)); // NOI18N
        jLabel14.setText("Pago:");

        jLabel15.setFont(new java.awt.Font("Comic Sans MS", 1, 14)); // NOI18N
        jLabel15.setText("Cambio:");

        txtcambio.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        txtcambio.setText("$");

        btn_guarda.setText("Guardar");
        btn_guarda.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_guardaActionPerformed(evt);
            }
        });

        tabla.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Usuario", "ID Pelicula", "Titulo", "Director", "Actor Principal", "Cantidad", "Precio", "Total"
            }
        ));
        tabla.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tablaMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tabla);

        Menu.setText("Menú");
        Menu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MenuActionPerformed(evt);
            }
        });

        btnlimpiar.setText("Limpiar");
        btnlimpiar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnlimpiarActionPerformed(evt);
            }
        });

        txtRuta.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        txtRuta.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtRutaActionPerformed(evt);
            }
        });
        txtRuta.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtRutaKeyTyped(evt);
            }
        });

        panel1.setBackground(java.awt.SystemColor.activeCaption);
        panel1.setLayout(null);

        jLabel16.setFont(new java.awt.Font("Comic Sans MS", 1, 14)); // NOI18N
        jLabel16.setText("Ingreso al carrito de peliculas");
        panel1.add(jLabel16);
        jLabel16.setBounds(30, 20, 240, 42);

        jLabel17.setFont(new java.awt.Font("Comic Sans MS", 1, 14)); // NOI18N
        jLabel17.setText("ID Pelicula");
        panel1.add(jLabel17);
        jLabel17.setBounds(30, 77, 73, 21);

        txtidpel1.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        txtidpel1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtidpel1ActionPerformed(evt);
            }
        });
        txtidpel1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtidpel1KeyTyped(evt);
            }
        });
        panel1.add(txtidpel1);
        txtidpel1.setBounds(150, 75, 100, 25);

        jLabel18.setFont(new java.awt.Font("Comic Sans MS", 1, 14)); // NOI18N
        jLabel18.setText("Titulo:");
        panel1.add(jLabel18);
        jLabel18.setBounds(30, 113, 45, 21);

        txtitulo1.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        txtitulo1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtitulo1KeyTyped(evt);
            }
        });
        panel1.add(txtitulo1);
        txtitulo1.setBounds(150, 111, 227, 25);

        jLabel19.setFont(new java.awt.Font("Comic Sans MS", 1, 14)); // NOI18N
        jLabel19.setText("Director");
        panel1.add(jLabel19);
        jLabel19.setBounds(30, 149, 57, 21);

        txtdirector1.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        txtdirector1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtdirector1KeyTyped(evt);
            }
        });
        panel1.add(txtdirector1);
        txtdirector1.setBounds(150, 147, 227, 25);

        jLabel20.setFont(new java.awt.Font("Comic Sans MS", 1, 14)); // NOI18N
        jLabel20.setText("Actor principal");
        panel1.add(jLabel20);
        jLabel20.setBounds(20, 192, 99, 21);

        txtactor1.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        panel1.add(txtactor1);
        txtactor1.setBounds(150, 190, 227, 25);

        txtventa1.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        txtventa1.setText("$");
        panel1.add(txtventa1);
        txtventa1.setBounds(136, 279, 110, 25);

        jLabel21.setFont(new java.awt.Font("Comic Sans MS", 1, 14)); // NOI18N
        jLabel21.setText("Precio Venta");
        panel1.add(jLabel21);
        jLabel21.setBounds(20, 281, 85, 21);

        jLabel22.setFont(new java.awt.Font("Comic Sans MS", 1, 14)); // NOI18N
        jLabel22.setText("Precio Renta");
        panel1.add(jLabel22);
        jLabel22.setBounds(20, 324, 85, 21);

        txtrenta1.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        txtrenta1.setText("$");
        panel1.add(txtrenta1);
        txtrenta1.setBounds(136, 324, 110, 25);

        checkrenta1.setText("Renta");
        checkrenta1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                checkrenta1ActionPerformed(evt);
            }
        });
        panel1.add(checkrenta1);
        checkrenta1.setBounds(290, 50, 67, 23);

        jLabel23.setFont(new java.awt.Font("Comic Sans MS", 1, 14)); // NOI18N
        jLabel23.setText("Clasificación");
        panel1.add(jLabel23);
        jLabel23.setBounds(20, 239, 83, 21);

        jLabel24.setFont(new java.awt.Font("Comic Sans MS", 1, 14)); // NOI18N
        jLabel24.setText("Cantidad");
        panel1.add(jLabel24);
        jLabel24.setBounds(270, 280, 59, 21);

        checkventa1.setText("Ventas");
        checkventa1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                checkventa1ActionPerformed(evt);
            }
        });
        panel1.add(checkventa1);
        checkventa1.setBounds(290, 80, 67, 23);

        btn_eliminarpel1.setText("Eliminar");
        btn_eliminarpel1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_eliminarpel1ActionPerformed(evt);
            }
        });
        panel1.add(btn_eliminarpel1);
        btn_eliminarpel1.setBounds(220, 370, 89, 43);

        btn_modifica1.setText("Modificar");
        btn_modifica1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_modifica1ActionPerformed(evt);
            }
        });
        panel1.add(btn_modifica1);
        btn_modifica1.setBounds(120, 370, 89, 43);

        btn_calculo1.setText("Calcular Total");
        btn_calculo1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_calculo1ActionPerformed(evt);
            }
        });
        panel1.add(btn_calculo1);
        btn_calculo1.setBounds(751, 110, 110, 52);

        txtsubtotal1.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        txtsubtotal1.setText("$");
        txtsubtotal1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtsubtotal1KeyTyped(evt);
            }
        });
        panel1.add(txtsubtotal1);
        txtsubtotal1.setBounds(524, 39, 90, 25);

        jLabel25.setFont(new java.awt.Font("Comic Sans MS", 1, 14)); // NOI18N
        jLabel25.setText("SubTotal:");
        panel1.add(jLabel25);
        jLabel25.setBounds(454, 39, 67, 21);

        jLabel26.setFont(new java.awt.Font("Comic Sans MS", 1, 14)); // NOI18N
        jLabel26.setText("Iva:");
        panel1.add(jLabel26);
        jLabel26.setBounds(454, 79, 29, 21);

        txtiva1.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        txtiva1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtiva1KeyTyped(evt);
            }
        });
        panel1.add(txtiva1);
        txtiva1.setBounds(524, 79, 50, 25);

        jLabel27.setFont(new java.awt.Font("Comic Sans MS", 1, 14)); // NOI18N
        jLabel27.setText("Descuento:");
        panel1.add(jLabel27);
        jLabel27.setBounds(454, 119, 74, 21);

        txtdescuento1.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        panel1.add(txtdescuento1);
        txtdescuento1.setBounds(534, 119, 40, 25);

        jLabel28.setFont(new java.awt.Font("Comic Sans MS", 1, 14)); // NOI18N
        jLabel28.setText("Total:");
        panel1.add(jLabel28);
        jLabel28.setBounds(454, 162, 42, 21);

        txtotal1.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        txtotal1.setText("$");
        txtotal1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtotal1ActionPerformed(evt);
            }
        });
        panel1.add(txtotal1);
        txtotal1.setBounds(514, 162, 100, 25);

        txtpago1.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        txtpago1.setText("$");
        panel1.add(txtpago1);
        txtpago1.setBounds(514, 202, 100, 25);

        jLabel29.setFont(new java.awt.Font("Comic Sans MS", 1, 14)); // NOI18N
        jLabel29.setText("Pago:");
        panel1.add(jLabel29);
        jLabel29.setBounds(454, 202, 35, 21);

        jLabel30.setFont(new java.awt.Font("Comic Sans MS", 1, 14)); // NOI18N
        jLabel30.setText("Cambio:");
        panel1.add(jLabel30);
        jLabel30.setBounds(454, 242, 53, 21);

        txtcambio1.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        txtcambio1.setText("$");
        panel1.add(txtcambio1);
        txtcambio1.setBounds(524, 242, 100, 25);

        btn_guarda1.setText("Guardar");
        btn_guarda1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_guarda1ActionPerformed(evt);
            }
        });
        panel1.add(btn_guarda1);
        btn_guarda1.setBounds(10, 370, 89, 43);

        tabla1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Usuario", "ID Pelicula", "Titulo", "Director", "Actor Principal", "Cantidad", "Precio", "Total"
            }
        ));
        jScrollPane2.setViewportView(tabla1);

        panel1.add(jScrollPane2);
        jScrollPane2.setBounds(10, 460, 916, 91);

        Menu1.setText("Menú");
        Menu1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Menu1ActionPerformed(evt);
            }
        });
        panel1.add(Menu1);
        Menu1.setBounds(187, 569, 89, 43);

        btnlimpiar1.setText("Limpiar");
        btnlimpiar1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnlimpiar1ActionPerformed(evt);
            }
        });
        panel1.add(btnlimpiar1);
        btnlimpiar1.setBounds(340, 370, 89, 43);

        txtRuta1.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        txtRuta1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtRuta1ActionPerformed(evt);
            }
        });
        txtRuta1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtRuta1KeyTyped(evt);
            }
        });
        panel1.add(txtRuta1);
        txtRuta1.setBounds(524, 300, 100, 25);

        txtactor.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N

        txtventa2.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        txtventa2.setText("$");

        javax.swing.GroupLayout panelLayout = new javax.swing.GroupLayout(panel);
        panel.setLayout(panelLayout);
        panelLayout.setHorizontalGroup(
            panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(panel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addGroup(panelLayout.createSequentialGroup()
                .addGap(30, 30, 30)
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 240, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(20, 20, 20)
                .addComponent(checkrenta, javax.swing.GroupLayout.PREFERRED_SIZE, 67, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(97, 97, 97)
                .addComponent(jLabel5)
                .addGap(3, 3, 3)
                .addComponent(txtsubtotal, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addGroup(panelLayout.createSequentialGroup()
                .addGap(30, 30, 30)
                .addComponent(jLabel2)
                .addGap(47, 47, 47)
                .addComponent(txtidpel, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(40, 40, 40)
                .addComponent(checkventa, javax.swing.GroupLayout.PREFERRED_SIZE, 67, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(97, 97, 97)
                .addComponent(jLabel6)
                .addGap(41, 41, 41)
                .addComponent(txtiva, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addGroup(panelLayout.createSequentialGroup()
                .addGap(30, 30, 30)
                .addGroup(panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel3)
                    .addComponent(jLabel4))
                .addGap(63, 63, 63)
                .addGroup(panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtitulo, javax.swing.GroupLayout.PREFERRED_SIZE, 227, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtdirector, javax.swing.GroupLayout.PREFERRED_SIZE, 227, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(77, 77, 77)
                .addGroup(panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelLayout.createSequentialGroup()
                        .addComponent(jLabel12)
                        .addGap(6, 6, 6)
                        .addComponent(txtdescuento, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(panelLayout.createSequentialGroup()
                        .addComponent(jLabel13)
                        .addGap(18, 18, 18)
                        .addComponent(txtotal, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(137, 137, 137)
                .addComponent(btn_calculo, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addGroup(panelLayout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addComponent(jLabel8)
                .addGap(31, 31, 31)
                .addComponent(txtactor, javax.swing.GroupLayout.PREFERRED_SIZE, 227, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(77, 77, 77)
                .addComponent(jLabel14)
                .addGap(25, 25, 25)
                .addComponent(txtpago, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addGroup(panelLayout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addComponent(jLabel10)
                .addGap(47, 47, 47)
                .addComponent(txtclasificacion, javax.swing.GroupLayout.PREFERRED_SIZE, 227, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(77, 77, 77)
                .addComponent(jLabel15)
                .addGap(17, 17, 17)
                .addComponent(txtcambio, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addGroup(panelLayout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addGroup(panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel7)
                    .addComponent(jLabel9))
                .addGap(31, 31, 31)
                .addGroup(panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtventa2, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtrenta, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(24, 24, 24)
                .addComponent(jLabel11)
                .addGap(11, 11, 11)
                .addComponent(txtcantidad, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(134, 134, 134)
                .addComponent(txtRuta, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addGroup(panelLayout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addComponent(btn_guarda, javax.swing.GroupLayout.PREFERRED_SIZE, 89, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(21, 21, 21)
                .addComponent(btn_modifica, javax.swing.GroupLayout.PREFERRED_SIZE, 89, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(11, 11, 11)
                .addComponent(btn_eliminarpel, javax.swing.GroupLayout.PREFERRED_SIZE, 89, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(31, 31, 31)
                .addComponent(btnlimpiar, javax.swing.GroupLayout.PREFERRED_SIZE, 89, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addGroup(panelLayout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 916, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addGroup(panelLayout.createSequentialGroup()
                .addGap(187, 187, 187)
                .addComponent(Menu, javax.swing.GroupLayout.PREFERRED_SIZE, 89, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        panelLayout.setVerticalGroup(
            panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelLayout.createSequentialGroup()
                .addComponent(panel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(19, 19, 19)
                .addGroup(panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(panelLayout.createSequentialGroup()
                        .addGap(30, 30, 30)
                        .addComponent(checkrenta))
                    .addGroup(panelLayout.createSequentialGroup()
                        .addGap(19, 19, 19)
                        .addComponent(jLabel5))
                    .addGroup(panelLayout.createSequentialGroup()
                        .addGap(19, 19, 19)
                        .addComponent(txtsubtotal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(2, 2, 2)
                .addGroup(panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelLayout.createSequentialGroup()
                        .addGap(2, 2, 2)
                        .addComponent(jLabel2))
                    .addComponent(txtidpel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(panelLayout.createSequentialGroup()
                        .addGap(5, 5, 5)
                        .addComponent(checkventa))
                    .addGroup(panelLayout.createSequentialGroup()
                        .addGap(4, 4, 4)
                        .addComponent(jLabel6))
                    .addGroup(panelLayout.createSequentialGroup()
                        .addGap(4, 4, 4)
                        .addComponent(txtiva, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(6, 6, 6)
                .addGroup(panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelLayout.createSequentialGroup()
                        .addGap(3, 3, 3)
                        .addComponent(jLabel3)
                        .addGap(15, 15, 15)
                        .addComponent(jLabel4))
                    .addGroup(panelLayout.createSequentialGroup()
                        .addGap(1, 1, 1)
                        .addComponent(txtitulo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(11, 11, 11)
                        .addComponent(txtdirector, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(panelLayout.createSequentialGroup()
                        .addGap(9, 9, 9)
                        .addGroup(panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel12)
                            .addComponent(txtdescuento, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel13)
                            .addComponent(txtotal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(btn_calculo, javax.swing.GroupLayout.PREFERRED_SIZE, 52, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(3, 3, 3)
                .addGroup(panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelLayout.createSequentialGroup()
                        .addGap(2, 2, 2)
                        .addComponent(jLabel8))
                    .addComponent(txtactor, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(panelLayout.createSequentialGroup()
                        .addGap(12, 12, 12)
                        .addComponent(jLabel14))
                    .addGroup(panelLayout.createSequentialGroup()
                        .addGap(12, 12, 12)
                        .addComponent(txtpago, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(3, 3, 3)
                .addGroup(panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelLayout.createSequentialGroup()
                        .addGap(9, 9, 9)
                        .addComponent(jLabel10))
                    .addComponent(txtclasificacion, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(panelLayout.createSequentialGroup()
                        .addGap(12, 12, 12)
                        .addComponent(jLabel15))
                    .addGroup(panelLayout.createSequentialGroup()
                        .addGap(12, 12, 12)
                        .addComponent(txtcambio, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(12, 12, 12)
                .addGroup(panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelLayout.createSequentialGroup()
                        .addGap(2, 2, 2)
                        .addComponent(jLabel7)
                        .addGap(22, 22, 22)
                        .addComponent(jLabel9))
                    .addGroup(panelLayout.createSequentialGroup()
                        .addComponent(txtventa2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(20, 20, 20)
                        .addComponent(txtrenta, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(panelLayout.createSequentialGroup()
                        .addGap(1, 1, 1)
                        .addComponent(jLabel11))
                    .addGroup(panelLayout.createSequentialGroup()
                        .addGap(1, 1, 1)
                        .addComponent(txtcantidad, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(panelLayout.createSequentialGroup()
                        .addGap(21, 21, 21)
                        .addComponent(txtRuta, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(21, 21, 21)
                .addGroup(panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btn_guarda, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btn_modifica, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btn_eliminarpel, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnlimpiar, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(47, 47, 47)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 91, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(Menu, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(panel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(panel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void MenuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MenuActionPerformed
        Menus men= new Menus();
        men.setVisible(true);
        this.setVisible(false);
    }//GEN-LAST:event_MenuActionPerformed

    private void txtotalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtotalActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtotalActionPerformed

    private void txtivaKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtivaKeyTyped
        // TODO add your handling code here:
    }//GEN-LAST:event_txtivaKeyTyped

    private void txtsubtotalKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtsubtotalKeyTyped
        // TODO add your handling code here:
    }//GEN-LAST:event_txtsubtotalKeyTyped

    private void btn_calculoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_calculoActionPerformed

    }//GEN-LAST:event_btn_calculoActionPerformed

    private void btn_eliminarpelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_eliminarpelActionPerformed

      this.eliminarPeliculas();
    }//GEN-LAST:event_btn_eliminarpelActionPerformed

    private void checkventaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_checkventaActionPerformed
        checkrenta.setVisible(false);
        checkventa.setVisible(true);
        txtrenta.setVisible(false);
        txtcantidad.setVisible(true);
    }//GEN-LAST:event_checkventaActionPerformed

    private void checkrentaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_checkrentaActionPerformed
        checkrenta.setVisible(true);
        checkventa.setVisible(false);
        txtrenta.setVisible(true);
        txtcantidad.setVisible(false);
    }//GEN-LAST:event_checkrentaActionPerformed

    private void txtdirectorKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtdirectorKeyTyped

    }//GEN-LAST:event_txtdirectorKeyTyped

    private void txtituloKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtituloKeyTyped

    }//GEN-LAST:event_txtituloKeyTyped

    private void txtidpelKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtidpelKeyTyped

    }//GEN-LAST:event_txtidpelKeyTyped

    private void txtidpelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtidpelActionPerformed

    }//GEN-LAST:event_txtidpelActionPerformed

    private void btnlimpiarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnlimpiarActionPerformed
        LimpiarTexto lp = new LimpiarTexto();
        lp.limpiar_texto(panel);
    }//GEN-LAST:event_btnlimpiarActionPerformed

    private void btn_modificaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_modificaActionPerformed
         File ruta = new File(txtRuta.getText());
        this.modificarPelicula(ruta);
    }//GEN-LAST:event_btn_modificaActionPerformed

    private void txtRutaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtRutaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtRutaActionPerformed

    private void txtRutaKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtRutaKeyTyped
        // TODO add your handling code here:
    }//GEN-LAST:event_txtRutaKeyTyped

    private void btn_guardaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_guardaActionPerformed
         File ruta = new File(txtRuta.getText());
        this.ingresarPelicula(ruta);
    }//GEN-LAST:event_btn_guardaActionPerformed

    private void txtidpel1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtidpel1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtidpel1ActionPerformed

    private void txtidpel1KeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtidpel1KeyTyped
        // TODO add your handling code here:
    }//GEN-LAST:event_txtidpel1KeyTyped

    private void txtitulo1KeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtitulo1KeyTyped
        // TODO add your handling code here:
    }//GEN-LAST:event_txtitulo1KeyTyped

    private void txtdirector1KeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtdirector1KeyTyped
        // TODO add your handling code here:
    }//GEN-LAST:event_txtdirector1KeyTyped

    private void checkrenta1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_checkrenta1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_checkrenta1ActionPerformed

    private void checkventa1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_checkventa1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_checkventa1ActionPerformed

    private void btn_eliminarpel1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_eliminarpel1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btn_eliminarpel1ActionPerformed

    private void btn_modifica1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_modifica1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btn_modifica1ActionPerformed

    private void btn_calculo1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_calculo1ActionPerformed

    }//GEN-LAST:event_btn_calculo1ActionPerformed

    private void txtsubtotal1KeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtsubtotal1KeyTyped
        // TODO add your handling code here:
    }//GEN-LAST:event_txtsubtotal1KeyTyped

    private void txtiva1KeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtiva1KeyTyped
        // TODO add your handling code here:
    }//GEN-LAST:event_txtiva1KeyTyped

    private void txtotal1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtotal1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtotal1ActionPerformed

    private void btn_guarda1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_guarda1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btn_guarda1ActionPerformed

    private void Menu1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Menu1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_Menu1ActionPerformed

    private void btnlimpiar1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnlimpiar1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnlimpiar1ActionPerformed

    private void txtRuta1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtRuta1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtRuta1ActionPerformed

    private void txtRuta1KeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtRuta1KeyTyped
        // TODO add your handling code here:
    }//GEN-LAST:event_txtRuta1KeyTyped

    private void tablaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tablaMouseClicked
   clic_tabla = tabla.rowAtPoint(evt.getPoint());

        int id = (int)tabla.getValueAt(clic_tabla, 0);
        String titulo = ""+tabla.getValueAt(clic_tabla,1);
        String director = ""+tabla.getValueAt(clic_tabla,2);
        String actor = ""+tabla.getValueAt(clic_tabla,3);
        String clasificacion= ""+tabla.getValueAt(clic_tabla,4);
       

       txtidpel.setText(String.valueOf(id));
        txtitulo.setText(titulo);
        txtdirector.setText(director);
        txtactor.setText(actor);
        txtclasificacion.setText(clasificacion);
    }//GEN-LAST:event_tablaMouseClicked

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(CarritoPeliculas.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(CarritoPeliculas.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(CarritoPeliculas.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(CarritoPeliculas.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new CarritoPeliculas().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton Menu;
    private javax.swing.JButton Menu1;
    private javax.swing.JButton btn_calculo;
    private javax.swing.JButton btn_calculo1;
    private javax.swing.JButton btn_eliminarpel;
    private javax.swing.JButton btn_eliminarpel1;
    private javax.swing.JButton btn_guarda;
    private javax.swing.JButton btn_guarda1;
    private javax.swing.JButton btn_modifica;
    private javax.swing.JButton btn_modifica1;
    private javax.swing.JButton btnlimpiar;
    private javax.swing.JButton btnlimpiar1;
    private javax.swing.JCheckBox checkrenta;
    private javax.swing.JCheckBox checkrenta1;
    private javax.swing.JCheckBox checkventa;
    private javax.swing.JCheckBox checkventa1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel24;
    private javax.swing.JLabel jLabel25;
    private javax.swing.JLabel jLabel26;
    private javax.swing.JLabel jLabel27;
    private javax.swing.JLabel jLabel28;
    private javax.swing.JLabel jLabel29;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel30;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JPanel panel;
    private javax.swing.JPanel panel1;
    private javax.swing.JTable tabla;
    private javax.swing.JTable tabla1;
    private javax.swing.JTextField txtRuta;
    private javax.swing.JTextField txtRuta1;
    private javax.swing.JTextField txtactor;
    private javax.swing.JTextField txtactor1;
    private javax.swing.JTextField txtcambio;
    private javax.swing.JTextField txtcambio1;
    private javax.swing.JTextField txtcantidad;
    private javax.swing.JTextField txtclasificacion;
    private javax.swing.JTextField txtdescuento;
    private javax.swing.JTextField txtdescuento1;
    private javax.swing.JTextField txtdirector;
    private javax.swing.JTextField txtdirector1;
    private javax.swing.JTextField txtidpel;
    private javax.swing.JTextField txtidpel1;
    private javax.swing.JTextField txtitulo;
    private javax.swing.JTextField txtitulo1;
    private javax.swing.JTextField txtiva;
    private javax.swing.JTextField txtiva1;
    private javax.swing.JTextField txtotal;
    private javax.swing.JTextField txtotal1;
    private javax.swing.JTextField txtpago;
    private javax.swing.JTextField txtpago1;
    private javax.swing.JTextField txtrenta;
    private javax.swing.JTextField txtrenta1;
    private javax.swing.JTextField txtsubtotal;
    private javax.swing.JTextField txtsubtotal1;
    private javax.swing.JTextField txtventa1;
    private javax.swing.JTextField txtventa2;
    // End of variables declaration//GEN-END:variables
}
