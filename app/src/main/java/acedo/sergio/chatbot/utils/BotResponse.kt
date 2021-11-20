package acedo.sergio.chatbot.utils

import java.lang.Exception
import java.sql.Date
import java.sql.Timestamp
import java.text.SimpleDateFormat

object BotResponse {
    fun basicResponses(_message: String):String{

        val random =(0..2).random()
        val message = _message.toLowerCase()



        return when{
            //Flips a coin
            message.contains("flip")&& message.contains("coin")-> {
                val r = (0..1).random()
                val result = if (r==0) "heads" else "tails"
                "I fliped a coin and it landed on $result"
            }
            //Math calculations
            message.contains("solve")-> {
            val equation: String?  =message.substringAfterLast("solve")
                return try{
                    val answer  = SolveMath.solveMath(equation ?: "0")
                    "$answer"

                }catch (e: Exception){
                    "Sorry i can't solve that."
                }
            }
            //Hello
            message.contains("hello")-> {
                when(random){
                    0 -> "Hello there!"
                    1 -> "Sup"
                    2 -> "Que tranza"
                    else -> "error" }
                }
            //How are you?
            message.contains("how are you")-> {
                when(random){
                    0 -> "I'm doing fine, thanks!"
                    1 -> "I'm hungry..."
                    2 -> "pretty good how about you"
                    else -> "error" }
            }

            //What time is it?
            message.contains("time") &&  message.contains("?")-> {
               val timeStamp =Timestamp(System.currentTimeMillis())
                val sdf =SimpleDateFormat("dd/MM/yyyy HH:mm")
                val date  =sdf.format(Date(timeStamp.time))
                date.toString()

            }

            //Open Google
            message.contains("open") &&  message.contains("google")-> {
                Constans.OPEN_GOOGLE
            }

            //Search on the internet
            message.contains("search") -> {
                Constans.OPEN_SEARCH
            }
            //Program doesnt understand
            else -> {
                when  (random){
                    0 -> "I don't understand..."
                    1 -> "Try asking me something different"
                    2 -> "Idk"
                   else-> "error"

                }
            }
        }
    }
}