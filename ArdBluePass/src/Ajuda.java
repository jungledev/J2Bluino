
import javax.microedition.lcdui.*;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author fontao
 */
public class Ajuda implements CommandListener {
    Form conteudo;
    StringItem texto;
    Command cmdVoltar;
    Display telaCelular;
    Form telaAnterior;
    
    public Ajuda(Display tC, Form tA) {
        telaCelular = tC;
        telaAnterior = tA;
    }

    public void show(){
        conteudo = new Form("Ajuda");
        texto = new StringItem("J2Bluino",
                "Para abrir a fechadura informe a senha correta e pressione o bot�o enviar." +
                "Caso a senha esteja correta uma mensagem ser� exibida confirmando a a��o. Em caso de senha incorreta uma mensagem, tamb�m, ser� exibida.");
        
        conteudo.append(texto);
                   
        cmdVoltar = new Command("Voltar", Command.BACK, 0);  
        conteudo.addCommand(cmdVoltar);
        
        conteudo.setCommandListener(this);
        telaCelular.setCurrent(conteudo);
        
    }
    
    public void commandAction(Command c, Displayable d) {
        if (c == cmdVoltar){
            telaCelular.setCurrent(telaAnterior);
        }
        
    }
    
}
