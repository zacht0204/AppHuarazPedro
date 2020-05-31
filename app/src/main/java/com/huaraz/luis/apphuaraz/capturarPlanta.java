package com.huaraz.luis.apphuaraz;

import android.Manifest;
import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.IntentSender;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.location.Location;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.DrawableRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.ActivityRecognition;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResult;
import com.google.android.gms.location.LocationSettingsStatusCodes;
import com.huaraz.luis.apphuaraz.Model.Districts;
import com.huaraz.luis.apphuaraz.Model.Demo;
import com.huaraz.luis.apphuaraz.Model.Pedido;
import com.huaraz.luis.apphuaraz.Model.UserResponse;
import com.huaraz.luis.apphuaraz.Model.Usuario;
import com.huaraz.luis.apphuaraz.Servicio.APIService;
import com.huaraz.luis.apphuaraz.Servicio.ApiUtils;
import com.huaraz.luis.apphuaraz.Servicio.Conectividad;
import com.huaraz.luis.apphuaraz.Servicio.ValidationUtils;
import com.huaraz.luis.apphuaraz.Sql.PedidosDbHelper;

import com.huaraz.luis.apphuaraz.alarm.MyBroadcastReceiver;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

//import com.pet.ebert.findme.Adaptador.ProfileApapter;

