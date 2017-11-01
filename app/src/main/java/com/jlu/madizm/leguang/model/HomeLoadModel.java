package com.jlu.madizm.leguang.model;

import android.content.Context;

import com.jlu.madizm.leguang.model.entity.HomeBottom;
import com.jlu.madizm.leguang.model.entity.HomeTop;
import com.jlu.madizm.leguang.presenter.OnLoadListener;

/**
 * @author Smile Wei
 * @since 2016/9/22.
 */

public interface HomeLoadModel extends LoadModel {

    void load(OnLoadListener<HomeTop> listener, Context context, int type);

    void load(OnLoadListener<HomeBottom> listener, Context context, int type, int page, int pageSize);
}
