package com.huaraz.luis.apphuaraz.alarm;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.AsyncTask;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.huaraz.luis.apphuaraz.Adaptador.StoreAdapter;
import com.huaraz.luis.apphuaraz.Global;
import com.huaraz.luis.apphuaraz.MisPedidosOff;
import com.huaraz.luis.apphuaraz.Model.Demo;
import com.huaraz.luis.apphuaraz.Model.Pedido;
import com.huaraz.luis.apphuaraz.Model.Usuario;
import com.huaraz.luis.apphuaraz.R;
import com.huaraz.luis.apphuaraz.Servicio.APIService;
import com.huaraz.luis.apphuaraz.Servicio.ApiUtils;
import com.huaraz.luis.apphuaraz.Sql.PedidosDbHelper;
import com.huaraz.luis.apphuaraz.Sql.UsuariosDbHelper;
import com.huaraz.luis.apphuaraz.Sql.UsuariosDbHelper;
import com.huaraz.luis.apphuaraz.loginPet;
import com.huaraz.luis.apphuaraz.registro_usuario;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MyBroadcastReceiver extends BroadcastReceiver {
    private APIService mAPIService;
    private PedidosDbHelper pedidosDbHelper;
    private UsuariosDbHelper UsuariosDbHelper;
    Context mContext;

    String DNI="";

    int envio=0;
    StoreAdapter LostPet;

    private Button boton_registrar_planta1;
    ListView lv;
    @Override
    public void onReceive(Context context, Intent intent) {
     //   Toast.makeText(context, "Se activo alarma....", Toast.LENGTH_LONG).show();

        System.out.println("inicio programacion");

        if(isOnlineNet()){

            UsuariosDbHelper = new UsuariosDbHelper(context.getApplicationContext());
            usuarioLawyers();

            pedidosDbHelper = new PedidosDbHelper(context.getApplicationContext());
            loadLawyers();




        }else{


        }



    }

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


    private void loadLawyers() {
        new LawyersLoadTask().execute();
    }

    private void usuarioLawyers() {
        new UserLoadTask().execute();
    }

    private class LawyersLoadTask extends AsyncTask<Void, Void, Cursor> {

        @Override
        protected Cursor doInBackground(Void... voids) {

            return pedidosDbHelper.getPedidosos();
        }

        @Override
        protected void onPostExecute(Cursor cursor) {
            if (cursor != null && cursor.getCount() > 0) {

                if (cursor.moveToFirst()) {
                    //Recorremos el cursor hasta que no haya más registros
                    do {

                        enviarInformacion( cursor.getString(2),cursor.getString(3),cursor.getString(4),cursor.getString(5),cursor.getString(6),cursor.getInt(7), cursor.getString(9));

                    } while(cursor.moveToNext());

                  pedidosDbHelper.deletePedidos();
                }



            } else {
                // Mostrar empty state
                System.out.println("no hay informacion en la base de datos para enviar");

            }
        }
    }


    //Enviar informacion
    public void enviarInformacion(String foto1,String foto2,String foto3,String provincia,String distrito,int usuario,String fecha){

        mAPIService = ApiUtils.getAPIService();

        mAPIService.addPedido(foto1,foto2,foto3,provincia,distrito,usuario,0,fecha,1,"0","0","0").enqueue(new Callback<Pedido>() {
            @Override
            public void onResponse(Call<Pedido> call, Response<Pedido> response) {

                if(response.isSuccessful()) {
                    System.out.println("Se envio registro");

                }else {
                    int statusCode  = response.code();
                    System.out.println("estado de error"+statusCode);
                    // handle request errors depending on status code
                }

            }

            @Override
            public void onFailure(Call<Pedido> call, Throwable t) {

            }
        });



    }

    private class UserLoadTask extends AsyncTask<Void, Void, Cursor> {


        @Override
        protected Cursor doInBackground(Void... voids) {

            return UsuariosDbHelper.getPedidosos();
        }

        @Override
        protected void onPostExecute(Cursor cursor) {
            if (cursor != null && cursor.getCount() > 0) {

                if (cursor.moveToFirst()) {
                    //Recorremos el cursor hasta que no haya más registros
                    do {

                        if(cursor.getInt(8)==3){
                            enviarUsuario(cursor.getString(2),cursor.getString(3),cursor.getString(4),cursor.getString(5),cursor.getString(6),cursor.getString(7),cursor.getInt(8));
                            DNI=cursor.getString(4);
                            com.huaraz.luis.apphuaraz.Sql.Usuario demo = new com.huaraz.luis.apphuaraz.Sql.Usuario(cursor.getString(2),cursor.getString(3),cursor.getString(4),cursor.getString(5),cursor.getString(6),cursor.getString(7),1);
                            new AddEditLawyerTask().execute(demo);

                        }




                    } while(cursor.moveToNext());




                //    UsuariosDbHelper.deletePedidos();
                }



            } else {
                // Mostrar empty state
                System.out.println("no hay informacion en la base de datos para enviar");

            }
        }
    }



    private class AddEditLawyerTask extends AsyncTask<com.huaraz.luis.apphuaraz.Sql.Usuario, Void, Boolean> {

        @Override
            protected Boolean doInBackground(com.huaraz.luis.apphuaraz.Sql.Usuario... lawyers) {
            if (DNI != null) {
                return UsuariosDbHelper.updateLawyerUser(lawyers[0], DNI) > 0;

            }

            return null;
        }

        @Override
        protected void onPostExecute(Boolean result) {
          //  showLawyersScreen(result);
        }

    }


    public void enviarUsuario( String nombres, String apellidos, String dni, String contrasena, String correo, String telefono,int tipo){

        mAPIService = ApiUtils.getAPIService();
        mAPIService.addUsuario(nombres,apellidos,dni,contrasena,correo,telefono,tipo).enqueue(new Callback<Usuario>() {
            @Override
            public void onResponse(Call<Usuario> call, Response<Usuario> response) {

                if(response.isSuccessful()) {


                    System.out.println("salio el usuario modo off");



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
