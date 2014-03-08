package mx.suh.crro.kuwinda.fragment;

import mx.suh.crro.kuwinda.R;
import mx.suh.crro.kuwinda.application.KuwindaApplication;
import mx.suh.crro.kuwinda.datastorage.GroupLoader;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.LocalBroadcastManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;

public class CreateGroupFragment extends Fragment {
	private LocalBroadcastManager _lbm;
	private GroupLoader _gLoader;
	private ImageButton _saveBtn, _cancelBtn;
	private ListView _lv;
	private EditText _groupNameEt;
	
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		_lbm = LocalBroadcastManager.getInstance(getActivity());
		
	}
	
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.create_group_layout, null);
		_gLoader = KuwindaApplication.getGroupLoader();
		
		_saveBtn = (ImageButton) v.findViewById(R.id.saveGrpBtn); 
		_cancelBtn = (ImageButton) v.findViewById(R.id.cancelGrpBtn);
		_groupNameEt = (EditText) v.findViewById(R.id.groupName);
				
		_lv = (ListView) v.findViewById(R.id.websitesForGroup);
		
		//I should probably restore the session at some point.
		_cancelBtn.setOnClickListener(new OnClickListener() {
			
			public void onClick(View v) {
				//This should take us back to where we were (i.e the groups)
				getActivity().getFragmentManager().popBackStack();
			}
		});
		
		
		
		
		
		return v;
	}
}
