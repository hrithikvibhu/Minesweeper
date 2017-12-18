/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Grid;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.*;
import javax.swing.event.MenuEvent;
import javax.swing.event.MenuListener;
/**
 *
 * @author Lenovo
 */
public class GridPrac {
    
    private static int BOOM=10;
    private static int FLAG=9;
    
    private static int counter=0;
    private static int dim=9,mines=20;
    private static JFrame frame;
    private static JButton playMat[][];
    private static int stateMat[][];
    private static boolean revealed[][];
    private static ImageIcon bomb;
    private static ImageIcon flag;
    
    public static void main(String args[])
    {
        createLayout();
    }
    
    private static void fillMatrix()
    {
        for(int i=0;i<mines;++i)
        {
            int rani=(int)(Math.random()*(dim));
            int ranj=(int)(Math.random()*(dim));
            if(stateMat[rani][ranj]==BOOM){
                i--;
                continue;
            }else{
                stateMat[rani][ranj]=BOOM;
            }
        }
        
        int dx[]={0,1,0,-1,-1,1,-1,1};
        int dy[]={1,0,-1,0,-1,1,1,-1};
        
        for(int i=0;i<dim;++i){
            for(int j=0;j<dim;++j){
                revealed[i][j]=false;
                if(stateMat[i][j]==BOOM)
                    continue;
                int c=0;
                for(int k=0;k<8;++k){
                    int x=i+dx[k];
                    int y=j+dy[k];
                    if(isSafe(x,y)&&stateMat[x][y]==BOOM)
                        c++;
                }
                stateMat[i][j]=c;
            }
        }
    }
    
    private static boolean isSafe(int x,int y)
    {
        if(x>=0&&x<dim&&y>=0&&y<dim)
            return true;
        return false;
    }
    
    private static void makeGrid()
    {
        playMat=new JButton[dim][dim];
        stateMat=new int[dim][dim];
        revealed=new boolean[dim][dim];
        counter=0;
    }
    
    private static void loadIcons()
    {
        String path="C:\\Users\\Lenovo\\Documents\\NetBeansProjects\\PracticeProject\\resources\\bomb1.png";
        bomb=new ImageIcon(path);
        Image newimg=bomb.getImage().getScaledInstance(35,35,Image.SCALE_SMOOTH);
        bomb=new ImageIcon(newimg);
        
        path="C:\\Users\\Lenovo\\Documents\\NetBeansProjects\\PracticeProject\\resources\\flag.png";
        flag=new ImageIcon(path);
        newimg=flag.getImage().getScaledInstance(35,35,Image.SCALE_SMOOTH);
        flag=new ImageIcon(newimg);
    }
    
