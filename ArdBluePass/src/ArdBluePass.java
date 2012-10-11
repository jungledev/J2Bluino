/**
 * Bibliotecas que est�o sendo utilizadas durante a implementa��o do aplicativo.
 * */
import javax.microedition.midlet.*; //Biblioteca para poder utilizar a MIDlet e fazer o controle de vida da app
import javax.microedition.lcdui.*; //Biblioteca para desenhar elementos na tela
import javax.bluetooth.*; //Biblioteca para poder utilizar o bluetooth
import java.io.*; //Biblioteca para tratar entrada e sa�da de dados
import javax.microedition.io.*; // Biblioteca para tratar entrada e sa�da de dados

/** ArdBluePass: Arduino + Bluetooth + Password
 * 
 * Esta MIDlet � o nosso aplicativo que se comunica com o Arduino para poder enviar a senha
 * que ser� utilizada para destravar a fechadura eletr�nica.
 * Na verdade, este � o aplicativo que conhecemos como cliente.
 * 
 * O nome da classe � ArdbluePass que herda todos atributos e m�todos da classe MIDlet e 
 * implementa a escuta de comandos (CommandListener) para que possamos entender a intera��o do 
 * usu�rio com os bot�es e elementos da tela.
 * Al�m disso, tamb�m implementa o DiscoveryListener utilizado para escutar e detectar dispositivos
 * com conex�o bluetooth.
 * 
 * */
public class ArdBluePass extends MIDlet implements CommandListener, DiscoveryListener {

    private Display display;
    private Command ok, comecar,sair;
    private Form inicial;
    private List comandos;
    
    private StreamConnection con;
    private OutputStream outs;
    private InputStream ins;
    
    private LocalDevice localDevice = null;
   
    private String URL = "btspp://001204111155:1"; // Endere�o MAC do Arduino com o qual queremos enviar os dados
    
    public ArdBluePass(){
    	display = Display.getDisplay(this);
    }


    public void startApp() {
       
        ok = new Command("OK", Command.OK, 1);
        comecar = new Command("Come�ar", Command.OK, 1);
        sair = new Command("Sair", Command.EXIT, 1);

        inicial = new Form("ArdBluePass");
        comandos = new List("Escolha uma a��o", List.EXCLUSIVE);

        inicial.addCommand(comecar);
        inicial.setCommandListener(this);

        comandos.addCommand(ok);
        comandos.addCommand(sair);
        
        
        
        comandos.append("Acender LED", null);
        comandos.append("Apagar LED", null);
        comandos.setCommandListener(this);

        display.setCurrent(inicial);
    }


    public void commandAction(Command c, Displayable d) {
        if (c == comecar){
            comecar();
        }  else if(c == sair){
              destroyApp(false);
              notifyDestroyed();
        } else if (c == ok){
            enviar();
        }

    }

    public void pauseApp() {}

    public void destroyApp(boolean unconditional) {
    }

    private void comecar() {
        try {
            localDevice = LocalDevice.getLocalDevice();
            localDevice.setDiscoverable(DiscoveryAgent.GIAC);
            Display.getDisplay(this).setCurrent(comandos);
        } catch (BluetoothStateException e) {
            e.printStackTrace();
            Alert alerta = new Alert("Error", "Bluetooth n�o dispon�vel", null, AlertType.ERROR);
            alerta.setTimeout(Alert.FOREVER);
            Display.getDisplay(this).setCurrent(alerta);
        }
    }



    private  void conectar(){
        try{
            con = (StreamConnection) Connector.open(URL);
            outs = con.openOutputStream();
            ins = con.openInputStream();
        }
        catch(IOException e){
        	mostrarAlerta(e, comandos, 3);
        }
    }

    private void desconectar() {
        try {
            ins.close();
            outs.close();
            con.close();
        } catch (IOException e) {
        	mostrarAlerta(e, comandos, 4);
        }
    }

    private void enviar(){
       conectar();
       
       int i = comandos.getSelectedIndex();
       String comando_elegido = comandos.getString(i);
       
       if(comando_elegido == "Acender LED"){
           char greeting = '1';
           
           try {
        	   
           outs.write((byte) greeting);
           mostrarAlerta(null, comandos, 5);
           }
           catch(IOException e){
        	   mostrarAlerta(e, comandos,2);
           }
       }
       else{
           char greeting = '2';
          
           try {
           outs.write((byte) greeting);
           mostrarAlerta(null, comandos, 5);
           }
           catch(IOException e){
        	   mostrarAlerta(e, comandos,2);
           }

       }
       desconectar();


    }


    public void mostrarAlerta(Exception e, Screen s, int tipo) {
        Alert alerta = null;
        if (tipo == 0) {
            alerta = new Alert("Excepcion", "Exception " + e.getClass().getName(), null,
                    AlertType.ERROR);
        } else if (tipo == 1) {
            alerta = new Alert("Info", "Completou o proceso de busca de servi�os", null, AlertType.INFO);
        } else if (tipo == 2) {
            alerta = new Alert("ERROR", "N�o se pode enviar", null, AlertType.ERROR);
        } else if (tipo == 3) {
            alerta = new Alert("ERROR", "N�o se pode estabelecer conex�o" + e.getClass().getName(), null, AlertType.ERROR);
        } else if (tipo == 4) {
            alerta = new Alert("ERROR", "Nao se pode desconectar" , null, AlertType.INFO);
        }  else if (tipo == 5) {
            alerta = new Alert("INFO", "Comando enviado para Arduino" , null, AlertType.INFO);
        }
        alerta.setTimeout(Alert.FOREVER);
        display.setCurrent(alerta, s);
    }
    
   public void deviceDiscovered(RemoteDevice remoteDevice, DeviceClass deviceClass) {}
   public void inquiryCompleted(int discType) {}
   public void servicesDiscovered(int transID, ServiceRecord[] servRecord) {}
   public void serviceSearchCompleted(int transID, int respCode) {}


}


