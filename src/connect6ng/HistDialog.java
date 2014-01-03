package connect6ng;


/**
 *
 * @author houqi1993
 */
@SuppressWarnings("serial")
public class HistDialog extends javax.swing.JDialog {

    /**
     * Creates new form HistJDialog
     */
    public HistDialog(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
    }

    private void initComponents() {

        icon_label = new javax.swing.JLabel();
        title_label = new javax.swing.JLabel();
        all_label_text = new javax.swing.JLabel();
        win_label_text = new javax.swing.JLabel();
        ratio_label_text = new javax.swing.JLabel();
        OK_btn = new javax.swing.JButton();
        all_label = new javax.swing.JLabel();
        win_label = new javax.swing.JLabel();
        ratio_label = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        icon_label.setIcon(new javax.swing.ImageIcon("D:\\Study\\WorkSpace\\connect6-ng-dev\\res\\logo_100.png")); // NOI18N

        title_label.setFont(new java.awt.Font("华文行楷", 1, 22)); // NOI18N
        title_label.setText("六子棋小游戏");

        all_label_text.setText("已玩游戏局数");

        win_label_text.setText("游戏胜利次数");

        ratio_label_text.setText(" 游戏胜利率 ");

        OK_btn.setText("确定");
        OK_btn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                OK_btnActionPerformed(evt);
            }
        });

        HistResult h = (new Database()).DataStatistics();
        all_label.setText(String.valueOf(h.getAll()));

        win_label.setText(String.valueOf(h.getWin()));

        if( h.getAll() == 0 ){
            ratio_label.setText("0.00%");
        }else{
            ratio_label.setText( String.valueOf(100*(float)(h.getWin())/(float)(h.getAll())) + "%");
        }

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(58, 58, 58)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(all_label_text)
                            .addComponent(icon_label)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(ratio_label_text)
                                .addComponent(win_label_text)))
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(26, 26, 26)
                                .addComponent(title_label))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(win_label)
                                    .addComponent(all_label)
                                    .addComponent(ratio_label))
                                .addGap(75, 75, 75))))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(149, 149, 149)
                        .addComponent(OK_btn, javax.swing.GroupLayout.PREFERRED_SIZE, 98, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(32, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(31, 31, 31)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(icon_label, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(title_label)
                        .addGap(32, 32, 32)))
                .addGap(31, 31, 31)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(all_label_text)
                    .addComponent(all_label))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(win_label_text)
                    .addComponent(win_label))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(ratio_label_text)
                    .addComponent(ratio_label))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 26, Short.MAX_VALUE)
                .addComponent(OK_btn)
                .addGap(19, 19, 19))
        );

        pack();
    }// </editor-fold>                        

    private void OK_btnActionPerformed(java.awt.event.ActionEvent evt) {                                       
        // TODO add your handling code here:
        HistDialog.this.dispose();
    }                                      

    // Variables declaration - do not modify                     
    private javax.swing.JButton OK_btn;
    private javax.swing.JLabel all_label;
    private javax.swing.JLabel all_label_text;
    private javax.swing.JLabel icon_label;
    private javax.swing.JLabel ratio_label;
    private javax.swing.JLabel ratio_label_text;
    private javax.swing.JLabel title_label;
    private javax.swing.JLabel win_label;
    private javax.swing.JLabel win_label_text;
    // End of variables declaration                   
}

