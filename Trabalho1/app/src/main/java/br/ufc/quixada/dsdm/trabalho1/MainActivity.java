package br.ufc.quixada.dsdm.trabalho1;

import android.content.Intent;
import android.media.MediaPlayer;
import android.view.Menu;
import android.view.MenuItem;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

public class MainActivity extends AppCompatActivity {

    private final static Integer NUM_PAGES = 2;

    private MediaPlayer mediaPlayer;
    private ViewPager2 viewPager;
    private TabLayout tabLayout;
    private FloatingActionButton floatingButton;

    private FragmentStateAdapter fragmentAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // https://www.youtube.com/watch?v=V1ocJmXeQ28
        mediaPlayer = MediaPlayer.create(this, R.raw.sleep);

        // https://developer.android.com/training/animation/screen-slide-2
        viewPager = findViewById(R.id.pager);
        fragmentAdapter = new ScreenSlidePagerAdapter(this);
        viewPager.setAdapter(fragmentAdapter);

        // https://developer.android.com/guide/navigation/navigation-swipe-view-2
        // https://stackoverflow.com/questions/57603881/unresolved-reference-tablayoutmediator
        tabLayout = findViewById(R.id.tab_layout);
        new TabLayoutMediator(tabLayout, viewPager,
                (tab, position) -> tab.setText(
                    position == 0?R.string.header_done_todo:R.string.header_ongoing_todo
                )).attach();

        // https://developer.android.com/guide/topics/ui/floating-action-button
        floatingButton = findViewById(R.id.floatingActionButton);
        floatingButton.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, CreateTodoActivity.class);
            startActivity(intent);
        });
    }

    @Override
    public void onBackPressed() {
        if (viewPager.getCurrentItem() == 0) {
            // If the user is currently looking at the first step, allow the system to handle the
            // Back button. This calls finish() on this activity and pops the back stack.
            super.onBackPressed();
        } else {
            // Otherwise, select the previous step.
            viewPager.setCurrentItem(viewPager.getCurrentItem() - 1);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Integer id = item.getItemId();
        if (id.equals(R.id.settings)) {
            Intent settingIntent = new Intent(this, SettingsActivity.class);
            startActivityForResult(settingIntent, 1);
        }

        if (id.equals(R.id.playSound)) {
            if (mediaPlayer.isPlaying()) {
                mediaPlayer.pause();
                item.setTitle(R.string.txt_play_sound);
            }
            else {
                mediaPlayer.start();
                item.setTitle(R.string.txt_stop_sound);
            }

        }

        return super.onOptionsItemSelected(item);
    }

    private static class ScreenSlidePagerAdapter extends FragmentStateAdapter {
        public ScreenSlidePagerAdapter(FragmentActivity fa) {
            super(fa);
        }

        @NonNull
        @Override
        public Fragment createFragment(int position) {
            if (position == 0) return new DoneTodoFragment();
            else return new OngoingTodoFragment();
        }

        @Override
        public int getItemCount() {
            return NUM_PAGES;
        }
    }

}
