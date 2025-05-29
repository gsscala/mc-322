// Declaração do pacote ao qual esta interface pertence
package entity;

// Importação da classe Ambiente do pacote ambiente
import ambiente.Ambiente;

/**
 * Interface que define o contrato básico para todas as entidades do sistema.
 * Uma entidade representa qualquer elemento que pode existir no ambiente.
 */
public interface Entidade {
    /**
     * Obtém a coordenada X (horizontal) da posição da entidade
     * @return Valor inteiro representando a posição no eixo X
     */
    int getPosicaoX();

    /**
     * Obtém a coordenada Y (vertical) da posição da entidade
     * @return Valor inteiro representando a posição no eixo Y
     */
    int getPosicaoY();

    /**
     * Obtém o tipo categórico da entidade
     * @return Valor do enum TipoEntidade que classifica a entidade
     */
    TipoEntidade getTipo();

    /**
     * Obtém uma descrição detalhada da entidade
     * @return String contendo informações descritivas
     */
    String getDescricao();

    /**
     * Obtém o caractere/símbolo que representa visualmente a entidade
     * @return Caractere único para representação em interfaces textuais
     */
    char getRepresentacao();

    /**
     * Estabelece a associação entre a entidade e seu ambiente
     * @param ambiente Instância do ambiente onde a entidade está inserida
     */
    void setAmbiente(Ambiente ambiente);
}