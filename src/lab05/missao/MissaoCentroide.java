package missao;
import ambiente.Ambiente;
import entity.Entidade;
import robos.Robo;
import sensores.NaoSensoriavelException;
import robos.AgenteInteligente;

public final class MissaoCentroide implements Missao{

    AgenteInteligente robo;
    Ambiente ambiente;

    public MissaoCentroide(AgenteInteligente robo, Ambiente ambiente){
        this.robo = robo;
        this.ambiente = ambiente;
    }

    public void executarMissao() throws NaoSensoriavelException {
        int px = 0, py = 0, cnt = 0;
        for (Entidade e : ambiente.getEntidades()){
            if (!(e instanceof Robo) || e == robo)
                continue;
            px += e.getPosicaoX();
            py += e.getPosicaoY();
            cnt++;
        }

        if (cnt == 0)
            return;

        robo.mover(px / cnt, py / cnt);
    }
}