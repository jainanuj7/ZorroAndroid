package com.wallet.zorro;


import android.os.Bundle;
import android.support.v4.app.Fragment;;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.URISyntaxException;

import static android.R.attr.fragment;


/**
 * A simple {@link Fragment} subclass.
 */
public class WelcomeActivity extends Fragment  {
Long mob;
    EditText etName,etBal;

    public WelcomeActivity() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment


        return inflater.inflate(R.layout.fragment_welcome, container, false);


    }
    public void onViewCreated(View v,Bundle savedInstanceState)
    {
        etName=(EditText)v.findViewById(R.id.etName);



        Intent wIntent=getActivity().getIntent();
        mob=wIntent.getLongExtra("mob",-1);
        etBal=(EditText)v.findViewById(R.id.etBal);
        etName=(EditText)v.findViewById(R.id.etName);
        Response.Listener<String> responseListener = new Response.Listener<String>(){

            @Override
            public void onResponse(String response) {
                try{
                    JSONObject  jsonResponse =new JSONObject(response);
                    String bal = jsonResponse.getString("bal");
                    Float fbal=Float.parseFloat(bal.toString());
                    String name = jsonResponse.getString("name");
                    etBal.setText(bal);
                    etName.setText(name);




                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        };
        WelcomeRequest welcomeRequest=new WelcomeRequest(mob,responseListener);
        RequestQueue queue = Volley.newRequestQueue(getContext());
        queue.add(welcomeRequest);


        final Button bRefresh=(Button)v.findViewById(R.id.bRefresh);
      bRefresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent wIntent=new Intent(getContext(),NavActivity.class);
                wIntent.putExtra("mob",mob);
                WelcomeActivity.this.startActivity(wIntent);
                getActivity().finish();


            }


        });
        final Button bLogout =(Button)v.findViewById(R.id.bLogout);
        bLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent wIntent=new Intent(getContext(),LoginActivity.class);
                Toast.makeText(getContext(), "Successfully logged out!", Toast.LENGTH_SHORT).show();

                WelcomeActivity.this.startActivity(wIntent);
                getActivity().finish();
            }
        });



        WelcomeActivity.this.getView().setFocusableInTouchMode(true);
        WelcomeActivity.this.getView().requestFocus();
        WelcomeActivity.this.getView().setOnKeyListener( new View.OnKeyListener()
        {
            @Override
            public boolean onKey( View v, int keyCode, KeyEvent event )
            {
                if( keyCode == KeyEvent.KEYCODE_BACK )
                {
                    return true;

                }
                return false;
            }
        } );

    }





}
