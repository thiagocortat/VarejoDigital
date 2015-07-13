package com.varejodigital.fragments;


import android.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.varejodigital.R;
import com.varejodigital.adapter.AuditateAdapter;
import com.varejodigital.fragments.base.BaseFragment;
import com.varejodigital.model.ApiProdutos;
import com.varejodigital.repository.RestClient;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class AuditateFragment extends BaseFragment {

    @InjectView(R.id.recycler_view) protected RecyclerView mRecyclerView;
    private AuditateAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    protected static List<ApiProdutos.Produto> mListProdutos;
    RestClient restClient;

    public static AuditateFragment newInstance() {
        Bundle args = new Bundle();
        AuditateFragment fragment = new AuditateFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public int getLayoutResource() {
        return R.layout.fragment_auditate;
    }

    public void onBeforeInjectViews(Bundle savedInstanceState) {
    }

    @Override
    public void onAfterInjectViews(Bundle savedInstanceState) {

        restClient = new RestClient();

        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        mRecyclerView.setHasFixedSize(true);

        // use a linear layout manager
        mLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(mLayoutManager);

        if (mListProdutos == null)
            connectService();
        else {
            mAdapter = new AuditateAdapter(mListProdutos);
            mRecyclerView.setAdapter(mAdapter);
        }


    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        menu.clear();
        inflater.inflate(R.menu.menu_product_detail, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == R.id.reload)
            connectService();

        return super.onOptionsItemSelected(item);
    }

    public void connectService() {
        showProgress();

        restClient.getApiService().obtainProdutosToAuditate(new Callback<ApiProdutos>() {
            @Override
            public void success(ApiProdutos apiProdutos, Response response) {
                try {
                    mListProdutos = apiProdutos.getList();
                    mAdapter = new AuditateAdapter(mListProdutos);
                    mRecyclerView.setAdapter(mAdapter);
                    hideProgress();
                } catch (Exception e) {
                    failure(null);
                }
            }

            @Override
            public void failure(RetrofitError error) {
                setContentEmpty(true);
                setEmptyText(R.string.erro_conexion_try_again);
                hideProgress();
            }
        });
    }



}
