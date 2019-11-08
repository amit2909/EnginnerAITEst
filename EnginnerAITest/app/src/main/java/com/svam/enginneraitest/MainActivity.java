package com.svam.enginneraitest;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.AsyncTask;
import android.os.Bundle;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;


public class MainActivity extends AppCompatActivity {
private RecyclerView recyclerItems;
    ActionBar actionBar;
private String URL="https://hn.algolia.com/api/v1/search_by_date?tags=story&page=1";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerItems=(RecyclerView)findViewById(R.id.recyclerItems);
        actionBar = getSupportActionBar();
        actionBar.show();
        new GetHitsDataTask().execute();

    }

    class GetHitsDataTask extends AsyncTask<String, Void, String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            /* Showing the ProgressBar, Making the main design GONE */

        }

        protected String doInBackground(String... args) {
            String response = HttpRequest.excuteGet(URL);
            return response;
        }

        @Override
        protected void onPostExecute(String result) {


            try {

                JSONObject mainObj= new JSONObject(result);
                JSONArray jsonArray = mainObj.getJSONArray(AppConstants.API_KEY_MAIN_OBJ_LIST);
                ArrayList<HitEntry> arrayResponseList= new ArrayList<>();
                for (int i=0;i<jsonArray.length();i++)
                {
                    HitEntry newObj= new HitEntry();
                    newObj.createdAt=((JSONObject)jsonArray.get(i)).getString(AppConstants.API_KEY_CREATED_AT);
                    newObj.postTitle=((JSONObject)jsonArray.get(i)).getString(AppConstants.API_KEY_TITLE);
                    arrayResponseList.add(newObj);
                }
                recyclerItems.setAdapter(new ItemAdapter(arrayResponseList, MainActivity.this, new Callback() {
                    @Override
                    public void onCallback(Object data) {
                        ArrayList itemsSelectedList = (ArrayList) data;
                        actionBar.setTitle(getString(R.string.item_selected,((ArrayList) data).size()));
                    }
                }));

                LinearLayoutManager llm = new LinearLayoutManager(MainActivity.this);
                llm.setOrientation(LinearLayoutManager.VERTICAL);
                recyclerItems.setLayoutManager(llm);



            } catch (JSONException e) {

            }

        }
    }
}
