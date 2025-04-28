// A classe RoboAtirador herda da classe RoboAereo e representa um robô aéreo armado com a capacidade de atirar

import ambiente.Ambiente;

public class RoboAtirador extends RoboAereo {
    // Declaração da variável privada arma, que armazena o nome da arma do robô (inicializada como "Ak-47")
    private String arma = "Ak-47";

    // Método setter para definir o nome da arma do robô
    public void setArma(String arma) {
        this.arma = arma;  // Define a arma do robô
    }

    // Método getter para obter o nome da arma do robô
    public String getArma() {
        return this.arma;  // Retorna o nome da arma do robô
    }

    // Construtor da classe RoboAtirador, que inicializa o robô com nome, posição (X e Y), direção, altitude máxima e a arma
    public RoboAtirador(String nome, int xIni, int yIni, String direcao, int altitudeMaxima, String arma) {
        super(nome, xIni, yIni, direcao, altitudeMaxima);  // Chama o construtor da classe pai (RoboAereo) para inicializar nome, posição, direção e altitude máxima
        setArma(arma);  // Define a arma do robô
    }

    // Método que simula o ato de atirar do robô, eliminando os robôs inimigos em sua linha de fogo
    public void atirar() {
        // Para cada ambiente onde o robô está presente
        for (Ambiente ambiente : this.getAmbientes()) {
            // Remove os robôs que estão na linha de tiro, ou seja, que estão na mesma posição X ou Y, dependendo da direção do robô
            ambiente.getRobos().removeIf(enemy -> enemy != this && (enemy.getPosicaoX() == this.getPosicaoX() &&
            // Verifica se o inimigo está na mesma posição Y e dentro da linha de tiro na direção "Norte" ou "Sul"
            ((enemy.getPosicaoY() >= this.getPosicaoY() && this.getDirecao() == "Norte") ||
            (enemy.getPosicaoY() <= this.getPosicaoY() && this.getDirecao() == "Sul")) ||
            // Verifica se o inimigo está na mesma posição X e dentro da linha de tiro na direção "Leste" ou "Oeste"
            enemy.getPosicaoY() == this.getPosicaoY() && ((enemy.getPosicaoX() >= this.getPosicaoX() && this.getDirecao() == "Leste") ||
            (enemy.getPosicaoX() <= this.getPosicaoX() && this.getDirecao() == "Oeste"))));
        }
        // Exibe uma mensagem indicando que o robô atirador eliminou os inimigos utilizando sua arma
        System.out.println(this.getNome() + " acaba de eliminar os inimigos ao utilizar " + this.getArma());
    }
}
