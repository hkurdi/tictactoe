package dev.whoishlk.tictactoe

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.children
import androidx.lifecycle.lifecycleScope
import dev.whoishlk.tictactoe.databinding.ActivityMainBinding
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private var flag = 0
    private var tileCounter = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.apply {
           btnReset.setOnClickListener {
               newScore(TAG_RESET)
               newGame()
           }
        }




        enableEdgeToEdge()
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

    }

    fun oxClick(view: View) {
        val btn = view as ImageView
        binding.apply {
            if(btn.drawable == null) {
                tileCounter++
                when(flag) {
                    0 -> {
                        btn.setImageResource(R.drawable.ic_o)
                        btn.tag = TAG_O
                        cardO.strokeWidth = 0
                        cardX.strokeWidth = 2
                        flag = 1
                    }

                    1 -> {
                        btn.setImageResource(R.drawable.ic_x)
                        btn.tag = TAG_X
                        cardO.strokeWidth = 2
                        cardX.strokeWidth = 0
                        flag = 0
                    }
                }
                    lifecycleScope.launch {
                        if (iv1.tag == iv2.tag && iv2.tag == iv3.tag && iv3.tag != null) {
                            newScore(iv1.tag.toString())
                            withAnimation(iv1, iv2, iv3)
                        } else if (iv4.tag == iv5.tag && iv5.tag == iv6.tag && iv6.tag != null) {
                            newScore(iv4.tag.toString())
                            withAnimation(iv4, iv5, iv6)
                        } else if (iv7.tag == iv8.tag && iv8.tag == iv9.tag && iv9.tag != null) {
                            newScore(iv7.tag.toString())
                            withAnimation(iv7, iv8, iv9)
                        } else if (iv1.tag == iv5.tag && iv5.tag == iv9.tag && iv9.tag != null) {
                            newScore(iv1.tag.toString())
                            withAnimation(iv1, iv5, iv9)
                        } else if (iv3.tag == iv5.tag && iv5.tag == iv7.tag && iv7.tag != null) {
                            newScore(iv3.tag.toString())
                            withAnimation(iv3, iv5, iv7)
                        } else if (iv1.tag == iv4.tag && iv4.tag == iv7.tag && iv7.tag != null) {
                            newScore(iv1.tag.toString())
                            withAnimation(iv1, iv4, iv7)
                        } else if (iv2.tag == iv5.tag && iv5.tag == iv8.tag && iv8.tag != null) {
                            newScore(iv2.tag.toString())
                            withAnimation(iv2, iv5, iv8)
                        } else if (iv3.tag == iv6.tag && iv6.tag == iv9.tag && iv9.tag != null) {
                            newScore(iv3.tag.toString())
                            withAnimation(iv3, iv6, iv9)
                        } else if(tileCounter == 9) {
                            Toast.makeText(this@MainActivity , "This game has no winner!", Toast.LENGTH_SHORT).show()
                            newGame()
                            }
                }
            }

        }
    }

    private suspend fun withAnimation(viewOne: View, viewTwo: View, viewThree: View) {
        viewOne.setBackgroundResource(R.drawable.board_back_green)
        delay(200)
        viewTwo.setBackgroundResource(R.drawable.board_back_green)
        delay(200)
        viewThree.setBackgroundResource(R.drawable.board_back_green)
        delay(200)
        newGame()
    }

    @SuppressLint("SetTextI18n")
    private fun newScore(tag: String) {
        when(tag) {
            TAG_X->{
                val xScore = binding.boardXCount.text.toString().toInt()
                binding.boardXCount.text = (xScore + 1).toString()
            }
            TAG_O->{
                val oScore = binding.boardOCount.text.toString().toInt()
                binding.boardOCount.text = (oScore + 1).toString()
            }
            TAG_RESET->{
                binding.boardOCount.text = "0"
                binding.boardXCount.text = "0"
            }
        }
    }

    private fun newGame() {
        flag = 0
        tileCounter = 0
        binding.grid.children.filterIsInstance<ImageView>().forEach { iv ->
            iv.setImageDrawable(null)
            iv.tag = null
            iv.setBackgroundResource(R.drawable.board_back)
        }
        binding.cardO.strokeWidth = 2
        binding.cardX.strokeWidth = 0
    }

}