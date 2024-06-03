import javax.swing.*;
import java.awt.Font;
import java.awt.event.*;
import java.awt.FlowLayout;
import java.util.ArrayList;
import com.fedor.RequestNetwork;
import com.fedor.Files;
import com.fedor.JsonParser;

public class Main {
  public static boolean isRussian = true;

  public static void main(String args[]) {
    JFrame a = new JFrame("Проверка текста");
    // Кнопка для проверки
    JButton check = new JButton("Проверить");
    check.setBounds(10, 70, 120, 20);
    a.add(check);
    // Поле ввода текста
    JTextField d = new JTextField();
    d.setBounds(10, 30, 230, 30);
    a.add(d);
    // Подсказка ввода
    JLabel hint = new JLabel("Введите текст для проверки");
    hint.setBounds(10, 0, 300, 30);
    a.add(hint);
    // Поле вывода результата
    JTextArea res = new JTextArea("Текст");
    res.setBounds(10, 100, 300, 300);
    res.setLineWrap(true);
    res.setWrapStyleWord(true);
    a.add(res);
    // Выбор языка
    String[] langs = {
      "Русский язык",
      "Английский язык"
    };
    JComboBox lang = new JComboBox(langs);
    lang.setBounds(140, 70, 100, 30);
    a.add(lang);
    a.setSize(300, 300);
    a.setLayout(null);
    a.setVisible(true);
    // Событие выбора языков
    ActionListener actionListener = new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        isRussian = eql(e.getActionCommand(), "Русский язык");
      }
    };
    lang.addActionListener(actionListener);
    JsonParser jp = new JsonParser();
    // Событие при нажатии кнопки
    check.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        final String correct = "{\"status\":true,\"response\":{\"result\":true,\"errors\":[]}}";
        try {
          if (eql(check(replaceSpacesToPluses(d.getText())), correct)) {
            res.setText("В тексте нет ошибок");
          } else {
            res.setText(jp.jmain(check(replaceSpacesToPluses(d.getText()))));
          }
        } catch (Exception ex) {
          res.setText("Ошибка");
        }
      }
    });
  }

  public static String check(String d) throws Exception {
    RequestNetwork rn = new RequestNetwork();
    Files f = new Files();
    String key = f.readfile("key.config");
    if (isRussian) {
      return rn.getHTML("https://api.textgears.com/spelling?text=" + d
          + ".&language=ru-RU&whitelist=&dictionary_id=&ai=1&key=" + key);
    } else {
      return rn.getHTML("https://api.textgears.com/spelling?text=" + d
          + ".&language=en-GB&whitelist=&dictionary_id=&ai=1&key=" + key);
    }
  }

  public static boolean eql(String a, String b) {
    return a.equals(b);
  }

  public static String replaceSpacesToPluses(String replaceble) {
    return replaceble.replace(" ", "+");
  }
}