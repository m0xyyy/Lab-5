import android.os.AsyncTask;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

public class DataLoader extends AsyncTask<Void, Void, List<Currency>> {
    private WeakReference<MainActivity> activityReference;

    DataLoader(MainActivity context) {
        activityReference = new WeakReference<>(context);
    }

    @Override
    protected List<Currency> doInBackground(Void... voids) {
        try {
            // Implement data loading and parsing logic here
            // Use the Parser class to parse the data
            // For example, you can use HttpURLConnection to download data from a URL
            // Modify the URL based on your preferred data source
            URL url = new URL("http://www.floatrates.com/daily/usd.xml");
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();

            // Read the data from the InputStream
            InputStream inputStream = connection.getInputStream();
            List<Currency> currencies = Parser.parse(inputStream);

            // Close the connection
            connection.disconnect();

            return currencies;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    protected void onPostExecute(List<Currency> currencies) {
        // Update MainActivity's UI with the parsed currency data
        MainActivity activity = activityReference.get();
        if (activity != null) {
            activity.updateCurrencyList(currencies);
        }
    }
}
