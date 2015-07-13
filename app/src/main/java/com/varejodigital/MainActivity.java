package com.varejodigital;

import android.app.AlertDialog;
import android.app.Fragment;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Toast;

import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.accountswitcher.AccountHeader;
import com.mikepenz.materialdrawer.model.DividerDrawerItem;
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem;
import com.mikepenz.materialdrawer.model.ProfileDrawerItem;
import com.mikepenz.materialdrawer.model.SecondaryDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IProfile;
import com.parse.ParseException;
import com.parse.ParsePush;
import com.parse.ParseUser;
import com.parse.PushService;
import com.parse.SaveCallback;
import com.varejodigital.activities.LoginActivity;
import com.varejodigital.fragments.AuditateFragment;
import com.varejodigital.fragments.BillingFragment;
import com.varejodigital.fragments.CRMFragment;
import com.varejodigital.fragments.EmployeeFilterFragment;
import com.varejodigital.fragments.FaturamentoFragment;
import com.varejodigital.fragments.ProductFilterFragment;
import com.varejodigital.fragments.SampleBarCodeScannerFragment;
import com.varejodigital.fragments.SettingsFragment;
import com.varejodigital.fragments.UpdateEstoqueFragment;
import com.varejodigital.fragments.base.DemoFragment;
import com.varejodigital.model.ApiChannels;
import com.varejodigital.model.ApiFaturamento;
import com.varejodigital.model.User;
import com.varejodigital.repository.RestClient;

import java.util.List;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;


public class MainActivity extends AppCompatActivity {

    private Drawer.Result result = null;
    protected Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);

//        ParseUser currentUser = ParseUser.getCurrentUser();
        User currentUser = User.getCurrentInstance();
        if (currentUser == null) {
            loadLoginView();
        }
        else {
            connectService();

            //Add Profile
            final IProfile profile = new ProfileDrawerItem()
//                .withName("João").withEmail("joao.teste@projetandoo.com")
                    .withName("Olá").withEmail(currentUser.getEmail())
                    .setEnabled(false)
                    .withIcon(getResources().getDrawable(R.drawable.profile5));

            // Create the AccountHeader
            AccountHeader.Result headerResult = new AccountHeader()
                    .withActivity(this)
                    .withHeaderBackground(R.drawable.header)
                    .addProfiles(profile)
                    .withAlternativeProfileHeaderSwitching(false)
                    .withProfileImagesClickable(false)
                    .withSelectionListEnabled(false)
                    .build();

            result = new Drawer()
                    .withActivity(this)
                    .withAccountHeader(headerResult)
                    .withToolbar(toolbar)
                    .withActionBarDrawerToggleAnimated(true)
                    .withActionBarDrawerToggle(true)
                    .addDrawerItems(getDrawerItensByRole())
                    .withOnDrawerItemClickListener(new Drawer.OnDrawerItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int i, long id, IDrawerItem drawerItem) {
                            Fragment f = null;
                            if (drawerItem.getIdentifier() == 0) {
                                f = FaturamentoFragment.newInstance();
                            }
                            else if (drawerItem.getIdentifier() == 1) {
                                f = ProductFilterFragment.newInstance();
                            }
                            else if (drawerItem.getIdentifier() == 2) {
                                f = EmployeeFilterFragment.newInstance();
                            }
                            else if (drawerItem.getIdentifier() == 3) {
//                            Fragment f = CRMFragment.newInstance();
                                f = DemoFragment.newInstance("");
                            }
                            else if (drawerItem.getIdentifier() == 4) {
                                f = AuditateFragment.newInstance();
                            }
                            else if (drawerItem.getIdentifier() == 5) {
                                f = UpdateEstoqueFragment.newInstance();
                            }
//                            else if (drawerItem.getIdentifier() == 6) {
//                                SettingsFragment f = SettingsFragment.newInstance();
//                                getFragmentManager().beginTransaction().replace(R.id.frame_container, f).commit();
//                            }
                            else if (drawerItem.getIdentifier() == 7) {
//                            ParseUser.logOut();
                                User.resetCurrentUser();
                                loadLoginView();
                                return;
                            }
                            getFragmentManager().beginTransaction().replace(R.id.frame_container, f).commit();
                        }

                    })
                            //just use this with the Drawer.Builder
                    .withSelectedItem(-1)
                    .withFireOnInitialOnClick(false)
                    .build();

            result.openDrawer();
            result.getActionBarDrawerToggle().setDrawerIndicatorEnabled(true);
        }

        SharedPreferences pref =  PreferenceManager.getDefaultSharedPreferences(this);
        boolean alert = pref.getBoolean(getString(R.string.original_checkbox_key), false);
        if (alert){
            showSimpleDialog(getString(R.string.alert_warning));
        }
    }


    private void loadLoginView() {
        Intent intent = new Intent(this, LoginActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                if (result.isDrawerOpen())
                    result.closeDrawer();
                else
                    result.openDrawer();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }


    protected void showSimpleDialog(String message) {
        try{
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle(R.string.app_name);
            builder.setMessage(message);
            builder.setNeutralButton("OK",null);
            builder.create().show();
        }catch (Exception e) {}
    }

    public void showToolBarLoading(boolean show){
        try {
            toolbar.findViewById(R.id.progress_spinner).setVisibility(show ? View.VISIBLE : View.GONE);
        }catch (Exception e){}
    }


    public void connectService() {

        RestClient restClient = new RestClient();
        restClient.getApiService().obtainCanais(new Callback<ApiChannels>() {
            @Override
            public void success(ApiChannels apiChannels, Response response) {
                if (apiChannels.getList() != null) {
                    for (String channel : apiChannels.getList()) {

                        ParsePush.subscribeInBackground(channel, new SaveCallback() {
                            @Override
                            public void done(ParseException e) {
//                                if (e == null)
//                                    Toast.makeText(getBaseContext(), "FOI ", Toast.LENGTH_LONG).show();
//                                else
//                                    Toast.makeText(getBaseContext(), "ERRO " + e.getMessage(), Toast.LENGTH_LONG).show();
                            }
                        });
                    }
                }
            }

            @Override
            public void failure(RetrofitError error) {
                Toast.makeText(getBaseContext(), "Erro " + error.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

    public IDrawerItem[] getDrawerItensByRole(){

        IDrawerItem[] items = null;
        User currentUser = User.getCurrentInstance();
        if (currentUser.getRoles().contains(User.ROLE_ADMINISTRATOR)) {
            items = new IDrawerItem[] {
                    new PrimaryDrawerItem().withName("Faturamento").withIdentifier(0),
                    new PrimaryDrawerItem().withName("Produtos").withIdentifier(1),
                    new PrimaryDrawerItem().withName("Auditoria").withIdentifier(4),
                    new PrimaryDrawerItem().withName("Código de Barras").withIdentifier(5),
                    new DividerDrawerItem(),
                    new SecondaryDrawerItem().withName("Logout").withIdentifier(7)};
        }
        else if (currentUser.getRoles().contains(User.ROLE_AUDITOR)) {
            items = new IDrawerItem[] {
                    new PrimaryDrawerItem().withName("Produtos").withIdentifier(1),
                    new PrimaryDrawerItem().withName("Código de Barras").withIdentifier(5),
                    new DividerDrawerItem(),
                    new SecondaryDrawerItem().withName("Logout").withIdentifier(7)};

        }

        return items;
    }
}
