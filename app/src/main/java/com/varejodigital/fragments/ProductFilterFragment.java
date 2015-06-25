package com.varejodigital.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Toast;

import com.varejodigital.R;
import com.varejodigital.activities.ProductDetailActivity;
import com.varejodigital.fragments.base.FilterFragment;
import com.varejodigital.model.ApiProdutos;
import com.varejodigital.repository.RestClient;
import com.varejodigital.utilities.Constante;
import com.varejodigital.utilities.StringUtils;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by thiagocortat on 3/28/15.
 */
public class ProductFilterFragment extends FilterFragment {

    public static ProductFilterFragment newInstance() {
        ProductFilterFragment f = new ProductFilterFragment();
        return (f);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    protected String[] getItems() {
        return getResources().getStringArray(R.array.products_array);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        ApiProdutos.Produto produto = (ApiProdutos.Produto) parent.getAdapter().getItem(position);

        Intent intent = new Intent(getActivity(), ProductDetailActivity.class);
        intent.putExtra(Constante.Extra.ID, produto.getId());
        startActivity(intent);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        connectService();
    }

    public void setListAdapter(List<ApiProdutos.Produto> list){

        for (Iterator<ApiProdutos.Produto> it = list.iterator(); it.hasNext(); ) {
            ApiProdutos.Produto produto = it.next();
            if (StringUtils.isEmpty(produto.getNome()))
                it.remove();
        }

        mItems = new ArrayList<>(list);

        new Poplulate().execute(mItems);
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

        RestClient restClient = new RestClient();
        restClient.getApiService().obtainProdutos(new Callback<ApiProdutos>() {
            @Override
            public void success(ApiProdutos apiProdutos, Response response) {

                try {
                    setListAdapter(apiProdutos.getList());
                    hideProgress();
                }catch (Exception e) {
                    failure(null);
                }
            }

            @Override
            public void failure(RetrofitError error) {

                setContentEmpty(true);
                setEmptyText("Erro de Conexão, por favor tente novamente!");
                hideProgress();
            }
        });
    }

}
