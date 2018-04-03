package com.ghn.android.ui.feeds;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.app.Fragment;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.ghn.android.R;
import com.ghn.android.data.model.Feeds;
import com.ghn.android.data.model.News;
import com.ghn.android.news.LatestFragment;
import com.ghn.android.news.adapter.LatestAdapter;
import com.ghn.android.news.item.ItemLatest;
import com.ghn.android.news.util.AlertDialogManager;
import com.ghn.android.news.util.Constant;
import com.ghn.android.news.util.JsonUtils;
import com.ghn.android.views.DepthPageTransformer;
import com.ghn.android.views.VerticalViewPager;
import com.google.gson.Gson;
import com.thefinestartist.finestwebview.FinestWebView;
import com.thoughtw.retail.animation.GuillotineAnimation;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import nl.changer.polypicker.Config;
import nl.changer.polypicker.ImagePickerActivity;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.Response;

public class FeedsTestActivity extends AppCompatActivity implements View.OnClickListener, com.ghn.android.util.ToggleToolbarViewListener {

    private static final String EXTRA_TRIGGER_SYNC_FLAG =
            "uk.co.ribot.androidboilerplate.ui.main.MainActivity.EXTRA_TRIGGER_SYNC_FLAG";
    private static final long RIPPLE_DURATION = 250;
    private static final int INTENT_REQUEST_GET_IMAGES = 13;
    private static final MediaType MEDIA_TYPE_PNG = MediaType.parse("image/jpg");
    public com.ghn.android.views.ExpandablePanel expandablePanel;
    public LinearLayout moreItemValueLayout;
    @Bind(R.id.toolBarMenu)
    Toolbar toolBarMenu;
    @Bind(R.id.epaper)
    ImageView ivEpaper;
    @Bind(R.id.refresh)
    ImageView ivrefresh;
    List<Fragment> fragments;
    @Bind(R.id.vertical_viewpager)
    VerticalViewPager viewPager;
   // @Bind(R.id.progress)
   // ProgressBar progressBar;
    @Bind(R.id.content_hamburger)
    View contentHamburger;
    @Bind(R.id.root)
    FrameLayout root;
    @Bind(R.id.tittle_fragment)
    com.ghn.android.views.FontTextView mTittleFragment;
    private GuillotineAnimation guillotineAnimation;
    private ImageView homeIcon;
    private com.ghn.android.views.FontTextView homeTittle;
    private ImageView electronicsIcon;
    private com.ghn.android.views.FontTextView electronicsTittle;
    private ImageView furnitureIcon;
    private com.ghn.android.views.FontTextView furnitureTittle;
    private ArrayList<com.ghn.android.data.model.News> listAllContent;
    private com.ghn.android.ui.feeds.FeedsAdapter feedsAdapter;
    private com.ghn.android.views.FontTextView tvMoreItem;
    //  private LinearLayout ajab_gajab_group, desh_group, videsh_group, rashifal_group, health_tips_group, election_keeda_group;
    private ImageView ivMoreItem;
    Response response;

    LatestAdapter objAdapter;
    ArrayList<String> allListnews,allListnewsCatName;
    ArrayList<String> allListNewsCId,allListNewsCatId,allListNewsCatImage,allListNewsCatName,allListNewsHeading,allListNewsImage,allListNewsDes,allListNewsDate;
    String[] allArraynews,allArraynewsCatName;
    String[] allArrayNewsCId,allArrayNewsCatId,allArrayNewsCatImage,allArrayNewsCatName,allArrayNewsHeading,allArrayNewsImage,allArrayNewsDes,allArrayNewsDate;
    private News objAllBean;
    private int columnWidth;
    JsonUtils util;
    int textlength = 0;
    /**
     * Return an Intent to start this Activity.
     * triggerDataSyncOnCreate allows disabling the background sync service onCreate. Should
     * only be set to false during testing.
     */
    public static Intent getStartIntent(Context context, boolean triggerDataSyncOnCreate) {
        Intent intent = new Intent(context, FeedsActivity.class);
        intent.putExtra(EXTRA_TRIGGER_SYNC_FLAG, triggerDataSyncOnCreate);
        return intent;
    }

