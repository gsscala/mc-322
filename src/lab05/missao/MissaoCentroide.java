import ambiente.Ambiente;
import entity.Entidade;
import robos.Robo;

public class MissaoCentroide implements Missao{
    public void executar(AgenteInteligente robo, Ambiente ambiente){
        int pX = 0, py = 0;
        for (Entidade e : ambiente.getEntidades()){
            if (! (e instanceof Robo) || e == this)
                continue;
            px += e.getPosicaoX();
            py += e.getPosicaoY();
            cnt ++;
        }

        if (cnt == 0)
            return;

        robo.mover(pX / cnt, pY / cnt);
    }
}