//http://edu.pjwstk.edu.pl/wyklady/zap/scb/
package com.tzak.wspolbiezna_java.concurrent.pjwstk;

import javax.swing.*;
import java.awt.event.*;
import java.util.List;
import java.util.concurrent.*;
import java.lang.reflect.*;

public class CallableSample2WindowMain extends JFrame implements ActionListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 663142288877806296L;
	int k = 0;
	int n = 15;
	JTextArea ta = new JTextArea(40, 60);

	CallableSample2WindowMain() {
		add(new JScrollPane(ta));
		JPanel p = new JPanel();
		JButton b = new JButton("Start");
		b.addActionListener(this);
		p.add(b);
		b = new JButton("Stop current");
		b.setActionCommand("Stop");
		b.addActionListener(this);
		p.add(b);
		b = new JButton("Curent result");
		b.setActionCommand("Result");
		b.addActionListener(this);
		p.add(b);
		b = new JButton("Shutdown");
		b.addActionListener(this);
		p.add(b);
		b = new JButton("ShutdownNow");
		b.addActionListener(this);
		p.add(b);
		add(p, "South");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		pack();
		setVisible(true);
	}

	public void actionPerformed(ActionEvent e) {
		String cmd = e.getActionCommand();
		try {
			Method m = this.getClass().getDeclaredMethod("task" + cmd);
			m.invoke(this);
		} catch (Exception exc) {
			exc.printStackTrace();
		}
	}

	class SumTask implements Callable<Integer> {

		private int taskNum, limit;

		public SumTask(int taskNum, int limit) {
			this.taskNum = taskNum;
			this.limit = limit;
		}

		/**
		 * warto zaobserwować, że w call() nie musimy obsługiwać wyjątku InterruptedException przy sleep (throws Exception) jednak pisząc kody zadań nie możemy pozbyć się myślenia w kategoriach wątków (isInterrupted(), sleep)
		 */
		public Integer call() throws Exception {
			ta.append("Wywołanie metody call(). Początek wykonywania nowego zadania. Task: " + taskNum + '\n');
			int sum = 0;
			for (int i = 1; i <= limit; i++) {
				if (Thread.currentThread().isInterrupted())
					return null;
				sum += i;
				ta.append("Task " + taskNum + " part result = " + sum + '\n');
				Thread.sleep(1000);
			}
			ta.append("Koniec wykonywania zadania: Task: " + taskNum + '\n');
			return sum;
		}
	};

	Future<Integer> task;

	// ExecutorService exec = Executors.newSingleThreadExecutor();
	ExecutorService exec = Executors.newFixedThreadPool(3);

	/**
	 * Uruchomienie zadania - watku - przekazane obiektu "task" do wykonania
	 * jako obiekt FutureTask
	 */
	public void taskStart() {
		try {
			task = exec.submit(new SumTask(++k, 15));
			ta.append("Task " + k + " uruchomiono\n");
		} catch (RejectedExecutionException exc) {
			ta.append("Execution rejected\n");
			return;
		}
		
	}

	public void taskResult() {
		String msg = "";
		if (task.isCancelled())
			msg = "Task cancelled.";
		else if (task.isDone()) {
			try {
				msg = "Ready. Result = " + task.get();
			} catch (Exception exc) {
				msg = exc.getMessage();
			}
		} else
			msg = "Task is running or waiting for execution";
		JOptionPane.showMessageDialog(null, msg);
	}

	public void taskStop() {
		task.cancel(true);
	}

	public void taskShutdown() {
		exec.shutdown();
		ta.append("Executor shutdown\n");
	}

	/**
	 * shutdownNow zwraca listę zadań oczekujących na wykonanie w momencie
	 * zamknięcia serwisu - to są ogólnie Runnable, ale ponieważ zostały
	 * przekazane do wykonania jako FutureTasks - prawdziwą klasą jest
	 * FutureTask.
	 */
	public void taskShutdownNow() {
		List<Runnable> awaiting = exec.shutdownNow();
		ta.append("Eeecutor shutdown now - awaiting tasks - lista zadań oczekujących na uruchomienie: \n");
		if (awaiting.isEmpty()) {
			ta.append("   Lista zadań jest pusta - wykonano wszystkie zadania. W kolejce nie oczekują żadna zadania na uruchomienie \n");
		} else {
			for (Runnable r : awaiting) {
				ta.append("   " + r.getClass().getName() + '\n');
			}
		}
	}

	public static void main(String[] args) {
		new CallableSample2WindowMain();
	}

}