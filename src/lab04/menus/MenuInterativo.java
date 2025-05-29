package menus;

import java.util.Scanner;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.*;

import robos.EstadoRobo;
import robos.Robo;
import robos.RoboAereo;
import robos.RoboAleatorio;
import robos.RoboAtirador;
import robos.RoboDesligadoException;
import robos.RoboNotFoundException;
import robos.RoboTerrestre;
import robos.TaskNotFoundException;
import ambiente.Ambiente;
import comunicacao.Comunicavel;
import comunicacao.ErroComunicacaoException;
import entity.Entidade;
import sensores.*;

public class MenuInterativo {
    private boolean terminated = false;
    private Ambiente ambienteAtual;

    public MenuInterativo(Ambiente ambiente) {
        this.ambienteAtual = ambiente;
    }

    public Ambiente getAmbienteAtual() {
        return ambienteAtual;
    }

    public void run() {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Bem-vindo ao Simulador de Robôs!");
        System.out.println("Digite 'help' para ver a lista de comandos disponíveis.");
        System.out.println("Ambiente: " + ambienteAtual.getNome());

        while (!terminated) {
            System.out.print("> ");
            String input = scanner.nextLine().trim();
            handleInput(input);
        }

        System.out.println("Simulação finalizada");
        scanner.close();
    }

    private void handleInput(String input) {
        if (input.isEmpty()) {
            System.out.println("Digite um comando. Use 'help' para ver opções.");
            return;
        }

        // Regex para dividir a entrada em argumentos, considerando aspas para strings com espaços
        List<String> parsedArgs = new ArrayList<>();
        Matcher m = Pattern.compile("\"([^\"]*)\"|(\\S+)").matcher(input);
        while (m.find()) {
            if (m.group(1) != null) {
                parsedArgs.add(m.group(1));
            } else {
                parsedArgs.add(m.group(2));
            }
        }

        String[] args = parsedArgs.toArray(new String[0]);

        switch (args[0].toLowerCase()) {
            case "end":
                terminated = true;
                break;

            case "help":
                showHelp();
                break;

            case "move":
                move(args);
                break;

            case "executartarefa":
                executarTarefa(args);
                break;

            case "tarefas":
                tarefas(args);
                break;

            case "status":
                showStatus();
                break;

            case "monitorar":
                monitorar(args);
                break;

            case "comunicar":
                comunicar(args);
                break;

            case "showmapa":
                showMapa(args);
                break;
            
            case "mudarestado":
                mudarestado(args);
                break;

            case "listmensagens":
                getAmbienteAtual().getCentralComunicacao().exibirMensagens();
                break;

            default:
                System.out.println("Comando inválido. Digite 'help' para ver os comandos disponíveis.");
                break;
        }
    }

    private void showHelp() {
        System.out.println("Comandos disponíveis:");
        System.out.println("- end                             : Encerra a simulação imediatamente. Nenhum parâmetro adicional.");
        System.out.println("- showRobos                       : Exibe lista de todos os robôs registrados no ambiente. Sem parâmetros.");
        System.out.println("- status <nome_robo>              : Mostra posição (x,y,z), nível de bateria e estado (ligado/desligado/morto) do robô especificado.");
        System.out.println("- move <nome_robo> <deltaX> <deltaY>       : Desloca o robô em deltaX (eixo X) e deltaY (eixo Y). Valores inteiros." );
        System.out.println("- executarTarefa <nome_robo> <tarefa> [args] : Executa tarefa especial. 'tarefa' deve ser o nome exato (roubar, subir, explodir, etc.). 'args' são parâmetros da tarefa, quando aplicável (ex: deltaZ ou raio).");
        System.out.println("- tarefas <nome_robo>             : Lista todas as tarefas que o tipo de robô suporta. Sem parâmetros.");
        System.out.println("- monitorar <nome_robo>           : Aciona sensores de proximidade e umidade do robô, exibindo leituras atuais.");
        System.out.println("- comunicar <nome1> <nome2> <msg> : Envia a string <msg> do robô <nome1> para o robô <nome2>. Mensagem entre aspas não é obrigatória.");
        System.out.println("- showMapa                        : Desenha no console a representação textual do mapa e posição de todos os robôs.");
        System.out.println("- mudarEstado <nome_robo>         : Alterna entre ligado e desligado. Se desligado, não aceita comandos até re-ligado.");
        System.out.println("- listMensagens                   : Exibe todas as mensagens trocadas no ambiente desde o início da simulação.");
        System.out.println("- help                            : Exibe esta mensagem de ajuda com sintaxes e descrições detalhadas.");
    
        System.out.println("%nDetalhamento de executarTarefa por classe de robô:");
        
        System.out.println("-- Robo (classe base):");
        System.out.println("   - Roubar : rouba bateria de todos os robôs do ambiente. A quantidade roubada é o piso de B / R, em que B é a bateria do robô alvo e R é a distância euclidiana entre os dois.");
        
        System.out.println("-- RoboAereo (subclasse de Robo):");
        System.out.println("   - Roubar : mesma lógica de Robo");
        System.out.println("   - Subir <deltaZ> : aumenta altitude até o máximo permitido pelo robô");
        System.out.println("   - Descer <deltaZ> : diminui a altitude (não pode ficar abaixo de zero)");
        
        System.out.println("-- RoboTerrestre (subclasse de Robo):");
        System.out.println("   - Roubar : mesma lógica de Robo");
        
        System.out.println("-- RoboAleatorio (subclasse de Robo):");
        System.out.println("   - Roubar : mesma lógica de Robo");
        System.out.println("   - Explodir <raio> : causa dano letal a todos os robôs dentro do raio especificado (inteiro)");
        
        System.out.println("-- RoboAtirador (subclasse de RoboAereo):");
        System.out.println("   - Roubar : mesma lógica de Robo");
        System.out.println("   - Subir <deltaZ> : herdado de RoboAereo");
        System.out.println("   - Descer <deltaZ> : herdado de RoboAereo");
        System.out.println("   - Atirar : elimina todos os robôs alinhados no mesmo X ou Y");
        System.out.println("   - EncherOSaco <n> : envia n mensagens aleatórias para um robô-alvo, gerando spam");
    }
    

