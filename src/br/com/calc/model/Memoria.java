package br.com.calc.model;

import java.util.ArrayList;
import java.util.List;

public class Memoria {
	
	private enum TipoComando {
			ZERAR,SINAL, PORCEN, NUM, DIV, MULT, SUB, SOMA, IGUAL, VIRG
	};
	
	private static final Memoria instancia = new Memoria();
	private List<MemoriaObservador> observadores = new ArrayList<>();
	private String textoAtual = "";
	private String textoBuffer = "";
	private boolean substituir = false;
	private TipoComando ultimaOperacao = null;
	
	private Memoria() {
		
	}

	public static Memoria getInstancia() {
		return instancia;
	}
	
	public void adicionarObservador(MemoriaObservador observador) {
		observadores.add(observador);
	}
	
	public String getTextoAtual() {
		return textoAtual.isEmpty() ? "0" : textoAtual;
	}
	
	private TipoComando detectarTipoComando(String texto) {
		
		if (textoAtual.isEmpty() && texto == "0") {
			return null;
		}
		
		try {
			
			Integer.parseInt(texto);
			return TipoComando.NUM;
			
		} catch (NumberFormatException e) {
			
			if ("AC".equalsIgnoreCase(texto)) {
				return TipoComando.ZERAR;
			}else if("+/-".equalsIgnoreCase(texto)) {
				return TipoComando.SINAL;
			}else if("/".equalsIgnoreCase(texto)) {
				return TipoComando.DIV;
			}else if("*".equalsIgnoreCase(texto)) {
				return TipoComando.MULT;
			}else if("+".equalsIgnoreCase(texto)) {
				return TipoComando.SOMA;
			}else if("-".equalsIgnoreCase(texto)) {
				return TipoComando.SUB;
			}else if(",".equalsIgnoreCase(texto) && !textoAtual.contains(",")) {
				return TipoComando.VIRG;
			}else if("=".equalsIgnoreCase(texto)) {
				return TipoComando.IGUAL;
			}else if("%".equalsIgnoreCase(texto)) {
				return TipoComando.PORCEN;
			} else {
				return null;
			}
		}
		
	}
	
	private String obterResultadoOperacao() {
		if (ultimaOperacao == null || ultimaOperacao == TipoComando.IGUAL) {
			return textoAtual;
		}
		Double numBuffer = Double.parseDouble(textoBuffer.replace(",", "."));
		Double numAtual = Double.parseDouble(textoAtual.replace(",", "."));
		Double resultado = null;
		
		if (ultimaOperacao == TipoComando.SOMA) {
			resultado = numBuffer + numAtual;
		}  else if(ultimaOperacao == TipoComando.SUB) {
			resultado = numBuffer - numAtual;
		}  else if(ultimaOperacao == TipoComando.MULT) {
			resultado = numBuffer * numAtual;
		}  else if(ultimaOperacao == TipoComando.DIV) {
			resultado = numBuffer / numAtual;
		}  
		
		String resultadoString = Double.toString(resultado).replace(".", ",");
		boolean inteiro = resultadoString.endsWith(",0");
			
		return inteiro ? resultadoString.replace(",0", "") : resultadoString;
	}
	
	public void processarComando(String texto) {
		
		TipoComando tipoComando = detectarTipoComando(texto);
		
		if (tipoComando == null) {
			return;
			
		} else if(tipoComando == TipoComando.ZERAR){
			
			textoAtual = "";
			textoBuffer = "";
			substituir = false;
			ultimaOperacao = null;
			
		} else if (tipoComando == TipoComando.SINAL && textoAtual.contains("-")) {
			textoAtual = textoAtual.substring(1);
			
		} else if (tipoComando == TipoComando.SINAL  && !textoAtual.contains("-")) {
			textoAtual = "-" + textoAtual; 
			
		} else if(tipoComando == TipoComando.NUM
				|| tipoComando == TipoComando.VIRG){
			
			textoAtual = substituir ? texto : textoAtual + texto;
			substituir = false;
			
		} else if(tipoComando == TipoComando.PORCEN){
			
			Double resultado = Double.parseDouble(textoAtual.replace(",", "."));
			resultado /= 100;
			textoAtual = resultado.toString().replace(".", ",");
			
		}  else {
			substituir = true;
			textoAtual = obterResultadoOperacao();
			textoBuffer = textoAtual;
			ultimaOperacao = tipoComando;
		} 
		
		observadores.forEach(o -> o.valorAlterado(getTextoAtual()));
	}
	
	
}
