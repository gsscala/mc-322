// A classe RoboTerrestre herda da classe Robo e representa um robô terrestre com uma velocidade máxima
public class RoboTerrestre extends Robo {
    // Declaração da variável privada velocidadeMaxima, que armazena a velocidade máxima do robô terrestre
    private int velocidadeMaxima;

    // Método getter para obter a velocidade máxima do robô terrestre
    public int getVelocidadeMaxima() {
        return velocidadeMaxima;  // Retorna a velocidade máxima do robô
    }

    // Método setter para definir a velocidade máxima do robô terrestre
    public void setVelocidadeMaxima(int velocidadeMaxima) {
        this.velocidadeMaxima = velocidadeMaxima;  // Define a velocidade máxima do robô
    }

    // Construtor da classe RoboTerrestre, que inicializa o robô com nome, posição (X e Y), direção e velocidade máxima
    public RoboTerrestre(String nome, int xIni, int yIni, String direcao, int velocidadeMaxima) {
        super(nome, xIni, yIni, direcao);  // Chama o construtor da classe pai (Robo) para inicializar nome, posição e direção
        // Verifica se a velocidade máxima é válida (não pode ser menor que 1)
        if (velocidadeMaxima < 1) {
            setVelocidadeMaxima(1);  // Define a velocidade mínima como 1
            System.out.println("Velocidade máxima não pode ser menor do que 1");  // Exibe um aviso
        } else {
            setVelocidadeMaxima(velocidadeMaxima);  // Define a velocidade máxima do robô terrestre
        }
    }

    // Método que permite ao robô terrestre mover-se, considerando a velocidade fornecida
    public void mover(int deltaX, int deltaY, int velocidade) {
        // Verifica se a velocidade não excede a velocidade máxima do robô
        if (velocidade <= getVelocidadeMaxima()) {
            super.mover(deltaX, deltaY);  // Chama o método mover da classe pai (Robo) para mover o robô
        } else {
            System.out.println("Não é permitido exceder a velocidade máxima!");  // Exibe uma mensagem caso a velocidade seja superior à máxima
        }
    }
}
