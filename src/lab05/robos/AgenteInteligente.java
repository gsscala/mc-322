package robos;

import missao.Missao;

public abstract class AgenteInteligente extends Robo {

    protected Missao missao;

    protected AgenteInteligente(String nome, int xIni, int yIni, String direcao) {
        super(nome, xIni, yIni, direcao);
    }

    public Missao getMissao() {
        return missao;
    }

    public void definirMissao(Missao missao) {
        this.missao = missao;
    }

    public boolean hasMissao() {
        return missao != null;
    }

    public abstract void executarMissao();    
}
