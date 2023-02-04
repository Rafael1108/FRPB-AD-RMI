package chatRMI.GrupoA.Client;

import java.awt.Color;
import java.awt.Dimension;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.text.AttributeSet;
import javax.swing.text.DefaultCaret;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyleContext;

/**
 *
 * @author Edgar Basurto
 */
public class ChatClientForm extends javax.swing.JFrame {

    DefaultCaret caret;
    public String user = "";

    /**
     * Creates new form ChatClientForm
     */
    public ChatClientForm() {
        String nick_usuario = JOptionPane.showInputDialog(
                null,
                "NickName: ",
                "Ingrese su NickName",
                JOptionPane.INFORMATION_MESSAGE);
        if (nick_usuario != null && !nick_usuario.isEmpty()) {

            initComponents();
            lblNickName.setText(nick_usuario);
            user = nick_usuario;
            this.setLocationRelativeTo(null);

            /* */
            caret = (DefaultCaret) this.lblHistorico.getCaret();
            caret.setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);
            setPreferredSize(new Dimension(480, 360));
        } else {
            System.exit(0);
        }
    }

    public void notificarHistorico(String linea, Color _color) {
        appendToPane(lblHistorico, linea + "\n", _color);
    }

    /**
     * Método que cambia de color a la línea que se va a agregar al JTextPane
     *
     * @param tp Objeto de tipo JTextPane donde se va a agregar el texto
     * @param msg String que se procesa y se cambia el estilo y el color
     * @param c Objeto de tipo Color que determina el color que lleva el string
     */
    private void appendToPane(JTextPane tp, String msg, Color c) {
        StyleContext sc = StyleContext.getDefaultStyleContext();
        AttributeSet aset = sc.addAttribute(SimpleAttributeSet.EMPTY, StyleConstants.Foreground, c);

        aset = sc.addAttribute(aset, StyleConstants.FontFamily, "Lucida Console");
        aset = sc.addAttribute(aset, StyleConstants.Alignment, StyleConstants.ALIGN_JUSTIFIED);

        int len = tp.getDocument().getLength();
        tp.setCaretPosition(len);
        tp.setCharacterAttributes(aset, false);
        tp.replaceSelection(msg);
    }

    public void alternarAutoScroll() {
        if (caret.getUpdatePolicy() != DefaultCaret.NEVER_UPDATE) {
            caret.setUpdatePolicy(DefaultCaret.NEVER_UPDATE);
        } else {
            this.lblHistorico.setCaretPosition(this.lblHistorico.getDocument().getLength());
            caret.setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jScrollPane3 = new javax.swing.JScrollPane();
        lblHistorico = new javax.swing.JTextPane();
        btnEnviar = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        lblNickName = new javax.swing.JLabel();
        btnLogout = new javax.swing.JButton();
        txtEnviar = new javax.swing.JTextField();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Chat RMI");
        setAlwaysOnTop(true);
        setMinimumSize(new java.awt.Dimension(460, 200));

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/chatRMI/GrupoA/Img/icon_01.png"))); // NOI18N
        jLabel1.setText("CHAT GRUPO A");

        lblHistorico.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        lblHistorico.setFocusable(false);
        jScrollPane3.setViewportView(lblHistorico);

        btnEnviar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/chatRMI/GrupoA/Img/icon-03.png"))); // NOI18N
        btnEnviar.setText("ENVIAR");
        btnEnviar.setMargin(new java.awt.Insets(2, 12, 3, 12));
        btnEnviar.setMaximumSize(new java.awt.Dimension(75, 23));
        btnEnviar.setMinimumSize(new java.awt.Dimension(80, 23));

        jLabel2.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel2.setText("NickName:");

        lblNickName.setText("________________");

        btnLogout.setIcon(new javax.swing.ImageIcon(getClass().getResource("/chatRMI/GrupoA/Img/icon-04.png"))); // NOI18N
        btnLogout.setText("LOGOUT");
        btnLogout.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLogoutActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(txtEnviar)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btnEnviar, javax.swing.GroupLayout.PREFERRED_SIZE, 118, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jScrollPane3)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(12, 12, 12)
                        .addComponent(jLabel1)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(lblNickName, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnLogout)))
                .addGap(32, 32, 32))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(jLabel2)
                    .addComponent(lblNickName)
                    .addComponent(btnLogout))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 244, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnEnviar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtEnviar, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(22, 22, 22))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnLogoutActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLogoutActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnLogoutActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnEnviar;
    private javax.swing.JButton btnLogout;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JTextPane lblHistorico;
    private javax.swing.JLabel lblNickName;
    private javax.swing.JTextField txtEnviar;
    // End of variables declaration//GEN-END:variables

    public JButton getBtnEnviar() {
        return btnEnviar;
    }

    public void setBtnEnviar(JButton btnEnviar) {
        this.btnEnviar = btnEnviar;
    }

    public JButton getBtnLogout() {
        return btnLogout;
    }

    public void setBtnLogout(JButton btnLogout) {
        this.btnLogout = btnLogout;
    }

    public JTextField getTxtEnviar() {
        return txtEnviar;
    }

    public void setTxtEnviar(JTextField txtEnviar) {
        this.txtEnviar = txtEnviar;
    }

}
