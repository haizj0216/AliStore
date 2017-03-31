package com.haizj.alistore.modules.framgnt;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.haizj.alistore.R;
import com.haizj.alistore.base.bean.Product;
import com.haizj.alistore.base.database.ProductTable;
import com.haizj.alistore.utils.UIFragmentHelper;
import com.haizj.alistore.zxing.activity.CaptureActivity;
import com.hyena.framework.app.fragment.BaseUIFragment;
import com.hyena.framework.database.DataBaseManager;

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

    private Product mProduct;

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
        mZXing.setOnClickListener(mOnClickListener);
        view.findViewById(R.id.save).setOnClickListener(mOnClickListener);

        if (mProduct != null) {
            mZXing.setText(mProduct.productZxing);
            mName.setText(mProduct.productName);
            mLeft.setText(mProduct.productLeft);
            mPrice.setText(mProduct.productPrice);
            mSaltPrice.setText(mProduct.seltPrice);
            mLink.setText(mProduct.link);
        }
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
                case R.id.save:
                    saveGood();
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
                    mZXing.setText("二维码:" + bundle.getString("result"));
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        }
    }

    private void saveGood() {
        String zxing = mZXing.getText().toString();
        String name = mName.getText().toString();
        String link = mLink.getText().toString();
        String price = mPrice.getText().toString();
        String seltPrice = mSaltPrice.getText().toString();
        String left = mLeft.getText().toString();

        Product product = new Product();
        product.productId = zxing;
        product.productZxing = zxing;
        product.productName = name;
        product.productCount = left;
        product.seltPrice = seltPrice;
        product.productPrice = price;
        product.productCount = left;
        product.productLeft = left;
        product.link = link;

        ProductTable table = DataBaseManager.getDataBaseManager().getTable(ProductTable.class);
        table.setProduct(product);
    }
}
