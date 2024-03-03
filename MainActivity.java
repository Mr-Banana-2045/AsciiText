package com.mycompany.ascii;

import android.app.*;
import android.os.*;
import android.view.*;
import android.view.View.*;
import android.widget.*;
import java.io.*;
import java.net.*;

public class MainActivity extends Activity 
{
	private EditText input;
	private TextView text;
	private Button show;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
		text = findViewById(R.id.mainTextView1);
		show = findViewById(R.id.mainButton1);
		show.setOnClickListener(new OnClickListener(){
			public void onClick(View view){
				input = findViewById(R.id.mainEditText1); 
				String edit = input.getText().toString();
                new DownloadWebsiteSource().execute(edit);
				
			}
		});
}
		private class DownloadWebsiteSource extends AsyncTask<String, Void, String> {

			@Override
			protected String doInBackground(String... urls) {
				try {
					URL url = new URL(urls[0]);
					HttpURLConnection connection = (HttpURLConnection) url.openConnection();
					connection.setRequestMethod("GET");
					connection.connect();

					InputStream inputStream = connection.getInputStream();
					BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
					StringBuilder stringBuilder = new StringBuilder();
					String line;

					while ((line = bufferedReader.readLine()) != null) {
						stringBuilder.append(line).append("\n");
					}

					bufferedReader.close();
					inputStream.close();
					connection.disconnect();

					return stringBuilder.toString();
				} catch (IOException e) {
					e.printStackTrace();
					return null;
				}
			}

			@Override
			protected void onPostExecute(String result) {
				if (result != null) {
					text.setText(result);
				} else {
					text.setText("Error :(");
				}
			}
    }
}
