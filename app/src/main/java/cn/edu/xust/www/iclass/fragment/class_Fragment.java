package cn.edu.xust.www.iclass.fragment;


import android.content.Intent;
import android.os.Bundle;

import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;

import java.util.ArrayList;
import java.util.List;

import cn.edu.xust.www.iclass.R;
import cn.edu.xust.www.iclass.activity.ClassActivity;
import cn.edu.xust.www.iclass.adapter.MyBaseRecyclerViewAdapter;
import cn.edu.xust.www.iclass.entity.HomeItem;

public class class_Fragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    //private static final Class<?>[] ACTIVITY = {AnimationUseActivity.class, MultipleItemUseActivity.class, HeaderAndFooterUseActivity.class, PullToRefreshUseActivity.class, SectionUseActivity.class, EmptyViewUseActivity.class, ItemDragAndSwipeUseActivity.class,RecyclerClickItemActivity.class, ItemClickActivity.class,ExpandableUseActivity.class, DataBindingUseActivity.class};
    private static final String[] TITLE = {"Animation Use", "MultipleItem Use", "HeaderAndFooter Use", "PullToRefresh Use", "Section Use", "EmptyView Use", "ItemDragAndSwipe Use", "RecyclerClickItemActivity", "ItemClickActivity", "ExpandableItem Activity", "DataBinding Use"};
    private static final String[] COLOR_STR = {"#0dddb8", "#0bd4c3", "#03cdcd", "#00b1c5", "#04b2d1", "#04b2d1", "#04b2d1", "#04b2d1", "#04b2d1", "#04b2d1", "#04b2d1"};
    public List<String> list = new ArrayList<>();
    private String mParam1;
    private String mParam2;
    private List<HomeItem> mlist;


    public class_Fragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment class_Fragment.
     */
    // TODO: Rename and change types and number of parameters
    public static class_Fragment newInstance(String param1, String param2) {
        class_Fragment fragment = new class_Fragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);


        }

        initData();

    }

    private void initData() {
        mlist = new ArrayList<>();
        for (int i = 0; i < TITLE.length; i++) {
            HomeItem item = new HomeItem();
            item.setColorStr(COLOR_STR[i]);
            item.setTitle(TITLE[i]);

            mlist.add(item);

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        FrameLayout layout = (FrameLayout) inflater.inflate(R.layout.fragment_class, container, false);


        // Inflate the layout for this fragment

        RecyclerView recyclerview = (RecyclerView) layout.findViewById(R.id.recyclerview_list);

        recyclerview.setLayoutManager(new LinearLayoutManager(getActivity()));

        MyBaseRecyclerViewAdapter myBaseRecyclerViewAdapter = new MyBaseRecyclerViewAdapter(R.layout.home_item_view, mlist);


        myBaseRecyclerViewAdapter.openLoadAnimation();

        recyclerview.addOnItemTouchListener(new OnItemClickListener() {
            @Override
            public void SimpleOnItemClick(BaseQuickAdapter baseQuickAdapter, View view, int i) {
               // Toast.makeText(getContext(), "第" + i + "个item被点击", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getActivity(), ClassActivity.class);
                startActivity(intent);
            }
        });


        recyclerview.setAdapter(myBaseRecyclerViewAdapter);
        //recyclerview.setAdapter(new MyRecyclerViewAdapter(getContext(), list));


        return layout;
    }


}
