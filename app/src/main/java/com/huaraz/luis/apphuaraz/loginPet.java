
package com.huaraz.luis.apphuaraz;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import static android.Manifest.permission.CALL_PHONE;

import com.huaraz.luis.apphuaraz.Model.Usuario;
import com.huaraz.luis.apphuaraz.Servicio.APIService;
import com.huaraz.luis.apphuaraz.Servicio.ApiUtils;
import com.huaraz.luis.apphuaraz.Servicio.Conectividad;
import com.huaraz.luis.apphuaraz.Servicio.Peticion;

import com.huaraz.luis.apphuaraz.Servicio.APIService;
import com.huaraz.luis.apphuaraz.Servicio.ApiUtils;
import com.huaraz.luis.apphuaraz.Sql.UsuariosDbHelper;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class loginPet extends AppCompatActivity {

    private UsuariosDbHelper usuariosDbHelper;
    private Toolbar toolbar;
    private Button ingresar ,crear;

    private EditText input_usuario;
    private EditText input_contrasena;
    private Context context = this;
    private String usuario;
    Dialog customDialog = null;
    private APIService mAPIService;
    FragmentManager fragmentManager = getSupportFragmentManager();
    private String contrasena;
    Usuario per = new Usuario();
    private FloatingActionButton fab;
    String usuariSinConexion;
    String contrasenaSinConexion;
    String sinConexion;

    String apellidosoff=null;
    boolean respuesta;
    //Datos de usuario


    public static  int id_user=0;
    public  static String correo_user=null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_pet);


        toolbar = (Toolbar) findViewById(R.id.activity_my_toolbar);
        setSupportActionBar(toolbar);
        toolbar.setLogo(R.drawable.ic_launcher);

        getSupportActionBar().setTitle("  Doctor Verde  ");


        ingresar= (Button)findViewById(R.id.boton_iniciar_sesion);
        crear =(Button)findViewById(R.id.boton_crear_cuenta);

        fab=(FloatingActionButton)findViewById(R.id.fab);

        // fab=(FloatingActionButton)findViewById(R.id.fab);
        input_usuario = (EditText)findViewById(R.id.usuario);
        input_contrasena  = (EditText)findViewById(R.id.contrasena);

        usuariosDbHelper = new UsuariosDbHelper(getApplicationContext());

        //  mAPIService = ApiUtils.getAPIService();
        // fab=(Button)findViewById(R.id.fab);
    /*
        if(isOnlineNet()==false){
            Toast toast = new Toast(getApplicationContext());
            LayoutInflater inflater = getLayoutInflater();
            View layout = inflater.inflate(R.layout.toast_layout,
                    (ViewGroup) findViewById(R.id.lytLayout));
            TextView txtMsg = (TextView)layout.findViewById(R.id.txtMensaje);
            txtMsg.setText("¡Activar su Servicio de Internet! ");
            toast.setDuration(Toast.LENGTH_SHORT);
            toast.setView(layout);
            toast.show();
        }*/
        crear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(loginPet.this,registro_usuario.class);
                startActivity(i);
            }
        });
        ingresar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                usuario = input_usuario.getText().toString();
                contrasena = input_contrasena.getText().toString();


                if(respuesta=Validar(usuario,contrasena)){

                    input_usuario.setText("");
                    input_contrasena.setText("");
                    input_usuario.setEnabled(true);
                    input_contrasena.setEnabled(true);
                    getIngreso(usuario,contrasena);

                };




            }
        });


        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                customDialog = new Dialog(context);
                //deshabilitamos el título por defecto
                customDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                //obligamos al usuario a pulsar los botones para cerrarlo
                customDialog.setCancelable(false);
                //establecemos el contenido de nuestro dialog
                customDialog.setContentView(R.layout.confirmacion);
                TextView titulo = (TextView) customDialog.findViewById(R.id.titulo);
                titulo.setText("DOCTOR VERDE");

                TextView contenido = (TextView) customDialog.findViewById(R.id.contenido);
                contenido.setText("¿Llamar a DOCTOR VERDE?");

                ((Button) customDialog.findViewById(R.id.aceptar)).setOnClickListener(new View.OnClickListener() {

                    @RequiresApi(api = Build.VERSION_CODES.M)
                    @Override
                    public void onClick(View view)
                    {
                        customDialog.dismiss();
                        //Evento para matar la aplicacion
                        llamar("926026797");
                        //  MainActivity.this.finish();
                    }///



                });

                ((Button) customDialog.findViewById(R.id.cancelar)).setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View view)
                    {
                        customDialog.dismiss();


                    }
                });

                customDialog.show();

            }
        });



        //metodo de login


        //Codigo para realizar llamada telefonica
