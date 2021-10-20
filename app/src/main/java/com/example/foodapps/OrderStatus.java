package com.example.foodapps;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.foodapps.Common.Common;
import com.example.foodapps.Interface.ItemClickListener;
import com.example.foodapps.Model.Request;
import com.example.foodapps.ViewHolder.OrderViewHolder;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class OrderStatus extends AppCompatActivity {

    public RecyclerView mRecyclerView;
    public RecyclerView.LayoutManager mLayoutManager;

    FirebaseDatabase mDatabase;
    DatabaseReference request;
    FirebaseRecyclerAdapter<Request, OrderViewHolder> mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_status);

        init();
        order();

    }

    private void order() {
        if (getIntent() == null)
            loadOrders(Common.currentUser.getPhone());
        else
            loadOrders(getIntent().getStringExtra("userPhone"));

        loadOrders(Common.currentUser.getPhone());
    }

    private void init(){
        mDatabase = FirebaseDatabase.getInstance();
        request = mDatabase.getReference();
        mRecyclerView = findViewById(R.id.listOrders);
        mLayoutManager = new LinearLayoutManager(this);
    }

    private void loadOrders(String phone) {

        FirebaseRecyclerOptions<Request> options = new FirebaseRecyclerOptions.Builder<Request>().setQuery(request.orderByChild("phone").equalTo(phone), Request.class).build();


        mAdapter = new FirebaseRecyclerAdapter<Request, OrderViewHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull OrderViewHolder orderViewHolder, int i, @NonNull Request request) {
                orderViewHolder.txtOrderId.setText(mAdapter.getRef(i).getKey());
                orderViewHolder.txtOrderStatus.setText(Common.convertCodeToStatus(request.getStatus()));
                orderViewHolder.txtOrderAddress.setText(request.getAddress());
                orderViewHolder.txtOrderPhone.setText(request.getPhone());
                orderViewHolder.setItemClickListener(new ItemClickListener() {
                    @Override
                    public void onClick(View view, int position, boolean isLongClick) {

                    }
                });

            }

            @NonNull
            @Override
            public OrderViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.order_layout, parent, false);
                return new OrderViewHolder(view);
            }
        };
        //set adapter
        mAdapter.startListening();
        mAdapter.notifyDataSetChanged();
        mRecyclerView.setAdapter(mAdapter);

    }

}