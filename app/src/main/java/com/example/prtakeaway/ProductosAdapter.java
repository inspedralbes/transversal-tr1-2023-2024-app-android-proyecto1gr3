package com.example.prtakeaway;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class ProductosAdapter extends RecyclerView.Adapter<ProductosAdapter.ProductoViewHolder>  {

    private List<Productos.Producto> productos;

    public ProductosAdapter(List<Productos.Producto> productos){
        this.productos = productos;
    }
    // Método para actualizar la lista de productos
    public void actualizarProductos(List<Productos.Producto> nuevosProductos) {
        productos.clear(); // Limpia la lista actual
        productos.addAll(nuevosProductos); // Agrega los nuevos productos
        notifyDataSetChanged(); // Notifica al adaptador que los datos han cambiado
    }

    @NonNull
    @Override
    public ProductoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view =     LayoutInflater.from(parent.getContext()).inflate(R.layout.item_producto, parent, false);
        return new ProductoViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductoViewHolder holder, int position) {
        Productos.Producto producto = productos.get(position);

        // Configura las vistas del elemento de lista con los datos del producto
        // Aquí deberías cargar la imagen desde la URL si es necesario.
        //holder.imagenProducto.setImageResource(R.drawable.imagen_placeholder);
        holder.nombreProducto.setText(producto.getNombreProducto());
        holder.precioProducto.setText(String.valueOf(producto.getPrecioUnitario()));
    }

    @Override
    public int getItemCount() {
        return productos.size();
    }

    public static class ProductoViewHolder extends RecyclerView.ViewHolder {
        //ImageView imagenProducto;
        TextView nombreProducto, precioProducto;

        public ProductoViewHolder(View itemView) {
            super(itemView);
            //imagenProducto = itemView.findViewById(R.id.imagenProducto);
            nombreProducto = itemView.findViewById(R.id.nombreProducto);
            precioProducto = itemView.findViewById(R.id.precioProducto);
        }
    }
}
