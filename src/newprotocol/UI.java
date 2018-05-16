package newprotocol;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * @author dxw
 * @date 2018/5/16
 */
public class UI extends JFrame{

    private JPanel contentPane;
    private JTextField textFieldP;
    private JTextField textFieldQ;
    private JTextField textFieldG;
    private JTextArea textArea;
    private JTextField textFieldNumber;

    /**
     * Launch the application.
     */
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    UI frame = new UI();
                    frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    /**
     * Create the frame.
     */
    public UI() {
        setTitle("多方密钥协商");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 800, 300);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);

        JLabel p = new JLabel("p");
        p.setBounds(10, 10, 16, 22);
        contentPane.add(p);

        JLabel q = new JLabel("q");
        q.setBounds(10, 42, 16, 22);
        contentPane.add(q);

        JLabel g = new JLabel("g");
        g.setBounds(10, 74, 16, 22);
        contentPane.add(g);

        textFieldP = new JTextField();
        textFieldP.setBounds(22, 11, 600, 21);
        contentPane.add(textFieldP);
        textFieldP.setColumns(10);

        textFieldQ = new JTextField();
        textFieldQ.setColumns(10);
        textFieldQ.setBounds(22, 43, 600, 21);
        contentPane.add(textFieldQ);

        textFieldG = new JTextField();
        textFieldG.setColumns(10);
        textFieldG.setBounds(22, 75, 600, 21);
        contentPane.add(textFieldG);

        JButton start = new JButton("start");
        start.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                int n = Integer.parseInt(textFieldNumber.getText());
                Data data = Main.dataForUI(n);
                textFieldP.setText(data.getP());
                textFieldQ.setText(data.getQ());
                textFieldG.setText(data.getG());
                textArea.setText(data.getMessage());
            }
        });
        start.setBounds(675, 53, 63, 41);
        contentPane.add(start);

        textArea = new JTextArea();
        textArea.setBounds(20, 106, 700, 145);
        contentPane.add(textArea);
        //JScrollPane scrollPane = new JScrollPane(textArea);
        //scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        contentPane.add(textArea);

        JLabel label = new JLabel("输入用户数");
        label.setBounds(642, 14, 74, 15);
        contentPane.add(label);

        textFieldNumber = new JTextField();
        textFieldNumber.setBounds(718, 11, 30, 21);
        contentPane.add(textFieldNumber);
        textFieldNumber.setColumns(10);
    }

}
