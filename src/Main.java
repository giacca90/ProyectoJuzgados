import edu.duke.*;

import java.io.*;
import java.util.*;


public class Main {
    public final String Exit = "/Database.csv";
    public static ArrayList <String> Links;
    public static ArrayList <String> Result = new ArrayList<>();
    
    public void CreateCSV (String source)  {
        Links = new ArrayList<>();
        URLResource URL = new URLResource(source);
//        String Source = URL.asString().toLowerCase();
        String Source = URL.asString();
        Source = Source.replaceAll("\n"," ");

//System.out.println(Source);

        int index = Source.indexOf("<script language=\"javascript\">          ");
        int fin = Source.indexOf("<aside id=",index);
        Source = Source.substring(index,fin);
        index = 1;

//System.out.println(Source);

        while (index != -1)  {
            index = Source.indexOf("<a href=",index);
            if(index == -1)
                break;
            index = Source.indexOf('"', index);
            if (index != -1)  {
                int stop = Source.indexOf('"', index+1);
                if (stop != -1){
                    String S = "https://www.poderjudicial.es"+Source.substring(index+1, stop);
                    Links.add(S);

//System.out.println(S);

                    index = stop;
                } else {
                    break;
                }             
            }
        }

//for (String s : Links)  {
//    System.out.println(s);
//}

        int arraySize= Links.size();
        for (int i=0; i<arraySize; i++)  {
            String Link = Links.remove(0);

//System.out.println("--"+Link);

            boolean siguiente = true;
            while (siguiente)  { 
                URLResource URL2 = new URLResource(Link);
                String Source2 = URL2.asString();
                Source2 = Source2.replaceAll("\n"," ");
                int index2 = Source2.indexOf("<th data-cabecera=");
                int fin2 = Source2.indexOf("</tbody>", index2);
                String sub = Source2.substring(index2, fin2);
                
//String prueba = Source2.substring(index2);
//System.out.println(prueba);                
                index2=0;
                while (index2 != -1)  {
                    index2 = sub.indexOf("href=",index2);
                    if (index2 == -1)
                        break;
                    index2 = sub.indexOf('"',index2);
                    if (index2 != -1)  {
                        int stop2 = sub.indexOf('"', index2+1);
                        if (stop2 == -1)  
                            break;
                        String s = sub.substring(index2+1, stop2);
                        s = "https://www.poderjudicial.es"+s;
                        
System.out.println(s);

                        Links.add(s);
                        index2 = stop2; 
                    }
                }
                index2 = Source2.indexOf("<li class=\"liSiguiente\">");
                if (index2 == -1)
                    break;
                int stop = Source2.indexOf("</li>", index2);
                if (stop == -1)
                    break;
                String substring = Source2.substring(index2,stop);

//System.out.println(substring);

                if(substring.indexOf("href=")!=-1)  {
                    int start = substring.indexOf("href=");
                    start = substring.indexOf('"',start);
                    int stop2 = substring.indexOf('"', start+1);
                    Link = substring.substring(start+1, stop2);
                    Link = "https://www.poderjudicial.es"+Link;

System.out.println(Link);

                }else{
                    siguiente = false;
                }
            }    
        }
        File enlaces = new File("enlaces.txt");
        try {
            FileWriter w = new FileWriter(enlaces);
            for (String Link : Links)  {
                w.append(Link);
                w.append("\n");
            }
            w.close();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        for(String Link : Links)  {
            readLink(Link);
        }

        File csv = new File("pruebaCSV.csv");
        try {
            FileWriter W = new FileWriter(csv);
            for (String s : Result)  {
//System.out.println(s);
                W.append(s);
                W.append("\n");
            }
            W.close();


        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }    

    private void readLink (String Link)  {
        URLResource URL = new URLResource(Link);
        String Source = URL.asString();
        Source = Source.replaceAll("\n", " ");
        
        int start = Source.indexOf("<h2 class=\"titu7\">");
        String titulo = Source.substring(Source.indexOf(">", start)+1, Source.indexOf("<",start+10));

        start = Source.indexOf("<dt>Dirección:</dt>",start);
        start = Source.indexOf("<dd>",start);
        String direc = Source.substring(Source.indexOf(">", start)+1, Source.indexOf("<",start+5));
        direc = direc.replaceAll(",", " ");

        start = Source.indexOf("<dt>Código Postal:</dt>",start);
        start = Source.indexOf("<dd>",start);
        String codPost = Source.substring(Source.indexOf(">", start)+1, Source.indexOf("<",start+5));

        start = Source.indexOf("<dt>Municipio:</dt>",start);
        start = Source.indexOf("<dd>",start);
        String muni = Source.substring(Source.indexOf(">", start)+1, Source.indexOf("<",start+5));
        muni = muni.replaceAll(",", " ");

        start = Source.indexOf("<dt>Provincia:</dt>",start);
        start = Source.indexOf("<dd>",start);
        String prov = Source.substring(Source.indexOf(">", start)+1, Source.indexOf("<",start+5));
        prov = prov.replaceAll(",", " ");

        start = Source.indexOf("<dt>Comunidad autónoma:</dt>",start);
        start = Source.indexOf("<dd>",start);
        String comu = Source.substring(Source.indexOf(">", start)+1, Source.indexOf("<",start+5));
        comu = comu.replaceAll(",", " ");

        start = Source.indexOf("<dt>Teléfono/s:</dt>",start);
        start = Source.indexOf("<dd>",start);
        String tel = Source.substring(Source.indexOf(">", start)+1, Source.indexOf("<",start+5));
        tel = tel.replaceAll(",", " ");

        start = Source.indexOf("<dt>Fax:</dt>",start);
        start = Source.indexOf("<dd>",start);
        String fax = Source.substring(Source.indexOf(">", start)+1, Source.indexOf("<",start+5));
        fax = fax.replaceAll(",", " ");

        start = Source.indexOf("<dt>E-mail:</dt>",start);
        start = Source.indexOf("<a href=",start);
        String mail = Source.substring(Source.indexOf(">", start)+1, Source.indexOf("<",start+5));
        mail = mail.replaceAll(",", " ");

        start = Source.indexOf("<td class=\"grisOscuroTabla\">",start);
        start = Source.indexOf("<td>",start);
        String juez = Source.substring(Source.indexOf(">", start)+1, Source.indexOf("<",start+5));
        juez = juez.replaceAll(",", " ");

        StringBuilder res = new StringBuilder();
        res.append(titulo);
        res.append(" ,");
        res.append(direc);
        res.append(" ,");
        res.append(codPost);
        res.append(" ,");
        res.append(muni);
        res.append(" ,");
        res.append(prov);
        res.append(" ,");
        res.append(comu);
        res.append(" ,");
        res.append(tel);
        res.append(" ,");
        res.append(fax);
        res.append(" ,");
        res.append(mail);
        res.append(" ,");
        res.append(juez);

        String salida = res.toString();

System.out.println(salida);

        Result.add(salida);

    }    



    public static void main(String[] args) {
        Main a = new Main();
//            a.CreateCSV("https://www.poderjudicial.es/cgpj/es/Servicios/Directorio/Directorio_de_Organos_Judiciales");
//        for (String S : Links)  {
//            a.readLink(S);
//        }
        FileResource FR = new FileResource("enlaces.txt");
        for(String S : FR.lines())  {
            a.readLink(S);
        }
        File csv = new File("pruebaCSV.csv");
        try {
            FileWriter W = new FileWriter(csv);
            for (String s : Result)  {
//System.out.println(s);
                W.append(s);
                W.append("\n");
            }
            W.close();


        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }
}
