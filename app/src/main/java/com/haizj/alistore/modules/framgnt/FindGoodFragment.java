package com.haizj.alistore.modules.framgnt;

import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.haizj.alistore.R;
import com.haizj.alistore.base.bean.Product;
import com.haizj.alistore.base.database.ProductTable;
import com.haizj.alistore.utils.UIFragmentHelper;
import com.hyena.framework.app.adapter.SingleTypeAdapter;
import com.hyena.framework.app.fragment.BaseUIFragment;
import com.hyena.framework.database.DataBaseManager;

import java.util.List;

/**
 * Created by weilei on 17/3/27.
 */
public class FindGoodFragment extends BaseUIFragment<UIFragmentHelper> {

    private EditText mFindEdit;
    private ListView mList;
    private ProductAdapter mAdapter;

    @Override
    public void onCreateImpl(Bundle savedInstanceState) {
        super.onCreateImpl(savedInstanceState);
        setSlideable(true);
    }

    @Override
    public View onCreateViewImpl(Bundle savedInstanceState) {
        return View.inflate(getActivity(), R.layout.layout_find_good, null);
    }

    @Override
    public void onViewCreatedImpl(View view, Bundle savedInstanceState) {
        super.onViewCreatedImpl(view, savedInstanceState);
        getUIFragmentHelper().getTitleBar().setTitle("查找产品");
//        getUIFragmentHelper().getTitleBar().setRightMoreTxt("扫描二维码", new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//            }
//        });

        mFindEdit = (EditText) view.findViewById(R.id.find_edit);
        mList = (ListView) view.findViewById(R.id.find_list);

        mFindEdit.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                loadGood(mFindEdit.getText().toString());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        mAdapter = new ProductAdapter(getActivity());
        mList.setAdapter(mAdapter);

        mList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Bundle bundle = new Bundle();
                bundle.putSerializable("product", mAdapter.getItem(position));
                AddGoodsFragment fragment = AddGoodsFragment.newFragment(getActivity(),
                        AddGoodsFragment.class, bundle);
                showFragment(fragment);
            }
        });
    }

    private void loadGood(String name) {
        if (TextUtils.isEmpty(name)) {
            return;
        }
        ProductTable table = DataBaseManager.getDataBaseManager().getTable(ProductTable.class);
        List<Product> products = table.findProductLikeName(name);
        if (products != null) {
            mAdapter.setItems(products);
        }
    }

    class ProductAdapter extends SingleTypeAdapter<Product> {

        public ProductAdapter(Context context) {
            super(context);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            convertView = View.inflate(getActivity(), R.layout.layout_find_good_item, null);

            Product product = getItem(position);
            ((TextView) convertView.findViewById(R.id.item_name)).setText(product.productName);
            ((TextView) convertView.findViewById(R.id.item_zxing)).setText(product.productId);
            ((TextView) convertView.findViewById(R.id.item_left)).setText(product.productLeft + "");
            ((TextView) convertView.findViewById(R.id.item_price)).setText(product.productPrice + "");
            ((TextView) convertView.findViewById(R.id.item_seltprice)).setText(product.seltPrice + "");

            return convertView;
        }
    }
}
