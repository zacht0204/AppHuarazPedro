package com.huaraz.luis.apphuaraz;

import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.MediaController;
import android.widget.Spinner;
import android.widget.Toast;
import android.widget.VideoView;

import com.huaraz.luis.apphuaraz.Adaptador.NoticiaAdapter;
import com.huaraz.luis.apphuaraz.Model.Noticias;
import com.huaraz.luis.apphuaraz.Model.PetLost;
import com.huaraz.luis.apphuaraz.Servicio.APIService;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Inicio extends Fragment {

    private APIService mAPIService;
    //Inovacaion del servicio rest

    NoticiaAdapter LostPet;

    ListView lv;

    //valores de envio
    private LinearLayout llSearchLostPets;
   // private RecyclerView.LayoutManager lManager;
    private Spinner spnDistrict;
    private Spinner spnPetType;
    private Button btnSearchLostPets,ocultar;
    private VideoView video;


    ///////////
    public Inicio() {
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
        View root = inflater.inflate(R.layout.fragment_inicio, container, false);

        video=(VideoView) root.findViewById(R.id.videoView);

        MediaController mc= new MediaController(getActivity());


        String path = "android.resource://" + getActivity().getPackageName() + "/" + R.raw.video;
        video.setVideoURI(Uri.parse(path));
        video.setMediaController(mc);
        video.start();

     //   video = (VideoView) root.findViewById(R.id.videoView_videos);

      //  imgOwnerPhoto = (ImageView) root.findViewById(R.id.ownerPhoto);

/*
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
  */

        return  root;

  }
    public void loadLostPets(){

        final List<PetLost> itemsLostPets = new ArrayList<>();
        // final List<Pet> itemsPet = new ArrayList<>();
        mAPIService.getPetLost().enqueue(new Callback<List<PetLost>>() {
            @Override
            public void onResponse(Call<List<PetLost>> call, Response<List<PetLost>> response) {


                if(response.isSuccessful()) {
                    for(int i=0;i<response.body().size();i++){
                        itemsLostPets.add(response.body().get(i));
                        // itemsPet.add(response.body().get(i).getPet());
                        // System.out.println("Demo Demo"+itemsLostPets.get(i).getPet().getName());
                        // System.out.println("array ++"+itemsLostPets.get(i).getInfo()+"Name"+itemsPet.get(i).getName());


                    }

                }else {
                    int statusCode  = response.code();
                    // handle request errors depending on status code
                }
                if (getActivity()!=null){
                   /*
                LostPet = new PetLostAdapter(getActivity(),itemsLostPets);
                lv.setAdapter(LostPet);
                */
                }//codigo importante
            }

            @Override
            public void onFailure(Call<List<PetLost>> call, Throwable t) {
                t.printStackTrace();
            }
        });



    }


    //realizar filtros

    public void  filtro(String tipo, String distrito ){

        final List<PetLost> itemsLostPets2 = new ArrayList<>();

        mAPIService.addPetSearch(distrito,tipo).enqueue(new Callback<List<PetLost>>() {
            @Override
            public void onResponse(Call<List<PetLost>> call, Response<List<PetLost>> response) {

                if(response.isSuccessful()) {

                    for(int i=0;i<response.body().size();i++){
                        itemsLostPets2.clear();
                        itemsLostPets2.add(response.body().get(i));
                        // itemsPet.add(response.body().get(i).getPet());
                        // System.out.println("Demo Demo"+itemsLostPets.get(i).getPet().getName());
                        // System.out.println("array ++"+itemsLostPets.get(i).getInfo()+"Name"+itemsPet.get(i).getName());


                    }

                }else {
                    int statusCode  = response.code();
                    // handle request errors depending on status code
                }
                if (getActivity()!=null){
                    /*
                    LostPet = new PetLostAdapter(getActivity(),itemsLostPets2);
                    lv.setAdapter(LostPet);
                    */
                }//codigo importante

            }

            @Override
            public void onFailure(Call<List<PetLost>> call, Throwable t) {

            }
        });


    }



}
