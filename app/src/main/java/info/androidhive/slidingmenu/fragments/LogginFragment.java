package info.androidhive.slidingmenu.fragments;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.concurrent.ExecutionException;

import info.androidhive.slidingmenu.MainActivity;
import info.androidhive.slidingmenu.R;
import info.androidhive.slidingmenu.constants.Constants;
import info.androidhive.slidingmenu.database.Controller;
import info.androidhive.slidingmenu.entities.Client;

/**
 * Created by Juan on 17/03/2017.
 */

public class LogginFragment extends CustomFragment{

    private View view;
    private int layout;
    private Context context;
    private EditText nombre;
    private EditText apellidos;
    private EditText nif;
    private EditText telefono;
    private EditText correo;
    private EditText direccion;
    private EditText codPos;
    private EditText password;
    private Button logginButton;

    public LogginFragment(int layout, View rootView, Context context) {
        super(layout, rootView, context);
        this.layout = layout;
        this.context = context;
    }

    @Override
    public View onCreateCustomView(View var1) {
        view = var1;

        nombre = (EditText) view.findViewById(R.id.nombre);
        apellidos = (EditText) view.findViewById(R.id.apellido);
        nif = (EditText) view.findViewById(R.id.nif);
        telefono = (EditText) view.findViewById(R.id.telefono);
        correo = (EditText) view.findViewById(R.id.correo);
        direccion = (EditText) view.findViewById(R.id.direccion);
        codPos = (EditText) view.findViewById(R.id.codpos);
        password = (EditText) view.findViewById(R.id.clave);
        logginButton = (Button) view.findViewById(R.id.logginButton);

        logginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loggin();
            }
        });

        return view;
    }

    public void loggin(){



        String nombreStr = nombre.getText().toString();
        String apellidosStr = apellidos.getText().toString();
        String nifStr = nif.getText().toString();
        String telefonoStr = telefono.getText().toString();
        String correoStr = correo.getText().toString();
        String direccionStr = direccion.getText().toString();
        String codPosStr = codPos.getText().toString();
        String passwordStr = password.getText().toString();

        Client cliente = new Client(nombreStr, apellidosStr, nifStr, correoStr, direccionStr, codPosStr, telefonoStr, passwordStr);
        Constants.currentClient = cliente;
        try {
            new MainActivity.JSONUpdateClients().execute().get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        if(Constants.success) {
            Controller.insertClients(cliente);
            Intent intent = getActivity().getIntent();
            getActivity().finish();
            startActivity(intent);
        }else{
            if(Constants.mysqlErrNo == 1062)
                Toast.makeText(context, "El NIF o correo ya están siendo utilizados", Toast.LENGTH_SHORT).show();
        }


    }
}
