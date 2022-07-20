package com.nour.beautyapp.ui.categories;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.nour.beautyapp.R;
import com.nour.beautyapp.ShowItemsActivity;

public class CategoriesFragment extends Fragment {

    private CategoriesViewModel dashboardViewModel;

    private LinearLayout skin, hair, lips, body, hand, makeup;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        dashboardViewModel =
                ViewModelProviders.of(this).get(CategoriesViewModel.class);
        View root = inflater.inflate(R.layout.fragment_categories, container, false);
         dashboardViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
             }
        });


        skin = (LinearLayout) root.findViewById(R.id.skin_category);
        hair = (LinearLayout) root.findViewById(R.id.hair_category);
        lips = (LinearLayout) root.findViewById(R.id.lips_category);
        hand = (LinearLayout) root.findViewById(R.id.hand_category);
        body = (LinearLayout) root.findViewById(R.id.body_category);
        makeup = (LinearLayout) root.findViewById(R.id.makeup_category);


        skin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                Intent intent = new Intent(getActivity(),  ShowItemsActivity.class);
                intent.putExtra("category", "العناية بالوجه");
                startActivity(intent);
            }
        });

        hair.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                Intent intent = new Intent(getActivity(),  ShowItemsActivity.class);
                intent.putExtra("category", "العناية بالشعر");
                startActivity(intent);
            }
        });

        lips.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                Intent intent = new Intent(getActivity(),  ShowItemsActivity.class);
                intent.putExtra("category", "العناية بالشفاه");
                startActivity(intent);
            }
        });

        hand.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                Intent intent = new Intent(getActivity(),  ShowItemsActivity.class);
                intent.putExtra("category", "العناية باليدين والقدمين");
                startActivity(intent);
            }
        });

        body.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                Intent intent = new Intent(getActivity(), ShowItemsActivity.class);
                intent.putExtra("category", "العناية بالجسم");
                startActivity(intent);
            }
        });

        makeup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                Intent intent = new Intent(getActivity(),  ShowItemsActivity.class);
                intent.putExtra("category", "المكياج والموضة");
                startActivity(intent);
            }
        });

        return root;
    }
}