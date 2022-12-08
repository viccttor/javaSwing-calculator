package br.com.calc.view;

import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;

import br.com.calc.model.Memoria;

@SuppressWarnings("serial")
public class Teclado extends JButton implements ActionListener{
	
	private final Color COR_ZINZA_ESCURO = new Color(68,68,68);
	private final Color COR_ZINZA_CLARO = new Color(97,100,99);
	private final Color COR_LARANJA = new Color(242,163,60);
	
	public Teclado() {
		
		GridBagLayout layout = new GridBagLayout();
		GridBagConstraints c = new GridBagConstraints();
		
		setLayout(layout);
		c.weightx = 1;
		c.weighty = 1;
		c.fill = GridBagConstraints.BOTH;
		
		adicionarBotao("AC", COR_ZINZA_ESCURO, c, 0,0);
		adicionarBotao("+/-", COR_ZINZA_ESCURO, c, 1,0);
		adicionarBotao("%", COR_ZINZA_ESCURO, c, 2,0);
		adicionarBotao("/", COR_LARANJA, c, 3,0);

		adicionarBotao("7",COR_ZINZA_CLARO , c, 0,1);
		adicionarBotao("8", COR_ZINZA_ESCURO, c, 1,1);
		adicionarBotao("9", COR_ZINZA_ESCURO, c, 2,1);
		adicionarBotao("*", COR_LARANJA, c, 3,1);
		
		adicionarBotao("4",COR_ZINZA_CLARO , c, 0,2);
		adicionarBotao("5", COR_ZINZA_ESCURO, c, 1,2);
		adicionarBotao("6", COR_ZINZA_ESCURO, c, 2,2);
		adicionarBotao("-", COR_LARANJA, c, 3,2);
		
		adicionarBotao("1",COR_ZINZA_CLARO , c, 0,3);
		adicionarBotao("2", COR_ZINZA_ESCURO, c, 1,3);
		adicionarBotao("3", COR_ZINZA_ESCURO, c, 2,3);
		adicionarBotao("+", COR_LARANJA, c, 3,3);
		
		c.gridwidth = 2;
		adicionarBotao("0",COR_ZINZA_CLARO , c, 0,4);
		c.gridwidth = 1;
		adicionarBotao(",", COR_ZINZA_ESCURO, c, 2,4);
		adicionarBotao("=", COR_LARANJA, c, 3,4);		

	}

	private void adicionarBotao(String texto, Color cor, GridBagConstraints c, int i, int j) {
		
		c.gridx = i;
		c.gridy = j;
		
		Botao botao = new Botao(texto, cor);
		botao.addActionListener(this);
		add(botao,c);
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() instanceof JButton) {
			JButton botao = (JButton) e.getSource();
			Memoria.getInstancia().processarComando(botao.getText());
		}
	}
}
