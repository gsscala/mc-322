// Declaração do pacote ao qual esta interface pertence
package robos;

/**
 * Interface que define o comportamento de roubo para robôs.
 * Robôs que implementam esta interface possuem a capacidade
 * de realizar ações de roubo de recursos ou informações.
 */
public interface Ladrao {
    
    /**
     * Realiza uma ação de roubo.
     * A implementação concreta deve definir:
     *   - O que será roubado (recursos, dados, energia)
     *   - De quem será roubado (outro robô, ambiente)
     *   - A mecânica do roubo
     */
    void roubar();
}