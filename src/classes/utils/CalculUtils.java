package classes.utils;

import java.util.List;

public class CalculUtils {
    /**
     * Calculer la moyenne par une liste de nombre.
     * @param nombres -
     * @return moyenne
     */
    public static Double calculMoyenne(List<Double> nombres) {
        if (nombres.size() == 0) {
            return null;
        }

        double somme = 0.00;

        for (Double n: nombres) {
            if (n != null) {
                somme += n;
            }
        }

        return somme / nombres.size();
    }

    public static String echoDoubleHTML(Double d) {
        return (d == null) ? "X" : d.toString();
    }
}