    @OnClick(R.id.refresh)
    public void refreshContent() {

        getNewsFromApi();
    }

    private void getNewsFromApi() {

//        new AsyncTask<Void, Void, Void>() {
//            @Override
//            protected Void doInBackground(Void... params) {
//                RequestBody formBody = null;
//                if (com.ghn.android.BoilerplateApplication.preference.getCartItems() == null) {
//                    formBody = new FormBody.Builder()
//                            .add("limit", "500")
//                            //    .add("langId", "0")
//                            .build();
//
//                } else {
//                    formBody = new FormBody.Builder()
//                            .add("updateBy", com.ghn.android.BoilerplateApplication.preference.getLastSync())
//                            .add("limit", "500")
//                            // .add("langId", "0")
//                            .build();
//                }
//                try {
//                    response = new com.ghn.android.util.ApiService(formBody).getNews();
//                    if (response.code() == 200) {
//                        Gson gson = new Gson();
//                        Feeds feeds = gson.fromJson(response.body().charStream(), Feeds.class);
//                        ArrayList<News> list = (ArrayList<News>) feeds.getData().getNews();
//                        processNewsData(list);
//                    }
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//                return null;
//            }
//
//            @Override
//            protected void onPostExecute(Void aVoid) {
//                super.onPostExecute(aVoid);
//                removeFragmentsFromPager();
//                //listAllContent = com.ghn.android.BoilerplateApplication.preference.getCartItems();
//                listAllContent=new ArrayList<News>();
//                if (listAllContent != null)
//                    setAllContent();
//                updateItemSelection(homeTittle, getResources().getString(R.string.activity));
//
//            }
//        }.execute();

        util=new JsonUtils(this);


        if (JsonUtils.isNetworkAvailable(this)) {
            new MyTask().execute(Constant.LATEST_URL);
        } else {
            showToast("No Network Connection!!!");
//			 alert.showAlertDialog(getActivity(), "Internet Connection Error",
//	                    "Please connect to working Internet connection", false);
        }
    }

    private void removeAlreadyExistingItemsInData(ArrayList<News> list) {
        ArrayList<News> storeListInPreference = com.ghn.android.BoilerplateApplication.preference.getCartItems();
        if (list.size() != 0)
            for (News newsNew : list) {
                for (Iterator<News> it = storeListInPreference.iterator(); it.hasNext(); ) {
                    News s = it.next();
                    if (s.getId().equals(newsNew.getId())) {
                        it.remove();
                    }
                }
            }
        com.ghn.android.BoilerplateApplication.preference.setCartItems(storeListInPreference);
    }

