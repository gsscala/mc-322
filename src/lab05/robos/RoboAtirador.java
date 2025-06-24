// Declaração do pacote ao qual esta classe pertence
package robos;

import java.util.List;

// Importações necessárias para a classe
import comunicacao.Comunicavel;          // Interface para comunicação
import comunicacao.ErroComunicacaoException;  // Exceção de comunicação
import entity.Entidade;                  // Interface base para entidades
import utils.RandomNumberGenerator;      // Utilitário para geração de números aleatórios
import utils.RandomStringGenerator;      // Utilitário para geração de strings aleatórias
import robos.subsistemas.ModuloComunicacao;  // Módulo de comunicação do robô

/**
 * Classe que representa um robô aéreo armado com capacidade de atirar, comunicar-se e enviar spam.
 * Estende RoboAereo e implementa as interfaces Comunicavel e EnchedorDeSaco.
 */
public class RoboAtirador extends AgenteInteligente implements Comunicavel, EnchedorDeSaco {
    
    // Nome da arma que o robô possui (valor padrão: "Ak-47")
    private String arma = "Ak-47";

    private ModuloComunicacao moduloComunicacao = new ModuloComunicacao(this);

    /**
     * Construtor que inicializa o robô atirador.
     * 
     * @param nome Identificação do robô
     * @param xIni Posição inicial X
     * @param yIni Posição inicial Y
     * @param direcao Direção inicial
     * @param altitudeMaxima Limite máximo de altitude
     * @param arma Nome da arma do robô
     */
    public RoboAtirador(String nome, int xIni, int yIni, String direcao, String arma) {
        // Chama o construtor da superclasse RoboAereo
        super(nome, xIni, yIni, direcao);
        // Define a arma do robô
        setArma(arma);
        setDescricao("Um robô atirador possui a capacidade de disparar projéteis ou operar armamentos, sendo utilizado em aplicações que demandam lançamento à distância.");
    }

    /**
     * Define a arma do robô.
     * @param arma Novo nome da arma
     */
    public void setArma(String arma) {
        this.arma = arma;
    }

    /**
     * Obtém o nome da arma do robô.
     * @return Nome da arma
     */
    public String getArma() {
        return this.arma;
    }

    public ModuloComunicacao getModuloComunicacao() {
        return moduloComunicacao;
    }
    
    /**
     * Simula o ato de atirar, eliminando robôs inimigos na linha de tiro.
     * A direção do tiro depende da orientação atual do robô.
     */
    public void atirar() {
        List<Entidade> entidades = getAmbiente().getEntidades();
        int i = 0;
        boolean eliminouAlguem = false;

        while (i < entidades.size()) {
            Entidade entidade = entidades.get(i);

            if (entidade == this || !(entidade instanceof Robo)) {
                i++;
                continue;
            }

            Robo inimigo = (Robo) entidade;
            boolean alinhadoVertical = inimigo.getPosicaoX() == this.getPosicaoX();
            boolean alinhadoHorizontal = inimigo.getPosicaoY() == this.getPosicaoY();

            boolean naLinhaDeTiro = false;

            switch (this.getDirecao()) {
                case "Norte":
                    naLinhaDeTiro = alinhadoVertical && inimigo.getPosicaoY() >= this.getPosicaoY();
                    break;
                case "Sul":
                    naLinhaDeTiro = alinhadoVertical && inimigo.getPosicaoY() <= this.getPosicaoY();
                    break;
                case "Leste":
                    naLinhaDeTiro = alinhadoHorizontal && inimigo.getPosicaoX() >= this.getPosicaoX();
                    break;
                case "Oeste":
                    naLinhaDeTiro = alinhadoHorizontal && inimigo.getPosicaoX() <= this.getPosicaoX();
                    break;
            }

            if (naLinhaDeTiro) {
                entidades.remove(i); // don't increment i, because list shifted
                System.out.println(this.getNome() + " eliminou " + inimigo.getNome() + " com sua " + this.getArma());
                eliminouAlguem = true;
            } else {
                i++; // only increment if not removed
            }
        }

        if (!eliminouAlguem) {
            System.out.println(this.getNome() + " atirou com sua " + this.getArma() + ", mas não havia inimigos na linha de tiro.");
        }
    }


    /**
     * Implementação da interface EnchedorDeSaco: envia múltiplas mensagens aleatórias.
     * 
     * @param robo Robô alvo do spam
     * @param numeroDeVezes Quantidade de mensagens a enviar
     * @throws RoboDesligadoException Se o robô remetente estiver desligado
     */
    @Override
    public void encherOSaco(Comunicavel robo, int numeroDeVezes) throws RoboDesligadoException {
        // Cria gerador de números aleatórios
        RandomNumberGenerator numGen = new RandomNumberGenerator(1, 95);
        
        // Envia o número especificado de mensagens
        for (int i = 0; i < numeroDeVezes; i++) {
            // Gera tamanho aleatório para a mensagem
            int stringSize = numGen.generate();
            // Gera quantidade aleatória de caracteres
            int numCharacters = numGen.generate();
            // Cria mensagem aleatória
            String mensagem = RandomStringGenerator.generatePrintableRandomString(stringSize, numCharacters);
            // Envia a mensagem
            enviarMensagem(robo, mensagem);
        }
    }

    /**
     * Procura um robô pelo nome no ambiente.
     * 
     * @param nome Nome do robô a ser encontrado
     * @return Instância do robô encontrado ou null se não existir
     */
    private Robo findRobo(String nome) {
        // Percorre todas as entidades do ambiente
        for (Entidade e : this.getAmbiente().getEntidades()) {
            // Filtra apenas robôs
            if (!(e instanceof Robo)) {
                continue;
            }
            Robo robo = (Robo) e;
            // Compara nomes (case-insensitive)
            if (robo.getNome().equalsIgnoreCase(nome)) {
                return robo;
            }
        }
        return null;
    }

    public void executarMissao(){
        getMissao().executarMissao();   
    }

    /**
     * Executa tarefas específicas para robôs atiradores.
     * 
     * @param tarefa Nome da tarefa a executar
     * @param args Argumentos adicionais para a tarefa
     */
    @Override
    public void executarTarefa(String tarefa, String[] args) 
        throws RoboDesligadoException, ErroComunicacaoException, TaskNotFoundException {
        
        // Converte o nome da tarefa para minúsculas para comparação insensível a maiúsculas
        try{
            switch (tarefa.toLowerCase()) {
                case "atirar":
                    atirar();  // Executa disparo
                    break;
                case "encherosaco":
                    // Encontra o robô alvo pelo nome
                    Robo robo = findRobo(args[0]);
                    if (robo == null) {
                        throw new ErroComunicacaoException("Robô não encontrado: " + args[0]);
                    }
                    if (!(robo instanceof Comunicavel)) {
                        throw new ErroComunicacaoException("Robô não comunicável: " + args[0]);
                    }
                    // Executa envio de spam
                    encherOSaco((Comunicavel) robo, Integer.parseInt(args[1]));
                    break;
                case "roubar":
                    roubar();  // Tarefa herdada
                    break;
                default:
                    // Tarefa não reconhecida
                    throw new TaskNotFoundException("Tarefa não encontrada: " + tarefa);
            }
        } catch (ArrayIndexOutOfBoundsException e){
            System.err.println("Número de argumentos insuficiente. Execute help para descobrir como rodar o comando corretamente!");
        }
    }
}