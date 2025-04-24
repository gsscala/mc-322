// Declaração da classe Main
public class Main {
    // Método principal (entry point) da aplicação
    public static void main(String[] args) {
        // Criação de um objeto do tipo Ambiente com largura 100 e altura 200
        Ambiente ambiente = new Ambiente(100, 200);
        
        // Criação de um robô aleatório chamado "dog" na posição (0, 0), voltado para "Norte" com energia 100
        RoboAleatorio roboaleatorio = new RoboAleatorio("dog", 0, 0, "Norte", 100);
        
        // Criação de um robô atirador chamado "dog" na posição (0, 0), voltado para "Norte" com energia 100 e arma "Ak-47"
        RoboAtirador roboatirador = new RoboAtirador("dog", 0, 0, "Norte", 100, "Ak-47");
        
        // Criação de um robô terrestre chamado "dog" na posição (0, 0), voltado para "Norte" com energia 100
        RoboTerrestre roboterrestre = new RoboTerrestre("dog", 0, 0, "Norte", 100);
        
        // Criação de um robô aéreo chamado "dog" na posição (0, 0), voltado para "Norte" com energia 100
        RoboAereo roboaereo = new RoboAereo("dog", 0, 0, "Norte", 100);
        
        // Criação de um robô genérico chamado "dog" na posição (0, 0), voltado para "Norte"
        Robo robo = new Robo("dog", 0, 0, "Norte");

        // Adiciona os robôs criados ao ambiente
        ambiente.adicionarRobo(roboaleatorio);
        ambiente.adicionarRobo(roboatirador);
        ambiente.adicionarRobo(roboterrestre);
        ambiente.adicionarRobo(roboaereo);
        ambiente.adicionarRobo(robo);

        // Faz todos os robôs do ambiente se moverem para a posição (1, 1)
        for (Robo r : ambiente.getRobos())
            r.mover(1, 1);

        // Faz os robôs aéreos (roboaleatorio, roboatirador, roboaereo) subirem 1 unidade
        for (RoboAereo r : new RoboAereo[]{roboaleatorio, roboatirador, roboaereo})
            r.subir(1);
        
        // Faz o robô terrestre tentar mover-se para a posição (1000, 1000, 1000)
        roboterrestre.mover(1000, 1000, 1000);

        // Faz o robô aleatório mover-se
        roboaleatorio.mover();

        // Mostra robôs antes de atirar (confirmar que foram criados)

        for (Robo obstaculo : robo.identificarObstaculo())
            System.out.println("Robo identificado: (" + obstaculo.exibirPosicao().first() + ", " + obstaculo.exibirPosicao().second() + ")");

        // Faz o robô atirador realizar a ação de atirar
        roboatirador.atirar();

        // Para cada obstáculo identificado pelo robô, exibe sua posição
        for (Robo obstaculo : robo.identificarObstaculo())
            System.out.println("Robo identificado: (" + obstaculo.exibirPosicao().first() + ", " + obstaculo.exibirPosicao().second() + ")");
    }
}
