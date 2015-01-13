package com.example.bulksms;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends Activity {

	final int PICK_COLUMN_REQUEST_CODE = 1;
	private EditText input_msg;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		input_msg = (EditText)findViewById(R.id.et_input_msg);

		Button bn_view_tables = (Button)findViewById(R.id.bn_add_var);
		
		bn_view_tables.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent tableListIntent = new Intent("android.intent.action.TABLE_SELECTOR2");
				startActivityForResult(tableListIntent, PICK_COLUMN_REQUEST_CODE);
			}
		});
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);
		
		if(requestCode == PICK_COLUMN_REQUEST_CODE){
			Toast.makeText(this, resultCode+"", Toast.LENGTH_SHORT).show();
			if(resultCode == RESULT_OK){
				
				String varName = data.getStringExtra("fullName");
				Log.d("sql", "activity returned");
				String msg = input_msg.getText().toString()+"$$"+varName+"$$";
				input_msg.setText(msg);
				input_msg.setSelection(msg.length());
			}
		}
	}
}
