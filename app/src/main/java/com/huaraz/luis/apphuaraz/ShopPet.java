package com.huaraz.luis.apphuaraz;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.huaraz.luis.apphuaraz.Adaptador.NoticiaAdapter;
import com.huaraz.luis.apphuaraz.Adaptador.StoreAdapter;
import com.huaraz.luis.apphuaraz.Model.Noticias;
import com.huaraz.luis.apphuaraz.Model.Store;
import com.huaraz.luis.apphuaraz.Servicio.APIService;
import com.huaraz.luis.apphuaraz.Servicio.ApiUtils;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ShopPet extends Fragment {

    private APIService mAPIService;

   // StoreAdapter LostPet;

  ///  private APIService mAPIService;
    //Inovacaion del servicio rest

    NoticiaAdapter LostPet;

   // ListView lv;

    ListView lv;

    public ShopPet() {
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
        View root = inflater.inflate(R.layout.fragment_shop, container, false);

        Noticias no = new Noticias();
        Noticias n1 = new Noticias();
        Noticias n2 = new Noticias();
        Noticias n3 = new Noticias();
        Noticias n4 = new Noticias();

        no.setNoticia("Evento de Plantas");
        no.setCaracteristica("Fertilizacion");
        no.setFecha("25/11/2019");

        n1.setNoticia("Evento de Plantas");
        n1.setCaracteristica("Injertos");
        n1.setFecha("30/11/2019");

        n2.setNoticia("Formas de Sembrado");
        n2.setCaracteristica("Fertilizacion");
        n2.setFecha("01/01/2020");

        n3.setNoticia("Evento de Truchas");
        n3.setCaracteristica("Criaderos");
        n3.setFecha("02/03/2020");

        n4.setNoticia("Evento de Naranja");
        n4.setCaracteristica("Plantacion");
        n4.setFecha("03/04/2020");

        final List<Noticias> itemsPets = new ArrayList<>();
        itemsPets.add(no);
        itemsPets.add(n1);
        itemsPets.add(n2);
        itemsPets.add(n3);
        itemsPets.add(n4);

        lv = (ListView) root.findViewById(R.id.lista_noticia);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Toast.makeText(getActivity(),"Evento Proximo a Informarle ",Toast.LENGTH_SHORT).show();

            }
        });
        LostPet = new NoticiaAdapter(getActivity(),itemsPets);
        lv.setAdapter(LostPet);



        ///Demo de valor bueno

        /*

        mAPIService = ApiUtils.getAPIService();
        //codigoSocio = WebServiceValidarUsuario.codigoSocio;

        tiendas();



        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Intent in  = new Intent(getActivity(),store_categoria.class);
                /////////////Envio de id de mascota Perdida

                Store lista = LostPet.getItem(position);
                in.putExtra("name",lista.getName());
                //////////


                in.putExtra("Comida",lista.getLatitude());
                in.putExtra("Correas",lista.getLongitude());
                in.putExtra("Collares",lista.getLongitude());
                in.putExtra("Champus",lista.getLatitude());
                in.putExtra("Ropa",lista.getLongitude());

                startActivity(in);

            }
        });
        // lv = (ListView) root.findViewById(R.id.lista_lost_pet);
        // AsyncCallWS task = new AsyncCallWS();
        //task.execute();
        //Evento de pruebas

        */

        return  root;

    }

    public  void tiendas (){

  /*
        final List<Store> itemsPets = new ArrayList<>();
        mAPIService.getStore().enqueue(new Callback<List<Store>>() {
            @Override
            public void onResponse(Call<List<Store>> call, Response<List<Store>> response) {

                if(response.isSuccessful()) {
                    for(int i=0;i<response.body().size();i++){
                        itemsPets.add(response.body().get(i));


                    }

                }else {
                    int statusCode  = response.code();

                    // handle request errors depending on status code
                }
                if (getActivity()!=null){
                    LostPet = new StoreAdapter(getActivity(),itemsPets);
                    lv.setAdapter(LostPet);
                }//codigo importante

            }

            @Override
            public void onFailure(Call<List<Store>> call, Throwable t) {

            }
        });


   */
    }


}
