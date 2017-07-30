package ru.karapetiandav.yamblzproject.di.module;

import android.content.Context;
import android.content.res.Resources;
import android.support.annotation.NonNull;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import ru.karapetiandav.yamblzproject.R;
import ru.karapetiandav.yamblzproject.data.db.DBHelper;
import ru.karapetiandav.yamblzproject.data.db.DBHelperImpl;
import ru.karapetiandav.yamblzproject.di.qualifiers.DbName;
import ru.karapetiandav.yamblzproject.di.qualifiers.DbVersion;

@Module
public class DBModule {

    @Provides
    @NonNull
    @Singleton
    @DbName
    String provideDBName(Resources resources) {
        return resources.getString(R.string.db_name);
    }

    @Provides
    @DbVersion
    int provideDBVersion(Resources resources) {
        return resources.getInteger(R.integer.db_version);
    }

    @Provides
    @NonNull
    DBHelper provideDBHelper(Context context, @DbName String dbName,
                             @DbVersion int dbVersion) {
        return new DBHelperImpl(context, dbName, dbVersion);
    }

}
