package com.example.prtakeaway;

import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.ViewHolder> {

    public interface OnPedidoItemClickListener {
        void onPedidoItemClick(Pedidos.Pedido pedido);
    }
    private OnPedidoItemClickListener listener;

    private List<Pedidos.Pedido> localDataSet;

    /**
     * Provide a reference to the type of views that you are using
     * (custom ViewHolder).
     */
    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView textView, textView2, textView3;



        public ViewHolder(View view) {
            super(view);
            // Define click listener for the ViewHolder's View
            textView = view.findViewById(R.id.textView);
            textView3 = view.findViewById(R.id.editText);
            textView2 = view.findViewById(R.id.textView2);

        }

        public TextView getTextView() {
            return textView;
        }
        public TextView getTextView2() {
            return textView2;
        }
        public TextView getEditView() {
            return textView3;
        }
        public void bind(final Pedidos.Pedido item, final OnPedidoItemClickListener listener) {
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override public void onClick(View v) {
                    Log.d("Click", "Elemento de lista clickeado para el producto: " + itemView.getId());
                    listener.onPedidoItemClick(item);
                }
            });
        }
    }

    /**
     * Initialize the dataset of the Adapter.
     *
     * @param dataSet String[] containing the data to populate views to be used
     * by RecyclerView.
     */
    private int idUsuario;
    public CustomAdapter(List<Pedidos.Pedido> dataSet, int id, OnPedidoItemClickListener listener) {
        localDataSet = dataSet;
        this.idUsuario = id;
        this.listener = listener;
    }
    List<Pedidos.Pedido> pedidosUsuario = new ArrayList<>();

    // Create new views (invoked by the layout manager)
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        // Create a new view, which defines the UI of the list item

        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.text_row_item, viewGroup, false);

        return new ViewHolder(view);
    }
    public void actualizarPedidos(List<Pedidos.Pedido> nuevopedido) {
        localDataSet.clear(); // Limpia la lista actual
        localDataSet.addAll(nuevopedido); // Agrega los nuevos productos
        notifyDataSetChanged(); // Notifica al adaptador que los datos han cambiado
    }
    public void filtrarPedidosPorUsuario() {
        for (Pedidos.Pedido pedido : localDataSet) {
            if (pedido.getIDCliente() == idUsuario) {
                pedidosUsuario.add(pedido);
            }
        }
        notifyDataSetChanged();
    }
    private void setColorByEstado(TextView textView, String estado) {
        int colorResId;
        switch (estado) {
            case "Pendent":
                colorResId = R.color.colorPendiente;
                break;
            case "Tancades":
                colorResId = R.color.colorCerrado;
                break;
            case "Rebutjades":
                colorResId = R.color.colorRechazado;
                break;
            case "En Preparacio":
                colorResId = R.color.colorEnProceso;
                break;

            case "Entregades":
                colorResId = R.color.colorEntregades;
                break;
            default:
                colorResId = android.R.color.black; // Color predeterminado
                break;
        }
        int color = ContextCompat.getColor(textView.getContext(), colorResId);
        textView.setTextColor(color);
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder viewHolder, final int position) {
        Pedidos.Pedido pedido = localDataSet.get(position);
        viewHolder.getTextView().setText(String.valueOf(pedido.getIDPedido()) + " ");
        viewHolder.getTextView2().setText(pedido.getEstado());
        viewHolder.getTextView2().setTypeface(null, Typeface.BOLD_ITALIC);
        viewHolder.getEditView().setText(pedido.getComentario());

        setColorByEstado(viewHolder.getTextView2(), pedido.getEstado());
        viewHolder.bind(localDataSet.get(position), listener);

    }


    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return localDataSet.size();
    }
}
