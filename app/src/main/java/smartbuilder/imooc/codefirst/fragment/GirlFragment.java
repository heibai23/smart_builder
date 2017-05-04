package smartbuilder.imooc.codefirst.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import smartbuilder.imooc.codefirst.R;

/**
 * 创建时间： 2017/4/26.
 * 描述：TODO
 */

public class GirlFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.girlfragment,null);

        return view;
    }
}
