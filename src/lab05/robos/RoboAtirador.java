// Declaração do pacote ao qual esta classe pertence
package robos;

// Importações necessárias para a classe
import comunicacao.Comunicavel;          // Interface para comunicação
import comunicacao.ErroComunicacaoException;  // Exceção de comunicação
import entity.Entidade;                  // Interface base para entidades
import missao.MissaoCentroide;
import missao.MissaoExploraçãoSegura;
import missao.MissaoMatador;
import sensores.*;                  // Interface para sensores
import utils.RandomNumberGenerator;      // Utilitário para geração de números aleatórios
import utils.RandomStringGenerator;      // Utilitário para geração de strings aleatórias
import java.util.ArrayList;
/**
 * Classe que representa um robô aéreo armado com capacidade de atirar, comunicar-se e enviar spam.
 * Estende RoboAereo e implementa as interfaces Comunicavel e EnchedorDeSaco.
 */
public class RoboAtirador extends AgenteInteligente implements Comunicavel, EnchedorDeSaco, Sensoreavel {
    
    // Nome da arma que o robô possui (valor padrão: "Ak-47")
    private String arma = "Ak-47";

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
    
        addSensor(new SensorProximidade(new RandomNumberGenerator(1, 8).generate()));
        // Para missões que envolvem sensoriamento local 

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
    
    /**
     * Simula o ato de atirar, eliminando robôs inimigos na linha de tiro.
     * A direção do tiro depende da orientação atual do robô.
     */
    public void atirar() {
        // Remove robôs inimigos que estão na linha de tiro
        getAmbiente().getEntidades().removeIf(enemy -> 
            enemy != this && // Não é o próprio robô
            enemy instanceof Robo && // É um robô
            ( // Verifica se está na linha de tiro:
                // Alinhado verticalmente e na direção Norte/Sul
                (enemy.getPosicaoX() == this.getPosicaoX() && 
                    ((enemy.getPosicaoY() >= this.getPosicaoY() && this.getDirecao().equals("Norte")) ||
                    (enemy.getPosicaoY() <= this.getPosicaoY() && this.getDirecao().equals("Sul"))) ||
                // Alinhado horizontalmente e na direção Leste/Oeste
                (enemy.getPosicaoY() == this.getPosicaoY() && 
                    ((enemy.getPosicaoX() >= this.getPosicaoX() && this.getDirecao().equals("Leste")) ||
                    (enemy.getPosicaoX() <= this.getPosicaoX() && this.getDirecao().equals("Oeste")))
            )))
        );
        
        // Mensagem de confirmação do disparo
        System.out.println(this.getNome() + " acaba de eliminar os inimigos em seu caminho ao utilizar " + this.getArma());
    }

    /**
     * Implementação da interface Comunicavel: envia mensagem para outro robô.
     * 
     * @param destinatario Robô que receberá a mensagem
     * @param mensagem Conteúdo da mensagem
     * @throws RoboDesligadoException Se o robô remetente estiver desligado
     */
    @Override
    public void enviarMensagem(Comunicavel destinatario, String mensagem) throws RoboDesligadoException {
        // Verifica se o robô está ligado
        if (getEstado() == EstadoRobo.LIGADO) {
            // Registra a mensagem na central de comunicação
            getAmbiente().getCentralComunicacao().registrarMensagem(
                this.getNome(), 
                ((Robo) destinatario).getNome(), 
                mensagem
            );
            try {
                // Tenta entregar a mensagem ao destinatário
                destinatario.receberMensagem(mensagem);
            } catch (RoboDesligadoException e) {
                // Trata erro se o destinatário estiver desligado
                System.out.println("Erro ao enviar mensagem: " + e.getMessage());
            }
        } else {
            throw new RoboDesligadoException("Remetente desligado, não é possível enviar mensagem.");
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
     * Ativa todos os sensores do robô para monitoramento.
     * 
     * @throws RoboDesligadoException Se o robô estiver desligado
     */
    public void acionarSensores() throws RoboDesligadoException {
        // Verifica se o robô está ligado
        if (getEstado() == EstadoRobo.LIGADO) {
            // Ativa cada sensor individualmente
            for (Sensor sensor : this.getSensores()) {
                sensor.monitorar();
            }
        } else {
            throw new RoboDesligadoException("Robô desligado não consegue usar sensores");
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

    public void executarMissao(String missao) throws NaoSensoriavelException, TaskNotFoundException{
        // Missões que o RoboAtirador pode realizar
        
        switch (missao.toLowerCase()){
            case "centroide":
                MissaoCentroide centroide = new MissaoCentroide(this, getAmbiente());
                centroide.executarMissao();
                break;
            case "exploracao":
                MissaoExploraçãoSegura exploracao = new MissaoExploraçãoSegura(this, getAmbiente());
                exploracao.executarMissao();
                break;
            case "matador":
                MissaoMatador matador = new MissaoMatador(this, getAmbiente());
                matador.executarMissao();
                break;
            default:
                throw new TaskNotFoundException("Missão não encontrada");
        }

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
                case "subir":
                    subir(Integer.parseInt(args[0]));  // Tarefa herdada
                    break;
                case "descer":
                    descer(Integer.parseInt(args[0]));  // Tarefa herdada
                    break;
                // MUDOU PRA AGENTE INTELIGENTE, TEM QUE MUDAR TODA A INFRA
                default:
                    // Tarefa não reconhecida
                    throw new TaskNotFoundException("Tarefa não encontrada: " + tarefa);
            }
        } catch (ArrayIndexOutOfBoundsException e){
            System.err.println("Número de argumentos insuficiente. Execute help para descobrir como rodar o comando corretamente!");
        }
    }
}