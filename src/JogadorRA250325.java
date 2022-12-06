import java.util.ArrayList;

public class JogadorRA250325 extends Jogador {
	private ArrayList<Carta> mao;
	private ArrayList<CartaLacaio> lacaios;
	private ArrayList<CartaLacaio> lacaiosOponente;
	private boolean primeiroJogador;
	
	/**
	  * O método construtor do JogadorAleatorio.
	  * 
	  * @param maoInicial Contém a mão inicial do jogador. Deve conter o número de cartas correto dependendo se esta classe Jogador que está sendo construída é o primeiro ou o segundo jogador da partida. 
	  * @param primeiro   Informa se esta classe Jogador que está sendo construída é o primeiro jogador a iniciar nesta jogada (true) ou se é o segundo jogador (false).
	  */
	public JogadorRA250325(ArrayList<Carta> maoInicial, boolean primeiro){
		primeiroJogador = primeiro;
		
		mao = maoInicial;
		lacaios = new ArrayList<CartaLacaio>();
		lacaiosOponente = new ArrayList<CartaLacaio>();
		
		// Mensagens de depuração:
		System.out.println("*Classe JogadorRAxxxxxx* Sou o " + (primeiro?"primeiro":"segundo") + " jogador (classe: JogadorAleatorio)");
		System.out.println("Mao inicial:");
		for(int i = 0; i < mao.size(); i++)
			System.out.println("ID " + mao.get(i).getID() + ": " + mao.get(i));
	}
	
	/**
	  * Um método que processa o turno de cada jogador. Este método deve retornar as jogadas do Jogador decididas para o turno atual (ArrayList de Jogada).
	  * 
	  * @param mesa   O "estado do jogo" imediatamente antes do início do turno corrente. Este objeto de mesa contém todas as informações 'públicas' do jogo (lacaios vivos e suas vidas, vida dos heróis, etc).
	  * @param cartaComprada   A carta que o Jogador recebeu neste turno (comprada do Baralho). Obs: pode ser null se o Baralho estiver vazio ou o Jogador possuir mais de 10 cartas na mão.
	  * @param jogadasOponente   Um ArrayList de Jogada que foram os movimentos utilizados pelo oponente no último turno, em ordem.
	  * @return            um ArrayList com as Jogadas decididas
	  */
	  
	public ArrayList<Jogada> processarTurno (Mesa mesa, Carta cartaComprada, ArrayList<Jogada> jogadasOponente){
		int minhaMana, minhaVida;
		if(cartaComprada != null)
			mao.add(cartaComprada);
		
		if(primeiroJogador){
			minhaMana = mesa.getManaJog1();
			minhaVida = mesa.getVidaHeroi1();
			lacaios = mesa.getLacaiosJog1();
			lacaiosOponente = mesa.getLacaiosJog2();
			//System.out.println("--------------------------------- Começo de turno pro jogador1");
		}
		else{
			minhaMana = mesa.getManaJog2();
			minhaVida = mesa.getVidaHeroi2();
			lacaios = mesa.getLacaiosJog2();
			lacaiosOponente = mesa.getLacaiosJog1();
			//System.out.println("--------------------------------- Começo de turno pro jogador2");
		}
		
		ArrayList<Jogada> minhasJogadas = new ArrayList<Jogada>();
		
		// O laço abaixo cria jogas de baixar lacaios da mão para a mesa se houver mana disponível.
/*		for(int i = 0; i < mao.size(); i++){
			Carta card = mao.get(i);
			if(card instanceof CartaLacaio && card.getMana() <= minhaMana){
				Jogada lac = new Jogada(TipoJogada.LACAIO, card, null);
				minhasJogadas.add(lac);
				minhaMana -= card.getMana();
				System.out.println("Jogada: Decidi uma jogada de baixar o lacaio: "+ card);
				mao.remove(i);
				i--;
			}
		}
		*/
		System.out.println("\n\nInformações pré turno: \nMão Jogador: "+ mao + "\nLacaios Jogador: "+ lacaios + "\nLacaios Oponente: " + lacaiosOponente + "\n\n");

		//Obtendo informações para a tomada de decisão da jogada

		int vidaHinim;
		ArrayList<CartaLacaio> listaLacsOponente;

		if (primeiroJogador){
			vidaHinim = mesa.getVidaHeroi2();
			listaLacsOponente = mesa.getLacaiosJog2();
		}else{
			vidaHinim = mesa.getVidaHeroi1();
			listaLacsOponente = mesa.getLacaiosJog1();
		}
		
		//Posturas adotadas durante a partida.
		if (false){
			if(listaLacsOponente.size() >= 5)
				return comportamentoControle(mesa, cartaComprada, jogadasOponente, minhasJogadas, minhaMana, minhaVida, lacaios, lacaiosOponente);
			if(vidaHinim >= 17)
				return comportamentoCurvaDeMana(mesa, cartaComprada, jogadasOponente, minhasJogadas, minhaMana, minhaVida, lacaios, lacaiosOponente);
			if(vidaHinim < 17)
				return comportamentoAgressivo(mesa, cartaComprada, jogadasOponente, minhasJogadas, minhaMana, minhaVida, lacaios, lacaiosOponente);
			
			return comportamentoCurvaDeMana(mesa, cartaComprada, jogadasOponente, minhasJogadas, minhaMana, minhaVida, lacaios, lacaiosOponente);
		}
		return comportamentoAgressivo(mesa, cartaComprada, jogadasOponente, minhasJogadas, minhaMana, minhaVida, lacaios, lacaiosOponente);
		
		//return minhasJogadas;
	}

