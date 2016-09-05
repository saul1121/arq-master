package uk.co.ribot.androidboilerplate.data;

import android.util.Log;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import retrofit2.http.Path;
import retrofit2.http.Query;
import rx.Observable;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;
import uk.co.ribot.androidboilerplate.data.local.DatabaseHelper;
import uk.co.ribot.androidboilerplate.data.local.PreferencesHelper;
import uk.co.ribot.androidboilerplate.data.model.CarMakesResponse;
import uk.co.ribot.androidboilerplate.data.model.Ribot;
import uk.co.ribot.androidboilerplate.data.remote.RibotsService;

@Singleton
public class DataManager {

    private final RibotsService mRibotsService;
    private final DatabaseHelper mDatabaseHelper;
    private final PreferencesHelper mPreferencesHelper;
    private final String TAG = DataManager.class.getSimpleName();

    @Inject
    public DataManager(RibotsService ribotsService, PreferencesHelper preferencesHelper,
                       DatabaseHelper databaseHelper) {
        mRibotsService = ribotsService;
        mPreferencesHelper = preferencesHelper;
        mDatabaseHelper = databaseHelper;
    }

    public PreferencesHelper getPreferencesHelper() {
        return mPreferencesHelper;
    }


    public Observable<Ribot> syncRibots2() {
    //public Observable<List<Ribot>> syncRibots2() {


/*
        return mRibotsService.getRibots2()
                .concatMap(new Func1<List<Ribot>, Observable<Ribot>>() {
                    @Override
                    public Observable<Ribot> call(List<Ribot> ribots) {

                            //return ribots;

                            return mDatabaseHelper.setRibots(ribots);

                    }
                });
*/
        return null;

    }



    public Observable<List<Ribot>> getRibots() {
        return mDatabaseHelper.getRibots().distinct();
    }

    public Observable<CarMakesResponse> syncRibots3(String makeNiceName,
                                                    int year,
                                                    String view,
                                                    String api_key,
                                                    String fmt) {
        Log.d(TAG,"ENTERING DATA MANAGER" );
Observable<CarMakesResponse> call =        mRibotsService.getCarMakes(makeNiceName,year, view, api_key,fmt);
        Log.d(TAG,"AFTER ENTERING DATA MANAGER" );
        return call;

        //return mRibotsService.getRibots2();
    }

}
