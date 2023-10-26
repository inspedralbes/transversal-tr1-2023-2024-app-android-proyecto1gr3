package com.example.prtakeaway;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class CarritoAdapter extends RecyclerView.Adapter<CarritoAdapter.CarritoViewHolder> {

    private List<ProductoEnCarrito> carrito;

    public CarritoAdapter(List<ProductoEnCarrito> productosEnCarrito){
        this.carrito = productosEnCarrito;
    }
    @NonNull
    @Override
    public CarritoAdapter.CarritoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_carrito, parent, false);
        return new CarritoViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CarritoAdapter.CarritoViewHolder holder, int position) {
        ProductoEnCarrito producto = carrito.get(position);

        // Configura las vistas del elemento de lista con los datos del producto en el carrito.
        holder.nombreProducto.setText(producto.getNombre());
        Log.d("pruebaAdapter:",producto.getNombre());
        holder.precioProducto.setText(String.valueOf(producto.getPrecio()));
        Log.d("pruebaAdapter:",String.valueOf(producto.getPrecio()));
        holder.cantidadProducto.setText("Cantidad: " + String.valueOf(producto.getCantidad()));
        Log.d("pruebaAdapter:",String.valueOf(producto.getCantidad()));

    }

    @Override
    public int getItemCount() {
        return carrito.size();
    }

    public static class CarritoViewHolder extends RecyclerView.ViewHolder {
        TextView nombreProducto, precioProducto, cantidadProducto;

        public CarritoViewHolder(View itemView) {
            super(itemView);
            nombreProducto = itemView.findViewById(R.id.productoCarritoNombre);
            precioProducto = itemView.findViewById(R.id.productoCarritoPrecio);
            cantidadProducto = itemView.findViewById(R.id.productoCarritoCantidad);
        }
    }
}
