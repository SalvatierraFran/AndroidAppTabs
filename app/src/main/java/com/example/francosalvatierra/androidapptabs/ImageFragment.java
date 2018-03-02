package com.example.francosalvatierra.androidapptabs;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import static android.app.Activity.RESULT_OK;

public class ImageFragment extends Fragment {

    ImageView imagen;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_image, container, false);

        //this.getActivity().setContentView(R.layout.fragment_image);

        imagen = (ImageView)v.findViewById(R.id.image_iv);

        Button miBtn = (Button)v.findViewById(R.id.image_btn);

        miBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                buttonOnAction();
            }
        });

        // Inflate the layout for this fragment

        return v;
        //return inflater.inflate(R.layout.fragment_image, container, false);
    }

    public void buttonOnAction()
    {
        cargarImagen();
    }

    private void cargarImagen()
    {
        Intent miIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

        miIntent.setType("image/");
        startActivityForResult(miIntent.createChooser(miIntent, "Seleccione la Aplicacion"), 10);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode==RESULT_OK)
        {
            Uri path = data.getData();
            imagen.setImageURI(path);
        }
    }
}
