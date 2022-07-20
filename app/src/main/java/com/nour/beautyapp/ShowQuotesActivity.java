package com.nour.beautyapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.nour.beautyapp.Interface.ItemClickListener;
import com.nour.beautyapp.Model.Quote;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.ads.AdError;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.FullScreenContentCallback;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;
import com.google.android.gms.ads.interstitial.InterstitialAd;
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class ShowQuotesActivity extends AppCompatActivity {

    private InterstitialAd mInterstitialAd;

    private DatabaseReference QuotesRef;
    private RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_quotes);

        QuotesRef = FirebaseDatabase.getInstance().getReference().child("Quotes");


        recyclerView = findViewById(R.id.admin_recycler_menu);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);



        //Adds
        MobileAds.initialize(this, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {
                loadAds();
            }
        });
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
            mInterstitialAd.show( ShowQuotesActivity.this);
        } else {
            Log.d("TAG", "The interstitial ad wasn't ready yet.");
            super.finish();
        }

    }


    @Override
    protected void onStart() {
        super.onStart();

        FirebaseRecyclerOptions<Quote> options =
                new FirebaseRecyclerOptions.Builder<Quote>()
                        .setQuery(QuotesRef, Quote.class)
                        .build();


        FirebaseRecyclerAdapter<Quote, QuoteViewHolder> adapter =
                new FirebaseRecyclerAdapter<Quote, QuoteViewHolder>(options) {
                    @Override
                    protected void onBindViewHolder(@NonNull QuoteViewHolder holder, int position, @NonNull final Quote model) {
                        holder.txtQuote.setText(model.getQuote());

                        holder.copy.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {

                                ClipboardManager clipboard = (ClipboardManager)
                                        getSystemService(Context.CLIPBOARD_SERVICE);

                                ClipData clip = ClipData.newPlainText("Quote", model.getQuote());
                                clipboard.setPrimaryClip(clip);

                                Toast.makeText(ShowQuotesActivity.this,"تم نسخ النص الى الحافظة" ,Toast.LENGTH_SHORT).show();


                            }
                        });


                    }

                    @NonNull
                    @Override
                    public QuoteViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.quote_layout, parent, false);
                        QuoteViewHolder holder = new QuoteViewHolder(view);
                        return holder;
                    }
                };
        recyclerView.setAdapter(adapter);
        adapter.startListening();
    }

    private class QuoteViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public TextView txtQuote;
        public ImageView copy;
        public ItemClickListener listener;


        public QuoteViewHolder(View itemView) {
            super(itemView);

            txtQuote = (TextView) itemView.findViewById(R.id.txt_quote);
            copy = (ImageView) itemView.findViewById(R.id.copy_icon);
        }

        public void setItemClickListener(ItemClickListener listener) {
            this.listener = listener;
        }

        @Override
        public void onClick(View view) {
            listener.onClick(view, getAdapterPosition(), false);
        }
    }
}
