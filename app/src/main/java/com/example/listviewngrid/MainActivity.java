package com.example.listviewngrid;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewStub;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
public class MainActivity extends AppCompatActivity {
    private GridView gridView;
    private GridViewAdapter gridViewAdapter;
    private List<Product> productList;
    private int currentViewMode = 0;
    static final int VIEW_MODE_LISTVIEW = 0;
    static final int VIEW_MODE_GRIDVIEW = 1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //Inflate ViewStub before get view
        gridView = (GridView) findViewById(R.id.mygridview);
        getProductList();
        //Get current view mode in share reference
        SharedPreferences sharedPreferences = getSharedPreferences("ViewMode", MODE_PRIVATE);
        currentViewMode = sharedPreferences.getInt("currentViewMode", VIEW_MODE_LISTVIEW);//Default is view listview
        //Register item lick
        gridView.setOnItemClickListener(onItemClick);
        registerForContextMenu(gridView);
    }


    public List<Product> getProductList() {
        //pseudo code to get product, replace your code to get real product here
        productList = new ArrayList<>();
        productList.add(new Product(R.drawable.mtp, "Title 1", "This is description 1"));
        productList.add(new Product(R.drawable.mtp, "Title 1", "This is description 1"));
        productList.add(new Product(R.drawable.mtp, "Title 1", "This is description 1"));
        productList.add(new Product(R.drawable.mtp, "Title 1", "This is description 1"));
        productList.add(new Product(R.drawable.mtp, "Title 1", "This is description 1"));
        gridViewAdapter = new GridViewAdapter(this, R.layout.grid_item, productList);
        gridView.setAdapter(gridViewAdapter);
        return productList;
    }
    AdapterView.OnItemClickListener onItemClick = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            //Do any thing when user click to item
            Toast.makeText(getApplicationContext(), productList.get(position).getTitle() + " - " + productList.get(position).getDescription(), Toast.LENGTH_SHORT).show();
        }
    };
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return super.onCreateOptionsMenu(menu);
    }
    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        MenuInflater inflater=getMenuInflater();
        inflater.inflate(R.menu.context,menu);
    }
    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        AdapterView.AdapterContextMenuInfo info= (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        gridView.setAdapter(gridViewAdapter);
        final int pos=info.position;
        switch (item.getItemId()){
            case R.id.xoa:
                final Dialog dialog=new Dialog(MainActivity.this);
                dialog.setContentView(R.layout.xoa);
                Button btn_xoa=dialog.findViewById(R.id.co);
                Button btn_confirm_no=dialog.findViewById(R.id.khong);
                btn_xoa.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        productList.remove(pos);
                        Toast.makeText(MainActivity.this,"Đã xóa thành công",Toast.LENGTH_SHORT).show();
                        gridViewAdapter.notifyDataSetChanged();
                        dialog.dismiss();
                    }
                });
                btn_confirm_no.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.cancel();
                    }
                });
                dialog.show();
        }
        return super.onContextItemSelected(item);
    }

}