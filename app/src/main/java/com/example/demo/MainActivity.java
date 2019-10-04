package com.example.demo;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.demo.bean.News;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    private EditText editText = null;
    private Button button = null;
    private TextView title, date, category, author_name = null;
    private final String baseUrl = "http://v.juhe.cn/toutiao/";
    private final String key = "3593f48dd34486e85d9fc451315c56c8";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editText = findViewById(R.id.edit);
        button = findViewById(R.id.button);
        title = findViewById(R.id.title);
        date = findViewById(R.id.date);
        category = findViewById(R.id.category);
        author_name = findViewById(R.id.author_name);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String type = editText.getText().toString();
                if (!type.equals("")) {
                    getNews(type);
                } else {
                    Toast.makeText(MainActivity.this, "输入的新闻标题不能为空", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public void getNews(String type) {
        OkHttpClient.Builder client = new OkHttpClient.Builder();
        Retrofit retrofit = new Retrofit.Builder()
                .client(client.build())
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        MyService service = retrofit.create(MyService.class);
        Call<News> call = service.getNews(type,key);
        call.enqueue(new Callback<News>() {

            @Override
            public void onResponse(Call<News> call, Response<News> response) {
                News news = response.body();
                if (news.getReason().equals("成功的返回")) {
                    Log.e("news", "成功");
                    title.setText(news.getResult().getData().get(1).getTitle());
                    date.setText(news.getResult().getData().get(1).getDate());
                    category.setText(news.getResult().getData().get(1).getCategory());
                    author_name.setText(news.getResult().getData().get(1).getAuthor_name());
                } else {
                    Log.e("news", "失败");
                    Toast.makeText(MainActivity.this, "出错了哦！", Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onFailure(Call<News> call, Throwable t) {
                Log.d("aaa", "onFailure: " + t.getMessage());
                t.getStackTrace();
                Toast.makeText(MainActivity.this, "出错了哦！", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
