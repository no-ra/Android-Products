package com.example.nora.firstproject;

import android.content.ClipData;
import android.content.Context;
import android.content.Intent;
import android.location.GpsStatus;
import android.net.sip.SipSession;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by NORA on 1/9/2016.
 */
public class RecyclerViewHolder extends RecyclerView.ViewHolder {
    TextView prod_em, prod_kd, prod_cm;
    ImageView im;
    CardView mCard;

    public RecyclerViewHolder(View itemView) {
        super(itemView);
        prod_em = (TextView) itemView.findViewById(R.id.test_produkti);
        prod_kd = (TextView) itemView.findViewById(R.id.test_kodi);
        prod_cm = (TextView) itemView.findViewById(R.id.test_cmimi);
        im = (ImageView) itemView.findViewById(R.id.imageView);
        mCard=(CardView) itemView.findViewById(R.id.card_view);
    }
}