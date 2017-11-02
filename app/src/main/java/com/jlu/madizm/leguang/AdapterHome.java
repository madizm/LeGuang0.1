package com.jlu.madizm.leguang;

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.jlu.madizm.leguang.base.Type;
import com.jlu.madizm.leguang.model.entity.HomeBase;
import com.jlu.madizm.leguang.model.entity.HomeTop;
import com.jlu.madizm.leguang.utils.GlideUtil;
import com.jlu.madizm.leguang.utils.ToastUtil;
import com.jlu.madizm.leguang.widget.CirclePageIndicator;
import com.jlu.madizm.leguang.widget.FooterLoading;
import com.jlu.madizm.leguang.widget.HomeSearchBar;
import com.oushangfeng.marqueelayout.MarqueeLayout;
import com.oushangfeng.marqueelayout.MarqueeLayoutAdapter;

import java.util.List;
import java.util.Random;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.trinea.android.view.autoscrollviewpager.AutoScrollViewPager;

/**
 * @author Smile Wei
 * @since 2017/03/01.
 */

public class AdapterHome extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private Context context;
    private Activity activity;
    private List<HomeBase> list;
    private List<HomeTop.Carousel> loopList;
    private List<HomeBase> headlineList;
    private final SpanSizeLookup spanSizeLookup = new SpanSizeLookup();
    private LayoutInflater inflater;

    public AdapterHome(Context context, Activity activity, List<HomeBase> list) {
        this.context = context;
        this.activity = activity;
        this.list = list;
        inflater = LayoutInflater.from(context);
    }

    public void setLoopList(List<HomeTop.Carousel> loopList) {
        this.loopList = loopList;
    }

    public void setHeadlineList(List<HomeBase> headlineList) {
        this.headlineList = headlineList;
    }

    public GridLayoutManager.SpanSizeLookup getSpanSizeLookup() {
        return spanSizeLookup;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;
        switch (viewType) {
            case HomeBase.TYPE_CAROUSEL:
                view = inflater.inflate(R.layout.item_home_type_carousel, parent, false);
                return new CarouselHolder(view);
            case HomeBase.TYPE_CATEGORY:
                view = inflater.inflate(R.layout.item_home_type_category, parent, false);
                return new CategoryHolder(view);
            case HomeBase.TYPE_HEADLINE:
                view = inflater.inflate(R.layout.item_home_type_headline, parent, false);
                return new HeadlineHolder(view);
            case HomeBase.TYPE_HEADER:
                view = inflater.inflate(R.layout.item_home_type_header, parent, false);
                return new HeaderHolder(view);
            case HomeBase.TYPE_DIVIDER:
                view = inflater.inflate(R.layout.item_home_type_divider, parent, false);
                return new PlaceHolder(view);
            case HomeBase.TYPE_LIVE:
                view = inflater.inflate(R.layout.item_home_type_live, parent, false);
                return new LiveHolder(view);
            case HomeBase.TYPE_HOT:
                view = inflater.inflate(R.layout.item_home_type_hot, parent, false);
                return new CommonHolder(view);
            case HomeBase.TYPE_RECOMMEND:
                view = inflater.inflate(R.layout.item_home_type_recommend, parent, false);
                return new RecommendHolder(view);
            case HomeBase.TYPE_PLACE:
                view = inflater.inflate(R.layout.item_place, parent, false);
                return new PlaceHolder(view);
//            case HomeBase.TYPE_HOME_SEARCH_BAR:
//                view = inflater.inflate(R.layout.item_home_type_search,parent,false);
//                return new HomeSearchBarHolder(view);
            default:
                view = inflater.inflate(R.layout.item_footer_loading, parent, false);
                return new FooterHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int position) {
        HomeBase bean = list.get(position);
        if (viewHolder instanceof CarouselHolder) {
            CarouselHolder holder = (CarouselHolder) viewHolder;
            holder.viewPager.setAdapter(new AdapterImageHome(context, activity, loopList));
            holder.indicator.setViewPager(holder.viewPager);
            holder.viewPager.setInterval(4000);
            holder.viewPager.setSlideBorderMode(AutoScrollViewPager.SLIDE_BORDER_MODE_TO_PARENT);
            holder.viewPager.startAutoScroll();
        } else if (viewHolder instanceof HeadlineHolder) {
            HeadlineHolder holder = (HeadlineHolder) viewHolder;
            MarqueeLayoutAdapter<HomeBase> topAdapter = new MarqueeLayoutAdapter<HomeBase>(headlineList) {
                @Override
                protected int getItemLayoutId() {
                    return R.layout.item_marquee;
                }

                @Override
                protected void initView(View view, int position, HomeBase item) {
                    ((TextView) view).setText(item.getName());
                }
            };
            holder.marqueeLayout.setAdapter(topAdapter);
            holder.marqueeLayout.start();
        } else if (viewHolder instanceof HeaderHolder) {
            HeaderHolder holder = (HeaderHolder) viewHolder;
            holder.tvTitle.setText(bean.getName());
        } else if (viewHolder instanceof LiveHolder) {
            LiveHolder holder = (LiveHolder) viewHolder;
            GlideUtil.load(activity, bean.getUrl(), holder.ivHome);
            holder.tvTitle.setText(bean.getName());
        } else if (viewHolder instanceof CommonHolder) {
            CommonHolder holder = (CommonHolder) viewHolder;
            GlideUtil.load(activity, bean.getUrl(), holder.ivHome);
            holder.tvTitle.setText(bean.getName());
            holder.tvPrice.setText(String.format("%s%s", bean.getCurrency(), bean.getPrice()));
        } else if (viewHolder instanceof RecommendHolder) {
            RecommendHolder holder = (RecommendHolder) viewHolder;
            GlideUtil.load(activity, bean.getUrl(), holder.ivHome);
            holder.tvTitle.setText(bean.getName());
            holder.tvPrice.setText(String.format("%s%s", bean.getCurrency(), bean.getPrice()));
            holder.tvCount.setText(String.format("%d人付款", new Random().nextInt(10000)));
        } else if (viewHolder instanceof FooterHolder) {
            FooterHolder holder = (FooterHolder) viewHolder;
            holder.footerLoading.onLoad(Type.TYPE_FOOTER_FULL == list.get(position).getType());
        }
//        else if(viewHolder instanceof HomeSearchBarHolder){
//            HomeSearchBarHolder holder = (HomeSearchBarHolder) viewHolder;
//            holder.msg.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    ToastUtil.showShortToast(context,"msg clicked");
//                }
//            });
//            holder.searchText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
//                @Override
//                public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
//                    ToastUtil.showShortToast(context, v.getText().toString());
//                    return false;
//                }
//            });
//        }
    }

    @Override
    public int getItemViewType(int position) {
        return list.get(position).getType();
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

//    class HomeSearchBarHolder extends RecyclerView.ViewHolder{
//        EditText searchText;
//        Button msg;
//        public HomeSearchBarHolder(View itemView) {
//            super(itemView);
//            searchText = itemView.findViewById(R.id.edText_search);
//            msg = itemView.findViewById(R.id.msg);
//        }
//    }

    class CarouselHolder extends RecyclerView.ViewHolder {
        //@BindView(R.id.view_pager)
        AutoScrollViewPager viewPager;
        //@BindView(R.id.indicator)
        CirclePageIndicator indicator;

        CarouselHolder(View itemView) {
            super(itemView);
            //ButterKnife.bind(this, itemView);
            viewPager = itemView.findViewById(R.id.view_pager);
            indicator = itemView.findViewById(R.id.indicator);
        }
    }

    class HeadlineHolder extends RecyclerView.ViewHolder {
        //@BindView(R.id.marquee_layout)
        MarqueeLayout marqueeLayout;

        HeadlineHolder(View itemView) {
            super(itemView);
            //ButterKnife.bind(this, itemView);
            marqueeLayout = itemView.findViewById(R.id.marquee_layout);
        }
    }


    class CommonHolder extends RecyclerView.ViewHolder {
        //@BindView(R.id.iv_home)
        ImageView ivHome;
        //@BindView(R.id.tv_title)
        TextView tvTitle;
        //@BindView(R.id.tv_price)
        TextView tvPrice;


        CommonHolder(View itemView) {
            super(itemView);
            //ButterKnife.bind(this, itemView);
            ivHome = itemView.findViewById(R.id.iv_home);
            tvTitle = itemView.findViewById(R.id.tv_title);
            tvPrice = itemView.findViewById(R.id.tv_price);
        }

    }

    class RecommendHolder extends RecyclerView.ViewHolder {
        //@BindView(R.id.iv_home)
        ImageView ivHome;
        //@BindView(R.id.tv_title)
        TextView tvTitle;
        //@BindView(R.id.tv_price)
        TextView tvPrice;
        //@BindView(R.id.tv_count)
        TextView tvCount;

        RecommendHolder(View itemView) {
            super(itemView);
            //ButterKnife.bind(this, itemView);
            ivHome = itemView.findViewById(R.id.iv_home);
            tvTitle = itemView.findViewById(R.id.tv_title);
            tvPrice = itemView.findViewById(R.id.tv_price);
            tvCount = itemView.findViewById(R.id.tv_count);
        }

    }

    class LiveHolder extends RecyclerView.ViewHolder {
        //@BindView(R.id.iv_home)
        ImageView ivHome;
        //@BindView(R.id.tv_title)
        TextView tvTitle;

        LiveHolder(View itemView) {
            super(itemView);
            //ButterKnife.bind(this, itemView);
            ivHome = itemView.findViewById(R.id.iv_home);
            tvTitle = itemView.findViewById(R.id.tv_title);
        }

    }

    class HeaderHolder extends RecyclerView.ViewHolder {
        //@BindView(R.id.tv_title)
        TextView tvTitle;

        HeaderHolder(View itemView) {
            super(itemView);
            //ButterKnife.bind(this, itemView);
            tvTitle = itemView.findViewById(R.id.tv_title);
        }
    }

    private class PlaceHolder extends RecyclerView.ViewHolder {
        PlaceHolder(View itemView) {
            super(itemView);
        }
    }


    private class SpanSizeLookup extends GridLayoutManager.SpanSizeLookup {
        @Override
        public int getSpanSize(int position) {
            return list.get(position).getSpanCount();
        }
    }

    class CategoryHolder extends RecyclerView.ViewHolder {

        CategoryHolder(View view) {
            super(view);
            //ButterKnife.bind(this, view);
        }
    }

    class FooterHolder extends RecyclerView.ViewHolder {
        //@BindView(R.id.footer)
        FooterLoading footerLoading;

        FooterHolder(View itemView) {
            super(itemView);
            //ButterKnife.bind(this, itemView);
            footerLoading = itemView.findViewById(R.id.footer);
        }
    }
}
