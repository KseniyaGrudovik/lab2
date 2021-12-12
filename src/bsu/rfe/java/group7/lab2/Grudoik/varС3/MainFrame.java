package bsu.rfe.java.group7.lab2.Grudoik.varС3;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

import static java.lang.Math.*;

public class MainFrame extends JFrame{
    private static final int width = 1000;
    private static final int height = 500;

    private JTextField resultField;
    private JTextField xField;
    private JTextField yField;
    private JTextField zField;
    private JTextField memoryTextField;

    private JLabel image;
    int formulaNumber = 1;
    private int memoryId = 1;

    private ButtonGroup radioButtons = new ButtonGroup();
    private ButtonGroup radioMemoryButtons = new ButtonGroup();

    private Box formulaType = Box.createHorizontalBox();
    private Box hBoxMemoryType = Box.createHorizontalBox();

    private Double mem1 = new Double(0);
    private Double mem2 = new Double(0);
    private Double mem3 = new Double(0);

    public Double formula1(Double x, Double y, Double z)
    {
        if(x*x+sin(z)+exp(cos(z)) < 0){
            JOptionPane.showMessageDialog(MainFrame.this,
                    "Выражение под корнем должно быть положительным или равняться 0",
                    "Ошибка ввода", JOptionPane.WARNING_MESSAGE);
            return 0.0;
        }

        if(y <= 0){
            JOptionPane.showMessageDialog(MainFrame.this,
                    "у должен быть положительным",
                    "Ошибка ввода", JOptionPane.WARNING_MESSAGE);
            return 0.0;
        }

        return sin(log(y)+sin(PI*y*y))*pow(x*x+sin(z)+exp(cos(z)), 0.25);
    }

    public Double formula2(Double x, Double y, Double z){
        if (x <= 0){
            JOptionPane.showMessageDialog(MainFrame.this,
                    "x должен быть положительным",
                    "Ошибка ввода", JOptionPane.WARNING_MESSAGE);
            return 0.0;
        }

        if(y <= 0){
            JOptionPane.showMessageDialog(MainFrame.this,
                    "у должен быть положительным",
                    "Ошибка ввода", JOptionPane.WARNING_MESSAGE);
            return 0.0;
        }

        return (1+pow(x, z)+pow(log(y), 2))*(1-sin(y*z))/(sqrt(pow(x, 3)+1));
    }

