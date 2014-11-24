package senac.tsi.eclairvinhos;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import senac.tsi.eclairvinhos.adapter.CustomListAdapter;
import senac.tsi.eclairvinhos.controller.VolleyController;
import senac.tsi.eclairvinhos.model.Produto;
import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.widget.ListView;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;

public class MovieActivity extends Activity {
	// Log tag
	private static final String TAG = MovieActivity.class.getSimpleName();

	// Movies json url
	private static final String url = "http://eclair.sistemasparainter.net/4Sem/webservices/listaProduto.php";
	private ProgressDialog pDialog;   
	private List<Produto> wineList = new ArrayList<Produto>();
	private ListView listView;
	private CustomListAdapter adapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_movie);

		listView = (ListView) findViewById(R.id.list);
		adapter = new CustomListAdapter(this, wineList);
		listView.setAdapter(adapter);

		pDialog = new ProgressDialog(this);
		// Showing progress dialog before making http request
		pDialog.setMessage("Loading...");
		pDialog.show();

		// changing action bar color
		//getActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#1b1b1b")));

		// Creating volley request obj
		StringRequest movieReq = new StringRequest(url,
				new Response.Listener<String>() {
					@Override
					public void onResponse(String response) {
						Log.d(TAG, response.toString());
						hidePDialog();
						
						// Parsing json
						//for (int i = 0; i < response.length(); i++) {
							try {
								response = response.replace("}", "},");
								response = "["+response+"]";
								response = response.replace("},]","}]");
								JSONArray jsonArr = new JSONArray(response);
								
								for (int i = 0; i < jsonArr.length(); i++) {
								JSONObject obj = jsonArr.getJSONObject(i);
								Produto vinho = new Produto();
								vinho.setIdProduto(Integer.parseInt(obj.get("idProduto").toString()));
								vinho.setNomeProduto(obj.getString("nomeProduto"));
								//vinho.setUrlImage(obj.getString("urlImage"));
								vinho.setPrecProduto(Double.parseDouble(obj.get("precProduto").toString()));
								vinho.setDescontoPromocao(Double.parseDouble(obj.get("descontoPromocao").toString()));
								vinho.setPrecFinal(Double.parseDouble(obj.get("precFinal").toString()));

								// adding movie to movies array
								wineList.add(vinho);
								}
							} catch (Exception e) {
								e.printStackTrace();
							

						}

						// notifying list adapter about data changes
						// so that it renders the list view with updated data
						adapter.notifyDataSetChanged();
					}
				}, new Response.ErrorListener() {
					@Override
					public void onErrorResponse(VolleyError error) {
						VolleyLog.d(TAG, "Error: " + error.getMessage());
						//System.out.println(error.getMessage());
						hidePDialog();

					}
				});
		movieReq.setRetryPolicy(new DefaultRetryPolicy(10000,DefaultRetryPolicy.DEFAULT_MAX_RETRIES , DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

		// Adding request to request queue
		VolleyController.getInstance().addToRequestQueue(movieReq);
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		hidePDialog();
	}

	private void hidePDialog() {
		if (pDialog != null) {
			pDialog.dismiss();
			pDialog = null;
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
