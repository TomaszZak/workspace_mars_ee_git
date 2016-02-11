package com.tzak.gui.main;

import javax.swing.*;
import java.awt.event.*;
import java.util.List;
import java.util.concurrent.*;
import java.lang.reflect.*;

public class OknoPrzykladTextAreaButtons extends JFrame implements ActionListener {

	public static void main(String[] args) {
		new OknoPrzykladTextAreaButtons();
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 663142288877806296L;
	int k = 0;
	int n = 15;
	JTextArea ta = new JTextArea(40, 60);

	OknoPrzykladTextAreaButtons() {
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

	/**
	 * Uruchomienie zadania - watku - przekazane obiektu "task" do wykonania
	 * jako obiekt FutureTask
	 */
	public void taskStart() {
		ta.append("Metoda " + Thread.currentThread().getStackTrace()[1].getMethodName() + '\n');
	}

	public void taskResult() {
		ta.append("Metoda " + Thread.currentThread().getStackTrace()[1].getMethodName() + '\n');
	}

	public void taskStop() {
		ta.append("Metoda " + Thread.currentThread().getStackTrace()[1].getMethodName() + '\n');
	}

	public void taskShutdown() {
		ta.append("Metoda " + Thread.currentThread().getStackTrace()[1].getMethodName() + '\n');
	}

	public void taskShutdownNow() {
		ta.append("Metoda " + Thread.currentThread().getStackTrace()[1].getMethodName() + '\n');
	}
	
}