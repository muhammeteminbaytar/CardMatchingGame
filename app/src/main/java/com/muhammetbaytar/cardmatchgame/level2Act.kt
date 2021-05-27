package com.muhammetbaytar.cardmatchgame

import android.content.Context
import android.content.DialogInterface
import android.media.AudioAttributes
import android.media.AudioManager
import android.media.SoundPool
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.view.View
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import kotlinx.android.synthetic.main.activity_level1.*
import kotlinx.android.synthetic.main.activity_level2.*
import kotlin.system.exitProcess

class level2Act : AppCompatActivity() {

    val matchList=ArrayList<Int>()
    val clickMatchList=ArrayList<Int>()
    val knewCards=ArrayList<Int>()
    private lateinit var oyunSuresi:CountDownTimer
    var puan=0

    lateinit var soundPool: SoundPool
    var sound1 : Int = 0
    var sound2 : Int = 0
    var sound3 : Int = 0
    var sound4 : Int = 0
    var sound5 : Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_level2)



        firstRun()

        lvl2gameTimer()

        audioCreater()
    }

    private fun firstRun(){
        // 1 den 6 ye kadar sayıları rasgele sıralar


        for (i in 1..20){

            var matchVal=(1..10).random()

            while (diziAdetDonder(matchVal,matchList)==2){
                matchVal=(1..10).random()
            }
            matchList.add(matchVal)

        }
        //--------------------------------------------------

        // rasgele resim atanır
        val iwList= listOf<ImageView>(lvl2iw1,lvl2iw2,lvl2iw3,lvl2iw4,lvl2iw5,lvl2iw6,
            lvl2iw7,lvl2iw8,lvl2iw9,lvl2iw10,lvl2iw11,lvl2iw12,lvl2iw13,lvl2iw14,
                lvl2iw15,lvl2iw16,lvl2iw17,lvl2iw18,lvl2iw19,lvl2iw20)

        val imageList= listOf<Int>(R.drawable.c1,R.drawable.c2,R.drawable.c3,R.drawable.c4,R.drawable.c5,
                R.drawable.c6,R.drawable.c7,R.drawable.c8,R.drawable.c9,R.drawable.c10)

        for (j in 0..19){
            iwList.get(j).setImageResource(imageList.get(matchList.get(j)-1))
        }
        for (iw in iwList) iw.isClickable=false


        firstShowTimer(iwList)

    }

    fun audioCreater(){

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP){
            var audioAttrs: AudioAttributes = AudioAttributes.Builder()
                .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
                .setUsage(AudioAttributes.USAGE_MEDIA)
                .build()

            soundPool = SoundPool.Builder()
                .setMaxStreams(2)
                .setAudioAttributes(audioAttrs)
                .build()
        }

        else{
            soundPool = SoundPool(2, AudioManager.STREAM_MUSIC, 0)
        }
        sound1 = soundPool.load(this, R.raw.eslesdi, 1)
        sound2 = soundPool.load(this, R.raw.click, 1)
        sound3 = soundPool.load(this, R.raw.eslesmedi, 1)
        sound4 = soundPool.load(this, R.raw.basarili, 1)
        sound5 = soundPool.load(this, R.raw.basarisiz, 1)

    }

    fun sharedPreferences(){
        val sharedPreferences=this.getSharedPreferences("com.muhammetbaytar.cardmatchgame", Context.MODE_PRIVATE)
        if(sharedPreferences.getInt("h3Score",0)<puan){
            Toast.makeText(this, "New Best Score !", Toast.LENGTH_SHORT).show()
            sharedPreferences.edit().putInt("h3Score",puan).apply()
        }


    }


    fun lvl2iwclick(view: View){

        if(sound2 != 0) {
            soundPool.play(sound2, .5f, .5f, 1, 0, 1.0f)
        }

        //seçilen resimlerin gösterimi yapılıyor
        val imageList= listOf<Int>(R.drawable.c1,R.drawable.c2,R.drawable.c3,R.drawable.c4,R.drawable.c5,
                R.drawable.c6,R.drawable.c7,R.drawable.c8,R.drawable.c9,R.drawable.c10)

        val iwList= listOf<ImageView>(lvl2iw1,lvl2iw2,lvl2iw3,lvl2iw4,lvl2iw5,lvl2iw6,
                lvl2iw7,lvl2iw8,lvl2iw9,lvl2iw10,lvl2iw11,lvl2iw12,lvl2iw13,lvl2iw14,
                lvl2iw15,lvl2iw16,lvl2iw17,lvl2iw18,lvl2iw19,lvl2iw20)

        var index=0
        var sonuc=0
        for (iw in iwList){
            if(iw==view){
                sonuc=index
            }
            index++
        }
        //  iwList.get(sonuc).isClickable=false

        //animasyon
        view.animate().apply {
            duration=500
            rotationYBy(360f).start()
        }


        iwList[sonuc].setImageResource(imageList[matchList[sonuc] -1])

//--------------------------------------------
        // seçilen iki resim doğru ve yanlış olma durumu kontrol ediliyor

        clickMatchList.add(sonuc)
        if(clickMatchList.size==1){
            iwList.get(sonuc).isClickable=false
        }
        else if(clickMatchList.size==2){
            iwList[clickMatchList[0]].isClickable=false
            iwList[clickMatchList[1]].isClickable=false
            for (iw in iwList) iw.isClickable=false

        }

        val timer=object: CountDownTimer(800,800){
            override fun onTick(millisUntilFinished: Long) {
            }

            override fun onFinish() {

                if(clickMatchList.size==2){
                    if(matchList[clickMatchList[0]]==matchList[clickMatchList[1]]){

                        if(sound1 != 0) {
                            soundPool.play(sound1, .5f, .5f, 1, 0, 1.0f)
                        }

                        //animasyon
                        iwList[clickMatchList[0]].animate().apply {
                            duration=500
                            rotationXBy(-360f).start()
                        }
                        iwList[clickMatchList[1]].animate().apply {
                            duration=500
                            rotationXBy(-360f).start()
                        }



                        iwList[clickMatchList[0]].setImageResource(R.drawable.knew)
                        iwList[clickMatchList[1]].setImageResource(R.drawable.knew)
                        iwList.get(clickMatchList[0]).isClickable=false
                        iwList.get(clickMatchList[1]).isClickable=false

                        knewCards.add(clickMatchList[0])
                        knewCards.add(clickMatchList[1])

                    }else{

                        if(sound3 != 0) {
                            soundPool.play(sound3, .5f, .5f, 1, 0, 1.0f)
                        }

                        //animasyon
                        iwList[clickMatchList[0]].animate().apply {
                            duration=500
                            rotationXBy(360f).start()
                        }
                        iwList[clickMatchList[1]].animate().apply {
                            duration=500
                            rotationXBy(360f).start()
                        }
                        iwList[clickMatchList[0]].setImageResource(R.drawable.qcard)
                        iwList[clickMatchList[1]].setImageResource(R.drawable.qcard)
                        iwList.get(clickMatchList[0]).isClickable=true
                        iwList.get(clickMatchList[1]).isClickable=true
                    }

                    clickMatchList.clear()
                    for (iw in iwList){
                        if(!knewCards.contains(iwList.indexOf(iw))){
                            iw.isClickable=true

                        }
                    }
                    if(knewCards.size==20){
                        oyunSuresi.cancel()
                        alertDialogCreater(true)
                    }

                }
            }

        }
        timer.start()

    }

    private fun diziAdetDonder(aranan: Int, dizi: ArrayList<Int>):Int{
        var adet=0
        for (a in dizi){
            if(a==aranan){
                adet++
            }
        }
        return adet
    }

    private fun firstShowTimer (iwList:List<ImageView>){
        val timer=object: CountDownTimer(3000,1000){
            override fun onTick(millisUntilFinished: Long) {
            }

            override fun onFinish() {
                for (j in 0..19){
                    iwList[j].setImageResource(R.drawable.qcard)
                    for (iw in iwList) iw.isClickable=true

                    oyunSuresi.start()

                }
            }

        }
        timer.start()
    }

    private fun lvl2gameTimer(){

        oyunSuresi = object: CountDownTimer(50000, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                lvl2gametimer.text=(millisUntilFinished/1000).toString()
                puan=(millisUntilFinished/100).toInt()
            }

            override fun onFinish() {
                if(knewCards.size==19){
                    oyunSuresi.cancel()
                    alertDialogCreater(true)
                }else{
                    alertDialogCreater(false)

                }

            }

        }


    }

    fun alertDialogCreater(basarilimi:Boolean){

        sharedPreferences()
        val builder = AlertDialog.Builder(this@level2Act)

        if(basarilimi){

            if(sound4 != 0) {
                soundPool.play(sound4, .5f, .5f, 1, 0, 1.0f)
            }

            builder.setTitle("Tebrikler")
            builder.setCancelable(false)
            builder.setMessage(lvl2gametimer.text.toString() +" Saniye Erken Bitirdiniz \n Puanınız : "+puan)



        }else{
            if(sound5 != 0) {
                soundPool.play(sound5, .5f, .5f, 1, 0, 1.0f)
            }

            builder.setTitle("Süre Bitti")
            builder.setMessage("Hay Aksi!")
            builder.setCancelable(false)
        }
        builder.setPositiveButton("Tamam", DialogInterface.OnClickListener { dialog, which ->

            exitProcess(-1)
        })

        builder.show()
    }

    override fun onBackPressed() {
        oyunSuresi.cancel()
        super.onBackPressed()
    }

}