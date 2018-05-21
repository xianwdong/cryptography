package BD;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JTextArea;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class UI extends JFrame {

    private JPanel contentPane;
    private JTextField textFieldP;
    private JTextField textFieldQ;
    private JTextField textFieldG;
    private JTextArea textArea;
    private JTextField textField;
    private JTextField sumTimeLable;
    private JTextField calTimeLable;

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
        setBounds(100, 100, 800, 435);
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
                int n = Integer.parseInt(textField.getText());
                Data data = BD.dataForUI(n);
                textFieldP.setText(data.getP());
                textFieldQ.setText(data.getQ());
                textFieldG.setText(data.getG());
                sumTimeLable.setText(data.getSumTime());
                calTimeLable.setText(data.getCalTime());
                textArea.setText(data.getMessage());
            }
        });
        start.setBounds(675, 53, 63, 41);
        contentPane.add(start);

        textArea = new JTextArea();
        textArea.setBounds(20, 106, 700, 160);
        contentPane.add(textArea);

        JLabel label = new JLabel("\u8F93\u5165\u7528\u6237\u6570\u76EE");
        label.setBounds(642, 14, 96, 15);
        contentPane.add(label);

        textField = new JTextField();
        textField.setBounds(732, 11, 30, 21);
        contentPane.add(textField);
        textField.setColumns(10);

        JLabel sumTime = new JLabel("\u7A0B\u5E8F\u8FD0\u884C\u65F6\u95F4");
        sumTime.setBounds(10, 276, 82, 22);
        contentPane.add(sumTime);

        sumTimeLable = new JTextField();
        sumTimeLable.setBounds(102, 277, 108, 21);
        contentPane.add(sumTimeLable);
        sumTimeLable.setColumns(10);

        JLabel calTime = new JLabel("\u5BC6\u94A5\u534F\u5546\u65F6\u95F4");
        calTime.setBounds(10, 308, 82, 22);
        contentPane.add(calTime);

        calTimeLable = new JTextField();
        calTimeLable.setColumns(10);
        calTimeLable.setBounds(102, 308, 108, 21);
        contentPane.add(calTimeLable);
    }
}


