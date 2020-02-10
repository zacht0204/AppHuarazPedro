package com.huaraz.luis.apphuaraz;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.huaraz.luis.apphuaraz.Model.Districts;
import com.huaraz.luis.apphuaraz.Model.Demo;
import com.huaraz.luis.apphuaraz.Model.UserResponse;
import com.huaraz.luis.apphuaraz.Servicio.APIService;
import com.huaraz.luis.apphuaraz.Servicio.ApiUtils;
import com.huaraz.luis.apphuaraz.Servicio.Conectividad;
import com.huaraz.luis.apphuaraz.Servicio.ValidationUtils;
import com.huaraz.luis.apphuaraz.Sql.Pedido;
import com.huaraz.luis.apphuaraz.Sql.PedidosDbHelper;
import com.huaraz.luis.apphuaraz.alarm.MyBroadcastReceiver;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

//import com.pet.ebert.findme.Adaptador.ProfileApapter;

public class capturarPlanta extends Fragment {

    private APIService mAPIService;
    //Inovacaion del servicio rest
    private ImageView imgOwnerPhoto;
    private ImageView imgOwnerPhoto2;
    private ImageView imgOwnerPhoto3;
    private Conectividad conectividad;
    //TextView txvEmail;
    private TextInputLayout tilFullname, tilSurname;
    //, tilAddress,tilCiudad;
    private RadioGroup rbgSex;
    private Spinner spnDistrict;
    private Button boton_registrar_planta;
    private PedidosDbHelper PedidosDbHelper;
   // private FloatingActionButton fabSaveMyInfo;
    //ProfileApapter profile;
    static final int REQUEST_IMAGE_CAPTURE = 1;
    public String foto ="foto";

    public String Distrito;
    public  String Ciudad;
    TextView nombrePerfil;

    int c=0;
    private NavigationView navView;


    List<Districts> itemsDistritos = new ArrayList<>();

