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

    /**
     * Get the page's name from an url such as "/delete?param=stuff
     * @param fullRoute -
     * @return realFileName
     */
    public static void getPageName(String fullRoute) {
        // analyzing the string
        //String[] tokensVal = fullRoute.split("?");

        //return tokensVal[0];
    }
}
