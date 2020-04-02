

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;



public class Main {

	public static void main(String[] args) throws ParseException, IOException {
		// TODO Auto-generated method stub
		File dadosMercado = new File("C:\\Users\\carlos\\eclipse-workspace\\provaTecnica\\provaTecnica\\DadosMercado.csv");
		File operacoes = new File("C:\\Users\\carlos\\eclipse-workspace\\provaTecnica\\provaTecnica\\Operacoes.csv");
		
		
		try {
			String linhaMercado = new String();
			String linhaOperacoes = new String();
			Scanner leitorDadosMercado = new Scanner(dadosMercado);
			Scanner leitorOperacoes = new Scanner(operacoes);
			
			leitorDadosMercado.nextLine();
			leitorOperacoes.nextLine();
			
			SimpleDateFormat formato =  new SimpleDateFormat("dd/MM/yyyy");
					
			ArrayList<Double>  resultado = new ArrayList<Double>();
			FileWriter writer = new FileWriter("c:\\saidaprova\\resultado.csv");
			writer.append("NM_SUBPRODUTO");
	        writer.append(';');
	        writer.append("RESULTADO");
	        writer.append('\n');
	        
			int i = 0;
			while (leitorOperacoes.hasNext()) {
				
				linhaOperacoes = leitorOperacoes.nextLine();
				String[] valorEntreDoisOperacoes = linhaOperacoes.split(";");
				String idPrecoOperacoes = valorEntreDoisOperacoes[13];
				String quantidade = valorEntreDoisOperacoes[12];
				//String codOperacao = valorEntreDoisOperacoes[0];
				String NSubproduto = valorEntreDoisOperacoes[9];
				
				
				Date dataFinal = formato.parse(valorEntreDoisOperacoes[2]);
				Date dataInicial = formato.parse(valorEntreDoisOperacoes[1]);
				
				long diferencaMili = Math.abs(dataFinal.getTime() - dataInicial.getTime() );
				long diferenca = TimeUnit.DAYS.convert(diferencaMili, TimeUnit.MILLISECONDS);
				
				
					while(leitorDadosMercado.hasNext()) {
						
						linhaMercado = leitorDadosMercado.nextLine();
						String[] valorEntreDoisPontosMercado = linhaMercado.split(";");
						String idPrecoMercado = valorEntreDoisPontosMercado[0];
						String prazoDiasCorridos = valorEntreDoisPontosMercado[1];
						String precoMercado = valorEntreDoisPontosMercado[2].replaceAll(",", ".");
						
																
						
						if(idPrecoOperacoes.equals(idPrecoMercado)) {
							    
							if(diferenca == Long.parseLong(prazoDiasCorridos)){
						    	Double result = Double.parseDouble(precoMercado) * Double.parseDouble(quantidade);
						    	resultado.add(i, result);
						    	writer.append(NSubproduto);
								writer.append(';');
								writer.append(resultado.get(i).toString());
								writer.append('\n');
								
						    	//System.out.println("Resultado "+resultado.get(i).toString());
								break;
						    }	
						}else {
							resultado.add(i, (double) 0);
							writer.append(NSubproduto);
							writer.append(';');
							writer.append(resultado.get(i).toString());
							writer.append('\n');						
						}					
							
					    					    	
					}
					
				
				}writer.flush();
		        writer.close();
			    //leitorDadosMercado.close();
			    //leitorOperacoes.close();
			    
			
				}catch( FileNotFoundException e){
					
				}	
		
		
			
		}
				
	}

