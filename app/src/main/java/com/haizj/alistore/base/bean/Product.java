package com.haizj.alistore.base.bean;

import android.text.TextUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by weilei on 17/3/6.
 */
public class Product implements Serializable {

    public String productId;
    public String productName;
    public String productCount;
    public String productLeft;
    public String productDate;
    public String productPrice;
    public String productZxing;
    public String seltPrice;
    public String link;
    public String expirationDate;
    public String color;
    public String profit;

    public List<Product> subProducts;

    public String getJsonString() {
        JSONObject object = new JSONObject();
        try {
            object.put("productId", productId);
            object.put("productName", productName);
            object.put("productCount", productCount);
            object.put("productLeft", productLeft);
            object.put("productDate", productDate);
            object.put("productPrice", productPrice);
            object.put("productZxing", productZxing);
            object.put("seltPrice", seltPrice);
            object.put("link", link);
            object.put("expirationDate", expirationDate);
            object.put("color", color);
            object.put("profit", profit);
            if (subProducts != null && subProducts.size() > 0) {
                JSONArray array = new JSONArray();
                for (int i = 0; i < subProducts.size(); i++) {
                    Product product = subProducts.get(i);
                    array.put(product.getJsonString());
                }
                object.put("sub", array);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return object.toString();
    }

    public String getSubString(List<Product> subs) {
        if (subs != null) {
            JSONArray array = new JSONArray();
            for (int i = 0; i < subs.size(); i++) {
                Product product = subs.get(i);
                array.put(product.getJsonString());
            }
            return array.toString();
        }
        return "";
    }

    public List<Product> parseSub(String jsonStr) {
        if (TextUtils.isEmpty(jsonStr)) {
            return null;
        }
        List<Product> subProducts = new ArrayList<>();
        try {
            JSONArray array = new JSONArray(jsonStr);
            for (int i = 0; i < array.length(); i++) {
                Product product = new Product();
                product.parseJson(array.optString(i));
                subProducts.add(product);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return subProducts;
    }

    public void parseJson(String jsonStr) {
        try {
            JSONObject jsonObject = new JSONObject(jsonStr);
            productCount = jsonObject.optString("productCount");
            productId = jsonObject.optString("productId");
            productName = jsonObject.optString("productName");
            productLeft = jsonObject.optString("productLeft");
            productDate = jsonObject.optString("productDate");
            productPrice = jsonObject.optString("productPrice");
            productZxing = jsonObject.optString("productZxing");
            seltPrice = jsonObject.optString("seltPrice");
            link = jsonObject.optString("link");
            expirationDate = jsonObject.optString("expirationDate");
            color = jsonObject.optString("color");
            profit = jsonObject.optString("profit");
            if (jsonObject.has("sub")) {
                JSONArray array = jsonObject.optJSONArray("sub");
                subProducts = new ArrayList<>();
                for (int i = 0; i < array.length(); i++) {
                    Product product = new Product();
                    product.parseJson(array.optJSONObject(i).toString());
                    subProducts.add(product);
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
