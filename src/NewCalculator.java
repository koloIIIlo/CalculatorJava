import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.StringTokenizer;
import javax.swing.*;

public class NewCalculator extends JFrame implements ActionListener {

    public static void main(String[] args) {
        NewCalculator calculator = new NewCalculator();
        calculator.setVisible(true);
        calculator.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Scanner scn = new Scanner(System.in);
        scn.nextLine();
        System.out.println(list);
    }


    static ArrayList<String> list;
    String[] KEYS = {"(", ")", "",
            "7", "8", "9",
            "4", "5", "6",
            "1", "2", "3",
            ".", "0", ""};
    String[] CLEAR = {"AC", "Backspace"};
    String[] SYMBOl = {"/", "*", "-", "+", "="};

    JButton[] keys = new JButton[KEYS.length];
    JButton[] clear = new JButton[CLEAR.length];
    JButton[] symbol = new JButton[SYMBOl.length];
    JTextField resultText = new JTextField("0");
    JTextField counter = new JTextField("Number counter");

    boolean vBegin = true;
    boolean equals_flag = true;
    boolean isContinueInput = true;

    final int SIZE = 100;


    public NewCalculator() {
        super();
        init();
        this.setBackground(Color.LIGHT_GRAY);
        this.setTitle("My Calc");
        this.setLocation(660, 360);
        this.setResizable(false);
        this.pack();
    }

    public void init() {
        resultText.setHorizontalAlignment(JTextField.RIGHT);
        resultText.setEditable(false);
        list = new ArrayList<>();
        counter.setEditable(false);
        counter.setHorizontalAlignment(JTextField.RIGHT);

        initLayout();  // Interface
        initActionEvent();


    }

