package uk.co.ribot.androidboilerplate.ui.main;

import android.content.Intent;
import android.util.Log;

import java.util.List;

import javax.inject.Inject;

import rx.Observable;
import rx.Observer;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import timber.log.Timber;
import uk.co.ribot.androidboilerplate.data.DataManager;
import uk.co.ribot.androidboilerplate.data.model.CarMakesResponse;
import uk.co.ribot.androidboilerplate.data.model.Ribot;
import uk.co.ribot.androidboilerplate.injection.ConfigPersistent;
import uk.co.ribot.androidboilerplate.ui.base.BasePresenter;
import uk.co.ribot.androidboilerplate.util.AndroidComponentUtil;
import uk.co.ribot.androidboilerplate.util.NetworkUtil;
import uk.co.ribot.androidboilerplate.util.RxUtil;

@ConfigPersistent
public class MainPresenter extends BasePresenter<MainMvpView> {

    public static final String MEDIA_URL = "https://media.ed.edmunds-media.com";
    private static final String BASE_URL = "https://api.edmunds.com/";
    private static final String API_KEY = "67fc8n4tm29nsm9gj2n3v6pe";
    private final String TAG = MainPresenter.class.getSimpleName();

    private final DataManager mDataManager;
    private Subscription mSubscription;

    @Inject
    public MainPresenter(DataManager dataManager) {
        mDataManager = dataManager;
    }

    @Override
    public void attachView(MainMvpView mvpView) {
        super.attachView(mvpView);
    }

    @Override
    public void detachView() {
        super.detachView();
        if (mSubscription != null) mSubscription.unsubscribe();
    }

    public void loadRibots() {
        checkViewAttached();
        RxUtil.unsubscribe(mSubscription);
        mSubscription = mDataManager.getRibots()

                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Subscriber<List<Ribot>>() {
                    @Override
                    public void onCompleted() {
                    }

                    @Override
                    public void onError(Throwable e) {
                        Timber.e(e, "There was an error loading the ribots.");
                        getMvpView().showError();
                    }

                    @Override
                    public void onNext(List<Ribot> ribots) {
                        if (ribots.isEmpty()) {
                            getMvpView().showRibotsEmpty();
                        } else {
                            getMvpView().showRibots(ribots);
                        }
                    }
                });
    }




    public void loadRibots2() {
        Timber.i("Starting sync...");

  /*      if (!NetworkUtil.isNetworkConnected(this)) {
            Timber.i("Sync canceled, connection not available");
            AndroidComponentUtil.toggleComponent(this, SyncOnConnectionAvailable.class, true);
            stopSelf(startId);
            return START_NOT_STICKY;
        }
*/
        if (mSubscription != null && !mSubscription.isUnsubscribed()) mSubscription.unsubscribe();

                 mSubscription = mDataManager.syncRibots2()
                .subscribeOn(Schedulers.io())
                .subscribe(new Observer<Ribot>() {
                    @Override
                    public void onCompleted() {
                        Timber.i("Synced successfully!");

                    }

                    @Override
                    public void onError(Throwable e) {
                        Timber.w(e, "Error syncing.");


                    }

                    @Override
                    public void onNext(Ribot ribot) {
                    }
                });


    }

    public void loadRibots3() {
        Log.d(TAG,"ENTERING PRESENTER..." );
        checkViewAttached();
        RxUtil.unsubscribe(mSubscription);
        if (mSubscription != null && !mSubscription.isUnsubscribed()) mSubscription.unsubscribe();






        Log.d(TAG,"AFTER RESPONSE..." );
        mSubscription =  mDataManager.syncRibots3("new", 2015, "basic", API_KEY, "json")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<CarMakesResponse>() {
                    @Override
                    public void onCompleted() {
                        Log.d(TAG,"ON COMPLETED..." );
                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                        //listener.OnError("An Error Occured! ");
                    }

                    @Override
                    public void onNext(CarMakesResponse carMakesResponse) {
                        Log.d(TAG,"ON NEXT..." );
                        carMakesResponse.getMakes();

                        getMvpView().showCars(carMakesResponse);
                        Log.d(TAG,"AFTER ON NEXT..." );
                        //listener.OnResponse(carMakesResponse);
                    }
                });
//slow
/*

        Observable<CarMakesResponse> all = mDataManager.syncRibots3("new", 2015, "basic", API_KEY, "json").subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
        all.subscribe(new Observer<CarMakesResponse>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(CarMakesResponse ribots) {

                getMvpView().showCars(ribots);
            }
        });

*/




    }




}
