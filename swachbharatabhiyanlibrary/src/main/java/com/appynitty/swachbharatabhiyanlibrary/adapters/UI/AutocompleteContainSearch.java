package com.appynitty.swachbharatabhiyanlibrary.adapters.UI;

import android.content.Context;
import androidx.annotation.NonNull;
import android.widget.ArrayAdapter;
import android.widget.Filter;

import com.appynitty.swachbharatabhiyanlibrary.utils.AUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ayan Dey on 24/4/19.
 */
public class AutocompleteContainSearch extends ArrayAdapter<String> {

    private final List<String> suggesion;
    private final List<String> tempList;

    public AutocompleteContainSearch(Context context, int resource, ArrayList<String> object){
        super(context, resource, object);

        tempList = new ArrayList<>(object);
        suggesion = new ArrayList<>();
    }

    @NonNull
    @Override
    public Filter getFilter() {
        return containFilter;
    }

    private final Filter containFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence charSequence) {

            FilterResults filterResults = new FilterResults();
            if(AUtils.isNull(charSequence)){
                filterResults.values = tempList;
                filterResults.count = tempList.size();
                return filterResults;
            }else {
                suggesion.clear();
                for(String names : tempList){
                    if(names.toLowerCase().contains(charSequence.toString().toLowerCase())){
                        suggesion.add(names);
                    }
                }

                filterResults.values = suggesion;
                filterResults.count = suggesion.size();
                return  filterResults;
            }
        }

        @Override
        protected void publishResults(CharSequence charSequence, FilterResults results) {
            try{
                if(results != null && results.values != null){
                    List<String> list = (List<String>) results.values;
                    if(results.count > 0){
                        clear();
                        for (String name : list){
                            add(name);
                            notifyDataSetChanged();
                        }
                    }
                }
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    };
}
