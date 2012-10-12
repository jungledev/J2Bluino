
import javax.microedition.lcdui.*;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author fontao
 */
public class Sobre implements CommandListener {
    Form conteudo;
    StringItem texto;
    Command cmdVoltar;
    Display telaCelular;
    Form telaAnterior;
    
    public Sobre(Display tC, Form tA) {
        telaCelular = tC;
        telaAnterior = tA;
    }

    public void show(){
        conteudo = new Form("Sobre");
        texto = new StringItem("J2Bluino",
                "Versão 1.0. Com esta app você pode comunicar seu celular com o Arduino através de Bluetooth e comandar outros objetos." +
                "Para esta app enviamos uma senha para o arduino.");
        
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
