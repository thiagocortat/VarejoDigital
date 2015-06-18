package com.varejodigital.activities;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.varejodigital.R;
import com.varejodigital.model.ApiProduto;
import com.varejodigital.repository.RestClient;
import com.varejodigital.utilities.Constante;

import butterknife.ButterKnife;
import butterknife.InjectView;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class ProductDetailActivity extends BaseActivity {

    @InjectView(R.id.imageProduct)
    ImageView imageProduct;
    @InjectView(R.id.txtName)
    TextView txtName;
    @InjectView(R.id.txCusto)
    TextView txCusto;
    @InjectView(R.id.txPrice)
    TextView txPrice;
    @InjectView(R.id.txLucro)
    TextView txLucro;
    @InjectView(R.id.txMinimo)
    TextView txMinimo;
    @InjectView(R.id.txMaximo)
    TextView txMaximo;
    @InjectView(R.id.txDisponivel)
    TextView txDisponivel;
    @InjectView(R.id.txRessuprimento)
    TextView txRessuprimento;


    private ApiProduto.Produto mProduto;
    private int mID;

    @Override
    public void onAfterInjectViews(Bundle savedInstanceState) {

        final Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setTitle(getString(R.string.product_detail));

        if (getIntent().hasExtra(Constante.Extra.ID)) {
            mID = getIntent().getIntExtra(Constante.Extra.ID, 0);

            showProgress();

            RestClient restClient = new RestClient();
            restClient.getApiService().obtainProduto(mID, new Callback<ApiProduto>() {
                @Override
                public void success(ApiProduto apiProduto, Response response) {
//                    Toast.makeText(getBaseContext(), "Foi", Toast.LENGTH_LONG).show();

                    mProduto = apiProduto.getProduto();
                    if (mProduto.getImagens().size() > 0)
                        loadImage(mProduto.getImagens().get(0).getUrl());

                    txtName.setText(mProduto.getNome());
                    txCusto.setText("R$ " + mProduto.getCusto());
                    txPrice.setText("R$ " + mProduto.getPreco());

                    txLucro.setText("XXX");

                    txMinimo.setText("" + mProduto.getEstoque().getMinimo());
                    txMaximo.setText("" + mProduto.getEstoque().getMaximo());
                    txDisponivel.setText("" + mProduto.getEstoque().getDisponivel());
                    txRessuprimento.setText("" +  mProduto.getEstoque().getPontoRessuprimento());


                    hideProgress();
                }

                @Override
                public void failure(RetrofitError error) {
//                    Toast.makeText(getBaseContext(), "Erro " + error.getMessage(), Toast.LENGTH_LONG).show();

                    setEmptyText("Erro de Conexão, por favor tente novamente!");
                    hideProgress();
                }
            });
        }
    }

    @Override
    public int getLayoutResource() {
        return R.layout.activity_product_detail;
    }


    private void loadImage(String url) {

        Picasso.with(ProductDetailActivity.this)
                .load(url)
                .placeholder(R.drawable.img_product_placeholder)
                .into(imageProduct);
    }
}
