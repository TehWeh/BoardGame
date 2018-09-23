package config;

import gui.Alerts;
import main.main.Main;

import java.io.*;

public class ConfigurationManager {
    static ConfigurationManager manager;

    public static synchronized ConfigurationManager getManager(){
        if(manager == null) manager = new ConfigurationManager();
        return manager;
    }

    private File config;
    private ConfigurationObject confs;

    public ConfigurationManager(){
        config = new File("config/config.cf");
        if(!config.exists()) {
            try {
                config.createNewFile();
                confs = new ConfigurationObject();
                writeConfigFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        try {
            readFromConfigFile();
        }
        catch(IOException e){
            Alerts.alertError("Unable to load saved Configurations");
        }
        catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

    }

    public void setPlayerName(String s){
        confs.playername = s;
    }

    public void setServerIP(String s){
        confs.serverip = s;
    }

    public String getPlayerName(){
        return confs.playername;
    }
    public String getServerIP(){
        return confs.serverip;
    }

    public void readFromConfigFile() throws ClassNotFoundException, IOException {
        try{
            confs =  (ConfigurationObject) new ObjectInputStream(new FileInputStream(config)).readObject();
        }
        catch(IOException e){
            confs = new ConfigurationObject();
            throw e;
        }

    }

    public void writeConfigFile() throws IOException {
        Main.getEventLogger().addEntry("Writing config file");
        config.createNewFile();
        new ObjectOutputStream(new FileOutputStream(config)).writeObject(confs);
    }

    private static class ConfigurationObject implements Externalizable {
        String playername;
        String serverip;

        public ConfigurationObject(){
            playername = "";
            serverip = "";
        }

        @Override
        public void writeExternal(ObjectOutput out) throws IOException {
            out.writeObject(playername);
            out.writeObject(serverip);
        }

        @Override
        public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
            playername = (String) in.readObject();
            serverip = (String) in.readObject();
        }
    }
}
