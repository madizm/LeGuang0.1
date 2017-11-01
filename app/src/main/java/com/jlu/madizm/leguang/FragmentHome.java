package com.jlu.madizm.leguang;

import android.app.Activity;
import android.content.Context;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.aurelhubert.ahbottomnavigation.AHBottomNavigation;
import com.jlu.madizm.leguang.base.Type;
import com.jlu.madizm.leguang.model.entity.HomeBase;
import com.jlu.madizm.leguang.model.entity.HomeBottom;
import com.jlu.madizm.leguang.model.entity.HomeTop;
import com.jlu.madizm.leguang.presenter.HomePresenter;
import com.jlu.madizm.leguang.utils.ToastUtil;
import com.jlu.madizm.leguang.widget.LoadMoreRecyclerView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class FragmentHome extends Fragment
        implements SwipeRefreshLayout.OnRefreshListener, LoadMoreRecyclerView.OnLoadMoreListener, HomeContract.View{

    private final static int HOME_TOP = 1;
    private final static int HOME_BOTTOM = 2;

//    @BindView(R.id.refresh_layout)
    SwipeRefreshLayout refreshLayout;
//    @BindView(R.id.recycler_view)
    LoadMoreRecyclerView recyclerView;
    private Context context;
    private Activity activity;

//    private AHBottomNavigation mBottomNavigation;

    private List<HomeBase> list = new ArrayList<>();

    private AdapterHome adapter;
    private HomePresenter presenter;
    private int page = 1;
    private int pageSize = 10;
    private HomeBase footerItem = new HomeBase();

    public FragmentHome(){

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frag_home,container,false);
//        ButterKnife.bind(this, view);
        refreshLayout = view.findViewById(R.id.refresh_layout);
        recyclerView = view.findViewById(R.id.recycler_view);

        activity = getActivity();
        context = activity.getApplicationContext();

        presenter = new HomePresenter();
        presenter.init(this, context);//调用initView()
        return view;
    }

    public static Fragment newInstance() {
        FragmentHome fragment = new FragmentHome();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;

    }

    @Override
    public void onRefresh() {
        setRefreshLoading(true);
        presenter.start(HOME_TOP);//调用show(HomeTop bean)
        page = 1;
    }

    @Override
    public void onLoadMore() {
        setRefreshLoading(true);
        page++;
        presenter.start(HOME_BOTTOM, page, pageSize);//调用show(HomeBottom bean)
    }

    @Override
    public void initView() {
        refreshLayout.setColorSchemeResources(R.color.font_orange_color);
        refreshLayout.setOnRefreshListener(this);
        int spanCount = getResources().getInteger(R.integer.grid_span_count);

        GridLayoutManager layoutManager = new GridLayoutManager(activity, spanCount);
        recyclerView.setLayoutManager(layoutManager);

        adapter = new AdapterHome(context, activity, list);
        layoutManager.setSpanSizeLookup(adapter.getSpanSizeLookup());
        recyclerView.setAdapter(adapter);
        recyclerView.setOnLoadMoreListener(this);

        footerItem.setType(Type.TYPE_FOOTER_LOAD);
        footerItem.setSpanCount(spanCount);
        presenter.start(HOME_TOP);//调用show(HomeTop bean)

    }

    @Override
    public void show(HomeTop bean) {
        list.clear();
        adapter.setLoopList(bean.getCarousel());
        //adapter.setHeadlineList(bean.getHeadlines());

        list.addAll(bean.getList());
        list.add(footerItem);
        adapter.notifyDataSetChanged();

        presenter.start(HOME_BOTTOM, page, pageSize);
    }

    @Override
    public void show(HomeBottom bean) {
        recyclerView.setPage(page, bean.getTotalPage());
        footerItem.setType(page < bean.getTotalPage() ? Type.TYPE_FOOTER_LOAD : Type.TYPE_FOOTER_FULL);
        list.addAll(list.size() - 1, bean.getData());
        adapter.notifyDataSetChanged();
        setRefreshLoading(false);
    }

    @Override
    public void loading() {

    }

    @Override
    public void networkError() {
        setRefreshLoading(false);
        ToastUtil.showShortToast(context, R.string.toast_network_error);
    }

    @Override
    public void error(String msg) {
        setRefreshLoading(false);
        ToastUtil.showShortToast(context, msg);
    }

    @Override
    public void showFailed(String msg) {
        setRefreshLoading(false);
        ToastUtil.showShortToast(context, msg);
    }

    /**
     * 设置刷新和加载更多的状态
     *
     * @param isLoading 加载更多状态
     */
    private void setRefreshLoading(boolean isLoading) {
        if (!isLoading) {
            recyclerView.setLoading(false);
            refreshLayout.setRefreshing(false);
        }
    }
}
