package com.huaraz.luis.apphuaraz;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.huaraz.luis.apphuaraz.Model.UserResponse;
import com.huaraz.luis.apphuaraz.Model.Usuario;
import com.huaraz.luis.apphuaraz.Servicio.APIService;
import com.huaraz.luis.apphuaraz.Servicio.ApiUtils;
import com.huaraz.luis.apphuaraz.Servicio.Peticion;
import com.huaraz.luis.apphuaraz.Servicio.User;
import com.huaraz.luis.apphuaraz.Servicio.ValidationUtils;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class registro_usuario extends AppCompatActivity {

    TextInputLayout id_nombres, id_apellidos, id_dni, id_telefono,id_contrasena,id_confirmar_contrasena,id_correo;
    private APIService mAPIService;
    private Toolbar toolbar;
    Button btnRegistrar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro_usuario);


        id_nombres = (TextInputLayout) findViewById(R.id.id_nombres);
        id_apellidos = (TextInputLayout) findViewById(R.id.id_apellidos);
        id_dni = (TextInputLayout) findViewById(R.id.id_dni);
        id_contrasena = (TextInputLayout) findViewById(R.id.id_contrasena);
        id_confirmar_contrasena = (TextInputLayout) findViewById(R.id.id_confirmar_contrasena);
        id_correo = (TextInputLayout) findViewById(R.id.id_correo);
        id_telefono = (TextInputLayout) findViewById(R.id.id_telefono);
        btnRegistrar = (Button) findViewById(R.id.btnRegistrar);

        //Configuracion de la barra de titulo
        toolbar = (Toolbar) findViewById(R.id.activity_my_toolbar);
        setSupportActionBar(toolbar);
        toolbar.setLogo(R.drawable.ic_launcher);
        getSupportActionBar().setTitle("Nuevo Registro Usuario");

        mAPIService = ApiUtils.getAPIService();

        btnRegistrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                signUp();

            }
        });


    }

    private void signUp() {

        if(!ValidationUtils.isEmpty(id_nombres, id_apellidos, id_dni, id_telefono,id_contrasena,id_confirmar_contrasena,id_correo)) {

            String pass = id_contrasena.getEditText().getText().toString().trim();
            String passConfirm = id_confirmar_contrasena.getEditText().getText().toString().trim();
            String dni = id_dni.getEditText().getText().toString().trim();
            String telefono = id_telefono.getEditText().getText().toString().trim();

            if(!((dni.length()) ==8)){
                id_dni.setError("DNI requerido de 8 digitos");
                id_dni.setErrorEnabled(true);
                return;
            }

            if(!((telefono.length()) ==9)){
                id_telefono.setError("Celular requerido de 9 o 7 digitos");
                id_telefono.setErrorEnabled(true);
                return;
            }

            if (!pass.equals(passConfirm)) {
                id_contrasena.setError("Contraseña no coinciden");
                id_confirmar_contrasena.setError("Contraseña no coinciden");
                id_contrasena.setErrorEnabled(true);
                id_confirmar_contrasena.setErrorEnabled(true);
                return;
            }

            String nombres = id_nombres.getEditText().getText().toString().trim();
            String apellidos = id_apellidos.getEditText().getText().toString().trim();

            String contrasena = id_contrasena.getEditText().getText().toString().trim();
            String correo = id_correo.getEditText().getText().toString().trim();



            Usuario usuario = new Usuario();

            usuario.setNombres(nombres);
            usuario.setApellidos(apellidos);
            usuario.setDni(dni);
            usuario.setContrasena(contrasena);
            usuario.setCorreo(correo);
            usuario.setTelefono(telefono);
            usuario.setTipo(2);  //Registro tipo dos es tecnico

            System.out.println("entreo" +pass+passConfirm);

            // mAPIService.addUsuario
            //Registro de usuario de tecnico
            mAPIService.addUsuario(nombres,apellidos,dni,contrasena,correo,telefono,2).enqueue(new Callback<Usuario>() {
                @Override
                public void onResponse(Call<Usuario> call, Response<Usuario> response) {

                    if(response.isSuccessful()) {




                        Toast toast = new Toast(getApplicationContext());

                        LayoutInflater inflater = getLayoutInflater();
                        View layout = inflater.inflate(R.layout.toast_layout,
                                (ViewGroup) findViewById(R.id.lytLayout));

                        TextView txtMsg = (TextView)layout.findViewById(R.id.txtMensaje);
                        txtMsg.setText("¡Registro De Usuario Exitoso! ");

                        toast.setDuration(Toast.LENGTH_LONG);
                        toast.setView(layout);
                        toast.show();
                        System.out.println("Se registro Usuario Tecnico");

                        Intent i = new Intent(registro_usuario.this,loginPet.class);
                        startActivity(i);



                    }else {
                        int statusCode  = response.code();
                        System.out.println("2"+statusCode);
                        // handle request errors depending on status code
                    }

                }

                @Override
                public void onFailure(Call<Usuario> call, Throwable t) {

                }
            });


        }
    }
}