    private void mudarestado(String[] args){
        Robo robo;
        try{
            robo = findRobo(args[1]);
        }
        catch (RoboNotFoundException e){
            System.err.println(e.getMessage());
            return;
        }
        if (robo.getEstado() == EstadoRobo.MORTO){
            System.out.printf("Robô %s está morto, isto é inalterável!", robo.getNome());
            return;
        }
        
        System.out.println(robo.setEstado(robo.getEstado() == EstadoRobo.DESLIGADO ? EstadoRobo.LIGADO : EstadoRobo.DESLIGADO));
    }

    private void tarefas(String [] args){
        Robo robo;
        
        try {
            robo = findRobo(args[1]);
        }catch (RoboNotFoundException e){
            System.err.println(e.getMessage());
            return;
        }
        System.out.printf("Funcionalidades do robô %s:%n", robo.getNome());

        if (robo.getClass() == Robo.class) {
            System.out.println("- Roubar : rouba bateria de todos os robôs do ambiente. A quantidade roubada é o piso de B / R, em que B é a bateria do robô roubado e R é a distância euclideana entre os dois.");
        } 
        else if (robo.getClass() == RoboAereo.class) {
            System.out.println("- Roubar : rouba bateria de todos os robôs do ambiente. A quantidade roubada é o piso de B / R, em que B é a bateria do robô roubado e R é a distância euclideana entre os dois.");
            System.out.println("- Subir : aumenta altitude do robô");
            System.out.println("- Descer : diminui a altitude do robô");
        } 
        else if (robo.getClass() == RoboAleatorio.class) {
            System.out.println("- Roubar : rouba bateria de todos os robôs do ambiente. A quantidade roubada é o piso de B / R, em que B é a bateria do robô roubado e R é a distância euclideana entre os dois.");
            System.out.println("- Explodir : mata todos em os robôs em um raio R");
        } 
        else if (robo.getClass() == RoboAtirador.class) {
            System.out.println("- Roubar : rouba bateria de todos os robôs do ambiente. A quantidade roubada é o piso de B / R, em que B é a bateria do robô roubado e R é a distância euclideana entre os dois.");
            System.out.println("- Subir : aumenta altitude do robô");
            System.out.println("- Descer : diminui a altitude do robô");
            System.out.println("- Atirar : mata todos os robôs com a mesma coordenada x o y do robô");
            System.out.println("- EncherOSaco : Manda um número de mensagens aleatórias (spam) para o robô escolhido");
        } 
        else if (robo.getClass() == RoboTerrestre.class) {
            System.out.println("- Roubar : rouba bateria de todos os robôs do ambiente. A quantidade roubada é o piso de B / R, em que B é a bateria do robô roubado e R é a distância euclideana entre os dois.");
        } 
    }

