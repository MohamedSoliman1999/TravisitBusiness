package com.travisit.travisitbusiness.vvm.adapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;
import java.util.ArrayList;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.travisit.travisitbusiness.R;
import com.travisit.travisitbusiness.model.PaymentItem;

public class PaymentAdapter extends RecyclerView.Adapter<PaymentAdapter.PaymentHolder> {
    List<PaymentItem> arrayList = new ArrayList<>();
    private Context mContext;

    public PaymentAdapter(List<PaymentItem> arrayList, Context mContext) {
        this.arrayList = arrayList;
        this.mContext = mContext;
    }

    public void setList(List<PaymentItem> arrayList) {
        this.arrayList = arrayList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public PaymentHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //Display item cardView in parent layout in Recycle View
        View view;
        LayoutInflater mInflater = LayoutInflater.from(mContext);
        view = mInflater.inflate(R.layout.payment_item, parent, false);
        return new PaymentHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull final PaymentHolder holder, final int position) {
        //holder.textView.setText(arrayList.get(position).textLine);
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class PaymentHolder extends RecyclerView.ViewHolder {
        //TextView textView;
        public PaymentHolder(@NonNull final View itemView) {
            super(itemView);
            /*textView=itemView.findViewById(R.id.textLine_txt);
            textView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(itemView.getContext(),arrayList.get(getAdapterPosition()).textLine+"",Toast.LENGTH_LONG).show();
                }
            });*/
        }
    }

    public PaymentItem getWordItemAt(int id) {
        return arrayList.get(id);
    }
}