    private void addMemoryRadioButton(String buttonName, final int memoryId){
        JRadioButton button = new JRadioButton(buttonName);

        button.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent event) {
                MainFrame.this.memoryId = memoryId;
                if (memoryId == 1) memoryTextField.setText(mem1.toString());
                if (memoryId == 2) memoryTextField.setText(mem2.toString());
                if (memoryId == 3) memoryTextField.setText(mem3.toString());
            }
        });

        radioMemoryButtons.add(button);
        hBoxMemoryType.add(button);
    }

    private void addRadioButton(String name, final int formulaNumber){
        JRadioButton button = new JRadioButton(name);
        button.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent event) {
                MainFrame.this.formulaNumber = formulaNumber;
                if(formulaNumber == 1) image.setIcon(new ImageIcon(MainFrame.class.getResource("formula_1.jpg")));
                if(formulaNumber == 2) image.setIcon(new ImageIcon(MainFrame.class.getResource("formula_2.jpg")));
            }
        });
        radioButtons.add(button);
        formulaType.add(button);
    }

    public MainFrame(){
        super("Вычисление формулы");
        setSize(width, height);
        Toolkit kit = Toolkit.getDefaultToolkit();
        setLocation((kit.getScreenSize().width - width)/2, (kit.getScreenSize().height - height)/2);

        Box picture = Box.createHorizontalBox();                           // область с картинкой
        picture.add(Box.createVerticalGlue());
        picture.add(Box.createHorizontalGlue());
        image = new JLabel(new ImageIcon(MainFrame.class.getResource("formula_1.jpg")));
        picture.add(image);
        picture.add(Box.createHorizontalGlue());
        picture.setBorder(BorderFactory.createLineBorder(Color.WHITE));

        formulaType.add(Box.createHorizontalGlue());                      //область с выбором формул
        addRadioButton("Формула 1", 1);
        addRadioButton("Формула 2", 2);
        radioButtons.setSelected(radioButtons.getElements().nextElement().getModel(), true);
        formulaType.add(Box.createHorizontalGlue());
        formulaType.setBorder(BorderFactory.createLineBorder(Color.WHITE));

        Box data = Box.createHorizontalBox();             //Область с полями ввода значений

        xField = new JTextField("0", 10);
        xField.setMaximumSize(xField.getPreferredSize());
        yField = new JTextField("0", 10);
        yField.setMaximumSize(yField.getPreferredSize());
        zField = new JTextField("0", 10);
        zField.setMaximumSize(zField.getPreferredSize());

        JLabel xLabel = new JLabel("X:");
        JLabel yLabel = new JLabel("Y:");
        JLabel zLabel = new JLabel("Z:");

        data.add(Box.createHorizontalGlue());
        data.add(xLabel);
        data.add(Box.createHorizontalStrut(10));
        data.add(xField);
        data.add(Box.createHorizontalStrut(100));
        data.add(yLabel);
        data.add(Box.createHorizontalStrut(10));
        data.add(yField);
        data.add(Box.createHorizontalStrut(100));
        data.add(zLabel);
        data.add(Box.createHorizontalStrut(10));
        data.add(zField);
        data.add(Box.createHorizontalGlue());
        data.setBorder(BorderFactory.createLineBorder(Color.WHITE));

        Box resultArea = Box.createHorizontalBox();                 //область для результата
        resultArea.add(Box.createHorizontalGlue());
        JLabel resultLabel = new JLabel("Результат:");
        resultField = new JTextField("0", 15);
        resultField.setMaximumSize(resultField.getPreferredSize());
        resultArea.add(resultLabel);
        resultArea.add(Box.createHorizontalStrut(10));
        resultArea.add(resultField);
        resultArea.add(Box.createHorizontalGlue());
        resultArea.setBorder(BorderFactory.createLineBorder(Color.WHITE));

        Box actions = Box.createHorizontalBox();
        JButton calcButton = new JButton("Вычислить");
        calcButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent event) {
                //Преобразование введенных строк в числа с плавающей точкой может
                //спровоцировать исключительную ситуацию при неправильном формате чисел,
                //поэтому необходим блок try-catch
                try{
                    Double x = Double.parseDouble(xField.getText());
                    Double y = Double.parseDouble(yField.getText());
                    Double z = Double.parseDouble(zField. getText());
                    Double result;

                    if(formulaNumber == 1){
                        result = formula1(x, y, z);
                    }
                    else{
                        result = formula2(x, y, z);
                    }
                    //Установить текст надписи равным результату
                    resultField.setText(result.toString());
                }
                catch (NumberFormatException ex){
                    JOptionPane.showMessageDialog(MainFrame.this,
                            "Ошибка в формате записи чмслас плавающей точкой",
                            "Ошибочный формат числа", JOptionPane.WARNING_MESSAGE);
                }
            }
        });
        JButton cleanButton = new JButton("Очистить");
        cleanButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ev) {
                xField.setText("0");
                yField.setText("0");
                zField.setText("0");
                resultField.setText("0");
            }
        });

        actions.add(Box.createHorizontalGlue());
        actions.add(calcButton);
        actions.add(Box.createHorizontalStrut(10));
        actions.add(cleanButton);
        actions.add(Box.createHorizontalGlue());

        hBoxMemoryType.add(Box.createHorizontalGlue());
        addMemoryRadioButton("Память 1", 1);
        addMemoryRadioButton("Память 2", 2);
        addMemoryRadioButton("Память 3", 3);
        radioMemoryButtons.setSelected(radioMemoryButtons.getElements().nextElement().getModel(), true);
        hBoxMemoryType.add(Box.createHorizontalGlue());

        Box memoryResult = Box.createHorizontalBox();
        memoryResult.add(Box.createHorizontalGlue());
        JButton MC = new JButton("MC");

        MC.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ev) {
                if(memoryId == 1) mem1 = (double) 0;
                if(memoryId == 2) mem2 = (double) 0;
                if(memoryId == 3) mem3 = (double) 0;
                memoryTextField.setText("0.0");
            }
        });

        JButton MPlus = new JButton("M+");
        MPlus.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                try{
                    Double result = Double.parseDouble(resultField.getText());

                    if(memoryId == 1) {mem1 += result; memoryTextField.setText(mem1.toString());}
                    if(memoryId == 2) {mem2 += result; memoryTextField.setText(mem2.toString());}
                    if(memoryId == 3) {mem3 += result; memoryTextField.setText(mem3.toString());}
                }catch(NumberFormatException ex){
                    JOptionPane.showMessageDialog(MainFrame.this,
                            "Ошибка в формате записи числа с плавающей точкой",
                            "Ошибочный формат числа", JOptionPane.WARNING_MESSAGE);
                }
            }
        });

        memoryTextField = new JTextField("0.0", 15);
        memoryTextField.setMaximumSize(memoryTextField.getPreferredSize());

        memoryResult.add(Box.createHorizontalStrut(10));
        memoryResult.add(MC);
        memoryResult.add(Box.createHorizontalStrut(10));
        memoryResult.add(memoryTextField);
        memoryResult.add(Box.createHorizontalStrut(10));
        memoryResult.add(MPlus);
        memoryResult.add(Box.createHorizontalGlue());

        Box contentBox = Box.createVerticalBox();
        contentBox.add(Box.createVerticalGlue());
        contentBox.add(picture);
        contentBox.add(Box.createVerticalGlue());
        contentBox.add(formulaType);
        contentBox.add(Box.createVerticalGlue());
        contentBox.add(data);
        contentBox.add(Box.createVerticalGlue());
        contentBox.add(resultArea);
        contentBox.add(Box.createVerticalGlue());
        contentBox.add(actions);
        contentBox.add(Box.createVerticalGlue());
        contentBox.add(hBoxMemoryType);
        contentBox.add(Box.createVerticalGlue());
        contentBox.add(memoryResult);


        getContentPane().add(contentBox, BorderLayout.CENTER);

    }

    public static void main(String[] args) {
        MainFrame frame = new MainFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }
}