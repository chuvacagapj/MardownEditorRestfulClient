package com.example.jesus.markdowneditor;

import android.content.Intent;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.jesus.markdowneditor.Model.MarkdownModel;
import com.example.jesus.markdowneditor.ServiceActions.ServiceAction;

public class EditActivity extends AppCompatActivity {

    MarkdownModel model;
    EditText title;
    EditText body;
    Button guardar;
    Button cancelar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);
        startService(new Intent(getBaseContext(), MyIntentService.class));
        this.title = (EditText)findViewById(R.id.editText);
        this.body = (EditText)findViewById(R.id.editText2);
        this.guardar = (Button)findViewById(R.id.button);
        this.cancelar = (Button)findViewById(R.id.button2);

        Intent intent = getIntent();

        if (intent.getExtras().getBoolean("new")){
            this.model = new MarkdownModel();
        }else {
            this.model = MarkdownModel.getbyid(intent.getIntExtra("id", 0));
            this.title.setText(this.model.getTitle());
            this.body.setText(this.model.getBody());
        }

    }

    public void onClick(View v){
        if (v.equals(this.guardar)){
            this.model.setTitle(this.title.getText().toString());
            this.model.setBody(this.body.getText().toString());
            MarkdownModel.upsert(this.model);
            sendMessege();
            MyIntentService.startAction(this, ServiceAction.UPSERT);
            MainActivity.adapter.notifyDataSetChanged();
        }

        finish();
    }

    public void sendMessege(){
        Intent intent = new Intent("Dataset-changed");
        LocalBroadcastManager.getInstance(this).sendBroadcast(intent);
    }
}
