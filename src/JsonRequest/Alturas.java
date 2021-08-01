
package JsonRequest;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Scanner;
import org.json.JSONArray;
import org.json.JSONObject;



public class Alturas {
    public static void main(String[] args) {
        //First the client is created and then the request is built using the json URL mach-eight.uc.r.appspot.com
        HttpClient client =  HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder().uri(URI.create("https://mach-eight.uc.r.appspot.com/")).build();
        //So we can send this request using our client and also send it asynchronously
        //The second parameter means that we want the server to return the response as a string.
        client.sendAsync(request, HttpResponse.BodyHandlers.ofString())
                //Once the response is received, this method is applied to the previous result. Using the body method of the HttpResponse class.
                .thenApply(HttpResponse::body)
                .thenApply(Alturas::parse)
                //For this case join () acts as a return of the results obtained, without it nothing can be displayed.
                .join();
    }
    
    
    //We proceed to analyze the data from the json file
    //Because the file has a json Array structure inside a jsonObject, the code to print is built based on this.
    public static String parse(String resposeBody){
            JSONObject jsonObj = new JSONObject(resposeBody);
            JSONArray players = jsonObj.getJSONArray("values");
            int length = players.length();
            Scanner teclado = new Scanner(System.in);
            System.out.println("Enter player height in inches:");
            String filtro = teclado.next();
            //A flag is used to control the result that will be printed.
            int estado = 0;
            for(int i=0;i < length;i++){
                JSONObject player = players.getJSONObject(i);
                    String firtsName = player.getString("first_name");
                    String lastName = player.getString("last_name");
                    if(filtro.equals(player.getString("h_in"))){
                    System.out.println(firtsName + " " + lastName);
                    estado = 0;
                    }else{
                        estado = 1;
                    }
            }
            if(estado == 1){
                    System.out.println("No matches found");
                }
            return null;
        }   
}
