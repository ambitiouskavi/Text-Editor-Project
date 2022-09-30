import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.plaf.metal.MetalLookAndFeel;
import javax.swing.plaf.metal.OceanTheme;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;

class TextEditor extends JFrame implements ActionListener
{
    JFrame frame;
    JTextArea area;

    JSpinner fontSizeSpinner;
    JLabel fontLabel;


    TextEditor()
    {
     frame =new JFrame("Text Editor"); //frame creation

     //Exception handling
        try {
            // Set metal look and feel
            UIManager.setLookAndFeel("javax.swing.plaf.metal.MetalLookAndFeel");

            // Set theme to ocean
            MetalLookAndFeel.setCurrentTheme(new OceanTheme());
        }
        catch (Exception e) {

        }

     //textarea creation and formatting
     area=new JTextArea();
     area.setLineWrap(true);
     area.setWrapStyleWord(true);
     area.setFont(new Font("Arial",Font.PLAIN,16));
     JScrollPane scrollPane = new JScrollPane(area);
     scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
     scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);

     fontLabel =new JLabel("Font:");
     fontSizeSpinner=new JSpinner();
     fontSizeSpinner.setPreferredSize(new Dimension(50,20));
     //fontSizeSpinner.setBounds(0,0,20,20);
     fontSizeSpinner.setValue(20);
     fontSizeSpinner.addChangeListener(new ChangeListener() {
         public void stateChanged(ChangeEvent e)
         {
             area.setFont(new Font(area.getFont().getFamily(),Font.PLAIN,(int)fontSizeSpinner.getValue()));
         }

     });
     //menubar and menu creation
     JMenuBar menubar=new JMenuBar();
     menubar.setLayout(new FlowLayout());

     JMenu menu1=new JMenu("File");
     JMenuItem item1=new JMenuItem("New");
     JMenuItem item2=new JMenuItem("Open");
     JMenuItem item3=new JMenuItem("Save");
     JMenuItem item4 = new JMenuItem("Exit");

     item1.addActionListener(this);
     item2.addActionListener(this);
     item3.addActionListener(this);
     item4.addActionListener(this);


     menu1.add(item1);
     menu1.add(item2);
     menu1.add(item3);
     menu1.add(item4);


     JMenu menu2=new JMenu("Edit");
     JMenuItem item5=new JMenuItem("Cut");
     JMenuItem item6=new JMenuItem("Copy");
     JMenuItem item7=new JMenuItem("Paste");

     item5.addActionListener(this);
     item6.addActionListener(this);
     item7.addActionListener(this);

     menu2.add(item5);
     menu2.add(item6);
     menu2.add(item7);

     JMenu menu3=new JMenu("Theme");
     JMenuItem darkTheme=new JMenuItem("Dark");
     JMenuItem moonLightTheme=new JMenuItem("MoonLight");
     JMenuItem Default=new JMenuItem("Default");

     darkTheme.addActionListener(this);
     moonLightTheme.addActionListener(this);
     Default.addActionListener(this);
     menu3.add(Default);
     menu3.add(darkTheme);
     menu3.add(moonLightTheme);


     menubar.add(menu1);
     menubar.add(menu2);
     menubar.add(menu3);
     menubar.add(fontLabel);
     menubar.add(fontSizeSpinner);

     //adding menubar and scrollpane to frame
     frame.getContentPane().add(scrollPane);
     frame.setJMenuBar(menubar);
     frame.setSize(750,500);
     frame.setLocationRelativeTo(null);
     frame.setVisible(true);
    }
    public void actionPerformed(ActionEvent e)
    {

    String s = e.getActionCommand();
    if (s.equals("Cut")) {
            area.cut();
    }
    else if (s.equals("Copy")) {
            area.copy();
    }
    else if (s.equals("Paste")) {
            area.paste();
    }
    else if (s.equals("New")) {
        area.setText("");
    }
    else if (s.equals("Save")){
        JFileChooser fileChooser=new JFileChooser();
        int i = fileChooser.showSaveDialog(null);
        if (i == JFileChooser.APPROVE_OPTION)
        {
            File fi = new File(fileChooser.getSelectedFile().getAbsolutePath());
            try{
                FileWriter wr = new FileWriter(fi, false);
                BufferedWriter w = new BufferedWriter(wr);
                w.write(area.getText());
                w.flush();
                w.close();
            }
            catch(Exception evt)
            {
                JOptionPane.showMessageDialog(frame, evt.getMessage());
            }
        }
        else
        {
            JOptionPane.showMessageDialog(frame, "the user cancelled the operation");
        }

    }
    else if (s.equals("Open"))
    {
        JFileChooser fileChooser = new JFileChooser();
        int i = fileChooser.showOpenDialog(null);
        if (i  == JFileChooser.APPROVE_OPTION)
        {
            File file = new File(fileChooser.getSelectedFile().getAbsolutePath());
            try{
                String line="";
                String textFile="";
                FileReader reader = new FileReader(file);
                BufferedReader br = new BufferedReader(reader);
                while ((line = br.readLine()) != null) {
                    textFile = textFile + "\n" + line;
                }
                area.setText(textFile);
            }
            catch(Exception evt)
            {
                JOptionPane.showMessageDialog(frame, evt.getMessage());
            }

        }
        else{
            JOptionPane.showMessageDialog(frame, "the user cancelled the operation");
        }

    }
    else if(s.equals("Dark"))
    {
        area.setBackground(Color.DARK_GRAY);
        area.setForeground(Color.WHITE);
    }
    else if(s.equals("MoonLight"))
    {
        area.setBackground(new Color(107,169,255));
        area.setForeground(Color.BLACK);
    }
    else if (s.equals("Default"))
    {
        area.setBackground(Color.WHITE);
        area.setForeground(Color.BLACK);
    }
    else if(s.equals("Exit")) {
        frame.setVisible(false);
    }
    }
}
public class Main {
    public static void main(String[] args) {
        TextEditor e = new TextEditor();
    }
}