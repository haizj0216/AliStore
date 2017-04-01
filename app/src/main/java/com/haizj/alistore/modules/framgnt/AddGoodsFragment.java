package com.haizj.alistore.modules.framgnt;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.haizj.alistore.R;
import com.haizj.alistore.base.bean.Product;
import com.haizj.alistore.base.database.ProductTable;
import com.haizj.alistore.utils.UIFragmentHelper;
import com.haizj.alistore.zxing.activity.CaptureActivity;
import com.hyena.framework.app.adapter.SingleTypeAdapter;
import com.hyena.framework.app.fragment.BaseUIFragment;
import com.hyena.framework.app.widget.AccuracListView;
import com.hyena.framework.database.DataBaseManager;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by weilei on 17/3/6.
 */
public class AddGoodsFragment extends BaseUIFragment<UIFragmentHelper> {

    private int requestCode = 1;
    private String zXing;

    private EditText mZXing;
    private EditText mName;
    private EditText mLeft;
    private EditText mPrice;
    private EditText mSaltPrice;
    private EditText mLink;
    private EditText mColor;
    private EditText mPublishDate;
    private EditText mEndDate;
    private Button addBtn;
    private LinearLayout newBatch;
    private AccuracListView newBatchList;

    private List<Product> subProducts = new ArrayList<>();
    private Product mProduct;
    private PatchAdapter mAdapter;

    @Override
    public void onCreateImpl(Bundle savedInstanceState) {
        super.onCreateImpl(savedInstanceState);
        setSlideable(true);
        if (getArguments() != null) {
            mProduct = (Product) getArguments().getSerializable("product");
        }
    }

    @Override
    public View onCreateViewImpl(Bundle savedInstanceState) {
        return View.inflate(getActivity(), R.layout.layout_add_goods, null);
    }

    @Override
    public void onViewCreatedImpl(View view, Bundle savedInstanceState) {
        super.onViewCreatedImpl(view, savedInstanceState);
        getUIFragmentHelper().getTitleBar().setTitle("添加产品");

        mZXing = (EditText) view.findViewById(R.id.zxing);
        mName = (EditText) view.findViewById(R.id.name);
        mLeft = (EditText) view.findViewById(R.id.left);
        mPrice = (EditText) view.findViewById(R.id.price);
        mSaltPrice = (EditText) view.findViewById(R.id.seltprice);
        mLink = (EditText) view.findViewById(R.id.link);
        mColor = (EditText) view.findViewById(R.id.color);
        mPublishDate = (EditText) view.findViewById(R.id.publish_date);
        mEndDate = (EditText) view.findViewById(R.id.profit_date);
        addBtn = (Button) view.findViewById(R.id.add_batch);
        newBatch = (LinearLayout) view.findViewById(R.id.new_batch);
        newBatchList = (AccuracListView) view.findViewById(R.id.new_batch_list);

        mZXing.setOnClickListener(mOnClickListener);
        addBtn.setOnClickListener(mOnClickListener);
        getUIFragmentHelper().getTitleBar().setRightMoreTxt("保存", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveGood();
            }
        });

        if (mProduct != null) {
            mZXing.setText(mProduct.productZxing);
            mName.setText(mProduct.productName);
            mLeft.setText(mProduct.productLeft);
            mPrice.setText(mProduct.productPrice);
            mSaltPrice.setText(mProduct.seltPrice);
            mLink.setText(mProduct.link);
            mColor.setText(mProduct.color);
            mPublishDate.setText(mProduct.productDate);
            mEndDate.setText(mProduct.expirationDate);
        }

        mAdapter = new PatchAdapter(getActivity());
        mAdapter.setItems(subProducts);
        newBatchList.setAdapter(mAdapter);
    }

    private View.OnClickListener mOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            int id = v.getId();
            switch (id) {
                case R.id.zxing:
                    Intent intent = new Intent();
                    intent.setClass(getContext(), CaptureActivity.class);
                    startActivityForResult(intent, requestCode);
                    break;
                case R.id.add_batch:
                    addNewPatch();
                    break;
            }
        }
    };

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (this.requestCode == requestCode) {
            if (resultCode == 0) {
                try {
                    Bundle bundle = data.getExtras();
                    zXing = bundle.getString("result");
                    mZXing.setText(bundle.getString("result"));
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        }
    }

    private void addNewPatch() {
        updateProduct();
        Bundle mBundle = new Bundle();
        mBundle.putSerializable("product", mProduct);
        NewPatchFragment fragment = NewPatchFragment.newFragment(getActivity(), NewPatchFragment.class, mBundle);
        fragment.setOnAddNewPatchListener(new NewPatchFragment.OnAddNewPatchListener() {
            @Override
            public void onAddNewPatch(Product product) {
                subProducts.add(product);
                updatePatchList();
            }
        });
        showFragment(fragment);
    }

    private void updatePatchList() {
        mAdapter.notifyDataSetChanged();
        if (mAdapter.getCount() == 0) {
            newBatch.setVisibility(View.GONE);
        } else {
            newBatch.setVisibility(View.VISIBLE);
        }
    }

    private void updateProduct() {
        String zxing = mZXing.getText().toString();
        String name = mName.getText().toString();
        String link = mLink.getText().toString();
        String price = mPrice.getText().toString();
        String seltPrice = mSaltPrice.getText().toString();
        String left = mLeft.getText().toString();
        String color = mColor.getText().toString();
        String publisDate = mPublishDate.getText().toString();
        String endDate = mEndDate.getText().toString();

        if (mProduct == null) {
            mProduct = new Product();
        }
        mProduct.productId = zxing;
        mProduct.productZxing = zxing;
        mProduct.productName = name;
        mProduct.productCount = left;
        mProduct.seltPrice = seltPrice;
        mProduct.productPrice = price;
        mProduct.productCount = left;
        mProduct.productLeft = left;
        mProduct.link = link;
        mProduct.color = color;
        mProduct.productDate = publisDate;
        mProduct.expirationDate = endDate;
    }

    private void saveGood() {
        updateProduct();

        if (subProducts != null && subProducts.size() > 0) {
            mProduct.subProducts = subProducts;
        }

        ProductTable table = DataBaseManager.getDataBaseManager().getTable(ProductTable.class);
        table.setProduct(mProduct);
    }

    class PatchAdapter extends SingleTypeAdapter<Product> {

        public PatchAdapter(Context context) {
            super(context);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            convertView = View.inflate(getActivity(), R.layout.layou_new_path_item, null);
            TextView pulishDate = (TextView) convertView.findViewById(R.id.item_publish_date);
            TextView endDate = (TextView) convertView.findViewById(R.id.item_end_date);
            TextView color = (TextView) convertView.findViewById(R.id.item_color);
            TextView count = (TextView) convertView.findViewById(R.id.item_count);
            TextView price = (TextView) convertView.findViewById(R.id.item_price);
            TextView seltPrice = (TextView) convertView.findViewById(R.id.item_selt_price);

            Product product = getItem(position);
            pulishDate.setText(product.productDate);
            endDate.setText(product.expirationDate);
            color.setText(product.color);
            count.setText(product.productCount);
            price.setText(product.productPrice);
            seltPrice.setText(product.seltPrice);
            return convertView;
        }
    }
}
