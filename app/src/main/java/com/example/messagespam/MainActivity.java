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
     TextView phN ;
     TextView stat;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        goButton = findViewById(R.id.button);
        goButton.setOnClickListener(MainActivity.this);

    }
    @Override
    public void onClick(View v) {
        EditText editText;
        editText = findViewById(R.id.editText);
        phN = findViewById(R.id.phN);
        stat = findViewById(R.id.textView4);
        String phoneNumber = editText.getText().toString();
        if (!isValidPhoneNumber(phoneNumber))
            phN.setText("Invalid Phone Number");
        else {

            phN.setText("Ready !");
            stat.setText("0");
            String numMssg = ((EditText) findViewById((R.id.editText2))).getText().toString();
            final int numMsg = Integer.parseInt(numMssg);
            String URL = "https://message-spam.herokuapp.com/?n="+phoneNumber;
            RequestQueue RQ = Volley.newRequestQueue(this);

            index=0;
            if (numMsg>0)
                goButton.setEnabled(false);
            final JsonObjectRequest objectRequest = new JsonObjectRequest(
                    Request.Method.GET,
                    URL,
                    null,
                    new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {

                            index++;
                            Log.d("respose",""+response);
                            stat.setText(""+index);
                            if (index==numMsg)
                                goButton.setEnabled(true);

                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {

                        }
                    }
                );
               for (int c=0;c<numMsg;c++)
                   RQ.add(objectRequest);




           //goButton.setEnabled(true);
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
