package Iniciante;


/*
Pedrinho está organizando um evento em sua Universidade. O evento deverá ser no
mês de Abril, iniciando e terminando dentro do mês. O problema é que Pedrinho
quer calcular o tempo que o evento vai durar, uma vez que ele sabe quando
inicia e quando termina o evento.

Sabendo que o evento pode durar de poucos segundos a vários dias, você deverá
ajudar Pedrinho a calcular a duração deste evento.

Entrada

Como entrada, na primeira linha vai haver a descrição “Dia”, seguido de um
espaço e o dia do mês no qual o evento vai começar. Na linha seguinte, será
informado o momento no qual o evento vai iniciar, no formato hh : mm : ss. Na
terceira e quarta linha de entrada haverá outra informação no mesmo formato
das duas primeiras linhas, indicando o término do evento.

Saída

Na saída, deve ser apresentada a duração do evento, no seguinte formato:

W dia(s)
X hora(s)
Y minuto(s)
Z segundo(s)

Obs: Considere que o evento do caso de teste para o problema tem duração mínima de 1 minuto.

Exemplo de Entrada              Exemplo de Saída
Dia 5                           3 dia(s)
08 : 12 : 23                    22 hora(s)
Dia 9                           1 minuto(s)
06 : 13 : 23                    0 segundo(s)
*/

import org.jetbrains.annotations.NotNull;

import java.util.Scanner;

public class Problema1061 {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String diaInicial = scanner.nextLine();
        String horaInicial = scanner.nextLine();
        String diaFinal = scanner.nextLine();
        String horaFinal = scanner.nextLine();
        Duracao duracao = new Duracao(
                diaInicial,
                horaInicial,
                diaFinal,
                horaFinal);
        duracao.calcularDuracao();
        duracao.exibirDuracao();
    }
}

class Duracao {

    public static final int MINUTOS_PARA_SEGUNDOS = 60;
    public static final int HORAS_PARA_SEGUNDOS = 3600;
    public static final int DIAS_PARA_SEGUNDOS = 86400;
    private String diaInicial;
    private String tempoInicial;
    private String diaFinal;
    private String tempoFinal;
    private int diaInicialParseado;
    private int diaFinalParseado;
    private int horaInicialParseado;
    private int horaFinalParseado;
    private int minutoInicialParseado;
    private int minutoFinalParseado;
    private int segundoInicialParseado;
    private int segundoFinalParseado;
    private int duracaoDias;
    private int duracaoHora;
    private int duracaoMinuto;
    private int duracaoSegundo;

    Duracao(String diaInicial,
            String tempoInicial,
            String diaFinal,
            String tempoFinal) {
        this.diaInicial = diaInicial;
        this.tempoInicial = tempoInicial;
        this.diaFinal = diaFinal;
        this.tempoFinal = tempoFinal;

        parsearDados();
    }

    private void parsearDados() {
        parsearDias();
        parsearTempo();
    }

    private void parsearDias() {
        this.diaInicialParseado = parsearDia(this.diaInicial);
        this.diaFinalParseado   = parsearDia(this.diaFinal);
    }

    private int parsearDia(String dia) {
        String[] buffer;
        buffer = dia.split(" ");
        return Integer.parseInt(buffer[1]);
    }

    private void parsearTempo() {
        parsearHoras();
        parsearMinutos();
        parsearSegundos();
    }

    private void parsearSegundos() {
        this.segundoInicialParseado = extrairTempo(this.tempoInicial,'s');
        this.segundoFinalParseado = extrairTempo(this.tempoFinal,'s');
    }

    private void parsearMinutos() {
        this.minutoInicialParseado = extrairTempo(this.tempoInicial,'m');
        this.minutoFinalParseado = extrairTempo(this.tempoFinal,'m');
    }

    private void parsearHoras() {
        this.horaInicialParseado = extrairTempo(this.tempoInicial,'h');
        this.horaFinalParseado = extrairTempo(this.tempoFinal,'h');
    }

    private int extrairTempo(String tempo, char opcao) {
        opcao = Character.toLowerCase(opcao);
        String[] buffer = tempo.split(" : ");
        String valor = switch (opcao) {
            case 'h' -> buffer[0];
            case 'm' -> buffer[1];
            case 's' -> buffer[2];
            default -> "-1";
        };
        return Integer.parseInt(valor);
    }

    public void calcularDuracao() {
        int diferencaTempoEmSegundos = getDiferencaTempoEmSegundos();

        int qtdDias, qtdHoras, qtdMinutos, qtdSegundos, resto;

        qtdDias = (int) diferencaTempoEmSegundos / DIAS_PARA_SEGUNDOS;
        resto = diferencaTempoEmSegundos % DIAS_PARA_SEGUNDOS;

        qtdHoras = (int) resto / HORAS_PARA_SEGUNDOS;
        resto = resto % HORAS_PARA_SEGUNDOS;

        qtdMinutos = (int) resto / MINUTOS_PARA_SEGUNDOS;
        resto = resto % MINUTOS_PARA_SEGUNDOS;

        qtdSegundos = resto;

        this.duracaoDias = qtdDias;
        this.duracaoHora = qtdHoras;
        this.duracaoMinuto = qtdMinutos;
        this.duracaoSegundo = qtdSegundos;
    }

    private int getDiferencaTempoEmSegundos() {
        int tempoEmSegundosFinal =
                this.segundoFinalParseado +
                converterMinutosParaSegundos(this.minutoFinalParseado) +
                converterHorasParaSegundos(this.horaFinalParseado) +
                converterDiasParaSegundos(this.diaFinalParseado);

        int tempoEmSegundosInicial =
                this.segundoInicialParseado +
                converterMinutosParaSegundos(this.minutoInicialParseado) +
                converterHorasParaSegundos(this.horaInicialParseado) +
                converterDiasParaSegundos(this.diaInicialParseado);

        return tempoEmSegundosFinal - tempoEmSegundosInicial;
    }

    private int converterDiasParaSegundos(int quantidadeDias) {
        return quantidadeDias * DIAS_PARA_SEGUNDOS;
    }

    private int converterHorasParaSegundos(int quantidadeHora) {
        return quantidadeHora * HORAS_PARA_SEGUNDOS;
    }

    private int converterMinutosParaSegundos(int quantidadeMinutos) {
        return quantidadeMinutos * MINUTOS_PARA_SEGUNDOS;
    }

    public void exibirDuracao() {
        String duracao = """
                %d dia(s)
                %d hora(s)
                %d minuto(s)
                %d segundo(s)
                """;
        System.out.printf(duracao, this.duracaoDias, this.duracaoHora, this.duracaoMinuto, this.duracaoSegundo);
    }
}
