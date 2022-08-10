package miscStuff;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;

import mainStuff.MainGameComponent;
import mainStuff.Play;

import java.io.FileReader;

public class Config {
    MainGameComponent gp;
    Play pl;
    public Config(MainGameComponent g){
        gp = g;
    }

    public Config(Play p){
        pl = p;
    }

    public void saveConfig(){
        try{
           BufferedWriter bw = new BufferedWriter(new FileWriter("assets/config.txt"));
           System.out.println(pl.getNumOfPlayer());
           bw.write("" + pl.getNumOfPlayer());
           bw.newLine();
           bw.write("" + pl.getMaxScore());
           bw.close();
        }
        catch (IOException e){
           e.printStackTrace();
        }
    }

    public void loadConfig(){
        try {
            BufferedReader br = new BufferedReader(new FileReader("assets/config.txt"));
            String s;
              
            s = br.readLine();
            int scr = Integer.parseInt(s);
            gp.setGameType(scr);


            s = br.readLine();
            scr = Integer.parseInt(s);
            gp.setGameScore(scr);
            br.close();

        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}
