package com.tzak.wspolbiezna_java.concurrent.pjwstk.asynchroniczna_obsługa_wynikow;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import javax.swing.JFrame;
import javax.swing.JLabel;

public class MainTestowa {

	public static void main(String[] args) {
		  ExecutorService exec = Executors.newFixedThreadPool(3);
		  ResultHandler<Integer> handler = new ResultHandler<Integer>();
		  SumTaskA task;
		try {
			task = new SumTaskA("Task 1", handler, "handleResults");
			exec.execute(task.getTask());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		  
	}
	
}

class ResultHandler<V> {

	  int x = 400, y = 50;
	  public void handleResult(V result, Exception exc) {
	    String msg;
	    if (exc != null) msg = exc.toString();
	    else msg = "Wynik = " + result;
	    JFrame f = new JFrame("Task results");
	    JLabel lab = new JLabel("        " + msg);
	    f.add(lab);
	    f.setBounds(x, y, 300, 100);
	    f.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
	    x+=50;
	    y+=50;
	    f.setVisible(true);

	  }
	}

class SumTaskA extends AbstractTask<Integer> {

    public SumTaskA(String taskName, ResultHandler h, String handlerMethod )
                 throws Exception
    {
      super(taskName, h, handlerMethod);
    }

    public Integer call() throws Exception {
      Future<Integer> task = this.getTask();
      int sum = 0;
      if (task.isCancelled()) return null;
      for (int i = 1; i <= 10; i++) {
        if (task.isCancelled()) break;
        sum+=i;
      //  append(getName() + " part result = " + sum + '\n');
        Thread.sleep(1000);
      }
      return sum;
    }
  }