package br.ufc.quixada.dsdm.trabalho4;

import android.content.Intent;
import android.view.Menu;
import android.view.MenuItem;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;
import br.ufc.quixada.dsdm.trabalho4.Adapters.ChatTabAdapter;
import br.ufc.quixada.dsdm.trabalho4.Auth.LoginActivity;
import br.ufc.quixada.dsdm.trabalho4.Fragments.GroupsFragment;
import br.ufc.quixada.dsdm.trabalho4.Fragments.UsersFragment;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    TabLayout tabLayout;
    ViewPager2 viewPager;
    ChatTabAdapter chatTabAdapter;

    FirebaseAuth mAuth = FirebaseAuth.getInstance();
    List<String> titles = new ArrayList<>();

    @Override
    protected void onStart() {
        super.onStart();
        if (mAuth.getCurrentUser() == null) {
            Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
            startActivity(intent);
            finish();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        viewPager = findViewById(R.id.view_pager);
        tabLayout = findViewById(R.id.tab_layout);
        chatTabAdapter = new ChatTabAdapter(this);

        addTab(new UsersFragment(), "Conversas Diretas");
        addTab(new GroupsFragment(), "Conversas em Grupo");

        viewPager.setAdapter(chatTabAdapter);
        new TabLayoutMediator(tabLayout, viewPager,
            (tab, position) -> tab.setText(titles.get(position))
        ).attach();
    }

    @Override
    public void onBackPressed() {
        int currentTab = viewPager.getCurrentItem();
        if (currentTab == 0) super.onBackPressed();
        else viewPager.setCurrentItem(currentTab - 1);
    }

    public void addTab(Fragment fragment, String title) {
        chatTabAdapter.addTab(fragment);
        titles.add(title);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.logout) {
            mAuth.signOut();
            startActivity(new Intent(this, LoginActivity.class));
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}