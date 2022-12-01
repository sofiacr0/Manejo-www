import jdk.nashorn.api.scripting.URLReader;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.*;
import java.util.logging.Logger;
import java.io.BufferedReader;
import java.io.InputStreamReader;

/*
Manejo de recursos en la www
    Desarrollar una aplicación Java que:
        1. reciba a través de los argumentos de línea de comandos un URL de una pagina web y una palabra.
        2. debe de contar cuantas veces aparece la palabra en el documento HTML
        3. debe decir en que posición se encuentra cada ocurrencia
 */

public class Main {
    public static final String TAG = URLReader.class.getSimpleName();

    public static final Logger LOG = Logger.getLogger(TAG);

    public static void main(String[] args) {
        Scanner tc = new Scanner(System.in);
        URL webPage = null;
        URLConnection connection = null;
        try {
            System.out.println("Ingrese una dirección html: ");
            String html = tc.nextLine();
            webPage = new URL(html);

            connection = webPage.openConnection();

        }  catch (MalformedURLException ex) {
            LOG.severe(ex.getMessage());
        } catch (IOException ex) {
            LOG.severe(ex.getMessage());
        }

        BufferedReader in = null;
        try {
            in = new BufferedReader(
                    new InputStreamReader( connection.getInputStream() ));
        } catch (IOException ex) {
            LOG.severe(ex.getMessage());
        }

        String textLine = null;

        String delimiters = "\\s+|,\\s*|\\.\\s*|\\;\\s*|\\:\\s*|\\!\\s*|\\¡\\s*|\\¿\\s*|\\?\\s*|\\-\\s*"
                + "|\\[\\s*|\\]\\s*|\\(\\s*|\\)\\s*|\\\"\\s*|\\_\\s*|\\%\\s*|\\+\\s*|\\/\\s*|\\#\\s*|\\$\\s*";

        ArrayList<String> list = new ArrayList<String>();

        String inputLine = null;

        BufferedReader inputFile = new BufferedReader(in);

        try {//lee de renglón a renglón
            while ((textLine = inputFile.readLine()) != null) {

                if (textLine.trim().length() == 0) {
                    continue;
                }

                String[] words = textLine.split(delimiters);

                for (String theWord : words) {

                    theWord = theWord.toLowerCase().trim();

                    list.add(theWord);
                }
            }
            System.out.println("Ingrese una palabra: ");
            String word = tc.nextLine();
            int veces=0;

            for (String loquesea:list) {
                if (loquesea.equals(word)) {
                    veces++;
                }
            }
            if (veces==1){
                System.out.println("Se repite una vez");
            } else if (list.contains(word)) {
                System.out.println("La palabra \"" + word + "\" se repite " + veces + " veces");
            } else {
                System.out.println("La palabra no aparece en el texto");
            }

            if (word.equals(word)){
                System.out.println(inputFile);
            }

        } catch (IOException ex) {
            LOG.severe(ex.getMessage());
        }
        try {
            in.close();
        } catch (IOException ex) {
            LOG.severe(ex.getMessage());
        }

    }
}
