package com.pmdm.t1v1;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.media.AsyncPlayer;
import android.media.AudioAttributes;
import android.net.Uri;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class MainActivity extends AppCompatActivity implements ListView.OnItemClickListener,
        Spinner.OnItemSelectedListener, DialogoFecha.OnFechaSeleccionada,
        DialogoFragment.RespuestaDialogoFragment {

    //region vectoresDDatos
    String[] ciudades;
    String[] descripciones;
    String [] elementos;
    int imagenes[] = { R.mipmap.asound, R.mipmap.bbf, R.mipmap.vrock};
    ActionBar actionBar;
    //endregion

    //region elementos
    Switch SwitchPregRock;
    EditText numDiscos;
    RadioGroup btnGrupo;
    ArrayList<String> canciones;
    String festival;
    GregorianCalendar fecha;
    Spinner selectorCiudades;
    AdaptadorPersonalizado a;
    EditText etFecha;
    //endregion

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        //este metodo para que no gire la app
        setContentView(R.layout.activity_main);
        //region listViev
        creaListView();
        //endregion

        //region spinner
        descripciones = new String[]{getString(R.string.locf3),getString(R.string.locf2),getString(R.string.locf1)};
        ciudades = new String[]{ getString(R.string.f1), getString(R.string.f2),getString(R.string.f3) };
        selectorCiudades = (Spinner) findViewById(R.id.spinner);
        a=new AdaptadorPersonalizado(this, R.layout.lineaspiner, ciudades);
        selectorCiudades.setAdapter(a);
        selectorCiudades.setOnItemSelectedListener(this);
        //endregion

        //region inicializarVariables
        contador = 0;
        canciones = new ArrayList<String>();
        festival = "";
        fecha = new GregorianCalendar();
        //endregion

        //region elementos
        SwitchPregRock = findViewById(R.id.pregRock);
        numDiscos = findViewById(R.id.numDiscos);
        btnGrupo = findViewById(R.id.grupoDRadios);
        //endregion

        //region actionBar
        actionBar = getSupportActionBar();
        actionBar.setIcon(R.mipmap.portada);
        actionBar.setDisplayShowHomeEnabled(true);
        //endregion
    }

    //region menu
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.my, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Toast.makeText(getApplicationContext(),"Has seleccionado: "+item.getTitle(),Toast.LENGTH_LONG).show();
        if(item.getTitle().equals(getString(R.string.limpiar)))
            limpiarValores();
        return super.onOptionsItemSelected(item);
    }
    //endregion

    //region listView
    public void creaListView(){
        elementos= new String[]{getString(R.string.c1), getString(R.string.c2), getString(R.string.c3)};
        ArrayAdapter<String> adaptador;
        ListView l=(ListView)findViewById(R.id.listaCanciones);
        l.setChoiceMode(AbsListView.CHOICE_MODE_MULTIPLE);
        adaptador=new ArrayAdapter<String>(this,android.R.layout.simple_list_item_multiple_choice,elementos);
        l.setAdapter(adaptador);
        l.setOnItemClickListener(this);
    }


    public void onItemClick(AdapterView<?> a, View view, int position, long id){
        //TextView t=(TextView)findViewById(R.id.textView3);
        ListView l=(ListView)findViewById(R.id.listaCanciones);

        String seleccionado=new String();
        SparseBooleanArray checked = l.getCheckedItemPositions();

        for(int i=0;i<checked.size();i++)
            if(checked.valueAt(i)){
                seleccionado=seleccionado+
                        a.getItemAtPosition(checked.keyAt(i)).toString()
                        +";";
                if(!canciones.contains(elementos[i]))
                    canciones.add(elementos[i]);
            }
        //t.setText(seleccionado);
    }
    //endregion

    //region spinner
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id){
        TextView c=(TextView)view.findViewById(R.id.nombre);
        TextView seleccion=(TextView)findViewById(R.id.ciudadSeleccionada);

        festival = (c.getText().toString()); //TODO a lo mejor falla aqui el codigo
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

    //region campoFecha
    public void onClickFecha(View view) {
        DialogoFecha d=new DialogoFecha();
        d.show(getFragmentManager(),"Mi diálogo Fecha");
    }

    @Override
    public void onResultadoFecha(GregorianCalendar fecha) {
        etFecha=(EditText)findViewById(R.id.txtFechaNacimiento);
        etFecha.setText(fecha.get(Calendar.DAY_OF_MONTH)+"/"+(fecha.get(Calendar.MONTH)+1)+"/"+fecha.get(Calendar.YEAR));
        this.fecha = fecha;
    }

    //endregion

    //region dialogoFragmet
    public void click(View v){
        DialogoFragment ds = new DialogoFragment();
        ds.show(getFragmentManager(),"Mi diálogo Fecha");
    }

    @Override
    public void onRespuesta(String s) {
        //Toast.makeText(getApplicationContext(),s,Toast.LENGTH_LONG ).show();

        if(s.equals(getString(R.string.bSi))){
            Toast.makeText(getApplicationContext(),"Gracias por tu interesante aportacion",
                    Toast.LENGTH_LONG ).show();
            //TODO obtener valores y crear objeto
            boolean rock;
            int numDisc;
            String grupo;
            if(SwitchPregRock.isChecked()){
                rock = true;
            }else{
                rock = false;
            }
            if(numDiscos.getText().toString() == null){
                numDisc = Integer.parseInt(numDiscos.getText().toString());
            }else{
                numDisc = 0;
            }


            if(btnGrupo.getCheckedRadioButtonId() == -1){
                //no ha seleccionado nada
                grupo = "no le gusta nada";
            }else{
                RadioButton rB = findViewById(btnGrupo.getCheckedRadioButtonId());
                grupo = rB.getText().toString();
            }
            //TODO las canciones van directas, el festival y la fecha concierto tmbn

            Datos datos = new Datos(rock, numDisc, grupo, canciones, festival, this.fecha);

            Toast.makeText(getApplicationContext(),datos.toString(),
                    Toast.LENGTH_LONG ).show();

            Log.w("Resultado", datos.toString());

        }else{
            //TODO no hace nada xD
            reproducir();
        }
    }
    //endregion

    public void limpiarValores(){
        SwitchPregRock.setChecked(false);
        numDiscos.setText("");
        btnGrupo.clearCheck();
        creaListView();
        String festival;
        GregorianCalendar fecha;
        selectorCiudades.setAdapter(a);
        selectorCiudades.setOnItemSelectedListener(this);
        etFecha.setHint(R.string.tFecha);
    }

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

    public void reproducir(){
       AsyncPlayer rep =  new AsyncPlayer("musicote");
        AudioAttributes audioAttributes = new AudioAttributes.Builder().
                setUsage(AudioAttributes.USAGE_MEDIA).
                setContentType(AudioAttributes.CONTENT_TYPE_MUSIC).build();
        String uriPath = "android.resource://" + getPackageName()
                + "/" + R.raw.no;
        Uri uri = Uri.parse(uriPath);
       rep.play(getApplicationContext(), uri, false,audioAttributes);
    }
    //endregion
}
