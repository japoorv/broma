package com.example.messagespam;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private Button goButton;
    int index;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        goButton = (Button) findViewById(R.id.button);
        goButton.setOnClickListener(MainActivity.this);

    }
    @Override
    public void onClick(View v) {
        EditText editText;
        TextView phN;
        editText = (EditText) findViewById(R.id.editText);
        phN = (TextView) findViewById(R.id.phN);
        String phoneNumber = editText.getText().toString();
        if (!isValidPhoneNumber(phoneNumber))
            phN.setText("Invalid Phone Number");
        else {
            phN.setText("Ready !");
            ((TextView)findViewById((R.id.textView4))).setText("0");
            String numMssg = ((EditText) findViewById((R.id.editText2))).getText().toString();
            int numMsg = Integer.parseInt(numMssg);
            RequestQueue requestQueue = Volley.newRequestQueue(this);
            String URL = "https://message-spam.herokuapp.com/?n="+phoneNumber;
            goButton.setEnabled(false);
            index=0;
            for (index=0;index<numMsg;index++) {
                JsonObjectRequest objectRequest = new JsonObjectRequest(
                        Request.Method.GET,
                        URL,
                        null,
                        new Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(JSONObject response) {
                                try {
                                    Log.d("status",response.getString("status"));
                                    if (response.getInt("status")!=-1)
                                        ((TextView)findViewById((R.id.textView4))).setText(""+(index+1));
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {

                            }
                        }
                    );
                requestQueue.add(objectRequest);
            }
            goButton.setEnabled(true);
        }
    }

    Boolean isValidPhoneNumber(String a)
    {
        for (int c=0;c<a.length();c++)
        {
            if (a.charAt(c)-'0'<0||a.charAt(c)-'0'>9)
                    return false;

        }
        return a.length()==10;
    }


}
