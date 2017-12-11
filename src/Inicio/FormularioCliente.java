
package Inicio;

import Clientess.Clientes;
import Clientess.LimpiarTexto;
import Clientess.ImagenTabla;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.StringTokenizer;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableModel;

public class FormularioCliente extends javax.swing.JFrame {

    
    
    LimpiarTexto lt = new LimpiarTexto();
    
    private String ruta_txt = "Clientes.txt"; 
    
    Clientes cli;
    Metodos met;
    
    int clic_tabla;
            
    public FormularioCliente() {
        initComponents();
        met= new Metodos();
        
        try{
            cargatexto();
            listarRegistro();
        }catch(Exception ex){
            mensaje("No existe el archivo Cliente");
        }
    }

    public void cargatexto(){
        File ruta = new File(ruta_txt);
        try{
            
            FileReader fi = new FileReader(ruta);
            BufferedReader bu = new BufferedReader(fi);
            
            
            String linea = null;
            while((linea = bu.readLine())!=null){
                StringTokenizer st = new StringTokenizer(linea, ",");
                cli = new Clientes();
                cli.setId(Integer.parseInt(st.nextToken()));
                cli.setNombre(st.nextToken());
                  cli.setApellidos(st.nextToken());
                   cli.setCorreo(st.nextToken());
                  cli.setUsuario(st.nextToken()); 
                met.agregarCliente(cli);
            }
            bu.close();
        }catch(Exception ex){
            mensaje("Error al cargar el archivo cliente: "+ex.getMessage());
            System.out.println(ex.getMessage());
        }
    }
    
    public void grabar_txt(){
        FileWriter fw;
        PrintWriter pw;
        try{
            fw = new FileWriter(ruta_txt);
            pw = new PrintWriter(fw);
            for(int i = 0; i < met.cantidadID(); i++){
                cli= met.obtenerCliente(i);
                pw.println(String.valueOf(","+cli.getId()+","+cli.getNombre()+","+cli.getApellidos()+","+cli.getCorreo()+","+cli.getUsuario()));
            }
             pw.close();
        }catch(Exception ex){
            mensaje("Error al grabar archivo: "+ex.getMessage());
            System.out.println(ex.getMessage());
        }
    }
    
    public void ingresarRegistro(File ruta){
        try{
            if(leerid() == -666)mensaje("Ingresar id ");
            else if(leernombre() == null)mensaje("Ingresar Nombre");
             else if(leerapellidos() == null)mensaje("Ingresar Apellidos");
            else if(leercorreo() == null)mensaje("Ingresar Correo");
            else if(leerusuario() == null)mensaje("Ingresar Usuario");
            else{
                cli= new Clientes(leerid(),leernombre(),leerapellidos(), leercorreo(), leerusuario());
                if(met.buscarID(cli.getId())!= -1)mensaje("Este id ya existe");
                else met.agregarCliente(cli);
                
                grabar_txt();
                listarRegistro();
                lt.limpiar_texto(panel); 
            }
        }catch(Exception ex){
            mensaje(ex.getMessage());
        }
    }
    
    public void modificarRegistro(File ruta){
        try{
           if(leerid() == -666)mensaje("Ingresar id ");
            else if(leernombre() == null)mensaje("Ingresar Nombre");
            else if(leerapellidos() == null)mensaje("Ingresar Apellidos");
            else if(leercorreo() == null)mensaje("Ingresar Correo");
            else if(leerusuario() == null)mensaje("Ingresar Usuario");
            else{
                int ids = met.buscarID(leerid());
                cli = new Clientes(leerid(),leernombre(), leerapellidos(), leercorreo(),leerusuario());
                
                if(ids == -1)met.agregarCliente(cli);
                else met.modificarCliente(ids,cli);
                
                grabar_txt();
                listarRegistro();
                lt.limpiar_texto(panel);
            }
        }catch(Exception ex){
            mensaje(ex.getMessage());
        }
    }
    
