package com.gainwise.devprocesshelper

import android.app.ActivityManager
import android.content.Context
import android.content.Intent
import android.os.Build.VERSION
import android.os.Build.VERSION_CODES
import android.os.Bundle
import android.os.Handler
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.View
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*
import spencerstudios.com.bungeelib.Bungee



class MainActivity : AppCompatActivity() {
    var count = 0
    var num = 0
    var TAG = "com.gainwise.devprocesshelperINFO"
    var go = false


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        var runtime: Runtime = Runtime.getRuntime();
        var usedMemInMB: Long =(runtime.totalMemory() - runtime.freeMemory()) / 1048576L;
        var maxHeapSizeInMB: Long =runtime.maxMemory() / 1048576L;
        var availHeapSizeInMB: Long = maxHeapSizeInMB - usedMemInMB;
        tv.setText("Memory Available: $availHeapSizeInMB MB")

        if (intent.hasExtra("num")) {
            num = intent.getIntExtra("num", 0)
            if (num>0){
                ll.visibility = View.GONE
                start()
            }else {
                go = false
                ll.visibility = View.VISIBLE
            }
        }
        if (intent.hasExtra("go")) {
            go = intent.getBooleanExtra("go", false)

        }

        if (intent.hasExtra("count")) {
            count = intent.getIntExtra("count", 0)
            supportActionBar?.title = "Activity #$count"
        }


        Log.i(TAG, "SPAWNER Activity $count onCreate called")

    }

    override fun onDestroy() {
        super.onDestroy()
        Log.i(TAG, "SPAWNER Activity $count onDestroy called")
    }

    fun button1(v: View) {
        var id = v.id

        when(id){
            R.id.b10 ->{num = 10}
            R.id.b50 ->{num = 50}
            R.id.b100 ->{num = 100}
            else ->{}
        }
        go = true;

        var intent = Intent(this@MainActivity, MainActivity::class.java)
        intent.putExtra("count", ++count)
        intent.putExtra("num", --num)
        startActivity(intent);
        Bungee.fade(this@MainActivity)
        intent.putExtra("go", go)

    }

    fun button2(v: View) {

        if (VERSION_CODES.KITKAT <= VERSION.SDK_INT) {
            (this@MainActivity.getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager)
                    .clearApplicationUserData() // note: it has a return value!
        }



    }

    fun start(){
     var handler = Handler()
        handler.postDelayed(object : Runnable {
            override fun run() {
                var intent = Intent(this@MainActivity, MainActivity::class.java)
                intent.putExtra("count", ++count)
                intent.putExtra("go", go)
                intent.putExtra("num", --num)
                startActivity(intent);
                Bungee.fade(this@MainActivity)
            }
        }

        , 150)


    }

    override fun onBackPressed() {
     Toast.makeText(this@MainActivity, "The back button can not be used, hit the CLEAR ALL BUTTON", Toast.LENGTH_LONG).show()
    }

}
