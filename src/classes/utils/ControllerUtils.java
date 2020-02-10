package classes.utils;

public class ControllerUtils {

    /**
     * Parse request id, returns 0 if incorrect.
     * @param id -
     * @return parsed id
     */
    public static int parseRequestId(String id) {
        // Math.max returns the higher number. Used to avoid negative id.
        return (isNumeric(id)) ? Math.max(Integer.parseInt(id), 0) : 0;
    }

    /**
     * Parse request id, returns 0 if incorrect.
     * @param nb -
     * @return parsed id
     */
    public static float parseNote(String nb) {
        // Float.min retourne le plus petit nombre (si > 20) et Float.max le plus grand (si < 0).
        return (isFloat(nb)) ? Float.max(Float.min(Float.parseFloat(nb), 20), 0) : 0;
    }

    /**
     * Check if a string is numeric.
     * @param strId -
     * @return bool
     */
    public static boolean isNumeric(String strId) {
        if (strId == null) {
            return false;
        }

        try {
            int i = Integer.parseInt(strId);
        } catch (NumberFormatException nfe) {
            return false;
        }

        return true;
    }

    public static boolean isFloat(String strNb) {
        try {
            Float.parseFloat(strNb);
            return true;
        } catch(NumberFormatException e) {
            return false;
        }
    }
}