    public void eliminarRegistro(){
        try{
            if(leerid() == -666) mensaje("Ingresar id");
            
            else{
                int ids = met.buscarID(leerid());
                if(ids == -1) mensaje("el id del programa existe");
                
                else{
                    int s = JOptionPane.showConfirmDialog(null, "Esta seguro de eliminar este producto","Si/No",0);
                    if(s == 0){
                       met.eliminarCliente(ids);
                        
                        grabar_txt();
                        listarRegistro();
                        lt.limpiar_texto(panel);
                    }
                }  
            }
        }catch(Exception ex){
            mensaje(ex.getMessage());
        }
    }
    
    public void listarRegistro(){
        DefaultTableModel dt = new DefaultTableModel(){
            @Override
            public boolean isCellEditable(int row, int column){
                return false;
            }
        };
        
        dt.addColumn("ID");
        dt.addColumn("Nombre");
        dt.addColumn("Apellidos");
        dt.addColumn("Correo");
        dt.addColumn("Usuario");
        
        tabla.setDefaultRenderer(Object.class, new ImagenTabla());
        
        Object fila[] = new Object[dt.getColumnCount()];
        for(int i = 0; i < met.cantidadID(); i++){
            cli = met.obtenerCliente(i);
            fila[0] = cli.getId();
            fila[1] = cli.getNombre();
            fila[2] = cli.getApellidos();
            fila[3] = cli.getCorreo();
            fila[4] = cli.getUsuario();
            dt.addRow(fila);
        }
        tabla.setModel(dt);
        tabla.setRowHeight(60);
    }
    
    public int leerid(){
        try{
            int id= Integer.parseInt(txtid.getText().trim());
            return id;
        }catch(Exception ex){
            return -666;
        }
    }
    
