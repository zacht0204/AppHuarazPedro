package com.huaraz.luis.apphuaraz.Adaptador;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.huaraz.luis.apphuaraz.Model.Demo;
import com.huaraz.luis.apphuaraz.Model.Pedido;
import com.huaraz.luis.apphuaraz.R;

import java.util.List;

/**
 * Created by java on 07/12/2016.
 */

public class PedidoAdapter extends ArrayAdapter<Pedido> {

    String name=null;

    public List<Pedido> list;

    public PedidoAdapter(Context context, List<Pedido> objects) {
        super(context, 0, objects);
        list = objects;
    }

    @Override
    public Pedido getItem(int position) {
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
        holder.provincia = (TextView) convertView.findViewById(R.id.provincia);
        holder.distrito = (TextView) convertView.findViewById(R.id.distrito);
        holder.estadoP   = (TextView) convertView.findViewById(R.id.estadoP);
        holder.fechaP    = (TextView) convertView.findViewById(R.id.fechaP);
     //  holder.fecha=(TextView) convertView.findViewById(R.id.fecha);

        convertView.setTag(holder);
        }else{
            holder= (ViewHolder)convertView.getTag();
        }

        // Lead actual.
        Pedido pedido = getItem(position);
        String estadoPedido="Pendiente";
        String  fecha="";

        if(pedido.getEstado()==1){
            estadoPedido="Procesando Pedido";

        }else {
            estadoPedido="Pendiente";
        };
        fecha=pedido.getFecha();
        String[] parts = fecha.split(" ");
        String part = parts[0]; // 004
        String[] part1 = part.split("/");
        fecha=part1[2]+"/"+part1[1]+"/"+part1[0];
        System.out.println("valor de usuario"+fecha);
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
        holder.provincia.setText(pedido.getProvincia());
        holder.distrito.setText(pedido.getDistrito());
        holder.estadoP.setText(estadoPedido);
        holder.fechaP.setText(fecha);
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
        TextView provincia;
        TextView distrito;

        TextView estadoP;
        TextView fechaP;


    }
}