    private static void createLayout()
    {
        frame=new JFrame("Minesweeper");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        JPanel panel=new JPanel();
        panel.setLayout(new GridLayout(dim,dim));
        //panel.setPreferredSize(new Dimension(600,600));
        
        makeGrid();
        
        loadIcons();
        
        fillMatrix();
        
        for(int i=0;i<dim;++i)
        {
            for(int j=0;j<dim;++j)
            {
                int size=40;
                playMat[i][j]=new JButton();
                playMat[i][j].setBackground(Color.LIGHT_GRAY);
                playMat[i][j].setFocusPainted(false);
                playMat[i][j].setPreferredSize(new Dimension(size,size));

                panel.add(playMat[i][j]);
                final int state=stateMat[i][j];
                final int a=i;
                final int b=j;
                playMat[i][j].addMouseListener(new MouseListener() {
                    @Override
                    public void mouseClicked(MouseEvent e) {
                        
                        if(e.getButton()==MouseEvent.BUTTON1){
                            if(state==BOOM){
                                display();
                                String btTexts[]={"Restart","Close"};
                                
                                int n=JOptionPane.showOptionDialog(frame,"BUSTED","Game Ends",JOptionPane.OK_CANCEL_OPTION,
                                        JOptionPane.PLAIN_MESSAGE,bomb,btTexts,btTexts[1]);
                                if(n==JOptionPane.YES_OPTION){
                                    restart();
                                }else if(n==JOptionPane.NO_OPTION || n==JOptionPane.CLOSED_OPTION){
                                    exit();
                                }
                            }
                            else if(revealed[a][b]==false){
                                openUp(a,b);
                            }
                        }else if(e.getButton()==MouseEvent.BUTTON3){
                            setFlag(a,b);
                        }
                    }

                    @Override
                    public void mousePressed(MouseEvent e) {
                        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
                    }

                    @Override
                    public void mouseReleased(MouseEvent e) {
                        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
                    }

                    @Override
                    public void mouseEntered(MouseEvent e) {
                        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
                    }

                    @Override
                    public void mouseExited(MouseEvent e) {
                        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
                    }
                });
            }
        }
        JMenuBar menu=createMenu();
        frame.setJMenuBar(menu);
        frame.add(panel);
        frame.setResizable(false);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
    
    private static void setFlag(int x,int y)
    {
        playMat[x][y].setIcon(flag);
    }
    
    private static void openUp(int x,int y)
    {
        if(stateMat[x][y]==BOOM || revealed[x][y])
            return;
        else
        {
            show(x,y);
            revealed[x][y]=true;
            counter++;
            checkStatus();
        }
        
        int dx[]={0,1,0,-1,-1,1,-1,1};
        int dy[]={1,0,-1,0,-1,1,1,-1};
        
        for(int i=0;i<8;++i)
        {
            int a=x+dx[i];
            int b=y+dy[i];
            
            if(isSafe(a,b))
            {
                if(revealed[a][b])
                    continue;
                if(stateMat[a][b]>0 && stateMat[a][b]<8){
                    show(a,b);
                    revealed[a][b]=true;
                    counter++;
                    checkStatus();
                }
                else if(stateMat[a][b]==0){
                    openUp(a,b);
                }
            }
        }
        
    }
    
    private static void show(int x,int y)
    {
        playMat[x][y].setBackground(Color.DARK_GRAY);
        playMat[x][y].setForeground(Color.YELLOW);
        playMat[x][y].setFont(new Font("Arial Black",Font.PLAIN,9));
        if(stateMat[x][y]==0)
            playMat[x][y].setText("");
        else
            playMat[x][y].setText(String.valueOf(stateMat[x][y]));
    }
    
    private static void display()
    {
        for(int i=0;i<dim;++i){
            for(int j=0;j<dim;++j){
                if(stateMat[i][j]==BOOM)
                    playMat[i][j].setIcon(bomb);
                else
                    show(i,j);
            }
        }
    }
    
    private static JMenuBar createMenu()
    {
        JMenuBar menu=new JMenuBar();
        JMenu opt=new JMenu("Options");
        JMenu exit=new JMenu("Exit");
        exit.addMenuListener(new MenuListener() {
            @Override
            public void menuSelected(MenuEvent e) {
                exit();
            }

            @Override
            public void menuDeselected(MenuEvent e) {
                //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }

            @Override
            public void menuCanceled(MenuEvent e) {
                //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }
        });
        menu.add(opt);
        menu.add(exit);
        JMenuItem diff=new JMenuItem("Difficulty");
        diff.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                OptionClass opMenu=new OptionClass(dim,mines);
                frame.setVisible(false);
                frame.dispose();
            }
        });
        opt.add(diff);
        
        JMenuItem re=new JMenuItem("Restart");
        re.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                restart();
            }
        });
        opt.add(re);
        return menu;
    }

    public static void setDim(int d, int m) {
        //System.out.println("d is: "+d+" Mines are : "+m);
       dim=d;
       mines=m;
       restart();
    }
    
    private static void checkStatus()
    {
        //System.out.println("The counter was: "+counter);
        if(counter==(dim*dim)-mines)
        {
            display();
            String btTexts[]={"Restart","Close"};
                                
            int n=JOptionPane.showOptionDialog(frame,"YOU NAILED IT","Game Won",JOptionPane.OK_CANCEL_OPTION,
                    JOptionPane.PLAIN_MESSAGE,bomb,btTexts,btTexts[1]);
            if(n==JOptionPane.YES_OPTION){
                restart();
            }else if(n==JOptionPane.NO_OPTION || n==JOptionPane.CLOSED_OPTION){
                exit();
            }
        }
    }
    
    private static void restart()
    {
        exit();
        createLayout();
    }
    
    private static void exit()
    {
        frame.setVisible(false);
        frame.dispose();
    }
    
}