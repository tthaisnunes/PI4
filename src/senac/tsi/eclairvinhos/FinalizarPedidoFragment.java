package senac.tsi.eclairvinhos;

import senac.tsi.eclairvinhos.adapter.CarrinhoAdapter;
import senac.tsi.eclairvinhos.model.Singleton;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;

public class FinalizarPedidoFragment extends Fragment {
	// Log tag
	private static final String TAG = CarrinhoFragment.class.getSimpleName();

	// Movies json url
	//private static final String url = "http://pieclair.azurewebsites.net/4Sem/webservices/listarCategoria.php";
	private ProgressDialog pDialog;   
	Singleton sing = Singleton.getInstance();
	private ListView listView;
	private CarrinhoAdapter adapter;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		super.onCreateView(inflater, container, savedInstanceState);
		View view = inflater.inflate(R.layout.fragment_finalizar, container, false);
		Spinner spinner = (Spinner) view.findViewById(R.id.spin_pgto);
		// Create an ArrayAdapter using the string array and a default spinner layout
		ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getActivity(),
		        R.array.forma_pgto, android.R.layout.simple_spinner_item);
		// Specify the layout to use when the list of choices appears
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		// Apply the adapter to the spinner
		spinner.setAdapter(adapter);
		
		return view;
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

}
