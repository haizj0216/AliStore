package com.haizj.alistore.modules.framgnt;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.haizj.alistore.R;
import com.haizj.alistore.base.bean.Product;
import com.haizj.alistore.base.database.ProductTable;
import com.haizj.alistore.utils.UIFragmentHelper;
import com.hyena.framework.app.fragment.BaseUIFragment;
import com.hyena.framework.database.DataBaseManager;

/**
 * Created by weilei on 17/4/1.
 */
public class NewPatchFragment extends BaseUIFragment<UIFragmentHelper> {
    private Product mProduct;
    private TextView mZXing;
    private TextView mName;
    private EditText mLeft;
    private EditText mPrice;
    private EditText mSaltPrice;
    private EditText mColor;
    private EditText mPublishDate;
    private EditText mEndDate;

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
        return View.inflate(getActivity(), R.layout.layout_new_patch, null);
    }

    @Override
    public void onViewCreatedImpl(View view, Bundle savedInstanceState) {
        super.onViewCreatedImpl(view, savedInstanceState);
        getUIFragmentHelper().getTitleBar().setTitle("增加批次");
        getUIFragmentHelper().getTitleBar().setRightMoreTxt("确定", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveGood();
            }
        });
        mZXing = (TextView) view.findViewById(R.id.zxing);
        mName = (TextView) view.findViewById(R.id.name);
        mLeft = (EditText) view.findViewById(R.id.left);
        mPrice = (EditText) view.findViewById(R.id.price);
        mSaltPrice = (EditText) view.findViewById(R.id.seltprice);
        mColor = (EditText) view.findViewById(R.id.color);
        mPublishDate = (EditText) view.findViewById(R.id.publish_date);
        mEndDate = (EditText) view.findViewById(R.id.profit_date);

        if (mProduct != null) {
            mZXing.setText(mProduct.productZxing);
            mName.setText(mProduct.productName);
            mLeft.setText(mProduct.productLeft);
            mPrice.setText(mProduct.productPrice);
            mSaltPrice.setText(mProduct.seltPrice);
            mColor.setText(mProduct.color);
            mPublishDate.setText(mProduct.productDate);
            mEndDate.setText(mProduct.expirationDate);
        }
    }

    private void saveGood() {
        String zxing = mZXing.getText().toString();
        String name = mName.getText().toString();
        String price = mPrice.getText().toString();
        String seltPrice = mSaltPrice.getText().toString();
        String left = mLeft.getText().toString();
        String color = mColor.getText().toString();
        String publisDate = mPublishDate.getText().toString();
        String endDate = mEndDate.getText().toString();

        Product product = new Product();
        product.productId = zxing;
        product.productZxing = zxing;
        product.productName = name;
        product.productCount = left;
        product.seltPrice = seltPrice;
        product.productPrice = price;
        product.productCount = left;
        product.productLeft = left;
        product.color = color;
        product.productDate = publisDate;
        product.expirationDate = endDate;
        if (mOnAddNewPatchListener != null) {
            mOnAddNewPatchListener.onAddNewPatch(product);
        }
        finish();
    }

    private OnAddNewPatchListener mOnAddNewPatchListener;

    public void setOnAddNewPatchListener(OnAddNewPatchListener listener) {
        mOnAddNewPatchListener = listener;
    }

    public interface OnAddNewPatchListener {
        public void onAddNewPatch(Product product);
    }

}
