package com.example.laptop.icraudi;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;


public class carChoose extends Activity
{
    Spinner spncars;
    private List<String> carNames;
    private ArrayList<String> additional;
    RadioGroup radioGroup,rgdialog;
    ImageView img1;
    RadioButton rDizel,rBenzin,rHybrid;
    Button btnforward;
    CustomAdapter adapter1=null;
    final Context context = this;

   public static  String selectedCar="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.choosecar);

        radioGroup = (RadioGroup)findViewById(R.id.radioGroup);
        rDizel = (RadioButton)findViewById(R.id.radiodiesel);
        img1 = (ImageView)findViewById(R.id.imageView);
        rBenzin = (RadioButton)findViewById(R.id.radiobenzin);
        rHybrid = (RadioButton)findViewById(R.id.radiohibrid);
        spncars = (Spinner)findViewById(R.id.spinnerCars);
        btnforward = (Button)findViewById(R.id.btnForward);



            new GetData().execute();
        showListView();
        listeners();
    }

    public void showListView()
    {

        ArrayList<Model> gearList = new ArrayList<Model>();
        Model v1 = new Model("Xenon svetla");
        gearList.add(v1);
        Model v2 = new Model("Parking senzori");
        gearList.add(v2);
        Model v3 = new Model("Svetla za maglu");
        gearList.add(v3);
        Model v4 = new Model("Tempomat");
        gearList.add(v4);
        Model v5 = new Model("Kozna sedista");
        gearList.add(v5);



        adapter1 = new CustomAdapter(this,
                R.layout.list, gearList);
        ListView listView1 = (ListView) findViewById(R.id.listiq);
        listView1.setAdapter(adapter1);
    }

    private void listeners()
    {

        spncars.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedCar = spncars.getSelectedItem().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        btnforward.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String temp = "";
                String temp2=selectedCar;
                int selectedradio = radioGroup.getCheckedRadioButtonId();

                if(selectedradio == rDizel.getId())
                {
                    temp="Dizel";
                }
                else if(selectedradio==rBenzin.getId())
                {
                    temp="Benzin";
                }
                else
                {
                    temp="Hybrid";
                }

                final Dialog dialog = new Dialog(context);
                dialog.setContentView(R.layout.customdialog);
                dialog.setTitle("Audi Srbija");

                TextView tx1 = (TextView)findViewById(R.id.textView);
                TextView tx3 = (TextView)findViewById(R.id.textView3);
                TextView tx5 = (TextView)findViewById(R.id.textView5);

                final RadioButton r1 = (RadioButton)dialog.findViewById(R.id.radioButton2);
                final RadioButton r2 = (RadioButton) dialog.findViewById(R.id.radioButton3);
                final RadioButton r3 = (RadioButton) dialog.findViewById(R.id.radioButton4);
                final RadioButton r4 = (RadioButton) dialog.findViewById(R.id.radioButton5);
                final RadioButton r5 = (RadioButton) dialog.findViewById(R.id.radioButton6);

            final TextView textCar = (TextView)dialog.findViewById(R.id.txtSelectedCar);
                textCar.setText(temp2);
                if(temp2.contentEquals("AUDI TT"))
                {
                    r4.setVisibility(View.INVISIBLE);
                    r5.setVisibility(View.INVISIBLE);
                    //ovo bi trebalo da se uradi sa sqlite bazom,tj da se range konjskih snaga ispisuje dinamicki iz baze u zavisnosti od modela automobila
                    //select 140ks,160ks... from horsepower(tabela) where value="A4" (jedno od resenja)
                }

            final TextView fuel = (TextView)dialog.findViewById(R.id.fuelType);
                fuel.setText(temp);

            final TextView textGear = (TextView)dialog.findViewById(R.id.txtAdditionalGear);

                String s = additional.toString();
                s = s.substring(1,s.length()-1);
                textGear.setText(s);




            final    Button btnDismiss = (Button)dialog.findViewById(R.id.btnSend);
                btnDismiss.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });


                dialog.show();
            }

        });
    }

    private class GetData extends AsyncTask<Void,Void,Void>
    {
        @Override
        protected Void doInBackground(Void... params) {

            try
            {
             DatabaseHelper db = new DatabaseHelper(getApplicationContext());

                carNames= db.getCarsData();


            }
            catch (Exception e)
            {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);


            ArrayAdapter<String> adapter = new ArrayAdapter<String>(carChoose.this
                    ,android.R.layout.simple_spinner_item,carNames);
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spncars.setAdapter(adapter);


        }
    }

    private class CustomAdapter extends ArrayAdapter<Model>
    {
        private ArrayList<Model> gearList;


        public CustomAdapter(Context context, int textViewResourceId,
                             ArrayList<Model> gearList) {
            super(context, textViewResourceId, gearList);
            this.gearList = new ArrayList<Model>();
            this.gearList.addAll(gearList);
        }

        private class ViewHoldere {
            TextView code;
            CheckBox name;
        }


        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            ViewHoldere holder = null;
            additional=new ArrayList<String>();
            if (convertView == null) {
                LayoutInflater vi = (LayoutInflater)getSystemService(
                        Context.LAYOUT_INFLATER_SERVICE);
                convertView = vi.inflate(R.layout.list, null);

                holder = new ViewHoldere();

                holder.name = (CheckBox) convertView.findViewById(R.id.checkBoxGear);
                convertView.setTag(holder);

                holder.name.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                        if(isChecked)
                        {
                            additional.add(buttonView.getText().toString());
                        }
                        else
                        {
                            additional.remove(buttonView.getText().toString());
                        }

                    }
                });

            }
            else {
                holder = (ViewHoldere) convertView.getTag();
            }

            Model country = gearList.get(position);

            holder.name.setText((CharSequence) country.getName());
          //  holder.name.setChecked(country.isSelected());
            holder.name.setTag(country);

            return convertView;

        }
    }
}