public class capturarPlanta extends Fragment   implements
        GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener,
        LocationListener, ResultCallback<Status> {

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
    String dateString="";

    int c=0;
    private NavigationView navView;


    List<Districts> itemsDistritos = new ArrayList<>();

    ListView lv;
    int usuario;
    //Varibles de GSP
    private static final String TAG = capturarPlanta.class.getSimpleName();

    private static final String LOCATION_KEY = "location-key";
    private static final String ACTIVITY_KEY = "activity-key";
    // Location API
    private GoogleApiClient mGoogleApiClient;
    private LocationRequest mLocationRequest;
    private LocationSettingsRequest mLocationSettingsRequest;
    private Location mLastLocation;

    //Variable global

    String latitud="";
    String longitud="";

    // Activity Recognition API
   // private ActivityDetectionBroadcastReceiver mBroadcastReceiver;



    // UI
    private TextView mLatitude;
    private TextView mLongitude;
    private ImageView mDectectedActivityIcon;

    // Códigos de petición
    public static final int REQUEST_LOCATION = 1;
    public static final int REQUEST_CHECK_SETTINGS = 2;



    ///***//



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
        final View root = inflater.inflate(R.layout.fragment_info, container, false);
        usuario=Integer.parseInt(Global.usuario);
        System.out.println("Global de usuario de capturar planta"+usuario);
        long date = System.currentTimeMillis();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/d HH:mm:ss ");
         dateString = sdf.format(date);

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

        mAPIService = ApiUtils.getAPIService();


        //Metodos para llamar al gps
        // Referencias UI



        // Establecer punto de entrada para la API de ubicación
        buildGoogleApiClient();

        // Crear configuración de peticiones
        createLocationRequest();

        // Crear opciones de peticiones
        buildLocationSettingsRequest();

        // Verificar ajustes de ubicación actuales
        checkLocationSettings();

      //  mBroadcastReceiver = new ActivityDetectionBroadcastReceiver();

        updateValuesFromBundle(savedInstanceState);





        //Metodos para llamar al gps
        boton_registrar_planta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (!ValidationUtils.isEmpty(tilFullname, tilSurname)) {
                    addFoto();

                    Toast toast = new Toast(getContext());

                    LayoutInflater inflater = getLayoutInflater();
                    View layout = inflater.inflate(R.layout.toast_layout, (ViewGroup) root.findViewById((R.id.lytLayout)));

                    TextView txtMsg = (TextView)layout.findViewById(R.id.txtMensaje);
                    txtMsg.setText("¡Se registro su pedido ☻! Un tecnico se comunicara lo mas pronto posible!");

                    toast.setDuration(Toast.LENGTH_LONG);
                    toast.setView(layout);
                    toast.show();

                    Intent i = new Intent(getContext(), MainActivity.class);
                    startActivity(i);

                }else{
                    Toast.makeText(getActivity(),"Ingrese la informacion faltante",Toast.LENGTH_SHORT).show();
                }

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

        List<Demo> friendsList = new ArrayList<Demo>();

        String distri=tilFullname.getEditText().getText().toString();
        String provincia=tilSurname.getEditText().getText().toString();

        Demo demo = new Demo();


        if(isOnlineNet()){

            //Metodo Actualizado
            mAPIService.addPedido(petPhoto64,petPhoto642,petPhoto643,distri,provincia,usuario,0,dateString,1,latitud,longitud,"1").enqueue(new Callback<Pedido>() {
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


        }else{


                System.out.println("global sin conexion");
                com.huaraz.luis.apphuaraz.Sql.Pedido lawyer = new com.huaraz.luis.apphuaraz.Sql.Pedido("",petPhoto64, petPhoto642, petPhoto643, distri,provincia,Integer.parseInt(Global.IdDni),0,dateString,3,latitud,longitud,"3");
                System.out.println("fecha sin conexion"+dateString);
                new AddEditLawyerTask().execute(lawyer);
                startAlert(25);

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

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        // Obtenemos la última ubicación al ser la primera vez
        processLastLocation();
        // Iniciamos las actualizaciones de ubicación
        startLocationUpdates();
        // Y también las de reconocimiento de actividad
     //   startActivityUpdates();

    }

    @Override
    public void onConnectionSuspended(int i) {
        Log.d(TAG, "Conexión suspendida");
        mGoogleApiClient.connect();

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        Toast.makeText(
                getActivity(),
                "Error de conexión con el código:" + connectionResult.getErrorCode(),
                Toast.LENGTH_LONG)
                .show();

    }

    @Override
    public void onResult(@NonNull Status status) {
        if (status.isSuccess()) {
            Log.d(TAG, "Detección de actividad iniciada");

        } else {
            Log.e(TAG, "Error al iniciar/remover la detección de actividad: "
                    + status.getStatusMessage());
        }

    }

    @Override
    public void onLocationChanged(Location location) {
        Log.d(TAG, String.format("Nueva ubicación: (%s, %s)",
                location.getLatitude(), location.getLongitude()));
        latitud= String.valueOf(location.getLatitude());
        longitud=String.valueOf(location.getLongitude());
        System.out.println("Impresion de cordenadas"+latitud+longitud);
        mLastLocation = location;
        updateLocationUI();
    }




    private class AddEditLawyerTask extends AsyncTask<com.huaraz.luis.apphuaraz.Sql.Pedido, Void, Boolean> {

        @Override
        protected Boolean doInBackground(com.huaraz.luis.apphuaraz.Sql.Pedido... pedidos) {


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


        int interval = 1000*60*60*2; //cada dos horas
       // int interval = 1000*25;

        Intent intent = new Intent(getActivity().getApplicationContext(), MyBroadcastReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(
                getActivity().getApplicationContext(), 234324243, intent, 0);
        AlarmManager alarmManager = (AlarmManager) getActivity().getSystemService(Context.ALARM_SERVICE);

        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, System.currentTimeMillis() ,interval, pendingIntent);
      //  Toast.makeText(getActivity().getApplicationContext(), "Alarm activada " + i + " seconds",Toast.LENGTH_LONG).show();
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
        }else{

            switch (requestCode) {
                case REQUEST_CHECK_SETTINGS:
                    switch (resultCode) {
                        case Activity.RESULT_OK:
                            Log.d(TAG, "El usuario permitió el cambio de ajustes de ubicación.");
                            processLastLocation();
                            startLocationUpdates();
                            break;
                        case Activity.RESULT_CANCELED:
                            Log.d(TAG, "El usuario no permitió el cambio de ajustes de ubicación");
                            break;
                    }
                    break;
            }


        }

    }



    private void getPhoto() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getContext().getPackageManager()) != null) {
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
        }
    }

    //Codigo de inicio del gps
    @Override
    public  void onPause() {
        super.onPause();
        if (mGoogleApiClient.isConnected()) {
            stopLocationUpdates();
         //   stopActivityUpdates();
        }

   //     LocalBroadcastManager.getInstance(getActivity()).unregisterReceiver(mBroadcastReceiver);
    }

    @Override
    public void onResume() {
        super.onResume();
        if (mGoogleApiClient.isConnected()) {
            startLocationUpdates();
         //   startActivityUpdates();
        }



    }
    //Codigo de inicio del gps

    @Override
    public void onSaveInstanceState(Bundle outState) {
        // Protegemos la ubicación actual antes del cambio de configuración
        outState.putParcelable(LOCATION_KEY, mLastLocation);
      //  outState.putInt(ACTIVITY_KEY, mImageResource);
        super.onSaveInstanceState(outState);
    }




    //Metodos para aplicar el gps de configuracion
    private synchronized void buildGoogleApiClient() {
        mGoogleApiClient = new GoogleApiClient.Builder(getActivity())
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .addApi(ActivityRecognition.API)
                .enableAutoManage(getActivity(), this)
                .build();
    }


    private void createLocationRequest() {
        mLocationRequest = new LocationRequest()
                .setInterval(1000)
                .setFastestInterval(1000/2)
                .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
    }


    private void buildLocationSettingsRequest() {
        LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder();
        builder.addLocationRequest(mLocationRequest)
                .setAlwaysShow(true);
        mLocationSettingsRequest = builder.build();
    }


    private void checkLocationSettings() {
        PendingResult<LocationSettingsResult> result =
                LocationServices.SettingsApi.checkLocationSettings(
                        mGoogleApiClient, mLocationSettingsRequest
                );

        result.setResultCallback(new ResultCallback<LocationSettingsResult>() {
            @Override
            public void onResult(@NonNull LocationSettingsResult result) {
                Status status = result.getStatus();

                switch (status.getStatusCode()) {
                    case LocationSettingsStatusCodes.SUCCESS:
                        Log.d(TAG, "Los ajustes de ubicación satisfacen la configuración.");
                        startLocationUpdates();
                        break;
                    case LocationSettingsStatusCodes.RESOLUTION_REQUIRED:
                        try {
                            Log.d(TAG, "Los ajustes de ubicación no satisfacen la configuración. " +
                                    "Se mostrará un diálogo de ayuda.");
                            status.startResolutionForResult(
                                    getActivity(),
                                    REQUEST_CHECK_SETTINGS);
                        } catch (IntentSender.SendIntentException e) {
                            Log.d(TAG, "El Intent del diálogo no funcionó.");
                            // Sin operaciones
                        }
                        break;
                    case LocationSettingsStatusCodes.SETTINGS_CHANGE_UNAVAILABLE:
                        Log.d(TAG, "Los ajustes de ubicación no son apropiados.");
                        break;

                }
            }
        });
    }

    private void startLocationUpdates() {
        if (isLocationPermissionGranted()) {
            if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(getActivity(), "\n" +
                        "Primero habilite el ACCESO A LA UBICACIÓN en la configuración.", Toast.LENGTH_LONG).show();
                return;
            }
            LocationServices.FusedLocationApi.requestLocationUpdates(
                    mGoogleApiClient, mLocationRequest, this);
        } else {
            manageDeniedPermission();
        }
    }

    private boolean isLocationPermissionGranted() {
        int permission = ActivityCompat.checkSelfPermission(
                getActivity(),
                Manifest.permission.ACCESS_FINE_LOCATION);
        return permission == PackageManager.PERMISSION_GRANTED;
    }
    private void manageDeniedPermission() {
        if (ActivityCompat.shouldShowRequestPermissionRationale(getActivity(),
                Manifest.permission.ACCESS_FINE_LOCATION)) {
            // Aquí muestras confirmación explicativa al usuario
            // por si rechazó los permisos anteriormente
        } else {
            ActivityCompat.requestPermissions(
                    getActivity(), new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    REQUEST_LOCATION);
        }
    }

    private void updateValuesFromBundle(Bundle savedInstanceState) {
        if (savedInstanceState != null) {
            if (savedInstanceState.containsKey(LOCATION_KEY)) {
                mLastLocation = savedInstanceState.getParcelable(LOCATION_KEY);

                updateLocationUI();
            }

        }
    }

    private void updateLocationUI() {
        String latitud =(String.valueOf(mLastLocation.getLatitude()));
        String longitud=(String.valueOf(mLastLocation.getLongitude()));
    }

    private void processLastLocation() {
        getLastLocation();
        if (mLastLocation != null) {
            updateLocationUI();
        }
    }
    private void getLastLocation() {
        if (isLocationPermissionGranted()) {
            if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(getActivity(), "\n" +
                        "Primero habilite el ACCESO A LA UBICACIÓN en la configuración.", Toast.LENGTH_LONG).show();
                return;
            }
            mLastLocation = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
        } else {
            manageDeniedPermission();
        }
    }


    private void stopLocationUpdates() {
        LocationServices.FusedLocationApi
                .removeLocationUpdates(mGoogleApiClient, this);
    }


}
