package com.example.jesus.markdowneditor;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.example.jesus.markdowneditor.Model.MarkdownModel;
import com.example.jesus.markdowneditor.ServiceActions.ServiceAction;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

public class MainActivity extends AppCompatActivity {

    private ListView lv;
    public static MyAdapter adapter;
    private Button boton;
    public boolean seleccion = false;
    public Set<Integer> selected = new HashSet();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        adapter = new MyAdapter();
        this.boton = (Button)findViewById(R.id.button3);
        this.lv = (ListView) findViewById(R.id.listview);
        this.lv.setAdapter(adapter);
        this.lv.setOnItemClickListener(new MyOnClickListener());
        this.lv.setOnItemLongClickListener(new MyOnLongClickListener());
        startService(new Intent(getBaseContext(), MyIntentService.class));
        MyIntentService.startAction(getBaseContext(), ServiceAction.READ);
    }

    public void onclick(View v){
        if (seleccion){
            List<MarkdownModel> lista = new LinkedList<>();
            for (Integer id: selected) {
                lista.add(MarkdownModel.getbyid(id));
            }
            seleccion = false;
            selected = new HashSet();
            boton.setText("Nuevo");
            boton.setBackgroundColor(Color.GREEN);
            MarkdownModel.delete(lista);
            MyIntentService.startAction(this, ServiceAction.DELETE);
            update();
        }else {
            Intent intent = new Intent(this, EditActivity.class);
            intent.putExtra("new", true);
            startActivity(intent);
        }
    }

    @Override
    protected void onPause() {
        // Unregister since the activity is paused.
        LocalBroadcastManager.getInstance(this).unregisterReceiver(
                mMessageReceiver);
        super.onPause();
    }

    @Override
    protected void onResume() {
        // Register to receive messages.
        // We are registering an observer (mMessageReceiver) to receive Intents
        // with actions named "custom-event-name".
        LocalBroadcastManager.getInstance(this).registerReceiver(
                mMessageReceiver, new IntentFilter("Dataset-changed"));
        super.onResume();
    }

    private BroadcastReceiver mMessageReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            adapter.notifyDataSetChanged();
        }
    };

    public class MyAdapter extends BaseAdapter {
        @Override
        public int getCount() {
            return MarkdownModel.getSize();
        }

        @Override
        public MarkdownModel getItem(int i) {
            return MarkdownModel.getbyid(i);
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            view = getLayoutInflater().inflate(R.layout.item_layout, null);
            TextView titulo = (TextView) view.findViewById(R.id.titleText);
            MarkdownModel m = MarkdownModel.getbyid(i);
            titulo.setText(m.getTitle());

            if (seleccion && selected.contains(i)){
                view.setBackgroundColor(Color.GREEN);
                titulo.setTextColor(Color.WHITE);
            }

            return view;
        }

    }

    public class MyOnClickListener implements AdapterView.OnItemClickListener{
        @Override
        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
            if(seleccion){
                setAdd(i);
                update();
            }else{
                detalle(i);
            }
        }
    }

    public class MyOnLongClickListener implements AdapterView.OnItemLongClickListener{
        @Override
        public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
            if(seleccion){
                setAdd(i);
                update();
            }else{
                seleccion = true;
                setAdd(i);
                update();
            }
            return true;
        }
    }

    public void detalle (int i){
        Intent in = new Intent(this, ShowActivity.class);
        in.putExtra("id", i);
        startActivity(in);
    }

    public void setAdd(int i){
        if (selected.contains(i)){

            selected.remove(i);
            this.seleccion = !selected.isEmpty();

            if(selected.isEmpty()){
                boton.setText("Nuevo");
                boton.setBackgroundColor(Color.GREEN);
            }

        }else{
            selected.add(i);
            boton.setText("Borrar");
            boton.setBackgroundColor(Color.RED);
        }

        lv.deferNotifyDataSetChanged();
    }

    public void update(){
        adapter.notifyDataSetChanged();
    }
}
