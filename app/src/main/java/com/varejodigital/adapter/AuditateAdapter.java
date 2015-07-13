package com.varejodigital.adapter;

import android.content.Context;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.varejodigital.MainActivity;
import com.varejodigital.R;
import com.varejodigital.model.ApiProduto;
import com.varejodigital.model.ApiProdutos;
import com.varejodigital.model.ApiUpdateProduto;
import com.varejodigital.repository.RestClient;

import java.util.HashMap;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by thiagocortat on 7/12/15.
 */
public class AuditateAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    protected List<ApiProdutos.Produto> list;
    protected RestClient restClient;

    public AuditateAdapter(List<ApiProdutos.Produto> list) {
        this.list = list;
        this.restClient = new RestClient();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_auditator, null);
        return new AuditateHolder(parent.getContext(), view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        AuditateHolder auditateHolder = (AuditateHolder) holder;
        ApiProdutos.Produto produto = list.get(position);

        auditateHolder.btUpdate.setTag(produto);
        auditateHolder.btCancel.setTag(produto);
        auditateHolder.txName.setText(produto.getNome());
        auditateHolder.editEstoque.setText("0");
        auditateHolder.editGondola.setText("0");
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public void addItem(int position, ApiProdutos.Produto data) {
        list.add(position, data);
        notifyItemInserted(position);
    }

    public void removeItem(int position) {
        list.remove(position);
        notifyItemRemoved(position);
    }

    public void removeItem(ApiProdutos.Produto entity) {
        int position = getLocation(entity);
        if (position != -1) {
            list.remove(position);
            notifyItemRemoved(position);
        }
    }

    private int getLocation(ApiProdutos.Produto entity) {
        for (int j = 0; j < list.size(); ++j) {
            ApiProdutos.Produto newEntity = list.get(j);
            if (entity.equals(newEntity)) {
                return j;
            }
        }

        return -1;
    }

    public class AuditateHolder extends RecyclerView.ViewHolder {

        private Context context;
        @InjectView(R.id.txName)        TextView txName;
        @InjectView(R.id.editEstoque)   EditText editEstoque;
        @InjectView(R.id.editGondola)   EditText editGondola;
        @InjectView(R.id.btUpdate)      Button btUpdate;
        @InjectView(R.id.btCancel)      Button btCancel;

        public AuditateHolder(Context context,  View itemView) {
            super(itemView);
            this.context = context;
            ButterKnife.inject(this, itemView);
        }

        @OnClick(R.id.btCancel)
        public void btnActionChangeAdapter(View view) {
            ApiProdutos.Produto produto = (ApiProdutos.Produto) view.getTag();
            AuditateAdapter.this.removeItem(produto);
        }

        @OnClick (R.id.btUpdate)
        public void btnActonUpdateEstoque(View view) {

            final MainActivity mainActivity = (MainActivity) context;
            mainActivity.showToolBarLoading(true);

            final ApiProdutos.Produto smallProd = (ApiProdutos.Produto) view.getTag();
            ApiUpdateProduto.Produto produto = new ApiUpdateProduto.Produto(smallProd.getId());
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
                    AuditateAdapter.this.removeItem(smallProd);
                    mainActivity.showToolBarLoading(false);
                }

                @Override
                public void failure(RetrofitError error) {
                    Toast.makeText(context, R.string.erro_conexion_try_again, Toast.LENGTH_LONG).show();
                    mainActivity.showToolBarLoading(false);
                }
            });
        }




    }



}
