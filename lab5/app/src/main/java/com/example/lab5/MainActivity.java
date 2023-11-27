import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private ListView listView;
    private EditText filterEditText;
    private CurrencyAdapter currencyAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listView = findViewById(R.id.listView);
        filterEditText = findViewById(R.id.filterEditText);

        // Initialize and set adapter for the ListView
        currencyAdapter = new CurrencyAdapter(this, new ArrayList<>());
        listView.setAdapter(currencyAdapter);

        // Set up a text change listener for the filter
        filterEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                // Filter the currency list based on user input
                currencyAdapter.getFilter().filter(charSequence.toString());
            }

            @Override
            public void afterTextChanged(Editable editable) {}
        });

        // Start the data loading process
        new DataLoader(this).execute();
    }

    // Method to update the UI with currency data
    public void updateCurrencyList(List<Currency> currencies) {
        currencyAdapter.setCurrencies(currencies);
    }
}
