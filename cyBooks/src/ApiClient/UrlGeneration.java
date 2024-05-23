package ApiClient;

import java.io.UnsupportedEncodingException;
import java.net.*;

public class UrlGeneration {
    // URL Components
    public final static String BaseURL = "http://catalogue.bnf.fr/api/SRU";
    public final static String version = "?version=1.2";
    public static final String SearchRetrieve = "&operation=searchRetrieve";
    public static final String query = "&query=";
    public static final String startSearch = "&startRecord=";
    public static final String stopSearch = "&maximumRecords=";

    public String BaseUrlGeneration() { // Base URL generation
        return BaseURL + version + SearchRetrieve + query;
    }


    // The following three methods, only use one and preferably at the end, an implementation has to be done in the front end triggers

    public String urlStartSearch(String url, int i) { // Start at index i
        if (i > 0) {
            url += startSearch + i;
            return url;
        } else {
            throw new IllegalArgumentException("Invalid start index: " + i);
        }
    }

    public String urlStopSearch(String url, int i) { // Stop at index i
        if (i > 0) {
            url += stopSearch + i;
            return url;
        } else {
            throw new IllegalArgumentException("Invalid stop index: " + i);
        }
    }

    public String urlBetweenIndexes(String url, int startIndex, int stopIndex) { // Limit search between two indexes
        if (startIndex > 0 && stopIndex >= startIndex) {
            url += startSearch + startIndex + stopSearch + stopIndex;
            return url;
        } else {
            throw new IllegalArgumentException("Invalid indexes: start=" + startIndex + ", stop=" + stopIndex);
        }
    }

    // Initial search methods based on attributes

    public String searchInTitle(String keyWord) { // Search in title
        String url = BaseUrlGeneration();
        url += "(bib.title all \"" + keyWord + "\")";
        return url;
    }

    public String searchByAuthor(String keyWord) { // Search by author
        String url = BaseUrlGeneration();
        url += "(bib.author all \"" + keyWord + "\")";
        return url;
    }

    public String generalSearch(String keyWord) { // General search
        String url = BaseUrlGeneration();
        url += "(bib.anywhere all \"" + keyWord + "\")";
        return url;
    }

    public String searchByISBN(String keyWord) { // Search By ISBN
        String url = BaseUrlGeneration();
        url += "(bib.ISBN all \"" + keyWord + "\")";
        return url;
    }

    public String searchByTheme(String keyWord) { // Search By Theme
        String url = BaseUrlGeneration();
        url += "(bib.subject all \"" + keyWord + "\")";
        return url;
    }

    // Additional search criteria methods

    public String addSearchInTitle(String url, String keyWord) { // Add search in title
        url += " and (bib.title all \"" + keyWord + "\")";
        return url;
    }

    public String addSearchByAuthor(String url, String keyWord) { // Add search by author
        url += " and (bib.author all \"" + keyWord + "\")";
        return url;
    }

    public String addGeneralSearch(String url, String keyWord) { // Add general search
        url += " and (bib.anywhere all \"" + keyWord + "\")";
        return url;
    }

    public String addSearchByISBN(String url, String keyWord) {
        url += " and (bib.ISBN all \"" + keyWord + "\")";
        return url;
    }

    public String addSearchByTheme(String url, String keyWord) {
        url += " and (bib.subject all \"" + keyWord + "\")";
        return url;
    }

    // URL Encoding

    public static String PrepareQuery(String url) {
        try {
            String encodedUrl = URLEncoder.encode(url, "UTF-8");
            // Encode the '&' character separately
            return encodedUrl.replace("&", "%26");
        } catch (UnsupportedEncodingException e) {
            // Handle encoding exception
            e.printStackTrace();
            return null;
        }
    }
}
