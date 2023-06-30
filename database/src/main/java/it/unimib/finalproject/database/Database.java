package it.unimib.finalproject.database;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;


public class Database {
    
    private Map<String, String> database = new HashMap<String, String>();

    public Database(){
        File myObj = new File("database/src/main/java/it/unimib/finalproject/database/startMovies.txt");
        try {
            Scanner myReader = new Scanner(myObj);
            while (myReader.hasNextLine()) {
                String data = myReader.nextLine();
                String[] keyValue = data.split("&");
                database.put(keyValue[0], keyValue[1]);
            }
            myReader.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public String getData(String key) {
        return database.get(key);
    }

    public String getDataByType(String type){
        String result = "";
        for (Map.Entry<String, String> entry : database.entrySet()) {
            if(entry.getKey().startsWith(type)){
                result += entry.getValue() + "\n";
            }
        }
        return result;
    }

    public synchronized void setData(String key, String value) {
        database.put(key, value);
    }

    public synchronized void deleteData(String key) {
        database.remove(key);
    }

    public synchronized void updateData(String key, String value) {
        database.replace(key, value);
    }

}
