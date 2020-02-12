package com.huaraz.luis.apphuaraz.alarm;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.AsyncTask;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.huaraz.luis.apphuaraz.Adaptador.StoreAdapter;
import com.huaraz.luis.apphuaraz.Global;
import com.huaraz.luis.apphuaraz.MisPedidosOff;
import com.huaraz.luis.apphuaraz.Model.Demo;
import com.huaraz.luis.apphuaraz.Model.Pedido;
import com.huaraz.luis.apphuaraz.Servicio.APIService;
import com.huaraz.luis.apphuaraz.Servicio.ApiUtils;
import com.huaraz.luis.apphuaraz.Sql.PedidosDbHelper;
import com.huaraz.luis.apphuaraz.Sql.UsuariosDbHelper;
import com.huaraz.luis.apphuaraz.Sql.UsuariosDbHelper;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MyBroadcastReceiver extends BroadcastReceiver {
    private APIService mAPIService;
    private PedidosDbHelper pedidosDbHelper;
    private UsuariosDbHelper UsuariosDbHelper;
    Context mContext;


    StoreAdapter LostPet;

    private Button boton_registrar_planta1;
    ListView lv;
    @Override
    public void onReceive(Context context, Intent intent) {
     //   Toast.makeText(context, "Se activo alarma....", Toast.LENGTH_LONG).show();

        if(Global.conexion.equals("3")){

            System.out.println("alarma usuario 33333");

            UsuariosDbHelper = new UsuariosDbHelper(context.getApplicationContext());
            usuarioLawyers();

            pedidosDbHelper = new PedidosDbHelper(context.getApplicationContext());
            loadLawyers();

        }else{

            pedidosDbHelper = new PedidosDbHelper(context.getApplicationContext());
            loadLawyers();

        }

   //     Toast.makeText(context, "Se termino de enviar....", Toast.LENGTH_LONG).show();


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

                        enviarInformacion( cursor.getString(2),cursor.getString(3),cursor.getString(4),cursor.getString(5),cursor.getString(6),cursor.getInt(7),cursor.getString(9));

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

                        enviarInformacion( cursor.getString(2),cursor.getString(3),cursor.getString(4),cursor.getString(5),cursor.getString(6),cursor.getInt(7),cursor.getString(9));

                    } while(cursor.moveToNext());

                    UsuariosDbHelper.deletePedidos();
                }



            } else {
                // Mostrar empty state
                System.out.println("no hay informacion en la base de datos para enviar");

            }
        }
    }


}
