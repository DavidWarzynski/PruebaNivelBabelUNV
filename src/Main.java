import java.util.Scanner;

public class Main {
    public final static int FORCE_PASSWORD_POINTS_THRESHOLD = 8;

    public static void main(String[] args) {
        if(args.length <1){
            System.out.println("Se requiere el parámetro contraseña");
            System.exit(1);
        }

        String password = args[0].trim();
        int force = calculatePasswordForce(password);

        System.out.println("La contraseña: "+password+", tiene un nivel de fuerza "+forceTag(force)+" con un valor de "+force);

        if(force <= FORCE_PASSWORD_POINTS_THRESHOLD && !confirmWeakPassword()){
            System.out.println("La contraseña ha sido denegada");
            System.exit(1);
        }
        System.out.println("La contraseña ha sido aceptada");
    }

    /**
     * Método que calcúla la fuerza de la contraseña
     * @param password contraseña
     * @return fuerza de la contraseña
     */
    public static int calculatePasswordForce(String password){
        int forcePasswordPoints=0;

        forcePasswordPoints+= calculateLength(password);
        forcePasswordPoints+= calculateCase(password);
        forcePasswordPoints+= calculateDigit(password);
        forcePasswordPoints+= calculateSymbol(password);

        if(forcePasswordPoints==9){
            forcePasswordPoints++;
        }

        return forcePasswordPoints;
    }

    /**
     * Método que calcúla los puntos de la contraseña en función de su longitud
     * @param password contaseña
     * @return puntos asignados
     */
    private static int calculateLength(String password){
        int passwordLength = password.length();
        if(passwordLength> 12){
            return 3;
        } else if (passwordLength>=9) {
            return 2;
        } else if (passwordLength>=7) {
            return 1;
        }
        return 0;
    }

    /**
     * Método que calcúla los puntos de la contraseña en función de si contiene letras
     *  mayúsculas, minúsculas o ambas.
     * @param password contaseña
     * @return puntos asignados
     */
    private static int calculateCase(String password){
        int forcePasswordPoints =0;
        boolean hasLowerCase = password.matches(".*[a-z].*");
        boolean hasUpperCase = password.matches(".*[A-Z].*");
        if(hasUpperCase || hasLowerCase){
            forcePasswordPoints++;
        }
        if(hasLowerCase && hasUpperCase){
            forcePasswordPoints= forcePasswordPoints + 2;
        }
        return forcePasswordPoints;
    }

    /**
     * Método que calcúla los puntos de la contraseña en función de si presenta algún número
     * @param password contaseña
     * @return puntos asignados
     */
    private static int calculateDigit(String password){
        if(password.matches(".*\\d.*")){
            return 1;
        }
        return 0;
    }

    /**
     * Método que calcúla los puntos de la contraseña en función de si presenta algún símbolo
     * @param password contaseña
     * @return puntos asignados
     */
    private static int calculateSymbol(String password){
        if(password.matches(".*[^a-zA-Z\\d].*")){
            return 2;
        }
        return 0;
    }

    /**
     * Método qe asigna una etiqueta a la fuerza según su valor númerico
     * @param forcePasswordPoints valor númerico de la fuerza de la contaseña
     * @return etiqueta
     */
    private static String forceTag(int forcePasswordPoints){
        if(forcePasswordPoints==10){
            return "muy fuerte";
        }
        else if (forcePasswordPoints >=8){
            return "fuerte";
        }
        else if (forcePasswordPoints>=6){
            return "moderada";
        }
        else if (forcePasswordPoints>=3){
            return "débil";
        }
        else {
            return "muy débil";
        }
    }

    /**
     * Método que prgunta al usuario por consola si confirma la contraseña débil
     * @return verdadero si es confirmada, falso en caso contrario
     */
    private static boolean confirmWeakPassword(){
        Scanner reader = new Scanner(System.in);
        System.out.println("¿Confirmar contraseña débil? (s/n)");
        String response= reader.next();
        return response.equalsIgnoreCase("s");
    }
}