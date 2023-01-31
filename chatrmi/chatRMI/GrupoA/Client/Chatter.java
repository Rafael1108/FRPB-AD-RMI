package chatRMI.GrupoA.Client;

import java.util.Random;

/**
 * Una clase que representa a un usuario del chat room.
 */
public class Chatter {

    /**
     * Construye un Chatter con un nombre dado.
     *
     * @param aName
     */
    public Chatter(String aName) {
        name = aName;
         color = Chatter.generateColor();
    }

    
    /**
     * Retorna el nombre.
     *
     * @return el nombre
     */
    public String getName() {
        return name;
    }

    /**
     * Retorna el color de visualizacion en el chat.
     *
     * @return el color de visualizacion en el chat
     */
    public String getColor() {
        return color;
    }

    String color;
    String name;

    public static String generateColor() {
        // create object of Random class
        Random obj = new Random();
        int rand_num = obj.nextInt(0xffffff + 1);
// format it as hexadecimal string and print
        return String.format("#%06x", rand_num);
    }
}
