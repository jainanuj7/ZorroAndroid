package com.wallet.zorro;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class PassBookActivity extends Fragment {
    Long mob;
    ArrayList<String> transArray=new ArrayList<String>();
    ListView lvTransaction;
    ArrayAdapter<String> adapter;

    public PassBookActivity() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_pass_book, container, false);
    }
    public void onViewCreated(View v,Bundle savedInstanceState)
    {
        lvTransaction=(ListView)v.findViewById(R.id.lvTransaction);


        Intent passbookIntent=getActivity().getIntent();
        mob=passbookIntent.getLongExtra("mob",-1);

        Response.Listener<String> responseListener=new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject=new JSONObject(response);
                    boolean success=jsonObject.getBoolean("success");
                    if(success)
                    {

                        int i=1;
                        Integer iter=jsonObject.getInt("iter");
                        while(i<iter) {
                            String temp=jsonObject.getString("str"+i+"");

                            String temp2=jsonObject.getString("other_str"+i+"");
                            String temp3=jsonObject.getString("another_str"+i+"");
                            transArray.add(temp+"\n\n"+temp3+"\n\n"+temp2);

                            i++;
                        }


                    }
                    else
                    {
                        Toast.makeText(getActivity(),"Error",Toast.LENGTH_SHORT).show();
                    }


                } catch (JSONException e) {
                    e.printStackTrace();
                }
                adapter=new ArrayAdapter<String>(getActivity(),android.R.layout.simple_list_item_1,transArray);
                lvTransaction.setAdapter(adapter);

            }
        };
        PassBookRequest passBookRequest=new PassBookRequest(mob,responseListener);
        RequestQueue queue = Volley.newRequestQueue(getActivity());
        queue.add(passBookRequest);




        PassBookActivity.this.getView().setFocusableInTouchMode(true);
       PassBookActivity.this.getView().requestFocus();
        PassBookActivity.this.getView().setOnKeyListener( new View.OnKeyListener()
        {
            @Override
            public boolean onKey( View v, int keyCode, KeyEvent event )
            {
                if( keyCode == KeyEvent.KEYCODE_BACK )
                {
                    Intent setIntent=new Intent(getActivity(),NavActivity.class);
                    setIntent.putExtra("mob",mob);
                    setIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    PassBookActivity.this.startActivity(setIntent);
                    getActivity().finish();

                }
                return false;
            }
        } );
    }


}
