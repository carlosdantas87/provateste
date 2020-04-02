

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
		//pega o tempo de inicio de execu��o do sistema
		// eu ia fazer duas theads uma para as linhas impares e outras para as pares,
		// mas me enrolei com algumas coisas e acabou nao danto tempo. 
		// ent�o preferi entregar do que otimizar
		// sou formado pela impacta desde 2014 mas nao trabalhei na area 
		//tive dificuldade para conseguir algumas coisas
		long inicio = System.currentTimeMillis();  
		  
		
	    File diretorio = new File("C:\\saidaprova");
	            diretorio.mkdir();
	    
		//Busca os arquivos a serem trabalhados
		File dadosMercado = new File("C:\\saidaprova\\DadosMercado.csv");
		File operacoes = new File("C:\\saidaprova\\Operacoes.csv");
		
		
		try {
			//vari�veria para trabalhar os arquivos
			String linhaMercado = new String();
			String linhaOperacoes = new String();
			
			//Scanner para buscar cada linha do aqrquivo
			Scanner leitorDadosMercado = new Scanner(dadosMercado);
			Scanner leitorOperacoes = new Scanner(operacoes);
			
			//pular para a segunda linha para n�o pegar os cabe�alios
			leitorDadosMercado.nextLine();
			leitorOperacoes.nextLine();
			
			//metodo para form�tar a hora vinda do arquivo e passar para fazer a conta de dias
			SimpleDateFormat formato =  new SimpleDateFormat("dd/MM/yyyy");
					
			ArrayList<Double>  resultado = new ArrayList<Double>();
			
			//metodo para a cria��o do arquivo 
			FileWriter writer = new FileWriter("c:\\saidaprova\\resultado.csv");
			writer.append("NM_SUBPRODUTO");
	        writer.append(';');
	        writer.append("RESULTADO");
	        writer.append('\n');
	        
			int i = 0;
			//la�o de repeti��o para andar todas as linhas do arquivo de opera��es
			while (leitorOperacoes.hasNext()) {
				//pega a linha da vez
				linhaOperacoes = leitorOperacoes.nextLine();
				
				//cria um array de colunas quebrando no ";"
				String[] valorEntreDoisOperacoes = linhaOperacoes.split(";");
				
				//recupera as strings a serem trabalhadas de acordo com o indice do arquivo
				String idPrecoOperacoes = valorEntreDoisOperacoes[13];
				String quantidade = valorEntreDoisOperacoes[12];
				String NSubproduto = valorEntreDoisOperacoes[9];
				
				//calcula os dias corridos das datas iniciais e finais
				Date dataFinal = formato.parse(valorEntreDoisOperacoes[2]);
				Date dataInicial = formato.parse(valorEntreDoisOperacoes[1]);
				
				long diferencaMili = Math.abs(dataFinal.getTime() - dataInicial.getTime() );
				long diferenca = TimeUnit.DAYS.convert(diferencaMili, TimeUnit.MILLISECONDS);
				
					// la�o para percorrer o arquivi de mercado
					while(leitorDadosMercado.hasNext()) {
						
						//pega a linha da vez do mercado
						linhaMercado = leitorDadosMercado.nextLine();
						
						//cria um array de colunas quebrando no ";"
						String[] valorEntreDoisPontosMercado = linhaMercado.split(";");
						
						//recupera as colunas a serem trabalhadas de acordo com o indice
						String idPrecoMercado = valorEntreDoisPontosMercado[0];
						String prazoDiasCorridos = valorEntreDoisPontosMercado[1];
						String precoMercado = valorEntreDoisPontosMercado[2].replaceAll(",", ".");
						
																
						//checa se o id pre�o da opera��o � igual ao id preci mercado nessa repeti��o 
						if(idPrecoOperacoes.equals(idPrecoMercado)) {
							    // fe for igual checa se o numero de dias corridos � igual o tempo da opera��o
							if(diferenca == Long.parseLong(prazoDiasCorridos)){
								// se sim calcula a quantidade e o pre�o para a quantidade de dias corridos
						    	Double result = Double.parseDouble(precoMercado) * Double.parseDouble(quantidade);
						    	
						    	//adiciona no array linst o resultado
						    	resultado.add(i, result);
						    	
						    	//escreve no arquivo de saida a linha em quest�o
						    	writer.append(NSubproduto);
								writer.append(';');
								writer.append(resultado.get(i).toString());
								writer.append('\n');
								
								break;
						    }	
						}else {
							
							//se nao for igual nem o numero nem as datas adiciona 0 ao arrray e a respectiva linha 
							//no arquivo de saida
							resultado.add(i, (double) 0);
							writer.append(NSubproduto);
							writer.append(';');
							writer.append(resultado.get(i).toString());
							writer.append('\n');						
						}					
							
					    					    	
					}
					
				
				}writer.flush();
		        writer.close();
			    
		        //se houver erro no catch mostra a exce��o
				}catch( FileNotFoundException e){
					
				}	
		//calcula o temp de execu��o do systema
		long fim  = System.currentTimeMillis();  
		System.out.println( "Tempo de execu��o "+(fim - inicio) / 1000+ " Segundos." );
			
		}
				
	}

