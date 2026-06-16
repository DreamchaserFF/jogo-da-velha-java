    /////////////////////////////////////////////////////////////
   ///                                                       ///
  ///                    LÓGICA DO JOGO                     ///
 ///                                                       ///
/////////////////////////////////////////////////////////////

public class TabuleiroLogico {

    boolean jogando             = true;
    private char[] estado       = {' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' '}; // Array para marcar estado
    public int tamanhoTabuleiro = estado.length;

    // Setter para o array estado
    public void registrarJogada(int indiceEscolhido, char simbolo) {
        // Por segurança, verifica antes de marcar
        if(estado[indiceEscolhido] == ' '){
            estado[indiceEscolhido] = simbolo;
        }
    }

    // Metodo para limpar a memoria
    public void limparMemoria(){
        for (int i = 0; i < estado.length; i++) {
            estado[i] = ' ';
        }
    }

    // Getter para o array estado
    public char obterSimbolo(int indice){
        return estado[indice];
    }

    // Metodo de verificar se existe espaço vazio
    public boolean temEspacoVazio() {
        for(int i = 0; i < estado.length; i++) {
            if(estado[i] == ' ') {
                return true; // Achou espaço, o jogo pode continuar
            }
        }
        return false; // Não tem espaço, o tabuleiro lotou
    }

        // Metodo que verifica a cada turno se alguem venceu
    public char verificarVencedor(){
        char[][] padraoVitoria = {
            {0, 1, 2}, {3, 4, 5}, {6, 7, 8}, // Linhas Horizontais
            {0, 3, 6}, {1, 4, 7}, {2, 5, 8}, // Colunas verticais
            {0, 4, 8}, {2, 4, 6}             // Diagonais
        };

        // Percorre o dicionario testando cada linha
        for(int i = 0; i < padraoVitoria.length; i++){
            int pos1 = padraoVitoria[i][0];
            int pos2 = padraoVitoria[i][1];
            int pos3 = padraoVitoria[i][2];

            // Se a primeira posição não estiver vazia E for igual à segunda E igual à terceira...
            if (estado[pos1] != ' ' && estado[pos1] == estado[pos2] && estado[pos2] == estado[pos3]) {
                jogando = false;
                
                return estado[pos1];
            }
        }
        return ' ';
    }
}