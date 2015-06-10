package com.varejodigital.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Toast;

import com.varejodigital.R;
import com.varejodigital.activities.ProductDetailActivity;
import com.varejodigital.fragments.base.FilterFragment;
import com.varejodigital.model.ApiProdutos;
import com.varejodigital.repository.RestClient;
import com.varejodigital.utilities.Constante;

import java.util.ArrayList;
import java.util.Arrays;
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
    protected String[] getItems() {
        return getResources().getStringArray(R.array.products_array);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent intent = new Intent(getActivity(), ProductDetailActivity.class);
        intent.putExtra(Constante.Extra.TITLE, (String) parent.getAdapter().getItem(position));
        startActivity(intent);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        RestClient restClient = new RestClient();
        restClient.getApiService().obtainProdutos(new Callback<ApiProdutos>() {
            @Override
            public void success(ApiProdutos apiProdutos, Response response) {
                Toast.makeText(getActivity(), "Foi", Toast.LENGTH_LONG).show();

                setListAdapter(apiProdutos.getList());

            }

            @Override
            public void failure(RetrofitError error) {
                Toast.makeText(getActivity(), "Erro " + error.getMessage(), Toast.LENGTH_LONG).show();
            }
        });

    }

    public void setListAdapter(List<ApiProdutos.Produto> list){

//        List<String> strings = new ArrayList<>();
//        for (ApiProdutos.Produto produto : list) {
//            strings.add(produto != null ? produto.getNome() : null);
//        }
//
//        mItems = new ArrayList<>(strings);
//
//        new Poplulate().execute(mItems);

    }
}
