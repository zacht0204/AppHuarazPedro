package com.huaraz.luis.apphuaraz;

import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;

import com.huaraz.luis.apphuaraz.Adaptador.StoreAdapter;
import com.huaraz.luis.apphuaraz.Model.Demo;
import com.huaraz.luis.apphuaraz.Servicio.APIService;
import com.huaraz.luis.apphuaraz.Servicio.ApiUtils;
import com.huaraz.luis.apphuaraz.Servicio.Conectividad;
import com.huaraz.luis.apphuaraz.Sql.PedidosDbHelper;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MisPedidosOff extends Fragment {

    private APIService mAPIService;
    private PedidosDbHelper pedidosDbHelper;


    StoreAdapter LostPet;

    private Button boton_registrar_planta1;
    ListView lv;

    public MisPedidosOff() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_off, container, false);
        boton_registrar_planta1 = (Button) root.findViewById(R.id.boton_registrar_planta1);



        pedidosDbHelper = new PedidosDbHelper(getActivity());

        boton_registrar_planta1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(Conectividad.isOnline(getActivity().getApplicationContext())){
                    loadLawyers();
                }else{

                }



            }
        });


       return  root;

}
    private void loadLawyers() {
        new LawyersLoadTask().execute();
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

                          enviarInformacion( cursor.getString(2),cursor.getString(3),cursor.getString(4),cursor.getString(5),cursor.getString(6));

                      } while(cursor.moveToNext());

                      pedidosDbHelper.deletePedidos();
                  }



              } else {
                  // Mostrar empty state
                  System.out.println("no hay informacion en la base de datos");
              }
          }
      }


      //Enviar informacion
    public void enviarInformacion(String foto1,String foto2,String foto3,String provincia,String distrito){

            mAPIService = ApiUtils.getAPIService();

        mAPIService.addFoto(foto1,foto2,foto3,distrito,provincia).enqueue(new Callback<Demo>() {
        @Override
        public void onResponse(Call<Demo> call, Response<Demo> response) {


            if(response.isSuccessful()) {
                System.out.println("salio");

            }else {
                int statusCode  = response.code();
                System.out.println("no internet1"+statusCode);
                // handle request errors depending on status code
            }

        }

        @Override
        public void onFailure(Call<Demo> call, Throwable t) {

            System.out.println("conversion issuallallalallae! big problem+"+ t.getMessage());
            if (t instanceof IOException) {
                System.out.println("conversion issuallallalallae! big problem");
                // logging probably not necessary
            }
            else {

                // todo log to some central bug tracking service
            }
        }
    });


    }





}
