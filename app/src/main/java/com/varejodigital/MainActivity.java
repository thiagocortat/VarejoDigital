package com.varejodigital;

import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Toast;

import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.accountswitcher.AccountHeader;
import com.mikepenz.materialdrawer.model.DividerDrawerItem;
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem;
import com.mikepenz.materialdrawer.model.SecondaryDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.Nameable;
import com.varejodigital.activities.BaseActivity;
import com.varejodigital.fragments.EmployeeFilterFragment;
import com.varejodigital.fragments.ProductFilterFragment;
import com.varejodigital.fragments.base.DemoFragment;
import com.varejodigital.fragments.base.FilterFragment;


public class MainActivity extends ActionBarActivity {

    private Drawer.Result result = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Create the AccountHeader
        AccountHeader.Result headerResult = new AccountHeader()
                .withActivity(this)
                .withHeaderBackground(R.drawable.header)
                .build();

        result = new Drawer()
                .withActivity(this)
                .withAccountHeader(headerResult)
                .withToolbar(toolbar)
                .withActionBarDrawerToggleAnimated(true)
                .withActionBarDrawerToggle(true)
                .addDrawerItems(
                        new PrimaryDrawerItem().withName("Faturamento").withIdentifier(0),
                        new PrimaryDrawerItem().withName("Produto").withIdentifier(1),
                        new PrimaryDrawerItem().withName("RH").withIdentifier(2),
                        new PrimaryDrawerItem().withName("CRM").withIdentifier(3),
                        new DividerDrawerItem(),
                        new SecondaryDrawerItem().withName("Configuração").withIdentifier(4),
                        new SecondaryDrawerItem().withName("Logout").withIdentifier(5)
                )
                .withOnDrawerItemClickListener(new Drawer.OnDrawerItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int i, long id, IDrawerItem drawerItem) {

//                        if (drawerItem != null && drawerItem instanceof Nameable) {
//                            getSupportActionBar().setTitle(((Nameable) drawerItem).getNameRes());
////                            Fragment f = DemoFragment.newInstance(getResources().getString(((Nameable) drawerItem).getNameRes()));
////                            getSupportFragmentManager().beginTransaction().replace(R.id.frame_container, f).commit();
//                        }

                        if (i == 0) {
                            Fragment f = DemoFragment.newInstance("Teste");
                            getSupportFragmentManager().beginTransaction().replace(R.id.frame_container, f).commit();
                        } else if (i == 1) {
                            Fragment f = ProductFilterFragment.newInstance();
                            getSupportFragmentManager().beginTransaction().replace(R.id.frame_container, f).commit();
                        } else if (i == 2) {
                            Fragment f = EmployeeFilterFragment.newInstance();
                            getSupportFragmentManager().beginTransaction().replace(R.id.frame_container, f).commit();
                        } else if (i == 3) {
                            Fragment f = DemoFragment.newInstance("Teste");
                            getSupportFragmentManager().beginTransaction().replace(R.id.frame_container, f).commit();
                        } else if (i == 4 || i == 5 || i == 6) {
                            Toast.makeText(MainActivity.this, "Funcionalidade não disponível.", Toast.LENGTH_LONG).show();
                        }
                    }

                })
                .withFireOnInitialOnClick(true)
                .build();

        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        result.getActionBarDrawerToggle().setDrawerIndicatorEnabled(true);
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
}
