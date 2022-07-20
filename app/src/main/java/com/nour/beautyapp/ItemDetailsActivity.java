package com.nour.beautyapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

public class ItemDetailsActivity extends AppCompatActivity {

    private TextView title, details, notes;

    private ImageView imageView,fill_favorite, empty_favorite;

    private String itemId = "";
    private DatabaseReference itemRef;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_details);

        itemId = getIntent().getStringExtra("itemId");
        itemRef = FirebaseDatabase.getInstance().getReference().child("Items").child(itemId);


        title = (TextView) findViewById(R.id.show_item_title);
        details = (TextView) findViewById(R.id.show_item_details);
        notes = (TextView) findViewById(R.id.show_item_notes);
        imageView = (ImageView) findViewById(R.id.show_image);
        fill_favorite = (ImageView) findViewById(R.id.fill_favorite_icon);
        empty_favorite = (ImageView) findViewById(R.id.empty_favorite_icon);


        fill_favorite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                fill_favorite.setVisibility(View.GONE);
                empty_favorite.setVisibility(View.VISIBLE);
                removeItemFromFavorite();
            }
        });

        empty_favorite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fill_favorite.setVisibility(View.VISIBLE);
                empty_favorite.setVisibility(View.GONE);
               addItemToFavorite();

            }
        });

        displaySpecificItemInfo();

    }

    private void removeItemFromFavorite() {
        itemRef.child("favorite").setValue("no");
        Toast.makeText(ItemDetailsActivity.this,"تمت  إزالته من المفضلة"  ,Toast.LENGTH_SHORT).show();
    }

    private void addItemToFavorite() {
        itemRef.child("favorite").setValue("yes");
        Toast.makeText(ItemDetailsActivity.this,"تمت  إضافته إلى المفضلة"  ,Toast.LENGTH_SHORT).show();


    }


    private void displaySpecificItemInfo() {

        itemRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot)
            {
                if(snapshot.exists())
                {
                    String itemTitle = snapshot.child("title").getValue().toString();
                    String itemDetails = snapshot.child("details").getValue().toString();
                    String itemNotes = snapshot.child("notes").getValue().toString();
                    String itemImage = snapshot.child("image").getValue().toString();
                    String favorite = snapshot.child("favorite").getValue().toString();

                    if (favorite.equals("yes")){
                        fill_favorite.setVisibility(View.VISIBLE);
                        empty_favorite.setVisibility(View.GONE);
                    }
                    else {
                        fill_favorite.setVisibility(View.GONE);
                        empty_favorite.setVisibility(View.VISIBLE);
                    }

                    title.setText(itemTitle);
                    details.setText(itemDetails);
                    notes.setText(itemNotes);

                    Picasso.get().load(itemImage).into(imageView);
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}