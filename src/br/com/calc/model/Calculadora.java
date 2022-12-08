package br.com.calc.model;

import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.JFrame;

import br.com.calc.view.Display;
import br.com.calc.view.Teclado;

@SuppressWarnings("serial")
public class Calculadora extends JFrame{
	
	public Calculadora() {
		
		organizarLayout();
		
		//setUndecorated(true); tirar a barra de fechar, terá que construir
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setVisible(true);
		setSize(232, 322);
		
		setLocationRelativeTo(null);
		
	}
	
	private void organizarLayout() {
		setLayout(new BorderLayout());
		
		Display display = new Display();
		display.setPreferredSize(new Dimension(232,60));
		add(display, BorderLayout.NORTH);

		Teclado teclado = new Teclado();
		add(teclado, BorderLayout.CENTER);
		teclado.setBorder(null);
		
	}

	public static void main(String[] args) {
		new Calculadora();
	}
}
