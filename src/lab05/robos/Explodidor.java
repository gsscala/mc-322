// Declaração do pacote ao qual esta interface pertence
package robos;

// Importação da exceção personalizada necessária
import ambiente.ForaMapaException;

/**
 * Interface que define o comportamento de explosão para robôs.
 * Robôs que implementam esta interface podem realizar explosões controladas
 * em seu ambiente, afetando áreas circunvizinhas.
 */
public interface Explodidor {
    
    /**
     * Realiza uma explosão centrada na posição atual do robô com raio especificado.
     * 
     * @param radius Raio de efeito da explosão (em células do ambiente)
     * @throws ForaMapaException Se a explosão tentar afetar áreas fora dos limites do mapa
     */
    void explodir(int radius) throws ForaMapaException;
}