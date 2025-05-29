package menus;

import java.util.Scanner;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.*;

import robos.EstadoRobo;
import robos.Robo;
import robos.RoboAleatorio;
import robos.RoboAereo;
import robos.RoboAtirador;
import robos.RoboDesligadoException;
import robos.RoboNotFoundException;
import robos.RoboTerrestre;
import robos.TaskNotFoundException;
import ambiente.Ambiente;
import comunicacao.CentralComunicacao;
import comunicacao.Comunicavel;
import comunicacao.ErroComunicacaoException;
import entity.Entidade;
import sensores.*;

public class MenuInterativo {
    private boolean terminated = false;
    private Ambiente ambienteAtual;
    private CentralComunicacao centralComunicacao = new CentralComunicacao();

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

        List<String> parsedArgs = new ArrayList<>();
        Matcher m = Pattern.compile("\"([^\"]*)\"|(\\S+)").matcher(input);
        while (m.find()) {
            if (m.group(1) != null) {
                // Quoted part, without quotes
                parsedArgs.add(m.group(1));
            } else {
                // Unquoted word
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

            case "showrobos":
                showRobos();
                break;

            case "move":
                move(args);
                break;

            case "executartarefa":
                executarTarefa(args);
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

            default:
                System.out.println("Comando inválido. Digite 'help' para ver os comandos disponíveis.");
                break;
        }
    }

    private void showHelp() {
        System.out.println("Comandos disponíveis:");
        System.out.println("- end                          : Finaliza a simulação");
        System.out.println("- showRobos                    : Lista todos os robôs no ambiente");
        System.out.println("- status                       : Mostra posição e status dos robôs");
        System.out.println("- mudarestado <nome_robo>      : Desliga (se estiver ligado) ou liga (se estiver desligado) o robô");
        System.out.println(
                "- move <nome_robo> <x> <y>     : Move o robô especificado pelo deslocamento (deltaX, deltaY)");
        System.out.println("- executartarefa <nome_robo> <ação> [...] : Executa uma habilidade especial de um robô");
        System.out.println("  Ações especiais:");
        System.out.println("    - RoboAtirador: atirar");
        System.out.println("    - RoboTerrestre: turbo <deltaX> <deltaY> <velocidade>");
        System.out.println("    - RoboAereo: subir <deltaZ> | descer <deltaZ>");
        System.out.println("    - RoboAleatorio: aleatorio");
        System.out.println(
                "- monitorar <nome_robo>: Usa os sensores do robô para monitorar o ambiente");
        System.out.println("  Tipos de sensores:");
        System.out.println("    - proximidade");
        System.out.println("    - umidade");
    }

    private void mudarestado(String [] args) throws RoboNotFoundException{
        Robo robo = findRobo(args[0]);
        try:
            if (robo == null){
                throw new RoboNotFoundException("Robo não encontrado");
            }
        catch
        if (robo.getEstado() == EstadoRobo.MORTO){
            System.out.printf("Robô %s está morto, isto é inalterável!", robo.getNome());
            return;
        }
        
        System.out.println(robo.setEstado(robo.getEstado() == EstadoRobo.DESLIGADO ? EstadoRobo.LIGADO : EstadoRobo.DESLIGADO));
    }

    private void showRobos() {
        for (Entidade e : ambienteAtual.getEntidades()) {
            if (e instanceof Robo) {
                Robo robo = (Robo) e;
  
                System.out.println(robo.getNome() + " (" + robo.getClass().getSimpleName() + ") - " + robo.getEstado().name());
            }
        }
    }

    private void showStatus() {
        System.out.println("Status dos robôs:");
        for (Entidade e : ambienteAtual.getEntidades()) {
            if (e instanceof Robo) {
                Robo robo = (Robo) e;
                
                // Exibe o status do robô, incluindo nome, tipo, posição e bateria
                System.out.printf("%s (%s) - Posição: (%d, %d, %d) - Bateria: %d%n - Estado: %s",
                robo.getNome(),
                robo.getClass().getSimpleName(),
                robo.getPosicaoX(),
                robo.getPosicaoY(),
                robo.getAltitude(),
                robo.getBateria(),
                robo.getEstado() == EstadoRobo.LIGADO ? "ligado" : 
                robo.getEstado() == EstadoRobo.DESLIGADO ? "desligado" : "morto");
            }
        }
    }

    private void move(String[] args) {
        if (args.length < 4) {
            System.out.println("Uso: move <nome_robo> <deltaX> <deltaY>");
            return;
        }

        Robo robo = findRobo(args[1]);
        if (robo == null) {
            System.out.println("Robô não encontrado: " + args[1]);
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

        Robo robo = findRobo(args[1]);
        if (robo == null) {
            System.out.println("Robô não encontrado: " + args[1]);
            return;
        }

        try { 
            if (robo instanceof Sensoreavel) {
                ((Sensoreavel)robo).acionarSensores();
            }
            else {
                System.out.println("Robô não é sensoreavel");
            }
        } 
        catch (RoboDesligadoException e) {
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

        try:
            remetente = findRobo(args[1]);
        catch 
        // try catch solução mais funcional 

        Robo destinario = findRobo(args[2]);
        if (destinario == null) {
            System.out.println("Robô destinatário não encontrado: " + args[2]);
            return;
        }

        String mensagem = args[3];

        try {
            if (remetente instanceof Comunicavel && destinario instanceof Comunicavel) {
                ((Comunicavel) remetente).enviarMensagem((Comunicavel) destinario, mensagem);
                centralComunicacao.registrarMensagem(remetente.getNome(), destinario.getNome(), mensagem);
                System.out.println("Mensagem enviada de " + remetente.getNome() + " para " + destinario.getNome());
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