    private void processNewsData(ArrayList<News> list) {
        com.ghn.android.BoilerplateApplication.preference.setLastSync(new SimpleDateFormat("dd-MM-yyyy hh:mm:ss").format(new Date()));
        if (com.ghn.android.BoilerplateApplication.preference.getCartItems() == null) {
            com.ghn.android.BoilerplateApplication.preference.setCartItems(list);
        } else {
            removeAlreadyExistingItemsInData(list);
            int sumOfItems = com.ghn.android.BoilerplateApplication.preference.getCartItems().size() + list.size();
            if (sumOfItems > 500) {
                int rowsToRemove = sumOfItems - 500;
                com.ghn.android.BoilerplateApplication.preference.setCartItems((ArrayList<com.ghn.android.data.model.News>) com.ghn.android.BoilerplateApplication.preference.getCartItems().
                        subList(0, com.ghn.android.BoilerplateApplication.preference.getCartItems().size() - (rowsToRemove + 1)));
            }
            list.addAll(com.ghn.android.BoilerplateApplication.preference.getCartItems());
            com.ghn.android.BoilerplateApplication.preference.setCartItems(list);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_cards);
        ButterKnife.bind(this);
        ivEpaper.setOnClickListener(this);
        setUpToolBar();
        fragments = new ArrayList<>();
        listAllContent=new ArrayList<News>();
        getNewsFromApi();
        feedsAdapter = new com.ghn.android.ui.feeds.FeedsAdapter(getSupportFragmentManager(), fragments);
        viewPager.setAdapter(feedsAdapter);
        viewPager.setOverScrollMode(View.OVER_SCROLL_NEVER);
        viewPager.setPageTransformer(true, new DepthPageTransformer());
        viewPager.getAdapter().notifyDataSetChanged();
        viewPager.destroyDrawingCache();
        View guillotineMenu = LayoutInflater.from(this).inflate(R.layout.drawer_items_menu, null);
        expandablePanel = (com.ghn.android.views.ExpandablePanel) guillotineMenu.findViewById(R.id.expadable_panel);
        moreItemValueLayout = (LinearLayout) guillotineMenu.findViewById(R.id.more_root);
        expandablePanel.setCollapsedHeight(moreItemValueLayout.getHeight());
        expandablePanel.setOnExpandListener(new com.ghn.android.views.ExpandablePanel.OnExpandListener() {
            public void onCollapse(View handle, View content) {
                if (tvMoreItem == null) {
                    LinearLayout btn = (LinearLayout) handle;
                    tvMoreItem = (com.ghn.android.views.FontTextView) btn.findViewById(R.id.more_text);
                    ivMoreItem = (ImageView) btn.findViewById(R.id.more_icon);
                }
                tvMoreItem.setText("MORE");
                ivMoreItem.setImageResource(R.drawable.ic_action_more);
            }

            public void onExpand(View handle, View content) {
                if (tvMoreItem == null) {
                    LinearLayout btn = (LinearLayout) handle;
                    tvMoreItem = (com.ghn.android.views.FontTextView) btn.findViewById(R.id.more_text);
                    ivMoreItem = (ImageView) btn.findViewById(R.id.more_icon);
                }
                tvMoreItem.setText("LESS");
                ivMoreItem.setImageResource(R.drawable.ic_action_less);
            }
        });
        setUpOnClicks(guillotineMenu);
        setUpGuillotineAnimation(guillotineMenu);
        root.addView(guillotineMenu);
       // listAllContent = com.ghn.android.BoilerplateApplication.preference.getCartItems();
        allListnews=new ArrayList<String>();
        allListnewsCatName=new ArrayList<String>();
        allListNewsCId=new ArrayList<String>();
        allListNewsCatId=new ArrayList<String>();
        //allListNewsCatImage=new ArrayList<String>();
        allListNewsCatName=new ArrayList<String>();
        allListNewsHeading=new ArrayList<String>();
        allListNewsImage=new ArrayList<String>();
        allListNewsDes=new ArrayList<String>();
        allListNewsDate=new ArrayList<String>();

        allArraynews=new String[allListnews.size()];
        allArraynewsCatName=new String[allListnewsCatName.size()];
        allArrayNewsCId=new String[allListNewsCId.size()];
        allArrayNewsCatId=new String[allListNewsCatId.size()];
        //allArrayNewsCatImage=new String[allListNewsCatImage.size()];
        allArrayNewsCatName=new String[allListNewsCatName.size()];
        allArrayNewsHeading=new String[allListNewsHeading.size()];
        allArrayNewsImage=new String[allListNewsImage.size()];
        allArrayNewsDes=new String[allListNewsDes.size()];
        allArrayNewsDate=new String[allListNewsDate.size()];
        //getNewsFromApi();
        // if (listAllContent != null)
         //   setAllContent();
       // progressBar.setVisibility(View.GONE);
       // viewPager.setVisibility(View.VISIBLE);
        updateItemSelection(furnitureIcon, furnitureTittle, R.drawable.ic_action_furniture_active, getResources().getString(R.string.tenth_jobs));
        if (listAllContent != null)
            setSelectedCategory("7");
//        try {
//           viewPager.setOffscreenPageLimit(4);
//        } catch (Exception e) {
//            //ignore
//        }
    }

