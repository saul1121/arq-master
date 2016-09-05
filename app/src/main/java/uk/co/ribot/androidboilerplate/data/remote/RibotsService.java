package uk.co.ribot.androidboilerplate.data.remote;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.ryanharter.auto.value.gson.AutoValueGsonTypeAdapterFactory;

import java.util.List;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;
import rx.Observable;
import uk.co.ribot.androidboilerplate.data.model.CarMakesResponse;
import uk.co.ribot.androidboilerplate.data.model.Ribot;

public interface RibotsService {

    String ENDPOINT = "https://api.ribot.io/";
    public static final String MEDIA_URL = "https://media.ed.edmunds-media.com";
    public static final String BASE_URL = "https://api.edmunds.com/";

/*
    @GET("ribots")
    Observable<List<Ribot>> getRibots2();
*/


    @GET("/api/vehicle/v2/makes?width=500&height=854")
    Observable<CarMakesResponse> getCarMakes(@Query("state") String state,
                                             @Query("year") int year,
                                             @Query("view") String view,
                                             @Query("api_key") String api_key,
                                             @Query("fmt") String fmt);



    /******** Helper class that sets up a new services *******/
    class Creator {

        public static RibotsService newRibotsService() {
            Gson gson = new GsonBuilder()
                    .registerTypeAdapterFactory(new AutoValueGsonTypeAdapterFactory())
                    .setDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
                    .create();
            Retrofit retrofit = new Retrofit.Builder()
                    //.baseUrl(RibotsService.ENDPOINT)
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                    .build();
            return retrofit.create(RibotsService.class);
        }
    }
}
