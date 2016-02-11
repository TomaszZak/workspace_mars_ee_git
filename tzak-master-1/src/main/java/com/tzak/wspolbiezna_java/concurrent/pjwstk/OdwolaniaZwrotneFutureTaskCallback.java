package com.tzak.wspolbiezna_java.concurrent.pjwstk;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.concurrent.*;


class  OdwolaniaZwrotneFutureTaskCallback<V> extends  FutureTask<V> {

  public OdwolaniaZwrotneFutureTaskCallback(Callable<V> callable) {
    super(callable);
  }

  // Bezpośrednia konstrukcja (za pomocą konstruktorów klasy) zadań jako FutureTask ma pewną zaletę wobec submit(Callable) czy submit(Runnable):  w łatwy sposób możemy dostarczać odwołań  zwrotnych (callback) reagujących na zakończenie zadania. Klasa FutureTask dostarcza bowiem chronionej metody done(), która jest wywoływana po zakończeniu zadania.
  //metoda done(), która jest wywoływana po zakończeniu zadania
  public void done() {
    String result = "Wynik: ";
    if (isCancelled()) result += "Cancelled - Anulowano wykonanie zadania";
    else try {
      result += get();
    } catch(Exception exc) {
        result += exc.toString();
    }
    JOptionPane.showMessageDialog(null, result);
  }

}


@SuppressWarnings("serial")
class SimpleCallback extends JFrame {

  JTextField input = new JTextField(40),
             progress = new JTextField(40);

  String toReverse;

  Callable<String> reverseTask = new Callable<String>() {
    public String call() throws Exception {
      Thread t = Thread.currentThread();
      if (toReverse == null || toReverse.trim().equals(""))
        throw new IllegalArgumentException("Set string to reverse");
      if (t.isInterrupted()) return null;
      char[] org = toReverse.toCharArray();
      StringBuffer out = new StringBuffer();
      if (t.isInterrupted()) return null;
      for (int i = org.length-1; i>=0; i--) {
        Thread.sleep(500);
        out.append(org[i]);
        if (t.isInterrupted()) return null;
        progress.setText(out.toString());
        if (t.isInterrupted()) return null;
      }
      return out.toString();
    }
  };

  ExecutorService exec = Executors.newSingleThreadExecutor();
  OdwolaniaZwrotneFutureTaskCallback<String> ft;


  public SimpleCallback() {
    Font f = new Font("Dialog", Font.PLAIN, 16);
    input.setFont(f);
    progress.setFont(f);
    JPanel p = new JPanel();
    JButton b = new JButton("Start");
    b.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        ft = new OdwolaniaZwrotneFutureTaskCallback<String>(reverseTask);
        toReverse = input.getText();
        exec.execute(ft);
      }
    });
    p.add(b);
    b = new JButton("Stop");
    b.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        if (ft != null) ft.cancel(true);
      }
    });
    p.add(b);
    Container cp = getContentPane();
    cp.setLayout(new BoxLayout(cp, BoxLayout.Y_AXIS));
    add(input);
    add(p);
    add(progress);
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    pack();
    setVisible(true);
  }





  public static void main(String[] args) {
     new SimpleCallback();
  }

}