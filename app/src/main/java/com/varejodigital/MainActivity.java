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
import com.varejodigital.fragments.BillingFragment;
import com.varejodigital.fragments.CRMFragment;
import com.varejodigital.fragments.EmployeeFilterFragment;
import com.varejodigital.fragments.FaturamentoFragment;
import com.varejodigital.fragments.ProductFilterFragment;
import com.varejodigital.fragments.SettingsFragment;
import com.varejodigital.fragments.base.DemoFragment;
import com.varejodigital.model.ApiChannels;
import com.varejodigital.model.ApiFaturamento;
import com.varejodigital.repository.RestClient;

import java.util.List;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;


public class MainActivity extends AppCompatActivity {

    private Drawer.Result result = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ParseUser currentUser = ParseUser.getCurrentUser();
        if (currentUser == null) {
            loadLoginView();
        }

        final Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //Add Profile
        final IProfile profile = new ProfileDrawerItem()
                .withName("João").withEmail("joao.teste@projetandoo.com")
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
                .addDrawerItems(
                        new PrimaryDrawerItem().withName("Faturamento").withIdentifier(0),
                        new PrimaryDrawerItem().withName("Produtos").withIdentifier(1),
                        new PrimaryDrawerItem().withName("RH").withIdentifier(2),
                        new PrimaryDrawerItem().withName("CRM").withIdentifier(3),
                        new DividerDrawerItem(),
                        new SecondaryDrawerItem().withName("Configuração"),
                        new SecondaryDrawerItem().withName("Logout")
                )
                .withOnDrawerItemClickListener(new Drawer.OnDrawerItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int i, long id, IDrawerItem drawerItem) {

                        if (i == 0) {
                            Fragment f = FaturamentoFragment.newInstance();
                            getFragmentManager().beginTransaction().replace(R.id.frame_container, f).commit();
                        } else if (i == 1) {
                            Fragment f = ProductFilterFragment.newInstance();
                            getFragmentManager().beginTransaction().replace(R.id.frame_container, f).commit();
                        } else if (i == 2) {
                            Fragment f = EmployeeFilterFragment.newInstance();
                            getFragmentManager().beginTransaction().replace(R.id.frame_container, f).commit();
                        } else if (i == 3) {
//                            Fragment f = CRMFragment.newInstance();
                            Fragment f = DemoFragment.newInstance("");
                            getFragmentManager().beginTransaction().replace(R.id.frame_container, f).commit();
                        } else if (i == 5) {
                            SettingsFragment f = SettingsFragment.newInstance();
                            getFragmentManager().beginTransaction().replace(R.id.frame_container, f).commit();
                        } else if (i == 6) {
                            ParseUser.logOut();
                            loadLoginView();
                        }
                    }

                })
                //just use this with the Drawer.Builder
                .withSelectedItem(-1)
                .withFireOnInitialOnClick(false)
                .build();

        result.openDrawer();

        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        result.getActionBarDrawerToggle().setDrawerIndicatorEnabled(true);

        connectService();

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
                                if (e == null)
                                    Toast.makeText(getBaseContext(), "FOI ", Toast.LENGTH_LONG).show();
                                else
                                    Toast.makeText(getBaseContext(), "ERRO " + e.getMessage(), Toast.LENGTH_LONG).show();
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
}