    public void initLayout() {
        JPanel keysPanel = new JPanel();
        keysPanel.setLayout(new GridLayout(5, 3, 3, 3));
        for (int i = 0; i < KEYS.length; i++) {
            keys[i] = new JButton(KEYS[i]);
            keysPanel.add(keys[i]);
            keys[i].setForeground(Color.DARK_GRAY);
        }
        for (int i = 0; i < SYMBOl.length; i++) {
            symbol[i] = new JButton(SYMBOl[i]);
            keysPanel.add(symbol[i]);
            symbol[i].setForeground(Color.MAGENTA);
        }
        for (int i = 0; i < CLEAR.length; i++) {
            clear[i] = new JButton(CLEAR[i]);
            keysPanel.add(clear[i]);
            clear[i].setForeground(Color.MAGENTA);
        }


        JPanel text = new JPanel();
        text.setLayout(new BorderLayout());
        text.add("Center", resultText);

        JPanel count = new JPanel();
        count.setLayout(new BorderLayout());
        count.add("South", counter);

        JPanel panel1 = new JPanel();
        panel1.setLayout(new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.BOTH;
        gbc.insets = new Insets(3, 3, 3, 3);

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 1;
        gbc.gridheight = 1;
        panel1.add(symbol[0], gbc);//   / - div

        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        gbc.gridheight = 1;
        panel1.add(clear[0], gbc); // AC

        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 1;
        gbc.gridheight = 1;
        panel1.add(symbol[1], gbc); // * - multi

        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.gridwidth = 2;
        gbc.gridheight = 2;
        panel1.add(clear[1], gbc);  // backspace

        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 1;
        gbc.gridheight = 1;
        panel1.add(symbol[2], gbc); // -

        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.ipady = 33;
        panel1.add(symbol[3], gbc);  // +

        gbc.gridx = 1;
        gbc.gridy = 3;
        gbc.gridwidth = 2;
        gbc.ipady = 33;
        panel1.add(symbol[4], gbc); // =


        getContentPane().setLayout(new BorderLayout(3, 3));
        getContentPane().add("Center", keysPanel);
        getContentPane().add("North", text);
        getContentPane().add("East", panel1);
        getContentPane().add("South", count);


    }


    public void initActionEvent() {
        for (int i = 0; i < KEYS.length; i++) {
            keys[i].addActionListener(this);
        }
        for (int i = 0; i < CLEAR.length; i++) {
            clear[i].addActionListener(this);
        }
        for (int i = 0; i < SYMBOl.length; i++) {
            symbol[i].addActionListener(this);
        }
    }


    @Override
    public void actionPerformed(ActionEvent e) {
        String label = e.getActionCommand();
        if (label.equals(CLEAR[1])) {
            handleBackspace();
        } else if (label.equals(CLEAR[0])) {    //работа Ac - очистка полностью всего
            list.clear();
            resultText.setText("0");
            vBegin = true;
            equals_flag = true;
        } else {
            handle(label);
        }
    }

    //Работа Backspace
    private void handleBackspace() {
        String text = resultText.getText();
        list.add(text);
        int i = text.length();
        if (i > 0) {
            text = text.substring(0, i - 1);
            list.remove(list.size() - 1);
            if (text.length() == 0) {
                list.clear();
                resultText.setText("0");
                vBegin = true;
                equals_flag = true;
            } else {
                resultText.setText(text);
            }
        }
    }

    public void handle(String key) {
        String text = resultText.getText();
        if (!equals_flag) {
            list.add(text);
            vBegin = false;
        }

        if (!list.isEmpty()) {  // Sorting
            TipChecker(list.get(list.size() - 1), key);
        } else {
            TipChecker("#", key);
        }

        if (isContinueInput && "π0123456789.()+-*/^".contains(key)) {
            list.add(key);}

        if (isContinueInput && "π0123456789.()+-*/^".contains(key)) {
            if (!equals_flag && ("+-*/^".contains(key))) {
                vBegin = false;
                equals_flag = true;
                printText(key);
            } else if (!equals_flag && ("π0123456789.()").contains(key)) {
                vBegin = true;
                equals_flag = true;
                printText(key);
            } else {
                printText(key);
            }
        }else if (isContinueInput && equals_flag && key.equals("=")) {
            isContinueInput = false;
            equals_flag = false;
            vBegin = true;
            process(resultText.getText());
            list.clear();
        }
        isContinueInput = true;
    }

    private void printText(String key) {
        if (vBegin) {
            resultText.setText(key);
        } else {
            resultText.setText(resultText.getText() + key);
        }
        vBegin = false;
    }

    private void TipChecker(String tipcom1, String tipcom2) {
        int Tipcode = 0;
        int tiptype1 = 0, tiptype2 = 0;

        if (tipcom1.compareTo("#") == 0   // Проверка первого введеного символа и запрет некоторых
                && (tipcom2.compareTo("/") == 0 ||
                tipcom2.compareTo("*") == 0 ||
                tipcom2.compareTo("+") == 0 ||
                tipcom2.compareTo(")") == 0)) {
            Tipcode = -1;
        } else if (tipcom1.compareTo("#") != 0) { // Когда уже есть символ и нужно разрешить вводить только некоторые как следующий
            if (tipcom1.compareTo("(") == 0) {
                tiptype1 = 1;
            } else if (")".contains(tipcom1)) {
                tiptype1 = 2;
            } else if (".".contains(tipcom1)) {
                tiptype1 = 3;
            } else if ("1234567890".contains(tipcom1)) {
                tiptype1 = 4;
            } else if ("-".contains(tipcom1)) {
                tiptype1 = 5;
            }  else if ("*/+".contains(tipcom1)) {
                tiptype1 = 8;
            }

            // определение второго типа знаков
            if ("(".contains(tipcom2)) {
                tiptype2 = 1;
            } else if (")".contains(tipcom2)) {
                tiptype2 = 2;
            } else if (".".contains(tipcom2)) {
                tiptype2 = 3;
            } else if ("1234567890".contains(tipcom2)) {
                tiptype2 = 4;
            } else if ("-".contains(tipcom2)) {
                tiptype2 = 5;
            } else if ("*/+".contains(tipcom2)) {
                tiptype2 = 8;
            }

            switch (tiptype1) {
                case 1: //(
                    if (tiptype2 == 2 || tiptype2 == 3 ||
                             tiptype2 == 8)
                        Tipcode = 1;
                    break;
                case 2://)
                    if (tiptype2 == 3 )
                        Tipcode = 2;
                    break;
                case 3://.
                    if (tiptype2 == 1 || tiptype2 == 3)
                        Tipcode = 3;
                    break;
                case 4://0123456789
                    if (tiptype2 == 1 )
                        Tipcode = 4;
                    break;
                case 5://-
                    if (tiptype2 == 2 || tiptype2 == 3
                        || tiptype2 == 5 ||tiptype2 == 8)
                        Tipcode = 5;
                    break;

                case 8://+*/
                    if (tiptype2 == 2 || tiptype2 == 3
                        || tiptype2 == 5 || tiptype2 == 8)
                        Tipcode = 6;
                    break;
            }
        }

        if (Tipcode == 0 && ".".contains(tipcom2)) {
            int tip_points = 0;
            for (String s : list) {
                if (s.equals(".")) {
                    tip_points++;
                }
                if (s.equals("*") || s.equals("/") || s.equals("+") || s.equals("-")
                        || s.equals("^") || s.equals("(") || s.equals(")")) {
                    tip_points = 0;
                }
            }
            tip_points++;

            if (tip_points > 1) {
                Tipcode = 7;
            }
        }

        if (Tipcode == 0 && ")".contains(tipcom2)) {
            int tip_right_bracket = 0;
            for (String s : list) {
                if (s.equals("(")) {
                    tip_right_bracket++;
                }
                if (s.equals(")")) {
                    tip_right_bracket--;
                }
            }
            if (tip_right_bracket == 0) {
                Tipcode = 8;
            }
        }

        if (Tipcode == 0 && "=".contains(tipcom2)) {
            int tip_bracket = 0;
            for (String s : list) {
                if (s.equals("(")) {
                    tip_bracket++;
                }
                if (s.equals(")")) {
                    tip_bracket--;
                }
            }
            if (tip_bracket > 0) {
                Tipcode = 9;
            } else if (tip_bracket == 0) {
                if ("*/-+".contains(tipcom1)) {
                    Tipcode = 10;
                }
            }
        }

        if (Tipcode != 0) {
            isContinueInput = false;
        }
    }


    public void process(String str){
        int weightPlus = 0, topOp=0, topNum = 0, flag = 1,  weightTemp;

        int[] weight;
        float[] number;

        char ch;
        char ch_gai;
        char[] operator;

        String num;
        weight = new int[SIZE];
        number = new float[SIZE];
        operator = new char[SIZE];



        StringTokenizer expToken = new StringTokenizer(str,"/*-+()^");
        counter.setText(String.valueOf(expToken.countTokens()));// количество цифр

        int i =0;
        while (i < str.length()){
            ch = str.charAt(i);
            if(i == 0) {
               if (ch == '-')
                   flag = -1;
            }else if ((str.charAt(i-1) =='(' || str.charAt(i-1) =='*' || str.charAt(i-1) =='+' || str.charAt(i-1) =='/') && ch =='-') {
                flag = -1;
            }
            if(ch <= '9' && ch >='0' || ch == '.'){
                num = expToken.nextToken();
                ch_gai = ch;
                while(i<str.length() && (ch_gai <= '9' && ch_gai >='0' || ch_gai == '.')){
                    ch_gai = str.charAt(i++);
                }

                if(i >= str.length())
                    i-=1;
                else{
                    i-=2;
                }
                if(num.compareTo(".")==0)
                    number[topNum++] = 0;
                else {
                    number[topNum++] = Float.parseFloat(num) * flag;
                    flag = 1;
                }
            }


            if(ch =='(')
                weightPlus +=4;
            if(ch ==')')
                weightPlus -=4;
            if((ch =='/' || ch =='*' || ch =='-'
               || ch == '+') && flag == 1) {
                weightTemp = switch (ch) {
                    case '+', '-' -> 1 + weightPlus;
                    default -> 2 + weightPlus;
                };

                if(topOp == 0 || weight[topOp-1]< weightTemp){
                    weight[topOp] = weightTemp;
                    operator[topOp] =ch;
                    topOp++;
                }else {
                    while(topOp > 0 && weight[topOp-1] >= weightTemp){
                        switch (operator[topOp - 1]) {
                            case'+':
                                number[topNum-2] += number[topNum-1];
                                break;
                            case'-':
                                number[topNum-2] -= number[topNum-1];
                                break;
                            case'*':
                                number[topNum-2] *= number[topNum-1];
                                break;
                            case'/':
                                if (topNum-1 ==0){
                                    return;
                                }
                                number[topNum-2] /= number[topNum-1];
                                break;
                        }
                        topNum--;
                        topOp--;
                    }
                    weight[topOp] = weightTemp;
                    operator[topOp] = ch;
                    topOp++;
                }
            }
            i++;
        }

        while (topOp > 0){
            switch (operator[topOp-1]){
                case'+':
                    number[topNum-2] += number[topNum-1];
                    break;
                case'-':
                    number[topNum-2] -= number[topNum-1];
                    break;
                case'*':
                    number[topNum-2] *= number[topNum-1];
                    break;
                case'/':
                    if (topNum-1 ==0){
                        return;
                    }
                    number[topNum-2] /= number[topNum-1];
                    break;
            }
            topNum--;
            topOp--;
        }
        resultText.setText(String.valueOf(number[0]));
    }
}
