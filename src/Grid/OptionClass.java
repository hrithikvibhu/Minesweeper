/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Grid;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.util.Hashtable;
import javax.swing.*;

/**
 *
 * @author Lenovo
 */
public class OptionClass {
    
    private static JFrame frame;
    private static int dimen;
    private static int mines;
    
    public OptionClass(int d,int m){
        dimen=d;
        mines=m;
        createLayout();
    }
    
    /*public static void main(String args[]){
        createLayout();
    }*/
    
    private static void createLayout(){
        frame=new JFrame();
        JPanel panel=new JPanel();
        panel.setLayout(new GridBagLayout());
        panel.setPreferredSize(new Dimension(350,400));
        
        JRadioButton stn=new JRadioButton("Standard");
        stn.setSelected(true);
        GridBagConstraints c=new GridBagConstraints();
        c.gridx=0;
        c.gridy=0;
        c.gridwidth=1;
        panel.add(stn,c);
        
        JSlider m=new JSlider(JSlider.HORIZONTAL,1,3,1);
        m.setMajorTickSpacing(1);
        m.setMinorTickSpacing(1);
        m.setPaintTicks(true);
        Hashtable labelTable=new Hashtable();
        labelTable.put(new Integer(1),new JLabel("Beginner"));
        labelTable.put(new Integer(2),new JLabel("Intermediate"));
        labelTable.put(new Integer(3),new JLabel("Advanced"));
        m.setLabelTable(labelTable);
        m.setPaintLabels(true);
        c=new GridBagConstraints();
        c.gridx=1;
        c.gridy=1;
        c.gridwidth=1;
        c.fill=GridBagConstraints.HORIZONTAL;
        panel.add(m,c);
        
        JRadioButton cus=new JRadioButton("Custom");
        stn.setSelected(true);
        c=new GridBagConstraints();
        c.gridx=0;
        c.gridy=3;
        c.gridwidth=1;
        c.weightx=1;
        c.weighty=1;
        panel.add(cus,c);
        
        JLabel dLabel=new JLabel("DIMENSION");
        //dLabel.setPreferredSize(new Dimension(100,100));
        //dLabel.setFont(new Font("Tahoma",Font.BOLD,20));
        c=new GridBagConstraints();
        c.gridx=0;
        c.gridy=4;
        c.gridwidth=1;
        c.weightx=1;
        c.weighty=1;
        panel.add(dLabel,c);
        
        SpinnerNumberModel sm=new SpinnerNumberModel(9,5,25,1);
        JSpinner dSpinner=new JSpinner(sm);
        dSpinner.setPreferredSize(new Dimension(50,20));
        dSpinner.setValue(dimen);
        dSpinner.setEnabled(false);
        //dSpinner.setFont(new Font("Tahoma",Font.BOLD,18));
        ((JSpinner.DefaultEditor)dSpinner.getEditor()).getTextField().setEditable(false);
        c=new GridBagConstraints();
        c.gridx=1;
        c.gridy=4;
        c.gridwidth=1;
        c.weightx=1;
        c.weighty=1;
        panel.add(dSpinner,c);
        
        JLabel mLabel=new JLabel("MINES");
        //mLabel.setPreferredSize(new Dimension(100,100));
        //mLabel.setFont(new Font("Tahoma",Font.BOLD,20));
        c=new GridBagConstraints();
        c.gridx=0;
        c.gridy=5;
        c.gridwidth=1;
        c.weightx=1;
        c.weighty=1;
        panel.add(mLabel,c);
        
        SpinnerNumberModel smm=new SpinnerNumberModel(20,20,600,5);
        JSpinner mSpinner=new JSpinner(smm);
        mSpinner.setLocation(new Point(80,20));
        mSpinner.setPreferredSize(new Dimension(50,20));
        mSpinner.setValue(mines);
        mSpinner.setEnabled(false);
        //mSpinner.setFont(new Font("Tahoma",Font.BOLD,18));
        ((JSpinner.DefaultEditor)mSpinner.getEditor()).getTextField().setEditable(false);
        c=new GridBagConstraints();
        c.gridx=1;
        c.gridy=5;
        c.gridwidth=1;
        c.weightx=1;
        c.weighty=1;
        panel.add(mSpinner,c);
        
        
        ButtonGroup btGp=new ButtonGroup();
        btGp.add(stn);
        btGp.add(cus);
        
        stn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                m.setEnabled(true);
                dSpinner.setEnabled(false);
                mSpinner.setEnabled(false);
            }
        });
        
        cus.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                m.setEnabled(false);
                dSpinner.setEnabled(true);
                mSpinner.setEnabled(true);
            }
        });
        
        JButton bt=new JButton("SET");
        bt.setFont(new Font("Tahoma",Font.BOLD,20));
        bt.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(stn.isSelected()){
                    int ch=(int)m.getValue();
                   if(ch==1){
                       dimen=9;
                       mines=20;
                   }else if(ch==2){
                       dimen=16;
                       mines=100;
                   }else if(ch==3){
                       dimen=24;
                       mines=300;
                   }
                }else if(cus.isSelected()){
                    dimen=(int)dSpinner.getValue();
                    mines=(int)mSpinner.getValue();
                }
                if(mines<(dimen*dimen))
                exit();
            }
        });
        c=new GridBagConstraints();
        c.gridx=0;
        c.gridy=6;
        c.gridwidth=3;
        c.weightx=0.0;
        c.ipady=20;
        c.fill=GridBagConstraints.HORIZONTAL;
        panel.add(bt,c);
        
        frame.add(panel);
        frame.pack();
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.addWindowListener(new java.awt.event.WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {
                //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }

            @Override
            public void windowClosing(WindowEvent e) {
                exit();
            }

            @Override
            public void windowClosed(WindowEvent e) {
                //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }

            @Override
            public void windowIconified(WindowEvent e) {
                //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }

            @Override
            public void windowDeiconified(WindowEvent e) {
                //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }

            @Override
            public void windowActivated(WindowEvent e) {
                //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }

            @Override
            public void windowDeactivated(WindowEvent e) {
                //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }
        });
    }
    
    private static void exit()
    {
        GridPrac.setDim(dimen,mines);
        frame.setVisible(false);
        frame.dispose();
    }
    
}
