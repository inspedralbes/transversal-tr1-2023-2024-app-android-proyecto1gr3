package com.example.prtakeaway;

import android.annotation.SuppressLint;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class CarritoAdapter extends RecyclerView.Adapter<CarritoAdapter.CarritoViewHolder> {

    private List<ProductoEnCarrito> carrito;

    public CarritoAdapter(List<ProductoEnCarrito> productosEnCarrito){
        this.carrito = productosEnCarrito;
    }

    public List<ProductoEnCarrito> getCarrito() {
        return carrito;
    }

    @NonNull
    @Override
    public CarritoAdapter.CarritoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_carrito, parent, false);
        return new CarritoViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CarritoAdapter.CarritoViewHolder holder, @SuppressLint("RecyclerView") int position) {
        ProductoEnCarrito producto = carrito.get(position);

        // Configura las vistas del elemento de lista con los datos del producto en el carrito.
        holder.nombreProducto.setText(producto.getNombre());
        //Log.d("pruebaAdapter:",producto.getNombre());
        holder.precioProducto.setText(String.valueOf(producto.getPrecio()));
        //Log.d("pruebaAdapter:",String.valueOf(producto.getPrecio()));
        holder.cantidadProducto.setText("Cantidad: " + String.valueOf(producto.getCantidad()));
        //Log.d("pruebaAdapter:",String.valueOf(producto.getCantidad()));
        holder.descripcion.setText(producto.getDescripcion());
        holder.btnEliminar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((CarritoActivity) v.getContext()).eliminarProducto(position);
            }
        });

    }

    public void eliminarProducto(int position) {
        carrito.remove(position);
        notifyDataSetChanged();
    }
    public double calcularPrecioTotal(){
        double precioTotal = 0.0;


        for(ProductoEnCarrito producto : carrito){
            precioTotal += producto.getPrecio() * producto.getCantidad();
        }
        return precioTotal;
    }

    @Override
    public int getItemCount() {
        return carrito.size();
    }

    public static class CarritoViewHolder extends RecyclerView.ViewHolder {
        TextView nombreProducto, precioProducto, cantidadProducto, descripcion;
        Button btnEliminar;
        TextView tvPrecio;


        public CarritoViewHolder(View itemView) {
            super(itemView);
            nombreProducto = itemView.findViewById(R.id.productoCarritoNombre);
            precioProducto = itemView.findViewById(R.id.productoCarritoPrecio);
            cantidadProducto = itemView.findViewById(R.id.productoCarritoCantidad);
            btnEliminar = itemView.findViewById(R.id.btnEliminar);
            descripcion = itemView.findViewById(R.id.descripcionCarrito);
        }
    }

}
