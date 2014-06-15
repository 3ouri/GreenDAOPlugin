package com.ouri.plugins;

import java.text.DateFormat;
import java.util.Date;

import org.apache.cordova.CallbackContext;
import org.apache.cordova.CordovaArgs;
import org.apache.cordova.CordovaPlugin;
import org.json.JSONException;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.widget.EditText;
import de.greenrobot.daoexample.DaoMaster;
import de.greenrobot.daoexample.DaoMaster.DevOpenHelper;
import de.greenrobot.daoexample.DaoSession;
import de.greenrobot.daoexample.Note;
import de.greenrobot.daoexample.NoteDao;

public class Plugin extends CordovaPlugin {
      private SQLiteDatabase db;
	    private EditText editText;
	    private DaoMaster daoMaster;
	    private DaoSession daoSession;
	    private NoteDao noteDao;
	    private Cursor cursor;
	    public Plugin() {
	    	 
		  }
	    
	    @Override
	    public boolean execute(String action, CordovaArgs args, CallbackContext callbackContext) throws JSONException {
		      DevOpenHelper helper = new DaoMaster.DevOpenHelper(this.cordova.getActivity().getApplicationContext(), "cordavo-notes-db", null);
	        db = helper.getWritableDatabase();
	        daoMaster = new DaoMaster(db);
	        daoSession = daoMaster.newSession();
	        noteDao = daoSession.getNoteDao();
		      System.out.println("hello");
		      if ( action.equals("count"))
		      {
		

	        String textColumn = NoteDao.Properties.Text.columnName;
	        String orderBy = textColumn + " COLLATE LOCALIZED ASC";
	        cursor = db.query(noteDao.getTablename(), noteDao.getAllColumns(), null, null, null, null, orderBy);
	       // String[] from = { textColumn, NoteDao.Properties.Comment.columnName };
	       // int[] to = { android.R.id.text1, android.R.id.text2 };

	        //cursor.getCount();
	        callbackContext.success("hello"+ cursor.getCount());
		}
		if ( action.equals("add"))
		{
			 String noteText = "helloFromPage";
		        //editText.setText("");

		        final DateFormat df = DateFormat.getDateTimeInstance(DateFormat.MEDIUM, DateFormat.MEDIUM);
		        String comment = "Added on " + df.format(new Date());
		        Note note = new Note(null, noteText, comment, new Date());
		        noteDao.insert(note);
		        Log.d("DaoExample", "Inserted new note, ID: " + note.getId());
		        callbackContext.success("hello done adding");
		       
		}
	       /* SimpleCursorAdapter adapter = new SimpleCursorAdapter(this, android.R.layout.simple_list_item_2, cursor, from,
	                to);
	        setListAdapter(adapter);*/

	        //editText = (EditText) findViewById(R.id.editTextNote);
		
		
		return super.execute(action, args, callbackContext);
	}

}
