package robos;
// A classe RoboAtirador herda da classe RoboAereo e representa um robô aéreo armado com a capacidade de atirar

import comunicacao.Comunicavel;
import comunicacao.ErroComunicacaoException;
import entity.Entidade;
import sensores.Sensor;
import utils.RandomNumberGenerator;
import utils.RandomStringGenerator;

public class RoboAtirador extends RoboAereo implements Comunicavel, EnchedorDeSaco {
    // Declaração da variável privada arma, que armazena o nome da arma do robô (inicializada como "Ak-47")
    private String arma = "Ak-47";


    // Construtor da classe RoboAtirador, que inicializa o robô com nome, posição (X e Y), direção, altitude máxima e a arma
    public RoboAtirador(String nome, int xIni, int yIni, String direcao, int altitudeMaxima, String arma) {
        super(nome, xIni, yIni, direcao, altitudeMaxima);  // Chama o construtor da classe pai (RoboAereo) para inicializar nome, posição, direção e altitude máxima
        setArma(arma);  // Define a arma do robô
    }


    // Método setter para definir o nome da arma do robô
    public void setArma(String arma) {
        this.arma = arma;  // Define a arma do robô
    }

    // Método getter para obter o nome da arma do robô
    public String getArma() {
        return this.arma;  // Retorna o nome da arma do robô
    }

    // Método que simula o ato de atirar do robô, altitude do projetil é ajustada pelo robo para passar por cima de obstaculos ou atingir objetos voadores
    public void atirar() {
        // Remove os robôs que estão na linha de tiro, ou seja, que estão na mesma posição X ou Y, dependendo da direção do robô
        getAmbiente().getEntidades().removeIf(enemy -> enemy != this && enemy instanceof Robo && (enemy.getPosicaoX() == this.getPosicaoX() &&
        // Verifica se o inimigo está na mesma posição Y e dentro da linha de tiro na direção "Norte" ou "Sul"
        ((enemy.getPosicaoY() >= this.getPosicaoY() && this.getDirecao() == "Norte") ||
        (enemy.getPosicaoY() <= this.getPosicaoY() && this.getDirecao() == "Sul")) ||
        // Verifica se o inimigo está na mesma posição X e dentro da linha de tiro na direção "Leste" ou "Oeste"
        enemy.getPosicaoY() == this.getPosicaoY() && ((enemy.getPosicaoX() >= this.getPosicaoX() && this.getDirecao() == "Leste") ||
        (enemy.getPosicaoX() <= this.getPosicaoX() && this.getDirecao() == "Oeste"))));
        
        // Exibe uma mensagem indicando que o robô atirador eliminou os inimigos utilizando sua arma
        System.out.println(this.getNome() + " acaba de eliminar os inimigos em seu caminho ao utilizar " + this.getArma());
        // feature de matar o robo
    }

    
    public void enviarMensagem(Comunicavel destinatario, String mensagem) throws RoboDesligadoException {
        // Verifica se o robô está ligado antes de enviar a mensagem
        
        if (getEstado() == EstadoRobo.LIGADO) {
            // System.out.println(getNome() + " enviou a mensagem: " + mensagem + " para " + destinatario.getNome()); isso eh na CLI    
            try {
                destinatario.receberMensagem(mensagem);  // Chama o método receberMensagem do destinatário
            } catch (RoboDesligadoException e) {
                System.out.println("Erro ao enviar mensagem: " + e.getMessage());  // Exibe mensagem de erro se o robô estiver desligado
            }

        } else {
            throw new RoboDesligadoException("Remetente desligado, não é possível enviar mensagem.");  // Lança exceção se o robô estiver desligado
        }
    }       

    public void encherOSaco(Comunicavel robo, int numeroDeVezes) throws RoboDesligadoException {
        RandomNumberGenerator numGen = new RandomNumberGenerator(1, 95);
        for (int i = 0; i < numeroDeVezes; i++){
            
            int stringSize = numGen.generate();
            int numCharacters = numGen.generate();

            String mensagem = RandomStringGenerator.generatePrintableRandomString(stringSize, numCharacters);
            enviarMensagem(robo, mensagem);
        }
    }

    public void receberMensagem(String mensagem) throws RoboDesligadoException {
        // Verifica se o robô está ligado antes de receber a mensagem
        if (getEstado() == EstadoRobo.LIGADO) {
            System.out.println(getNome() + " recebeu a mensagem: " + mensagem);  // Exibe a mensagem recebida
        } else {
            throw new RoboDesligadoException("Destinatario desligado, não é possível receber mensagem.");  // Lança exceção se o robô estiver desligado
        }   
    }

    public void acionarSensores() throws RoboDesligadoException {
        if (getEstado() == EstadoRobo.LIGADO) {
            for (Sensor sensor : this.getSensores()) {
                sensor.monitorar();
            }   
        }
        else {
            throw new RoboDesligadoException("Robô desligado não consegue usar sensores");
        }
    }

    // TODO: ARRUMAR SPLIT MENSAGEM

    private Robo findRobo(String nome) {
        for (Entidade e : this.getAmbiente().getEntidades()) {
            if (!(e instanceof Robo)) {
                continue; // Ignora entidades que não são robôs
            }
            Robo robo = (Robo) e;
            if (robo.getNome().equalsIgnoreCase(nome)) {
                return robo;
            }
        }
        return null;
    }

    public void executarTarefa(String tarefa, String[] args) throws RoboDesligadoException, ErroComunicacaoException {
        switch (tarefa) {
            case "atirar":
                atirar();
                break;
            case "encherOSaco":
                Robo robo = findRobo(args[0]);
                if (!(robo instanceof Comunicavel))
                    throw new ErroComunicacaoException("Robô não comunicável");
                encherOSaco((Comunicavel)findRobo(args[0]), Integer.parseInt(args[1]));
                break;
            case "roubar":
                roubar();
                break;
            case "subir":
                subir(Integer.parseInt(args[0]));
                break;
            case "descer":
                descer(Integer.parseInt(args[0]));
                break;
            default:
                break;
        }
    }
}
