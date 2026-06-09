/////////////////////////////////////////////////////////////
///                                                       ///
///                     LÓGICA DO JOGO                    ///
///                                                       ///
/////////////////////////////////////////////////////////////

public class TabuleiroLogico {
    private char[] tabuleiro;
    private char simboloJogador = 'o';
    private char simboloCpu     = 'x';
    private char simboloVazio   = ' ';
    private char turnoAtual     = simboloJogador;

    // Metodos
    public static char obterSimbolo(int indice){
        return ' ';
    }

    public TabuleiroLogico(){
        this.tabuleiro  = new char[9];
    
        for (int i = 0; i < tabuleiro.length; i++){
            tabuleiro[i] = ' ';
        }
    }
}
