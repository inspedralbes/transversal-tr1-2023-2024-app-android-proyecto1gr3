package com.example.prtakeaway;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class ProductosAdapter extends RecyclerView.Adapter<ProductosAdapter.ProductoViewHolder>  {

    private List<Productos.Producto> productos;
    private List<ProductoEnCarrito> carrito;

    public ProductosAdapter(List<Productos.Producto> productos, List<ProductoEnCarrito> carrito){

        this.productos = productos;
        this.carrito = carrito;
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
        holder.precioProducto.setText(String.valueOf(producto.getPrecioUnitario())+"€");
        holder.descripcionProducto.setText(producto.getDescripcion());
        holder.btnAfegir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((ProductesActivity) v.getContext()).afegirProducte(producto);
                for(ProductoEnCarrito product : carrito){

                    int cantidad = product.getCantidad();
                    String prueba = product.getNombre().toString()+" "+cantidad;
                    Log.d("carrito", prueba);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return productos.size();
    }

    public static class ProductoViewHolder extends RecyclerView.ViewHolder {
        //ImageView imagenProducto;
        TextView nombreProducto, precioProducto, descripcionProducto;
        Button btnAfegir;

        public ProductoViewHolder(View itemView) {
            super(itemView);
            //imagenProducto = itemView.findViewById(R.id.imagenProducto);
            nombreProducto = itemView.findViewById(R.id.nombreProducto);
            precioProducto = itemView.findViewById(R.id.precioProducto);
            btnAfegir = itemView.findViewById(R.id.btnAfegir);
            descripcionProducto = itemView.findViewById(R.id.descripcionProducto);
        }
    }

    public List<ProductoEnCarrito> obtenerProductos(){
        return carrito;
    }

    public void filtrarProductosPorPrecio(double precioMaximo) {
        List<Productos.Producto> productosFiltrados = new ArrayList<>();

        for (Productos.Producto producto : productos) {
            if (producto.getPrecioUnitario() < precioMaximo) {
                productosFiltrados.add(producto);
            }
        }

        actualizarProductos(productosFiltrados);
    }

}
