package com.wallet.zorro;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;


/**
 * A simple {@link Fragment} subclass.
 */
public class AddMoneyActivity extends Fragment {
Long mob;
    EditText etAmount;
    public AddMoneyActivity() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_add_money, container, false);
    }
public void onViewCreated(View v,Bundle savedInstanceState)
    {
        Intent aIntent=getActivity().getIntent();
        mob=aIntent.getLongExtra("mob",-1);
        Button bAdd=(Button)v.findViewById(R.id.bAdd);
      etAmount =(EditText)v.findViewById(R.id.etAmount);
        bAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                if(etAmount.getText().toString().isEmpty())
                {
                    Toast.makeText(getContext(),"Please Enter Amount",Toast.LENGTH_SHORT).show();
                }
                else
                {
                    float amount=Float.parseFloat(etAmount.getText().toString());


                    Response.Listener<String> responseListener=new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            try {
                                JSONObject jsonObject=new JSONObject(response);
                                boolean success=jsonObject.getBoolean("success");
                                if(success)
                                {
                                    Toast.makeText(getActivity(),"Amount Added Successfully",Toast.LENGTH_SHORT).show();
                                }
                                else
                                {
                                    Toast.makeText(getActivity(),"Transaction Failed",Toast.LENGTH_SHORT).show();
                                }
                                Intent wIntent=new Intent(getActivity(),NavActivity.class);
                                wIntent.putExtra("mob",mob);
                                AddMoneyActivity.this.startActivity(wIntent);
                                getActivity().finish();

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                        }
                    };



                    AddMoneyRequest addMoneyRequest=new AddMoneyRequest(mob,amount,responseListener);
                    RequestQueue queue = Volley.newRequestQueue(getActivity());
                    queue.add(addMoneyRequest);

                }
            }
        });


        AddMoneyActivity.this.getView().setFocusableInTouchMode(true);
        AddMoneyActivity.this.getView().requestFocus();
       AddMoneyActivity.this.getView().setOnKeyListener( new View.OnKeyListener()
        {
            @Override
            public boolean onKey( View v, int keyCode, KeyEvent event )
            {
                if( keyCode == KeyEvent.KEYCODE_BACK )
                {
                    Intent setIntent=new Intent(getActivity(),NavActivity.class);
                    setIntent.putExtra("mob",mob);
                    setIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    AddMoneyActivity.this.startActivity(setIntent);
                    getActivity().finish();

                }
                return false;
            }
        } );
    }

}
