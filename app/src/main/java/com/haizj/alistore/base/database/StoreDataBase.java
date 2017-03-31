package com.haizj.alistore.base.database;

import com.hyena.framework.database.BaseDataBaseHelper;
import com.hyena.framework.database.DataBaseHelper;
import com.hyena.framework.utils.BaseApp;

/**
 * Created by weilei on 17/3/6.
 */
public class StoreDataBase extends BaseDataBaseHelper {
    public static final String DATABASE_NAME = "store.db";
    public static final int DATABASE_VERSION = 1;

    public StoreDataBase() {
        super(BaseApp.getAppContext(), DATABASE_NAME, DATABASE_VERSION);
    }

    @Override
    public void initTablesImpl(DataBaseHelper db) {
        addTable(ProductTable.class, new ProductTable(db));
    }
}
