package com.varejodigital.fragments.base;


import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.varejodigital.R;
import com.varejodigital.components.listviewfilter.PinnedHeaderAdapter;
import com.varejodigital.components.listviewfilter.ui.IndexBarView;
import com.varejodigital.components.listviewfilter.ui.PinnedHeaderListView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Locale;

/**
 * A simple {@link Fragment} subclass.
 */
public abstract class FilterFragment extends BaseFragment implements PinnedHeaderAdapter.OnFilterResult, AdapterView.OnItemClickListener {


    // unsorted list items
    protected ArrayList<?> mItems;

    // array list to store section positions
    protected ArrayList<Integer> mListSectionPos;

    // array list to store listView data
    protected ArrayList mListItems;

    // custom list view with pinned header
    PinnedHeaderListView mListView;

    // custom adapter
    PinnedHeaderAdapter mAdaptor;

    // search box
    EditText mSearchView;

    // loading view
    ProgressBar mLoadingView;

    // empty view
    TextView mEmptyView;

    protected abstract Object[] getItems();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_filter, container, false);

        mSearchView = (EditText) view.findViewById(R.id.search_view);
        mLoadingView = (ProgressBar) view.findViewById(R.id.loading_view);
        mListView = (PinnedHeaderListView) view.findViewById(R.id.list_view);
        mEmptyView = (TextView) view.findViewById(R.id.empty_view);

        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

//        // Array to ArrayList
//        mItems = new ArrayList<>(Arrays.asList(getItems()));
        mItems = new ArrayList<>();
        mListSectionPos = new ArrayList<>();
        mListItems = new ArrayList<>();

        // for handling configuration change
        if (savedInstanceState != null) {
            mListItems = (ArrayList<?>) savedInstanceState.getSerializable("mListItems");//StringArrayList("mListItems");
            mListSectionPos = savedInstanceState.getIntegerArrayList("mListSectionPos");

            if (mListItems != null && mListItems.size() > 0 && mListSectionPos != null && mListSectionPos.size() > 0) {
                setListAdaptor();
            }

            String constraint = savedInstanceState.getString("constraint");
            if (constraint != null && constraint.length() > 0) {
                mSearchView.setText(constraint);
                setIndexBarViewVisibility(constraint);
            }
        }
//        else {
//            new Poplulate().execute(mItems);
//        }

        mSearchView.addTextChangedListener(filterTextWatcher);
    }

    public void setItems(Object[] items){

        // Array to ArrayList
        mItems = new ArrayList<>(Arrays.asList(items));
//        mListSectionPos = new ArrayList<>();
//        mListItems = new ArrayList<>();

        new Poplulate().execute(mItems);
    }


    private void setListAdaptor() {
        // create instance of PinnedHeaderAdapter and set adapter to list view
        mAdaptor = new PinnedHeaderAdapter(getActivity(), mListItems, mListSectionPos);
        mAdaptor.setListener(this);
        mListView.setAdapter(mAdaptor);

        LayoutInflater inflater = (LayoutInflater) getActivity().getSystemService(Activity.LAYOUT_INFLATER_SERVICE);

        // set header view
        View pinnedHeaderView = inflater.inflate(R.layout.section_row_view, mListView, false);
        mListView.setPinnedHeaderView(pinnedHeaderView);
        mListView.setOnItemClickListener(this);

        // set index bar view
        IndexBarView indexBarView = (IndexBarView) inflater.inflate(R.layout.index_bar_view, mListView, false);
        indexBarView.setData(mListView, mListItems, mListSectionPos);
        mListView.setIndexBarView(indexBarView);

        // set preview text view
        View previewTextView = inflater.inflate(R.layout.preview_view, mListView, false);
        mListView.setPreviewView(previewTextView);

        // for configure pinned header view on scroll change
        mListView.setOnScrollListener(mAdaptor);
    }

    private TextWatcher filterTextWatcher = new TextWatcher() {
        public void afterTextChanged(Editable s) {
            String str = s.toString();
            if (mAdaptor != null && str != null)
                mAdaptor.getFilter(mItems).filter(str);
        }

        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        }

        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }
    };


    private void setIndexBarViewVisibility(String constraint) {
        // hide index bar for search results
        if (constraint != null && constraint.length() > 0) {
            mListView.setIndexBarVisibility(false);
        } else {
            mListView.setIndexBarVisibility(true);
        }
    }

    @SuppressWarnings("unchecked")
    @Override
    public void onfilterResult(CharSequence constraint, ArrayList<?> resultsFiltered) {
        setIndexBarViewVisibility(constraint.toString());
        new Poplulate().execute(resultsFiltered);
    }

    @Override
    public abstract void onItemClick(AdapterView<?> parent, View view, int position, long id);

    // sort array and extract sections in background Thread here we use
    // AsyncTask
    public class Poplulate extends AsyncTask<ArrayList<?>, Void, Void> {

        private void showLoading(View contentView, View loadingView, View emptyView) {
            contentView.setVisibility(View.GONE);
            loadingView.setVisibility(View.VISIBLE);
            emptyView.setVisibility(View.GONE);
        }

        private void showContent(View contentView, View loadingView, View emptyView) {
            contentView.setVisibility(View.VISIBLE);
            loadingView.setVisibility(View.GONE);
            emptyView.setVisibility(View.GONE);
        }

        private void showEmptyText(View contentView, View loadingView, View emptyView) {
            contentView.setVisibility(View.GONE);
            loadingView.setVisibility(View.GONE);
            emptyView.setVisibility(View.VISIBLE);
        }

        @Override
        protected void onPreExecute() {
            // show loading indicator
            showLoading(mListView, mLoadingView, mEmptyView);
            super.onPreExecute();
        }

        @Override
        protected Void doInBackground(ArrayList<?>... params) {
            mListItems.clear();
            mListSectionPos.clear();
            ArrayList<?> items = params[0];
            if (mItems.size() > 0) {

                // NOT forget to sort array
                Collections.sort(items, new SortIgnoreCase());

                String prev_section = "";
//                for (String current_item : items) {
                for (int i = 0; i < items.size(); i++ ) {
                    String current_section = items.get(i).toString().substring(0, 1).toUpperCase(Locale.getDefault());

                    if (!prev_section.equals(current_section)) {
                        mListItems.add(current_section);
                        mListItems.add(items.get(i));
                        // array list of section positions
                        mListSectionPos.add(mListItems.indexOf(current_section));
                        prev_section = current_section;
                    } else {
                        mListItems.add(items.get(i));
                    }
                }
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            if (!isCancelled()) {
                if (mListItems.size() <= 0) {
                    showEmptyText(mListView, mLoadingView, mEmptyView);
                } else {
                    setListAdaptor();
                    showContent(mListView, mLoadingView, mEmptyView);
                }
            }
            super.onPostExecute(result);
        }
    }

    public class SortIgnoreCase implements Comparator<Object> {
        public int compare(Object s1, Object s2) {
            return s1.toString().compareToIgnoreCase(s2.toString());
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        if (mListItems != null && mListItems.size() > 0) {
            outState.putSerializable("mListItems", mListItems);
        }
        if (mListSectionPos != null && mListSectionPos.size() > 0) {
            outState.putIntegerArrayList("mListSectionPos", mListSectionPos);
        }
        String searchText = mSearchView.getText().toString();
        if (searchText != null && searchText.length() > 0) {
            outState.putString("constraint", searchText);
        }
        super.onSaveInstanceState(outState);
    }
}
