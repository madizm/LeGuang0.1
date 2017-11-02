package com.jlu.madizm.leguang.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.jlu.madizm.leguang.R;
import com.jlu.madizm.leguang.utils.ToastUtil;

/**
 * Created by Administrator on 2017/11/1.
 */

public class HomeSearchBar extends RelativeLayout {
    private EditText mSearchText;
    private Button mBtnCart;
    private Button mBtnMsg;
    public HomeSearchBar(Context context) {
        super(context);
        initView(context);
    }

    public HomeSearchBar(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(context);
    }

    public HomeSearchBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context);
    }

    public HomeSearchBar(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        initView(context);
    }

    private void initView(final Context context){
        View view = LayoutInflater.from(context).inflate(R.layout.item_home_type_search, this, true);
        mSearchText = view.findViewById(R.id.edText_search);
        mBtnCart = view.findViewById(R.id.cart);
        mBtnMsg = view.findViewById(R.id.msg);

//        mSearchText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
//            @Override
//            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
//                Log.e("Search","Clicked");
//                ToastUtil.showShortToast(context, v.getText().toString());
//                return false;
//            }
//        });

    }

    public void setBtnCartListener(OnClickListener listener){
        mBtnCart.setOnClickListener(listener);
    }

    public void setBtnMsgListener(OnClickListener listener){
        mBtnMsg.setOnClickListener(listener);
    }
}
