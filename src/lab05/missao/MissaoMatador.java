import ambiente.Ambiente;
import entity.Entidade;
import robos.AgenteInteligente;
import robos.RoboAtirador;
import java.util.HashMap;
import java.util.Map;

public final class MissaoMatador implements Missao{
    public void executar(AgenteInteligente robo, Ambiente ambiente) throws NaoSensoriavelException {
        // Pensar no que acontece se não for atirador

        HashMap<Integer, Integer> x = new HashMap<>();

        HashMap<Integer, Integer> y = new HashMap<>();

        
        for (Entidade e : ambiente.getEntidades()){
            if (e == this)
                continue;

            if (x.get(e.getPosicaoX()) == null)
                x.put(e.getPosicaoX(), 0);

            if (x.get(e.getPosicaoY()) == null)
                x.put(e.getPosicaoY(), 0);

            x.put(e.getPosicaoX(), x.get(e.getPosicaoX()) + 1);
            y.put(e.getPosicaoY(), y.get(e.getPosicaoY()) + 1);
        }
        
        int xScore = 0;
        int xCoord = robo.getPosicaoX();

        for (Map.Entry<Integer, Integer> entry : x.entrySet()){
            Integer key = entry.getKey();
            Integer value = entry.getValue();
            if (xScore < value){
                value = xScore;
                xCoord = key;
            }
        }

        int yScore = 0;
        int yCoord = robo.getPosicaoY();

        for (Map.Entry<Integer, Integer> entry : y.entrySet()){
            Integer key = entry.getKey();
            Integer value = entry.getValue();
            if (yScore < value){
                value = yScore;
                yCoord = key;
            }
        }

        robo.mover(xCoord - robo.getPosicaoX(), yCoord - robo.getPosicaoY());

    }
}