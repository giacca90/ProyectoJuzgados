import java.io.File;
import java.io.FileWriter;

import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

import edu.duke.FileResource;

public class busqueda {

    public void start() {
        FileResource FR = new FileResource();
        CSVParser parser = FR.getCSVParser();
        StringBuilder SB = new StringBuilder();
        for(CSVRecord record : parser)  {
            String nombre = record.get(0);
            if(nombre.contains("Penal"))  {
                SB.append(record.get(8)+"\n");
            }
        }
        File F = new File("Correos_Penal.txt");
        try{
            FileWriter W = new FileWriter(F);
            W.append(SB.toString());
            W.close();
        }
        catch (Exception e)  {}
        
        
    }

   

    public static void main(String[] args) {
        busqueda a = new busqueda();
        a.start();
    }
}