    private void showStatus() {
        // Mostra status do ambiente
        System.out.printf("Ambiente: %s | Dimensões: %dx%dx%d | Umidade: %d%%%n",
            ambienteAtual.getNome(),
            ambienteAtual.getLargura(),
            ambienteAtual.getProfundidade(),
            ambienteAtual.getAltura(),
            ambienteAtual.getUmidade()
        );
        System.out.println();

        // Mostra status dos robôs
        System.out.println("Status dos robôs:");
        for (Entidade e : ambienteAtual.getEntidades()) {
            if (e instanceof Robo) {
                Robo robo = (Robo) e;

                // Exibe o status do robô, incluindo nome, tipo, posição e bateria
                System.out.printf("%s (%s) - Posição: (%d, %d, %d) - Bateria: %d - Estado: %s%n",
                    robo.getNome(),
                    robo.getClass().getSimpleName(),
                    robo.getPosicaoX(),
                    robo.getPosicaoY(),
                    robo.getAltitude(),
                    robo.getBateria(),
                    robo.getEstado() == EstadoRobo.LIGADO ? "ligado" : 
                    robo.getEstado() == EstadoRobo.DESLIGADO ? "desligado" : "morto"
                );
            }
        }
    }

    private void move(String[] args) {
        if (args.length < 4) {
            System.out.println("Uso: move <nome_robo> <deltaX> <deltaY>");
            return;
        }

        Robo robo;

        try{
            robo = findRobo(args[1]);
        }catch (RoboNotFoundException e){
            System.err.println(e.getMessage());
            return;
        }

        try {
            int deltaX = Integer.parseInt(args[2]);
            int deltaY = Integer.parseInt(args[3]);
            robo.mover(deltaX, deltaY);
            System.out
                    .println(robo.getNome() + " movido para (" + robo.getPosicaoX() + ", " + robo.getPosicaoY() + ")");
        } catch (NumberFormatException e) {
            System.out.println("Coordenadas inválidas. Use números inteiros para deltaX e deltaY.");
        }
    }

    private void executarTarefa(String[] args) {
        if (args.length < 3) {
            System.out.println("Uso: special <nome_robo> <ação> [argumentos]");
            return;
        }

        Robo robo = null;
        try {
            robo = findRobo(args[1]);
        } catch (RoboNotFoundException e) {
            System.out.println(e.getMessage());
            return;
        }

        String comando = args[2];

        String[] args_tarefa = new String[0];
        if (args.length > 3) {
            // Copia os argumentos restantes para args_tarefa
            args_tarefa = Arrays.copyOfRange(args, 3, args.length);
        }

        try {
            robo.executarTarefa(comando, args_tarefa);
        } catch (RoboDesligadoException e) {
            System.out.println(e.getMessage());
            return;
        } catch (ErroComunicacaoException e) {
            System.out.println(e.getMessage());
            return;
        } catch (TaskNotFoundException e) {
            System.out.println(e.getMessage());
            return;
        }

    }

    private void monitorar(String[] args) {
        if (args.length < 2) {
            System.out.println("Uso: monitorar <nome_robo>");
            return;
        }

        Robo robo;
        try{
            robo = findRobo(args[1]);}
        catch (RoboNotFoundException e){
            System.err.println(e.getMessage());
            return;
        }

        try {
            if (!(robo instanceof Sensoreavel))
                throw new NaoSensoriavelException("Robô não sensoriável");
            ((Sensoreavel)robo).acionarSensores();
        } 
        catch (RoboDesligadoException e) {
            System.err.println(e.getMessage()); 
        }
        catch (NaoSensoriavelException e){
            System.err.println(e.getMessage());
        }
    }
        

    private Robo findRobo(String nome) throws RoboNotFoundException {
        for (Entidade e : ambienteAtual.getEntidades()) {
            if (!(e instanceof Robo)) {
                continue; // Ignora entidades que não são robôs
            }
            Robo robo = (Robo) e;
            if (robo.getNome().equalsIgnoreCase(nome)) {
                return robo;
            }
        }
        throw new RoboNotFoundException("Robô não encontrado");
    }

    private void comunicar(String[] args) {

        if (args.length < 4) {
            System.out.println("Uso: comunicar <nome1> <nome2> <mensagem>");
            return;
        }

        Robo remetente;

        try{
            remetente = findRobo(args[1]);
        }
        catch (RoboNotFoundException e){
            System.err.println(e.getMessage());
            return;
        }

        Robo destinatario;

        try{
            destinatario = findRobo(args[2]);
        }catch (RoboNotFoundException e){
            System.err.println(e.getMessage());
            return;
        }

        String mensagem = args[3];

        try {
            if (remetente instanceof Comunicavel && destinatario instanceof Comunicavel) {
                ((Comunicavel) remetente).enviarMensagem((Comunicavel) destinatario, mensagem);
                System.out.println("Mensagem enviada de " + remetente.getNome() + " para " + destinatario.getNome());
            } else {
                throw new ErroComunicacaoException("Robôs não são comunicaveis");
            }

        } catch (RoboDesligadoException e) {
            System.err.println("Erro ao enviar mensagem: " + e.getMessage());
        } catch (ErroComunicacaoException e) {
            System.err.println("Erro ao enviar mensagem: " +  e.getMessage());
        }

    }

    private void showMapa(String[] args) {
        getAmbienteAtual().visualizarAmbiente();
    }   
}
