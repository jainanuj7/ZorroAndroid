package com.wallet.zorro;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by lenovo on 20-Sep-17.
 */

public class PassBookRequest extends StringRequest {
    private static final String REQUEST_URL = "http://192.168.43.111:6969/zorro/passbook.php";
    private Map<String, String> params;
    public PassBookRequest(long mob, Response.Listener<String> listener)
    {
        super(Method.POST,REQUEST_URL,listener,null);
        params = new HashMap<>();
        params.put("mob",mob+"");

    }

    @Override
    public Map<String, String> getParams() {
        return params;
    }

}
