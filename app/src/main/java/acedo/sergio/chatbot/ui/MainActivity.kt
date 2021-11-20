package acedo.sergio.chatbot.ui

import acedo.sergio.chatbot.R
import acedo.sergio.chatbot.data.Message
import acedo.sergio.chatbot.utils.BotResponse
import acedo.sergio.chatbot.utils.Constans
import acedo.sergio.chatbot.utils.Time
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.*

class MainActivity : AppCompatActivity() {
    private val TAG = "MainActivity"


    var messageList = mutableListOf<Message>()

    private lateinit var adapter: MessangingAdapter
    private val botList = listOf("Peter","Francesca","Sergio","Luigi","Igor")


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        recyclerView()

        clickEvents()

        val random = (0..3).random()
        customBotMessage("Hello! Today you are speaking with ${botList[random]}, how may i help?")
    }
    private fun clickEvents() {

        //Send messages
        findViewById<Button>(R.id.btn_send).setOnClickListener {
            sendMessage()
        }

        //Scroll back to correct position when user clicks on text view
        findViewById<EditText>(R.id.et_message).setOnClickListener{
            GlobalScope.launch {
                delay(100)
                withContext(Dispatchers.Main){
                    findViewById<RecyclerView>(R.id.rv_mesages).scrollToPosition(adapter.itemCount -1)
                }
            }
        }



    }
    private fun recyclerView() {
        adapter = MessangingAdapter()
        findViewById<RecyclerView>(R.id.rv_mesages).adapter = adapter
        findViewById<RecyclerView>(R.id.rv_mesages).layoutManager = LinearLayoutManager(applicationContext)

    }

    private fun sendMessage() {
        val message = findViewById<EditText>(R.id.et_message).text.toString()
        val timeStamp = Time.timeStamp()

        if (message.isNotEmpty()){
            //adds
            messageList.add(Message(message, Constans.SEND_ID,timeStamp))
            findViewById<EditText>(R.id.et_message).setText("")

            adapter.insertMessage(Message(message, Constans.SEND_ID,timeStamp))
            findViewById<RecyclerView>(R.id.rv_mesages).scrollToPosition(adapter.itemCount -1)
            botResponse(message)
        }

    }

    private fun botResponse(message: String) {
        val timeStamp = Time.timeStamp()
        GlobalScope.launch {
            delay(1000)
            withContext(Dispatchers.Main)
            {
                val response = BotResponse.basicResponses(message)


                messageList.add(Message(message, Constans.RECEIVE_ID,timeStamp))

                adapter.insertMessage(Message(response, Constans.RECEIVE_ID,timeStamp))

                findViewById<RecyclerView>(R.id.rv_mesages).scrollToPosition(adapter.itemCount - 1)

                when (response){
                    Constans.OPEN_GOOGLE -> {
                        val site =Intent (Intent.ACTION_VIEW)
                        site.data = Uri.parse("https://www.google.com/")
                        startActivity(site)
                    }
                    Constans.OPEN_SEARCH->{
                        val site =Intent (Intent.ACTION_VIEW)
                        val searchTerm: String? = message.substringAfterLast("search")
                        site.data = Uri.parse("https://www.google.com/search?&q=$searchTerm")
                        startActivity(site)
                    }
                }


            }
        }
    }

    private fun customBotMessage(message: String) {
        GlobalScope.launch {
            delay(1000)
            withContext(Dispatchers.Main) {
                val timeStamp = Time.timeStamp()

                messageList.add(Message(message, Constans.RECEIVE_ID, timeStamp))

                adapter.insertMessage(Message(message, Constans.RECEIVE_ID, timeStamp))

                findViewById<RecyclerView>(R.id.rv_mesages).scrollToPosition(adapter.itemCount - 1)
            }
        }
    }
    override fun onStart() {
        super.onStart()
        //In case there are messages, scroll to bottom when re-opening app
        GlobalScope.launch {
            delay(100)
            withContext(Dispatchers.Main){
                findViewById<RecyclerView>(R.id.rv_mesages).scrollToPosition(adapter.itemCount -1)
            }
        }
    }




}