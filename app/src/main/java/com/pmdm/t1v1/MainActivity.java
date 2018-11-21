package com.pmdm.t1v1;

import android.content.Context;
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

    String[] ciudades = { "Toledo", "Ciudad Real", "Albacete","Cuenca", "Guadalajara" };

    String[] descripciones = { "La ciudad Imperial", "Qué gran ciudad",
            "Ciudad gastronómica", "Ciudad encantada", "Ciudad colgante" };

    int imagenes[] = { R.drawable.toledo, R.drawable.ciudadreal, R.drawable.albacete,
            R.drawable.cuenca, R.drawable.guadalajara};



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        String [] elementos={"Toledo","Ciudad Real","Cuenca","Guadalajara","Albacete"};
        ArrayAdapter<String> adaptador;
        ListView l=(ListView)findViewById(R.id.listView);
        l.setChoiceMode(AbsListView.CHOICE_MODE_MULTIPLE);
        adaptador=new ArrayAdapter<String>(this,android.R.layout.simple_list_item_multiple_choice,elementos);
        l.setAdapter(adaptador);
        l.setOnItemClickListener(this);
        Spinner selectorCiudades = (Spinner) findViewById(R.id.spinner);
        AdaptadorPersonalizado a=new AdaptadorPersonalizado(this, R.layout.lineaspiner, ciudades);
        selectorCiudades.setAdapter(a);
        selectorCiudades.setOnItemSelectedListener(this);
    }


    //region codigo ya hecho
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

    public void onItemSelected(AdapterView<?> parent, View view, int position, long id){
        TextView c=(TextView)view.findViewById(R.id.nombre);
        TextView seleccion=(TextView)findViewById(R.id.ciudadSeleccionada);

        seleccion.setText(c.getText());
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
}
