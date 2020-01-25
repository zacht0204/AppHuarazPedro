package com.huaraz.luis.apphuaraz.Adaptador;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.huaraz.luis.apphuaraz.Model.Demo;
import com.huaraz.luis.apphuaraz.R;

import java.util.List;

/**
 * Created by java on 07/12/2016.
 */

public class DemoAdapter extends ArrayAdapter<Demo> {

    String name=null;

    public List<Demo> list;

    public DemoAdapter(Context context, List<Demo> objects) {
        super(context, 0, objects);
        list = objects;
    }

    @Override
    public Demo getItem(int position) {
        return list.get(position);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Obtener inflater.
        LayoutInflater inflater = (LayoutInflater) getContext()
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        ViewHolder holder;

        // ¿Existe el view actual?
        if (null == convertView) {
            convertView = inflater.inflate(
                    R.layout.lista_demo,
                    parent,
                    false);


        holder = new ViewHolder();
          //  holder.photo = (ImageView) convertView.findViewById(R.id.petPhoto);
        holder.name_pet = (TextView) convertView.findViewById(R.id.petName);
        holder.info_pet = (TextView) convertView.findViewById(R.id.petInfo);
     //  holder.fecha=(TextView) convertView.findViewById(R.id.fecha);

        convertView.setTag(holder);
        }else{
            holder= (ViewHolder)convertView.getTag();
        }

        // Lead actual.
        Demo noti = getItem(position);
      //  name = noti.getId_foto1();

        // Configuracion
  /*
        String foto=noti.getId_foto1();

        if(foto!=null){
        // Receiving side
        byte[] data1 = Base64.decode(foto, Base64.DEFAULT);
        String text1 = null;
        try {
            Bitmap bitmap = BitmapFactory.decodeByteArray(data1, 0, data1.length);
            holder.photo.setImageBitmap(bitmap);
        } catch (Exception e) {
            e.printStackTrace();
        }

        }
        */
/*
        Picasso.with(getContext())
                .load("data:image/png;base64,"+noti.getId_foto1())
                .resize(120, 120)
                .into( holder.photo);
 */
        holder.name_pet.setText(noti.getId_distrito());
        holder.info_pet.setText(noti.getId_provincia());
        //holder.fecha.setText(noti.getFecha());

     /*
        holder.sal_disp.setText(cuenta.getSaldo());
        holder.fecha_aper.setText(cuenta.getFechaApertura());
        holder.fecha_ult.setText(cuenta.getFechaUltimoMovimiento());
        holder.est_cuenta.setText(cuenta.getEstado());*/


        return convertView;
    }

    static class ViewHolder {

       // ImageView photo;
        TextView name_pet;
        TextView info_pet;


    }
}
