package vortex.project.unify.Views.Classes

import android.os.AsyncTask
import android.widget.TextView

class AsyncFirebaseData (var diceNumber: Int, var mTextView: TextView) : AsyncTask<Void, Unit, String>() {
    override fun doInBackground(vararg voids: Void?): String? {
        return (1..diceNumber).random().toString()
    }

    override fun onPostExecute(result: String?) {
        mTextView.setText(result!!)
    }
}