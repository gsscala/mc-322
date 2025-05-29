// Declaração do pacote ao qual este enum pertence
package robos;

/**
 * Enumeração que representa os possíveis estados operacionais de um robô.
 * Define os estados fundamentais que um robô pode assumir durante sua operação.
 */
public enum EstadoRobo {
    /**
     * Estado indicando que o robô está ligado e operacional.
     * Neste estado, o robô pode executar todos os seus comandos normalmente.
     */
    LIGADO(),
    
    /**
     * Estado indicando que o robô está desligado.
     * Neste estado, o robô não responde a comandos mas pode ser religado.
     */
    DESLIGADO(),
    
    /**
     * Estado indicando que o robô está inoperante (morto).
     * Geralmente resulta de danos irreparáveis ou falhas críticas.
     * O robô não pode mais ser utilizado.
     */
    MORTO();
}