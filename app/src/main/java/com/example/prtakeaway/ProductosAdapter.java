package com.example.prtakeaway;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class ProductosAdapter extends RecyclerView.Adapter<ProductosAdapter.ProductoViewHolder>  {

    private List<Productos.Producto> productos;
    private List<ProductoEnCarrito> carrito;
    private Context context;

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

        Log.d("habilitado",producto.getNombreProducto()+" "+producto.getHabilitado());

        // Verifica si el producto está habilitado
        if (producto.getHabilitado() == 1) {
            // El producto está habilitado, configura las vistas del elemento de lista con los datos del producto
            Picasso.get().load(producto.getImatge()).into(holder.imagenProducto);
            holder.nombreProducto.setText(producto.getNombreProducto());
            holder.precioProducto.setText(String.valueOf(producto.getPrecioUnitario()) + "€");
            holder.descripcionProducto.setText(producto.getDescripcion());
            holder.btnAfegir.setEnabled(true); // Habilita el botón
            holder.btnAfegir.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ((ProductesActivity) v.getContext()).afegirProducte(producto);
                    for (ProductoEnCarrito product : carrito) {
                        int cantidad = product.getCantidad();
                        String prueba = product.getNombre().toString() + " " + cantidad;
                        Log.d("carrito", prueba);
                    }
                }
            });
        } else {
            // El producto no está habilitado, realiza alguna acción, como deshabilitar el botón o cambiar el color de fondo
            holder.btnAfegir.setEnabled(false); // Deshabilita el botón
            holder.nombreProducto.setText(producto.getNombreProducto()+" no disponible");
            holder.precioProducto.setText("");
            holder.descripcionProducto.setText("");
            }
    }

    @Override
    public int getItemCount() {
        return productos.size();
    }

    public static class ProductoViewHolder extends RecyclerView.ViewHolder {
        //ImageView imagenProducto;
        TextView nombreProducto, precioProducto, descripcionProducto;
        Button btnAfegir;
        ImageView imagenProducto;

        public ProductoViewHolder(View itemView) {
            super(itemView);
            //imagenProducto = itemView.findViewById(R.id.imagenProducto);
            nombreProducto = itemView.findViewById(R.id.nombreProducto);
            precioProducto = itemView.findViewById(R.id.precioProducto);
            btnAfegir = itemView.findViewById(R.id.btnAfegir);
            descripcionProducto = itemView.findViewById(R.id.descripcionProducto);
            imagenProducto = itemView.findViewById(R.id.imagenProducto);
        }
    }

    public List<ProductoEnCarrito> obtenerProductos(){
        return carrito;
    }

    public void filtrarProductosPorPrecio(String categoria) {
        List<Productos.Producto> productosFiltrados = new ArrayList<>();


        for(Productos.Producto producto : productos){
            if(!producto.getCategoria().isEmpty()){
                if(producto.getCategoria().equals(categoria)){
                    productosFiltrados.add(producto);
                }
            }

        }

        actualizarProductos(productosFiltrados);
    }

}
