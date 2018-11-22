package com.pmdm.t1v1;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import java.util.Calendar;
import java.util.GregorianCalendar;

public class MainActivity extends AppCompatActivity implements ListView.OnItemClickListener,
        Spinner.OnItemSelectedListener, DialogoFecha.OnFechaSeleccionada,
        DialogoFragment.RespuestaDialogoFragment {
    String[] ciudades;
    String[] descripciones;

    int imagenes[] = { R.mipmap.asound, R.mipmap.bbf, R.mipmap.vrock};



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        //este metodo para que no gire la app
        setContentView(R.layout.activity_main);
        //region listView
        String [] elementos={getString(R.string.c1), getString(R.string.c2), getString(R.string.c3)};

        ArrayAdapter<String> adaptador;
        ListView l=(ListView)findViewById(R.id.listView);
        l.setChoiceMode(AbsListView.CHOICE_MODE_MULTIPLE);
        adaptador=new ArrayAdapter<String>(this,android.R.layout.simple_list_item_multiple_choice,elementos);
        l.setAdapter(adaptador);
        l.setOnItemClickListener(this);
        //endregion
        //region spinner

        descripciones = new String[]{getString(R.string.locf3),getString(R.string.locf2),getString(R.string.locf1)};
        ciudades = new String[]{ getString(R.string.f1), getString(R.string.f2),getString(R.string.f3) };
        Spinner selectorCiudades = (Spinner) findViewById(R.id.spinner);
        AdaptadorPersonalizado a=new AdaptadorPersonalizado(this, R.layout.lineaspiner, ciudades);
        selectorCiudades.setAdapter(a);
        selectorCiudades.setOnItemSelectedListener(this);
        //endregion
        contador = 0;
    }

    //region codigo ya hecho
    //region listView
    public void onItemClick(AdapterView<?> a, View view, int position, long id){
        //TextView t=(TextView)findViewById(R.id.textView3);
        ListView l=(ListView)findViewById(R.id.listView);
        String seleccionado=new String();
        SparseBooleanArray checked = l.getCheckedItemPositions();

        for(int i=0;i<checked.size();i++)
            if(checked.valueAt(i)){
                seleccionado=seleccionado+
                        a.getItemAtPosition(checked.keyAt(i)).toString()
                        +";";
            }
        //t.setText(seleccionado);
    }
    //endregion

    //region spinner
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id){
        //TextView c=(TextView)view.findViewById(R.id.nombre);
        TextView seleccion=(TextView)findViewById(R.id.ciudadSeleccionada);

        //seleccion.setText(c.getText());
    }

    public void onNothingSelected(AdapterView<?> parent){
        TextView seleccion=(TextView)findViewById(R.id.ciudadSeleccionada);
        seleccion.setText("nada seleccionado!");
    }

    public class AdaptadorPersonalizado extends ArrayAdapter<String> {
        public AdaptadorPersonalizado(Context ctx, int txtViewResourceId, String[] objects){
            super(ctx, txtViewResourceId, objects);
        }

        @Override
        public View getDropDownView(int position, View cnvtView, ViewGroup prnt){
            return crearFilaPersonalizada(position, cnvtView, prnt);
        }

        @Override
        public View getView(int pos, View cnvtView, ViewGroup prnt){
            return crearFilaPersonalizada(pos, cnvtView, prnt);
        }

        public View crearFilaPersonalizada(int position, View convertView, ViewGroup parent){

            LayoutInflater inflater = getLayoutInflater();
            View miFila = inflater.inflate(R.layout.lineaspiner, parent, false);

            TextView nombre = (TextView) miFila.findViewById(R.id.nombre);
            nombre.setText(ciudades[position]);

            TextView descripcion = (TextView) miFila.findViewById(R.id.descripcion);
            descripcion.setText(descripciones[position]);

            ImageView imagen = (ImageView) miFila.findViewById(R.id.imagenCiudad);
            imagen.setImageResource(imagenes[position]);
            return miFila;

        }
    }
    //endregion

    public void onClickFecha(View view) {
        DialogoFecha d=new DialogoFecha();
        d.show(getFragmentManager(),"Mi diálogo Fecha");
    }

    @Override
    public void onResultadoFecha(GregorianCalendar fecha) {
        EditText et=(EditText)findViewById(R.id.etFechaNacimiento);
        et.setText(fecha.get(Calendar.DAY_OF_MONTH)+"/"+(fecha.get(Calendar.MONTH)+1)+"/"+fecha.get(Calendar.YEAR));
    }


    //endregion

    //region dialogoFragmet
    public void click(View v){
        DialogoFragment ds = new DialogoFragment();
        ds.show(getFragmentManager(),"Mi diálogo Fecha");
    }


    @Override
    public void onRespuesta(String s) {
        Toast.makeText(getApplicationContext(),s,Toast.LENGTH_LONG ).show();
    }
    //endregion


    //region easterEgg
    private int contador;
    public void easterEgg(View v){
        contador++;
        if(contador > 5){
            contador = 0;
            Intent intent = new Intent(this, MainActivityVideo.class);
            startActivity(intent);
        }
    }
    //endregion
}