    ListView lv;
    public capturarPlanta() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_info, container, false);


        //Datos para agregar la informacion
     //   spnDistrict = (Spinner) root.findViewById(R.id.spnDistrict);
        imgOwnerPhoto = (ImageView) root.findViewById(R.id.ownerPhoto);
        imgOwnerPhoto2 = (ImageView) root.findViewById(R.id.ownerPhoto2);
        imgOwnerPhoto3 = (ImageView) root.findViewById(R.id.ownerPhoto3);
       // txvEmail = (TextView) root.findViewById(R.id.txvEmail);
        tilFullname = (TextInputLayout) root.findViewById(R.id.tilFullname);///Distrito
        tilSurname = (TextInputLayout) root.findViewById(R.id.tilSurname); /// Ciudad

        boton_registrar_planta = (Button) root.findViewById(R.id.boton_registrar_planta);

        PedidosDbHelper = new PedidosDbHelper(getActivity());

        // tilAddress = (TextInputLayout) root.findViewById(R.id.tilAddress);

       // rbgSex = (RadioGroup) root.findViewById(R.id.rbgSexo);
        // fabSaveMyInfo = (FloatingActionButton) root.findViewById(R.id.fabSaveMyInfo);

       //Cargar el spinner
        String[] Distritos = {"LINCE","SAN MIGUEL","INDEPENDENCIA","SURCO","SAN ISIDRO","LA MOLINA","SAN MARTIN DE PORRES","LOS OLIVOS","MIRAFLORES","MIRAFLORES","SAN MIGUEL","COMAS"};
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(getActivity().getApplicationContext(),R.layout.spinner_item,Distritos);
       // arrayAdapter.setDropDownViewResource(R.layout.spinner_dropdown_item);
       // spnDistrict.setAdapter(arrayAdapter);
        // Creating adapter for spinner
        /*
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, datos);

        spnDistrict.setAdapter(adaptador);*/
        //task.execute();
        //Evento de pruebas

        mAPIService = ApiUtils.getAPIService();
        boton_registrar_planta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (!ValidationUtils.isEmpty(tilFullname, tilSurname)) {
                    addFoto();
                    Toast.makeText(getActivity(),"Se Capturo la Planta,por favor Espere..",Toast.LENGTH_SHORT).show();
                    Toast.makeText(getActivity(),"Un Tecnico Se Estara Comunicando con Usted ",Toast.LENGTH_SHORT).show();


                    Intent i = new Intent(getContext(), MainActivity.class);
                    startActivity(i);

                }else{
                    Toast.makeText(getActivity(),"Ingrese la informacion faltante",Toast.LENGTH_SHORT).show();
                }



               ;
            }
        });
       // loadProfile();

        imgOwnerPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getPhoto();
            }
        });


        imgOwnerPhoto2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                getPhoto();

            }
        });

        imgOwnerPhoto3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                getPhoto();

            }
        });
        /*
        fabSaveMyInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                actualizardatos();
            }
        }
        );*/



        return  root;

    }
    public void addFoto(){


        //Agregar fotografia 1

        BitmapDrawable drawable = (BitmapDrawable) imgOwnerPhoto.getDrawable();
        Bitmap bitmap = drawable.getBitmap();

        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, bos);
        byte[] bb = bos.toByteArray();
        String petPhoto64 = Base64.encodeToString(bb, 0);

        //Agregar fotografia 2
        BitmapDrawable drawable2 = (BitmapDrawable) imgOwnerPhoto2.getDrawable();
        Bitmap bitmap2 = drawable2.getBitmap();

        ByteArrayOutputStream bos2 = new ByteArrayOutputStream();
        bitmap2.compress(Bitmap.CompressFormat.PNG, 100, bos2);
        byte[] bb2 = bos2.toByteArray();
        String petPhoto642 = Base64.encodeToString(bb2, 0);

        //Agregar fotgrafia 3
        BitmapDrawable drawable3 = (BitmapDrawable) imgOwnerPhoto3.getDrawable();
        Bitmap bitmap3 = drawable3.getBitmap();

        ByteArrayOutputStream bos3 = new ByteArrayOutputStream();
        bitmap3.compress(Bitmap.CompressFormat.PNG, 100, bos3);
        byte[] bb3 = bos3.toByteArray();
        String petPhoto643 = Base64.encodeToString(bb3, 0);

        /////


        List<Demo> friendsList = new ArrayList<Demo>();


        String distri=tilFullname.getEditText().getText().toString();
        String provincia=tilSurname.getEditText().getText().toString();

        Demo demo = new Demo();





        if(Conectividad.isOnline(getActivity().getApplicationContext())){

            mAPIService.addFoto(petPhoto64,petPhoto642,petPhoto643,distri,provincia).enqueue(new Callback<Demo>() {
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

        }else{

            System.out.println("no tiene internet");
            Pedido lawyer = new Pedido("",petPhoto64, petPhoto642, petPhoto643, distri,provincia,"1","1","","s","","","");

            System.out.println("no tiene internet2");
            new AddEditLawyerTask().execute(lawyer);
            startAlert(20);
            //activar alarma


        }
       // friendsList.add(fotito);


    }

    private class AddEditLawyerTask extends AsyncTask<Pedido, Void, Boolean> {

        @Override
        protected Boolean doInBackground(Pedido... pedidos) {


            System.out.println("registro de plantas");
                return PedidosDbHelper.saveLawyer(pedidos[0]) > 0;

        }

        @Override
        protected void onPostExecute(Boolean result) {
            showPedidosScreen(result);
        }

    }

    private void showPedidosScreen(Boolean requery) {
        if (!requery) {
            showAddEditError();
            getActivity().setResult(Activity.RESULT_CANCELED);
        } else {
            getActivity().setResult(Activity.RESULT_OK);
        }

        getActivity().finish();
    }
    private void showAddEditError() {
        Toast.makeText(getActivity(),
                "Error al agregar nueva información", Toast.LENGTH_SHORT).show();
    }

    public void startAlert(int i) {


        Intent intent = new Intent(getActivity().getApplicationContext(), MyBroadcastReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(
                getActivity().getApplicationContext(), 234324243, intent, 0);
        AlarmManager alarmManager = (AlarmManager) getActivity().getSystemService(Context.ALARM_SERVICE);
        alarmManager.set(AlarmManager.RTC_WAKEUP, System.currentTimeMillis()
                + (i * 1000), pendingIntent);
        Toast.makeText(getActivity().getApplicationContext(), "Alarm activada " + i + " seconds",Toast.LENGTH_LONG).show();
    }

    public void addFoto1(){


        //Agregar fotografia 1

        BitmapDrawable drawable = (BitmapDrawable) imgOwnerPhoto.getDrawable();
        Bitmap bitmap = drawable.getBitmap();

        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, bos);
        byte[] bb = bos.toByteArray();
        String petPhoto64 = Base64.encodeToString(bb, 0);

        //Agregar fotografia 2
        BitmapDrawable drawable2 = (BitmapDrawable) imgOwnerPhoto2.getDrawable();
        Bitmap bitmap2 = drawable2.getBitmap();

        ByteArrayOutputStream bos2 = new ByteArrayOutputStream();
        bitmap2.compress(Bitmap.CompressFormat.PNG, 100, bos2);
        byte[] bb2 = bos2.toByteArray();
        String petPhoto642 = Base64.encodeToString(bb2, 0);

        //Agregar fotgrafia 3
        BitmapDrawable drawable3 = (BitmapDrawable) imgOwnerPhoto3.getDrawable();
        Bitmap bitmap3 = drawable3.getBitmap();

        ByteArrayOutputStream bos3 = new ByteArrayOutputStream();
        bitmap3.compress(Bitmap.CompressFormat.PNG, 100, bos3);
        byte[] bb3 = bos3.toByteArray();
        String petPhoto643 = Base64.encodeToString(bb3, 0);

        /////


        List<Demo> friendsList = new ArrayList<Demo>();


        String distri=tilFullname.getEditText().getText().toString();
        String provincia=tilSurname.getEditText().getText().toString();

        Demo demo = new Demo();

        demo.setId_foto1(petPhoto64);
        demo.setId_foto2(petPhoto642);
        demo.setId_foto3(petPhoto643);
        demo.setId_provincia(distri);
        demo.setId_distrito(provincia);



        System.out.println("ingreso 1");
        // friendsList.add(fotito);
        mAPIService.addFoto(petPhoto64,petPhoto642,petPhoto643,distri,provincia).enqueue(new Callback<Demo>() {
            @Override
            public void onResponse(Call<Demo> call, Response<Demo> response) {


                if(response.isSuccessful()) {
                    System.out.println("valor de ingreso");

                }else {
                    int statusCode  = response.code();
                    System.out.println("2"+statusCode);
                    // handle request errors depending on status code
                }

            }

            @Override
            public void onFailure(Call<Demo> call, Throwable t) {

            }
        });


    }
    public  void  loadProfile(){
        final List<Demo> itemsLostPets = new ArrayList<>();
        // final List<Pet> itemsPet = new ArrayList<>();
        System.out.println("Demo Demo");
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
/*
                    LostPet = new PetLostAdapter(getActivity(),itemsLostPets);
                    lv.setAdapter(LostPet);*/

                System.out.println("3");
                }////codigo importante



            }

            @Override
            public void onFailure(Call<List<Demo>> call, Throwable t) {
                t.printStackTrace();
            }
        });


    }


    //Codigo de la fotografia
    //Codigo para tomar foto y enviarlo al servidor

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_IMAGE_CAPTURE) {


            if (data != null) {

                c++;
                System.out.println("valor del contador="+c);
                if (c == 1){
                    System.out.println("Primer if="+c);
                Bundle extras = data.getExtras();
                Bitmap imageBitmap = (Bitmap) extras.get("data");
                imgOwnerPhoto.setImageBitmap(imageBitmap);
                }else if (c==2){
                    System.out.println("Segundo if="+c);
                    Bundle extras = data.getExtras();
                    Bitmap imageBitmap = (Bitmap) extras.get("data");
                    imgOwnerPhoto2.setImageBitmap(imageBitmap);

                }else{

                    Bundle extras = data.getExtras();
                    Bitmap imageBitmap = (Bitmap) extras.get("data");
                    imgOwnerPhoto3.setImageBitmap(imageBitmap);
                }
            }
        }

    }



    private void getPhoto() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getContext().getPackageManager()) != null) {
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
        }
    }














    //
        public void actualizardatos(){
            int race =0;
            if (!ValidationUtils.isEmpty(tilFullname, tilSurname)) {
                 /*
                String distrito =spnDistrict.getSelectedItem().toString();


                if(distrito.equals("LINCE")){
                    race=1;
                }else if (distrito.equals("SAN MIGUEL")){
                    race=2;
                }
                else if (distrito.equals("INDEPENDENCIA")){
                    race=3;
                }
                else if (distrito.equals("SURCO")){
                    race=4;
                }
                else if (distrito.equals("SAN ISIDRO")){
                    race=5;
                }
                else if (distrito.equals("LA MOLINA")){
                    race=6;
                }else if (distrito.equals("SAN MARTIN DE PORRES")){
                    race=7;

                }else if (distrito.equals("LOS OLIVOS")){
                    race=8;
                }
                else if (distrito.equals("MIRAFLORES")){
                    race=9;
                }
                else if (distrito.equals("MIRAFLORES")){
                    race=10;
                }
                else if (distrito.equals("SAN MIGUEL")){
                    race=11;
                }
                else if (distrito.equals("COMAS")){
                    race=12;
                }
                */


              //  int sexSelectedId = rbgSex.getCheckedRadioButtonId();
               // RadioButton rbtnSexo = (RadioButton) getActivity().findViewById(sexSelectedId);



                String name = tilFullname.getEditText().getText().toString().trim();
                String lastname = tilSurname.getEditText().getText().toString().trim();
                //String address = tilAddress.getEditText().getText().toString().trim();
               // String sex = rbtnSexo.getText().toString().trim();


                int user1=loginPet.id_user;

                String user= String.valueOf(user1);



                BitmapDrawable drawable = (BitmapDrawable) imgOwnerPhoto.getDrawable();
                Bitmap bitmap = drawable.getBitmap();

                ByteArrayOutputStream bos = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.JPEG, 75, bos);
                byte[] bb = bos.toByteArray();
                String petPhoto64 = Base64.encodeToString(bb, 0);
                int idUser= Integer.parseInt(user);
                Demo usuario = new Demo();
                /*
                usuario.setName(name);
                usuario.setLastname(lastname);
                //usuario.setAddress(address);
                //usuario.setSex(sex);
                usuario.setUserId(idUser);
                usuario.setPicture(petPhoto64);



                usuario.setDistrictId(race);
                */

            mAPIService.ActualizarUsuario(user,usuario).enqueue(new Callback<UserResponse>() {
                @Override
                public void onResponse(Call<UserResponse> call, Response<UserResponse> response) {
                    if(response.isSuccessful()) {

                        Toast.makeText(getContext(), "Se actualizaron los datos ",
                                Toast.LENGTH_LONG).show();
                        Intent in = new Intent(getContext(),MainActivity.class);

                        startActivity(in);


                    }else {
                        int statusCode  = response.code();
                        System.out.println("codigoError"+statusCode);
                        // handle request errors depending on status code
                    }

                }

                @Override
                public void onFailure(Call<UserResponse> call, Throwable t) {

                }
            });
            }

        }
}
