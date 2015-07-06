package com.varejodigital.fragments;


import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;

import android.os.Bundle;

import android.support.v7.app.AlertDialog;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;


import com.varejodigital.R;
import com.varejodigital.activities.BarCodeScanActivity;
import com.varejodigital.fragments.base.BaseFragment;
import com.varejodigital.model.ApiProduto;
import com.varejodigital.model.ApiUpdateProduto;
import com.varejodigital.repository.RestClient;

import java.util.HashMap;

import butterknife.InjectView;
import butterknife.OnClick;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class UpdateEstoqueFragment extends BaseFragment {


    @InjectView(R.id.editBarCode)   protected EditText editBarCode;
    @InjectView(R.id.btSendBarCode) protected Button btSendBarCode;
    @InjectView(R.id.editEstoque)   protected EditText editEstoque;
    @InjectView(R.id.editGondola)   protected EditText editGondola;
    @InjectView(R.id.layoutEstoque) protected RelativeLayout layoutEstoque;
    @InjectView(R.id.txName)        protected TextView txName;

    RestClient restClient;
    ApiProduto.Produto mProduto;

    public static UpdateEstoqueFragment newInstance() {

        Bundle args = new Bundle();
        UpdateEstoqueFragment fragment = new UpdateEstoqueFragment();
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public int getLayoutResource() {
        return R.layout.fragment_update_estoque;
    }

    public void onBeforeInjectViews(Bundle savedInstanceState) {}

    @Override
    public void onAfterInjectViews(Bundle savedInstanceState) {
        editBarCode.addTextChangedListener(textWatcherNumbers);
        restClient = new RestClient();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == Activity.RESULT_OK) {
            String barCode = data.getStringExtra(SampleBarCodeScannerFragment.BAR_CODE);
            editBarCode.setText(barCode);
        }
    }

    @OnClick (R.id.btScanBarCode)
    public void btnActonOpenBarcodeScan(){

        Intent it = new Intent(getActivity(), BarCodeScanActivity.class);
        startActivityForResult(it, 0);

    }

    @OnClick (R.id.btSendBarCode)
    public void btnActonSendBarcodeToService(){

        connectService(editBarCode.getText().toString());
    }

    @OnClick (R.id.btUpdateEstoque)
    public void btnActonUpdateEstoque(){

        ApiUpdateProduto.Produto produto = new ApiUpdateProduto.Produto(mProduto.getId());
        if (editEstoque.getVisibility() == View.VISIBLE) {
            double estoque = Double.parseDouble(editEstoque.getText().toString());
            produto.setEstoque(new ApiUpdateProduto.Produto.Estoque(estoque));
        }
        if (editGondola.getVisibility() == View.VISIBLE) {
            double gondola = Double.parseDouble(editGondola.getText().toString());
            produto.setGondola(new ApiUpdateProduto.Produto.Gondola(gondola));
        }
        ApiUpdateProduto updateProduto = new ApiUpdateProduto(produto);
        restClient.getApiService().updateProduto(updateProduto, new Callback<HashMap>() {
            @Override
            public void success(HashMap hashMap, Response response) {
                if (response.getStatus() == 200) {
                    new AlertDialog.Builder(getActivity())
                            .setTitle(R.string.app_name)
                            .setMessage(getString(R.string.dialog_message_update_qtd))
                            .setPositiveButton(getString(R.string.ok), dialogClickListener)
                            .show();
                }
            }

            @Override
            public void failure(RetrofitError error) {
                Toast.makeText(getActivity(), R.string.erro_conexion_try_again, Toast.LENGTH_LONG).show();
            }
        });

    }

    public void updateEditTextValores(ApiProduto.Produto produto) {
        mProduto = produto;
        txName.setText(produto.getNome());
        editEstoque.setText("" + produto.getEstoque().getDisponivel());
        editGondola.setText("" + produto.getGondola().getDisponivel());
    }

    public void connectService(String barcode) {
        showProgress(layoutEstoque);
        layoutEstoque.setVisibility(View.VISIBLE);

        restClient.getApiService().obtainProdutoByCode(barcode, new Callback<ApiProduto>() {
            @Override
            public void success(ApiProduto apiProduto, Response response) {

                try {
                    updateEditTextValores(apiProduto.getProduto());
                    hideProgress();

                }catch (Exception e) {
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

    protected TextWatcher textWatcherNumbers = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

        @Override
        public void afterTextChanged(Editable s) {
            btSendBarCode.setEnabled(s.toString().length() >= 8);
        }
    } ;

    protected DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
        @Override
        public void onClick(DialogInterface dialogInterface, int i) {
             editBarCode.setText("");
             layoutEstoque.setVisibility(View.GONE);
        }
    };
}
