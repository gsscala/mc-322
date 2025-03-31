public class Main {
    public static void main(String[] args) {
        Ambiente ambiente = new Ambiente(100, 200);
        Robo robo = new Robo("dog", 0, 0);
        robo.mover(10, 20);
        Pair posAtual = robo.exibirPosicao();
        
        System.out.println(String.format("Posicao atual: (%d, %d)", posAtual.first(), posAtual.second()));
        
        System.out.println(ambiente.dentroDosLimites(posAtual.first(), posAtual.second()));
    }
}
