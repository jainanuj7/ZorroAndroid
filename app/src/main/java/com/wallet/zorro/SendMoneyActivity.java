package com.wallet.zorro;


import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.provider.ContactsContract.CommonDataKinds.Phone;






import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import static android.app.Activity.RESULT_OK;


/**
 * A simple {@link Fragment} subclass.
 */
public class SendMoneyActivity extends Fragment {

    Long mob;
    EditText etMob;
    public static final int RESULT_PICK_CONTACT = 85500;
    public SendMoneyActivity() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_send_money, container, false);
    }


    public void onViewCreated(View v,Bundle savedInstanceState)
    {
        etMob = (EditText) v.findViewById(R.id.etMob);
        final EditText etAmount = (EditText) v.findViewById(R.id.etAmount);
        final Button bSend = (Button) v.findViewById(R.id.bSend);

        Intent aIntent=getActivity().getIntent();
        mob=aIntent.getLongExtra("mob",-1);

        bSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(etAmount.getText().toString().isEmpty() || etMob.getText().toString().isEmpty())
                {
                    if(!(etMob.getText().toString().isEmpty()))
                        Toast.makeText(getActivity(),"Please Enter Amount",Toast.LENGTH_SHORT).show();

                    else
                    {
                        Toast.makeText(getActivity(),"Please Enter Mobile Number",Toast.LENGTH_SHORT).show();
                    }
                }
                else
                {
                    float amount=Float.parseFloat(etAmount.getText().toString());
                    long senderMob = Long.parseLong(etMob.getText().toString());

                    Response.Listener<String> responseListener=new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            try {
                                JSONObject jsonObject=new JSONObject(response);
                                boolean success=jsonObject.getBoolean("success");
                                boolean zero_bal=jsonObject.getBoolean("zero_bal");
                                if(success)
                                {
                                    if(!(zero_bal)) {
                                        Toast.makeText(getActivity(), "Amount Sent Successfully", Toast.LENGTH_SHORT).show();
                                    }
                                    else
                                    {
                                        Toast.makeText(getActivity(),"Insufficient Balance",Toast.LENGTH_SHORT).show();
                                    }
                                }
                                else
                                {
                                    Toast.makeText(getActivity(),"Transaction Failed",Toast.LENGTH_SHORT).show();
                                }
                                Intent wIntent=new Intent(getActivity(),NavActivity.class);
                                wIntent.putExtra("mob",mob);
                                SendMoneyActivity.this.startActivity(wIntent);

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                        }
                    };



                    SendMoneyRequest sendMoneyRequest=new SendMoneyRequest(mob,senderMob,amount,responseListener);
                    RequestQueue queue = Volley.newRequestQueue(getActivity());
                    queue.add(sendMoneyRequest);

                }
            }
        });
        TextView tvContact =(TextView)v.findViewById(R.id.tvContact);
        tvContact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent contactPickerIntent = new Intent(Intent.ACTION_PICK, Phone.CONTENT_URI);
                 startActivityForResult(contactPickerIntent, 1001);
            }
        });



        SendMoneyActivity.this.getView().setFocusableInTouchMode(true);
        SendMoneyActivity.this.getView().requestFocus();
        SendMoneyActivity.this.getView().setOnKeyListener( new View.OnKeyListener()
        {
            @Override
            public boolean onKey( View v, int keyCode, KeyEvent event )
            {
                if( keyCode == KeyEvent.KEYCODE_BACK )
                {


                    Intent setIntent=new Intent(getActivity(),NavActivity.class);
                    setIntent.putExtra("mob",mob);
                    setIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    SendMoneyActivity.this.startActivity(setIntent);
                    getActivity().finish();

                }
                return false;
            }
        } );

    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        // check whether the result is ok


        if (resultCode == RESULT_OK) {

            // Check for the request code, we might be usign multiple startActivityForReslut

            contactPicked(data);

        }
        else {
            Log.e("MainActivity", "Failed to pick contact");
        }
    }
    void contactPicked(Intent data) {

        Cursor cursor = null;
        try {
            String phoneNo = null ;

            // getData() method will have the Content Uri of the selected contact
            Uri uri = data.getData();
            //Query the content uri

          cursor =getActivity().getContentResolver().query(uri, null, null, null, null);
            cursor.moveToFirst();
            // column index of the phone number
            int  phoneIndex =cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER);
            // column index of the contact name

            phoneNo = cursor.getString(phoneIndex);

            // Set the value to the textviews
            phoneNo=phoneNo.replace(" ","");
            phoneNo= phoneNo.replace("+91", "");
            phoneNo=phoneNo.replace("(","");
            phoneNo=phoneNo.replace(")","");
            phoneNo=phoneNo.replace("-","");
            etMob.setText(phoneNo);


        } catch (Exception e) {
            e.printStackTrace();
        }





    }

}