	public ArrayList<Jogada> comportamentoAgressivo(Mesa mesa, Carta cartaComprada, ArrayList<Jogada> jogadasOponente, ArrayList<Jogada> minhasJogadas, int mana, int vida, ArrayList<CartaLacaio> lacaios, ArrayList<CartaLacaio> lacaiosOponente){

		//Primeiramente compra todas as cartas mais baratas com a Mana disponível e, logo em seguida, utiliza todas as cartas para atacar o Herói adversário.

		int minhaMana = mana;

		int lacaioAtual = 0;
		int lacaioIndex = 0;

		for (int i = 0; i < mao.size(); i++) {
			Carta card = mao.get(i);
		}

		//Seleciona o lacaio com maior ataque
		/*for (int i = 0; i < mao.size(); i++) {
			if (mao.get(i) instanceof CartaLacaio){
				CartaLacaio carta = (CartaLacaio) mao.get(i);
				if (carta.getAtaque() > lacaioAtual){
					lacaioMAIndex = i;
				}
			}
		}*/
		//Baixa todas as cartas com a mana disponível
		ArrayList<Carta> listaCartasOrdenada;
		for (int i = 0; i < mao.size(); i++) {
			for (int j = 0; j < mao.size(); j++) {
				
				if (mao.get(i) instanceof CartaLacaio){
					CartaLacaio carta = (CartaLacaio) mao.get(i);
					if (carta.getAtaque() > lacaioAtual){
						lacaioIndex = i;
					}
				}

			}
		}
		Carta card = mao.get(lacaioIndex);

		Jogada lac = new Jogada(TipoJogada.LACAIO, card, null);

		if (minhaMana >= card.getMana()){
			minhasJogadas.add(lac);
			minhaMana -= card.getMana();
			mao.remove(lacaioIndex);
		}
		for (int i = 0; i < lacaios.size(); i++) {
			Jogada atq = new Jogada(TipoJogada.ATAQUE, lacaios.get(i), null);
			minhasJogadas.add(atq);
		}

		return minhasJogadas;
	}

	public ArrayList<Jogada> comportamentoControle(Mesa mesa, Carta cartaComprada, ArrayList<Jogada> jogadasOponente, ArrayList<Jogada> minhasJogadas, int mana, int vida, ArrayList<CartaLacaio> lacaios, ArrayList<CartaLacaio> lacaiosOponente){

		return minhasJogadas;
	}

	public ArrayList<Jogada> comportamentoCurvaDeMana(Mesa mesa, Carta cartaComprada, ArrayList<Jogada> jogadasOponente, ArrayList<Jogada> minhasJogadas, int mana, int vida, ArrayList<CartaLacaio> lacaios, ArrayList<CartaLacaio> lacaiosOponente){

		return minhasJogadas;
	}
}