package com.haizj.alistore.base.database;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteOpenHelper;

import com.haizj.alistore.base.bean.Product;
import com.hyena.framework.database.BaseTable;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by weilei on 17/3/6.
 */
public class ProductTable extends BaseTable<Product> {
    private static final String TABLE_NAME = "table_product";

    private static final String PRODUCT_NAME = "product_name";
    private static final String PRODUCT_ID = "product_ID";
    private static final String PRODUCT_COUNT = "product_count";
    private static final String PRODUCT_LEFT = "product_left";
    private static final String PRODUCT_PRICE = "product_price";
    private static final String PRODUCT_SELF_PRICE = "product_selfprice";
    private static final String PRODUCT_ZXING = "product_zing";
    private static final String PRODUCT_LINK = "product_link";

    private String[] colums = new String[]
            {
                    PRODUCT_NAME, PRODUCT_ID, PRODUCT_COUNT, PRODUCT_LEFT,
                    PRODUCT_PRICE, PRODUCT_SELF_PRICE, PRODUCT_ZXING, PRODUCT_LINK
            };

    public ProductTable(SQLiteOpenHelper sqlHelper) {
        super(TABLE_NAME, sqlHelper);
    }

    @Override
    public Product getItemFromCursor(Cursor cursor) {
        Product product = new Product();
        product.productCount = cursor.getString(cursor.getColumnIndexOrThrow(PRODUCT_COUNT));
        product.productLeft = cursor.getString(cursor.getColumnIndexOrThrow(PRODUCT_LEFT));
        product.productPrice = cursor.getString(cursor.getColumnIndexOrThrow(PRODUCT_PRICE));
        product.seltPrice = cursor.getString(cursor.getColumnIndexOrThrow(PRODUCT_SELF_PRICE));
        product.productId = cursor.getString(cursor.getColumnIndexOrThrow(PRODUCT_ID));
        product.productName = cursor.getString(cursor.getColumnIndexOrThrow(PRODUCT_NAME));
        product.productZxing = cursor.getString(cursor.getColumnIndexOrThrow(PRODUCT_ZXING));
        product.link = cursor.getString(cursor.getColumnIndexOrThrow(PRODUCT_LINK));
        return product;
    }

    @Override
    public ContentValues getContentValues(Product item) {
        ContentValues values = new ContentValues();
        values.put(PRODUCT_NAME, item.productName);
        values.put(PRODUCT_ID, item.productId);
        values.put(PRODUCT_COUNT, item.productCount);
        values.put(PRODUCT_LEFT, item.productLeft);
        values.put(PRODUCT_PRICE, item.productPrice);
        values.put(PRODUCT_SELF_PRICE, item.seltPrice);
        values.put(PRODUCT_ZXING, item.productZxing);
        values.put(PRODUCT_LINK, item.link);
        return values;
    }

    @Override
    public String getCreateSql() {
        String sql = "CREATE TABLE IF NOT EXISTS " + TABLE_NAME + "("
                + _ID + " integer primary key ," + PRODUCT_NAME
                + " varchar," + PRODUCT_ID + " varchar,"
                + PRODUCT_COUNT + " varchar," + PRODUCT_LEFT + " varchar,"
                + PRODUCT_PRICE + " varchar," + PRODUCT_LINK
                + " varchar," + PRODUCT_SELF_PRICE
                + " varchar," + PRODUCT_ZXING + " varchar)";
        return sql;
    }

    public void setProduct(Product product) {
        if (findProductByID(product.productId) != null) {
            updateByCase(product, PRODUCT_ID + " = ? ",
                    new String[]{product.productId});
        } else {
            insert(product);
        }
    }

    public Product findProductByID(String productId) {
        if (productId != null) {
            List<Product> userList = queryByCase("product_ID = ?",
                    new String[]{productId}, null);
            if (userList != null && userList.size() > 0) {
                return userList.get(0);
            }
        }
        return null;
    }

    public List<Product> findProductByName(String productId) {
        if (productId != null) {
            List<Product> userList = queryByCase("product_name = ?",
                    new String[]{productId}, null);
            if (userList != null && userList.size() > 0) {
                return userList;
            }
        }
        return null;
    }

    public List<Product> findProductLikeName(String name) {

        Cursor cursor = getDatabase().query(getTableName(), colums, PRODUCT_NAME + "  LIKE ? ",
                new String[]{"%" + name + "%"}, null, null, null);
        List<Product> products = new ArrayList<>();
        while (cursor.moveToNext()) {
            products.add(getItemFromCursor(cursor));
        }
        return products;
    }
}
