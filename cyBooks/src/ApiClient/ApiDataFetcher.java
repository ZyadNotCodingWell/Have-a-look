package ApiClient;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashSet;
import java.util.Set;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;



import cyBooks.Book;

public class ApiDataFetcher {

    public static Set<Book> parseResponse(String xmlResponse) {
        Set<Book> books = new HashSet<>();
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document doc = builder.parse(new ByteArrayInputStream(xmlResponse.getBytes()));
            NodeList recordList = doc.getElementsByTagName("record");

            for (int i = 0; i < recordList.getLength(); i++) {
                Element record = (Element) recordList.item(i);
                Element metadata = (Element) record.getElementsByTagName("metadata").item(0);

                String title = getTagValue("title", metadata);
                String isbn = getTagValue("ISBN", metadata);
                String author = getTagValue("author", metadata);
                String theme = getTagValue("subject", metadata);

                books.add(new Book(isbn, title, author, theme != null ? theme.toLowerCase() : null));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return books;
    }

    private static String getTagValue(String tag, Element element) {
        NodeList nodeList = element.getElementsByTagName(tag);
        if (nodeList != null && nodeList.getLength() > 0) {
            return nodeList.item(0).getTextContent();
        }
        return null;
    }

    public static Set<Book> fetchBooksFromApi(String apiUrl) throws Exception {
        URL url = new URL(apiUrl);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        int responseCode = conn.getResponseCode();
        if (responseCode == HttpURLConnection.HTTP_OK) {
            BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String inputLine;
            StringBuilder response = new StringBuilder();
            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();
            return parseResponse(response.toString());
        } else {
            throw new IOException("Failed to fetch data from API. Response code: " + responseCode);
        }
    }
    // ##########################################################################

    
public static Set<Book> NewParseResponse(String xmlResponse) {
    Set<Book> books = new HashSet<>();

    try {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document doc = builder.parse(new ByteArrayInputStream(xmlResponse.getBytes()));

        NodeList recordList = doc.getElementsByTagName("srw:record");

        for (int i = 0; i < recordList.getLength(); i++) {
            Element record = (Element) recordList.item(i);
            Element recordData = (Element) record.getElementsByTagName("srw:recordData").item(0);
            Element mxcRecord = (Element) recordData.getElementsByTagName("mxc:record").item(0);

            String isbn = getValueFromXPath(mxcRecord, "//mxc:datafield[@tag='010']/mxc:subfield[@code='a']");
            if (isbn == null || isbn.isEmpty()) {
                // Skip records without an ISBN
                continue;
            }

            String title = getValueFromXPath(mxcRecord, "//mxc:datafield[contains(@tag, '200')]/mxc:subfield[@code='a']");
            String author = getAuthorFromXPath(mxcRecord);
            String theme = getSubjectFromXPath(mxcRecord);

            books.add(new Book(isbn, title, author, theme));
        }
    } catch (Exception e) {
        e.printStackTrace();
    }

    return books;
}

private static String getValueFromXPath(Node node, String xpathExpression) {
    try {
        XPathFactory xpathFactory = XPathFactory.newInstance();
        XPath xpath = xpathFactory.newXPath();
        NodeList nodes = (NodeList) xpath.evaluate(xpathExpression, node, XPathConstants.NODESET);

        if (nodes.getLength() > 0) {
            return nodes.item(0).getTextContent();
        }
    } catch (XPathExpressionException e) {
        e.printStackTrace();
    }

    return null;
}

private static String getAuthorFromXPath(Node node) {
    StringBuilder sb = new StringBuilder();

    try {
        XPathFactory xpathFactory = XPathFactory.newInstance();
        XPath xpath = xpathFactory.newXPath();

        String[] authorXpaths = {
            "//mxc:datafield[@tag='700']/mxc:subfield[@code='a']",
            "//mxc:datafield[@tag='700']/mxc:subfield[@code='b']",
            "//mxc:datafield[@tag='700']/mxc:subfield[@code='3']"
        };

        for (String xpathExpression : authorXpaths) {
            NodeList nodes = (NodeList) xpath.evaluate(xpathExpression, node, XPathConstants.NODESET);
            for (int i = 0; i < nodes.getLength(); i++) {
                sb.append(nodes.item(i).getTextContent()).append(" ");
            }
        }
    } catch (XPathExpressionException e) {
        e.printStackTrace();
    }

    return sb.toString().trim();
}

private static String getSubjectFromXPath(Node node) {
    StringBuilder sb = new StringBuilder();

    try {
        XPathFactory xpathFactory = XPathFactory.newInstance();
        XPath xpath = xpathFactory.newXPath();

        String[] subjectXpaths = {
            "//mxc:datafield[starts-with(@tag, '6')]/mxc:subfield[@code='a']",
            "//mxc:datafield[starts-with(@tag, '6')]/mxc:subfield[@code='x']",
            "//mxc:datafield[starts-with(@tag, '6')]/mxc:subfield[@code='y']",
            "//mxc:datafield[starts-with(@tag, '6')]/mxc:subfield[@code='z']"
        };

        for (String xpathExpression : subjectXpaths) {
            NodeList nodes = (NodeList) xpath.evaluate(xpathExpression, node, XPathConstants.NODESET);
            for (int i = 0; i < nodes.getLength(); i++) {
                sb.append(nodes.item(i).getTextContent()).append(" ");
            }
        }
    } catch (XPathExpressionException e) {
        e.printStackTrace();
    }

    return sb.toString().trim();
}

    
}




