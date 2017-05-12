package com.don.easy2readyoedge.main;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.MenuItem;

import com.don.easy2readyoedge.R;
import com.don.easy2readyoedge.apis.ApiService;
import com.don.easy2readyoedge.configs.ApiConfigs;
import com.don.easy2readyoedge.huijicomic.HuiJiComicFragment;
import com.don.easy2readyoedge.bookmark.BookmarkFragment;
import com.don.easy2readyoedge.home.HomeFragment;
import com.don.easy2readyoedge.morecomic.MoreComicFragment;

import com.orz.orzframework.logger.ORZLog;
import javax.inject.Inject;

import okhttp3.ResponseBody;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import rx.functions.Action0;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

import static dagger.internal.Preconditions.checkNotNull;

/**
 * Created by don on 10/21/16.
 */

public class MainActivity extends AppCompatActivity implements MainActivityContract.ViewModel,
  NavigationView.OnNavigationItemSelectedListener
  , HomeFragment.OnFragmentInteractionListener
  , HuiJiComicFragment.OnFragmentInteractionListener
  , BookmarkFragment.OnFragmentInteractionListener
  , MoreComicFragment.OnFragmentInteractionListener {

  private final static String FG_HOME = HomeFragment.class.getSimpleName();
  private final static String FG_HUIJI_COMIC = HuiJiComicFragment.class.getSimpleName();
  private final static String FG_MORE_COMIC = MoreComicFragment.class.getSimpleName();
  private final static String FG_BOOKMARK = BookmarkFragment.class.getSimpleName();
  private DrawerLayout drawer;
  private NavigationView navigationView;
  private Toolbar toolbar;
  @Inject
  MainActivityPresenter mPresenter;

  @Override
  protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
//    mPresenter = new MainActivityPresenter(this);
//    HuiJiComicActivityComponent mainActivityComponent = Dagg
    DaggerMainActivityComponent.builder()
      .mainModule(new MainModule(this))
      .build()
      .inject(this);

    mPresenter.initControl();
    mPresenter.setToolbar();
    mPresenter.showHome();
  }

  @Override
  public void onBackPressed() {
    mPresenter.showExitAlert();
  }

  @Override
  protected void onDestroy() {
    super.onDestroy();
  }

  @Override
  public boolean onNavigationItemSelected(MenuItem item) {
    switch (item.getItemId()) {
      case R.id.nav_home:
        mPresenter.showHome();
        break;
      case R.id.nav_comic:
        mPresenter.showHuiJiComic();
        break;
      case R.id.nav_bookmark:
        mPresenter.showBookMark();
        break;
      case R.id.nav_more_comic:
        mPresenter.showMoreComic();
        break;
    }

    DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
    drawer.closeDrawer(GravityCompat.START);
    return true;
  }

  private void exitApp() {
    super.onBackPressed();
  }

  @Override
  public void initControl() {
    drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
    navigationView = (NavigationView) findViewById(R.id.nav_view);
    toolbar = (Toolbar) findViewById(R.id.toolbar);

    navigationView.setNavigationItemSelectedListener(this);
  }

  @Override
  public void showHomePage() {
    HomeFragment fg = (HomeFragment) getSupportFragmentManager().findFragmentByTag(FG_HOME);
    FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
//    fgHomeFragment = (HomeFragment) getSupportFragmentManager().findFragmentByTag(FG_HOME);
    if (fg != null) {
      transaction.show(fg);
    } else {
      fg = new HomeFragment();
      transaction.add(R.id.fragment_container, fg, FG_HOME);
//      transaction.addToBackStack(FG_HOME);
    }
    transaction.commit();
  }

  @Override
  public void showHuiJiComicPage() {
    HuiJiComicFragment fg = (HuiJiComicFragment) getSupportFragmentManager().findFragmentByTag(FG_HUIJI_COMIC);
    FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
//    fgHuiJiComicFragment = (HuiJiComicFragment) getSupportFragmentManager().findFragmentByTag(FG_HUIJI_COMIC);
    if (fg != null) {
      transaction.show(fg);
    } else {
      fg = new HuiJiComicFragment();
      transaction.add(R.id.fragment_container, fg, FG_HUIJI_COMIC);
//      transaction.addToBackStack(FG_HUIJI_COMIC);
    }
    transaction.commit();
  }

  @Override
  public void showMoreComicPage() {

    MoreComicFragment fg = (MoreComicFragment) getSupportFragmentManager().findFragmentByTag(FG_MORE_COMIC);
    FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
//    fgMoreComicFragment = (MoreComicFragment) getSupportFragmentManager().findFragmentByTag(FG_MORE_COMIC);
    if (fg != null) {
      transaction.show(fg);
    } else {
      fg = new MoreComicFragment();
      transaction.add(R.id.fragment_container, fg, FG_MORE_COMIC);
//      transaction.addToBackStack(FG_MORE_COMIC);
    }
    transaction.commit();
  }

  @Override
  public void showBookMarkPage() {
    BookmarkFragment fg = (BookmarkFragment) getSupportFragmentManager().findFragmentByTag(FG_BOOKMARK);
    FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
//    fgBookmarkFragment = (BookmarkFragment) getSupportFragmentManager().findFragmentByTag(FG_BOOKMARK);
    if (fg != null) {
      transaction.show(fg);
    } else {
      fg = new BookmarkFragment();
      transaction.add(R.id.fragment_container, fg, FG_BOOKMARK);
//      transaction.addToBackStack(FG_BOOKMARK);
    }
    transaction.commit();
  }

  @Override
  public void hideHomePage() {
    HomeFragment fg = (HomeFragment) getSupportFragmentManager().findFragmentByTag(FG_HOME);
    FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
//    fgHomeFragment = (HomeFragment) getSupportFragmentManager().findFragmentByTag(FG_HOME);
    if (fg != null) {
      transaction.hide(fg);
    }
    transaction.commit();
  }

  @Override
  public void hideHuiJiComicPage() {
    HuiJiComicFragment fg = (HuiJiComicFragment) getSupportFragmentManager().findFragmentByTag(FG_HUIJI_COMIC);
    FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
//    fgHuiJiComicFragment = (HuiJiComicFragment) getSupportFragmentManager().findFragmentByTag(FG_HUIJI_COMIC);
    if (fg != null) {
      transaction.hide(fg);
    }
    transaction.commit();
  }

  @Override
  public void hideMoreComicPage() {
    MoreComicFragment fg = (MoreComicFragment) getSupportFragmentManager().findFragmentByTag(FG_MORE_COMIC);
    FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
//    fgMoreComicFragment = (MoreComicFragment) getSupportFragmentManager().findFragmentByTag(FG_MORE_COMIC);
    if (fg != null) {
      transaction.hide(fg);
    }
    transaction.commit();
  }

  @Override
  public void hideBookMarkPage() {
    BookmarkFragment fg = (BookmarkFragment) getSupportFragmentManager().findFragmentByTag(FG_BOOKMARK);
    FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

    if (fg != null) {
      transaction.hide(fg);
    }
    transaction.commit();
  }

  @Override
  public void showExitAlert() {
    new AlertDialog.Builder(this).setMessage("是否退出？")
      .setNegativeButton("取消", null)
      .setPositiveButton("确定", new DialogInterface.OnClickListener() {
        @Override
        public void onClick(DialogInterface dialogInterface, int i) {
          exitApp();
        }
      }).show();
  }

  @Override
  public void setToolbar() {
    setSupportActionBar(toolbar);
    toolbar.setTitle(getString(R.string.app_name));
    toolbar.setNavigationIcon(R.drawable.ic_action_home);
    toolbar.setNavigationOnClickListener(new android.view.View.OnClickListener() {
      @Override
      public void onClick(android.view.View view) {
        if (drawer.isDrawerOpen(Gravity.LEFT)) {
          drawer.closeDrawers();
        } else {
          drawer.openDrawer(Gravity.LEFT);
        }
      }
    });
    getBookListAndInfo("");
  }

