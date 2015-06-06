package br.edu.ifce.swappers.swappers.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

import br.edu.ifce.swappers.swappers.R;
import br.edu.ifce.swappers.swappers.model.ReferencedComponentItem;


public class ReferencedLibrariesActivity extends AppCompatActivity {

    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_referenced_libraries);

        this.toolbar = (Toolbar) findViewById(R.id.toolbar);
        this.setUpActivityToolbar();

        ArrayList<ReferencedComponentItem> dataSource = this.createDataSource();
        ArrayAdapter<ReferencedComponentItem> arrayAdapter = this.createArrayAdapter(dataSource);

        ListView listView = (ListView) findViewById(R.id.referenced_libraries_list_view);
        listView.setAdapter(arrayAdapter);
    }

    private ArrayList<ReferencedComponentItem> createDataSource(){
        ReferencedComponentItem floatingActionButtonItem      = new ReferencedComponentItem("Floating Action Button", "https://goo.gl/aEY1bV");
        ReferencedComponentItem materialNavigationDrawerItem  = new ReferencedComponentItem("Material Design Navigation drawers", "https://goo.gl/ykgWB4");
        ReferencedComponentItem circularImageViewItem         = new ReferencedComponentItem("Circular Image View", "https://goo.gl/MyCNfw");
        ReferencedComponentItem circularImageButtonItem       = new ReferencedComponentItem("Circular Image Button", "https://goo.gl/ZOucGN");
        ReferencedComponentItem appIntroItem                  = new ReferencedComponentItem("App Intro", "https://goo.gl/Vyho8J");
        ReferencedComponentItem backgroundImageItem           = new ReferencedComponentItem("Background Image", "http://goo.gl/Savtu8");

        ArrayList<ReferencedComponentItem> references = new ArrayList<>();
        references.add(floatingActionButtonItem);
        references.add(materialNavigationDrawerItem);
        references.add(circularImageButtonItem);
        references.add(circularImageViewItem);
        references.add(appIntroItem);
        references.add(backgroundImageItem);

        return references;
    }

    private ArrayAdapter<ReferencedComponentItem> createArrayAdapter(final ArrayList<ReferencedComponentItem> dataSource){
        ArrayAdapter<ReferencedComponentItem> referencedLibraryItemArrayAdapter;

        referencedLibraryItemArrayAdapter = new ArrayAdapter<ReferencedComponentItem>(getApplicationContext(), android.R.layout.simple_list_item_2, android.R.id.text1, dataSource){
            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                View rootView = super.getView(position, convertView, parent);

                TextView titleTextView    = (TextView) rootView.findViewById(android.R.id.text1);
                TextView subtitleTextView = (TextView) rootView.findViewById(android.R.id.text2);

                String titleText    = dataSource.get(position).getLibraryName();
                String subtitleText = dataSource.get(position).getLibraryLink();

                titleTextView.setText(titleText);
                titleTextView.setTextAppearance(getApplicationContext(), android.R.style.TextAppearance_Medium_Inverse);

                subtitleTextView.setText(subtitleText);
                subtitleTextView.setTextAppearance(getApplicationContext(), android.R.style.TextAppearance_Small_Inverse);

                return rootView;
            }
        };

        return referencedLibraryItemArrayAdapter;
    }

    private void setUpActivityToolbar(){
        if (this.toolbar != null){
            this.setSupportActionBar(this.toolbar);

            try {
                getSupportActionBar().setDisplayHomeAsUpEnabled(true);
                getSupportActionBar().setDisplayShowHomeEnabled(true);
            }
            catch (NullPointerException e){
                Log.e("ReferencedLibsActivity", "Null Pointer Exception when setDisplayHomeAsUpEnabled as true");
            }
        }
    }
}