    public String leernombre(){
        try{
            String nombre = txtnombre.getText().trim().replace(" ", " ");
            return nombre;
        }catch(Exception ex){
            return null;
        }
    }
    public String leerapellidos(){
        try{
            String apellidos = txtapellidos.getText().trim().replace(" ", " ");
            return apellidos;
        }catch(Exception ex){
            return null;
        }
    }
    public String leercorreo(){
        try{
            String correo = txtcorreo.getText().trim().replace(" ", " ");
            return correo;
        }catch(Exception ex){
            return null;
        }
    }
    public String leerusuario(){
        try{
            String usuario = txtusuario.getText().trim().replace(" ", " ");
            return usuario;
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
        jScrollPane1 = new javax.swing.JScrollPane();
        tabla = new javax.swing.JTable();
        btn_guardar = new javax.swing.JButton();
        btn_modificar = new javax.swing.JButton();
        btn_eliminar = new javax.swing.JButton();
        jLabel3 = new javax.swing.JLabel();
        txtapellidos = new javax.swing.JTextField();
        txtnombre = new javax.swing.JTextField();
        txtid = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        txtRuta = new javax.swing.JTextField();
        btn_limpiar = new javax.swing.JButton();
        txtcorreo = new javax.swing.JTextField();
        txtusuario = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        btnsalir = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Registro de Archivos txt");

        panel.setBackground(java.awt.SystemColor.activeCaption);

        tabla.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null}
            },
            new String [] {
                "ID_Cliente", "Nombres", "Apellidos", "Correo", "Usuario"
            }
        ));
        tabla.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tablaMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tabla);

        btn_guardar.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        btn_guardar.setText("Guardar");
        btn_guardar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_guardarActionPerformed(evt);
            }
        });

        btn_modificar.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        btn_modificar.setText("Modificar");
        btn_modificar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_modificarActionPerformed(evt);
            }
        });

        btn_eliminar.setFont(new java.awt.Font("Times New Roman", 1, 12)); // NOI18N
        btn_eliminar.setText("Eliminar");
        btn_eliminar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_eliminarActionPerformed(evt);
            }
        });

        jLabel3.setFont(new java.awt.Font("Comic Sans MS", 1, 14)); // NOI18N
        jLabel3.setText("Apellidos");

        txtapellidos.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        txtapellidos.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtapellidosKeyTyped(evt);
            }
        });

        txtnombre.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        txtnombre.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtnombreKeyTyped(evt);
            }
        });

        txtid.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        txtid.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtidActionPerformed(evt);
            }
        });
        txtid.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtidKeyTyped(evt);
            }
        });

        jLabel1.setFont(new java.awt.Font("Comic Sans MS", 1, 14)); // NOI18N
        jLabel1.setText("ID");

        jLabel2.setFont(new java.awt.Font("Comic Sans MS", 1, 14)); // NOI18N
        jLabel2.setText("Nombre:");

        txtRuta.setEditable(false);
        txtRuta.setFont(new java.awt.Font("Arial", 0, 10)); // NOI18N

        btn_limpiar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Iconos/limpiar.png"))); // NOI18N
        btn_limpiar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_limpiarActionPerformed(evt);
            }
        });

        txtcorreo.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N

        txtusuario.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N

        jLabel7.setFont(new java.awt.Font("Comic Sans MS", 1, 14)); // NOI18N
        jLabel7.setText("Usuario");

        jLabel8.setFont(new java.awt.Font("Comic Sans MS", 1, 14)); // NOI18N
        jLabel8.setText("Correo");

        jLabel6.setFont(new java.awt.Font("Comic Sans MS", 1, 14)); // NOI18N
        jLabel6.setText("Registro de Clientes");

        btnsalir.setText("Salir");
        btnsalir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnsalirActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout panelLayout = new javax.swing.GroupLayout(panel);
        panel.setLayout(panelLayout);
        panelLayout.setHorizontalGroup(
            panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelLayout.createSequentialGroup()
                        .addGroup(panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(panelLayout.createSequentialGroup()
                                .addGroup(panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel2)
                                    .addComponent(jLabel1)
                                    .addComponent(jLabel3))
                                .addGap(47, 47, 47)
                                .addGroup(panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                    .addComponent(txtid, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 116, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(txtnombre, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 227, Short.MAX_VALUE)
                                    .addComponent(txtapellidos, javax.swing.GroupLayout.Alignment.LEADING)))
                            .addGroup(panelLayout.createSequentialGroup()
                                .addGap(108, 108, 108)
                                .addComponent(txtcorreo, javax.swing.GroupLayout.PREFERRED_SIZE, 227, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jLabel8)
                            .addGroup(panelLayout.createSequentialGroup()
                                .addComponent(jLabel7)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(txtusuario, javax.swing.GroupLayout.PREFERRED_SIZE, 227, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGroup(panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelLayout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 64, Short.MAX_VALUE)
                                .addGroup(panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelLayout.createSequentialGroup()
                                        .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 202, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(132, 132, 132))
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelLayout.createSequentialGroup()
                                        .addComponent(btn_modificar, javax.swing.GroupLayout.PREFERRED_SIZE, 102, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(18, 18, 18)
                                        .addComponent(btn_limpiar)
                                        .addGap(192, 192, 192))
                                    .addGroup(panelLayout.createSequentialGroup()
                                        .addComponent(btn_guardar, javax.swing.GroupLayout.PREFERRED_SIZE, 102, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(18, 18, 18)
                                        .addComponent(btn_eliminar, javax.swing.GroupLayout.PREFERRED_SIZE, 102, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(145, 145, 145))))
                            .addGroup(panelLayout.createSequentialGroup()
                                .addGroup(panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(panelLayout.createSequentialGroup()
                                        .addGap(141, 141, 141)
                                        .addComponent(txtRuta, javax.swing.GroupLayout.PREFERRED_SIZE, 155, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(panelLayout.createSequentialGroup()
                                        .addGap(40, 40, 40)
                                        .addComponent(btnsalir, javax.swing.GroupLayout.PREFERRED_SIZE, 109, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addGap(0, 0, Short.MAX_VALUE))))
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.TRAILING)))
        );
        panelLayout.setVerticalGroup(
            panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelLayout.createSequentialGroup()
                .addGroup(panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelLayout.createSequentialGroup()
                        .addGap(19, 19, 19)
                        .addGroup(panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel1)
                            .addComponent(txtid, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(txtnombre, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtRuta, javax.swing.GroupLayout.DEFAULT_SIZE, 25, Short.MAX_VALUE))
                .addGroup(panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelLayout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel3)
                            .addComponent(txtapellidos, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addComponent(jLabel8))
                    .addGroup(panelLayout.createSequentialGroup()
                        .addGap(29, 29, 29)
                        .addGroup(panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(btn_guardar)
                            .addComponent(btn_eliminar, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(btn_modificar)
                                .addComponent(txtcorreo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(btn_limpiar))))
                .addGroup(panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelLayout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addGroup(panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtusuario, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel7)))
                    .addGroup(panelLayout.createSequentialGroup()
                        .addGap(24, 24, 24)
                        .addComponent(btnsalir, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(52, 52, 52)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 156, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(128, 128, 128))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(panel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(panel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void btn_limpiarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_limpiarActionPerformed
        LimpiarTexto lp = new LimpiarTexto();
        lp.limpiar_texto(panel);
    }//GEN-LAST:event_btn_limpiarActionPerformed

    private void btn_eliminarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_eliminarActionPerformed
        this.eliminarRegistro();
    }//GEN-LAST:event_btn_eliminarActionPerformed

    private void btn_modificarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_modificarActionPerformed
        File ruta = new File(txtRuta.getText());
        this.modificarRegistro(ruta);
    }//GEN-LAST:event_btn_modificarActionPerformed

    private void btn_guardarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_guardarActionPerformed
        File ruta = new File(txtRuta.getText());
        this.ingresarRegistro(ruta);
    }//GEN-LAST:event_btn_guardarActionPerformed

    private void tablaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tablaMouseClicked

        clic_tabla = tabla.rowAtPoint(evt.getPoint());

        int id = (int)tabla.getValueAt(clic_tabla, 0);
        String nombre = ""+tabla.getValueAt(clic_tabla,1);
        String apellidos = ""+tabla.getValueAt(clic_tabla,2);
        String correo = ""+tabla.getValueAt(clic_tabla,3);
        String usuario = ""+tabla.getValueAt(clic_tabla,4);
       

        txtid.setText(String.valueOf(id));
        txtnombre.setText(nombre);
        txtapellidos.setText(apellidos);
        txtcorreo.setText(correo);
        txtusuario.setText(usuario);
       
    }//GEN-LAST:event_tablaMouseClicked

    private void txtidActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtidActionPerformed
  
 
    }//GEN-LAST:event_txtidActionPerformed

    private void txtnombreKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtnombreKeyTyped
 char c= evt.getKeyChar();
 if(Character.isDigit(c)){
     getToolkit().beep();
      evt.consume();
     JOptionPane.showMessageDialog(this,"Solo puedes ingresar letras");
     txtnombre.setCursor(null);
 }
    }//GEN-LAST:event_txtnombreKeyTyped

    private void txtapellidosKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtapellidosKeyTyped
      char c= evt.getKeyChar();
 if(Character.isDigit(c)){
     getToolkit().beep();
      evt.consume();
     JOptionPane.showMessageDialog(this,"Solo puedes ingresar letras");
     txtapellidos.setCursor(null);
 }
    }//GEN-LAST:event_txtapellidosKeyTyped

    private void txtidKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtidKeyTyped
         char n= evt.getKeyChar();
 if(Character.isLetter(n)){
      evt.consume();
     JOptionPane.showMessageDialog(this,"Solo puedes ingresar numeros");
     txtid.setCursor(null);
 }
    }//GEN-LAST:event_txtidKeyTyped

    private void btnsalirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnsalirActionPerformed
        Menus menu= new Menus();
        menu.setVisible(true);
        this.setVisible(false);
        this.dispose();
    }//GEN-LAST:event_btnsalirActionPerformed

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
            java.util.logging.Logger.getLogger(FormularioCliente.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(FormularioCliente.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(FormularioCliente.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(FormularioCliente.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new FormularioCliente().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btn_eliminar;
    private javax.swing.JButton btn_guardar;
    private javax.swing.JButton btn_limpiar;
    private javax.swing.JButton btn_modificar;
    private javax.swing.JButton btnsalir;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JPanel panel;
    private javax.swing.JTable tabla;
    private javax.swing.JTextField txtRuta;
    private javax.swing.JTextField txtapellidos;
    private javax.swing.JTextField txtcorreo;
    private javax.swing.JTextField txtid;
    private javax.swing.JTextField txtnombre;
    private javax.swing.JTextField txtusuario;
    // End of variables declaration//GEN-END:variables
}
