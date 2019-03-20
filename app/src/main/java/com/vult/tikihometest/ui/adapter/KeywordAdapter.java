package com.vult.tikihometest.ui.adapter;

import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.vult.tikihometest.R;
import com.vult.tikihometest.utils.Utils;

import java.util.List;
import java.util.Random;

public class KeywordAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<String> mKeywordList;

    public void setKeywordModelList(List<String> keywordList) {
        mKeywordList = keywordList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());

        View view = layoutInflater.inflate(viewType, parent, false);

        return new KeywordAdapter.KeywordHolder(view);
    }

    @Override
    public int getItemViewType(int position) {
        return R.layout.activity_main_keyword_item;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        String keyword = mKeywordList.get(position);

        KeywordHolder keywordHolder = (KeywordHolder) holder;
        keywordHolder.bindKeyword(keyword);
    }

    @Override
    public int getItemCount() {
        if (mKeywordList != null)
            return mKeywordList.size();
        return 0;
    }

    private class KeywordHolder extends RecyclerView.ViewHolder {

        private TextView mValueTextView;

        private KeywordHolder(View itemView) {
            super(itemView);
            mValueTextView = itemView.findViewById(R.id.value_text_view);
        }

        private void bindKeyword(String keyword) {

            mValueTextView.setText(Utils.convertToTwoLinesValue(keyword));

            Random rnd = new Random();
            int color = Color.argb(255, rnd.nextInt(256), rnd.nextInt(256), rnd.nextInt(256));
            GradientDrawable shape = new GradientDrawable();
            shape.setCornerRadius(6);
            shape.setColor(color);
            itemView.setBackground(shape);
        }
    }
}