/*
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Codigo personalizado para un mensaje personalizado
              /*  customDialog = new Dialog(context);
                customDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                customDialog.setContentView(R.layout.confirmacion);
                customDialog.setCancelable(false);
              //  customDialog = new Dialog(this,R.style.Theme_Dialog_Translucent);
                //deshabilitamos el título por defecto
                TextView titulo = (TextView) customDialog.findViewById(R.id.titulo);
                titulo.setText("COOPERATIVA CACOP");
                TextView texto = (TextView) customDialog.findViewById(R.id.texto);
                texto.setText("¿Llamar a la Cooperativa Cacop?");
                //ImageView dialogImage = (ImageView)openDialog.findViewById(R.id.dialog_image);
                Button dialogCloseButton = (Button)customDialog.findViewById(R.id.aceptar);
                dialogCloseButton.setOnClickListener(new OnClickListener(){
                    @Override
                    public void onClick(View view)
                    {
                        customDialog.dismiss();
                        llamar("(01)4247133");
                    }
                });
                ((Button) customDialog.findViewById(R.id.cancelar)).setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View view)
                    {
                        customDialog.dismiss();
                    }
                });
                customDialog.show();*/

        //   customDialog = new Dialog(context);
        //deshabilitamos el título por defecto
        // customDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        //obligamos al usuario a pulsar los botones para cerrarlo
        //     customDialog.setCancelable(false);
        //establecemos el contenido de nuestro dialog

                /*
                customDialog.setContentView(R.layout.confirmacion);
                TextView titulo = (TextView) customDialog.findViewById(R.id.titulo);
                titulo.setText("COOPERATIVA CACOP");
                TextView contenido = (TextView) customDialog.findViewById(R.id.contenido);
                contenido.setText("¿Llamar a la Cooperativa Cacop?");
                ((Button) customDialog.findViewById(R.id.aceptar)).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view)
                    {
                        customDialog.dismiss();
                        //Evento para matar la aplicacion
                        llamar("(01)4247133");
                        //  MainActivity.this.finish();
                    }///
                });
                ((Button) customDialog.findViewById(R.id.cancelar)).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view)
                    {
                        customDialog.dismiss();
                    }
                });
                customDialog.show();
                */
        //  }*/

        // });*/



    }


    public boolean Validar(String usuario, String contrasena){
        boolean valor =true;

        if(usuario.length()==0 || usuario.toString()==""){
            Toast toast = new Toast(getApplicationContext());

            LayoutInflater inflater = getLayoutInflater();
            View layout = inflater.inflate(R.layout.toast_layout,
                    (ViewGroup) findViewById(R.id.lytLayout));

            TextView txtMsg = (TextView)layout.findViewById(R.id.txtMensaje);
            txtMsg.setText("Ingresar su Usuario:DNI");

            toast.setDuration(Toast.LENGTH_LONG);
            toast.setView(layout);
            toast.show();
            valor =false;

        }else if(contrasena.length()==0 || contrasena.toString()==""){
            Toast toast = new Toast(getApplicationContext());

            LayoutInflater inflater = getLayoutInflater();
            View layout = inflater.inflate(R.layout.toast_layout,
                    (ViewGroup) findViewById(R.id.lytLayout));

            TextView txtMsg = (TextView)layout.findViewById(R.id.txtMensaje);
            txtMsg.setText("Ingresar su Contraseña");

            toast.setDuration(Toast.LENGTH_LONG);
            toast.setView(layout);
            toast.show();
            valor =false;

        }

        return valor;
    }
    //metoo de login
    public void getIngreso(String usuario , String contrasena1){

        int tipo=0;

        Toast toast = new Toast(getApplicationContext());

        LayoutInflater inflater = getLayoutInflater();
        View layout = inflater.inflate(R.layout.toast_layout,
                (ViewGroup) findViewById(R.id.lytLayout));

        TextView txtMsg = (TextView)layout.findViewById(R.id.txtMensaje);
        txtMsg.setText("Cargando Informacion");

        toast.setDuration(Toast.LENGTH_LONG);
        toast.setView(layout);
        toast.show();

        contrasena=contrasena1;
        if(isOnlineNet()==true){

            mAPIService = ApiUtils.getAPIService();
            try {


                Log.d("Ingreso del servidor", "realizando Pediticion");

                mAPIService.getlogin(usuario,1).enqueue(new Callback<Usuario>() {
                    @Override
                    public void onResponse(Call<Usuario> call, Response<Usuario> response) {
                        Log.d("Ingreso del servidor", "valor de peticion");

                        if(response.isSuccessful()) {
                            System.out.println("Impresion de valor"+response.body().getApellidos());
                            System.out.println("Impresion de valor"+response.body().getRespuesta());
                           ///Cambios nuevos
                            if(response.body().getRespuesta()==null){

                                per.setId_usuario(response.body().getId_usuario());
                                per.setNombres(response.body().getNombres());
                                per.setApellidos(response.body().getApellidos());
                                per.setDni(response.body().getDni());
                                per.setContrasena(response.body().getContrasena());
                                per.setTipo(response.body().getTipo());


                                if((per.getContrasena().equals(contrasena))&&(1==per.getTipo())||(per.getContrasena().equals(contrasena))&&(3==per.getTipo())){


                                    Global.usuario=(String.valueOf(per.getDni()));
                                    Global.nombre=per.getNombres();
                                    System.out.println("Global con internet"+Global.usuario);


                                    Toast toast = new Toast(getApplicationContext());

                                    LayoutInflater inflater = getLayoutInflater();
                                    View layout = inflater.inflate(R.layout.toast_layout,
                                            (ViewGroup) findViewById(R.id.lytLayout));

                                    TextView txtMsg = (TextView)layout.findViewById(R.id.txtMensaje);
                                    txtMsg.setText("Bienvenido :"+per.getNombres()+" "+per.getApellidos());

                                    toast.setDuration(Toast.LENGTH_LONG);
                                    toast.setView(layout);
                                    toast.show();
                                    System.out.println("Bienvenido"+per.getNombres()+per.getApellidos());

                                    Intent in = new Intent(loginPet.this,MainActivity.class);
                                    in.putExtra("nombre",per.getNombres());

                                    startActivity(in);

                                }else{

                                    Toast toast = new Toast(getApplicationContext());

                                    LayoutInflater inflater = getLayoutInflater();
                                    View layout = inflater.inflate(R.layout.toast_layout,
                                            (ViewGroup) findViewById(R.id.lytLayout));

                                    TextView txtMsg = (TextView)layout.findViewById(R.id.txtMensaje);
                                    txtMsg.setText("¡Contraseña Incorrecta! ");

                                    toast.setDuration(Toast.LENGTH_LONG);
                                    toast.setView(layout);
                                    toast.show();



                                }




                            }else{

                                Toast toast = new Toast(getApplicationContext());

                                LayoutInflater inflater = getLayoutInflater();
                                View layout = inflater.inflate(R.layout.toast_layout,
                                        (ViewGroup) findViewById(R.id.lytLayout));

                                TextView txtMsg = (TextView)layout.findViewById(R.id.txtMensaje);
                                txtMsg.setText("¡Usuario o Contraseña Incorrecta! ");

                                toast.setDuration(Toast.LENGTH_LONG);
                                toast.setView(layout);
                                toast.show();


                            }





                        } else {

                            Toast toast = new Toast(getApplicationContext());

                            LayoutInflater inflater = getLayoutInflater();
                            View layout = inflater.inflate(R.layout.toast_layout,
                                    (ViewGroup) findViewById(R.id.lytLayout));

                            TextView txtMsg = (TextView)layout.findViewById(R.id.txtMensaje);
                            txtMsg.setText("¡Contraseña Incorrecta! ");

                            toast.setDuration(Toast.LENGTH_LONG);
                            toast.setView(layout);
                            toast.show();
                            System.out.println("Se registro Usuario Tecnico");
                            int statusCode  = response.code();
                            System.out.println("2"+statusCode);
                            // handle request errors depending on status code
                        }

                    }

                    @Override
                    public void onFailure(Call<Usuario> call, Throwable t) {



                    }


                });

                Log.d("Salida del servidor", "Proyeccion lenta");

               // System.out.println("Se registro Usuario Tecnico");






            }catch (Exception e){

                e.printStackTrace();
                System.out.println("Error del servidor");
            }

        }else{


            usuarioLawyers();
            System.out.println("Login sin internet");




        }
      /*
        mAPIService.getIngreso(peticion).enqueue(new Callback<UserResponse>() {
            @Override
            public void onResponse(Call<UserResponse> call, Response<UserResponse> response) {
                if(response.isSuccessful()) {
                    String nombre=response.body().getUsername();
                   if (nombre!=null){
                        Toast.makeText(getApplicationContext(), "Bienvenido "+response.body().getUsername()+" :)",
                                Toast.LENGTH_LONG).show();
                       id_user=response.body().getId();
                       correo_user=response.body().getEmail();
                        Intent in = new Intent(loginPet.this,MainActivity.class);
                        startActivity(in);
                    }else{
                       Toast.makeText(getApplicationContext(), "Ingreso la informacion Correcta",
                               Toast.LENGTH_LONG).show();
                    }
                }else {
                    int statusCode  = response.code();
                    System.out.println("codigoError"+statusCode);
                    // handle request errors depending on status code
                }
            }
            @Override
            public void onFailure(Call<UserResponse> call, Throwable t) {
            }
        });*/

    }



    ///Verificar si cuenta con internet
    public Boolean isOnlineNet() {

        try {
            Process p = Runtime.getRuntime().exec("ping -c 1 www.google.es");

            int val           = p.waitFor();
            boolean reachable = (val == 0);
            return reachable;

        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return false;
    }
    @RequiresApi(api = Build.VERSION_CODES.M)
    public void llamar(String tel){

        Intent i = new Intent(Intent.ACTION_CALL);
        i.setData(Uri.parse("tel:"+tel));
/*
Intent i = new Intent(Intent.ACTION_DIAL);
i.setData(Uri.parse("tel:0612312312"));
if (i.resolveActivity(getPackageManager()) != null) {
      startActivity(i);
}*/
        if (ContextCompat.checkSelfPermission(getApplicationContext(), CALL_PHONE) == PackageManager.PERMISSION_GRANTED) {
            startActivity(i);
        } else {
            requestPermissions(new String[]{CALL_PHONE}, 1);
        }

        /*
        try{
            startActivity(new Intent(Intent.ACTION_CALL, Uri.parse("tel:"+tel)));
        }catch(Exception e){
            e.printStackTrace();
        }*/
    }

    private void usuarioLawyers() {
        new UsuarioLoadTask().execute();
    }


    private class UsuarioLoadTask extends AsyncTask<Void, Void, Cursor> {

        @Override
        protected Cursor doInBackground(Void... voids) {

            return usuariosDbHelper.getPedidosos();
        }

        @Override
        protected void onPostExecute(Cursor cursor) {
            if (cursor != null && cursor.getCount() > 0) {

                if (cursor.moveToFirst()) {
                    //Recorremos el cursor hasta que no haya más registros
                    do {



                        Global.nombre=cursor.getString(2);
                        apellidosoff=cursor.getString(3);
                        usuariSinConexion=cursor.getString(4);
                        contrasenaSinConexion=cursor.getString(5);
                        sinConexion=cursor.getString(8);

//                        System.out.println("datos sin conexion"+usuariSinConexion+contrasenaSinConexion);
                        //     System.out.println("dni"+sinConexion);



                    } while(cursor.moveToNext());

                    if(usuario.equals(usuariSinConexion)&&contrasenaSinConexion.equals(contrasena)){
                        System.out.println("usuario final"+Global.nombre);
                        System.out.println("dni"+sinConexion);

                        Toast toast = new Toast(getApplicationContext());

                        LayoutInflater inflater = getLayoutInflater();
                        View layout = inflater.inflate(R.layout.toast_layout,
                                (ViewGroup) findViewById(R.id.lytLayout));

                        TextView txtMsg = (TextView)layout.findViewById(R.id.txtMensaje);
                        txtMsg.setText("Bienvenido :"+Global.nombre+" "+apellidosoff);




                        Global.conexion=sinConexion;
                        Global.IdDni=usuariSinConexion;
                        Global.usuario=usuariSinConexion;

                        toast.setDuration(Toast.LENGTH_LONG);
                        toast.setView(layout);
                        toast.show();

                        Intent in = new Intent(loginPet.this,MainActivity.class);
                        //  in.putExtra("nombre",per.getNombres());

                        startActivity(in);



                        // usuario
                        //contrasena

                    }else{

                        Toast toast = new Toast(getApplicationContext());

                        LayoutInflater inflater = getLayoutInflater();
                        View layout = inflater.inflate(R.layout.toast_layout,
                                (ViewGroup) findViewById(R.id.lytLayout));

                        TextView txtMsg = (TextView)layout.findViewById(R.id.txtMensaje);
                        txtMsg.setText("¡Verificar su Usuario o Contraseña! ");

                        toast.setDuration(Toast.LENGTH_LONG);
                        toast.setView(layout);
                        toast.show();




                    }
                    //  usuariosDbHelper.deletePedidos();
                }



            } else {
                // Mostrar empty state
                System.out.println("base de datos vacia");

                Toast toast = new Toast(getApplicationContext());

                LayoutInflater inflater = getLayoutInflater();
                View layout = inflater.inflate(R.layout.toast_layout,
                        (ViewGroup) findViewById(R.id.lytLayout));

                TextView txtMsg = (TextView)layout.findViewById(R.id.txtMensaje);
                txtMsg.setText("¡Verificar Usuario o Contraseña /Comuniquese a nuestro numero de atencion!");

                toast.setDuration(Toast.LENGTH_LONG);
                toast.setView(layout);
                toast.show();

            }
        }
    }

    @Override
    public void onBackPressed()
    {

        super.onBackPressed();
        //  System.exit(0);
        // Añade más funciones si fuese necesario
      //  super.onBackPressed();  // Invoca al método
    }





}