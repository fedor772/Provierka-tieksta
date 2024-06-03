import javax.swing.*;
import java.awt.Font;
import java.awt.event.*;
import java.awt.FlowLayout;
import java.util.ArrayList;
import com.fedor.RequestNetwork;
public class Main {
  public static boolean isRussian = true;
public static void main(String args[]) {
JFrame a = new JFrame("Проверка текста");
  //Кнопка для проверки
JButton check = new JButton("Проверить");
check.setBounds(10,70,120,20);
a.add(check);
a.setSize(300,300);
a.setLayout(null);
a.setVisible(true);
  //Поле ввода текста
  JTextField d = new JTextField();
d.setBounds(10,30,230,30);
a.add(d);
a.setSize(300,300);
a.setLayout(null);
a.setVisible(true);
  //Подсказка ввода
   JLabel hint = new JLabel("Введите текст для проверки");
  hint.setBounds(10,0,300,30);
  a.add(hint);
  a.setSize(300,300);
  a.setLayout(null);
  a.setVisible(true);
  //Поле вывода результата
  JLabel res = new JLabel("Текст");
res.setBounds(10,100,300,30);
a.add(res);
a.setSize(400,400);
a.setLayout(null);
a.setVisible(true);
  //Выбор языка
  String[] langs = {
    "Русский язык",
    "Английский язык"
  };
  JComboBox lang = new JComboBox(langs);
  lang.setBounds(140,70,100,30);
  a.add(lang);
  a.setSize(300,300);
  a.setLayout(null);
  a.setVisible(true);
    //Событие выбора языков
  ActionListener actionListener = new ActionListener() {
    public void actionPerformed(ActionEvent e) {
        isRussian = eql(e.getActionCommand(), "Русский язык");
      }
    };
  lang.addActionListener(actionListener);
  //Событие при нажатии кнопки
    check.addActionListener(new ActionListener() {
    public void actionPerformed(ActionEvent e) {
      final String correct = "{\"status\":true,\"response\":{\"result\":true,\"errors\":[]}}";
      try {
      if (eql(check(replaceSpacesToPluses(d.getText())), correct)) {
        res.setText("В тексте нет ошибок");
      } else {
        res.setText("В тексте есть ошибки");
      }
      } catch (Exception ex) {
        res.setText("Ошибка");
      }
      }
    });
}
  public static String check(String d) throws Exception {
    RequestNetwork rn = new RequestNetwork();
    if (isRussian) {
      return rn.getHTML("https://api.textgears.com/spelling?text=" + d + ".&language=ru-RU&whitelist=&dictionary_id=&ai=1&key=SyBOo3FE9fH3xS4D");
    } else {
    return rn.getHTML("https://api.textgears.com/spelling?text=" + d + ".&language=en-GB&whitelist=&dictionary_id=&ai=1&key=SyBOo3FE9fH3xS4D");
    }
  }
  public static boolean eql(String a, String b) {
return a.equals(b);
  }
  public static String replaceSpacesToPluses(String replaceble) {
    return replaceble.replace(" ", "+");
  }
}