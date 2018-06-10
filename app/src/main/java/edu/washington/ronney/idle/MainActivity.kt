package edu.washington.ronney.idle

import android.app.Activity
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView



class MainActivity : AppCompatActivity() {

    var money = 0;

    var appleCost = 1
    var appleIncrement = 1
    var appleAutomatic = false
    var applePerSecond = 0
    var applePerSecondCost = 50

    var treeCost = 1000
    var treeIncrement = 100
    var treeAutomatic = false
    var treePerSecond = 0
    var treePerSecondCost = 50000

    var farmCost = 10000
    var farmIncrement = 1000
    var farmAutomatic = false
    var farmPerSecond = 0
    var farmPerSecondCost = 5000000

    var t = Thread()

    var treeEnabled = false
    var farmEnabled = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val counter = findViewById<TextView>(R.id.count)


        // FIRST SCREEN ELEMENTS
        val apple = findViewById<Button>(R.id.apple)
        val tree = findViewById<Button>(R.id.tree)
        val farm = findViewById<Button>(R.id.farm)

        val store = findViewById<Button>(R.id.shop)
        val apc = findViewById<TextView>(R.id.applesPerClick)
        val aps= findViewById<TextView>(R.id.applesPerSecond)

        val tpc = findViewById<TextView>(R.id.treesPerClick)
        val tps= findViewById<TextView>(R.id.treesPerSecond)

        val fpc = findViewById<TextView>(R.id.farmsPerClick)
        val fps= findViewById<TextView>(R.id.farmsPerSecond)



        counter.setText("Apples: " + money)


        store.setOnClickListener() {
            t.interrupt() // this stops the thread
            val intent = Intent(this, shop::class.java)
            intent.putExtra("money", money)
            intent.putExtra("boughtApple", appleAutomatic)
            intent.putExtra("boughtTree", treeAutomatic)
            intent.putExtra("boughtFarm", farmAutomatic)
            intent.putExtra("aps", applePerSecond)
            intent.putExtra("tps", treePerSecond)
            intent.putExtra("fps", farmPerSecond)
            intent.putExtra("apsCost", applePerSecondCost)
            intent.putExtra("treeEnabled", treeEnabled)
            intent.putExtra("farmEnabled", farmEnabled)
            startActivityForResult(intent, 1)
        }

        apc.setText(Integer.toString(appleIncrement))
        aps.setText(Integer.toString(applePerSecond))

        tpc.setText(Integer.toString(treeIncrement))
        tps.setText(Integer.toString(treePerSecond))

        fpc.setText(Integer.toString(farmIncrement))
        fps.setText(Integer.toString(farmPerSecond))

        apple.setOnClickListener() {
            updateCount(counter, appleIncrement)
        }

        tree.setOnClickListener() {
            updateCount(counter, treeIncrement)
        }

        farm.setOnClickListener() {
            updateCount(counter, farmIncrement)
        }


            // UPGRADE SCREEN ELEMENTS
            val upgrade = findViewById<Button>(R.id.upgrade)
            val done = findViewById<Button>(R.id.done)
            val appleUpgrade = findViewById<Button>(R.id.appleUpgrade)
            val treeUpgrade = findViewById<Button>(R.id.treeUpgrade)
            val farmUpgrade = findViewById<Button>(R.id.farmUpgrade)



            upgrade.setOnClickListener() {
                store.setVisibility(View.INVISIBLE)
                apple.setVisibility(View.INVISIBLE)
                tree.setVisibility(View.INVISIBLE)
                farm.setVisibility(View.INVISIBLE)

                upgrade.setVisibility(View.INVISIBLE)
                appleUpgrade.setVisibility(View.VISIBLE)
                treeUpgrade.setVisibility(View.VISIBLE)
                farmUpgrade.setVisibility(View.VISIBLE)

                done.setVisibility(View.VISIBLE)
                appleUpgrade.setText("$"+appleCost)
                treeUpgrade.setText("$"+treeCost)
                farmUpgrade.setText("$"+farmCost)

                if (money > appleCost) {
                    appleUpgrade.setEnabled(true)
                }

                if (money > treeCost && treeEnabled) {
                    treeUpgrade.setEnabled(true)
                }

                if (money > farmCost && farmEnabled) {
                    farmUpgrade.setEnabled(true)
                }

            }

