package acedo.sergio.chatbot.ui


import acedo.sergio.chatbot.R
import acedo.sergio.chatbot.data.Message
import acedo.sergio.chatbot.utils.Constans
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class MessangingAdapter:RecyclerView.Adapter<MessangingAdapter.MessageViewHolder>() {
var messageList = mutableListOf<Message>()

    inner class  MessageViewHolder(itemView: View):RecyclerView.ViewHolder(itemView){

        init {
            itemView.setOnClickListener{

            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MessageViewHolder {
        return  MessageViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.message_item,parent,false))
    }

    override fun onBindViewHolder(holder: MessageViewHolder, position: Int) {
        val currentMessage= messageList[position]
        when(currentMessage.id){
            Constans.SEND_ID->{
                holder.itemView.findViewById<TextView>(R.id.tv_message).apply {
                    text  = currentMessage.message
                    visibility = View.VISIBLE
                }
                holder.itemView.findViewById<TextView>(R.id.tv_bot_message).visibility = View.GONE
            }
            Constans.RECEIVE_ID -> {
                holder.itemView.findViewById<TextView>(R.id.tv_bot_message).apply {
                    text= currentMessage.message
                    visibility = View.VISIBLE
                }
                holder.itemView.findViewById<TextView>(R.id.tv_message).visibility = View.GONE
            }

        }
    }

    override fun getItemCount(): Int {

       return   messageList.size
    }

    fun insertMessage(message: Message){
        this.messageList.add(message)
        notifyItemInserted(messageList.size)
        notifyDataSetChanged()
    }

}