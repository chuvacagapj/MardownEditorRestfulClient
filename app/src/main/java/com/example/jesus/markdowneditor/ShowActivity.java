package com.example.jesus.markdowneditor;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.jesus.markdowneditor.Model.MarkdownModel;
import com.mukesh.MarkdownView;

public class ShowActivity extends AppCompatActivity {

    private int id;
    private Button boton;
    private MarkdownModel model;
    private MarkdownView markdownView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show);
        Intent intent = getIntent();
        this.id = intent.getIntExtra("id", 0);
        this.model = MarkdownModel.getbyid(this.id);
        this.markdownView = (MarkdownView)findViewById(R.id.markdown_view);

        this.boton = (Button)findViewById(R.id.button4);
        this.boton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editar();
            }
        });

    }

    public void editar(){
        Intent intent = new Intent(this, EditActivity.class);
        intent.putExtra("new", false);
        intent.putExtra("id", this.id);
        startActivity(intent);
    }

    @Override
    protected void onStart() {
        super.onStart();
        this.markdownView.setMarkDownText(model.getBody());
    }
}
