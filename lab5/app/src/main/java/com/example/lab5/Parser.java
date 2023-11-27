import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class Parser {
    public static List<Currency> parse(InputStream inputStream) {
        List<Currency> currencies = new ArrayList<>();

        try {
            // Create XML parser
            XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
            factory.setNamespaceAware(true);
            XmlPullParser parser = factory.newPullParser();

            // Set the input for the parser
            parser.setInput(inputStream, null);

            // Start parsing
            int eventType = parser.getEventType();
            Currency currentCurrency = null;

            while (eventType != XmlPullParser.END_DOCUMENT) {
                String tagName = parser.getName();

                switch (eventType) {
                    case XmlPullParser.START_TAG:
                        if ("item".equals(tagName)) {
                            currentCurrency = new Currency("", 0);
                        } else if ("targetName".equals(tagName) && currentCurrency != null) {
                            currentCurrency.setCode(parser.nextText().trim());
                        } else if ("exchangeRate".equals(tagName) && currentCurrency != null) {
                            currentCurrency.setRate(Double.parseDouble(parser.nextText().trim()));
                        }
                        break;

                    case XmlPullParser.END_TAG:
                        if ("item".equals(tagName) && currentCurrency != null) {
                            currencies.add(currentCurrency);
                            currentCurrency = null;
                        }
                        break;
                }

                eventType = parser.next();
            }

        } catch (XmlPullParserException | IOException e) {
            e.printStackTrace();
        }

        return currencies;
    }
}
