package robos;
import missao.Missao;
import robos.subsistemas.GerenciadorSensores;
import sensores.Sensoreavel;

public abstract class AgenteInteligente extends Robo implements Sensoreavel {

    protected Missao missao;

    private final GerenciadorSensores gerenciadorSensores = new GerenciadorSensores(this);

    public GerenciadorSensores getGerenciadorSensores() {
        return gerenciadorSensores;
    }

    protected AgenteInteligente(String nome, int xIni, int yIni, String direcao) {
        super(nome, xIni, yIni, direcao);
    }

    public Missao getMissao() {
        return missao;
    }

    public void setMissao(Missao missao) {
        this.missao = missao;
    }

    public boolean hasMissao() {
        return missao != null;
    }

    public abstract void executarMissao();    
}
