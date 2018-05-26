package cdioil.feedbackmonkey.application;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cdioil.feedbackmonkey.R;

public class MainMenuActivity extends AppCompatActivity {
    private static final int CURRENT_WINDOW_HEIGHT = Resources.getSystem().getDisplayMetrics().heightPixels;
    private String[] menuOptions = {"Listar Inquéritos", "Scan QR Code", "Perfil", "Funcionalidade 4"};
    private int[] menuOptionImages = {R.drawable.survey_icon, R.drawable.code_search_icon, R.drawable.profile_icon,
            R.drawable.question_mark};
    private String[] menuBackgroundColors = {"#17a2da","#ffd05b","#f96955","#17a2da"};

    private int optionIndex;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);
        configureView();
    }

    private void configureView() {
        ListView mainMenuListView = findViewById(R.id.mainMenuListView);
        mainMenuListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                            switch(i){
                                case 0:
                                    startListSurveyActivity();
                                    break;
                                case 1:
                                    //Create intent to qr scan
                                    scanItemCode();
                                    break;
                                case 2:
                                    //Create intent to profile
                                    break;
                                case 3:
                                    //Create intent to something
                                    break;
                            }
            }
        });
        MainMenuItemListViewAdapter mainMenuItemListViewAdapter = new MainMenuItemListViewAdapter(MainMenuActivity.this);
        mainMenuListView.setAdapter(mainMenuItemListViewAdapter);
        for (int i = 0; i < menuOptions.length; i++) {
            mainMenuItemListViewAdapter.add(i);
        }
    }

    /**
     * MainMenuItemListViewAdapter that represents a custom adapter used for the ListView that holds the
     * main menu options
     *
     * @author <a href="1160936@isep.ipp.pt">Gil Durão</a>
     * @author <a href="1161191@isep.ipp.pt">Margarida Guerra</a>
     */
    private class MainMenuItemListViewAdapter extends ArrayAdapter<Integer> {

        /**
         * Builds a new {@link ListSurveyActivity.SurveyItemListViewAdapter} being adapted on a certain activity ListView
         *
         * @param activity    Activity with the activity which the ListView is being added the adapter
         * @param surveyItems List with a previous survey items predefined
         */
        public MainMenuItemListViewAdapter(Activity activity, List<Integer> surveyItems) {
            super(activity, R.layout.survey_item_list_row, surveyItems);
        }

        /**
         * Builds a new {@link ListSurveyActivity.SurveyItemListViewAdapter} being adapted on a certain activity ListView
         *
         * @param activity Activity with the activity which the ListView is being added the adapter
         */
        public MainMenuItemListViewAdapter(Activity activity) {
            super(activity, R.layout.survey_item_list_row);
        }

        /**
         * Returns a converted view item for the adapter on a certain item position
         *
         * @param position    Integer with the adapter item position
         * @param convertView View with the view being converted as item
         * @param parent      ViewGroup with the group parent
         * @return View with the converted view as a adapter item
         */
        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

            if (convertView == null) {
                convertView = LayoutInflater
                        .from(parent.getContext())
                        .inflate(R.layout.main_menu_option, parent, false);
            }
            if (optionIndex < menuOptionImages.length) {
                RelativeLayout relativeLayout = convertView.findViewById(R.id.mainMenuOptionRelativeLayout);
                relativeLayout.getLayoutParams().height = CURRENT_WINDOW_HEIGHT / 4;
                relativeLayout.setBackgroundColor(Color.parseColor(menuBackgroundColors[optionIndex]));

                ImageView imageView = convertView.findViewById(R.id.mainMenuOptionImgView);
                TextView textView = convertView.findViewById(R.id.mainMenuOptionTxtView);
                textView.setText(menuOptions[optionIndex]);
                imageView.setImageResource(menuOptionImages[optionIndex]);
                optionIndex++;
            }
            return convertView;
        }

    }

    /**
     * Starts the ListSurveyActivity activity with extra information being stored within the bundle, besides the authentication token.
     * @param bundleExtras extra information being stored in the bundle
     */
    private void startListSurveyActivity(Map<String, String> bundleExtras){
        String authenticationToken = getIntent().getExtras().getString("authenticationToken");
        Intent listSurveyActivityIntent = new Intent(MainMenuActivity.this,ListSurveyActivity.class);
        Bundle bundle = new Bundle();
        bundle.putString("authenticationToken",authenticationToken);
        for(Map.Entry<String, String> entry : bundleExtras.entrySet()){
            bundle.putString(entry.getKey(), entry.getValue());
        }
        listSurveyActivityIntent.putExtras(bundle);
        startActivity(listSurveyActivityIntent);
    }

    /**
     * Starts the ListSurveyActivity activity with authentication token as the single content within the bundle.
     */
    private void startListSurveyActivity(){
        String authenticationToken = getIntent().getExtras().getString("authenticationToken");
        Intent listSurveyActivityIntent = new Intent(MainMenuActivity.this,ListSurveyActivity.class);
        Bundle bundle = new Bundle();
        bundle.putString("authenticationToken",authenticationToken);
        listSurveyActivityIntent.putExtras(bundle);
        startActivity(listSurveyActivityIntent);
    }


    /**
     * Scans a SurveyItem's code.
     */
    private void scanItemCode() {

        IntentIntegrator integrator = new IntentIntegrator(this);

        //Set integrator settings here
        //Define which codes can be read
        integrator.setDesiredBarcodeFormats(IntentIntegrator.ALL_CODE_TYPES);
        integrator.setOrientationLocked(false);     //orientation is set within the manifest
        integrator.setPrompt("Por favor aponte a sua câmera para um código válido");

        integrator.initiateScan();
    }

    //Used by code scanner
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if(result != null) {
            if(result.getContents() == null) {
                Log.d("MainActivity", "Cancelled scan");
                Toast.makeText(this, "Leitura Cancelada", Toast.LENGTH_SHORT).show();
            } else {
                Log.d("MainActivity", "Scanned");

                String itemCode = result.getContents();

                if(!itemCode.trim().isEmpty()) {
                    Toast.makeText(this, "Código Lido: " + itemCode, Toast.LENGTH_LONG).show();

                    Map<String, String> bundleExtras = new HashMap<>();
                    bundleExtras.put("itemCode", itemCode);
                    startListSurveyActivity(bundleExtras);
                }
                else{
                    Toast.makeText(this, "Por favor leia um código válido", Toast.LENGTH_LONG).show();
                }
            }
        } else {
            // This is important, otherwise the result will not be passed to the fragment
            super.onActivityResult(requestCode, resultCode, data);
        }
    }
}
