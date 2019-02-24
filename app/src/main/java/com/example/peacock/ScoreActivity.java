package com.example.peacock;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

public class ScoreActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scores);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        populateSpinner(R.id.oudlers, R.array.oudlers);
        populateSpinner(R.id.handful, R.array.handfuls);
        populateSpinner(R.id.contract, R.array.contracts);
        populateSpinner(R.id.slam, R.array.slams);
        populateSpinner(R.id.oneatend, R.array.oneatend);
        openKeyboard();
    }

    private void openKeyboard() {
        findViewById(R.id.points).requestFocus();
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
    }
    private void closeKeyboard() {
        InputMethodManager imm = (InputMethodManager) this.getSystemService(Activity.INPUT_METHOD_SERVICE);

        View view = this.getCurrentFocus();
        if (view == null) {
            view = new View(this);
        }
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    private void populateSpinner(int viewId, int arrayId) {
        Spinner spinner = (Spinner) findViewById(viewId);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                arrayId, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setSelection(0);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.nav_renton) {
            Intent intent = new Intent(this, RentonActivity.class);
            startActivity(intent);
            this.finish();
        } else if (id == R.id.nav_scores) {

        } else if (id == R.id.nav_leaderboard) {
            Intent intent = new Intent(this, LeaderboardActivity.class);
            startActivity(intent);
            this.finish();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void computeHandler(View view) {
        closeKeyboard();
        ((TextView) findViewById(R.id.finalscore)).setText(Float.toString(computeFinalScore()));
    }

    private float computeFinalScore() {
        float extraPoints = 0;
        float finalScore;
        int handfulPoints = 0;
        int multiplier = 0;
        int oneAtEndPoints = 0;
        int slamPoints = 0;
        float points = Float.parseFloat(((EditText) findViewById(R.id.points)).getText().toString());
        int oudlers = Integer.parseInt(((Spinner) findViewById(R.id.oudlers)).getSelectedItem().toString());
        String contract = ((Spinner) findViewById(R.id.contract)).getSelectedItem().toString();
        String handful = ((Spinner) findViewById(R.id.handful)).getSelectedItem().toString();
        String slam = ((Spinner) findViewById(R.id.slam)).getSelectedItem().toString();
        String oneAtEnd = ((Spinner) findViewById(R.id.oneatend)).getSelectedItem().toString();

        if (contract.equals(getResources().getString(R.string.small))) multiplier=2;
        if (contract.equals(getResources().getString(R.string.guard))) multiplier=4;
        if (contract.equals(getResources().getString(R.string.guardw))) multiplier=6;
        if (contract.equals(getResources().getString(R.string.guarda))) multiplier=8;

        if (oneAtEnd.equals(getResources().getString(R.string.kept))) oneAtEndPoints=10;
        if (oneAtEnd.equals(getResources().getString(R.string.lost))) oneAtEndPoints=-10;

        if (slam.equals(getResources().getString(R.string.announced))) slamPoints=400;
        if (slam.equals(getResources().getString(R.string.unannounced))) slamPoints=200;
        if (slam.equals(getResources().getString(R.string.failed))) slamPoints=-200;

        switch (oudlers){
            case 0: extraPoints = points - 56; break;
            case 1: extraPoints = points - 51; break;
            case 2: extraPoints = points - 41; break;
            case 3: extraPoints = points - 36; break;
        }

        if (extraPoints >= 0){
            if (handful.equals(getResources().getString(R.string.simple))) handfulPoints=60;
            if (handful.equals(getResources().getString(R.string._double))) handfulPoints=90;
            if (handful.equals(getResources().getString(R.string.triple))) handfulPoints=120;

            finalScore = (extraPoints + oneAtEndPoints + 25) * multiplier + handfulPoints + slamPoints;


        } else{
            if (handful.equals(getResources().getString(R.string.simple))) handfulPoints=-40;
            if (handful.equals(getResources().getString(R.string._double))) handfulPoints=-60;
            if (handful.equals(getResources().getString(R.string.triple))) handfulPoints=-80;

            finalScore = (extraPoints + oneAtEndPoints - 25) * multiplier + handfulPoints + slamPoints;
        }

        return finalScore;
    }
}