//  @Override
//  public boolean onCreateOptionsMenu(Menu menu) {
//    getMenuInflater().inflate(R.menu.activity_main_menu, menu);
//    return super.onCreateOptionsMenu(menu);
//  }

//  @Override
//  public boolean onOptionsItemSelected(MenuItem item) {
//    switch (item.getItemId()) {
//      case android.R.id.home:
//        Toast.makeText(MainActivity.this, "home", Toast.LENGTH_LONG).show();
//        return true;
//      case R.id.action_setting:
////        Toast.makeText(MainActivity.this, "action_settings", Toast.LENGTH_LONG).show();
//        mPresenter.showChangeTypeAlert();
//        return true;
//    }
//    return super.onOptionsItemSelected(item);
//  }

  public void getBookListAndInfo(String bookUrl) {
    Retrofit retrofit = new Retrofit.Builder()
      .baseUrl(ApiConfigs.YOEDGE)
      .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
      .build();

    ApiService apiService = retrofit.create(ApiService.class);
    apiService.getArticle(bookUrl)
//      .subscribeOn(Schedulers.io())
//      .unsubscribeOn(Schedulers.io())
//      .observeOn(AndroidSchedulers.mainThread())
      .subscribeOn(Schedulers.io()) //指定耗时进程
      .observeOn(Schedulers.newThread()) //指定doOnNext执行线程是新线程
      .doOnNext(new Action1<ResponseBody>() {
        @Override
        public void call(ResponseBody responseBody) {
          ORZLog.i("don", "next 1");
        }
      })
//      .doOnError(new Action1<Throwable>() {
//        @Override
//        public void call(Throwable throwable) {
//          SelfLog.i("don", "error 1");
//        }
//      })
      .doOnNext(new Action1<ResponseBody>() {
        @Override
        public void call(ResponseBody responseBody) {
          ORZLog.i("don", "next 2");
        }
      })
//      .doOnError(new Action1<Throwable>() {
//        @Override
//        public void call(Throwable throwable) {
//          SelfLog.i("don", "error 2");
//        }
//      })
      .doOnCompleted(new Action0() {
        @Override
        public void call() {

        }
      }).count();
  }

  @Override
  protected void onResume() {
    super.onResume();
  }

//  @Override
//  public void setPresenter(MainActivityContract.Presenter presenter) {
//    mPresenter = checkNotNull(presenter);
//  }
}
