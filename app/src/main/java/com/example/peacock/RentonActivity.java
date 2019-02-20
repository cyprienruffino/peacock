package com.example.peacock;

import android.content.Context;
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
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

public class RentonActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private class CardClickListener implements View.OnClickListener{
        int cardID;

        public CardClickListener(int cardID){
            this.cardID = cardID;
        }

        @Override
        public void onClick(View v) {
            addCard(this.cardID);
        }
    }

    public class ImageAdapter extends BaseAdapter {
        private Context context;
        private final int[] cardImages;
        public ImageAdapter(Context context, int[] cardImages) {
            this.context = context;
            this.cardImages = cardImages;
        }

        public View getView(int position, View convertView, ViewGroup parent) {

            View view;

            if (convertView == null) {

                // get layout from mobile.xml
                            // set image based on selected text
                ImageButton imageButton = new ImageButton(context);
                imageButton.setImageResource(this.cardImages[position]);
                imageButton.setOnClickListener(new CardClickListener(position));

                view = imageButton;
            } else {
                view = convertView;
            }

            return view;
        }

        @Override
        public int getCount() {
            return cardImages.length;
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

    }

    /*
    Cards order :
    h1, h2, h3, h4, h5, h6, h7, h8, h9, h10, jack, knight, queen, king,
    t1, t2, t3, t4, t5, t6, t7, t8, t9, t10, t11, t12, t13, t14, t15, t16, t17, t18, t19, t20, t21, excuse
     */

    private int card1;
    private int card2;
    private int card3;

    final int[] cardImages = {
            R.drawable.h1,R.drawable.h2,R.drawable.h3,R.drawable.h4,R.drawable.h5,R.drawable.h6,
            R.drawable.h7,R.drawable.h8,R.drawable.h9,R.drawable.h10,R.drawable.jack,R.drawable.knight,
            R.drawable.queen,R.drawable.king,
            R.drawable.t1,R.drawable.t2,R.drawable.t3,R.drawable.t4,R.drawable.t5,R.drawable.t6,
            R.drawable.t7,R.drawable.t8,R.drawable.t9,R.drawable.t10,R.drawable.t11,R.drawable.t12,
            R.drawable.t13,R.drawable.t14,R.drawable.t15,R.drawable.t16,R.drawable.t17,R.drawable.t18,
            R.drawable.t19,R.drawable.t20,R.drawable.t21,R.drawable.excuse

    };

    final int[] cardsScores = {
            1,2,3,4,5,6,7,8,9,10,15,20,25,35,
            100,20,20,20,21,22,23,24,25,26,27,28,29,30,35,40,45,50,60,70,150,125
    };
    final int[] fullCardsScores = {
            1,2,3,4,5,6,7,8,9,10,15,20,25,35,
            1,2,3,4,5,6,7,8,9,10,15,20,25,35,
            1,2,3,4,5,6,7,8,9,10,15,20,25,35,
            1,2,3,4,5,6,7,8,9,10,15,20,25,35,
            100,20,20,20,21,22,23,24,25,26,27,28,29,30,35,40,45,50,60,70,150,125
    };


    private float computeRentonValue(){
        int score = this.cardsScores[card1] + this.cardsScores[card2] + this.cardsScores[card3];
        int max = 0;
        float count = 0;

        for (int i=0; i<fullCardsScores.length; i++)
            for (int j = 0; j < fullCardsScores.length; j++)
                if (i<56 || j < 56 || j != i)
                    for (int k = 0; k < fullCardsScores.length; k++)
                        if ((i<56 || k < 56 || k != i) && (j<56 || k < 56 || k != j)) {
                            max += 1;
                            if (score <= this.fullCardsScores[i] + this.fullCardsScores[j] + this.fullCardsScores[k])
                                count += 1;
                        }

        return count/max;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        populateCardsGrid((GridView) findViewById(R.id.dog_gridview));
        this.card1 = -1;
        this.card2 = -1;
        this.card3 = -1;


    }

    private void populateCardsGrid(GridView dogGrid) {
        GridView grid = (GridView) findViewById(R.id.dog_gridview);
        grid.setAdapter(new ImageAdapter(this, this.cardImages));
    }

    private void addCard(int card){
        if (this.card1 == -1){
            this.card1 = card;
            ((ImageButton) findViewById(R.id.card1)).setImageResource(this.cardImages[card]);
        }
        else if (this.card2 == -1){
            this.card2 = card;
            ((ImageButton) findViewById(R.id.card2)).setImageResource(this.cardImages[card]);
        }
        else {
            this.card3 = card;
            ((ImageButton) findViewById(R.id.card3)).setImageResource(this.cardImages[card]);
        }
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
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_renton) {

        } else if (id == R.id.nav_scores) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void emptyCard(View view) {
        ImageButton img = (ImageButton) view;
        img.setImageResource(R.drawable.empty);
    }

    public void clickCard1(View view) {
        this.card1 = -1;
        emptyCard(view);
    }

    public void clickCard2(View view) {
        this.card2 = -1;
        emptyCard(view);
    }

    public void clickCard3(View view) {
        this.card3 = -1;
        emptyCard(view);
    }

    public void clickRenton(View view) {
        float score = 0f;
        if (this.card1 != -1 && this.card2 != -1 && this.card3 != -1)
            score = computeRentonValue();

        TextView  rentonValue = findViewById(R.id.renton_value);
        rentonValue.setText(String.format("%.2f", score));
    }

    public void clickReset(View view) {
        this.card1 = -1;
        this.card2 = -1;
        this.card3 = -1;
        ((ImageView) findViewById(R.id.card1)).setImageResource(R.drawable.empty);
        ((ImageView) findViewById(R.id.card2)).setImageResource(R.drawable.empty);
        ((ImageView) findViewById(R.id.card3)).setImageResource(R.drawable.empty);
        ((TextView) findViewById(R.id.renton_value)).setText(Float.toString((0f)));
    }
}