    private void setUpGuillotineAnimation(View guillotineMenu) {
        initItemOfGuillotine(guillotineMenu);
        guillotineAnimation = new GuillotineAnimation.GuillotineBuilder(guillotineMenu, guillotineMenu.findViewById(R.id.guillotine_hamburger), contentHamburger)
                .setStartDelay(RIPPLE_DURATION)
                .setActionBarViewForAnimation(toolBarMenu)
                .setClosedOnStart(true)
                .build();
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();

//    mMainPresenter.detachView();
    }


    private void setUpOnClicks(View guillotineMenu) {
        guillotineMenu.findViewById(R.id.activity_group).setOnClickListener(this);
        guillotineMenu.findViewById(R.id.electronic_group).setOnClickListener(this);
        guillotineMenu.findViewById(R.id.furniture_group).setOnClickListener(this);
        guillotineMenu.findViewById(R.id.ajab_gajab_group).setOnClickListener(this);
        guillotineMenu.findViewById(R.id.desh_group).setOnClickListener(this);
        guillotineMenu.findViewById(R.id.videsh_group).setOnClickListener(this);
        guillotineMenu.findViewById(R.id.rashifal_group).setOnClickListener(this);
        guillotineMenu.findViewById(R.id.health_tips_group).setOnClickListener(this);
        guillotineMenu.findViewById(R.id.election_keeda_group).setOnClickListener(this);
        guillotineMenu.findViewById(R.id.contactus_group).setOnClickListener(this);
        guillotineMenu.findViewById(R.id.upload_news_group).setOnClickListener(this);
        guillotineMenu.findViewById(R.id.epaper_group).setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.activity_group:
                updateItemSelection(homeIcon, homeTittle, R.drawable.ic_action_house_active, getResources().getString(R.string.activity));
                if (listAllContent != null)
                    setAllContent();
                break;
            case R.id.electronic_group:
                updateItemSelection(electronicsIcon, electronicsTittle, R.drawable.ic_action_plug_active, getResources().getString(R.string.admit_cards));
                if (listAllContent != null)
                    setSelectedCategory("21");
                break;
            case R.id.furniture_group:
                updateItemSelection(furnitureIcon, furnitureTittle, R.drawable.ic_action_furniture_active, getResources().getString(R.string.govt_jobs));
                if (listAllContent != null)
                    setSelectedCategory("20");
                break;
            case R.id.ajab_gajab_group:
                updateItemSelection(furnitureIcon, furnitureTittle, R.drawable.ic_action_furniture_active, getResources().getString(R.string.bank_jobs));
                if (listAllContent != null)
                    setSelectedCategory("19");
                break;
            case R.id.desh_group:
                updateItemSelection(furnitureIcon, furnitureTittle, R.drawable.ic_action_furniture_active, getResources().getString(R.string.railway_jobs));
                if (listAllContent != null)
                    setSelectedCategory("18");
                break;
            case R.id.videsh_group:
                updateItemSelection(furnitureIcon, furnitureTittle, R.drawable.ic_action_furniture_active, getResources().getString(R.string.ssc_jobs));
                if (listAllContent != null)
                    setSelectedCategory("10");
                break;
            case R.id.rashifal_group:
                updateItemSelection(furnitureIcon, furnitureTittle, R.drawable.ic_action_furniture_active, getResources().getString(R.string.psc_jobs));
                if (listAllContent != null)
                    setSelectedCategory("9");
                break;
            case R.id.health_tips_group:
                updateItemSelection(furnitureIcon, furnitureTittle, R.drawable.ic_action_furniture_active, getResources().getString(R.string.teaching_jobs));
                if (listAllContent != null)
                    setSelectedCategory("8");
                break;
            case R.id.epaper:
                updateItemSelection(furnitureIcon, furnitureTittle, R.drawable.ic_action_furniture_active, getResources().getString(R.string.tenth_jobs));
                if (listAllContent != null)
                    setSelectedCategory("7");
                break;
            case R.id.election_keeda_group:
                updateItemSelection(furnitureIcon, furnitureTittle, R.drawable.ic_action_furniture_active, getResources().getString(R.string.results));
                if (listAllContent != null)
                    setSelectedCategory("6");
                break;
            case R.id.contactus_group:
                updateItemSelection(furnitureIcon, furnitureTittle, R.drawable.ic_action_furniture_active, getResources().getString(R.string.defence_jobs));
                if (listAllContent != null)
                    setSelectedCategory("5");
                break;
            case R.id.upload_news_group:
                startActivity(new Intent(this, UploadNewsActivity.class));
                break;
            case R.id.epaper_group:
                updateItemSelection(furnitureIcon, furnitureTittle, R.drawable.ic_action_furniture_active, getResources().getString(R.string.tenth_jobs));
                if (listAllContent != null)
                    setSelectedCategory("7");
                break;
        }
    }



    private void updateItemSelection(ImageView activeImage, com.ghn.android.views.FontTextView activeTittle, int icon, String toolBarTittle) {
        resetViewsItems();
        //   activeImage.setImageDrawable(ResourcesCompat.getDrawable(getResources(), icon, null));
        activeTittle.setTextColor(Color.parseColor("#30d1d5"));
        mTittleFragment.setText(toolBarTittle);
        guillotineAnimation.close();
    }


    private void resetViewsItems() {
        homeIcon.setImageDrawable(ResourcesCompat.getDrawable(getResources(), R.drawable.ic_action_home, null));
        electronicsIcon.setImageDrawable(ResourcesCompat.getDrawable(getResources(), R.drawable.ic_action_ads, null));
        furnitureIcon.setImageDrawable(ResourcesCompat.getDrawable(getResources(), R.drawable.ic_action_jobs, null));
        homeTittle.setTextColor(Color.parseColor("#ffffff"));
        electronicsTittle.setTextColor(Color.parseColor("#ffffff"));
        furnitureTittle.setTextColor(Color.parseColor("#ffffff"));
    }

    private void initItemOfGuillotine(View view) {
        electronicsIcon = (ImageView) view.findViewById(R.id.elec_icon);
        furnitureIcon = (ImageView) view.findViewById(R.id.furniture_icon);
        homeIcon = (ImageView) view.findViewById(R.id.home_icon);
        electronicsTittle = (com.ghn.android.views.FontTextView) view.findViewById(R.id.elec_tittle);
        furnitureTittle = (com.ghn.android.views.FontTextView) view.findViewById(R.id.furniture_tittle);
        homeTittle = (com.ghn.android.views.FontTextView) view.findViewById(R.id.home_tittle);
    }


    private void setAllContent() {
        removeFragmentsFromPager();
        for (int i = 0; i < listAllContent.size(); i++) {
            com.ghn.android.data.model.News news = listAllContent.get(i);
            fragments.add(ContentFragment.newInstance(news, i));
        }
        Log.i("fragmentsize",""+fragments.size());
        feedsAdapter.notifyDataSetChanged();
    }

    private void setJobs() {
        removeFragmentsFromPager();
        for (int i = 0; i < listAllContent.size(); i++) {
            com.ghn.android.data.model.News news = listAllContent.get(i);
            if (news.getContent_type().equals("20")) {
                fragments.add(ContentFragment.newInstance(news, i));
            }
        }
        Log.i("fragmentsize",""+fragments.size());
        feedsAdapter.notifyDataSetChanged();
    }

    private void setSelectedCategory(String categoryType) {
        removeFragmentsFromPager();
        for (int i = 0; i < listAllContent.size(); i++) {
            com.ghn.android.data.model.News news = listAllContent.get(i);

            if (news.getCategory_id() != null && news.getCategory_id().equals(categoryType)) {
                fragments.add(ContentFragment.newInstance(news, i));
            }
        }
        feedsAdapter.notifyDataSetChanged();
    }

    @Override
    public void toggleVisibilityOfToolbar() {
        if (toolBarMenu.getVisibility() == View.VISIBLE) {
            toolBarMenu.setVisibility(View.GONE);
        } else {
            toolBarMenu.setVisibility(View.VISIBLE);
        }
    }

    private void setAds() {
        removeFragmentsFromPager();
        for (int i = 0; i < listAllContent.size(); i++) {
            com.ghn.android.data.model.News news = listAllContent.get(i);
            if (news.getContent_type().equals("21")) {
                fragments.add(ContentFragment.newInstance(news, i));
            }
        }
        Log.i("fragmentsize",""+fragments.size());
        feedsAdapter.notifyDataSetChanged();
    }

    private void removeFragmentsFromPager() {
        fragments.clear();
    }

    private void setUpToolBar() {
        if (toolBarMenu != null) {
            setSupportActionBar(toolBarMenu);
            getSupportActionBar().setTitle(null);
        }
    }

    private	class MyTask extends AsyncTask<String, Void, String> {

        ProgressDialog pDialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            pDialog = new ProgressDialog(FeedsTestActivity.this);
            pDialog.setMessage("Loading...");
            pDialog.setCancelable(false);
            pDialog.show();
        }

        @Override
        protected String doInBackground(String... params) {
            return JsonUtils.getJSONString(params[0]);
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);

            if (null != pDialog && pDialog.isShowing()) {
                pDialog.dismiss();
            }

            if (null == result || result.length() == 0) {
                showToast("Server Connection Error");
                //alert.showAlertDialog(getActivity(), "Server Connection Error",
                  //      "May Server Under Maintaines Or Low Network", false);

            } else {

                try {
                    JSONObject mainJson = new JSONObject(result);
                    JSONArray jsonArray = mainJson.getJSONArray(Constant.CATEGORY_ARRAY_NAME);
                    JSONObject objJson = null;
                    for (int i = 0; i < jsonArray.length(); i++) {
                        objJson = jsonArray.getJSONObject(i);

                        News news = new News();
                        news.setYoutube_url("https://www.youtube.com/watch?v=abqavYCnXiw");
                        news.setTitle(objJson.getString(Constant.CATEGORY_ITEM_NEWSHEADING));
                        news.setReadmore_url("https://www.google.com");
                        news.setLanguage_id("1");
                        news.setDescription(objJson.getString(Constant.CATEGORY_ITEM_NEWSDESCRI));
                        news.setDate_posted(objJson.getString(Constant.CATEGORY_ITEM_NEWSDATE));
                        news.setContent_type(objJson.getString(Constant.CATEGORY_ITEM_CAT_ID));
                        news.setNews_image(objJson.getString(Constant.CATEGORY_ITEM_NEWSIMAGE));
                        news.setId("1");
                        news.setCategory_id(objJson.getString(Constant.CATEGORY_ITEM_CID));
                        listAllContent.add(news);


                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
                for(int j=0;j<listAllContent.size();j++)
                {

                    objAllBean=listAllContent.get(j);

                    allListNewsCatId.add(objAllBean.getCategory_id());
                    allArrayNewsCatId=allListNewsCatId.toArray(allArrayNewsCatId);

                    allListNewsImage.add(String.valueOf(objAllBean.getNews_image()));
                    allArrayNewsImage=allListNewsImage.toArray(allArrayNewsImage);


                    allListNewsHeading.add(String.valueOf(objAllBean.getTitle()));
                    allArrayNewsHeading=allListNewsHeading.toArray(allArrayNewsHeading);

                    allListNewsDes.add(String.valueOf(objAllBean.getDescription()));
                    allArrayNewsDes=allListNewsDes.toArray(allArrayNewsDes);

                    allListNewsDate.add(String.valueOf(objAllBean.getDate_posted()));
                    allArrayNewsDate=allListNewsDate.toArray(allArrayNewsDate);



                }
            }

        }
    }
    public void showToast(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_LONG).show();
    }

    @Override
    public void onResume() {
        super.onResume();
        // Check should we need to refresh the fragment

            // refresh fragment
            updateItemSelection(homeIcon, homeTittle, R.drawable.ic_action_house_active, getResources().getString(R.string.activity));
            if (listAllContent != null)
                setAllContent();
    }

    @Override
    public void onStop() {
        super.onStop();
    }

    @Override
    protected void onStart() {
        super.onStart();
        updateItemSelection(homeIcon, homeTittle, R.drawable.ic_action_house_active, getResources().getString(R.string.activity));
        if (listAllContent != null)
            setAllContent();
    }


}