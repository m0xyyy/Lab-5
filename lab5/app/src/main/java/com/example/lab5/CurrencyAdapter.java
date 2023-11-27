import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;

public class CurrencyAdapter extends ArrayAdapter<Currency> implements Filterable {
    private List<Currency> currencyList;
    private List<Currency> filteredList;
    private LayoutInflater inflater;
    private Filter filter;

    public CurrencyAdapter(Context context, List<Currency> currencies) {
        super(context, 0, currencies);
        this.currencyList = currencies;
        this.filteredList = new ArrayList<>(currencies);
        this.inflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        // Implement getView for custom layout
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.item_currency, parent, false);
        }

        // Bind data to views
        TextView codeTextView = convertView.findViewById(R.id.codeTextView);
        TextView rateTextView = convertView.findViewById(R.id.rateTextView);

        Currency currency = getItem(position);
        if (currency != null) {
            codeTextView.setText(currency.getCode());
            rateTextView.setText(String.valueOf(currency.getRate()));
        }

        return convertView;
    }

    @NonNull
    @Override
    public Filter getFilter() {
        if (filter == null) {
            filter = new CurrencyFilter();
        }
        return filter;
    }

    private class CurrencyFilter extends Filter {
        @Override
        protected FilterResults performFiltering(CharSequence charSequence) {
            // Implement filtering logic
            FilterResults results = new FilterResults();
            List<Currency> filteredCurrencies = new ArrayList<>();

            if (charSequence == null || charSequence.length() == 0) {
                // No filter, return the original list
                filteredCurrencies.addAll(currencyList);
            } else {
                // Filter currencies based on input
                String filterPattern = charSequence.toString().toLowerCase().trim();
                for (Currency currency : currencyList) {
                    if (currency.getCode().toLowerCase().contains(filterPattern)) {
                        filteredCurrencies.add(currency);
                    }
                }
            }

            results.values = filteredCurrencies;
            results.count = filteredCurrencies.size();
            return results;
        }

        @Override
        protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
            // Update filteredList and notify the adapter
            filteredList.clear();
            filteredList.addAll((List<Currency>) filterResults.values);
            notifyDataSetChanged();
        }
    }
}