            appleUpgrade.setOnClickListener() {
                money -= appleCost
                counter.setText("Apples: " + money)
                appleCost = (appleCost * 3/2) + 1
                appleUpgrade.setText("$"+appleCost)
                appleIncrement = (appleIncrement * 12/10) + 1

                // set upgraded text
                apc.setText("" + appleIncrement)

                if (money < appleCost) {
                    appleUpgrade.setEnabled(false)
                }
            }

        treeUpgrade.setOnClickListener() {
            money -= treeCost
            counter.setText("Apples: " + money)
            treeCost = (treeCost * 3/2) + 1
            treeUpgrade.setText("$"+treeCost)
            treeIncrement = (treeIncrement * 12/10) + 100

            // set upgraded text
            tpc.setText("" + treeIncrement)

            if (money < treeCost) {
                treeUpgrade.setEnabled(false)
            }
        }

        farmUpgrade.setOnClickListener() {
            money -= farmCost
            counter.setText("Apples: " + money)
            farmCost = (farmCost * 3/2) + 1
            farmUpgrade.setText("$"+farmCost)
            farmIncrement = (farmIncrement * 12/10) + 100

            // set upgraded text
            fpc.setText("" + farmIncrement)

            if (money < treeCost) {
                farmUpgrade.setEnabled(false)
            }
        }



            done.setOnClickListener() {
                store.setVisibility(View.VISIBLE)
                apple.setVisibility(View.VISIBLE)
                tree.setVisibility(View.VISIBLE)
                farm.setVisibility(View.VISIBLE)
                upgrade.setVisibility(View.VISIBLE)
                appleUpgrade.setVisibility(View.INVISIBLE)
                treeUpgrade.setVisibility(View.INVISIBLE)
                farmUpgrade.setVisibility(View.INVISIBLE)
                done.setVisibility(View.INVISIBLE)
            }



        // TIMER
        t = object : Thread() {
            override fun run() {
                while (!isInterrupted) {
                    try {
                        Thread.sleep(1000)  //1000ms = 1 sec
                        runOnUiThread {
                            money = money + applePerSecond + treePerSecond + farmPerSecond
                            counter.setText("Apples: " + money)
                        }
                    } catch (e: InterruptedException) {
                        e.printStackTrace()
                        return
                    }
                }
            }
        }

    }

    fun updateCount(counter : TextView, increment : Int) {
        money = money + increment
        counter.setText("Apples: " + money)
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent) {
        if (requestCode == 1) {
            if (resultCode == Activity.RESULT_OK) {
                money = data.extras.getInt("money")
                appleAutomatic = data.extras.getBoolean("boughtApple")
                applePerSecond = data.extras.getInt("aps")
                treePerSecond = data.extras.getInt("tps")
                farmPerSecond = data.extras.getInt("fps")
                applePerSecondCost = data.extras.getInt("apsCost")
                treePerSecondCost = data.extras.getInt("atpsCost")
                farmPerSecondCost = data.extras.getInt("afpsCost")

                treeEnabled = data.extras.getBoolean("treeEnabled")
                if (treeEnabled) {
                    findViewById<Button>(R.id.tree).setEnabled(true)
                }

                farmEnabled = data.extras.getBoolean("farmEnabled")
                if (farmEnabled) {
                    findViewById<Button>(R.id.farm).setEnabled(true)
                }

                findViewById<TextView>(R.id.count).setText("Apples: " + money)

                if (data.extras.getBoolean("boughtApple") ||
                        data.extras.getBoolean("boughtTree") ||
                        data.extras.getBoolean("boughtFarm")) {
                    t.start()
                    findViewById<TextView>(R.id.applesPerSecond).setText(Integer.toString(applePerSecond))
                    findViewById<TextView>(R.id.treesPerSecond).setText(Integer.toString(treePerSecond))
                    findViewById<TextView>(R.id.farmsPerSecond).setText(Integer.toString(farmPerSecond))
                }
            }
        }
    }

    override fun onBackPressed() {
    }
}
