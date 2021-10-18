// Java program to create a blank text
// field of definite number of columns.
import java.awt.event.*;
import javax.swing.*;
import        javax.swing.JFrame;
import javax.swing.JTextField;

class hw01_01_001 extends JFrame implements ActionListener {

    // JTextField
    static JTextField t01;
    static JTextField t02;

    // JFrame
    static JFrame f;

    // JButton
    static JButton b01;

    // label to display text
    static JLabel l01;
    static JLabel l02;

    // default constructor
    hw01_01_001()
    {
    }

    // main class
    public static void main(String[] args)
    {
        // create a new frame to store text field and button
        f = new JFrame("from F to C");

        // create a label to display text
        l01 = new JLabel("from F");
        l02 = new JLabel("to C");
        t02 = new JTextField(16);

        // create a new button
        b01 = new JButton("Calculate");

        // create a object of the text class
        hw01_01_001 te = new hw01_01_001();

        // addActionListener to button
        b01.addActionListener(te);

        // create a object of JTextField with 16 columns
        t01 = new JTextField(16);

        JPanel p = new JPanel();

        // add buttons and textfield to panel
        p.add(l01);
        p.add(t01);
        p.add(l02);
        p.add(t02);
        p.add(b01);

        // add panel to frame
        f.add(p);

        // set the size of frame
        f.setSize(300, 300);

        f.show();
     }

    public void actionPerformed(ActionEvent e)
    {
         String text = t01.getText();
        double outDouPrint = Double.parseDouble(text);
        outDouPrint = (outDouPrint-32)*5/9;
        String outStrPrint=String.valueOf(outDouPrint);
        String s = e.getActionCommand();
        if (s.equals("Calculate")) {
            t02.setText(outStrPrint);
        }
    }
}
