package app.com.bisnode.requests;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.toolbox.JsonArrayRequest;

import org.json.JSONArray;

import java.util.HashMap;
import java.util.Map;

public class CustomJsonArrayRequest extends JsonArrayRequest
{
    public CustomJsonArrayRequest(String url, Response.Listener<JSONArray> listener, Response.ErrorListener errorListener)
    {
        super(url, listener, errorListener);
    }

    @Override
    public Map<String, String> getHeaders() throws AuthFailureError {
        Map<String, String> headers = new HashMap<>();
        headers.put("sid", "gnosus/56url2test28134");
        return headers;
    }

}
