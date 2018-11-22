package com.pmdm.t1v1;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.app.DialogFragment;
import android.view.LayoutInflater;

public class DialogoFragment extends DialogFragment {

    RespuestaDialogoFragment respuesta;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        // Get the layout inflater
        LayoutInflater inflater = getActivity().getLayoutInflater();

        // Inflate and set the layout for the dialog
        // Pass null as the parent view because its going in the dialog layout
        builder.setView(inflater.inflate(R.layout.dialogo_emergente, null))
                // Add action buttons
                .setPositiveButton(R.string.bSi, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        //TODO rellenar metodos para si
                        respuesta.onRespuesta(getString(R.string.bSi));
                    }
                })
                .setNegativeButton(R.string.bNo, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        respuesta.onRespuesta(getString(R.string.bNo));
                        DialogoFragment.this.getDialog().cancel();
                    }
                });



        return builder.create();
    }

    public interface RespuestaDialogoFragment{
        public void onRespuesta(String s);
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        respuesta=(RespuestaDialogoFragment)activity;
    }
}
