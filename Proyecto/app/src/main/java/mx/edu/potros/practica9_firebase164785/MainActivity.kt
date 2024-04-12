package mx.edu.potros.practica9_firebase164785

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import com.google.firebase.database.ChildEventListener
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase

class MainActivity : AppCompatActivity() {
    private val userRef = FirebaseDatabase.getInstance().getReference("Users")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        var btnSave:Button = findViewById<Button>(R.id.save_button)
        btnSave.setOnClickListener { saveMarkFromForm() }

        userRef.addChildEventListener(object : ChildEventListener{
            override fun onCancelled(databaseError: DatabaseError){}
            override fun onChildMoved(dataSnapshot: DataSnapshot, previousName: String?) {}
            override fun onChildChanged(dataSnapshot: DataSnapshot, previousName: String?) {}
            override fun onChildRemoved(snapshot: DataSnapshot) {}

            override fun onChildAdded(dataSnapshot: DataSnapshot, p1: String?) {
                val value = dataSnapshot.getValue()

                if (value is String){
                } else if (value is User){
                    val usuario= value

                    if (usuario != null) {writeMark(usuario)}
                }
            }
        })
    }
    private fun saveMarkFromForm(){
        var name: EditText = findViewById<EditText>(R.id.et_name)
        var lastName:EditText = findViewById<EditText>(R.id.et_lastName)
        var age: EditText = findViewById<EditText>(R.id.et_age)

        val usuario = User(
            name.text.toString(),
            lastName.text.toString(),
            age.text.toString()
        )

        userRef.push().setValue(usuario)
    }
    private fun writeMark(mark:User){
        var listV: TextView = findViewById<TextView>(R.id.list_textView)
        val text = listV.text.toString() + mark.toString() + "/n"
        listV.text = text
    }
}