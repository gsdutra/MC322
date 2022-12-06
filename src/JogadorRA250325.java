/*Explicando o funcionamento da classe:

Comportamento agressivo:
	Esse comportamento é o mais simples de todos. Basicamente, ele funciona de modo que visa finalizar
	o jogo quando o Herói do oponente já não está com a vida cheia e já fica mais fácil vencer o jogo
	apenas baixando cartas baratas e o atacando diretamente.

	Resumindo o comportamento:
	Primeiramente compra todas as cartas mais baratas com a mana disponível (exceto magias de buff) e,
	logo em seguida, utiliza todas as cartas para atacar o Herói adversário.
	O motivo de não utilizar magias de Buff é porque, a curto prazo, vale mais a pena depositar toda a
	mana para tirar vida do Herói do oponente pois isso garante uma vitória mais rápida.

Comportamento controle:
	O comportamento controle é utilizado mais em casos de 'emergência', quando o oponente tem muitos
	lacaios vivos e é preciso controlar melhor seus lacaios. Esse método consiste principalmente em 
	atacar os lacaios mais fracos do oponente e utilizar magias de dano em área. Ao atacar os lacaios
	mais fracos com magias, é importante lembrar de não disperdiçar o potencial de dano da magia, por
	isso é visado utilizá-las apenas para atacar os lacaios cuja diferença entre sua vida e o dano da
	magia seja no máximo 1.

	Resumindo o comportamento:
	Primeiramente verificamos se é possível realizar trocas favoráveis;
	Se não houver, atacamos o Herói adversário;
	Depois disso, utiliza-se as magias.
	Para realizar trocas favoráveis, testa-se o lacaio mais fraco do oponente contra o lacaio de ataque
	mais forte que temos e faz a jogada se essa conseguir matar de fato o lacaio oponente.
	Se não for possível matar nenhum lacaio adversário, utilizamos os lacaios em mesa para atacar
	diretamente o Herói adversário.
	Depois disso, são utilizadas as magias de ataque de forma eficiente contra os lacaios inimigos a
	fim de não disperdiçarmos seus poderes de ataque.
	Por último, caso ainda tenha sobrado mana, utiliza-se, caso disponível, uma magia de buff em algum
	lacaio aliado aleatório.

Comportamento curva de mana:
	O comportamento curva de mana visa principalmente utilizar toda a mana disponível em cada turno para
	fortalecer a mesa e ter uma utilização de mana mais eficiente do que o adversário, garantindo assim
	uma vantagem.

	Resumindo o comportamento
	Primeiramente, compra-se o lacaio mais caro que a mana daquele turno permitir.
	Logo em seguida, utiliza-se a mana que restar para utilizar uma magia de alvo para atacar um lacaio
	inimigo, mas somente se essa magia for capaz de eliminar algum lacaio.

Critério de troca entre comportamentos:
	O critério de troca de comportamentos foi bem simples.

	A primeira condicional é se a quantidade de lacaios na mesa do oponente for maior do que 5. Nesse caso, é ativado o
	comportamento "controle", mais comum em turnos mais avançados da partida, pois demora um certo tempo para que algum
	jogador fique com tantos lacaios vivos na mesa. Além disso, ele não traz muita vantagem no início do jogo, mas é muito
	útil para controlar a mesa em estágios mais avançados.

	A segunda condicional opta por utilizar a estratégia "curva de mana" quando a vida do herói oponente for maior ou igual
	a 18. Esse método é utilizado mais no início do jogo, e é muito bom para construir uma mesa com lacaios mais fortes e
	ter uma base sólida antes de ir para o comportamento agressivo que costuma finalizar o jogo.
	
	Por fim, caso nenhuma das condições acima seja satisfeita, é utilizado o comportamento "agressivo", pois significa que 
	já é um bom momento para atacar diretamente o Herói adversário o mais rápido possível para finalizar o jogo.
	
	*/
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

		//System.out.println("\n\nInformações pré turno: \nMão Jogador: "+ mao + "\nLacaios Jogador: "+ lacaios + "\nLacaios Oponente: " + lacaiosOponente + "\n\n");

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
		
		//Decisão de comportamentos

		if(listaLacsOponente.size() > 5){
			return comportamentoControle(mesa, cartaComprada, jogadasOponente, minhasJogadas, minhaMana, minhaVida, lacaios, lacaiosOponente);
		}
		if(vidaHinim >= 18){
			return comportamentoCurvaDeMana(mesa, cartaComprada, jogadasOponente, minhasJogadas, minhaMana, minhaVida, lacaios, lacaiosOponente);
		}else{
		return comportamentoAgressivo(mesa, cartaComprada, jogadasOponente, minhasJogadas, minhaMana, minhaVida, lacaios, lacaiosOponente);
		}

		
		//return minhasJogadas;
	}

	public ArrayList<Jogada> comportamentoAgressivo(Mesa mesa, Carta cartaComprada, ArrayList<Jogada> jogadasOponente, ArrayList<Jogada> minhasJogadas, int mana, int vida, ArrayList<CartaLacaio> lacaios, ArrayList<CartaLacaio> lacaiosOponente){

		//Primeiramente compra todas as cartas mais baratas com a Mana disponível (exceto magias de buff) e, logo em seguida, utiliza todas as cartas para atacar o Herói adversário.

		int minhaMana = mana;

		//Ordena e baixa as cartas por mana (crescente)
		int cartaIndex = 0;

		ArrayList<Carta> listaCartasOrdenada = new ArrayList<>();

		for (int i = 0; i < mao.size(); i++) {

			int manaMenor = 8;
			for (int j = 0; j < mao.size(); j++) {

				if (mao.get(j).getMana() <= manaMenor && !listaCartasOrdenada.contains(mao.get(j))){
					cartaIndex = j;
					manaMenor = mao.get(j).getMana();
				}
			}
			listaCartasOrdenada.add(mao.get(cartaIndex));

			if (minhaMana >= mao.get(cartaIndex).getMana()){
				Carta card = mao.get(cartaIndex);
				if (mao.get(cartaIndex) instanceof CartaLacaio){
					Jogada lac = new Jogada(TipoJogada.LACAIO, card, null);
					//System.out.println("Baixei o lacaio: "+ card);
					minhasJogadas.add(lac);
					minhaMana -= card.getMana();
					mao.remove(cartaIndex);
				}else{
					CartaMagia cardM = (CartaMagia) card;
					if (cardM.getMagiaTipo() != TipoMagia.BUFF){
						Jogada mag = new Jogada(TipoJogada.MAGIA, card, null);
						//System.out.println("Usei a magia: "+ card);
						minhasJogadas.add(mag);
						minhaMana -= card.getMana();
						mao.remove(cartaIndex);
					}
				}
			}
		}

		//Ataca o Heroi com os lacaios disponíveis
		for (int i = 0; i < lacaios.size(); i++) {
			Jogada atq = new Jogada(TipoJogada.ATAQUE, lacaios.get(i), null);
			minhasJogadas.add(atq);
		}

		return minhasJogadas;
	}

	public ArrayList<Jogada> comportamentoControle(Mesa mesa, Carta cartaComprada, ArrayList<Jogada> jogadasOponente, ArrayList<Jogada> minhasJogadas, int mana, int vida, ArrayList<CartaLacaio> lacaios, ArrayList<CartaLacaio> lacaiosOponente){
		int minhaMana = mana;
		//Primeiramente verificamos se é possível realizar trocas favoráveis;
		//Se não houver, atacamos o Herói adversário;
		//Depois disso, utiliza-se as magias.

		//Testa o lacaio mais fraco do oponente contra o ataque mais forte que temos e faz a jogada se for favorável
		int lacMenorVida = 100;
		int lacMenorIndex = 0;
		for (int i = 0; i < lacaiosOponente.size(); i++) {
			if (lacaiosOponente.get(i).getVidaAtual() < lacMenorVida){
				lacMenorVida = lacaiosOponente.get(i).getVidaAtual();
				lacMenorIndex = i;
			}
		}
		int lacMaiorAtaque = 0;
		int lacMaiorIndex = 0;
		for (int i = 0; i < lacaios.size(); i++) {
			if (lacaios.get(i).getAtaque() > lacMaiorAtaque){
				lacMaiorAtaque = lacaios.get(i).getAtaque();
				lacMaiorIndex = i;
			}
		}
		if (lacMaiorAtaque >= lacMenorVida){
			Carta card = lacaios.get(lacMaiorIndex);
			Carta cardAlvo = lacaiosOponente.get(lacMenorIndex);
			Jogada atk = new Jogada(TipoJogada.ATAQUE, card, cardAlvo);
			minhasJogadas.add(atk);
		}else{
			//Caso não tenha trocas favoráveis, atacaremos o Herói adversário com todos os lacaios
			for (int i = 0; i < lacaios.size(); i++) {
				Jogada atq = new Jogada(TipoJogada.ATAQUE, lacaios.get(i), null);
				minhasJogadas.add(atq);
			}
		}

		//Agora, vamos utilizar as magias de forma eficiente
		for (int i = 0; i < mao.size(); i++) {
			if(mao.get(i) instanceof CartaMagia){
				CartaMagia magia = (CartaMagia) mao.get(i);

				if (magia.getMagiaTipo() == TipoMagia.ALVO && mao.get(i).getMana() <= minhaMana){
					for (int j = 0; j < lacaiosOponente.size(); j++) {
						if (Math.abs(lacaiosOponente.get(i).getVidaAtual() - magia.getMagiaDano()) <= 1){
							Jogada mag = new Jogada(TipoJogada.MAGIA, magia, lacaiosOponente.get(i));
							minhasJogadas.add(mag);
							break;
						}
					}
				}

				if (magia.getMagiaTipo() == TipoMagia.AREA && mao.get(i).getMana() <= minhaMana){
					if (lacaiosOponente.size() >= 2){
						Jogada mag = new Jogada(TipoJogada.MAGIA, magia, null);
						minhasJogadas.add(mag);
						break;
					}
				}
			}
		}

		//Por último, caso sobre mana usaremos um buff em um lacaio aleatório
		for (int i = 0; i < mao.size(); i++) {
			if(mao.get(i) instanceof CartaMagia && lacaios.size()>0){
				CartaMagia magia = (CartaMagia) mao.get(i);
				if (magia.getMagiaTipo() == TipoMagia.BUFF && mao.get(i).getMana() <= minhaMana){
					Jogada mag = new Jogada(TipoJogada.MAGIA, magia, lacaios.get(0));
					minhasJogadas.add(mag);
				}
			}
		}

		return minhasJogadas;
	}

	public ArrayList<Jogada> comportamentoCurvaDeMana(Mesa mesa, Carta cartaComprada, ArrayList<Jogada> jogadasOponente, ArrayList<Jogada> minhasJogadas, int mana, int vida, ArrayList<CartaLacaio> lacaios, ArrayList<CartaLacaio> lacaiosOponente){

		int minhaMana = mana;
		//Basicamente, nesse comportamento é priorizado baixar o lacaio mas caro possível com a mana disponível e, caso sobre mana, utiliza-se magias.

		//Ordena e baixa as cartas por mana (decrescente)
		int cartaIndex = 0;

		ArrayList<Carta> listaCartasOrdenada = new ArrayList<>();

		for (int i = 0; i < mao.size(); i++) {

			int manaMaior = 0;
			for (int j = 0; j < mao.size(); j++) {

				if (mao.get(j).getMana() >= manaMaior && !listaCartasOrdenada.contains(mao.get(j))){
					cartaIndex = j;
					manaMaior = mao.get(j).getMana();
				}
			}
			listaCartasOrdenada.add(mao.get(cartaIndex));

			if (minhaMana >= mao.get(cartaIndex).getMana()){
				Carta card = mao.get(cartaIndex);
				if (mao.get(cartaIndex) instanceof CartaLacaio){
					Jogada lac = new Jogada(TipoJogada.LACAIO, card, null);
					//System.out.println("Baixei o lacaio: "+ card);
					minhasJogadas.add(lac);
					minhaMana -= card.getMana();
					mao.remove(cartaIndex);

					break;
				}
			}
		}
		//Em seguida, utiliza-se magias com a mana que sobrar
		for (int i = 0; i < mao.size(); i++) {
			if (minhaMana >= mao.get(i).getMana() && mao.get(i) instanceof CartaMagia){
				Carta card = mao.get(i);
				CartaMagia cardM = (CartaMagia) card;
				if (cardM.getMagiaTipo() == TipoMagia.ALVO){
					for (int j = 0; j < lacaiosOponente.size(); j++) {
						if (lacaiosOponente.get(j).getVidaAtual() <= cardM.getMagiaDano()){
							Jogada mag = new Jogada(TipoJogada.MAGIA, card, lacaiosOponente.get(j));
							//System.out.println("Usei a magia: "+ card);
							minhasJogadas.add(mag);
							minhaMana -= card.getMana();
							mao.remove(cartaIndex);
						}
					}
				}
			}
		}
		
		//Ataca o Heroi com os lacaios disponíveis
		for (int i = 0; i < lacaios.size(); i++) {
			Jogada atq = new Jogada(TipoJogada.ATAQUE, lacaios.get(i), null);
			minhasJogadas.add(atq);
		}
		
		return minhasJogadas;
	}
}