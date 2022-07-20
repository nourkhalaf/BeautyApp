package com.nour.beautyapp;

import com.google.android.gms.ads.AdError;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.FullScreenContentCallback;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;
import com.google.android.gms.ads.interstitial.InterstitialAd;
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.nour.beautyapp.Model.Item;
import com.nour.beautyapp.ViewHolder.ItemViewHolder;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

public class ShowItemsActivity extends AppCompatActivity {


    private InterstitialAd mInterstitialAd;

    private DatabaseReference ItemsRef;
    private RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;


    private String CategoryName;
    private TextView category;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_items);
        ItemsRef = FirebaseDatabase.getInstance().getReference().child("Items");


        CategoryName = getIntent().getExtras().get("category").toString();



        //Adds
        MobileAds.initialize(this, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {
                loadAds();
            }
        });



        ///

        category = findViewById(R.id.category_title);
        category.setText(CategoryName);
        recyclerView = findViewById(R.id.admin_recycler_menu);
        recyclerView.setHasFixedSize(true);
        layoutManager = new GridLayoutManager(this,2);
        recyclerView.setLayoutManager(layoutManager);
    }

    private void loadAds() {

        AdRequest adRequest = new AdRequest.Builder().build();

        //InterstitialAd.load(this,"ca-app-pub-2229056719280254/5489879992", adRequest, new InterstitialAdLoadCallback() {
        InterstitialAd.load(this,"ca-app-pub-2229056719280254/5489879992", adRequest, new InterstitialAdLoadCallback() {
            @Override
            public void onAdLoaded(@NonNull InterstitialAd interstitialAd) {
                // The mInterstitialAd reference will be null until
                // an ad is loaded.
                mInterstitialAd = interstitialAd;

                mInterstitialAd.setFullScreenContentCallback(new FullScreenContentCallback(){
                    @Override
                    public void onAdDismissedFullScreenContent() {
                        // Called when fullscreen content is dismissed.
                        finish();
                        Log.d("TAG", "The ad was dismissed.");
                    }

                    @Override
                    public void onAdFailedToShowFullScreenContent(AdError adError) {
                        // Called when fullscreen content failed to show.
                        Log.d("TAG", "The ad failed to show.");
                    }

                    @Override
                    public void onAdShowedFullScreenContent() {
                        // Called when fullscreen content is shown.
                        // Make sure to set your reference to null so you don't
                        // show it a second time.
                        mInterstitialAd = null;
                        Log.d("TAG", "The ad was shown.");
                    }
                });
            }

            @Override
            public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {
                // Handle the error

                mInterstitialAd = null;
            }
        });
    }

    @Override
    public void finish() {


        if (mInterstitialAd != null) {
            mInterstitialAd.show( ShowItemsActivity.this);
        } else {
            Log.d("TAG", "The interstitial ad wasn't ready yet.");
            super.finish();
        }

    }

    @Override
    protected void onStart()
    {
        super.onStart();

        FirebaseRecyclerOptions<Item> options =
                new FirebaseRecyclerOptions.Builder<Item>()
                        .setQuery(ItemsRef.orderByChild("category").equalTo(CategoryName), Item.class)
                        .build();


        FirebaseRecyclerAdapter<Item, ItemViewHolder> adapter =
                new FirebaseRecyclerAdapter<Item, ItemViewHolder>(options) {
                    @Override
                    protected void onBindViewHolder(@NonNull ItemViewHolder holder, int position, @NonNull final Item  model)
                    {
                        holder.txtItemTitle.setText(model.getTitle());
                        holder.txtItemDetails.setText( model.getDetails());
                        holder.txtItemNotes.setText( model.getNotes());

                        Picasso.get().load(model.getImage()).into(holder.imageView);


                        holder.itemView.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v)
                            {

                                Intent intent = new Intent(ShowItemsActivity.this, ItemDetailsActivity.class);
                                intent.putExtra("itemId", model.getItemId());
                                startActivity(intent);

                            }

                        });
                    }

                    @NonNull
                    @Override
                    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
                    {
                        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_layout, parent, false);
                        ItemViewHolder holder = new ItemViewHolder(view);
                        return holder;
                    }
                };
        recyclerView.setAdapter(adapter);
        adapter.startListening();
    }

}