package com.miller.futurechat

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.miller.mockretrofitinterceptor.MockInterceptor
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_main.*
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

@ExperimentalStdlibApi
class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        callMockApi()
    }

    private fun callMockApi() {
        val retrofit = Retrofit.Builder()
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl("http://mock.com")
            .client(
                OkHttpClient.Builder()
                    .addInterceptor(
                        MockInterceptor(
                            AppResourceHelper(this@MainActivity)
                        )
                    )
                    .build()
            )
            .build()

        val apiService = retrofit.create(ApiService::class.java)

        apiService.getSomething()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                {
                    textId.text = it.id.toString()
                },
                {
                    it.printStackTrace()
                    textId.text = it.localizedMessage
                }
            ).apply {
                CompositeDisposable().add(this)
            }
    }
}
