package co.samsao.reporter.network;

import java.util.List;

import co.samsao.reporter.model.Repository;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import rx.Observable;

/**
 * Network interface containing all the endpoints signature.
 * The factory creates the retrofit object.
 */
public interface NetworkService {

    String BASE_URL = "https://api.github.com/";

    @GET("users/samsao/repos")
    Observable<List<Repository>> getPublicRepositories();

    class Factory {
        public static NetworkService create() {
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                    .build();
            return retrofit.create(NetworkService.class);
        }
    }
}
