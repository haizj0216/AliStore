package com.haizj.alistore.base.database;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteOpenHelper;
import android.text.TextUtils;

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
    private static final String PRODUCT_COLOR = "product_color";
    private static final String PRODUCT_PUBLSH_DATE = "product_publish";
    private static final String PRODUCT_END_DATE = "product_end";
    private static final String PRODUCT_PROFIT = "product_profit";
    private static final String PRODUCT_SUBS = "product_subs";

    private String[] colums = new String[]
            {
                    PRODUCT_NAME, PRODUCT_ID, PRODUCT_COUNT, PRODUCT_LEFT,
                    PRODUCT_PRICE, PRODUCT_SELF_PRICE, PRODUCT_ZXING, PRODUCT_LINK,
                    PRODUCT_COLOR, PRODUCT_PUBLSH_DATE, PRODUCT_END_DATE, PRODUCT_PROFIT,
                    PRODUCT_SUBS
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
        product.color = cursor.getString(cursor.getColumnIndexOrThrow(PRODUCT_COLOR));
        product.productDate = cursor.getString(cursor.getColumnIndexOrThrow(PRODUCT_PUBLSH_DATE));
        product.expirationDate = cursor.getString(cursor.getColumnIndexOrThrow(PRODUCT_END_DATE));
        product.profit = cursor.getString(cursor.getColumnIndexOrThrow(PRODUCT_PROFIT));
        String subs = cursor.getString(cursor.getColumnIndexOrThrow(PRODUCT_SUBS));
        product.subProducts = product.parseSub(subs);

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
        values.put(PRODUCT_COLOR, item.color);
        values.put(PRODUCT_PROFIT, item.profit);
        values.put(PRODUCT_PUBLSH_DATE, item.productDate);
        values.put(PRODUCT_END_DATE, item.expirationDate);
        values.put(PRODUCT_SUBS, item.getSubString(item.subProducts));
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
                + " varchar," + PRODUCT_COLOR
                + " varchar," + PRODUCT_PUBLSH_DATE
                + " varchar," + PRODUCT_END_DATE
                + " varchar," + PRODUCT_PROFIT
                + " varchar," + PRODUCT_SUBS
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
