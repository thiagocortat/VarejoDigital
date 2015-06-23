package com.varejodigital.activities;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.varejodigital.R;
import com.varejodigital.model.ApiProduto;
import com.varejodigital.repository.RestClient;
import com.varejodigital.utilities.Constante;

import java.text.DecimalFormat;
import java.util.List;

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
    @InjectView(R.id.txVendaDia)
    TextView txVendaDia;
    @InjectView(R.id.txVendaSemana)
    TextView txVendaSemana;
    @InjectView(R.id.txVendaMes)
    TextView txVendaMes;
    @InjectView(R.id.linearFornecedor)
    LinearLayout linearFornecedor;


    private ApiProduto.Produto mProduto;
    private int mID;

    @Override
    public int getLayoutResource() {
        return R.layout.activity_product_detail;
    }


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

                    try {
                        Double lucro = Double.parseDouble(mProduto.getMargem());
                        DecimalFormat df = new DecimalFormat("0.##");
                        txLucro.setText("" + df.format(lucro * 100) + "%");
                    } catch (Exception e) {
                        e.printStackTrace();
                        txLucro.setText("---");
                    }

                    txMinimo.setText("" + mProduto.getEstoque().getMinimo());
                    txMaximo.setText("" + mProduto.getEstoque().getMaximo());
                    txDisponivel.setText("" + mProduto.getEstoque().getDisponivel());
                    txRessuprimento.setText("" + mProduto.getEstoque().getPontoRessuprimento());

                    txVendaDia.setText("" + mProduto.getVendaDia().getQuantidade());
                    txVendaSemana.setText("" + mProduto.getVendaSemana().getQuantidade());
                    txVendaMes.setText("" + mProduto.getVendaMes().getQuantidade());

                    List<ApiProduto.Produto.Fornecedor> fornecedores = mProduto.getFornecedores();
                    if (fornecedores != null && fornecedores.size() > 0) {
                        linearFornecedor.setVisibility(View.VISIBLE);
                        for (ApiProduto.Produto.Fornecedor f : mProduto.getFornecedores()) {
                            TextView txFornecedor = (TextView) getLayoutInflater().inflate(R.layout.textview_caption, null);
                            txFornecedor.setText(f.getNome());
                            linearFornecedor.addView(txFornecedor);
                        }
                    }

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


    private void loadImage(String url) {

        Picasso.with(ProductDetailActivity.this)
                .load(url)
                .placeholder(R.drawable.img_product_placeholder)
                .into(imageProduct);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.inject(this);
    }
}
