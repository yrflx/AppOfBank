package com.yurifelix.appofbank.Conexao;

import android.os.AsyncTask;
import android.util.Log;

import com.yurifelix.appofbank.R;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.net.InetSocketAddress;
import java.net.Socket;

/**
 * Created by yurif on 03/05/2018.
 */

public class Conexao {

    private Socket socket;

    private static String IP = "192.168.0.20";

    private InputStream inputStream;
    private OutputStream outputStream;
    private BufferedReader bufferedReader;
    private PrintStream printStream;
    private int porta;

    private InetSocketAddress inetSocketAddress;

    public Conexao(int porta) {
        this.porta = porta;
    }

    public Boolean conectaServidor()  {
        socket = new Socket();

        try {

            socket.connect(new InetSocketAddress(IP,porta));
            socket.setSoTimeout(10 * 1000);

            inputStream = socket.getInputStream();
            outputStream = socket.getOutputStream();

            bufferedReader = new BufferedReader(new InputStreamReader(inputStream)); //in

            printStream = new PrintStream(outputStream);

            //objectInputStream = new ObjectInputStream(socket.getInputStream());
            // objectOutputStream = new ObjectOutputStream(socket.getOutputStream());


        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }

        return true;
    }

    public void fechaConexao(){
        try {

            this.enviaString("FIM");
            bufferedReader.close();
            inputStream.close();
            outputStream.close();
            socket.close();
        } catch (IOException e) {


            e.printStackTrace();
        }
    }


    public void enviaString(String s){

        printStream.println(s);
        printStream.flush();
    }


    public String recebeString(){
        String recebe = "";

        try {
            recebe = bufferedReader.readLine();

        } catch (IOException e) {
            e.printStackTrace();
        }

        return recebe;
    }


    public Boolean isConected(){
        if(socket.isConnected()){
            return true;
        }
        return false;
    }


}
