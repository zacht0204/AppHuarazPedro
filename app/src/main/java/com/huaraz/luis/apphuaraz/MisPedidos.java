package com.huaraz.luis.apphuaraz;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.huaraz.luis.apphuaraz.Adaptador.DemoAdapter;
import com.huaraz.luis.apphuaraz.Adaptador.PedidoAdapter;
import com.huaraz.luis.apphuaraz.Model.Demo;
import com.huaraz.luis.apphuaraz.Model.Pedido;
import com.huaraz.luis.apphuaraz.Model.Pet;
import com.huaraz.luis.apphuaraz.Servicio.APIService;
import com.huaraz.luis.apphuaraz.Servicio.ApiUtils;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MisPedidos extends Fragment {
    private APIService mAPIService;
    PedidoAdapter pedido;
    private FloatingActionButton fabAddPet;
    ListView lv;
    int  usuario;

    public MisPedidos() {
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
        final View root = inflater.inflate(R.layout.fragment_pets, container, false);

        lv = (ListView) root.findViewById(R.id.lista_demos);

        if(isOnlineNet()){

            usuario=Integer.parseInt(Global.usuario);
            System.out.println("valor de usuario"+usuario);

            mAPIService = ApiUtils.getAPIService();
            loadProfile();


        }else{

            Toast.makeText(getActivity(),"Nesecitas contar un Red de Internet estable. ",Toast.LENGTH_LONG).show();
        }




        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Toast toast = new Toast(getContext());

                LayoutInflater inflater = getLayoutInflater();
                View layout = inflater.inflate(R.layout.toast_layout, (ViewGroup) root.findViewById((R.id.lytLayout)));

                TextView txtMsg = (TextView)layout.findViewById(R.id.txtMensaje);
                txtMsg.setText("¡Estamos evaluando su Envio,Un tecnico se comunicara con usted lo mas pronto posible!");

                toast.setDuration(Toast.LENGTH_LONG);
                toast.setView(layout);
                toast.show();




            }
        });



      //   fabAddPet = (FloatingActionButton)root.findViewById(R.id.AddPet);



        //codigoSocio = WebServiceValidarUsuario.codigoSocio;

        // lv = (ListView) root.findViewById(R.id.lista_lost_pet);
        // AsyncCallWS task = new AsyncCallWS();
        //task.execute();
        //Evento de pruebas

        return  root;

    }

    public  void  loadProfile(){



            final List<Pedido> itemsPedidos = new ArrayList<>();
            // final List<Pet> itemsPet = new ArrayList<>();
            System.out.println("Demo Demo");
            mAPIService.getMyPedido(usuario).enqueue(new Callback<List<Pedido>>() {
                @Override
                public void onResponse(Call<List<Pedido>> call, Response<List<Pedido>> response) {

                    if(response.isSuccessful()) {
                        for(int i=0;i<response.body().size();i++){
                            itemsPedidos.add(response.body().get(i));
                            System.out.println("valor de llegADA"+itemsPedidos.get(i).getDistrito());
                            // itemsPet.add(response.body().get(i).getPet());
                            //   System.out.println("Luis"+itemsLostPets.get(i).getId_distrito().toString());
                            // System.out.println("array ++"+itemsLostPets.get(i).getInfo()+"Name"+itemsPet.get(i).getName());


                        }

                    }else {
                        int statusCode  = response.code();
                        System.out.println("2"+statusCode);
                        // handle request errors depending on status code
                    }
                    if (getActivity()!=null){

                        pedido = new PedidoAdapter (getActivity(),itemsPedidos);
                        lv.setAdapter(pedido);

                        System.out.println("3");
                    }////codigo importante

                }

                @Override
                public void onFailure(Call<List<Pedido>> call, Throwable t) {

                }
            });




        /*
        mAPIService.getFoto().enqueue(new Callback<List<Demo>>() {
            @Override
            public void onResponse(Call<List<Demo>> call, Response<List<Demo>> response) {


                if(response.isSuccessful()) {
                    for(int i=0;i<response.body().size();i++){
                        itemsLostPets.add(response.body().get(i));
                        // itemsPet.add(response.body().get(i).getPet());
                        System.out.println("Luis"+itemsLostPets.get(i).getId_distrito().toString());
                        // System.out.println("array ++"+itemsLostPets.get(i).getInfo()+"Name"+itemsPet.get(i).getName());


                    }

                }else {
                    int statusCode  = response.code();
                    System.out.println("2"+statusCode);
                    // handle request errors depending on status code
                }
                if (getActivity()!=null){

                    pet = new DemoAdapter (getActivity(),itemsLostPets);
                    lv.setAdapter(pet);

                    System.out.println("3");
                }////codigo importante



            }

            @Override
            public void onFailure(Call<List<Demo>> call, Throwable t) {
                t.printStackTrace();
            }
        });
        */


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


    public  void pets(){
        int user=loginPet.id_user;
        String usuario= String.valueOf(user);
        //idi de usuario mientras realizo el login fijo 49

        final List<Pet> itemsPets = new ArrayList<>();
        mAPIService.getMyPets(usuario).enqueue(new Callback<List<Pet>>() {
            @Override
            public void onResponse(Call<List<Pet>> call, Response<List<Pet>> response) {

                if(response.isSuccessful()) {
                    System.out.println("Ingresa el valor"+response.body());
                    for(int i=0;i<response.body().size();i++){
                        itemsPets.add(response.body().get(i));

                    }

                }else {
                    int statusCode  = response.code();
                    // handle request errors depending on status code
                }
                if (getActivity()!=null){

                    /*
                pet = new PetAdapter(getActivity(),itemsPets);
                lv.setAdapter(pet);*/
                }
            }

            @Override
            public void onFailure(Call<List<Pet>> call, Throwable t) {
                t.printStackTrace();
            }
        });

    }


}
