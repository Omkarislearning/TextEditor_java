import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Scanner;

public class MyTextApp extends JFrame implements ActionListener
{
    JTextArea area=new JTextArea();
    JScrollPane scrollPane=new JScrollPane(area);
    JSpinner spinner=new JSpinner();
    JLabel label = new JLabel(" Font Size");
    JMenuBar menuBar =new JMenuBar();
    JMenu File;
    JMenuItem New_File;    //File
    JMenuItem Save;        //File
    JMenuItem Open;        //File
    JMenuItem Exit;
    JButton button1;
    JButton button2;
    JComboBox jComboBox;

    Font myfont=new Font("Arial",Font.PLAIN,7);
     JFileChooser fileChooser;

    MyTextApp()
    {
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setTitle("MyEditor");
        this.setSize(1000,500);
        this.setLayout(new FlowLayout());
        this.setLocationRelativeTo(null);
        this.setResizable(false);
        getContentPane().setBackground(Color.white);

        area.setFont(new Font("Arial",Font.PLAIN,25));
        area.setLineWrap(true);
        area.setWrapStyleWord(true);
        scrollPane.setPreferredSize(new Dimension(950,370));
        scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);

        spinner.setPreferredSize(new Dimension(50,25));
        spinner.setLocation(50,100);
        spinner.setValue(25);
        spinner.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e)
            {
                area.setFont(new Font(area.getFont().getFamily(),Font.PLAIN,(int)spinner.getValue()));

            }
        });

        label.setFont(new Font("Arial",Font.PLAIN,25));

        File = new JMenu("File");


        New_File = new JMenuItem("New File");
        Save = new JMenuItem("Save");
        Open = new JMenuItem("Open");
        Exit = new JMenuItem("Exit");



        New_File.addActionListener(this);
        Save.addActionListener(this);
        Open.addActionListener(this);
        Exit.addActionListener(this);

        File.add(New_File);
        File.add(Save);
        File.add(Open);
        File.add(Exit);

        menuBar.add(File);
        File.setForeground(Color.WHITE);
        File.setBackground(Color.DARK_GRAY);

        menuBar.setBackground(Color.DARK_GRAY);


        button1=new JButton("Font Color");
        button1.setBackground(Color.DARK_GRAY);
        button1.setForeground(Color.WHITE);

        button2=new JButton("BackGround-Color");
        button2.setBackground(Color.DARK_GRAY);
        button2.setForeground(Color.WHITE);

        button1.setFont(myfont);
        button2.setFont(myfont);
        button1.setFocusable(true);
        button2.setFocusable(true);
        button1.addActionListener(this);
        button2.addActionListener(this);

        String [] fonts=GraphicsEnvironment.getLocalGraphicsEnvironment().getAvailableFontFamilyNames();
        jComboBox=new JComboBox(fonts);
        jComboBox.addActionListener(this);
        jComboBox.setFont(myfont);
        jComboBox.setForeground(Color.DARK_GRAY);
        jComboBox.setBackground(Color.white);

        this.add(jComboBox);
        this.add(button1);
        this.add(button2);
        this.setJMenuBar(menuBar);
        this.add(label);
        this.add(spinner);
        this.add(scrollPane);
        this.setVisible(true);


    }

    @Override
    public void actionPerformed(ActionEvent e)
    {
        Color color;
        if(e.getSource()==button1)
        {
             color = JColorChooser.showDialog(null,"Choose a Color", java.awt.Color.black);
            area.setForeground(color);

        }
         if(e.getSource()==button2)
        {
            color = JColorChooser.showDialog(null,"Choose a Color", java.awt.Color.black);
            JColorChooser colorChooser = new JColorChooser();
            area.setBackground(color);
        }
         if(e.getSource()==jComboBox)
         {
             area.setFont(new Font((String)jComboBox.getSelectedItem(),Font.PLAIN,area.getFont().getSize()));
         }

         if(e.getSource()==New_File)

         {
             fileChooser.showOpenDialog(null);
         }

        if(e.getSource()==Save)
        {
            JFileChooser fileChooser=new JFileChooser();
            fileChooser.setCurrentDirectory(new File("."));
            int response=fileChooser.showSaveDialog(null);
            if(response==JFileChooser.APPROVE_OPTION)
            {
                PrintWriter fileout=null;
                File file = new File(String.valueOf(fileChooser.getSelectedFile().getAbsoluteFile()));
                try {
                    fileout = new PrintWriter(file);
                    fileout.println(area.getText());
                } catch (FileNotFoundException ex) {
                    ex.printStackTrace();
                }
                finally {
                    fileout.close();
                }
            }

        }
        if(e.getSource()==Open)
        {
            JFileChooser fileChooser=new JFileChooser();
            fileChooser.setCurrentDirectory(new File("."));
            FileNameExtensionFilter filter=new FileNameExtensionFilter("TEXT","TXT","HTML","JAVA");
            fileChooser.setFileFilter(filter);

            int response=fileChooser.showOpenDialog(null);

            if (response==JFileChooser.APPROVE_OPTION)
            {
                File file=new File(String.valueOf(fileChooser.getSelectedFile().getAbsoluteFile()));
                Scanner filein=null;
                try
                {
                    filein=new Scanner(file);
                    if(file.isFile())
                    {
                        while(filein.hasNextLine())
                        {
                            String line=filein.nextLine()+"\n";
                            area.append(line);
                        }
                    }

                } catch (FileNotFoundException ex) {
                    ex.printStackTrace();
                }
                finally {
                    filein.close();
                }


            }


        }
        if(e.getSource()==Exit)
        {
            System.exit(0);
        }

    }
}
