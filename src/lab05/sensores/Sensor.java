// Declaração do pacote ao qual esta classe pertence
package sensores;

// Importação da classe Robo necessária para associação
import robos.Robo;

/**
 * Classe base que representa um sensor genérico no sistema.
 * Define propriedades e comportamentos básicos que todos os sensores devem ter.
 */
public class Sensor {
    // Raio de alcance do sensor (em unidades do ambiente)
    private double raio;
    
    // Robô ao qual este sensor está acoplado
    protected Robo robo; 

    /**
     * Construtor que inicializa o sensor com um raio específico.
     * 
     * @param raio Alcance máximo de detecção do sensor (deve ser positivo)
     */
    public Sensor(double raio) {
        // Atribui o raio fornecido ao campo da classe
        this.raio = raio;
    }

    // --- Métodos de acesso para o raio ---
    
    /**
     * Obtém o raio de alcance atual do sensor.
     * @return Valor do raio em unidades do ambiente
     */
    public double getRaio() {
        return raio;
    }

    /**
     * Define um novo raio de alcance para o sensor.
     * @param raio Novo valor do raio (deve ser positivo)
     */
    public void setRaio(double raio) {
        this.raio = raio;
    }

    // --- Métodos de acesso para o robô associado ---
    
    /**
     * Obtém o robô ao qual este sensor está acoplado.
     * @return Instância do robô associado
     */
    public Robo getRobo() {
        return robo;
    }

    /**
     * Associa o sensor a um robô específico.
     * @param robo Robô ao qual este sensor será acoplado
     */
    public void setRobo(Robo robo) {
        this.robo = robo;
    }

    /**
     * Método base para monitoramento ambiental.
     * Esta implementação padrão não realiza ação (deve ser sobrescrita por subclasses).
     */
    public void monitorar() {
        // Implementação vazia intencional - método placeholder para subclasses
        return;
    }
}